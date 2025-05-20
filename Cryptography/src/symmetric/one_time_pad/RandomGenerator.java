package symmetric.one_time_pad;

import java.util.Random;

public class RandomGenerator {

    private Random random ;
    public static String ALPHABET = " ABCDEFGHIJLMNOPQRSTUVWXYZ";


    public RandomGenerator(){
        this.random = new Random();
    }

    //generate n pseudo-random numbers
    public int[] generate(int n) {
        int [] randomSequence = new int[n];
        for(int i =0;i<n;i++)
        {
            randomSequence[i]= random.nextInt(0,ALPHABET.length()-1);
        }

        return randomSequence;
    }

}
