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
import com.jxszj.mapper.sap.SapZzfyjtTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapZzfyjtTb;
import com.jxszj.pojo.sap.SapZzfyjtTbExample;
import com.jxszj.pojo.sap.SapZzfyjtTbExample.Criteria;
import com.jxszj.service.sap.IZzfyjtService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.ObjectUtils;
import com.jxszj.utils.ServiceStub;
import com.jxszj.utils.ServiceStub.AccountingBusinessTransactionTypeCode;
import com.jxszj.utils.ServiceStub.AccountingDocumentTypeCode;
import com.jxszj.utils.ServiceStub.Amount;
import com.jxszj.utils.ServiceStub.BusinessDocumentMessageHeader;
import com.jxszj.utils.ServiceStub.BusinessDocumentMessageID;
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
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItem;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestJournalEntryItemAccountAssignment;
import com.jxszj.utils.ServiceStub.JournalEntryCreateRequestMessage;
import com.jxszj.utils.ServiceStub.LogItem;
import com.jxszj.utils.ServiceStub.OriginalReferenceDocumentLogicalSystem;
import com.jxszj.utils.ServiceStub.ReasonCode;
import com.jxszj.utils.ServiceStub.TaxDeterminationDate_GFN;

@Service
public class ZzfyjtServiceImpl implements IZzfyjtService {
	
	@Autowired
	private SapZzfyjtTbMapper sapZzfyjtTbMapper;

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
			Set<String> number=new HashSet<String>();
			for (int i = 1; i < listob.size(); i++) {
				if("".equals(ObjectUtils.getString(listob.get(i).get(0)))){
					return -1;
				}
				number.add(ObjectUtils.getString(listob.get(i).get(0)));
			}
			for(String str:number){
				List<List<SapZzfyjtTb>> listobs = new ArrayList<List<SapZzfyjtTb>>();
				List<SapZzfyjtTb> list=null;
				for (int i = 1; i < listob.size(); i++) {
					if(str.equals(ObjectUtils.getString(listob.get(i).get(0)))){
						list=new ArrayList<SapZzfyjtTb>();
						SapZzfyjtTb sapZzfyjtTb=new SapZzfyjtTb();
						sapZzfyjtTb.setZz(ObjectUtils.getString(listob.get(i).get(1)));
						sapZzfyjtTb.setJfkm(ObjectUtils.getString(listob.get(i).get(2)));
						sapZzfyjtTb.setJfyydm(ObjectUtils.getString(listob.get(i).get(3)));
						sapZzfyjtTb.setJfje(df.format(ObjectUtils.getDouble(listob.get(i).get(4))));
						sapZzfyjtTb.setCbzx(ObjectUtils.getString(listob.get(i).get(5)));
						sapZzfyjtTb.setDfkm(ObjectUtils.getString(listob.get(i).get(6)));
						sapZzfyjtTb.setDfyydm(ObjectUtils.getString(listob.get(i).get(7)));
						sapZzfyjtTb.setDfje(df.format(ObjectUtils.getDouble(listob.get(i).get(8))));
						sapZzfyjtTb.setDate(DateUtils.getNowDate());
						sapZzfyjtTb.setCjr(ObjectUtils.getString(listob.get(i).get(9)));
						sapZzfyjtTb.setPzrq(ObjectUtils.getString(listob.get(i).get(10)));
						sapZzfyjtTb.setGzrq(ObjectUtils.getString(listob.get(i).get(11)));
						sapZzfyjtTb.setHtext(ObjectUtils.getString(listob.get(i).get(12)));
						sapZzfyjtTb.setText(ObjectUtils.getString(listob.get(i).get(13)));
						
						list.add(sapZzfyjtTb);
						listobs.add(list);
					}
					
				}
				List<String> lists=sendSAPPost(listobs);
				for (int i = 0; i < listobs.size(); i++) {
					listobs.get(i).get(0).setPzbh(lists.get(0));
					listobs.get(i).get(0).setStatus(lists.get(1));
					listobs.get(i).get(0).setMsg(lists.get(2));
					num+=sapZzfyjtTbMapper.insertByBatch(listobs.get(i));
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	//同步到SAP过账（含清账）
	public List<String> sendSAPPost(List<List<SapZzfyjtTb>> listobs){
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
			
			TaxDeterminationDate_GFN taxDeterminationDate=new TaxDeterminationDate_GFN();
			taxDeterminationDate.setTaxDeterminationDate_GFN(listobs.get(0).get(0).getGzrq());
			journalEntryCreateRequestJournalEntry.setTaxDeterminationDate(taxDeterminationDate);
			
			
//			JournalEntryCreateRequestJournalEntryItem[] journalEntryCreateRequestJournalEntryItems=new JournalEntryCreateRequestJournalEntryItem[listobs.size()];
			List<JournalEntryCreateRequestJournalEntryItem> journalEntryCreateRequestJournalEntryItems=new ArrayList<JournalEntryCreateRequestJournalEntryItem>();
			for (int i = 0; i < listobs.size(); i++) {
				//借方
				JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
				BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem=new BusinessTransactionDocumentItemID();
				Token token9=new Token();
				token9.setValue(i+"");
				businessTransactionDocumentItemIDItem.setBusinessTransactionDocumentItemID(token9);
				journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem);
				
				
				String km=listobs.get(i).get(0).getJfkm();
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
				if(!"".equals(cbzx)){
					//成本中心
					JournalEntryCreateRequestJournalEntryItemAccountAssignment accountAssignment=new JournalEntryCreateRequestJournalEntryItemAccountAssignment();
					CostCentreID costCentreID=new CostCentreID();
					Token costCentreIDToken=new Token();
					costCentreIDToken.setValue(cbzx.trim());
					costCentreID.setCostCentreID(costCentreIDToken);
					accountAssignment.setCostCenter(costCentreID);
					journalEntryCreateRequestJournalEntryItem.setAccountAssignment(accountAssignment);
				}
				
				DocumentItemText itemParam=new DocumentItemText();
				itemParam.setDocumentItemText(listobs.get(i).get(0).getText());
				journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam);
				
				String jfyydm=listobs.get(i).get(0).getJfyydm();
				if(!"".equals(jfyydm)){
					ReasonCode reasonCode=new ReasonCode();
	    			reasonCode.setReasonCode(jfyydm);
	    			journalEntryCreateRequestJournalEntryItem.setReasonCode(reasonCode); //原因代码
				}

				
				journalEntryCreateRequestJournalEntryItems.add(journalEntryCreateRequestJournalEntryItem);
			}
			
			for (int i = 0; i < listobs.size(); i++) {
				//贷方
				JournalEntryCreateRequestJournalEntryItem journalEntryCreateRequestJournalEntryItem=new JournalEntryCreateRequestJournalEntryItem();
				BusinessTransactionDocumentItemID businessTransactionDocumentItemIDItem1=new BusinessTransactionDocumentItemID();
				Token token13=new Token();
				token13.setValue(i+"");
				businessTransactionDocumentItemIDItem1.setBusinessTransactionDocumentItemID(token13);
				journalEntryCreateRequestJournalEntryItem.setReferenceDocumentItem(businessTransactionDocumentItemIDItem1);
				
				
				String dfkm=listobs.get(i).get(0).getDfkm();
				ChartOfAccountsItemCode chartOfAccountsItemCode1=new ChartOfAccountsItemCode();
				Token token14=new Token();
				token14.setValue(dfkm.trim());
				chartOfAccountsItemCode1.setChartOfAccountsItemCodeContent(token14);
				journalEntryCreateRequestJournalEntryItem.setGLAccount(chartOfAccountsItemCode1);
				
				
				Amount amountItem1=new Amount();
				amountItem1.setAmountContent(new BigDecimal(-Double.valueOf(listobs.get(i).get(0).getDfje().trim()))); //贷方金额
				CurrencyCode currencyCodeItem1=new CurrencyCode();//币种
				Token token15=new Token();
				token15.setValue("CNY");
				currencyCodeItem1.setCurrencyCode(token15);
				amountItem1.setCurrencyCode(currencyCodeItem1);
				journalEntryCreateRequestJournalEntryItem.setAmountInTransactionCurrency(amountItem1);
				
				DebitCreditCode debitCreditCodeItem1=new DebitCreditCode();
				Token token16=new Token();
				token16.setValue("H");
				debitCreditCodeItem1.setDebitCreditCode(token16);
				journalEntryCreateRequestJournalEntryItem.setDebitCreditCode(debitCreditCodeItem1);
				
				DocumentItemText itemParam1=new DocumentItemText();
				itemParam1.setDocumentItemText(listobs.get(i).get(0).getText());
				journalEntryCreateRequestJournalEntryItem.setDocumentItemText(itemParam1);
				
				String dfyydm=listobs.get(i).get(0).getDfyydm();
				if(!"".equals(dfyydm)){
					ReasonCode reasonCode=new ReasonCode();
	    			reasonCode.setReasonCode(dfyydm);
	    			journalEntryCreateRequestJournalEntryItem.setReasonCode(reasonCode); //原因代码
				}
				
				journalEntryCreateRequestJournalEntryItems.add(journalEntryCreateRequestJournalEntryItem);
			}
			JournalEntryCreateRequestJournalEntryItem[] item=new JournalEntryCreateRequestJournalEntryItem[journalEntryCreateRequestJournalEntryItems.size()];
			journalEntryCreateRequestJournalEntryItems.toArray(item);
			journalEntryCreateRequestJournalEntry.setItem(item);
			
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

	@Override
	public EUDataGridResult getSAPPost(int page, int rows,String zzfyjtStatus) {
		SapZzfyjtTbExample example = new SapZzfyjtTbExample();
		if(zzfyjtStatus!=null && !"".equals(zzfyjtStatus)){
			Criteria criteria=example.createCriteria();
			criteria.andStatusEqualTo(zzfyjtStatus);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapZzfyjtTb> item = sapZzfyjtTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapZzfyjtTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int delete(List<Integer> list) {
		SapZzfyjtTbExample example = new SapZzfyjtTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(list);
		int num=sapZzfyjtTbMapper.deleteByExample(example);
		return num;
	}

}
