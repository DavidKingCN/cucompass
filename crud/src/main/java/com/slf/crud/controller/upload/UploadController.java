package com.slf.crud.controller.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.slf.crud.model.ResponseData;

/**
 * 
 * @author daikai
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/file")
public class UploadController {
	public static final String upload_dir = "D://upload2/";

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseData upload(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println(request.getAttribute("args"));
		System.out.println(request.getParameter("args"));
		for (int i = 0; i < files.length; i++) {
			System.out.println("fileName---------->"
					+ files[i].getOriginalFilename());

			if (!files[i].isEmpty()) {
				int pre = (int) System.currentTimeMillis();
				try {

					// 拿到输出流，同时重命名上传的文件
					FileOutputStream os = new FileOutputStream(upload_dir
							+ files[i].getOriginalFilename());
					// 拿到上传文件的输入流
					//FileInputStream in = (FileInputStream) files[i].getInputStream();
					
					ByteArrayInputStream in = (ByteArrayInputStream) files[i].getInputStream();

					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					os.flush();
					os.close();
					in.close();
					int finaltime = (int) System.currentTimeMillis();
					System.out.println(finaltime - pre);

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("上传出错");
				}
			}
		}
		Map<String, String> results = new HashMap<String, String>();
		results.put("result", "success");
		ResponseData ok = ResponseData.ok();

		ok.putDataValue("result", "upload successful!");
		return ok;
	}

	private ServletRequest getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/upload2", method = RequestMethod.POST)
	public ResponseData upload2(HttpServletRequest request,
			HttpServletResponse response/*,@RequestBody UploadVo uploadVo*/) throws IllegalStateException,
			IOException {
		
//		System.out.println(JSONUtil.toJson(uploadVo));
		//request
		System.out.println(request.getAttribute("args"));
		System.out.println(request.getParameter("args"));
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				int pre = (int) System.currentTimeMillis();

				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {

					String myFileName = file.getOriginalFilename();

					if (myFileName.trim() != "") {
						System.out.println(myFileName);

						String fileName = file.getOriginalFilename();

						String path = upload_dir+ fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}

				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}

		}
		Map<String, String> results = new HashMap<String, String>();
		results.put("result", "success");
		ResponseData ok = ResponseData.ok();

		ok.putDataValue("result", "upload successful!");
		return ok;
	}

	@RequestMapping("fileUpload")
	public String fileUpload(@RequestParam("file") CommonsMultipartFile file)
			throws IOException {

		// 用来检测程序运行时间
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + file.getOriginalFilename());

		try {
			// 获取输出流
			OutputStream os = new FileOutputStream("E:/" + new Date().getTime()
					+ file.getOriginalFilename());
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			InputStream is = file.getInputStream();
			int temp;
			// 一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
			os.flush();
			os.close();
			is.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime)
				+ "ms");
		return "/success";
	}

	@RequestMapping("fileUpload2")
	public String fileUpload2(@RequestParam("file") CommonsMultipartFile file)
			throws IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + file.getOriginalFilename());
		String path = "E:/" + new Date().getTime() + file.getOriginalFilename();

		File newFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(newFile);
		long endTime = System.currentTimeMillis();
		System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime)
				+ "ms");
		return "/success";
	}

}
