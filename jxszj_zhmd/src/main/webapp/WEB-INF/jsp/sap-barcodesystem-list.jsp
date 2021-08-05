<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

	<div id="barcodesystem-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addBarcodesystem()" plain="true">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editBarcodesystem()" plain="true">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printBarcodesystem()" plain="true">打印</a>
		</div>
		
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="barcodesystemList" toolbar="#barcodesystem-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getBarcodesystemList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'cpmc',width:150">产品名称</th>
				<th data-options="field:'ggxh',width:150">规格型号</th>
				<th data-options="field:'codedata',width:150">二维码数据</th>
				<th data-options="field:'number',width:150">打印张数</th>
			</tr>
		</thead>
	</table>
	</div>

<!-- 手动添加 -->
<div id="barcodesystemDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:600px; height:400px; padding:10px;" 
	buttons="#barcodesystemDlg-buttons" >
	<form id="barcodesystemForm" method="post" >
		<input type="hidden" name="id" />
		<table >
			<tr>
				<td>产品名称:</td>
				<td><input name="cpmc"  data-options="required:true" class="easyui-textbox" style="width: 280px;"></input></td> 
			</tr>
			<tr>
				<td>规格型号:</td>
				<td><input name="ggxh" data-options="required:true" class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>二维码数据:</td>
				<td><input name="codedata" data-options="required:true" class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>打印张数:</td>
				<td><input name="number"   class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
		</table>
	</form>
</div>
<div id="barcodesystemDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBarcodesystem()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#barcodesystemDlg').dialog('close')">取消</a>
</div>

<script>

function getbarcodesystemSelectionsIds() {
	var barcodesystemList = $("#barcodesystemList");
	var sels = barcodesystemList.datagrid("getSelections");
	var ids = [];
	for ( var i in sels) {
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}

	
	
	//手工新增物料
	function addBarcodesystem() {
		$('#barcodesystemDlg').dialog('open').dialog('setTitle', '新增物料');
		$('#barcodesystemForm').form('clear');
		url = '${pageContext.request.contextPath}/addBarcodesystemTb';
	}
	
	
	//修改
	function editBarcodesystem() {
		var ids = getbarcodesystemSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一个物料才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一个物料!','info');
			return;
		}
		var row = $('#barcodesystemList').datagrid('getSelected');
		if (row) {
			$('#barcodesystemDlg').dialog('open').dialog('setTitle', '修改物料');
			$('#barcodesystemForm').form('load', row);
			url = '${pageContext.request.contextPath}/editBarcodesystemTb';
		}

	}
	
	function saveBarcodesystem() {
        $("#barcodesystemForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
            	var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#barcodesystemDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#barcodesystemList").datagrid("load");
                    });
                } else {
                    $.messager.alert("警告", result.msg, 'warning');
                }
            }
        });
    }
	
	
	function printBarcodesystem(){
		var ids = getbarcodesystemSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '请选择一条数据打印!','info');
			return;
		}
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
			 data: {ids:ids},
		     url: "${pageContext.request.contextPath}"+"/print",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '打印中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
                if (data.status == 200) {
                    $.messager.alert("提示", data.msg,'info');
                } else {
                    $.messager.alert("警告", data.msg, 'warning');
                }
		     }  
		  });
	}
	
	
</script>
