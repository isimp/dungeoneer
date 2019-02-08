package de.dungeoneer.helper;

/**
 * Loglevel 0 Critical
 * Loglevel 1 Debug
 * Loglevel 2 Info
 * Loglevel 3 DetailledInfo
 */
public class LogHelper {
    private static int loglevel = 3;

    public static boolean logCritical(String string) {
        if(loglevel >= 0) {
            System.out.println("[CRITICAL] " + string);
            return true;
        }

        return false;
    }

    public static boolean logDebug(String string) {
        if(loglevel >= 1) {
            System.out.println("[DEBUG] " + string);
            return true;
        }

        return false;
    }

    public static boolean logInfo(String string) {
        if(loglevel >= 2) {
            System.out.println("[INFO] " + string);
            return true;
        }

        return false;
    }

    public static boolean logDetailledInfo(String string) {
        if(loglevel >= 3) {
            System.out.println("[DINFO] " + string);
            return true;
        }

        return false;
    }

    public static int getLoglevel() { return loglevel; }

    public static void setLoglevel(int loglevel) { LogHelper.loglevel = loglevel; }
}
