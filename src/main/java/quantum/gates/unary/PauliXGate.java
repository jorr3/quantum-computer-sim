package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class PauliXGate extends UnaryQuantumGate {
    public PauliXGate() {
        super(createPauliXMatrix());
    }

    private static Matrix createPauliXMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(0), new ComplexNumber(1)},
                {new ComplexNumber(1), new ComplexNumber(0)}
        };
        return new Matrix(data);
    }
}
