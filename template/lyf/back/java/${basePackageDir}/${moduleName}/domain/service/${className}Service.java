<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.service;

import com.github.pagehelper.PageInfo;
import com.rome.arch.core.clientobject.Response;
import ${basePackage}.${moduleName}.api.dto.${className}ReqDTO;
import ${basePackage}.${moduleName}.api.dto.${className}DTO;
import ${basePackage}.${moduleName}.infrastructure.dataobject.${className}DO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description: ${table.tableDesc?if_exists}  Service 接口层
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
public interface ${className}Service {

    // 批量新增 ${className} 对象
    Response intfSync${className}(${className}ReqDTO reqDTO);

    //根据ID查询${className}对象
    ${className}DTO getById(Long id);

    //根据map查询${className} List
    List<${className}DTO> getListByMap(Map<String, Object> param);

    //根据map查询${className} 总数
    Integer getCountByMap(Map<String, Object> param);

    //新增${className}对象
    Integer save(${className}DTO ${classNameLower});

    //更新${className}对象
    Integer update(${className}DTO ${classNameLower});

    //新增或者修改 ${className} 对象
    Integer saveOrUpdate(${className}DTO ${classNameLower});

    //根据ID物理删除${className}对象
    Integer deleteById(Long id);

    //根据map分页查询${className}
    PageInfo<${className}DTO> queryPageByMap(Map<String, Object> params);

    //批量更新${className}对象
    Integer saveBatch(List<${className}DTO> ${classNameLower}List);

    //批量新增${className}对象
    Integer updateBatch(List<${className}DTO> ${classNameLower}List);

    //批量新增或者更新CardBill对象
    boolean saveOrUpdateBatch(List<${className}DTO> ${classNameLower}List);

    // 批量更加id 逻辑删除
    Integer removeByIds(List<Long> ids);
}
