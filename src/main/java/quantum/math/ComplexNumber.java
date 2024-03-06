package quantum.math;


import java.util.Objects;

public final class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(double real) {
        this.real = real;
        this.imaginary = 0.0;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(
                real + other.getReal(),
                imaginary + other.getImaginary()
        );
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(
                real - other.getReal(),
                imaginary - other.getImaginary()
        );
    }
    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(
                real * other.getReal() - imaginary * other.getImaginary(),
                real * other.getImaginary() + imaginary * other.getReal()
        );
    }

    public ComplexNumber divide(double scalar) {
        return new ComplexNumber(
                real / scalar,
                imaginary / scalar
        );
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imaginary);
    }

    public double magnitudeSquared() {
        return Math.pow(real, 2) + Math.pow(imaginary, 2);
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double phase() {
        return Math.atan2(imaginary, real);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ComplexNumber other = (ComplexNumber) obj;

        return areEqual(real, other.real) && areEqual(imaginary, other.imaginary);
    }

    private boolean areEqual(double a, double b) {
        final double EPSILON = 1E-10;
        return Math.abs(a - b) < EPSILON;
    }

    public boolean isZero() {
        return (real == 0 && imaginary == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public String toString() {
       return String.format("(% 4.2f %s %.2fi)", real, imaginary >= 0 ? "+" : "-", Math.abs(imaginary));
    }
}
