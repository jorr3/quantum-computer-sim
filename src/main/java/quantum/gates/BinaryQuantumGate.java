package quantum.gates;

import quantum.core.Qubit;
import quantum.gates.binary.SwapGate;
import quantum.math.Matrix;
import quantum.math.Vector;

public abstract class BinaryQuantumGate extends QuantumGate {
    private static final SwapGate swapGate = new SwapGate();

    protected BinaryQuantumGate(Matrix matrixRepresentation) {
        super(2, matrixRepresentation);
    }

    public Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits) {
        validateInputQubits(inputQubits);

        Qubit qubit1 = inputQubits[0];
        Qubit qubit2 = inputQubits[1];

        // Move qubit2 to one place beneath qubit1 in the state vector
        swapGate.moveQubit(state, allQubits, qubit2, qubit1.getIndex() + 1);

        Matrix applicationMatrix = createApplicationMatrix(qubit1, allQubits.length);

        return applicationMatrix.multiply(state);
    }

    private void validateInputQubits(Qubit[] inputQubits) {
        if (inputQubits.length != 2) {
            throw new IllegalArgumentException("BinaryQuantumGate requires exactly two input qubits.");
        }
    }
}
