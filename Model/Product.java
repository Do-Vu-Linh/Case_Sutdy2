package Case_Study.Model;

import java.io.Serializable;

public class Product implements Serializable {
    public Long INDEX = Long.valueOf(0);
    private int id;
    private String name;
    private double price;
    public Integer quantity;
private Category category;
    public Product(int size, String name, Double price, Integer quantity, Category category) {
        this.id = size;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product() {
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
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        if (category == null) {
            return "Sản phẩm{" +
                    "Mã ID=" + id +
                    ", Tên sản phẩm ='" + name + '\'' +
                    ", Giá sản phẩm =" + price +
                    ", Số lượng =" + quantity +
                    ", Loại sản phẩm = trống" +
                    '}';
        }
        return "Sản Phẩm{" +
                "Mã ID = " + id +
                ", Tên sản phẩm ='" + name + '\'' +
                ", Giá sản phẩm =" + price +
                ", Số lượng =" + quantity +
                ", Loại sản phẩm=" + category.getName() +
                '}';
    }
    public void display() {
        if (category != null) {
            System.out.printf("%-10s%-10s%-15s%-20s%s", id, name, price, quantity, category.getName() + "\n");
        } else {
            System.out.printf("%-10s%-10s%-15s%-20s%s", id, name, price, quantity, "null\n");
        }

    }
}

