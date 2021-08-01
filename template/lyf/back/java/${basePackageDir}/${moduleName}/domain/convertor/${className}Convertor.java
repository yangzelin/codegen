<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.convertor;

import com.rome.arch.core.domain.EntityFactory;
import ${basePackage}.${moduleName}.api.dto.*;
import org.mapstruct.Mapper;
import com.github.pagehelper.PageInfo;
import ${basePackage}.${moduleName}.infrastructure.dataobject.${className}DO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * <p>
 * Description: ${table.tableDesc?if_exists}  转换器
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Mapper(componentModel = "spring", uses = EntityFactory.class)
public interface ${className}Convertor {

    //dataobject转成DTO
    ${className}DTO doToDto(${className}DO ${classNameLower}DO);

    //DTO转成dataobject
    ${className}DO dtoToDo(${className}DTO ${classNameLower}DTO);

    //dataobject list转成DTO list
    List<${className}DTO> doListToDtoList(List<${className}DO> ${classNameLower}DOList);

    //DTO list转成dataobject list
    List<${className}DO> dtoListToDoList(List<${className}DTO> ${classNameLower}DTOList);

    //dataobject page转成DTO page
    PageInfo<${className}DTO> doToDto(PageInfo<${className}DO> ${classNameLower}DOPageInfo);

    //DTO page转成dataobject page
    PageInfo<${className}DO> dtoToDo(PageInfo<${className}DTO> ${classNameLower}PageInfo);

}

