//通过iframe中调用，修改grid中的分配数
function changeFPS(PID,totalFPS) {
	var grid = Ext.getCmp('SUBGRID')
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
