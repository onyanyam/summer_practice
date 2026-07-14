package utils;

/**
 * Класс со вспомогательными статическими методами общего назначения,
 * используемыми в тестах (например, парсинг длительности видео).
 */
public class Utils {

    private Utils() {}

    /**
     * Парсит строку с длительностью видеоролика в секунды.
     * Поддерживает форматы "MM:SS" и "HH:MM:SS".
     */
    public static int parseDuration(String duration) {
        String[] parts = duration.split(":");

        if (parts.length == 3) {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int s = Integer.parseInt(parts[2]);

            return h * 3600 + m * 60 + s;
        }

        int m = Integer.parseInt(parts[0]);
        int s = Integer.parseInt(parts[1]);

        return m * 60 + s;
    }

}