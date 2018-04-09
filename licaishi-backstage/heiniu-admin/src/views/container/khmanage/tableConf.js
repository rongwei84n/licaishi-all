/*
 * @Author: 张浩然 
 * @Date: 2018-03-24 03:58:06 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-26 08:54:58
 * 
 * 此文件为table组件的配置文件
 * 
 */

export const editInlineColumns = [{
        title: '姓名', //表格头
        align: 'center',
        key: 'name', //与传入的属性一一对应显示正确的值
        width: 90, //设置当前列的宽度
        editable: true //当前列是否可以在可编辑表格下被编辑
    }, {
        title: '所属理财师', //表格头
        align: 'center',
        key: 'financer', //与传入的属性一一对应显示正确的值
        width: 90, //设置当前列的宽度
        editable: true //当前列是否可以在可编辑表格下被编辑
    },
    {
        title: '电话',
        align: 'center',
        key: 'phone',
        editable: true
    },
    {
        title: '性别',
        align: 'center',
        key: 'sexStr',
        editable: true
    },
    {
        title: '电子邮箱',
        align: 'center',
        key: 'email',
        editable: true
    },

    {
        title: '创建日期',
        align: 'center',
        key: 'createtime',
        editable: true
    },
    {
        title: '出生日期',
        align: 'center',
        key: 'birthday',
        editable: true
    },
    {
        title: '地址',
        align: 'center',
        key: 'address',
        editable: true
    },
    {
        title: '操作',
        align: 'center',
        key: 'handle',
        width: 240,
        handle: ['edit', 'ban', 'delete'] //分别为，编辑按钮、禁用||启用按钮、删除按钮
    }
];