package sk.jb.tictactoe.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import sk.jb.tictactoe.game.players.*;
import sk.jb.tictactoe.game.Symbol;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jakub Bateľ
 */
public class MenuController implements Initializable {

    @FXML
    private Slider gameSize;

    @FXML
    private ComboBox firstPlayerType;

    @FXML
    private ComboBox secondPlayerType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstPlayerType.getItems().addAll(PlayerType.values());
        secondPlayerType.getItems().addAll(PlayerType.values());
        firstPlayerType.setValue(PlayerType.HUMAN);
        secondPlayerType.setValue(PlayerType.SIMPLE_AI);
    }

    private int getGameSize() {
        return (int) Math.round(gameSize.getValue());
    }

    /**
     * Build player of given type
     * @param type of the player
     * @param symbol which player will use
     * @return new player
     */
    private AbstractPlayer createPlayer(PlayerType type, Symbol symbol) {
        switch (type) {
            case HUMAN:
                return new HumanPlayer(symbol);
            case SIMPLE_AI:
                return new SimpleAI(symbol);
            case HARD_AI:
                return new HardAI(symbol);
        }
        return null;
    }

    @FXML
    private void switchToGame() {
        PlayerType fpType = (PlayerType) firstPlayerType.getValue();
        PlayerType spType = (PlayerType) secondPlayerType.getValue();
        TicTacToe.switchToGame(getGameSize(), createPlayer(fpType, Symbol.CROSS), createPlayer(spType, Symbol.CIRCLE));
    }

    @FXML
    private void exit() {
        TicTacToe.exit();
    }
}
