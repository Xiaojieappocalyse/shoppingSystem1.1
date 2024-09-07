import java.util.*;
import java.util.regex.Pattern;

public class Customer extends User {
    private static int idCounter = 1;
    private int id;
    private String email;
    private double totalSpent;
    private ShoppingCart cart;
    private List<Product> shoppingHistory;
    private int loginAttempts;
    private boolean isLocked;

    // Constructor
    public Customer(String username, String password, String email) {
        super(username, password);
        this.id = idCounter++;
        this.email = email;
        this.totalSpent = 0;
        this.cart = new ShoppingCart();
        this.shoppingHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // Username and password validation
    public static boolean validateUsername(String username) {
        return username.length() >= 5;
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W]).{9,}$";
        return Pattern.matches(regex, password);
    }

    // Login method with account lock functionality
    @Override
    public boolean login(String inputUsername, String inputPassword) {
        if (isLocked) {
            System.out.println("Account is locked.");
            return false;
        }

        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            loginAttempts = 0; // Reset error count on successful login
            System.out.println("Login successful!");
            return true;
        } else {
            loginAttempts++;
            if (loginAttempts >= 5) {
                isLocked = true; // Lock account after 5 failed attempts
                System.out.println("Account locked due to multiple incorrect attempts.");
            } else {
                System.out.println("Incorrect password. Attempts remaining: " + (5 - loginAttempts));
            }
            return false;
        }
    }

    // Change password
    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            if (validatePassword(newPassword)) {
                this.password = newPassword;
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("New password does not meet the requirements.");
            }
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    // Reset password
    public void resetPassword(String inputUsername, String inputEmail) {
        if (this.username.equals(inputUsername) && this.email.equals(inputEmail)) {
            String newPassword = generateRandomPassword();
            this.password = newPassword;
            System.out.println("A new password has been sent to your email: " + newPassword);
        } else {
            System.out.println("Username or email is incorrect.");
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder newPassword = new StringBuilder();
        Random rnd = new Random();
        while (newPassword.length() < 10) {
            int index = (int) (rnd.nextFloat() * chars.length());
            newPassword.append(chars.charAt(index));
        }
        return newPassword.toString();
    }

    // Add product to cart
    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
        System.out.println(quantity + " of " + product.getName() + " added to cart.");
    }

    // Remove product from cart
    public void removeFromCart(Product product) {
        cart.removeProduct(product);
        System.out.println(product.getName() + " removed from cart.");
    }

    // Checkout functionality
    public void checkout() {
        double total = cart.calculateTotal();
        if (total == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Total cost: " + total);
        System.out.println("Select payment method: 1. Alipay 2. WeChat 3. Bank Card");
        Scanner scanner = new Scanner(System.in);
        int paymentMethod = scanner.nextInt();

        switch (paymentMethod) {
            case 1:
                System.out.println("Alipay payment success.");
                break;
            case 2:
                System.out.println("WeChat payment success.");
                break;
            case 3:
                System.out.println("Bank card payment success.");
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantity(product.getQuantity() - quantity); // Deduct stock
            shoppingHistory.add(product);  // Add to shopping history
        }

        totalSpent += total; // Update total spent
        cart.clearCart();    // Clear cart
        System.out.println("Checkout complete. Thank you for your purchase!");
    }

    // View shopping history
    public void viewShoppingHistory() {
        if (shoppingHistory.isEmpty()) {
            System.out.println("Your shopping history is empty.");
        } else {
            System.out.println("Your shopping history:");
            for (Product product : shoppingHistory) {
                System.out.println(product.getName() + " (Price: " + product.getPrice() + ")");
            }
        }
    }

    // Get customer's shopping history
    public List<Product> getShoppingHistory() {
        return shoppingHistory;
    }

    // Getter for password
    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
