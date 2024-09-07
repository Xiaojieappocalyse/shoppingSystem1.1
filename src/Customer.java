import java.util.*;
import java.util.regex.Pattern;

public class Customer extends User {
    private static int idCounter = 1;
    private int id;
    private String email;
    private double totalSpent;
    private ShoppingCart cart;
    private List<Product> shoppingHistory;
    private int loginAttempts;
    private boolean isLocked;

    // 构造函数
    public Customer(String username, String password, String email) {
        super(username, password);
        this.id = idCounter++;
        this.email = email;
        this.totalSpent = 0;
        this.cart = new ShoppingCart();
        this.shoppingHistory = new ArrayList<>();
        this.loginAttempts = 0;
        this.isLocked = false;
    }

    // 注册时的用户名和密码验证
    public static boolean validateUsername(String username) {
        return username.length() >= 5;
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W]).{9,}$";
        return Pattern.matches(regex, password);
    }

    // 登录方法，加入锁定账户的功能
    @Override
    public boolean login(String inputUsername, String inputPassword) {
        if (isLocked) {
            System.out.println("Account is locked.");
            return false;
        }

        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            loginAttempts = 0; // 成功登录，重置错误次数
            System.out.println("Login successful!");
            return true;
        } else {
            loginAttempts++;
            if (loginAttempts >= 5) {
                isLocked = true; // 错误超过5次，锁定账户
                System.out.println("Account locked due to multiple incorrect attempts.");
            } else {
                System.out.println("Incorrect password. Attempts remaining: " + (5 - loginAttempts));
            }
            return false;
        }
    }

    // 修改密码功能
    public void changePassword(String oldPassword, String newPassword) {
        // 判断用户输入的旧密码是否正确
        if (this.password.equals(oldPassword)) {
            // 验证新密码是否符合要求
            if (validatePassword(newPassword)) {
                this.password = newPassword;
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("New password does not meet the requirements.");
            }
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    // 忘记密码功能，随机生成新密码并模拟发送邮件
    public void resetPassword(String inputUsername, String inputEmail) {
        if (this.username.equals(inputUsername) && this.email.equals(inputEmail)) {
            String newPassword = generateRandomPassword();
            this.password = newPassword;
            System.out.println("A new password has been sent to your email: " + newPassword);
        } else {
            System.out.println("Username or email is incorrect.");
        }
    }

    // 生成随机密码
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder newPassword = new StringBuilder();
        Random rnd = new Random();
        while (newPassword.length() < 10) {
            int index = (int) (rnd.nextFloat() * chars.length());
            newPassword.append(chars.charAt(index));
        }
        return newPassword.toString();
    }

    // 添加商品到购物车
    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity); // 将商品添加到购物车
        System.out.println(quantity + " of " + product.getName() + " added to cart.");
    }

    // 从购物车移除商品
    public void removeFromCart(Product product) {
        cart.removeProduct(product); // 从购物车移除商品
        System.out.println(product.getName() + " removed from cart.");
    }

    // 结账功能
    public void checkout() {
        double total = cart.calculateTotal();
        if (total == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Total cost: " + total);
        System.out.println("Select payment method: 1. Alipay 2. WeChat 3. Bank Card");
        Scanner scanner = new Scanner(System.in);
        int paymentMethod = scanner.nextInt(); // 模拟选择支付方式

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

        // 支付成功，扣减库存
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantity(product.getQuantity() - quantity); // 扣减库存
        }

        totalSpent += total; // 更新总消费
        cart.clear(); // 清空购物车
        System.out.println("Checkout completed.");
    }

    // 获取总消费
    public double getTotalSpent() {
        return totalSpent;
    }

    // 查看购物历史
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

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Username: " + username + ", Total Spent: " + totalSpent;
    }
}