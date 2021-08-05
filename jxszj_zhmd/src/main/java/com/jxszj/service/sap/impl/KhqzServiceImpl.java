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
import com.jxszj.mapper.sap.SapKhqzTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapKhqzTb;
import com.jxszj.pojo.sap.SapKhqzTbExample;
import com.jxszj.pojo.sap.SapKhqzTbExample.Criteria;
import com.jxszj.service.sap.IKhqzService;
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
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDebtorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPayment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class KhqzServiceImpl implements IKhqzService {
	
	@Autowired
	private SapKhqzTbMapper sapKhqzTbMapper;

	@Override
	public int uploadKhqzExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		DecimalFormat df =new DecimalFormat("#0.00");
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			
			List<SapKhqzTb> list=new ArrayList<SapKhqzTb>();
			for (int i = 1; i < listob.size(); i++) {
				SapKhqzTb sapKhqzTb=new SapKhqzTb();
				sapKhqzTb.setZz(ObjectUtils.getString(listob.get(i).get(0)));
				sapKhqzTb.setJfjzm(ObjectUtils.getString(listob.get(i).get(1)));
				sapKhqzTb.setJfkm(ObjectUtils.getString(listob.get(i).get(2)));
				sapKhqzTb.setJfzzbs(ObjectUtils.getString(listob.get(i).get(3)));
				sapKhqzTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
				sapKhqzTb.setDfjzm(ObjectUtils.getString(listob.get(i).get(5)));
				sapKhqzTb.setGys(ObjectUtils.getString(listob.get(i).get(6)));
				sapKhqzTb.setDfzzbs(ObjectUtils.getString(listob.get(i).get(7)));
				sapKhqzTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(8))));
				sapKhqzTb.setCjr(ObjectUtils.getString(listob.get(i).get(9)));
				sapKhqzTb.setDate(DateUtils.getNowDate());
				sapKhqzTb.setPzrq(ObjectUtils.getString(listob.get(i).get(10)));
				sapKhqzTb.setGzrq(ObjectUtils.getString(listob.get(i).get(11)));
				sapKhqzTb.setHtext(ObjectUtils.getString(listob.get(i).get(12)));
				sapKhqzTb.setText(ObjectUtils.getString(listob.get(i).get(13)));
				list.add(sapKhqzTb);
			}
			
			List<SapKhqzTb> lists=new ArrayList<SapKhqzTb>();
			for (int i = 0; i < list.size(); i++) {
				lists.add(sendSAPPost(list.get(i)));
			}
			num=sapKhqzTbMapper.insertByBatch(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	@Override
	public void sendPost(List<Integer> list) {
		SapKhqzTbExample example = new SapKhqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		List<SapKhqzTb> sapKhqzTb=sapKhqzTbMapper.selectByExample(example);
		for (int i = 0; i < sapKhqzTb.size(); i++) {
			sapKhqzTbMapper.updateByPrimaryKey(sendSAPPost(sapKhqzTb.get(i)));
		}
		
	}
	
	
	
	//同步到SAP过账（含清账）
	public SapKhqzTb sendSAPPost(SapKhqzTb sapKhqzTb){
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
			token7.setValue(sapKhqzTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue(sapKhqzTb.getZz());
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapKhqzTb.getPzrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapKhqzTb.getGzrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			DocumentHeaderText param=new DocumentHeaderText();
			param.setDocumentHeaderText(sapKhqzTb.getHtext());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(param);
			
			//债务人
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem1=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token9=new Token();
			token9.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token9);
			journalEntryCreateRequestJournalEntryDebtorItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			String jfkm="";
			if(sapKhqzTb.getJfkm().contains("(")){
				jfkm=sapKhqzTb.getJfkm().substring(0, sapKhqzTb.getJfkm().indexOf("("));
			}else if(sapKhqzTb.getJfkm().contains("（")){
				jfkm=sapKhqzTb.getJfkm().substring(0, sapKhqzTb.getJfkm().indexOf("（"));
			}else{
				jfkm=sapKhqzTb.getJfkm();
			}
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token10=new Token();
			token10.setValue(jfkm.trim());
			businessPartnerInternalID1.setBusinessPartnerInternalID(token10);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebtor(businessPartnerInternalID1);
			
			Amount amountDebtorItem=new Amount();
			amountDebtorItem.setAmountContent(new BigDecimal(Double.parseDouble(sapKhqzTb.getJfje().trim())));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token11=new Token();
			token11.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token11);
			amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setAmountInTransactionCurrency(amountDebtorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token12=new Token();
			token12.setValue("S");
			debitCreditCodeDebtorItem1.setDebitCreditCode(token12);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			if(!"".equals(sapKhqzTb.getJfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment=new JournalEntryCreateRequestJournalEntryDownPayment();
    			SpecialGLCode specialGLCode=new SpecialGLCode();
    			Token token13=new Token();
    			token13.setValue(sapKhqzTb.getJfzzbs().trim());
    			specialGLCode.setSpecialGLCode(token13);
    			journalEntryCreateRequestJournalEntryDownPayment.setSpecialGLCode(specialGLCode);
    			journalEntryCreateRequestJournalEntryDebtorItem1.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment);
			}
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapKhqzTb.getText());
			journalEntryCreateRequestJournalEntryDebtorItem1.setDocumentItemText(itemParam1);
			
			//
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem2=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token14=new Token();
			token14.setValue("2");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token14);
			journalEntryCreateRequestJournalEntryDebtorItem2.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			String gys="";
			if(sapKhqzTb.getGys().contains("(")){
				gys=sapKhqzTb.getGys().substring(0, sapKhqzTb.getGys().indexOf("("));
			}else if(sapKhqzTb.getGys().contains("（")){
				gys=sapKhqzTb.getGys().substring(0, sapKhqzTb.getGys().indexOf("（"));
			}else{
				gys=sapKhqzTb.getGys();
			}
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token15=new Token();
			token15.setValue(gys.trim());
			businessPartnerInternalID2.setBusinessPartnerInternalID(token15);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebtor(businessPartnerInternalID2);
			
			Amount amountCreditorItem=new Amount();
			amountCreditorItem.setAmountContent(new BigDecimal(-Double.parseDouble(sapKhqzTb.getDfje().trim())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token16=new Token();
			token16.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token16);
			amountCreditorItem.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryDebtorItem2.setAmountInTransactionCurrency(amountCreditorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token17=new Token();
			token17.setValue("H");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token17);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebitCreditCode(debitCreditCodeDebtorItem2);
			
			//总账标识
			if(!"".equals(sapKhqzTb.getDfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment=new JournalEntryCreateRequestJournalEntryDownPayment();
				SpecialGLCode specialGLCode=new SpecialGLCode();
				Token token18=new Token();
				token18.setValue(sapKhqzTb.getDfzzbs().trim());
				specialGLCode.setSpecialGLCode(token18);
				journalEntryCreateRequestJournalEntryDownPayment.setSpecialGLCode(specialGLCode);
				journalEntryCreateRequestJournalEntryDebtorItem2.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment);
			}
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapKhqzTb.getText());
			journalEntryCreateRequestJournalEntryDebtorItem2.setDocumentItemText(itemParam2);
			
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem1,journalEntryCreateRequestJournalEntryDebtorItem2};
			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems);
			
			
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
				sapKhqzTb.setPzbm("");
				sapKhqzTb.setStatus("n");
				sapKhqzTb.setMsg(set.toString());
			}else{
				sapKhqzTb.setPzbm(documentNo);
				sapKhqzTb.setStatus("y");
				sapKhqzTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapKhqzTb;
	}


	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String khqzStatus) {
		SapKhqzTbExample example = new SapKhqzTbExample();
		if(khqzStatus!=null && !"".equals(khqzStatus)){
			Criteria criteria=example.createCriteria();
			criteria.andStatusEqualTo(khqzStatus);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapKhqzTb> item = sapKhqzTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapKhqzTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	
	@Override
	public int delete(List<Integer> list) {
		SapKhqzTbExample example = new SapKhqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapKhqzTbMapper.deleteByExample(example);
		return num;
	}


}
