package project.logger;

/* prints the game move by move to stdout.  useful for making sure things are working correctly */
public class StdOutGameLogger extends GameLogger {

    private final String[] symbols = new String[]{" ","X","O"};
    private final String template = "%s|%s|%s\n-----\n%s|%s|%s\n-----\n%s|%s|%s";

    @Override
    public void logMove(int playerId, int[] board) {
        System.out.println(drawBoard(board));
        System.out.println();
    }

    @Override
    public void gameOver(int winningPlayerId) {
        super.gameOver(winningPlayerId);
        if (winningPlayerId >= 0) {
            System.out.println("GAME OVER -- Player " + winningPlayerId + " has won");
        } else {
            System.out.println("THE GAME IS A DRAW");
        }
    }

    private String drawBoard(int[] board) {
        String[] s = new String[9];
        for (int i=0; i<9; i++) s[i] = symbols[board[i]+1];
        return String.format(template, s);
    }
}