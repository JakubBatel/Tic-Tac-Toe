package sk.jb.tictactoe.game.players;

import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;

/**
 * @author Jakub Bateľ
 */
public abstract class AbstractPlayer {

    private final Symbol SYMBOL;
    private final PlayerType TYPE;
    protected Game game;

    public AbstractPlayer(Game game, Symbol symbol, PlayerType type) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol can't be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Player type can't be null");
        }
        this.SYMBOL = symbol;
        this.TYPE = type;
        this.game = game;
    }

    public Symbol getSymbol() {
        return SYMBOL;
    }

    public PlayerType getType() {
        return TYPE;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Method called by {@link Game#nextMove()} to do next move of the player
     */
    public abstract void doMove();
}
