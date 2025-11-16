package com.example.clientap6.user;

import com.example.clientap6.objects.Snake;
import java.util.ArrayList;

public class User {
    private final String name;

    public User(String name) throws Exception {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
