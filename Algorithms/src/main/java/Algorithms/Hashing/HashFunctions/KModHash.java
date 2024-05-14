package Algorithms.Hashing.HashFunctions;

public class KModHash implements HashFunction{
    private final int K;

    KModHash(int k) {
        this.K = k;
    }

    @Override
    public int hash(int value) {
        return value%K;
    }

}
