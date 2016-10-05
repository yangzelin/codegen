<#assign className = "${table.className}">
package ${basePackage}.${moduleName}.dao;


import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ${basePackage}.${moduleName}.domain.*;

/**
 * TODO(Dao)
 * @author by turbo core generator
 * @version 1.0.0
 */
@Repository
public interface ${className}Repository extends JpaRepository<${className}, Long>  {

	@Query("select t from ${className} t order by t.weekBegin desc, t.week desc")
	public Page<${className}> getTop(Pageable pageable);
	
}

