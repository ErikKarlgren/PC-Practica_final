package ucm.erikkarl.common;

public class ServerInformation {
    private static final int DEFAULT_PORT = 9991;
    private String address = "127.0.0.1";
    private int port = DEFAULT_PORT;
    private boolean isAddressSet = false;
    private boolean isPortSet = false;
    public ServerInformation() {/* Do nothing */}

    public static int getDefaultPort() { return DEFAULT_PORT; }

    public String getAddress() { return address; }

    public void setAddress(String newAddress) {
        if (!isAddressSet)
        {
            address = newAddress;
            isAddressSet = true;
        }
        else
            throw new IllegalStateException("Server address has already been set");
    }

    public int getPort() { return port; }

    public void setPort(int newPort) {
        if (!isPortSet)
        {
            port = newPort;
            isPortSet = true;
        }
        else
            throw new IllegalStateException("Server port has already been set");
    }

    public void makeDataFinal() {
        isPortSet = true;
        isAddressSet = true;
    }
}
