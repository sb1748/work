<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="jdywl-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="getJdywl()" plain="true">获取简道云物料</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-delete'" onclick="delJdywl()" plain="true">删除简道云物料</a>
<!-- 			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="getPlan()" plain="true">计算超期</a> -->
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up'" onclick="addJdywlExcel()" plain="true">上传物料编码批量删除</a>
		</div>
		<div class="wu-toolbar-search">
           	<label>产品名称：</label><input id="jdycpmc" class="easyui-textbox" style="width: 180px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindWL()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
	<table class="easyui-datagrid" id="jdywl" toolbar="#jdywl-toolbar"
		data-options="singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getJdywl',method:'post',pageSize:50,pageList: [50, 100, 500]">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:180">唯一键</th>
				<th data-options="field:'cpbm',width:180">产品编码</th>
				<th data-options="field:'cpmc',width:200">产品名称</th>
				<th data-options="field:'xszz',width:80">销售组织</th>
				<th data-options="field:'fxqd',width:80">分销渠道</th>
				<th data-options="field:'cplx',width:70">产品类型</th>
				<th data-options="field:'sap',width:70">品牌SAP码</th>
				<th data-options="field:'ppmc',width:70">品牌名称</th>
				<th data-options="field:'wlzbm',width:70">物料组编码</th>
				<th data-options="field:'wlzmc',width:80">物料组名称</th>
				<th data-options="field:'flbs',width:80">返利标识</th>
				<th data-options="field:'ggcc',width:160">规格尺寸</th>
			</tr>
		</thead>
	</table>
</div>
<!-- Excel导入 -->
<div id="jdywlExcelDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:150px; padding:10px;" 
	buttons="#jdywlExcelDlg-buttons" >
	<form id="jdywlExcelForm"  method="post" enctype="multipart/form-data">
	   选择文件：　<input id="jdywlExcelFile" name="jdywlExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
	</form> 
</div>
<div id="jdywlExcelDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadjdywlExcel()">导入数据</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#jdywlExcelDlg').dialog('close')">取消</a>
</div>

<script>

	function getJdywlSelectionsIds() {
		var jdywl = $("#jdywl");
		var sels = jdywl.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
	
	//上传Excel数据
	function addJdywlExcel() {
		$('#jdywlExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
		$('#jdywlExcelForm').form('clear');
	}
	
	function uploadjdywlExcel(){   
		 //得到上传文件的全路径
		 var fileName= $('#jdywlExcelFile').filebox('getValue');
		//进行基本校验
		 if(fileName==""){   
			$.messager.alert('提示','请选择上传文件！','info'); 
		 }else{
			 //对文件格式进行校验
			 var d1=/\.[^\.]+$/.exec(fileName); 
			 if(d1==".xlsx"){
				 var formData=new FormData(document.getElementById("jdywlExcelForm"));
				 $.ajax({  
			      	url: "${pageContext.request.contextPath}/jdywl/uploadPostExcel",  
			      	type:"post",
					data:formData,
					//告诉jQuery不要去处理发送的数据
					processData:false,
					//告诉jQuery不要去设置Content-Type请求头,因为表单已经设置了multipart/form-data
					contentType:false,
					beforeSend: function () {
						    $.messager.progress({ 
						       title: '提示', 
						       msg: '正在删除上传的编码，请稍候……', 
						       text: '' 
						    });
						 },
						 complete: function () {
						       $.messager.progress('close');
					},
					success:function(data) {
						if(data.status ==200){
							$("#jdywlExcelDlg").dialog("close");
							$.messager.alert('提示',data.msg,'info',function(){
		                    	$("#jdywl").datagrid("load");
		                    });
						}else{
							$.messager.alert("警告", data.msg, 'warning');
							return;
						}
					}
				    });
		    }else{
		        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
		    	$('#jdywlExcelFile').filebox('setValue',''); 
		    }
		 } 

	 }
	
	
	function doFindWL(){
		$('#jdywl').datagrid('load',{
			jdycpmc: $('#jdycpmc').val(),
		});
	}
	
	
	function getJdywl(){
		var jdycpmclike=$('#jdycpmclike').val();
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
			 data: {jdycpmclike:jdycpmclike},
		     url: "${pageContext.request.contextPath}"+"/addJdywl",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '获取中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#jdywl").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	
	//删除
	function delJdywl(){
		var ids = getJdywlSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个物料进行删除!','info');
			return;
		}
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
			 data: {ids:ids},
		     url: "${pageContext.request.contextPath}"+"/delJdywl",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '删除中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#jdywl").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	
	//删除
// 	function delJdywl() {
// 		var ids = getJdywlSelectionsIds();
// 		if (ids.length == 0) {
// 			$.messager.alert('提示', '至少选择一个物料进行删除!','info');
// 			return;
// 		}
// 		$.messager.confirm('确认', '确定删除选中的物料吗？', function(r) {
// 			if (r) {
// 				var params = {
// 					"ids" : ids
// 				};
// 				$.post("${pageContext.request.contextPath}/delJdywl", params,
// 						function(data) {
// 							if (data.status == 200) {
// 			                    $.messager.alert("提示", data.msg,'info',function(){
// 			                    	 $("#sapBmList").datagrid("load");
// 			                    });
// 							} else {
// 								$.messager.alert('提示', data.msg,'info');
// 							}
// 						});
// 			}
// 		});

// 	}

</script>
