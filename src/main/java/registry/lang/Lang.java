package registry.lang;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lang {

    private static Map<String, Object> data = Collections.emptyMap();

    // load ngôn ngữ
    public static void load(String lang) {
        try {
            InputStreamReader reader = new InputStreamReader(
                    Objects.requireNonNull(Lang.class.getResourceAsStream("/lang/" + lang + ".json")),
                    StandardCharsets.UTF_8
            );

            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> raw = new Gson().fromJson(reader, type);

            data = deepUnmodifiable(raw);

        } catch (Exception e) {
            System.out.println("Load language failed!");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (data == null) return key;

        String[] keys = key.split("\\.");
        Object current = data;

        for (String k : keys) {
            if (!(current instanceof Map)) return key;
            current = ((Map<?, ?>) current).get(k);
            if (current == null) return key;
        }

        return current.toString();
    }

    public static Map<String, Object> getData() {
        return data;
    }

    private static Map<String, Object> deepUnmodifiable(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();

            if (value instanceof Map) {
                value = deepUnmodifiable((Map<String, Object>) value);
            }

            result.put(entry.getKey(), value);
        }

        return Collections.unmodifiableMap(result);
    }
}