package excel;

import java.util.ArrayList;
import java.util.List;

import cn.com.davidking.poi.AdvanExlUtil;
import cn.com.davidking.poi.Student;

public class ReadExcelApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	String path = "/excel/student_info.xlsx";
    	List<String> orderFields = new ArrayList<>();
    	orderFields.add(0, "no");
    	orderFields.add(1, "name");
    	orderFields.add(2, "age");
    	orderFields.add(3, "score");
    	Class<Student> clz = Student.class;
    	List<Student> ls = new ArrayList<>();
		try {
			ls = AdvanExlUtil.newReadExcelUtil().readXlsx(path, orderFields, clz);
		} catch (Exception e) {	}
		
		if(ls != null){
			ls.forEach(s->{
				System.out.println(s.toString());
			});
		}
	}

}
