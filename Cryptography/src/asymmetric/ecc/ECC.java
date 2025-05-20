package asymmetric.ecc;

public class ECC {
    // y^2 = x^3 + ax + b
    // bitcoin  a=0 and b=7
    private double a;
    private double b;

    public ECC(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Point pointAddition(Point p, Point q) {
        double x1 = p.getX();
        double y1 = p.getY();
        double x2 = q.getX();
        double y2 = q.getY();

        //define sloap
        double m = 0;
        //sometimes we have to make point addition and
        //if P=Q then we have to do point doubling

        if (Double.compare(x1, x2) == 0 && Double.compare(y1, y2) == 0) {
            //point doubling P=Q -- using derivatives
            m = (3 * x1 * x1 + a) / (2 * y1);
        } else {
            //point addition
            m = (y2 - y1) / (x2 - x1);
        }

        // we can calculate the point R
        double x3 = m * m - x2 - x1;
        double y3 = m * (x1 - x3) - y1;

        return new Point(x3, y3);
    }

    // Time Complexity - O(n)
    public Point doubleAndAdd(int n, Point p) {
        Point temp = new Point(p.getX(), p.getY());
        // 10 = "1010"
        String nBinary = Integer.toBinaryString(n);

        for (int i = 1; i < nBinary.length(); i++) {
            int actualBit = Integer.parseInt("" + nBinary.charAt(i));

            // point doubling operation == 2P
            temp = pointAddition(temp, temp);

            if (actualBit == 1) {
                temp = pointAddition(temp, p);
            }
        }

        return temp;
    }

}
