package office.util;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.DataValidation;

public class ExportXiaLaExcel<T> {

	public void exportExcel(String title,Collection<T> dataset, OutputStream out) {
        exportExcel(title, null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String title,String[] headers, Collection<T> dataset,
            OutputStream out) {
        exportExcel(title, headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset,
            OutputStream out, String pattern) {
        exportExcel("PDP详细数据", headers, dataset, out, pattern);
    }
    public void exportExcel(String title, String[] headers,
            Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();//表头样式
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            @SuppressWarnings("deprecation")
           // HSSFCell cell = row.createCell((short) (celln + 1));   
			HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //begin row,begin col,end row,end col//合并单元格
        //sheet.addMergedRegion(new Region(0,(short)0,0,(short)5));   
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            String tmp=t.getClass().getName();
            creatAppRow(sheet,index,style2);
            
            int j=fields.length;
            for (short i = 0; i < fields.length; i++) {
                @SuppressWarnings("deprecation")
				HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                   // System.out.println("fieldName:"+fieldName);
                    //System.out.println("value:"+value);
                    if(fieldName.equals("position")||fieldName.equals("identity")||fieldName.equals("password")||fieldName.equals("newnumber"))
                    {
                    	String strValue = "";
                    	if(value!=null)
                    	{
                    		strValue = value.toString();
                    	}
                    	cell.setCellValue(strValue);
                    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    }
                    else if (value instanceof Integer) {
                     int intValue = (Integer) value;
                     cell.setCellValue(intValue);
                     }
                    else if (value instanceof Float) {
                     float fValue = (Float) value;
                     
                     cell.setCellValue(fValue);
                     }
                    else if (value instanceof Double) {
                     double dValue = (Double) value;
                     
                     cell.setCellValue(dValue);
                     }
                    else if (value instanceof Long) {
                     long longValue = (Long) value;
                     cell.setCellValue(longValue);
                     }
                    else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue = "女";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                       if(value==null||value==""||value=="null")
                    	   textValue="";
                       else
                    	textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) 
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                       /* if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } */
                       // else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                         //  String strtemp = richString.getString();
                          
                        	   
//                           if(textValue.contains(".")&&!textValue.contains("%"))
//                           {
//                        	   cell.setCellValue(Double.parseDouble(textValue));
//                           }
                          /*if(textValue.matches("\\d+"))
                           {
                        	   cell.setCellValue(Double.parseDouble(textValue)); 
                           }
                           else*/
                        	   cell.setCellValue(richString);
                            
                        //}
                    }
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                	 System.out.println("first i="+i);
                    e.printStackTrace();
                   
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                	 System.out.println("2 i="+i);
                    e.printStackTrace();
                   
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                	System.out.println("3 i="+i+"' index="+index+"' fieldName="+fieldName);
                	e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                	System.out.println("4 i="+i);
                	e.printStackTrace();
                    
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                	System.out.println("5 i="+i);
                	e.printStackTrace();
                    
                } finally {
                    // 清理资源
                }
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
        	System.out.println("100 i=");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    
    public void creatAppRow(HSSFSheet userinfosheet1,int RowIndex,HSSFCellStyle datastyle ){    
        //在第一行第一个单元格，插入下拉框    
        HSSFRow row = userinfosheet1.createRow(RowIndex);  
        //所属分类  
        String [] list1={"四楼值班室","四楼设备间1","四楼设备间2","五楼东休息区","五楼设备间","501","502","503","504","505","507","508","509","511","512","母婴室","六楼综合办公区1","六楼综合办公区2","六楼综合办公区3","601","602","605","606","607","608","609","610","611","612","613","614","六楼设备间","七楼东工区","七楼西工区","七楼中间办公室","七楼西休息室","七楼东休息区","七楼设备间","八楼东工区","八楼西工区","八楼中间办公室","八楼西休息室","八楼东休息区","八楼设备间","九楼培训室","九楼中间办公室","九楼东工区","九楼东休息区"};   
        //分类名称  
              //4.品牌  
        HSSFCell cell5 = row.createCell(4);    
        cell5.setCellValue("请选择");    
        cell5.setCellStyle(datastyle);    
        
        //得到验证对象      
        DataValidation data_validation_list1 = this.getDataValidationByFormula(list1,RowIndex,4); //从1开始下拉框处于第几列    
        //工作表添加验证数据      
        userinfosheet1.addValidationData(data_validation_list1);  
                  
    }    
        
    /**   
     * 使用已定义的数据源方式设置一个数据验证   
     * @param formulaString   
     * @param naturalRowIndex   
     * @param naturalColumnIndex   
     * @return   
     */    
    public DataValidation getDataValidationByFormula(String[] formulaString,int RowIndex,int ColumnIndex){    
        //加载下拉列表内容      
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(formulaString);     
        //设置数据有效性加载在哪个单元格上。      
        //四个参数分别是：起始行、终止行、起始列、终止列      
        int firstRow = RowIndex;    
        int lastRow = RowIndex;    
        int firstCol = ColumnIndex;    
        int lastCol = ColumnIndex;    
        CellRangeAddressList regions=new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);      
        //数据有效性对象     
        DataValidation data_validation_list = new HSSFDataValidation(regions,constraint);    
        return data_validation_list;      
    }    
}
