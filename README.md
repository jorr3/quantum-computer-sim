# Quantum Computer Simulation Library

This library provides a simulation framework for quantum computing, enabling users to create and manipulate quantum registers, apply quantum gates, perform measurements, and construct quantum circuits.

## Features

- **Quantum Register**: Represents a collection of qubits and their collective state.
- **Quantum Gates**: Implementations of various quantum gates (Hadamard, Pauli-X, Swap, etc.) that can be applied to qubits or sets of qubits within a quantum register.
- **Quantum Circuit**: Allows for the sequential application of quantum gates and measurements, facilitating the construction of quantum algorithms.
- **Measurement**: Supports the measurement of qubits, resulting in the collapse of the qubit state and extraction of classical information.
- **Quantum Algorithms**: Includes implementations of various quantum algorithms.
- **Mathematical Foundations**: Provides essential mathematical constructs such as complex numbers, vectors, and matrices, tailored for quantum computing simulations.

## Getting Started

### Prerequisites

Ensure you have Java installed on your machine. This library is written in Java, targeting JDK version 8 or higher.

### Basic Usage

1. **Creating a Quantum Register**

   Initialize a quantum register with a specified number of qubits.
   ```java
   QuantumRegister register = new QuantumRegister(2); // Creates a quantum register with 2 qubits.
   ```
2. **Applying Quantum Gates**

   Apply quantum gates to qubits within the register.
   ```java
   register.applyGate(new HadamardGate(), 0); // Applies the Hadamard gate to the first qubit.
   register.applyGate(new PauliXGate(), 1); // Applies the Pauli-X gate to the second qubit.
   ```

3. **Measurement**

   Measure the state of a qubit.
   ```java
   MeasurementResult result = register.measure(0); // Measures the first qubit.
   System.out.println("Measurement Result: " + result.value());
   ```

4. **Building and Executing a Quantum Circuit**

   Construct a quantum circuit and execute a sequence of gates and measurements.
   ```java
   QuantumCircuit circuit = new QuantumCircuit(register);
   circuit.addGate(new HadamardGate(), 0);
   circuit.addMeasurement(0);
   circuit.execute();
   ```

5. **Running a Quantum Algorithm**

   Execute the Deutsch-Josza algorithm.
   ```java
   QuantumRegister reg = new QuantumRegister(3); // Adjust register size according to your oracle
   IQuantumGate oracle = /* your oracle implementation here */;
   boolean isConstant = DeutschJosza.run(reg, oracle);
   System.out.println("Oracle is " + (isConstant ? "constant" : "balanced"));
   ```

## Acknowledgments

This library is a simplified simulation and does not represent the full complexity and capabilities of an actual quantum computer. It serves as an educational tool to understand quantum computing concepts.
