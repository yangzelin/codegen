<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.${functionName}.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bailian.core.framework.base.page.Page;

import ${basePackage}.${moduleName}.${functionName}.domain.entity.${className};
import ${basePackage}.${moduleName}.${functionName}.domain.dto.${className}DTO;
import ${basePackage}.${moduleName}.${functionName}.persistence.${className}Mapper;
import ${basePackage}.${moduleName}.${functionName}.service.${className}Service;



/**
 * TODO ${table.tableDesc?if_exists}(服务层实现类)
 * @author by ibm core generator
 * @version 1.0.0
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ${className}Mapper ${classNameLower}Mapper;
	
	public ${className}Mapper get${className}Mapper() {
		return ${classNameLower}Mapper;
	}

	public void set${className}Mapper(${className}Mapper ${classNameLower}Mapper) {
		this.${classNameLower}Mapper = ${classNameLower}Mapper;
	}
	
	public int insert(${className} ${classNameLower}){
		return ${classNameLower}Mapper.insert(${classNameLower});
	}
	
	public ${className} selectByPrimaryKey(Long sid){
		return ${classNameLower}Mapper.selectByPrimaryKey(sid);
	}
	
	public int deleteByPrimaryKey(Long sid){
		return ${classNameLower}Mapper.deleteByPrimaryKey(sid);
	}
	
	public int updateByPrimaryKey(${className} ${classNameLower}){
		return ${classNameLower}Mapper.updateByPrimaryKey(${classNameLower});
	}
	
	public List<${className}> selectByParam(${className}DTO params){
		return ${classNameLower}Mapper.selectByParam(params);
	}
	
	public Page<${className}> selectPageListByParam(${className}DTO params, Page<${className}> page){
		if(params == null){
			params = new ${className}DTO();
		}
		if(page == null){
			page = new Page<${className}>();
		}
		int count = ${classNameLower}Mapper.getCountByParam(params);
		page.setCount(count);
		params.setStart(page.getStart());
		params.setEnd(page.getEnd());
		List<${className}> list = ${classNameLower}Mapper.selectPageListByParam(params);
		if (list != null && list.size() > 0) {
			page.setList(list);
			return page;
		}
		return page;
	}
	
}