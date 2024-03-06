package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class RYGate extends UnaryQuantumGate {
    public RYGate(double theta) {
        super(createRYMatrix(theta));
    }

    private static Matrix createRYMatrix(double theta) {
        double cos = Math.cos(theta / 2);
        double sin = Math.sin(theta / 2);
        ComplexNumber[][] data = {
                {new ComplexNumber(cos, 0), new ComplexNumber(-sin, 0)},
                {new ComplexNumber(sin, 0), new ComplexNumber(cos, 0)}
        };
        return new Matrix(data);
    }
}
