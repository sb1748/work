 <%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div id="sap-data-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="sap_dataOpenAdd()" plain="true">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="sap_dataOpenEdit()" plain="true">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="sap_dataRemove()" plain="true">删除</a>
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">取消</a> -->
		</div>
		
		<div class="wu-toolbar-search">
           	<label>小类码值：</label><input id="bmz" class="easyui-textbox" style="width: 180px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFind()">查询</a>
        </div>
	</div>

	<table class="easyui-datagrid" id="sap-dataList" toolbar="#sap-data-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/data/getSapDataList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'bmz',width:200">小类码值</th>
				<th data-options="field:'data',width:600">数据值</th>
				<th data-options="field:'wlmclz',width:200">物料名样例</th>
				<th data-options="field:'ggxhlz',width:200">规格型号样例</th>
				<th data-options="field:'gzs',width:100">数据值编码</th>
			</tr>
		</thead>
	</table>
</div>
<div id="sap-dataDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:600px; height:400px; padding:10px;" 
	buttons="#sap-dataDlg-buttons" >
	<form id="sap-dataForm" method="post">
		<input type="hidden" name="id" />
		<table >
			<tr>
				<td>小类码值:</td>
				<td><input class="easyui-textbox"  name="bmz" data-options="required:true" style="width: 450px;"></input></td>
			</tr>
			<tr>
				<td>数据值:</td>
				<td><input class="easyui-textbox"  name="data" data-options="multiline:true" style="width: 450px;height:100px"></input></td>
			</tr>
			<tr>
				<td>物料名样例:</td>
				<td><input class="easyui-textbox"  name="wlmclz" id="wlmclz"  style="width: 450px" ></input></td>
			</tr>
			<tr>
				<td>规格型号样例:</td>
				<td><input class="easyui-textbox"  name="ggxhlz" id="ggxhlz"  style="width: 450px" ></input></td>
			</tr>
			<tr>
				<td>数据值编码:</td>
				<td><input class="easyui-textbox"  name="gzs" id="gzs"  style="width: 450px" ></input></td>
			</tr>
		</table>
	</form>
</div>
<div id="sap-dataDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savesap_data()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sap-dataDlg').dialog('close')">取消</a>
</div>
<script>
	function getSapDataSelectionsIds() {
		var sapDataList = $("#sap-dataList");
		var sels = sapDataList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
	var url;
	//新增
	function sap_dataOpenAdd() {
		$('#sap-dataDlg').dialog('open').dialog('setTitle', '新增数据');
		$('#sap-dataForm').form('clear');
		url = '${pageContext.request.contextPath}/sap/data/addSapData';
	}

	//修改
	function sap_dataOpenEdit() {
		var ids = getSapDataSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一条数据才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一条数据!','info');
			return;
		}
		var row = $('#sap-dataList').datagrid('getSelected');
		if (row) {
			$('#sap-dataDlg').dialog('open').dialog('setTitle', '修改编辑');
			$('#sap-dataForm').form('load', row);
			url = '${pageContext.request.contextPath}/sap/data/editSapData';
		}

	}
	//删除
	function sap_dataRemove() {
		var ids = getSapDataSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/sap/data/deleteSapData", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sap-dataList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}
	
	
	
	
	function savesap_data() {
        $("#sap-dataForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sap-dataDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#sap-dataList").datagrid("load");
                    });
                } else {
                    $.messager.alert("提示", result.msg,'info');
                    return;
                }
            }
        });
    }
	
	
	function doFind(){
		$('#sap-dataList').datagrid('load',{
			bmz: $('#bmz').val()
		});
	}
	
</script>