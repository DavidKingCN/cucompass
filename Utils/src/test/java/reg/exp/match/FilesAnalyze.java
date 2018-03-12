package reg.exp.match;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SuppressWarnings("all")
public class FilesAnalyze {
	private ArrayList files = new ArrayList();
	// 用于承载文件路径
	private String _path;
	// 用于承载未合并的正则公式
	private String _regexp;

	class MyFileFilter implements FileFilter {

		/**
		 * 匹配文件名称
		 */
		public boolean accept(File file) {
			try {
				Pattern pattern = Pattern.compile(_regexp);
				Matcher match = pattern.matcher(file.getName());
				return match.matches();
			} catch (Exception e) {
				return true;
			}
		}
	}

	/**
	 * 解析输入流
	 * 
	 * @param inputs
	 */
	FilesAnalyze(String path, String regexp) {
		getFileName(path, regexp);
	}

	/**
	 * 分析文件名并加入files
	 * 
	 * @param input
	 */
	private void getFileName(String path, String regexp) {
		// 目录
		_path = path;
		_regexp = regexp;
		File directory = new File(_path);
		File[] filesFile = directory.listFiles(new MyFileFilter());
		if (filesFile == null)
			return;
		for (int j = 0; j < filesFile.length; j++) {
			files.add(filesFile[j]);
		}
		return;
	}

	/**
	 * 显示输出信息
	 * 
	 * @param out
	 */
	public void print(PrintStream out) {
		Iterator elements = files.iterator();
		while (elements.hasNext()) {
			File file = (File) elements.next();
			out.println(file.getPath());
		}
	}

	public static void output(String path, String regexp) {

		FilesAnalyze fileGroup1 = new FilesAnalyze(path, regexp);
		fileGroup1.print(System.out);
	}

	public static void main(String[] args) {
		output("D://", "[A-z|.]*");
	}
}
