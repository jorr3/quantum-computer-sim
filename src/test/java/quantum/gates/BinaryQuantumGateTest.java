package quantum.gates;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import quantum.core.QuantumRegister;
import quantum.gates.binary.CNOTGate;
import quantum.gates.binary.SwapGate;
import quantum.gates.unary.IdentityGate;
import quantum.gates.unary.PauliXGate;
import quantum.math.*;

import static utils.StateParser.parseState;
import static utils.StateParser.stateFromStr;

import java.util.stream.Stream;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BinaryQuantumGateTest {
    record BinaryGateTestParams(IQuantumGate binaryGate, IQuantumGate[] initialisationGates, Vector expectedState) {}

    private BinaryGateTestParams createTestParams(IQuantumGate gate, IQuantumGate[] initGates, String expectedStateStr) {
        Vector expectedState = stateFromStr(expectedStateStr);
        return new BinaryGateTestParams(gate, initGates, expectedState);
    }

    private Stream<BinaryGateTestParams> cnotGateTests() {
        IQuantumGate cnotGate = new CNOTGate();
        return Stream.of(
                createTestParams(cnotGate, new IQuantumGate[]{}, "|00⟩"),
                createTestParams(cnotGate, new IQuantumGate[]{new IdentityGate(), new PauliXGate()}, "|01⟩"),
                createTestParams(cnotGate, new IQuantumGate[]{new PauliXGate(), new IdentityGate()}, "|11⟩"),
                createTestParams(cnotGate, new IQuantumGate[]{new PauliXGate(), new PauliXGate()}, "|10⟩")
        );
    }

    private Stream<BinaryGateTestParams> swapGateTests() {
        IQuantumGate swapGate = new SwapGate();
        return Stream.of(
                createTestParams(swapGate, new IQuantumGate[]{}, "|00⟩"),
                createTestParams(swapGate, new IQuantumGate[]{new IdentityGate(), new PauliXGate()}, "|10⟩"),
                createTestParams(swapGate, new IQuantumGate[]{new PauliXGate(), new IdentityGate()}, "|01⟩"),
                createTestParams(swapGate, new IQuantumGate[]{new PauliXGate(), new PauliXGate()}, "|11⟩")
        );
    }

    private static Stream<BinaryGateTestParams> gateProvider() {
        return Stream.concat(
                new BinaryQuantumGateTest().cnotGateTests(),
                new BinaryQuantumGateTest().swapGateTests()
        );
    }

    @ParameterizedTest
    @MethodSource("gateProvider")
    void testBinaryGates(BinaryGateTestParams params) {
        QuantumRegister register = new QuantumRegister(2);

        // Apply initial state gates
        for (int i = 0; i < params.initialisationGates().length; i++) {
            register.applyGate(params.initialisationGates()[i], i % 2);
        }

        Vector initialState = register.getState();

        // Apply the binary gate
        register.applyGate(params.binaryGate(), 0, 1);

        Vector actualState = register.getState();
        int inumInputs = params.binaryGate().getInputSize();
        assertTrue(params.expectedState().equals(actualState),
                "Binary gate " + params.binaryGate().getClass().getSimpleName() +
                        " did not produce the expected state.\n" +
                        "Initial State: " + parseState(initialState, inumInputs) + "\n" +
                        "Expected: " + parseState(params.expectedState(), inumInputs) + "\n" +
                        "Actual: " + parseState(actualState, inumInputs) + "\n");
    }
}
