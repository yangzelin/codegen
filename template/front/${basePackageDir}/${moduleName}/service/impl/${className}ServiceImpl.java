<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yn10086.commons.base.BaseEntityManager;
import cn.yn10086.commons.base.EntityDao;
import cn.yn10086.commons.helper.ServiceManager;
import ${basePackage}.${moduleName}.domain.${className};
import ${basePackage}.${moduleName}.dao.${className}Dao;
import ${basePackage}.${moduleName}.service.${className}Service;

/**
 * TODO(服务层实现类)
 * @author by turbo core generator
 * @version 1.0.0
 */
@Service
public class ${className}ServiceImpl extends BaseEntityManager<${className}, Long> implements ${className}Service {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ${className}Dao ${classNameLower}Dao;
	
	@Override
	protected EntityDao<${className}, Long> getEntityDao() {
		return this.${classNameLower}Dao;
	}
	
	@Autowired
	public void set${className}Dao(${className}Dao ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}
	
	
	
}