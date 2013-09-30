// custom renderer function // for grid page
function renderOptions(value, metaData, record, rowIndex, colIndex, store,
		displayValue, colValue, fieldName) {
	if (fieldName != 'CONFLICT') {
		return displayValue;
	}
	if (record.get('CONFLICT') == '1')
		return '<font color=red>面试地址冲突</font>';
	else
		return '<font color=grean>正常</font>';
}