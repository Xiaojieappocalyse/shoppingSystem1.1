public class Product {
    private static int idCounter = 0;
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

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer; // Added getter for manufacturer
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", price=" + price
                + ", quantity=" + quantity + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}