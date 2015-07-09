<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
<#assign moduleNameLower = moduleName?uncap_first>
package ${basePackage}.${moduleName}.controller;

import java.util.*;


import ${basePackage}.${moduleName}.domain.${className};
import ${basePackage}.${moduleName}.service.${className}Service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yn10086.commons.base.BaseRestSpringController;
import cn.yn10086.commons.helper.ServiceManager;

@Controller
@RequestMapping("/${moduleNameLower}/${classNameLower}")
public class ${className}Controller extends BaseRestSpringController {
	
	@RequestMapping(value = "/query")
	public @ResponseBody List<${className}> query(String mobileNo) {
//		return ServiceManager.logisticsService.findLogisticsList(mobileNo);
		return null;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(String logisticsStr) {
//		List<TOdLogistics> logLst =new ArrayList<TOdLogistics>();
//		ServiceManager.logisticsService.batchSaveLogisticsList(logLst);
	}

}
