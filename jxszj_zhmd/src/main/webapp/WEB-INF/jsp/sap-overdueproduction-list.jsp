<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="sap-overdueproduction-toolbar">
		<div class="wu-toolbar-search">
			<label>销售凭证：</label><input id="overdueproduction_xspz" class="easyui-textbox" style="width: 120px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindoverdueproduction()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sap-overdueproduction" toolbar="#sap-overdueproduction-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getOverdueproduction',method:'get',pageSize:50,pageList: [50, 100, 500]">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'xspz',width:80">销售凭证</th>
				<th data-options="field:'xspzh',width:70">销售凭证行</th>
				<th data-options="field:'kh',width:180">客户名称</th>
				<th data-options="field:'dhdh',width:120">订货单号</th>
				<th data-options="field:'cpbm',width:150">产品编码</th>
				<th data-options="field:'cpmc',width:230">产品名称</th>
				<th data-options="field:'wlz',width:80">物料组名称</th>
				<th data-options="field:'pp',width:80">品牌名称</th>
				<th data-options="field:'jhzq',width:60">交货周期</th>
				<th data-options="field:'xsdq',width:60">销售地区</th>
				<th data-options="field:'dhrq',width:80,formatter:ReportForm.formatDate">订货日期</th>
				<th data-options="field:'dhsl',width:60">订货数量</th>
				<th data-options="field:'ljrksl',width:60">累计入库数量</th>
				<th data-options="field:'scrkrq',width:80,formatter:ReportForm.formatDate">生产入库日期</th>
			</tr>
		</thead>
	</table>
	</div>

<script>
	
	function doFindoverdueproduction(){
		$('#sap-overdueproduction').datagrid('load',{
			xspz: $('#overdueproduction_xspz').val(),
		});
	}
	
</script>
