package by.it_academy.jd2.controller.servlet.delete;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        // Получаем данные о дате в виде Map

        JsonNode node = p.getCodec().readTree(p);

        var year = node.get("year").asInt();
        int month = node.get("month").intValue();
        int day = node.get("day").intValue();

        // Возвращаем объект LocalDate
        return LocalDate.of(year, month, day);
    }
}
