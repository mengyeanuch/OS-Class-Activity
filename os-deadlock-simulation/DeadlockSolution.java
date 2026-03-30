import java.util.concurrent.Semaphore;

class Account {
    String name;
    int balance;
    Semaphore lock = new Semaphore(1);

    Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
}

class Transfer {

    static void transfer(Account acc1, Account acc2, int amount) {
        Account first, second;

        // ✅ Global ordering (by name)
        if (acc1.name.compareTo(acc2.name) < 0) {
            first = acc1;
            second = acc2;
        } else {
            first = acc2;
            second = acc1;
        }

        try {
            System.out.println(Thread.currentThread().getName() +
                    " locking FIRST " + first.name);
            first.lock.acquire();

            Thread.sleep(100);

            System.out.println(Thread.currentThread().getName() +
                    " locking SECOND " + second.name);
            second.lock.acquire();

            // Critical section
            acc1.balance -= amount;
            acc2.balance += amount;

            System.out.println(Thread.currentThread().getName() +
                    " transfer completed");

            second.lock.release();
            first.lock.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class DeadlockSolution {
    public static void main(String[] args) {

        Account account1 = new Account("Account-1", 1000);
        Account account2 = new Account("Account-2", 1000);

        Thread t1 = new Thread(() ->
                Transfer.transfer(account1, account2, 100),
                "Thread-1"
        );

        Thread t2 = new Thread(() ->
                Transfer.transfer(account2, account1, 200),
                "Thread-2"
        );

        t1.start();
        t2.start();
    }
}