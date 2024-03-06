package utils;

public class Utils {
    public static int reverseBits(int n, int numBits) {
        int reversed = 0;
        for (int i = 0; i < numBits; i++) {
            reversed = (reversed << 1) | (n & 1);
            n >>= 1;
        }
        return reversed;
    }
}
