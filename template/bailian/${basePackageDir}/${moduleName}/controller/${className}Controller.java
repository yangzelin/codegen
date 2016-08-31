<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.action;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import ${basePackage}.${moduleName}.domain.entity.${className};
import ${basePackage}.${moduleName}.service.${className}Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bailian.core.framework.base.controller.BaseController;
import com.bailian.core.framework.base.page.Page;
import com.bailian.core.framework.exception.BleException;
import com.bailian.core.utils.HttpUtil;
import com.bailian.core.utils.JsonUtil;
import com.bailian.core.utils.PropertyConfigurer;
import com.bailian.core.utils.ResultUtil;


@Controller
@RequestMapping("/${classNameLower}Admin")
public class ${className}Controller extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8393912077646937944L;
	
	@Autowired
	private ${className}Service ${classNameLower}Service;

	/**
	 * 
	 *<p>
	 * Description:
	 *</p>
	 * @return
	 * @auth zelinyang
	 * @date 2016年8月31日
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestBody ${className} para, HttpServletRequest request) throws ParseException {
		return ResultUtil.creObjSucResult(null);
	}
}
