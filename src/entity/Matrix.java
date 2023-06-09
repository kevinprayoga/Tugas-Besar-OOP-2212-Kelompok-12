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

    public boolean contains(T value) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                T temp = data[i][j];
                if (temp != null && temp.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void remove(T value) {
        if (contains(value)) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    T temp = data[i][j];
                    if (temp != null && temp.equals(value)) {
                        data[i][j] = null;
                    }
                }
            }
        }
    }
}
