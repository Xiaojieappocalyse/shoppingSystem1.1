import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class User {
    protected String username;
    protected String password;
    protected int id;
    protected String email;

    public User(String username, String password) {
        this.username = username;
        this.password = hashPassword(password);
    }

    public abstract boolean login(String inputUsername, String inputPassword);

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public static boolean validateUsername(String username) {
        return username != null && username.length() >= 5;
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 9) return false;
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial;
    }

    protected static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(hashPassword(oldPassword))) {
            this.password = hashPassword(newPassword);
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    protected abstract void setId(int id);

    protected abstract void setEmail(String email);
}
