export const editInlineColumns = [{
        title: "订单编号", // 表格头
        align: "center",
        key: "orderNo", // 与传入的属性一一对应显示正确的值
        width: 90, // 设置当前列的宽度
    },
    {
        title: "订单金额", // 表格头
        align: "center",
        key: "amount", // 与传入的属性一一对应显示正确的值
        width: 90, // 设置当前列的宽度
    },
    {
        title: "订单日期",
        align: "center",
        key: "orderDate",
        editable: true
    },
    {
        title: "理财师",
        align: "center",
        key: "financer",
        editable: true
    },
    // {
    //     title: "电话",
    //     align: "center",
    //     key: "financerTel",
    //     editable: true
    // },
    {
        title: "客户",
        align: "center",
        key: "customer",
        editable: true
    },
    // {
    //     title: "电话",
    //     align: "center",
    //     key: "customerTel",
    //     editable: true
    // },
    {
        title: "产品简称",
        align: "center",
        key: "product",
        editable: true
    },
    // {
    //     title: "发行机构",
    //     align: "center",
    //     key: "inst",
    //     editable: true
    // },
    {
        title: "收益比率",
        align: "center",
        key: "proRatio",
        editable: true
    },
    {
        title: "佣金比率",
        align: "center",
        key: "comRatio",
        editable: true
    },
    {
        title: "佣金",
        align: "center",
        key: "commission",
        editable: true
    },
    {
        title: "操作",
        align: "center",
        key: "handle",
        width: 120,
        handle: ['detailsButton'] //分别为修改合同，修改订单状态
    }
];