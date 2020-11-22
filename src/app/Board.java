package app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int rows, columns;
    private Cell[][] cells;
    private List<Cell> blockedCells;

    public Board() {

    }

    public Board(JSONObject jsonObject) {
        this.rows = Integer.parseInt(jsonObject.getString("rows"));
        this.columns = Integer.parseInt(jsonObject.getString("columns"));
        this.cells = new Cell[rows][columns];

        JSONArray arr = jsonObject.getJSONArray("blocked");
        blockedCells = getBlockedCells(arr);

        initBoard();
        System.out.println("--- Unsolved Maze ---");
        printBoard();
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setBlockedCells(List<Cell> blockedCells) {
        this.blockedCells = blockedCells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void initBoard() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                cells[x][y] = new Cell(0, new Coordinate(x, y));
            }
        }

        setBlockedBoard();
    }

    private List<Cell> getBlockedCells(JSONArray arr) {
        List<Cell> tmpBlockedCells = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            int column = arr.getJSONObject(i).getInt("column");
            int row = arr.getJSONObject(i).getInt("row");

            tmpBlockedCells.add(new Cell(1, new Coordinate(row, column)));
        }

        return tmpBlockedCells;
    }

    public void setBlockedBoard() {
        for (Cell cell : blockedCells) {
            int row = cell.getCoordinate().getRow();
            int column = cell.getCoordinate().getColumn();

            Cell tmpCell = cells[row][column];
            tmpCell.setValue(1);

            cells[row][column] = tmpCell;
        }
    }

    public void printBoard() {
        for (int row = 0; row < this.columns; row++) {
            for (int column = 0; column < this.rows; column++) {
                System.out.print(cells[row][column].getValue() + " ");
            }
            System.out.println();
        }
    }


    public Cell getUpCell(Cell currentCell) {

        if (currentCell.getCoordinate().getColumn() - 1 >= 0) {
            return cells[currentCell.getCoordinate().getColumn() - 1][currentCell.getCoordinate().getRow()];
        }

        return null;
    }

    public Cell getRightCell(Cell currentCell) {

        if (currentCell.getCoordinate().getRow() + 1 < rows) {
            return cells[currentCell.getCoordinate().getColumn()][currentCell.getCoordinate().getRow() + 1];
        }

        return null;
    }

    public Cell getLeftCell(Cell currentCell) {

        if (currentCell.getCoordinate().getRow() - 1 >= 0) {
            return cells[currentCell.getCoordinate().getColumn()][currentCell.getCoordinate().getRow() - 1];
        }

        return null;
    }

    public Cell getDownCell(Cell currentCell) {

        if (currentCell.getCoordinate().getColumn() + 1 < columns) {
            return cells[currentCell.getCoordinate().getColumn() + 1][currentCell.getCoordinate().getRow()];
        }

        return null;
    }

    public List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        neighbours.add(getUpCell(cell));
        neighbours.add(getLeftCell(cell));
        neighbours.add(getRightCell(cell));
        neighbours.add(getDownCell(cell));

        return neighbours;
    }
}
