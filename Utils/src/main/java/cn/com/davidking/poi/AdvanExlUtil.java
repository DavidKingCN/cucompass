package cn.com.davidking.poi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.com.davidking.util.DateUtil;
import cn.com.davidking.util.StringUtil;


/**
 * @author daikai
 * @created 
 */
@SuppressWarnings("all")
public final class AdvanExlUtil implements ExlUtilApi {
    
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#readExl(java.lang.String, java.util.List, java.lang.Class, boolean)
	 */
    @Override
	public <T> List<T> readExl(String path,List<String> orderFields,Class<T> clz,boolean isClsPath) throws Exception {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = Util.getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path,orderFields,clz,isClsPath);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path,orderFields,clz,isClsPath);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#writeXlsx(java.lang.String, boolean, java.util.List, java.util.List, java.lang.Class, java.util.List)
	 */
    @Override
	public <T> void writeXlsx(String path,boolean needHead,List<String> headers,List<String> orderFields,Class<T> clz,List<T> datas) {
    	
    	File f = new File(path);
		XlsxWriter e = null;
		try {
			e = new XlsxWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int headIdx = 0;
		if(needHead) {
			e.createRow(0);
			
			for(String header : headers)
				e.setCell(headIdx++, header);
		}
		int rowInx = 1;
		
		
		for(T data : datas) {
			e.createRow(rowInx ++);
			headIdx = 0;
            for(String field:orderFields){
            	try{
                	Field curField = clz.getDeclaredField(field);
                	Type type = curField.getGenericType();
                	String typeName = type.getTypeName();
                	String getter = "get" + StringUtil.upperFirstCapital(field);
                	Method m = clz.getMethod(getter, null);
            
                	Object obj = m.invoke(data, null);
                	
                	e.setCell(headIdx ++, objToStr(typeName, obj));
            	} catch (Exception ex) {
					continue;
				}
            }
		}
		try {
			e.export();
			System.out.println(" 导出Excel文件[成功] ");
		} catch (IOException ex) {
			System.out.println(" 导出Excel文件[失败] ");
			ex.printStackTrace();
		}
    }
    
    
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#writeXls(java.lang.String, boolean, java.util.List, java.util.List, java.lang.Class, java.util.List)
	 */
    @Override
	public <T> void  writeXls(String path,boolean needHead,List<String> headers,List<String> orderFields,Class<T> clz,List<T> datas) throws Exception {
    	File f = new File(path);
		XlsWriter e = null;
		try {
			e = new XlsWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int headIdx = 0;
		if(needHead) {
			e.createRow(0);
			
			for(String header : headers)
				e.setCell(headIdx++, header);
		}
		int rowInx = 1;
		
		
		for(T data : datas) {
			e.createRow(rowInx ++);
			headIdx = 0;
            for(String field:orderFields){
            	try{
                	Field curField = clz.getDeclaredField(field);
                	Type type = curField.getGenericType();
                	String typeName = type.getTypeName();
                	String getter = "get" + StringUtil.upperFirstCapital(field);
                	Method m = clz.getMethod(getter, null);
            
                	Object obj = m.invoke(data, null);
                	
                	e.setCell(headIdx ++, objToStr(typeName, obj));
            	} catch (Exception ex) {
					continue;
				}
            }
		}
		try {
			e.export();
			System.out.println(" 导出Excel文件[成功] ");
		} catch (IOException ex) {
			System.out.println(" 导出Excel文件[失败] ");
			ex.printStackTrace();
		}
    }
    
    
    
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#readXlsx(java.lang.String, java.util.List, java.lang.Class)
	 */
    @Override
	public<T>  List<T> readXlsx(String path,List<String> orderFields,Class<T> clz) throws Exception {
    	return readXlsx(path,orderFields,clz,true);
    }
    
    
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#readXlsx(java.lang.String, java.util.List, java.lang.Class, boolean)
	 */
    @Override
	public<T>  List<T> readXlsx(String path,List<String> orderFields,Class<T> clz,boolean isClsPath) throws Exception {
    	InputStream is = null;
    	if(isClsPath)
    		is = AdvanExlUtil.class.getResourceAsStream(path);
    	else
    		is = new FileInputStream(path);
    	if(is==null){ 
    		return null;
    	}
        XSSFWorkbook xwb = new XSSFWorkbook(is);
        T t = null;
        List<T> ts = new ArrayList<T>();
        // Read the Sheet
        for (int shtNo = 0; shtNo < xwb.getNumberOfSheets(); shtNo++) {
            XSSFSheet xs = xwb.getSheetAt(shtNo);
            if (xs == null) {
                continue;
            }
            // Read the Row
            for (int rowNo = 1; rowNo <= xs.getLastRowNum(); rowNo++) {
                XSSFRow xssfRow = xs.getRow(rowNo);
                if (xssfRow != null) {
                    t = clz.newInstance();
                    int fieldIdx = 0;
                    for(String field:orderFields){
                    	
                    	try {
							XSSFCell cel = xssfRow.getCell(fieldIdx++);
							Field f = clz.getDeclaredField(field);
							Type type = f.getGenericType();
							String typeName = type.getTypeName();
							
							String setter = "set"+StringUtil.upperFirstCapital(field);
							Method m = clz.getMethod(setter, f.getType());
							String str = getValue(cel);
							t = strToObj(typeName,t,m,str);
						} catch (Exception e) {
							System.out.println("field.name="+field);
							continue;
						}
                    }
                    ts.add(t);
                }
            }
        }
        return ts;
    }

    private String objToStr(String typeName, Object v) throws Exception {
    	if(v == null ) return "";
    	try {
			if (Date.class.getName().equals(typeName)) {
				Date date = (Date)v;
				return DateUtil.toTimeString(date);
			}else {
				return String.valueOf(v);
			}
		} catch (Exception e) {
		}
    	return "";
    }
    private <T> T strToObj(String typeName,T t,Method m,String str) throws Exception{
    	try {
			if(typeName.equals(String.class.getName())){
				m.invoke(t, str);
			} else if(typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName())){
				m.invoke(t, Float.valueOf(str));
			} else if(typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())){
				String target = str.substring(0, str.lastIndexOf("."));
				m.invoke(t, Integer.valueOf(target));
			} else if(typeName.equals(byte.class.getName()) || typeName.equals(Byte.class.getName())){
				m.invoke(t, Byte.valueOf(str));
			} else if(typeName.equals(char.class.getName()) || typeName.equals(Character.class.getName())){
				m.invoke(t, str.charAt(0));
			} else if(typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName())){
				m.invoke(t, Boolean.valueOf(str));
			} else if(typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())){
				m.invoke(t, Long.valueOf(str));
			} else if(typeName.equals(double.class.getName()) || typeName.equals(Double.class.getName())){
				m.invoke(t, Double.valueOf(str));
			}else if (Date.class.getName().equals(typeName)) {
				//m.invoke(t, Double.valueOf(getValue(cel)));
				System.out.println("not support type" + typeName);
			}else {
				System.out.println("not support type" + typeName);
			}
		} catch (Exception e) {
		}
    	return t;
    }
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#readXls(java.lang.String, java.util.List, java.lang.Class)
	 */
    @Override
	public <T> List<T> readXls(String path,List<String> orderFields,Class<T> clz) throws Exception {
    	return readXls(path,orderFields,clz,true); 
    }
    /* (non-Javadoc)
	 * @see cn.com.davidking.poi.ExlUtilApi#readXls(java.lang.String, java.util.List, java.lang.Class, boolean)
	 */
    @Override
	public <T> List<T> readXls(String path,List<String> orderFields,Class<T> clz,boolean isClsPath) throws Exception {
        System.out.println(Common.PROCESSING + path);
        InputStream is = null;
    	if(isClsPath)
    		is = AdvanExlUtil.class.getResourceAsStream(path);
    	else
    		is = new FileInputStream(path);
    	if(is==null){ 
    		return null;
    	}
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        T t = null;
        List<T> ts = new ArrayList<T>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	t = clz.newInstance();
                    int fieldIdx = 0;
                    for(String field:orderFields){
                    	System.out.println("doing.field.name="+field);
                    	try{
	                    	HSSFCell cel = hssfRow.getCell(fieldIdx++);
	                    	Field f = clz.getDeclaredField(field);
	                    	Type type = f.getGenericType();
	                    	String typeName = type.getTypeName();
	                    	String setter = "set"+StringUtil.upperFirstCapital(field);
	                    	Method m = clz.getMethod(setter, f.getType());
	                    	String str = getValue(cel);
	                    	
	                    	t = strToObj(typeName,t,m,str);
                    	} catch (Exception e) {
							System.out.println("field.name="+field);
							continue;
						}
                    }
                    ts.add(t);
                }
            }
        }
        return ts;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
    	
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    public static ExlUtilApi newReadExcelUtil(){
    	return new AdvanExlUtil();
    }
    public static void main(String[] args) {
//    	String path = "/excel/student_info.xlsx";
//    	List<String> orderFields = new ArrayList<>();
//    	orderFields.add(0, "no");
//    	orderFields.add(1, "name");
//    	orderFields.add(2, "age");
//    	orderFields.add(3, "score");
//    	Class<Student> clz = Student.class;
//    	List<Student> ls = new ArrayList<>();
//		try {
//			ls = AdvanExlUtil.newReadExcelUtil().readXlsx(path, orderFields, clz);
//		} catch (Exception e) {	}
//		
//		if(ls != null){
//			ls.forEach(s->{
//				System.out.println(s.toString());
//			});
//		}
    	Object v = 1.1;
    	System.out.println(String.valueOf(v));
	}
}