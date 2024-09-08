import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CustomerMenu extends Menu {
    private Customer customer;
    private Admin admin;
    private List<Product> products;
    private boolean returnToMainMenu;

    public CustomerMenu(Scanner scanner, Customer customer, Admin admin, List<Product> products) {
        super(scanner);
        this.customer = customer;
        this.admin = admin;
        this.products = products;
    }

    @Override
    public void displayMenu() {
        System.out.println("\nCustomer Menu:");
        System.out.println("1. Add Product to Cart");
        System.out.println("2. Remove Product from Cart");
        System.out.println("3. Checkout");
        System.out.println("4. View Shopping History");
        System.out.println("5. Change Password");
        System.out.println("6. Forgot Password");
        System.out.println("7. Logout");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addProductToCart();
                break;
            case 2:
                removeProductFromCart();
                break;
            case 3:
                customer.checkout();
                break;
            case 4:
                customer.viewShoppingHistory();
                break;
            case 5:
                changePassword();
                break;
            case 6:
                forgotPassword();
                break;
            case 7:
                System.out.println("Logging out...");
                returnToMainMenu = true; // Set flag to return to main menu
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addProductToCart() {
        System.out.println("Enter the product ID to add to cart:");
        int productId = getInputInt();
        Product product = findProductById(productId);

        if (product != null) {
            System.out.println("Enter the quantity to add:");
            int quantity = getInputInt();
            customer.addToCart(product, quantity);
        } else {
            System.out.println("Product not found.");
        }
    }

    private void removeProductFromCart() {
        System.out.println("Enter the product ID to remove from cart:");
        int productId = getInputInt();
        Product product = findProductById(productId);

        if (product != null) {
            customer.removeFromCart(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    private void changePassword() {
        System.out.println("Enter your current password:");
        String currentPassword = scanner.nextLine();
        System.out.println("Enter your new password:");
        String newPassword = scanner.nextLine();
        customer.changePassword(currentPassword, newPassword);
    }

    private void forgotPassword() {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        customer.resetPassword(username, email);
    }

    private Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    private int getInputInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number:");
            }
        }
    }
}
