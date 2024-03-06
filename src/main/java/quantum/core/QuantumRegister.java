package quantum.core;

import quantum.gates.IQuantumGate;
import quantum.math.ComplexNumber;
import quantum.math.Vector;

import static utils.Utils.reverseBits;

public class QuantumRegister {
    private Vector globalState;
    private int numQubits;
    private Qubit[] qubits;

    public QuantumRegister(int numQubits) {
        this.numQubits = numQubits;
        this.globalState = globalZeroState(numQubits);
        this.qubits = new Qubit[numQubits];
        for (int i = 0; i < numQubits; i++) {
            qubits[i] = new Qubit(i, i);
        }
    }

    public Vector getState() {
        return globalState;
    }

    public int getNumQubits() {
        return numQubits;
    }

    public Qubit getQubit(int id) {
        for (Qubit qubit : qubits) {
            if (qubit.getId() == id) {
                return qubit;
            }
        }

        throw new IllegalArgumentException("Qubit with id " + id + " does not exist.");
    }

    public void applyGate(IQuantumGate gate, int... qubitIds) {
        Qubit[] inputQubits = new Qubit[qubitIds.length];
        for (int i = 0; i < qubitIds.length; i++) {
            inputQubits[i] = getQubit(qubitIds[i]);
        }

        globalState = gate.apply(globalState, qubits, inputQubits);
    }

    public MeasurementResult measure(int qubitId) {
        Qubit qubit = getQubit(qubitId);

        double probabilityOfZero = getProbabilityOfZero(qubit);
        double random = Math.random();
        int measurementResult = (random < probabilityOfZero) ? 0 : 1;

        globalState = collapseQubitState(qubit, measurementResult);

        return new MeasurementResult(qubitId, measurementResult);
    }

    double getProbabilityOfZero(Qubit qubit) {
        double probability = 0.0;

        for (int state = 0; state < globalState.size(); state++) {
            // Use bitwise operation to check if the qubit at qubitIndex is in the |0⟩ state
            if ((reverseBits(state, numQubits) & (1 << qubit.getIndex())) == 0) {
                probability += globalState.get(state).magnitudeSquared();
            }
        }

        return probability;
    }

    private Vector collapseQubitState(Qubit qubit, int measurementResult) {
        ComplexNumber[] newAmplitudes = new ComplexNumber[globalState.size()];

        for (int state = 0; state < globalState.size(); state++) {
            if (((state >> qubit.getIndex()) & 1) != measurementResult) {
                newAmplitudes[state] = new ComplexNumber(0, 0);
            } else {
                newAmplitudes[state] = globalState.get(state);
            }
        }

        Vector newState = new Vector(newAmplitudes);
        return newState.normalize();
    }


    private static Vector globalZeroState(int numQubits) {
        int possibleStates = (int) Math.pow(2, numQubits);
        ComplexNumber[] state = new ComplexNumber[possibleStates];

        state[0] = new ComplexNumber(1);
        for (int i = 1; i < possibleStates; i++) {
            state[i] = new ComplexNumber(0);
        }

        return new Vector(state);
    }
}
