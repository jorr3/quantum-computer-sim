package quantum.gates;

import quantum.core.Qubit;
import quantum.math.Matrix;
import quantum.math.Vector;

public abstract class UnaryQuantumGate extends QuantumGate {
    protected UnaryQuantumGate(Matrix matrixRepresentation) {
        super(1, matrixRepresentation);
    }

    public Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits) {
        validateInputQubits(inputQubits);

        Qubit target = inputQubits[0];
        Matrix applicationMatrix = createApplicationMatrix(target, allQubits.length);
        return applicationMatrix.multiply(state);
    }

    private void validateInputQubits(Qubit[] inputQubits) {
        if (inputQubits.length != 1) {
            throw new IllegalArgumentException("UnaryQuantumGate requires exactly one input qubit.");
        }
    }
}
