package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.server.SesionServidor;

import java.util.Objects;

/**
 * Clase que representa un mensaje mandado al servidor por el cliente.
 */
public abstract class MensajeDelCliente
        implements Mensaje {

    protected final String origin;
    protected final String destiny;
    private final TipoMensaje type;

    protected MensajeDelCliente(TipoMensaje type, String origin, String destiny) {
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
     * El mensaje es procesado por el {@link SesionServidor}.
     *
     * @param sesionServidor {@link SesionServidor} que procesa el mensaje
     */
    public abstract void getProcessedBy(SesionServidor sesionServidor);
}
