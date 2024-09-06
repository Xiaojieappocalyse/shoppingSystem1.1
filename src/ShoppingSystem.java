// ShoppingSystem.java
import java.util.Scanner;

public class ShoppingSystem implements Navigable { // 实现 Navigable 接口
    private static Admin admin = new Admin();
    private static Customer[] customers = new Customer[100];
    private static Product[] products = new Product[100];
    private static int customerCount = 0;
    private static int productCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }

    public static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome to the Shopping Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    adminLogin(scanner);
                    break;
                case 2:
                    customerRegistration(scanner);
                    break;
                case 3:
                    customerLogin(scanner);
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Implement Navigable method to return to main menu
    @Override
    public void returnToMainMenu() {
        System.out.println("Returning to main menu...");
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }

    // Admin login logic remains the same
    private static void adminLogin(Scanner scanner) {
        System.out.println("\nEnter admin username:");
        String username = scanner.next();
        System.out.println("Enter admin password:");
        String password = scanner.next();

        if (admin.login(username, password)) {
            System.out.println("Admin logged in successfully!");
            AdminMenu adminMenu = new AdminMenu(scanner, admin, products, customers, productCount, customerCount);
            adminMenu.displayMenu();

            while (true) {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                adminMenu.handleChoice(choice);
                if (choice == 8) { // Logout option
                    new ShoppingSystem().returnToMainMenu(); // Return to main menu on logout
                    break;
                }
            }
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    // Modify customer registration to log in automatically and show CustomerMenu
    private static void customerRegistration(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        customers[customerCount] = new Customer(username, password);
        Customer newCustomer = customers[customerCount++];
        System.out.println("Customer registered successfully!");

        // Automatically log in the newly registered customer
        CustomerMenu customerMenu = new CustomerMenu(scanner, newCustomer, admin, products, productCount);
        customerMenu.displayMenu();

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            customerMenu.handleChoice(choice);
            if (choice == 5) { // Logout option
                new ShoppingSystem().returnToMainMenu(); // Return to main menu on logout
                break;
            }
        }
    }

    // Customer login logic remains the same
    private static void customerLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        Customer customer = null;
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].login(username, password)) {
                customer = customers[i];
                break;
            }
        }

        if (customer != null) {
            System.out.println("Customer logged in successfully!");
            CustomerMenu customerMenu = new CustomerMenu(scanner, customer, admin, products, productCount);
            customerMenu.displayMenu();

            while (true) {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                customerMenu.handleChoice(choice);
                if (choice == 5) { // Logout option
                    new ShoppingSystem().returnToMainMenu(); // Return to main menu on logout
                    break;
                }
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}
