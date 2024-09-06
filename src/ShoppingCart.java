class ShoppingCart {

    public void addProduct(Product product, int quantity) {
        System.out.println("Added product: " + product.getName() + " (" + quantity + " items)");
    }

    public void removeProduct(Product product) {
        System.out.println("Removed product: " + product.getName());
    }

    public void checkout() {
        System.out.println("Cart checked out.");
    }

    public void viewHistory() {
        System.out.println("Viewing shopping history.");
    }
}
