package com.jxszj.controller.sap;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.Constants.Configuration;
import org.apache.axis2.databinding.types.Token;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.utils.billing.ServiceStub;
import com.jxszj.utils.billing.ServiceStub.BD_ActionControl;
import com.jxszj.utils.billing.ServiceStub.BD_ReferenceDocument;
import com.jxszj.utils.billing.ServiceStub.BDwReferenceRequest;
import com.jxszj.utils.billing.ServiceStub.BDwReferenceRequestMessage;
import com.jxszj.utils.billing.ServiceStub.BusinessDocumentMessageHeader;
import com.jxszj.utils.billing.ServiceStub.BusinessDocumentMessageID;
import com.jxszj.utils.billing.ServiceStub.BusinessSystemID;
import com.jxszj.utils.billing.ServiceStub.BusinessTransactionDocumentID;
import com.jxszj.utils.billing.ServiceStub.BusinessTransactionDocumentTypeCode;
import com.jxszj.utils.billing.ServiceStub.Date;
import com.jxszj.utils.billing.ServiceStub.GLOBAL_DateTime;
import com.jxszj.utils.billing.ServiceStub.Indicator;
import com.jxszj.utils.billing.ServiceStub.SDDocumentCategory;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/bill")
public class BillingController {
	
	
	@RequestMapping("/uploadBillExcel")
	@ResponseBody
	public MessageResult uploadWlExcel(@RequestParam("billExcelFile") MultipartFile billExcelFile,HttpServletRequest request, HttpServletResponse response){
		int number=0;
		try {
			if (billExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			InputStream in = null;
			List<List<Object>> listob = null;
			
			in = billExcelFile.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, billExcelFile.getOriginalFilename());
			for (int i = 1; i < listob.size(); i++) {
				//将Excel中的数据提出来
				updateMaterial(listob.get(i));
				number++;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(200,"开票完成，开票数量："+number);
	}
	
	
	public void updateMaterial(List<Object> list){
		ServiceStub stub;
		try {
			stub = new ServiceStub();
			Authenticator auth = new Authenticator();
			auth.setUsername("JDYUSER");
			auth.setPassword("HfjTP>TgfqQMtHEghZbUxmgeNvrffXw9CoBnATjl");
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
			stub._getServiceClient().getOptions().setProperty(Configuration.CHARACTER_SET_ENCODING, "GBK");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			stub._getServiceClient().getOptions().setProperty(Configuration.HTTP_METHOD, HTTPConstants.HTTP_METHOD_POST);
			BDwReferenceRequest bDwReferenceRequest=new BDwReferenceRequest();
			BDwReferenceRequestMessage message=new BDwReferenceRequestMessage();
			
			BusinessDocumentMessageHeader messageHeader=new BusinessDocumentMessageHeader();
			BusinessDocumentMessageID businessDocumentMessageID=new BusinessDocumentMessageID();
			Token token1=new Token();
			token1.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageID.setBusinessDocumentMessageIDContent(token1);
			messageHeader.setID(businessDocumentMessageID);
			GLOBAL_DateTime gLOBAL_DateTime=new GLOBAL_DateTime();
			gLOBAL_DateTime.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			messageHeader.setCreationDateTime(gLOBAL_DateTime);
			BusinessSystemID param = new BusinessSystemID();
			Token token5=new Token();
			token5.setValue("0M2S511");
			param.setBusinessSystemID(token5);
			messageHeader.setSenderBusinessSystemID(param);
			message.setMessageHeader(messageHeader);
			
			BD_ActionControl actionControl=new BD_ActionControl();
			Indicator indicator=new Indicator();
			indicator.setIndicator(Boolean.valueOf(list.get(4).toString()));
			actionControl.setAutomPostingToAcctgIsDisabled(indicator);
			
			Date date=new Date();
			date.setDate(list.get(3).toString());
			actionControl.setBillingDocumentDate(date);
			message.setActionControl(actionControl);
			
			
			BD_ReferenceDocument referenceDocument=new BD_ReferenceDocument();
			BusinessTransactionDocumentID businessTransactionDocumentID=new BusinessTransactionDocumentID();
			Token token2=new Token();
			token2.setValue(list.get(0).toString());
			businessTransactionDocumentID.setBusinessTransactionDocumentID(token2);
			referenceDocument.setReferenceDocument(businessTransactionDocumentID);
			
			SDDocumentCategory documentCategory=new SDDocumentCategory();
			Token token3=new Token();
			token3.setValue(list.get(1).toString());
			documentCategory.setSDDocumentCategory(token3);
			referenceDocument.setReferenceDocumentCategory(documentCategory);

			BusinessTransactionDocumentTypeCode businessTransactionDocumentTypeCode=new BusinessTransactionDocumentTypeCode();
			Token token4=new Token();
			token4.setValue(list.get(2).toString());
			businessTransactionDocumentTypeCode.setBusinessTransactionDocumentTypeCode(token4);
			referenceDocument.setBillingDocumentType(businessTransactionDocumentTypeCode);
			BD_ReferenceDocument[] referenceDocuments={referenceDocument};
			message.setReferenceDocument(referenceDocuments);
			
			bDwReferenceRequest.setBDwReferenceRequest(message);
			stub.bDwReferenceRequest_In(bDwReferenceRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getString(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
	
}
