package ucm.erikkarl.common.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

/**
 * Clase con los siguientes datos de un usuario del sistema: su UID, su direccion IP y los nombres
 * de los ficheros disponibles
 */
public interface Usuario extends Serializable {
    /**
     * @return Identificador unico del usuario.
     */
    String uid();

    /**
     * @return {@link InetAddress} con la direccion IP del usuario.
     */
    InetAddress ip();

    /**
     * @return Una lista con los nombres de los ficheros que tiene disponible un usuario.
     */
    List<String> ficherosDisponibles();
}
