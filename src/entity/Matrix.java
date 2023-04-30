package entity.unit;

public class Matrix<T> {
    private T[][] matrix;
    private int row;
    private int column;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        matrix = (T[][]) new Object[row][column];
    }

    public void set(int row, int column, T value) {
        matrix[row][column] = value;
    }

    public T get(int row, int column) {
        return matrix[row][column];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
