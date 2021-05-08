package ucm.erikkarl.common;

public class ServerInformation {
    private ServerInformation() {/* Utility class */}

    private static String address = "127.0.0.1";
    private static int port = 9991;

    private static boolean isAddressSet = false;
    private static boolean isPortSet = false;

    public static String getAddress() { return address; }

    public static void setAddress(String newAddress) {
        if (!isAddressSet)
        {
            address = newAddress;
            isAddressSet = true;
        }
        else
            throw new IllegalStateException("Server address has already been set");
    }

    public static int getPort() { return port; }

    public static void setPort(int newPort) {
        if (!isPortSet)
        {
            port = newPort;
            isPortSet = true;
        }
        else
            throw new IllegalStateException("Server port has already been set");
    }

    public static void makeDataFinal() {
        isPortSet = true;
        isAddressSet = true;
    }
}
