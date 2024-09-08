import java.util.*;
import java.util.InputMismatchException;

public class ShoppingSystem implements Navigable {
    private static Admin admin = new Admin();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize some products
        Product product1 = new Product("Laptop", "Dell", 999.99, 10);
        Product product2 = new Product("Smartphone", "Apple", 799.99, 20);
        Product product3 = new Product("Headphones", "Sony", 199.99, 30);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        // Add initial customers
        Customer customer1 = new Customer("johnDoe", "Password123!", "john@gmail.com");
        Customer customer2 = new Customer("janeSmith", "SecurePass456$", "jane@example.com");
        Customer customer3 = new Customer("bobBrown", "MyPassword789@", "bob@example.com");

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        // Show main menu
        showMainMenu(scanner);
    }

    public static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome to the Shopping Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Customer Login");
            System.out.println("4. Forgot Password");
            System.out.println("5. Exit");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.next(); // Clear invalid input
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
                    forgotPassword(scanner);
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin(Scanner scanner) {
        System.out.println("\nEnter admin username:");
        String username = scanner.next();
        System.out.println("Enter admin password:");
        String password = scanner.next();

        if (admin.login(username, password)) {
            System.out.println("Admin logged in successfully!");
            AdminMenu adminMenu = new AdminMenu(scanner, admin, products, customers);
            boolean returnToMainMenu = false;

            while (!returnToMainMenu) {
                adminMenu.displayMenu();
                int choice = scanner.nextInt();
                adminMenu.handleChoice(choice);
                if (choice == 8) { // Logout option
                    returnToMainMenu = true;
                }
            }

            // After logout, return to main menu
            showMainMenu(scanner);
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void customerRegistration(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();

        if (!Customer.validateUsername(username)) {
            System.out.println("Username must be at least 5 characters long.");
            return;
        }

        System.out.println("Enter password:");
        String password = scanner.next();

        if (!Customer.validatePassword(password)) {
            System.out.println("Password must be at least 9 characters long and include uppercase, lowercase letters, numbers, and symbols.");
            return;
        }

        System.out.println("Enter email:");
        String email = scanner.next();

        // Register new customer and automatically log in
        Customer newCustomer = new Customer(username, password, email);
        customers.add(newCustomer);
        System.out.println("Customer registered successfully!");

        CustomerMenu customerMenu = new CustomerMenu(scanner, newCustomer, admin, products);
        boolean returnToMainMenu = false;

        while (!returnToMainMenu) {
            customerMenu.displayMenu();
            int choice = scanner.nextInt();
            customerMenu.handleChoice(choice);
            if (choice == 7) { // Logout option
                returnToMainMenu = true;
            }
        }

        // After logout, return to main menu
        showMainMenu(scanner);
    }

    private static void customerLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        Customer customer = null;

        // Iterate through customers list to find matching customer
        for (Customer cust : customers) {
            if (cust.login(username, password)) {
                customer = cust;
                break;
            }
        }

        // If a matching customer was found
        if (customer != null) {
            System.out.println("Customer logged in successfully!");
            CustomerMenu customerMenu = new CustomerMenu(scanner, customer, admin, products);
            boolean returnToMainMenu = false;

            while (!returnToMainMenu) {
                customerMenu.displayMenu();
                int choice = scanner.nextInt();
                customerMenu.handleChoice(choice);
                if (choice == 7) { // Logout option
                    returnToMainMenu = true;
                }
            }

            // After logout, return to main menu
            showMainMenu(scanner);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void forgotPassword(Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.next();

        // Iterate through customers list to find matching customer
        Customer customer = null;
        for (Customer cust : customers) {
            if (cust.getEmail().equals(email)) {
                customer = cust;
                break;
            }
        }

        if (customer != null) {
            System.out.println("Enter your new password:");
            String newPassword = scanner.next();
            if (!Customer.validatePassword(newPassword)) {
                System.out.println("Password must be at least 9 characters long and include uppercase, lowercase letters, numbers, and symbols.");
                return;
            }
            customer.setPassword(newPassword);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("No account associated with this email.");
        }
    }

    @Override
    public void returnToMainMenu() {
        System.out.println("Returning to main menu...");
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }
}
