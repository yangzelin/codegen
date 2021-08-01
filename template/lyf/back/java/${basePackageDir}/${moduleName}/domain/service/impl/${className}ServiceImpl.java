<#assign className="${table.className}">
<#assign classNameLower=className?uncap_first>
package ${basePackage}.${moduleName}.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rome.arch.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rome.arch.core.clientobject.Response;
import ${basePackage}.${moduleName}.api.dto.${className}ReqDTO;
import ${basePackage}.${moduleName}.api.dto.${className}DTO;

import com.venus.core.model.BatchResult;
import com.venus.bill.common.utils.PageUtils;
import com.venus.bill.common.utils.Util;
import com.venus.bill.common.constant.BillDict;

import com.venus.bill.common.mapper.CommonMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ${basePackage}.${moduleName}.domain.convertor.${className}Convertor;
import ${basePackage}.${moduleName}.domain.service.${className}Service;
import ${basePackage}.${moduleName}.infrastructure.dataobject.${className}DO;
import ${basePackage}.${moduleName}.infrastructure.mapper.${className}Mapper;


/**
 * <p>
 * Description: ${table.tableDesc?if_exists}  服务实现类
 * </p>
 *
 * @author yangzelin
 * @date 2020/3/11
 **/
@Slf4j
@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Resource
    private ${className}Mapper ${classNameLower}Mapper;

    @Resource
    private ${className}Convertor ${classNameLower}Convertor;

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Response intfSync${className}(${className}ReqDTO reqDTO) {
        long begin = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        String batch = StringUtils.defaultIfEmpty(reqDTO.getTransId(), new IdWorker().getId() + "");
        log.info("Step 1.构建${table.tableDesc?if_exists}DO对象,批次：{} Size:{}", batch, reqDTO.getData().size());
        List<${className}DTO> dataList = reqDTO.getData();
        log.info("Step 2. 插入前校验${table.tableDesc?if_exists}已经存在的,批次：{}", batch);

        List<String> busiKeyList = dataList.stream().map(${className}DTO::getBusiKey).distinct().collect(Collectors.toList());
        List<String> dbBusiKeyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(busiKeyList)) {
            begin = System.currentTimeMillis();
            // TODO　注意替换表名
            dbBusiKeyList = PageUtils.execPage(busiKeyList, ((subList, pageNo, pageSize, total) -> commonMapper.queryDBBusinessKey(subList, BillDict.BusiKeyTableName.bc_sup_outsource_order.getCode())));
            end = System.currentTimeMillis();
            log.info("Step 3. 查询${table.tableDesc?if_exists}已经存在的数据, 批次:{}，数据：{} ,耗时: {} ms", batch, dbBusiKeyList, (end - begin));
        }

        log.info("Step 4. 过滤数据库已经存在${table.tableDesc?if_exists}数据，批次:{}, 请求busiKey:[{}], 数据库存在 busiKey:[{}]", batch, busiKeyList, dbBusiKeyList);
        if (!CollectionUtils.isEmpty(busiKeyList) && !CollectionUtils.isEmpty(dbBusiKeyList)) {
            List<String> finalDbBusiKeyList = dbBusiKeyList;
            dataList = dataList.stream().filter(t -> !org.springframework.util.CollectionUtils.contains(finalDbBusiKeyList.iterator(), t.getBusiKey())).collect(Collectors.toList());
        }


        List<${className}DO> doDataList = ${classNameLower}Convertor.dtoListToDoList(dataList);
        if (!CollectionUtils.isEmpty(doDataList)) {
            Date now = Util.getNow();
            for (${className}DO doItem : doDataList) {
                // 设置公共参数
                if (doItem != null) {
                    doItem.setBatch(batch);
                    doItem.setCreateTime(now);
                    doItem.setUpdateTime(now);
                    doItem.setIsDeleted(BillDict.DeleteEnume.No.getCode());
                }
            }
        }

        if (!CollectionUtils.isEmpty(doDataList)) {
            begin = System.currentTimeMillis();
            // 开始插入主表
            PageUtils.execPage(doDataList, ((subList, pageNo, pageSize, total) -> ${classNameLower}Mapper.insertBatch(subList)));
            end = System.currentTimeMillis();
            log.info("Step 5.批量插入${table.tableDesc?if_exists}对象, 批次:{}，大小:{} 耗时：{} ms", batch, doDataList.size(), (end - begin));
        }
        if (!CollectionUtils.isEmpty(dbBusiKeyList)) {
            BatchResult result = BatchResult.existRecordId(dbBusiKeyList);
            return Response.builderSuccess(result);
        } else {
            return Response.builderSuccess("ok");
        }
    }


    @Override
    public ${className}DTO getById(Long id){
        ${className}DO ${classNameLower}Do=${classNameLower}Mapper.getById(id);
        return ${classNameLower}Convertor.doToDto(${classNameLower}Do);
    }

    @Override
    public List<${className}DTO> getListByMap(Map<String, Object> param){
        List<${className}DO> doList=${classNameLower}Mapper.getByMap(param);
        return ${classNameLower}Convertor.doListToDtoList(doList);
    }

    @Override
    public Integer getCountByMap(Map<String, Object> param){
        return ${classNameLower}Mapper.getCountByMap(param);
    }

    @Override
    public Integer save(${className}DTO ${classNameLower}){
        return ${classNameLower}Mapper.insert(${classNameLower}Convertor.dtoToDo(${classNameLower}));
    }

    @Override
    public Integer update(${className}DTO ${classNameLower}){
        return ${classNameLower}Mapper.update(${classNameLower}Convertor.dtoToDo(${classNameLower}));
    }

    @Override
    public Integer saveOrUpdate(CardBillDTO dto) {
        int count = 0;
        if(dto != null){
            // ID 不为空或者-1 认为是更新，其他为保存
            if(dto.getId() != null && dto.getId() != -1){
                count = this.save(dto);
            }else{
                count = this.update(dto);
            }
        }
        return count;
    }

    @Override
    public Integer deleteById(Long id){
        return ${classNameLower}Mapper.deleteById(id);
    }

    @Override
    public PageInfo<${className}DTO> queryPageByMap(Map<String, Object> params){
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(MapUtils.getInteger(params,"page"),MapUtils.getInteger(params,"limit"),true);
        List<${className}DO> ${classNameLower}DoList=${classNameLower}Mapper.getByMap(params);
        PageInfo<${className}DO> pageDoList=new PageInfo<>(${classNameLower}DoList);
        return ${classNameLower}Convertor.doToDto(pageDoList);
    }

    @Override
    public Integer saveBatch(List<${className}DTO> ${classNameLower}List){
        List<${className}DO> ${classNameLower}DoList=${classNameLower}Convertor.dtoListToDoList(${classNameLower}List);
        return ${classNameLower}Mapper.insertBatch(${classNameLower}DoList);
    }

    @Override
    public Integer updateBatch(List<${className}DTO> ${classNameLower}List){
        List<${className}DO> ${classNameLower}DoList=${classNameLower}Convertor.dtoListToDoList(${classNameLower}List);
        return ${classNameLower}Mapper.updateBatch(${classNameLower}DoList);
    }

    @Override
    public boolean saveOrUpdateBatch(List<${className}DTO> ${classNameLower}List) {
        boolean succ = true;
        // 盘点更新或者插入
        if(CollectionUtils.isNotEmpty(${classNameLower}List)){
            List<${className}DTO> insertList = new ArrayList<>(16);
            List<${className}DTO> updateList = new ArrayList<>(16);
            for (${className}DTO dto : ${classNameLower}List) {
                if(dto != null){
                    // ID 不为空或者-1 认为是更新，其他为保存
                    if(dto.getId() != null && dto.getId() != -1){
                        insertList.add(dto);
                    }else{
                        updateList.add(dto);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(insertList)){
                this.saveBatch(insertList);
            }

            if(CollectionUtils.isNotEmpty(updateList)){
                this.updateBatch(updateList);
            }
        }

        return succ;
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        if(CollectionUtils.isNotEmpty(ids)){
            Integer count = ${classNameLower}Mapper.removeByIds(ids);
            return count;
        }
        return 0;
    }
}
