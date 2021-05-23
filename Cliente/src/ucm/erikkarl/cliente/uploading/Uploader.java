package ucm.erikkarl.cliente.uploading;

import ucm.erikkarl.cliente.LocalFilesManager;
import ucm.erikkarl.common.Oyente;
import ucm.erikkarl.common.cliente.Fichero;
import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class Uploader
        extends Oyente {

    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(Uploader.class.getName());

    private final String nombreFichero;

    protected Uploader(Socket socket, String nombreFichero) {
        super(socket);
        this.nombreFichero = nombreFichero;
    }

    @Override
    protected void runUsingSocket(ObjectInputStream in, ObjectOutputStream out)
    {
        try
        {
            var fichero = readFile();
            out.writeObject(fichero);
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error while uploading", e);
        }
    }

    private Fichero readFile() throws IOException {
        var path = LocalFilesManager.FILES_FOLDER_NAME + '/' + nombreFichero;
        String contents = Files.readString(Path.of(path));
        return new Fichero(nombreFichero, contents);
    }
}
