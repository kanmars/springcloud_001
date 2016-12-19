import cn.kanmars.sn.dao.tddldao.TblUserInfoSerMapper;
import cn.kanmars.sn.entity.UserInfo;
import cn.kanmars.sn.util.IDCreaterPlusUtils;
import cn.kanmars.sn.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.Date;

/**
 * Created by zhaojiuyang on 2015/12/8.
 */
public class TestTddl {

    private static final Logger logger = LoggerFactory.getLogger(TestTddl.class);

    static TblUserInfoSerMapper tblUserInfoSerMapper;

    public synchronized static void main(String[] args) {
        logger.debug("start============>>>>>>>>>....");
        logger.info("init tblUserInfoSerMapper>>isDebugEnabled=" + logger.isDebugEnabled());
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        tblUserInfoSerMapper = (TblUserInfoSerMapper) context.getBean("tblUserInfoSerMapper");
        for (int i = 0; i < 1000; i++) {
            insert();
        }
        logger.info("=====" + tblUserInfoSerMapper.getUserInfoList());
        logger.info("=====" + tblUserInfoSerMapper.getUserInfo(1445001821L).getWx_nickName());
        logger.info("111111" + createPk());
        logger.debug("end============>>>>>>>>>....");
    }

    public static void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPk(createPk());
        userInfo.setWx_nickName("nickname" + RandomUtils.random(1, 10000));
        tblUserInfoSerMapper.insertUserInfo(userInfo);
    }

    public static Long createPk() {
        long origin = 0L;
        long d = System.currentTimeMillis();
        d = d - new Date(2015, 11, 1).getTime();
        logger.info(d+"");
        // 1449729872734
        long val = Long.valueOf(RandomUtils.random(0, 100000));

        long pk = 0L;
        try {
            pk = IDCreaterPlusUtils.setValue(origin, d, 1, 29);
            pk = IDCreaterPlusUtils.setValue(pk, 15, 30, 33);
            pk = IDCreaterPlusUtils.setValue(pk, val, 34, 39);
        } catch (Exception e) {
            logger.error("异常错误",e);
        }

        logger.info("pk1111111>>>" + pk);
        logger.info("2>>>" + IDCreaterPlusUtils.pl64(pk));
        logger.info("pk2222222>>>" + pk);
        return pk;
    }
}
