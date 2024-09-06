class Admin extends User {

    public Admin() {
        super("admin", "ynuinfo#777"); // 默认管理员账户
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addProduct(Product[] products, Product product, int productCount) {
        products[productCount] = product;
        System.out.println("Product added successfully!");
    }

    public void modifyProduct(Product product, String newName, double newPrice, int newQuantity) {
        product.setName(newName);
        product.setPrice(newPrice);
        product.setQuantity(newQuantity);
        System.out.println("Product modified successfully!");
    }

    public void deleteProduct(Product[] products, int productId, int productCount) {
        for (int i = 0; i < productCount; i++) {
            if (products[i].getId() == productId) {
                products[i] = products[productCount - 1]; // 用最后一个产品覆盖要删除的产品
                products[productCount - 1] = null;
                System.out.println("Product deleted successfully!");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void listAllCustomers(Customer[] customers, int customerCount) {
        System.out.println("Listing all customers:");
        for (int i = 0; i < customerCount; i++) {
            System.out.println("Customer ID: " + i + ", Username: " + customers[i].getUsername());
        }
    }

    public void listAllProducts(Product[] products, int productCount) {
        System.out.println("Listing all products:");
        for (int i = 0; i < productCount; i++) {
            System.out.println(products[i]);
        }
    }

    public Customer findCustomerById(int id, Customer[] customers, int customerCount) {
        if (id >= 0 && id < customerCount) {
            return customers[id];
        }
        return null;
    }

    public void resetCustomerPassword(Customer customer, String newPassword) {
        customer.changePassword(newPassword);
        System.out.println("Customer password reset successfully!");
    }

    public Product findProductById(int id, Product[] products, int productCount) {
        if (id >= 0 && id < productCount) {
            return products[id];
        }
        return null;
    }
}
