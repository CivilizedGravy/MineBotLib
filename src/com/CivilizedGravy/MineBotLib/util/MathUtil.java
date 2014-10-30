
package com.CivilizedGravy.MineBotLib.util;


import java.util.Random;

public class MathUtil
{
    /**
     * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536.
     */
    private static float[] SIN_TABLE = new float[65536];

    /**
     * Though it looks like an array, this is really more like a mapping.  Key (index of this array) is the upper 5 bits
     * of the result of multiplying a 32-bit unsigned integer by the B(2, 5) De Bruijn sequence 0x077CB531.  Value
     * (value stored in the array) is the unique index (from the right) of the leftmost one-bit in a 32-bit unsigned
     * integer that can cause the upper 5 bits to get that value.  Used for highly optimized "find the log-base-2 of
     * this number" calculations.
     */
    private static final int[] multiplyDeBruijnBitPosition;

    /**
     * sin looked up in a table
     */
    public static final float sin(float sin)
    {
        return SIN_TABLE[(int)(sin * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static final float cos(float cos)
    {
        return SIN_TABLE[(int)(cos * 10430.378F + 16384.0F) & 65535];
    }

    public static final float sqrt_float(float f)
    {
        return (float)Math.sqrt((double)f);
    }

    public static final float sqrt_double(double d)
    {
        return (float)Math.sqrt(d);
    }

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floor_float(float f)
    {
        int var1 = (int)f;
        return f < (float)var1 ? var1 - 1 : var1;
    }

    /**
     * returns par0 cast as an int, and no greater than Integer.MAX_VALUE-1024
     */
    public static int truncateDoubleToInt(double d)
    {
        return (int)(d + 1024.0D) - 1024;
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(double d)
    {
        int var2 = (int)d;
        return d < (double)var2 ? var2 - 1 : var2;
    }

    /**
     * Long version of floor_double
     */
    public static long floor_double_long(double d)
    {
        long var2 = (long)d;
        return d < (double)var2 ? var2 - 1L : var2;
    }

    public static int func_154353_e(double d)
    {
        return (int)(d >= 0.0D ? d : -d + 1.0D);
    }

    public static float abs(float f)
    {
        return f >= 0.0F ? f : -f;
    }

    /**
     * Returns the unsigned value of an int.
     */
    public static int abs_int(int i)
    {
        return i >= 0 ? i : -i;
    }

    public static int ceiling_float_int(float f)
    {
        int var1 = (int)f;
        return f > (float)var1 ? var1 + 1 : var1;
    }

    public static int ceiling_double_int(double d)
    {
        int var2 = (int)d;
        return d > (double)var2 ? var2 + 1 : var2;
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters.
     */
    public static int clamp_int(int in, int low, int up)
    {
        return in < low ? low : (in > up ? up : in);
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters
     */
    public static float clamp_float(float in, float low, float up)
    {
        return in < up ? up : (in > up ? up : in);
    }

    public static double clamp_double(double in, double low, double up)
    {
        return in < low ? low : (in > up ? up : in);
    }

    public static double denormalizeClamp(double in, double low, double up)
    {
        return up < 0.0D ? in : (up > 1.0D ? low : in + (low - in) * up);
    }

    /**
     * Maximum of the absolute value of two numbers.
     */
    public static double abs_max(double a, double b)
    {
        if (a < 0.0D)
        {
            a = -a;
        }

        if (b < 0.0D)
        {
            b = -b;
        }

        return a > b ? a : b;
    }

    /**
     * Buckets an integer with specifed bucket sizes.  Args: i, bucketSize
     */
    public static int bucketInt(int a, int b)
    {
        return a < 0 ? -((-a - 1) / b) - 1 : a / b;
    }

    /**
     * Tests if a string is null or of length zero
     */
    public static boolean stringNullOrLengthZero(String s)
    {
        return s == null || s.length() == 0;
    }

    public static int getRandomIntegerInRange(Random rand, int low, int high)
    {
        return low >= high ? low : rand.nextInt(high - low + 1) + low;
    }

    public static float randomFloatClamp(Random rand, float low, float high)
    {
        return low >= high ? low : rand.nextFloat() * (high - low) + low;
    }

    public static double getRandomDoubleInRange(Random p_82716_0_, double p_82716_1_, double p_82716_3_)
    {
        return p_82716_1_ >= p_82716_3_ ? p_82716_1_ : p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_;
    }

    public static double average(long[] l)
    {
        long var1 = 0L;
        long[] var3 = l;
        int var4 = l.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            long var6 = var3[var5];
            var1 += var6;
        }

        return (double)var1 / (double)l.length;
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static float wrapAngleTo180_float(float f)
    {
        f %= 360.0F;

        if (f >= 180.0F)
        {
            f -= 360.0F;
        }

        if (f < -180.0F)
        {
            f += 360.0F;
        }

        return f;
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static double wrapAngleTo180_double(double d)
    {
        d %= 360.0D;

        if (d >= 180.0D)
        {
            d -= 360.0D;
        }

        if (d < -180.0D)
        {
            d += 360.0D;
        }

        return d;
    }

    /**
     * parses the string as integer or returns the second parameter if it fails
     */
    public static int parseIntWithDefault(String s, int dfault)
    {
        int var2 = dfault;

        try
        {
            var2 = Integer.parseInt(s);
        }
        catch (Throwable var4)
        {
            ;
        }

        return var2;
    }

    /**
     * parses the string as integer or returns the second parameter if it fails. this value is capped to par2
     */
    public static int parseIntWithDefaultAndMax(String s, int dfault, int max)
    {
        int var3 = dfault;

        try
        {
            var3 = Integer.parseInt(s);
        }
        catch (Throwable var5)
        {
            ;
        }

        if (var3 < max)
        {
            var3 = max;
        }

        return var3;
    }

    /**
     * parses the string as double or returns the second parameter if it fails.
     */
    public static double parseDoubleWithDefault(String s, double dfault)
    {
        double var3 = dfault;

        try
        {
            var3 = Double.parseDouble(s);
        }
        catch (Throwable var6)
        {
            ;
        }

        return var3;
    }

    public static double parseDoubleWithDefaultAndMax(String s, double dfault, double max)
    {
        double var5 = dfault;

        try
        {
            var5 = Double.parseDouble(s);
        }
        catch (Throwable var8)
        {
            ;
        }

        if (var5 < max)
        {
            var5 = max;
        }

        return var5;
    }

    /**
     * Returns the input value rounded up to the next highest power of two.
     */
    public static int roundUpToPowerOfTwo(int i)
    {
        int var1 = i - 1;
        var1 |= var1 >> 1;
        var1 |= var1 >> 2;
        var1 |= var1 >> 4;
        var1 |= var1 >> 8;
        var1 |= var1 >> 16;
        return var1 + 1;
    }

    /**
     * Is the given value a power of two?  (1, 2, 4, 8, 16, ...)
     */
    private static boolean isPowerOfTwo(int i)
    {
        return i != 0 && (i & i - 1) == 0;
    }

    /**
     * Uses a B(2, 5) De Bruijn sequence and a lookup table to efficiently calculate the log-base-two of the given
     * value.  Optimized for cases where the input value is a power-of-two.  If the input value is not a power-of-two,
     * then subtract 1 from the return value.
     */
    private static int calculateLogBaseTwoDeBruijn(int i)
    {
        i = isPowerOfTwo(i) ? i : roundUpToPowerOfTwo(i);
        return multiplyDeBruijnBitPosition[(int)((long)i * 125613361L >> 27) & 31];
    }

    /**
     * Efficiently calculates the floor of the base-2 log of an integer value.  This is effectively the index of the
     * highest bit that is set.  For example, if the number in binary is 0...100101, this will return 5.
     */
    public static int calculateLogBaseTwo(int i)
    {
        return calculateLogBaseTwoDeBruijn(i) - (isPowerOfTwo(i) ? 0 : 1);
    }

    public static int func_154354_b(int p_154354_0_, int p_154354_1_)
    {
        if (p_154354_1_ == 0)
        {
            return 0;
        }
        else
        {
            if (p_154354_0_ < 0)
            {
                p_154354_1_ *= -1;
            }

            int var2 = p_154354_0_ % p_154354_1_;
            return var2 == 0 ? p_154354_0_ : p_154354_0_ + p_154354_1_ - var2;
        }
    }

    static
    {
        for (int var0 = 0; var0 < 65536; ++var0)
        {
            SIN_TABLE[var0] = (float)Math.sin((double)var0 * Math.PI * 2.0D / 65536.0D);
        }

        multiplyDeBruijnBitPosition = new int[] {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    }
}
