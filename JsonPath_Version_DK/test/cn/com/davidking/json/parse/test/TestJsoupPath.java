package cn.com.davidking.json.parse.test;

import java.io.File;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import cn.com.davidking.json.util.FileUtil;
import cn.com.davidking.test.MethodExec;

public class TestJsoupPath extends MethodExec {

	@Override
	public void process() {
		File jsonFile = new File("resources/json.json");

		String json = FileUtil.nioReadFile(jsonFile.getAbsolutePath());

		JsonPath path = JsonPath.compile("$.children[*].name");

		List<String> names = path.read(json);
		//System.out.println("jsoup:" + names);

		String name = JsonPath.read(json, "$.children[0].children[0].name").toString();
		//System.out.println("jsoup:" + name);
	}

	public static void main(String[] args) {
		new TestJsoupPath().calcTms(100, true, true, TestJsoupPath.class);
	}
}
