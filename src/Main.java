import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SodaMachine machine = new SodaMachine();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Soda Machine ---");
            System.out.println("1. Insert money");
            System.out.println("2. Select product");
            System.out.println("3. Cancel request");
            System.out.println("4. End day");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount (10000, 20000, 50000, 100000, 200000): ");
                    int amount = scanner.nextInt();
                    if (machine.insertMoney(amount)) {
                        System.out.println("Inserted: " + amount + " VND");
                    } else {
                        System.out.println("Invalid amount");
                    }
                    break;
                case 2:
                    System.out.print("Enter product (Coke, Pepsi, Soda): ");
                    String product = scanner.next();
                    System.out.println(machine.selectProduct(product));
                    break;
                case 3:
                    System.out.println("Request canceled, refund: " + machine.cancelRequest() + " VND");
                    break;
                case 4:
                    machine.endDay();
                    System.out.println("Day ended, win rate updated if needed.");
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }
}