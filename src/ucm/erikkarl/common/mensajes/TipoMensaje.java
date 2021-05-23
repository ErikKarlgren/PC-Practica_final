package ucm.erikkarl.common.mensajes;

import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;

/**
 * Tipo de mensaje. Se puede usar para diferenciar entre distintos {@link MensajeDelCliente} sin necesidad
 * de usar {@link MensajeDelCliente#getClass()}.
 */
public enum TipoMensaje {
    PETICION_INICIO_SESION,
    CONFIRMAR_INICIO_SESION,
    PETICION_CIERRE_SESION,
    CONFIRMAR_CIERRE_SESION,
    PETICION_FICHERO_A_SERVIDOR,
    PETICION_FICHERO_A_CLIENTE,
    MANDAR_INFO_CLIENTE_A_SERVIDOR,
    CONFIRMAR_MANDAR_INFO_CLIENTE_A_SERVIDOR,
    MANDAR_INFO_CLIENTE_A_CLIENTE,
    CONFIRMAR_MANDAR_INFO_CLIENTE_A_CLIENTE,
    PETICION_LISTA_USUARIOS,
    MANDAR_LISTA_USUARIOS,
    PETICION_ACTUALIZAR_LISTA_FICHEROS,
    CONFIRMAR_ACTUALIZAR_LISTA_FICHEROS;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace('_', ' ');
    }
}
