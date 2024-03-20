package quantum.algorithms;

import quantum.core.QuantumCircuit;
import quantum.core.QuantumRegister;
import quantum.gates.IQuantumGate;
import quantum.gates.unary.HadamardGate;
import quantum.gates.unary.PauliXGate;

public class DeutschJosza {
    public static boolean run(QuantumRegister register, IQuantumGate oracle) {
        int oracleInputSize = oracle.getInputSize();
        if (register.getNumQubits() != oracleInputSize) {
            throw new IllegalArgumentException("DeutschJosza requires " + oracleInputSize + " qubits in the register.");
        }

        QuantumCircuit circuit = new QuantumCircuit(register);

        circuit.addGate(new PauliXGate(), oracleInputSize - 1);

        for (int i = 0; i < register.getNumQubits(); i++) {
            circuit.addGate(new HadamardGate(), i);
        }

        int[] qubitIndices = new int[oracleInputSize];
        for (int i = 0; i < oracleInputSize; i++) {
            qubitIndices[i] = i;
        }
        circuit.addGate(oracle, qubitIndices);

        for (int i = 0; i < oracleInputSize - 1; i++) {
            circuit.addGate(new HadamardGate(), i);
        }

        for (int i = 0; i < oracleInputSize - 1; i++) {
            circuit.addMeasurement(i);
        }

        circuit.execute();

        return circuit.getMeasurementResults().stream()
                .limit(oracleInputSize - 1)
                .allMatch(result -> result.value() == 0);
    }
}
