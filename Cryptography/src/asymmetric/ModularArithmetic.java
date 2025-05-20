package asymmetric;

public class ModularArithmetic {

    // step 1 make sure the first no is larger than second
    // 45 = 10 x q + r
    // 10 = 5 x q + r == if rest is 0 here that means gcd(45,10) = 5
    //gcd(24,9) = 6 than gcd(9,6) = 3 than gcd (3,0) ==> gcd = 3
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int modularInverse(int a, int m){
        //brut force approach so we check all possible values in
        //the range [0, m-1]
        for (int inv =0; inv < m; inv++){
            if((a*inv)%m == 1){
                return inv;
            }
        }

        //there is no modular inverse
        return -1;
    }

    public int[] extendedEuclideanAlgorithm(int a, int b){
        // base-case
        // gcd(0,b)=b and a*x+b*y=b - so x=0 and y=1
        if(a == 0) {
            return new int[] { b, 0, 1 };
        }

        // so we use the Euclidean algorithm for gcd()
        // b%a is always the smaller number - and 'a' is the smaller integer
        // always in this implementation
        int[] values = extendedEuclideanAlgorithm(b % a, a);

        // and we update the parameters for x, y accordingly
        int gcd = values[0];
        int x1 = values[1];
        int y1 = values[2];

        int x = y1 - (b / a) * x1;
        int y = x1;

        return new int[] {gcd, x, y};
    }

}
