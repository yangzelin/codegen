<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.api.controller;

import java.util.*;

import com.github.pagehelper.PageInfo;
import com.rome.arch.core.clientobject.Response;
import com.rome.arch.util.controller.RomeController;
import com.venus.bill.common.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ${basePackage}.${moduleName}.infrastructure.dataobject.${className}DO;
import ${basePackage}.${moduleName}.api.dto.${className}DTO;
import ${basePackage}.${moduleName}.domain.service.${className}Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *
 * <p>
 * Description: ${table.tableDesc?if_exists} Controller Layer
 * </p>
 * @author yangzelin
 * @date 2020/3/11
 *
 */
@Api(value = "${table.tableDesc?if_exists}", tags = "${table.tableDesc?if_exists}")
@Slf4j
@RestController
@RomeController
@RequestMapping("/${classNameLower}")
public class ${className}AdminController {

	private ${className}Service ${classNameLower}Service;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入${className}")
	public Response<${className}DTO> detail(${className}DTO ${classNameLower}) {
		${className}DTO detail = ${classNameLower}Service.getById(${classNameLower}.getId());
		return Response.builderSuccess(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入${classNameLower}")
	public Response<PageInfo<${className}DTO>> list(${className}DTO ${classNameLower}, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		//TODO 请求对象转换为参数Map
		Util.beanCondtionToMap(param, cardBill);
		param.put("page", Util.getPageNo(request));
		param.put("limit", Util.getPageSize(request));
		PageInfo<${className}DTO>  pages = ${classNameLower}Service.queryPageByMap(param);
		return Response.builderSuccess(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入${classNameLower}")
	public Response submit(@RequestBody ${className}DTO ${classNameLower}) {
		// TODO 校验逻辑
		${classNameLower}Service.saveOrUpdate(${classNameLower});
		return Response.builderSuccess(${classNameLower});
	}

	/**
	 * 批量新增或修改
	 */
	@PostMapping("/submitBatch")
	@ApiOperation(value = "批量新增或修改", notes = "传入${classNameLower}List")
	public Response submitBatch(@Valid @RequestBody List<${className}DTO> ${classNameLower}List) {
		// TODO批量校验数据
		boolean result = ${classNameLower}Service.saveOrUpdateBatch(${classNameLower}List);
		return Response.builderSuccess(result);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public Response remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return Response.builderSuccess(${classNameLower}Service.removeByIds(Util.toIntList(ids)));
	}
}
