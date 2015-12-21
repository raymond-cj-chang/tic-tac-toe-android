package rchang.tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 * AI Class used to determine the best possible move. Uses the minimax algorithm.
 *
 * Created by raymondchang on 12/20/15.
 */

public class TicTacToeAI {

    protected String playerMark;
    protected String aiMark;
    protected GameBoard board;

    public TicTacToeAI(GameBoard board, String playerMark, String aiMark) {
        this.board = board;
        this.playerMark = playerMark;
        this.aiMark = aiMark;
    }

    public int[] getMove() {
        int[] result = minimax(2, playerMark, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {result[1], result[2]};
    }

    private int[] minimax(int depth, String player, int alpha, int beta) {

        List<int[]> possibleMoves = generateMoves();

        int score;
        int bestRow = -1;
        int bestCol = -1;

        if (possibleMoves.isEmpty() || depth == 0) {
            score = evaluate();
            return new int[] {score, bestRow, bestCol};
        }
        else {
            for (int[] move : possibleMoves) {
                board.makeMove(move[0], move[1], player);
                if (player.equals(playerMark)) {
                    score = minimax(depth - 1, aiMark, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                else {
                    score = minimax(depth - 1, playerMark, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }

                board.clearCell(move[0], move[1]);

                if (alpha >= beta) {
                    break;
                }
            }
        }

        return new int[] {(player.equals(playerMark)) ? alpha : beta, bestRow, bestCol};
    }

    /**
     * Returns a list {x, y} of all possible moves (empty spaces).
     *
     * @return an int[] containing all possible moves
     */

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>();

        if (!board.checkForWinner().equals(board.EMPTY_STRING)) {
            return nextMoves;
        }

        for (int row = 0; row < board.NUM_ROWS; row++) {
            for (int col = 0; col < board.NUM_COLUMNS; col++) {
                if (board.getCell(row, col).equals(board.EMPTY_STRING)) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }

        return nextMoves;
    }

    /**
     * Evaluates all the given moves.
     * @return the total score
     */

    private int evaluate() {
        int score = 0;

        score += evaluateLine(0, 0, 0, 1, 0, 2); //row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2); //row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2); //row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0); //col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1); //col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2); //col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2); //diagonal 1
        score += evaluateLine(0, 2, 1, 1, 2, 0); //diagonal 2

        return score;
    }

    /**
     * Evaluates the "score" of a given move given the line coordinates.
     *
     * (positive points for player, negative points for AI)
     * 100 points for 3 in a line
     * 10 points for 2 in a line
     * 1 point for 1 in a line
     *
     * @param row1 row of the first cell to check
     * @param col1 col of the first cell to check
     * @param row2 row of the second cell to check
     * @param col2 col of the second cell to check
     * @param row3 row of the third cell to check
     * @param col3 col of the third cell to check
     * @return the score of the given line.
     */

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        //evaluate first cell
        if (board.getCell(row1, col1).equals(playerMark)) {
            score = 1;
        }
        else if (board.getCell(row1, col1).equals(aiMark)) {
            score = -1;
        }

        //evaluate second cell
        if (board.getCell(row2, col2).equals(playerMark)) {
            if (score == 1) { //if player occupied first cell
                score = 10;
            }
            else if (score == -1) { //if AI occupied first cell
                return 0;
            }
            else { //first cell was empty
                score = 1;
            }
        }
        else if (board.getCell(row2, col2).equals(aiMark)) {
            if (score == -1) { //if AI occupied first cell
                score = -10;
            }
            else if (score == 1) { //if player occupied first cell
                return 0;
            }
            else { //first cell was empty
                score = -1;
            }
        }

        if (board.getCell(row3, col3).equals(playerMark)) {
            if (score > 0) { //player occupied first and/or second cells
                score *= 10;
            }
            else if (score < 0) { //ai occupied first and/or second cells
                return 0;
            }
            else { //first and second cells are empty
                score = 1;
            }
        }
        else if (board.getCell(row3, col3).equals(aiMark)) {
            if (score < 0) { //ai occupied first and/or second cells
                score *= 10;
            }
            else if (score > 1) { //player occupied first and/or second cells
                return 0;
            }
            else { //first and second cells are empty
                score = -1;
            }
        }

        return score;
    }
}