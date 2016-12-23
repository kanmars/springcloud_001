package utils;

import java.io.File;

/**
 * Created by baolong on 2016/1/8.
 */
public class FileUtils {
    public static boolean mkdirs(String path){
        File f = new File(path);
        f.mkdirs();
        return f.exists() && f.isDirectory();
    }

    public static void main(String[] args) {
        boolean b = mkdirs("G:\\c\\d\\e\\ff");
        System.out.println(b);
    }
}
