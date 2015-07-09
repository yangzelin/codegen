<#assign className = "${table.className}">
package ${basePackage}.${moduleName}.dao.impl;

import ${basePackage}.${moduleName}.model.*;

import ${basePackage}.${moduleName}.dao.${className}Dao;

import com.gmcc.dao.hibernate.base.BaseDao;
import com.gmcc.export.model.ExportTask;

/**
 * TODO(持久化类)
 * @author by turbo core generator
 * @version 1.0.0
 */
public class ${className}DaoImpl extends BaseDao<${className}, Long> implements ${className}Dao {
	
	public ${className}DaoImpl() {
		this.setEntityClass(${className}.class);
		this.setPKClass(Long.class);
	}
	

}

