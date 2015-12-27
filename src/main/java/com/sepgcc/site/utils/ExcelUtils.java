package com.sepgcc.site.utils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.beanutils.BeanUtils;

import java.io.ByteArrayOutputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUtils {

    private String[] fTags = null;  //标签代号信息列明

    private String[] fNames = null; //对象属性信息

    private Hashtable allTags = null; //标签缓存

    private jxl.biff.DisplayFormat[] columnFormat = null;

    /**
     * @param fTags   表头列名
     * @param fNames  数据的属性名,如dealId、price、contractGlobalId
     * @param allTags
     */
    public ExcelUtils(String[] fTags, String[] fNames, jxl.biff.DisplayFormat[] columnFormat, Hashtable allTags) {
        this.fTags = fTags;
        this.fNames = fNames;
        this.allTags = allTags;
        this.columnFormat = columnFormat;
    }

    public byte[] createExcel(List dataList) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WritableWorkbook book = null;
        try {
            book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet("sheet1", 0);
            this.setHeader(sheet); //设置Excel标题信息
            this.setBody(sheet, dataList); // 设置Excel内容主体信息
            book.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (book != null)
                    book.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return os.toByteArray();
    }

    private void setHeader(WritableSheet sheet) throws WriteException {
        String[] header = new String[fTags.length];
        for (int i = 0; i < fTags.length; i++) {
            String fTagsName = (String) allTags.get("F_" + fTags[i].toUpperCase());
            header[i] = fTagsName != null ? fTagsName : fTags[i];
        }
        this.setHeader(sheet, header);
    }

    private void setHeader(WritableSheet sheet, String[] column) throws WriteException {
        WritableCellFormat headerFormat = new WritableCellFormat();
        headerFormat.setAlignment(Alignment.LEFT);  //水平居中对齐
        headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);   //竖直方向居中对齐
        headerFormat.setBorder(Border.NONE, jxl.format.BorderLineStyle.THIN);
        for (int i = 0; i < column.length; i++) {
            Label label = new Label(i, 0, column[i], headerFormat);
            sheet.addCell(label);
            sheet.setColumnView(i, 20);
            sheet.setRowView(0, 500);
        }
    }

    private void setBody(WritableSheet sheet, List rowList) throws Exception {
        this.setBody(sheet, rowList, fNames);
    }

    private static boolean isDecimal(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        return pattern.matcher(str).matches();
    }

    private void setBody(WritableSheet sheet, List rowList, String[] column) throws Exception {
        Dictionary<Integer, WritableCellFormat> bodyFormats = getWritableCellFormat();
        for (int i = 0; i < rowList.size(); i++) {
            Object obj = rowList.get(i);
            for (int j = 0; j < column.length; j++) {
                WritableCellFormat bodyFormat = bodyFormats.get(j);
                if (obj instanceof Map) {
                    Label label = new Label(j, i + 1, String.valueOf(((Map) obj).get(column[j].toLowerCase())), bodyFormat);
                    sheet.addCell(label);
                } else {
                    String item = BeanUtils.getProperty(obj, column[j]);
                    if (item == null) {
                        item = "";
                    }
                    if (!columnFormat[j].equals(NumberFormats.TEXT) && isDecimal(item.toString())) {
                        double num = Double.valueOf(item.toString()).doubleValue();
                        Number number = new Number(j, i + 1, num, bodyFormat);
                        sheet.addCell(number);
                    } else {
                        Label label = new Label(j, i + 1, item, bodyFormat);
                        sheet.addCell(label);
                    }
                }
                sheet.setRowView(i + 1, 350);
            }
        }
    }

    private Dictionary<Integer, WritableCellFormat> getWritableCellFormat() throws Exception {
        Dictionary<Integer, WritableCellFormat> ret = new Hashtable<Integer, WritableCellFormat>();
        for (int i = 0; i < columnFormat.length; i++) {
            WritableCellFormat bodyFormat = new WritableCellFormat(columnFormat[i]);
            bodyFormat.setAlignment(Alignment.LEFT); //水平居中对齐
            bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);   //竖直方向居中对齐
            bodyFormat.setBorder(Border.NONE, jxl.format.BorderLineStyle.THIN);
            ret.put(i, bodyFormat);
        }
        return ret;
    }
}