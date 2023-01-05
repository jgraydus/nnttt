package project.app;

import project.logger.GameLogger;
import project.ttt.TicTacToe;
import project.ttt.player.LearningPlayer;
import project.ttt.player.Player;
import project.ttt.player.RandomPlayer;

import java.util.Random;

public class Main {

    public static void main(String... args) {
        final Random rnd = new Random();
        final int trials = 100000;

        Runnable game1 = () -> {
            new TttGame(trials, new RandomPlayer(rnd), new RandomPlayer(rnd), new GameLogger()).run();
        };

        Runnable game2 = () -> {
            final GameLogger logger = new GameLogger();
            final Player p1 = new LearningPlayer(rnd, logger);
            final Player p2 = new RandomPlayer(rnd);
            new TttGame(trials, p1, p2, logger).run();
        };

        Runnable game3 = () -> {
            final GameLogger logger = new GameLogger();
            final Player p1 = new RandomPlayer(rnd);
            final Player p2 = new LearningPlayer(rnd, logger);
            new TttGame(trials, p1, p2, logger).run();
        };

        game1.run();
        game2.run();
        game3.run();
    }

    private static class TttGame implements Runnable {
        private final int trials;
        private final Player p1;
        private final Player p2;
        private final GameLogger logger;

        public TttGame(int trials, Player p1, Player p2, GameLogger logger) {
            this.trials = trials;
            this.p1 = p1;
            this.p2 = p2;
            this.logger = logger;
        }

        @Override
        public void run() {
            final int[] results = new int[3];
            logger.subscribe((i) -> results[i+1]++);

            for (int n=0; n<trials; n++) { new TicTacToe(p1, p2, logger).run(); }

            System.out.println(p1.getClass().getSimpleName()+ " (player 1) vs " +
                    p2.getClass().getSimpleName() + " (player 2)");
            System.out.println("player 1:    " + results[1]);
            System.out.println("player 2:    " + results[2]);
            System.out.println("draws:       " + results[0]);
            System.out.println("total games: " + trials);
        }
    }
}