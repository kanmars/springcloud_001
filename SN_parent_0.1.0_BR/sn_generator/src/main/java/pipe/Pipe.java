package pipe;

import context.Context;

/**
 * Created by baolong on 2016/1/7.
 */
public interface Pipe {
    public void exec(Context c) throws Exception;
}
