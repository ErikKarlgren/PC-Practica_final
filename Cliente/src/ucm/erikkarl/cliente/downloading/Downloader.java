package ucm.erikkarl.cliente.downloading;

import ucm.erikkarl.cliente.LocalFilesManager;
import ucm.erikkarl.common.Oyente;
import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.cliente.Fichero;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delcliente.PeticionActualizarListaFicheros;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

public class Downloader
        extends Oyente
{
    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(Downloader.class.getName());
    private final Cliente cliente;
    private Fichero objFichero;

    protected Downloader(Cliente cliente, Socket socket) {
        super(socket);
        LOGGER.setSocket(socket);
        this.cliente = cliente;
    }

    @Override
    protected void runUsingSocket(ObjectInputStream in, ObjectOutputStream out) throws IOException
    {
        try
        {
            objFichero = (Fichero) in.readObject();
            saveFile();
            cliente.mostrarTextoPorConsola(objFichero.getName() + ": fin de la descarga", false);
            updateServerInfo();
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.log(Level.SEVERE, "Error while downloading", e);
        }
        catch (IOException e)
        {
            throw new IOException("Error while saving file", e);
        }
    }

    private void saveFile() throws IOException {
        var file = new File(LocalFilesManager.FILES_FOLDER_NAME + '/' + objFichero.getName());
        if (!file.createNewFile())
            LOGGER.warning("Might be overwriting existing file");
        try (var writer = new FileWriter(file))
        {
            writer.write(objFichero.getContent());
        }
    }

    private void updateServerInfo() {
        var msg = new PeticionActualizarListaFicheros(cliente.uid(), objFichero.getName(), cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
    }
}
