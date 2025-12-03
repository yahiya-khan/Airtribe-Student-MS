package airtribe.studentms.util;

public final class InputValidator {
    private InputValidator() {}

    public static boolean isValidId(String id) {
        return id != null && id.matches("^[A-Za-z0-9-]{3,20}$");
    }
}
