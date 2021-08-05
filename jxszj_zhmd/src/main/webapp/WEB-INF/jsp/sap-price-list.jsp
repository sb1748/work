<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">


	<div id="sapPrice-toolbar">
		<div class="wu-toolbar-button">
			<label>物料编码：</label><input id="priceWlbm" class="easyui-textbox" style="width: 180px"> 
			<label>创建人：</label>
			<select class="easyui-combobox" id="SAPPriceCjr" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
            	<option value="CB9980000000">无</option>
                <option value="CB9980000033">易瑾</option>
                <option value="CB9980000035">吴灿</option>
                <option value="CB9980000012">黄凤娇</option>
                <option value="CB9980000081">沈梦婷</option>
                <option value="CB9980000230">黄苑怡</option>
                <option value="CB9980000112">刘勇</option>
            </select>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-down'" onclick="getPrice()" plain="true">获取销售价</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-arrow-up'" onclick="sendToJDY()" plain="true">更新到简道云</a>
		</div>
		<div class="wu-toolbar-search">
           	<label>物料编码：</label><input id="priceMaterial" class="easyui-textbox" style="width: 180px"> 
           	<label>创建人：</label>
			<select class="easyui-combobox" id="priceCjr" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
            	<option value="无">无</option>
                <option value="易瑾">易瑾</option>
                <option value="吴灿">吴灿</option>
                <option value="黄凤娇">黄凤娇</option>
                <option value="沈梦婷">沈梦婷</option>
                <option value="黄苑怡">黄苑怡</option>
                <option value="刘勇">刘勇</option>
            </select>
            <label>同步状态：</label>
			<select class="easyui-combobox" id="priceTbzt" panelHeight="auto" style="width:100px">
            	<option value="">全部</option>
                <option value="已同步">已同步</option>
                <option value="未同步">未同步</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doFind()">查询</a>
        </div>
	</div>
    <div id="center" data-options="region:'center'">
			<table class="easyui-datagrid" id="sapPrice" toolbar="#sapPrice-toolbar"
		data-options="rownumbers:true,singleSelect:false,multiSort:true,pagination:true,fit:true,fitColumns:false,url:'${pageContext.request.contextPath}/price/getPriceList',method:'get',pageSize:50,pageList: [50, 100, 500]">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',width:80">条件记录编号</th>
				<th data-options="field:'cjr',width:60">创建人</th>
				<th data-options="field:'xszz',width:60">销售组织</th>
				<th data-options="field:'fxqd',width:60">分销渠道</th>
				<th data-options="field:'tjlx',width:60">条件类型</th>
				<th data-options="field:'khbm',width:60">客户编码</th>
				<th data-options="field:'khjgz',width:70">客户价格组</th>
				<th data-options="field:'wlbm',width:160">物料编码</th>
				<th data-options="field:'wlms',width:220">物料描述</th>
				<th data-options="field:'dw',width:40">单位</th>
				<th data-options="field:'bz',width:40">币种</th>
				<th data-options="field:'djzk',width:80">单价/折扣率</th>
				<th data-options="field:'cjrq',width:80">创建日期</th>
				<th data-options="field:'yxksrq',width:80">有效期自</th>
				<th data-options="field:'yxjsrq',width:80">有效期至</th>
				<th data-options="field:'sxzt',width:80,styler: function(value,row,index){
					if(value =='失效'){
						return 'color:red;';
					}else if(value=='生效'){
						return 'color:green;';
					}else if(value=='未生效'){
						return 'color:#FF00FF;';
					}
				}">失效状态</th>
				<th data-options="field:'tbzt',width:80,styler: function(value,row,index){
					if(value =='未同步'){
						return 'color:red;';
					}else if(value=='已同步'){
						return 'color:green;';
					}
				}">同步状态</th>
			</tr>
		</thead>
	</table>
	</div>

<script>

	function getSapPriceSelectionsIds() {
		var sapPrice = $("#sapPrice");
		var sels = sapPrice.datagrid("getSelections");
		var ids = [];
		for ( var i in sels) {
			ids.push(sels[i].id);
		}
		ids = ids.join(",");
		return ids;
	}
	
	
	function doFind(){
		$('#sapPrice').datagrid('load',{
			priceMaterial: $('#priceMaterial').val(),
			priceCjr: $('#priceCjr').combobox('getValue'),
			priceTbzt: $('#priceTbzt').combobox('getValue')
		});
	}
	
	function sendToJDY(){
		var ids = getSapPriceSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '至少选择一个物料!','info');
			return;
		}
		var sapPrice = $("#sapPrice");
		var sels = sapPrice.datagrid("getSelections");
		for ( var i in sels) {
			if(sels[i].tbzt=='已同步'){
				$.messager.alert('提示', '已同步的价格不能重复同步！','info');
				return;
			}else if(sels[i].sxzt=='未生效'){
				$.messager.alert('提示', '未生效的价格不能同步！','info');
				return;
			}
		}
		
		var itemList = $("#sapPrice");
		var sels = itemList.datagrid("getSelections");
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'POST',  
			 traditional:true,
		     dataType : 'json', 
		     data: JSON.stringify(sels),
		     url: "${pageContext.request.contextPath}/price/sendPriceToJDY",   
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
							$("#sapPrice").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}
	
	function getPrice(){
		priceWlbm= $('#priceWlbm').val();
		sapPriceCjr= $('#SAPPriceCjr').combobox('getValue');
		if(priceWlbm!="" && sapPriceCjr!=""){
			$.messager.alert('提示', '只能选择一个条件进行查询!','info');
			return;
		}
		$.ajax({  
			 contentType:"application/json;charset=utf-8",
			 type: 'GET',  
			 data: {priceWlbm:priceWlbm,sapPriceCjr:sapPriceCjr},
		     url: "${pageContext.request.contextPath}/price/getSAPSalesPrice",   
			 beforeSend: function () {
			    $.messager.progress({ 
			       title: '提示', 
			       msg: '获取价格中，请稍候……', 
			       text: '' 
			    });
			 },
			 complete: function () {
			       $.messager.progress('close');
			 },
		     success: function(data){   
		     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info',function(){
							$("#sapPrice").datagrid("load");
					});
				}else{
					$.messager.alert('提示',data.msg,'info');
				}
		     }  
		  });
	}

</script>
