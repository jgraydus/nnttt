package project.ttt;

import project.logger.GameLogger;
import project.ttt.player.Player;

import java.util.Arrays;

/* implementation of the game */
public class TicTacToe implements Runnable {

    private final Player[] players;
    private final GameLogger gameLogger;
    private final int[] board = new int[9];
    private int moves = 0;

    public TicTacToe(Player p1,
                     Player p2,
                     GameLogger gameLogger) {
        players = new Player[]{p1, p2};
        this.gameLogger = gameLogger;
        Arrays.fill(board, -1); // -1 indicates an empty board position
    }

    private boolean isGameWon() {
        int p = board[0];
        if (check(p,1,2) || check(p,3,6)) {
            return true;
        }
        p = board[8];
        if (check(p,6,7) || check(p,2,5)) {
            return true;
        }
        p = board[4];
        if (check(p,0,8) || check(p,2,6) || check(p,1,7) || check(p,3,5)) {
            return true;
        }
        return false;
    }

    private boolean check(int p, int a, int b) {
        return p != -1 && p == board[a] && p == board[b];
    }

    private void validate(int playerId, int m) {
        if (board[m] != -1) throw new IllegalStateException("Player " + playerId + " has made an illegal move");
    }

    @Override
    public void run() {
        int p = 0;
        while (true) {
            int m = players[p].chooseMove(p, board);
            //validate(p, m);
            board[m] = p;
            moves++;
            gameLogger.logMove(p, board);
            if (isGameWon()) {
                gameLogger.gameOver(p);
                break;
            }
            if (moves == 9) {
                gameLogger.gameOver(-1);  // -1 indicates a draw
                break;
            }
            p = (p + 1) % 2;
        }
    }
}