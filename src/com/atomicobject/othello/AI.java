package com.atomicobject.othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class AI {

	ListIterator<int[]> moveList;

	public AI(int[][] moves) {
		moveList = Arrays.asList(moves).listIterator();
	}

	public int[] computeMove(GameState state) {
		System.out.println("AI returning canned move for game state - " + state);

		// Determine legal moves.

		heuristic(state);
		return moveList.next();
	}

	public int heuristic(GameState state) {
		int[][] board = state.getBoard();
		int myColor = state.getPlayer();
		List<int[]> legalMoves = new ArrayList<int[]>();
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalMove(board, i, j, myColor)) {
					int[] legal = new int[2];
					legal[0] = i;
					legal[1] = j;
					legalMoves.add(legal);              //.([i, j]);
				}
			}
		}
		for(int i = 0; i < 4; i++) {
			System.out.println(Arrays.toString(legalMoves.get(i)));
		}

		return 0;
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

	public boolean checkLegalVectors(int[][] board, int row, int col, int adjRow, int adjCol, int myColor){
		int rowVec = adjRow - row;
		int colVec = adjCol - col;
		for(int i = 2;  i<= 7; i++) {
			int j = row + (i*rowVec);
			int k = col + (i*colVec);
			if(j > 0 && 7 > j ) {
				if(k > 0 && 7 > k ) {
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
