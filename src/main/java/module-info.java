module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens sk.jb.tictactoe.gui to javafx.fxml;
    exports sk.jb.tictactoe.gui;
}