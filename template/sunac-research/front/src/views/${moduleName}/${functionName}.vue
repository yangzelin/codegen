<template>
  <basic-container>
    <h3>表格例子</h3>
    <avue-crud :option="option"
               :page="page"
               :data="data"></avue-crud>
  </basic-container>
</template>

<script>
  export default {
    data() {
      return {
        page: {
          total: 122
        },
        data: [
        ],
        option: {
          column: [
          <#list table.columns as column>
          <#if column.pk == true>
            {
              label: "${column.columnComment?if_exists}",
              prop: "${column.propertyName}",
              hidden: true,
              span: 14,
              row: true,
            },
          <#else>
            {
              label: "${column.columnComment?if_exists}",
              prop: "${column.propertyName}",
              <#if column.columnClass == 'java.util.Date'>
              type: "date",
              </#if>
              <#if column.columnType?index_of('ClOB') gt 0 >
              type: "textarea",
              minRows: 8,
              overHidden: true,
              </#if>
              span: 14,
              row: true,
            },
         </#if>
         </#list>
          ]
        }
      };
    }
  };
</script>

<style>
</style>
