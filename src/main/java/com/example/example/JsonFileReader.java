package com.example.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements DataReader {
    private final InputStream inputStream;

    public JsonFileReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Map<String, List<Map<String, Object>>> readData() throws DataReadException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, new TypeReference<Map<String, List<Map<String, Object>>>>() {
            });
        } catch (IOException e) {
            throw new DataReadException("Error reading data from file", e);
        }
    }
}
