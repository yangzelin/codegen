<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.api.controller;

import java.util.*;


import com.rome.arch.core.clientobject.Response;
import com.rome.arch.util.controller.RomeController;
import ${basePackage}.${moduleName}.api.dto.${className}ReqDTO;
import ${basePackage}.${moduleName}.domain.service.${className}Service;
import com.venus.core.annotation.BeanValid;
import com.venus.core.annotation.LogRecord;
import com.venus.lock.annotation.DistributedLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>
 * Description: ${table.tableDesc?if_exists} Controllerc Layer
 * </p>
 * @author yangzelin
 * @date 2020/3/11
 *
 */
@Api(value = "${table.tableDesc?if_exists}", tags = "${table.tableDesc?if_exists}")
@Slf4j
@RestController
@RomeController
@RequestMapping("/intf/bill/${classNameLower}")
public class ${className}RestController {

	@Autowired
	private ${className}Service ${classNameLower}Service;

	@DistributedLock(lockKey = {"'${table.tableName?lower_case}#'+#reqDTO.data[0].recordId+':'+#reqDTO.data[0].companyCode+'#'+#reqDTO.data[0].postDate"})
	@LogRecord(name = "同步${table.tableDesc?if_exists}数据同步财务中台")
	@BeanValid
	@ApiOperation(value = "同步${table.tableDesc?if_exists}数据到财务中台")
	@ApiResponse(code = 0, message = "success", response = Response.class)
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public Response sync(@RequestBody ${className}ReqDTO reqDTO) {
		Response rsp = cardBillService.intfSync${className}(reqDTO);
		return rsp;
	}

}
