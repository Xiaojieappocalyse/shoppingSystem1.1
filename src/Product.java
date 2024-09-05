class Product {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String manufacturer;
    private double price;
    private int quantity;

    public Product(String name, String manufacturer, double price, int quantity) {
        this.id = idCounter++;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Manufacturer: " + manufacturer + ", Price: " + price + ", Quantity: " + quantity;
    }
}
