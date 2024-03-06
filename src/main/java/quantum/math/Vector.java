package quantum.math;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class Vector implements Iterable<ComplexNumber> {
    private final ComplexNumber[] elements;

    public Vector(ComplexNumber[] elements) {
        this.elements = elements.clone();
    }

    public ComplexNumber[] getElements() {
        return elements.clone();
    }

    public ComplexNumber get(int index) {
        return elements[index];
    }

    public int size() {
        return elements.length;
    }

    private Vector scalarOperation(ComplexNumber scalar, BiFunction<ComplexNumber, ComplexNumber, ComplexNumber> operation) {
        ComplexNumber[] newValues = new ComplexNumber[size()];
        for (int i = 0; i < size(); i++) {
            newValues[i] = operation.apply(elements[i], scalar);
        }
        return new Vector(newValues);
    }

    private Vector elementWiseOperation(Vector other, BiFunction<ComplexNumber, ComplexNumber, ComplexNumber> operation) {
        validateMatchingSize(other);
        ComplexNumber[] newValues = new ComplexNumber[size()];
        for (int i = 0; i < size(); i++) {
            newValues[i] = operation.apply(elements[i], other.get(i));
        }
        return new Vector(newValues);
    }

    public Vector add(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::add);
    }

    public Vector add(Vector other) {
        return elementWiseOperation(other, ComplexNumber::add);
    }

    public Vector subtract(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::subtract);
    }

    public Vector subtract(Vector other) {
        return elementWiseOperation(other, ComplexNumber::subtract);
    }

    public Vector multiply(ComplexNumber scalar) {
        return scalarOperation(scalar, ComplexNumber::multiply);
    }

    public Vector multiply(Vector other) {
        return elementWiseOperation(other, ComplexNumber::multiply);
    }

    public ComplexNumber dotProduct(Vector other) {
        validateMatchingSize(other);
        ComplexNumber dotProduct = new ComplexNumber(0, 0);
        for (int i = 0; i < size(); i++) {
            dotProduct = dotProduct.add(elements[i].multiply(other.get(i)));
        }
        return dotProduct;
    }

    public Vector tensorProduct(Vector other) {
        ComplexNumber[] newValues = new ComplexNumber[size() * other.size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < other.size(); j++) {
                newValues[i * other.size() + j] = elements[i].multiply(other.get(j));
            }
        }
        return new Vector(newValues);
    }

    public double magnitude() {
        double sum = 0.0;
        for (ComplexNumber element : elements) {
            sum += element.magnitude() * element.magnitude();
        }
        return Math.sqrt(sum);
    }

    public Vector normalize() {
        double norm = 0;
        for (ComplexNumber amplitude : elements) {
            norm += amplitude.magnitudeSquared();
        }
        norm = Math.sqrt(norm);

        ComplexNumber[] normalizedAmplitudes = new ComplexNumber[size()];

        for (int i = 0; i < size(); i++) {
            ComplexNumber amplitude = elements[i];
            normalizedAmplitudes[i] = amplitude.divide(norm);
        }

        return new Vector(normalizedAmplitudes);
    }

    private void validateMatchingSize(Vector other) {
        if (size() != other.size()) {
            throw new IllegalArgumentException("Vector sizes do not match: " + size() + " and " + other.size());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i < elements.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean equals(Vector other) {
        validateMatchingSize(other);

        for (int i = 0; i < size(); i++) {
            if (!elements[i].equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }


    @Override
    public Iterator<ComplexNumber> iterator() {
        return new VectorIterator();
    }

    private class VectorIterator implements Iterator<ComplexNumber> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < elements.length;
        }

        @Override
        public ComplexNumber next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[currentIndex++];
        }
    }
}
