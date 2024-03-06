package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class PauliZGate extends UnaryQuantumGate {
    public PauliZGate() {
        super(createPauliZMatrix());
    }

    private static Matrix createPauliZMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(1), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(-1)}
        };
        return new Matrix(data);
    }
}
