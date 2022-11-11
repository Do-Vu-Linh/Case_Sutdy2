package Case_Study.Function;

import Case_Study.Data.Data;
import Case_Study.Model.*;
import com.jakewharton.fliptables.FlipTable;
import com.jakewharton.fliptables.FlipTableConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserManager implements Serializable {
    User user;
    Data data;
    Scanner sc = new Scanner(System.in);

    private static final ArrayList<Category> categories;

    static {
        categories = new ArrayList<>();
        categories.add(new Category("Smartphone"));
        categories.add(new Category("Laptop"));
        categories.add(new Category("Tablet"));
    }

    public UserManager(User user, Data data) {
        this.user = user;
        this.data = data;
    }

    public UserManager() {

    }

    public void add(ArrayList<Category> categories, Scanner sc) {
        if (user instanceof Seller) {
            try {
                System.out.println("Nhập tên sản phẩm ");
                String name = sc.nextLine();
                System.out.println("Nhập giá sản phẩm ");
                Double price = Double.parseDouble(sc.nextLine());
                System.out.println("Nhập số lượng sản phẩm ");
                Integer quantity = Integer.parseInt(sc.nextLine());
                System.out.println("Nhập loại sản phẩm");
                Category category = getCategoryByIndex(categories, sc);
                data.getProducts().add(new Product(getNewId(), name, price, quantity, category));
                data.saveProductFile();
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Category getCategoryByIndex(ArrayList<Category> categories, Scanner scanner) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.println("0. Không lựa chọn");
        int choice;
        try {
            do {
                System.out.println("Nhập lựa chọn ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    return null;
                }
                if (choice > 0 && choice <= categories.size()) {
                    return categories.get(choice - 1);
                }
                System.err.println("mời chọn lại");
            } while (choice < 0 || choice > categories.size());
        } catch (NumberFormatException | InputMismatchException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void delete(Scanner sc) {
        if (user instanceof Seller) {
            try {
                System.out.println("Nhập id bạn muốn xóa : ");
                int index = Integer.parseInt(sc.nextLine());
                Product productDelete;
                if ((productDelete = getProduct(checkIDExist(index))) != null) {
                    data.getProducts().remove(productDelete);
                    data.saveProductFile();
                } else {
                    System.err.println("Không tồn tại id!");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public void update(ArrayList<Category> categories, Scanner sc) {
        if (user instanceof Seller) {
            try {
                System.out.println("Nhập id sản phẩm bạn muốn cập nhật: ");
                int index = Integer.parseInt(sc.nextLine());
                Product productUpdate;
                if ((productUpdate = getProduct(checkIDExist(index))) != null) {
                    System.out.println("Nhập tên sản phẩm mới: ");
                    String name = sc.nextLine();
                    if (!name.equals("")) {
                        productUpdate.setName(name);
                    }
                    System.out.println("Nhập giá mới: ");
                    String price = sc.nextLine();
                    if (!price.equals("")) {
                        productUpdate.setPrice(Double.parseDouble(price));
                    }
                    System.out.println("Nhập số lượng mới:");
                    String quantity = sc.nextLine();
                    if (!quantity.equals("")) {
                        productUpdate.setQuantity(Integer.parseInt(quantity));
                    }
                    System.out.println("Chọn loại sản phẩm mới: ");
                    Category category;
                    if ((category = getCategoryByIndex(categories, sc)) != null) {
                        productUpdate.setCategory(category);
                    }
                    data.saveProductFile();
                } else {
                    System.err.println("Không có sản phẩm nào với ID của bạn nhập");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    public void displayByCategory(ArrayList<Category> categories, Scanner scanner) {
        System.out.println("Nhập loại sản phẩm cần hiển thị ");
        Category category = getCategoryByIndex(categories, scanner);
        if (category != null) {
            System.out.printf("%-10s%-10s%-15s%-20s%s", "ID", "Name", "Price", "Quantity", "Category\n");
            for (Product p : data.getProducts()) {
                if (p.getCategory().getName().equals(category.getName())) {
                    p.display();
                }
            }
        } else {
            System.err.println("Không loại sản phẩm nào được hiển thị");
        }
    }

    public void buy() {
        System.out.println("Nhập ID sản phẩm bạn muốn mua :");
        int index = Integer.parseInt(sc.nextLine());
        int id;
        if ((id = checkIDExist(index)) != -1) {
            System.out.println("Nhập số lượng: ");
            int amount = Integer.parseInt(sc.nextLine());

            if (amount > data.getProducts().get(id).getQuantity()) {
                System.out.println("Bạn nhập quá số lượng có sẵn !!!");
            } else {
                String productName = data.getProducts().get(id).getName();
                HashMap<String, Integer> bought = ((Buyer) user).getBought();
                if (bought.containsKey(productName)) {
                    int value = bought.get(productName);
                    bought.replace(productName, value + amount);
                } else {
                    bought.put(productName, amount);
                }
                System.out.println("Hóa đơn mua hàng là :" + productName + " số lượng " + amount);
                int value = data.getProducts().get(id).getQuantity();
                data.getProducts().get(id).setQuantity(value - amount);
                data.saveUserFile();
            }
        } else {
            System.out.println("Không có ID sản phẩm nào trùng khớp, mời thử lại :");
        }

    }


    public void display() {

        String[] headers = {"Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng", "Loại sản phẩm"};
        Object[][] data1 = new Object[data.getProducts().size()][6];
        for (int i = 0; i < data.getProducts().size(); i++) {
            data1[i] = new Object[]{data.getProducts().get(i).getId(), data.getProducts().get(i).getName(),
                    data.getProducts().get(i).getPrice(), data.getProducts().get(i).getQuantity(),
                    data.getProducts().get(i).getCategory().getName()};
        }
        System.out.println(FlipTableConverters.fromObjects(headers, data1));

    }

    private int checkIDExist(int id) {
        int flag = -1;
        for (int i = 0; i < data.getProducts().size(); i++) {
            int idProduct = data.getProducts().get(i).getId();
            if (idProduct == id) {
                flag = i;
                break;
            }
        }
        return flag;
    }

    public int getNewId() {
        int a = 0;
        for (Product p : data.getProducts()) {
            a = p.getId();
        }
        return ++a;
    }

    private Product getProduct(int index) {
        if (index == -1) return null;
        return data.getProducts().get(index);
    }

    public void menu() {

        if (user instanceof Seller) {
            do {
                System.out.println("Chào mừng nhà bán hàng " + user.getName() + " đã đăng nhập !!!");
                String[] headers = {"CỬA HÀNG CHUYÊN DOANH ĐIỆN TỬ"};
                String[][] data = {{"[1] Thêm sản phẩm "}, {"[2] Xóa sản phẩm theo ID "}, {"[3] Hiển thị tất cả sản phẩm"},
                        {"[4] Cập nhật thông tin sản phẩm"}, {"[5] Hiển thị sản phẩm theo Category"}, {"[0] Thoát chương trình"}};
                System.out.println(FlipTable.of(headers, data));
                System.out.println("Nhập lựa chọn");
                try {
                    int choice = Integer.parseInt(sc.nextLine());
                    if (choice < 0 || choice > 6) {
                        throw new RuntimeException();
                    }

                    switch (choice) {
                        case 1:
                            add(categories, sc);
                            break;
                        case 2:
                            delete(sc);
                            break;
                        case 3:
                            display();
                            break;
                        case 4:
                            update(categories, sc);
                            break;
                        case 5:
                            displayByCategory(categories, sc);
                            break;
                        case 6:
                            ((Buyer) user).showBought();
                            ;
                            break;
                        case 0:
                            System.exit(0);
                    }

                } catch (Exception e) {
                    System.err.println("Chọn lại lựa chọn!");
                }

            } while (true);
        } else {
            do {
                System.out.println("Chào mừng người mua " + user.getName() + " đến cửa hàng");
                String[] headers = {"CỬA HÀNG CHUYÊN DOANH ĐIỆN TỬ"};
                String[][] data = {{"[1] Hiển thị tất cả sản phẩm của cửa hàng là : "}, {"[2] Để mua sản phẩm"}, {"[3] Xem lại lịch sử mua hàng"},
                        {"[4] Hiển thị sản phẩm theo loại"}, {"[0] Thoát chương trình"}};
                System.out.println(FlipTable.of(headers, data));
                System.out.println("Nhập lựa chọn");
                try {
                    int choice2 = Integer.parseInt(sc.nextLine());
                    if (choice2 < 0 || choice2 > 4) {
                        throw new RuntimeException();
                    }
                    switch (choice2) {
                        case 1:
                            display();
                            break;
                        case 2:
                            buy();
                            break;
                        case 3:
                            ((Buyer) user).showBought();
                            break;
                        case 4:
                            displayByCategory(categories, sc);
                            break;
                        case 0:
                            System.exit(0);

                    }
                } catch (NumberFormatException | InputMismatchException e) {
                    e.printStackTrace();
                }
            } while (true);

        }
    }


}



