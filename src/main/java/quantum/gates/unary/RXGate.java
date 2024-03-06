package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class RXGate extends UnaryQuantumGate {
    public RXGate(double theta) {
        super(createRXMatrix(theta));
    }

    private static Matrix createRXMatrix(double theta) {
        double cos = Math.cos(theta / 2);
        double sin = Math.sin(theta / 2);
        ComplexNumber[][] data = {
                {new ComplexNumber(cos, 0), new ComplexNumber(0, -sin)},
                {new ComplexNumber(0, -sin), new ComplexNumber(cos, 0)}
        };
        return new Matrix(data);
    }
}
