package com.example.example;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataReader {
    Map<String, List<Map<String, Object>>> readData() throws IOException;
}
