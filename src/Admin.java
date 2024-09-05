
class Admin {
    private String username = "admin";
    private String password = "ynuinfo#777";

    // 登录方法
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }


    // 修改管理员密码
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // 重置客户密码
    public void resetCustomerPassword(Customer customer, String newPassword) {
        customer.setPassword(newPassword);
    }

    // 查询客户信息
    public Customer findCustomerById(int customerId, Customer[] customers, int customerCount) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i] != null && customers[i].getId() == customerId) {
                return customers[i];
            }
        }
        return null;
    }

    // 删除客户信息
    public void deleteCustomer(Customer[] customers, int customerId, int customerCount) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i] != null && customers[i].getId() == customerId) {
                customers[i] = null;
                System.out.println("Customer deleted successfully!");
                break;
            }
        }
    }

    // 列出所有客户
    public void listAllCustomers(Customer[] customers, int customerCount) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i] != null) {
                System.out.println(customers[i]);
            }
        }
    }

    // 添加商品
    public void addProduct(Product[] products, Product product, int productCount) {
        products[productCount] = product;
        System.out.println("Product added successfully!");
    }

    // 修改商品信息
    public void modifyProduct(Product product, String newName, double price, double newPrice) {
        product.setName(newName);
        product.setPrice(newPrice);
        System.out.println("Product modified successfully!");
    }

    // 删除商品
    public boolean deleteProduct(Product[] products, int productId, int productCount) {
        for (int i = 0; i < productCount; i++) {
            if (products[i] != null && products[i].getId() == productId) {
                products[i] = null;
                System.out.println("Product deleted successfully!");
                break;
            }
        }
        return false;
    }

    // 查询商品
    public Product findProductById(int productId, Product[] products, int productCount) {
        for (int i = 0; i < productCount; i++) {
            if (products[i] != null && products[i].getId() == productId) {
                return products[i];
            }
        }
        return null;
    }

    // 列出所有商品
    public void listAllProducts(Product[] products, int productCount) {
        for (int i = 0; i < productCount; i++) {
            if (products[i] != null) {
                System.out.println(products[i]);
            }
        }
    }
}


