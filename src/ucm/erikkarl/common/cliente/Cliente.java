package ucm.erikkarl.common.cliente;

import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Available operations to be executed in the client's machine.
 */
public interface Cliente
{
    /**
     * @return Identificador unico del usuario.
     */
    String uid();

    /**
     * @return {@link InetAddress} con la direccion IP del usuario.
     */
    String ip();

    /**
     * @return {@link InetAddress} con la direccion IP del servidor
     */
    String serverIP();

    /**
     * Añade un {@link MensajeDelCliente} a una cola de espera para
     * que acabe siendo enviado al servidor.
     *
     * @param msg {@link MensajeDelCliente} para mandar.
     */
    void mandarMensajeAServidor(MensajeDelCliente msg);

    /**
     * Hace saber al cliente si se ha iniciado sesion con exito.
     */
    void confirmarInicioSesion(boolean confirmado);

    /**
     * Hace saber al cliente si se ha cerrado sesion con exito.
     */
    void confirmarCierreSesion(boolean confirmado);

    /**
     * Manda un string para ser mostrado por consola.
     *
     * @param texto String que mostrar por consola.
     */
    void mostrarTextoPorConsola(String texto);

    /**
     * Manda un string para ser mostrado por consola.
     *
     * @param texto     String que mostrar por consola.
     * @param wakeUpCLI Si es {@code true}, despierta al hilo de la consola.
     *                  Si es {@code false} imprime el texto directamente.
     */
    void mostrarTextoPorConsola(String texto, boolean wakeUpCLI);

    /**
     * @return {@code true} si el cliente esta conectado al servidor,
     * {@code false} en caso contrario.
     */
    boolean isConnectedToServer();

    /**
     * Establece el nombre de usuario del cliente.
     *
     * @param username Nombre de usuario del cliente
     */
    void setUsername(String username);

    /**
     * Pide al cliente que se prepare para mandar un fichero.
     *
     * @param nombreFichero Nombre del fichero
     */
    void requestUpload(String nombreFichero) throws IOException;

    /**
     * Pide al cliente que se prepare para descargar un fichero de otro cliente
     *
     * @param remoteIP   ip del cliente con el fichero
     * @param remotePort puerto usado por el cliente con el fichero
     */
    void requestDownload(String remoteIP, int remotePort) throws IOException;

    /**
     * @return Port used by the client's local uploader server.
     */
    int getUploadServerPort();
}
