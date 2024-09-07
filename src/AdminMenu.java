import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu extends Menu {
    private Admin admin;
    private List<Product> products;
    private List<Customer> customers;

    public AdminMenu(Scanner scanner, Admin admin, List<Product> products, List<Customer> customers) {
        super(scanner);
        this.admin = admin;
        this.products = products;
        this.customers = customers;
    }

    @Override
    public void displayMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Change Admin Password");
        System.out.println("2. Reset Customer Password");
        System.out.println("3. List All Customers");
        System.out.println("4. Add Product");
        System.out.println("5. Modify Product");
        System.out.println("6. Delete Product");
        System.out.println("7. List All Products");
        System.out.println("8. Logout");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                changeAdminPassword();
                break;
            case 2:
                resetCustomerPassword();
                break;
            case 3:
                admin.listAllCustomers(customers);
                break;
            case 4:
                addProduct();
                break;
            case 5:
                modifyProduct();
                break;
            case 6:
                deleteProduct();
                break;
            case 7:
                admin.listAllProducts(products);
                break;
            case 8:
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void changeAdminPassword() {
        System.out.println("Enter new password for admin:");
        String newPassword = scanner.next();
        admin.changePassword(newPassword);
    }

    private void resetCustomerPassword() {
        System.out.println("Enter customer ID to reset password:");
        int customerId = scanner.nextInt();
        Customer customer = admin.findCustomerById(customerId, customers);
        if (customer != null) {
            System.out.println("Enter new password for customer:");
            String newPassword = scanner.next();
            admin.resetCustomerPassword(customer, newPassword);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void addProduct() {
        System.out.println("Enter product name:");
        String name = scanner.next();
        System.out.println("Enter product manufacturer:");
        String manufacturer = scanner.next();

        double price = 0;
        while (true) {
            try {
                System.out.println("Enter product price:");
                price = scanner.nextDouble();
                if (price >= 0) break;
                System.out.println("Price cannot be negative.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid price. Please enter a valid number.");
                scanner.next();
            }
        }

        int quantity = 0;
        while (true) {
            try {
                System.out.println("Enter product quantity:");
                quantity = scanner.nextInt();
                if (quantity >= 0) break;
                System.out.println("Quantity cannot be negative.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid quantity. Please enter a valid number.");
                scanner.next();
            }
        }

        Product product = new Product(name, manufacturer, price, quantity);
        admin.addProduct(products, product);
    }

    private void modifyProduct() {
        System.out.println("Enter product ID to modify:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products);
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

    private void deleteProduct() {
        System.out.println("Enter product ID to delete:");
        int productId = scanner.nextInt();
        admin.deleteProduct(products, productId);
    }
}
