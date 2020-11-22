package gui;

import app.Board;
import app.JsonParser;
import app.Purenrupu;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    private Drawer drawer;
    private Purenrupu purenrupu;
    private int stepCounter = 0;

    @FXML
    public Text infoText;

    @FXML
    public GridPane gridPane;

    public void handleOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F5) {
            if (stepCounter < purenrupu.getVisitedCells().size()) {
                System.out.println("Step");
                drawer.drawStep(this.purenrupu.getVisitedCells().get(stepCounter));
                stepCounter++;
            } else {
                System.out.println("Already finished");
            }
        } else if (keyEvent.getCode() == KeyCode.F6) {
            System.out.println("Solve");
            drawer.drawAllSteps(this.purenrupu.getVisitedCells());
        } else if (keyEvent.getCode() == KeyCode.F8) {
            System.out.println("Close");
            System.exit(0);
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDropped(DragEvent dragEvent) {
        try {
            JsonParser jsonParser = new JsonParser();
            String jsonAsString = jsonParser.getJsonAsString(dragEvent);
            JSONObject jsonObject = jsonParser.getJsonAsObject(jsonAsString);

            Board board = new Board(jsonObject);
            this.purenrupu = new Purenrupu(board);
            infoText.setVisible(false);

            drawer = new Drawer(gridPane, purenrupu.getBoard());
            drawer.drawInit();

            boolean solved = purenrupu.solveMaze();

            if (solved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Solved \n F5 => Step \n F6 => Solve \n F8 => Close");
                alert.show();
            }
        } catch (Exception e) {
            infoText.setVisible(true);
            infoText.setText("Sorry. Try again with another JSON wile, compatible with the puzzle format " + e);
        }
    }
}
