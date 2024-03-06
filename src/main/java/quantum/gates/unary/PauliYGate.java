package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class PauliYGate extends UnaryQuantumGate {
    public PauliYGate() {
        super(createPauliYMatrix());
    }

    private static Matrix createPauliYMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(0, 0), new ComplexNumber(0, -1)},
                {new ComplexNumber(0, 1), new ComplexNumber(0, 0)}
        };
        return new Matrix(data);
    }
}
