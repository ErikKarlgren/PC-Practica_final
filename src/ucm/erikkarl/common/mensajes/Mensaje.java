package ucm.erikkarl.common.mensajes;

import java.io.Serializable;

/**
 * Mensaje entre un cliente y un servidor o entre dos clientes.
 */
public interface Mensaje
        extends Serializable {
    /**
     * @return Tipo de mensaje ({@link TipoMensaje})
     */
    TipoMensaje type();

    /**
     * @return Direccion IP de origen del mensaje
     */
    String origin();

    /**
     * @return Direccion IP de destino del mensaje
     */
    String destiny();

}
