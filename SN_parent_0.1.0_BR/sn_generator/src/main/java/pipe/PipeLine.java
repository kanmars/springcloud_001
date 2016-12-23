package pipe;

import context.Context;
import tablelist.TableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baolong on 2016/1/7.
 */
public class PipeLine {
    private List<Pipe> pipeLineList = new ArrayList<Pipe>();

    public void addPipe(Pipe p){
        pipeLineList.add(p);
    }

    public void exec(Context c,TableList tablelist) throws Exception{
        for(Object o : tablelist){
            c.put(Context.CURR_TABLE,(String)o);
            for(Pipe p:pipeLineList){
                p.exec(c);
            }
        }
    }
}
