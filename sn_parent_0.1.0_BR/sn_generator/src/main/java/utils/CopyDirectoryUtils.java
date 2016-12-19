package utils;

import java.io.*;
import java.util.List;

/**
 * Created by baolong on 2016/1/11.
 */
public class CopyDirectoryUtils {

    public static void main(String[] args) {
        deleteDirectory("E:/sn");
    }

    /**
     * 删除某个文件夹
     * @param dest
     */
    public static void deleteDirectory(String dest){
        if(dest==null || dest.trim().equals("") || !new File(dest).exists()){
            return;
        }
        File[] file = (new File(dest)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                file[i].delete();
            }
            if (file[i].isDirectory()) {
                deleteDirectory(file[i].getAbsolutePath());
            }
        }
        new File(dest).delete();
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 复制文件夹，复制的时候，跳过一些文件夹
     * @param sourceDir
     * @param targetDir
     * @param jumpDirList
     * @param jumpFileList
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir, List<String> jumpDirList,List<String> jumpFileList)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                boolean needJump=false;
                for(String s : jumpFileList){
                    if(s.equals(file[i].getName())){
                        needJump = true;
                        break;
                    }
                }
                //如果当前文件夹需要跳过，则跳过
                if(needJump) continue;
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                boolean needJump=false;
                for(String s : jumpDirList){
                    if(s.equals(file[i].getName())){
                        needJump = true;
                        break;
                    }
                }
                //如果当前文件夹需要跳过，则跳过
                if(needJump) continue;
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2,jumpDirList,jumpFileList);
            }
        }
    }
}
