package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.Constants.Configuration;
import org.apache.axis2.databinding.types.Token;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapHdfTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapHdfTb;
import com.jxszj.pojo.sap.SapHdfTbExample;
import com.jxszj.pojo.sap.SapHdfTbExample.Criteria;
import com.jxszj.service.sap.IHdfService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.JDYAPIUtils;
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
import com.jxszj.utils.ServiceStub.CreatedByUser;
import com.jxszj.utils.ServiceStub.CurrencyCode;
import com.jxszj.utils.ServiceStub.Date;
import com.jxszj.utils.ServiceStub.DebitCreditCode;
import com.jxszj.utils.ServiceStub.DocumentHeaderText;
import com.jxszj.utils.ServiceStub.DocumentItemText;
import com.jxszj.utils.ServiceStub.DocumentReferenceID;
import com.jxszj.utils.ServiceStub.GLOBAL_DateTime;
import com.jxszj.utils.ServiceStub.JournalEntryBulkCreateRequest;
import com.jxszj.utils.ServiceStub.JournalEntryCreateConfirmationMessage;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestBulkMessage;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntry;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDebtorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPayment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItemAccountAssignment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.ProfitCentreID;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class HdfServiceImpl implements IHdfService {
	
	@Autowired
	private SapHdfTbMapper sapHdfTbMapper;
	
 	private static final String APPID = "5cc110c3b3c41744aaa12b2e";
 	private static final String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
 	 // 简道云     DJ1-开户申请（经销商信息）
 	private static final String DJ1_ENTRYID = "5d102d3721443528346561db";
 	
    // 简道云      DF1-销售收款单   	
 	private static final String DF1_ENTRYID = "5d3bc22704614439fd55e71d";
 	
    // 简道云      DM1-销售提成_(自动)   	
 	private static final String M1_ENTRYID = "5d8883aa1b7fa5166a4c255d";
 	
	 // POS经销商之家 --  年度目标维护
	private static final String ND_ENTRYID = "6042e1e0d75cf1000701dcc5";
 	
 	JDYAPIUtils dj1_api = new JDYAPIUtils(APPID, DJ1_ENTRYID, APIKEY);
 	JDYAPIUtils df1_api = new JDYAPIUtils(APPID, DF1_ENTRYID, APIKEY);
 	

	@Override
	public int uploadHdfExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			
			DecimalFormat df = new DecimalFormat("#0.00");
			List<SapHdfTb> list=new ArrayList<SapHdfTb>();
			for (int i = 1; i < listob.size(); i++) {
				String no=String.valueOf((int)(Math.random()*1000));//随机数
				SapHdfTb sapHdfTb=new SapHdfTb();
				sapHdfTb.setDjbh("hdf"+no+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1));
				sapHdfTb.setJxsbm(ObjectUtils.getString(listob.get(i).get(0)));
				sapHdfTb.setZkrq(ObjectUtils.getString(listob.get(i).get(1)));
				sapHdfTb.setSkbz(ObjectUtils.getString(listob.get(i).get(2)));
				sapHdfTb.setHdf(df.format(ObjectUtils.getDouble(listob.get(i).get(3))));
				sapHdfTb.setCjr(ObjectUtils.getString(listob.get(i).get(4)));
				sapHdfTb.setLrzx(ObjectUtils.getString(listob.get(i).get(5)));
				sapHdfTb.setSklx(ObjectUtils.getString(listob.get(i).get(6)));
				list.add(sapHdfTb);
			}
			
			List<SapHdfTb> lists=new ArrayList<SapHdfTb>();
			for (int i = 0; i < list.size(); i++) {
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("field", "_widget_1543814801964");
				map1.put("type", "text");
				map1.put("method", "eq");
				map1.put("value",list.get(i).getJxsbm());
		        condList.add(map1);
		        Map<String, Object> filter = new HashMap<String, Object>(){
		            {
		                put("rel", "and");
		                put("cond", condList);
		            }
		        };
		        List<Map<String, Object>> dj1 = dj1_api.getAllFormData(null,filter);
		        if(ObjectUtils.getString(dj1.get(0).get("_widget_1545377155914")).equals("PD")){
		        	SapHdfTb sapHdfTb=sendSAPPost(list.get(i),ObjectUtils.getString(dj1.get(0).get("_widget_1586139868538")));
		        	if(!"".equals(sapHdfTb.getPzbh()) && sapHdfTb.getPzbh()!=null){//判断同步到SAP是否成功
//		        		getSapHdfTb(sapHdfTb,dj1,df1_api);
		        		Map<String, Object> resmap=getSapHdfTb(sapHdfTb,dj1,df1_api);
		        		if(!"".equals(ObjectUtils.getString(resmap.get("_widget_1548037673456")))){//判断是否同步到DF1
		        			sapHdfTb.setJdybm(ObjectUtils.getString(resmap.get("_widget_1548037673456")));
		        			sapHdfTb.setJxsmc(ObjectUtils.getString(dj1.get(0).get("_widget_1543814802008")));
		        			addZHKDM1(resmap);
		        		}
			        	lists.add(sapHdfTb);
		        	}
		        }else if(ObjectUtils.getString(dj1.get(0).get("_widget_1545377155914")).equals("MS")){
		        	SapHdfTb sapHdfTb=sendSAPPost(list.get(i),ObjectUtils.getString(dj1.get(0).get("_widget_1586139868538")));
		        	if(!"".equals(sapHdfTb.getPzbh()) && sapHdfTb.getPzbh()!=null){//判断同步到SAP是否成功
//		        		getSapHdfTb(sapHdfTb,dj1,df1_api);
		        		Map<String, Object> resmap=getSapHdfTb(sapHdfTb,dj1,df1_api);
		        		if(!"".equals(ObjectUtils.getString(resmap.get("_widget_1548037673456")))){//判断是否同步到DF1
		        			sapHdfTb.setJdybm(ObjectUtils.getString(resmap.get("_widget_1548037673456")));
		        			sapHdfTb.setJxsmc(ObjectUtils.getString(dj1.get(0).get("_widget_1543814802008")));
		        			addZHKDM1(resmap);
		        		}
			        	lists.add(sapHdfTb);
		        	}
		        }
		        
			}
			num=sapHdfTbMapper.insertByBatch(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
//	@Override
//	public void sendPost(List<Integer> list) {
//		SapHdfTbExample example = new SapHdfTbExample();
//		Criteria criteria=example.createCriteria();
//		criteria.andIdIn(list);
//		List<SapHdfTb> sapDskqzTbs=SapHdfTbMapper.selectByExample(example);
//		for (int i = 0; i < sapDskqzTbs.size(); i++) {
//			SapHdfTbMapper.updateByPrimaryKey(sendSAPPost01(sapDskqzTbs.get(i)));
//		}
//		
//	}
	
	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String status,String zkrq) {
		SapHdfTbExample example = new SapHdfTbExample();
		Criteria criteria=example.createCriteria();
		if(status!=null && !"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if(zkrq!=null && !"".equals(zkrq)){
			criteria.andZkrqLike(zkrq+"%");
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapHdfTb> item = sapHdfTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapHdfTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapHdfTbExample example = new SapHdfTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapHdfTbMapper.deleteByExample(example);
		return num;
	}
	
	
	//同步到SAP过账（含清账）补4.9%服务费给经销商的收款单生成凭证
	public SapHdfTb sendSAPPost(SapHdfTb sapHdfTb,String jxsbm){
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
			token5.setValue("DZ");
			accountingDocumentTypeCode.setAccountingDocumentTypeCode(token5);
			journalEntryCreateRequestJournalEntry.setAccountingDocumentType(accountingDocumentTypeCode);
			
			DocumentReferenceID documentReferenceID=new DocumentReferenceID();
			Token token6=new Token();
			token6.setValue(sapHdfTb.getDjbh());
			documentReferenceID.setDocumentReferenceID(token6);
			journalEntryCreateRequestJournalEntry.setDocumentReferenceID(documentReferenceID);
			
			DocumentHeaderText documentHeaderText=new DocumentHeaderText();
			documentHeaderText.setDocumentHeaderText(sapHdfTb.getSkbz());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(documentHeaderText);
			
			CreatedByUser createdByUser=new CreatedByUser();
			Token token7=new Token();
			token7.setValue(sapHdfTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue("C001");
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapHdfTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapHdfTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			

			//活动费
			DecimalFormat df = new DecimalFormat("#0.00");
		    String str=df.format(Double.parseDouble(sapHdfTb.getHdf().trim())/1.06);
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
			Token token19=new Token();
			token19.setValue("1");
			businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token19);
			journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode=new ChartOfAccountsItemCode();
			Token token20=new Token();
			token20.setValue("6051090000");
			chartOfAccountsItemCode.setChartOfAccountsItemCodeContent(token20);
			journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode);
			
			Amount amountItem=new Amount();
			amountItem.setAmountContent(new BigDecimal(-Double.parseDouble(str))); 
			CurrencyCode currencyCodeItem=new CurrencyCode();//币种
			Token token21=new Token();
			token21.setValue("CNY");
			currencyCodeItem.setCurrencyCode(token21);
			amountItem.setCurrencyCode(currencyCodeItem);
			journalEntryCreateRequestJournalEntryItem.setAmountInTransactionCurrency(amountItem);
			
			DebitCreditCode debitCreditCodeItem=new DebitCreditCode();
			Token token22=new Token();
			token22.setValue("H");
			debitCreditCodeItem.setDebitCreditCode(token22);
			journalEntryCreateRequestJournalEntryItem.setDebitCreditCode(debitCreditCodeItem);
			
			DocumentItemText itemParam=new DocumentItemText();
			itemParam.setDocumentItemText(sapHdfTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam);
			
			JournalEntryCreateRequestJournalEntryItemAccountAssignment journalEntryCreateRequestJournalEntryItemAccountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
			ProfitCentreID profitCentreID=new ProfitCentreID();
			Token token23=new Token();
			token23.setValue(sapHdfTb.getLrzx());
			profitCentreID.setProfitCentreID(token23);
			journalEntryCreateRequestJournalEntryItemAccountAssignment.setProfitCenter(profitCentreID);
			journalEntryCreateRequestJournalEntryItem.setAccountAssignment(journalEntryCreateRequestJournalEntryItemAccountAssignment);
			
			//销项税
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem1=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem1=new BusinessTransactionDocumentItemID();
			Token token24=new Token();
			token24.setValue("2");
			businessTransactionDocumentItemIDItem1.setBusinessTransactionDocumentItemID(token24);
			journalEntryCreateRequestJournalEntryItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDItem1);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode1=new ChartOfAccountsItemCode();
			Token token25=new Token();
			token25.setValue("2221010500");
			chartOfAccountsItemCode1.setChartOfAccountsItemCodeContent(token25);
			journalEntryCreateRequestJournalEntryItem1.setGLAccount(chartOfAccountsItemCode1);
			
			Amount amountItem1=new Amount();
			amountItem1.setAmountContent(new BigDecimal(-(Double.parseDouble(sapHdfTb.getHdf())-Double.parseDouble(str)))); 
			CurrencyCode currencyCodeItem1=new CurrencyCode();//币种
			Token token26=new Token();
			token26.setValue("CNY");
			currencyCodeItem1.setCurrencyCode(token26);
			amountItem1.setCurrencyCode(currencyCodeItem1);
			journalEntryCreateRequestJournalEntryItem1.setAmountInTransactionCurrency(amountItem1);
			
			DebitCreditCode debitCreditCodeItem1=new DebitCreditCode();
			Token token27=new Token();
			token27.setValue("H");
			debitCreditCodeItem1.setDebitCreditCode(token27);
			journalEntryCreateRequestJournalEntryItem1.setDebitCreditCode(debitCreditCodeItem1);
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapHdfTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem1.setDocumentItemText(itemParam1);
			
			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems={journalEntryCreateRequestJournalEntryItem,journalEntryCreateRequestJournalEntryItem1};
			journalEntryCreateRequestJournalEntry.setItem(journalEntryCreateRequestJournalEntryItems);
			
			
			//贷方
			//债务人
			//预收款
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token14=new Token();
			token14.setValue("3");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token14);
			journalEntryCreateRequestJournalEntryDebtorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token15=new Token();
			token15.setValue(jxsbm);
			businessPartnerInternalID2.setBusinessPartnerInternalID(token15);
			journalEntryCreateRequestJournalEntryDebtorItem.setDebtor(businessPartnerInternalID2);;
			
			Amount amountDebtorItem2=new Amount();
			amountDebtorItem2.setAmountContent(new BigDecimal(Double.parseDouble(sapHdfTb.getHdf())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token16=new Token();
			token16.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token16);
			amountDebtorItem2.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryDebtorItem.setAmountInTransactionCurrency(amountDebtorItem2);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token17=new Token();
			token17.setValue("S");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token17);
			journalEntryCreateRequestJournalEntryDebtorItem.setDebitCreditCode(debitCreditCodeDebtorItem2);
			//总账标识
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode2=new SpecialGLCode();
			Token token18=new Token();
			token18.setValue("A");
			specialGLCode2.setSpecialGLCode(token18);
			journalEntryCreateRequestJournalEntryDownPayment.setSpecialGLCode(specialGLCode2);
			journalEntryCreateRequestJournalEntryDebtorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment);
			
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapHdfTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem.setDocumentItemText(itemParam2);
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem};
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
				sapHdfTb.setPzbh("");
				sapHdfTb.setStatus("n");
				sapHdfTb.setMsg(set.toString());
			}else{
				sapHdfTb.setPzbh(documentNo);
				sapHdfTb.setStatus("y");
				sapHdfTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapHdfTb;
	}
	
	public Map<String, Object> getSapHdfTb(SapHdfTb SapHdfTb,List<Map<String, Object>> dj1,JDYAPIUtils df1_api) throws ParseException{
		Map<String, Object> rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value",SapHdfTb.getDjbh());
		rawData.put("_widget_1611404421677", m1);// 导入单据编号
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value",SapHdfTb.getPzbh());
		rawData.put("_widget_1593141119943", m2);// 凭证编号
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value",SapHdfTb.getCjr());
		rawData.put("_widget_1564236770978", m4);// 员工
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value",dj1.get(0).get("_widget_1543814802272"));
		rawData.put("_widget_1548057662333", m5);// 品牌
		Map<String, Object> m6 = new HashMap<String, Object>();
		m6.put("value",dj1.get(0).get("_widget_1545377155914"));
		rawData.put("_widget_1548057662316", m6);// 品牌编码
		Map<String, Object> m7 = new HashMap<String, Object>();
		m7.put("value",dj1.get(0).get("_widget_1543814802008"));
		rawData.put("_widget_1548037673470", m7);// 经销商
		Map<String, Object> m8 = new HashMap<String, Object>();
		m8.put("value",dj1.get(0).get("_widget_1543814801964"));
		rawData.put("_widget_1548037673508", m8);// 经销商编码
		Map<String, Object> m9 = new HashMap<String, Object>();
		m9.put("value","美时智能家居（深圳）有限公司");
		rawData.put("_widget_1548057663778", m9);// 收款公司
		Map<String, Object> m10 = new HashMap<String, Object>();
		m10.put("value","101");
		rawData.put("_widget_1548037673908", m10);// 收款公司
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("value",dj1.get(0).get("_widget_1545375767934"));
		rawData.put("_widget_1548057662282", m11);// 区域
		Map<String, Object> m12 = new HashMap<String, Object>();
		m12.put("value",dj1.get(0).get("_widget_1544178899217"));
		rawData.put("_widget_1548057662538", m12);// 省
		Map<String, Object> m13 = new HashMap<String, Object>();
		m13.put("value",dj1.get(0).get("_widget_1544178899432"));
		rawData.put("_widget_1548057662521", m13);// 市
		Map<String, Object> m14 = new HashMap<String, Object>();
		m14.put("value",dj1.get(0).get("_widget_1565404958806"));
		rawData.put("_widget_1548057662630", m14);// 省/市
		Map<String, Object> m15 = new HashMap<String, Object>();
		m15.put("value",dj1.get(0).get("_widget_1560762110588"));
		rawData.put("_widget_1569114309696", m15);// 城市等级
		Map<String, Object> m16 = new HashMap<String, Object>();
		m16.put("value",dj1.get(0).get("_widget_1566469277094"));
		rawData.put("_widget_1567042903337", m16);// 业务
		Map<String, Object> m17 = new HashMap<String, Object>();
		m17.put("value",dj1.get(0).get("_widget_1548225231293"));
		rawData.put("_widget_1548319045972", m17);// 督导
		Map<String, Object> m18 = new HashMap<String, Object>();
		m18.put("value",dj1.get(0).get("_widget_1561362443098"));
		rawData.put("_widget_1564272870802", m18);// 经销商采购员
		Map<String, Object> m19 = new HashMap<String, Object>();
		m19.put("value",dj1.get(0).get("_widget_1563435448247"));
		rawData.put("_widget_1564272870818", m19);// 工厂跟单
		Map<String, Object> m20 = new HashMap<String, Object>();
		m20.put("value",DateUtils.getDateStringToString(DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG)));
		rawData.put("_widget_1548037674843", m20);// 收款日期
		Map<String, Object> m21 = new HashMap<String, Object>();
		m21.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR));
		rawData.put("_widget_1564272871299", m21);// 收款年
		Map<String, Object> m22 = new HashMap<String, Object>();
		m22.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
		rawData.put("_widget_1548049038941", m22);// 收款年月
		Map<String, Object> m23 = new HashMap<String, Object>();
		m23.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_2_1_STRING));
		rawData.put("_widget_1564272871532", m23);// 收款年月日
		Map<String, Object> m24 = new HashMap<String, Object>();
		m24.put("value",-Double.valueOf(SapHdfTb.getHdf()));
		rawData.put("_widget_1548037675480", m24);// 收款金额
		Map<String, Object> m25 = new HashMap<String, Object>();
		m25.put("value",-Double.valueOf(SapHdfTb.getHdf()));
		rawData.put("_widget_1564390193051", m25);// 日常返单金额
		Map<String, Object> m26 = new HashMap<String, Object>();
		m26.put("value",0);
		rawData.put("_widget_1564390192987", m26);// 摆场金额
		Map<String, Object> m27 = new HashMap<String, Object>();
		m27.put("value",0);
		rawData.put("_widget_1573440693101", m27);// 其中硬装金额
		Map<String, Object> m28 = new HashMap<String, Object>();
		m28.put("value",0);
		rawData.put("_widget_1564390193003", m28);// 工程单金额
		Map<String, Object> m29 = new HashMap<String, Object>();
		m29.put("value","101"+dj1.get(0).get("_widget_1545377155914")+dj1.get(0).get("_widget_1543814801964")+"审批通过"+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
		rawData.put("_widget_1565060809315", m29);// 【月累计收款取数标识(审批通过)】公司编码+品牌编码+客户编码+审批状态+年月
		Map<String, Object> m30 = new HashMap<String, Object>();
		m30.put("value",SapHdfTb.getSkbz());
		rawData.put("_widget_1548037674969", m30);// 收款备注(财务)
		Map<String, Object> m31 = new HashMap<String, Object>();
		m31.put("value","审批通过");
		rawData.put("_widget_1548037675919", m31);// 审批状态
		Map<String, Object> m32 = new HashMap<String, Object>();
		m32.put("value","N");
		rawData.put("_widget_1593160180082", m32);// SAP同步标识
		Map<String, Object> m33 = new HashMap<String, Object>();
		m33.put("value",SapHdfTb.getSklx());
		rawData.put("_widget_1611649660788", m33);// 收款类型
		Map<String, Object> resmap=df1_api.createData(rawData);
		return resmap;
	}
	
	
    
    // 将转货款数据推送到DM1-销售提成_(自动)
    private void addZHKDM1(Map<String, Object> resmap){
    	try {
			final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("field", "_widget_1565060809315");
			map1.put("type", "text");
			map1.put("method", "eq");
			map1.put("value",resmap.get("_widget_1565060809315"));
			condList1.add(map1);
			Map<String, Object> filter1 = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList1);
	            }
	        };
	        JDYAPIUtils api = new JDYAPIUtils(APPID, DF1_ENTRYID, APIKEY);
	        List<Map<String, Object>> xsskd = api.getAllFormData(null,filter1);
	        
	        Double skje=0.0;
	        Double rcfdje=0.0;
	        Double xlsje=0.0;
	        for (int j = 0; j < xsskd.size(); j++) {
	        	skje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
	    		rcfdje+=Double.valueOf(xsskd.get(j).get("_widget_1564390193051").toString());
	    		if("新零售分销".equals(xsskd.get(j).get("_widget_1611649660788").toString())){
	    			xlsje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
	    		}
	        }
	        //获取年度目标
			JDYAPIUtils nd_api = new JDYAPIUtils(APPID, ND_ENTRYID, APIKEY);
			List<Map<String, Object>> ndmb = nd_api.getAllFormData(null, null);
			//获取M1中的经销商
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1565054283205");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value",resmap.get("_widget_1565060809315"));
	        condList.add(map);
	        Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        JDYAPIUtils dmapi = new JDYAPIUtils(APPID, M1_ENTRYID, APIKEY);
	        List<Map<String, Object>> listM1 = dmapi.getAllFormData(null,filter);
	        if(listM1.size()==0){
	 			Map<String, Object> rawData = new HashMap<String, Object>();
	 			Map<String, Object> m1 = new HashMap<String, Object>();
	 			m1.put("value","审批通过");
	 			rawData.put("_widget_1565054282382", m1);// 审批状态
	 			Map<String, Object> m2 = new HashMap<String, Object>();
	 			m2.put("value","营业中");
	 			rawData.put("_widget_1565054282475", m2);// 营业状态
	 			Map<String, Object> m3 = new HashMap<String, Object>();
	 			m3.put("value","启用");
	 			rawData.put("_widget_1565054280172", m3);// 使用标识
	 			Map<String, Object> m4 = new HashMap<String, Object>();
	 			m4.put("value",resmap.get("_widget_1548037673508")+"审批通过"+"营业中"+"启用");
	 			rawData.put("_widget_1565054282503", m4);// 门店所属经销商标识
	 			Map<String, Object> m5 = new HashMap<String, Object>();
	 			m5.put("value",resmap.get("_widget_1565060809315"));
	 			rawData.put("_widget_1565054283205", m5);// 【月累计收款取数标识】公司编码+品牌编码+客户编码+审批状态+年月
	 			Map<String, Object> m6 = new HashMap<String, Object>();
	 			m6.put("value",resmap.get("_widget_1548037673508"));
	 			rawData.put("_widget_1565054280890", m6);// 经销商编码
	 			Map<String, Object> m7 = new HashMap<String, Object>();
	 			m7.put("value",resmap.get("_widget_1548037673470"));
	 			rawData.put("_widget_1564996674855", m7);// 经销商
	 			Map<String, Object> m8 = new HashMap<String, Object>();
	 			m8.put("value",resmap.get("_widget_1548057662333"));
	 			rawData.put("_widget_1565054280408", m8);// 品牌
	 			Map<String, Object> m9 = new HashMap<String, Object>();
	 			m9.put("value",resmap.get("_widget_1548057662316"));
	 			rawData.put("_widget_1565054280535", m9);// 品牌编码
	 			Map<String, Object> m10 = new HashMap<String, Object>();
	 			m10.put("value",resmap.get("_widget_1548057663778"));
	 			rawData.put("_widget_1565054283269", m10);// 公司
	 			Map<String, Object> m11 = new HashMap<String, Object>();
	 			m11.put("value",resmap.get("_widget_1548037673908"));
	 			rawData.put("_widget_1565054283284", m11);// 公司编码
	 			Map<String, Object> m12 = new HashMap<String, Object>();
	 			m12.put("value",resmap.get("_widget_1567042903337"));
	 			rawData.put("_widget_1567042226458", m12);// 业务
	 			Map<String, Object> m13 = new HashMap<String, Object>();
	 			m13.put("value",resmap.get("_widget_1548319045972"));
	 			rawData.put("_widget_1564996674896", m13);// 督导
	 			Map<String, Object> m14 = new HashMap<String, Object>();
	 			m14.put("value",resmap.get("_widget_1564272870818"));
	 			rawData.put("_widget_1565054281513", m14);// 跟单
	 			Map<String, Object> m15 = new HashMap<String, Object>();
	 			m15.put("value",resmap.get("_widget_1548037674843"));
	 			rawData.put("_widget_1565054281209", m15);// 日期
	 			Map<String, Object> m16 = new HashMap<String, Object>();
	 			m16.put("value",resmap.get("_widget_1564272871299"));
	 			rawData.put("_widget_1565054282027", m16);// 年
	 			Map<String, Object> m17 = new HashMap<String, Object>();
	 			m17.put("value",resmap.get("_widget_1548049038941"));
	 			rawData.put("_widget_1564996675064", m17);// 年月
	 			Map<String, Object> m18 = new HashMap<String, Object>();
	 			m18.put("value",resmap.get("_widget_1564272871532"));
	 			rawData.put("_widget_1565054281937", m18);// 年月日
	 			Map<String, Object> m19 = new HashMap<String, Object>();
	 			m19.put("value",0);
	 			rawData.put("_widget_1565166083759", m19);// 门店数
	 			Map<String, Object> m20 = new HashMap<String, Object>();
	 			m20.put("value",0);
	 			rawData.put("_widget_1565166083774", m20);// 目标
	 			Map<String, Object> m21 = new HashMap<String, Object>();
	 			m21.put("value",skje);
	 			rawData.put("_widget_1565166084025", m21);// 回款
	 			Map<String, Object> m22 = new HashMap<String, Object>();
	 			m22.put("value",0);
	 			rawData.put("_widget_1564996675036", m22);// 回款率
	 			Map<String, Object> m23 = new HashMap<String, Object>();
	 			m23.put("value",rcfdje);
	 			rawData.put("_widget_1565166084168", m23);// 回款(日常返单)
	 			Map<String, Object> m24 = new HashMap<String, Object>();
	 			m24.put("value",0);
	 			rawData.put("_widget_1565065411232", m24);// 店均返单回款
	 			Map<String, Object> m25 = new HashMap<String, Object>();
	 			m25.put("value",0);
	 			rawData.put("_widget_1564996675255", m25);// (督导)销售提成
	 			Map<String, Object> m26 = new HashMap<String, Object>();
	 			if("HMW".equals(ObjectUtils.getString(resmap.get("_widget_1548057662316")))){
	 				m26.put("value",skje*0.003);
	 			}else if("MS".equals(ObjectUtils.getString(resmap.get("_widget_1548057662316")))){
	 				m26.put("value",skje*0.003);
	 			}else if("PD".equals(ObjectUtils.getString(resmap.get("_widget_1548057662316")))){
	 				m26.put("value",skje*0.003);
	 			}else if("DUX".equals(ObjectUtils.getString(resmap.get("_widget_1548057662316")))){
	 				m26.put("value",skje*0.0015);
	 			}else if("NB".equals(ObjectUtils.getString(resmap.get("_widget_1548057662316")))){
	 				m26.put("value",skje*0.003);
	 			}
	 			rawData.put("_widget_1565074205311", m26);// (跟单)销售提成
	 			Map<String, Object> m27 = new HashMap<String, Object>();
	 			m27.put("value",resmap.get("updateTime"));
	 			rawData.put("_widget_1565074204963", m27);// 操作更新时间
	 			Map<String, Object> m28 = new HashMap<String, Object>();
	 			m28.put("value",resmap.get("_widget_1569114309696"));
	 			rawData.put("_widget_1573095139039", m28);// 城市等级
	 			Map<String, Object> m30 = new HashMap<String, Object>();
	 			m30.put("value",resmap.get("_widget_1548057662282"));
	 			rawData.put("_widget_1588925232200", m30);// 区域
	 			Map<String, Object> m31 = new HashMap<String, Object>();
	 			m31.put("value",resmap.get("_widget_1548057662538"));
	 			rawData.put("_widget_1588925232215", m31);// 省份
	 			Map<String, Object> m32 = new HashMap<String, Object>();
	 			m32.put("value",resmap.get("_widget_1548057662521"));
	 			rawData.put("_widget_1588925232243", m32);// 城市
	 			Map<String, Object> m33 = new HashMap<String, Object>();
	 			m33.put("value",0);
	 			rawData.put("_widget_1614911874299", m33);// 督导年度目标
	 			Map<String, Object> m34 = new HashMap<String, Object>();
	 			m34.put("value",0);
	 			rawData.put("_widget_1614911874278", m34);// 品牌年度目标
	 			Map<String, Object> m35 = new HashMap<String, Object>();
	 			m35.put("value",ndmb.get(0).get("_widget_1614908475285"));
	 			rawData.put("_widget_1614996372547", m35);// 年度目标
	 			Map<String, Object> m36 = new HashMap<String, Object>();
	 			m36.put("value",0);
	 			rawData.put("_widget_1611902543094", m36);// 新零售目标
	 			Map<String, Object> m37 = new HashMap<String, Object>();
	 			m37.put("value",xlsje);
	 			rawData.put("_widget_1611902543190", m37);// 新零售订单金额
	 			Map<String, Object> m38 = new HashMap<String, Object>();
	 			m38.put("value",0);
	 			rawData.put("_widget_1611902543438", m38);// 新零售目标完成率
	 			dmapi.createForData(rawData);// 向简道云添加DM1-销售提成_(自动);
	        }else if(listM1.size()==1){
 		       DecimalFormat df = new DecimalFormat("#0.0000");  
 		       Map<String, Object> rawData = new HashMap<String, Object>();
 		       Map<String, Object> m1 = new HashMap<String, Object>();
 		       m1.put("value",skje);
 		       rawData.put("_widget_1565166084025", m1);// 回款
 		       
 		       double mb=Double.valueOf(listM1.get(0).get("_widget_1565166083774").toString());
 		       Map<String, Object> m2 = new HashMap<String, Object>();
 		       if(mb!=0.0){
 		    	  m2.put("value",df.format(skje/mb));
 		       }else{
 		    	  m2.put("value",0);
 		       }
 		       rawData.put("_widget_1564996675036", m2);// 回款率
 		       
 		       Map<String, Object> m3 = new HashMap<String, Object>();
 		       m3.put("value",rcfdje);
 		       rawData.put("_widget_1565166084168", m3);// 回款(日常返单)
 		       
 		       Map<String, Object> m6 = new HashMap<String, Object>();
 		       if("HMW".equals(listM1.get(0).get("_widget_1565054280535").toString())){
 		    	  m6.put("value",skje*0.003);
 		       }else if("MS".equals(listM1.get(0).get("_widget_1565054280535").toString())){
 		    	  m6.put("value",skje*0.003);
 		       }else if("PD".equals(listM1.get(0).get("_widget_1565054280535").toString())){
 		    	  m6.put("value",skje*0.003);
 		       }else if("DUX".equals(listM1.get(0).get("_widget_1565054280535").toString())){
 		    	  m6.put("value",skje*0.0015);
 		       }else if("NB".equals(listM1.get(0).get("_widget_1565054280535").toString())){
 		    	  m6.put("value",skje*0.003);
 		       }
 		       rawData.put("_widget_1565074205311", m6);// (跟单)销售提成
 		       
 		       Map<String, Object> m7 = new HashMap<String, Object>();
 		       m7.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG));
 		       rawData.put("_widget_1565074204963", m7);// 操作更新时间
 		       
  	 		   Map<String, Object> m8 = new HashMap<String, Object>();
 		       m8.put("value",xlsje);
 		       rawData.put("_widget_1611902543190", m8);// 新零售订单金额
 		       
 		       Double xlsmb=ObjectUtils.getDouble(listM1.get(0).get("_widget_1611902543094"));
 		       Map<String, Object> m9 = new HashMap<String, Object>();
 		       m9.put("value",xlsmb!=0.0?df.format(xlsje/xlsmb):0);
 		       rawData.put("_widget_1611902543438", m9);// 新零售目标完成率
     			
 		       dmapi.updateData(listM1.get(0).get("_id").toString(), rawData);// 向简道云更新DM1-销售提成_(自动)
	        }
		    //修改当前督导的店均返单回款和(督导)销售提成
		    DecimalFormat df = new DecimalFormat("#0.00");  
    	   	final List<Map<String, Object>> condList5 = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map5=new HashMap<String, Object>();
	        map5.put("field", "_widget_1564996674896");
	        map5.put("type", "text");
	        map5.put("method", "eq");
	        map5.put("value",resmap.get("_widget_1548319045972"));
			condList5.add(map5);
	        Map<String, Object> map6=new HashMap<String, Object>();
	        map6.put("field", "_widget_1564996675064");
	        map6.put("type", "text");
	        map6.put("method", "eq");
	        map6.put("value",DateUtils.getNowDateToString("yyyy年M月"));
			condList5.add(map6);
			Map<String, Object> map7=new HashMap<String, Object>();
	        map7.put("field", "_widget_1565166083759");
	        map7.put("type", "text");
	        map7.put("method", "ne");
	        map7.put("value",0);
			condList5.add(map7);
			Map<String, Object> filter5 = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList5);
	            }
	        };
	       List<Map<String, Object>> M1 = dmapi.getAllFormData(null,filter5);
	       //当前督导负责的门店数
	       int mds=0;
	       double hkje=0.0;
	       for (int i = 0; i < M1.size(); i++) {
	    	   mds+=Integer.valueOf(M1.get(i).get("_widget_1565166083759").toString());
	    	   hkje+=Double.valueOf(M1.get(i).get("_widget_1565166084168").toString());
	       }
	       //修改
	       for(int i = 0; i < M1.size(); i++){
	    	   double hk=Double.valueOf(M1.get(i).get("_widget_1565166084025").toString());
	    	   Map<String, Object> rawData = new HashMap<String, Object>();
 		       Map<String, Object> m1 = new HashMap<String, Object>();
 		       double djfdhk=0.0;
 		       if(mds!=0.0){
 		    	  djfdhk=Double.valueOf(df.format(hkje/mds));
 		       }
 		       m1.put("value",djfdhk);
 		       rawData.put("_widget_1565065411232", m1);// 店均返单回款
     			
 		       Map<String, Object> m2 = new HashMap<String, Object>();
 		       if(djfdhk<40000){
 		    	  m2.put("value",0);
 		       }else if(djfdhk>=40000 && djfdhk<60000){
 		    	  m2.put("value",hk*0.005);
 		       }else if(djfdhk>=60000 && djfdhk<90000){
 		    	  m2.put("value",hk*0.01);
 		       }else if(djfdhk>=90000){
 		    	  m2.put("value",hk*0.02);
 		       }
 		       rawData.put("_widget_1564996675255", m2);// (督导)销售提成
 		       dmapi.updateData(M1.get(i).get("_id").toString(), rawData);// 向简道云更新DM1-销售提成_(自动)
	       }
 			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl,String hdfStatus, String zkrq) {
		XSSFWorkbook wb = null;
		try {
			SapHdfTbExample example = new SapHdfTbExample();
			Criteria criteria=example.createCriteria();
			if(hdfStatus!=null && !"".equals(hdfStatus)){
				criteria.andStatusEqualTo(hdfStatus);
			}
			if(zkrq!=null && !"".equals(zkrq)){
				criteria.andZkrqLike(zkrq+"%");
			}
			// 分页处理
			List<SapHdfTb> item = sapHdfTbMapper.selectByExample(example);

			File fi = new File(excelUrl);
			// 读取excel模板
			wb = new XSSFWorkbook(new FileInputStream(fi));
			// 读取了模板内所有sheet内容
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowIndex = 1;
			for (SapHdfTb SapHdfTb : item) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}

				XSSFCell cell0 = row.getCell(0);
				if (null == cell0) {
					cell0 = row.createCell(0);
				}
				cell0.setCellValue(SapHdfTb.getJxsbm());

				XSSFCell cell1 = row.getCell(1);
				if (null == cell1) {
					cell1 = row.createCell(1);
				}
				cell1.setCellValue(SapHdfTb.getZkrq());

				XSSFCell cell2 = row.getCell(2);
				if (null == cell2) {
					cell2 = row.createCell(2);
				}
				cell2.setCellValue(SapHdfTb.getSkbz());

				XSSFCell cell3 = row.getCell(3);
				if (null == cell3) {
					cell3 = row.createCell(3);
				}
				cell3.setCellValue(SapHdfTb.getHdf());
				
				XSSFCell cell4 = row.getCell(4);
				if (null == cell4) {
					cell4 = row.createCell(4);
				}
				cell4.setCellValue(SapHdfTb.getCjr());
				
				XSSFCell cell5 = row.getCell(5);
				if (null == cell5) {
					cell5 = row.createCell(5);
				}
				cell5.setCellValue(SapHdfTb.getLrzx());
				
				XSSFCell cell6 = row.getCell(6);
				if (null == cell6) {
					cell6 = row.createCell(6);
				}
				cell6.setCellValue(SapHdfTb.getSklx());
				
				rowIndex++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

}
