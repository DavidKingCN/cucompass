package excel;

import java.util.ArrayList;
import java.util.List;

import cn.com.davidking.model.Qu;
import cn.com.davidking.poi.AdvanExlUtil;
import cn.com.davidking.poi.ExlUtilApi;

public class WriteExcelApiTest {
	static ExlUtilApi writeApi =new AdvanExlUtil();
	public static void main(String[] args) throws Exception{
		writeXlsx();
		writeXls();
	}

	public static void writeXlsx() throws Exception{
		String path = "D:\\qt.xls";
		boolean needHead = true;
		List<String> headers = new ArrayList<>();
		headers.add( "试题编码");
		headers.add( "题型");
		headers.add( "分值");
		headers.add( "难度");
		headers.add( "级别");
		headers.add( "知识点");
		
		List<String> orderFields = new ArrayList<>();
		orderFields.add("quCode");
		orderFields.add("quType");
		orderFields.add("quCoin");
		orderFields.add("quNd");
		orderFields.add("level");
		orderFields.add("name");
		
		Class clz = Qu.class;
		
		List<Qu> datas = new ArrayList<>();
		
		Qu q1 = new Qu();
		q1.setQuCode("t1");
		q1.setQuType(1);
		q1.setQuCoin(3.0);
		q1.setQuNd(1);
		q1.setLevel("重要");
		q1.setName("专业");
		datas.add(q1);
		
		q1 = new Qu();
		q1.setQuCode("t2");
		q1.setQuType(1);
		q1.setQuCoin(6.0);
		q1.setQuNd(1);
		q1.setLevel("特别重要");
		q1.setName("高数");
		datas.add(q1);
		
		writeApi.writeXls(path, needHead, headers, orderFields, clz, datas); 
	}
	public static void writeXls(){
		
	}
}
