package project.ttt.player;

public interface Player {
    /**
     * @param id the number for this player's positions on the board
     * @param board a representation of the 3x3 tic tac toe board.  entries 0-2 are the first row, and so on
     * @return the index of the position chosen by this player
     */
    int chooseMove(int id, int[] board);
}