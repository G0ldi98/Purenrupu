package gui;

import app.Board;
import app.Cell;
import app.CellType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.List;

public class Drawer {
    private GridPane gridPane;
    private Board board;

    public Drawer(GridPane gridPane, Board board) {
        this.gridPane = gridPane;
        this.board = board;
        Region tile = new Region();

        StackPane stackPane = new StackPane(tile);
        stackPane.getChildren().clear();
    }

    public void drawInit() {
        gridPane.getChildren().clear();
        for (Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                drawCell(cell);
            }
        }
    }

    public void drawCell(Cell currentCell) {
        Region tile = new Region();
        String borders = 1 + " " + 1 + " " + 1 + " " + 1;

        tile.setStyle("-fx-background-color: black, white; -fx-background-insets: 0, " + borders + "; -fx-min-width: 40; -fx-min-height:40; -fx-max-width:40; -fx-max-height: 40;");
        tile.setStyle("-fx-background-color: black, " + Style.getCellColorAsString(currentCell.getValue()) + "; -fx-background-insets: 0, " + borders + "; -fx-min-width: 40; -fx-min-height:40; -fx-max-width:40; -fx-max-height: 40;");

        StackPane stackPane = new StackPane();

        if (currentCell.getCellType() == CellType.standard) {
            stackPane = new StackPane(tile);
        } else if (currentCell.getCellType() == CellType.angelUpLeft) {
            stackPane.getChildren().add(getCellType(currentCell.getCellType()));
        }

        gridPane.add(stackPane, currentCell.getCoordinate().getColumn(), currentCell.getCoordinate().getRow());
    }

    public void drawCellSolution(Cell currentCell) {
        Region tile = new Region();
        String borders = 1 + " " + 1 + " " + 1 + " " + 1;
        tile.setStyle("-fx-background-color: black, white; -fx-background-insets: 0, " + borders + "; -fx-min-width: 40; -fx-min-height:40; -fx-max-width:40; -fx-max-height: 40;");
        StackPane stackPane = new StackPane();

        assert currentCell != null;
        if (currentCell.getCellType() == CellType.standard) {
            stackPane = new StackPane(tile);
        } else if (currentCell.getCellType() != CellType.standard) {
            stackPane.getChildren().add(getCellType(currentCell.getCellType()));
        }

        gridPane.add(stackPane, currentCell.getCoordinate().getColumn(), currentCell.getCoordinate().getRow());
    }

    private Canvas getCellType(CellType cellType) {
        Canvas canvas = new Canvas(40, 40);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOWGREEN);
        gc.setLineWidth(4);

        switch (cellType) {
            case horizontal:
                gc.strokeLine(0, 20, 40, 20);
                break;
            case vertical:
                gc.strokeLine(20, 0, 20, 40);
                break;
            case angelUpLeft:
                gc.strokeLine(20, 20, 40, 20);
                gc.strokeLine(20, 20, 20, 40);
                break;
            case angelDownLeft:
                gc.strokeLine(20, 20, 40, 20);
                gc.strokeLine(20, 20, 20, 0);
                break;
            case angelUpRight:
                gc.strokeLine(0, 20, 20, 20);
                gc.strokeLine(20, 20, 20, 40);
                break;
            case angelDownRight:
                gc.strokeLine(0, 20, 20, 20);
                gc.strokeLine(20, 20, 20, 0);
                break;
        }

        return canvas;
    }

    public void drawStep(Cell currentCell) {
        drawCellSolution(currentCell);
    }

    public void drawAllSteps(List<Cell> visitedCells) {
        for (Cell cell : visitedCells) {
            drawStep(cell);
        }
    }
}
