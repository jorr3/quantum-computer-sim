package quantum.gates.unary;

import quantum.gates.UnaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class HadamardGate extends UnaryQuantumGate {
    public HadamardGate() {
        super(createHadamardMatrix());
    }

    private static Matrix createHadamardMatrix() {
        double factor = 1 / Math.sqrt(2);
        ComplexNumber[][] data = {
                {new ComplexNumber(factor), new ComplexNumber(factor)},
                {new ComplexNumber(factor), new ComplexNumber(-factor)}
        };
        return new Matrix(data);
    }
}
