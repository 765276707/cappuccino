(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-9c2ee63e"],{"160b":function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-container",[a("el-header",{staticStyle:{padding:"10px"},attrs:{height:"80px"}},[a("div",{staticStyle:{padding:"5px",background:"#F2F6FC","border-radius":"5px"}},[a("div",{staticClass:"topbar"},[a("div",{staticClass:"left-panel"},[a("el-button",{attrs:{icon:"el-icon-refresh"},on:{click:e.refresh}},[e._v("刷新")])],1),e._v(" "),a("div",{staticClass:"right-panel"},[a("div",{staticClass:"right-panel-search"},[a("el-select",{staticStyle:{width:"100px"},attrs:{placeholder:"操作类型",clearable:""},model:{value:e.searchForm.opType,callback:function(t){e.$set(e.searchForm,"opType",t)},expression:"searchForm.opType"}},[a("el-option",{attrs:{label:"新增",value:!1}}),e._v(" "),a("el-option",{attrs:{label:"更新",value:!0}})],1),e._v(" "),a("el-date-picker",{attrs:{type:"daterange","start-placeholder":"开始日期","end-placeholder":"结束日期",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss","default-time":["00:00:00","23:59:59"],clearable:""},on:{change:e.setTime},model:{value:e.opTimeRanges,callback:function(t){e.opTimeRanges=t},expression:"opTimeRanges"}}),e._v(" "),a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"操作描述/操作者"},model:{value:e.searchForm.searchText,callback:function(t){e.$set(e.searchForm,"searchText",t)},expression:"searchForm.searchText"}}),e._v(" "),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.getPage()}}})],1)])])])]),e._v(" "),a("el-main",{staticStyle:{"padding-left":"10px","padding-right":"10px"}},[a("el-card",{attrs:{shadow:"always","body-style":"padding: 0px; min-height: 500px;"}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"Table",staticStyle:{width:"100%","min-height":"450px"},attrs:{data:e.pageResult.list,"element-loading-text":"加载数据中","header-row-style":{height:"45px"},"row-style":{height:"60px"},"header-cell-style":{background:"#F2F6FC"}},scopedSlots:e._u([{key:"empty",fn:function(){return[a("el-empty",{attrs:{description:"查无数据","image-size":100}})]},proxy:!0}])},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-descriptions",{staticStyle:{padding:"10px",color:"#2b4b6b"},attrs:{title:"参数详情",column:1}},[a("el-descriptions-item",{attrs:{label:"请求方法"}},[e._v(e._s(t.row.opMethod))]),e._v(" "),a("el-descriptions-item",{attrs:{label:"请求参数"}},[e._v(e._s(t.row.opArgs))])],1)]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"id",label:"编号",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"reqUri",label:"请求路径"}}),e._v(" "),a("el-table-column",{attrs:{prop:"opMethod",label:"请求方法"}}),e._v(" "),a("el-table-column",{attrs:{prop:"opDesc",label:"操作描述"}}),e._v(" "),a("el-table-column",{attrs:{prop:"opUser",label:"操作者"}}),e._v(" "),a("el-table-column",{attrs:{prop:"opTime",label:"操作时间"}})],1),e._v(" "),a("pagination",{attrs:{total:e.pageResult.total},on:{"page-change":e.pageChange}})],1)],1)],1)},r=[],s=(a("96cf"),a("1da1")),n={data:function(){return{opTimeRanges:null,searchForm:{opType:null,searchText:"",bgnOpTime:null,endOpTime:null},pageResult:{total:0,pageNum:1,pageSize:10,list:[]},loading:!1}},mounted:function(){this.getPage()},methods:{getPage:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return this.loading=!0,e.next=3,this.$API.oplog.getPage(this.searchForm);case 3:t=e.sent,this.pageResult=t.data,this.loading=!1;case 6:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),refresh:function(){this.searchForm.searchText="",this.searchForm.bgnOpTime=null,this.searchForm.endOpTime=null,this.searchForm.opType=null,this.opTimeRanges=null,this.getPage()},pageChange:function(e,t){this.searchForm.pageNum=e,this.searchForm.pageSize=t,this.getPage()},setTime:function(e){this.searchForm.bgnOpTime=e[0],this.searchForm.endOpTime=e[1]}}},i=n,o=(a("3647"),a("2877")),c=Object(o["a"])(i,l,r,!1,null,"4b1cbb46",null);t["default"]=c.exports},3647:function(e,t,a){"use strict";a("af1a")},af1a:function(e,t,a){}}]);