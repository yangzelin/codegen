<#assign className = "${table.className}">
<#assign classNameLower = className?uncap_first>
package ${basePackage}.${moduleName}.controller;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bailian.common.core.BaseQueryParam;
import com.bailian.core.framework.base.controller.BaseController;
import com.bailian.core.framework.base.page.Page;
import com.bailian.core.framework.exception.BleException;
import com.bailian.core.utils.HttpUtil;
import com.bailian.core.utils.JsonUtil;
import com.bailian.core.utils.PropertyConfigurer;
import com.bailian.core.utils.ResultUtil;
import com.bailian.stock.params.controller.ParamsController;

import ${basePackage}.${moduleName}.${functionName}.domain.entity.${className};
import ${basePackage}.${moduleName}.${functionName}.domain.dto.${className}DTO;
import ${basePackage}.${moduleName}.${functionName}.service.${className}Service;



/**
 * 
 * <p>
 * Description: ${table.tableDesc?if_exists} Controllerc Layer
 * </p>
 * @author zelinyang
 * @date 2016年9月1日
 *
 */
@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8393912077646937944L;
	
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
	@Autowired
	private ${className}Service ${classNameLower}Service;

	/**
	 * 
	 *<p>
	 * Description: 根据条件查询分页查询
	 *</p>
	 * @return
	 * @auth zelinyang
	 * @date 2016年8月31日
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestBody BaseQueryParam<${className}DTO> para, HttpServletRequest request) throws ParseException {
		
		int currentPage = para.getCurrentPage();
		int pageSize = para.getPageSize();
		${className}DTO condition = para.getConditions();
		//构建查询参数
		if(condition == null){
			condition = new ${className}DTO();
		}
		Page<${className}> page = new Page<${className}>();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		Page<${className}> pageResult = ${classNameLower}Service.selectPageListByParam(condition, page);
		
		return ResultUtil.crePageSucResult(pageResult);
	}
	
	
	/**
	 * 
	 *<p>
	 * Description: add mehtod
	 *</p>
	 * @return
	 * @auth zelinyang
	 * @date 2016年8月31日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@RequestBody List<${className}> paramList, HttpServletRequest request) throws ParseException {
		List<${className}> addList = new ArrayList<${className}>();
		if(paramList.size() > 0){
			for(int i=0; i < paramList.size(); i++){
				${className} para = paramList.get(i);
				${classNameLower}Service.insert(para);
				addList.add(para);
			}
		}
		return ResultUtil.creObjSucResult(addList);
	}
	
	/**
	 * 
	 *<p>
	 * Description: 只更新传入字段值
	 *</p>
	 * @return
	 * @auth zelinyang
	 * @date 2016年8月31日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(@RequestBody ${className} para, HttpServletRequest request) throws ParseException {
		
		Long sid = para.getSid();
		if(para != null && para.getSid() == null){
			return ResultUtil.creComErrorResult("sid can't be null!");
		}
		
		${className} oldValue = ${classNameLower}Service.selectByPrimaryKey(sid);
		if(oldValue == null){
			return ResultUtil.creComErrorResult("sid:{"+sid+"} not find row!");
		}
		//
		${className} newValue = new ${className}();
		BeanUtils.copyProperties(oldValue, newValue);
		BeanUtils.copyProperties(para, newValue,getNullPropertyNames(para));
		${classNameLower}Service.updateByPrimaryKey(newValue);
		return ResultUtil.creObjSucResult(newValue);
	}
	
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
	/**
	 * 
	 *<p>
	 * Description: 根据SID 批量删除记录
	 *</p>
	 * @return
	 * @auth zelinyang
	 * @date 2016年8月31日
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody List<${className}> paramList, HttpServletRequest request) throws ParseException {
		List<Long> succeDelSidList = new ArrayList<Long>(0);
		if(paramList != null && paramList.size() > 0){
			${className}  para = null;
			for(int i = 0; i < paramList.size() ;i++){
				para = paramList.get(i);
				Long sid = para.getSid();
				if(para != null && para.getSid() != null){
					int count = ${classNameLower}Service.deleteByPrimaryKey(sid);
					if(count == 1){
						succeDelSidList.add(sid);
					}
				}
			}
			
		}
		return ResultUtil.creObjSucResult("delete sid:{"+StringUtils.join(succeDelSidList, ",")+"} , total "+succeDelSidList.size()+" rows deleted!");
	}
}
