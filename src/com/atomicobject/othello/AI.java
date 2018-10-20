package com.atomicobject.othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AI {

    private ListIterator<int[]> moveList;

    private int[] bestMove;

    private final static int[][] CORNERS = new int[][]{{0,0},
                                                {0,7},
                                                {7,0},
                                                {7,7}};


    public AI(int[][] moves) {
        moveList = Arrays.asList(moves).listIterator();
    }

    public int[] computeMove(GameState state) {
        System.out.println("AI returning canned move for game state - " + state);

        // Determine legal moves.


        Minimax(state,1);
        return bestMove;
        //moveList.add(bestMove);
        //return moveList.next();
    }

    private int Minimax(GameState state, int depth) {
        List<int[]> legalMoves = determineLegalMoves(state);
        int currentValue;
        if (depth == 0) {
            return value(state, legalMoves);
        }
        if (state.getPlayer() == 1) {
            currentValue = Integer.MIN_VALUE;
            for (int i = 0; i < legalMoves.size(); i++) {
                int[][] childBoard = state.getBoard();
                childBoard[legalMoves.get(i)[0]][legalMoves.get(i)[1]] = 1;
                GameState childState = state;
                childState.setBoard(childBoard);
                childState.setPlayer(2);
                int minimaxResult = Minimax(childState, depth - 1);
                if (currentValue < minimaxResult) {
                    bestMove = legalMoves.get(i);
                    currentValue = minimaxResult;
                }
            }
        } else {
            currentValue = Integer.MAX_VALUE;
            for (int i = 0; i < legalMoves.size(); i++) {
                int[][] childBoard = state.getBoard();
                childBoard[legalMoves.get(i)[0]][legalMoves.get(i)[1]] = 2;
                GameState childState = state;
                childState.setBoard(childBoard);
                childState.setPlayer(1);
                int minimaxResult = Minimax(childState, depth - 1);
                if (currentValue > minimaxResult) {
                    bestMove = legalMoves.get(i);
                    currentValue = minimaxResult;
                }
            }
        }
        return currentValue;
    }

    public int value(GameState state, List<int[]> legalMoves) {
        int currentValue = 0;
        int[][] board = state.getBoard();
        for(int i = 0; i<4; i++) {
            if(board[CORNERS[i][0]][CORNERS[i][1]] == 1) {
                currentValue += 10;
            } else if(board[CORNERS[i][0]][CORNERS[i][1]] == 2) {
                currentValue -=10;
            }
        }
        if(state.getPlayer() == 1) {
            currentValue += legalMoves.size()*2;
            state.setPlayer(2);
            List<int[]> opponentLegalMoves = determineLegalMoves(state);
            currentValue -= opponentLegalMoves.size()*2;
        } else {
            currentValue -= legalMoves.size()*2;
            state.setPlayer(1);
            List<int[]> opponentLegalMoves = determineLegalMoves(state);
            currentValue += opponentLegalMoves.size()*2;
        }
        return currentValue;
    }

    public List<int[]> determineLegalMoves(GameState state) {
        int[][] board = state.getBoard();
        int myColor = state.getPlayer();
        List<int[]> legalMoves = new ArrayList<int[]>();
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (legalMove(board, i, j, myColor)) {
                    int[] legal = new int[2];
                    legal[0] = i;
                    legal[1] = j;
                    legalMoves.add(legal);              //.([i, j]);
                }
            }
        }
        return legalMoves;
    }

    public boolean legalMove(int[][] board, int row, int col, int myColor) {
        if (board[row][col] == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                if (i > 0 && 7 > i) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (j > 0 && 7 > j) {
                            if (board[i][j] != myColor && board[i][j] != 0) {
                                if (checkLegalVectors(board, row, col, i, j, myColor)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean checkLegalVectors(int[][] board, int row, int col, int adjRow, int adjCol, int myColor) {
        int rowVec = adjRow - row;
        int colVec = adjCol - col;
        for (int i = 2; i <= 7; i++) {
            int j = row + (i * rowVec);
            int k = col + (i * colVec);
            if (j > 0 && 7 > j) {
                if (k > 0 && 7 > k) {
                    if (board[j][k] == myColor) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
