<#assign className = "${table.className}">
package ${basePackage}.${moduleName}.dao;


import java.util.*;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.yn10086.commons.base.BaseHibernateDao;
import ${basePackage}.${moduleName}.domain.*;

/**
 * TODO(Dao)
 * @author by turbo core generator
 * @version 1.0.0
 */
@Repository
public class ${className}Dao extends BaseHibernateDao<${className},Long>  {

}

