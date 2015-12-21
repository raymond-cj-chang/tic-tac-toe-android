package rchang.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {

    public final int NUM_ROWS = 3;
    public final int NUM_COLUMNS = 3;

    private String playerMark = "O", aiMark = "X";
    private final String EMPTY = " ";
    private GameBoard board = null;
    private Button[][] buttons = new Button[NUM_ROWS][NUM_COLUMNS];
    private TextView textView;
    private TicTacToeAI ai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        board = new GameBoard();
        ai = new TicTacToeAI(board, playerMark, aiMark);
        init();
    }

    /**
     * Disables all the buttons on the board. Invoked after the game is over.
     */
    private void disableAllButtons() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Helper method to reset the game board.
     */

    public void resetGame(View view) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                buttons[i][j].setText(EMPTY);
                buttons[i][j].setEnabled(true);
                board.clearCell(i, j);
            }
        }
        textView.setText(R.string.start_message);
    }

    private void init() {
        textView = (TextView) findViewById(R.id.gameText);

        buttons[0][0] = (Button) findViewById(R.id.one);
        buttons[0][1] = (Button) findViewById(R.id.two);
        buttons[0][2] = (Button) findViewById(R.id.three);
        buttons[1][0] = (Button) findViewById(R.id.four);
        buttons[1][1] = (Button) findViewById(R.id.five);
        buttons[1][2] = (Button) findViewById(R.id.six);
        buttons[2][0] = (Button) findViewById(R.id.seven);
        buttons[2][1] = (Button) findViewById(R.id.eight);
        buttons[2][2] = (Button) findViewById(R.id.nine);

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                buttons[i][j].setOnClickListener(new TicTacToeButtonListener(i, j));
                if (!buttons[i][j].isEnabled()) {
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setText(EMPTY);
                }
            }
        }
    }

    class TicTacToeButtonListener implements View.OnClickListener {
        int x;
        int y;

        public TicTacToeButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // handle the click event
        public void onClick(View view) {
            if (view instanceof Button) {
                Button button = (Button) view;
                makeMove(button, playerMark, x, y);

                int[] aiMove = ai.getMove();

                //no possible moves
                if (aiMove[0] == -1 || aiMove[1] == -1) {
                    disableAllButtons();
                    textView.setText(R.string.tie_game);
                }
                else {
                    Button aiButton = buttons[aiMove[0]][aiMove[1]];
                    makeMove(aiButton, aiMark, aiMove[0], aiMove[1]);
                }

                if (board.checkForWinner().equals(playerMark)) {
                    disableAllButtons();
                    textView.setText(R.string.player_wins);
                }
                else if (board.checkForWinner().equals(aiMark)) {
                    disableAllButtons();
                    textView.setText(R.string.computer_wins);
                }
            }
        }

        private void makeMove(Button button, String mark, int x, int y) {
            button.setText(mark);
            button.setEnabled(false);
            board.makeMove(x, y, mark);
        }
    }
}
