<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.dto.${className};

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.venus.core.annotation.BeanValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * Description: ${table.tableDesc?if_exists} 数据传输对象实体类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="${className}DTO", description="${table.tableDesc?if_exists}")
public class ${className}DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    <#list table.columns as column>
    <#if column.commonColumn == false>
    <#if column.pk>
    @BeanValid(label = "${column.columnComment?if_exists}", notEmpty = true)
    @ApiModelProperty(value="${column.columnComment?if_exists}")
    private ${column.columnClass} ${column.propertyName};

    <#else>
    /** ${column.columnComment?if_exists}**/
    @BeanValid(label = "${column.columnComment?if_exists}", notEmpty = true)
    @ApiModelProperty(value="${column.columnComment?if_exists}")
    private  ${column.columnClass} ${column.propertyName};

    </#if>
    </#if>
    </#list>
    public String getBusiKey(){
        // TODO 默认的业务主键，每个同步接口必须实现
        return null;
    }
}
