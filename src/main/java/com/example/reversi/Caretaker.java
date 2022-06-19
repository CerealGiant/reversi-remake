package com.example.reversi;

import java.util.ArrayList;

public class Caretaker {
    //We will be remembering the state of board before the player's move and save it here
    private ArrayList<ArrayList<ArrayList<String>>> memory = new ArrayList<>();

    public Caretaker() {
    }

    public void push(ArrayList<ArrayList<String>> data) {
        ArrayList<ArrayList<String>> dataCopy = new ArrayList<>();
        dataCopy.addAll(data);
        //This function is called everytime a player move is made
        memory.add(dataCopy);
        System.out.println("SIZE OF MEMORY: " + memory.size());
    }

    public int getSize() {return memory.size();}

    public Boolean isEmpty() {
        return memory.size() == 0;
    }


    public ArrayList<ArrayList<String>> pop() {
        int lastIndex = memory.size() - 1;
        ArrayList<ArrayList<String>> s = memory.get(lastIndex);
        memory.remove(lastIndex);

        //s represents the state the board was at before the player's move.
        return s;
    }
}
