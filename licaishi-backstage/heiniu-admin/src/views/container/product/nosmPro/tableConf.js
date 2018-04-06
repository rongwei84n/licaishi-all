/*
 * @Author: 张浩然 
 * @Date: 2018-03-24 03:58:06 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-25 22:20:52
 * 
 * 此文件为table组件的配置文件
 * 
 */

import {
    pTypeList,
    pSaleStatusList
} from '../enumeration';
import Util from '@/libs/util';

export const editInlineColumns = [
    // {
    //     title: 'uid',
    //     type: 'center',
    //     key: 'uid',
    //     align: 'center'
    // },
    // {
    //     title: '产品编号', //表格头
    //     align: 'center',
    //     key: 'pCode', //与传入的属性一一对应显示正确的值
    //     width: 90, //设置当前列的宽度
    //     editable: true //当前列是否可以在可编辑表格下被编辑
    // },
    {
        title: '产品简称',
        align: 'center',
        key: 'pShortName',
        editable: true
    },
    {
        title: '产品全称',
        align: 'center',
        key: 'pFullName',
    },
    {
        title: '产品类型',
        align: 'center',
        key: 'pType',
        render: (h, params) => {
            return h('span', {}, pTypeList[(parseInt(params.row.pType))].value);
        }
    },
    {
        title: '预期年化收益(%)',
        align: 'center',
        key: 'pExpectAnnualRevenue'
    },
    {
        title: '募集规模(元)',
        align: 'center',
        key: 'pAllIssuingScale',
        render: (h, params) => {
            return h('span', {}, Util.toThousands(params.row.pAllIssuingScale));
        }
    },
    {
        title: '投资期限(月)',
        align: 'center',
        key: 'pDulTime',
    },
    {
        title: '产品状态',
        align: 'center',
        key: 'pSaleStatus',
        render: (h, params) => {
            return h('span', {}, pSaleStatusList[(parseInt(params.row.pSaleStatus))].value);
        }
    },
    {
        title: '成立日期',
        align: 'center',
        key: 'pSaleStartTime',
        editable: true
    },
    {
        title: '操作',
        align: 'center',
        key: 'handle',
        width: 260,
        handle: ['details', 'edit', 'delete']
    }
];