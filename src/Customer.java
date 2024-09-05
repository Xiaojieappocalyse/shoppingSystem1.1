import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Customer {
    private static int idCounter = 1;
    private int id;
    private String username;
    protected String password;
    private double totalSpent;
    private ShoppingCart cart;
    private List<Product> shoppingHistory;

    public Customer(String username, String password) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.totalSpent = 0;
        this.cart = new ShoppingCart();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    public void removeFromCart(Product product) {
        cart.removeProduct(product);
    }

    public void checkout() {
        double total = cart.calculateTotal();
        System.out.println("Total cost: " + total);
        totalSpent += total;
        cart.clear();
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Username: " + username + ", Total Spent: " + totalSpent;
    }

    public void viewShoppingHistory() {
        if (shoppingHistory.isEmpty()) {
            System.out.println("Your shopping history is empty.");
        } else {
            System.out.println("Shopping History:");
            for (Product product : shoppingHistory) {
                System.out.println(product);
            }
        }
    }

    public Object getPassword() {
        return password;
    }
}

