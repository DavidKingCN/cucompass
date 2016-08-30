/**
 * 使用前导入Jquery.js and JSON.js
 */
$.fn.serializeForm = function(){
     var obj = {};
     $.each( this.serializeArray(), function(i,o){
         var n = o.name, v = o.value;
         obj[n] = obj[n] === undefined ? v
         : $.isArray( obj[n] ) ? obj[n].concat( v )
         : [ obj[n], v ];
     });
     return JSON.stringify(obj);
 };
