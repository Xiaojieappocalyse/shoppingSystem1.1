import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShoppingCart {
    // A map to store products and their quantities
    private Map<Product, Integer> productsInCart = new HashMap<>();

    // Add a product to the cart
    public void addProduct(Product product, int quantity) {
        // Add the product to the cart or update the quantity if it's already in the cart
        productsInCart.put(product, productsInCart.getOrDefault(product, 0) + quantity);
        System.out.println("Added product: " + product.getName() + " (" + quantity + " items)");
    }

    // Remove a product from the cart
    public void removeProduct(Product product) {
        if (productsInCart.containsKey(product)) {
            productsInCart.remove(product);
            System.out.println("Removed product: " + product.getName());
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    // Calculate the total cost of the products in the cart
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }

    // Get the products in the cart
    public Map<Product, Integer> getProducts() {
        return productsInCart;
    }

    // Clear the cart
    public void clear() {
        productsInCart.clear();
        System.out.println("The cart has been cleared.");
    }

    // Checkout the cart
    public void checkout() {
        double total = calculateTotal();
        if (total == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Your total is: " + total);
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

        // Simulate stock reduction and clear the cart after payment
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantity(product.getQuantity() - quantity); // Reduce the stock
        }

        clear(); // Clear the cart after checkout
        System.out.println("Checkout completed.");
    }

    // View cart contents
    public void viewCart() {
        if (productsInCart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart contains:");
            for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(product.getName() + " (Quantity: " + quantity + ", Price per item: " + product.getPrice() + ")");
            }
        }
    }

    // View shopping history (optional for shopping history tracking)
    public void viewHistory() {
        System.out.println("Viewing shopping history.");
    }
}
