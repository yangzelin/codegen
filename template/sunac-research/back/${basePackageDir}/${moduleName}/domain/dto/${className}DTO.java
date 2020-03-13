<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.dto.${className};

import ${basePackage}.${moduleName}.domain.entity.${className};
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Description: 数据传输对象实体类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ${className}DTO extends ${className} {

    private static final long serialVersionUID = 1L;

}
