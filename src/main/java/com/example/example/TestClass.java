package com.example.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestClass {

    public TestClass(@Autowired BCryptPasswordEncoder encoder) {
        System.out.println(encoder);
    }
}
