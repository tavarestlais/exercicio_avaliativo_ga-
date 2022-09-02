public class RNG{
    public static int randomNumberGenerator(int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }
}