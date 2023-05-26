package com.mahdigmk.apaa.Model.Game;

import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import com.mahdigmk.apaa.Model.DifficultyLevel;
import com.mahdigmk.apaa.Model.Map;

import java.io.*;
import java.util.ArrayList;

public class GameData {
    private final DifficultyLevel difficultyLevel;
    private final Map map;
    private final float planetRadius, ballRadius;
    private final ArrayList<BallData> balls;
    private double rotation;

    public GameData(DifficultyLevel difficultyLevel, Map map, float range, float ballRadius, double rotation, ArrayList<BallData> balls) {
        this.difficultyLevel = difficultyLevel;
        this.map = map;
        this.planetRadius = range;
        this.ballRadius = ballRadius;
        this.rotation = rotation;
        this.balls = new ArrayList<>(balls);
    }

    public GameData(DifficultyLevel difficultyLevel, Map map, float radius, float ballRadius) {
        this.difficultyLevel = difficultyLevel;
        this.map = map;
        this.planetRadius = radius;
        this.ballRadius = ballRadius;
        rotation = 0;
        balls = new ArrayList<>();
        float slice = 2 * MathUtils.PI / map.getInitialBallCount();
        for (int i = 0; i < map.getInitialBallCount(); i++) balls.add(new BallData(slice * i, -1, -1));
    }

    public static GameData load() {
        File file = new File("Data/gamesave.json");
        if (!file.exists())
            return null;

        try {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            GameData data = gson.fromJson(reader, GameData.class);
            reader.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public float getBallRadius() {
        return ballRadius;
    }

    public float getPlanetRadius() {
        return planetRadius;
    }

    public Map getMap() {
        return map;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public ArrayList<BallData> getBalls() {
        return balls;
    }

    public void save() {
        File file = new File("Data/gamesave.json");
        file.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate(BallData data) {
        final double circle = 2 * MathUtils.PI;
        double loc = data.location();
        for (BallData ball : balls) {
            double loc2 = ball.location();
            double delta = (loc2 - loc) % circle;
            delta = Math.min(delta, circle - delta);
            if (delta * planetRadius < ballRadius * 2) return false;
        }
        return true;
    }
}