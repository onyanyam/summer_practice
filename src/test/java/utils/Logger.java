package utils;

public final class Logger {

    private static final String INFO_PREFIX = "[INFO] ";

    private static final String SEPARATOR =
            "========================================";

    private Logger() {}

    public static void info(String message) {
        System.out.println(INFO_PREFIX + message);
    }

    public static void testStart(String testName) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("Запуск теста: " + testName);
        System.out.println();
    }

    public static void testEnd(String testName) {
        System.out.println();
        System.out.println("Конец теста: " + testName);
        System.out.println(SEPARATOR);
        System.out.println();
    }
}
