package view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GameUI {
    private Map<String, String> messages;
    private boolean isMuted =  false;

    public GameUI(String filepath) {
        messages = new HashMap<>();
        loadMessages(filepath);
    }

    private void loadMessages(String filepath) {
        try(Reader reader = new FileReader(filepath)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            messages = gson.fromJson(reader, type);
        }
        catch (Exception e) {
            System.out.println("Error at messages.json loading : " + e.getMessage());
        }
    }

    public String getMessage(String key, Object... args) {
        String msg = messages.getOrDefault(key, "[MISSING MSG: " + key + "]");
        if (args.length > 0) {
            return String.format(msg, args);
        }
        return msg;
    }

    public void setMuted(boolean muted) {
        this.isMuted = muted;
    }

    public void print(String key, Object... args) {
        if(!isMuted) {
            System.out.println(getMessage(key, args));
        }
    }

    public void printInline(String key, Object... args) {
        if (!isMuted) {
            System.out.print(getMessage(key, args));
        }
    }

    public void printRaw(String text) {
        if (!isMuted) {
            System.out.println(text);
        }
    }
}
