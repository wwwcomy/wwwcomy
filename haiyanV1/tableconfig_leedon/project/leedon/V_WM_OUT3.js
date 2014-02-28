
Hy.UIFunction.getsuborderid=function() {
	var paras=Hy.getQueryParams().split('&');
	var result;
	paras.each(function(item,index){
		var tmp = item.split("=");
		if(tmp[0]=="SUBORDER_ID")
			result=tmp[1];
	});
	return result;
};