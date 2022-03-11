package sample;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import main.Facade;
import main.Algorithm;

public class Controller implements Initializable {
    public String[] inputs = {"", "", ""};      //algorithm, colour, depth
    public String[] results = {"Your score: ", "Computer score: ", " "};
    private String[] algorithmsClass = {"Minimax", "Minimax with alpha-beta pruning"};
    public String[] colors = {"Red", "Yellow"};
    private String[][] gridStatus = new String[6][7];
    public Alert a = new Alert(Alert.AlertType.WARNING);
    private boolean playing = false;
    private int moves = 0;
    TranslateTransition translate = new TranslateTransition();
    TranslateTransition translate2 = new TranslateTransition();
    TranslateTransition translate3 = new TranslateTransition();
    TranslateTransition translate4 = new TranslateTransition();

    private RadialGradient Red = new RadialGradient(
        0,
        0,
        0.5,
        0.5,
        0.5,
        true,
        CycleMethod.REPEAT,
        new Stop(0, Color.web("0xd90202cc")),
        new Stop(1, Color.web("0xff0000ff"))
    );
    private RadialGradient Yellow = new RadialGradient(
            0,
            0,
            0.5,
            0.5,
            0.5,
            true,
            CycleMethod.REPEAT,
            new Stop(0, Color.web("0xbcb11cff")),
            new Stop(1, Color.web("0xffee00ff"))
    );
    private RadialGradient compColor;
    private RadialGradient playerColor;
    private Facade facade = new Facade();

    @FXML
    private ChoiceBox<String> algorithms;
    @FXML
    private ChoiceBox<String> color;
    @FXML
    private Button start;
    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane hiddenGrid;
    @FXML
    private ListView<String> resultBoard;
    @FXML
    private TextField k;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithms.getItems().addAll(algorithmsClass);
        resultBoard.getItems().addAll(results);
        algorithms.setOnAction(this::getAlgorithm);
        k.setOnKeyPressed(this::change);
        color.getItems().addAll(colors);
        color.setOnAction(this::getColor);
        start.setOnAction(this::start);
        gridPane.setOnMouseClicked(this::setColor);
    }

    public void change(KeyEvent keyEvent) {
        k.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    k.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (k.getText().length() > 2) {
                    String s = k.getText().substring(0, 2);
                    k.setText(s);
                }
            }
        });

    }
    public void setColor(MouseEvent mouseEvent) {
        if (playing && moves < 42){
            Node node = (Node) mouseEvent.getTarget();
            String id = node.getId();
            if (id.charAt(0) == 'c'){
                int col = intOf(id.charAt(1));
                if (gridStatus[0][col] == "White"){
                    getCorrectCircle(col, playerColor);
                }
            }
        }
    }
    private int intOf(char ch) {
        return Integer.parseInt(String.valueOf(ch));
    }
    private boolean getCorrectCircle (int col, RadialGradient color) {
        for (int i = 5; i >= 0; i--) {
            if(gridStatus[i][col] == "White"){
                Circle c = (Circle) getNodeFromGridPane(gridPane, col, i);
                if (c != null){
                    gridPane.setDisable(true);
                    StackPane s = (StackPane)(getNodeFromGridPane(hiddenGrid, col, 0));
                    Circle c2 = (Circle) s.getChildren().get(i);
                    double y = c.getLayoutY() - c2.getLayoutY();
                    c2.setFill(color);
                    c2.setVisible(true);
                    translate.setNode(c2);
                    translate.setDuration(Duration.millis(500));
                    translate.setCycleCount(1);
                    translate.setByY(y + 135);
                    translate.setAutoReverse(false);
                    int row = i;
                    translate.setOnFinished(finish -> {
                        c2.setVisible(false);
                        translate2.setNode(c2);
                        translate2.setByY(-y - 135);
                        translate2.setCycleCount(1);
                        translate2.setDuration(Duration.millis(500));
                        translate2.play();
                        c.setFill(color);
                        computerTurn(col);
                        gridPane.setDisable(false);
                    });
                    translate.play();
                    gridStatus[i][col] = inputs[1];
                    moves += 2;

                    return true;
                }
            }
        }
        return false;
    }
    private void computerTurn(int col){
        facade.dropPlayerPiece(col);
        int[] result = new int[2];
        result = facade.dropAiPiece();
        result[0] = 5 - result[0];
        Circle c = (Circle) getNodeFromGridPane(gridPane, result[1], result[0]);
        StackPane s = (StackPane) (getNodeFromGridPane(hiddenGrid, result[1], 0));
        Circle c2 = (Circle) s.getChildren().get(result[0]);
        double y = c.getLayoutY() - c2.getLayoutY();
        c2.setFill(compColor);
        c2.setVisible(true);
        translate3.setNode(c2);
        translate3.setDuration(Duration.millis(500));
        translate3.setCycleCount(1);
        translate3.setByY(y + 135);
        translate3.setAutoReverse(false);
        translate3.setOnFinished(finish -> {
            c2.setVisible(false);
            translate4.setNode(c2);
            translate4.setByY(-y - 135);
            translate4.setCycleCount(1);
            translate4.setDuration(Duration.millis(500));
            translate4.play();
            c.setFill(compColor);
            gridPane.setDisable(false);
        });
        translate3.play();
        if (inputs[1] == "Red"){
            gridStatus[result[0]][result[1]] = "Yellow";
        } else {
            gridStatus[result[0]][result[1]] = "Red";
        }
        facade.printTree();
        System.out.println();
        resultBoard.getItems().removeAll(results);
        int p = facade.getPlayerScore();
        int a = facade.getAiScore();
        results[0] = "Your score: " + p;
        results[1] = "Computer score: " + a;
        resultBoard.getItems().addAll(results[0], results[1]);
        if(moves == 42){
            start.setDisable(false);
            if(a == p)
                results[2] = "TIE";
            else if(p > a)
                results[2] = "YOU WON";
            else
                results[2] = "YOU LOST";
            resultBoard.getItems().addAll(results[2]);
            facade = new Facade();
        }
    }
    private void resetBoard() {
        moves = 0;
        playing = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gridStatus[i][j] = "White";
                ((Circle)getNodeFromGridPane(gridPane, j, i)).setFill(Color.WHITE);
            }
        }
        resultBoard.getItems().removeAll(results);
        results = new String[]{"Your score: ", "Computer score: ", " "};
        resultBoard.getItems().addAll(results);
        start.setDisable(true);
    }
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node != null && GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null){
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
        }
        return null;
    }
    public void start(ActionEvent actionEvent) {
        inputs[2] = k.getText();
        if (inputs[0] == "" || inputs[1] == "" || inputs[2] == "") {
            a.setContentText("Make sure that you selected the algorithm, tree depth and your color!!");
            a.show();
        } else {
            resetBoard();
            playing = true;

            if (inputs[1] == "Red"){
                playerColor = Red;
                compColor = Yellow;
            } else {
                playerColor = Yellow;
                compColor = Red;
            }
            if (inputs[0] == "Minimax")
                facade.setAlgorithm(Algorithm.MIN_MAX);
            else
                facade.setAlgorithm(Algorithm.ALPHA_BETA);
            facade.setK(inputs[2]);
        }
    }
    public void getColor(ActionEvent actionEvent) {
        inputs[1] = color.getValue();
    }
    public void getAlgorithm(ActionEvent event) {
        inputs[0] = algorithms.getValue();
    }
}
