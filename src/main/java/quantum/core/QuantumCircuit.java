package quantum.core;

import quantum.gates.IQuantumGate;

import java.util.ArrayList;
import java.util.List;

public class QuantumCircuit {
    private final QuantumRegister register;
    private final List<Operation> operations;
    private final List<MeasurementResult> measurementResults;

    public QuantumCircuit(QuantumRegister register) {
        this.register = register;
        this.operations = new ArrayList<>();
        this.measurementResults = new ArrayList<>();
    }

    public void addGate(IQuantumGate gate, int... qubitIds) {
        operations.add(new GateOperation(gate, qubitIds));
    }

    public void addMeasurement(int qubitId) {
        operations.add(new MeasurementOperation(qubitId));
    }

    public void execute() {
        for (Operation operation : operations) {
            operation.execute(register);
        }
    }

    public List<MeasurementResult> getMeasurementResults() {
        return measurementResults;
    }

    private interface Operation {
        void execute(QuantumRegister register);
    }

    private static class GateOperation implements Operation {
        private final IQuantumGate gate;
        private final int[] qubitIds;

        public GateOperation(IQuantumGate gate, int[] qubitIds) {
            this.gate = gate;
            this.qubitIds = qubitIds;
        }

        @Override
        public void execute(QuantumRegister register) {
            register.applyGate(gate, qubitIds);
        }
    }

    private class MeasurementOperation implements Operation {
        private final int qubitId;

        public MeasurementOperation(int qubitId) {
            this.qubitId = qubitId;
        }

        @Override
        public void execute(QuantumRegister register) {
            MeasurementResult measurement = register.measure(qubitId);
            measurementResults.add(measurement);
        }
    }

}
