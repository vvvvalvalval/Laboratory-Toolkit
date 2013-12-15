/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.primes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class Eratosthene {

    private List<Integer> knownPrimeNumbers;
    private int sieveSize;

    private Eratosthene() {
        this.sieveSize = 4;
        this.knownPrimeNumbers = new ArrayList<Integer>(2);
        knownPrimeNumbers.add(2);
        knownPrimeNumbers.add(3);
    }

    /**
     * Returns a finite list of prime numbers that contains all the prime
     * dividors of the specified integer (but not only them).
     *
     * @param n a positive integer
     * @return a list of possible prime dividors of the parameterized integer.
     */
    public synchronized List<Integer> potentialPrimeDividors(int n) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; this.nthPrimeNumber(i) <= n; i++) {
            res.add(this.nthPrimeNumber(i));
        }
        return res;
    }

    /**
     * The n-th prime number (starting from 0);
     *
     * @param index
     * @return the n-th prime number
     */
    public synchronized int nthPrimeNumber(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Negative index : " + index);
        }

        while (this.greatestKnownPrimeNumberIndex() < index) {
            this.expand();

        }
        return this.knownPrimeNumbers.get(index);
    }

    private int greatestKnownPrimeNumberIndex() {
        return this.knownPrimeNumbers.size() - 1;
    }

    private void expand() {
        this.expand(sieveSize << 1);
    }

    private void expand(int newSieveSize) {
        this.knownPrimeNumbers = new SieveRunner(newSieveSize).findPrimes();
        this.sieveSize = newSieveSize;
    }

    public static Eratosthene getInstance() {
        return EratostheneHolder.INSTANCE;
    }

    private static class EratostheneHolder {

        private static final Eratosthene INSTANCE = new Eratosthene();
    }

    private static class SieveRunner {

        final int size;
        boolean[] sieve;
        int currentIndex = 1;
        int remainingCells;
        final List<Integer> primes = new ArrayList<>();

        public SieveRunner(int size) {
            this.sieve = new boolean[size];
            this.size = size;
            remainingCells = size - 2;
        }

        public List<Integer> findPrimes() {
            while (!isFinished()) {
                this.moveToNextPrime();
                this.removeMultiples();
            }
            return primes;
        }

        private void moveToNextPrime() {
            while (true) {
                currentIndex++;
                if (sieve[currentIndex] == false) {
                    primes.add(currentIndex);
                    sieve[currentIndex] = true;
                    remainingCells--;
                    break;
                }
            }
        }

        private boolean isFinished() {
            return remainingCells == 0;
        }

        private void removeMultiples() {
            for (int i = currentIndex; i < size; i += currentIndex) {
                if (sieve[i] == false) {
                    remainingCells--;
                    sieve[i] = true;
                }
            }
        }
    }
}
