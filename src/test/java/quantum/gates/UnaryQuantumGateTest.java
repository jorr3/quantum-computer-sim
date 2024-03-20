package quantum.gates;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import quantum.core.QuantumRegister;
import quantum.gates.unary.*;
import quantum.math.ComplexNumber;
import quantum.math.Vector;

import static utils.StateParser.parseState;
import static utils.StateParser.stateFromStr;

import java.util.function.Function;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnaryQuantumGateTest {
    record UnaryGateTestParams(IQuantumGate gate, boolean initialStateOne, Vector expectedState) {
    }

    private UnaryGateTestParams createTestParams(IQuantumGate gate, boolean initialStateOne, String expectedStateStr) {
        Vector expectedState = stateFromStr(expectedStateStr);
        return new UnaryGateTestParams(gate, initialStateOne, expectedState);
    }

    private Stream<UnaryGateTestParams> identityGateTests() {
        return Stream.of(
                createTestParams(new IdentityGate(), false, "|0⟩"),
                createTestParams(new IdentityGate(), true, "|1⟩")
        );
    }

    private Stream<UnaryGateTestParams> pauliXGateTests() {
        return Stream.of(
                createTestParams(new PauliXGate(), false, "|1⟩"),
                createTestParams(new PauliXGate(), true, "|0⟩")
        );
    }

    private Stream<UnaryGateTestParams> pauliYGateTests() {
        return Stream.of(
                createTestParams(new PauliYGate(), false, "i|1⟩"),
                createTestParams(new PauliYGate(), true, "-i|0⟩")
        );
    }

    private Stream<UnaryGateTestParams> pauliZGateTests() {
        return Stream.of(
                createTestParams(new PauliZGate(), false, "|0⟩"),
                createTestParams(new PauliZGate(), true, "-|1⟩")
        );
    }

    private Stream<UnaryGateTestParams> hadamardGateTests() {
        ComplexNumber halfSqrt2 = new ComplexNumber(1 / Math.sqrt(2), 0);
        return Stream.of(
                new UnaryGateTestParams(new HadamardGate(), false, new Vector(new ComplexNumber[]{halfSqrt2, halfSqrt2})), // H|0⟩ = 1/√2(|0⟩ + |1⟩)
                new UnaryGateTestParams(new HadamardGate(), true, new Vector(new ComplexNumber[]{halfSqrt2, new ComplexNumber(-1 / Math.sqrt(2), 0)}))  // H|1⟩ = 1/√2(|0⟩ - |1⟩)
        );
    }

    private Stream<UnaryGateTestParams> rxGateTests() {
        double angle = Math.PI / 2;
        ComplexNumber halfSqrt2 = new ComplexNumber(1/Math.sqrt(2), 0);
        ComplexNumber negHalfSqrt2i = new ComplexNumber(0, -1/Math.sqrt(2));
        return Stream.of(
                new UnaryGateTestParams(new RXGate(angle), false, new Vector(new ComplexNumber[]{halfSqrt2, negHalfSqrt2i})),
                new UnaryGateTestParams(new RXGate(angle), true, new Vector(new ComplexNumber[]{negHalfSqrt2i, halfSqrt2}))
        );
    }

    private Stream<UnaryGateTestParams> ryGateTests() {
        double angle = Math.PI / 2;
        ComplexNumber halfSqrt2 = new ComplexNumber(1/Math.sqrt(2), 0);
        ComplexNumber negHalfSqrt2 = new ComplexNumber(-1/Math.sqrt(2), 0);
        return Stream.of(
                new UnaryGateTestParams(new RYGate(angle), false, new Vector(new ComplexNumber[]{halfSqrt2, halfSqrt2})), // |0⟩ to 1/√2(|0⟩ + |1⟩)
                new UnaryGateTestParams(new RYGate(angle), true, new Vector(new ComplexNumber[]{negHalfSqrt2, halfSqrt2})) // |1⟩ to 1/√2(|0⟩ - |1⟩)
        );
    }

    private Stream<UnaryGateTestParams> rzGateTests() {
        double angle = Math.PI / 2;
        ComplexNumber expMinusIPiOver4 = new ComplexNumber(1/Math.sqrt(2), -1/Math.sqrt(2)); // e^{-iπ/4}
        ComplexNumber expPlusIPiOver4 = new ComplexNumber(1/Math.sqrt(2), 1/Math.sqrt(2));  // e^{iπ/4}
        return Stream.of(
                new UnaryGateTestParams(new RZGate(angle), false, new Vector(new ComplexNumber[]{expMinusIPiOver4, new ComplexNumber(0, 0)})), // |0⟩ to e^{-iπ/4}|0⟩
                new UnaryGateTestParams(new RZGate(angle), true, new Vector(new ComplexNumber[]{new ComplexNumber(0, 0), expPlusIPiOver4}))  // |1⟩ to e^{iπ/4}|1⟩
        );
    }

    private Stream<UnaryGateTestParams> sGateTests() {
        return Stream.of(
                new UnaryGateTestParams(new SGate(), false, new Vector(new ComplexNumber[]{new ComplexNumber(1, 0), new ComplexNumber(0, 0)})), // S|0⟩ = |0⟩
                new UnaryGateTestParams(new SGate(), true, new Vector(new ComplexNumber[]{new ComplexNumber(0, 0), new ComplexNumber(0, 1)}))  // S|1⟩ = i|1⟩
        );
    }

    private Stream<UnaryGateTestParams> tGateTests() {
        ComplexNumber onePlusIOverSqrt2 = new ComplexNumber(1 / Math.sqrt(2), 1 / Math.sqrt(2));
        return Stream.of(
                new UnaryGateTestParams(new TGate(), false, new Vector(new ComplexNumber[]{new ComplexNumber(1, 0), new ComplexNumber(0, 0)})), // T|0⟩ = |0⟩
                new UnaryGateTestParams(new TGate(), true, new Vector(new ComplexNumber[]{new ComplexNumber(0, 0), onePlusIOverSqrt2}))  // T|1⟩ = (1+i)/√2|1⟩
        );
    }

    private Stream<UnaryGateTestParams> tDaggerGateTests() {
        ComplexNumber oneMinusIOverSqrt2 = new ComplexNumber(1 / Math.sqrt(2), -1 / Math.sqrt(2));
        return Stream.of(
                new UnaryGateTestParams(new TDaggerGate(), false, new Vector(new ComplexNumber[]{new ComplexNumber(1, 0), new ComplexNumber(0, 0)})), // T†|0⟩ = |0⟩
                new UnaryGateTestParams(new TDaggerGate(), true, new Vector(new ComplexNumber[]{new ComplexNumber(0, 0), oneMinusIOverSqrt2}))  // T†|1⟩ = (1-i)/√2|1⟩
        );
    }

    private static Stream<UnaryGateTestParams> gateProvider() {
        return Stream.of(
                new UnaryQuantumGateTest().tGateTests(),
                new UnaryQuantumGateTest().pauliYGateTests(),
                new UnaryQuantumGateTest().identityGateTests(),
                new UnaryQuantumGateTest().pauliXGateTests(),
                new UnaryQuantumGateTest().pauliZGateTests(),
                new UnaryQuantumGateTest().hadamardGateTests(),
                new UnaryQuantumGateTest().rxGateTests(),
                new UnaryQuantumGateTest().ryGateTests(),
                new UnaryQuantumGateTest().rzGateTests(),
                new UnaryQuantumGateTest().sGateTests(),
                new UnaryQuantumGateTest().tDaggerGateTests()

        ).flatMap(Function.identity());
    }

    @ParameterizedTest
    @MethodSource("gateProvider")
    void testUnaryGates(UnaryGateTestParams params) {
        QuantumRegister register = new QuantumRegister(1);

        // Initialize the qubit to |1> if initialStateOne is true
        if (params.initialStateOne()) {
            register.applyGate(new PauliXGate(), 0);
        }

        Vector initialState = register.getState();

        register.applyGate(params.gate(), 0);

        Vector actualState = register.getState();
        int numInputs = 1; // Unary gates always work on a single qubit
        assertTrue(params.expectedState().equals(actualState),
                "Unary gate " + params.gate().getClass().getSimpleName() +
                        " did not produce the expected state.\n" +
                        "Initial State: " + parseState(initialState, numInputs) + "\n" +
                        "Expected: " + parseState(params.expectedState(), numInputs) + "\n" +
                        "Actual: " + parseState(actualState, numInputs) + "\n");
    }
}
