package app;

import java.util.ArrayList;
import java.util.List;

public class Purenrupu {
    private Board board;
    private Cell startPosition;
    private List<Cell> visitedCells = new ArrayList<>();

    public Purenrupu(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }


    public List<Cell> getVisitedCells() {
        return visitedCells;
    }

    public void setStartPosition() {
        Cell tmpCell = null;
        for (Cell[] cell1 : board.getCells()) {
            if (tmpCell == null) {
                for (Cell cell2 : cell1) {
                    if (cell2.getValue() == 0) tmpCell = cell2;
                    break;
                }
            } else {
                break;
            }
        }
        this.startPosition = tmpCell;
    }

    public void getStartPosition() {
        setStartPosition();
    }

    public boolean isNeighbour(Cell startPosition, Cell currentPosition) {
        List<Cell> neighbours = this.board.getNeighbours(startPosition);

        return neighbours.contains(currentPosition);
    }

    private boolean isSafe(int rowPosition, int columnPosition) {
        return rowPosition >= 0 && rowPosition < this.board.getRows() && columnPosition >= 0 && columnPosition < this.board.getColumns() && board.getCells()[rowPosition][columnPosition].getValue() == 0;
    }

    public boolean solveMaze() {
        getStartPosition();

        if (!solveMazeUtil(this.startPosition.getCoordinate().getRow(), this.startPosition.getCoordinate().getColumn())) {
            System.out.println("Solution doesnt exist");
            return false;
        } else {
            System.out.println("--- Maze solved ---");
            board.printBoard();
            System.out.println();
            setCornerAngels();
            setLines();
            return true;
        }
    }

    private boolean solveMazeUtil(int row, int column) {
        int amountOfUnvisited = 0;

        for (Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                if (cell.getValue() == 0) amountOfUnvisited++;
            }
        }

        if (amountOfUnvisited == 0 && isNeighbour(startPosition, visitedCells.get(visitedCells.size() - 1))) {
            return true;
        }

        if (isSafe(row, column)) {
            Cell tmpCell = board.getCells()[row][column];
            tmpCell.setValue(2);
            this.visitedCells.add(tmpCell);
            board.getCells()[row][column].setValue(2);

            if (solveMazeUtil(row + 1, column))
                return true;

            if (solveMazeUtil(row, column + 1))
                return true;

            if (solveMazeUtil(row - 1, column))
                return true;

            if (solveMazeUtil(row, column - 1))
                return true;

            this.visitedCells.remove(tmpCell);
            board.getCells()[row][column].setValue(0);
            return false;
        }
        return false;
    }

    private void setCornerAngels() {
        if (board.getCells()[0][0].getValue() == 2)
            board.getCells()[0][0].setCellType(CellType.angelUpLeft);

        if (board.getCells()[0][board.getColumns() - 1].getValue() == 2)
            board.getCells()[0][board.getColumns() - 1].setCellType(CellType.angelUpRight);

        if (board.getCells()[board.getRows() - 1][0].getValue() == 2)
            board.getCells()[board.getRows() - 1][0].setCellType(CellType.angelDownLeft);

        if (board.getCells()[board.getRows() - 1][board.getColumns() - 1].getValue() == 2)
            board.getCells()[board.getRows() - 1][board.getColumns() - 1].setCellType(CellType.angelDownRight);
    }

    private void setLines() {
        for (int index = 0; index < visitedCells.size(); index++) {
            Cell currentCell = visitedCells.get(index);
            Cell previousCell = currentCell;
            Cell nextCell = currentCell;

            if (index != 0)
                previousCell = visitedCells.get(index - 1);

            if (index < visitedCells.size() - 1)
                nextCell = visitedCells.get(index + 1);

            if (currentCell == visitedCells.get(visitedCells.size() - 1))
                nextCell = visitedCells.get(0);

            setLineType(currentCell, previousCell, nextCell);
        }
    }

    private void setLineType(Cell currentCell, Cell previousCell, Cell nextCell) {
        if (currentCell.getCellType() != CellType.standard) return;

        if (currentCell.getCoordinate().getRow() == 0 && currentCell.getCoordinate().getColumn() == 1)
            System.out.println("Stop");

        int currentRow = currentCell.getCoordinate().getRow();
        int currentColumn = currentCell.getCoordinate().getColumn();

        int previousRow = previousCell.getCoordinate().getRow();
        int previousColumn = previousCell.getCoordinate().getColumn();

        int nextRow = nextCell.getCoordinate().getRow();
        int nextColumn = nextCell.getCoordinate().getColumn();

        if (currentRow == previousRow && currentRow == nextRow && currentColumn != previousColumn && currentColumn != nextColumn)
            currentCell.setCellType(CellType.horizontal);

        if (currentRow != previousRow && currentRow != nextRow && currentColumn == previousColumn && currentColumn == nextColumn)
            currentCell.setCellType(CellType.vertical);

        if (currentRow <= previousRow && currentRow <= nextRow && currentColumn <= previousColumn && currentColumn <= nextColumn)
            currentCell.setCellType(CellType.angelUpLeft);

        if (currentRow <= previousRow && currentRow <= nextRow && currentColumn >= previousColumn && currentColumn >= nextColumn)
            currentCell.setCellType(CellType.angelUpRight);

        if (currentRow >= previousRow && currentRow >= nextRow && currentColumn <= previousColumn && currentColumn <= nextColumn)
            currentCell.setCellType(CellType.angelDownLeft);

        if (currentRow >= previousRow && currentRow >= nextRow && currentColumn >= previousColumn && currentColumn >= nextColumn)
            currentCell.setCellType(CellType.angelDownRight);
    }
}
