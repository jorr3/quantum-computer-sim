package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class RZGate extends UnaryQuantumGate {
    public RZGate(double theta) {
        super(createRZMatrix(theta));
    }

    private static Matrix createRZMatrix(double theta) {
        ComplexNumber eNegIHalfTheta = new ComplexNumber(Math.cos(-theta / 2), Math.sin(-theta / 2));
        ComplexNumber eIHalfTheta = new ComplexNumber(Math.cos(theta / 2), Math.sin(theta / 2));
        ComplexNumber[][] data = {
                {eNegIHalfTheta, new ComplexNumber(0, 0)},
                {new ComplexNumber(0, 0), eIHalfTheta}
        };
        return new Matrix(data);
    }
}
