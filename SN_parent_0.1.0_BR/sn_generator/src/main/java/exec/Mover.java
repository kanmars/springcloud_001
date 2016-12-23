package exec;

import mover.MoverPatternerUtils;
import utils.CopyDirectoryUtils;

import java.io.*;
import java.util.*;

/**
 * 一键分身功能，作用是：
 * 1、指定源文件夹，指定目标文件夹，，警告！！！！！目标文件夹会清空掉，因此一定要小心
 * 2、指定目标名称列表，例如SN-admin替换为gfs-admin......
 * 3、指定字符集
 * 4、指定文件类型，例如.css  .js  .java  .properties等
 * 5、全局替换文件夹，将文件夹列表按照从底层到高层的顺序，替换掉文件中的SN-admin文件夹为gfs-admin
 * 6、全局性搜索文件，将文件中的SN-admin，替换为gfs-admin
 *
 *
 * 使用说明：
 * 1、设置被搬家文件夹srcDir
 * 2、设置目标文件夹dstDir
 * 3、设置跳过文件夹和跳过文件列表jumpDirs,jumpFiles
 * 4、设置替换内容列表replaceList：包括项目名称，端口列表或者其他需要替换的内容
 * 5、设置需要替换的文件的列表的后缀名，needChangeTypes。该功能为避免png等无需替换的内容被误操作//此步骤一般不需操作
 * 之后以Main方法运行，即可生成目标代码
 *
 * 替换规则可见mover.MoverPatternerUtils:
 *      三类字符串会被替换
 *
 *  Created by baolong on 2016/1/11.
 *
 */
public class Mover {

    public static String FILE_SEPARATOR = "\\";
    public static String FILE_SEPARATOR_SPILTER = "\\\\";

    public static void main(String[] args) throws Exception {
        String srcDir = "E:\\work\\GFS_NEW_IntenlliJ\\SN\\branches\\SN-parent_0.0.1_BR";
        //警告！！！！！目标文件夹会被清空掉，因此一定要小心确认
        String dstDir = "E:\\FGATE";
        //列出要替换的名称列表
        List<String> replaceList = new ArrayList<String>();
        replaceList.add("SN,FGATE");
        replaceList.add("Sn,Fgate");
        replaceList.add("sn,fgate");
        replaceList.add("神农后台管理系统,金融系统统一网关");
        replaceList.add("神农,金融");
        replaceList.add("20901,20901");//admin端口
        replaceList.add("20902,20902");//inapi端口
        replaceList.add("20903,20903");//outapi端口
        replaceList.add("20904,20904");//task端口
        replaceList.add("20905,20905");//web端口
        replaceList.add("20880,20880");//service dubbo端口

        exec(srcDir,dstDir,replaceList);
    }

    public static void exec(String srcDir,String dstDir,List<String> replaceList) throws Exception{
        CopyDirectoryUtils.deleteDirectory(dstDir);
        List<String> jumpDirs = new ArrayList<String>();
        jumpDirs.add(".svn");
        jumpDirs.add(".idea");
        jumpDirs.add("target");
        jumpDirs.add(".settings");
        List<String> jumpFiles = new ArrayList<String>();
        jumpFiles.add("SN-parent.iml");
        jumpFiles.add("SN-admin.iml");
        jumpFiles.add("SN-dao.iml");
        jumpFiles.add("SN-dubbo.iml");
        jumpFiles.add("SN-generator.iml");
        jumpFiles.add("SN-inapi.iml");
        jumpFiles.add("SN-outapi.iml");
        jumpFiles.add("SN-service.iml");
        jumpFiles.add("SN-task.iml");
        jumpFiles.add("SN-web.iml");
        jumpFiles.add(".project");
        CopyDirectoryUtils.copyDirectiory(srcDir,dstDir,jumpDirs,jumpFiles);

        //-----------------------------以下内容轻易别修改----------------------------------------------------------
        String charset = "utf-8";

        //1、对文件夹和文件进行替换
        replaceDirs(dstDir, replaceList);
        //2、对文件名称进行替换
        replaceFilesName(dstDir,replaceList);
        //3、对文件内容进行替换
        List<String> needChangeTypes = new ArrayList<String>();
        needChangeTypes.add(".css");
        needChangeTypes.add(".html");
        needChangeTypes.add(".ftl");
        needChangeTypes.add(".bat");
        needChangeTypes.add(".properties");
        needChangeTypes.add(".sh");
        needChangeTypes.add(".js");
        needChangeTypes.add(".svg");
        needChangeTypes.add(".txt");
        needChangeTypes.add(".xml");
        needChangeTypes.add(".java");
        needChangeTypes.add(".jsp");
        changeFileContext(dstDir,needChangeTypes,replaceList,charset);
    }



    public static void replaceDirs(String dstDir,List<String> replaceList){
        List<String> directoryLists = new ArrayList<String>();
        getDirectoryList(dstDir, directoryLists);
        //生成一个待替换列表，从底层开始向顶层替换
        //0、生成最终数据结构Map<层数，文件夹名称的列表>
        HashMap<Integer,List<String>> ceng_filename = new HashMap<Integer,List<String>>();
        //1、对directoryLists进行遍历，按照不同的层级进行划分，寻找到符合replaceList条件的文件夹，并放入到ceng_filename中
        for(String dir:directoryLists){
            String[] dirs = dir.split(FILE_SEPARATOR_SPILTER);
            //如果最后一个路径，在replaceList中包含的列表中，则加入tong中
            String last = dirs[dirs.length-1];
            boolean needChange = false;
            for(String s : replaceList){
                if(last.indexOf(s.split(",")[0])>=0){
                    needChange = true;
                    break;
                }
            }
            //如果发现文件夹是需要修改的，则加入到ceng_filename中
            if(needChange){
                int length = dirs.length;
                List<String> tong = ceng_filename.get(length);
                if(tong == null){
                    tong = new ArrayList<String>();
                    ceng_filename.put(length,tong);
                }
                tong.add(dir);
            }
        }
        //按照从底层到高层，开始对文件夹进行替换处理，仅按照replaceList的顺序替换一次
        //获取最大层数
        int max_ceng=0;
        for(Integer i : ceng_filename.keySet()){
            if(i>max_ceng){
                max_ceng = i;
            }
        }
        //从底层到高层开始替换
        for(int i= max_ceng;i>0;i--){
            List<String> tong = ceng_filename.get(i);
            if(tong == null) continue;
            for(String dir:tong){
                String[] dirs = dir.split(FILE_SEPARATOR_SPILTER);
                //如果最后一个路径，在replaceList中包含的列表中，则加入tong中
                String last = dirs[dirs.length-1];
                String changeStr = "";
                for(String s : replaceList){
                    if(last.indexOf(s.split(",")[0])>=0){
                        changeStr = s;
                        break;
                    }
                }
                String new_name = "";
                for(int path_i = 0;path_i<dirs.length;path_i++){
                    if(path_i!=0)new_name=new_name+File.separator;
                    if(path_i!=dirs.length-1)new_name=new_name+dirs[path_i];//非最后一个直接加路径
                    if(path_i==dirs.length-1)new_name=new_name+MoverPatternerUtils.transStr(dirs[path_i],changeStr.split(",")[0],changeStr.split(",")[1]);//用正则表达式替换文件夹名称
                }
                File oldDir = new File(dir);
                File newDir = new File(new_name);
                boolean success = oldDir.renameTo(newDir);
                if(!success)throw  new RuntimeException("运行时文件夹替换错误["+oldDir.getAbsolutePath()+"]["+newDir.getAbsolutePath()+"]");
                if(success) System.out.println("文件夹替换成功["+oldDir.getAbsolutePath()+"]["+newDir.getAbsolutePath()+"]");
            }
        }

    }

    /**
     * 获取文件夹的列表
     * @param dstDir
     * @param dirs
     */
    public static void getDirectoryList(String dstDir,List<String> dirs){
        if(dstDir==null || dstDir.trim().equals("") || new File(dstDir).isFile()){
            return;
        }
        File[] file = (new File(dstDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            //过滤特殊文件
            if(jumpFile(file[i].getAbsolutePath()))continue;
            //正常文件处理
            if (file[i].isDirectory()) {
                dirs.add(file[i].getAbsolutePath());
            }
            getDirectoryList(file[i].getAbsolutePath(),dirs);
        }
    }


    public static void replaceFilesName(String dstDir, List<String> replaceList){
        List<String> fileLists = new ArrayList<String>();
        getFileList(dstDir, fileLists);
        //生成一个待替换列表，从底层开始向顶层替换
        //0、生成最终数据结构Map<层数，文件夹名称的列表>
        HashMap<Integer,List<String>> ceng_filename = new HashMap<Integer,List<String>>();
        //1、对directoryLists进行遍历，按照不同的层级进行划分，寻找到符合replaceList条件的文件夹，并放入到ceng_filename中
        for(String file:fileLists){
            String[] dirs = file.split(FILE_SEPARATOR_SPILTER);
            //如果最后一个路径，在replaceList中包含的列表中，则加入tong中
            String last = dirs[dirs.length-1];
            boolean needChange = false;
            for(String s : replaceList){
                if(last.indexOf(s.split(",")[0])>=0){
                    needChange = true;
                    break;
                }
            }
            //如果发现文件夹是需要修改的，则加入到ceng_filename中
            if(needChange){
                int length = dirs.length;
                List<String> tong = ceng_filename.get(length);
                if(tong == null){
                    tong = new ArrayList<String>();
                    ceng_filename.put(length,tong);
                }
                tong.add(file);
            }
        }
        //按照从底层到高层，开始对文件夹进行替换处理，仅按照replaceList的顺序替换一次
        //获取最大层数
        int max_ceng=0;
        for(Integer i : ceng_filename.keySet()){
            if(i>max_ceng){
                max_ceng = i;
            }
        }
        //从底层到高层开始替换
        for(int i= max_ceng;i>0;i--){
            List<String> tong = ceng_filename.get(i);
            if(tong == null) continue;
            for(String dir:tong){
                String[] dirs = dir.split(FILE_SEPARATOR_SPILTER);
                //如果最后一个路径，在replaceList中包含的列表中，则加入tong中
                String last = dirs[dirs.length-1];
                String changeStr = "";
                for(String s : replaceList){
                    if(last.indexOf(s.split(",")[0])>=0){
                        changeStr = s;
                        break;
                    }
                }
                String new_name = "";
                for(int path_i = 0;path_i<dirs.length;path_i++){
                    if(path_i!=0)new_name=new_name+File.separator;
                    if(path_i!=dirs.length-1)new_name=new_name+dirs[path_i];//非最后一个直接加路径
                    if(path_i==dirs.length-1)new_name=new_name+MoverPatternerUtils.transStr(dirs[path_i], changeStr.split(",")[0], changeStr.split(",")[1]);//用正则表达式替换文件名称
                }
                File oldDir = new File(dir);
                File newDir = new File(new_name);
                boolean success = oldDir.renameTo(newDir);
                if(!success)throw  new RuntimeException("运行时文件替换错误["+oldDir.getAbsolutePath()+"]["+newDir.getAbsolutePath()+"]");
                if(success) System.out.println("文件替换成功["+oldDir.getAbsolutePath()+"]["+newDir.getAbsolutePath()+"]");
            }
        }
    }

    /**
     * 获取文件的列表
     * @param dstDir
     * @param files
     */
    public static void getFileList(String dstDir,List<String> files){
        if(dstDir==null || dstDir.trim().equals("") || new File(dstDir).isFile()){
            return;
        }
        File[] file = (new File(dstDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            //过滤特殊文件
            if(jumpFile(file[i].getAbsolutePath()))continue;
            //正常文件处理
            if(file[i].isFile()){
                files.add(file[i].getAbsolutePath());
            }
            if(file[i].isDirectory()){
                getFileList(file[i].getAbsolutePath(), files);
            }
        }
    }


    /**
     *
     * @param dstDir        目标文件夹
     * @param jumpTypes      跳过的文件类型
     */
    /**
     * 替换文件文本内容
     * @param dstDir                目标文件夹
     * @param needChangeTypes       需要替换的文件类型
     * @param replaceList           需要替换的内容的清单
     * @param charset               需要替换的字符集
     */
    public static void changeFileContext(String dstDir,List<String> needChangeTypes,List<String> replaceList,String charset){
        List<String> fileLists = new ArrayList<String>();
        getFileList(dstDir, fileLists);
        //判断文件是否需要跳过
        Set<String> fileTypeSet = new HashSet<String>();
        for(String file:fileLists){
            //记录所有的文件类型
            if(file.contains(".")){
                fileTypeSet.add(file.substring(file.lastIndexOf(".")));
            }else{
                //无文件类型则不处理
                continue;
            }
            boolean needChange = false;
            for(String type:needChangeTypes){
                if(file.endsWith(type)){
                    needChange = true;
                }
            }
            //如果不需要替换，则跳出
            if(!needChange)continue;
            //如果类型检查通过，则进行文件替换处理
            String fileCotext = "";
            try {
                fileCotext =readContext(file,charset);
                //对文件进行处理
                String newFileContext = fileCotext;
                for(String changeStr:replaceList){
                    newFileContext =  MoverPatternerUtils.transStr(newFileContext, changeStr.split(",")[0], changeStr.split(",")[1]);
                }
                //重新写入文件
                writeContext(file,newFileContext,charset);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("file["+file+"]fileCotext:\r\n"+fileCotext);
            }
        }

        System.out.println("文件类型清单为：[" + fileTypeSet + "]");
    }

    public static String readContext(String filePath,String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        InputStream is = new FileInputStream(new File(filePath));
        byte[] tmp = new byte[1024];
        int count =0;
        while((count = is.read(tmp))>=0){
            bao.write(tmp, 0, count);
        }
        is.close();
        sb.append(new String(bao.toByteArray(),charset));
        return sb.toString();
    }

    public static void writeContext(String filePath,String context,String charset) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)),charset));
        bw.write(context);
        bw.close();
    }

    public static boolean jumpFile(String file){
        if(file.contains("svn")){return true;}
        if(file.contains(".idea")){return true;}
        return false;
    }

}
