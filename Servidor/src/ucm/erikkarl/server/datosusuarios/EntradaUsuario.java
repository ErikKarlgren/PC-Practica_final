package ucm.erikkarl.server.datosusuarios;

import ucm.erikkarl.common.concurrency.ReaderWriterController;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.common.server.Usuario;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Clase que contiene un {@link ucm.erikkarl.common.server.Usuario} y se asegura de que el acceso
 * concurrente al mismo sea seguro.
 */
class EntradaUsuario {

    private static final Logger LOGGER = SocketReadyLogger.create(EntradaUsuario.class.getName());
    private final ReaderWriterController controller;
    private Usuario usuario;
    private SesionServidor sesion;

    public EntradaUsuario(Usuario usuario) {
        this.usuario = Objects.requireNonNull(usuario);
        sesion = null;
        controller = new ReaderWriterController();
    }

    /**
     * @return Una copia del {@link Usuario} de esta {@link EntradaUsuario}.
     */
    public Usuario usuario() {
        controller.requestRead();
        Usuario copia = new Usuario(usuario);
        controller.releaseRead();
        return copia;
    }

    /**
     * Sobrescribe los datos del {@link Usuario} con otros nuevos.
     *
     * @param nuevosDatos Nuevos datos para el usuario.
     * @throws IllegalArgumentException Si el {@link Usuario#uid()} de ambos usuarios no coincide.
     */
    public void sobrescribirUsuario(Usuario nuevosDatos) {
        if (!nuevosDatos.equals(usuario))
            throw new IllegalArgumentException("Not the same user");
        controller.requestWrite();
        usuario = new Usuario(nuevosDatos);
        controller.releaseWrite();
    }

    /**
     * @return Un {@link Optional<SesionServidor>} que contiene la sesion actual del usuario si
     * esta conectado, y un {@link Optional#empty()} si no lo esta.
     */
    public Optional<SesionServidor> sesion() {
        controller.requestRead();
        Optional<SesionServidor> s = (sesion == null) ? Optional.empty() : Optional.of(sesion);
        controller.releaseRead();
        return s;
    }

    /**
     * Modifica la {@link SesionServidor} del usuario. Si es {@code null} se reconoce como un cierre de sesion.
     *
     * @param nuevaSesion Nueva sesion del usuario.
     * @throws IllegalArgumentException Si se pasa como parametro una sesion no nula distinta a la registrada en
     *                                  esta {@link EntradaUsuario}, la cual es tambien no nula.
     */
    public void setSesion(SesionServidor nuevaSesion) {
        controller.requestWrite();
        if (sesion == nuevaSesion)
        {
            LOGGER.warning(() -> "Trying to set same server session to user " + usuario.uid());
        }
        // Sesiones diferentes y ninguna es null, es decir, corresponden a sesiones en curso
        else if (sesion != null && nuevaSesion != null)
        {
            throw new IllegalArgumentException("Trying to set a different server session to currently online user " + usuario
                    .uid());
        }
        else
        {
            sesion = nuevaSesion;
        }
        controller.releaseWrite();
    }

    @Override
    public int hashCode() {
        return usuario().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntradaUsuario)
        {
            var e = (EntradaUsuario) obj;
            return this.usuario.equals(e.usuario);
        }
        else
            return false;
    }
}
