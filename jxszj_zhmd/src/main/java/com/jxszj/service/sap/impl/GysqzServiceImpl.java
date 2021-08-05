package com.jxszj.service.sap.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.Constants.Configuration;
import org.apache.axis2.databinding.types.Token;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapGysqzTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapGysqzTb;
import com.jxszj.pojo.sap.SapGysqzTbExample;
import com.jxszj.pojo.sap.SapGysqzTbExample.Criteria;
import com.jxszj.service.sap.IGysqzService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.ObjectUtils;
import com.jxszj.utils.ServiceStub;
import com.jxszj.utils.ServiceStub.AccountingBusinessTransactionTypeCode;
import com.jxszj.utils.ServiceStub.AccountingDocumentTypeCode;
import com.jxszj.utils.ServiceStub.Amount;
import com.jxszj.utils.ServiceStub.BusinessDocumentMessageHeader;
import com.jxszj.utils.ServiceStub.BusinessDocumentMessageID;
import com.jxszj.utils.ServiceStub.BusinessPartnerInternalID;
import com.jxszj.utils.ServiceStub.BusinessTransactionDocumentItemID;
import com.jxszj.utils.ServiceStub.BusinessTransactionDocumentTypeCode;
import com.jxszj.utils.ServiceStub.CompanyCodeID;
import com.jxszj.utils.ServiceStub.CreatedByUser;
import com.jxszj.utils.ServiceStub.CurrencyCode;
import com.jxszj.utils.ServiceStub.Date;
import com.jxszj.utils.ServiceStub.DebitCreditCode;
import com.jxszj.utils.ServiceStub.DocumentHeaderText;
import com.jxszj.utils.ServiceStub.DocumentItemText;
import com.jxszj.utils.ServiceStub.GLOBAL_DateTime;
import com.jxszj.utils.ServiceStub.JournalEntryBulkCreateRequest;
import com.jxszj.utils.ServiceStub.JournalEntryCreateConfirmationMessage;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestBulkMessage;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntry;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryCreditorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPaymentCreditor;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class GysqzServiceImpl implements IGysqzService {
	
	@Autowired
	private SapGysqzTbMapper sapGysqzTbMapper;

	@Override
	public int uploadGysqzExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		DecimalFormat df =new DecimalFormat("#0.00");
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			
			List<SapGysqzTb> list=new ArrayList<SapGysqzTb>();
			for (int i = 1; i < listob.size(); i++) {
				SapGysqzTb sapGysqzTb=new SapGysqzTb();
				sapGysqzTb.setZz(ObjectUtils.getString(listob.get(i).get(0)));
				sapGysqzTb.setJfjzm(ObjectUtils.getString(listob.get(i).get(1)));
				sapGysqzTb.setJfkm(ObjectUtils.getString(listob.get(i).get(2)));
				sapGysqzTb.setJfzzbs(ObjectUtils.getString(listob.get(i).get(3)));
				sapGysqzTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
				sapGysqzTb.setDfjzm(ObjectUtils.getString(listob.get(i).get(5)));
				sapGysqzTb.setGys(ObjectUtils.getString(listob.get(i).get(6)));
				sapGysqzTb.setDfzzbs(ObjectUtils.getString(listob.get(i).get(7)));
				sapGysqzTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(8))));
				sapGysqzTb.setCjr(ObjectUtils.getString(listob.get(i).get(9)));
				sapGysqzTb.setDate(DateUtils.getNowDate());
				sapGysqzTb.setPzrq(ObjectUtils.getString(listob.get(i).get(10)));
				sapGysqzTb.setGzrq(ObjectUtils.getString(listob.get(i).get(11)));
				sapGysqzTb.setHtext(ObjectUtils.getString(listob.get(i).get(12)));
				sapGysqzTb.setText(ObjectUtils.getString(listob.get(i).get(13)));
				list.add(sapGysqzTb);
			}
			
			List<SapGysqzTb> lists=new ArrayList<SapGysqzTb>();
			for (int i = 0; i < list.size(); i++) {
				lists.add(sendSAPPost(list.get(i)));
			}
			num=sapGysqzTbMapper.insertByBatch(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	@Override
	public void sendPost(List<Integer> list) {
		SapGysqzTbExample example = new SapGysqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		List<SapGysqzTb> sapKhqzTb=sapGysqzTbMapper.selectByExample(example);
		for (int i = 0; i < sapKhqzTb.size(); i++) {
			sapGysqzTbMapper.updateByPrimaryKey(sendSAPPost(sapKhqzTb.get(i)));
		}
		
	}
	
	
	
	//同步到SAP过账（含清账）
	public SapGysqzTb sendSAPPost(SapGysqzTb sapGysqzTb){
		try {
			ServiceStub stub = new ServiceStub();
			Authenticator auth = new Authenticator();
			auth.setUsername("JDYUSER");
			auth.setPassword("HfjTP>TgfqQMtHEghZbUxmgeNvrffXw9CoBnATjl");
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
			stub._getServiceClient().getOptions().setProperty(Configuration.CHARACTER_SET_ENCODING, "GBK");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			stub._getServiceClient().getOptions().setProperty(Configuration.HTTP_METHOD, HTTPConstants.HTTP_METHOD_POST);
			
			
			JournalEntryBulkCreateRequest journalEntryBulkCreateRequest=new JournalEntryBulkCreateRequest(); //最外层节点
			JournalEntryCreateRequestBulkMessage journalEntryCreateRequestBulkMessage=new JournalEntryCreateRequestBulkMessage();//包含头部MessageHeader和JournalEntryCreateRequest主体
			BusinessDocumentMessageHeader businessDocumentMessageHeader=new BusinessDocumentMessageHeader();//头部MessageHeader中的ID和CreationDateTime
			//组装头部MessageHeader中的ID
			BusinessDocumentMessageID businessDocumentMessageIDHeader=new BusinessDocumentMessageID();
			Token tokenHeader=new Token();
			tokenHeader.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDHeader.setBusinessDocumentMessageIDContent(tokenHeader);
			businessDocumentMessageHeader.setID(businessDocumentMessageIDHeader);
			//组装头部MessageHeader中的CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeHeader=new GLOBAL_DateTime();
			gLOBAL_DateTimeHeader.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeader.setCreationDateTime(gLOBAL_DateTimeHeader);
			//添加头部MessageHeader信息
			journalEntryCreateRequestBulkMessage.setMessageHeader(businessDocumentMessageHeader);
			
			//组装JournalEntryCreateRequest主体数据
			JournalEntryCreateRequestMessage journalEntryCreateRequestMessage=new JournalEntryCreateRequestMessage();
			//添加JournalEntryCreateRequest主体的头部---------
			BusinessDocumentMessageHeader businessDocumentMessageHeaderEntry=new BusinessDocumentMessageHeader();
			//添加JournalEntryCreateRequest主体的头部ID
			BusinessDocumentMessageID businessDocumentMessageIDEntry=new BusinessDocumentMessageID();
			Token tokenEntry=new Token();
			tokenEntry.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDEntry.setBusinessDocumentMessageIDContent(tokenEntry);
			businessDocumentMessageHeaderEntry.setID(businessDocumentMessageIDEntry);
			//添加JournalEntryCreateRequest主体的头部CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeEntry=new GLOBAL_DateTime();
			gLOBAL_DateTimeEntry.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeaderEntry.setCreationDateTime(gLOBAL_DateTimeEntry);
			journalEntryCreateRequestMessage.setMessageHeader(businessDocumentMessageHeaderEntry);
			
			//添加JournalEntryCreateRequest主体的内容-----------
			JournalEntryCreateRequestJournalEntry journalEntryCreateRequestJournalEntry=new JournalEntryCreateRequestJournalEntry();
			//开始组装主体数据
			BusinessTransactionDocumentTypeCode businessTransactionDocumentTypeCode=new BusinessTransactionDocumentTypeCode();
			Token token1=new Token();
			token1.setValue("BKPFF");
			businessTransactionDocumentTypeCode.setBusinessTransactionDocumentTypeCode(token1);
			journalEntryCreateRequestJournalEntry.setOriginalReferenceDocumentType(businessTransactionDocumentTypeCode);
			
//			ReferenceDocumentID referenceDocumentID=new ReferenceDocumentID();
//			Token token2=new Token();
//			token2.setValue("00001");
//			referenceDocumentID.setReferenceDocumentID(token2);
//			journalEntryCreateRequestJournalEntry.setOriginalReferenceDocument(referenceDocumentID);
			
			OriginalReferenceDocumentLogicalSystem originalReferenceDocumentLogicalSystem=new OriginalReferenceDocumentLogicalSystem();
			Token token3=new Token();
			token3.setValue("0M2S511");
			originalReferenceDocumentLogicalSystem.setOriginalReferenceDocumentLogicalSystem(token3);
			journalEntryCreateRequestJournalEntry.setOriginalReferenceDocumentLogicalSystem(originalReferenceDocumentLogicalSystem);
			
			AccountingBusinessTransactionTypeCode accountingBusinessTransactionTypeCode=new AccountingBusinessTransactionTypeCode();
			Token token4=new Token();
			token4.setValue("RFBU");
			accountingBusinessTransactionTypeCode.setAccountingBusinessTransactionTypeCode(token4);
			journalEntryCreateRequestJournalEntry.setBusinessTransactionType(accountingBusinessTransactionTypeCode);
			
			AccountingDocumentTypeCode accountingDocumentTypeCode=new AccountingDocumentTypeCode();
			Token token5=new Token();
			token5.setValue("SA");
			accountingDocumentTypeCode.setAccountingDocumentTypeCode(token5);
			journalEntryCreateRequestJournalEntry.setAccountingDocumentType(accountingDocumentTypeCode);
			
//			DocumentReferenceID documentReferenceID=new DocumentReferenceID();
//			Token token6=new Token();
//			token6.setValue(data.getString("_widget_1548037673456"));
//			documentReferenceID.setDocumentReferenceID(token6);
//			journalEntryCreateRequestJournalEntry.setDocumentReferenceID(documentReferenceID);
			
//			DocumentHeaderText documentHeaderText=new DocumentHeaderText();
//			documentHeaderText.setDocumentHeaderText(data.getString("_widget_1587439548589"));
//			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(documentHeaderText);
			
			CreatedByUser createdByUser=new CreatedByUser();
			Token token7=new Token();
			token7.setValue(sapGysqzTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue(sapGysqzTb.getZz());
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapGysqzTb.getPzrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapGysqzTb.getGzrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			DocumentHeaderText param=new DocumentHeaderText();
			param.setDocumentHeaderText(sapGysqzTb.getHtext());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(param);
			
			//债务人
			JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem1=new JournalEntryCreateRequestJournalEntryCreditorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token9=new Token();
			token9.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token9);
			journalEntryCreateRequestJournalEntryCreditorItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			String jfkm="";
			if(sapGysqzTb.getJfkm().contains("(")){
				jfkm=sapGysqzTb.getJfkm().substring(0, sapGysqzTb.getJfkm().indexOf("("));
			}else if(sapGysqzTb.getJfkm().contains("（")){
				jfkm=sapGysqzTb.getJfkm().substring(0, sapGysqzTb.getJfkm().indexOf("（"));
			}else{
				jfkm=sapGysqzTb.getJfkm();
			}
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token10=new Token();
			token10.setValue(jfkm.trim());
			businessPartnerInternalID1.setBusinessPartnerInternalID(token10);
			journalEntryCreateRequestJournalEntryCreditorItem1.setCreditor(businessPartnerInternalID1);
			
			Amount amountDebtorItem=new Amount();
			amountDebtorItem.setAmountContent(new BigDecimal(Double.parseDouble(sapGysqzTb.getJfje().trim())));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token11=new Token();
			token11.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token11);
			amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryCreditorItem1.setAmountInTransactionCurrency(amountDebtorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token12=new Token();
			token12.setValue("S");
			debitCreditCodeDebtorItem1.setDebitCreditCode(token12);
			journalEntryCreateRequestJournalEntryCreditorItem1.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			if(!"".equals(sapGysqzTb.getJfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
    			SpecialGLCode specialGLCode=new SpecialGLCode();
    			Token token13=new Token();
    			token13.setValue(sapGysqzTb.getJfzzbs().trim());
    			specialGLCode.setSpecialGLCode(token13);
    			journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
    			journalEntryCreateRequestJournalEntryCreditorItem1.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
			}
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapGysqzTb.getText());
			journalEntryCreateRequestJournalEntryCreditorItem1.setDocumentItemText(itemParam1);
			
			
			//
			JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem2=new JournalEntryCreateRequestJournalEntryCreditorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token14=new Token();
			token14.setValue("2");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token14);
			journalEntryCreateRequestJournalEntryCreditorItem2.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			String gys="";
			if(sapGysqzTb.getGys().contains("(")){
				gys=sapGysqzTb.getGys().substring(0, sapGysqzTb.getGys().indexOf("("));
			}else if(sapGysqzTb.getGys().contains("（")){
				gys=sapGysqzTb.getGys().substring(0, sapGysqzTb.getGys().indexOf("（"));
			}else{
				gys=sapGysqzTb.getGys();
			}
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token15=new Token();
			token15.setValue(gys.trim());
			businessPartnerInternalID2.setBusinessPartnerInternalID(token15);
			journalEntryCreateRequestJournalEntryCreditorItem2.setCreditor(businessPartnerInternalID2);
			
			Amount amountCreditorItem=new Amount();
			amountCreditorItem.setAmountContent(new BigDecimal(-Double.parseDouble(sapGysqzTb.getDfje().trim())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token16=new Token();
			token16.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token16);
			amountCreditorItem.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryCreditorItem2.setAmountInTransactionCurrency(amountCreditorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token17=new Token();
			token17.setValue("H");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token17);
			journalEntryCreateRequestJournalEntryCreditorItem2.setDebitCreditCode(debitCreditCodeDebtorItem2);
			
			//总账标识
			if(!"".equals(sapGysqzTb.getDfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
				SpecialGLCode specialGLCode=new SpecialGLCode();
				Token token18=new Token();
				token18.setValue(sapGysqzTb.getDfzzbs().trim());
				specialGLCode.setSpecialGLCode(token18);
				journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
				journalEntryCreateRequestJournalEntryCreditorItem2.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
			}
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapGysqzTb.getText());
			journalEntryCreateRequestJournalEntryCreditorItem2.setDocumentItemText(itemParam2);
			
			
			
			JournalEntryCreateRequestJournalEntryCreditorItem[] journalEntryCreateRequestJournalEntryCreditorItems={journalEntryCreateRequestJournalEntryCreditorItem1,journalEntryCreateRequestJournalEntryCreditorItem2};
			journalEntryCreateRequestJournalEntry.setCreditorItem(journalEntryCreateRequestJournalEntryCreditorItems);
			
			
			journalEntryCreateRequestMessage.setJournalEntry(journalEntryCreateRequestJournalEntry);
			
			//将JournalEntryCreateRequest主体添加到数组
			JournalEntryCreateRequestMessage[] journalEntryCreateRequestMessages={journalEntryCreateRequestMessage};
			//添加JournalEntryCreateRequest主体
			journalEntryCreateRequestBulkMessage.setJournalEntryCreateRequest(journalEntryCreateRequestMessages);
			
			
			
			journalEntryBulkCreateRequest.setJournalEntryBulkCreateRequest(journalEntryCreateRequestBulkMessage);
			
			String documentNo="";
			Set<String> set=new HashSet<String>();
			LogItem[] logItem=null;
			//调用并输出        
			JournalEntryCreateConfirmationMessage[] journalEntryCreateConfirmationMessages=stub.journalEntryCreateRequestConfirmation_In(journalEntryBulkCreateRequest).getJournalEntryBulkCreateConfirmation().getJournalEntryCreateConfirmation();
			for (int i = 0; i < journalEntryCreateConfirmationMessages.length; i++) {
				documentNo=journalEntryCreateConfirmationMessages[i].getJournalEntryCreateConfirmation().getAccountingDocument().toString();
				logItem=journalEntryCreateConfirmationMessages[i].getLog().getItem();
			}
			if("0000000000".equals(documentNo)){
				for (int j = 0; j < logItem.length; j++) {
					if(!logItem[j].getNote().getLogItemNote().contains("Error")){
						set.add(logItem[j].getNote().getLogItemNote());
					}
				}
				sapGysqzTb.setPzbm("");
				sapGysqzTb.setStatus("n");
				sapGysqzTb.setMsg(set.toString());
			}else{
				sapGysqzTb.setPzbm(documentNo);
				sapGysqzTb.setStatus("y");
				sapGysqzTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapGysqzTb;
	}


	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String khqzStatus) {
		SapGysqzTbExample example = new SapGysqzTbExample();
		if(khqzStatus!=null && !"".equals(khqzStatus)){
			Criteria criteria=example.createCriteria();
			criteria.andStatusEqualTo(khqzStatus);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapGysqzTb> item = sapGysqzTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapGysqzTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapGysqzTbExample example = new SapGysqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapGysqzTbMapper.deleteByExample(example);
		return num;
	}

}
