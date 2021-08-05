<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

	<div id="fwf-toolbar">
		<div class="wu-toolbar-button">
<!-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="sendfwf()" plain="true">同步</a> -->
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up'" onclick="addfwfExcel()" plain="true">上传数据</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="wyfee.exportExcel()" plain="true">导出数据</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="fwfRemove()" plain="true">删除</a>
		</div>
		<div class="wu-toolbar-search">
			<label>同步状态：</label>
			<select class="easyui-combobox" id="fwfStatus" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
                <option value="y">已同步</option>
                <option value="n">未同步</option>
            </select>
            <label>转款日期 : </label><input id="fwfzkrq" class="easyui-datebox" style="width: 150px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindfwfList()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
		<table class="easyui-datagrid" id="fwf" toolbar="#fwf-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/fwf/getSAPPostList',method:'get',pageSize:50,pageList: [50, 100, 500]">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'pzbh',width:90, styler: function(value,row,index){
					if(value !=''){
						return 'color:green;';
					}
				}">凭证编号</th>
					<th data-options="field:'djbh',width:140">单据编号</th>
					<th data-options="field:'jdybm',width:140">简道云编码</th>
					<th data-options="field:'jxsbm',width:90">经销商编码</th>
					<th data-options="field:'jxsmc',width:200">经销商名称</th>
					<th data-options="field:'zkrq',width:90,formatter:ReportForm.formatDateTime3">转款日期</th>
					<th data-options="field:'skbz',width:200">收款备注</th>
					<th data-options="field:'fwf',width:60">服务费</th>
					<th data-options="field:'lrzx',width:60">利润中心</th>
					<th data-options="field:'cjr',width:60">创建人</th>
					<th data-options="field:'status',width:60,formatter:ReportForm.formattbjdyStatus, styler: function(value,row,index){
					if(value =='n'){
						return 'color:red;';
					}else if(value=='y'){
						return 'color:green;';
					}
				}">同步状态</th>
					<th data-options="field:'msg',width:320,formatter:formatCellTooltip, styler: function(value,row,index){
					if(value !=''){
						return 'color:red;';
					}
				}">失败原因</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- Excel导入 -->
<div id="fwfExcelDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:150px; padding:10px;" 
	buttons="#fwfExcelDlg-buttons" >
	<form id="fwfExcelForm"  method="post" enctype="multipart/form-data">
	   选择文件：　<input id="fwfExcelFile" name="fwfExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
	</form> 
</div>
<div id="fwfExcelDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadfwfExcel()">导入数据</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fwfExcelDlg').dialog('close')">取消</a>
</div>
<script>

function getfwfSelectionsIds() {
	var fwf = $("#fwf");
	var sels = fwf.datagrid("getSelections");
	var ids = [];
	for ( var i in sels) {
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}

function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 



// //同步
// function sendfwf() {
// 	var fwf = $("#fwf");
// 	var sels = fwf.datagrid("getSelections");
// 	for ( var i in sels) {
// 		if(sels[i].status=='y'){
// 			$.messager.alert('提示', '已同步的数据不能重复同步!','info');
// 			return;
// 		}
// 	}
	
// 	var ids = getfwfSelectionsIds();
// 	if (ids.length == 0) {
// 		$.messager.alert('提示', '至少选择一条数据同步!','info');
// 		return;
// 	}
// 	$.ajax({  
// 		 contentType:"application/json;charset=utf-8",
// 		 type: 'GET',  
// 		 data: {ids:ids},
// 	     url: "${pageContext.request.contextPath}"+"/fwf/sendfwf",   
// 		 beforeSend: function () {
// 		    $.messager.progress({ 
// 		       title: '提示', 
// 		       msg: '同步中，请稍候……', 
// 		       text: '' 
// 		    });
// 		 },
// 		 complete: function () {
// 		       $.messager.progress('close');
// 		 },
// 	     success: function(data){   
// 	     	if(data.status ==200){
// 	     		$.messager.alert('提示',data.msg,'info',function(){
//                 	$("#fwf").datagrid("load");
//                 });
// 			}else{
// 				$.messager.alert('提示',data.msg,'info');
// 			}
// 	     }  
// 	  });

// }


//上传Excel数据
function addfwfExcel() {
	$('#fwfExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
	$('#fwfExcelForm').form('clear');
}

function uploadfwfExcel(){   
	 //得到上传文件的全路径
	 var fileName= $('#fwfExcelFile').filebox('getValue');
	//进行基本校验
	 if(fileName==""){   
		$.messager.alert('提示','请选择上传文件！','info'); 
	 }else{
		 //对文件格式进行校验
		 var d1=/\.[^\.]+$/.exec(fileName); 
		 if(d1==".xlsx" || d1==".xls"){
			 var formData=new FormData(document.getElementById("fwfExcelForm"));
			 $.ajax({  
		      	url: "${pageContext.request.contextPath}/fwf/uploadPostExcel",  
		      	type:"post",
				data:formData,
				//告诉jQuery不要去处理发送的数据
				processData:false,
				//告诉jQuery不要去设置Content-Type请求头,因为表单已经设置了multipart/form-data
				contentType:false,
				beforeSend: function () {
					    $.messager.progress({ 
					       title: '提示', 
					       msg: '数据上传同步中，请稍候……', 
					       text: '' 
					    });
					 },
					 complete: function () {
					       $.messager.progress('close');
				},
				success:function(data) {
					if(data.status ==200){
						$("#fwfExcelDlg").dialog("close");
						$.messager.alert('提示',data.msg,'info',function(){
	                    	$("#fwf").datagrid("load");
	                    });
					}else{
						$.messager.alert("警告", data.msg, 'warning');
						return;
					}
				}
			    });
	    }else{
	        $.messager.alert('提示','请选择xlsx或xls格式文件！','info'); 
	    	$('#fwfExcelFile').filebox('setValue',''); 
	    }
	 } 

 }
	
	function doFindfwfList(){
		var fwfStatus= $('#fwfStatus').combobox('getValue');
		$('#fwf').datagrid('load',{
			zkrq: $('#fwfzkrq').datebox('getValue'),
			fwfStatus: fwfStatus,
		});
	}
	
	
	//删除
	function fwfRemove() {
		var ids = getfwfSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据进行删除!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/fwf/delfwf", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	$("#fwf").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});

	}
	
	
	   var ctx = "${pageContext.request.contextPath}";
		var wyfee = (function(){
			return{
			exportExcel:function(){
				var fwfStatus= $('#fwfStatus').combobox('getValue');
				var zkrq=$('#fwfzkrq').datebox('getValue');
				var url =  ctx + "/fwf/export?fwfStatus="+fwfStatus+"&zkrq="+zkrq;
				window.location.href = url;  
		}
		}
	})();
	

</script>
