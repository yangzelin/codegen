import Layout from '@/page/index/'
export default [{
    path: '/${functionName}',
    component: Layout,
    redirect: '/${functionName}/index',
    children: [{
        path: 'index',
        name: '${table.tableDesc?if_exists}',
        meta: {
            i18n: 'dashboard',
            keepAlive: false,
            isTab: true,
            isAuth: false
        },
        component: () =>
            import( /* webpackChunkName: "views" */ '@/views/${moduleName}/${functionName}')
    }]
  }
}