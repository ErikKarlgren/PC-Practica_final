package ucm.erikkarl.common.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase con los siguientes datos de un usuario del sistema: su UID, su direccion IP y los nombres
 * de los ficheros disponibles al igual que su estado de conexion.
 */
public class Usuario
        implements Serializable {

    private final String uid;
    private String ip;
    private List<String> ficherosDisponibles;
    private EstadoConexion estadoConexion;

    /**
     * Crea un {@link Usuario} dado un nombre de usuario, una IP y su estado de conexion.
     * Su lista de ficheros disponibles esta vacia.
     *
     * @param username       Nombre de usuario
     * @param ip             Direccion IP del usuario
     * @param estadoConexion Estado de conexion del usuario
     */
    public Usuario(String username, String ip, EstadoConexion estadoConexion) {
        this.uid = username;
        this.ip = ip;
        this.ficherosDisponibles = new LinkedList<>();
        this.estadoConexion = estadoConexion;
    }

    /**
     * @return Identificador unico del usuario.
     */
    public String uid() {return uid;}

    /**
     * @return {@link InetAddress} con la direccion IP del usuario.
     */
    public String ip() {return ip;}

    /**
     * @param ip Nueva IP para el usuario
     */
    public void setIp(String ip) {
        // TODO: should verify given string is an ip
        this.ip = ip;
    }

    /**
     * @return Una lista con los nombres de los ficheros que tiene disponible un usuario.
     */
    public List<String> ficherosDisponibles() {return Collections.unmodifiableList(ficherosDisponibles);}

    /**
     * @param ficherosDisponibles Nueva lista de ficheros disponibles
     */
    public void setFicherosDisponibles(List<String> ficherosDisponibles) {
        this.ficherosDisponibles = ficherosDisponibles;
    }

    /**
     * @return {@link EstadoConexion} con el estado de la conexion del usuario.
     */
    public EstadoConexion estadoConexion() {return estadoConexion;}

    /**
     * @param estadoConexion Nuevo estado de conexion para el usuario
     */
    public void setEstadoConexion(EstadoConexion estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario)
        {
            var usu = (Usuario) obj;
            return usu.uid.equals(this.uid);
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
