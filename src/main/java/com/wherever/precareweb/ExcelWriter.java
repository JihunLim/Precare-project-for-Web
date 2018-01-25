package com.wherever.precareweb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.wherever.precareweb.dto.UserDto;


public class ExcelWriter {
	private String filePath;
	private String fileName;
	private ArrayList<String> que_list;
	private ArrayList<String> and_list;
	private UserDto userInfo;
	
	public ExcelWriter(String filePath, String fileName, ArrayList<String> que_list, ArrayList<String> and_list,
			UserDto userInfo) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.que_list = que_list;
		this.and_list = and_list;
		this.userInfo = userInfo;
	}


	public ExcelWriter() {
		// TODO Auto-generated constructor stub
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public ArrayList<String> getQue_list() {
		return que_list;
	}


	public void setQue_list(ArrayList<String> que_list) {
		this.que_list = que_list;
	}


	public ArrayList<String> getAnd_list() {
		return and_list;
	}


	public void setAnd_list(ArrayList<String> and_list) {
		this.and_list = and_list;
	}


	public void writeData() throws Exception {
        try {
            	this.filePath = "./"; 		//file ���� ��ġ mac���� �۾��ѰŶ� �̷������� ���� ����
            String filename= "lazyjin_HFFS_test.xls";
            FileOutputStream fout = setFile(filePath, filename);
            HSSFWorkbook wb = setExcel(filename);
                     //write the workbook to the output stream
            wb.write(fout);
            fout.close(); 


        } catch (Exception e) {
        	e.printStackTrace();            
        }       
    }


    private FileOutputStream setFile(String filePath, String filename) throws FileNotFoundException {
        //���� ���� ����   	
        //���丮 ������ ����.
        File fDir = new File(filePath);
        if (!fDir.exists()) { 
             fDir.mkdirs();
        }
 
       FileOutputStream fout = new FileOutputStream(filePath + filename);

       return fout;
    }


    private HSSFWorkbook setExcel(String filename) throws IOException {
        //���� ���� ����
        HSSFWorkbook wb = new HSSFWorkbook();

       //��Ʈ �� ��Ʈ ����
        HSSFSheet sht = wb.createSheet(filename);
        sht.setGridsPrinted(true);
        sht.setFitToPage(true);
        sht.setDisplayGuts(true);
        HSSFRow row = null;
        HSSFCell cell = null;

        //��Ʈ �̸� �ֱ�
        wb.setSheetName(0, filename);

        //���� �� ����
        String[] title1	= {"NO.",	"NAME",	 ""};
        String[] title2	= {"NO.",  "First",	"Last"};
        String[] contents = {"","Gildong","Hong"};
        int[] cellwidth = {	6,		 20,	  20 };

        //row 1 table start
        row = sht.createRow((short)1);
        row.setHeight((short)500);    //Į�� ����
        short width = 265;


     //========== title1 - first row  ========================
        for (int i=0 ; i < title1.length ; i++) {
   sht.setColumnWidth(i, (cellwidth[i] * width));    // Column ���� ����
   cell = row.createCell(i);
   cell.setCellValue(new HSSFRichTextString(title1[i]));
   if(i==1){
    //====== Cell �պ� ==================  
	sht.addMergedRegion(new CellRangeAddress(1,1,i,i+1));
   //==================================
  }

 cell.setCellStyle(getTitleStyle(wb));
}

 // ===========title2 - Second row  ====================
row = sht.createRow(2);
row.setHeight((short)500);    //Į�� ����

        for(int i = 0;	 i < title2.length;	i++){
              sht.setColumnWidth(i, (cellwidth[i] * width));
 cell = row.createCell((i));
 cell.setCellValue(new HSSFRichTextString(title2[i]));
 cell.setCellStyle(getTitleStyle(wb));
        }


  // ======================================================


 //===========  Table Contents   ===================
        row = sht.createRow(3);
        row.setHeight((short)500);    //Į�� ����
 
        for(int i = 0;	 i < contents.length;	i++){
               sht.setColumnWidth(i, (cellwidth[i] * width));
               cell = row.createCell((i));

              if(i==0)
        	  cell.setCellValue(new HSSFRichTextString(String.valueOf(i)));
              else
        	  cell.setCellValue(new HSSFRichTextString(contents[i]));

              cell.setCellStyle(getTextStyle(wb));
        }
  //=====================================================  


       //��¼���
       HSSFPrintSetup hps = sht.getPrintSetup();
       //��������
       hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
       //��¹��⼳��
       hps.setLandscape(false);
       //��º�������
       hps.setScale((short)100);
       //footer�� ��������ȣ ����
       HSSFFooter footer = sht.getFooter();
       footer.setCenter(HSSFFooter.page() + "/" + HSSFFooter.numPages() );


        //��Ʈ ���� ����
        sht.setMargin(HSSFSheet.TopMargin, 0.6);
        sht.setMargin(HSSFSheet.BottomMargin, 0.6);
        sht.setMargin(HSSFSheet.LeftMargin, 0.6);
        sht.setMargin(HSSFSheet.RightMargin, 0.6);

        //�ݺ��� ����
        wb.setRepeatingRowsAndColumns(0, 0, 3, 0, 0);
 
       return wb;
    }
 
   /* 
    	@@@@@     Font ���� Method    @@@@@
    */
    private HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
    	//���� ��Ʈ
        HSSFFont hf = wb.createFont();
        hf.setFontHeightInPoints((short) 8);
        hf.setColor((short) HSSFColor.BLACK.index);
        hf.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        //Header style setting
        HSSFCellStyle hcs = wb.createCellStyle();
        hcs.setFont(hf);
        hcs.setAlignment(HSSFCellStyle.ALIGN_CENTER);


        //set border style
        hcs.setBorderBottom(HSSFCellStyle.BORDER_THICK);
        hcs.setBorderRight(HSSFCellStyle.BORDER_THIN);
        hcs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        hcs.setBorderTop(HSSFCellStyle.BORDER_THIN);
        hcs.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        //set color
        hcs.setFillBackgroundColor((short) HSSFColor.WHITE.index );
        hcs.setFillForegroundColor((short) HSSFColor.VIOLET.index );
        hcs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        hcs.setLocked(true);
        hcs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return hcs;
    }

   /* 
	@@@@@     Font ���� Method    @@@@@
    */
    private HSSFCellStyle getTextStyle(HSSFWorkbook wb) {
	    HSSFFont hf = wb.createFont();
	    hf.setFontHeightInPoints((short) 8);
	    hf.setColor((short) HSSFColor.BLACK.index);


	    HSSFCellStyle hcs = wb.createCellStyle();
	    hcs.setFont(hf);
	    hcs.setAlignment(HSSFCellStyle.ALIGN_CENTER);


            //set border style
            hcs.setBorderBottom(HSSFCellStyle.BORDER_THICK);
	    hcs.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    hcs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    hcs.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    hcs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	
	    //set color
	    hcs.setFillBackgroundColor((short) HSSFColor.WHITE.index );
	    hcs.setFillForegroundColor((short) HSSFColor.WHITE.index );
	    hcs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	
            hcs.setLocked(true);
	    hcs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    return hcs;
    }
 

}


