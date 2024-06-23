package cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean added = true;
        if (!accounts.containsKey(account.id())) {
            accounts.putIfAbsent(account.id(), account);
        } else {
            added = false;
        }
        return added;
    }

    public synchronized boolean update(Account account) {
        boolean replaced = true;
        if (accounts.get(account.id()) != null) {
            accounts.replace(account.id(), account);
        } else {
            replaced = false;
        }
        return replaced;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean transferred = true;
        Account from = null;
        Account to = null;
        if (getById(fromId).isPresent()) {
            from = getById(fromId).get();
        }
        if (getById(toId).isPresent()) {
            to = getById(fromId).get();
        }
        assert from != null;
        assert to != null;
        if (from.amount() >= amount) {
            update(new Account(fromId, from.amount() - amount));
            update(new Account(toId, to.amount() + amount));
        } else {
            transferred = false;
        }
        return transferred;
    }
}
