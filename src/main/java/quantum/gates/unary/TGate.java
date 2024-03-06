package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class TGate extends UnaryQuantumGate {
    public TGate() {
        super(createTGateMatrix());
    }

    private static Matrix createTGateMatrix() {
        double theta = Math.PI / 4;
        ComplexNumber expIPIOver4 = new ComplexNumber(Math.cos(theta), Math.sin(theta));
        ComplexNumber[][] data = {
                {new ComplexNumber(1, 0), new ComplexNumber(0, 0)},
                {new ComplexNumber(0, 0), expIPIOver4}
        };
        return new Matrix(data);
    }
}
