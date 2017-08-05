<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.apache.commons.lang.builder.*;

import cn.lightjob.charts.common.Global;

import java.math.BigDecimal;

/**
 *  ${table.tableDesc} 实体类
 * @ClassName: ${table.tableName}
 * @Description: 
 * @author yangzelin
 * @date 2017年8月5日 下午10:02:06
 */
@Entity
@Table(schema=Global.DATABASE_CHART_USER_SCHEMA , name="${table.tableName}")
public class ${className}  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	// alias
	public static final String TABLE_ALIAS = "${table.tableName}";
	<#list table.columns as column>
	public static final String ${column.columnName} = "${column.propertyName}";
	</#list>
	
	// properties
	<#list table.columns as column>
	<#if column.pk>
    /** ${column.propertyName} - ${column.columnComment?if_exists} */
    @Id
    @Column(name = "${column.columnName}")
    @GeneratedValue(strategy = GenerationType.TABLE,generator="idGen")
    @TableGenerator(name="idGen",schema=Global.DATABASE_CHART_USER_SCHEMA,table="SYS_ID",pkColumnName="key_id",valueColumnName="key_value",pkColumnValue="${table.tableName}_ID")
    private ${column.columnClass} ${column.propertyName};
    <#else>
    /** ${column.propertyName} - ${column.columnComment?if_exists} */
    @Column(name = "${column.columnName}")
    private ${column.columnClass} ${column.propertyName};
    </#if>
    
    </#list>
    
    <#-- output set get method-->
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