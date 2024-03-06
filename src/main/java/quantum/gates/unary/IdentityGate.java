package quantum.gates.unary;

import quantum.core.Qubit;
import quantum.gates.UnaryQuantumGate;
import quantum.math.Matrix;
import quantum.math.Vector;

public class IdentityGate extends UnaryQuantumGate {
    public IdentityGate() {
        super(Matrix.identity(2));
    }

    @Override
    public Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits) {
        return state;
    }
}
