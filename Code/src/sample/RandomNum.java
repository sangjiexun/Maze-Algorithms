package sample;

import java.util.Random;

/**
 * This class is created just to return a pseudo-randomly generated number in a chosen range (min, max)
 * I created this class because i couldn't find any documentation on how to do it otherwise
 */
public class RandomNum {

    private static Random rnd = new Random();

    /**
     * The method that returns the random number
     *
     * Mind you it's a static method, because I will need to call it from various classes and I don't want a
     * 100 imports and variables in every class anyway.
     *
     * @param min The minimum range
     * @param max The maximum range
     * @return Int. Returns a 'random' number between the given range.
     */
    public static int Next(int min, int max)
    {
        return rnd.nextInt(max - min) + min;
    }
}
