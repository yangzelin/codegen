<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.vo;

import ${basePackage}.${moduleName}.domain.entity.${className};
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * Description: 视图实体类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "${className}VO对象", description = "${className}VO对象")
public class ${className}VO extends ${className} implements Serializable {

    private static final long serialVersionUID = 1L;

}
