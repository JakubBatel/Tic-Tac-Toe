package sk.jb.tictactoe.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import sk.jb.tictactoe.game.Game;
import sk.jb.tictactoe.game.players.AbstractPlayer;
import sk.jb.tictactoe.game.players.HumanPlayer;
import sk.jb.tictactoe.game.players.PlayerType;
import sk.jb.tictactoe.game.renderers.GuiGameRenderer;
import sk.jb.tictactoe.game.renderers.Renderer;

import java.io.IOException;

import static sk.jb.tictactoe.game.renderers.GuiGameRenderer.*;

/**
 * @author Jakub Bateľ
 */
public class TicTacToe extends Application {

    private static Scene scene;
    private static Game game;

    /**
     * Set root of scene to Parent loaded from fxml
     * @param fxml name of file to load
     * @throws IOException when failed to read fxml file
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Resize window to given width and height
     * @param width of window
     * @param height of window
     */
    private static void resizeWindowToFit(double width, double height) {
        Window window = scene.getWindow();
        final double decorationHeight = window.getHeight() - scene.getHeight();
        final double decorationWidth = window.getWidth() - scene.getWidth();
        window.setHeight(height + decorationHeight);
        window.setWidth(width + decorationWidth);
        window.centerOnScreen();
    }

    /**
     * Load parent from fxml file
     * @param fxml name of the file
     * @return parent build from fxml file
     * @throws IOException when failed to read fxml file
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Close the window
     */
    public static void exit() {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    /**
     * Initialize game screen and set it as root
     * @param size of game (e.g. traditional 3x3 game, ...)
     * @param first player of the game
     * @param second player of the game
     */
    public static void switchToGame(int size, AbstractPlayer first, AbstractPlayer second) {
        VBox root = new VBox();
        Canvas canvas = new Canvas(getTileSize(size), getTileSize(size));
        canvas.setOnMouseClicked((event) -> {
            AbstractPlayer player = game.getPlayerOnMove();
            if (player != null && player.getType() == PlayerType.HUMAN) {
                HumanPlayer hp = (HumanPlayer) player;
                hp.doMove(translate(event.getX()), translate(event.getY()));
            }
        });
        root.getChildren().add(canvas);
        resizeWindowToFit(canvas.getWidth(), canvas.getHeight());
        Renderer renderer = new GuiGameRenderer(canvas.getGraphicsContext2D());
        game = new Game(size, renderer);
        game.start(first, second);
        scene.setRoot(root);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("menu.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}