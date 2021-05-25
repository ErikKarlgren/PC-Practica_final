package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfirmarActualizarListaFicheros
        extends MensajeDelServidor
{
    private static final Logger LOGGER = SocketReadyLogger.create(ConfirmarActualizarListaFicheros.class.getName());
    private final String nuevoFichero;
    private final boolean exito;

    public ConfirmarActualizarListaFicheros(String nuevoFichero, boolean exito, String origin, String destiny) {
        super(TipoMensaje.CONFIRMAR_ACTUALIZAR_LISTA_FICHEROS, origin, destiny);
        this.nuevoFichero = nuevoFichero;
        this.exito = exito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        var texto = exito ? "succesful" : "unsuccesful";
        var textoFinal = "File update of " + nuevoFichero + " in server was " + texto;
        //cliente.mostrarTextoPorConsola(textoFinal);
        LOGGER.log(exito ? Level.INFO : Level.SEVERE, textoFinal);
    }
}
