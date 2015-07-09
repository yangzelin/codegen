<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ibm.util.annotation.DisplayColumn;
import com.ibm.model.BaseObject;

import org.apache.commons.lang.builder.*;
import java.math.BigDecimal;
/**
 *   ${table.tableDesc?if_exists} (实体类)
 * @author by turbo generator
 * @version 1.0.0
 */
@Entity
@Table(name = "${table.tableName}" )
public class ${className} extends BaseObject implements java.io.Serializable{
	<#list table.columns as column>
	<#if column.pk>
    /** ${column.propertyName} - ${column.columnComment?if_exists} */
    @Id
    @Column(name = "${column.columnName}")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DisplayColumn(property = "${column.propertyName}" , initFlag="1" , titleKey = "${classNameLower}.${column.propertyName}")
    private ${column.columnClass} ${column.propertyName};
    
    <#else>
    /** ${column.propertyName} - ${column.columnComment?if_exists} */
    @Column(name = "${column.columnName}")
    @DisplayColumn(property = "${column.propertyName}" , initFlag="1" , titleKey = "${classNameLower}.${column.propertyName}")
    private ${column.columnClass} ${column.propertyName};
    
    </#if>
    </#list>
    <#-- 输出set、get方法-->
    <@generateJavaColumns/>
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.propertyName}",get${column.methodName}())
		</#list>
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.methodName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		<#list table.columns as column>
		<#if column.pk>
		if(this.get${column.methodName}() == null){
			return false;
		}
		</#if>
		</#list>
		if(!(obj instanceof ${className})){
			return false;
		}
		if(this == obj) {
			return true;
		}
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.methodName}(),other.get${column.methodName}())
			</#list>
			.isEquals();
	}
	
	<#-- 输出BaseObject默认认方法-->
	<#if (!table.contains('id'))>
	<@generateSetGetMethod propertyName='id'  columnClass='java.util.Long' />
	</#if>
	<#if (!table.contains('createdBy'))>
	<@generateSetGetMethod propertyName='createdBy'  columnClass='java.lang.String' />
	</#if>
	<#if (!table.contains('createdTime'))>
	<@generateSetGetMethod propertyName='createdTime'  columnClass='java.util.Date' />
	</#if>
	<#if (!table.contains('lastUpdatedBy'))>
	<@generateSetGetMethod propertyName='lastUpdatedBy'  columnClass='java.lang.String' />
	</#if>
	<#if (!table.contains('lastUpdatedTime'))>
	<@generateSetGetMethod propertyName='lastUpdatedTime'  columnClass='java.util.Date' />
	</#if>
	<#if (!table.contains('enabled'))>
	<@generateSetGetMethod propertyName='enabled'  columnClass='java.lang.String' />
	</#if>
	
}
<#macro generateJavaColumns>
<#list table.columns as column><#if column.pk>
	public void set${column.methodName}(${column.columnClass} ${column.propertyName}) {
		this.${column.propertyName} = ${column.propertyName};
	}
	
	public ${column.columnClass} get${column.methodName}() {
		return this.${column.propertyName};
	}
<#else>
    public void set${column.methodName}(${column.columnClass} ${column.propertyName}) {
    	this.${column.propertyName} = ${column.propertyName};
    }

    public ${column.columnClass} get${column.methodName}() {
    	return this.${column.propertyName};
    }
    
</#if>
	</#list>
</#macro>
<#macro generateSetGetMethod propertyName columnClass>
<#assign propertyNameUpper = propertyName?cap_first>
	public void set${propertyNameUpper}(${columnClass} ${propertyName}) {
		
	}
	
	@Transient
	public ${columnClass} get${propertyNameUpper}() {
		return null;
	}
	
</#macro>