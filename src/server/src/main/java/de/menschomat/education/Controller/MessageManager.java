package de.menschomat.education.Controller;

import model.ChatMessage;
import utils.JsonUtil;
import utils.TimeUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class MessageManager {
    private Logger logger = Logger.getLogger("de.wikibooks");

    public MessageManager(int port) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Map<Long, Queue<ChatMessage>> queueMap = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            AtomicLong outThreadId = new AtomicLong(0);
            //READER
            executorService.execute(() -> {
                while (!socket.isClosed()) {
                    try {
                        try {
                            ChatMessage msg = (ChatMessage) new ObjectInputStream(socket.getInputStream()).readObject();
                            msg.setSender(String.valueOf(socket.getPort()));
                            queueMap.entrySet().stream()
                                    .filter(e -> e.getKey() != outThreadId.get())
                                    .forEach(e -> e.getValue().offer(msg)
                                    );
                        } catch (SocketException | EOFException | ClassNotFoundException e) {
                            logger.warning("Closing connection to " + socket.getPort() + " after problems.");
                            socket.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            //WRITER
            executorService.execute(() -> {
                try {
                    BlockingQueue<ChatMessage> queue = new LinkedBlockingQueue<ChatMessage>();
                    outThreadId.set(Thread.currentThread().getId());
                    queueMap.put(outThreadId.get(), queue);
                    while (!socket.isClosed()) {
                        ChatMessage msg = queue.poll(5, TimeUnit.SECONDS);
                        if (msg != null) {
                            try {
                                try {
                                    new ObjectOutputStream(socket.getOutputStream()).writeObject(msg);
                                } catch (SocketException e) {
                                    logger.warning("Closing connection to " + socket.getPort() + " after problems.");
                                    socket.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    queueMap.remove(Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        }
    }
}
