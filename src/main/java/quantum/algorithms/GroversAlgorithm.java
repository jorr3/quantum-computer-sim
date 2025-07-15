package quantum.algorithms;

import quantum.core.*;
import quantum.gates.*;
import quantum.gates.unary.*;

import java.util.List;

public class GroversAlgorithm {

    public static int run(QuantumRegister register, IQuantumGate oracle) {
        int n = register.getNumQubits();
        QuantumCircuit circuit = new QuantumCircuit(register);

        for (int i = 0; i < n; i++) {
            circuit.addGate(new HadamardGate(), i);
        }

        int iterations = (int) Math.ceil(Math.sqrt(Math.pow(2, n)));
        for (int i = 0; i < iterations; i++) {
            circuit.addGate(oracle);

            for (int j = 0; j < n; j++) {
                circuit.addGate(new HadamardGate(), j);
                circuit.addGate(new PauliXGate(), j);
            }

            for (int j = 0; j < n; j++) {
                circuit.addGate(new PauliXGate(), j);
                circuit.addGate(new HadamardGate(), j);
            }
        }

        for (int i = 0; i < n; i++) {
            circuit.addMeasurement(i);
        }

        circuit.execute();
        List<MeasurementResult> results = circuit.getMeasurementResults();

        int solution = 0;
        for (MeasurementResult result : results) {
            solution |= (result.value() << result.qubitId());
        }

        return solution;
    }
}
