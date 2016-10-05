<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

、
import ${basePackage}.${moduleName}.dao.${className}Repository;
import ${basePackage}.${moduleName}..domain.${className};

/**
 * TODO ${table.tableDesc}(服务层接口)
 * @author by turbo core generator
 * @version 1.0.0
 */
public class ${className}Service {
	
	@Autowired
	private ${className}Repository ${classNameLower}Repository;
	
	public ${className} save(${className} ${classNameLower}){
		${classNameLower} = ${classNameLower}Repository.save(${classNameLower});
		return ${classNameLower};
	}
	
	public Long count(){
		return ${classNameLower}Repository.count();
	}
	
	public List<${className}>findAll(){
		return ${classNameLower}Repository.findAll();
	}
	
	public List<${className}> findOne(Sort sort){
		return ${classNameLower}Repository.findAll(sort);
	}
	
	public ${className} findOne(Long sid){
		return ${classNameLower}Repository.findOne(sid);
	}
	
	public ${className} findOne(Example<${className}> expression){
		return ${classNameLower}Repository.findOne(expression);
	}
	
	public boolean exists(Long sid){
		return ${classNameLower}Repository.exists(sid);
	}
	
	public boolean exists(Example<${className}> expression){
		return ${classNameLower}Repository.exists(expression);
	}
	
	public void delete(Long sid){
		${classNameLower}Repository.delete(sid);
	}
	
	public void delete(${className} entity){
		${classNameLower}Repository.delete(entity);
	}
	
	/**
	 * 
	 * <p>
	 * Description:获取最近日期 Top5 的记录
	 * </p>
	 * @return
	 * @auhtor Administrator
	 * @date 2016年10月5日
	 */
	public List<${className}> getTop(int topNum){
		if(topNum < 1){
			topNum = 5;
		}
		Pageable pageble = new PageRequest(1,topNum);
		Page<${className}> page = ${classNameLower}Repository.getTop(pageble);
		return page.getContent();
	}
}
