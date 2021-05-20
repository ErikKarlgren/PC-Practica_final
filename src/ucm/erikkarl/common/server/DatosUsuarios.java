package ucm.erikkarl.common.server;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Tipo abstracto de datos que contiene los datos de todos los usuarios
 * registrados en el servidor.
 */
public interface DatosUsuarios {

    /**
     * @return Numero de usuarios registrados
     */
    int size();

    /**
     * @return {@code true} si no hay usuarios registrados
     */
    boolean isEmpty();

    /**
     * @param nombre Nombre de usuario
     * @return {@code true} si existe el usuario
     */
    boolean contieneUsuario(String nombre);

    /**
     * @param usu Usuario
     * @return {@code true} si existe el usuario
     */
    boolean contieneUsuario(Usuario usu);

    /**
     * Busca un {@link Usuario} dado su nombre de usuario y devuelve una copia.
     * Si no existe devuelve un {@link Optional#empty()}.
     *
     * @param nombre Nombre de usuario
     * @return Un posible {@link Usuario} si existe, o un {@link Optional#empty()}.
     */
    Optional<Usuario> get(String nombre);

    /**
     * Registra un usuario o sobrescribe sus datos si ya existia
     *
     * @param usu Nuevo usuario.
     */
    void put(Usuario usu);

    /**
     * Quita el usuario del servidor
     *
     * @param nombre Nombre del usuario a quitar
     * @return El usuario que se ha quitado si existia, o {@link Optional#empty()} en
     * caso contrario.
     */
    Optional<Usuario> remove(String nombre);

    /**
     * Elimina todos los usuarios del servidor.
     */
    void clear();

    /**
     * @return Un conjunto con los nombres de todos los usuarios.
     */
    Set<String> nombresDeUsuario();

    /**
     * @return Copia de los {@link Usuario}s registrados en el servidor.
     */
    TreeSet<Usuario> usuarios();

    /**
     * Busca un usuario dado un nombre y actualiza su correspondiente sesion
     * del servidor.
     *
     * @param username nombre de usuario
     * @param sesion   Nueva sesion del cliente
     * @return {@code true} si se encuentra el usuario y se puede actualizar su sesion, y
     * {@code false} en caso contrario.
     */
    boolean setSesionDelUsuario(String username, SesionServidor sesion);

    /**
     * Busca la sesion correspondiente a un usuario dado su nombre si
     * es que este esta conectado y registrado en el servidor.
     *
     * @param username Nombre de usuario.
     * @return La {@link SesionServidor} correspondiente al usuario. Si el usuario
     * no esta conectado o no esta registrado, devuelve un {@link Optional#empty()}.
     */
    Optional<SesionServidor> sesionDelUsuario(String username);
}
