class Customer extends User {
    private ShoppingCart cart = new ShoppingCart();

    public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    public void removeFromCart(Product product) {
        cart.removeProduct(product);
    }

    public void checkout() {
        System.out.println("Checking out...");
        cart.checkout();
    }

    public void viewShoppingHistory() {
        cart.viewHistory();
    }
}
