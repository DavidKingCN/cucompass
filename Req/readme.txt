AsynTemplate
HttpTemplate 提供的方法基本相同。
举个例子
doGet方法后面帶有3个参数，doGet(
url,//例如http://www.baidu.com
reqHeaders,//请求头
req参数（支持pairs,json,parameters等）,//请求的参数 ?参数1=参数值1&.....
相应头//响应头信息 这里含有诸如content-type charset的设置，很有用啊，不用这个包装字符流会乱码的
)
AsynTemplate 提供client链接池的异步访问实现，理论上并发效率应该很高吧！！
 

doGetNotStop 跟HttpTemplate 一样
doGet 多一个参数是关闭的条件 
doGet(url,shutdown连接池资源标识位,reqHeaders,req参数（支持pairs,json,parameters等）,相应头)

shutdown连接池资源标识位 存在的目的执行一次性任务如果所有任务有限度条件，满足限度条件则关闭
连接池资源，等待再次调度时在开启资源，这个意义重大，后续会包装更完善某些限定的资源关闭实现
提供了多种重载方法供调用！