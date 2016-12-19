package cn.kanmars.sn.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.base.AdvancedAjaxBaseController;
import cn.kanmars.sn.entity.TblFileInfo;
import cn.kanmars.sn.logic.FileInfoLogic;
import cn.kanmars.sn.util.SysSequenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("/file")
public class FileUpLoadController  extends AdvancedAjaxBaseController {
	protected HLogger logger = LoggerManager.getLoger("FileUpLoadController");
	@Autowired
	private FileInfoLogic fileInfoLogic;
	
	@Value("${file_root_path:/tmp}")
	private String file_root_path;//文件根路径
	
	@Value("${file_path:temp}")
	private String file_path;
	
	@RequestMapping("/upLoad")
	public void upLoad(@RequestParam(value = "upFile", required = false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> othMsg=new HashMap<String, Object>();
		logger.info("------------开始文件上传----------------------");
		TblFileInfo tblFileInfo=new TblFileInfo();
		try {
			Date date=new Date();
			SimpleDateFormat sim=new SimpleDateFormat("yyyymmddHHmmss");
			tblFileInfo.setFileNo(Integer.parseInt(SysSequenceUtils.generateStringIdNoneTopAndSuffixAndLength("tbl_file_info")));//
			tblFileInfo.setBusinessType(request.getParameter("business_type"));//业务类型
			tblFileInfo.setBusinessNo(Integer.parseInt(request.getParameter("business_no")));//业务编号，关联用
			tblFileInfo.setFileName(file.getOriginalFilename());//文件原来名称
			String new_file_name=sim.format(date)+file.getOriginalFilename();
			tblFileInfo.setFilePath(file_path+"/"+new_file_name);//文件路径
			tblFileInfo.setFileRootPath(file_root_path);//文件根路径
			tblFileInfo.setCreateTm(sim.format(date));//创建时间
			tblFileInfo.setFileStat("010");//文件状态有效
			tblFileInfo.setFileSize(String.valueOf(file.getSize()/100)+"kb");
			tblFileInfo.setFileDesc(request.getParameter("file_desc"));
			int flag= fileInfoLogic.insertFileInfo(tblFileInfo);
			File targetFile = new File("d://", new_file_name);  
			if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
			file.transferTo(targetFile);  
		    othMsg.put("fileName",  file.getOriginalFilename());
		    othMsg.put("file_path", file_root_path+file_path+"/"+new_file_name);
		    othMsg.put("file_size", String.valueOf(file.getSize()/100)+"kb");
		    othMsg.put("file_desc", tblFileInfo.getFileDesc());//文件描述
		    othMsg.put("file_no",tblFileInfo.getFileNo());
		    othMsg.put("business_no", tblFileInfo.getBusinessNo());//业务编号
		    othMsg.put("business_type", tblFileInfo.getBusinessType());//业务类型
			ajaxJsonMessage(request,response,SUCCESS, "上传成功", "upload", othMsg);
			logger.info("----------------文件上传成功------------------------------");
		} catch (Exception e) {
			logger.info("上传失败",e);
			ajaxJsonMessage(request, response, ERROR,"操作异常,请联系管理员！", SAVE);
		}
	}
	@RequestMapping("/fileDemo")
	public String init() {
		return "file/file";
	}

}
