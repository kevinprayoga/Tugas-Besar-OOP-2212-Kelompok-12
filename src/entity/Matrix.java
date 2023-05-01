package entity;

public class Matrix<T> {
    private T[][] data;
    private int row;
    private int column;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        data = (T[][]) new Object[row][column];
    }

    public void set(int row, int column, T value) {
        data[row][column] = value;
    }

    public T get(int row, int column) {
        return data[row][column];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
