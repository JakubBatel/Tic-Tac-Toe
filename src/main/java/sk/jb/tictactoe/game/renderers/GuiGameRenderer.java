package sk.jb.tictactoe.game.renderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.Symbol;
import sk.jb.tictactoe.gui.TicTacToe;

import java.io.IOException;

public class GuiGameRenderer implements Renderer {

    private static final int TILE_SIZE = 150;
    private static final int GAP_SIZE = 15;

    private static final Image X_SYMBOL = new Image("sk/jb/tictactoe/gui/x.png", TILE_SIZE, TILE_SIZE, false, true);
    private static final Image O_SYMBOL = new Image("sk/jb/tictactoe/gui/o.png", TILE_SIZE, TILE_SIZE, false, true);

    private GraphicsContext gc;

    public GuiGameRenderer(GraphicsContext graphicsContext) {
        gc = graphicsContext;
    }

    /**
     * Count offset for tile
     * @param size number of tile to offset
     * @return offset
     */
    public static int getTileSize(int size) {
        return TILE_SIZE * size + GAP_SIZE * (size + 1);
    }

    /**
     * Map pixels to game indexes
     * @param origin pixel value
     * @return game index
     */
    public static int translate(double origin) {
        int i = 0;
        int HALF_GAP_SIZE = GAP_SIZE / 2;
        while (origin > getTileSize(i + 1) - HALF_GAP_SIZE) {
            i++;
        }
        return i;
    }

    /**
     * Draw the game grid
     * @param size of the game
     */
    private void drawLines(int size) {
        int fullSize = getTileSize(size);
        gc.setLineWidth(5);
        for (int i = 1; i < size; i++) {
            int pos = getTileSize(i) - GAP_SIZE / 2;
            // Horizontal lines
            gc.strokeLine(GAP_SIZE, pos, fullSize - GAP_SIZE, pos);
            // Vertical lines
            gc.strokeLine(pos, GAP_SIZE, pos, fullSize - GAP_SIZE);
        }
    }

    /**
     * Clear screen, called by {@link #render(Game)} before actual rendering
     */
    private void clearScreen() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /**
     * Display message of given type, if message is information of end then set ok button to switch to menu
     * @param type of the message
     * @param msg text of message
     * @param title of window
     */
    private void showMessage(Alert.AlertType type, String msg, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        if (type == Alert.AlertType.ERROR) {
            alert.showAndWait();
        } else {
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        TicTacToe.setRoot("menu");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void render(Game game) {
        int size = game.getSize();
        clearScreen();
        drawLines(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Symbol symbol = game.at(i, j);
                if (symbol != null) {
                    gc.drawImage((symbol == Symbol.CROSS) ? X_SYMBOL : O_SYMBOL, getTileSize(i), getTileSize(j));
                }
            }
        }
    }

    @Override
    public void showWinMessage(String msg) {
        showMessage(Alert.AlertType.INFORMATION, msg, "CONGRATULATION!");
    }

    @Override
    public void showDrawMessage(String msg) {
        showMessage(Alert.AlertType.INFORMATION, msg, "DRAW!");
    }

    @Override
    public void showLooseMessage(String msg) {
        showMessage(Alert.AlertType.INFORMATION, msg, "LOSE!");
    }

    @Override
    public void showErrorMessage(String msg) {
        showMessage(Alert.AlertType.ERROR, msg, "ERROR!");
    }

    @Override
    public void showErrorMessage(Exception ex) {
        showErrorMessage(ex.getMessage());
    }

}
