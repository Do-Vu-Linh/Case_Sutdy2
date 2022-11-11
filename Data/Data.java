package Case_Study.Data;

import Case_Study.IOFile.IOFile;
import Case_Study.Model.Product;
import Case_Study.Model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<Product> products;
    private ArrayList<User> users;

    public Data() {
        IOFile productFile = new IOFile(this);
        products = (ArrayList<Product>) productFile.readFile("product.txt");
        users = (ArrayList<User>) productFile.readFile("user.txt");
        if(products == null){
            products = new ArrayList<>();
        }
        if(users == null){
            users = new ArrayList<>();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    public void saveUserFile(){
        IOFile file = new IOFile(users);
        file.writefile("user.txt", users);
    }

    public void saveProductFile(){
        IOFile file = new IOFile(products);
        file.writefile("product.txt", products);
    }




}
