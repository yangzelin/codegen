<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ibm.ms.research.admin.common.domain.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Description: ${table.tableDesc} 实体类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${table.tableName}")
@ApiModel(value = "${className}对象", description = "${className}对象")
public class ${className} extends BaseEntity {

	<#list table.columns as column>
	<#if !column.isCommonColumn>
	<#if column.pk>
	/** ${column.columnComment?if_exists}  使用接口IdentifierGenerator的方法nextId(默认实现类为 DefaultIdentifierGenerator 雪花算法)**/
	@TableId(value = "${column.columnName}", type = IdType.ASSIGN_ID)
    private ${column.columnClass} ${column.propertyName};
    <#else>
	/** ${column.columnComment?if_exists}**/
	@ApiModelProperty(value = "${column.columnComment?if_exists}")
	@TableField("${column.columnName}")
	private  ${column.columnClass} ${column.propertyName};
    </#if>
	</#if>
    </#list>
}