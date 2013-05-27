package physics.math;
 
 
public final class Algebra
{
 
    private Algebra()
    {
    }
 
    public static int[] simplifyFraction(int n, int d)
    {
        int gcd = gcd(n, d);
        return (new int[] {
            n / gcd, d / gcd
        });
    }
 
    public static int gcd(int num1, int num2)
    {
        int rem = num2 % num1;
        if(rem != 0)
            return gcd(rem, num1);
        else
            return num1;
    }
 
    public static Double[] quadraticRoots(double a, double b, double c)
    {
        Double roots[] = new Double[2];
        roots[0] = null;
        roots[1] = null;
        double discriminant = b * b - 4D * a * c;
        if(discriminant >= 0.0D)
        {
            roots[0] = Double.valueOf((-b + Math.sqrt(b * b - 4D * a * c)) / (2D * a));
            if(discriminant > 0.0D)
                roots[1] = Double.valueOf((-b - Math.sqrt(b * b - 4D * a * c)) / (2D * a));
        }
        return roots;
    }
}