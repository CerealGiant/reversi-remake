package com.example.reversi;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class mostPiecesAlgorithm implements AI_Algo{

    private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();
    private int rows = 8;
    private int columns = 8;
    private ArrayList<ArrayList<String>> Board;
    private String current_player;

    public mostPiecesAlgorithm(ArrayList<Pair<Integer,Integer>> positions,ArrayList<ArrayList<String>> board,String player) {
        possible_positions = positions;
        this.Board = board;
        this.current_player = player;
    }


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


        Board.get(row_of_current_piece).set(column_of_current_piece, " - ");

        return pieces_flipped;

    }



    @Override
    public int algorithm() {

        Pair max_pos = null;
        int max_pieces = 0;

        for(Pair p: possible_positions) {
            int pos = (((int)p.getKey()*8) + (int)p.getValue());

            int pieces_flipped = piecesFlipped(pos);
            System.out.println("PIECES FLIPPED: " + pieces_flipped);

            if(pieces_flipped > max_pieces) {
                max_pieces = pieces_flipped;
                max_pos = p;
            }
        }

        int maxPiecesPos = (((int)max_pos.getKey()*8) + (int)max_pos.getValue());

        return maxPiecesPos;
    }
}
