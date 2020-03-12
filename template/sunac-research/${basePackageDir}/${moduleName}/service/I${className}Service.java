<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service;

import com.ibm.ms.research.admin.common.service.BaseService;
import ${basePackage}.${moduleName}.domain.entity.Question;

/**
 * <p>
 * Description: Service 接口层
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
public interface I${className}Service extends BaseService<${className}> {

}
