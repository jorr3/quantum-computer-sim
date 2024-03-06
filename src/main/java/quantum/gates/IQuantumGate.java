package quantum.gates;

import quantum.core.Qubit;
import quantum.math.Vector;

public interface IQuantumGate {
    int getInputSize();
    Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits);
}
