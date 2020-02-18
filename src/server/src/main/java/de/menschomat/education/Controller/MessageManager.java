package de.menschomat.education.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MessageManager {
    public MessageManager(int port) throws IOException {
        ExecutorService inputHandlet = Executors.newFixedThreadPool(8);
        ExecutorService outputHandler = Executors.newFixedThreadPool(8);
        Map<Long, Queue> queueMap = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            AtomicLong outThreadId = new AtomicLong(0);
            inputHandlet.execute(() -> {
                while (!socket.isClosed()) {
                    try {
                        try {
                            String msg = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream())
                            ).readLine();
                            queueMap.entrySet().stream()
                                    .filter(e -> e.getKey() != outThreadId.get())
                                    .forEach(e -> e.getValue()
                                            .offer(String.format("[%s]: %s", socket.getPort(), msg))
                                    );
                        } catch (SocketException e) {
                            System.out.println("closing");
                            socket.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            outputHandler.execute(() -> {
                try {
                    BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
                    outThreadId.set(Thread.currentThread().getId());
                    queueMap.put(outThreadId.get(), queue);
                    while (!socket.isClosed()) {
                        String msg = null;

                        msg = queue.poll(5, TimeUnit.SECONDS);

                        if (msg != null) {
                            try {
                                try {
                                    new PrintWriter(socket.getOutputStream(), true).println(msg);
                                } catch (SocketException e) {
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
