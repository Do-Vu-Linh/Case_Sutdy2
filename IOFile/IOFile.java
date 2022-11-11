package Case_Study.IOFile;

import java.io.*;

public class IOFile implements Serializable {
    Object data;

    public IOFile(Object data) {
        this.data = data;
    }

    public  void writefile(String string, Object obj) {
        File file = new File(string);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  Object readFile(String string) {
        File file = new File(string);
        Object users = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            while (fis.available() > 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = ois.readObject();
            }
            fis.close();


        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();

        }
        return users;
    }
}
