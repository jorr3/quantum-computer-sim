package quantum.gates;

import quantum.core.Qubit;
import quantum.math.Matrix;

public abstract class QuantumGate implements IQuantumGate {
    protected final int inputSize;
    protected final Matrix matrixRepresentation;

    protected QuantumGate(int inputSize, Matrix matrixRepresentation) {
        this.inputSize = inputSize;
        this.matrixRepresentation = matrixRepresentation;
    }

    public int getInputSize() {
        return inputSize;
    }

    protected Matrix createApplicationMatrix(Qubit target, int totalQubitCount) {
        Matrix identity = Matrix.identity(2);
        int targetIndex = target.getIndex();

        Matrix beforeTarget = identity.tensorPower(targetIndex);
        Matrix afterTarget = identity.tensorPower(totalQubitCount - targetIndex - inputSize);

        return beforeTarget.tensorProduct(matrixRepresentation).tensorProduct(afterTarget);
    }
}
