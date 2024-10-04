package by.it_academy.jd2.service;

import by.it_academy.jd2.entity.EStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestJson {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("USD", 123);
        map.put("EUR", 321);

        String json = gson.toJson(map);
        System.out.println(json);

        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> read = gson.fromJson(json, type);
        System.out.println(read);

    }
}
