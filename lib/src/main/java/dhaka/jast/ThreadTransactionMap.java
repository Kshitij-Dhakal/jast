package dhaka.jast;

import com.google.common.collect.Maps;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

class ThreadTransactionMap {
    private static ConcurrentMap<Long, Connection> instance;

    private ThreadTransactionMap() {
        //no instance
    }

    private static Map<Long, Connection> getMap() {
        var result = instance;
        if (result == null) { // First check (no locking)
            synchronized (ThreadTransactionMap.class) {
                result = instance;
                if (result == null) { // Second check (with locking)
                    instance = result = Maps.newConcurrentMap();
                }
            }
        }
        return result;
    }

    public static Connection get() {
        return getMap().get(Thread.currentThread().getId());
    }

    public static void put(Connection connection) {
        var map = getMap();
        var id = Thread.currentThread().getId();
        if (map.containsKey(id)) {
            throw new InvalidTransactionStatusException("There is already a transaction in progress in " +
                    Thread.currentThread().getName() + " thread. Please commit or rollback previous transaction " +
                    "before starting new transaction session.");
        }
        map.put(id, connection);
    }

    public static boolean containsTransactionInProgress() {
        return getMap().containsKey(Thread.currentThread().getId());
    }
}
