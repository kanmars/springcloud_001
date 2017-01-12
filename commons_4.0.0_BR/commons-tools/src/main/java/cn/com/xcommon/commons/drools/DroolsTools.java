package cn.com.xcommon.commons.drools;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.xcommon.commons.drools.commision.Commision;
import cn.com.xcommon.commons.drools.commisionRuleEngine.impl.CommisionRuleEngineImpl;

public class DroolsTools {
	
	public static ConcurrentHashMap<String, CommisionRuleEngineImpl> map = new ConcurrentHashMap<String, CommisionRuleEngineImpl>();
	
	/**
	 * 默认刷新规则引擎
	 * @param drlPath	规则文件地址，有两种写法，1、绝对路径，2使用classpath:/相对路径
	 * @param commision
	 */
	public static void runDrools(String drlPath,Commision commision) {
		runDrools(drlPath,commision,false);
	}
	
	/**
	 * 默认刷新规则引擎
	 * @param drlPath	规则文件地址，有两种写法，1、绝对路径，2使用classpath:/相对路径
	 * @param commision
	 * @param isRefresh	是否刷新
	 */
	public static void runDrools(String drlPath,Commision commision,boolean isRefresh) {
		//CommisionRuleEngineImpl pointRuleEngine = map.get("drlPath");
		//20150414发现一个类似BUG，如果使用缓存的对象，大概在两个小时之后就会崩溃，无法计算出值，所以换成每次都要重新初始化
		CommisionRuleEngineImpl pointRuleEngine = null;
		if(pointRuleEngine==null){
			pointRuleEngine = new CommisionRuleEngineImpl();
			pointRuleEngine.setDrlPath(drlPath);
			pointRuleEngine.initEngine();
		}
		if(drlPath.startsWith("classpath")){
			pointRuleEngine.executeRuleEngine(commision);
		}else{
			if(isRefresh){
				pointRuleEngine.refreshEnginRule();
			}
			pointRuleEngine.executeRuleEngine(commision);
		}
	}

	public static void main(String[] args) throws IOException {
		if(1==1)return;
	}
}
