package Case_Study.Model;

import java.io.Serializable;
import java.util.HashMap;

public class Buyer extends User implements Serializable {

    private HashMap<String, Integer> bought;

    public Buyer(String name, String password) {
        super(name, password);
        bought = new HashMap<>();
    }


    public HashMap<String, Integer> getBought() {
        return bought;
    }
    public void showBought() {
        for (String key : bought.keySet()) {
            System.out.println("Sản phẩm "+ key + " đã mua với số lượng là " + bought.get(key));
        }
    }

    public void setBought(HashMap<String, Integer> bought) {
        this.bought = bought;
    }

}
