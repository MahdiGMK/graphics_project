package com.mahdigmk.apaa.Model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String username, password;
    private int pfp;
    private int score;

    public User(String username, String password, int pfp) {
        this.username = username;
        this.password = password;
        this.pfp = pfp;
        score = 0;
    }

    public static User getUser(String username) {
        File file = new File("Data/Users/" + username + "/data.json");
        if (!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            reader.close();
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File("Data/Users");
        for (File userFolder : folder.listFiles(File::isDirectory)) {
            users.add(getUser(userFolder.getName()));
        }
        return users;
    }

    @Override
    public String toString() {
        return username;
    }

    public void save() {
        File file = new File("Data/Users/" + username + "/data.json");

        Gson gson = new Gson();
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPfp() {
        return pfp;
    }

    public void setPfp(int pfp) {
        this.pfp = pfp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
