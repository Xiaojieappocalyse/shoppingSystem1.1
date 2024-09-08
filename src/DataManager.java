import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String USER_FILE = "E:\\test\\shoppingsystem1.1\\src\\users.txt";
    private static final String PRODUCT_FILE = "E:\\test\\shoppingsystem1.1\\src\\products.txt";
    private static final String HISTORY_FILE = "E:\\test\\shoppingsystem1.1\\src\\history.txt";

    public static void saveUsers(List<Customer> customers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Customer customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
        }
    }

    public static List<Customer> loadUsers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customers.add(Customer.fromString(line));
            }
        }
        return customers;
    }

    public static void saveProducts(List<Product> products) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getManufacturer() + "," + product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
        }
    }

    public static List<Product> loadProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Product product = new Product(Integer.parseInt(parts[0]), parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
                products.add(product);
            }
        }
        return products;
    }

    public static void saveShoppingHistory(List<Product> history) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (Product product : history) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getPrice());
                writer.newLine();
            }
        }
    }

    public static List<Product> loadShoppingHistory() throws IOException {
        List<Product> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Product product = new Product(Integer.parseInt(parts[0]), parts[1], "", Double.parseDouble(parts[2]), 0);
                history.add(product);
            }
        }
        return history;
    }
}
