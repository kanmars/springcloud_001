package cn.kanmars.sn.dyndata;

/**
 * Created by baolong on 2016/12/20.
 */
public interface DynamicDataSourceRouter {
    public String router(Object partionKey);
}
