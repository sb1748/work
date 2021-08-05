<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料管理系统</title>
<link rel="stylesheet" type="text/css" href="easyui/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="css/wu.css" />
<link rel="stylesheet" type="text/css" href="css/icon.css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="easyui/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<style>
</style>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true">
    	<div class="wu-header-left">
        	<h1>当前登录用户:<lable id="yhm">${name}</lable></h1>
        </div>
        <div class="wu-header-right">
<%--         	<p><strong class="easyui-tooltip" title="2条未读消息">${username }</strong>，欢迎您！</p> --%>
<!--             <p><a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="#">安全退出</a></p> -->
			<p id="date"></p>
            <p id="time"></p>
        </div>
        
    </div>
    
    <div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false" > 
    		
            <div title="SAP" data-options="iconCls:'icon-application-double'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
<!--                 	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="sap-wllb-list" iframe="0">小类编码</a></li> -->
<!--                 	<li iconCls="icon-application-view-columns"><a href="javascript:void(0)" data-icon="icon-application-view-columns" data-link="sap-wlbm-view" iframe="0">小类编码查看</a></li> -->
<!--                 	<li iconCls="icon-application-edit"><a href="javascript:void(0)" data-icon="icon-application-edit" data-link="sap-data-list" iframe="0">数据值管理</a></li> -->
<!--                 	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-bm-list" iframe="0">物料编码管理</a></li> -->
<!--                 	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-bm-remove-list" iframe="0">物料编码还原</a></li> -->
<!--                 	<li iconCls="icon-application-view-columns"><a href="javascript:void(0)" data-icon="icon-application-view-columns" data-link="sap-bm-view" iframe="0">物料编码查看</a></li> -->
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-jdywl-list" iframe="0">简道云物料管理</a></li>
<!--                 	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-overdueorderlist-list" iframe="0">未清销售订单</a></li> -->
<!--                 	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-overdue-list" iframe="0">超期报表</a></li> -->
<!--                 	<li iconCls="icon-application-view-columns"><a href="javascript:void(0)" data-icon="icon-application-view-columns" data-link="sap-overdueproduction-list" iframe="0">生产入库明细</a></li> -->
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-journalEntryPost-list" iframe="0">费用计提</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-zzfyjt-list" iframe="0">总账费用计提</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-dskqz-list" iframe="0">代收款清账</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-khqz-list" iframe="0">客户清账</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-gysqz-list" iframe="0">供应商清账</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-zjxshk-list" iframe="0">转经销商货款</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-fwf-list" iframe="0">经销商补服务费</a></li>
                	<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-hdf-list" iframe="0">活动费扣款</a></li>
					<li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-ydmb-list" iframe="0">本月回款管理</a></li>
					<li iconCls="icon-application-view-columns"><a href="javascript:void(0)" data-icon="icon-application-view-columns" data-link="sap-pdsc-view" iframe="0">排单生产查看</a></li>
					<li iconCls="icon-application-view-columns"><a href="javascript:void(0)" data-icon="icon-application-view-columns" data-link="sap-price-list" iframe="0">价格主数据管理</a></li>
                </ul>
            </div>
            <div title="逾期管理" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
					  <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="sap-condition-cq-list" iframe="0">物料描述条件</a></li>
               		  <li iconCls="icon-application-form-edit"><a href="javascript:void(0)" data-icon="icon-application-form-edit" data-link="sap-xsdd-cq-list" iframe="0">订单明细</a></li>
                </ul>
            </div>
             <div title="钉钉日志管理" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
					  <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="sap-DailyUser-list" iframe="0">日志管理</a></li>
                </ul>
            </div>
        </div>
    </div>	


	<!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="closable:false,iconCls:'icon-tip',cls:'pd3'">
			    <div id="p" class="easyui-panel" title="经销商之家" style="width:100%;">
					<div class="easyui-tabs">
						<div title="物料主数据" style="padding:10px">
							<form id="wl" method="post">
								<table >
									<tr>
										<td>销售组织:</td>
										<td><input id="wl_ProductSalesOrg" class="easyui-textbox" data-options="required:true"  style="width: 180px;"></input></td>
									</tr>
									<tr>
										<td>分销渠道:</td>
										<td><input id="wl_ProductDistributionChnl" data-options="required:true" class="easyui-textbox" style="width: 180px;"></input></td>
									</tr>
									<tr>
										<td>物料编码:</td>
										<td><input id="wl_Product" data-options="multiline:true,required:true"  style="width: 180px;height:80px" class="easyui-textbox" ></input></td>
									</tr>
								</table>
							</form>
							<div style="padding:5px">
						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="wlsubmitForm()">同步更新</a>
						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="wl.exportExcel()">导出数据</a>
						    </div>
						</div>
						<div title="客户主数据" style="padding:10px">
							<form id="kh" method="post">
								<table >
									<tr>
										<td>销售组织:</td>
										<td><input id="kh_SalesOrganization" data-options="required:true" class="easyui-textbox" style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>分销渠道:</td>
										<td><input id="kh_DistributionChannel" class="easyui-textbox" data-options="required:true"  style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>产品组:</td>
										<td><input id="kh_Division" data-options="required:true" class="easyui-textbox" style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>客户编码:</td>
										<td><input id="customer" class="easyui-textbox"  data-options="required:true"  style="width: 140px;"></input></td>
									</tr>
								</table>
							</form>
							<div style="padding:5px">
						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="khsubmitForm()">同步更新</a>
						    </div>
						</div>
<!-- 						<div title="价格主数据" style="padding:10px"> -->
<!-- 							<form id="prices" method="post"> -->
<!-- 								<table > -->
<!-- 									<tr> -->
<!-- 										<td>物料编码:</td> -->
<!-- 									</tr> -->
<!-- 									<tr> -->
<!-- 										<td><input id="recordNumber" class="easyui-textbox"  data-options="multiline:true"  style="width: 340px;height:100px"></input></td> -->
<!-- 									</tr> -->
<!-- 								</table> -->
<!-- 							</form> -->
<!-- 							<div style="padding:5px"> -->
<!-- 						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getPrice()">同步更新</a> -->
<!-- <!-- 						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="delPrice()">同步删除</a> --> 
<!-- <!-- 						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="delFailPrice()">失效价格清除</a> --> 
<!-- 						    </div> -->
<!-- 						</div> -->
						
						<div title="物料修改" style="padding:10px">
 							<div style="width:400px; padding:10px;" >
								<form id="wlExcelForm"  method="post" enctype="multipart/form-data">
									   选择文件：　<input id="wlExcelFile" name="wlExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
								</form> 
							</div>
							<div>
						    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadWlmsExcel()">修改物料描述</a>
						    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadWltjExcel()">修改物料体积</a>
								<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWlExcel()">取消</a>
						    </div>
						</div>
						
						<div title="开票管理" style="padding:10px">
 							<div style="width:400px; padding:10px;" >
								<form id="billExcelForm"  method="post" enctype="multipart/form-data">
									   选择文件：　<input id="billExcelFile" name="billExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'">
								</form> 
							</div>
							<div>
						    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadBillExcel()">导入数据</a>
								<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeBillExcel()">取消</a>
						    </div>
						</div>
					</div>
			    </div>
			    
			    
			    <div class="easyui-panel" title="简道云华生集团" style="width:100%">
					<div class="easyui-tabs">
						<div title="物料主数据" style="padding:10px">
							<form id="hswl" method="post">
							<table >
								<tr>
									<td>评估范围:</td>
									<td><input id="valuationArea" data-options="required:true" class="easyui-textbox" style="width: 180px;"></input></td>
								</tr>
								<tr>
									<td>物料编码:</td>
									<td><input id="hswl_Product" data-options="multiline:true,required:true" class="easyui-textbox" style="width: 180px;height:80px"></input></td>
								</tr>
							</table>
						</form>
						<div style="padding:5px">
					    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="hswlsubmitForm()">同步更新</a>
					    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="hsSendBatchWl()">批量更新</a>
					    </div>
						</div>
						<div title="供应商主数据" style="padding:10px">
							<form id="hsgys" method="post">
								<table >
									<tr>
										<td>公司代码:</td>
										<td><input id="CompanyCode" data-options="required:true" class="easyui-textbox" style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>供应商编码:</td>
										<td><input id="Supplier" class="easyui-textbox" data-options="required:true"  style="width: 140px;"></input></td>
									</tr>
								</table>
							</form>
							<div style="padding:5px">
						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="gysSubmitForm()">同步更新</a>
						    </div>
						</div>
						<div title="客户主数据" style="padding:10px">
							<form id="hskh" method="post">
								<table >
									<tr>
										<td>销售组织:</td>
										<td><input id="hs_SalesOrganization" data-options="required:true" class="easyui-textbox" style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>分销渠道:</td>
										<td><input id="hs_DistributionChannel" class="easyui-textbox" data-options="required:true"  style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>产品组:</td>
										<td><input id="hs_Division" data-options="required:true" class="easyui-textbox" style="width: 140px;"></input></td>
									</tr>
									<tr>
										<td>客户编码:</td>
										<td><input id="hs_customer" class="easyui-textbox"  data-options="required:true"  style="width: 140px;"></input></td>
									</tr>
								</table>
							</form>
							<div style="padding:5px">
						    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="hs_khsubmitForm()">同步更新</a>
						    </div>
						</div>
					</div>
			    </div>
			    
			    <div class="easyui-panel" title="MES" style="width:100%">
					<div class="easyui-tabs">
						<div title="生产派工单" style="padding:10px">
<!-- 							<div style="width:400px; padding:10px;" > -->
<!-- 								<form id="mesQrcodeExcelForm"  method="post" enctype="multipart/form-data"> -->
<!-- 									   选择文件：　<input id="mesQrcodeExcelFile" name="mesQrcodeExcelFile" class="easyui-filebox" buttonText="选择文件" style="width:260px" data-options="prompt:'请选择文件...'"> -->
<!-- 								</form>  -->
<!-- 							</div> -->
							<div>
<!-- 						    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadMesQrcodeExcel()">导入数据</a> -->
<!-- 								<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeMesQrcodeExcel()">取消</a> -->
								<a href="#" class="easyui-linkbutton" id="linkbutton" plain="false" data-options="iconCls:'icon-arrow-up'" onclick="addMesQrcodeTb()">同步生产派工单</a>
						    </div>
						</div>
					</div>
			    </div>
			    
            </div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="wu-footer" data-options="region:'south',border:true,split:true">
<!--     	&copy; 2019 深圳华生 All Rights Reserved -->
    </div>
    <!-- end of footer -->  
	<script type="text/javascript">
	/*  $(function(){
	    	$('#menu').tree({
	    		onClick: function(node){
	    			if($('#menu').tree("isLeaf",node.target)){
	    				var tabs = $("#wu-tabs");
	    				var tab = tabs.tabs("getTab",node.text);
	    				if(tab){
	    					tabs.tabs("select",node.text);
	    				}else{
	    					tabs.tabs('add',{
	    					    title:node.text,
	    					    href: node.attributes.url,
	    					    closable:true,
	    					    bodyCls:"content"
	    					});
	    				}
	    			}
	    		}
	    	});
	    }); */
	    
	    
	    function wlsubmitForm(){
	    	var wl_Product=$('#wl_Product').val();
	    	var wl_ProductSalesOrg=$('#wl_ProductSalesOrg').val();
	    	var wl_ProductDistributionChnl=$('#wl_ProductDistributionChnl').val();
	        $("#wl").form("submit", {
	        	url: "${pageContext.request.contextPath}"+"/sendWl?wl_Product="+wl_Product+"&wl_ProductSalesOrg="+wl_ProductSalesOrg+"&wl_ProductDistributionChnl="+wl_ProductDistributionChnl,
	            onSubmit : function() {
	                return $(this).form("validate");
	            },
	            success : function(result) {
	                var result = eval('(' + result + ')');
	                if (result.status == 200) {
	                	$.messager.alert("提示", result.msg,'info',function(){
	                		$("#wl_Product").textbox("setValue", "");
	                    });
	                } else {
	                    $.messager.alert("提示", result.msg, 'error');
	                    return;
	                }
	            }
	        });
		}
	    
		function sendBatchWl(){
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/sendBatchWl",   
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '物料同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
							$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
		
		//物料描述修改
		function uploadWlmsExcel(){   
			 //得到上传文件的全路径
			 var fileName= $('#wlExcelFile').filebox('getValue');
			//进行基本校验
			 if(fileName==""){   
				$.messager.alert('提示','请选择上传文件！','info'); 
			 }else{
				 //对文件格式进行校验
				 var d1=/\.[^\.]+$/.exec(fileName); 
				 if(d1==".xlsx"){
					 var formData=new FormData(document.getElementById("wlExcelForm"));
					 $.ajax({  
				      	url: "${pageContext.request.contextPath}/materialChange/uploadWlmsExcel",  
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
								$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert("警告", data.msg, 'warning');
							}
							$('#wlExcelFile').filebox('setValue',''); 
						}
					    });
			    }else{
			        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
			    	$('#wlExcelFile').filebox('setValue',''); 
			    }
			 } 

		 }
		
		//物料体积修改
		function uploadWltjExcel(){   
			 //得到上传文件的全路径
			 var fileName= $('#wlExcelFile').filebox('getValue');
			//进行基本校验
			 if(fileName==""){   
				$.messager.alert('提示','请选择上传文件！','info'); 
			 }else{
				 //对文件格式进行校验
				 var d1=/\.[^\.]+$/.exec(fileName); 
				 if(d1==".xlsx"){
					 var formData=new FormData(document.getElementById("wlExcelForm"));
					 $.ajax({  
				      	url: "${pageContext.request.contextPath}/materialChange/uploadWltjExcel",  
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
								$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert("警告", data.msg, 'warning');
							}
							$('#wlExcelFile').filebox('setValue',''); 
						}
					    });
			    }else{
			        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
			    	$('#wlExcelFile').filebox('setValue',''); 
			    }
			 } 

		 }
		
		function closeWlExcel(){
			$('#wlExcelFile').filebox('setValue',''); 
		}
		
		
		//开票管理
		function uploadBillExcel(){   
			 //得到上传文件的全路径
			 var fileName= $('#billExcelFile').filebox('getValue');
			//进行基本校验
			 if(fileName==""){   
				$.messager.alert('提示','请选择上传文件！','info'); 
			 }else{
				 //对文件格式进行校验
				 var d1=/\.[^\.]+$/.exec(fileName); 
				 if(d1==".xlsx"){
					 var formData=new FormData(document.getElementById("billExcelForm"));
					 $.ajax({  
				      	url: "${pageContext.request.contextPath}/bill/uploadBillExcel",  
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
								$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert("警告", data.msg, 'warning');
							}
							$('#billExcelFile').filebox('setValue',''); 
						}
					    });
			    }else{
			        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
			    	$('#billExcelFile').filebox('setValue',''); 
			    }
			 } 

		 }
		
		function closeBillExcel(){
			$('#billExcelFile').filebox('setValue',''); 
		}
		
		
		//生产流程单
		function uploadMesQrcodeExcel(){   
			 //得到上传文件的全路径
			 var fileName= $('#mesQrcodeExcelFile').filebox('getValue');
			//进行基本校验
			 if(fileName==""){   
				$.messager.alert('提示','请选择上传文件！','info'); 
			 }else{
				 //对文件格式进行校验
				 var d1=/\.[^\.]+$/.exec(fileName); 
				 if(d1==".xlsx"){
					 var formData=new FormData(document.getElementById("mesQrcodeExcelForm"));
					 $.ajax({  
				      	url: "${pageContext.request.contextPath}/uploadMesQrcodeExcel",  
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
								$.messager.alert('提示',data.msg,'info');
							}else{
								$.messager.alert("警告", data.msg, 'warning');
							}
							$('#billExcelFile').filebox('setValue',''); 
						}
					    });
			    }else{
			        $.messager.alert('提示','请选择xlsx格式文件！','info'); 
			    	$('#mesQrcodeExcelFile').filebox('setValue',''); 
			    }
			 } 

		 }
		
		function closeBillExcel(){
			$('#billExcelFile').filebox('setValue',''); 
		}
		
		//华生物料主数据
		function hswlsubmitForm(){
	    	var hswl_Product=$('#hswl_Product').val();
	    	var valuationArea=$('#valuationArea').val();
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/hs/sendWl?hswl_Product="+hswl_Product+"&valuationArea="+valuationArea,
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '物料同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
		function hsSendBatchWl(){
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/hs/sendBatchWl",   
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '物料同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
							$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
	    
	    function khsubmitForm(){
	    	var kh_SalesOrganization=$('#kh_SalesOrganization').val();
	    	var kh_DistributionChannel=$('#kh_DistributionChannel').val();
	    	var kh_Division=$('#kh_Division').val();
	    	var customer=$('#customer').val();
	        $("#kh").form("submit", {
	        	url: "${pageContext.request.contextPath}"+"/sendKh?kh_SalesOrganization="+kh_SalesOrganization+"&kh_DistributionChannel="+kh_DistributionChannel+"&kh_Division="+kh_Division+"&customer="+customer+"",
	            onSubmit : function() {
	                return $(this).form("validate");
	            },
	            success : function(result) {
	                var result = eval('(' + result + ')');
	                if (result.status == 200) {
	                	$.messager.alert("提示", result.msg,'info',function(){
	                		$("#customer").textbox("setValue", "");
	                    });
	                } else {
	                    $.messager.alert("提示", result.msg, 'error');
	                    return;
	                }
	            }
	        });
		}
	    
	    //华生客户主数据
	    function hs_khsubmitForm(){
	    	var hs_SalesOrganization=$('#hs_SalesOrganization').val();
	    	var hs_DistributionChannel=$('#hs_DistributionChannel').val();
	    	var hs_Division=$('#hs_Division').val();
	    	var hs_customer=$('#hs_customer').val();
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/hs/sendKh?hs_SalesOrganization="+hs_SalesOrganization+"&hs_DistributionChannel="+hs_DistributionChannel+"&hs_Division="+hs_Division+"&hs_customer="+hs_customer+"",
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '物料同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
			     		$("#hs_customer").textbox("setValue", "");
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
	    
	    //华生供应商主数据
	    function gysSubmitForm(){
	    	var Supplier=$('#Supplier').val();
	    	var CompanyCode=$('#CompanyCode').val();
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/addGYS?Supplier="+Supplier+"&CompanyCode="+CompanyCode+"",
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '供应商同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
	    
	    
	    function getPrice(){
	    	var recordNumber=$('#recordNumber').val();
	    	$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/getPrice?recordNumber="+recordNumber,
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '价格同步更新中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
	    
	    function delPrice(){
	    	var recordNumber=$('#recordNumber').val();
	    	if(recordNumber==''){
	    		$.messager.alert('提示','不能为空','info');
	    		return;
	    	}
	    	$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/delPrice?recordNumber="+recordNumber,
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '价格同步删除中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}
	    
	    function delFailPrice(){
	    	$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}"+"/delFailPrice",
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '失效价格清理中，请稍候……', 
				       text: '' 
				    });
				 },
				 complete: function () {
				       $.messager.progress('close');
				 },
			     success: function(data){   
			     	if(data.status ==200){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
			     }  
			  });
		}

	    
	    $(function(){
			$('.wu-side-tree a').bind("click",function(){
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe')==1?true:false;
				addTab(title,url,iconCls,iframe);
			});	
		})
		
		/**
		* Name 添加菜单选项
		* Param title 名称
		* Param href 链接
		* Param iconCls 图标样式
		* Param iframe 链接跳转方式（true为iframe，false为href）
		*/	
		function addTab(title, href, iconCls, iframe){
			var tabPanel = $('#wu-tabs');
			if(!tabPanel.tabs('exists',title)){
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
				if(iframe){
					tabPanel.tabs('add',{
						title:title,
						content:content,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
				else{
					tabPanel.tabs('add',{
						title:title,
						href:href,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
			}
			else
			{
				tabPanel.tabs('select',title);
			}
		}
		
		
		//导出物料
		   var ctx = "${pageContext.request.contextPath}";
			var wl = (function(){
				return{
				exportExcel:function(){
					var url =  ctx + "/wl/export";
					window.location.href = url;  
			}
			}
		})();
			
			
		function addMesQrcodeTb(){
			$.ajax({  
				 type: 'GET',  
			     dataType : 'json', 
			     url: "${pageContext.request.contextPath}/addMesQrcodeTb",   
				 beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: '正在添加更新MES生产流程单数据...', 
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
		
			
		window: onload = function() {
			setInterval(function() {
				var date = new Date();
				var year = date.getFullYear(); //获取当前年份
				var mon = date.getMonth() + 1; //获取当前月份
				var da = date.getDate(); //获取当前日
				var day = date.getDay(); //获取当前星期几
				var h = date.getHours(); //获取小时
				var m = date.getMinutes(); //获取分钟
				var s = date.getSeconds(); //获取秒
				var d1 = document.getElementById('date');
				var d2 = document.getElementById('time');
				// 当分秒不足10时，前面加0
				if (m < 10) {
					var mm = "0" + m;
				} else {
					mm = m;
				}
				if (s < 10) {
					var ss = "0" + s;
				} else {
					ss = s;
				}
				var week;
				if (day === 0) {
				    week = "天";
				} else if (day === 1) {
					week = "一";
				} else if (day === 2) {
					week = "二";
				} else if (day === 3) {
					week = "三";
				} else if (day === 4) {
					week = "四";
				} else if (day === 5) {
					week = "五";
				} else if (day === 6) {
					week = "六";
				}
				d1.innerHTML =  year + "年" + mon + '月' + da + '日' + " "+ '星期' + week + '<br/>' ;
				d2.innerHTML = "当前时间："+ h + ':' + mm + ':' + ss;
			}, 1000);
			
			$("#valuationArea").textbox("setValue", "B001,C001");
			
		}
		
	</script>
</body>
</html>