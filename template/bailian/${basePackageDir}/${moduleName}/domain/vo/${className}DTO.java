<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.vo;


import ${basePackage}.${moduleName}.domain.entity.${className};

/**
 * TODO(Vo)
 * @author by turbo core generator
 * @version 1.0.0
 */
public class ${className}DTO extends ${className}{
	<#list table.columns as column>
	<#if (column.columnType == "DATE" || column.columnType == "TIMESTAMP")>
    /** ${column.propertyName} - ${column.columnComment?if_exists} 开始 */
    private ${column.columnClass} ${column.propertyName}Start;
    
    /** ${column.propertyName} - ${column.columnComment?if_exists} 结束*/
    private ${column.columnClass} ${column.propertyName}End;
    </#if>
    </#list>
    
    <#list table.columns as column>
	<#if (column.columnType == "DATE" || column.columnType == "TIMESTAMP")>
	<#-- 输出set、get方法-->
    <@generateSetGetMethod propertyName='${column.propertyName}Start' columnClass='${column.columnClass}' />
    <@generateSetGetMethod propertyName='${column.propertyName}End' columnClass='${column.columnClass}' />
    </#if>
    </#list>
    
}
<#macro generateSetGetMethod propertyName columnClass>
<#assign propertyNameUpper = propertyName?cap_first>
	public void set${propertyNameUpper}(${columnClass} ${propertyName}) {
		this.${propertyName} = ${propertyName};
	}
	
	public ${columnClass} get${propertyNameUpper}() {
		return this.${propertyName};
	}
	
</#macro>