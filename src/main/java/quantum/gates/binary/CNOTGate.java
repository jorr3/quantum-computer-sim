package quantum.gates.binary;

import quantum.gates.BinaryQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;

public class CNOTGate extends BinaryQuantumGate {

    public CNOTGate() {
        super(createCNOTGateMatrix());
    }

    private static Matrix createCNOTGateMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1)},
                {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0)}
        };
        return new Matrix(data);
    }
}
