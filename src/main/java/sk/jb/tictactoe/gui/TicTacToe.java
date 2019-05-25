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

public class TicTacToe extends Application {

    private static Scene scene;
    private static Game game;

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static void resizeWindowToFit(double width, double height) {
        Window window = scene.getWindow();
        final double decorationHeight = window.getHeight() - scene.getHeight();
        final double decorationWidth = window.getWidth() - scene.getWidth();
        window.setHeight(height + decorationHeight);
        window.setWidth(width + decorationWidth);
        window.centerOnScreen();
    }

    public static void switchToGame(int size, AbstractPlayer first, AbstractPlayer second) {
        VBox root = new VBox();
        Canvas canvas = new Canvas(getTileSize(size), getTileSize(size));
        canvas.setOnMouseClicked((event) -> {
            AbstractPlayer player = game.getPlayerOnMove();
            if (player != null && player.getType() == PlayerType.HUMAN) {
                HumanPlayer hp = (HumanPlayer) player;
                hp.doMove(translateX(event.getX()), translateY(event.getY()));
            }
        });
        root.getChildren().add(canvas);
        resizeWindowToFit(canvas.getWidth(), canvas.getHeight());
        Renderer renderer = new GuiGameRenderer(canvas.getGraphicsContext2D());
        game = new Game(size, renderer);
        game.start(first, second);
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void exit() {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
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