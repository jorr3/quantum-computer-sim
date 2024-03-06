package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class TDaggerGate extends UnaryQuantumGate {
    public TDaggerGate() {
        super(createTDaggerMatrix());
    }

    private static Matrix createTDaggerMatrix() {
        ComplexNumber one = new ComplexNumber(1, 0);
        ComplexNumber minusIOverRoot2 = new ComplexNumber(Math.cos(-Math.PI / 4), Math.sin(-Math.PI / 4));
        ComplexNumber[][] data = {
                {one, new ComplexNumber(0, 0)},
                {new ComplexNumber(0, 0), minusIOverRoot2}
        };
        return new Matrix(data);
    }
}
