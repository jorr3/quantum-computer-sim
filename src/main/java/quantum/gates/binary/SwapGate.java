package quantum.gates.binary;

import quantum.gates.QuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Matrix;
import quantum.math.Vector;
import quantum.core.Qubit;

public class SwapGate extends QuantumGate {

    public SwapGate() {
        super(2, createSwapGateMatrix());
    }

    private static Matrix createSwapGateMatrix() {
        ComplexNumber[][] data = {
                {new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0)},
                {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1)}
        };
        return new Matrix(data);
    }

    public Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits) {
        if (inputQubits.length != 2) {
            throw new IllegalArgumentException("Swap operation requires exactly two input qubits.");
        }

        Qubit qubit1 = inputQubits[0];
        Qubit qubit2 = inputQubits[1];
        int index1 = qubit1.getIndex();
        int index2 = qubit2.getIndex();

        state = moveQubit(state, allQubits, qubit1, index2);
        state = moveQubit(state, allQubits, qubit2, index1);

        return state;
    }

    public Vector moveQubit(Vector state, Qubit[] allQubits, Qubit qubit1, int index) {
        while (qubit1.getIndex() != index) {
            int swapIdx = qubit1.getIndex() < index ? qubit1.getIndex() + 1 : qubit1.getIndex() - 1;
            state = swapAdjacent(state, allQubits, qubit1, allQubits[swapIdx]);
        }
        return state;
    }

    private Vector swapAdjacent(Vector state, Qubit[] allQubits, Qubit qubit1, Qubit qubit2) {
        if (Math.abs(qubit1.getIndex() - qubit2.getIndex()) != 1) {
            throw new IllegalArgumentException("Qubits must be adjacent in a state to apply a swap operation.");
        }

        Matrix operationMatrix = Matrix.identity(1);

        for (int i = 0; i < allQubits.length; i++) {
            if (i == qubit1.getIndex() || i == qubit2.getIndex()) {
                operationMatrix = operationMatrix.tensorProduct(matrixRepresentation);
                i++;
            } else {
                Matrix identityMatrix = Matrix.identity(2);
                operationMatrix = operationMatrix.tensorProduct(identityMatrix);
            }
        }

        Vector newState = operationMatrix.multiply(state);

        int index1 = qubit1.getIndex();
        int index2 = qubit2.getIndex();
        qubit1.setIndex(index2);
        qubit2.setIndex(index1);
        
        return newState;
    }
}
