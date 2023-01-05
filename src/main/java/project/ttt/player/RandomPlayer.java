package project.ttt.player;

import java.util.ArrayList;
import java.util.Random;

/* chooses moves at random */
public class RandomPlayer implements Player {

    private final Random random;

    public RandomPlayer(Random random) {
        this.random = random;
    }

    @Override
    public int chooseMove(int id, int[] board) {
        ArrayList<Integer> available = new ArrayList<>(9);
        for (int i=0; i<9; i++) {
            if (board[i] == -1) available.add(i);
        }
        int r = random.nextInt(available.size());
        return available.get(r);
    }
}