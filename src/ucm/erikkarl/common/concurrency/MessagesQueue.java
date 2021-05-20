package ucm.erikkarl.common.concurrency;

import ucm.erikkarl.common.mensajes.Mensaje;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronized fifo queue of {@link Mensaje}s.
 */
public class MessagesQueue {
    private final Queue<Mensaje> queue;
    private final Lock lock;
    private final Condition full;

    /**
     * Creates an empty {@link MessagesQueue}.
     */
    public MessagesQueue() {
        queue = new LinkedList<>();
        lock = new ReentrantLock();
        full = lock.newCondition();
    }

    /**
     * Adds a {@link Mensaje} to the bottom of the queue.
     *
     * @param msg {@link Mensaje} to be added.
     */
    public void add(Mensaje msg) {
        lock.lock();
        queue.add(msg);
        full.signal();
        lock.unlock();
    }

    /**
     * Gets and removes first {@link Mensaje} in the queue. Blocks current
     * thread if no {@link Mensaje} is available.
     *
     * @return First {@link Mensaje} in the queue.
     */
    public Mensaje poll() throws InterruptedException {
        Mensaje msg;

        lock.lock();
        while (queue.isEmpty())
            full.await();
        msg = queue.poll();
        lock.unlock();

        return msg;
    }
}
