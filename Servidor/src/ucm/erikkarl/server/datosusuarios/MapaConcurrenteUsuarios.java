package ucm.erikkarl.server.datosusuarios;

import ucm.erikkarl.common.concurrency.ReaderWriterController;
import ucm.erikkarl.common.server.DatosUsuarios;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.common.server.Usuario;

import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MapaConcurrenteUsuarios
        implements DatosUsuarios {

    private final TreeMap<String, EntradaUsuario> uidToUserEntry;
    private final ReaderWriterController controller;

    public MapaConcurrenteUsuarios() {
        uidToUserEntry = new TreeMap<>();
        controller = new ReaderWriterController();
    }

    @Override
    public int size() {
        controller.requestRead();
        var size = uidToUserEntry.size();
        controller.releaseRead();
        return size;
    }

    @Override
    public boolean isEmpty() {
        controller.requestRead();
        var a = uidToUserEntry.isEmpty();
        controller.releaseRead();
        return a;
    }

    @Override
    public boolean contieneUsuario(String nombre) {
        controller.requestRead();
        var a = uidToUserEntry.containsKey(nombre);
        controller.releaseRead();
        return a;
    }

    public boolean contieneUsuario(Usuario usu) {
        return contieneUsuario(usu.uid());
    }

    @Override
    public Optional<Usuario> get(String nombre) {
        controller.requestRead();
        var entrada = uidToUserEntry.get(nombre);
        Optional<Usuario> usu = (entrada == null) ? Optional.empty() : Optional.of(entrada.usuario());
        controller.releaseRead();
        return usu;
    }

    @Override
    public void put(Usuario usu) {
        controller.requestWrite();
        var entrada = new EntradaUsuario(usu);
        uidToUserEntry.put(usu.uid(), entrada);
        controller.releaseWrite();
    }

    @Override
    public Optional<Usuario> remove(String nombre) {
        controller.requestWrite();
        var a = uidToUserEntry.remove(nombre);
        controller.releaseWrite();
        return a == null ? Optional.empty() : Optional.of(a.usuario());
    }

    @Override
    public void clear() {
        controller.requestWrite();
        uidToUserEntry.clear();
        controller.releaseWrite();
    }

    @Override
    public Set<String> nombresDeUsuario() {
        controller.requestRead();
        var a = new TreeSet<>(uidToUserEntry.keySet());
        controller.releaseRead();
        return a;
    }

    @Override
    public TreeSet<Usuario> usuarios() {
        controller.requestRead();
        var entries = new TreeSet<>(uidToUserEntry.values());
        var sortedUsers = entries.stream()
                                 .map(EntradaUsuario::usuario)
                                 .collect(Collectors.toCollection(TreeSet::new));
        controller.releaseRead();
        return sortedUsers;
    }

    @Override
    public Optional<SesionServidor> sesionDelUsuario(String username) {
        controller.requestRead();
        var entrada = uidToUserEntry.get(username);
        Optional<SesionServidor> sesion = (entrada == null) ? Optional.empty() : entrada.sesion();
        controller.releaseRead();
        return sesion;
    }
}
