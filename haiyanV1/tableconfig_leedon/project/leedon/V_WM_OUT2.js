showMask2 = function(b){
	if(b){
		showMask(true);
		showMask = function(){};
	} else {
		showMask = function(flag) {
			flag?Ext.fly(Ext.DomQuery.select('body')[0]).mask():Ext.fly(Ext.DomQuery.select('body')[0]).unmask();
			if (!flag && !this.inited) { // 兼容
				if (Ext.get('loading-mask'))
				Ext.get('loading-mask').fadeOut({ hidden:true, duration:0.30 });
				if (Ext.get('loading'))
				Ext.get('loading').fadeOut({ hidden:true, duration:0.30 });
				this.inited=true;
			}
		};
		showMask(false);
	}
};

//保证Iframe加载出来之前，页面还被保护着,仅在编辑页面有效,配置中的HostPage没起作用
if(window.location.href.indexOf("edit.do")>0)
	showMask2(true);

//通过iframe中调用，修改grid中的分配数
function changeFPS(PID,totalFPS) {
	var grid = Ext.getCmp('SUBGRID');
	if (grid){
		grid.getStore().data.each(function(item){
			if(item.get('PRODUCTID')==PID){
				item.set('OUT_PCOUNT',totalFPS);
			}
		}
		);
		grid.getView().refresh(true);
	}
}

Hy.UIFunction.changestatus=function() {
	// alert(123);
	if(!document.getElementById('SUBFRAME')){
		alert("No Subframe Found!");
		throw("No Subframe Found!");
	}
	document.getElementById('SUBFRAME').contentWindow.changeStatus();
};

Hy.UIFunction.showmask2=function(b) {
	showMask2(b);
};