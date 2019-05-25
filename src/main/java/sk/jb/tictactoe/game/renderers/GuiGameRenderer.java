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

    public static int getTileSize(int size) {
        return TILE_SIZE * size + GAP_SIZE * (size + 1);
    }

    private static int translate(double origin) {
        int i = 0;
        int HALF_GAP_SIZE = GAP_SIZE / 2;
        while (origin > getTileSize(i + 1) - HALF_GAP_SIZE) {
            i++;
        }
        return i;
    }

    public static int translateX(double x) {
        return translate(x);
    }

    public static int translateY(double y) {
        return translate(y);
    }

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

    private void clearScreen() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
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

    private void showMessage(Alert.AlertType type, String msg, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
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
