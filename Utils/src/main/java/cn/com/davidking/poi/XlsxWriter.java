package cn.com.davidking.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxWriter {
	// 设置cell编码解决中文高位字节截断
	private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;
	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";
	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"
	private OutputStream out = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;

	public XlsxWriter() {
	}

	public XlsxWriter(OutputStream out) {
		this.out = out;
		this.workbook = new XSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	public String getCell(int index) {
		XSSFCell cell = this.row.getCell((short) index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case XSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
				break;
			case XSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				strExcelCell = "";
				break;
			default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}

	public void setCell(int index, int value) {
		XSSFCell cell = this.row.createCell( index);
		
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	public void setCell(int index, double value) {
		XSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		XSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		XSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	public void setCell(int index, String value) {
		XSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
	}

	public void setCell(int index, Calendar value) {
		XSSFCell cell = this.row.createCell((short) index);
		// cell.set
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		XSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	public static void main(String[] args) {
		System.out.println(" 开始导出Excel文件 ");
		File f = new File("D:\\qt.xlsx");
		XlsxWriter e = new XlsxWriter();
		try {
			e = new XlsxWriter(new FileOutputStream(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		e.createRow(0);
		e.setCell(0, "试题编码 ");
		e.setCell(1, "题型");
		e.setCell(2, "分值");
		e.setCell(3, "难度");
		e.setCell(4, "级别");
		e.setCell(5, "知识点");
		e.createRow(1);
		e.setCell(0, "t1");
		e.setCell(1, 1);
		e.setCell(2, 3.0);
		e.setCell(3, 1);
		e.setCell(4, "重要");
		e.setCell(5, "专业");
		try {
			e.export();
			System.out.println(" 导出Excel文件[成功] ");
		} catch (IOException ex) {
			System.out.println(" 导出Excel文件[失败] ");
			ex.printStackTrace();
		}
	}
}