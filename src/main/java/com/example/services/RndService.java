package com.example.services;

import com.google.inject.Singleton;

import java.util.Random;

@Singleton
public class RndService {
    public int num;

    public RndService() {
        num = new Random().nextInt(100);
    }
}
