package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class SGate extends UnaryQuantumGate {
    public SGate() {
        super(createSGateMatrix());
    }

    private static Matrix createSGateMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(1, 0), new ComplexNumber(0, 0)},
                {new ComplexNumber(0, 0), new ComplexNumber(0, 1)}
        };
        return new Matrix(data);
    }
}
