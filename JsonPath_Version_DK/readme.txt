目前支持的语义解析包括如下
1.取单值 .....{"name":"zhangsan"....}  $.name
2.取数组里一组name的值$.xxxList[*].name
3.去数组里一组<name,值> <id,值>的List的取值 $.xxxList[*]{name,id}
4.取单值 有数组内的各个值拼串  $.xxxList[*][<name,id....><,;>] 一个数组内的key值根据分割符拼接字符串 默认分隔符为,后面拼接最多有2个
 必须程序指定的字符目前支持~!@#$%&-,.;








java目录下
cn.com.davidking.json
					常量类一个

cn.com.davidking.json
					  .parse 包下实现了对json的解析 
							接口3个 
							实现类4个 
							参数传递对象一个 
							核心处理类一个
							工具类一个 
							上下文获取执行结果一个
					  .util
					       FileUtil 对文件操作的类
					       JsonValidator json校验类
					       MatchUtils 正则表达式的处理类

cn.com.davidking.test
                      	拦截类一个，接口一个  拦截处理抽离类一个
resource目录下
		/json.json 测试要解析的json文本
test目录下
   cn.com.davidking.json.parse.test
   							实现代理接口的实现并测试程序耗时
   							
   							
总结：   就程序的粒度来讲，cpu越高，缓存越高对细粒度程序执行效率越高，越接近cpu的实现(位运算，字符运算，字符串运算等等)
只有硬件更硬执行效率更高，个人感觉堆的执行要比栈的执行效率高！
                                                                          。