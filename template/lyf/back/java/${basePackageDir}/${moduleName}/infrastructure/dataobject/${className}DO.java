<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.infrastructure.dataobject;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * <p>
 * Description: ${table.tableDesc?if_exists} 实体类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode
public class ${className}DO implements Serializable {
	private static final long serialVersionUID = -1L;
	<#list table.columns as column>
	<#if column.commonColumn == false>
	<#if column.pk>
	/** ${column.columnComment?if_exists}  使用接口IdentifierGenerator的方法nextId(默认实现类为 DefaultIdentifierGenerator 雪花算法)**/
    private ${column.columnClass} ${column.propertyName};

    <#else>
	/** ${column.columnComment?if_exists}**/
	private  ${column.columnClass} ${column.propertyName};

    </#if>
	</#if>
    </#list>
}
