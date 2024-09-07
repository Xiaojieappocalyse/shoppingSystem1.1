import java.util.*;

public class ShoppingSystem implements Navigable {
    private static Admin admin = new Admin();
    private static List<Customer> customers = new ArrayList<>(); // 使用List替代数组
    private static List<Product> products = new ArrayList<>();
    // 使用List替代数组

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }

    public static void showMainMenu(Scanner scanner) {
        Product product1 = new Product("Laptop", "Dell", 999.99, 10);
        Product product2 = new Product("Smartphone", "Apple", 799.99, 20);
        Product product3 = new Product("Headphones", "Sony", 199.99, 30);

        // 将产品添加到一个列表中
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        while (true) {
            System.out.println("\nWelcome to the Shopping Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.next(); // 清理错误输入
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
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public void returnToMainMenu() {
        System.out.println("Returning to main menu...");
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }

    private static void adminLogin(Scanner scanner) {
        System.out.println("\nEnter admin username:");
        String username = scanner.next();
        System.out.println("Enter admin password:");
        String password = scanner.next();

        if (admin.login(username, password)) {
            System.out.println("Admin logged in successfully!");
            AdminMenu adminMenu = new AdminMenu(scanner, admin, products, customers);
            adminMenu.displayMenu();

            while (true) {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                adminMenu.handleChoice(choice);
                if (choice == 8) { // Logout option
                    new ShoppingSystem().returnToMainMenu();
                    break;
                }
            }
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

        // 注册新客户并自动登录
        Customer newCustomer = new Customer(username, password, email);
        customers.add(newCustomer);
        System.out.println("Customer registered successfully!");

        CustomerMenu customerMenu = new CustomerMenu(scanner, newCustomer, admin, products);
        customerMenu.displayMenu();

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            customerMenu.handleChoice(choice);
            if (choice == 5) { // Logout option
                new ShoppingSystem().returnToMainMenu();
                break;
            }
        }
    }

    private static void customerLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        Customer customer = null;
        for (Customer cust : customers) {
            if (cust.login(username, password)) {
                customer = cust;
                break;
            }
        }

        if (customer != null) {
            System.out.println("Customer logged in successfully!");
            CustomerMenu customerMenu = new CustomerMenu(scanner, customer, admin, products);
            customerMenu.displayMenu();

            while (true) {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                customerMenu.handleChoice(choice);
                if (choice == 5) { // Logout option
                    new ShoppingSystem().returnToMainMenu();
                    break;
                }
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}
