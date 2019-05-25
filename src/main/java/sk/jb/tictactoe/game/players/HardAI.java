package sk.jb.tictactoe.game.players;

import sk.jb.tictactoe.game.Board;
import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;
import sk.jb.tictactoe.game.exceptions.GameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.pow;

/**
 * Class representing Hard AI. It is implementation of MiniMax algorithm.
 * @author Jakub Bateľ
 */
public class HardAI extends AbstractPlayer {

    private class Position {
        private int x;
        private int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x: " + x + " y: " + y;
        }
    }

    public HardAI(Symbol symbol) {
        this(null, symbol);
    }

    public HardAI(Game game, Symbol symbol) {
        super(game, symbol, PlayerType.HARD_AI);
    }

    /**
     * Evaluate list of symbols (e.g. one row, one col, ...)
     * @param list representation of row, col, etc.
     * @param prefer symbol of player which is maximised
     * @return integer value representing how good is this list according to preferred player
     */
    private int eval(List<Symbol> list, Symbol prefer) {
        int good = Collections.frequency(list, prefer);
        int bad = Collections.frequency(list, otherSymbol(prefer));
        if (good != 0 && bad == 0) {
            return (int) pow(2, good);
        }
        if (good == 0 && bad != 0) {
            return (int) -pow(2, bad);
        }
        return 0;
    }

    /**
     * Evaluate all rows and columns of given board
     * @param board representing state of game
     * @param symbol of preferred player
     * @return integer value representing partial score of how good this state is
     */
    private int evaluateHorizontalAndVertical(Board board, Symbol symbol) {
        final int size = game.getSize();
        int score = 0;
        for (int i = 0; i < size; i++) {
            score += eval(board.getRow(i), symbol) + eval(board.getColumn(i), symbol);
        }
        return score;
    }

    /**
     * Evaluate given state of game
     * @param board representing state of game
     * @param symbol of preferred player
     * @return integer value representing score of how good this state of game is
     */
    private int evaluate(Board board, Symbol symbol) {
        return evaluateHorizontalAndVertical(board, symbol) + eval(board.getFirstDiagonal(), symbol) +
                eval(board.getSecondDiagonal(), symbol);
    }

    /**
     * Find all empty places on given board
     * @param board representing state of game
     * @return list of empty positions
     */
    private List<Position> findAllEmptyPositions(Board board) {
        List<Position> list = new ArrayList<>();
        for (int i = 0; i < board.getSIZE(); i++) {
            for (int j = 0; j < board.getSIZE(); j++) {
                if (board.at(i, j) == null) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    /**
     * Find opposite symbol for given symbol
     * @param symbol to find opposite of
     * @return opposite symbol e.g. O -> X and vice versa
     */
    private Symbol otherSymbol(Symbol symbol) {
        return (symbol == Symbol.CIRCLE) ? Symbol.CROSS : Symbol.CIRCLE;
    }

    /**
     * MiniMax algorithm evaluating states of game
     * @param board representing state of game
     * @param depth max recursion depth
     * @param alpha value of MiniMax
     * @param beta value of MinimMax
     * @param symbol of preferred player
     * @return integer representing how good is this move
     */
    private int minimax(Board board, int depth, int alpha, int beta, Symbol symbol) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board, getSymbol());
        }
        List<Position> positions = findAllEmptyPositions(board);
        int bestEval = (getSymbol() == symbol) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Position pos : positions) {
            board.putAt(symbol, pos.x, pos.y);
            int eval = minimax(board, depth - 1, alpha, beta, otherSymbol(symbol));
            if (getSymbol() == symbol) {
                bestEval = max(alpha, eval);
                alpha = max(alpha, eval);
            } else {
                bestEval = min(beta, eval);
                beta = min(beta, eval);
            }
            board.putAt(null, pos.x, pos.y);
            if (beta <= alpha) {
                break;
            }
        }
        return bestEval;
    }

    /**
     * MiniMax algorithm for all possible moves for given board
     * @param board representing game state
     * @param depth max recursion depth
     * @return best position according to MiniMax
     */
    private Position minimax(Board board, int depth) {
        List<Position> positions = findAllEmptyPositions(board);
        TreeMap<Integer, Position> map = new TreeMap<>();
        for (Position pos : positions) {
            board.putAt(getSymbol(), pos.x, pos.y);
            int mm = minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, otherSymbol(getSymbol()));
            map.put(mm, pos);
            board.putAt(null, pos.x, pos.y);
        }
        return map.get(map.lastKey());
    }

    @Override
    public void doMove() {
        Board board = game.getCopyOfBoard();
        Position pos = minimax(board, 6);
        try {
            game.putAt(getSymbol(), pos.x, pos.y);
            game.nextMove();
        } catch (GameException ex) {
            game.getRenderer().showErrorMessage("AI stopped working:\n" + ex.getMessage());
        }
    }
}
