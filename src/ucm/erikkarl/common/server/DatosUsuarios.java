package ucm.erikkarl.common.server;

import java.util.Optional;
import java.util.TreeSet;

/**
 * Tipo abstracto de datos que contiene los datos de todos los usuarios
 * registrados en el servidor.
 */
public interface DatosUsuarios {
    /**
     * Busca un {@link Usuario} dado su nombre de usuario. Si no existe devuelve
     * un {@link Optional#empty()}.
     *
     * @param username Nombre de usuario
     * @return Un posible {@link Usuario} si existe, o un {@link Optional#empty()}.
     */
    Optional<Usuario> buscarUsuario(String username);

    /**
     * @return Copia de los {@link Usuario}s registrados en el servidor.
     *
     */
    TreeSet<Usuario> usuarios();

}
