 <%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div id="sap-condition-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="conditionAdd()" plain="true">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="conditionEdit()" plain="true">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="conditionRemove()" plain="true">删除</a>
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">取消</a> -->
		</div>
	</div>

	<table class="easyui-datagrid" id="sap-conditionList" toolbar="#sap-condition-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/getConditionList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:60">编号</th>
				<th data-options="field:'condiname',width:120">数据值</th>
			</tr>
		</thead>
	</table>
</div>
<div id="sap-conditionDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:200px; padding:10px;" 
	buttons="#sap-conditionDlg-buttons" >
	<form id="sap-conditionForm" method="post">
		<input type="hidden" name="id" />
		<table >
			<tr>
				<td>数据值:</td>
				<td><input class="easyui-textbox"  name="condiname" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
		</table>
	</form>
</div>
<div id="sap-conditionDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCondition()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sap-conditionDlg').dialog('close')">取消</a>
</div>
<script>
	function getConditionSelectionsIds() {
		var conditionList = $("#sap-conditionList");
		var sels = conditionList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
	var url;
	//新增
	function conditionAdd() {
		$('#sap-conditionDlg').dialog('open').dialog('setTitle', '新增数据');
		$('#sap-conditionForm').form('clear');
		url = '${pageContext.request.contextPath}/sap/addCondition';
	}

	//修改
	function conditionEdit() {
		var ids = getConditionSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一条数据才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一条数据!','info');
			return;
		}
		var row = $('#sap-conditionList').datagrid('getSelected');
		if (row) {
			$('#sap-conditionDlg').dialog('open').dialog('setTitle', '修改编辑');
			$('#sap-conditionForm').form('load', row);
			url = '${pageContext.request.contextPath}/sap/editCondition';
		}

	}
	//删除
	function conditionRemove() {
		var ids = getConditionSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/sap/deleteCondition", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sap-conditionList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}
	
	
	
	
	function saveCondition() {
        $("#sap-conditionForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sap-conditionDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#sap-conditionList").datagrid("load");
                    });
                } else {
                    $.messager.alert("提示", result.msg,'info');
                    return;
                }
            }
        });
    }
	
</script>