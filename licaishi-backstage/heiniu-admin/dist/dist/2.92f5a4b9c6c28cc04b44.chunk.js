webpackJsonp([2],{352:function(e,t,n){n(405),n(406);var a=n(16)(n(377),n(432),null,null);a.options.__file="/Users/huangrongwei/Documents/4_phicomm_proj/heiniu-admin/src/views/container/order/unpay/unpay.vue",a.esModule&&Object.keys(a.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),a.options.functional&&console.error("[vue-loader] unpay.vue: functional components are not supported with templates, they should use render functions."),e.exports=a.exports},358:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n(220),i=function(e){return e&&e.__esModule?e:{default:e}}(a),r=function(e,t,n,a){return t("Button",{props:{type:"primary"},style:{margin:"0 5px"},on:{click:function(){e.$emit("detailsBtn-click",a)}}},"详情")},o=function(e,t,n,a){return t("Button",{style:{margin:"0 5px"},on:{click:function(){e.$emit("on-handlePay",a)}}},"完成打款")},s=function(e,t,n,a){return t("Poptip",{props:{confirm:!0,title:"您确定要将这条数据置为失败吗?",transfer:!0},on:{"on-ok":function(){e.$emit("on-failure",a)}}},[t("Button",{style:{margin:"0 5px"},props:{type:"error",placement:"top"}},"失败")])},l=function(e,t,n,a){return t("Button",{style:{margin:"0 5px"},props:{disabled:0!=n.contractStatus},on:{click:function(){e.$emit("on-handleContract",a)}}},"完成合同")},u=function(e,t,n,a){return t("Button",{style:{margin:"0 5px"},on:{click:function(){e.$emit("on-handleSettled",a)}}},"结佣")},d=function(e,t,n){return e.hoverShow?t("div",{class:{"show-edit-btn":e.hoverShow}},[t("Button",{props:{type:"text",icon:"edit"},on:{click:function(t){e.edittingStore[n.index].edittingCell[n.column.key]=!0,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore))}}})]):t("Button",{props:{type:"text",icon:"edit"},on:{click:function(t){e.edittingStore[n.index].edittingCell[n.column.key]=!0,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore))}}})},c=function(e,t,n){return t("Button",{props:{type:"text",icon:"checkmark"},on:{click:function(t){e.edittingStore[n.index].edittingCell[n.column.key]=!1,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore)),e.$emit("input",e.handleBackdata(e.thisTableData)),e.$emit("on-cell-change",e.handleBackdata(e.thisTableData),n.index,n.column.key)}}})},h=function(e,t,n,a){return t("Input",{props:{type:"text",value:e.edittingStore[n.index][a.key]},on:{"on-change":function(t){var i=a.key;e.edittingStore[n.index][i]=t.target.value}}})};t.default={props:{refs:String,columnsList:Array,value:Array,url:String,editIncell:{type:Boolean,default:!1},hoverShow:{type:Boolean,default:!1}},data:function(){return{columns:[],thisTableData:[],edittingStore:[]}},created:function(){this.init()},methods:{init:function(){var e=this,t=this,n=this.columnsList.filter(function(e){if(e.editable&&!0===e.editable)return e}),a=JSON.parse((0,i.default)(this.value)),p=[];p=a.map(function(t,a){var i=!1;if(e.thisTableData[a])if(e.thisTableData[a].editting)i=!0;else for(var r in e.thisTableData[a].edittingCell)!0===e.thisTableData[a].edittingCell[r]&&(i=!0);if(i)return e.thisTableData[a];e.$set(t,"editting",!1);var o={};return n.forEach(function(e){o[e.key]=!1}),e.$set(t,"edittingCell",o),t}),this.thisTableData=p,this.edittingStore=JSON.parse((0,i.default)(this.thisTableData)),this.columnsList.forEach(function(n){n.editable&&(n.render=function(a,i){var r=e.thisTableData[i.index];return r.editting?a("Input",{props:{type:"text",value:r[n.key]},on:{"on-change":function(e){var n=i.column.key;t.edittingStore[i.index][n]=e.target.value}}}):e.editIncell?a("Row",{props:{type:"flex",align:"middle",justify:"center"}},[a("Col",{props:{span:"22"}},[r.edittingCell[i.column.key]?h(e,a,i,n):a("span",r[n.key])]),a("Col",{props:{span:"2"}},[r.edittingCell[i.column.key]?c(e,a,i):d(e,a,i)])]):a("span",r[n.key])}),n.handle&&(n.render=function(t,a){var i=e.thisTableData[a.index],d=[];return n.handle.forEach(function(n){"handleContract"===n?d.push(l(e,t,i,a.index)):"handleSettled"===n?d.push(u(e,t,0,a.index)):"detailsButton"===n?d.push(r(e,t,0,a.index)):"failureButton"===n?d.push(s(e,t,0,a.index)):"handlePayButton"===n&&d.push(o(e,t,0,a.index))}),t("div",d)})})},handleBackdata:function(e){var t=JSON.parse((0,i.default)(e));return t.forEach(function(e){delete e.editting,delete e.edittingCell,delete e.saving}),t}},watch:{value:function(e){this.init()}}}},359:function(e,t){},360:function(e,t,n){n(359);var a=n(16)(n(358),n(361),null,null);a.options.__file="/Users/huangrongwei/Documents/4_phicomm_proj/heiniu-admin/src/views/container/order/table/EditTable.vue",a.esModule&&Object.keys(a.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),a.options.functional&&console.error("[vue-loader] EditTable.vue: functional components are not supported with templates, they should use render functions."),e.exports=a.exports},361:function(e,t,n){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("Table",{ref:e.refs,attrs:{columns:e.columnsList,data:e.thisTableData,border:"","disabled-hover":""}})],1)},staticRenderFns:[]},e.exports.render._withStripped=!0},377:function(e,t,n){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var i=n(220),r=a(i),o=n(0),s=(a(o),n(360)),l=a(s),u=n(388);t.default={components:{EditTable:l.default},data:function(){return{startDate:"",endDate:"",orderList:[],listLoading:!1,total:0,pageSize:20,pageNumber:1}},created:function(){this.editInlineColumns=u.editInlineColumns,this.handleSearch()},filters:{formatContractStatus:function(e,t){return 1==e.contractStatus?"是":"否"}},methods:{contractOrder:function(e){var t=this,n=this.orderList[e];this.$ajax({url:"/order/ordercontract",params:{uid:n.uid},headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){200==e.data.status?(t.$Message.success("修改成功"),t.handleSearch()):t.$Message.error("修改失败")})},payOrder:function(e){var t=this,n=this.orderList[e];return"1"!=n.voucherStatus?void this.$Message.error("该订单还未上传凭证"):"1"!=n.contractStatus?void this.$Message.error("该订单合同还未完成"):void this.$ajax({url:"/order/ordersettle",params:{uid:n.uid},headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){200==e.data.status?(t.$Message.success("修改成功"),t.handleSearch()):t.$Message.error("修改失败")})},failureOrder:function(e){var t=this,n=this.orderList[e];this.$ajax({url:"/order/orderfailure",params:{uid:n.uid},headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){200==e.data.status?(t.$Message.success("修改成功"),t.handleSearch()):t.$Message.error("修改失败")})},formatVoucherStatus:function(e,t){return 1==e.voucherStatus?"是":"否"},formatContractStatus:function(e,t){return 1==e.contractStatus?"是":"否"},handleSizeChange:function(e){this.pageSize=e,this.handleSearch()},handleCurrentChange:function(e){this.pageNumber=e,this.handleSearch()},handleSearch:function(){var e=this;this.listLoading=!0;var t=void 0;t=this.startDate&&this.endDate?"/order/orderlist?status=01&startDate="+this.$moment(this.startDate).format("YYYY-MM-DD")+"&endDate="+this.$moment(this.endDate).format("YYYY-MM-DD")+"&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize:this.startDate?"/order/orderlist?status=01&startDate="+this.$moment(this.startDate).format("YYYY-MM-DD")+"&endDate=&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize:this.endDate?"/order/orderlist?status=01&startDate=&endDate="+this.$moment(this.endDate).format("YYYY-MM-DD")+"&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize:"/order/orderlist?status=01&startDate=&endDate=&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize,this.$ajax({url:t,method:"get"}).then(function(t){e.listLoading=!1,200==t.data.status&&(e.total=t.data.result.total,e.orderList=t.data.result.dataList)})},detailsBtn:function(e){var t=this.orderList[e];this.$router.push({name:"orderDetail",query:{orderDetail:(0,r.default)(t)}})}}}},388:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});t.editInlineColumns=[{title:"订单编号",align:"center",key:"orderNo",width:90},{title:"订单金额",align:"center",key:"amount",width:90},{title:"订单日期",align:"center",key:"orderDate",editable:!0},{title:"理财师",align:"center",key:"financer",editable:!0},{title:"电话",align:"center",key:"financerTel",editable:!0},{title:"客户",align:"center",key:"customer",editable:!0},{title:"电话",align:"center",key:"customerTel",editable:!0},{title:"产品",align:"center",key:"product",editable:!0},{title:"发行机构",align:"center",key:"inst",editable:!0},{title:"佣金比率",align:"center",key:"comRatio",editable:!0},{title:"收益",align:"center",key:"profit",editable:!0},{title:"操作",align:"center",key:"handle",width:350,handle:["detailsButton","handleContract","handlePayButton","failureButton"]}]},405:function(e,t){},406:function(e,t){},432:function(e,t,n){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"unpay"},[[n("Breadcrumb",[n("BreadcrumbItem",{attrs:{to:"/home"}},[e._v("首页")]),e._v(" "),n("BreadcrumbItem",{attrs:{to:"/main/unpay"}},[e._v("待打款")])],1)],e._v(" "),n("div",{staticClass:"unpay-body"},[n("div",{staticClass:"unpay-body-header"},[n("DatePicker",{attrs:{type:"date","value-format":"yyyy-MM-dd",placeholder:"开始日期",transfer:""},model:{value:e.startDate,callback:function(t){e.startDate=t},expression:"startDate"}}),e._v(" "),n("DatePicker",{attrs:{type:"date","value-format":"yyyy-MM-dd",placeholder:"结束日期",transfer:""},model:{value:e.endDate,callback:function(t){e.endDate=t},expression:"endDate"}}),e._v(" "),n("div",[n("Button",{attrs:{type:"primary",icon:"ios-search"},on:{click:e.handleSearch}},[e._v("检索")])],1)],1),e._v(" "),n("div",{staticClass:"unpay-body-body"},[n("edit-table",{attrs:{refs:"table2","columns-list":e.editInlineColumns},on:{"detailsBtn-click":e.detailsBtn,"on-handleContract":e.contractOrder,"on-handlePay":e.payOrder,"on-failure":e.failureOrder},model:{value:e.orderList,callback:function(t){e.orderList=t},expression:"orderList"}})],1)])],2)},staticRenderFns:[]},e.exports.render._withStripped=!0}});