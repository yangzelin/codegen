<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.action;

import java.util.*;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import ${basePackage}.${moduleName}.model.${className};
import ${basePackage}.${moduleName}.service.${className}Service;

import com.gmcc.export.service.ExportTaskService;
import com.gmcc.webapp.action.base.DisplayTagQueryAction;


public class ${className}ListAction extends DisplayTagQueryAction<${className}, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8393912077646937944L;
	
	private ${className}Service ${classNameLower}Service;

	/**
	 * 加载数据
	 * 
	 * @return
	 */
	public String load() throws Exception {
		return SUCCESS;
	}
	
	@Override
	public List<Criterion> filters() throws Exception {
		List<Criterion> list = super.filters();
		
		
		return list;
	}
	@Override
	public List<Order> orders() {
		List<Order> list = super.orders();
		list.add(Order.desc("offerId"));
		return list;
	}
	
	public ${className}Service get${className}Service() {
		return ${classNameLower}Service;
	}

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
		this.${classNameLower}Service.setEntityClass(${className}.class);
		this.${classNameLower}Service.setPKClass(Long.class);
	}
}
