package sk.jb.tictactoe.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private Symbol[][] board;
    private final int SIZE;

    public Board(Symbol[][] board, int size) {
        this.board = board;
        this.SIZE = size;
    }

    public int getSIZE() {
        return SIZE;
    }

    public Board copy() {
        return new Board(Arrays.stream(board).map(Symbol[]::clone).toArray(Symbol[][]::new), SIZE);
    }

    public Symbol at(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        return board[x][y];
    }

    public void putAt(Symbol symbol, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        board[x][y] = symbol;
    }

    public List<Symbol> getRow(int index) {
        return Arrays.asList(board[index]);
    }

    public List<Symbol> getColumn(int index) {
        return Arrays.stream(board).map(x -> x[index]).collect(Collectors.toList());
    }

    public List<Symbol> getFirstDiagonal() {
        return IntStream.range(0, SIZE).mapToObj(i -> board[i][i]).collect(Collectors.toList());
    }

    public List<Symbol> getSecondDiagonal() {
        return IntStream.range(0, SIZE).mapToObj(i -> board[i][SIZE - i - 1]).collect(Collectors.toList());
    }

    public static boolean isAllSameAndNotNull(List<Symbol> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) == null || list.get(i - 1) != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllSameOrNull(List<Symbol> list) {
        for (int i = 1; i < list.size(); i++) {
            Symbol first = list.get(i - 1);
            Symbol second = list.get(i);
            if (first != null && second != null && first != second) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVerticalAndHorizontal() {
        for (int i = 0; i < SIZE; i++) {
            if (isAllSameAndNotNull(getRow(i)) || isAllSameAndNotNull(getColumn(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return isAllSameAndNotNull(getFirstDiagonal()) || isAllSameAndNotNull(getSecondDiagonal());
    }

    public boolean isWon() {
        return checkVerticalAndHorizontal() || checkDiagonals();
    }

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