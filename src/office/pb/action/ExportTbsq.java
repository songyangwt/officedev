package office.pb.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import office.kqjl.bean.KqjlMonthZuzhangBean;
import office.pb.bean.TbsqBean;
import office.pb2.dao.TbsqPageDAO;
import office.pb2.pojo.TbsqPage;
import office.userinfo.dao.UserInfoDAO;
import office.userinfo.pojo.UserInfo;
import office.util.ExportExcelTip;
import office.util.UserUtil;
import office.util.Util;
import office.zcgl.dao.AssertFenDAO;
import office.zcgl.pojo.AssertFen;
import office.zcgl.pojo.AssetFenNameBean;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ccb.hibernate.HibernateSessionFactory;

public class ExportTbsq {
	private String number;
	private List<TbsqBean> listtbsq;
	private String begindate;
	private String enddate;
	private String Path;
	
	public List<TbsqBean> getListtbsq() {
		return listtbsq;
	}
	public void setListtbsq(List<TbsqBean> listtbsq) {
		this.listtbsq = listtbsq;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String execute() throws Exception{
		String filePath = "";

		TbsqPageDAO tbdao = new TbsqPageDAO();

		
		Session session = HibernateSessionFactory.getSession();
 	    Transaction trans = session.beginTransaction();
 	    List<TbsqPage> listtb = new ArrayList<TbsqPage>() ;
 		listtb = tbdao.findAllByTbDate(begindate, enddate);
	    UserInfoDAO uidao = new UserInfoDAO();
		Iterator itor = listtb.iterator();
		listtbsq =new ArrayList<TbsqBean>();
		int num = 1;
		while(itor.hasNext())
		{	
			
			TbsqPage tb = (TbsqPage)itor.next();
		    TbsqBean tbbean = new TbsqBean();
		    UserInfo ui = uidao.findByNewNumber(tb.getApplicant());
		    UserInfo uitb = uidao.findByName(tb.getTbname());
            tbbean.setIndex( Integer.toString(num));
		    tbbean.setTbname(ui.getUsername());
            tbbean.setTbopnumber(ui.getOpnumber());
            tbbean.setTbchu(UserUtil.chuToName(ui.getPosition().substring(2,3)));
            tbbean.setTbworktime(tb.getPrejihua());
            tbbean.setBtbname(tb.getTbname());
            tbbean.setBtbopnumber(uitb.getOpnumber());
            tbbean.setBtbchu(UserUtil.chuToName(uitb.getPosition().substring(2, 3)));
            tbbean.setBtbtime(tb.getNowjihua());
            tbbean.setBegindate(tb.getTbdate());
            tbbean.setEnddate(tb.getTbdate());
            tbbean.setReportdate(tb.getDate());
            tbbean.setRemark("工作需要");           
		    listtbsq.add(tbbean);	
			num++;
		}
			
		    trans.commit();
			session.flush();
			session.clear();
			session.close();
		//导出
			 try {
				 	filePath=Util.downloadpath+begindate+"-"+enddate+"tbsq.xls";
					Path = begindate+"-"+enddate+"tbsq.xls";
					OutputStream out = new FileOutputStream(filePath);

					// 声明一个工作薄
			        HSSFWorkbook workbook = new HSSFWorkbook();
			        // 生成一个表格
			        HSSFSheet sheet = workbook.createSheet("成都分中心调班明细表");
			        // 设置表格默认列宽度为15个字节
			        sheet.setDefaultColumnWidth((short)9);
			        
			        sheet.setColumnWidth(4,12000);
			        sheet.setColumnWidth(8,12000);
			       
			        // 生成一个样式
			        HSSFCellStyle styletitle = workbook.createCellStyle();//标题样式
			        
			        HSSFCellStyle styleremark = workbook.createCellStyle();//注释样式
			        //设置标题样式
			        styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			        styletitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        // 生成一个字体
			        HSSFFont fonttitle = workbook.createFont();
			        fonttitle.setFontHeightInPoints((short) 20);
			        fonttitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			        // 把字体应用到当前的样式
			        styletitle.setFont(fonttitle);
			        // 设置表头
			        HSSFCellStyle stylehead = workbook.createCellStyle();//表头样式
			        stylehead.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			        stylehead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        stylehead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        stylehead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        stylehead.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        stylehead.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        stylehead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			        stylehead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        stylehead.setWrapText(true);
			        // 生成一个字体
			        HSSFFont fonthead = workbook.createFont();
			        //fonthead.setColor(HSSFColor.VIOLET.index);
			        fonthead.setFontHeightInPoints((short)9);
			        fonthead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			        // 把字体应用到当前的样式
			        stylehead.setFont(fonthead);
			        // 设置注释
			        styleremark.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			        styleremark.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        // 生成一个字体
			        HSSFFont fontremark = workbook.createFont();
			        fontremark.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			        // 把字体应用到当前的样式
			        styleremark.setFont(fontremark);
			        // 生成并设置另一个样式
			        HSSFCellStyle stylecontent = workbook.createCellStyle();
			        stylecontent.setFillForegroundColor(HSSFColor.WHITE.index);
			        stylecontent.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        stylecontent.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        stylecontent.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        stylecontent.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        stylecontent.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        stylecontent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			        stylecontent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        stylecontent.setWrapText(true);
			        //stylecontent.setLocked(true);
			        // 生成另一个字体
			        HSSFFont fontcontent = workbook.createFont();
			        fontcontent.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			        // 把字体应用到当前的样式
			        stylecontent.setFont(fontcontent);

			        // 生成并设置另一个样式，带深色底纹的背景 
			        HSSFCellStyle stylecontent2 = workbook.createCellStyle();
			        stylecontent2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			        stylecontent2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        stylecontent2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        stylecontent2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        stylecontent2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        stylecontent2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        stylecontent2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			        stylecontent2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        stylecontent2.setWrapText(true);
			        // 把字体应用到当前的样式
			        stylecontent2.setFont(fontcontent);
			        // 声明一个画图的顶级管理器
			        //HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			        // 定义注释的大小和位置,详见文档
			        //HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,0, 0, 0, (short) 4, 2, (short) 6, 5));
			        // 设置注释内容
			        //comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
			        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			        //comment.setAuthor("leno");

			        //第一行title
			        HSSFRow row = sheet.createRow(0);
			        HSSFCell celltitle = row.createCell(0);
			      
			        HSSFCell celltitle1 = row.createCell(1);
			        HSSFCell celltitle2 = row.createCell(2);
			        HSSFCell celltitle3 = row.createCell(3);
			        HSSFCell celltitle4 = row.createCell(4);
			        HSSFCell celltitle5 = row.createCell(5);
			        HSSFCell celltitle6 = row.createCell(6);
			        HSSFCell celltitle7 = row.createCell(7);
			        HSSFCell celltitle8 = row.createCell(8);
			        HSSFCell celltitle9 = row.createCell(9);
			        HSSFCell celltitle10 = row.createCell(10);
			        HSSFCell celltitle11 = row.createCell(11);
			        HSSFCell celltitle12 = row.createCell(12);
			   
			        celltitle12.setCellStyle(styletitle);
			        celltitle1.setCellStyle(styletitle);
			        celltitle2.setCellStyle(styletitle);
			        celltitle3.setCellStyle(styletitle);
			        celltitle4.setCellStyle(styletitle);
			        celltitle5.setCellStyle(styletitle);
			        celltitle6.setCellStyle(styletitle);
			        celltitle7.setCellStyle(styletitle);
			        celltitle8.setCellStyle(styletitle);
			        celltitle9.setCellStyle(styletitle);
			        celltitle10.setCellStyle(styletitle);
			        celltitle11.setCellStyle(styletitle);
			        //sheet.addMergedRegion(new Region(0,(short)0,0,(short)17));//合并单元格 
			        celltitle.setCellStyle(styletitle);
			        celltitle.setCellValue(new HSSFRichTextString("成都分中心调班明细表"));
			        //第二行填写处室
			        row = sheet.createRow(1);
			        HSSFCell cellremarknull1 = row.createCell(2);
			        HSSFCell cellremarknull2 = row.createCell(3);
			        HSSFCell cellremarknull3 = row.createCell(4);
			        HSSFCell cellremarknull4 = row.createCell(6);
			        HSSFCell cellremarknull5 = row.createCell(7);
			        HSSFCell cellremarknull6 = row.createCell(8);
			        HSSFCell cellremarknull7 = row.createCell(10);
			     
			        HSSFCell cellremark1 = row.createCell(0);
			        HSSFCell cellremark2 = row.createCell(1);
			        HSSFCell cellremark3 = row.createCell(5);
			        HSSFCell cellremark4 = row.createCell(9);
			        HSSFCell cellremark5 = row.createCell(11);
			        HSSFCell cellremark6 = row.createCell(12);
			        cellremarknull1.setCellStyle(stylehead);
			        cellremarknull2.setCellStyle(stylehead);
			        cellremarknull3.setCellStyle(stylehead);
			        cellremarknull4.setCellStyle(stylehead);
			        cellremarknull5.setCellStyle(stylehead);
			        cellremarknull6.setCellStyle(stylehead);
			        cellremarknull7.setCellStyle(stylehead);
			   
			        cellremark1.setCellStyle(stylehead);
			        cellremark1.setCellValue("序号");
			        cellremark2.setCellStyle(stylehead);
			        cellremark2.setCellValue("调班人员");
			        cellremark3.setCellStyle(stylehead);
			        cellremark3.setCellValue("被调班人员");     
			        cellremark4.setCellStyle(stylehead);
			        cellremark4.setCellValue("调班时间");	        
			        cellremark5.setCellStyle(stylehead);
			        cellremark5.setCellValue("上报时间"); 
			        cellremark6.setCellStyle(stylehead);
			        cellremark6.setCellValue("调班事宜");
			        
			        
			        // 产生表格标题行
			        row = sheet.createRow(2);
			        HSSFCell cell2remarknull1 = row.createCell(0);
			        HSSFCell cell2remarknull2 = row.createCell(11);
			        HSSFCell cell2remarknull3 = row.createCell(12);
			    
			        HSSFCell cell2remark1 = row.createCell(1);
			        HSSFCell cell2remark2 = row.createCell(2);
			        HSSFCell cell2remark3 = row.createCell(3);
			        HSSFCell cell2remark4 = row.createCell(4);
			        HSSFCell cell2remark5 = row.createCell(5);
			        HSSFCell cell2remark6 = row.createCell(6);
			        HSSFCell cell2remark7 = row.createCell(7);
			        HSSFCell cell2remark8 = row.createCell(8);
			        HSSFCell cell2remark9 = row.createCell(9);
			        HSSFCell cell2remark10 = row.createCell(10);
			        cell2remarknull1.setCellStyle(stylehead); 
			        cell2remarknull2.setCellStyle(stylehead); 
			        cell2remarknull3.setCellStyle(stylehead); 
			
			        cell2remark1.setCellStyle(stylehead);
			        cell2remark1.setCellValue("姓名");
			        cell2remark2.setCellStyle(stylehead);
			        cell2remark2.setCellValue("工号");
			        cell2remark3.setCellStyle(stylehead);
			        cell2remark3.setCellValue("机构处室");
			        
			        cell2remark4.setCellStyle(stylehead);
			        cell2remark4.setCellValue("原班型");	        
			        cell2remark5.setCellStyle(stylehead);
			        cell2remark5.setCellValue("姓名");
			        
			        cell2remark6.setCellStyle(stylehead);
			        cell2remark6.setCellValue("工号");
			        cell2remark7.setCellStyle(stylehead);
			        cell2remark7.setCellValue("机构处室");	        
			        cell2remark8.setCellStyle(stylehead);
			        cell2remark8.setCellValue("原班型");
			        
			        cell2remark9.setCellStyle(stylehead);
			        cell2remark9.setCellValue("开始时间");
			        cell2remark10.setCellStyle(stylehead);
			        cell2remark10.setCellValue("结束时间");
			        //begin row,begin col,end row,end col//合并单元格
			        //sheet.addMergedRegion(new Region(0,(short)0,0,(short)5));   
			        // 遍历集合数据，产生数据行
			        int size = listtbsq.size();
			        for(int i=0;i<size;i++)
			        {
			        	TbsqBean tbbean = listtbsq.get(i);
			        	row = sheet.createRow(i+3);
			        	Field[] fields = tbbean.getClass().getDeclaredFields();
			        	int k=fields.length;
			        	for(int j=0;j<k;j++)
			        	{
			        		HSSFCell cell = row.createCell(j);
			        		
			        		Field field = fields[j];
			        		String fieldName = field.getName();
			        		String getMethodName = "get"
		                        + fieldName.substring(0, 1).toUpperCase()
		                        + fieldName.substring(1);
			        		try {
			        			Class tCls = tbbean.getClass();
			        			Method getMethod = tCls.getMethod(getMethodName,
			                            new Class[] {});
			        			String value = getMethod.invoke(tbbean,new Object[]{}).toString();
			        			System.out.println(value);
			        			cell.setCellStyle(stylecontent);
			        			
			        			cell.setCellValue(value);
			        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			        		} catch (SecurityException e) {
			                    // TODO Auto-generated catch block
			                	 System.out.println("first i="+i);
			                    e.printStackTrace();
			                   
			                } catch (NoSuchMethodException e) {
			                    // TODO Auto-generated catch block
			                	 System.out.println("2 i="+i);
			                    e.printStackTrace();
			                   
			                }  finally {
			                    // 清理资源
			                }
			        	}
			        }
			     
			       
			        //两两合并单元格
			       
			        	sheet.addMergedRegion(new Region(0,(short)0,0,(short)12));
			        	sheet.addMergedRegion(new Region(1,(short)0,2,(short)0));
			        	sheet.addMergedRegion(new Region(1,(short)1,1,(short)4));
			        	sheet.addMergedRegion(new Region(1,(short)5,1,(short)8));
			        	sheet.addMergedRegion(new Region(1,(short)9,1,(short)10));
			        	sheet.addMergedRegion(new Region(1,(short)11,2,(short)11));
			        	sheet.addMergedRegion(new Region(1,(short)12,2,(short)12));
			        	
			        
			        try {
			            workbook.write(out);
			        } catch (IOException e) {
			        	System.out.println("100 i=");
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
					
					out.close();
					System.out.println("excel导出成功！");
				} catch (FileNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		return "success";
	}
}
