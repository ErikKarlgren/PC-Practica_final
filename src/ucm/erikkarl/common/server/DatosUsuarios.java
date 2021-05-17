package ucm.erikkarl.common.server;

import java.util.Map;
import java.util.Optional;

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
     * Busca un usuario y comprueba si esta online o no. Si el usuario no existe
     * devuelve {@code false}.
     *
     * @param username Nombre de usuario
     * @return {@code true} si el usuario existe y esta online, {@code false} en caso
     * contrario.
     */
    boolean usuarioEstaOnline(String username);

    /**
     * @return Mapa que indica para cada {@link Usuario} registrado en el servidor si
     * esta online o no. Es una copia de los datos guardados en el servidor.
     */
    Map<Usuario, EstadoConexion> mapaUsuarioAEstadoConexion();

}
