<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">

	<div id="ydmb-toolbar">
		<div class="wu-toolbar-search">
<!-- 			<label>品牌：</label> -->
<%-- 			<input class="easyui-combobox" id="hk_ppbm" style="width: 180px;" data-options=" --%>
<%-- 				url:'${pageContext.request.contextPath}/ydmb/getPp', --%>
<!-- 				method:'get', -->
<!-- 				valueField:'ppbm', -->
<!-- 				textField:'ppbm', -->
<!-- 				multiple:true, -->
<%-- 				panelHeight:'auto' "> --%>
<!-- 			<label>督导：</label><input id="ydmb_dd" class="easyui-textbox" style="width: 120px"> -->
<!--             <a href="#" class="easyui-linkbutton"  plain="false" data-options="iconCls:'icon-arrow-down'" onclick="wyfee.exportExcelYdmbTemp()">下载模板数据</a> -->
<!-- 			<a href="#" class="easyui-linkbutton"  plain="false" data-options="iconCls:'icon-arrow-up'" onclick="uploadYdmbData()">上传模板数据</a> -->
			<a href="#" class="easyui-linkbutton"  plain="false" data-options="iconCls:'icon-add'" onclick="addYdmbData()">获取本月目标</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="linkbutton" plain="false" data-options="iconCls:'icon-arrow-refresh'" onclick="addKdsqToXstc()">1.同步目标</a><!-- DJ4-开店申请（门店信息）同步到 DM1-销售提成_(自动) -->
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-arrow-refresh'" onclick="addSkdToXstc()">2.同步回款</a><!-- DF1-销售收款 同步到 DM1-销售提成_(自动) -->
        </div>
	</div>
    <div id="center" data-options="region:'center'">
		<table class="easyui-datagrid" id="ydmb" toolbar="#ydmb-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/ydmb/getYdmb',method:'get',pageSize:50,pageList: [50, 100, 500]">
			<thead>
				<tr>
					<th data-options="field:'jxsmc',width:320">经销商名称</th>
					<th data-options="field:'jxsbm',width:180">经销商编码</th>
					<th data-options="field:'dd',width:80">督导</th>
					<th data-options="field:'yyzt',width:80">运营状态</th>
					<th data-options="field:'bymbhk',width:100">本月目标回款</th>
					<th data-options="field:'drsj',width:100">导入日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
 <!-- Excel导入 -->
<div id="ydmbExcelDlg" class="easyui-dialog" data-options="closed:true,modal:true,iconCls:'icon-save'" style="width:400px; height:150px; padding:10px;" 
	buttons="#ydmbExcelDlg-buttons" >
	<form id="ydmbExcelForm"  method="post" enctype="multipart/form-data">
	   选择文件：　<input id="ydmbExcelFile" name="ydmbExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
	</form> 
</div>
<div id="ydmbExcelDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadYdmbExcel()">导入数据</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#ydmbExcelDlg').dialog('close')">取消</a>
</div>
<script>

//上传Excel数据
function addydmbExcel() {
	$('#ydmbExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
	$('#ydmbExcelForm').form('clear');
}

	function doFindydmbList(){
		var ydmbStatus= $('#ydmbStatus').combobox('getValue');
		$('#ydmb').datagrid('load',{
			ydmbStatus: ydmbStatus
		});
	}
	
	//删除
	function ydmbRemove() {
		var ids = getydmbSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一条数据进行删除!','info');
			return;
		}
		$.messager.confirm('确认', '确定删除选中的数据吗？', function(r) {
			if (r) {
				var params = {
					"ids" : ids
				};
				$.post("${pageContext.request.contextPath}/ydmb/delydmb", params,
						function(data) {
							if (data.status == 200) {
			                    $.messager.alert("提示", data.msg,'info',function(){
			                    	$("#ydmb").datagrid("load");
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
			exportExcelYdmbTemp:function(){
				var hk_ppbm= $('#hk_ppbm').combobox('getValues').toString();
				var ydmb_dd= $('#ydmb_dd').val();
				var url =  ctx + "/ydmb/ydmbExport?ppbm="+hk_ppbm+"&dd="+ydmb_dd;
				window.location.href = url;  
		}
		}
		})();
		
		//上传Excel数据
		function uploadYdmbData() {
			$('#ydmbExcelDlg').dialog('open').dialog('setTitle', '导入Excel');
			$('#ydmbExcelForm').form('clear');
		}
			
		
		function uploadYdmbExcel(){   
			 //得到上传文件的全路径
			 var fileName= $('#ydmbExcelFile').filebox('getValue');
			//进行基本校验
			 if(fileName==""){   
				$.messager.alert('提示','请选择上传文件！','info'); 
			 }else{
				 //对文件格式进行校验
				 var d1=/\.[^\.]+$/.exec(fileName); 
				 if(d1==".xlsx"){
					 var formData=new FormData(document.getElementById("ydmbExcelForm"));
					 $.ajax({  
				      	url: "${pageContext.request.contextPath}/ydmb/uploadYdmbExcel",   
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
								$("#ydmbExcelDlg").dialog("close");
								$.messager.alert('提示',data.msg,'info',function(){
									$("#ydmb").datagrid("load");
								});
							}else{
								$.messager.alert("警告", data.msg, 'warning');
							}
						}
					    });
			    }else{
			        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
			    	$('#ydmbExcelFile').filebox('setValue',''); 
			    }
			 } 

		 }
		
		
		
		function addKdsqToXstc(){
			var yhm=$('#yhm').html().trim();
			if(yhm=='root'){
				$.messager.alert('提示',"该用户没有此操作权限！",'info');
				return;
			}
			$.messager.confirm('确认', "同步目标前，将删除简道云中M1-销售提成_(自动)本月的数据", function(r) {
				if (r) {
					$.ajax({  
						 type: 'GET',  
					     dataType : 'json', 
					     url: "${pageContext.request.contextPath}/jxszj/xstc/kdsqSendXstc",   
						 beforeSend: function () {
						    $.messager.progress({ 
						       title: '提示', 
						       msg: '正在将本月目标同步到M1-销售提成_(自动)，请稍候……', 
						       text: '' 
						    });
						 },
						 complete: function () {
						       $.messager.progress('close');
						 },
					     success: function(data){   
					     	if(data.status == 200){
					     		$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert('提示',data.msg,'info');
							}
					     }  
					  });
				}
			}); 
		}
		
		
		function addSkdToXstc(){
			var yhm=$('#yhm').html().trim();
			if(yhm=='root'){
				$.messager.alert('提示',"该用户没有此操作权限！",'info');
				return;
			}
			$.messager.confirm('确认', "同步回款前，请确保目标已同步！", function(r) {
				if (r) {
					$.ajax({  
						 type: 'GET',  
					     dataType : 'json', 
					     url: "${pageContext.request.contextPath}/jxszj/xstc/skdSendXstc",   
						 beforeSend: function () {
						    $.messager.progress({ 
						       title: '提示', 
						       msg: '正在将  DF1-销售收款单  同步到M1-销售提成_(自动)，请稍候……', 
						       text: '' 
						    });
						 },
						 complete: function () {
						       $.messager.progress('close');
						 },
					     success: function(data){   
					     	if(data.status == 200){
					     		$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert('提示',data.msg,'info');
							}
					     }  
					  });
				}
			}); 
		}
		
		
		function addYdmbData(){
			$.ajax({  
				 contentType:"application/json;charset=utf-8",
				 type: 'GET',  
				 url: "${pageContext.request.contextPath}/ydmb/addYdmbData",   
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '正在获取本月目标，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			    	if(data.status == 200){
				     	$("#ydmb").datagrid("load");
					}
			     }  
			  });
		}

</script>
