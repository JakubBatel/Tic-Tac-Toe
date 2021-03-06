package sk.jb.tictactoe.game;

import sk.jb.tictactoe.game.exceptions.GameException;
import sk.jb.tictactoe.game.players.AbstractPlayer;
import sk.jb.tictactoe.game.players.PlayerType;
import sk.jb.tictactoe.game.renderers.Renderer;

/**
 * Class representing Tic-Tac-Toe game
 * @author Jakub Bateľ
 */
public class Game {

    private boolean inProgress;
    private Board board;
    private AbstractPlayer firstPlayer;
    private AbstractPlayer secondPlayer;
    private Renderer renderer;

    public Game(int size, Renderer renderer) {
        if (size < 3) {
            throw new IllegalArgumentException("Invalid size of the game");
        }
        board = new Board(new Symbol[size][size]);
        this.renderer = renderer;
    }

    public int getSize() {
        return board.getSIZE();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Board getCopyOfBoard() {
        return board.copy();
    }

    public Symbol at(int x, int y) {
        return board.at(x, y);
    }

    /**
     * Place given symbol on given position
     * @param symbol to place, can't be null
     * @param x index of row
     * @param y index of column
     * @throws GameException if place is already taken or game is not in progress
     */
    public void putAt(Symbol symbol, int x, int y) throws GameException {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol can not be null");
        }
        if (!inProgress) {
            throw new GameException("Game is not in progress");
        }
        if (at(x, y) != null) {
            throw new GameException("Place already taken");
        }
        board.putAt(symbol, x, y);
    }

    /**
     * Return player currently on the move
     * @return player on move
     */
    public AbstractPlayer getPlayerOnMove() {
        return firstPlayer;
    }

    /**
     * Swap active player and waiting player, called after {@link #nextMove()}
     */
    private void switchPlayers() {
        AbstractPlayer tmp = firstPlayer;
        firstPlayer = secondPlayer;
        secondPlayer = tmp;
    }

    /**
     * Start game wih given players
     * @param firstPlayer to play game, can't be null
     * @param secondPlayer to play game, can't be null
     */
    public void start(AbstractPlayer firstPlayer, AbstractPlayer secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new IllegalArgumentException("None of the players can be null");
        }
        firstPlayer.setGame(this);
        secondPlayer.setGame(this);
        this.firstPlayer = secondPlayer;
        this.secondPlayer = firstPlayer;
        inProgress = true;
        try {
            nextMove();
        } catch (GameException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Show message at the end of the game
     */
    private void showFinishMessage() {
        if (renderer == null) {
            return;
        }
        if (!board.isWon()) {
            renderer.showDrawMessage("It's a draw!");
        } else {
            if (firstPlayer.getType() == PlayerType.HUMAN) {
                renderer.showWinMessage(firstPlayer.getSymbol() + " is a winner!");
            } else {
                renderer.showLooseMessage("You lose! The winner is " + firstPlayer.getSymbol());
            }
        }
    }

    /**
     * Do next move, swap players and call {@link AbstractPlayer#doMove()}
     * @throws GameException if game is not in progress
     */
    public void nextMove() throws GameException {
        if (renderer != null) {
            renderer.render(this);
        }
        if (!inProgress) {
            throw new GameException("Can't do next move, game is not in progress");
        }
        if (board.isFinished()) {
            showFinishMessage();
            inProgress = false;
        } else {
            switchPlayers();
            firstPlayer.doMove();
        }
    }

}
