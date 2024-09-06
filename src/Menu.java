import java.util.Scanner;

// Menu.java
public abstract class Menu {
    protected Scanner scanner; // 共享 Scanner 对象

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    // 显示菜单，具体内容由子类实现
    public abstract void displayMenu();

    // 处理用户选择，具体逻辑由子类实现
    public abstract void handleChoice(int choice);
}
