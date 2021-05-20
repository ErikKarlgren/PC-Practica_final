package ucm.erikkarl.common.server;

public class UserOfflineOrDoesNotExistException
        extends Exception {

    public UserOfflineOrDoesNotExistException(String s) {
        super(s);
    }

    public UserOfflineOrDoesNotExistException() {

    }
}
