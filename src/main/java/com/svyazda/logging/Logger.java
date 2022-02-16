package com.svyazda.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.svenson.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Logger {

    protected static List<Object> data = new ArrayList<Object>();

    public static void writeToLogs(Map<String, Object> map) throws IOException {
        try {

            //final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            //final Object object = mapper.convertValue(map, Object.class);

            readFromLogs();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Writer writer = Files.newBufferedWriter(Paths.get("log.json"));

            data.add(map);

            gson.toJson(data, writer);

            writer.close();

            data.clear();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//
//            // Content is appended (due to StandardOpenOption.APPEND)
//            Files.write(new File("log.json").toPath(), List.of(json), StandardOpenOption.APPEND);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void readFromLogs() {
        try {

            Reader reader = Files.newBufferedReader(Paths.get("log.json"));

            data = new Gson().fromJson(reader, new TypeToken<List<Object>>() {}.getType());

            if (data == null) {
                data = new ArrayList<Object>();
            }

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
