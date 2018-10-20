package com.atomicobject.othello;

import java.util.Arrays;
import java.util.ListIterator;

public class AI {

	ListIterator<int[]> moveList;

	public AI(int[][] moves) {
		moveList = Arrays.asList(moves).listIterator();
	}

	public int[] computeMove(GameState state) {
		System.out.println("AI returning canned move for game state - " + state);
		return moveList.next();
	}

	public int heuristic(GameState state) {
		board = state.getBoard();
		myColor = state.getPlayer();
		int[][] legalMoves;
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalMove(board, i, j, myColor)) {
					legalMoves.add([i, j]);
				}
			}
		}

	}

	public boolean legalMove(int[][] board, int row, int col, int myColor) {
		if(board[row][col] == 0) {
			for(int i = row - 1; i <= row+1; i++ ) {
				if(i > 0 && 7 > i) {
					for(int j = col - 1; j <= col+1; j++) {
						if(j > 0 && 7 > j) {
							if(board[i][j] != myColor && board[i][j] != 0) {
								if(checkLegalVectors(board, row, col, i, j, myColor)) {
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

	public boolean checkLegalVectors(int[][] board, int row, int col, int adjRow, int adjCol, int myColor) {}
		rowVec = adjRow - row;
		colVec = adjCol - col;
		for(i = 2;  i<= 7; i++) {
			j = row + (i*rowVec)
			k = col + (i*colVec)
			if(j < 0 || 7 < j ) {
				if(k < 0 || 7 < k ) {
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
	}
}
