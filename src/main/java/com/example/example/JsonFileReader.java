package com.example.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements DataReader {
    private final String filePath;

    public JsonFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<String, List<Map<String, Object>>> readData() throws DataReadException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new DataReadException("Error reading data from file", e);
        }
    }
}
