<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ${basePackage}.${moduleName}.model.${className};
import ${basePackage}.${moduleName}.dao.${className}Dao;
import ${basePackage}.${moduleName}.service.${className}Service;

import com.gmcc.estore.mmgt.product.model.PdInventoryDetail;
import com.gmcc.estore.mmgt.stock.dao.PdOfferStockDao;
import com.ibm.service.impl.OperateManagerImp;
/**
 * TODO(服务层实现类)
 * @author by ibm core generator
 * @version 1.0.0
 */
public class ${className}ServiceImpl extends OperateManagerImp<${className}, Long> implements ${className}Service {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ${className}Dao ${classNameLower}Dao;
	
	public ${className}Dao get${className}Dao() {
		return ${classNameLower}Dao;
	}

	public void set${className}Dao(${className}Dao ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
		this.${classNameLower}Dao.setEntityClass(${classNameLower}.class);
		this.${classNameLower}Dao.setPKClass(Long.class);
	}
	
}