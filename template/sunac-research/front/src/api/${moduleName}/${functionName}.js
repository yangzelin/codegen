import request from '@/router/axios';

export const getList = (current, size, params) => {
    return request({
        url: '/api/research-admin/${functionName}/list',
        method: 'get',
        params: {
            ...params,
            current,
            size,
        }
    })
}

export const getDetail = (id) => {
    return request({
        url: '/api/research-admin/${functionName}/detail',
        method: 'get',
        params: {
            id
        }
    })
}

export const remove = (ids) => {
    return request({
        url: '/api/research-admin/${functionName}/remove',
        method: 'post',
        params: {
            ids,
        }
    })
}

export const add = (row) => {
    return request({
        url: '/api/research-admin/${functionName}/submit',
        method: 'post',
        data: row
    })
}

export const update = (row) => {
    return request({
        url: '/api/research-admin/${functionName}/submit',
        method: 'post',
        data: row
    })
}

export const addBatch = (rows) => {
    return request({
        url: '/api/research-admin/${functionName}/submitBatch',
        method: 'post',
        data: rows
    })
}

export const updateBatch = (rows) => {
    return request({
        url: '/api/research-admin/${functionName}/submitBatch',
        method: 'post',
        data: rows
    })
}

