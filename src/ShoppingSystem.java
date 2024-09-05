import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingSystem {
    private static Admin admin = new Admin();
    private static Customer[] customers = new Customer[100];
    private static Product[] products = new Product[100];
    private static int customerCount = 0;
    private static int productCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
        scanner.close();
    }

    private static void showMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("Welcome to the Shopping Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

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
                    return; // Exit the application
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.next();
        System.out.println("Enter admin password:");
        String password = scanner.next();

        if (admin.login(username, password)) {
            System.out.println("Admin logged in successfully!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Change Admin Password");
            System.out.println("2. Reset Customer Password");
            System.out.println("3. List All Customers");
            System.out.println("4. Add Product");
            System.out.println("5. Modify Product");
            System.out.println("6. Delete Product");
            System.out.println("7. List All Products");
            System.out.println("8. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    changeAdminPassword(scanner);
                    break;
                case 2:
                    resetCustomerPassword(scanner);
                    break;
                case 3:
                    admin.listAllCustomers(customers, customerCount);
                    break;
                case 4:
                    addProduct(scanner);
                    break;
                case 5:
                    modifyProduct(scanner);
                    break;
                case 6:
                    deleteProduct(scanner);
                    break;
                case 7:
                    admin.listAllProducts(products, productCount);
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void changeAdminPassword(Scanner scanner) {
        System.out.println("Enter new password for admin:");
        String newPassword = scanner.next();
        admin.changePassword(newPassword);
        System.out.println("Admin password changed successfully!");
    }

    private static void resetCustomerPassword(Scanner scanner) {
        System.out.println("Enter customer ID to reset password:");
        int customerId = scanner.nextInt();
        Customer customer = admin.findCustomerById(customerId, customers, customerCount);

        if (customer != null) {
            System.out.println("Enter new password for customer:");
            String newPassword = scanner.next();
            admin.resetCustomerPassword(customer, newPassword);
            System.out.println("Customer password reset successfully!");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void addProduct(Scanner scanner) {
        String name;
        String manufacturer;
        double price = 0;
        int quantity = 0;

        // Get product name
        System.out.println("Enter product name:");
        name = scanner.next();

        // Get product manufacturer
        System.out.println("Enter product manufacturer:");
        manufacturer = scanner.next();

        // Get product price with error handling
        while (true) {
            try {
                System.out.println("Enter product price:");
                price = scanner.nextDouble();
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a valid price.");
                    continue;
                }
                break; // Exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for price. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Get product quantity with error handling
        while (true) {
            try {
                System.out.println("Enter product quantity:");
                quantity = scanner.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                    continue;
                }
                break; // Exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for quantity. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Create and add the product
        Product product = new Product(name, manufacturer, price, quantity);
        admin.addProduct(products, product, productCount++);
    }

    private static void modifyProduct(Scanner scanner) {
        System.out.println("Enter product ID to modify:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products, productCount);

        if (product != null) {
            System.out.println("Enter new product name:");
            String newName = scanner.next();
            System.out.println("Enter new product price:");
            double newPrice = scanner.nextDouble();
            System.out.println("Enter new product quantity:");
            int newQuantity = scanner.nextInt();
            admin.modifyProduct(product, newName, newPrice, newQuantity);
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.println("Enter product ID to delete:");
        int productId = scanner.nextInt();

        // Attempt to delete the product
        boolean success = admin.deleteProduct(products, productId, productCount);

        if (success) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found or an error occurred. Please try again.");
        }
    }


    private static void customerRegistration(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        // Create and register the new customer
        Customer customer = new Customer(username, password);
        customers[customerCount++] = customer;
        System.out.println("Customer registered successfully!");

        // Automatically log the customer in and navigate to the customer menu
        customerMenu(scanner, customer);
    }


    private static void customerLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        // Try to find the customer based on username and password
        Customer customer = null;
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getUsername().equals(username) && customers[i].getPassword().equals(password)) {
                customer = customers[i];
                break;
            }
        }

        // If customer is found, automatically navigate to the Customer Menu
        if (customer != null) {
            System.out.println("Customer logged in successfully!");
            customerMenu(scanner, customer);  // Directly enter the customer menu
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void customerMenu(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. Remove Product from Cart");
            System.out.println("3. Checkout");
            System.out.println("4. View Shopping History");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProductToCart(scanner, customer);
                    break;
                case 2:
                    removeProductFromCart(scanner, customer);
                    break;
                case 3:
                    customer.checkout();
                    break;
                case 4:
                    customer.viewShoppingHistory();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProductToCart(Scanner scanner, Customer customer) {
        System.out.println("Enter product ID to add to cart:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products, productCount);

        if (product != null && product.getQuantity() > 0) {
            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            if (quantity <= product.getQuantity()) {
                customer.addToCart(product, quantity);
                System.out.println("Product added to cart.");
            } else {
                System.out.println("Insufficient quantity.");
            }
        } else {
            System.out.println("Product not found or out of stock.");
        }
    }

    private static void removeProductFromCart(Scanner scanner, Customer customer) {
        System.out.println("Enter product ID to remove from cart:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products, productCount);

        if (product != null) {
            customer.removeFromCart(product);
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }
}
