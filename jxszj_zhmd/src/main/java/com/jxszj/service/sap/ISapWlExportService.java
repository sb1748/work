package com.jxszj.service.sap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ISapWlExportService {

	public XSSFWorkbook getXSSFWorkbook(String excelUrl);
}
