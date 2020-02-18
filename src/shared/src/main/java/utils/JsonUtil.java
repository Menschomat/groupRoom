package utils;

import com.google.gson.Gson;
import model.ChatMessage;

public abstract class JsonUtil {
    static final Gson gson = new Gson();

    public static String messageToJsonString(ChatMessage in) {
        return gson.toJson(in);
    }

    public static ChatMessage messageFromJsonString(String in) {
        return gson.fromJson(in, ChatMessage.class);
    }
}
