<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.persistence.${className}Mapper;
import ${basePackage}.${moduleName}.service.${className}Service;

/**
 * TODO(服务层实现类)
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
	
	public Long insert(${className} ${classNameLower}){
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
	
	public List<${className}> getByParams(Map<String,Object> params){
		return ${classNameLower}Mapper.getByParams(params);
	}
	
}