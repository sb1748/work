<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="sap-pdsc-toolbar">
		<div class="wu-toolbar-search">
			<label>排单日期 : </label><input id="sjpdrq" class="easyui-datebox" style="width: 150px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindpdsc()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sap-pdsc" toolbar="#sap-pdsc-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getPdscView',method:'get',pageSize:50,pageList: [50, 100, 500]">
		<thead>
			<tr>
<!-- 				<th data-options="field:'ck',checkbox:true"></th> -->
				<th data-options="field:'pdrq',width:100,formatter:ReportForm.formatDateTime3">排单日期</th>
				<th data-options="field:'wlz',width:60">物料组</th>
				<th data-options="field:'xdsl',width:80">计划交货数量</th>
				<th data-options="field:'rksl',width:80">实际入库数量</th>
				<th data-options="field:'jhmb',width:100">当日交货目标</th>
			</tr>
		</thead>
	</table>
	</div>

<script>
	
	function doFindpdsc(){
		$('#sap-pdsc').datagrid('load',{
			sjpdrq: $('#sjpdrq').datebox('getValue'),
		});
	}
	
	$('#sap-pdsc').datagrid({
		rowStyler:function(index,row){
			if (row.xdsl>=row.jhmb){
				return 'background-color:red;';
			}
		}
	});
</script>
