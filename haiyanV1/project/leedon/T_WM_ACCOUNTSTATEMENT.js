printtest=function() {
	$('info').style.display='none';
	window.print();
}
Hy.UIFunction.showprint=function() {
}

Ext.onReady(function(){
	if(Ext.isIE)
		setTimeout(wrapFunc,100);
	else
		wrapFunc();
});

function wrapFunc(){

	makeResize();
	
	var month = $("MONTH").value,re=new RegExp("20(\\d\\d)01"),r;
	var lastMonth;
	r=month.match(re);
	if(r){
		lastMonth = "20"+(r[1]-1)+"12";
	}else{
		lastMonth = month-1;
	}
	
	var suppID=$("SUPPLIER").value;
	if(!Ext.isEmpty(suppID)){
		Ext.Ajax.request( {
			url : 'project/leedon/T_WM_ACTSTMT.jsp?__opr=getLastMon&__suppID='+suppID+'&__mon='+month+'&__lastmon='+lastMonth
			, success : function(response) {
				var d = Ext.decode(response.responseText);
				if (d.success){
					Hy.setValue('LAST_AMOUNT', d.all); //上月应付
					Hy.setValue('LAST_R_AMOUNT', d.payed); //上月实付
					var topay = (parseFloat(d.topay)*1000+parseFloat(d.all)*1000-parseFloat(d.payed)*1000)/1000;
					Hy.setValue('ALL_AMOUNT', topay); //本月应付
					if($("BILL_AMOUNT"))
						Hy.setValue('BILL_AMOUNT', d.topay); //本月应付
					if(!d.getbill)
						$("CONTENT_L").innerHTML="<div style='font-size:10px;margin:0px 0px 0px 15px;color:red'>我们未能收到贵司上月发票，请您先核对<br>上月发票再开具本月，疑问请致电本公司</div>";
				}
			}
		});	
	}
	
	//$("ALL_AMOUNT").readOnly=true;
	$("TITLE_L").innerHTML="<div style='text-align:center;font-size:40px;margin:20px 0 20px 0'>&nbsp;供应商月度对账单</div>";
	//$("TITLE").parentNode.parentNode.remove();
	$("TITLE").style.display="none";
	$("TITLE_L").setAttribute("colspan",'4');
	$("MONTH_L").innerHTML="<div style='font-size:10px;margin:0px 0px 0px 15px'>月度:"+$("MONTH").value+"</div>";
	$("MONTH").style.display="none";

	$("SUPPLIER_L").innerHTML="<div style='font-size:10px;margin:0px 0px 0px 15px'>供应商:"+$("__SUPPLIER__NAME").value+"</div>";
	if(!Ext.isIE)
		$("__SUPPLIER__NAME").parentNode.parentNode.remove();
	else
		$("__SUPPLIER__NAME").parentNode.style.display="none";
	
	$("BEGIN_DATE_L").innerHTML="<div style='font-size:10px;margin:0px 0px 0px 15px'>日期:"+lastMonth+"21"+" 至 "+month+"20</div>";
//	$("BEGIN_DATE").parentNode.parentNode.remove();
	$("BEGIN_DATE").parentNode.style.display="none";

//	$("CONTENT").parentNode.parentNode.remove();
	$("CONTENT").style.display="none";
	var d = new Date();
	var year = d.getFullYear();
	var month1 = add_zero(d.getMonth()+1);
	var day = add_zero(d.getDate());
	if(""+year+month1+day<month+"20"){
		if(!Ext.isIE)
			$("BILL_AMOUNT").parentNode.parentNode.remove();
		else
			$("BILL_AMOUNT").parentNode.style.display="none";
		$("BILL_AMOUNT_L").innerHTML="<div style='font-size:10px;margin:0px 0px 0px 15px'>待月结帐期到达后，系统将会显示开票金额</div>";
	}
	if(document.URL.indexOf("queryOne.do")>0){
		$("PRINT_L").innerHTML="<button onclick=\"printtest();\" onmouseover=\"$('info').style.display='block';\"" +
				"onmouseout=\"$('info').style.display='none';\""+
				">打印</button><div id='info' style='display:none;color:red'>如打印未能全部显示列表，请调整页面布局为横向即可<div>";
		$("PRINT").parentNode.style.display="none";
	}else{
		$("PRINT_L").parentNode.style.display="none";
		$("PRINT").parentNode.style.display="none";
	}
}

function add_zero(temp)
{
	if(temp<10) return "0"+temp;
	else return temp;
}

function makeResize(){
	try {
		if ($('ID')!=null && $('ID').value=='-1')
			alert('ID lost.Not support!)');
		
		if ($('BILL_STATUS'))
			if ($('BILL_STATUS').value=='init'||Hy.isEmpty($('BILL_STATUS').value)) {
			} else {
			}
	}catch(E){}
	var g;
	g=Ext.getCmp('SUBGRID');
	
	var resizer = new Ext.Resizable('SUBGRID', {
//		handles: 'e',
	    minHeight: 200,
		wrap:true,
		pinned: true,
		dynamic: true
	});
	resizer.on('resize', function() {
		var box = resizer.getEl().getSize();
		this.setSize(box);
		this.body.setHeight(box.height);
		//this.syncSize();
		this.doLayout();
	}, g);
}
