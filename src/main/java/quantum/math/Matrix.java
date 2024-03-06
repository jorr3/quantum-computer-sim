package quantum.math;

import java.util.function.BiFunction;

public class Matrix {
    private final ComplexNumber[][] elements;

    public Matrix(ComplexNumber[][] elements) {
        this.elements = deepcopyElements(elements);
    }

    private static ComplexNumber[][] deepcopyElements(ComplexNumber[][] original) {
        int rows = original.length;
        ComplexNumber[][] copy = new ComplexNumber[rows][];

        for (int i = 0; i < rows; i++) {
            int cols = original[i].length;
            copy[i] = new ComplexNumber[cols];
            System.arraycopy(original[i], 0, copy[i], 0, cols);
        }

        return copy;
    }

    public ComplexNumber[][] getElements() {
        return deepcopyElements(elements);
    }

    public ComplexNumber getElement(int row, int column) {
        return elements[row][column];
    }

    public ComplexNumber[] getRow(int row) {
        return elements[row].clone();
    }

    public int getRowCount() {
        return elements.length;
    }

    public int getColumnCount() {
        return elements.length > 0 ? elements[0].length : 0;
    }

    public ComplexNumber[] getColumn(int col) {
        ComplexNumber[] columnValues = new ComplexNumber[getRowCount()];
        for (int i = 0; i < getRowCount(); i++) {
            columnValues[i] = elements[i][col];
        }
        return columnValues;
    }

    private Matrix scalarOperation(ComplexNumber scalar, BiFunction<ComplexNumber, ComplexNumber, ComplexNumber> operation) {
        ComplexNumber[][] result = new ComplexNumber[getRowCount()][getColumnCount()];
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                result[i][j] = operation.apply(elements[i][j], scalar);
            }
        }
        return new Matrix(result);
    }

    private Matrix elementWiseOperation(Matrix other, BiFunction<ComplexNumber, ComplexNumber, ComplexNumber> operation) {
        if (getRowCount() != other.getRowCount() || getColumnCount() != other.getColumnCount()) {
            throw new IllegalArgumentException(
                    "RuntimeError: Matrix shapes do not match for element-wise operation: " +
                            "(" + getRowCount() + "x" + getColumnCount() + ") and " +
                            "(" + other.getRowCount() + "x" + other.getColumnCount() + ")"
            );
        }

        ComplexNumber[][] result = new ComplexNumber[getRowCount()][getColumnCount()];
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                result[i][j] = operation.apply(elements[i][j], other.getElement(i, j));
            }
        }
        return new Matrix(result);
    }

    public Matrix add(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::add);
    }

    public Matrix add(Matrix other) {
        return elementWiseOperation(other, ComplexNumber::add);
    }

    public Matrix subtract(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::subtract);
    }

    public Matrix subtract(Matrix other) {
        return elementWiseOperation(other, ComplexNumber::subtract);
    }

    public Matrix multiply(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::multiply);
    }

    public Matrix multiply(Matrix other) {
        if (this.getColumnCount() != other.getRowCount()) {
            throw new IllegalArgumentException(
                    "RuntimeError: Matrix shapes cannot be multiplied: " +
                            "(" + getRowCount() + "x" + getColumnCount() + ") and " +
                            "(" + other.getRowCount() + "x" + other.getColumnCount() + ")"
            );
        }

        ComplexNumber[][] result = new ComplexNumber[this.getRowCount()][other.getColumnCount()];
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < other.getColumnCount(); j++) {
                result[i][j] = new ComplexNumber(0, 0);
                for (int k = 0; k < this.getColumnCount(); k++) {
                    result[i][j] = result[i][j].add(this.elements[i][k].multiply(other.elements[k][j]));
                }
            }
        }
        return new Matrix(result);
    }

    public Vector multiply(Vector inputVector) {
        if (this.getColumnCount() != inputVector.size()) {
            throw new IllegalArgumentException(
                    "RuntimeError: Matrix and Vector shapes cannot be multiplied: " +
                            "(" + getRowCount() + "x" + getColumnCount() + ") and " +
                            inputVector.size()
            );
        }

        ComplexNumber[] result = new ComplexNumber[getRowCount()];
        for (int i = 0; i < getRowCount(); i++) {
            ComplexNumber rowSum = new ComplexNumber(0, 0);
            for (int j = 0; j < getColumnCount(); j++) {
                ComplexNumber elementProduct = elements[i][j].multiply(inputVector.get(j));
                rowSum = rowSum.add(elementProduct);
            }
            result[i] = rowSum;
        }
        return new Vector(result);
    }

    public Matrix tensorProduct(Matrix otherMatrix) {
        int thisRowCount = getRowCount();
        int thisColumnCount = getColumnCount();
        int otherRowCount = otherMatrix.getRowCount();
        int otherColumnCount = otherMatrix.getColumnCount();

        ComplexNumber[][] result = new ComplexNumber[thisRowCount * otherRowCount][thisColumnCount * otherColumnCount];

        for (int thisRow = 0; thisRow < thisRowCount; thisRow++) {
            for (int thisCol = 0; thisCol < thisColumnCount; thisCol++) {
                for (int otherRow = 0; otherRow < otherRowCount; otherRow++) {
                    for (int otherCol = 0; otherCol < otherColumnCount; otherCol++) {
                        int resultRow = thisRow * otherRowCount + otherRow;
                        int resultCol = thisCol * otherColumnCount + otherCol;
                        result[resultRow][resultCol] = elements[thisRow][thisCol].multiply(otherMatrix.getElement(otherRow, otherCol));
                    }
                }
            }
        }

        return new Matrix(result);
    }

    public Matrix tensorPower(int exponent) {
        Matrix operationMatrix = Matrix.identity(1);
        for (int i = 0; i < exponent; i++) {
            operationMatrix = operationMatrix.tensorProduct(this);
        }
        return operationMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ComplexNumber[] row : elements) {
            for (ComplexNumber cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Matrix identity(int size) {
        ComplexNumber[][] identityMatrixElements = new ComplexNumber[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identityMatrixElements[i][j] = new ComplexNumber(1);
                } else {
                    identityMatrixElements[i][j] = new ComplexNumber(0);
                }
            }
        }
        return new Matrix(identityMatrixElements);
    }
}
