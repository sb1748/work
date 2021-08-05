package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapDskqzTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapDskqzTb;
import com.jxszj.pojo.sap.SapDskqzTbExample;
import com.jxszj.pojo.sap.SapDskqzTbExample.Criteria;
import com.jxszj.service.sap.IDskqzService;
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
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDebtorItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPayment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryDownPaymentCreditor;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.SpecialGLCode;

@Service
public class DskqzServiceImpl implements IDskqzService {
	
	@Autowired
	private SapDskqzTbMapper sapDskqzTbMapper;

	@Override
	public int uploadDskExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		DecimalFormat df = new DecimalFormat("#0.00");
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			
			List<SapDskqzTb> list=new ArrayList<SapDskqzTb>();
			for (int i = 1; i < listob.size(); i++) {
				SapDskqzTb sapDskqzTb=new SapDskqzTb();
				sapDskqzTb.setZz(ObjectUtils.getString(listob.get(i).get(0)));
				sapDskqzTb.setJfjzm(ObjectUtils.getString(listob.get(i).get(1)));
				sapDskqzTb.setJfkm(ObjectUtils.getString(listob.get(i).get(2)));
				sapDskqzTb.setJfzzbs(ObjectUtils.getString(listob.get(i).get(3)));
				sapDskqzTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
				sapDskqzTb.setDfjzm(ObjectUtils.getString(listob.get(i).get(5)));
				sapDskqzTb.setGys(ObjectUtils.getString(listob.get(i).get(6)));
				sapDskqzTb.setDfzzbs(ObjectUtils.getString(listob.get(i).get(7)));
				sapDskqzTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(8))));
				sapDskqzTb.setCjr(ObjectUtils.getString(listob.get(i).get(9)));
				sapDskqzTb.setDate(DateUtils.getNowDate());
				sapDskqzTb.setPzrq(ObjectUtils.getString(listob.get(i).get(10)));
				sapDskqzTb.setGzrq(ObjectUtils.getString(listob.get(i).get(11)));
				sapDskqzTb.setHtext(ObjectUtils.getString(listob.get(i).get(12)));
				sapDskqzTb.setText(ObjectUtils.getString(listob.get(i).get(13)));
				list.add(sapDskqzTb);
			}
			
			List<SapDskqzTb> lists=new ArrayList<SapDskqzTb>();
			for (int i = 0; i < list.size(); i++) {
				lists.add(sendSAPPost(list.get(i)));
			}
			num=sapDskqzTbMapper.insertByBatch(lists);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	@Override
	public void sendPost(List<Integer> list) {
		SapDskqzTbExample example = new SapDskqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		List<SapDskqzTb> sapDskqzTbs=sapDskqzTbMapper.selectByExample(example);
		for (int i = 0; i < sapDskqzTbs.size(); i++) {
			sapDskqzTbMapper.updateByPrimaryKey(sendSAPPost(sapDskqzTbs.get(i)));
		}
		
	}
	
	
	
	//同步到SAP过账（含清账）
	public SapDskqzTb sendSAPPost(SapDskqzTb sapDskqzTb){
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
			token7.setValue(sapDskqzTb.getCjr());
			createdByUser.setCreatedByUser(token7);
			journalEntryCreateRequestJournalEntry.setCreatedByUser(createdByUser);
			
			CompanyCodeID companyCodeID=new CompanyCodeID();
			Token token8=new Token();
			token8.setValue(sapDskqzTb.getZz());
			companyCodeID.setCompanyCodeID(token8);
			journalEntryCreateRequestJournalEntry.setCompanyCode(companyCodeID);
			
			Date documentDate=new Date();
			documentDate.setDate(sapDskqzTb.getPzrq());
			journalEntryCreateRequestJournalEntry.setDocumentDate(documentDate);
			
			Date postingDate=new Date();
			postingDate.setDate(sapDskqzTb.getGzrq());
			journalEntryCreateRequestJournalEntry.setPostingDate(postingDate);
			
			DocumentHeaderText param=new DocumentHeaderText();
			param.setDocumentHeaderText(sapDskqzTb.getHtext());
			journalEntryCreateRequestJournalEntry.setDocumentHeaderText(param);

			//债务人
			JournalEntryCreateRequestJournalEntryDebtorItem journalEntryCreateRequestJournalEntryDebtorItem=new JournalEntryCreateRequestJournalEntryDebtorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem1=new BusinessTransactionDocumentItemID();
			Token token9=new Token();
			token9.setValue("1");
			businessTransactionDocumentItemIDDebtorItem1.setBusinessTransactionDocumentItemID(token9);
			journalEntryCreateRequestJournalEntryDebtorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem1);
			
			String jfkm="";
			if(sapDskqzTb.getJfkm().contains("(")){
				jfkm=sapDskqzTb.getJfkm().substring(0, sapDskqzTb.getJfkm().indexOf("("));
			}else if(sapDskqzTb.getJfkm().contains("（")){
				jfkm=sapDskqzTb.getJfkm().substring(0, sapDskqzTb.getJfkm().indexOf("（"));
			}else{
				jfkm=sapDskqzTb.getJfkm();
			}
			BusinessPartnerInternalID businessPartnerInternalID1=new BusinessPartnerInternalID();
			Token token10=new Token();
			token10.setValue(jfkm.trim());
			businessPartnerInternalID1.setBusinessPartnerInternalID(token10);
			journalEntryCreateRequestJournalEntryDebtorItem.setDebtor(businessPartnerInternalID1);
			
			Amount amountDebtorItem=new Amount();
			amountDebtorItem.setAmountContent(new BigDecimal(Double.parseDouble(sapDskqzTb.getJfje().trim())));
			CurrencyCode currencyCodeDebtorItem1=new CurrencyCode();
			Token token11=new Token();
			token11.setValue("CNY");
			currencyCodeDebtorItem1.setCurrencyCode(token11);
			amountDebtorItem.setCurrencyCode(currencyCodeDebtorItem1);
			journalEntryCreateRequestJournalEntryDebtorItem.setAmountInTransactionCurrency(amountDebtorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem1=new DebitCreditCode();
			Token token12=new Token();
			token12.setValue("S");
			debitCreditCodeDebtorItem1.setDebitCreditCode(token12);
			journalEntryCreateRequestJournalEntryDebtorItem.setDebitCreditCode(debitCreditCodeDebtorItem1);
			
			if(!"".equals(sapDskqzTb.getJfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPayment journalEntryCreateRequestJournalEntryDownPayment=new JournalEntryCreateRequestJournalEntryDownPayment();
    			SpecialGLCode specialGLCode=new SpecialGLCode();
    			Token token13=new Token();
    			token13.setValue(sapDskqzTb.getJfzzbs().trim());
    			specialGLCode.setSpecialGLCode(token13);
    			journalEntryCreateRequestJournalEntryDownPayment.setSpecialGLCode(specialGLCode);
    			journalEntryCreateRequestJournalEntryDebtorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPayment);
			}
			
			DocumentItemText itemParam1=new DocumentItemText();
			itemParam1.setDocumentItemText(sapDskqzTb.getText());
			journalEntryCreateRequestJournalEntryDebtorItem.setDocumentItemText(itemParam1);
			
			JournalEntryCreateRequestJournalEntryDebtorItem[] journalEntryCreateRequestJournalEntryDebtorItems={journalEntryCreateRequestJournalEntryDebtorItem};
			journalEntryCreateRequestJournalEntry.setDebtorItem(journalEntryCreateRequestJournalEntryDebtorItems);
			
			//贷方
			//债权人
			JournalEntryCreateRequestJournalEntryCreditorItem journalEntryCreateRequestJournalEntryCreditorItem=new JournalEntryCreateRequestJournalEntryCreditorItem();
			BusinessTransactionDocumentItemID businessTransactionDocumentItemIDDebtorItem2=new BusinessTransactionDocumentItemID();
			Token token14=new Token();
			token14.setValue("2");
			businessTransactionDocumentItemIDDebtorItem2.setBusinessTransactionDocumentItemID(token14);
			journalEntryCreateRequestJournalEntryCreditorItem.setReferenceDocumentItem(businessTransactionDocumentItemIDDebtorItem2);
			
			String gys="";
			if(sapDskqzTb.getGys().contains("(")){
				gys=sapDskqzTb.getGys().substring(0, sapDskqzTb.getGys().indexOf("("));
			}else if(sapDskqzTb.getGys().contains("（")){
				gys=sapDskqzTb.getGys().substring(0, sapDskqzTb.getGys().indexOf("（"));
			}else{
				gys=sapDskqzTb.getGys();
			}
			BusinessPartnerInternalID businessPartnerInternalID2=new BusinessPartnerInternalID();
			Token token15=new Token();
			token15.setValue(gys.trim());
			businessPartnerInternalID2.setBusinessPartnerInternalID(token15);
			journalEntryCreateRequestJournalEntryCreditorItem.setCreditor(businessPartnerInternalID2);;
			
			Amount amountCreditorItem=new Amount();
			amountCreditorItem.setAmountContent(new BigDecimal(-Double.parseDouble(sapDskqzTb.getDfje().trim())));
			CurrencyCode currencyCodeDebtorItem2=new CurrencyCode();
			Token token16=new Token();
			token16.setValue("CNY");
			currencyCodeDebtorItem2.setCurrencyCode(token16);
			amountCreditorItem.setCurrencyCode(currencyCodeDebtorItem2);
			journalEntryCreateRequestJournalEntryCreditorItem.setAmountInTransactionCurrency(amountCreditorItem);
			
			DebitCreditCode debitCreditCodeDebtorItem2=new DebitCreditCode();
			Token token17=new Token();
			token17.setValue("H");
			debitCreditCodeDebtorItem2.setDebitCreditCode(token17);
			journalEntryCreateRequestJournalEntryCreditorItem.setDebitCreditCode(debitCreditCodeDebtorItem2);
			//总账标识
			if(!"".equals(sapDskqzTb.getDfzzbs())){
				JournalEntryCreateRequestJournalEntryDownPaymentCreditor journalEntryCreateRequestJournalEntryDownPaymentCreditor=new JournalEntryCreateRequestJournalEntryDownPaymentCreditor();
				SpecialGLCode specialGLCode=new SpecialGLCode();
				Token token18=new Token();
				token18.setValue(sapDskqzTb.getDfzzbs().trim());
				specialGLCode.setSpecialGLCode(token18);
				journalEntryCreateRequestJournalEntryDownPaymentCreditor.setSpecialGLCode(specialGLCode);
				journalEntryCreateRequestJournalEntryCreditorItem.setDownPaymentTerms(journalEntryCreateRequestJournalEntryDownPaymentCreditor);
			}
			
			DocumentItemText itemParam2=new DocumentItemText();
			itemParam2.setDocumentItemText(sapDskqzTb.getText());
			journalEntryCreateRequestJournalEntryCreditorItem.setDocumentItemText(itemParam2);
			
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
				sapDskqzTb.setPzbm("");
				sapDskqzTb.setStatus("n");
				sapDskqzTb.setMsg(set.toString());
			}else{
				sapDskqzTb.setPzbm(documentNo);
				sapDskqzTb.setStatus("y");
				sapDskqzTb.setMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapDskqzTb;
	}


	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String dskqzStatus) {
		SapDskqzTbExample example = new SapDskqzTbExample();
		if(dskqzStatus!=null && !"".equals(dskqzStatus)){
			Criteria criteria=example.createCriteria();
			criteria.andStatusEqualTo(dskqzStatus);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapDskqzTb> item = sapDskqzTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapDskqzTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapDskqzTbExample example = new SapDskqzTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapDskqzTbMapper.deleteByExample(example);
		return num;
	}
	
	
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl) {
		XSSFWorkbook wb = null;
		try {
			SapDskqzTbExample example = new SapDskqzTbExample();
			List<SapDskqzTb> item=sapDskqzTbMapper.selectByExample(example);

			File fi = new File(excelUrl);
			// 读取excel模板
			wb = new XSSFWorkbook(new FileInputStream(fi));
			// 读取了模板内所有sheet内容
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowIndex = 1;
			for (SapDskqzTb sapDskqzTb : item) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}

				XSSFCell cell0 = row.getCell(0);
				if (null == cell0) {
					cell0 = row.createCell(0);
				}
				cell0.setCellValue(sapDskqzTb.getPzbm());

				XSSFCell cell1 = row.getCell(1);
				if (null == cell1) {
					cell1 = row.createCell(1);
				}
				cell1.setCellValue(sapDskqzTb.getZz());

				XSSFCell cell2 = row.getCell(2);
				if (null == cell2) {
					cell2 = row.createCell(2);
				}
				cell2.setCellValue(sapDskqzTb.getJfjzm());

				XSSFCell cell3 = row.getCell(3);
				if (null == cell3) {
					cell3 = row.createCell(3);
				}
				cell3.setCellValue(sapDskqzTb.getJfkm());

				XSSFCell cell4 = row.getCell(4);
				if (null == cell4) {
					cell4 = row.createCell(4);
				}
				cell4.setCellValue(sapDskqzTb.getJfzzbs());
				
				XSSFCell cell5 = row.getCell(5);
				if (null == cell5) {
					cell5 = row.createCell(5);
				}
				cell5.setCellValue(sapDskqzTb.getJfje());
				
				XSSFCell cell6 = row.getCell(6);
				if (null == cell6) {
					cell6 = row.createCell(6);
				}
				cell6.setCellValue(sapDskqzTb.getDfjzm());
				
				XSSFCell cell7 = row.getCell(7);
				if (null == cell7) {
					cell7 = row.createCell(7);
				}
				cell7.setCellValue(sapDskqzTb.getGys());
				
				XSSFCell cell8 = row.getCell(8);
				if (null == cell8) {
					cell8 = row.createCell(8);
				}
				cell8.setCellValue(sapDskqzTb.getDfzzbs());
				
				XSSFCell cell9 = row.getCell(9);
				if (null == cell9) {
					cell9 = row.createCell(9);
				}
				cell9.setCellValue(sapDskqzTb.getDfje());
				
				XSSFCell cell10 = row.getCell(10);
				if (null == cell10) {
					cell10 = row.createCell(10);
				}
				cell10.setCellValue(sapDskqzTb.getCjr());

				XSSFCell cell11 = row.getCell(11);
				if (null == cell11) {
					cell11 = row.createCell(11);
				}
				cell11.setCellValue(sapDskqzTb.getPzrq());
				
				XSSFCell cell12 = row.getCell(12);
				if (null == cell12) {
					cell12 = row.createCell(12);
				}
				cell12.setCellValue(sapDskqzTb.getGzrq());
				
				XSSFCell cell13 = row.getCell(13);
				if (null == cell13) {
					cell13 = row.createCell(13);
				}
				cell13.setCellValue(sapDskqzTb.getHtext());
				
				XSSFCell cell14 = row.getCell(14);
				if (null == cell14) {
					cell14 = row.createCell(14);
				}
				cell14.setCellValue(sapDskqzTb.getText());
				
				rowIndex++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

}
