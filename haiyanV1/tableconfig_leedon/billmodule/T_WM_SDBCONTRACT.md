
<div id="tt" style="display:none;" ><div>请设置打印模板</div><br/><button onclick="window.print();">打印</button> 输入:<input type="text" /></div>
${WFBILLID}
${HYFORMKEY}
${HYVERSION}
<table class="tablequery">
	<tr>
		<td nowrap id="CODE_L" class="haiyan-comp-label" style="color:gray;" colspan="1" rowspan="1" >&nbsp;合同编码</td><td nowrap class="tddata" colspan="1" rowspan="1" >${CODE}</td>

		<td nowrap id="NAME_L" class="haiyan-comp-label" style="color:#000000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;合同标题</td><td nowrap class="tddata" colspan="1" rowspan="1" >${NAME}</td>

		<td nowrap id="CATEGORY_L" class="haiyan-comp-label" style="color:#990000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;类型</td><td nowrap class="tddata" colspan="6" rowspan="1" >${CATEGORY}</td>
	</tr>
	<tr>
		<td nowrap id="SHIP_SUPPLIER_L" class="haiyan-comp-label" style="color:#990000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;供应商</td><td nowrap class="tddata" colspan="1" rowspan="1" >${SHIP_SUPPLIER}</td>

		<td nowrap id="SHIP_SUPPLIERID_L" class="haiyan-comp-label" style="color:#000000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;供应商编号</td><td nowrap class="tddata" colspan="1" rowspan="1" >${SHIP_SUPPLIERID}</td>

		<td nowrap id="STATUS_L" class="haiyan-comp-label" style="color:#990000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;状态</td><td nowrap class="tddata" colspan="6" rowspan="1" >${STATUS}</td>
	</tr>
	<tr>
		<td nowrap id="WAREHOUSE_L" class="haiyan-comp-label" style="color:#990000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;收货仓库</td><td nowrap class="tddata" colspan="1" rowspan="1" >${WAREHOUSE}</td>

		<td nowrap id="WHOWNER_L" class="haiyan-comp-label" style="color:#990000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;库主</td><td nowrap class="tddata" colspan="1" rowspan="1" >${WHOWNER}</td>
		
		<td nowrap id="IMAGE_L" class="haiyan-comp-label" style="color:#000000;size:12;font-weight:bold;" colspan="1" rowspan="1" >&nbsp;扫描附件</td><td nowrap class="tddata" colspan="1" rowspan="1" >${IMAGE}</td>
	</tr>
	<tr>
		<td nowrap id="FREE_INSPACTION" class="haiyan-comp-label" style="display:none" colspan="1" rowspan="1" >&nbsp;是否免检</td><td nowrap class="tddata" colspan="1" rowspan="1" >${FREE_INSPACTION}</td>
	</tr>
	<tr>
		<td nowrap id="SUBGRID_L" class="tddata" style="color:#000099;size:12;font-weight:bold;" colspan="7" rowspan="1" >
			<span>&nbsp;商品列表</span>
			${SUBGRID}
		</td>
	</tr>
</table>

<script>
	if (Hy.isAction('queryOne.do')) {
		if ($('tt'))
			$('tt').style.display='block';
	}
	Hy.msg('提示', '请设置打印模板');
</script>