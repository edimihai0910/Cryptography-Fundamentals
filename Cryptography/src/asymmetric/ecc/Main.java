package asymmetric.ecc;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // these are all public: the ECC and the generator point
        ECC ecc = new ECC(3, 7);
        Point generator = new Point(-2, 1);
        System.out.println("Point doubling: " + ecc.pointAddition(generator, generator));

        System.out.println("Double and add: " + ecc.doubleAndAdd(100, generator));

        //Elliptic Curve Diffie-Hellman Algorithm
        //Random number for Alice
        Random random = new Random();
        int a = random.nextInt(10000);
        //Random number for Bob
        int b = random.nextInt(10000);

        //public keys with the double and add algorithm
        //these are points on the elliptic curve
        Point alicePublicKey = ecc.doubleAndAdd(a,generator);
        Point bobPublicKey = ecc.doubleAndAdd(b,generator);

        //they can generate the same private key they can use for symmetric encryption
        Point alicePrivateKey = ecc.doubleAndAdd(a, bobPublicKey);
        Point bobPrivateKey = ecc.doubleAndAdd(b, alicePublicKey);

        System.out.println("Alice Private Key: " + alicePrivateKey);
        System.out.println("Bob Private Key: " + bobPrivateKey);


    }
}
