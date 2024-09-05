class ShoppingCart {
    private Product[] products;
    private int[] quantities;
    private int count;

    public ShoppingCart() {
        this.products = new Product[100];
        this.quantities = new int[100];
        this.count = 0;
    }

    public void addProduct(Product product, int quantity) {
        products[count] = product;
        quantities[count] = quantity;
        count++;
    }

    public void removeProduct(Product product) {
        for (int i = 0; i < count; i++) {
            if (products[i] == product) {
                products[i] = null;
                quantities[i] = 0;
                System.out.println("Product removed from cart.");
                break;
            }
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < count; i++) {
            if (products[i] != null) {
                total += products[i].getPrice() * quantities[i];
                products[i].reduceQuantity(quantities[i]);
            }
        }
        return total;
    }

    public void clear() {
        this.products = new Product[100];
        this.quantities = new int[100];
        this.count = 0;
    }
}

