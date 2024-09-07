abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 抽象方法：登录
    public abstract boolean login(String username, String password);

    // 通用方法：获取用户名
    public String getUsername() {
        return username;
    }

    // 通用方法：修改密码
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully!");
    }

}
