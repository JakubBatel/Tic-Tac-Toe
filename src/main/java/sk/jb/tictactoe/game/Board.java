package sk.jb.tictactoe.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing board of Tic-Tac-Toe game
 * @author Jakub Bateľ
 */
public class Board {

    private Symbol[][] board;
    private final int SIZE;

    public Board(int size) {
        this(new Symbol[size][size]);
    }

    public Board(Symbol[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("Board can't be null");
        }
        if (board.length != board[0].length) {
            throw new IllegalArgumentException("Board is not square");
        }
        this.board = board;
        this.SIZE = board.length;
    }

    public int getSIZE() {
        return SIZE;
    }

    /**
     * Make a copy of this object
     * @return new object
     */
    public Board copy() {
        return new Board(Arrays.stream(board).map(Symbol[]::clone).toArray(Symbol[][]::new));
    }

    /**
     * Return {@link Symbol} at given position
     * @param x index of row
     * @param y index of column
     * @return {@link Symbol} at given position
     */
    public Symbol at(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        return board[x][y];
    }

    /**
     * Place given symbol at given place
     * @param symbol to place
     * @param x index of row
     * @param y index of column
     */
    public void putAt(Symbol symbol, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        board[x][y] = symbol;
    }

    /**
     * Return List representation of row
     * @param index of row
     * @return list of Symbols of row with given index
     */
    public List<Symbol> getRow(int index) {
        return Arrays.asList(board[index]);
    }

    /**
     * Return List representation of column
     * @param index of column
     * @return list of Symbols of column with given index
     */
    public List<Symbol> getColumn(int index) {
        return Arrays.stream(board).map(x -> x[index]).collect(Collectors.toList());
    }

    /***
     * Return list representation of first diagonal
     * @return list of Symbols from first diagonal
     */
    public List<Symbol> getFirstDiagonal() {
        return IntStream.range(0, SIZE).mapToObj(i -> board[i][i]).collect(Collectors.toList());
    }

    /***
     * Return list representation of second diagonal
     * @return list of Symbols from second diagonal
     */
    public List<Symbol> getSecondDiagonal() {
        return IntStream.range(0, SIZE).mapToObj(i -> board[i][SIZE - i - 1]).collect(Collectors.toList());
    }

    /**
     * Check if given list contains same elements except null
     * @param list to check
     * @return true if contains same elements only
     */
    public static boolean isAllSameAndNotNull(List<Symbol> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) == null || list.get(i - 1) != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check all vertical and horizontal win combos
     * @return true if any win combo is found
     */
    private boolean checkVerticalAndHorizontal() {
        for (int i = 0; i < SIZE; i++) {
            if (isAllSameAndNotNull(getRow(i)) || isAllSameAndNotNull(getColumn(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check diagonals for possible win combo
     * @return true if on any diagonal is win combo
     */
    private boolean checkDiagonals() {
        return isAllSameAndNotNull(getFirstDiagonal()) || isAllSameAndNotNull(getSecondDiagonal());
    }

    /**
     * Check if game is won by somebody
     * @return true if game is won
     */
    public boolean isWon() {
        return checkVerticalAndHorizontal() || checkDiagonals();
    }

    /**
     * Check if all positions on board are filled
     * @return true if no more moves left
     */
    private boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if game is finished already
     * @return true if game is finished
     */
    public boolean isFinished() {
        return isFull() || isWon();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                builder.append(board[i][j]);
                builder.append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
