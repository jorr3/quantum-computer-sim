package quantum.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import quantum.gates.IQuantumGate;
import quantum.gates.unary.HadamardGate;
import quantum.math.ComplexNumber;

public class QuantumRegisterTest {

    @Test
    void testInitialization() {
        int numQubits = 2;
        QuantumRegister register = new QuantumRegister(numQubits);
        assertEquals(numQubits, register.getNumQubits(), "Number of qubits should match the initialization value.");
        assertEquals(new ComplexNumber(1, 0), register.getState().get(0), "Initial state should be |0...0⟩");
    }

    @Test
    void testApplyGate() {
        QuantumRegister register = new QuantumRegister(2);
        IQuantumGate hadamardGate = new HadamardGate();

        register.applyGate(hadamardGate, 0);

        ComplexNumber[] expectedState = {
                new ComplexNumber(1/Math.sqrt(2), 0),
                new ComplexNumber(0, 0),
                new ComplexNumber(1/Math.sqrt(2), 0),
                new ComplexNumber(0, 0)
        };
        assertArrayEquals(expectedState, register.getState().getElements(), "State should match after applying Hadamard gate.");
    }

    @Test
    void testMeasure() {
        QuantumRegister register = new QuantumRegister(1);
        IQuantumGate hadamardGate = new HadamardGate();
        register.applyGate(hadamardGate, 0);

        MeasurementResult result = register.measure(0);
        assertTrue(result.result() == 0 || result.result() == 1, "Measurement result should be 0 or 1.");
    }

    @Test
    void testCollapseStatePostMeasurement() {
        QuantumRegister register = new QuantumRegister(1);
        IQuantumGate hadamardGate = new HadamardGate();
        register.applyGate(hadamardGate, 0);

        int result = register.measure(0).result();
        double expectedProbability = result == 0 ? 1.0 : 0.0;
        assertEquals(expectedProbability, register.getProbabilityOfZero(register.getQubit(0)),
                "Probability should collapse to 1.0 or 0.0 post measurement.");
    }
}
