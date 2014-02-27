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