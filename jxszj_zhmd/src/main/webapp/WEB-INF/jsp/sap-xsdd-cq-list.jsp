 <%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div id="sap-xsddcq-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="xsddcqEdit()" plain="true">修改</a>
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="xsddcqRemove()" plain="true">删除</a> -->
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">取消</a> -->
		</div>
		
		<div class="wu-toolbar-search">
           	<label>销售凭证：</label><input id="xsddcq_xspz" class="easyui-textbox" style="width: 180px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindxsddcq()">查询</a>
        </div>
	</div>

	<table class="easyui-datagrid" id="sap-xsddcqList" toolbar="#sap-xsddcq-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/getSapXsddCqTbList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:80">编号</th>
				<th data-options="field:'xspz',width:70">销售凭证</th>
				<th data-options="field:'xspzxm',width:60">销售凭证行</th>
				<th data-options="field:'wl',width:150">物料</th>
				<th data-options="field:'sl',width:50">数量</th>
				<th data-options="field:'khck',width:120">客户参考</th>
				<th data-options="field:'sdf',width:150">售达方</th>
				<th data-options="field:'wlms',width:180">物料描述</th>
				<th data-options="field:'njhrq',width:80">交货日期</th>
				<th data-options="field:'wlz',width:60">物料组</th>
				<th data-options="field:'ssfz',width:60">所属分组</th>
				<th data-options="field:'tcrq',width:60">剔除日期</th>
				<th data-options="field:'yy',width:120">原因</th>
			</tr>
		</thead>
	</table>
</div>
<div id="sap-xsddcqDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:500px; height:300px; padding:10px;" 
	buttons="#sap-xsddcqDlg-buttons" >
	<form id="sap-xsddcqForm" method="post">
		<input type="hidden" name="id" />
		<input type="hidden" name="wl" />
		<input type="hidden" name="sl" />
		<input type="hidden" name="khck" />
		<input type="hidden" name="sdf" />
		<input type="hidden" name="xszz" />
		<input type="hidden" name="jjyy" />
		<input type="hidden" name="jhrq" />
		<input type="hidden" name="njhrq" />
		<input type="hidden" name="wlz" />
		<input type="hidden" name="ssfz" />
		<input type="hidden" name="cjrq" />
		<input type="hidden" name="xgrq" />
		<table >
			<tr>
				<td>销售凭证:</td>
				<td><input class="easyui-textbox"  name="xspz" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
			<tr>
				<td>销售凭证行:</td>
				<td><input class="easyui-textbox"  name="xspzxm" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
			<tr>
				<td>物料描述:</td>
				<td><input class="easyui-textbox"  name="wlms" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
			<tr>
				<td>剔除日期:</td>
				<td><input class="easyui-datebox"  name="tcrq"  style="width: 200px;"></input></td>
			</tr>
			<tr>
				<td>剔除原因:</td>
				<td><input class="easyui-textbox"  name="yy" style="width: 200px;"></input></td>
			</tr>
		</table>
	</form>
</div>
<div id="sap-xsddcqDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savexsddcq()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sap-xsddcqDlg').dialog('close')">取消</a>
</div>
<script>
	function getxsddcqSelectionsIds() {
		var xsddcqList = $("#sap-xsddcqList");
		var sels = xsddcqList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
	var url;

	//修改
	function xsddcqEdit() {
		var ids = getxsddcqSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一条数据才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一条数据!','info');
			return;
		}
		var row = $('#sap-xsddcqList').datagrid('getSelected');
		if (row) {
			$('#sap-xsddcqDlg').dialog('open').dialog('setTitle', '修改编辑');
			$('#sap-xsddcqForm').form('load', row);
			url = '${pageContext.request.contextPath}/sap/editSapXsddCqTb';
		}

	}
	//删除
	function xsddcqRemove() {
		var ids = getxsddcqSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/sap/deleteSapXsddCqTb", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sap-xsddcqList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}
	
	
	
	
	function savexsddcq() {
        $("#sap-xsddcqForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sap-xsddcqDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#sap-xsddcqList").datagrid("load");
                    });
                } else {
                    $.messager.alert("提示", result.msg,'info');
                    return;
                }
            }
        });
    }
	
	function doFindxsddcq(){
		$('#sap-xsddcqList').datagrid('load',{
			xspz: $('#xsddcq_xspz').val()
		});
	}
	
</script>