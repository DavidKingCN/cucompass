crud/src/main/webapp/WEB-INF/js
json2是json对象和字符串序列化和反序列化的工具。
jquery.form.js 表单提交的组件。
jquery.page.js 分页组件。后面会对该分页组件的调用做进一步封装，满足单页面多个分页的更简介代码的实现。
map.js   js对hashmap的实现。实现了hashmap中存储表单数据序列化为json的toJson方法。
set.js   js对set集合的实现。
util.1.0.js 自定义封装的util包，杂项，含有对cookie的操作，还有form表单填充的方法，还有js转移字符处理。
extraData.js  对form表单数据提取json串！
validate.js   对提交form表单做非空或正在表达式校验。需要自定义的属性写到input ， select等标签内！
module_demo_user.js对一个模块的增删查改的实现。
引入的js很多，已经了解了requirejs的define和require的定义和调用了，后面有时间可以以requirejs的方式加强对js的管理，以免页面过多的引入js导致不必要的错误！



