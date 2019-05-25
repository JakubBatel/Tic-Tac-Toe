package sk.jb.tictactoe.game;

import org.junit.Before;
import org.junit.Test;
import sk.jb.tictactoe.game.exceptions.GameException;
import sk.jb.tictactoe.game.players.AbstractPlayer;
import sk.jb.tictactoe.game.players.PlayerType;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    private class TestPlayer extends AbstractPlayer {

        private boolean moveDone = false;

        public TestPlayer(Game game, Symbol symbol) {
            super(game, symbol, PlayerType.HUMAN);
        }

        @Override
        public void doMove() {
            moveDone = true;
        }
    }

    @Before
    public void setUp() {
        game = new Game(3, null);
    }

    @Test
    public void testAtOnEmptyGame() {
        int size = game.getSize();
        assertEquals(3, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertNull(game.at(i, j));
            }
        }
    }

    @Test(expected = GameException.class)
    public void putBeforeGameStarted() throws GameException {
        assertNull(game.at(0, 0));
        game.putAt(Symbol.CIRCLE, 0, 0);
    }

    @Test
    public void testPutAndAtGame() throws GameException {
        int size = game.getSize();
        assertEquals(3, size);
        game.start(new TestPlayer(game, Symbol.CROSS), new TestPlayer(game, Symbol.CIRCLE));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                game.putAt(Symbol.CROSS, i, j);
                assertEquals(Symbol.CROSS, game.at(i, j));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutAtNegative() throws GameException {
        game.start(new TestPlayer(game, Symbol.CROSS), new TestPlayer(game, Symbol.CIRCLE));
        game.putAt(Symbol.CROSS, -1, -1);
    }

    @Test(expected = GameException.class)
    public void testPutAtTakenPlace() throws GameException {
        game.start(new TestPlayer(game, Symbol.CROSS), new TestPlayer(game, Symbol.CIRCLE));
        assertNull(game.at(1, 1));
        game.putAt(Symbol.CROSS, 1, 1);
        assertEquals(Symbol.CROSS, game.at(1, 1));
        game.putAt(Symbol.CROSS, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNull() throws GameException {
        game.start(new TestPlayer(game, Symbol.CROSS), new TestPlayer(game, Symbol.CIRCLE));
        game.putAt(null, 1, 1);
    }

    @Test
    public void testSwitchingPlayerAfterMove() throws GameException {
        TestPlayer first = new TestPlayer(game, Symbol.CROSS);
        TestPlayer second = new TestPlayer(game, Symbol.CIRCLE);
        game.start(first, second);
        assertTrue(first.moveDone);
        assertFalse(second.moveDone);
        game.nextMove();
        assertTrue(first.moveDone);
        assertTrue(second.moveDone);
    }

}