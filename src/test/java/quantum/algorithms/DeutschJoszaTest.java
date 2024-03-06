package quantum.algorithms;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import quantum.core.QuantumRegister;
import quantum.core.Qubit;
import quantum.gates.BinaryQuantumGate;
import quantum.gates.IQuantumGate;
import quantum.gates.binary.CNOTGate;
import quantum.gates.binary.SwapGate;
import quantum.math.Matrix;
import quantum.math.Vector;

public class DeutschJoszaTest {
    @RepeatedTest(10)
    public void testConstantOracle() {
        class BinaryIdentityGate extends BinaryQuantumGate {
            public BinaryIdentityGate() {
                super(Matrix.identity(4));
            }

            @Override
            public Vector apply(Vector state, Qubit[] allQubits, Qubit[] inputQubits) {
                return state;
            }
        }

        QuantumRegister register = new QuantumRegister(2);
        IQuantumGate constantOracle = new BinaryIdentityGate();

        boolean isConstant = DeutschJosza.run(register, constantOracle);

        assertTrue(isConstant, "Oracle should be identified as constant");
    }

    @RepeatedTest(10)
    @Test
    public void testBalancedOracle() {
        QuantumRegister register = new QuantumRegister(2);
        IQuantumGate balancedOracle = new CNOTGate();

        boolean isConstant = DeutschJosza.run(register, balancedOracle);

        assertFalse(isConstant, "Oracle should be identified as balanced");
    }

    @Test
    public void testWithInvalidNumberOfQubits() {
        QuantumRegister register = new QuantumRegister(3);
        IQuantumGate oracle = new SwapGate();

        assertThrows(IllegalArgumentException.class, () -> DeutschJosza.run(register, oracle),
                "Should throw IllegalArgumentException for invalid number of qubits");
    }

}
