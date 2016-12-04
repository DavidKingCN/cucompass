package cn.com.davidking.json;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import cn.com.davidking.util.JsonValidator;
import cn.com.davidking.test.ExecChecker;
import cn.com.davidking.test.MethodExec;
//parent->hop.[<father>] ->hop必须写，只支持这种形式哈，复杂的暂时没空写...
//场景就是数组后面的对象里面还有键值对的情形[{{},{}}]
public class TestJsonQuery_Fun_6 extends MethodExec {

	@Override
	public void process() {
		InputStream is = TestJsonQuery_Fun_6.class.getResourceAsStream("/json.json");
		
		try {
			String json = IOUtils.toString(is);
			if(JsonValidator.checkJsonValid(json)){
				String rts = JsonQuery.getSingleVal(json, "$.children[0].girls[*].parent->hop.[<father>]");
				System.out.println(rts);
			}
			
		} catch (IOException ignore) {}
	}

	public static void main(String[] args) {
		ExecChecker.checkExecRt(new TestJsonQuery_Fun_6());
	}

}
