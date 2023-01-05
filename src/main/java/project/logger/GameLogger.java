package project.logger;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/* provides some hooks into the game.  */
public class GameLogger {

    private final List<Consumer<Integer>> subscribers = new LinkedList<>();

    public void logMove(int playerId, int[] board) {}

    public void gameOver(int id) {
        subscribers.forEach(c -> c.accept(id));
    }

    public void subscribe(Consumer<Integer> c) {
        subscribers.add(c);
    }
}