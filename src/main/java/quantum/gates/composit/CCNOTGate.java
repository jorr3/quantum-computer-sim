package quantum.gates.composit;

import quantum.core.Qubit;
import quantum.gates.*;
import quantum.gates.binary.CNOTGate;
import quantum.gates.unary.HadamardGate;
import quantum.gates.unary.SGate;
import quantum.gates.unary.TDaggerGate;
import quantum.gates.unary.TGate;
import quantum.math.Vector;

public class CCNOTGate extends QuantumGate {
    public CCNOTGate() {
        super(3, null);
    }

    public Vector apply(Vector state, Qubit[] allQubits, Qubit... inputQubits) {
        if (inputQubits.length != inputSize) {
            throw new IllegalArgumentException("CCNOTGate requires exactly three input qubits.");
        }

        Qubit control1 = inputQubits[0];
        Qubit control2 = inputQubits[1];
        Qubit target = inputQubits[2];

        HadamardGate hGate = new HadamardGate();
        CNOTGate cnotGate = new CNOTGate();
        TGate tGate = new TGate();
        TDaggerGate tDaggerGate = new TDaggerGate();
        SGate sGate = new SGate();

        // Applying the sequence of gates
        state = hGate.apply(state, allQubits, new Qubit[]{target});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control2, target});
        state = tDaggerGate.apply(state, allQubits, new Qubit[]{target});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control1, target});
        state = tGate.apply(state, allQubits, new Qubit[]{target});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control2, target});
        state = tDaggerGate.apply(state, allQubits, new Qubit[]{target});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control1, target});
        state = tGate.apply(state, allQubits, new Qubit[]{target});
        state = tGate.apply(state, allQubits, new Qubit[]{control2});
        state = hGate.apply(state, allQubits, new Qubit[]{target});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control1, control2});
        state = tGate.apply(state, allQubits, new Qubit[]{control2});
        state = tDaggerGate.apply(state, allQubits, new Qubit[]{control1});
        state = cnotGate.apply(state, allQubits, new Qubit[]{control1, control2});

        return state;
    }
}