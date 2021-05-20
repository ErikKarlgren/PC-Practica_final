package ucm.erikkarl.common.server;

import ucm.erikkarl.common.mensajes.delservidor.MensajeDelServidor;

import java.net.InetAddress;

/**
 * Interfaz para iniciar y cerrar sesion en el servidor e interactuar con este.
 */
public interface SesionServidor {
    /**
     * Devuelve la direccion IP del servidor.
     *
     * @return {@link InetAddress} con direccion IP del servidor.
     */
    String ip();

    /**
     * Devuelve el puerto usado por el servidor
     *
     * @return Puerto usado por el servidor.
     */
    int port();

    /**
     * Devuelve una estructura de datos {@link DatosUsuarios} con los
     * datos de los usuarios registrados en el servidor y sus ficheros
     *
     * @return {@link DatosUsuarios} con los datos de los usuarios.
     */
    DatosUsuarios datosUsuarios();

    /**
     * Inicia sesion en el servidor. Si no existia un usuario con los
     * datos del {@link Usuario} pasado como parametro, lo registra y le
     * asocia una direccion IP.
     *
     * @param usuario {@link Usuario} con el que iniciar sesion.
     * @return {@code true} si se inicia sesion con exito, {@code false} en caso contrario.
     */
    boolean iniciarSesion(Usuario usuario);

    /**
     * Cierra la sesion del usuario actual.
     *
     * @return {@code true} si se cierra sesion con exito. Si no, {@code false}.
     */
    boolean cerrarSesion();

    /**
     * Cierra la conexion con el cliente. Despues de esta operacion no se podra interactuar con
     * el servidor de nuevo sin crear una nueva conexion.
     */
    void cerrarConexion();

    /**
     * @return Indica si la conexion sigue abierta ({@code true}) o no.
     */
    boolean conexionAbierta();

    /**
     * @return Devuelve el {@link Usuario} que inicio sesion con esta instancia de
     * {@link SesionServidor}.
     */
    Usuario usuarioActual();

    /**
     * Envia un {@link MensajeDelServidor} a un {@link Usuario} dado su
     * nombre de usuario.
     *
     * @param msg      Mensaje para mandarle al cliente
     * @param username Nombre de usuario del cliente destinatario.
     */
    void mandarMensajeACliente(MensajeDelServidor msg, String username) throws UserOfflineOrDoesNotExistException;

    /**
     * Envia un {@link MensajeDelServidor} al usuario con el que hemos
     * iniciado sesion con esta instancia de {@link SesionServidor}.
     *
     * @param msg Mensaje que mandarle al usuario actual.
     */
    void mandarMensajeACliente(MensajeDelServidor msg);
}
