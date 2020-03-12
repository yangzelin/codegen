<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.controller;

import java.util.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ibm.ms.api.R;
import com.ibm.ms.auth.domain.AuthUser;
import com.ibm.ms.mp.support.Condition;
import com.ibm.ms.mp.support.Query;
import com.ibm.ms.research.admin.common.ctr.BaseController;

import com.ibm.ms.tools.fun.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.domain.vo.${className}DTO;
import ${basePackage}.${moduleName}.domain.dto.${className}DTO;
import ${basePackage}.${moduleName}.service.I${className}Service;
import ${basePackage}.${moduleName}.wapper.I${className}Wrapper;


/**
 * 
 * <p>
 * Description: ${table.tableDesc?if_exists} Controllerc Layer
 * </p>
 * @author yangzelin
 * @date 2020/3/11
 *
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/${classNameLower}")
@Api(value = "${table.tableDesc?if_exists}", tags = "${table.tableDesc?if_exists}")
public class ${className}Controller extends BaseController{

	private I${className}Service ${classNameLower}Service;


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入${className}")
	public R<${className}VO> detail(${className} ${classNameLower}) {
		${className} detail = ${classNameLower}Service.getOne(Condition.getQueryWrapper(${classNameLower}));
		return R.data(${className}Wrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入${classNameLower}")
	public R<IPage<${className}>> list(${className} ${classNameLower}, Query query) {
		IPage<${className}> pages = ${classNameLower}Service.page(Condition.getPage(query), Condition.getQueryWrapper(${classNameLower}));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入${classNameLower}")
	public R submit(@Valid @RequestBody ${className} ${classNameLower}, AuthUser user) {
		// TODO 校验逻辑
		return R.status(${classNameLower}Service.saveOrUpdate(${classNameLower}));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(${classNameLower}Service.removeByIds(Func.toIntList(ids)));
	}
}
