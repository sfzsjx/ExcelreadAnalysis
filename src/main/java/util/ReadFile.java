package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public ReadFile() {
    }

    /**
     * 读取某个文件夹下的所有文件
     */
    public static List readfile(String filepath) {
        List<String> list = new ArrayList<>();
        File file = new File(filepath);
         if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "\\" + filelist[i]);
                list.add(readfile.getAbsolutePath());
                return list;
            }
        }
        return null;
    }
}

