webpackJsonp([6],{346:function(e,t,a){a(417),a(395);var n=a(16)(a(369),a(443),null,null);n.options.__file="/Users/huangrongwei/Documents/4_phicomm_proj/heiniu-admin/src/views/container/khmanage/khmanage.vue",n.esModule&&Object.keys(n.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),n.options.functional&&console.error("[vue-loader] khmanage.vue: functional components are not supported with templates, they should use render functions."),e.exports=n.exports},369:function(e,t,a){"use strict";function n(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var i=a(52),l=n(i),s=a(0),o=(n(s),a(422)),r=n(o),d=a(384);t.default={components:{EditTable:r.default},data:function(){return{nameSearch:"",dataList:[],editInlineColumns:[],total:0,pageSize:20,pageNumber:1,ModalStaus:[!0,!1],titleModal:"",lcsModalVisible:!1,loadinglcs:!1,optionsLcs:[],lcsModalValidate:{name:"",phone:"",sex:-1,age:0,birthday:"",address:"",email:"",financer:"",financerId:0,uid:0},ruleValidate:{name:[{required:!0,message:"请输入姓名",trigger:"blur"}],phone:[{required:!0,message:"请输入正确的电话号码",trigger:"change"}],email:[{message:"请输入电子邮箱",trigger:"blur"}],sex:[{message:"请选择性别",trigger:"change"}],age:[{type:"number",message:"请输入年龄",trigger:"change"}],birthday:[{type:"string",message:"请选择您的生日",trigger:"change"}],address:[{type:"string",message:"请输入您的地址",trigger:"blur"}]}}},created:function(){this.editInlineColumns=d.editInlineColumns,this.handleSearch(),this.handleLscList()},methods:{handleLscList:function(){var e=this;this.$ajax({url:"/financer/queryfinaasync",method:"get"}).then(function(t){200==t.data.status&&(e.optionsLcs=t.data.result)})},handleSearch:function(){var e=this;this.$ajax({url:"/customer/customerlist?nameSearch="+this.nameSearch+"&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize,method:"get"}).then(function(t){200==t.data.status&&(e.total=t.data.result.total,e.pageSize=t.data.result.pageSize,e.pageNumber=t.data.result.pageNumber,e.dataList=t.data.result.dataList,t.data.result.dataList.map(function(e){e.sex=""+e.sex,e.sexStr="1"===e.sex?"男":"女",e.statusStr=0===e.status?"启用":"禁用"}))})},handleBan:function(e){var t=this,a=this.dataList[e];this.$Modal.confirm({title:"提示",content:0===a.status?"您确定要禁用该用户吗？":"您确定要启用该用户吗？",onOk:function(){t.$ajax({url:"/customer/handleswitch",params:{uid:a.uid},headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){e.data.status&&0===a.status?t.$Message.info("禁用成功"):t.$Message.info("启用成功"),t.handleSearch()})}})},handleDelete:function(e){var t=this;this.$ajax({url:"/customer/delcustomer",params:{uid:this.dataList[e].uid},headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){t.handleSearch()})},handleChange:function(e){this.ModalStaus=(0,l.default)({length:2},function(){return!1}),this.ModalStaus[1]=!0;var t=this.dataList[e];this.titleModal="编辑客户",this.lcsModalVisible=!0,this.lcsModalValidate={name:t.name,phone:t.phone,sex:t.sex,birthday:t.birthday,address:t.address,email:t.email,financer:t.financer,financerId:t.financerId,uid:t.uid},console.log(this.lcsModalValidate)},handleAdd:function(){this.ModalStaus=(0,l.default)({length:2},function(){return!1}),this.ModalStaus[0]=!0,this.titleModal="新增客户",this.lcsModalVisible=!0,this.lcsModalValidate={name:"",phone:"",sex:"",birthday:"",address:"",email:"",financerId:""}},handleSubmit:function(e){var t=this;this.lcsModalValidate.birthday=this.$moment(this.lcsModalValidate.birthday).format("YYYY-MM-DD"),this.$refs[e].validate(function(e){e?t.ModalStaus[0]?t.addKH():t.ModalStaus[1]&&t.editKH():t.$Message.error("参数有误")})},editKH:function(){var e=this;this.$ajax({url:"/customer/editcustomer",params:this.lcsModalValidate,headers:{"Content-Type":" application/x-www-form-urlencoded"}}).then(function(t){200===t.data.status&&(e.$Message.success("编辑成功!"),e.handleSearch())})},addKH:function(){var e=this;this.$ajax({url:"/customer/addcustomer",params:this.lcsModalValidate,headers:{"Content-Type":" application/x-www-form-urlencoded"}}).then(function(t){200===t.data.status&&(e.$Message.success("新增成功!"),e.handleSearch())})}}}},370:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(220),i=function(e){return e&&e.__esModule?e:{default:e}}(n),l=function(e,t,a,n){return t("Button",{props:{type:"primary",loading:a.saving},style:{margin:"0 5px"},on:{click:function(){e.$emit("on-change",n)}}},"编辑")},s=function(e,t,a,n){return t("Button",{props:{type:0===a.status?"success":"primary"},style:{margin:"0 5px"},on:{click:function(){e.$emit("banBtn-click",n)}}},0===a.status?"禁用":"启用")},o=function(e,t,a,n){return t("Poptip",{props:{confirm:!0,title:"您确定要删除这条数据吗?",transfer:!0},on:{"on-ok":function(){e.$emit("on-delete",n)}}},[t("Button",{style:{margin:"0 5px"},props:{type:"error",placement:"top"}},"删除")])},r=function(e,t,a){return e.hoverShow?t("div",{class:{"show-edit-btn":e.hoverShow}},[t("Button",{props:{type:"text",icon:"edit"},on:{click:function(t){e.edittingStore[a.index].edittingCell[a.column.key]=!0,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore))}}})]):t("Button",{props:{type:"text",icon:"edit"},on:{click:function(t){e.edittingStore[a.index].edittingCell[a.column.key]=!0,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore))}}})},d=function(e,t,a){return t("Button",{props:{type:"text",icon:"checkmark"},on:{click:function(t){e.edittingStore[a.index].edittingCell[a.column.key]=!1,e.thisTableData=JSON.parse((0,i.default)(e.edittingStore)),e.$emit("input",e.handleBackdata(e.thisTableData)),e.$emit("on-cell-change",e.handleBackdata(e.thisTableData),a.index,a.column.key)}}})},c=function(e,t,a,n){return t("Input",{props:{type:"text",value:e.edittingStore[a.index][n.key]},on:{"on-change":function(t){var i=n.key;e.edittingStore[a.index][i]=t.target.value}}})};t.default={props:{refs:String,columnsList:Array,value:Array,url:String,editIncell:{type:Boolean,default:!1},hoverShow:{type:Boolean,default:!1}},data:function(){return{columns:[],thisTableData:[],edittingStore:[]}},created:function(){this.init()},methods:{init:function(){var e=this,t=this,a=this.columnsList.filter(function(e){if(e.editable&&!0===e.editable)return e}),n=JSON.parse((0,i.default)(this.value)),u=[];u=n.map(function(t,n){var i=!1;if(e.thisTableData[n])if(e.thisTableData[n].editting)i=!0;else for(var l in e.thisTableData[n].edittingCell)!0===e.thisTableData[n].edittingCell[l]&&(i=!0);if(i)return e.thisTableData[n];e.$set(t,"editting",!1);var s={};return a.forEach(function(e){s[e.key]=!1}),e.$set(t,"edittingCell",s),t}),this.thisTableData=u,this.edittingStore=JSON.parse((0,i.default)(this.thisTableData)),this.columnsList.forEach(function(a){a.editable&&(a.render=function(n,i){var l=e.thisTableData[i.index];return l.editting?n("Input",{props:{type:"text",value:l[a.key]},on:{"on-change":function(e){var a=i.column.key;t.edittingStore[i.index][a]=e.target.value}}}):e.editIncell?n("Row",{props:{type:"flex",align:"middle",justify:"center"}},[n("Col",{props:{span:"22"}},[l.edittingCell[i.column.key]?c(e,n,i,a):n("span",l[a.key])]),n("Col",{props:{span:"2"}},[l.edittingCell[i.column.key]?d(e,n,i):r(e,n,i)])]):n("span",l[a.key])}),a.handle&&(a.render=function(t,n){var i=e.thisTableData[n.index],r=[];return a.handle.forEach(function(a){"edit"===a?r.push(l(e,t,i,n.index)):"ban"===a?r.push(s(e,t,i,n.index)):"delete"===a&&r.push(o(e,t,0,n.index))}),t("div",r)})})},handleBackdata:function(e){var t=JSON.parse((0,i.default)(e));return t.forEach(function(e){delete e.editting,delete e.edittingCell,delete e.saving}),t}},watch:{value:function(e){this.init()}}}},384:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});t.editInlineColumns=[{title:"姓名",align:"center",key:"name",width:90,editable:!0},{title:"所属理财师",align:"center",key:"financer",width:90,editable:!0},{title:"电话",align:"center",key:"phone",editable:!0},{title:"性别",align:"center",key:"sexStr",editable:!0},{title:"电子邮箱",align:"center",key:"email",editable:!0},{title:"创建日期",align:"center",key:"createtime",editable:!0},{title:"出生日期",align:"center",key:"birthday",editable:!0},{title:"地址",align:"center",key:"address",editable:!0},{title:"操作",align:"center",key:"handle",width:240,handle:["edit","ban","delete"]}]},395:function(e,t){},400:function(e,t){},417:function(e,t){},422:function(e,t,a){a(400);var n=a(16)(a(370),a(429),null,null);n.options.__file="/Users/huangrongwei/Documents/4_phicomm_proj/heiniu-admin/src/views/container/khmanage/table/EditTable.vue",n.esModule&&Object.keys(n.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),n.options.functional&&console.error("[vue-loader] EditTable.vue: functional components are not supported with templates, they should use render functions."),e.exports=n.exports},429:function(e,t,a){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("Table",{ref:e.refs,attrs:{columns:e.columnsList,data:e.thisTableData,border:"","disabled-hover":""}})],1)},staticRenderFns:[]},e.exports.render._withStripped=!0},443:function(e,t,a){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"khmanage"},[[a("Breadcrumb",[a("BreadcrumbItem",{attrs:{to:"/home"}},[e._v("首页")]),e._v(" "),a("BreadcrumbItem",{attrs:{to:"/main/khmanage"}},[e._v("客户管理")])],1)],e._v(" "),a("div",{staticClass:"khmanage-body"},[a("div",{staticClass:"khmanage-body-header"},[a("div",[a("Input",{staticStyle:{width:"300px"},attrs:{placeholder:"请输入搜索客户姓名"},model:{value:e.nameSearch,callback:function(t){e.nameSearch=t},expression:"nameSearch"}}),e._v(" "),a("Button",{attrs:{type:"primary",icon:"ios-search"},on:{click:e.handleSearch}},[e._v("检索")])],1),e._v(" "),a("div",[a("Button",{attrs:{type:"primary",icon:"plus"},on:{click:e.handleAdd}},[e._v("新增")])],1)]),e._v(" "),a("div",{staticClass:"khmanage-body-body"},[a("edit-table",{attrs:{refs:"table2","columns-list":e.editInlineColumns},on:{"on-delete":e.handleDelete,"on-change":e.handleChange,"banBtn-click":e.handleBan},model:{value:e.dataList,callback:function(t){e.dataList=t},expression:"dataList"}})],1)]),e._v(" "),a("Modal",{attrs:{title:e.titleModal},on:{"on-ok":function(t){e.handleSubmit("lcsModalValidate")}},model:{value:e.lcsModalVisible,callback:function(t){e.lcsModalVisible=t},expression:"lcsModalVisible"}},[a("Form",{ref:"lcsModalValidate",attrs:{model:e.lcsModalValidate,rule:e.ruleValidate,"label-width":60}},[a("FormItem",{attrs:{label:"姓名",prop:"name"}},[a("Input",{attrs:{placeholder:"请输入姓名"},model:{value:e.lcsModalValidate.name,callback:function(t){e.$set(e.lcsModalValidate,"name",t)},expression:"lcsModalValidate.name"}})],1),e._v(" "),a("FormItem",{attrs:{label:"电话",prop:"phone"}},[a("Input",{attrs:{maxlength:11,placeholder:"请输入电话号码"},model:{value:e.lcsModalValidate.phone,callback:function(t){e.$set(e.lcsModalValidate,"phone",t)},expression:"lcsModalValidate.phone"}})],1),e._v(" "),a("FormItem",{attrs:{label:"性别",prop:"sex"}},[a("Select",{attrs:{placeholder:"请选择性别",transfer:""},model:{value:e.lcsModalValidate.sex,callback:function(t){e.$set(e.lcsModalValidate,"sex",t)},expression:"lcsModalValidate.sex"}},[a("Option",{attrs:{value:"1"}},[e._v("男")]),e._v(" "),a("Option",{attrs:{value:"0"}},[e._v("女")])],1)],1),e._v(" "),a("FormItem",{attrs:{label:"生日",prop:"birthday"}},[a("DatePicker",{attrs:{type:"date",placeholder:"请选择生日",transfer:""},model:{value:e.lcsModalValidate.birthday,callback:function(t){e.$set(e.lcsModalValidate,"birthday",t)},expression:"lcsModalValidate.birthday"}})],1),e._v(" "),a("FormItem",{attrs:{label:"电子邮箱",prop:"email"}},[a("Input",{attrs:{placeholder:"请输入电子邮箱"},model:{value:e.lcsModalValidate.email,callback:function(t){e.$set(e.lcsModalValidate,"email",t)},expression:"lcsModalValidate.email"}})],1),e._v(" "),a("FormItem",{attrs:{label:"理财师"}},[a("Select",{attrs:{filterable:"",placeholder:"请输入理财师",transfer:"",placement:"top"},model:{value:e.lcsModalValidate.financerId,callback:function(t){e.$set(e.lcsModalValidate,"financerId",t)},expression:"lcsModalValidate.financerId"}},e._l(e.optionsLcs,function(t,n){return a("Option",{key:n,attrs:{value:t.uid}},[e._v(e._s(t.value))])}))],1),e._v(" "),a("FormItem",{attrs:{label:"地址",prop:"address"}},[a("Input",{attrs:{type:"textarea",autosize:{minRows:2,maxRows:5},placeholder:"请输入现在居住地址"},model:{value:e.lcsModalValidate.address,callback:function(t){e.$set(e.lcsModalValidate,"address",t)},expression:"lcsModalValidate.address"}})],1)],1)],1)],2)},staticRenderFns:[]},e.exports.render._withStripped=!0}});