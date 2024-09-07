import java.util.Scanner;
import java.util.List;

public class CustomerMenu extends Menu {
    private Customer customer;
    private Admin admin;
    private Product[] products;
    private int productCount;

    public CustomerMenu(Scanner scanner, Customer customer, Admin admin, Product[] products, int productCount) {
        super(scanner);
        this.customer = customer;
        this.admin = admin;
        this.products = products;
        this.productCount = productCount;
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
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // 添加商品到购物车
    private void addProductToCart() {
        System.out.println("Enter product ID to add to cart:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products, productCount);
        if (product != null) {
            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            customer.addToCart(product, quantity);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // 从购物车移除商品
    private void removeProductFromCart() {
        System.out.println("Enter product ID to remove from cart:");
        int productId = scanner.nextInt();
        Product product = admin.findProductById(productId, products, productCount);
        if (product != null) {
            customer.removeFromCart(product);
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // 修改密码
    private void changePassword() {
        System.out.println("Enter old password:");
        String oldPassword = scanner.next();
        System.out.println("Enter new password:");
        String newPassword = scanner.next();
        customer.changePassword(oldPassword, newPassword);
    }

    // 忘记密码
    private void forgotPassword() {
        System.out.println("Enter your username:");
        String username = scanner.next();
        System.out.println("Enter your registered email:");
        String email = scanner.next();
        customer.resetPassword(username, email);
    }
}
