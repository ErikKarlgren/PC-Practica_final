package ucm.erikkarl.common.mensajes;

import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;

/**
 * Tipo de mensaje. Se puede usar para diferenciar entre distintos {@link MensajeDelCliente} sin necesidad
 * de usar {@link MensajeDelCliente#getClass()}.
 */
public enum TipoMensaje {
    PETICION_INICIO_SESION, CONFIRMAR_INICIO_SESION, PETICION_CIERRE_SESION, CONFIRMAR_CIERRE_SESION, PETICION_FICHERO, PETICION_LISTA_USUARIOS, MANDAR_LISTA_USUARIOS, PETICION_ACTUALIZAR_LISTA_FICHEROS, CONFIRMAR_ACTUALIZAR_LISTA_FICHEROS;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace('_', ' ');
    }
}
