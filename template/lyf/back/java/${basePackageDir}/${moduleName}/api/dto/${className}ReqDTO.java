<#assign className ="${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.domain.dto.${className};

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.venus.core.model.BaseReqDTO;

import java.util.List;


/**
 * <p>
 * Description: ${table.tableDesc?if_exists} 接口请求对象
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Data
@EqualsAndHashCode
@ApiModel(value="${className}ReqDTO", description="${table.tableDesc?if_exists}查询请求对象")
public class ${className}ReqDTO extends BaseReqDTO<List<${className}DTO>> {

}
