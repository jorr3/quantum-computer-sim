package quantum.core;

public record MeasurementResult(int qubitId, int result) {
    @Override
    public String toString() {
        return "MeasurementResult{" +
                "qubitId=" + qubitId +
                ", result=" + result +
                '}';
    }
}
