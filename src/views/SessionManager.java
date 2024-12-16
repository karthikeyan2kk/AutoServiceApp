package views;

public class SessionManager {
    private static String mechanicName;

    public static String getMechanicName() {
        return mechanicName;
    }

    public static void setMechanicName(String name) {
        mechanicName = name;
    }
}
