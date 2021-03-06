package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
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

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Actioncard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapZjxshkTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapZjxshkTb;
import com.jxszj.pojo.sap.SapZjxshkTbExample;
import com.jxszj.pojo.sap.SapZjxshkTbExample.Criteria;
import com.jxszj.service.sap.IZjxshkService;
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
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryCreditorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDebtorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPayment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPaymentCreditor;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItemAccountAssignment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.ProfitCentreID;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class ZjxshkServiceImpl implements IZjxshkService {
	
	@Autowired
	private SapZjxshkTbMapper sapZjxshkTbMapper;
	
 	private static final String APPID = "5cc110c3b3c41744aaa12b2e";
 	private static final String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
 	 // ?????????     DJ1-?????????????????????????????????
 	private static final String DJ1_ENTRYID = "5d102d3721443528346561db";
 	
    // ?????????      DF1-???????????????   	
 	private static final String DF1_ENTRYID = "5d3bc22704614439fd55e71d";
 	
    // ?????????      DM1-????????????_(??????)   	
 	private static final String M1_ENTRYID = "5d8883aa1b7fa5166a4c255d";
 	
    // ?????????      ?????????????????????   	
 	private static final String NEW_ENTRYID = "601376d61b40030008166808";
 	
	 // POS??????????????? --  ??????????????????
	private static final String ND_ENTRYID = "6042e1e0d75cf1000701dcc5";
 	
 	JDYAPIUtils dj1_api = new JDYAPIUtils(APPID, DJ1_ENTRYID, APIKEY);
 	JDYAPIUtils df1_api = new JDYAPIUtils(APPID, DF1_ENTRYID, APIKEY);
 	JDYAPIUtils new_api = new JDYAPIUtils(APPID, NEW_ENTRYID, APIKEY);
 	
 	
	@Override
	public int uploadZjxshkExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			
			DecimalFormat df = new DecimalFormat("#0.00");
			List<SapZjxshkTb> list=new ArrayList<SapZjxshkTb>();
			for (int i = 1; i < listob.size(); i++) {
				if(ObjectUtils.getString(listob.get(i).get(0)).equals("")){
					continue;
				}
				String no=String.valueOf((int)(Math.random()*1000));//?????????
				SapZjxshkTb sapZjxshkTb=new SapZjxshkTb();
				sapZjxshkTb.setDjbh("zhk"+no+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1));
				sapZjxshkTb.setJxsbm(ObjectUtils.getString(listob.get(i).get(0)));
				sapZjxshkTb.setZkrq(ObjectUtils.getString(listob.get(i).get(1)));
				sapZjxshkTb.setDdje(df.format(ObjectUtils.getDouble(listob.get(i).get(2))));
				sapZjxshkTb.setSkbz(ObjectUtils.getString(listob.get(i).get(3)));
				sapZjxshkTb.setYsje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
				sapZjxshkTb.setFwf(df.format(ObjectUtils.getDouble(listob.get(i).get(5))));
				sapZjxshkTb.setCjr(ObjectUtils.getString(listob.get(i).get(6)));
				sapZjxshkTb.setLrzx(ObjectUtils.getString(listob.get(i).get(7)));
				sapZjxshkTb.setSklx(ObjectUtils.getString(listob.get(i).get(8)));
				list.add(sapZjxshkTb);
			}
			
			List<SapZjxshkTb> lists=new ArrayList<SapZjxshkTb>();
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
		        	SapZjxshkTb sapZjxshkTb=sendSAPPost01(list.get(i),ObjectUtils.getString(dj1.get(0).get("_widget_1586139868538")));
		        	if(!"".equals(sapZjxshkTb.getPzbh()) && sapZjxshkTb.getPzbh()!=null){//???????????????SAP????????????
		        		Map<String, Object> resmap=getSapZjxshkTb(sapZjxshkTb,dj1,df1_api);
		        		if(!"".equals(ObjectUtils.getString(resmap.get("_widget_1548037673456")))){//?????????????????????DF1
		        			sapZjxshkTb.setJdybm(ObjectUtils.getString(resmap.get("_widget_1548037673456")));
			        		sapZjxshkTb.setJxsmc(ObjectUtils.getString(dj1.get(0).get("_widget_1543814802008")));
		        			addZHKDM1(resmap);
		        			pushZHKDDJQR(resmap);
		        		}
		        	}
		        	lists.add(sapZjxshkTb);
		        }else if(ObjectUtils.getString(dj1.get(0).get("_widget_1545377155914")).equals("MS")){
		        	SapZjxshkTb sapZjxshkTb=ms1(list.get(i),ObjectUtils.getString(dj1.get(0).get("_widget_1586139868538")));
		        	//???MS??????????????????????????????????????????????????????
    				if(!sapZjxshkTb.getPzbh().equals("") && sapZjxshkTb.getPzbh()!=null){
    					sapZjxshkTb=ms3(list.get(i),ObjectUtils.getString(dj1.get(0).get("_widget_1586139868538")));
    					//?????????????????????????????????????????????DF1
    					if(sapZjxshkTb.getPzbh().split(",").length==2){
    						Map<String, Object> resmap=getSapZjxshkTb(sapZjxshkTb,dj1,df1_api);
    						if(!"".equals(ObjectUtils.getString(resmap.get("_widget_1548037673456")))){//?????????????????????DF1
    							sapZjxshkTb.setJdybm(ObjectUtils.getString(resmap.get("_widget_1548037673456")));
    			        		sapZjxshkTb.setJxsmc(ObjectUtils.getString(dj1.get(0).get("_widget_1543814802008")));
    		        			addZHKDM1(resmap);
    		        			pushZHKDDJQR(resmap);
    						}
    					}
    				}
    				lists.add(sapZjxshkTb);
		        }
		        
			}
			num=sapZjxshkTbMapper.insertByBatch(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
//	@Override
//	public void sendPost(List<Integer> list) {
//		SapZjxshkTbExample example = new SapZjxshkTbExample();
//		Criteria criteria=example.createCriteria();
//		criteria.andIdIn(list);
//		List<SapZjxshkTb> sapDskqzTbs=sapZjxshkTbMapper.selectByExample(example);
//		for (int i = 0; i < sapDskqzTbs.size(); i++) {
//			sapZjxshkTbMapper.updateByPrimaryKey(sendSAPPost01(sapDskqzTbs.get(i)));
//		}
//		
//	}
	
	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String status,String zkrq) {
		SapZjxshkTbExample example = new SapZjxshkTbExample();
		Criteria criteria=example.createCriteria();
		if(status!=null && !"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if(zkrq!=null && !"".equals(zkrq)){
			criteria.andZkrqLike(zkrq+"%");
		}
		// ????????????
		PageHelper.startPage(page, rows);
		List<SapZjxshkTb> item = sapZjxshkTbMapper.selectByExample(example);
		// ???????????????????????????
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// ??????????????????
		PageInfo<SapZjxshkTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapZjxshkTbExample example = new SapZjxshkTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapZjxshkTbMapper.deleteByExample(example);
		return num;
	}
	
	
	//?????????SAP?????????????????????(PD??????)
	public SapZjxshkTb sendSAPPost01(SapZjxshkTb sapZjxshkTb,String jxsbm){
		try {
			ServiceStub stub = new ServiceStub();
			Authenticator auth = new Authenticator();
			auth.setUsername("JDYUSER");
			auth.setPassword("HfjTP>TgfqQMtHEghZbUxmgeNvrffXw9CoBnATjl");
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
			stub._getServiceClient().getOptions().setProperty(Configuration.CHARACTER_SET_ENCODING, "GBK");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			stub._getServiceClient().getOptions().setProperty(Configuration.HTTP_METHOD, HTTPConstants.HTTP_METHOD_POST);
			
			
			JournalEntryBulkCreateRequest journalEntryBulkCreateRequest=new JournalEntryBulkCreateRequest(); //???????????????
			JournalEntryCreateRequestBulkMessage journalEntryCreateRequestBulkMessage=new JournalEntryCreateRequestBulkMessage();//????????????MessageHeader???JournalEntryCreateRequest??????
			BusinessDocumentMessageHeader businessDocumentMessageHeader=new BusinessDocumentMessageHeader();//??????MessageHeader??????ID???CreationDateTime
			//????????????MessageHeader??????ID
			BusinessDocumentMessageID businessDocumentMessageIDHeader=new BusinessDocumentMessageID();
			Token tokenHeader=new Token();
			tokenHeader.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDHeader.setBusinessDocumentMessageIDContent(tokenHeader);
			businessDocumentMessageHeader.setID(businessDocumentMessageIDHeader);
			//????????????MessageHeader??????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeHeader=new GLOBAL_DateTime();
			gLOBAL_DateTimeHeader.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeader.setCreationDateTime(gLOBAL_DateTimeHeader);
			//????????????MessageHeader??????
			journalEntryCreateRequestBulkMessage.setMessageHeader(businessDocumentMessageHeader);
			
			//??????JournalEntryCreateRequest????????????
			JournalEntryCreateRequestMessage journalEntryCreateRequestMessage=new JournalEntryCreateRequestMessage();
			//??????JournalEntryCreateRequest???????????????---------
			BusinessDocumentMessageHeader businessDocumentMessageHeaderEntry=new BusinessDocumentMessageHeader();
			//??????JournalEntryCreateRequest???????????????ID
			BusinessDocumentMessageID businessDocumentMessageIDEntry=new BusinessDocumentMessageID();
			Token tokenEntry=new Token();
			tokenEntry.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDEntry.setBusinessDocumentMessageIDContent(tokenEntry);
			businessDocumentMessageHeaderEntry.setID(businessDocumentMessageIDEntry);
			//??????JournalEntryCreateRequest???????????????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeEntry=new GLOBAL_DateTime();
			gLOBAL_DateTimeEntry.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeaderEntry.setCreationDateTime(gLOBAL_DateTimeEntry);
			journalEntryCreateRequestMessage.setMessageHeader(businessDocumentMessageHeaderEntry);
			
			//??????JournalEntryCreateRequest???????????????-----------
			JournalEntryCreateRequestJournalEntry journalEntryCreateRequestJournalEntry=new JournalEntryCreateRequestJournalEntry();
			//????????????????????????
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
			token6.setValue(sapZjxshkTb.getDjbh());
			documentReferenceID.setDocumentReferenceID(token6);
			journalEntryCreateRequestJournalEntry.setDocumentReferenceID(documentReferenceID);
			
			DocumentHeaderText documentHeaderText=new DocumentHeaderText();
			documentHeaderText.setDocumentHeaderText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(documentHeaderText);
			
			CreatedByUser createdByUser=new CreatedByUser();
			Token token7=new Token();
			token7.setValue(sapZjxshkTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue("C001");
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			

			List<JournalEntryCreateRequestJournalEntryDebtorItem> journalEntryCreateRequestJournalEntryDebtorItems=new ArrayList<JournalEntryCreateRequestJournalEntryDebtorItem>();
			//?????????(??????)
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem1=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token9=new Token();
			token9.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token9);
			journalEntryCreateRequestJournalEntryDebtorItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token10=new Token();
			token10.setValue("100204");
			businessPartnerInternalID1.setBusinessPartnerInternalID(token10);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebtor(businessPartnerInternalID1);
			
			Amount amountDebtorItem1=new Amount();
			amountDebtorItem1.setAmountContent(new BigDecimal(Double.parseDouble(sapZjxshkTb.getDdje().trim())));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token11=new Token();
			token11.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token11);
			amountDebtorItem1.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setAmountInTransactionCurrency(amountDebtorItem1);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token12=new Token();
			token12.setValue("S");//S?????????????????????
			debitCreditCodeDebtorItem1.setDebitCreditCode(token12);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment1=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode1=new SpecialGLCode();
			Token token13=new Token();
			token13.setValue("E");
			specialGLCode1.setSpecialGLCode(token13);
			journalEntryCreateRequestJournalEntryDownPayment1.setSpecialGLCode(specialGLCode1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment1);
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem1.setDocumentItemText(itemParam1);
			
			journalEntryCreateRequestJournalEntryDebtorItems.add(journalEntryCreateRequestJournalEntryDebtorItem1);
			
//			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem};
//			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems);
			
			//??????
			//?????????
			//?????????
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem2=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token14=new Token();
			token14.setValue("2");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token14);
			journalEntryCreateRequestJournalEntryDebtorItem2.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token15=new Token();
			token15.setValue(jxsbm);
			businessPartnerInternalID2.setBusinessPartnerInternalID(token15);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebtor(businessPartnerInternalID2);;
			
			Amount amountCreditorItem2=new Amount();
			amountCreditorItem2.setAmountContent(new BigDecimal(-Double.parseDouble(sapZjxshkTb.getYsje().trim())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token16=new Token();
			token16.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token16);
			amountCreditorItem2.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryDebtorItem2.setAmountInTransactionCurrency(amountCreditorItem2);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token17=new Token();
			token17.setValue("H");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token17);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebitCreditCode(debitCreditCodeDebtorItem2);
			//????????????
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment2=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode2=new SpecialGLCode();
			Token token18=new Token();
			token18.setValue("A");
			specialGLCode2.setSpecialGLCode(token18);
			journalEntryCreateRequestJournalEntryDownPayment2.setSpecialGLCode(specialGLCode2);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment2);
			
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem2.setDocumentItemText(itemParam2);
			journalEntryCreateRequestJournalEntryDebtorItems.add(journalEntryCreateRequestJournalEntryDebtorItem2);
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItemArray=new JournalEntryCreateRequestJournalEntryDebtorItem[journalEntryCreateRequestJournalEntryDebtorItems.size()];
			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems.toArray(journalEntryCreateRequestJournalEntryDebtorItemArray));
			
			
			//????????????
			DecimalFormat df = new DecimalFormat("#0.00");
		    String str=df.format(Double.parseDouble(sapZjxshkTb.getFwf().trim())/1.06);
		    
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
			Token token19=new Token();
			token19.setValue("3");
			businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token19);
			journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode=new ChartOfAccountsItemCode();
			Token token20=new Token();
			token20.setValue("6051080000");
			chartOfAccountsItemCode.setChartOfAccountsItemCodeContent(token20);
			journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode);
			
			Amount amountItem=new Amount();
			amountItem.setAmountContent(new BigDecimal(-Double.parseDouble(str))); 
			CurrencyCode currencyCodeItem=new CurrencyCode();//??????
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
			
			DocumentItemText itemParam3=new DocumentItemText();
			itemParam3.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam3);
			
			JournalEntryCreateRequestJournalEntryItemAccountAssignment journalEntryCreateRequestJournalEntryItemAccountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
			ProfitCentreID profitCentreID=new ProfitCentreID();
			Token token23=new Token();
			token23.setValue(sapZjxshkTb.getLrzx());
			profitCentreID.setProfitCentreID(token23);
			journalEntryCreateRequestJournalEntryItemAccountAssignment.setProfitCenter(profitCentreID);
			journalEntryCreateRequestJournalEntryItem.setAccountAssignment(journalEntryCreateRequestJournalEntryItemAccountAssignment);
			
			
			//?????????
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem1=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem1=new BusinessTransactionDocumentItemID();
			Token token24=new Token();
			token24.setValue("4");
			businessTransactionDocumentItemIDItem1.setBusinessTransactionDocumentItemID(token24);
			journalEntryCreateRequestJournalEntryItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDItem1);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode1=new ChartOfAccountsItemCode();
			Token token25=new Token();
			token25.setValue("2221010500");
			chartOfAccountsItemCode1.setChartOfAccountsItemCodeContent(token25);
			journalEntryCreateRequestJournalEntryItem1.setGLAccount(chartOfAccountsItemCode1);
			
			Amount amountItem1=new Amount();
			amountItem1.setAmountContent(new BigDecimal(-(Double.parseDouble(sapZjxshkTb.getFwf().trim())-Double.parseDouble(str)))); 
			CurrencyCode currencyCodeItem1=new CurrencyCode();//??????
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
			
			DocumentItemText itemParam4=new DocumentItemText();
			itemParam4.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem1.setDocumentItemText(itemParam4);
			
			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems={journalEntryCreateRequestJournalEntryItem,journalEntryCreateRequestJournalEntryItem1};
			journalEntryCreateRequestJournalEntry.setItem(journalEntryCreateRequestJournalEntryItems);
			
			
			journalEntryCreateRequestMessage.setJournalEntry(journalEntryCreateRequestJournalEntry);
			
			//???JournalEntryCreateRequest?????????????????????
			JournalEntryCreateRequestMessage[] journalEntryCreateRequestMessages={journalEntryCreateRequestMessage};
			//??????JournalEntryCreateRequest??????
			journalEntryCreateRequestBulkMessage.setJournalEntryCreateRequest(journalEntryCreateRequestMessages);
			
			
			journalEntryBulkCreateRequest.setJournalEntryBulkCreateRequest(journalEntryCreateRequestBulkMessage);
			
			String documentNo="";
			Set<String> set=new HashSet<String>();
			LogItem[] logItem=null;
			//???????????????        
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
				sapZjxshkTb.setPzbh("");
				sapZjxshkTb.setStatus("n");
				sapZjxshkTb.setMsg(set.toString());
			}else{
				sapZjxshkTb.setPzbh(documentNo);
				sapZjxshkTb.setStatus("y");
				sapZjxshkTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapZjxshkTb;
	}
	public SapZjxshkTb ms1(SapZjxshkTb sapZjxshkTb,String jxsbm) throws RemoteException{
		try {
			ServiceStub stub = new ServiceStub();
			Authenticator auth = new Authenticator();
			auth.setUsername("JDYUSER");
			auth.setPassword("HfjTP>TgfqQMtHEghZbUxmgeNvrffXw9CoBnATjl");
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
			stub._getServiceClient().getOptions().setProperty(Configuration.CHARACTER_SET_ENCODING, "GBK");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			stub._getServiceClient().getOptions().setProperty(Configuration.HTTP_METHOD, HTTPConstants.HTTP_METHOD_POST);
			
			JournalEntryBulkCreateRequest journalEntryBulkCreateRequest=new JournalEntryBulkCreateRequest(); //???????????????
			JournalEntryCreateRequestBulkMessage journalEntryCreateRequestBulkMessage=new JournalEntryCreateRequestBulkMessage();//????????????MessageHeader???JournalEntryCreateRequest??????
			BusinessDocumentMessageHeader businessDocumentMessageHeader=new BusinessDocumentMessageHeader();//??????MessageHeader??????ID???CreationDateTime
			//????????????MessageHeader??????ID
			BusinessDocumentMessageID businessDocumentMessageIDHeader=new BusinessDocumentMessageID();
			Token tokenHeader=new Token();
			tokenHeader.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDHeader.setBusinessDocumentMessageIDContent(tokenHeader);
			businessDocumentMessageHeader.setID(businessDocumentMessageIDHeader);
			//????????????MessageHeader??????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeHeader=new GLOBAL_DateTime();
			gLOBAL_DateTimeHeader.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeader.setCreationDateTime(gLOBAL_DateTimeHeader);
			//????????????MessageHeader??????
			journalEntryCreateRequestBulkMessage.setMessageHeader(businessDocumentMessageHeader);
			
			//??????JournalEntryCreateRequest????????????
			JournalEntryCreateRequestMessage journalEntryCreateRequestMessage=new JournalEntryCreateRequestMessage();
			//??????JournalEntryCreateRequest???????????????---------
			BusinessDocumentMessageHeader businessDocumentMessageHeaderEntry=new BusinessDocumentMessageHeader();
			//??????JournalEntryCreateRequest???????????????ID
			BusinessDocumentMessageID businessDocumentMessageIDEntry=new BusinessDocumentMessageID();
			Token tokenEntry=new Token();
			tokenEntry.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDEntry.setBusinessDocumentMessageIDContent(tokenEntry);
			businessDocumentMessageHeaderEntry.setID(businessDocumentMessageIDEntry);
			//??????JournalEntryCreateRequest???????????????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeEntry=new GLOBAL_DateTime();
			gLOBAL_DateTimeEntry.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeaderEntry.setCreationDateTime(gLOBAL_DateTimeEntry);
			journalEntryCreateRequestMessage.setMessageHeader(businessDocumentMessageHeaderEntry);
			
			//??????JournalEntryCreateRequest???????????????-----------
			JournalEntryCreateRequestJournalEntry journalEntryCreateRequestJournalEntry=new JournalEntryCreateRequestJournalEntry();
			//????????????????????????
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
			token6.setValue(sapZjxshkTb.getDjbh());
			documentReferenceID.setDocumentReferenceID(token6);
			journalEntryCreateRequestJournalEntry.setDocumentReferenceID(documentReferenceID);
			
			DocumentHeaderText documentHeaderText=new DocumentHeaderText();
			documentHeaderText.setDocumentHeaderText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(documentHeaderText);
			
			CreatedByUser createdByUser=new CreatedByUser();
			Token token7=new Token();
			token7.setValue(sapZjxshkTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue("C001");
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			
			//??????
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem1=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token13=new Token();
			token13.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token13);
			journalEntryCreateRequestJournalEntryDebtorItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token14=new Token();
			token14.setValue("C002");
			businessPartnerInternalID1.setBusinessPartnerInternalID(token14);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebtor(businessPartnerInternalID1);
			
			Amount amountDebtorItem1=new Amount();
			amountDebtorItem1.setAmountContent(new BigDecimal(sapZjxshkTb.getDdje().trim()));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token15=new Token();
			token15.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token15);
			amountDebtorItem1.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setAmountInTransactionCurrency(amountDebtorItem1);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token16=new Token();
			token16.setValue("S");
			debitCreditCodeDebtorItem1.setDebitCreditCode(token16);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment1=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode1=new SpecialGLCode();
			Token token18=new Token();
			token18.setValue("E");
			specialGLCode1.setSpecialGLCode(token18);
			journalEntryCreateRequestJournalEntryDownPayment1.setSpecialGLCode(specialGLCode1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment1);
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem1.setDocumentItemText(itemParam1);
			
			//??????
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem2=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token19=new Token();
			token19.setValue("2");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token19);
			journalEntryCreateRequestJournalEntryDebtorItem2.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token20=new Token();
			token20.setValue(jxsbm);
			businessPartnerInternalID2.setBusinessPartnerInternalID(token20);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebtor(businessPartnerInternalID2);
			
			Amount amountDebtorItem2=new Amount();
			amountDebtorItem2.setAmountContent(new BigDecimal(-Double.parseDouble(sapZjxshkTb.getYsje().trim())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token21=new Token();
			token21.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token21);
			amountDebtorItem2.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryDebtorItem2.setAmountInTransactionCurrency(amountDebtorItem2);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token22=new Token();
			token22.setValue("H");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token22);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDebitCreditCode(debitCreditCodeDebtorItem2);
			
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment2=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode2=new SpecialGLCode();
			Token token23=new Token();
			token23.setValue("A");
			specialGLCode2.setSpecialGLCode(token23);
			journalEntryCreateRequestJournalEntryDownPayment2.setSpecialGLCode(specialGLCode2);
			journalEntryCreateRequestJournalEntryDebtorItem2.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment2);
			
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem2.setDocumentItemText(itemParam2);
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem1,journalEntryCreateRequestJournalEntryDebtorItem2};
			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems);
			
			//????????????
			DecimalFormat df = new DecimalFormat("#0.00");
		    String str=df.format(Double.parseDouble(sapZjxshkTb.getFwf().trim())/1.06);
		    
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
			Token token24=new Token();
			token24.setValue("3");
			businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token24);
			journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode=new ChartOfAccountsItemCode();
			Token token25=new Token();
			token25.setValue("6051080000");
			chartOfAccountsItemCode.setChartOfAccountsItemCodeContent(token25);
			journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode);
			
			Amount amountItem=new Amount();
			amountItem.setAmountContent(new BigDecimal(-Double.parseDouble(str))); 
			CurrencyCode currencyCodeItem=new CurrencyCode();//??????
			Token token26=new Token();
			token26.setValue("CNY");
			currencyCodeItem.setCurrencyCode(token26);
			amountItem.setCurrencyCode(currencyCodeItem);
			journalEntryCreateRequestJournalEntryItem.setAmountInTransactionCurrency(amountItem);
			
			DebitCreditCode debitCreditCodeItem=new DebitCreditCode();
			Token token27=new Token();
			token27.setValue("H");
			debitCreditCodeItem.setDebitCreditCode(token27);
			journalEntryCreateRequestJournalEntryItem.setDebitCreditCode(debitCreditCodeItem);
			
			DocumentItemText itemParam3=new DocumentItemText();
			itemParam3.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam3);
			
			JournalEntryCreateRequestJournalEntryItemAccountAssignment journalEntryCreateRequestJournalEntryItemAccountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
			ProfitCentreID profitCentreID=new ProfitCentreID();
			Token token28=new Token();
			token28.setValue(sapZjxshkTb.getLrzx());
			profitCentreID.setProfitCentreID(token28);
			journalEntryCreateRequestJournalEntryItemAccountAssignment.setProfitCenter(profitCentreID);
			journalEntryCreateRequestJournalEntryItem.setAccountAssignment(journalEntryCreateRequestJournalEntryItemAccountAssignment);
			
			
			//?????????
			JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem1=new JournalEntryCreateRequestJournalEntryItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem1=new BusinessTransactionDocumentItemID();
			Token token29=new Token();
			token29.setValue("3");
			businessTransactionDocumentItemIDItem1.setBusinessTransactionDocumentItemID(token29);
			journalEntryCreateRequestJournalEntryItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDItem1);
			
			ChartOfAccountsItemCode chartOfAccountsItemCode1=new ChartOfAccountsItemCode();
			Token token30=new Token();
			token30.setValue("2221010500");
			chartOfAccountsItemCode1.setChartOfAccountsItemCodeContent(token30);
			journalEntryCreateRequestJournalEntryItem1.setGLAccount(chartOfAccountsItemCode1);
			
			Amount amountItem1=new Amount();
			amountItem1.setAmountContent(new BigDecimal(-(Double.parseDouble(sapZjxshkTb.getFwf().trim())-Double.parseDouble(str)))); 
			CurrencyCode currencyCodeItem1=new CurrencyCode();//??????
			Token token31=new Token();
			token31.setValue("CNY");
			currencyCodeItem1.setCurrencyCode(token31);
			amountItem1.setCurrencyCode(currencyCodeItem1);
			journalEntryCreateRequestJournalEntryItem1.setAmountInTransactionCurrency(amountItem1);
			
			DebitCreditCode debitCreditCodeItem1=new DebitCreditCode();
			Token token32=new Token();
			token32.setValue("H");
			debitCreditCodeItem1.setDebitCreditCode(token32);
			journalEntryCreateRequestJournalEntryItem1.setDebitCreditCode(debitCreditCodeItem1);
			
			DocumentItemText itemParam4=new DocumentItemText();
			itemParam4.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryItem1.setDocumentItemText(itemParam4);
			
			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems={journalEntryCreateRequestJournalEntryItem,journalEntryCreateRequestJournalEntryItem1};
			journalEntryCreateRequestJournalEntry.setItem(journalEntryCreateRequestJournalEntryItems);
			
			journalEntryCreateRequestMessage.setJournalEntry(journalEntryCreateRequestJournalEntry);
			
			//???JournalEntryCreateRequest?????????????????????
			JournalEntryCreateRequestMessage[] journalEntryCreateRequestMessages={journalEntryCreateRequestMessage};
			//??????JournalEntryCreateRequest??????
			journalEntryCreateRequestBulkMessage.setJournalEntryCreateRequest(journalEntryCreateRequestMessages);
			
			journalEntryBulkCreateRequest.setJournalEntryBulkCreateRequest(journalEntryCreateRequestBulkMessage);
			
			String documentNo="";
			Set<String> set=new HashSet<String>();
			LogItem[] logItem=null;
			//???????????????        
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
				sapZjxshkTb.setPzbh("");
				sapZjxshkTb.setStatus("n");
				sapZjxshkTb.setMsg(set.toString());
			}else{
				sapZjxshkTb.setPzbh(documentNo);
				sapZjxshkTb.setStatus("y");
				sapZjxshkTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapZjxshkTb;
	}
	
	public SapZjxshkTb ms3(SapZjxshkTb sapZjxshkTb,String jxsbm) throws RemoteException{
		try {
			ServiceStub stub = new ServiceStub();
			Authenticator auth = new Authenticator();
			auth.setUsername("JDYUSER");
			auth.setPassword("HfjTP>TgfqQMtHEghZbUxmgeNvrffXw9CoBnATjl");
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
			stub._getServiceClient().getOptions().setProperty(Configuration.CHARACTER_SET_ENCODING, "GBK");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			stub._getServiceClient().getOptions().setProperty(Configuration.HTTP_METHOD, HTTPConstants.HTTP_METHOD_POST);
			
			JournalEntryBulkCreateRequest journalEntryBulkCreateRequest=new JournalEntryBulkCreateRequest(); //???????????????
			JournalEntryCreateRequestBulkMessage journalEntryCreateRequestBulkMessage=new JournalEntryCreateRequestBulkMessage();//????????????MessageHeader???JournalEntryCreateRequest??????
			BusinessDocumentMessageHeader businessDocumentMessageHeader=new BusinessDocumentMessageHeader();//??????MessageHeader??????ID???CreationDateTime
			//????????????MessageHeader??????ID
			BusinessDocumentMessageID businessDocumentMessageIDHeader=new BusinessDocumentMessageID();
			Token tokenHeader=new Token();
			tokenHeader.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDHeader.setBusinessDocumentMessageIDContent(tokenHeader);
			businessDocumentMessageHeader.setID(businessDocumentMessageIDHeader);
			//????????????MessageHeader??????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeHeader=new GLOBAL_DateTime();
			gLOBAL_DateTimeHeader.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeader.setCreationDateTime(gLOBAL_DateTimeHeader);
			//????????????MessageHeader??????
			journalEntryCreateRequestBulkMessage.setMessageHeader(businessDocumentMessageHeader);
			
			//??????JournalEntryCreateRequest????????????
			JournalEntryCreateRequestMessage journalEntryCreateRequestMessage=new JournalEntryCreateRequestMessage();
			//??????JournalEntryCreateRequest???????????????---------
			BusinessDocumentMessageHeader businessDocumentMessageHeaderEntry=new BusinessDocumentMessageHeader();
			//??????JournalEntryCreateRequest???????????????ID
			BusinessDocumentMessageID businessDocumentMessageIDEntry=new BusinessDocumentMessageID();
			Token tokenEntry=new Token();
			tokenEntry.setValue("msg_"+System.currentTimeMillis());
			businessDocumentMessageIDEntry.setBusinessDocumentMessageIDContent(tokenEntry);
			businessDocumentMessageHeaderEntry.setID(businessDocumentMessageIDEntry);
			//??????JournalEntryCreateRequest???????????????CreationDateTime
			GLOBAL_DateTime gLOBAL_DateTimeEntry=new GLOBAL_DateTime();
			gLOBAL_DateTimeEntry.setGLOBAL_DateTime(DateUtils.getNowDateToString()+"T00:00:00.000Z");
			businessDocumentMessageHeaderEntry.setCreationDateTime(gLOBAL_DateTimeEntry);
			journalEntryCreateRequestMessage.setMessageHeader(businessDocumentMessageHeaderEntry);
			
			//??????JournalEntryCreateRequest???????????????-----------
			JournalEntryCreateRequestJournalEntry journalEntryCreateRequestJournalEntry=new JournalEntryCreateRequestJournalEntry();
			//????????????????????????
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
			token5.setValue("SA");
			accountingDocumentTypeCode.setAccountingDocumentTypeCode(token5);
			journalEntryCreateRequestJournalEntry.setAccountingDocumentType(accountingDocumentTypeCode);
			
			DocumentReferenceID documentReferenceID=new DocumentReferenceID();
			Token token6=new Token();
			token6.setValue(sapZjxshkTb.getDjbh());
			documentReferenceID.setDocumentReferenceID(token6);
			journalEntryCreateRequestJournalEntry.setDocumentReferenceID(documentReferenceID);
			
			DocumentHeaderText documentHeaderText=new DocumentHeaderText();
			documentHeaderText.setDocumentHeaderText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(documentHeaderText);
			
			CreatedByUser createdByUser=new CreatedByUser();
			Token token7=new Token();
			token7.setValue(sapZjxshkTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue("C002");
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapZjxshkTb.getZkrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			
			//??????
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem1=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token13=new Token();
			token13.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token13);
			journalEntryCreateRequestJournalEntryDebtorItem1.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token14=new Token();
			token14.setValue("100205");
			businessPartnerInternalID1.setBusinessPartnerInternalID(token14);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebtor(businessPartnerInternalID1);
			
			Amount amountDebtorItem1=new Amount();
			amountDebtorItem1.setAmountContent(new BigDecimal(sapZjxshkTb.getDdje().trim()));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token15=new Token();
			token15.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token15);
			amountDebtorItem1.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setAmountInTransactionCurrency(amountDebtorItem1);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token16=new Token();
			token16.setValue("S");
			debitCreditCodeDebtorItem1.setDebitCreditCode(token16);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment1=new JournalEntryCreateRequestJournalEntryDownPayment();
			SpecialGLCode specialGLCode1=new SpecialGLCode();
			Token token18=new Token();
			token18.setValue("E");
			specialGLCode1.setSpecialGLCode(token18);
			journalEntryCreateRequestJournalEntryDownPayment1.setSpecialGLCode(specialGLCode1);
			journalEntryCreateRequestJournalEntryDebtorItem1.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment1);
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryDebtorItem1.setDocumentItemText(itemParam1);
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem1};
			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems);
			
			//??????
			JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem=new JournalEntryCreateRequestJournalEntryCreditorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem=new BusinessTransactionDocumentItemID();
			Token token19=new Token();
			token19.setValue("2");
			businessTransactionDocumentItemIDDebtorItem.setBusinessTransactionDocumentItemID(token19);
			journalEntryCreateRequestJournalEntryCreditorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem);
			
			BusinessPartnerInternalID businessPartnerInternalID=new BusinessPartnerInternalID();
			Token token20=new Token();
			token20.setValue("C001");
			businessPartnerInternalID.setBusinessPartnerInternalID(token20);
			journalEntryCreateRequestJournalEntryCreditorItem.setCreditor(businessPartnerInternalID);
			
			Amount amountDebtorItem=new Amount();
			amountDebtorItem.setAmountContent(new BigDecimal(-Double.parseDouble(sapZjxshkTb.getDdje().trim())));
			CurrencyCode currencyCodeDebtorItem=new CurrencyCode();
			Token token21=new Token();
			token21.setValue("CNY");
			currencyCodeDebtorItem.setCurrencyCode(token21);
			amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem);
			journalEntryCreateRequestJournalEntryCreditorItem.setAmountInTransactionCurrency(amountDebtorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem=new DebitCreditCode();
			Token token22=new Token();
			token22.setValue("H");
			debitCreditCodeDebtorItem.setDebitCreditCode(token22);
			journalEntryCreateRequestJournalEntryCreditorItem.setDebitCreditCode(debitCreditCodeDebtorItem);
			
			JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
			SpecialGLCode specialGLCode=new SpecialGLCode();
			Token token23=new Token();
			token23.setValue("H");
			specialGLCode.setSpecialGLCode(token23);
			journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
			journalEntryCreateRequestJournalEntryCreditorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
			
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapZjxshkTb.getSkbz());
			journalEntryCreateRequestJournalEntryCreditorItem.setDocumentItemText(itemParam2);
			
			JournalEntryCreateRequestJournalEntryCreditorItem[] journalEntryCreateRequestJournalEntryCreditorItems={journalEntryCreateRequestJournalEntryCreditorItem};
			journalEntryCreateRequestJournalEntry.setCreditorItem(journalEntryCreateRequestJournalEntryCreditorItems);
			
			journalEntryCreateRequestMessage.setJournalEntry(journalEntryCreateRequestJournalEntry);
			
			//???JournalEntryCreateRequest?????????????????????
			JournalEntryCreateRequestMessage[] journalEntryCreateRequestMessages={journalEntryCreateRequestMessage};
			//??????JournalEntryCreateRequest??????
			journalEntryCreateRequestBulkMessage.setJournalEntryCreateRequest(journalEntryCreateRequestMessages);
			
			
			journalEntryBulkCreateRequest.setJournalEntryBulkCreateRequest(journalEntryCreateRequestBulkMessage);
			
			String documentNo="";
			Set<String> set=new HashSet<String>();
			LogItem[] logItem=null;
			//???????????????        
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
				sapZjxshkTb.setPzbh("");
				sapZjxshkTb.setStatus("n");
				sapZjxshkTb.setMsg(set.toString());
			}else{
				sapZjxshkTb.setPzbh(sapZjxshkTb.getPzbh()+","+documentNo);
				sapZjxshkTb.setStatus("y");
				sapZjxshkTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapZjxshkTb;
	}
	
	public Map<String, Object> getSapZjxshkTb(SapZjxshkTb sapZjxshkTb,List<Map<String, Object>> dj1,JDYAPIUtils df1_api) throws ParseException{
		Map<String, Object> rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value",sapZjxshkTb.getDjbh());
		rawData.put("_widget_1611404421677", m1);// ??????????????????
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value",sapZjxshkTb.getPzbh());
		rawData.put("_widget_1593141119943", m2);// ????????????
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value",sapZjxshkTb.getCjr());
		rawData.put("_widget_1564236770978", m4);// ??????
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value",dj1.get(0).get("_widget_1543814802272"));
		rawData.put("_widget_1548057662333", m5);// ??????
		Map<String, Object> m6 = new HashMap<String, Object>();
		m6.put("value",dj1.get(0).get("_widget_1545377155914"));
		rawData.put("_widget_1548057662316", m6);// ????????????
		Map<String, Object> m7 = new HashMap<String, Object>();
		m7.put("value",dj1.get(0).get("_widget_1543814802008"));
		rawData.put("_widget_1548037673470", m7);// ?????????
		Map<String, Object> m8 = new HashMap<String, Object>();
		m8.put("value",dj1.get(0).get("_widget_1543814801964"));
		rawData.put("_widget_1548037673508", m8);// ???????????????
		Map<String, Object> m9 = new HashMap<String, Object>();
		m9.put("value","??????????????????????????????????????????");
		rawData.put("_widget_1548057663778", m9);// ????????????
		Map<String, Object> m10 = new HashMap<String, Object>();
		m10.put("value","101");
		rawData.put("_widget_1548037673908", m10);// ????????????
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("value",dj1.get(0).get("_widget_1545375767934"));
		rawData.put("_widget_1548057662282", m11);// ??????
		Map<String, Object> m12 = new HashMap<String, Object>();
		m12.put("value",dj1.get(0).get("_widget_1544178899217"));
		rawData.put("_widget_1548057662538", m12);// ???
		Map<String, Object> m13 = new HashMap<String, Object>();
		m13.put("value",dj1.get(0).get("_widget_1544178899432"));
		rawData.put("_widget_1548057662521", m13);// ???
		Map<String, Object> m14 = new HashMap<String, Object>();
		m14.put("value",dj1.get(0).get("_widget_1565404958806"));
		rawData.put("_widget_1548057662630", m14);// ???/???
		Map<String, Object> m15 = new HashMap<String, Object>();
		m15.put("value",dj1.get(0).get("_widget_1560762110588"));
		rawData.put("_widget_1569114309696", m15);// ????????????
		Map<String, Object> m16 = new HashMap<String, Object>();
		m16.put("value",dj1.get(0).get("_widget_1566469277094"));
		rawData.put("_widget_1567042903337", m16);// ??????
		Map<String, Object> m17 = new HashMap<String, Object>();
		m17.put("value",dj1.get(0).get("_widget_1548225231293"));
		rawData.put("_widget_1548319045972", m17);// ??????
		Map<String, Object> m18 = new HashMap<String, Object>();
		m18.put("value",dj1.get(0).get("_widget_1561362443098"));
		rawData.put("_widget_1564272870802", m18);// ??????????????????
		Map<String, Object> m19 = new HashMap<String, Object>();
		m19.put("value",dj1.get(0).get("_widget_1563435448247"));
		rawData.put("_widget_1564272870818", m19);// ????????????
		Map<String, Object> m20 = new HashMap<String, Object>();
		m20.put("value",DateUtils.getDateStringToString(DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG)));
		rawData.put("_widget_1548037674843", m20);// ????????????
		Map<String, Object> m21 = new HashMap<String, Object>();
		m21.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR));
		rawData.put("_widget_1564272871299", m21);// ?????????
		Map<String, Object> m22 = new HashMap<String, Object>();
		m22.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
		rawData.put("_widget_1548049038941", m22);// ????????????
		Map<String, Object> m23 = new HashMap<String, Object>();
		m23.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_2_1_STRING));
		rawData.put("_widget_1564272871532", m23);// ???????????????
		Map<String, Object> m24 = new HashMap<String, Object>();
		m24.put("value",sapZjxshkTb.getYsje());
		rawData.put("_widget_1548037675480", m24);// ????????????
		Map<String, Object> m25 = new HashMap<String, Object>();
		m25.put("value",sapZjxshkTb.getYsje());
		rawData.put("_widget_1564390193051", m25);// ??????????????????
		Map<String, Object> m26 = new HashMap<String, Object>();
		m26.put("value",0);
		rawData.put("_widget_1564390192987", m26);// ????????????
		Map<String, Object> m27 = new HashMap<String, Object>();
		m27.put("value",0);
		rawData.put("_widget_1573440693101", m27);// ??????????????????
		Map<String, Object> m28 = new HashMap<String, Object>();
		m28.put("value",0);
		rawData.put("_widget_1564390193003", m28);// ???????????????
		Map<String, Object> m29 = new HashMap<String, Object>();
		m29.put("value","101"+dj1.get(0).get("_widget_1545377155914")+dj1.get(0).get("_widget_1543814801964")+"????????????"+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
		rawData.put("_widget_1565060809315", m29);// ??????????????????????????????(????????????)???????????????+????????????+????????????+????????????+??????
		Map<String, Object> m30 = new HashMap<String, Object>();
		m30.put("value",sapZjxshkTb.getSkbz());
		rawData.put("_widget_1548037674969", m30);// ????????????(??????)
		Map<String, Object> m31 = new HashMap<String, Object>();
		m31.put("value","????????????");
		rawData.put("_widget_1548037675919", m31);// ????????????
		Map<String, Object> m32 = new HashMap<String, Object>();
		m32.put("value","N");
		rawData.put("_widget_1593160180082", m32);// SAP????????????
		Map<String, Object> m33 = new HashMap<String, Object>();
		m33.put("value",sapZjxshkTb.getSklx());
		rawData.put("_widget_1611649660788", m33);// ????????????
		Map<String, Object> resmap=df1_api.createData(rawData);
		return resmap;
	}
	
	
    //????????????????????????(?????????)
    private void pushZHKDDJQR(Map<String, Object> resmap){
    	try {
			//??????M1???????????????????????????????????????
 			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("field", "_widget_1565054283205");
			map1.put("type", "text");
			map1.put("method", "eq");
			map1.put("value",resmap.get("_widget_1565060809315"));
	        condList.add(map1);
	        Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        JDYAPIUtils api = new JDYAPIUtils(APPID, M1_ENTRYID, APIKEY);
	        List<Map<String, Object>> m1 = api.getAllFormData(null,filter);
	        double mb=0.0;
	        String ylj="0";
	        String ydcl = "0.00%";
	        if(m1.size()!=0){
	        	mb=ObjectUtils.getDouble(m1.get(0).get("_widget_1565166083774"));//??????
		        DecimalFormat df = new DecimalFormat("#0.00");
		        ylj=df.format(Double.valueOf(ObjectUtils.getDouble(m1.get(0).get("_widget_1565166084025"))));
		        df = new DecimalFormat("#0.00%");
		        ydcl = df.format(Double.valueOf(ObjectUtils.getDouble(m1.get(0).get("_widget_1564996675036"))));
	        }
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=63fcb97730d44ce40f5fddf216c7fdb921462361284db69060a51088dcb14fe5");
			OapiRobotSendRequest request = new OapiRobotSendRequest();
			request.setMsgtype("actionCard");
			Actioncard actioncard=new Actioncard();
			actioncard.setTitle("????????????");
			String json="**********????????????**********  \n "+
					"  \n ?????????"+resmap.get("_widget_1548037673470")+
					"  \n ?????????" +resmap.get("_widget_1548057662333")+
					"  \n ?????????" +resmap.get("_widget_1548057662282")+
					"  \n ???/??????" +resmap.get("_widget_1548057662630")+
					"  \n ???????????????" +resmap.get("_widget_1548319045972")+
					"  \n ???????????????" +resmap.get("_widget_1564272871532")+
					"  \n ???????????????" +resmap.get("_widget_1548037675480")+
					"  \n ????????????" +ylj+
					"  \n ????????????" +mb+
					"  \n ???????????????" +ydcl+
					"  \n ?????????????????????????????????"+
					"  \n "+resmap.get("_widget_1611649660788");
			
			actioncard.setText(json);
//			List<Btns> btns=new ArrayList<Btns>();
//			Btns btn1=new Btns();
//			btn1.setTitle("??????????????????");
//			btn1.setActionURL("https://www.jiandaoyun.com/app/5cc110c3b3c41744aaa12b2e/entry/5d3cf9e7504d913d3c5f9973");
//			btns.add(btn1);
//			
//			Btns btn2=new Btns();
//			btn2.setTitle("??????????????????");
//			btn2.setActionURL("https://www.jiandaoyun.com/app/5cc110c3b3c41744aaa12b2e/entry/604324f9b8e1a9000738a0e6");
//			btns.add(btn2);
			
//			actioncard.setBtns(btns);
//			actioncard.setBtnOrientation("0");
			request.setActionCard(actioncard);
			client.execute(request);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ???????????????????????????DM1-????????????_(??????)
    private void addZHKDM1(Map<String, Object> resmap){
    	try {
			final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("field", "_widget_1565060809315");
			map1.put("type", "text");
			map1.put("method", "eq");
			map1.put("value",resmap.get("_widget_1565060809315"));
			condList1.add(map1);
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.put("field", "_widget_1611649660788");
			map2.put("type", "text");
			map2.put("method", "ne");
			map2.put("value","????????????");
			condList1.add(map2);
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
	    		if("???????????????".equals(xsskd.get(j).get("_widget_1611649660788").toString())){
	    			xlsje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
	    		}
	        }
	        //??????????????????
			JDYAPIUtils nd_api = new JDYAPIUtils(APPID, ND_ENTRYID, APIKEY);
			List<Map<String, Object>> ndmb = nd_api.getAllFormData(null, null);
			//??????M1???????????????
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
	 			m1.put("value","????????????");
	 			rawData.put("_widget_1565054282382", m1);// ????????????
	 			Map<String, Object> m2 = new HashMap<String, Object>();
	 			m2.put("value","?????????");
	 			rawData.put("_widget_1565054282475", m2);// ????????????
	 			Map<String, Object> m3 = new HashMap<String, Object>();
	 			m3.put("value","??????");
	 			rawData.put("_widget_1565054280172", m3);// ????????????
	 			Map<String, Object> m4 = new HashMap<String, Object>();
	 			m4.put("value",resmap.get("_widget_1548037673508")+"????????????"+"?????????"+"??????");
	 			rawData.put("_widget_1565054282503", m4);// ???????????????????????????
	 			Map<String, Object> m5 = new HashMap<String, Object>();
	 			m5.put("value",resmap.get("_widget_1565060809315"));
	 			rawData.put("_widget_1565054283205", m5);// ?????????????????????????????????????????????+????????????+????????????+????????????+??????
	 			Map<String, Object> m6 = new HashMap<String, Object>();
	 			m6.put("value",resmap.get("_widget_1548037673508"));
	 			rawData.put("_widget_1565054280890", m6);// ???????????????
	 			Map<String, Object> m7 = new HashMap<String, Object>();
	 			m7.put("value",resmap.get("_widget_1548037673470"));
	 			rawData.put("_widget_1564996674855", m7);// ?????????
	 			Map<String, Object> m8 = new HashMap<String, Object>();
	 			m8.put("value",resmap.get("_widget_1548057662333"));
	 			rawData.put("_widget_1565054280408", m8);// ??????
	 			Map<String, Object> m9 = new HashMap<String, Object>();
	 			m9.put("value",resmap.get("_widget_1548057662316"));
	 			rawData.put("_widget_1565054280535", m9);// ????????????
	 			Map<String, Object> m10 = new HashMap<String, Object>();
	 			m10.put("value",resmap.get("_widget_1548057663778"));
	 			rawData.put("_widget_1565054283269", m10);// ??????
	 			Map<String, Object> m11 = new HashMap<String, Object>();
	 			m11.put("value",resmap.get("_widget_1548037673908"));
	 			rawData.put("_widget_1565054283284", m11);// ????????????
	 			Map<String, Object> m12 = new HashMap<String, Object>();
	 			m12.put("value",resmap.get("_widget_1567042903337"));
	 			rawData.put("_widget_1567042226458", m12);// ??????
	 			Map<String, Object> m13 = new HashMap<String, Object>();
	 			m13.put("value",resmap.get("_widget_1548319045972"));
	 			rawData.put("_widget_1564996674896", m13);// ??????
	 			Map<String, Object> m14 = new HashMap<String, Object>();
	 			m14.put("value",resmap.get("_widget_1564272870818"));
	 			rawData.put("_widget_1565054281513", m14);// ??????
	 			Map<String, Object> m15 = new HashMap<String, Object>();
	 			m15.put("value",resmap.get("_widget_1548037674843"));
	 			rawData.put("_widget_1565054281209", m15);// ??????
	 			Map<String, Object> m16 = new HashMap<String, Object>();
	 			m16.put("value",resmap.get("_widget_1564272871299"));
	 			rawData.put("_widget_1565054282027", m16);// ???
	 			Map<String, Object> m17 = new HashMap<String, Object>();
	 			m17.put("value",resmap.get("_widget_1548049038941"));
	 			rawData.put("_widget_1564996675064", m17);// ??????
	 			Map<String, Object> m18 = new HashMap<String, Object>();
	 			m18.put("value",resmap.get("_widget_1564272871532"));
	 			rawData.put("_widget_1565054281937", m18);// ?????????
	 			Map<String, Object> m19 = new HashMap<String, Object>();
	 			m19.put("value",0);
	 			rawData.put("_widget_1565166083759", m19);// ?????????
	 			Map<String, Object> m20 = new HashMap<String, Object>();
	 			m20.put("value",0);
	 			rawData.put("_widget_1565166083774", m20);// ??????
	 			Map<String, Object> m21 = new HashMap<String, Object>();
	 			m21.put("value",skje);
	 			rawData.put("_widget_1565166084025", m21);// ??????
	 			Map<String, Object> m22 = new HashMap<String, Object>();
	 			m22.put("value",0);
	 			rawData.put("_widget_1564996675036", m22);// ?????????
	 			Map<String, Object> m23 = new HashMap<String, Object>();
	 			m23.put("value",rcfdje);
	 			rawData.put("_widget_1565166084168", m23);// ??????(????????????)
	 			Map<String, Object> m24 = new HashMap<String, Object>();
	 			m24.put("value",0);
	 			rawData.put("_widget_1565065411232", m24);// ??????????????????
	 			Map<String, Object> m25 = new HashMap<String, Object>();
	 			m25.put("value",0);
	 			rawData.put("_widget_1564996675255", m25);// (??????)????????????
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
	 			rawData.put("_widget_1565074205311", m26);// (??????)????????????
	 			Map<String, Object> m27 = new HashMap<String, Object>();
	 			m27.put("value",resmap.get("updateTime"));
	 			rawData.put("_widget_1565074204963", m27);// ??????????????????
	 			Map<String, Object> m28 = new HashMap<String, Object>();
	 			m28.put("value",resmap.get("_widget_1569114309696"));
	 			rawData.put("_widget_1573095139039", m28);// ????????????
	 			Map<String, Object> m30 = new HashMap<String, Object>();
	 			m30.put("value",resmap.get("_widget_1548057662282"));
	 			rawData.put("_widget_1588925232200", m30);// ??????
	 			Map<String, Object> m31 = new HashMap<String, Object>();
	 			m31.put("value",resmap.get("_widget_1548057662538"));
	 			rawData.put("_widget_1588925232215", m31);// ??????
	 			Map<String, Object> m32 = new HashMap<String, Object>();
	 			m32.put("value",resmap.get("_widget_1548057662521"));
	 			rawData.put("_widget_1588925232243", m32);// ??????
	 			Map<String, Object> m33 = new HashMap<String, Object>();
	 			m33.put("value",0);
	 			rawData.put("_widget_1614911874299", m33);// ??????????????????
	 			Map<String, Object> m34 = new HashMap<String, Object>();
	 			m34.put("value",0);
	 			rawData.put("_widget_1614911874278", m34);// ??????????????????
	 			Map<String, Object> m35 = new HashMap<String, Object>();
	 			m35.put("value",ndmb.get(0).get("_widget_1614908475285"));
	 			rawData.put("_widget_1614996372547", m35);// ????????????
	 			Map<String, Object> m36 = new HashMap<String, Object>();
	 			m36.put("value",0);
	 			rawData.put("_widget_1611902543094", m36);// ???????????????
	 			Map<String, Object> m37 = new HashMap<String, Object>();
	 			m37.put("value",xlsje);
	 			rawData.put("_widget_1611902543190", m37);// ?????????????????????
	 			Map<String, Object> m38 = new HashMap<String, Object>();
	 			m38.put("value",0);
	 			rawData.put("_widget_1611902543438", m38);// ????????????????????????
	 			dmapi.createForData(rawData);// ??????????????????DM1-????????????_(??????);
	        }else if(listM1.size()==1){
 		       DecimalFormat df = new DecimalFormat("#0.0000");  
 		       Map<String, Object> rawData = new HashMap<String, Object>();
 		       Map<String, Object> m1 = new HashMap<String, Object>();
 		       m1.put("value",skje);
 		       rawData.put("_widget_1565166084025", m1);// ??????
 		       
 		       double mb=Double.valueOf(listM1.get(0).get("_widget_1565166083774").toString());
 		       Map<String, Object> m2 = new HashMap<String, Object>();
 		       if(mb!=0.0){
 		    	  m2.put("value",df.format(skje/mb));
 		       }else{
 		    	  m2.put("value",0);
 		       }
 		       rawData.put("_widget_1564996675036", m2);// ?????????
 		       
 		       Map<String, Object> m3 = new HashMap<String, Object>();
 		       m3.put("value",rcfdje);
 		       rawData.put("_widget_1565166084168", m3);// ??????(????????????)
 		       
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
 		       rawData.put("_widget_1565074205311", m6);// (??????)????????????
 		       
 		       Map<String, Object> m7 = new HashMap<String, Object>();
 		       m7.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG));
 		       rawData.put("_widget_1565074204963", m7);// ??????????????????
 		       
  	 		   Map<String, Object> m8 = new HashMap<String, Object>();
 		       m8.put("value",xlsje);
 		       rawData.put("_widget_1611902543190", m8);// ?????????????????????
 		       
 		       Double xlsmb=ObjectUtils.getDouble(listM1.get(0).get("_widget_1611902543094"));
 		       Map<String, Object> m9 = new HashMap<String, Object>();
 		       m9.put("value",xlsmb!=0.0?df.format(xlsje/xlsmb):0);
 		       rawData.put("_widget_1611902543438", m9);// ????????????????????????
     			
 		       dmapi.updateData(listM1.get(0).get("_id").toString(), rawData);// ??????????????????DM1-????????????_(??????)
	        }
		    //??????????????????????????????????????????(??????)????????????
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
	        map6.put("value",DateUtils.getNowDateToString("yyyy???M???"));
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
	       //??????????????????????????????
	       int mds=0;
	       double hkje=0.0;
	       for (int i = 0; i < M1.size(); i++) {
	    	   mds+=Integer.valueOf(M1.get(i).get("_widget_1565166083759").toString());
	    	   hkje+=Double.valueOf(M1.get(i).get("_widget_1565166084168").toString());
	       }
	       //??????
	       for(int i = 0; i < M1.size(); i++){
	    	   double hk=Double.valueOf(M1.get(i).get("_widget_1565166084025").toString());
	    	   Map<String, Object> rawData = new HashMap<String, Object>();
 		       Map<String, Object> m1 = new HashMap<String, Object>();
 		       double djfdhk=0.0;
 		       if(mds!=0.0){
 		    	  djfdhk=Double.valueOf(df.format(hkje/mds));
 		       }
 		       m1.put("value",djfdhk);
 		       rawData.put("_widget_1565065411232", m1);// ??????????????????
     			
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
 		       rawData.put("_widget_1564996675255", m2);// (??????)????????????
 		       dmapi.updateData(M1.get(i).get("_id").toString(), rawData);// ??????????????????DM1-????????????_(??????)
	       }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl,String zjxshkStatus, String zkrq) {
		XSSFWorkbook wb = null;
		try {
			SapZjxshkTbExample example = new SapZjxshkTbExample();
			Criteria criteria=example.createCriteria();
			if(zjxshkStatus!=null && !"".equals(zjxshkStatus)){
				criteria.andStatusEqualTo(zjxshkStatus);
			}
			if(zkrq!=null && !"".equals(zkrq)){
				criteria.andZkrqLike(zkrq+"%");
			}
			// ????????????
			List<SapZjxshkTb> item = sapZjxshkTbMapper.selectByExample(example);

			File fi = new File(excelUrl);
			// ??????excel??????
			wb = new XSSFWorkbook(new FileInputStream(fi));
			// ????????????????????????sheet??????
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowIndex = 1;
			for (SapZjxshkTb sapZjxshkTb : item) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}

				XSSFCell cell0 = row.getCell(0);
				if (null == cell0) {
					cell0 = row.createCell(0);
				}
				cell0.setCellValue(sapZjxshkTb.getJxsbm());

				XSSFCell cell1 = row.getCell(1);
				if (null == cell1) {
					cell1 = row.createCell(1);
				}
				cell1.setCellValue(sapZjxshkTb.getZkrq());

				XSSFCell cell2 = row.getCell(2);
				if (null == cell2) {
					cell2 = row.createCell(2);
				}
				cell2.setCellValue(sapZjxshkTb.getDdje());

				XSSFCell cell3 = row.getCell(3);
				if (null == cell3) {
					cell3 = row.createCell(3);
				}
				cell3.setCellValue(sapZjxshkTb.getSkbz());

				XSSFCell cell4 = row.getCell(4);
				if (null == cell4) {
					cell4 = row.createCell(4);
				}
				cell4.setCellValue(sapZjxshkTb.getYsje());
				
				XSSFCell cell5 = row.getCell(5);
				if (null == cell5) {
					cell5 = row.createCell(5);
				}
				cell5.setCellValue(sapZjxshkTb.getFwf());
				
				XSSFCell cell6 = row.getCell(6);
				if (null == cell6) {
					cell6 = row.createCell(6);
				}
				cell6.setCellValue(sapZjxshkTb.getCjr());
				
				XSSFCell cell7 = row.getCell(7);
				if (null == cell7) {
					cell7 = row.createCell(7);
				}
				cell7.setCellValue(sapZjxshkTb.getLrzx());
				
				XSSFCell cell8 = row.getCell(8);
				if (null == cell8) {
					cell8 = row.createCell(8);
				}
				cell8.setCellValue(sapZjxshkTb.getSklx());
				
				rowIndex++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

}
