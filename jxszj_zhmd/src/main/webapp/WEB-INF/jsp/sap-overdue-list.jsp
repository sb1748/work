<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="overdue-toolbar">
		<div class="wu-toolbar-button">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="getUpdate()" plain="true">计算超期</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="wyfee.exportExcel()" plain="true">导出数据</a>
<!-- 			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="getPlan()" plain="true">计算超期</a> -->
		</div>
		<div class="wu-toolbar-search">
			<label>销售凭证：</label><input id="xspz" class="easyui-textbox" style="width: 120px"> 
           	<label>产品名称：</label><input id="cpmc" class="easyui-textbox" style="width: 120px"> 
           	<label>品牌名称：</label>
           	<select class="easyui-combobox" id="cq_ppmc" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
                <option value="PerDormire">PerDormire</option>
                <option value="MySide">MySide</option>
                <option value="Hemingway">Hemingway</option>
            </select>
            <label>客户名称：</label><input id="cq_khmc" class="easyui-textbox" style="width: 120px"> 
            <label>物料组名称：</label><input id="cq_wlzmc" class="easyui-textbox" style="width: 120px"> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFind()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="overdue" toolbar="#overdue-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/getOverdue',method:'get',pageSize:50,pageList: [50, 100, 500]">
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
				<th data-options="field:'yjjhrq',width:80,formatter:ReportForm.formatDate">预计交货日期</th>
				<th data-options="field:'dhsl',width:60">订货数量</th>
				<th data-options="field:'ljrksl',width:80">累计入库数量</th>
				<th data-options="field:'gdkcs',width:80">跟单库存数</th>
				<th data-options="field:'kcs',width:80">库存数(剔除跟单)</th>
				<th data-options="field:'scrkrq',width:80,formatter:ReportForm.formatDate">生产入库日期</th>
				<th data-options="field:'qtrkrq',width:80,formatter:ReportForm.formatDate">齐套入库日期</th>
				<th data-options="field:'cqts',width:80">生产超期天数</th>
				<th data-options="field:'thcqts',width:80">提货超期天数</th>
			</tr>
		</thead>
	</table>
	</div>

<script>

	function getOverdueSelectionsIds() {
		var overdue = $("#overdue");
		var sels = overdue.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].material);
		}
		ids = ids.join(",");
		return ids;
	}
	
	
	function doFind(){
		var cq_ppmc= $('#cq_ppmc').combobox('getValue');
		$('#overdue').datagrid('load',{
			xspz: $('#xspz').val(),
			cpmc: $('#cpmc').val(),
			khmc: $('#cq_khmc').val(),
			wlzmc: $('#cq_wlzmc').val(),
			ppmc:cq_ppmc
		});
	}
	
	function sendToJDY(){
		var ids = getOverdueSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个物料!','info');
			return;
		}
		var itemList = $("#overdue");
		var sels = itemList.datagrid("getSelections");
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'POST',  
			 traditional:true,
		     dataType : 'json', 
		     data: JSON.stringify(sels),
		     url: "${pageContext.request.contextPath}"+"/doSendPriceToJDY",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '价格同步中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#overdue").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	
	function getUpdate(){
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
		     url: "${pageContext.request.contextPath}"+"/addOverdue",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '计算中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#overdue").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	
	
	   var ctx = "${pageContext.request.contextPath}";
		var wyfee = (function(){
			return{
			exportExcel:function(){
				var xspz= $('#xspz').val();
				var cpmc=$('#cpmc').val();
				var khmc= $('#cq_khmc').val();
				var wlzmc=$('#cq_wlzmc').val();
				var ppmc= $('#cq_ppmc').combobox('getValue');
				var url =  ctx + "/sccq/export?xspz="+xspz+"&cpmc="+cpmc+"&khmc="+khmc+"&wlzmc="+wlzmc+"&ppmc="+ppmc;
				window.location.href = url;  
		}
		}
	})();

		// 	function getPlan(){
// 		$.ajax({  
// 			 contentType:"application/json;charset=utf-8",
// 			 type: 'GET',  
// 		     url: "${pageContext.request.contextPath}"+"/getPlan",   
// 			 beforeSend: function () {
// 			    $.messager.progress({ 
// 			       title: '提示', 
// 			       msg: '计算中，请稍候……', 
// 			       text: '' 
// 			    });
// 			 },
// 			 complete: function () {
// 			       $.messager.progress('close');
// 			 },
// 		     success: function(data){   
// 		     	if(data.status ==200){
// 						$.messager.alert('提示',data.msg,'info',function(){
// 							$("#overdue").datagrid("load");
// 					});
// 				}else{
// 					$.messager.alert('提示',data.msg,'info');
// 				}
// 		     }  
// 		  });
// 	}

</script>
