import java.io.*;
import java.util.*;

public class Admin extends User {

    private static final String USER_FILE = "E:\\test\\shoppingsystem1.1\\src\\users.txt";

    // Constructor for default admin account
    public Admin() {
        super("admin", "ynuinfo#777"); // Default admin account
        saveAdmin(); // Ensure the default admin is saved to the file
    }

    // Constructor for custom admin account
    public Admin(String username, String password) {
        super(username, password);
        saveAdmin(); // Save admin details when creating a new admin
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(hashPassword(password));
    }

    // Save admin to file
    private void saveAdmin() {
        List<User> users = loadUsers();
        boolean adminExists = false;
        for (User user : users) {
            if (user instanceof Admin) {
                adminExists = true;
                break;
            }
        }
        if (!adminExists) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
                writer.write(this.getId() + "," + this.getUsername() + "," + this.getEmail() + "," + this.getPassword() + ",admin");
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error saving admin information: " + e.getMessage());
            }
        }
    }

    // Load users from file
    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[4].equals("admin")) {
                    Admin admin = new Admin(parts[1], parts[3]);
                    admin.setId(Integer.parseInt(parts[0]));
                    admin.setEmail(parts[2]);
                    users.add(admin);
                } else {
                    Customer customer = new Customer(parts[1], parts[3], parts[2]);
                    customer.setId(Integer.parseInt(parts[0]));
                    users.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading user information: " + e.getMessage());
        }
        return users;
    }

    // Implementing abstract methods from User class
    @Override
    protected void setId(int id) {
        this.id = id;
    }

    @Override
    protected void setEmail(String email) {
        this.email = email;
    }

    // Add a product to the list
    public void addProduct(List<Product> products, Product product) {
        products.add(product);
        System.out.println("Product added successfully!");
    }

    // Modify an existing product
    public void modifyProduct(Product product, String newName, double newPrice, int newQuantity) {
        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantity(newQuantity);
        System.out.println("Product modified successfully!");
    }

    // Delete a product by ID
    public void deleteProduct(List<Product> products, int productId) {
        Product product = findProductById(productId, products);
        if (product != null) {
            products.remove(product);
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    // List all customers
    public void listAllCustomers(List<Customer> customers) {
        System.out.println("Listing all customers:");
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId() + ", Username: " + customer.getUsername() + ", Email: " + customer.getEmail());
        }
    }

    // List all products
    public void listAllProducts(List<Product> products) {
        System.out.println("Listing all products:");
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() + ", Manufacturer: " + product.getManufacturer() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
    }

    // Find customer by ID
    public Customer findCustomerById(int id, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    // Reset customer password
    public void resetCustomerPassword(Customer customer, String newPassword) {
        customer.setPassword(hashPassword(newPassword)); // Using setPassword() to set new password
        System.out.println("Customer password reset successfully!");
    }

    // Find product by ID
    public Product findProductById(int id, List<Product> products) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
