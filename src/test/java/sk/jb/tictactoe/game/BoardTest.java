package sk.jb.tictactoe.game;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(3);
    }

    @Test
    public void testOfCopy() {
        board.putAt(Symbol.CROSS, 1, 1);
        Board copy = board.copy();
        final int size = board.getSIZE();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals(board.at(i, j), copy.at(i, j));
            }
        }
        copy.putAt(Symbol.CIRCLE, 0, 0);
        assertNotEquals(board.at(0, 0), copy.at(0, 0));
    }

    @Test
    public void testAt() {
        final int size = board.getSIZE();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertNull(board.at(i, j));
            }
        }
    }

    @Test
    public void testPutAndAt() {
        board.putAt(Symbol.CROSS, 1, 1);
        assertEquals(board.at(1, 1), Symbol.CROSS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutAtNegative() {
        board.putAt(Symbol.CROSS, -1, -1);
    }

}
