package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;

import java.util.Objects;

/**
 * Clase que representa un mensaje mandado al cliente por el servidor.
 */
public abstract class MensajeDelServidor
        implements Mensaje {

    protected final String origin;
    protected final String destiny;
    protected final TipoMensaje type;

    protected MensajeDelServidor(TipoMensaje type, String origin, String destiny) {
        this.origin = Objects.requireNonNull(origin);
        this.destiny = Objects.requireNonNull(destiny);
        this.type = Objects.requireNonNull(type);
    }

    /**
     * @return Tipo de mensaje ({@link TipoMensaje})
     */
    @Override
    public final TipoMensaje type() { return type; }

    /**
     * @return Direccion IP de origen del mensaje
     */
    @Override
    public final String origin() { return origin; }

    /**
     * @return Direccion IP de destino del mensaje
     */
    @Override
    public final String destiny() { return destiny; }

    /**
     * El mensaje es procesado por el {@link Cliente}.
     *
     * @param cliente {@link Cliente} que procesa el mensaje.
     */
    public abstract void getProcessedBy(Cliente cliente);
}
