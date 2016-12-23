package cn.com.xcommon.commons.drools;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.xcommon.commons.drools.commision.Commision;
import cn.com.xcommon.commons.drools.commisionRuleEngine.impl.CommisionRuleEngineImpl;
import cn.com.xcommon.frame.util.DateFormatUtils;
import cn.com.xcommon.frame.util.DateUtils;
import cn.com.xcommon.frame.util.StringUtils;

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
		
		/*test_20150407120100();
		test_20150407130700();
		test_20150407120000();
		test_20150407114800();
		test_20150407114900();
		test_20150407121300();
		
		test_20150414165000();
		test_20150813104000();
		test_20150813111100();*/
		test_20150819114900();
		if(1==1)return;
	}
	
	/**
	 * 总利息计算，
	 * @param investType		//标的类型			010,020,030,040
	 * @param investRateType	//计息方式			120\130\140\150
	 * @param investPeriodUnit	//期限单位			月/自然月/日
	 * @param investPeriod		//总期限			数字
	 * @param beginTm			//开始销售时间		yyyyMMddHHmmss
	 * @param expireTm			//结束销售时间		yyyyMMddHHmmss
	 * @param packageSoldoutDt	//资产包售罄日期		yyyyMMdd
	 * @param rateStartTm		//开始计息时间		yyyyMMddHHmmss
	 * @param rateStopTm		//结束计息时间		yyyyMMddHHmmss
	 * @param packageStopDt		//资产包到期时间		yyyyMMdd
	 * @param orderPayedTm		//订单支付成功时间
	 * @param returnType		//返款类型
	 * @param comFlg			//复利标志
	 * @param roundFlg			//四舍五入方式
	 * @param investRate		//利率	如6.3%，在此处应填写"6.3"
	 * @param faceValue			//实际面值，单位为分
	 * @return
	 */
	public static BigDecimal calculationRateAmount_SUM(String investType,String investRateType,String investPeriodUnit,String investPeriod,String beginTm,String expireTm,String packageSoldoutDt,String rateStartTm,String rateStopTm,String packageStopDt,String orderPayedTm,String returnType,String comFlg,String roundFlg,String investRate,BigDecimal faceValue){
		BigDecimal result = new BigDecimal(faceValue.toPlainString());
		
		RoundingMode rmode = RoundingMode.HALF_UP;
		//处理小数点
		if(roundFlg.equals("0")){
			//四舍五入
			rmode = RoundingMode.HALF_UP;
		}else if(roundFlg.equals("1")){
			//舍弃小数点
			rmode = RoundingMode.HALF_DOWN;
		}else if(roundFlg.equals("2")){
			//如果有小数点，就直接进位
			rmode = RoundingMode.UP;
		}
		//利率计算
		investRate = new BigDecimal(investRate).divide(new BigDecimal("100")).toPlainString();//利率转化为百分数
		//计算总利息
		if(returnType.equals("010")){
			//到期一次性还本付息
			if(investPeriodUnit.equals("日")){
				//总收益 = 购买金额 * 项目期限 （天）* 年化收益率 / 365（天）
				result =  faceValue.multiply(new BigDecimal(investPeriod)).multiply(new BigDecimal(investRate)).divide(new BigDecimal("365"),rmode);
			}else if(investPeriodUnit.equals("月") || investPeriodUnit.equals("自然月")){
				//总收益 = 购买金额 * 项目期限 （月）* 年化收益率 / 12(月)；
				result =  faceValue.multiply(new BigDecimal(investPeriod)).multiply(new BigDecimal(investRate)).divide(new BigDecimal("12"),rmode);
			}
		}else if(returnType.equals("020")){
			//按月付息到期还本
			if(investPeriodUnit.equals("日")){
				//总收益 = 购买金额 * 项目期限 （天）* 年化收益率 / 365（天）
				result =  faceValue.multiply(new BigDecimal(investPeriod)).multiply(new BigDecimal(investRate)).divide(new BigDecimal("365"),rmode);
			}else if(investPeriodUnit.equals("月") || investPeriodUnit.equals("自然月")){
				//总收益 = 购买金额 * 项目期限 （月）* 年化收益率 / 12(月)；
				result =  faceValue.multiply(new BigDecimal(investPeriod)).multiply(new BigDecimal(investRate)).divide(new BigDecimal("365"),rmode);
			}
		}else if(returnType.equals("030")){
			//按月等额本息
			//每月还本付息金额 =[ 购买金额 * 月利率 * (1+月利率) * 项目期限（月） ] / [(1+月利率) * 还款月数 - 1]
			//每月利息 = 剩余本金 * 贷款月利率
			//总收益=购买金额 * 项目期限（月） * 月利率 *（1+月利率）*项目期限（月）/【（1+月利率）* 项目期限（月） - 1】-购买金额	//??
			//本息总额=项目期限（月）* 购买金额 *月利率*（1+月利率）*项目期限（月）/【（1+月利率）*项目期限（月） - 1】
			BigDecimal investRate_Month = new BigDecimal(investRate).divide(new BigDecimal("12"),rmode);
			BigDecimal investRate_Month_add_1 = new BigDecimal(investRate_Month.toPlainString()).add(new BigDecimal("1"));
			
			BigDecimal investRate_Month_add_1_mp_d1 = new BigDecimal(investRate_Month.toPlainString()).add(new BigDecimal("1")).multiply(new BigDecimal(investPeriod)).subtract(new BigDecimal("1"));
			result = faceValue.multiply(new BigDecimal(investPeriod)).multiply(investRate_Month).multiply(investRate_Month_add_1).multiply(investRate_Month).divide(investRate_Month_add_1_mp_d1,rmode).subtract(faceValue);
		}else if(returnType.equals("040")){
			//按月等额本金
			//每月还本付息金额=(购买金额/项目期限（月）)+( 购买金额-累计已还本金)×月利率
			//每月本金=购买金额/项目期限（月）
			//每月利息=(购买金额-累计已还本金)×月利率
			//总收益=（项目期限（月）+1）*购买金额*月利率/2
			//本息总额=(项目期限（月）+1)*购买金额*月利率/2+购买金额
			BigDecimal investRate_Month = new BigDecimal(investRate).divide(new BigDecimal("12"),rmode);
			result = new BigDecimal(investPeriod).add(new BigDecimal("1")).multiply(faceValue).multiply(investRate_Month).divide(new BigDecimal(2),rmode);
		}else if(returnType.equals("050")){
			//按季付息，到期还本
			//收益=本金*利率*持有天数/365（365不变）
			//需要计算持有天数//持有天数=到期日-满标日
			int chiyoutianshu = 0;
			if(investPeriodUnit.equals("日")){
				//当等于天时，直接使用总期限
				chiyoutianshu = Integer.parseInt(investPeriod);
			}else if(investPeriodUnit.equals("月") || investPeriodUnit.equals("自然月")){
				//当等于月时，需要计算到期日
				String daoqiri = getDaoqiri(packageSoldoutDt,investPeriod);
				//持有天数=到期日-满标日
				chiyoutianshu = DateUtils.getDaysBetween(daoqiri, packageSoldoutDt);
			}
			result = faceValue.multiply(new BigDecimal(investRate)).multiply(new BigDecimal(chiyoutianshu)).divide(new BigDecimal("365"),rmode);
		}else if(returnType.equals("060")){
		//0元购，满标计息，到期还本付息，人行利率进行显示，3个月2.1%，6个月2.3%，12个月2.5%
		//商品价值（返券金额）为N；用户投资金额（本金）为X；人行利率为Y；平台产品年化率为S；投资期限为M个月	
		//到期给用户利息：（X*Y*M/12）
		//投资金额：X=N*(1+S*M/12)/((S-Y)*M/12)
		//商品价值：N=(S-Y)*X*M/(12*(1+S*M/12))
		result = faceValue.multiply(new BigDecimal(investRate)).multiply(new BigDecimal(investPeriod)).divide(new BigDecimal("12"),rmode);
		}else if(returnType.equals("070")){
			//票据分支
			result = faceValue.multiply(new BigDecimal(investRate)).multiply(new BigDecimal(investPeriod)).divide(new BigDecimal("360"),rmode);
		}else if(returnType.equals("080")){
			//固有分支
			result = faceValue.multiply(new BigDecimal(investRate)).multiply(new BigDecimal(investPeriod)).divide(new BigDecimal("365"),rmode);
		}else{
			//没有考虑到的分支，抛出异常
			createAError("calculationRateAmount_SUM0001");
		}
		//处理小数点
		result = result.setScale(0, rmode);
		return result;
	}
	
	/**
	 * 获取N个月后的到期日
	 * @param buyDt	售罄日
	 * @param yue	月份
	 * @return
	 */
	public static String getDaoqiri(String buyDt,String yue){
		//计算到期月份
		String buyMonth_after_yue = DateUtils.countMonth(DateFormatUtils.format(buyDt, "yyyyMMdd", "yyyyMM"), Integer.parseInt(yue));
		//计算到期的"日"
		//01-首日
		String day_select_first = buyDt.substring(6,8);
		//02-最终月的末日-1
		String day_select_last_yyyyMMdd = DateUtils.getEndDateByMonths(buyDt, Integer.parseInt(yue));
		//String day_select_last_1 = DateUtils.countDate(day_select_last_yyyyMMdd, -1);
		String day_select_last =  day_select_last_yyyyMMdd.substring(6,8);
		//03-中间月的最后一日
		String day_between_in = "";
		for(int i=0;i<=Integer.parseInt(yue);i++){
			//获取月份的值
			String buy_yyyyMM_after_i_yue = DateUtils.countMonth(DateFormatUtils.format(buyDt, "yyyyMMdd", "yyyyMM"), i);
			String buy_MM_after_i_yue = buy_yyyyMM_after_i_yue.substring(4,6);
			if(buy_MM_after_i_yue.equals("01")||buy_MM_after_i_yue.equals("03")||buy_MM_after_i_yue.equals("05")||buy_MM_after_i_yue.equals("07")||buy_MM_after_i_yue.equals("08")||buy_MM_after_i_yue.equals("10")||buy_MM_after_i_yue.equals("12")){
				day_between_in = "31";
			}
			if(buy_MM_after_i_yue.equals("04")||buy_MM_after_i_yue.equals("06")||buy_MM_after_i_yue.equals("09")||buy_MM_after_i_yue.equals("11")){
				day_between_in = "30";
			}
			if(buy_MM_after_i_yue.equals("02")){
				if(DateUtils.isLeapYear(Integer.parseInt(buy_yyyyMM_after_i_yue.substring(0,4)))){
					//闰年
					day_between_in = "29";
					break;//有二月份的情况，为最终结果
				}else{
					//非闰年
					day_between_in = "28";
					break;//有二月份的情况，为最终结果
				}
			}
		}
		//取day_select_first和day_select_last和day_between_in的最小值
		String result = day_select_first;
		if(result.compareTo(day_select_last)>=0){
			result = day_select_last;
		}
		if(result.compareTo(day_between_in)>=0){
			result = day_between_in;
		}
		//返回yyyyMM   + dd
		return buyMonth_after_yue+result;
	}
	
	/**
	 * 返回计息开始时间
	 * @param investType		//标的类型			010,020,030,040
	 * @param investRateType	//计息方式			120\130\140\150\160
	 * @param investPeriodUnit	//期限单位			月/自然月/日
	 * @param investPeriod		//总期限			数字
	 * @param beginTm			//开始销售时间		yyyyMMddHHmmss
	 * @param expireTm			//结束销售时间		yyyyMMddHHmmss
	 * @param packageSoldoutDt	//资产包售罄日期		yyyyMMdd
	 * @param rateStartTm		//开始计息时间		yyyyMMddHHmmss
	 * @param rateStopTm		//结束计息时间		yyyyMMddHHmmss
	 * @param packageStopDt		//资产包到期时间		yyyyMMddHHmmss
	 * @param orderPayedTm		//订单支付成功时间	yyyyMMddHHmmss
	 * @return
	 */
	public static String returnRateStartTm(String investType,String investRateType,String investPeriodUnit,String investPeriod,String beginTm,String expireTm,String packageSoldoutDt,String rateStartTm,String rateStopTm,String packageStopDt,String orderPayedTm){
		if(investType.equals("010")){
			//票据分支
			if(investRateType.equals("180")){
				//购买完成开始计息
				return orderPayedTm;
			}
		}
		if(investType.equals("020")){
			//P2P分支
			if(investRateType.equals("120")){
				//购买完成开始计息
				return orderPayedTm;
			}
			if(investRateType.equals("130")){
				//购买次日开始计息
				String orderPayedDt = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMMdd");
				return DateFormatUtils.countDate(orderPayedDt, 1)+"000000";//购买次日00:00:00//orderPayedTm.substring(8);
			}
			if(investRateType.equals("140")){
				//项目完成开始计息
				if(StringUtils.isNotEmpty(packageSoldoutDt)){
					return packageSoldoutDt+"000000";//资产包售罄日期
				}else{
					return null;//资产包尚未售罄
				}
			}
			if(investRateType.equals("150")){
				//指定日期开始计息
				return rateStartTm;
			}
			if(investRateType.equals("160")){
				//满标日次日开始计息
				if(StringUtils.isNotEmpty(packageSoldoutDt)){
					return DateFormatUtils.countDate(packageSoldoutDt, 1)+"000000";//资产包售罄日期
				}else{
					return null;//资产包尚未售罄
				}
			}
			//
			if(investRateType.equals("170")){
				//0元购，满标次日开始计息
				if(StringUtils.isNotEmpty(packageSoldoutDt)){
					return DateFormatUtils.countDate(packageSoldoutDt, 1)+"000000";//资产包售罄日期
				}else{
					return null;//资产包尚未售罄
				}
			}
			//TODO 其他分支待开发
			createAError("returnRateStartTm0002");
		}
		if(investType.equals("030")){
			//众筹分支
			//TODO 待开发
			createAError("returnRateStartTm0003");
		}
		
		if(investType.equals("040")){
			//固收分支
			if(investRateType.equals("190")){
				//满标日次日开始计息
				if(StringUtils.isNotEmpty(packageSoldoutDt)){
					return DateFormatUtils.countDate(packageSoldoutDt, 1)+"000000";//资产包售罄日期
				}else{
					return null;//资产包尚未售罄
				}
			}
			//TODO 其他分支待开发
			createAError("returnRateStartTm0002");
		}
		
		createAError("returnRateStartTm0004");
		return null;
	}
	/**
	 * 返回计息结束时间
	 * @param investType		//标的类型			010,020,030,040
	 * @param investRateType	//计息方式			120\130\140\150\160
	 * @param investPeriodUnit	//期限单位			月/自然月/日
	 * @param investPeriod		//总期限			数字
	 * @param beginTm			//开始销售时间		yyyyMMddHHmmss
	 * @param expireTm			//结束销售时间		yyyyMMddHHmmss
	 * @param packageSoldoutDt	//资产包售罄日期		yyyyMMdd
	 * @param rateStartTm		//开始计息时间		yyyyMMddHHmmss
	 * @param rateStopTm		//结束计息时间		yyyyMMddHHmmss
	 * @param packageStopDt		//资产包到期时间		yyyyMMddHHmmss
	 * @param orderPayedTm		//订单支付成功时间	yyyyMMddHHmmss
	 * @return
	 */
	public static String returnRateStopTm(String investType,String investRateType,String investPeriodUnit,String investPeriod,String beginTm,String expireTm,String packageSoldoutDt,String rateStartTm,String rateStopTm,String packageStopDt,String orderPayedTm){
		if(investType.equals("010")){
			//票据分支
			if(investRateType.equals("180")){
				String orderPayedDt = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMMdd");
				int investPeriod_int = Integer.parseInt(investPeriod);
				orderPayedDt = DateFormatUtils.countDate(orderPayedDt, investPeriod_int-1);//结束日期前一天
				return orderPayedDt+"235959";//结束日期前一天的23点59分59秒
			}
		}
		if(investType.equals("020")){
			//P2P分支
			if(investRateType.equals("120")){
				//购买完成开始计息
				if(investPeriodUnit.equals("月")||investPeriodUnit.equals("自然月")){
					String orderMonth = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMM");
					int investPeriod_int = Integer.parseInt(investPeriod);
					String orderEndMonth = DateFormatUtils.countMonth(orderMonth, investPeriod_int);//计算结束月份
					String orderEndDt = orderEndMonth + orderPayedTm.substring(6, 8);//结算结束日期
					orderEndDt = DateFormatUtils.countDate(orderEndDt, -1);//结束日期前一天
					return orderEndDt+"235959";//结束日期前一天的23点59分59秒
				}
				if(investPeriodUnit.equals("日")){
					String orderPayedDt = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMMdd");
					int investPeriod_int = Integer.parseInt(investPeriod);
					orderPayedDt = DateFormatUtils.countDate(orderPayedDt, investPeriod_int-1);//结束日期前一天
					return orderPayedDt+"235959";//结束日期前一天的23点59分59秒
				}
				//TODO 其他分支待开发
				createAError("returnRateStopTm0002");
			}
			if(investRateType.equals("130")){
				//购买次日开始计息
				if(investPeriodUnit.equals("月")||investPeriodUnit.equals("自然月")){
					String orderMonth = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMM");
					int investPeriod_int = Integer.parseInt(investPeriod);
					String orderEndMonth = DateFormatUtils.countMonth(orderMonth, investPeriod_int);//计算结束月份
					String orderEndDt = orderEndMonth + orderPayedTm.substring(6, 8);//计算结算结束日期，月份+天
					return orderEndDt+"235959";//结束日期的23点59分59秒
				}
				if(investPeriodUnit.equals("日")){
					String orderPayedDt = DateFormatUtils.format(orderPayedTm, "yyyyMMddHHmmss", "yyyyMMdd");
					int investPeriod_int = Integer.parseInt(investPeriod);
					orderPayedDt = DateFormatUtils.countDate(orderPayedDt, investPeriod_int);//结束日期
					return orderPayedDt+"235959";//结束日期前一天的23点59分59秒
				}
				//TODO 其他分支待开发
				createAError("returnRateStopTm0003");
			}
			if(investRateType.equals("140")){
				//资产包尚未售罄
				if(StringUtils.isEmpty(packageSoldoutDt)){
					return null;//资产包售罄日期
				}
				//项目完成开始计息
				if(investPeriodUnit.equals("月")||investPeriodUnit.equals("自然月")){
					String packageSoldoutMonth = DateFormatUtils.format(packageSoldoutDt, "yyyyMMdd", "yyyyMM");
					int investPeriod_int = Integer.parseInt(investPeriod);
					String packageEndMonth = DateFormatUtils.countMonth(packageSoldoutMonth, investPeriod_int);//计算结束月份
					String packageEndDt = packageEndMonth + orderPayedTm.substring(6, 8);//计算结算结束日期，月份+天
					packageEndDt = DateFormatUtils.countDate(packageEndDt, investPeriod_int-1);//结束日期前一天
					return packageEndDt+"235959";//结束日期的23点59分59秒
				}
				if(investPeriodUnit.equals("日")){
					int investPeriod_int = Integer.parseInt(investPeriod);
					packageSoldoutDt = DateFormatUtils.countDate(packageSoldoutDt, investPeriod_int-1);//结束日期
					return packageSoldoutDt+"235959";//结束日期前一天的23点59分59秒
				}
				//TODO 其他分支待开发
				createAError("returnRateStopTm0004");
				return packageSoldoutDt;//资产包售罄日期
			}
			if(investRateType.equals("150")){
				//指定日期开始计息
				return rateStopTm;
			}
			if(investRateType.equals("160")){
				if(StringUtils.isEmpty(packageSoldoutDt)){
					createAError("returnRateStopTm0005，当计息方式为160满标后次日计息时，packageSoldoutDt必须存在");
				}
				//满标次日开始计息
				if(investPeriodUnit.equals("日")){
					//等于标的售罄日期+期限
					return DateUtils.countDate(packageSoldoutDt, Integer.parseInt(investPeriod))+"235959";
				}else if(investPeriodUnit.equals("月") || investPeriodUnit.equals("自然月")){
					//计算到期日 只是针对 p2p   需要业务确认
					//当等于月时，需要计算到期日
					String daoqiri = getDaoqiri(packageSoldoutDt,investPeriod);
					return daoqiri+"235959";
				}
			}
			//0元购 满标次日计息
			if(investRateType.equals("170")){
				if(StringUtils.isEmpty(packageSoldoutDt)){
					createAError("returnRateStopTm0006，当计息方式为170满标后次日计息时，packageSoldoutDt必须存在");
				}
				//当等于月时，需要计算到期日
				String daoqiri = getDaoqiri(packageSoldoutDt,investPeriod);
				return daoqiri+"235959";
				
			}
			
			//TODO 其他分支待开发
			createAError("returnRateStopTm0006");
		}
		
		if(investType.equals("030")){
			//众筹分支
			//TODO 待开发
			createAError("returnRateStopTm0007");
		}
		
		if(investType.equals("040")){
			//固收分支
			if(investRateType.equals("190")){
				if(StringUtils.isEmpty(packageSoldoutDt)){
					createAError("returnRateStopTm0005，当计息方式为190满标后次日计息时，packageSoldoutDt必须存在");
				}
				//满标次日开始计息
				if(investPeriodUnit.equals("日")){
					//等于标的售罄日期+期限
					return DateUtils.countDate(packageSoldoutDt, Integer.parseInt(investPeriod))+"235959";
				}
				/*else if(investPeriodUnit.equals("月") || investPeriodUnit.equals("自然月")){
					//当等于月时，需要计算到期日
					String daoqiri = getDaoqiri(packageSoldoutDt,investPeriod);
					return daoqiri+"235959";
				}*/
			}
			//TODO 其他分支待开发
			createAError("returnRateStopTm0006");
		}
		createAError("returnRateStopTm0008");
		return null;
	}
	
	/**
	 * 根据传入参数，返回一个返息时间的序列
	 * @param investType
	 * @param investRateType
	 * @param investPeriodUnit
	 * @param investPeriod
	 * @param beginTm
	 * @param expireTm
	 * @param packageSoldoutDt
	 * @param rateStartTm
	 * @param rateStopTm
	 * @param packageStopDt
	 * @param orderPayedTm
	 * @param returnType
	 * @param comFlg
	 * @param roundFlg
	 * @param investRate
	 * @param faceValue
	 * @return
	 */
	public static List getRateList(String investType,String investRateType,String investPeriodUnit,String investPeriod,String beginTm,String expireTm,String packageSoldoutDt,String rateStartTm,String rateStopTm,String packageStopDt,String orderPayedTm,String returnType,String comFlg,String roundFlg,String investRate,BigDecimal faceValue){
		//处理小数点
		RoundingMode rmode = RoundingMode.HALF_UP;
		if(roundFlg.equals("0")){
			//四舍五入
			rmode = RoundingMode.HALF_UP;
		}else if(roundFlg.equals("1")){
			//舍弃小数点
			rmode = RoundingMode.HALF_DOWN;
		}else if(roundFlg.equals("2")){
			//如果有小数点，就直接进位
			rmode = RoundingMode.UP;
		}
		if(investType.equals("010")){
			//票据分支
			//到期一次还本付息
			String rateStartTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
			String rateStopTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
			BigDecimal rateAll = calculationRateAmount_SUM(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm_, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue); 
			List result = new ArrayList();
			HashMap cell = new HashMap();
			cell.put("rateStartTm", rateStartTm_);
			cell.put("rateStopTm", rateStopTm_);
			cell.put("rate", rateAll.add(faceValue));
			result.add(cell);
			return result;
		}else if(investType.equals("020")){
			if(returnType.equals("010")){
				//到期一次还本付息
				String rateStartTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				String rateStopTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				BigDecimal rateAll = calculationRateAmount_SUM(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm_, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue); 
				List result = new ArrayList();
				HashMap cell = new HashMap();
				cell.put("rateStartTm", rateStartTm_);
				cell.put("rateStopTm", rateStopTm_);
				cell.put("rate", rateAll.add(faceValue));
				result.add(cell);
				return result;
			}else if(returnType.equals("020")){
				//按月付息，到期还本
				createAError("按月付息，到期还本，分支待开发[getRateList2201]");
			}else if(returnType.equals("030")){
				//按月等额本息
				createAError("按月等额本息，分支待开发[getRateList2301]");
			}else if(returnType.equals("040")){
				//按月等额本金
				createAError("按月等额本金，分支待开发[getRateList2401]");
			}else if(returnType.equals("050")){
				List result = new ArrayList();
				//按季付息，到期还本
				//1、计算付息日，一般来说，该方式下，investPeriod必须为3的倍数
				if((investPeriodUnit.equals("月")||investPeriodUnit.equals("自然月"))&&Integer.parseInt(investPeriod)%3!=0){
					//当使用按季度付息，到期还本时，如果单位为月，那么月必须为3的倍数，否则无法按季度处理
					createAError("当使用按季度付息，到期还本时，如果单位为月，那么月必须为3的倍数，否则无法按季度处理[getRateList2501]");
				}
				//计算季数
				int jishu = Integer.parseInt(investPeriod)/3;
				//上一个开始计算利息的时间
				String pre_rate_dt = packageSoldoutDt;
				BigDecimal rate_sum = calculationRateAmount_SUM(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue);
				BigDecimal rate_payed = new BigDecimal(0);
				for(int i=1;i<=jishu;i++){
					//计算上一个开始计算利息的时间+3个月后的时间，即1季度的时间
					String now_rate_dt = getDaoqiri(pre_rate_dt, "3");
					//计算时间差
					String dataBetween = DateUtils.getDaysBetween(now_rate_dt, pre_rate_dt)+"";
					BigDecimal rate_this_jidu = calculationRateAmount_SUM(investType, investRateType, "日", dataBetween, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue); 
					HashMap cell = new HashMap();
					
					cell.put("rateStartTm", DateUtils.addDay(pre_rate_dt, 1)+"000000");//起息日为上次计算到期日的次日
					cell.put("rateStopTm", now_rate_dt+"235959");
					cell.put("investPeriod", dataBetween);
					if(i!=jishu){
						//如果不是最后一季，只返回利息
						cell.put("rate", rate_this_jidu);
						//将已支付的利息进行加总
						rate_payed = rate_payed.add(rate_this_jidu);
					}else{
						//如果是最后一季，则返本付息
						//最后一季度的利息为：预计总利息-已支付的利息+面值
						cell.put("rate", rate_sum.subtract(rate_payed).add(faceValue));
					}
					result.add(cell);
					//将这次计算的时间，设置为下一次计算中的起息日
					pre_rate_dt = now_rate_dt;
				}
				return result;
			}else if(returnType.equals("060")){
				//0元购，满标计息，到期还本付息，人行利率进行显示，3个月2.1%，6个月2.3%，12个月2.5%
				String rateStartTm_ = returnRateStartTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				String rateStopTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				BigDecimal rateAll = calculationRateAmount_SUM(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm_, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue); 
				List result = new ArrayList();
				HashMap cell = new HashMap();
				cell.put("rateStartTm", rateStartTm_);
				cell.put("rateStopTm", rateStopTm_);
				cell.put("rate", rateAll.add(faceValue));
				result.add(cell);
				return result;
			}else{
				//没有考虑到的分支，抛出异常
				createAError("calculationRateAmount_SUM0001");
			}
		}else if(investType.equals("030")){
			//众筹分支
			createAError("众筹分支，待开发[getRateList3001]");
		
		}else if(investType.equals("040")){
			//固收分支
			if(returnType.equals("080")){
				//到期一次还本付息
				String rateStartTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				String rateStopTm_ = returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm);
				BigDecimal rateAll = calculationRateAmount_SUM(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm_, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue); 
				List result = new ArrayList();
				HashMap cell = new HashMap();
				cell.put("rateStartTm", rateStartTm_);
				cell.put("rateStopTm", rateStopTm_);
				cell.put("rate", rateAll.add(faceValue));
				result.add(cell);
				return result;
			}else{
				//没有考虑到的分支，抛出异常
				createAError("calculationRateAmount_SUM0001");
			}
		}else{
			//其他类型分支
			createAError("其他类型分支，待开发[getRateList4001]");
		}
		
		return null;
	}
	
//	为了防止利息计算被破坏，抛出一个异常
	public static void createAError(String position){
		if(1==1)throw new RuntimeException("发生运行时异常，所有的计息时间计算规则均不符合，为保障系统稳定运行，抛出该异常"+position);
	}
	
	/**
	 * 测试DroolsTools使用用例
	 */
	public static void test_20150407120000(){
		Commision commision = new Commision();
		//使用示例
		DroolsTools.runDrools("classpath:/drl/1.drl", commision);
		DroolsTools.runDrools("E:/t.drl", commision);
		DroolsTools.runDrools("E:/t.drl", commision);
		System.out.println(commision);
	}
	/**
	 * 测试到期时间计算
	 */
	public static void test_20150813104800(){
		//测试利息计算
//		String investType = "040";					//标的类型，P2P
//		String investRateType = "190";				//计息方式
//													010贴现
//													020到期一次付息
//													030定期平均付息
//													040定期递增利率
//													050定期递减利率
//													060正向浮动利率
//													070逆向浮动利率
//													080钉住利差
//													090钉住汇差
//													110钉住价差
//													120购买完成开始计息
//													130购买次日开始计息	优先
//													140项目完成开始计息	
//													150指定日期开始计息	优先

//		String investPeriodUnit = "日";			//期限单位
//		String investPeriod = "6";					//总期限
//		String beginTm = "20150401100000";			//开始销售时间
//		String expireTm = "20150708000000";			//结束销售时间
//		String packageSoldoutDt = "20150425";	//售罄时间
//		
//		String rateStartTm = "20150425102200";		//开始计息时间
//		String rateStopTm = "20150425102200";		//结束计息时间
		
//		String packageStopDt = null;		//标的到期时间		//-----------------------------
//		String orderPayedTm = "20150413164938";	//结束销售时间
		
//		System.out.println(returnRateStartTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm));
//		System.out.println(returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm));
		
		
		Commision commision = new Commision();
//		commision.put("investType", investType );
//		commision.put("investRateType", investRateType);
//		commision.put("investPeriodUnit", investPeriodUnit);
//		commision.put("investPeriod", investPeriod);
//		commision.put("beginTm", beginTm);
//		commision.put("expireTm", expireTm);
//		commision.put("packageSoldoutDt", packageSoldoutDt);
//		commision.put("rateStartTm", rateStartTm);
//		commision.put("rateStopTm", rateStopTm);
//		commision.put("packageStopDt", packageStopDt);
//		commision.put("orderPayedTm", orderPayedTm);
		commision.put("investType", "040" );
		commision.put("investRateType", "190");
		commision.put("investPeriodUnit", "日");
		commision.put("investPeriod", "1");
		commision.put("beginTm", "20150813150512");
		commision.put("expireTm", "20150930150512");
		commision.put("packageSoldoutDt", "20150813");
		commision.put("rateStartTm", null);
		commision.put("rateStopTm", null);
		commision.put("packageStopDt", null);
		commision.put("orderPayedTm", "20150813132830");
		//使用示例
		DroolsTools.runDrools("classpath:/drl/ratetm/ratetm.drl", commision);
		System.out.println(commision);
		System.out.println(commision.get("rateStartTm"));
		System.out.println(commision.get("rateStopTm"));
		
	}
	
	/**
	 * 测试固收开始结束时间
	 */
	public static void test_20150813111100(){
		
		Commision commision = new Commision();
		commision.put("investType", "040" );
		commision.put("investRateType", "190");
		commision.put("investPeriodUnit", "日");
		commision.put("investPeriod", "60");
		commision.put("beginTm", "20150813150512");
		commision.put("expireTm", "20150930150512");
		commision.put("packageSoldoutDt", "20150815");
		commision.put("rateStartTm", null);
		commision.put("rateStopTm", null);
		commision.put("packageStopDt", null);
		commision.put("orderPayedTm", "20150814132830");
		//使用示例
		//String ss = returnRateStartTm("040","190","日","1","20150813150512","20150930150512","20150815",null,null,null,"20150814132830");
		//System.out.println(ss);
		DroolsTools.runDrools("classpath:/drl/ratetm/ratetm.drl", commision);
		System.out.println(commision);
		System.out.println(commision.get("rateStartTm"));
		System.out.println(commision.get("rateStopTm"));
		
	}
	/**
	 * 测试用户利息
	 */
	public static void test_20150813104000(){
		//测试旧的计算利息
		Commision commision = new Commision();
		commision.put("investType", "040");
		commision.put("investRateType", "190");
		commision.put("returnType", "010");
		commision.put("investPeriodUnit", "日");
		commision.put("investPeriod", "365");
		commision.put("comFlg", "010");//
		commision.put("roundFlg", "0");//
		commision.put("investRate", "9.3");
		commision.put("faceValue", new BigDecimal("100000000"));//分
		
		DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
		//DroolsTools.runDrools("E:/rate.drl", commision);
		System.out.println(commision);
	}
	/**
	 * 测试计息方式160，到期还款方式050的计息
	 */ 
	public static void test_20150819114900(){
		//测试新的计算利息
		
		
		Commision commision = new Commision();
		commision.put("investType", "040");
		commision.put("investRateType", "190");
		commision.put("investPeriodUnit", "日");
		commision.put("investPeriod", "365");
		commision.put("beginTm", "20150819112000");
		commision.put("expireTm", "20150930000000");
		commision.put("packageSoldoutDt", "20150819");
		commision.put("rateStartTm", "20150820000000");
		commision.put("rateStopTm", "20160818235959");
		commision.put("packageStopDt", "20150819");
		commision.put("orderPayedTm", "20150819144437");
		commision.put("returnType", "080");
		commision.put("comFlg", "010");//
		commision.put("roundFlg", "0");//
		commision.put("investRate", "6.5");
		commision.put("faceValue", new BigDecimal("10"));//分
		List cd = getRateList("040","190","日","365","20150819112000","20150930000000","20150819","20150820000000","20160818235959","20150819","20150819144437","080","010","0","6.5",new BigDecimal("10"));
		//DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
		//DroolsTools.runDrools("E:/rate.drl", commision);
		//{rateStartTm=20150820000000,  packageStopDt=20150819, expireTm=20150930000000, orderPayedTm=20150819144437,
		//returnType=080, rateStopTm=20160818235959, investType=040, investRate=6.5, investPeriod=365, 
		//roundFlg=0, packageSoldoutDt=20150819, faceValue=10, investPeriodUnit=日, investRateType=190, beginTm=20150819112000}

		System.out.println(cd);
	}
	/**
	 * 测试到期时间计算，160和050分支
	 */
	public static void test_20150407121300(){
		//测试利息计算
		String investType = "020";					//标的类型，P2P
		String investRateType = "160";				//计息方式 160项目满标次日计息
		String investPeriodUnit = "月";			//期限单位
		String investPeriod = "12";					//总期限
		String beginTm = "20150325102200";			//开始销售时间
		String expireTm = "20150430102200";			//结束销售时间
		String packageSoldoutDt = "20150430";	//售罄时间
		
		String rateStartTm = null;		//开始计息时间
		String rateStopTm = null;		//结束计息时间
		
		String packageStopDt = "20160430";		//标的到期时间
		String orderPayedTm = "20150425102200";	//订单支付成功时间
		
		/**
		 * 返回计息结束时间
		 * @param investType		//标的类型			010,020,030,040
		 * @param investRateType	//计息方式			120\130\140\150\160
		 * @param investPeriodUnit	//期限单位			月/自然月/日
		 * @param investPeriod		//总期限			数字
		 * @param beginTm			//开始销售时间		yyyyMMddHHmmss
		 * @param expireTm			//结束销售时间		yyyyMMddHHmmss
		 * @param packageSoldoutDt	//资产包售罄日期		yyyyMMdd
		 * @param rateStartTm		//开始计息时间		yyyyMMddHHmmss
		 * @param rateStopTm		//结束计息时间		yyyyMMddHHmmss
		 * @param packageStopDt		//资产包到期时间		yyyyMMddHHmmss
		 * @param orderPayedTm		//订单支付成功时间	yyyyMMddHHmmss
		 * @return
		 */
		System.out.println(returnRateStartTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm));
		System.out.println(returnRateStopTm(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm));
		
		
		Commision commision = new Commision();
//		commision.put("investType", investType );
//		commision.put("investRateType", investRateType);
//		commision.put("investPeriodUnit", investPeriodUnit);
//		commision.put("investPeriod", investPeriod);
//		commision.put("beginTm", beginTm);
//		commision.put("expireTm", expireTm);
//		commision.put("packageSoldoutDt", packageSoldoutDt);
//		commision.put("rateStartTm", rateStartTm);
//		commision.put("rateStopTm", rateStopTm);
//		commision.put("packageStopDt", packageStopDt);
//		commision.put("orderPayedTm", orderPayedTm);
		commision.put("investType", investType );
		commision.put("investRateType", investRateType);
		commision.put("investPeriodUnit", investPeriodUnit);
		commision.put("investPeriod", investPeriod);
		commision.put("beginTm", beginTm);
		commision.put("expireTm", expireTm);
		commision.put("packageSoldoutDt", packageSoldoutDt);
		commision.put("rateStartTm", rateStartTm);
		commision.put("rateStopTm", rateStopTm);
		commision.put("packageStopDt", packageStopDt);
		commision.put("orderPayedTm", orderPayedTm);
		//使用示例
		DroolsTools.runDrools("classpath:/drl/ratetm/ratetm.drl", commision);
		System.out.println(commision);
		System.out.println(commision.get("rateStartTm"));
		System.out.println(commision.get("rateStopTm"));
		
	}
	
	/**
	 *测试返息方式计算
	 */
	public static void test_20150813170700(){
		//测试利息计算
		String investType = "040";							//标的类型，P2P
		String investRateType = "190";						//计息方式 160项目满标次日计息
		String investPeriodUnit = "日";						//期限单位
		String investPeriod = "60";							//总期限
		String beginTm = "20150325102200";					//开始销售时间
		String expireTm = "20150430102200";					//结束销售时间
		String packageSoldoutDt = "20150423";				//售罄时间
		String rateStartTm = "";
		String rateStopTm = "";
		String packageStopDt = "";
		
		String orderPayedTm = "20150425102200";				//订单支付成功时间
		String returnType = "010";							//返息方式
		String comFlg="010";								//单利方式
		String roundFlg = "0";								//四舍五入
		String investRate = "6.3";							//利息
		BigDecimal faceValue = new BigDecimal("100000000");	//面值
		
		/**
		 * 返回计息结束时间
		 * @param investType		//标的类型			010,020,030,040
		 * @param investRateType	//计息方式			120\130\140\150\160
		 * @param investPeriodUnit	//期限单位			月/自然月/日
		 * @param investPeriod		//总期限			数字
		 * @param beginTm			//开始销售时间		yyyyMMddHHmmss
		 * @param expireTm			//结束销售时间		yyyyMMddHHmmss
		 * @param packageSoldoutDt	//资产包售罄日期		yyyyMMdd
		 * @param rateStartTm		//开始计息时间		yyyyMMddHHmmss
		 * @param rateStopTm		//结束计息时间		yyyyMMddHHmmss
		 * @param packageStopDt		//资产包到期时间		yyyyMMddHHmmss
		 * @param orderPayedTm		//订单支付成功时间	yyyyMMddHHmmss
		 * @return
		 */
		System.out.println(getRateList(investType, investRateType, investPeriodUnit, investPeriod, beginTm, expireTm, packageSoldoutDt, rateStartTm, rateStopTm, packageStopDt, orderPayedTm, returnType, comFlg, roundFlg, investRate, faceValue));
		
		
		/*Commision commision = new Commision();
		commision.put("investType", investType );
		commision.put("investRateType", investRateType);
		commision.put("investPeriodUnit", investPeriodUnit);
		commision.put("investPeriod", investPeriod);
		commision.put("beginTm", beginTm);
		commision.put("expireTm", expireTm);
		commision.put("packageSoldoutDt", packageSoldoutDt);
		commision.put("rateStartTm", rateStartTm);
		commision.put("rateStopTm", rateStopTm);
		commision.put("packageStopDt", packageStopDt);
		commision.put("orderPayedTm", orderPayedTm);
		commision.put("returnType",returnType );
		commision.put("comFlg", comFlg);
		commision.put("roundFlg", roundFlg);
		commision.put("investRate", investRate);
		commision.put("faceValue", faceValue);
		
		//使用示例
		DroolsTools.runDrools(RateUtils.RATESEQ_DRL_PATH, commision);
		System.out.println(commision);
		List rateSeq = (List)commision.get("rateSeq");
		System.out.println(rateSeq);*/
	}
	
	/**
	 * 测试用户利息
	 */
	public static void test_20150414165000(){
		//测试旧的计算利息
		Commision commision = new Commision();
		/*try{
			
			commision.put("investType", "020");
			commision.put("investRateType", "170");
			commision.put("returnType", "060");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "2.1");
			commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		try{
			
			commision.put("investType", "020" );
			commision.put("investRateType", "170");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("beginTm", "20150401121212");
			commision.put("expireTm", "20150415121212");
			commision.put("packageSoldoutDt", "20150406");
			commision.put("rateStartTm", "");
			commision.put("rateStopTm", "");
			commision.put("packageStopDt", "");
			commision.put("orderPayedTm", "20150403121212");
			commision.put("returnType","060");
			commision.put("comFlg", "010");
			commision.put("roundFlg", "0");
			commision.put("investRate", "2.1");
			commision.put("faceValue", new BigDecimal("100000000"));
			
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl;classpath:/drl/ratetm/ratetm.drl;classpath:/drl/rateseq/rateseq.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*try{
			commision.clear();
			commision.put("investType", "020");
			commision.put("investRateType", "170");
			commision.put("returnType", "060");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "2.1");
			commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			commision.put("orderPayedTm", "20150402121212"); // 订单支付时间
			commision.put("beginTm", "20150401150512");
			commision.put("expireTm", "20150415150512");
			
			DroolsTools.runDrools("classpath:/drl/ratetm/ratetm.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		/*	
		try{
			commision.clear();
			//commision.put("investType", "020");
			commision.put("investRateType", "160");
			commision.put("returnType", "050");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "15");
			commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			commision.clear();
			//commision.put("investType", "020");
			commision.put("investRateType", "160");
			commision.put("returnType", "050");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "15");
			commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			commision.clear();
			//commision.put("investType", "020");
			commision.put("investRateType", "160");
			commision.put("returnType", "050");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "15");
			//commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			commision.clear();
			//commision.put("investType", "020");
			commision.put("investRateType", "160");
			commision.put("returnType", "050");
			commision.put("investPeriodUnit", "自然月");
			commision.put("investPeriod", "3");
			commision.put("comFlg", "010");//
			commision.put("roundFlg", "0");//
			commision.put("investRate", "15");
			commision.put("packageSoldoutDt", "20150414");
			commision.put("faceValue", new BigDecimal("100000000"));//分
			
			DroolsTools.runDrools("classpath:/drl/rate/rate.drl", commision);
			//DroolsTools.runDrools("E:/rate.drl", commision);
			System.out.println(commision);
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
}
