package ucm.erikkarl.common.concurrency;

import java.util.concurrent.Semaphore;

/**
 * Controlador para permitir el acceso concurrente de N hilos lectores o 1 hilo escritor a un recurso
 * compartido. Cuando hay escrituras pendientes da prioridad a las mismas sobre las lecturas.
 */
public class ReaderWriterController {
    private final Semaphore readers;
    private final Semaphore writers;
    private final Semaphore mutex;
    private int numReaders = 0;
    private int numWriters = 0;
    private int delayedReaders = 0;
    private int delayedWriters = 0;

    /**
     * Crea un {@link ReaderWriterController}.
     */
    public ReaderWriterController() {
        readers = new Semaphore(0);
        writers = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    /**
     * Pide una lectura. Solo si no hay ningun otro hilo escribiendo o esperando a escribir puede
     * empezar a leer.
     */
    public void requestRead() {
        mutex.acquireUninterruptibly();

        // Hay hilos escribiendo ahora mismo o esperando a ello, asi que esperamos a un pase de testigo
        if (numWriters > 0 || delayedWriters > 0)
        {
            delayedReaders++;
            mutex.release();
            readers.acquireUninterruptibly();
            // Nos pasan el testigo
        }
        numReaders++;

        // Si habian hilos lectores esperando, despertamos al siguiente
        if (delayedReaders > 0)
        {
            delayedReaders--;
            readers.release();
        }
        // Si no, soltamos el mutex porque este hilo es el que lo tenia, creo
        else
        {
            mutex.release();
        }
    }

    /**
     * El hilo actual no necesita leer nada mas.
     */
    public void releaseRead() {
        mutex.acquireUninterruptibly();
        numReaders--;

        // Si hay hilos escritores esperando y ningun otro lector, le pasamos el testigo
        if (numReaders == 0 && delayedWriters > 0)
        {
            delayedWriters--;
            writers.release();
        }
        // Si no, soltamos el mutex
        else
        {
            mutex.release();
        }
    }

    /**
     * Pide una escritura. Solo cuando no hay ningun otro hilo leyendo o escribiendo en el momento
     * puede empezar a escribir.
     */
    public void requestWrite() {
        mutex.acquireUninterruptibly();

        // Si hay al menos otro hilo leyendo o escribiendo, esperamos a un pase de testigo
        if (numWriters > 0 || numReaders > 0)
        {
            delayedWriters++;
            mutex.release();
            writers.acquireUninterruptibly();
            // Nos pasan el testigo
        }
        numWriters++;
        mutex.release();
    }

    /**
     * El hilo actual no necesita escribir nada mas.
     */
    public void releaseWrite() {
        mutex.acquireUninterruptibly();
        numWriters--;

        // Si quedan hilos escritores pendientes, los despertamos de uno en uno
        if (delayedWriters > 0)
        {
            delayedWriters--;
            writers.release();
        }
        // Si no, entonces depesertamos a los hilos lectores pendientes
        else if (delayedReaders > 0)
        {
            delayedReaders--;
            readers.release();
        }
        // Si no hay ningun otro hilo, liberamos el mutex
        else
        {
            mutex.release();
        }
    }
}
