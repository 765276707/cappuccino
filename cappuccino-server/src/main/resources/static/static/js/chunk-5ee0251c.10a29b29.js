(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5ee0251c"],{3520:function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-container",[n("el-header",{staticStyle:{padding:"10px"},attrs:{height:"80px"}},[n("div",{staticStyle:{padding:"5px",background:"#F2F6FC","border-radius":"5px"}},[n("div",{staticClass:"topbar"},[e.clusterInfo.enabled?n("div",{staticClass:"left-panel",staticStyle:{"font-size":"14px"}},[e._v("\n                    集群模式，当前共有 "),n("span",{staticStyle:{color:"#E6A23C"}},[e._v(" "+e._s(e.clusterInfo.clusters.length)+" ")]),e._v(" 个节点\n                ")]):n("div",{staticClass:"left-panel",staticStyle:{"font-size":"14px"}},[e._v("\n                    单机模式\n                ")]),e._v(" "),n("div",{staticClass:"right-panel"},[n("div",{staticClass:"right-panel-search"},[n("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"节点地址"},model:{value:e.searchForm.searchText,callback:function(t){e.$set(e.searchForm,"searchText",t)},expression:"searchForm.searchText"}}),e._v(" "),n("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.getInfo()}}}),e._v(" "),n("el-button",{attrs:{icon:"el-icon-refresh"},on:{click:function(t){return e.refresh()}}})],1)])])])]),e._v(" "),n("el-main",{staticStyle:{"padding-left":"10px","padding-right":"10px"}},[n("el-card",{attrs:{shadow:"always","body-style":"padding: 0px; min-height: 350px;"}},[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"Table",staticStyle:{width:"100%","min-height":"350px"},attrs:{data:e.clusterInfo.clusters,"element-loading-text":"加载数据中","header-row-style":{height:"45px"},"row-style":{height:"60px"},"header-cell-style":{background:"#F2F6FC"}},scopedSlots:e._u([{key:"empty",fn:function(){return[n("el-empty",{attrs:{description:"当前无节点","image-size":100}})]},proxy:!0}])},[n("el-table-column",{attrs:{type:"index",width:"80"}}),e._v(" "),n("el-table-column",{attrs:{prop:"address",label:"节点"}}),e._v(" "),n("el-table-column",{attrs:{label:"活跃状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.alive?n("el-tag",{attrs:{type:"success",size:"mini"}},[e._v("UP")]):n("el-tag",{attrs:{type:"danger",size:"mini"}},[e._v("DOWN")])]}}])})],1)],1)],1)],1)},s=[],r=(n("96cf"),n("1da1")),i={data:function(){return{loading:!1,searchForm:{searchText:""},clusterInfo:{enabled:null,clusters:[]},timer:null}},mounted:function(){this.getInfo(),this.timer=setInterval(this.getInfo,3e4)},beforeDestroy:function(){clearInterval(this.timer)},methods:{getInfo:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return this.loading=!0,e.next=3,this.$API.cluster.getInfo();case 3:t=e.sent,this.clusterInfo=t.data,this.loading=!1;case 6:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),refresh:function(){this.searchForm.searchText="",this.getInfo()}}},l=i,c=(n("ffef"),n("2877")),o=Object(c["a"])(l,a,s,!1,null,"516771c5",null);t["default"]=o.exports},"54d9":function(e,t,n){},ffef:function(e,t,n){"use strict";n("54d9")}}]);