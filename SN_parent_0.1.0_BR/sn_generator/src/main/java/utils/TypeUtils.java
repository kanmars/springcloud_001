package utils;

/**
 * Created by baolong on 2016/1/8.
 */
public class TypeUtils {
    public static String transDBType2JavaType(String typeName,String columnSize,String decimalDigits){
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)<=10){
            return "Integer";
        }
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)>10){
            return "Long";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)<=9){
            return "Integer";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)>9 && Integer.parseInt(columnSize) <= 17 ){
            return "Long";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize) >= 18 ){
            return "java.math.BigDecimal";
        }
        if(typeName.equals("VARCHAR")){
            return "String";
        }
        if(typeName.equals("VARCHAR2")){
            return "String";
        }
        if(typeName.equals("CHAR")){
            return "String";
        }
        if(!StringUtils.isEmpty(decimalDigits)&&Integer.parseInt(decimalDigits)>0){
            return "java.math.BigDecimal";
        }
        if((typeName.equals("TINYBLOB"))||(typeName.equals("BLOB"))||(typeName.equals("MEDIUMBLOB"))||(typeName.equals("LONGBLOB"))){
            return "byte[]";
        }
        throw new RuntimeException("无对应的数据类型"+typeName+"  "+ columnSize + "  "+decimalDigits);
    }
    public static String transDBType2MapperJavaType(String typeName,String columnSize,String decimalDigits){
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)<=10){
            return "java.lang.Integer";
        }
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)>10){
            return "java.lang.Long";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)<=9){
            return "java.lang.Integer";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)>9 && Integer.parseInt(columnSize) <= 17 ){
            return "java.lang.Long";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize) >= 18 ){
            return "java.math.BigDecimal";
        }
        if(typeName.equals("VARCHAR")){
            return "java.lang.String";
        }
        if(typeName.equals("VARCHAR2")){
            return "java.lang.String";
        }
        if(typeName.equals("CHAR")){
            return "java.lang.String";
        }
        if(!StringUtils.isEmpty(decimalDigits)&&Integer.parseInt(decimalDigits)>0){
            return "java.math.BigDecimal";
        }
        if((typeName.equals("TINYBLOB"))||(typeName.equals("BLOB"))||(typeName.equals("MEDIUMBLOB"))||(typeName.equals("LONGBLOB"))){
            return "[B";
        }
        throw new RuntimeException("无对应的数据类型"+typeName+"  "+ columnSize + "  "+decimalDigits);
    }
    public static String transDBType2MapperJDBCType(String typeName,String columnSize,String decimalDigits){
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)<=10){
            return "DECIMAL";
        }
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)>10){
            return "DECIMAL";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)<=9){
            return "DECIMAL";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)>9 && Integer.parseInt(columnSize) <= 17 ){
            return "DECIMAL";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize) >= 18 ){
            return "DECIMAL";
        }
        if(typeName.equals("VARCHAR")){
            return "VARCHAR";
        }
        if(typeName.equals("VARCHAR2")){
            return "VARCHAR";
        }
        if(typeName.equals("CHAR")){
            return "CHAR";
        }
        if(!StringUtils.isEmpty(decimalDigits)&&Integer.parseInt(decimalDigits)>0){
            return "DECIMAL";
        }
        if((typeName.equals("TINYBLOB"))||(typeName.equals("BLOB"))||(typeName.equals("MEDIUMBLOB"))||(typeName.equals("LONGBLOB"))){
            return "BLOB";
        }
        throw new RuntimeException("无对应的数据类型"+typeName+"  "+ columnSize + "  "+decimalDigits);
    }

    /**
     * 宏技术转换strValue为处理字符串，此工具方法，一般在controller中使用，所以不需要Blob
     * @param typeName
     * @param columnSize
     * @param decimalDigits
     * @param strValue
     * @return
     */
    public static String transStringVar2JavaType(String typeName,String columnSize,String decimalDigits,String strValue){
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)<=10){
            return "Integer.parseInt("+strValue+")";
        }
        if((typeName.equals("INT")||typeName.equals("BIGINT")) && Integer.parseInt(columnSize)>10){
            return "Long.parseLong("+strValue+")";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)<=9){
            return "Integer.parseInt("+strValue+")";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize)>9 && Integer.parseInt(columnSize) <= 17 ){
            return "Long.parseLong("+strValue+")";
        }
        if(typeName.equals("NUMBER") && Integer.parseInt(columnSize) >= 18 ){
            return "new java.math.BigDecimal("+strValue+")";
        }
        if(typeName.equals("VARCHAR")){
            return strValue;
        }
        if(typeName.equals("VARCHAR2")){
            return strValue;
        }
        if(typeName.equals("CHAR")){
            return strValue;
        }
        if(!StringUtils.isEmpty(decimalDigits)&&Integer.parseInt(decimalDigits)>0){
            return "new java.math.BigDecimal("+strValue+")";
        }
        throw new RuntimeException("无对应的数据类型"+typeName+"  "+ columnSize + "  "+decimalDigits);
    }
}
