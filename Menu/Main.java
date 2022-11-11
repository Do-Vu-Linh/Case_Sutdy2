package Case_Study.Menu;

import Case_Study.Data.Data;
import Case_Study.Model.Buyer;
import Case_Study.Model.Seller;
import Case_Study.Model.User;
import com.jakewharton.fliptables.FlipTable;

import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main implements Serializable {

    static Scanner sc = new Scanner(System.in);

    public static void login(Data data) {

        while (true) {
            try {
                System.out.println("Nhập tên đăng nhập :");
                String name = sc.nextLine();
                System.out.println("Nhập mật khẩu :");
                String password = sc.nextLine();
                User user = null;
                for (User i : data.getUsers()) {
                    if (name.equals(i.getName()) && password.equals(i.getPassword())) {
                        user = i;
                        break;
                    }
                }
                if (user != null) {
                    user.menu(data);
                } else {
                    System.out.println(" Sai mật khẩu nhập, mời nhập lại");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            data.saveUserFile();
        }
    }

    public static void register(Data data) {

        try {
            System.out.println("Nhập tên đăng kí");
            String name = sc.nextLine();
            boolean flag = true;
            for (User i : data.getUsers()) {
                if (name.equals(i.getName())) {
                    System.out.println("Tên đăng nhập đã tồn tại, vui lòng đặt tên khác");
                    flag = false;
                    break;
                }
            }

            if (flag) {
                System.out.println("Nhập mật khẩu đăng kí:");
                System.out.println("Yêu cầu : Có 1 số từ 0-9");
                System.out.println("Có cả  chữ hoa, chữ thường, 1 kí đặc biệt");
                System.out.println("Mật khẩu không có khoảng trắng, và có ít nhất 8 kí tự");
                String password = sc.nextLine();
                Boolean b3 = Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", password);
                if (b3) {
                    User user = new Buyer(name, password);
                    data.getUsers().add(user);
                    System.out.println("Đăng kí tài khoản thành công");
                    data.saveUserFile();
                } else {
                    System.out.println("Đăng kí tài khoản không thành công");
                }
            }
        } catch (Exception e) {
            System.out.println("Nhập sai định dạng");;
        }
    }


    public static void main(String[] args) {

        Data data = new Data();
        User user1 = new Seller("user1", "123");
        User user2 = new Buyer("user2", "123");
        data.getUsers().add(user2);
        data.getUsers().add(user1);
        do {
            try {

            String[] headers = {"CỬA HÀNG CHUYÊN DOANH ĐIỆN TỬ"};
            String[][] data1 = {{ "[1] Đăng nhập tài khoản  " }, {"[2] Đăng kí tài khoản khách hàng "}, };
            System.out.println(FlipTable.of(headers, data1));
            System.out.println("Nhập lựa chọn");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    login(data);
                    break;
                case 2:
                    register(data);
                    break;
            }
        }catch (Exception e)
        {
            System.out.println("Nhập sai định dạng");
        }

        } while (true);
    }
}
