package cn.com.xcommon.commons.drools.commisionRuleEngine.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.Activation;

import cn.com.xcommon.commons.drools.commision.Commision;
import cn.com.xcommon.commons.drools.commisionRuleEngine.CommisionRuleEngine;

public class CommisionRuleEngineImpl implements CommisionRuleEngine {

	/**
	 * ruleBase
	 */
	private RuleBase ruleBase;
	// drlPath
	private String drlPath;

	public void initEngine() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
		ruleBase = RuleBaseFactory.newRuleBase();

		try {
			PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
			ruleBase.addPackages(backageBuilder.getPackages());
		} catch (DroolsParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshEnginRule() {
		org.drools.rule.Package[] packages = ruleBase.getPackages();
		for (org.drools.rule.Package pg : packages) {
			ruleBase.removePackage(pg.getName());
		}
		System.out.println("初始化规则引擎...");
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
		try {
			PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
			ruleBase.addPackages(backageBuilder.getPackages());
		} catch (DroolsParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("初始化规则引擎结束.");
	}

	public void executeRuleEngine(Commision commision) {
		if(null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {  
            return;  
        }  
        StatefulSession statefulSession = ruleBase.newStatefulSession();  
        statefulSession.insert(commision);  
        // fire  
        statefulSession.fireAllRules(new org.drools.spi.AgendaFilter() {  
            public boolean accept(Activation activation) {  
                return !activation.getRule().getName().contains("_test");  
            }
        });  
        statefulSession.dispose();
	}
	/**
	 * 获取文件列表
	 * @return
	 */
	private List<String> getDrlFilesList() {
		List<String> drlFilePath = new ArrayList<String>();
		String[] paths = drlPath.split(";");
		for (String path : paths)
			drlFilePath.add(path);
		return drlFilePath;
	}

	/**
	 * 从Drl规则文件中读取规则
	 * 
	 * @return
	 * @throws Exception
	 */
	private PackageBuilder getPackageBuilderFromDrlFile() throws Exception {
		// 获取测试脚本文件
		List<String> drlFilePath = getDrlFilesList();
		// 装载测试脚本文件
		List<Reader> readers = readRuleFromDrlFile(drlFilePath);

		PackageBuilder backageBuilder = new PackageBuilder();
		for (Reader r : readers) {
			backageBuilder.addPackageFromDrl(r);
		}

		// 检查脚本是否有问题
		if (backageBuilder.hasErrors()) {
			throw new Exception(backageBuilder.getErrors().toString());
		}

		return backageBuilder;
	}

	/**
	 * @param drlFilePath
	 *            脚本文件路径
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	private List<Reader> readRuleFromDrlFile(List<String> drlFilePath)
			throws FileNotFoundException, UnsupportedEncodingException {
		if (null == drlFilePath || 0 == drlFilePath.size()) {
			return null;
		}
		List<Reader> readers = new ArrayList<Reader>();

		for (String ruleFilePath : drlFilePath) {
			if (ruleFilePath.startsWith("classpath")) {
				String classpath = ruleFilePath.split(":")[1];
				readers.add(new InputStreamReader(CommisionRuleEngineImpl.class.getResourceAsStream(classpath),"UTF-8"));
			} else {
				readers.add(new FileReader(new File(ruleFilePath)));
			}
		}
		return readers;
	}

	public String getDrlPath() {
		return drlPath;
	}

	public void setDrlPath(String drlPath) {
		this.drlPath = drlPath;
	}
}
