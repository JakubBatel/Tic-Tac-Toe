package sk.jb.tictactoe.game.players;

import sk.jb.tictactoe.game.exceptions.GameException;
import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;

/**
 * Representation of Human player
 * @author Jakub Bateľ
 */
public class HumanPlayer extends AbstractPlayer {

    private Integer nextX;
    private Integer nextY;

    public HumanPlayer(Symbol symbol) {
        this(null, symbol);
    }

    public HumanPlayer(Game game, Symbol symbol) {
        super(game, symbol, PlayerType.HUMAN);
    }

    @Override
    public void doMove() {
        if (nextX == null || nextY == null) {
            return;
        }
        try {
            game.putAt(getSymbol(), nextX, nextY);
            nextX = null;
            nextY = null;
            game.nextMove();
        } catch (GameException ex) {
            game.getRenderer().showErrorMessage(ex);
        }
    }

    /**
     * Method called by player controller, when player enters coordinates of next move
     * @param x index of row
     * @param y index of column
     */
    public void doMove(int x, int y) {
        nextX = x;
        nextY = y;
        doMove();
    }
}
