package rchang.tictactoe;

import java.util.HashSet;

/**
 * Created by raymondchang on 12/19/15.
 */
public class GameBoard {

    public final int NUM_ROWS = 3;
    public final int NUM_COLUMNS = 3;
    public final String EMPTY_STRING = " ";

    String [][] board = new String[NUM_ROWS][NUM_COLUMNS];

    public GameBoard() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                board[i][j] = EMPTY_STRING;
            }
        }
    }

    /**
     * Helper method that checks the board to see if there's a winner.
     * @return true is board has a winner; false otherwise
     */
    public String checkForWinner() {
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])
                && !board[0][0].equals(EMPTY_STRING)) {
            return board[0][0];
        }
        else if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])
                && !board[0][2].equals(EMPTY_STRING)) {
            return board[0][2];
        }
        else {
            for (int i = 0; i < NUM_ROWS; i++) {
                if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])
                        && !board[i][0].equals(EMPTY_STRING)) {
                    return board[i][0];
                }
                if (board[0][i].equals( board[1][i]) && board[0][i].equals(board[2][i])
                        && !board[0][i].equals(EMPTY_STRING)) {
                    return board[0][i];
                }
            }
        }

        return EMPTY_STRING;
    }

    public void makeMove(int x, int y, String marker) {
        if (board[x][y].equals(EMPTY_STRING)) {
            board[x][y] = marker;
        }
    }

    public String[][] getBoard() {
        return this.board;
    }

    public void clear() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                board[i][j] = EMPTY_STRING;
            }
        }
    }

    public void clearCell(int x, int y) {
        board[x][y] = EMPTY_STRING;
    }

    public String getCell(int x, int y) {
        return this.board[x][y];
    }
}
