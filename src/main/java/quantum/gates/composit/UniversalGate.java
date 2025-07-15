package quantum.gates.composit;

import quantum.core.Qubit;
import quantum.gates.QuantumGate;
import quantum.gates.unary.RYGate;
import quantum.gates.unary.RZGate;
import quantum.math.Vector;

public class UniversalGate extends QuantumGate {
    private final double theta; // Rotation around the Y-axis
    private final double phi;   // First rotation around the Z-axis
    private final double lambda; // Second rotation around the Z-axis

    public UniversalGate(double theta, double phi, double lambda) {
        super(3, null);
        this.theta = theta;
        this.phi = phi;
        this.lambda = lambda;
    }

    @Override
    public Vector apply(Vector state, Qubit[] allQubits, Qubit... inputQubits) {
        if (inputQubits.length != 1) {
            throw new IllegalArgumentException("UniversalGate requires exactly one input qubit.");
        }

        RZGate rzGatePhi = new RZGate(phi);
        RYGate ryGateTheta = new RYGate(theta);
        RZGate rzGateLambda = new RZGate(lambda);

        state = rzGatePhi.apply(state, allQubits, inputQubits);
        state = ryGateTheta.apply(state, allQubits, inputQubits);
        state = rzGateLambda.apply(state, allQubits, inputQubits);

        return state;
    }
}
