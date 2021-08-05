<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div id="sap-wllb-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="sap_wllbAdd()" plain="true">添加</a>
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="sap_wllbEdit()" plain="true">修改</a> -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="sap_wllbRemove()" plain="true">删除</a>
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">取消</a> -->
		</div>
	</div>

	<table class="easyui-datagrid" id="sap_wllbList" toolbar="#sap-wllb-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/wllb/getWlbmList',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'text',width:280">编码名</th>
				<th data-options="field:'id',width:80">编码</th>
<!-- 				<th data-options="field:'ptext',width:280">父级编码名</th> -->
<!-- 				<th data-options="field:'pid',width:80">父级编码</th> -->
			</tr>
		</thead>
	</table>
</div>
<div id="sap_wllbDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:600px; height:400px; padding:10px;" 
	buttons="#sap-wllbDlg-buttons" >
	<form id="sap_wllbForm" method="post">
		<table >
			<tr>
				<td>编码名:</td>
				<td><input name="text" id="text" data-options="required:true" class="easyui-textbox" style="width: 350px;"></td>
			</tr>
			<tr>
				<td>编码值:</td>
				<td><input class="easyui-textbox" data-options="required:true"  name="id"  style="width: 350px;"></input></td>
			</tr>
			<tr>
				<td>父级编码名:</td>
				<td><input class="easyui-textbox" data-options="animate:true" name="pid" id="pid" style="width:350px;" /></td>
			</tr>
			<input type="hidden" name="ptext" id="ptext"  >
		</table>
	</form>
</div>
<div id="sap-wllbDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="sap_saveWlbm()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sap_wllbDlg').dialog('close')">取消</a>
</div>
<script>
	function getSapWlbmSelectionsIds() {
		var itemList = $("#sap_wllbList");
		var sels = itemList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
		$('#pid').combotree({
			valueField: 'id',
		    textField: 'text',
			method: 'get', //必须指明
	        editable: false,   //编辑，支持模糊查询
	        multiple: false,   //为true，出来多选框，勾选多个
	        prompt: '请选择下拉...',
			onShowPanel : function(){
			    $('#pid').combotree({
					url: '${pageContext.request.contextPath}/sap/wllb/getWlbmLists',
			        onSelect: function(data) {
			         	$('#ptext').val(data.text); 
			        },
			        onLoadSuccess: function (row, data) {
			            $('#pid').combotree('tree').tree("collapseAll");         
					}
				});
			}
		})
	
//     $('#pid').combotree({
// 		url: '${pageContext.request.contextPath}/sap/wllb/getWlbmLists',
// 		valueField: 'id',
// 	    textField: 'text',
// 		method: 'get', //必须指明
//         editable: false,   //编辑，支持模糊查询
//         multiple: false,   //为true，出来多选框，勾选多个
//         required: true,
//         missingMessage: '不能为空，请选择',
//         prompt: '请选择下拉...',
//         onSelect: function(data) {
// //         	$('#ptext').val(data.text); 
//         	$('#ptext').textbox('setValue',data.text);
//         },
//         onLoadSuccess: function (row, data) {
//             $('#pid').combotree('tree').tree("collapseAll");         
// 		}
// 	});
	
	
	var url;
	//新增品牌
	function sap_wllbAdd() {
		$('#sap_wllbDlg').dialog('open').dialog('setTitle', '新增编码');
		$('#sap_wllbForm').form('clear');
		url = '${pageContext.request.contextPath}/sap/wllb/addWlbm';
	}

	//修改品牌
	function sap_wllbEdit() {
		var ids = getSapWlbmSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一个编码才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一个编码!','info');
			return;
		}
		var row = $('#sap_wllbList').datagrid('getSelected');
		if (row) {
			$('#sap_wllbDlg').dialog('open').dialog('setTitle', '修改编码');
			$('#sap_wllbForm').form('load', row);
			url = '${pageContext.request.contextPath}/sap/wllb/editWlbm';
		}

	}
	//删除
	function sap_wllbRemove() {
		var ids = getSapWlbmSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个编码!','info');
			return;
		}
		$.messager.confirm('确认', '删除的编码如果有子编码将一起删除？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/sap/wllb/deleteWlbm", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sap_wllbList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}
	function sap_saveWlbm() {
        $("#sap_wllbForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sap_wllbDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	 $("#sap_wllbList").datagrid("load");
                    });
                } else {
                    $.messager.alert("警告", result.msg, 'warning');
                    return;
                }
            }
        });
    }
</script>