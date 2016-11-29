package url.cut;

import cn.com.davidking.http.URLCutter;

public class TestUrlCut {

	public static void main(String[] args) {
		String url=
				"http://www.ly.com/hotel/handler/GetHotelList.json"+
					"?page=1"+
					"&AjaxCall=HotelList"+
					//"&txtHotelName="+
					"&txtCityId=374"+
					//"&txtTownId="+
					"&txtCityName=%E4%B8%BD%E6%B1%9F"+
					//"&memberId="+
					//"&txtBusinessSectionId=0"+
					//"&txtSectionId="+
					//"&roadName="+
					//"&labelId=0"+
					//"&ddlPrice="+
					//"&radius="+
					//"&ddlLevel="+
					//"&chainids="+
					//"&HotelTopicId="+
					//"&facility="+
					"&orderby=4"+
					//"&sales="+
					//"&intime="+
					//"&isDanbao="+
					//"&tag=0"+
					"&iid=0.09667096618747395";
		System.out.println(url);
		System.out.println(URLCutter.cut(url, false));
		url = "http://www.ly.com/hotel/handler/GetHotelList.json?page=1&AjaxCall=HotelList&txtCityId=374";
		System.out.println(url.replaceAll("&", "&amp;"));
	}

}
