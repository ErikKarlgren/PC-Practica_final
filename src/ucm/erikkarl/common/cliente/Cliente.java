package ucm.erikkarl.common.cliente;

import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;
import ucm.erikkarl.common.server.Usuario;

import java.net.InetAddress;
import java.util.TreeSet;

/**
 * Available operations to be executed in the client's machine.
 */
public interface Cliente {
    /**
     * @return Identificador unico del usuario.
     */
    String uid();

    /**
     * @return {@link InetAddress} con la direccion IP del usuario.
     */
    InetAddress ip();

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
     * Muestra por consola los usuarios registrados en el servidor,
     * los ficheros que tienen disponibles y su estado de conexion.
     *
     * @param datos Mapa que relaciona cada usuario y sus datos con
     *              su estado de conexion.
     */
    void mostrarDatosUsuarios(TreeSet<Usuario> datos);
}
