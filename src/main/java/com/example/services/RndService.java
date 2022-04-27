package com.example.services;

import com.google.inject.Singleton;

import java.util.Random;

@Singleton
public class RndService {
    public int num;
    private Random random = new Random();

    public RndService() {
        num = new Random().nextInt(100);
        random = new Random();
    }

    public int getRandomInt() {
        return random.nextInt();
    }
}
