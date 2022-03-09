package ru.job4j.concurrent.executorservice;

public class User {
    private String username;
    private String email;

    public User(String name, String email) {
        this.username = name;
        this.email = email;
    }

    public String getName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
