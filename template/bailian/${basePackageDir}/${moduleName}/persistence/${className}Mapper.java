<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.persistence;

import java.util.List;
import java.util.Map;
import ${basePackage}.${moduleName}.domain.entity.${className};

/**
 * TODO(持久化类) Mapper层
 * @author by turbo core generator
 * @version 1.0.0
 */
public interface ${className}Mapper{

	public Long insert(${className} ${classNameLower});
	
	public ${className} selectByPrimaryKey(Long sid);
	
	public int deleteByPrimaryKey(Long sid);
	
	public int updateByPrimaryKey(${className} ${classNameLower});
	
	public List<${className}> getByParams(Map<String,Object> params);

}

