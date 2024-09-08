import java.util.List;

public class Admin extends User {

    public Admin() {
        super("admin", "ynuinfo#777"); // Default admin account
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
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
        customer.changePassword(customer.getPassword(), newPassword); // Using getPassword() to retrieve old password
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
