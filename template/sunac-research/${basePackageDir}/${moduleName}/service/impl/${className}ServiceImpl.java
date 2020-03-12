<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service.impl;


import com.ibm.ms.research.admin.common.service.impl.BaseServiceImpl;
import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.mapper.${className}Mapper;
import ${basePackage}.${moduleName}.service.I${className}Service;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description: 服务实现类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Mapper, ${className}> implements I${className}Service {

}
