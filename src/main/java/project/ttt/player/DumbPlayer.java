package project.ttt.player;

/* dumbest possible player -- chooses first valid move */
public class DumbPlayer implements Player {

    @Override
    public int chooseMove(int id, int[] board) {
        for (int i=0; i<9; i++) {
            if (board[i] == -1) return i;
        }
        throw new IllegalStateException();
    }
}