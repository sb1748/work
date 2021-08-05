<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="sapBmView-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="bmView.exportExcel()" plain="true">导出数据</a>
		</div>
		<div class="wu-toolbar-search">
           	<label>新物料编码：</label><input id="new_wlbm_view" class="easyui-textbox" style="width: 120px"> 
           	<label>新物料名称：</label><input id="new_name_view" class="easyui-textbox" style="width: 150px"> 
           	<label>旧物料编码：</label><input id="old_wlbm_view" class="easyui-textbox" style="width: 120px"> 
           	<label>旧物料名称：</label><input id="old_name_view" class="easyui-textbox" style="width: 150px"> 
           	<label>是否需要创建BOM：</label>
           	 <select class="easyui-combobox" id="bom_view" panelHeight="auto" style="width:80px">
            	<option value="">全部</option>
                <option value="y">需要</option>
                <option value="n">不需要</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFindView()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sapBmView" toolbar="#sapBmView-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/sap/bm/getSapBmList',method:'get',pageSize:30">
		<thead>
			<tr>
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

<script>

function doFindView(){
	var bom_view= $('#bom_view').combobox('getValue');
	$('#sapBmView').datagrid('load',{
		newWlbm: $('#new_wlbm_view').val(),
		newName: $('#new_name_view').val(),
		oldWlbm: $('#old_wlbm_view').val(),
		oldName: $('#old_name_view').val(),
		bom:bom_view
	});
}

var ctx = "${pageContext.request.contextPath}";
	var bmView = (function(){
		return{
			exportExcel:function(){
			var url =  ctx + "/export";
			window.location.href = url;  
		}
	}
})();

</script>
