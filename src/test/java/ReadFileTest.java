import util.ReadFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFileTest {
    public static void main(String[] args) {
        String path = "C:\\Users\\hadoop\\Documents\\test";
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            String[] filelist = file.list();

            for (int i = 0; i < filelist.length; i++) {

                list.add( path + "\\" + filelist[i]);

            }
            System.out.println(list.size());
        }

    }
}
