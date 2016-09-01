<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service;

import java.util.List;
import java.util.Map;

import com.bailian.core.framework.base.page.Page;

import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.domain.vo.${className}DTO;



/**
 * TODO ${table.tableDesc?if_exists}(服务类)
 * @author by ibm core generator
 * @version 1.0.0
 */
public interface ${className}Service {

	public int insert(${className} ${classNameLower});
	
	public ${className} selectByPrimaryKey(Long sid);
	
	public int deleteByPrimaryKey(Long sid);
	
	public int updateByPrimaryKey(${className} ${classNameLower});
	
	public List<${className}> selectByParam(Map<String,Object> params);
	
	public Page<${className}> selectPageListByParam(Map<String, Object> paramMap, Page<${className}> page);
}
