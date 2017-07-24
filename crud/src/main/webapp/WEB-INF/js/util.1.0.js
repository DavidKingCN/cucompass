/***
 * @author 戴凯
 */

var cache ;

function trim(str){ 
	return str.replace(/(^\s*)|(\s*$)/g, ""); 
}
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


Util = jQuery.extend({
	/**
	 * 查看缓存是否可用
	 */
	cacheEnabled:function(){
		return navigator.cookieEnabled;
	},
	/**
	 * 设置缓存数据
	 */
	setCache: function(name,value){
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		return null;
	},
	/**
	 * 获取缓存数据
	 */
	getCache:function (name) {
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	},
	/***
	 * 删除缓存数据
	 */
	delCache:function(name)	{
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval= this.getCache(name);
		if(cval!=null)
			document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		return null;
	},
	/***
	 * 编码转义字符串
	 */
	encode:function(val){
		return escape (val);
	},
	/***
	 * 解码转义字符串
	 */
	decode:function(val){
		return unescape(val);
	},
	
	put:function(key,val){
		if(this.cacheEnabled()){
			this.setCache(key,val);
		}else if(params != null && params != undefined){
			params.put(key,val);
		}
	},
	get:function(key){
		var val = null;
		if(this.cacheEnabled()){
			val = this.getCache(key);
			this.delCache(key);
		}else if(params !=null && params != undefined){
			
			val =  params.get(key);
			params.remove(key);
		}
		return val;
	},
	/**
	 * 将json对象赋值到dom节点，dom节点满足<input type='text' name='' value=''/>
	 * 满足大部分的input dom节点
	 */
	jsonToDom:function(jsonObj){
		for(var key in jsonObj){
			var val_=jsonObj[key];
			var thisObj = $("[name='"+key+"']");
			if(isObjNotNull(thisObj)){
				
				var tagName = thisObj.prop('tagName');
				
				var tagName = thisObj.prop('tagName');
				var nodeName = thisObj.prop('nodeName');
				if(isNotNull(val_))
				if(tagName!=null && tagName != undefined){
					
					if(tagName=='INPUT'){
						//如果是input输入域则直接赋值
						
						var inputType = thisObj.attr('type').toUpperCase();
						if(inputType=='RADIO'){
							if(val_=='是'){
								thisObj.eq(0).attr('checked', true);
							}else{
								thisObj.eq(1).attr('checked', true);
							}
						}else{
							thisObj.val(val_);
						}
					}else if(tagName=='SELECT'){
						//如果是select下拉列表
						thisObj.find("option[value='"+val_+"']").attr("selected",true);
					}else if(tagName=='TEXTAREA'){
						//如果是textarea多行文本域
						thisObj.text(val_);
					}else if(tagName=='SPAN'){
						//如果是span块
						thisObj.text(val_);
					}else if(tagName=='IMG'){
						thisObj.attr('src',val_);
					}
				}else{
					continue;
				}
				
			}
		}
	}
}); 

//alert('test = '+Util);
//alert('test = '+Util.get('id'));


/***
 * window.location.href = url?arg0=val0&arg1=val1&arg2=val2的处理方式
 * @returns {___anonymous_UrlArgs}
 */

UrlArgs = function() { // url参数
	var data, index;
	(function init() {
		data = [];
		index = {};
		var u = window.location.search.substr(1);
		if (u != '') {
			var parms = u.split('&');//decodeURIComponent()
			for (var i = 0, len = parms.length; i < len; i++) {
				if (parms[i] != '') {
					var p = parms[i].split("=");
					if (p.length == 1 || (p.length == 2 && p[1] == '')) {// p | p=
						data.push([ '' ]);
						index[p[0]] = data.length - 1;
					} else if (typeof (p[0]) == 'undefined' || p[0] == '') { // =c | =
						data[0] = [ p[1] ];
					} else if (typeof (index[p[0]]) == 'undefined') { // c=aaa
						data.push([ p[1] ]);
						index[p[0]] = data.length - 1;
					} else {// c=aaa
						data[index[p[0]]].push(p[1]);
					}
				}
			}
		}
	})();
	return {
		// 获得参数,类似request.getParameter()
		getArg : function(o) { // o: 参数名或者参数次序
			try {
				return (typeof (o) == 'number' ? data[o][0] : data[index[o]][0]);
			} catch (e) {
			}
		},
		//获得参数组, 类似request.getParameterValues()
		getArgs : function(o) { // o: 参数名或者参数次序
			try {
				return (typeof (o) == 'number' ? data[o] : data[index[o]]);
			} catch (e) {
			}
		},
		//是否含有parmName参数
		hasArg : function(parmName) {
			return typeof (parmName) == 'string' ? typeof (index[parmName]) != 'undefined'
					: false;
		},
		// 获得参数Map ,类似request.getParameterMap()
		getArgMap : function() {
			var map = {};
			try {
				for ( var p in index) {
					map[p] = data[index[p]];
				}
			} catch (e) {
			}
			return map;
		}
	}
}();