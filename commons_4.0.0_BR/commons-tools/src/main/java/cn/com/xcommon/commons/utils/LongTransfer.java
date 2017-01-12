package cn.com.xcommon.commons.utils;

/**
 * Created by baolong on 2017/1/12.
 */
public class LongTransfer {

    private static int jz_s = 62;

    /*char 62 ,
     用来处理62进制
     */
    private static char[] info = {
        '0','1','2','3','4','5','6','7','8','9',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    };

    public static String getStrFrom(long l){
        StringBuffer sb = new StringBuffer();
        if(l==0)return "0";
        int jz = jz_s;
        while(l/jz >= 0){
            if(l==0){
                break;
            }
            char c = info[(int)(l%jz)];
            sb.insert(0, c);
            l=l/jz;
        }
        return sb.toString();
    }

    public static long getLongFrom(String str){
        long result = 0;
        int jz = jz_s;
        int strlength = str.length();
        char[] cc = new char[strlength];
        str.getChars(0, strlength, cc, 0);
        for(int i=0,j=strlength;i<j;i++ ){
            int num = -1;
            char c = cc[i];
            if('0'<=c&&c<='9'){
                num = c-'0';
            }
            if('a'<=c&&c<='z'){
                num = c-'a'+10;
            }
            if('A'<=c&&c<='Z'){
                num = c-'A'+36;
            }
            result = result*jz + num;
        }

        return result;

    }

    public static void main(String[] args) throws Exception {
        for(int i=0;i<1000;i++){
            System.out.println("-----------------------");
            long l = IDCreaterPlusUtils.createSimpleLong();
            String s = LongTransfer.getStrFrom(l);
            System.out.println("原始pk:\t\t"+l);
            System.out.println("解密后的pk:\t"+getLongFrom(s));
            System.out.println("压缩后的pk:\t"+s);
            System.out.println("pk中的时间:\t"+IDCreaterPlusUtils.getDateTimeFromLong(l));
        }
    }
}
