package sk.jb.tictactoe.game.players;

import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;
import sk.jb.tictactoe.game.exceptions.GameException;

import java.util.Random;

/**
 * Class representing Simple AI. Next move is chosen randomly.
 * @author Jakub Bateľ
 */
public class SimpleAI extends AbstractPlayer {

    public SimpleAI(Symbol symbol) {
        this(null, symbol);
    }

    public SimpleAI(Game game, Symbol symbol) {
        super(game, symbol, PlayerType.SIMPLE_AI);
    }

    @Override
    public void doMove() {
        int x;
        int y;
        Random random = new Random();
        final int GAME_SIZE = game.getSize();
        do {
            x = random.nextInt(GAME_SIZE);
            y = random.nextInt(GAME_SIZE);
        } while (game.at(x, y) != null);
        try {
            game.putAt(getSymbol(), x, y);
            game.nextMove();
        } catch (GameException ex) {
            game.getRenderer().showErrorMessage("AI stopped working:\n" + ex.getMessage());
        }
    }
}
