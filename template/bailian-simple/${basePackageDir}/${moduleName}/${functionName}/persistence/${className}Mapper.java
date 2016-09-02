<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.${functionName}.persistence;

import java.util.List;
import java.util.Map;

import ${basePackage}.${moduleName}.${functionName}.domain.entity.${className};
import ${basePackage}.${moduleName}.${functionName}.domain.dto.${className}DTO;

/**
 * TODO(持久化类) ${table.tableDesc?if_exists}Mapper层
 * @author by turbo core generator
 * @version 1.0.0
 */
public interface ${className}Mapper{

	public int insert(${className} ${classNameLower});
	
	public ${className} selectByPrimaryKey(Long sid);
	
	public int deleteByPrimaryKey(Long sid);
	
	public int updateByPrimaryKey(${className} ${classNameLower});
	
	public List<${className}> selectByParam(${className}DTO ${classNameLower}DTO);
	
	public int getCountByParam(${className}DTO ${classNameLower}DTO);
	
	public List<${className}> selectPageListByParam(Map<String,Object> paramMap ${classNameLower}DTO);

}

