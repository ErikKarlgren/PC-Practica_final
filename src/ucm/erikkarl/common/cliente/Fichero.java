package ucm.erikkarl.common.cliente;

import java.io.Serializable;

/**
 * Clase que representa los contenidos de un fichero.
 */
public class Fichero
        implements Serializable {
    private final String name;
    private final String content;

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Fichero(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
