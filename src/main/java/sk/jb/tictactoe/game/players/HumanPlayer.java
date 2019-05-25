package sk.jb.tictactoe.game.players;

import sk.jb.tictactoe.game.exceptions.GameException;
import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;

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

    public void doMove(int x, int y) {
        nextX = x;
        nextY = y;
        doMove();
    }
}
