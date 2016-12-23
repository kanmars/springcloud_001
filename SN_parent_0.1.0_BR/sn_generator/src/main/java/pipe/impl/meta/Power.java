package pipe.impl.meta;

/**
 * Created by baolong on 2016/8/22.
 */
public class Power {
    /**小文本-增删改查*/
    public static final String POWER_ADDWRITEREAD="YYYYYY";
    /**小文本-增删改查*/
    public static final String POWER_ADDWRIT="NNYYYY";
    /**小文本-可增可查不可改*/
    public static final String POWER_ADDREAD="YYYYNN";
    /**小文本-只读查，例如最后跟新时间*/
    public static final String POWER_READ="YYNYYN";

    /**大文本-增删改查*/
    public static final String POWER_BIGCONTENT_ADDWRITE="YNYYYY";
    /**大文本-可增可查不可改*/
    public static final String POWER_BIGCONTENT_ADDREAD="YNYYNN";
}
