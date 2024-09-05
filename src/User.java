import java.util.*;

class User {
    protected String username;
    protected String password;
    protected String phone;
    protected String email;

    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    // 修改密码
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }

    // 登录方法
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password reset successfully.");
    }
}
