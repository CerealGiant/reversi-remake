package com.example.reversi;

import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class reversiLogic {
    private static ArrayList<ArrayList<String>> Board = new ArrayList<>();
    private ArrayList<gameObserver> observers = new ArrayList<>();
    private static ArrayList<Pair<Integer,Integer>> position_list = new ArrayList<>();
    private static final int rows = 8;
    private static final int columns = 8;
    private static String current_player;
    private static int whites = 0;
    private static int blacks = 0;
    private AI_Algo algo = null;
    private static Caretaker caretaker = null;


    public reversiLogic() {

        //Creating the 2D Array Board
        for(int i =0;i<columns;i++) {
            ArrayList<String> row = new ArrayList<>();
            for(int j =0;j<rows;j++) {
                if(j == 3 && i == 3) row.add(" W ");
                else if(j == 3 && i == 4) row.add(" B ");
                else if(j == 4 && i == 3) row.add(" B ");
                else if(j == 4 && i == 4) row.add(" W ");
                else row.add(" - ");
            }
            Board.add(i,row);
        }
        //Setting the current player and starting and ending pieces.
        current_player = " W ";
        whites = 2;
        blacks = 2;
        caretaker = new Caretaker();
    }

    public void setCurrentPlayer() {
        if(current_player == " B ") current_player = " W ";
        else current_player = " B ";
    }

    public void restartGame() {

        //Resetting the board
        for(int i =0;i<columns;i++) {
            for(int j =0;j<rows;j++) {
                if(i == 3 && j == 3) Board.get(3).set(3," W ");
                else if(i == 3 && j == 4) Board.get(3).set(4," B ");
                else if(i == 4 && j == 3) Board.get(4).set(3," B ");
                else if(i == 4 && j == 4) Board.get(4).set(4," W ");
                else Board.get(i).set(j," - ");
            }
        }
        current_player = " W ";
        whites = 2;
        blacks = 2;
        caretaker = new Caretaker();
    }




    public void printBoard() {
        for(int i = 0; i < rows; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                s.append(Board.get(i).get(j)+" ");
            }
            System.out.println(s);
            System.out.println();
        }
    }
//    public void placePiece() {
//        Scanner s = new Scanner(System.in);
//        System.out.println("Current Player: "+current_player);
//        showMoves();
//        printBoard();
//        int pos = s.nextInt();
//        updateBoard(pos);
//        setCurrentPlayer();
//    }


    private int piecesFlipped(int position_of_placed_piece) {
        //Flips the pieces of the board accordingly
        int row_of_current_piece = position_of_placed_piece / rows;
        int column_of_current_piece = position_of_placed_piece % rows;
        Board.get(row_of_current_piece).set(column_of_current_piece, current_player);


        int pieces_flipped = 0;

        //Checking across the row
        //for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {

            if (Board.get(row_of_current_piece).get(j).equals(current_player) && j != column_of_current_piece) {
                int lowerBound = Math.min(j, column_of_current_piece);
                int upperBound = Math.max(j, column_of_current_piece);

                for (int m = lowerBound; m <= upperBound; m++) {
                    if (Board.get(row_of_current_piece).get(m).equals(" - ") || Board.get(row_of_current_piece).get(m).equals(" * ")) break;
                    if (!Board.get(row_of_current_piece).get(m).equals(current_player) && !Board.get(row_of_current_piece).get(m).equals(" - ") && !Board.get(row_of_current_piece).get(m).equals(" * ")) {
                        System.out.println("[CHANGED1] " + row_of_current_piece + " " + m);
                        //Board.get(row_of_current_piece).set(m, current_player);
                        pieces_flipped++;
                    }
                }
            }
        }
        //}

        //Checking across the column
        for (int i = 0; i < rows; i++) {
            //for (int j = 0; j < columns; j++) {
            if (Board.get(i).get(column_of_current_piece).equals(current_player) && i != row_of_current_piece) {
                int lowerBound = Math.min(i, row_of_current_piece);
                int upperBound = Math.max(i, row_of_current_piece);

                for (int m = lowerBound; m <= upperBound; m++) {
                    if(Board.get(m).get(column_of_current_piece).equals(" - ") || Board.get(m).get(column_of_current_piece).equals(" * ")) break;
                    if (!Board.get(m).get(column_of_current_piece).equals(current_player) && !Board.get(m).get(column_of_current_piece).equals(" - ") && !Board.get(m).get(column_of_current_piece).equals(" * ")) {
                        System.out.println("[CHANGED2] " + m + " " + column_of_current_piece);
                        //Board.get(m).set(column_of_current_piece, current_player);
                        pieces_flipped++;
                    }
                }
            }
            //}
        }

        //Checking diagonally
        int temp_row = row_of_current_piece;
        int temp_col = column_of_current_piece;


        while(temp_col > 0 && temp_row > 0) {

            int new_row = temp_row - 1;
            int new_col = temp_col - 1;

            if (Board.get(new_row).get(new_col).equals(current_player) && (new_row != row_of_current_piece && new_col != column_of_current_piece) ) {
                System.out.println("[Check 1]");
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece >= new_col && rowOfNewPiece >= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED3] " + rowOfNewPiece + " " + colOfNewPiece);
                        //Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                        pieces_flipped++;
                    }
                    colOfNewPiece--;
                    rowOfNewPiece--;
                }

            }
            temp_col--;
            temp_row--;
        }


        temp_row = row_of_current_piece;
        temp_col = column_of_current_piece;

        while(temp_col < columns-1 && temp_row < rows-1) {

            int new_row = temp_row + 1;
            int new_col = temp_col + 1;

            if (Board.get(new_row).get(new_col).equals(current_player) && new_row != row_of_current_piece && new_col != column_of_current_piece ) {
                System.out.println("[Check 2]");
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece <= new_col && rowOfNewPiece <= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED4] " + rowOfNewPiece + " " + colOfNewPiece);
                        //Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                        pieces_flipped++;
                    }
                    colOfNewPiece++;
                    rowOfNewPiece++;
                }

            }

            temp_col++;
            temp_row++;
        }

        temp_row = row_of_current_piece;
        temp_col = column_of_current_piece;

        while(temp_col > 0 && temp_row < rows-1) {

            int new_row = temp_row + 1;
            int new_col = temp_col - 1;

            if (Board.get(new_row).get(new_col).equals(current_player)  ) {

                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                System.out.println("[TEST%%]" + new_row + " " + new_col);
                System.out.println("[CURRENT5]" + rowOfNewPiece + " " + colOfNewPiece);

                while(colOfNewPiece >= new_col && rowOfNewPiece <= new_row) {

                    System.out.println("[CHANGED5TEST] " + rowOfNewPiece + " " + colOfNewPiece);

                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED5] " + rowOfNewPiece + " " + colOfNewPiece);
                        //Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                        pieces_flipped++;
                    }
                    colOfNewPiece--;
                    rowOfNewPiece++;
                }

            }

            temp_col--;
            temp_row++;

        }

        temp_row = row_of_current_piece;
        temp_col = column_of_current_piece;

        while(temp_col < columns-1 && temp_row > 0) {

            int new_row = temp_row - 1;
            int new_col = temp_col + 1;

            if (Board.get(new_row).get(new_col).equals(current_player)  ) {
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece <= new_col && rowOfNewPiece >= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED6] " + rowOfNewPiece + " " + colOfNewPiece);
                        //Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                        pieces_flipped++;
                    }
                    colOfNewPiece++;
                    rowOfNewPiece--;
                }

            }

            temp_col++;
            temp_row--;

        }

        return pieces_flipped;


    }






    public void updateBoard(int position_of_placed_piece) {
        //Flips the pieces of the board accordingly
        int row_of_current_piece = position_of_placed_piece / rows;
        int column_of_current_piece = position_of_placed_piece % rows;
        Board.get(row_of_current_piece).set(column_of_current_piece, current_player);


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Board.get(i).get(j).equals(" * ")) Board.get(i).set(j, " - ");
            }
        }

        //Checking across the row
        //for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if (Board.get(row_of_current_piece).get(j).equals(current_player) && j != column_of_current_piece) {
                    int lowerBound = Math.min(j, column_of_current_piece);
                    int upperBound = Math.max(j, column_of_current_piece);

                    for (int m = lowerBound; m <= upperBound; m++) {
                        if (Board.get(row_of_current_piece).get(m).equals(" - ") || Board.get(row_of_current_piece).get(m).equals(" * ")) break;
                            if (!Board.get(row_of_current_piece).get(m).equals(current_player) && !Board.get(row_of_current_piece).get(m).equals(" - ") && !Board.get(row_of_current_piece).get(m).equals(" * ")) {
                                System.out.println("[CHANGED1] " + row_of_current_piece + " " + m);
                                Board.get(row_of_current_piece).set(m, current_player);
                        }
                    }
                }
            }
        //}

        //Checking across the column
        for (int i = 0; i < rows; i++) {
            //for (int j = 0; j < columns; j++) {
                if (Board.get(i).get(column_of_current_piece).equals(current_player) && i != row_of_current_piece) {
                    int lowerBound = Math.min(i, row_of_current_piece);
                    int upperBound = Math.max(i, row_of_current_piece);

                    for (int m = lowerBound; m <= upperBound; m++) {
                        if(Board.get(m).get(column_of_current_piece).equals(" - ") || Board.get(m).get(column_of_current_piece).equals(" * ")) break;
                        if (!Board.get(m).get(column_of_current_piece).equals(current_player) && !Board.get(m).get(column_of_current_piece).equals(" - ") && !Board.get(m).get(column_of_current_piece).equals(" * ")) {
                            System.out.println("[CHANGED2] " + m + " " + column_of_current_piece);
                            Board.get(m).set(column_of_current_piece, current_player);
                        }
                    }
                }
            //}
        }

        //Checking diagonally
        int temp_row = row_of_current_piece;
        int temp_col = column_of_current_piece;


        while(temp_col > 0 && temp_row > 0) {

            int new_row = temp_row - 1;
            int new_col = temp_col - 1;

            if (Board.get(new_row).get(new_col).equals(current_player) && (new_row != row_of_current_piece && new_col != column_of_current_piece) ) {
                System.out.println("[Check 1]");
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece >= new_col && rowOfNewPiece >= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED3] " + rowOfNewPiece + " " + colOfNewPiece);
                        Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                    }
                    colOfNewPiece--;
                    rowOfNewPiece--;
                }

            }
                temp_col--;
                temp_row--;
        }


        temp_row = row_of_current_piece;
        temp_col = column_of_current_piece;

        while(temp_col < columns-1 && temp_row < rows-1) {

            int new_row = temp_row + 1;
            int new_col = temp_col + 1;

            if (Board.get(new_row).get(new_col).equals(current_player) && new_row != row_of_current_piece && new_col != column_of_current_piece ) {
                System.out.println("[Check 2]");
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece <= new_col && rowOfNewPiece <= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED4] " + rowOfNewPiece + " " + colOfNewPiece);
                        Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                    }
                    colOfNewPiece++;
                    rowOfNewPiece++;
                }

            }

                temp_col++;
                temp_row++;
        }

        temp_row = row_of_current_piece;
        temp_col = column_of_current_piece;

        while(temp_col > 0 && temp_row < rows-1) {

            int new_row = temp_row + 1;
            int new_col = temp_col - 1;

            if (Board.get(new_row).get(new_col).equals(current_player)  ) {

                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                System.out.println("[TEST%%]" + new_row + " " + new_col);
                System.out.println("[CURRENT5]" + rowOfNewPiece + " " + colOfNewPiece);

                while(colOfNewPiece >= new_col && rowOfNewPiece <= new_row) {

                    System.out.println("[CHANGED5TEST] " + rowOfNewPiece + " " + colOfNewPiece);

                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED5] " + rowOfNewPiece + " " + colOfNewPiece);
                        Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                    }
                    colOfNewPiece--;
                    rowOfNewPiece++;
                }

            }

            temp_col--;
            temp_row++;

            }

            temp_row = row_of_current_piece;
            temp_col = column_of_current_piece;

        while(temp_col < columns-1 && temp_row > 0) {

            int new_row = temp_row - 1;
            int new_col = temp_col + 1;

            if (Board.get(new_row).get(new_col).equals(current_player)  ) {
                int colOfNewPiece = column_of_current_piece;
                int rowOfNewPiece = row_of_current_piece;

                while(colOfNewPiece <= new_col && rowOfNewPiece >= new_row) {
                    if (Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") || Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) break;

                    if (!Board.get(rowOfNewPiece).get(colOfNewPiece).equals(current_player) && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" - ") && !Board.get(rowOfNewPiece).get(colOfNewPiece).equals(" * ")) {
                        System.out.println("[CHANGED6] " + rowOfNewPiece + " " + colOfNewPiece);
                        Board.get(rowOfNewPiece).set(colOfNewPiece,current_player);
                    }
                    colOfNewPiece++;
                    rowOfNewPiece--;
                }

            }

            temp_col++;
            temp_row--;

        }


        }

    public void showMoves() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Board.get(i).get(j).equals(current_player)) {

                    //Check across column
                    if (i + 1 < rows) {
                        if (!Board.get(i + 1).get(j).equals(current_player) && !Board.get(i + 1).get(j).equals(" - ") && !Board.get(i + 1).get(j).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square below the current color piece
                            int row_pos = i + 1;
                            while (!Board.get(row_pos).get(j).equals(current_player) && !Board.get(row_pos).get(j).equals(" - ") && !Board.get(row_pos).get(j).equals(" * ") && row_pos < rows-1) {
                                if (Board.get(row_pos).get(j).equals(" - ") || Board.get(row_pos).get(j).equals(" * ") ) break;
                                row_pos++;
                            }
                            System.out.println("[TEST1]" + row_pos);
                            if ( (row_pos < rows) && (Board.get(row_pos).get(j).equals(" - ") || Board.get(row_pos).get(j).equals(" * ")) ) {Board.get(row_pos).set(j, " * ");Pair p = new Pair(row_pos,j);position_list.add(p);}

                        }
                    }

                    if (i - 1 >= 0) {
                        if (!Board.get(i - 1).get(j).equals(current_player) && !Board.get(i - 1).get(j).equals(" - ") && !Board.get(i - 1).get(j).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square above the current color piece
                            int row_pos = i - 1;
                            while (row_pos >= 0 && !Board.get(row_pos).get(j).equals(current_player) && !Board.get(row_pos).get(j).equals(" - ") && !Board.get(row_pos).get(j).equals(" * ") ) {
                                if (Board.get(row_pos).get(j).equals(" - ") || Board.get(row_pos).get(j).equals(" * ")) break;
                                row_pos--;
                            }
                            System.out.println("[TEST2]" + row_pos);
                            if ( (row_pos >= 0) && (Board.get(row_pos).get(j).equals(" - ") || Board.get(row_pos).get(j).equals(" * ")) ) {Board.get(row_pos).set(j, " * ");Pair p = new Pair(row_pos,j);position_list.add(p);}
                        }
                    }

                    //Checking across row
                    if (j + 1 < columns) {
                        if (!Board.get(i).get(j + 1).equals(current_player) && !Board.get(i).get(j + 1).equals(" - ") && !Board.get(i).get(j + 1).equals(" * ")) {
                            int col_pos = j + 1;
                            while (!Board.get(i).get(col_pos).equals(current_player) && !Board.get(i).get(col_pos).equals(" - ") && !Board.get(i).get(col_pos).equals(" * ") && col_pos < columns-1) {
                                if (Board.get(i).get(col_pos).equals(" - ") || Board.get(i).get(col_pos).equals(" * ")) break;
                                col_pos++;
                            }
                            System.out.println("[TEST3]" + col_pos);
                            if ( (col_pos < rows) && (Board.get(i).get(col_pos).equals(" - ") || Board.get(i).get(col_pos).equals(" * "))) {Board.get(i).set(col_pos, " * ");Pair p = new Pair(i,col_pos);position_list.add(p);}
                        }

                    }
                    if (j - 1 >= 0) {
                        if (!Board.get(i).get(j - 1).equals(current_player) && !Board.get(i).get(j - 1).equals(" - ") && !Board.get(i).get(j - 1).equals(" * ")) {
                            int col_pos = j - 1;
                            while (col_pos >= 0 && !Board.get(i).get(col_pos).equals(current_player) && !Board.get(i).get(col_pos).equals(" - ") && !Board.get(i).get(col_pos).equals(" * ") ) {
                                if (Board.get(i).get(col_pos).equals(" - ") || Board.get(i).get(col_pos).equals(" * ")) break;
                                col_pos--;
                            }
                            System.out.println("[TEST4]" + col_pos);
                            if ((col_pos >= 0) && (Board.get(i).get(col_pos).equals(" - ") || Board.get(i).get(col_pos).equals(" * ")) ) {Board.get(i).set(col_pos, " * ");Pair p = new Pair(i,col_pos);position_list.add(p);}
                        }

                    }

                    //Checking diagonally
                    if (i - 1 >= 0 && j + 1 < columns) {
                        if (!Board.get(i - 1).get(j + 1).equals(current_player) && !Board.get(i - 1).get(j + 1).equals(" - ") && !Board.get(i - 1).get(j + 1).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square diagonally above the current color piece
                            int row_pos = i - 1;
                            int col_pos = j + 1;
                            while (row_pos >= 0 && !Board.get(row_pos).get(col_pos).equals(current_player) && !Board.get(row_pos).get(col_pos).equals(" - ") && !Board.get(row_pos).get(col_pos).equals(" * ") && col_pos < columns-1 ) {
                                if (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) break;
                                row_pos--;
                                col_pos++;
                            }
                            if ( (col_pos < columns && row_pos >= 0) && (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) ) {Board.get(row_pos).set(col_pos, " * ");Pair p = new Pair(row_pos,col_pos);position_list.add(p);}

                        }
                    }

                    if (i + 1 < rows && j - 1 >= 0) {
                        if (!Board.get(i + 1).get(j - 1).equals(current_player) && !Board.get(i + 1).get(j - 1).equals(" - ") && !Board.get(i + 1).get(j - 1).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square diagonally above the current color piece
                            int row_pos = i + 1;
                            int col_pos = j - 1;
                            if(row_pos < rows && col_pos >= 0) {
                                while (col_pos >= 0 && !Board.get(row_pos).get(col_pos).equals(current_player) && !Board.get(row_pos).get(col_pos).equals(" - ") && !Board.get(row_pos).get(col_pos).equals(" * ") && row_pos < rows - 1) {
                                    if (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * "))
                                        break;
                                    row_pos++;
                                    col_pos--;
                                }
                                if ( (col_pos >= 0 && row_pos < rows) && (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) ) {Board.get(row_pos).set(col_pos, " * ");Pair p = new Pair(row_pos,col_pos);position_list.add(p);}
                            }
                        }
                    }

                    if (i - 1 >= 0 && j - 1 >= 0) {
                        if (!Board.get(i - 1).get(j - 1).equals(current_player) && !Board.get(i - 1).get(j - 1).equals(" - ") && !Board.get(i - 1).get(j - 1).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square diagonally above the current color piece
                            int row_pos = i - 1;
                            int col_pos = j - 1;
                            if(row_pos >= 0 && col_pos >= 0) {
                                while (col_pos >= 0 && row_pos >= 0 && !Board.get(row_pos).get(col_pos).equals(current_player) && !Board.get(row_pos).get(col_pos).equals(" - ") && !Board.get(row_pos).get(col_pos).equals(" * ")) {
                                    if (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * "))
                                        break;
                                    row_pos--;
                                    col_pos--;
                                }
                                if ( (col_pos >= 0 && row_pos >= 0) && (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) ) {Board.get(row_pos).set(col_pos, " * ");Pair p = new Pair(row_pos,col_pos);position_list.add(p);}
                            }
                        }
                    }

                    if (i + 1 < rows && j + 1 < columns) {
                        if (!Board.get(i + 1).get(j + 1).equals(current_player) && !Board.get(i + 1).get(j + 1).equals(" - ") && !Board.get(i + 1).get(j + 1).equals(" * ")) {
                            //Means that there is a piece of opp color 1 square diagonally above the current color piece
                            int row_pos = i + 1;
                            int col_pos = j + 1;
                            if(row_pos < rows && col_pos < columns) {
                                while (!Board.get(row_pos).get(col_pos).equals(current_player) && !Board.get(row_pos).get(col_pos).equals(" - ") && !Board.get(row_pos).get(col_pos).equals(" * ") && col_pos < columns - 1 && row_pos < rows - 1) {
                                    if (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) break;
                                    row_pos++;
                                    col_pos++;
                                }
                                if ((col_pos < columns && row_pos < rows) && (Board.get(row_pos).get(col_pos).equals(" - ") || Board.get(row_pos).get(col_pos).equals(" * ")) ){Board.get(row_pos).set(col_pos, " * ");Pair p = new Pair(row_pos,col_pos);position_list.add(p);}
                            }
                        }
                    }

                }
            }
        }
    }

    public void updateBoard(ArrayList<ArrayList<String>> board) {
        Board = board;
    }

    public Caretaker getCareTaker(){return caretaker;}

    public void updatePieceCount() {
        blacks = 0;
        whites = 0;
        for(int row = 0;row<rows;row++) {
            for(int col=0;col<columns;col++) {
                if(!Board.get(row).get(col).equals(" - ")) {
                    if(Board.get(row).get(col).equals(" B ")) blacks++;
                    else if(Board.get(row).get(col).equals(" W ") ) whites++;
                }
            }
        }
    }


    public int checkStatus() {
        int count = 0;


        for(int row = 0;row<rows;row++) {
            for(int col=0;col<columns;col++) {
                if(!Board.get(row).get(col).equals(" - ") && !Board.get(row).get(col).equals(" * ")) count++;
            }
        }
        updatePieceCount();

        if(count == blacks*whites) {
            //Means board is filled
            if(blacks > whites) {System.out.println("Black Wins"); return 1;}
            else {System.out.println("White Wins");return 2; }
        }else if(position_list.size() == 0) {
            if(blacks > whites) {System.out.println("Black Wins"); return 1;}
            else {System.out.println("White Wins");return 2; }
        }
        return 0;
    }

    public String getCurrent_player() {
        return current_player;
    }

    public void updateAll() {
        for(gameObserver o: observers) {
            o.update();
        }
    }

    public void addObserver(gameObserver o) {
        observers.add(o);
    }

    public int getWhites() {return whites;}

    public int getBlacks() {return blacks;}


    public void setAlgo(AI_Algo a) {
        this.algo = a;
    }

    public AI_Algo getAlgo() {
        return algo;
    }

    public ArrayList<ArrayList<String>> GetBoard() {return Board;}
    public ArrayList<Pair<Integer,Integer>> GetPositions() {return position_list;}


    public static class GameButtonCommands implements ICommand, EventHandler<ActionEvent> {


        public static class randomAI implements ICommand, EventHandler<ActionEvent> {

            private Stage s;
            private Scene destScene;
            public AI_Algo algo_random = null;
            private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();
            private reversiLogic r;


            public randomAI(Stage s,Scene dest,ArrayList<Pair<Integer,Integer>> positions,reversiLogic r,AI_Algo algo) {
                this.s = s;
                destScene = dest;
                possible_positions = positions;
                this.r = r;
                this.algo_random = algo;
            }

            @Override
            public void execute() {
                System.out.println("CLIKCED HEREE");
                algo_random = new randomAlgorithm(possible_positions);
                r.setAlgo(algo_random);
                s.setScene(destScene);
            }

            @Override
            public void handle(ActionEvent actionEvent) {
                execute();
            }
        }

        private reversiLogic r;
        private Button b;
        private String white_player = "-fx-background-color: transparent; \n"  +
                "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_white.png');";
        private String black_player = "-fx-background-color: transparent; \n"  +
                "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_black.png');";

        private String canPlacePieceHere = "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/place_piece.png'); \n" +
                                                "-fx-background-color:transparent; \n"  +
                                                "-fx-background-repeat: no-repeat;";

        private AI_Algo algo = null;

        public GameButtonCommands(Button b, reversiLogic r,AI_Algo algo) {
            this.b = b;
            this.r = r;
            this.algo = algo;
        }


        @Override
        public void execute() {

            //Only if the board piece is an acceptable move
            if(b.getStyle().equals(canPlacePieceHere) ) {

                //Since a move has been made, we will save the previous state of the board
                ArrayList<ArrayList<String>> old_board = new ArrayList<>();

                for(int i = 0;i<8;i++) {
                    ArrayList<String> row = new ArrayList<>();
                    for(int j =0;j<8;j++) {
                        row.add(Board.get(i).get(j));
                    }
                    old_board.add(row);
                }

                caretaker.push(old_board);

                System.out.println("TESSTTTTTTTTTT");
                for(int i =0;i<8;i++){
                    for(int j=0;j<8;j++){
                        System.out.print(old_board.get(i).get(j) + " ");
                    }
                    System.out.println();
                }

                System.out.println("Saved old board");

                //Updating the board based on the current piece placed.
                r.updateBoard((int)b.getUserData());

                //Setting board style accordingly
                if (current_player.equals(" W ")) b.setStyle(white_player);
                else b.setStyle(black_player);

                //Updating the next player
                r.setCurrentPlayer();
                position_list.clear();
                r.showMoves();

                r.updateAll();

                System.out.println("BEFORE AI");
                r.printBoard();


                if(current_player.equals(" B ")) {

                    //algo = new mostPiecesAlgorithm(position_list,Board,current_player);
                    int pos_chosen = algo.algorithm();
                    r.updateBoard(pos_chosen);
                    r.setCurrentPlayer();
                    position_list.clear();
                    r.showMoves();
                    r.updateAll();

//                    algo = new randomAlgorithm(position_list);
//                    int pos_chosen = algo.algorithm();
//                    System.out.println("POSITIONS AVAILABLE: " +position_list.size() + " POSITION CHOSEN: " + pos_chosen);
//                    Pair position = position_list.get(pos_chosen);
//                    System.out.println("POSITION: " + position.getKey() + " " +position.getValue());
//                    int p = (((int)position.getKey()*8) + (int)position.getValue());
//                    r.updateBoard(p);
//
//
//                    r.setCurrentPlayer();
//                    position_list.clear();
//                    r.showMoves();
//
//                    r.updateAll();

                }


                r.printBoard();
                r.updatePieceCount();


                //Updating the respective observers
                r.updateAll();
            }

        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }

}
