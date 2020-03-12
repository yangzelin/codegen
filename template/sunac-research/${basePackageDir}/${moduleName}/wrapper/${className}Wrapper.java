<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.wapper;


import com.ibm.ms.mp.support.BaseEntityWrapper;
import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.domain.vo.${className}VO;
import ${basePackage}.${moduleName}.service.I${className}Service;
import com.ibm.ms.spring.SpringContextHolder;
import org.springframework.beans.BeanUtils;


/**
 * <p>
 * Description: 包装类,返回视图层所需的字段
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
public class ${className}Wrapper extends BaseEntityWrapper<${className}, ${className}VO> {
    private static I${className}Service ${classNameLower}Service;

    static {
        ${classNameLower}Service = SpringContextHolder.getBean(I${className}Service.class);
    }

    public static ${className}Wrapper build() {
        return new ${className}Wrapper();
    }

    @Override
    public ${className}VO entityVO(${className} ${classNameLower}) {
        ${className}VO ${classNameLower}VO = new ${className}VO();
        BeanUtils.copyProperties(${classNameLower}, ${classNameLower}VO);
        return ${classNameLower}VO;
    }
}
