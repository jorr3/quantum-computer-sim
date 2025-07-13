package quantum.core;

public record MeasurementResult(int qubitId, int value) {
    @Override
    public String toString() {
        return "MeasurementResult{" +
                "qubitId=" + qubitId +
                ", value=" + value +
                '}';
    }
}
