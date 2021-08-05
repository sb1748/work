<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="sap-overdueorderlist-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-refresh'" onclick="getUpdateOverdueorderlist()" plain="true">更新</a>
		</div>
		<div class="wu-toolbar-search">
			<label>销售凭证：</label><input id="overdueorderlist_xspz" class="easyui-textbox" style="width: 120px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindOverdueorderlist()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sap-overdueorderlist" toolbar="#sap-overdueorderlist-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getOverdueorderlist',method:'get',pageSize:50,pageList: [50, 100, 500]">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'xspz',width:80">销售凭证</th>
				<th data-options="field:'xspzh',width:70">销售凭证行</th>
				<th data-options="field:'kh',width:180">客户名称</th>
				<th data-options="field:'dhdh',width:120">订货单号</th>
				<th data-options="field:'cpbm',width:150">产品编码</th>
				<th data-options="field:'cpmc',width:230">产品名称</th>
				<th data-options="field:'wlz',width:80">物料组名称</th>
				<th data-options="field:'pp',width:80">品牌名称</th>
				<th data-options="field:'jhzq',width:60">交货周期</th>
				<th data-options="field:'xsdq',width:60">销售地区</th>
				<th data-options="field:'dhlx',width:60">订货类型</th>
				<th data-options="field:'gcgd',width:60">工厂跟单</th>
				<th data-options="field:'bz',width:180">备注</th>
				<th data-options="field:'dhrq',width:80,formatter:ReportForm.formatDate">订货日期</th>
				<th data-options="field:'dhsl',width:60">订货数量</th>
			</tr>
		</thead>
	</table>
	</div>

<script>
	
	function doFindOverdueorderlist(){
		$('#sap-overdueorderlist').datagrid('load',{
			xspz: $('#overdueorderlist_xspz').val(),
		});
	}
	
	function getUpdateOverdueorderlist(){
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
		     url: "${pageContext.request.contextPath}"+"/addOverdueorderlist",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '数据更新中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#sap-overdueorderlist").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	

</script>
