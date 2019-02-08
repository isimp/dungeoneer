package de.dungeoneer.helper;

public class DiceHelper {
    private static boolean isRandom;
    private static int value;

    static {
        setIsRandom(true);
        setValue(0);
    }

    /**
     * Simulates a dice roll, outputing a random number between (and including) 1 and sides.
     * @param sides sides of the dice
     * @return random number
     */
    public static int roll(int sides) {
        int result;

        if(isRandom) result = (int) (Math.random() * sides) + 1;
        else result = value;

        return result;
    }

    /**
     * Generates a random number between (and including) 0 and (not including) size.
     * @param size size (eg. of an array)
     * @return random number
     */
    public static int rollId(int size) {
        return (int) (Math.random()* size);
    }

    public static boolean isIsRandom() { return isRandom; }

    public static int isValue() { return value; }

    public static void setIsRandom(boolean isRandom) { DiceHelper.isRandom = isRandom; }

    public static void setValue(int value) { DiceHelper.value = value; }
}