package com.example.reversi;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class randomAlgorithm implements AI_Algo{

    private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();

    public randomAlgorithm(ArrayList<Pair<Integer,Integer>> positions) {
        possible_positions = positions;
    }


    @Override
    public int algorithm() {
        System.out.println("MOST PIECES!!");
        int options = possible_positions.size();
        Random random = new Random();
        int place = random.nextInt(options-1);
        Pair p = possible_positions.get(place);
        int pos = (((int)p.getKey()*8) + (int)p.getValue());

        return pos;
    }
}
