package cn.kanmars.sn.util;


import cn.com.xcommon.commons.utils.IDCreaterPlusUtils;
import cn.com.xcommon.frame.exception.SnCommonException;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.DateUtils;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.dao.TblSysSequenceMapper;
import cn.kanmars.sn.entity.TblSysSequence;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 序列ID生成工具类，使用示例<br>
 * System.out.println(SysSequenceUtils.generateStringId("TEST_SEQ"));
    drop table if exists tbl_sys_sequence;
    create table tbl_sys_sequence
        (
        id                   int(64) not null comment 'id',
        key_value            VARCHAR(32) not null comment '序列Key',
        seq_desc             VARCHAR(64) not null comment '序列描述',
        top                  VARCHAR(32) not null comment '前缀',
        suffix               VARCHAR(32) not null comment '后缀',
        curr_value           int(64) not null comment '当前值',
        batch_size           int(64) not null comment '递增数量',
        create_time          char(14) not null comment '添加时间',
        up_time              char(14) not null comment '更新时间',
        primary key (id)
        ) ENGINE MYISAM;

        alter table tbl_sys_sequence comment '系统ID表';
 * 使用场景：有数据库访问，需要数据生成序列的系统，例如sn-admin,sn-service,sn-task等，严禁sn-web和sn-zuul使用
 */
@Transactional
public class SysSequenceUtils implements ApplicationContextAware {

    private static HLogger logger= LoggerManager.getLoger("SysSequenceUtils");
    private static final String TYPE_DB="DB";
    private static final String TYPE_INNER="INNER";
    private static String type=TYPE_DB;//DB或者INNER，代表分别在数据库层生成序列，或者在内部直接生成序列

    private static ApplicationContext applicationContext;

    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SysSequenceUtils.applicationContext = applicationContext;
    }

    /**获取流水号的锁定*/
    private static final Lock getSeqNoLock = new ReentrantLock();

    /**序号的MAP*/
    private static final ConcurrentHashMap<String, IdSpace> seqMap = new ConcurrentHashMap<String, IdSpace>();

    private TblSysSequenceMapper tblSysSequenceMapper_;

    private static TblSysSequenceMapper tblSysSequenceMapper;

    /**
     * 对序列生成Mapper进行赋值
     */
    @PostConstruct
    public synchronized void init(){
        try{
            tblSysSequenceMapper_ = (TblSysSequenceMapper)applicationContext.getBean("tblSysSequenceMapper");
            tblSysSequenceMapper = tblSysSequenceMapper_;
            if(tblSysSequenceMapper_==null){
                logger.info("SysSequenceUtils在初始化时发生异常，选择使用内部序列生成器TYPE_INNER");
                type = TYPE_INNER;
            }
            //尝试从数据库中随机查一条数据出来，如果没有报错，则正常，如果报错了，则类型修改为TYPE_INNER
            TblSysSequence tblSysSequence = new TblSysSequence();
            tblSysSequence.setId(1);
            tblSysSequence = tblSysSequenceMapper_.select(tblSysSequence);
        }catch (Exception e){
            logger.debug("SysSequenceUtils在初始化时发生异常，选择使用内部序列生成器TYPE_INNER", e);
            type = TYPE_INNER;
        }
    }

    /**
     * 获取一个ID，有前后缀，无补位
     * @param seqKey
     * @return
     */
    public static String generateStringId(String seqKey) throws SnCommonException{
        if(type.equals(TYPE_INNER)){
            return createSimpleId()+"";
        }
        String top;
        String suffix;
        IdSpace idspace = getSequence(seqKey);
        top    = idspace.getTop();
        suffix = idspace.getSuffix();

        String answer = String.valueOf(idspace.getCurrval());

        if(top!=null&&top.trim().length()!=0){
            answer= top+answer;
        }
        if(suffix!=null&&suffix.trim().length()!=0){
            answer= answer+suffix;
        }
        return answer;
    }

    /**
     * 获取一个ID，有前后缀，无补位，是过去使用的最大的一个
     * @param seqKey
     * @return
     */
    public static String generateCurrStringId(String seqKey) throws SnCommonException{
        if(type.equals(TYPE_INNER)){
            return createSimpleId()+"";
        }
        String top;
        String suffix;
        IdSpace idspace = getCurrSequence(seqKey);
        top    = idspace.getTop();
        suffix = idspace.getSuffix();

        String answer = String.valueOf(idspace.getCurrval());

        if(top!=null&&top.trim().length()!=0){
            answer= top+answer;
        }
        if(suffix!=null&&suffix.trim().length()!=0){
            answer= answer+suffix;
        }
        return answer;
    }

    /**
     * 获取一个ID，有前后缀，有补位
     * @param seqKey
     * @param ch
     * @param length
     * @param atend
     * @return
     * @throws SnCommonException
     */
    public static String generateStringIdWithLength(String seqKey,char ch,int length,boolean atend) throws SnCommonException{
        if(type.equals(TYPE_INNER)){
            return StringUtils.fillString(createSimpleId() + "", '0', length, false);
        }
        String top;
        String suffix;
        IdSpace idspace = getSequence(seqKey);
        top    = idspace.getTop();
        suffix = idspace.getSuffix();

        String answer = String.valueOf(idspace.getCurrval());
        int length_bu = length - answer.length();
        if(length_bu>0){
            char[] char_s = new char[length_bu];
            for(int i =0;i<char_s.length;i++){
                char_s[i]=ch;
            }
            if(atend){
                answer = answer+new String(char_s);
            }else{
                answer = new String(char_s) + answer;
            }
        }
        if(top!=null&&top.trim().length()!=0){
            answer= top+answer;
        }
        if(suffix!=null&&suffix.trim().length()!=0){
            answer= answer+suffix;
        }
        return answer;
    }

    /**
     * 获取一个ID，无前后缀，无补位
     * @param seqKey
     * @return
     * @throws SnCommonException
     */
    public static String generateStringIdNoneTopAndSuffixAndLength(String seqKey) throws SnCommonException{
        if(type.equals(TYPE_INNER)){
            return createSimpleId()+"";
        }
        String top;
        String suffix;
        IdSpace idspace = getSequence(seqKey);
        top    = idspace.getTop();
        suffix = idspace.getSuffix();

        String answer = String.valueOf(idspace.getCurrval());
        return answer;
    }

    /**
     * 获取一个ID，无前后缀，有补位
     * @param seqKey
     * @param ch
     * @param length
     * @param atend
     * @return
     * @throws SnCommonException
     */
    public static String generateStringIdNoneTopAndSuffixWithLength(String seqKey,char ch,int length,boolean atend) throws SnCommonException{
        if(type.equals(TYPE_INNER)){
            return StringUtils.fillString(createSimpleId()+"",'0',length,false);
        }
        String top;
        String suffix;
        IdSpace idspace = getSequence(seqKey);
        top    = idspace.getTop();
        suffix = idspace.getSuffix();

        String answer = String.valueOf(idspace.getCurrval());
        int length_bu = length - answer.length();
        if(length_bu>0){
            char[] char_s = new char[length_bu];
            for(int i =0;i<char_s.length;i++){
                char_s[i]=ch;
            }
            if(atend){
                answer = answer+new String(char_s);
            }else{
                answer = new String(char_s) + answer;
            }
        }
        return answer;
    }

    /**
     * 传入一个序号，返回一个IdSpace对象，这个IdSpace是没有使用过的，代表了“可以使用的最小一个”
     * @param seqKey
     * @return
     */
    private static IdSpace getSequence(String seqKey){
        SysSequenceUtils sysSequenceUtils = (SysSequenceUtils)applicationContext.getBean("sysSequenceUtils");
        return sysSequenceUtils.getSequence_(seqKey);
    }
    @Transactional(value="sn-txManager",propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public IdSpace getSequence_(String seqKey){
        IdSpace result = null;
        getSeqNoLock.lock();
        try{
            IdSpace idSpace = seqMap.get(seqKey);

            //如果idSpace存在，并且idSpace的当前值小于等于最后更新值，边界为21-1
            if(idSpace!=null && idSpace.getCurrval() < idSpace.getLastval()-1){
                idSpace.setCurrval(idSpace.getCurrval()+1);

            }else if(idSpace==null || seqMap.get(seqKey).getCurrval()>=seqMap.get(seqKey).getLastval()-1){
                //如果idSpace不存在，或者idSpace的当前值大于等于最后更新值
				/*如果idSpace不存在*/
                if(idSpace==null){
                    idSpace = new IdSpace();
                    seqMap.put(seqKey, idSpace);
                }

                TblSysSequence info = new TblSysSequence();
                int curNo = 0;
                info.setKeyValue(seqKey);
                try{
                    int brk=0;
                    //乐观锁机制
                    while(true){
                        brk++;
                        TblSysSequence tblSysSequence = tblSysSequenceMapper.select(info);
                        if(tblSysSequence==null){
                            //如果不存在，则创建一个
                            if(createNewSequence(seqKey)){
                                tblSysSequence = tblSysSequenceMapper.select(info);
                            }else{
                                throw new Exception("该sys_info不存在["+seqKey+"]");
                            }
                        }
                        //数据缓存于info
                        info = tblSysSequence;
                        //在tblSysSequence表的keyValue对应的curr_value 上自增一个batchSize增加一个batchsize
                        TblSysSequence updataIncreaseOfBatch = new TblSysSequence();
                        updataIncreaseOfBatch.setKeyValue(info.getKeyValue());
                        updataIncreaseOfBatch.setCurrValue(info.getCurrValue());
                        int i = tblSysSequenceMapper.updateIncreaseOfBatch(updataIncreaseOfBatch);
                        if(i>0){
                            //如果更改了一条，则退出//相当于乐观锁机制
                            break;
                        }
                        if(brk==500){
                            throw new Exception("while次数超过500次");
                        }
                    }
                    if (info!=null){
                        idSpace.setCurrval(info.getCurrValue());//当前数量
                        idSpace.setBatchSize(info.getBatchSize());
                        idSpace.setTop(info.getTop());
                        idSpace.setSuffix(info.getSuffix());
                        idSpace.setLastval(info.getCurrValue()+info.getBatchSize());//最大数量
                    } else{
                        throw new Exception();
                    }

                }catch(Exception e){
                    logger.error("获取序列异常",e);
                }
            }
            //设置为结果
            result = (IdSpace)idSpace.clone();
        }catch(Exception e){
            logger.error("获取序列异常",e);
        }finally{
            getSeqNoLock.unlock();
        }
        return result;
    }
    /**
     * 传入一个序号，返回一个IdSpace对象，这个IdSpace是使用过的，代表了“过去的最大一个”
     * @param seqKey
     * @return
     */
    private static IdSpace getCurrSequence(String seqKey){
        IdSpace result = null;
        getSeqNoLock.lock();
        try{
            IdSpace idSpace = seqMap.get(seqKey);

            //如果idSpace存在，并且idSpace的当前值小于等于最后更新值，边界为21-1
            if(idSpace!=null && idSpace.getCurrval() < idSpace.getLastval()-1){
                //正常数据，不操作，直接返回

            }else if(idSpace==null || seqMap.get(seqKey).getCurrval()>=seqMap.get(seqKey).getLastval()-1){
                //如果idSpace不存在，或者idSpace的当前值大于等于最后更新值
				/*如果idSpace不存在*/
                if(idSpace==null){
                    idSpace = new IdSpace();
                    seqMap.put(seqKey, idSpace);
                }

                TblSysSequence info = new TblSysSequence();
                int curNo = 0;
                info.setKeyValue(seqKey);
                try{
                    TblSysSequence tblSysSequence = tblSysSequenceMapper.select(info);
                    if(tblSysSequence==null){
                        //如果不存在，则创建一个
                        if(createNewSequence(seqKey)){
                            tblSysSequence = tblSysSequenceMapper.select(info);
                        }else{
                            throw new Exception("该sys_info不存在["+seqKey+"]");
                        }
                    }
                    //数据缓存于info
                    info = tblSysSequence;
                    //增加一个batchsize
                    if (info!=null){
                        idSpace.setCurrval(info.getCurrValue()-1);//当前数量-1，代表上一次使用的数量
                        idSpace.setBatchSize(info.getBatchSize());
                        idSpace.setTop(info.getTop());
                        idSpace.setSuffix(info.getSuffix());
                        idSpace.setLastval(info.getCurrValue());//最大数量，为当前数量
                    } else{
                        throw new Exception();
                    }

                }catch(Exception e){
                    logger.error("获取序列异常", e);
                }
            }
            //设置为结果
            result = (IdSpace)idSpace.clone();
        }catch(Exception e){
            logger.error("获取序列异常",e);
        }finally{
            getSeqNoLock.unlock();
        }
        return result;
    }

    /**
     * 创建一个新的序列
     * @param keyValue
     * @return
     */
    private static boolean createNewSequence(String keyValue){
        SysSequenceUtils sysSequenceUtils = (SysSequenceUtils)applicationContext.getBean("sysSequenceUtils");
        return sysSequenceUtils.createNewSequence_(keyValue);
    }
    private boolean createNewSequence_(String keyValue){
        TblSysSequence sequence = new TblSysSequence();
        sequence.setId(new Random().nextInt(999999999));
        sequence.setKeyValue(keyValue);
        sequence.setSeqDesc(keyValue);
        sequence.setCurrValue(1);
        sequence.setCurrValue(1);
        sequence.setBatchSize(1);
        sequence.setBatchSize(1);
        sequence.setCreateTime(DateUtils.getCurrDateTime());
        sequence.setUpTime(DateUtils.getCurrDateTime());
        int i = tblSysSequenceMapper.insert(sequence);
        if ( 1 > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    static class IdSpace implements Cloneable, Serializable {
        private static final long serialVersionUID = 5581229616891125633L;

        public IdSpace() {
            mHashCode = 0;
            mName = null;
            currval = 0L;
            lastval = -1L;
            mBatchSize = 1000;
        }

        public String getName() {
            return mName;
        }

        void setName(String pName) {
            mName = pName;
            if (mName == null)
                mHashCode = 0;
            else
                mHashCode = mName.hashCode();
        }

        public long getCurrval() {
            return currval;
        }

        public void setCurrval(long val) {
            currval = val;
        }

        public long getLastval() {
            return lastval;
        }

        public void setLastval(long lastval_) {
            lastval = lastval_;
        }

        public void setBatchSize(int pBatchSize) {
            mBatchSize = pBatchSize;
        }

        public int getBatchSize() {
            return mBatchSize;
        }

        public void setTop(String top_) {
            top = top_;
        }

        public String getTop() {
            return top;
        }

        public void setSuffix(String pSuffix) {
            suffix = pSuffix;
        }

        public String getSuffix() {
            return suffix;
        }

        public int hashCode() {
            return mHashCode;
        }

        public boolean equals(Object pOther) {
            if (pOther == null){
                return false;
            }
            if(!(pOther instanceof IdSpace)){
                return false;
            }
            IdSpace o = (IdSpace) pOther;
            if (getName() == null)
                return o.getName() == null;
            else
                return getName().equals(o.getName());
        }

        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException impossible) {
                logger.error("不支持Clone",impossible);
                throw new InternalError();
            }
        }

        public void copyFrom(IdSpace pOther) {
            setName(pOther.getName());
            setCurrval(pOther.getCurrval());
            setBatchSize(pOther.getBatchSize());
            setTop(pOther.getTop());
            setSuffix(pOther.getSuffix());
            setLastval(pOther.getLastval());
        }

        protected transient int mHashCode;
        String mName;
        protected long currval;
        protected long lastval;
        int mBatchSize;
        String top;
        String suffix;
    }

    public static long createSimpleId(){
        try{
            long d = System.currentTimeMillis();
            d = d-new Date(2016,4,1).getTime();
            long val = Long.valueOf(new Random().nextInt(90000) + 10000);//10000到100000之间的数字
            long pk = 0;
            pk = IDCreaterPlusUtils.setValue(pk, 1, 34, 34);
            pk = IDCreaterPlusUtils.setValue(pk, d, 35, 50);
            pk = IDCreaterPlusUtils.setValue(pk, val, 51, 63);
            return pk;
        }catch (Exception e){
            logger.debug("创建简单Id异常",e);
            return new Random().nextInt(90000000)+10000000;//10000000到100000000之间的数字
        }
    }
}

