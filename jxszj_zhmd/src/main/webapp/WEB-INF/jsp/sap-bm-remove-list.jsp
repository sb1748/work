<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

	<div id="sapBm-remove-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-arrow-redo" onclick="sapBmRestore()" plain="true">数据还原</a>
		</div>
		
		<div class="wu-toolbar-search">
           	<label>新物料编码：</label><input id="new_rem_wlbm" class="easyui-textbox" style="width: 120px"> 
           	<label>新物料名称：</label><input id="new_rem_name" class="easyui-textbox" style="width: 150px"> 
           	<label>旧物料编码：</label><input id="old_rem_wlbm" class="easyui-textbox" style="width: 120px"> 
           	<label>旧物料名称：</label><input id="old_rem_name" class="easyui-textbox" style="width: 150px"> 
           	<label>是否需要创建BOM：</label>
           	 <select class="easyui-combobox" id="bom_rem" panelHeight="auto" style="width:80px">
            	<option value="">全部</option>
                <option value="y">需要</option>
                <option value="n">不需要</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindRem()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sapBmRem" toolbar="#sapBm-remove-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/bm/getSapBmRemList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'newWlbm',width:150">新物料编码</th>
				<th data-options="field:'newName',width:230">新物料名称</th>
				<th data-options="field:'oldWlbm',width:150">旧物料编码</th>
				<th data-options="field:'oldName',width:230">旧物料名称</th>
				<th data-options="field:'bom',width:120, styler: function(value,row,index){
					if(value =='n'){
						return 'color:red;';
					}else if(value=='y'){
						return 'color:green;';
					}
				},formatter:ReportForm.formatBom">是否需要创建BOM</th>
				<th data-options="field:'createtime',width:230">更新日期	</th>
			</tr>
		</thead>
	</table>
	</div>

<script>

function getSapBmRemSelectionsIds() {
	var sapBmRem = $("#sapBmRem");
	var sels = sapBmRem.datagrid("getSelections");
	var ids = [];
	for ( var i in sels) {
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}


function doFindRem(){
	var bom_rem= $('#bom_rem').combobox('getValue');
	$('#sapBmRem').datagrid('load',{
		newWlbm: $('#new_rem_wlbm').val(),
		newName: $('#new_rem_name').val(),
		oldWlbm: $('#old_rem_wlbm').val(),
		oldName: $('#old_rem_name').val(),
		bom:bom_rem
	});
}
	
	
	function sapBmRestore() {
		var ids = getSapBmRemSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个物料!','info');
			return;
		}
		var params = {
				"ids" : ids
			};
		$.post("${pageContext.request.contextPath}/sap/bm/restoreSapBm", params,
			function(data) {
				if (data.status == 200) {
                    $.messager.alert("提示", data.msg,'info',function(){
                    	 $("#sapBmRem").datagrid("load");
                    });
				} else {
					$.messager.alert('提示', data.msg,'info');
				}
			});

	}
</script>
