 <%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
	<div id="DailyUser-toolbar">
		<div class="wu-toolbar-search">
            <label>日期 : </label><input id="startDate" class="easyui-datebox" style="width: 150px"> - <input id="endDate" class="easyui-datebox" style="width: 150px" validType="end"> 
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="log.exportExcel()" plain="true">导出日志</a>
        </div>
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="dailyUserAdd()" plain="true">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="dailyUserRemove()" plain="true">删除</a>
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">取消</a> -->
		</div>
	</div>

	<table class="easyui-datagrid" id="DailyUserList" toolbar="#DailyUser-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getDailyUserList',method:'get',pageSize:30">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'userId',width:120">钉钉编号</th>
				<th data-options="field:'name',width:80">姓名</th>
			</tr>
		</thead>
	</table>
</div>
<div id="DailyUserDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:200px; padding:10px;" 
	buttons="#DailyUserDlg-buttons" >
	<form id="DailyUserForm" method="post">
		<input type="hidden" name="id" />
		<table >
			<tr>
				<td>钉钉编号:</td>
				<td><input class="easyui-textbox"  name="userId" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td><input class="easyui-textbox"  name="name" data-options="required:true" style="width: 200px;"></input></td>
			</tr>
		</table>
	</form>
</div>
<div id="DailyUserDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savedailyUser()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#DailyUserDlg').dialog('close')">取消</a>
</div>
<script>
	function getDailyUserSelectionsIds() {
		var dailyUserList = $("#DailyUserList");
		var sels = dailyUserList.datagrid("getSelections");
		var items = [];
		for ( var i in sels) {
			items.push(sels[i].userId+":"+sels[i].name);
		}
		items = items.join(",");
		return items;
	}
	
	var url;
	//新增
	function dailyUserAdd() {
		$('#DailyUserDlg').dialog('open').dialog('setTitle', '新增数据');
		$('#DailyUserForm').form('clear');
		url = '${pageContext.request.contextPath}/addDailyuserTb';
	}

	//删除
	function dailyUserRemove() {
		var dailyUserList = $("#DailyUserList");
		var sels = dailyUserList.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].userId);
		}
		ids = ids.join(",");
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/deleteDailyuserTb", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#DailyUserList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}
	
	
	
	
	function savedailyUser() {
        $("#DailyUserForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#DailyUserDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#DailyUserList").datagrid("load");
                    });
                } else {
                    $.messager.alert("提示", result.msg,'info');
                    return;
                }
            }
        });
    }
	
	$.extend($.fn.validatebox.defaults.rules, {
		  end: {
		    validator: function (value, param) {
		      var startDate = $('#startDate').datebox('getValue');
		      var startTmp = new Date(startDate.replace(/-/g, "/"));
		      var endTmp = new Date(value.replace(/-/g, "/"));
		      return startTmp <= endTmp;
		    },
		    message: '结束日期要大于开始日期！'
		  }
		})
	
	   var ctx = "${pageContext.request.contextPath}";
		var log = (function(){
			return{
			exportExcel:function(){
				var items = getDailyUserSelectionsIds();
				if (items.length == 0) {
					$.messager.alert('提示', '至少选择一条数据!','info');
					return;
				}
				var startDate=$('#startDate').datebox('getValue');
				var endDate=$('#endDate').datebox('getValue');
				var url =  ctx + "/dingTalkLog/export?startDate="+startDate+"&endDate="+endDate+"&items="+items;
				window.location.href = url;  
		}
		}
	})();


// 	function logExportExcel(){
// 		var ids = getDailyUserSelectionsIds();
// 		if (ids.length == 0) {
// 			$.messager.alert('提示', '至少选择一条数据!','info');
// 			return;
// 		}
		
// 		var dailyUserList = $("#DailyUserList");
// 		var dataJson = dailyUserList.datagrid("getSelections");
// 		$.ajax({  
// 			 contentType:"application/json;charset=utf-8",
// 			 type: 'POST',  
// 			 traditional:true,
// 		     dataType : 'json', 
// 		     data: JSON.stringify(dataJson),
// 		     url: "${pageContext.request.contextPath}/price/sendPriceToJDY",   
// 			 beforeSend: function () {
// 			    $.messager.progress({ 
// 			       title: '提示', 
// 			       msg: '价格同步中，请稍候……', 
// 			       text: '' 
// 			    });
// 			 },
// 			 complete: function () {
// 			       $.messager.progress('close');
// 			 },
// 		     success: function(data){   
// 		     	if(data.status ==200){
// 						$.messager.alert('提示',data.msg,'info',function(){
// 							$("#sapPrice").datagrid("load");
// 					});
// 				}else{
// 					$.messager.alert('提示',data.msg,'info');
// 				}
// 		     }  
// 		  });
// 	}
</script>