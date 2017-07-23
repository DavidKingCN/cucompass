/**
 * 
 * @param str
 * @returns
 */
function trim(str){ 
	return str.replace(/(^\s*)|(\s*$)/g, ""); 
}
/**
 * 
 * @param str
 * @param is_global
 * @returns
 */
//function trimAll(str,is_global){
//	var result;
//	result = str.replace(/(^\s+)|(\s+$)/g,"");
//	if(is_global.toLowerCase()=="g"){
//		result = result.replace(/\s/g,"");
//	}
//	return result;
//}
function processNullNum(val){
	//判空
	if(val==null||val==""||val==undefined ){
		return 0;
	}
	//是否浮点数
	if(!/^(-?\d+)(\.\d+)?$/.test(val)){
		 return 0;        
    }
	return  parseFloat(val);
}
/**
 * 将long数字转换成日期类型
 * @param num
 * @returns
 */
function toDateOper(num){
	var date = new Date();
	if(num!=0)
		date = new Date(num);
	var rt = date.format('yyyy-MM-dd');
	return rt;
}
function processNull(val){
	if(val==null||val==""||val==undefined ){
		return  "";
	}else{
		return  val;
	}
}

function processSiteGj(record){
	var curSite =  record.site;
	var site_str = '';
	if(curSite != null && curSite != ''){
		var insert = curSite.split("-");
		//console.log(JSON.stringify(insert));
		site_str = getSiteDescByCode(insert[0]);
	}else{
		site_str='';
	}
	return site_str;
}
/**
 * 
 * @param iv
 * @returns {Boolean}
 */
function isNull(iv){
	var rt = (iv==null||iv==''||iv == undefined);
	return rt;
}

function isNotNull(iv){
	var rt = (iv != null && iv != '' && iv != undefined);
	return rt;
}
/**
 * 
 * @param iv
 * @returns {Boolean}
 */
function isObjNotNull(iv){
	var rt = (iv != null && iv != undefined);
	return rt;
}
/**
 * 
 * @param url
 * @returns {String}
 */
function timestamp(url){
	var getTimestamp=new Date().getTime();
	if(url.indexOf("?")>-1){
		url=url+"&_="+getTimestamp
	}else{
		url=url+"?_="+getTimestamp
	}
	return url;
}


function showName3Chars(src){
	//console.info('src='+src);
	if(src!=null && src!=''){
		//console.info('src!=null');
		if(src.length>3){
			return src.substring(0,3)+"...";
		}
		return src;
	}
}

function showNameShort(src,len){
	//console.info('src='+src);
	if(src!=null && src!=''){
		//console.info('src!=null');
		if(src.length>len){
			return src.substring(0,len)+"...";
		}
		return src;
	}
}

function testTime(){
	// 获取当前时间戳(以s为单位)
	var timestamp = Date.parse(new Date());
	timestamp = timestamp / 1000;
	//当前时间戳为：1403149534
//	console.log("当前时间戳为：" + timestamp);

	// 获取某个时间格式的时间戳
	var stringTime = "2014-07-10 10:21:12";
	var timestamp2 = Date.parse(new Date(stringTime));
	timestamp2 = timestamp2 / 1000;
	//2014-07-10 10:21:12的时间戳为：1404958872 
//	console.log(stringTime + "的时间戳为：" + timestamp2);

	// 将当前时间换成时间格式字符串
	var timestamp3 = 1403058804;
	var newDate = new Date();
	newDate.setTime(timestamp3 * 1000);
	// Wed Jun 18 2014 
//	console.log(newDate.toDateString());
	// Wed, 18 Jun 2014 02:33:24 GMT 
//	console.log(newDate.toGMTString());
	// 2014-06-18T02:33:24.000Z
//	console.log(newDate.toISOString());
	// 2014-06-18T02:33:24.000Z 
//	console.log(newDate.toJSON());
	// 2014年6月18日 
//	console.log(newDate.toLocaleDateString());
	// 2014年6月18日 上午10:33:24 
//	console.log(newDate.toLocaleString());
	// 上午10:33:24 
//	console.log(newDate.toLocaleTimeString());
	// Wed Jun 18 2014 10:33:24 GMT+0800 (中国标准时间)
//	console.log(newDate.toString());
	// 10:33:24 GMT+0800 (中国标准时间) 
//	console.log(newDate.toTimeString());
	// Wed, 18 Jun 2014 02:33:24 GMT
//	console.log(newDate.toUTCString());
//	console.log(newDate.format('yyyy-MM-dd h:m:s'));
}

Date.prototype.format = function(format) {
       var date = {
              "M+": this.getMonth() + 1,
              "d+": this.getDate(),
              "h+": this.getHours(),
              "m+": this.getMinutes(),
              "s+": this.getSeconds(),
              "q+": Math.floor((this.getMonth() + 3) / 3),
              "S+": this.getMilliseconds()
       };
       if (/(y+)/i.test(format)) {
              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
       }
       for (var k in date) {
              if (new RegExp("(" + k + ")").test(format)) {
                     format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
              }
       }
       return format;
}


