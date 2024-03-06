package quantum.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import quantum.gates.binary.CNOTGate;
import quantum.gates.unary.HadamardGate;

import java.util.List;

public class QuantumCircuitTest {

    @Test
    void testSingleQubitCircuitExecution() {
        QuantumRegister register = new QuantumRegister(2);
        QuantumCircuit circuit = new QuantumCircuit(register);

        circuit.addGate(new HadamardGate(), 1);
        circuit.addGate(new HadamardGate(), 0);
        circuit.addGate(new HadamardGate(), 0);
        circuit.addGate(new HadamardGate(), 1);

        circuit.addMeasurement(0);

        circuit.execute();

        MeasurementResult result = circuit.getMeasurementResults().get(0);
        assertEquals(0, result.result());
    }

    @Test
    void testTwoQubitCircuitExecution() {
        QuantumRegister register = new QuantumRegister(2);
        QuantumCircuit circuit = new QuantumCircuit(register);

        circuit.addGate(new HadamardGate(), 0);
        circuit.addGate(new CNOTGate(), 0, 1);
        circuit.addMeasurement(0);
        circuit.addMeasurement(1);

        circuit.execute();

        List<MeasurementResult> results = circuit.getMeasurementResults();
        assertEquals(results.get(0).result(), results.get(1).result());
    }

    @Test
    void testCircuitWithNoOperations() {
        QuantumRegister register = new QuantumRegister(1);
        QuantumCircuit circuit = new QuantumCircuit(register);

        circuit.addMeasurement(0);

        circuit.execute();

        MeasurementResult result = circuit.getMeasurementResults().get(0);
        assertEquals(0, result.result());
    }
}
