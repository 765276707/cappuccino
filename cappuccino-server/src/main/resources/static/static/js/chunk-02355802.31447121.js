(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-02355802"],{"0c62":function(e,t,n){"use strict";n("319b")},"0ec9":function(e,t,n){"use strict";n.r(t);var l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-container",[n("el-header",{staticStyle:{padding:"10px"},attrs:{height:"80px"}},[n("div",{staticStyle:{padding:"5px",background:"#F2F6FC","border-radius":"5px"}},[n("div",{staticClass:"topbar"},[n("div",{staticClass:"left-panel"},[n("el-button",{attrs:{icon:"el-icon-refresh"},on:{click:e.refresh}},[e._v("刷新")])],1),e._v(" "),n("div",{staticClass:"right-panel"},[n("div",{staticClass:"right-panel-search"},[n("el-select",{staticStyle:{width:"150px"},attrs:{placeholder:"配置类型",clearable:""},model:{value:e.searchForm.type,callback:function(t){e.$set(e.searchForm,"type",t)},expression:"searchForm.type"}},[n("el-option",{attrs:{label:"主配置",value:"1"}}),e._v(" "),n("el-option",{attrs:{label:"灰度配置",value:"2"}})],1),e._v(" "),n("el-date-picker",{attrs:{type:"daterange","start-placeholder":"开始日期","end-placeholder":"结束日期",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss","default-time":["00:00:00","23:59:59"],clearable:""},on:{change:e.setTime},model:{value:e.opTimeRanges,callback:function(t){e.opTimeRanges=t},expression:"opTimeRanges"}}),e._v(" "),n("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"客户端编号/名称"},model:{value:e.searchForm.searchText,callback:function(t){e.$set(e.searchForm,"searchText",t)},expression:"searchForm.searchText"}}),e._v(" "),n("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.getPage()}}})],1)])])])]),e._v(" "),n("el-main",{staticStyle:{"padding-left":"10px","padding-right":"10px"}},[n("el-card",{attrs:{shadow:"always","body-style":"padding: 0px; min-height: 500px;"}},[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"Table",staticStyle:{width:"100%","min-height":"450px"},attrs:{data:e.pageResult.list,"element-loading-text":"加载数据中","header-row-style":{height:"45px"},"row-style":{height:"60px"},"header-cell-style":{background:"#F2F6FC"}},scopedSlots:e._u([{key:"empty",fn:function(){return[n("el-empty",{attrs:{description:"查无数据","image-size":100}})]},proxy:!0}])},[n("el-table-column",{attrs:{prop:"id",label:"序号",width:"100"}}),e._v(" "),n("el-table-column",{attrs:{label:"配置类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.type?n("el-tag",[e._v("主配置")]):n("el-tag",{attrs:{type:"info"}},[e._v("灰度配置")])]}}])}),e._v(" "),n("el-table-column",{attrs:{prop:"clientId",label:"客户端编号"}}),e._v(" "),n("el-table-column",{attrs:{prop:"clientName",label:"客户端名称"}}),e._v(" "),n("el-table-column",{attrs:{prop:"description",label:"配置描述"}}),e._v(" "),n("el-table-column",{attrs:{prop:"fileExtension",label:"拓展名"}}),e._v(" "),n("el-table-column",{attrs:{prop:"sign",label:"MD5签名"}}),e._v(" "),n("el-table-column",{attrs:{prop:"version",label:"版本",width:"80"}}),e._v(" "),n("el-table-column",{attrs:{prop:"configVersion",label:"来源主版本(灰度)",width:"120"}}),e._v(" "),n("el-table-column",{attrs:{prop:"rules",label:"规则(灰度)"}}),e._v(" "),n("el-table-column",{attrs:{prop:"releaseTime",label:"发布时间"}}),e._v(" "),n("el-table-column",{attrs:{prop:"releaseUser",label:"发布账号"}}),e._v(" "),n("el-table-column",{attrs:{label:"操作",width:"130"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{staticStyle:{color:"#67C23A"},attrs:{type:"text",icon:"el-icon-view"},on:{click:function(n){return e.views(t.$index,t.row)}}},[e._v("查看")]),e._v(" "),n("el-button",{staticStyle:{color:"#E6A23C"},attrs:{type:"text",icon:"el-icon-refresh-left"},on:{click:function(n){return e.rollback(t.$index,t.row)}}},[e._v("回滚")])]}}])})],1),e._v(" "),n("pagination",{attrs:{total:e.pageResult.total},on:{"page-change":e.pageChange}})],1)],1),e._v(" "),e.visibleViewsDialog?n("el-dialog",{attrs:{title:"查看配置",visible:e.visibleViewsDialog,width:"1000px","show-close":!0,"close-on-click-modal":!1,"close-on-press-escape":!1,"append-to-body":""},on:{"update:visible":function(t){e.visibleViewsDialog=t},close:function(t){return e.closeViews()}}},[n("el-descriptions",{attrs:{direction:"vertical",column:1,border:""}},[n("el-descriptions-item",{attrs:{label:"版本号"}},[e._v(e._s(null==e.currentRow.version?"-":e.currentRow.version))]),e._v(" "),n("el-descriptions-item",{attrs:{label:"配置"}},[n("code-mirror",{attrs:{mode:e.currentRow.fileExtension},model:{value:e.currentRow.content,callback:function(t){e.$set(e.currentRow,"content",t)},expression:"currentRow.content"}})],1)],1)],1):e._e()],1)},a=[],i=(n("96cf"),n("1da1")),r=n("7d19"),o={components:{CodeMirror:r["a"]},data:function(){return{opTimeRanges:null,searchForm:{type:null,searchText:"",bgnTime:null,endTime:null},pageResult:{total:0,pageNum:1,pageSize:10,list:[]},loading:!1,visibleViewsDialog:!1,currentRow:{fileExtension:null,content:null,version:null}}},mounted:function(){this.getPage()},methods:{getPage:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return this.loading=!0,e.next=3,this.$API.release.getPage(this.searchForm);case 3:t=e.sent,this.pageResult=t.data,this.loading=!1;case 6:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),refresh:function(){this.searchForm.searchText="",this.searchForm.bgnTime=null,this.searchForm.endTime=null,this.searchForm.type=null,this.opTimeRanges=null,this.getPage()},pageChange:function(e,t){this.searchForm.pageNum=e,this.searchForm.pageSize=t,this.getPage()},setTime:function(e){this.searchForm.bgnTime=e[0],this.searchForm.endTime=e[1]},views:function(e,t){this.visibleViewsDialog=!0,this.currentRow.fileExtension=t.fileExtension,this.currentRow.content=t.content,this.currentRow.version=t.version},closeViews:function(){this.visibleViewsDialog=!1,this.currentRow.fileExtension=null,this.currentRow.content=null,this.currentRow.version=null},rollback:function(e,t){var n=this;this.$confirm("回滚将会覆盖主配置并以此发布一个新的版本，是否继续?","版本回滚",{confirmButtonText:"继续",cancelButtonText:"取消",type:"warning",center:!0}).then((function(){n.$API.release.rollback(t.id).then((function(e){n.$emit("success"),n.$message.success(e.message),n.close()}))})).catch((function(){n.$message({type:"info",message:"已取消回滚"})}))}}},s=o,c=(n("c0d2"),n("2877")),u=Object(c["a"])(s,l,a,!1,null,"47a5d888",null);t["default"]=u.exports},"319b":function(e,t,n){},"6f3a":function(e,t,n){},"7d19":function(e,t,n){"use strict";var l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"code-editor"}},[n("codemirror",{attrs:{options:e.editorOptions},model:{value:e.selectValue,callback:function(t){e.selectValue=t},expression:"selectValue"}})],1)},a=[],i=n("8f94");n("a7be"),n("d2e8"),n("f4ba");n("dc5b"),n("ced0");var r={name:"CodeMirror",props:{value:{type:String},mode:{type:String},readOnly:{type:Boolean,default:!1}},components:{codemirror:i["codemirror"]},data:function(){return{editorOptions:{autoRefresh:!0,tabSize:4,mode:this.mode,theme:"material",lineNumbers:!0,line:!0,readOnly:this.readOnly}}},computed:{selectValue:{get:function(){return this.value},set:function(e){this.$emit("input",e)}}}},o=r,s=(n("0c62"),n("2877")),c=Object(s["a"])(o,l,a,!1,null,null,null);t["a"]=c.exports},c0d2:function(e,t,n){"use strict";n("6f3a")}}]);