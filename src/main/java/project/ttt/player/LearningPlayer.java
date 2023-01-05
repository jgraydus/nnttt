package project.ttt.player;

import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import project.logger.GameLogger;
import project.nn.Layer;
import project.nn.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* utilized a neural network to learn how to play better */
public class LearningPlayer implements Player {

    private int myId;
    private final Network nn;
    private List<Move> moves = new ArrayList<>(9);

    public LearningPlayer(Random random, GameLogger gameLogger) {
        gameLogger.subscribe(this::gameHasEnded); //gameLogger will notify when a game has ended
        nn = new TttNetwork(random).get();
    }

    // this method is invoked at the end of each game
    private void gameHasEnded(int id) {
        // if the game was a win or a draw, train the network on the moves that were made
        if (id == myId || id == -1) { train(); }
        // then clear the list of moves to prepare for the next game
        moves.clear();
    }

    // convert array to Vector
    private Vector input(int[] b) {
        final double[] v = new double[9];
        for (int i=0; i<9; i++) { v[i] = (double) b[i]; }
        return new DenseVector(v);
    }

    // convert integer representing a board position choice into a Vector
    private Vector desired(int i) {
        final double[] v = new double[9];
        v[i] = 1.0;
        return new DenseVector(v);
    }

    /* train the network. this does batch training -- all training pairs are propagated and then the weights are adjusted
     * we might get better results with online training */
    private void train() { moves.stream().flatMap(m -> train(m).stream()).forEach(Runnable::run); }
    private List<Runnable> train(Move m) { return nn.propagate(input(m.board), desired(m.m)); }

    /* feed the current board state into the neural network and extract a choice of move */
    private int askNn(int[] board) {
        final Vector result = nn.evaluate(input(board));
        // choose the largest value which represents a valid move
        int idx = -1;
        double max = 0.0;
        for (int i=0; i<9; i++) {
            if (board[i] == -1 && result.get(i) > max) {
                idx = i;
                max = result.get(i);
            }
        }
        return idx;
    }

    @Override
    public int chooseMove(int id, int[] board) {
        myId = id;
        int nnChoice = askNn(board);
        moves.add(new Move(nnChoice, board));
        return nnChoice;
    }

    private class Move {
        private final int m;
        private final int[] board;

        Move(int m, int[] board) {
            this.m = m;
            this.board = board.clone();
        }
    }

    private static class TttNetwork {
        private final Random random;

        public TttNetwork(Random random) {
            this.random = random;
        }

        private double eta = 0.1;
        private double bias = 1.0;

        public Network get() {
            final Layer output = new Layer("output", bias, random(12,9), null);
            //final Layer l3 = new Layer("3", bias, random(12,12), output);
            final Layer l2 = new Layer("2", bias, random(12,12), output);
            final Layer l1 = new Layer("1", bias, random(9,12), l2);
            return new Network(eta, l1);
        }

        private Matrix random(int n, int m) {
            double[][] r = new double[m][n];
            for (int i=0; i<m; i++)
                for (int j=0; j<n; j++)
                    r[i][j] = random.nextDouble();
            return new DenseMatrix(r);
        }
    }
}