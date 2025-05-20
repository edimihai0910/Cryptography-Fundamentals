package asymmetric;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {

    private Random random;

    public DiffieHellman() {
        random = new Random();
    }

    //we want to make sure that both Alice And Bob
    //wiull use the same privatre Key AES
    public void generatePrivateKeys(BigInteger n, BigInteger g) {
        //THESE VARIABLES ARE PRIVATE

        //randopm number for Alice where x<n-1
        int rand1 = random.nextInt(n.intValue()-2)+1;
        BigInteger x = new BigInteger(Integer.toString(rand1));

        //random number for Bob
        int rand2 = random.nextInt(n.intValue()-2)+1;
        BigInteger y = new BigInteger(Integer.toString(rand2));

        //THESE VARIABLES ARE PUBLIC
        //calculate g^x mod n which is Alice's k1
        BigInteger k1 = g.modPow(x, n);

        //calculate g^y mod n which is Bob's k2
        BigInteger k2 = g.modPow(y, n);

        //they can calculate the same private key !!
        BigInteger key1 = k2.modPow(x,n);
        BigInteger key2 = k1.modPow(y,n);


        System.out.println(String.format("Generating private keys from %d to %d", rand1, rand2));
        System.out.println(String.format("Generated keys key1: %s key2: %s", key1, key2));

    }
}
