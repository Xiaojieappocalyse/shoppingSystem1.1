import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public Customer(String username, String password, String email) {
        super(username, hashPassword(password));  // Use the static method from User class
        this.id = idCounter++;
        this.email = email;
        this.totalSpent = 0;
        this.cart = new ShoppingCart();
        this.shoppingHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // Additional constructor to be used in `fromString` method
    public Customer(int id, String username, String email, String password) {
        super(username, hashPassword(password));  // Use the static method from User class
        this.id = id;
        this.email = email;
        this.totalSpent = 0;
        this.cart = new ShoppingCart();
        this.shoppingHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // Static method to create a Customer object from a string
    public static Customer fromString(String str) {
        String[] parts = str.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid customer string: " + str);
        }
        int id = Integer.parseInt(parts[0]);
        String username = parts[1];
        String email = parts[2];
        String password = parts[3];
        double totalSpent = Double.parseDouble(parts[4]);
        Customer customer = new Customer(id, username, email, password);
        customer.totalSpent = totalSpent;
        return customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean login(String inputUsername, String inputPassword) {
        if (isLocked) {
            System.out.println("Account is locked.");
            return false;
        }

        if (this.username.equals(inputUsername) && this.password.equals(hashPassword(inputPassword))) {
            loginAttempts = 0;
            System.out.println("Login successful!");
            return true;
        } else {
            loginAttempts++;
            if (loginAttempts >= 5) {
                isLocked = true;
                System.out.println("Account locked due to multiple incorrect attempts.");
            } else {
                System.out.println("Incorrect password. Attempts remaining: " + (5 - loginAttempts));
            }
            return false;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(hashPassword(oldPassword))) {
            if (validatePassword(newPassword)) {
                this.password = hashPassword(newPassword);
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("New password does not meet the requirements.");
            }
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    public void resetPassword(String inputUsername, String inputEmail) {
        if (this.username.equals(inputUsername) && this.email.equals(inputEmail)) {
            String newPassword = generateRandomPassword();
            this.password = hashPassword(newPassword);
            System.out.println("A new password has been sent to your email: " + newPassword);
        } else {
            System.out.println("Username or email is incorrect.");
        }
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
        System.out.println(quantity + " of " + product.getName() + " added to cart.");
    }

    public void removeFromCart(Product product) {
        cart.removeProduct(product);
        System.out.println(product.getName() + " removed from cart.");
    }

    public void checkout() {
        double total = cart.calculateTotal();
        if (total == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Total cost: " + total);
        System.out.println("Select payment method: 1. Alipay 2. WeChat 3. Bank Card");
        Scanner scanner = new Scanner(System.in);
        int paymentMethod = -1;
        try {
            paymentMethod = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }

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
            product.setQuantity(product.getQuantity() - quantity);
            shoppingHistory.add(product);
        }

        totalSpent += total;
        cart.clearCart();
        System.out.println("Checkout complete. Thank you for your purchase!");
    }

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

    public String getEmail() {
        return email;
    }

    public void setPassword(String newPassword) {
        if (validatePassword(newPassword)) {
            this.password = hashPassword(newPassword);
            System.out.println("Password has been updated successfully.");
        } else {
            System.out.println("New password does not meet the requirements.");
        }
    }

    public int getId() {
        return id;
    }

    private String generateRandomPassword() {
        // Implement password generation logic
        return "newRandomPassword123!"; // Replace with actual password generation
    }

    // Static method to save a list of customers to a file
    public static void saveCustomers(List<Customer> customers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\test\\shoppingsystem1.1\\src\\User.txt"))) {
            for (Customer customer : customers) {
                writer.write(customer.getId() + "," + customer.getUsername() + "," + customer.getEmail() + "," + customer.getPassword() + "," + customer.totalSpent);
                writer.newLine();
            }
        }
    }

    // Static method to load customers from a file
    public static List<Customer> loadCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("E:\\test\\shoppingsystem1.1\\src\\User.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customers.add(fromString(line));
            }
        }
        return customers;
    }
}
