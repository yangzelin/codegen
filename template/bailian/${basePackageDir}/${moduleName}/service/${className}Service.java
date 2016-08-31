<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service;


import ${basePackage}.${moduleName}.model.${className};

import com.ibm.service.IOperateManager;
/**
 * TODO(服务类)
 * @author by ibm core generator
 * @version 1.0.0
 */
public interface ${className}Service extends IOperateManager<${className}, Long>{

	public Long insert(${className} ${classNameLower});
	
	public ${className} selectByPrimaryKey(Long sid);
	
	public int deleteByPrimaryKey(Long sid);
	
	public int updateByPrimaryKey(${className} ${classNameLower});
	
	public List<${className}> getByParams(Map<String,Object> params);
}
