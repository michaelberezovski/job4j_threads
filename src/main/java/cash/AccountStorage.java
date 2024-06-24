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
            return accounts.computeIfAbsent(account.id(), id -> account) == account;
    }

    public synchronized boolean update(Account account) {
            return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean transferred = true;
        Account from = getById(fromId).get();
        Account to = getById(toId).get();
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            if (from.amount() >= amount) {
                update(new Account(fromId, from.amount() - amount));
                update(new Account(toId, to.amount() + amount));
            } else {
                transferred = false;
            }
        } else {
            transferred = false;
        }
        return transferred;
    }
}
