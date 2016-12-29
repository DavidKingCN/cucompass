package cn.com.davidking.http.core;

import java.io.InputStream;

import org.apache.http.client.fluent.Request;

public final class ImageReq {
	public static InputStream readFromUrl(String url)throws Exception{
		return Request.Get(url).execute().returnContent().asStream();
	}
	
	public static boolean isImage(String url){
		//jpg,bmp,tga,vst,pcd,pct,gif,ai,fpx,img,cal,wi,png 
		//tiff,psd,eps,raw,pdf,png,pxr,mac
		//eps,ai,sct,pdf,pdp,dxf
		return true;
	}
}
