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

}
