<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'west',border:true,split:true," title="分类管理" style="width:200px; padding:5px;">
     <label>数据值编码：</label><input id="code" id="code" class="easyui-textbox" style="width: 80px">
        <ul id="wu-category-tree"></ul>
    </div>

	<div id="sapBm-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="sapBmOldAdd()" plain="true">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="sapBmOpenEdit()" plain="true">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="sapBmRemove()" plain="true">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-delete3" onclick="sapBmDelete()" plain="true">彻底删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-delete" onclick="sapBmDeleteAll()" plain="true">删除全部</a>
<!-- 			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="exportExcel()" plain="true">下载Excel模板</a> -->
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up'" onclick="addExcel()" plain="true">上传数据</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="wyfee.exportExcel()" plain="true">导出数据</a>
		</div>
		
		<div class="wu-toolbar-search">
           	<label>新物料编码：</label><input id="new_wlbm" class="easyui-textbox" style="width: 120px"> 
           	<label>新物料名称：</label><input id="new_name" class="easyui-textbox" style="width: 150px"> 
           	<label>旧物料编码：</label><input id="old_wlbm" class="easyui-textbox" style="width: 120px"> 
           	<label>旧物料名称：</label><input id="old_name" class="easyui-textbox" style="width: 150px"> 
           	<label>是否需要创建BOM：</label>
           	 <select class="easyui-combobox" id="bom" panelHeight="auto" style="width:80px">
            	<option value="">全部</option>
                <option value="y">需要</option>
                <option value="n">不需要</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindList()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sapBmList" toolbar="#sapBm-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/bm/getSapBmList',method:'get',pageSize:30">
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
<div id="sapBmDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:600px; height:400px; padding:10px;" 
	buttons="#sapBmDlg-buttons" >
	<form id="sapBmForm" method="post">
		<input type="hidden" name="id" />
		<table>
			<tr>
				<td>物料分类:</td>
				<td><input class="easyui-textbox" editable='false' name="bmm" id="bmm" style="width:350px;" /></td>
				<input type="hidden" name="bmid" id="bmid"  >
			</tr>
		</table>
		<table id="mytab">
		</table>
	</form>
</div>
<div id="sapBmDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savesapBm()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sapBmDlg').dialog('close')">取消</a>
</div>

<!-- 手动添加 -->
<div id="sapBmOldDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:600px; height:400px; padding:10px;" 
	buttons="#sapBmOldDlg-buttons" >
	<form id="sapBmOldForm" method="post" >
		<input type="hidden" name="id" />
		<table >
			<tr>
				<td>新物料编码:</td>
				<td><input name="newWlbm" id="newWlbm" data-options="required:true" class="easyui-textbox" style="width: 280px;"></input></td> 
			</tr>
			<tr>
				<td>新物料名称:</td>
				<td><input name="newName" id="newName" data-options="required:true" class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>旧物料编码:</td>
				<td><input name="oldWlbm" id="oldWlbm"  class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>旧物料名称:</td>
				<td><input name="oldName" id="oldName" class="easyui-textbox" style="width: 280px;"></input></td>
			</tr>
			<tr>
				<td>是否需要创建BOM:</td>
				<td><select id="bom" class="easyui-combobox" name="bom" style="width:250px;" data-options="editable:false,panelHeight:'auto'">
				    <option value="y">需要</option>
				    <option value="n">不需要</option>
				</select></td> 
			</tr>
		</table>
	</form>
</div>
<div id="sapBmOldDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="sapBmOldSave()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sapBmOldDlg').dialog('close')">取消</a>
</div>

<!-- Excel导入 -->
<div id="sapBmExcelDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:150px; padding:10px;" 
	buttons="#sapBmExcelDlg-buttons" >
	<form id="excelForm"  method="post" enctype="multipart/form-data">
	   选择文件：　<input id="excelFile" name="excelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
	</form> 
</div>
<div id="sapBmExcelDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadExcel()">导入数据</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sapBmExcelDlg').dialog('close')">取消</a>
</div>

<script>

function getSapBmSelectionsIds() {
	var sapBmList = $("#sapBmList");
	var sels = sapBmList.datagrid("getSelections");
	var ids = [];
	for ( var i in sels) {
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}


function doFindList(){
	var bom= $('#bom').combobox('getValue');
	$('#sapBmList').datagrid('load',{
		newWlbm: $('#new_wlbm').val(),
		newName: $('#new_name').val(),
		oldWlbm: $('#old_wlbm').val(),
		oldName: $('#old_name').val(),
		bom:bom
	});
}



var url;
/**
* Name 载入菜单树
*/
$(document).ready(function(){
    $('#wu-category-tree').tree({
        animate:true,
        url: '${pageContext.request.contextPath}/sap/wlbm/getWlbmView',
        onLoadSuccess: function (row, data)
        {
            $("#wu-category-tree").tree("collapseAll");
        },
	   	onClick:function(node){
        	var tree = $(this).tree;
        	var isLeaf = tree('isLeaf', node.target);
        	var gzs=$("#code").textbox("getValue");
        	if (!isLeaf) {
                 //清除选中  
                 $.messager.alert('提示', '<br/> 只能选择最小节点，请重新选择 ！', 'info');
             }else{
            	 $.ajax({  
		   		     type: 'POST',   
		   		     data: {bmz:node.id,gzs:gzs},
		   		     url: "${pageContext.request.contextPath}"+"/sap/bm/getSapDataLists",
			   		  success: function(data){   
			   			  if(data){
			   				$('#sapBmDlg').dialog('open').dialog('setTitle', '新增物料');
			   				$('#sapBmForm').form('clear');
			   				url = '${pageContext.request.contextPath}/sap/bm/addSapBm';
			   				$("#mytab").html(data);
			   				$.parser.parse("#mytab");
			   				$("#bmm").textbox("setValue", node.text);
			   				$('#bmid').val(node.id); 
			   			  }else{
			   				 $.messager.alert('提示', '<br/> 请输入数据值编码，例如：片、卷、五金脚、圆形五金脚！', 'info');
			   			  }
			   				
					     }
		   		  });
             }
	   		
	   	}
    });
});


//彻底删除
function sapBmDelete() {
	var ids = getSapBmSelectionsIds();
	if (ids.length == 0) {
		$.messager.alert('提示', '至少选择一个物料!','info');
		return;
	}
	$.messager.confirm('确认', '确定永久删除选中的物料吗？', function(r) {
		if (r) {
			var params = {
				"ids" : ids
			};
			$.post("${pageContext.request.contextPath}/sap/bm/deleteSapBm", params,
					function(data) {
						if (data.status == 200) {
		                    $.messager.alert("提示", data.msg,'info',function(){
		                    	 $("#sapBmList").datagrid("load");
		                    });
						} else {
							$.messager.alert('提示', data.msg,'info');
						}
					});
		}
	});
}

	//删除全部
	function sapBmDeleteAll() {
		$.messager.confirm('确认', '确定永久删除全部的物料吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/sap/bm/deleteAllSapBm",
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sapBmList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});
	}

	function savesapBm() {
        $("#sapBmForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sapBmDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#sapBmList").datagrid("load");
                    });
                } else {
                    $.messager.alert("警告", result.msg,'warning');
                    return;
                }
            }
        });
    }
	
	
	//手工新增物料
	function sapBmOldAdd() {
		$('#sapBmOldDlg').dialog('open').dialog('setTitle', '新增物料');
		$('#sapBmOldForm').form('clear');
		url = '${pageContext.request.contextPath}/sap/bm/addOldSapBm';
	}
	
	
	//修改
	function sapBmOpenEdit() {
		var ids = getSapBmSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一个物料才能编辑!','info');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一个物料!','info');
			return;
		}
		var row = $('#sapBmList').datagrid('getSelected');
		if (row) {
			$('#sapBmOldDlg').dialog('open').dialog('setTitle', '修改物料');
			$('#sapBmOldForm').form('load', row);
			url = '${pageContext.request.contextPath}/sap/bm/editSapBm';
		}

	}
	
	//删除
	function sapBmRemove() {
		var ids = getSapBmSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个物料进行删除!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的物料吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/sap/bm/removeSapBm", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	 $("#sapBmList").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});

	}
	
	function sapBmOldSave() {
        $("#sapBmOldForm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
            	var result = eval('(' + result + ')');
                if (result.status == 200) {
                	$("#sapBmOldDlg").dialog("close");
                    $.messager.alert("提示", result.msg,'info',function(){
                    	$("#sapBmList").datagrid("load");
                    });
                } else {
                    $.messager.alert("警告", result.msg, 'warning');
                    return;
                }
            }
        });
    }
	
	
	//上传Excel数据
	function addExcel() {
		$('#sapBmExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
		$('#excelForm').form('clear');
	}
	
	
	   function uploadExcel(){   
		 //得到上传文件的全路径
		 var fileName= $('#excelFile').filebox('getValue');
		//进行基本校验
		 if(fileName==""){   
			$.messager.alert('提示','请选择上传文件！','info'); 
		 }else{
			 //对文件格式进行校验
			 var d1=/\.[^\.]+$/.exec(fileName); 
			 if(d1==".xlsx"){
				 var formData=new FormData(document.getElementById("excelForm"));
				 $.ajax({  
			      	url: '${pageContext.request.contextPath}/sap/uploadExcel',
			      	type:"post",
					data:formData,
					//告诉jQuery不要去处理发送的数据
					processData:false,
					//告诉jQuery不要去设置Content-Type请求头,因为表单已经设置了multipart/form-data
					contentType:false,
					beforeSend: function () {
						    $.messager.progress({ 
						       title: '提示', 
						       msg: '数据上传中，请稍候……', 
						       text: '' 
						    });
						 },
						 complete: function () {
						       $.messager.progress('close');
					},
					success:function(data) {
						if(data.status ==200){
							$("#sapBmExcelDlg").dialog("close");
							$.messager.alert('提示',data.msg,'info',function(){
		                    	$("#sapBmList").datagrid("load");
		                    });
						}else{
							$.messager.alert("警告", data.msg, 'warning');
							return;
						}
					}
				    });
		    }else{
		        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
		    	$('#excelFile').filebox('setValue',''); 
		    }
		 } 

	  }
	   
	   
		   var ctx = "${pageContext.request.contextPath}";
			var wyfee = (function(){
				return{
				exportExcel:function(){
				var url =  ctx + "/export";
				window.location.href = url;  
			}
			}
		})();
</script>
