package app;

public class Cell {
    private int value;
    private Coordinate coordinate;
    private CellType cellType;

    public Cell(int value, Coordinate coordinate) {
        this.value = value;
        this.coordinate = coordinate;
        this.cellType = CellType.standard;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
