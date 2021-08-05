<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

	<div id="khqz-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="sendkhqz()" plain="true">同步</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up'" onclick="addkhqzExcel()" plain="true">上传数据</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="khqzRemove()" plain="true">删除</a>
		</div>
		<div class="wu-toolbar-search">
			<label>同步状态：</label>
			<select class="easyui-combobox" id="khqzStatus" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
                <option value="y">已同步</option>
                <option value="n">未同步</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindkhqzList()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
		<table class="easyui-datagrid" id="khqz" toolbar="#khqz-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/khqz/getSAPPostList',method:'get',pageSize:50,pageList: [50, 100, 500]">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'pzbm',width:85, styler: function(value,row,index){
					if(value !=''){
						return 'color:green;';
					}
				}">凭证编号</th>
					<th data-options="field:'zz',width:60">组织</th>
					<th data-options="field:'jfjzm',width:140">借方记账码</th>
					<th data-options="field:'jfkm',width:140">借方科目</th>
					<th data-options="field:'jfzzbs',width:80">借方总账标识</th>
					<th data-options="field:'jfje',width:70">借方金额</th>
					<th data-options="field:'dfjzm',width:140">贷方记账码</th>
					<th data-options="field:'gys',width:140">贷方科目</th>
					<th data-options="field:'dfzzbs',width:80">贷方总账标识</th>
					<th data-options="field:'dfje',width:60">贷方金额</th>
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
					<th data-options="field:'pzrq',width:80,formatter:ReportForm.formatDate">凭证日期</th>
					<th data-options="field:'gzrq',width:80,formatter:ReportForm.formatDate">过账日期</th>
					<th data-options="field:'cjr',width:80">创建人</th>
					<th data-options="field:'date',width:80,formatter:ReportForm.formatDate">创建日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- Excel导入 -->
<div id="khqzExcelDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:150px; padding:10px;" 
	buttons="#khqzExcelDlg-buttons" >
	<form id="khqzExcelForm"  method="post" enctype="multipart/form-data">
	   选择文件：　<input id="khqzExcelFile" name="khqzExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
	</form> 
</div>
<div id="khqzExcelDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadkhqzExcel()">导入数据</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#khqzExcelDlg').dialog('close')">取消</a>
</div>
<script>

function getkhqzSelectionsIds() {
	var khqz = $("#khqz");
	var sels = khqz.datagrid("getSelections");
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



//同步
function sendkhqz() {
	var khqz = $("#khqz");
	var sels = khqz.datagrid("getSelections");
	for ( var i in sels) {
		if(sels[i].status=='y'){
			$.messager.alert('提示', '已同步的数据不能重复同步!','info');
			return;
		}
	}
	
	var ids = getkhqzSelectionsIds();
	if (ids.length == 0) {
		$.messager.alert('提示', '至少选择一条数据同步!','info');
		return;
	}
	$.ajax({  
		 contentType:"application/json;charset=utf-8",
		 type: 'GET',  
		 data: {ids:ids},
	     url: "${pageContext.request.contextPath}"+"/khqz/sendKhqz",   
		 beforeSend: function () {
		    $.messager.progress({ 
		       title: '提示', 
		       msg: '同步中，请稍候……', 
		       text: '' 
		    });
		 },
		 complete: function () {
		       $.messager.progress('close');
		 },
	     success: function(data){   
	     	if(data.status ==200){
	     		$.messager.alert('提示',data.msg,'info',function(){
                	$("#khqz").datagrid("load");
                });
			}else{
				$.messager.alert('提示',data.msg,'info');
			}
	     }  
	  });

}


//上传Excel数据
function addkhqzExcel() {
	$('#khqzExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
	$('#khqzExcelForm').form('clear');
}

function uploadkhqzExcel(){   
	 //得到上传文件的全路径
	 var fileName= $('#khqzExcelFile').filebox('getValue');
	//进行基本校验
	 if(fileName==""){   
		$.messager.alert('提示','请选择上传文件！','info'); 
	 }else{
		 //对文件格式进行校验
		 var d1=/\.[^\.]+$/.exec(fileName); 
		 if(d1==".xlsx" || d1==".xls"){
			 var formData=new FormData(document.getElementById("khqzExcelForm"));
			 $.ajax({  
		      	url: "${pageContext.request.contextPath}/khqz/uploadPostExcel",  
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
						$("#khqzExcelDlg").dialog("close");
						$.messager.alert('提示',data.msg,'info',function(){
	                    	$("#khqz").datagrid("load");
	                    });
					}else{
						$.messager.alert("警告", data.msg, 'warning');
						return;
					}
				}
			    });
	    }else{
	        $.messager.alert('提示','请选择xlsx或xls格式文件！','info'); 
	    	$('#khqzExcelFile').filebox('setValue',''); 
	    }
	 } 

 }
	
	function doFindkhqzList(){
		var khqzStatus= $('#khqzStatus').combobox('getValue');
		$('#khqz').datagrid('load',{
			khqzStatus: khqzStatus
		});
	}
	
	//删除
	function khqzRemove() {
		var ids = getkhqzSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据进行删除!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/khqz/delKhqz", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	$("#khqz").datagrid("load");
			                    });
							} else {
								$.messager.alert('提示', data.msg,'info');
							}
						});
			}
		});

	}

</script>
