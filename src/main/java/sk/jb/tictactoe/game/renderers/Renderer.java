package sk.jb.tictactoe.game.renderers;

import sk.jb.tictactoe.game.Game;

/**
 * Interface of renderer for Tic Tac Toe game
 * @author Jakub Bateľ
 */
public interface Renderer {

    /**
     * Render game state
     * @param game state of game
     */
    void render(Game game);

    /**
     * Display win message
     * @param msg text of message
     */
    void showWinMessage(String msg);

    /**
     * Display draw message
     * @param msg text of message
     */
    void showDrawMessage(String msg);

    /**
     * Display loose message
     * @param msg text of message
     */
    void showLooseMessage(String msg);

    /**
     * Display error message
     * @param msg text of message
     */
    void showErrorMessage(String msg);

    /**
     * Display error
     * @param ex exception to display
     */
    void showErrorMessage(Exception ex);
}
