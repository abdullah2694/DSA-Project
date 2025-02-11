import java.util.*;

class Account {
    private int pin;
    private double balance;
    private Queue<String> transactions;

//    initlize the account with and the balance
    public Account(int pin, double balance) {
        this.pin = pin;
        this.balance = balance;
        this.transactions = new LinkedList<>();
    }

//    Methord to verify the pin
    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

//    Methord To update pin
    public void setPin(int newPin) {
        this.pin = newPin;
        addTransaction("PIN changed successfully.");
    }

//    Methord to check the account balance
    public double getBalance() {
        return balance;
    }

//    Methord to withdraw money
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient funds! Transaction failed.");
        } else {
            balance -= amount;
            addTransaction("Withdrawn: $" + amount);
            System.out.println("✅ Withdrawal successful! New Balance: $" + balance);
        }
    }

//    Methord to deposit money
    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposited: $" + amount);
        System.out.println("✅ Deposit successful! New Balance: $" + balance);
    }

//    Methord to transfer the money
    public void transfer(Account recipient, double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient funds! Transfer failed.");
        } else {
            balance -= amount;
            recipient.balance += amount;
            addTransaction("Transferred: $" + amount + " to Account #" + recipient);
            System.out.println("✅ Transfer successful! New Balance: $" + balance);
        }
    }

//    Methord to pay the bill
    public void payBill(double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient funds! Bill payment failed.");
        } else {
            balance -= amount;
            addTransaction("Bill Paid: $" + amount);
            System.out.println("✅ Bill payment successful! New Balance: $" + balance);
        }
    }

//    Methord to show the last 5 transactions
    public void showRecentTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("📌 No recent transactions.");
        } else {
            System.out.println("📜 Recent Transactions:");
            for (String transaction : transactions) {
                System.out.println("   ➤ " + transaction);
            }
        }
    }

//    Add transaction to the queue
    private void addTransaction(String transaction) {
        if (transactions.size() >= 5) {
            transactions.poll();
        }
        transactions.offer(transaction);
    }
}

public class ATMSystem {
    private static final Map<Integer, Account> accounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    static {

        accounts.put(111, new Account(1111, 5000));
        accounts.put(222, new Account(2222, 8000));
        accounts.put(333, new Account(3333, 10000));
        accounts.put(444, new Account(4444, 7000));
        accounts.put(555, new Account(5555, 12000));
        accounts.put(666, new Account(6666, 15000));
        accounts.put(777, new Account(7777, 11000));
        accounts.put(888, new Account(8888, 13000));
        accounts.put(999, new Account(9999, 14000));
        accounts.put(101, new Account(1010, 6000));
    }

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("         WELCOME TO ATM SYSTEM      ");
        System.out.println("====================================");

        Account userAccount = authenticateUser();
        if (userAccount == null) return;

        while (true) {
            System.out.println("\n====================================");
            System.out.println("          MAIN MENU                 ");
            System.out.println("====================================");
            System.out.println("1️⃣  Withdraw Money");
            System.out.println("2️⃣  Deposit Money");
            System.out.println("3️⃣  Transfer Money");
            System.out.println("4️⃣  Bank Inquiry (Check Balance)");
            System.out.println("5️⃣  Change PIN");
            System.out.println("6️⃣  View Recent Transactions");
            System.out.println("7️⃣  Bill Payment");
            System.out.println("8️⃣  Exit");
            System.out.print("🔹 Choose an option: ");

            int choice = getValidInteger();

            switch (choice) {
                case 1 -> withdrawMoney(userAccount);
                case 2 -> depositMoney(userAccount);
                case 3 -> transferMoney(userAccount);
                case 4 -> System.out.println("💰 Current Balance: $" + userAccount.getBalance());
                case 5 -> changePIN(userAccount);
                case 6 -> userAccount.showRecentTransactions();
                case 7 -> billPayment(userAccount);
                case 8 -> {
                    System.out.println("🚪 Thank you for using our ATM! Goodbye.");
                    return;
                }
                default -> System.out.println("⚠️  Invalid choice! Please try again.");
            }
        }
    }

    private static Account authenticateUser() {
        while (true) {
            System.out.print("\n🔑 Enter Account Number: ");
            int accNumber = getValidInteger();

            if (!accounts.containsKey(accNumber)) {
                System.out.println("❌ Account not found! Try again.");
                continue;
            }

            System.out.print("🔒 Enter PIN: ");
            int pin = getValidInteger();

            Account userAccount = accounts.get(accNumber);
            if (userAccount.verifyPin(pin)) {
                System.out.println("✅ Login Successful!");
                return userAccount;
            } else {
                System.out.println("❌ Incorrect PIN! Try again.");
            }
        }
    }

    private static void withdrawMoney(Account account) {
        System.out.print("💸 Enter amount to withdraw: ");
        double amount = getValidDouble();
        account.withdraw(amount);
    }

    private static void depositMoney(Account account) {
        System.out.print("💰 Enter amount to deposit: ");
        double amount = getValidDouble();
        account.deposit(amount);
    }

    private static void transferMoney(Account sender) {
        System.out.print("📤 Enter recipient account number: ");
        int recipientAcc = getValidInteger();

        if (!accounts.containsKey(recipientAcc)) {
            System.out.println("❌ Recipient account not found!");
            return;
        }

        System.out.print("💲 Enter amount to transfer: ");
        double amount = getValidDouble();
        sender.transfer(accounts.get(recipientAcc), amount);
    }

    private static void changePIN(Account account) {
        System.out.print("🔑 Enter new PIN: ");
        int newPIN = getValidInteger();
        account.setPin(newPIN);
        System.out.println("✅ PIN changed successfully!");
    }

    private static void billPayment(Account account) {
        System.out.print("📑 Enter bill amount: ");
        double amount = getValidDouble();
        account.payBill(amount);
    }

    private static int getValidInteger() {
        while (!scanner.hasNextInt()) {
            System.out.println("⚠️ Invalid input! Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getValidDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("⚠️ Invalid input! Please enter a valid amount.");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
