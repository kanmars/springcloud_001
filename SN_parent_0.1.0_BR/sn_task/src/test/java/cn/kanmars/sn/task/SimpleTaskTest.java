package cn.kanmars.sn.task;

import cn.kanmars.sn.bean.TaskBasicConfig;
import cn.kanmars.sn.step.IStep;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by baolong on 2016/3/22.
 */
public class SimpleTaskTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:No_Quertzspring.xml"});
        IStep istep = (IStep)context.getBean("***");//teamInvestAmountAnalysisTask,memberInvestAmountAnalysisTask;等
        TaskBasicConfig config = new TaskBasicConfig();
        config.setBusinessInfo("-30");
        List list = istep.queryTaskInfo(config,null);
        if(!CollectionUtils.isEmpty(list)){
            for(Object info : list ){
                istep.runTask(info);
            }
        }
    }
}
