package sk.jb.tictactoe.game.renderers;

import sk.jb.tictactoe.game.Game;

public interface Renderer {

    void render(Game game);

    void showWinMessage(String msg);

    void showDrawMessage(String msg);

    void showLooseMessage(String msg);

    void showErrorMessage(String msg);

    void showErrorMessage(Exception ex);
}
