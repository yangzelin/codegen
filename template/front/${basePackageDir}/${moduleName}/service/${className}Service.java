<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service;

import java.util.*;
import cn.yn10086.commons.base.IBaseEntityManager;
import ${basePackage}.${moduleName}.domain.${className};

/**
 * TODO(服务层接口)
 * @author by turbo core generator
 * @version 1.0.0
 */
public interface ${className}Service extends IBaseEntityManager<${className}, Long>{
	
}
