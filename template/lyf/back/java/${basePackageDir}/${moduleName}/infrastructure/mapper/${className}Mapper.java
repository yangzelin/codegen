<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.mapper;

import ${basePackage}.${moduleName}.infrastructure.dataobject.${className}DO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description: Mapper 接口层
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Mapper
public interface ${className}Mapper {

    //根据id查询单个对象
    ${className}DO getById(@Param(value = "id") Long id);

    //根据多个条件查询符合条件的list
    List<${className}DO> getByMap(Map<String, Object> param);

    //根据多个条件查询符合条件的总数量
    Integer getCountByMap(Map<String, Object> param);

    //插入一个对象，返回受影响的行数
    Integer insert(${className}DO ${classNameLower}DO);

    //修改一个对象，返回受影响的行数
    Integer update(${className}DO ${classNameLower}DO);

    //删除一个对象，返回受影响的行数
    Integer deleteById(@Param(value = "id") Long id);

    //批量插入
    Integer insertBatch(List<${className}DO> ${classNameLower}DOList);

    //批量更新
    Integer updateBatch(List<${className}DO> ${classNameLower}DOList);

    // 批量逻辑删除
    Integer removeByIds(List<Long> ids);
}

