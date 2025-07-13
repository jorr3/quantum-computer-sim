package utils;

import quantum.math.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateParser {
    public static String parseState(Vector state, int numQubits) {
        StringBuilder result = new StringBuilder();
        ComplexNumber[] elements = state.getElements();

        for (int i = 0; i < elements.length; i++) {
            ComplexNumber cn = elements[i];

            if (!cn.isZero()) {
                String complexPart = formatComplexNumber(cn);
                String binaryString = String.format("%" + numQubits + "s", Integer.toBinaryString(i)).replace(' ', '0');

                result.append(complexPart).append("|").append(binaryString).append("⟩ ");
            }
        }

        return !result.isEmpty() ? result.toString().trim() : state.toString();
    }

    private static String formatComplexNumber(ComplexNumber cn) {
        String realPart = cn.getReal() == 0 ? "" : formatDouble(cn.getReal());
        String imaginaryPart = cn.getImaginary() == 0 ? "" : formatDouble(cn.getImaginary()) + "i";

        if (!realPart.isEmpty() && imaginaryPart.startsWith("-")) {
            imaginaryPart = " - " + imaginaryPart.substring(1);
        } else if (!realPart.isEmpty() && !imaginaryPart.isEmpty()) {
            imaginaryPart = " + " + imaginaryPart;
        }

        return realPart + imaginaryPart;
    }

    private static String formatDouble(double value) {
        if (Math.abs(value) == 1) {
            return value < 0 ? "-" : "";
        } else {
            return String.format("%.2f", value).replaceAll("([.][0-9]*[1-9])0+$|^[.]", "$1");
        }
    }

    public static Vector stateFromStr(String stateStr) {
        // Pattern to match complex numbers in the form 'i' or '-i', or a leading '-' sign, followed by binary state
        Pattern pattern = Pattern.compile("(-)?([+-]?i)?(\\d+)");
        Matcher matcher = pattern.matcher(stateStr.replace("|", "").replace("⟩", ""));

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid state string format.");
        }

        String negativeSign = matcher.group(1);
        String imaginaryPartStr = matcher.group(2);
        String binaryStr = matcher.group(3);

        int numQubits = binaryStr.length();
        int numStates = (int) Math.pow(2, numQubits);
        ComplexNumber[] stateArray = new ComplexNumber[numStates];
        Arrays.fill(stateArray, new ComplexNumber(0, 0));

        int index = Integer.parseInt(binaryStr, 2);
        double realPart = 0.0;
        double imaginaryPart = 0.0;

        if (imaginaryPartStr != null) {
            imaginaryPart = imaginaryPartStr.equals("-i") ? -1.0 : 1.0;
        } else {
            realPart = 1.0;
        }

        // Apply the negative sign if present
        if (negativeSign != null) {
            realPart *= -1;
            imaginaryPart *= -1;
        }

        stateArray[index] = new ComplexNumber(realPart, imaginaryPart);

        return new Vector(stateArray);
    }
}
