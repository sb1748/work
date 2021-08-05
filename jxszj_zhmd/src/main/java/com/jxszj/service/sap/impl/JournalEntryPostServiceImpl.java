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
import com.jxszj.mapper.sap.SapJournalentrypostTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapJournalentrypostTb;
import com.jxszj.pojo.sap.SapJournalentrypostTbExample;
import com.jxszj.pojo.sap.SapJournalentrypostTbExample.Criteria;
import com.jxszj.service.sap.IJournalEntryPostService;
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
import com.jxszj.utils.ServiceStub.ChartOfAccountsItemCode;
import com.jxszj.utils.ServiceStub.CompanyCodeID;
import com.jxszj.utils.ServiceStub.CostCentreID;
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
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItemAccountAssignment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class JournalEntryPostServiceImpl implements IJournalEntryPostService {
	
	@Autowired
	private SapJournalentrypostTbMapper sapJournalentrypostTbMapper;

	@Override
	public int uploadFyjtExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		DecimalFormat df =new DecimalFormat("#0.00");  
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			if(ObjectUtils.getString(listob.get(0).get(0)).equals("序号")){
				Set<String> number=new HashSet<String>();
				for (int i = 1; i < listob.size(); i++) {
					if("".equals(ObjectUtils.getString(listob.get(i).get(0)))){
						continue;
					}
					number.add(ObjectUtils.getString(listob.get(i).get(0)));
				}
				for(String str:number){
					List<List<SapJournalentrypostTb>> listobs = new ArrayList<List<SapJournalentrypostTb>>();
					List<SapJournalentrypostTb> list=null;
					for (int i = 1; i < listob.size(); i++) {
						if(str.equals(ObjectUtils.getString(listob.get(i).get(0)))){
							list=new ArrayList<SapJournalentrypostTb>();
							SapJournalentrypostTb sapJournalentrypostTb=new SapJournalentrypostTb();
							sapJournalentrypostTb.setZz(ObjectUtils.getString(listob.get(i).get(1)));
							sapJournalentrypostTb.setJfjzm(ObjectUtils.getString(listob.get(i).get(2)));
							sapJournalentrypostTb.setKm(ObjectUtils.getString(listob.get(i).get(3)));
							sapJournalentrypostTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
							sapJournalentrypostTb.setCbzx(ObjectUtils.getString(listob.get(i).get(5)));
							sapJournalentrypostTb.setDfjzm(ObjectUtils.getString(listob.get(i).get(6)));
							sapJournalentrypostTb.setGys(ObjectUtils.getString(listob.get(i).get(7)));
							sapJournalentrypostTb.setZzbs(ObjectUtils.getString(listob.get(i).get(8)));
							sapJournalentrypostTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(9))));
							sapJournalentrypostTb.setDate(DateUtils.getNowDate());
							sapJournalentrypostTb.setCjr(ObjectUtils.getString(listob.get(i).get(10)));
							sapJournalentrypostTb.setPzrq(ObjectUtils.getString(listob.get(i).get(11)));
							sapJournalentrypostTb.setGzrq(ObjectUtils.getString(listob.get(i).get(12)));
							sapJournalentrypostTb.setHtext(ObjectUtils.getString(listob.get(i).get(13)));
							sapJournalentrypostTb.setText(ObjectUtils.getString(listob.get(i).get(14)));
							list.add(sapJournalentrypostTb);
							listobs.add(list);
						}
						
					}
					List<String> lists=sendSAPPost(listobs);
					for (int i = 0; i < listobs.size(); i++) {
						listobs.get(i).get(0).setPzbh(lists.get(0));
						listobs.get(i).get(0).setStatus(lists.get(1));
						listobs.get(i).get(0).setMsg(lists.get(2));
						num+=sapJournalentrypostTbMapper.insertByBatch(listobs.get(i));
					}
					
				}
			}else{
				List<SapJournalentrypostTb> list=new ArrayList<SapJournalentrypostTb>();
				for (int i = 1; i < listob.size(); i++) {
					if(!ObjectUtils.getString(listob.get(i).get(0)).equals("")){
						SapJournalentrypostTb sapJournalentrypostTb=new SapJournalentrypostTb();
						sapJournalentrypostTb.setZz(ObjectUtils.getString(listob.get(i).get(0)));
						sapJournalentrypostTb.setJfjzm(ObjectUtils.getString(listob.get(i).get(1)));
						sapJournalentrypostTb.setKm(ObjectUtils.getString(listob.get(i).get(2)));
						sapJournalentrypostTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(3))));
						sapJournalentrypostTb.setCbzx(ObjectUtils.getString(listob.get(i).get(4)));
						sapJournalentrypostTb.setDfjzm(ObjectUtils.getString(listob.get(i).get(5)));
						sapJournalentrypostTb.setGys(ObjectUtils.getString(listob.get(i).get(6)));
						sapJournalentrypostTb.setZzbs(ObjectUtils.getString(listob.get(i).get(7)));
						sapJournalentrypostTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(8))));
						sapJournalentrypostTb.setDate(DateUtils.getNowDate());
						sapJournalentrypostTb.setCjr(ObjectUtils.getString(listob.get(i).get(9)));
						sapJournalentrypostTb.setPzrq(ObjectUtils.getString(listob.get(i).get(10)));
						sapJournalentrypostTb.setGzrq(ObjectUtils.getString(listob.get(i).get(11)));
						sapJournalentrypostTb.setHtext(ObjectUtils.getString(listob.get(i).get(12)));
						sapJournalentrypostTb.setText(ObjectUtils.getString(listob.get(i).get(13)));
						list.add(sapJournalentrypostTb);
					}
				}
				
				List<SapJournalentrypostTb> lists=new ArrayList<SapJournalentrypostTb>();
				for (int i = 0; i < list.size(); i++) {
					lists.add(sendSAPPost(list.get(i)));
				}
				num=sapJournalentrypostTbMapper.insertByBatch(lists);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	@Override
	public void sendPost(List<Integer> list) {
		SapJournalentrypostTbExample example = new SapJournalentrypostTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		List<SapJournalentrypostTb> sapJournalentrypostTbs=sapJournalentrypostTbMapper.selectByExample(example);
		for (int i = 0; i < sapJournalentrypostTbs.size(); i++) {
			sapJournalentrypostTbMapper.updateByPrimaryKey(sendSAPPost(sapJournalentrypostTbs.get(i)));
		}
		
	}
	
	
	//同步到SAP过账（含清账）
	public List<String> sendSAPPost(List<List<SapJournalentrypostTb>> listobs){
		String pzbh="";
		String status="";
		String msg="";
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
			token7.setValue(listobs.get(0).get(0).getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue(listobs.get(0).get(0).getZz());
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(listobs.get(0).get(0).getPzrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(listobs.get(0).get(0).getGzrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			DocumentHeaderText param=new DocumentHeaderText();
			param.setDocumentHeaderText(listobs.get(0).get(0).getHtext());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(param);
			
			
			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems=new JournalEntryCreateRequestJournalEntryItem[listobs.size()];
			for (int i = 0; i < listobs.size(); i++) {
				//借方
				JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
				BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
				Token token9=new Token();
				token9.setValue(i+"");
				businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token9);
				journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
				
				
				String km=listobs.get(i).get(0).getKm();
				ChartOfAccountsItemCode chartOfAccountsItemCode=new ChartOfAccountsItemCode();
				Token token10=new Token();
				token10.setValue(km.trim());
				chartOfAccountsItemCode.setChartOfAccountsItemCodeContent(token10);
				journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode);
				
				
				Amount amountItem=new Amount();
				amountItem.setAmountContent(new BigDecimal(listobs.get(i).get(0).getJfje().trim())); //借方金额
				CurrencyCode currencyCodeItem=new CurrencyCode();//币种
				Token token11=new Token();
				token11.setValue("CNY");
				currencyCodeItem.setCurrencyCode(token11);
				amountItem.setCurrencyCode(currencyCodeItem);
				journalEntryCreateRequestJournalEntryItem.setAmountInTransactionCurrency(amountItem);
				
				DebitCreditCode debitCreditCodeItem=new DebitCreditCode();
				Token token12=new Token();
				token12.setValue("S");
				debitCreditCodeItem.setDebitCreditCode(token12);
				journalEntryCreateRequestJournalEntryItem.setDebitCreditCode(debitCreditCodeItem);
				
				String cbzx=listobs.get(i).get(0).getCbzx();
				//成本中心
				JournalEntryCreateRequestJournalEntryItemAccountAssignment accountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
				CostCentreID costCentreID=new CostCentreID();
				Token costCentreIDToken=new Token();
				costCentreIDToken.setValue(cbzx.trim());
				costCentreID.setCostCentreID(costCentreIDToken);
				accountAssignment.setCostCenter(costCentreID);
				journalEntryCreateRequestJournalEntryItem.setAccountAssignment(accountAssignment);
				
				
				DocumentItemText itemParam=new DocumentItemText();
				itemParam.setDocumentItemText(listobs.get(i).get(0).getText());
				journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam);
				
				journalEntryCreateRequestJournalEntryItems[i]=journalEntryCreateRequestJournalEntryItem;
			}
			journalEntryCreateRequestJournalEntry.setItem(journalEntryCreateRequestJournalEntryItems);
			
			
			JournalEntryCreateRequestJournalEntryCreditorItem[] journalEntryCreateRequestJournalEntryCreditorItems=new JournalEntryCreateRequestJournalEntryCreditorItem[listobs.size()];
			for (int i = 0; i < listobs.size(); i++) {
				//贷方
				JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem=new JournalEntryCreateRequestJournalEntryCreditorItem();
				BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem=new BusinessTransactionDocumentItemID();
				Token token13=new Token();
				token13.setValue(i+"");
				businessTransactionDocumentItemIDDebtorItem.setBusinessTransactionDocumentItemID(token13);
				journalEntryCreateRequestJournalEntryCreditorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem);
				
				String gys=listobs.get(i).get(0).getGys();
				BusinessPartnerInternalID businessPartnerInternalID=new BusinessPartnerInternalID();
				Token token14=new Token();
				token14.setValue(gys.trim());
				businessPartnerInternalID.setBusinessPartnerInternalID(token14);
				journalEntryCreateRequestJournalEntryCreditorItem.setCreditor(businessPartnerInternalID);
				
				Amount amountDebtorItem=new Amount();
				amountDebtorItem.setAmountContent(new BigDecimal(-Double.parseDouble(listobs.get(i).get(0).getDfje().trim())));
				CurrencyCode currencyCodeDebtorItem=new CurrencyCode();
				Token token15=new Token();
				token15.setValue("CNY");
				currencyCodeDebtorItem.setCurrencyCode(token15);
				amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem);
				journalEntryCreateRequestJournalEntryCreditorItem.setAmountInTransactionCurrency(amountDebtorItem);
				
				DebitCreditCode debitCreditCodeDebtorItem=new DebitCreditCode();
				Token token16=new Token();
				token16.setValue("H");
				debitCreditCodeDebtorItem.setDebitCreditCode(token16);
				journalEntryCreateRequestJournalEntryCreditorItem.setDebitCreditCode(debitCreditCodeDebtorItem);
				
				DocumentItemText creditorParam=new DocumentItemText();
				creditorParam.setDocumentItemText(listobs.get(i).get(0).getText());
				journalEntryCreateRequestJournalEntryCreditorItem.setDocumentItemText(creditorParam);
				
				if(!"".equals(listobs.get(i).get(0).getZzbs())){
					JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
	    			SpecialGLCode specialGLCode=new SpecialGLCode();
	    			Token token18=new Token();
	    			token18.setValue("H");
	    			specialGLCode.setSpecialGLCode(token18);
	    			journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
	    			journalEntryCreateRequestJournalEntryCreditorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
				}
				
				journalEntryCreateRequestJournalEntryCreditorItems[i]=journalEntryCreateRequestJournalEntryCreditorItem;
			}
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
				pzbh="";
				status="n";
				msg=set.toString();
			}else{
				pzbh=documentNo;
				status="y";
				msg="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> list=new ArrayList<String>();
		list.add(pzbh);
		list.add(status);
		list.add(msg);
		return list;
	}
	
	
	//同步到SAP过账（含清账）
	public SapJournalentrypostTb sendSAPPost(SapJournalentrypostTb sapJournalentrypostTb){
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
			token7.setValue(sapJournalentrypostTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue(sapJournalentrypostTb.getZz());
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapJournalentrypostTb.getPzrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapJournalentrypostTb.getGzrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			DocumentHeaderText param=new DocumentHeaderText();
			param.setDocumentHeaderText(sapJournalentrypostTb.getHtext());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(param);
			
			//借方
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
			Token token9=new Token();
			token9.setValue("1");
			businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token9);
			journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
			
			
			ChartOfAccountsItemCode chartOfAccountsItemCode=new ChartOfAccountsItemCode();
			Token token10=new Token();
			token10.setValue(sapJournalentrypostTb.getKm().trim());
			chartOfAccountsItemCode.setChartOfAccountsItemCodeContent(token10);
			journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode);
			
			
			Amount amountItem=new Amount();
			amountItem.setAmountContent(new BigDecimal(sapJournalentrypostTb.getJfje().trim())); //借方金额
			CurrencyCode currencyCodeItem=new CurrencyCode();//币种
			Token token11=new Token();
			token11.setValue("CNY");
			currencyCodeItem.setCurrencyCode(token11);
			amountItem.setCurrencyCode(currencyCodeItem);
			journalEntryCreateRequestJournalEntryItem.setAmountInTransactionCurrency(amountItem);
			
			DebitCreditCode debitCreditCodeItem=new DebitCreditCode();
			Token token12=new Token();
			token12.setValue("S");
			debitCreditCodeItem.setDebitCreditCode(token12);
			journalEntryCreateRequestJournalEntryItem.setDebitCreditCode(debitCreditCodeItem);
			
			//成本中心
			JournalEntryCreateRequestJournalEntryItemAccountAssignment accountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
			CostCentreID costCentreID=new CostCentreID();
			Token costCentreIDToken=new Token();
			costCentreIDToken.setValue(sapJournalentrypostTb.getCbzx().trim());
			costCentreID.setCostCentreID(costCentreIDToken);
			accountAssignment.setCostCenter(costCentreID);
			journalEntryCreateRequestJournalEntryItem.setAccountAssignment(accountAssignment);
			
			DocumentItemText itemParam=new DocumentItemText();
			itemParam.setDocumentItemText(sapJournalentrypostTb.getText());
			journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam);
			
			
			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems={journalEntryCreateRequestJournalEntryItem};
			journalEntryCreateRequestJournalEntry.setItem(journalEntryCreateRequestJournalEntryItems);
			
			//贷方
			JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem=new JournalEntryCreateRequestJournalEntryCreditorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem=new BusinessTransactionDocumentItemID();
			Token token13=new Token();
			token13.setValue("2");
			businessTransactionDocumentItemIDDebtorItem.setBusinessTransactionDocumentItemID(token13);
			journalEntryCreateRequestJournalEntryCreditorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem);
			
			BusinessPartnerInternalID businessPartnerInternalID=new BusinessPartnerInternalID();
			Token token14=new Token();
			token14.setValue(sapJournalentrypostTb.getGys().trim());
			businessPartnerInternalID.setBusinessPartnerInternalID(token14);
			journalEntryCreateRequestJournalEntryCreditorItem.setCreditor(businessPartnerInternalID);
			
			Amount amountDebtorItem=new Amount();
			amountDebtorItem.setAmountContent(new BigDecimal(-Double.parseDouble(sapJournalentrypostTb.getDfje().trim())));
			CurrencyCode currencyCodeDebtorItem=new CurrencyCode();
			Token token15=new Token();
			token15.setValue("CNY");
			currencyCodeDebtorItem.setCurrencyCode(token15);
			amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem);
			journalEntryCreateRequestJournalEntryCreditorItem.setAmountInTransactionCurrency(amountDebtorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem=new DebitCreditCode();
			Token token16=new Token();
			token16.setValue("H");
			debitCreditCodeDebtorItem.setDebitCreditCode(token16);
			journalEntryCreateRequestJournalEntryCreditorItem.setDebitCreditCode(debitCreditCodeDebtorItem);
			
			DocumentItemText creditorParam=new DocumentItemText();
			creditorParam.setDocumentItemText(sapJournalentrypostTb.getText());
			journalEntryCreateRequestJournalEntryCreditorItem.setDocumentItemText(creditorParam);
			
			if(!"".equals(sapJournalentrypostTb.getZzbs())){
				JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
    			SpecialGLCode specialGLCode=new SpecialGLCode();
    			Token token18=new Token();
    			token18.setValue("H");
    			specialGLCode.setSpecialGLCode(token18);
    			journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
    			journalEntryCreateRequestJournalEntryCreditorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
			}
			
			JournalEntryCreateRequestJournalEntryCreditorItem[] journalEntryCreateRequestJournalEntryCreditorItems={journalEntryCreateRequestJournalEntryCreditorItem};
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
				sapJournalentrypostTb.setPzbh("");
				sapJournalentrypostTb.setStatus("n");
				sapJournalentrypostTb.setMsg(set.toString());
			}else{
				sapJournalentrypostTb.setPzbh(documentNo);
				sapJournalentrypostTb.setStatus("y");
				sapJournalentrypostTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapJournalentrypostTb;
	}


	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String journalEntryPostStatus) {
		SapJournalentrypostTbExample example = new SapJournalentrypostTbExample();
		if(journalEntryPostStatus!=null && !"".equals(journalEntryPostStatus)){
			Criteria criteria=example.createCriteria();
			criteria.andStatusEqualTo(journalEntryPostStatus);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapJournalentrypostTb> item = sapJournalentrypostTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapJournalentrypostTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapJournalentrypostTbExample example = new SapJournalentrypostTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapJournalentrypostTbMapper.deleteByExample(example);
		return num;
	}

}
