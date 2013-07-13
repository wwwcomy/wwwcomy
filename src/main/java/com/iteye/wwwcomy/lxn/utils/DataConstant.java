package com.iteye.wwwcomy.lxn.utils;

public class DataConstant {
	public static final String SERVICE = "SERVICE";

	public static final String SERVER = "SERVER";

	public static final String NAME_FAVCHACH = "favourate.xml";

	public static final String NAME_FILEIDS = "fileids.bin";

	public static final String NAME_JARFILEIDS = "jarfileids.bin";

	public static final String NODENAME_MD5CONFIG = "CONFIG";

	public static final String NODENAME_MD5FILE = "FILE";

	public static final String NODENAME_MD5CODE = "MD5";

	public static final String NODENAME_FAVCONFIG = "CONFIG";

	public static final String NODENAME_FAVNAME = "NAME";

	public static final String NODENAME_FAVVALUE = "VALUE";

	// //////////////////////// 下面全是一些字段的常量 //////////////////////////

	public final static String STR_FLD_BILLID = "BillID";

	public final static String STR_FLD_BILLDTLID = "BillDtlID";

	public final static String STR_FLD_BILLTYPE = "BillType";

	public final static String STR_FLD_CORPID = "CorpID";

	public final static String STR_FLD_ROWNO = "RowNo";

	public final static String STR_FLD_SUBTOTAL = "Subtotal";

	public final static String STR_FLD_NO = "No";

	public final static String STR_FLD_BILLDATE = "BillDate";

	// 和expand有关的常量 /////////////////

	/**
	 * 是否是扩展出来的合计字段
	 */
	public final static String STR_ISEXPANDSUMFIELD = "_IsExpandSumField";

	/**
	 * 表示行的数据过滤,用于计算CalcExpandValue()公式
	 */
	public final static String STR_FLD_ROWDATAFILTER_EX = "_RowDataFilter";

	/**
	 * 表示扩展字段的过滤条件
	 */
	public final static String STR_FLD_DATAFILTER = "_FieldDataFilter";

	/**
	 * 只有扩展行,没有扩展列时,指向真实数据的行
	 */
	public final static String STR_FLD_DATABKMK = "DataBkmk";

	/**
	 * 行扩展时,指向扩展数据的Bookmark.比如按科目扩展,那有个数据集里的所有的科目,那这个字段里的值指向科目数据集里的值
	 */
	public final static String STR_FLD_EXPANDROWBKMK = "ExpandRowBkmk";

	// 下面几个为了维护界面Bookmark和数据对象Bookmark的关系,主要是字段名 //////////////////////////

	public final static String STR_FLD_GRIDBKMK = "GridBookmark";

	public final static String STR_FLD_TBLIDX = "TableIndex";

	public final static String STR_FLD_TBLBKMK = "TableBookmark";

	public final static String STR_FLD_TBLNEWBKMK = "TableNewBookmark";

	// //////////////////////// 下面几个是单据映射时用的,主要是字段名 //////////////////////////

	public final static String STR_FLD_ID = "MapDataID";

	public final static String STR_FLD_EDGEKEY = "EdgeKey";

	public final static String STR_FLD_EDGEID = "EdgeID";

	// public final static String STR_FLD_BKMK = "Bookmark";

	public final static String STR_FLD_TGTBILLDTLID = "TgtBillDtlID";

	public final static String STR_FLD_SRCBILLDTLID = "SrcBillDtlID";

	public final static String STR_FLD_AMOUNT = "Amount";

	public final static String STR_FLD_MONEY = "Money";

	public final static String STR_FLD_TREEID = "TreeID";

	public final static String STR_FLD_LEFT = "TreeLeft";

	public final static String STR_FLD_RIGHT = "TreeRight";

	public final static String STR_FLD_PARENT = "ParentID";

	public final static String STR_FLD_FOCUS = "Focus";

	public final static String STR_FLD_PBILLID = "PbillID";

	public final static String STR_FLD_PATH = "Path";

	public final static String STR_FLD_NOTES = "Notes";

	public final static String STR_FLD_CREATER = "Creater";

	public final static String STR_FLD_CREATETIME = "CreateTime";

	public final static String STR_FLD_ROWDATAFILTER = "RowDataFilter";

	// 下面两个字段用于虚拟迁移表取数时,虚数据表建立和扩展数据表之间的关系 //////////////////////////

	public final static String STR_FLD_TBLNAME = "TblName";

	public final static String STR_FLD_TBLEXIDX = "TblExIdx";

	public final static String STR_FLD_BKMKEX = "BkmkEx";

	// 保存时给程序加锁的值
	public final static String STR_FLD_LockNum = "LockNum";

	public final static String STR_BILL_SELECT = "Select";

	public final static String STR_FLD_DTLLockNum = "DtlLockNum";

	public final static String STR_BILL_VersionNumber = "Version_Number";

	// 用于成本核算的Bill
	public final static String STR_BILL_COST = "CostingBill";

	// 数据库中的字段，表示该行数据是出库还是入库
	public final static String STR_FLDDRIECTION = "Direction";

	public final static String STR_FLD_NEEDUPDATE = "NeedUpdate";

	// ////////////////////// 下面是一些和单据钩稽相关的常量 //////////////////////////

	public final static String STR_FLD_HOOKID = "HookID";

	public final static String STR_FLD_LEFTBILLTYPE = "Left" + STR_FLD_BILLTYPE;

	public final static String STR_FLD_LEFTBILLID = "Left" + STR_FLD_BILLID;

	public final static String STR_FLD_LEFTBILLDTLID = "Left"
			+ STR_FLD_BILLDTLID;

	public final static String STR_FLD_RIGHTBILLTYPE = "Right"
			+ STR_FLD_BILLTYPE;

	public final static String STR_FLD_RIGHTBILLID = "Right" + STR_FLD_BILLID;

	public final static String STR_FLD_RIGHTBILLDTLID = "Right"
			+ STR_FLD_BILLDTLID;

	public final static String STR_FLD_LEFTFOCUS = "LeftFocus";

	public final static String STR_FLD_RIGHTFOCUS = "RightFocus";

	public final static String STR_FLD_RESULT = "Result";

	// ////////////////////// 下面是和信用管理相关的常量 //////////////////////////

	public final static String STR_TBL_CREDIT = "SCM_Credit"; // 信用表的Key

	public final static String STR_FLD_CREDITID = "ID"; // 信用表的ID

	public final static String STR_FLD_CUSTOMERID = "CustomerID"; // 客户字段的Key

	public final static String STR_TBL_CPDEALERDTL = "CP_DLRDTL"; // 客户字典明细表

	public final static String STR_FLD_ISCREDITCONTROL = "ISCREDITCONTROL"; // 是否进行信用控制

	public final static String STR_FLD_CREDITMONEY = "CREDITMONEY"; // 信用额度

	public final static String STR_FLD_CREDITTIME = "CREDITTIME"; // 信用期限

	public static final String STR_SEPARATOR_BLANK = "";

	public static final String STR_FLD_FIXEDINDEX = "FixedIndex";

	public static final String STR_FLD_FIXEDVALUE = "FixedValue";

	public static final String STR_FLD_FIXEDFLD = "FixedFld";

	// 期间
	public static final String STR_PERIOD_DAY = "Day";// 日

	public static final String STR_PERIOD_MONTH = "Month";// 月份

	public static final String STR_PERIOD_WEEK = "Week";// 星期

	public static final String STR_PERIOD_PERIOD = "Period";// 会计期

	public static final String STR_GatherAngle_Key = "GatherAngle";// 汇总角度key

	public static final String STR_SumArrangement_Key = "SumArrangement";// 汇总层次KEY

	public static final String STR_OnlyShowSum_Key = "OnlyShowSum";// 只显示总数据key

	public static final String STR_IsSubtaotal_Key = "IsSubTotal";// 是否进行小计

	public static final String STR_FormInitialize = "_FormInitialize";

	public static final String STR_FormUnload = "_FormUnload";

	// ///////////////////////////////////////////////////////

	public static final String SS_COMMA = ",";

	public static final String SS_RADIXPOINT = ".";

	public static final String SS_EQUAL = "=";

	public static final String SS_MAX = ">";

	public static final String SS_MAXEQUAL = ">=";

	public static final String SS_MIN = "<";

	public static final String SS_MINEQUAL = "<=";

	public static final String SS_NOTEQUAL = "<>";

	public static final String SS_LIKE = "LIKE";

	public static final String SS_IN = "IN";

	public static final String SS_BETWEEN = "BETWEEN";

	public static final String SS_AND = "AND";

	public static final String SS_OR = "OR";

	public static final String SS_WHERE = "WHERE";

	public static final String SS_SELECT = "SELECT";

	public static final String SS_DELETE = "DELETE";

	public static final String SS_FROM = "FROM";

	public static final String SS_GROUP = "GROUP";

	public static final String SS_ORDER = "ORDER";

	public static final String SS_GROUPBY = "GROUP BY";

	public static final String SS_ORDERBY = "ORDER BY";

	// //////////////////////////////////////////////////////////////////

	public static final String STR_SEPARATOR = String
			.valueOf(new char[] { (char) 127 });

	public static final String STR_SEPARATOR2 = String
			.valueOf(new char[] { (char) 128 });

	public static final String STR_SEPARATOR3 = String
			.valueOf(new char[] { (char) 129 });

	public static final String STR_DSN = "DSN";

	public static final String STR_CURRENTTIMESTAMP = "CurrentTimestamp";

	public static final String STR_PUBLICSYSTEM = "Public";

	public static final String STR_SERVER = "Server";

	public static final String STR_SERVICE = "Service";

	public static final String STR_DBTYPE = "DBType";

	public static final String STR_SUBSYSTEMCODE = "SubsystemCode";

	public static final String STR_LANGUAGEID = "LangID";

	public static final String STR_SESSIONID = "SessionID";

	public static final String STR_SESSIONMODE = "SessionMode";

	public static final String STR_DSNDESCRIPTION = "DSNDescription";

	public static final String STR_USERCODE = "UserCode";

	public static final String STR_USERNAME = "UserName";

	public static final String STR_ROLEID = "RoleID";

	public static final String STR_ROLELIST = "RoleList";

	public static final String STR_ROLENAME = "RoleName";

	public static final String STR_OPERATORID = "OperatorID";

	public static final String STR_WORKFLOWSETTING = "WorkflowSetting";

	public static final String STR_ID = "ID";

	public static final String STR_CREATEBYID = "CreateByID";

	public static final String STR_CREATETIME = "CreateTime";

	public static final String STR_UPDATEBYID = "UpdateByID";

	public static final String STR_UPDATETIME = "UpdateTime";

	public static final String STR_CORPID = "CorpID";

	public static final String STR_CORPName = "CorpName";

	public static final String STR_ITEMID = "ItemID";

	public static final String STR_USERID = "UserID";

	public static final String STR_IDENTITYKEY = "IdentityKey";

	public static final String STR_TREE = "Tree";

	public static final String STR_RVALUE = "RightsValue";

	public static final String STR_STATUS = "Status";

	public static final String STR_PARENTID = "ParentID";

	public static final String STR_NODETYPE = "NodeType";

	public static final String STR_HASCHILD = "HasChild";

	public static final String STR_CODE = "Code";

	public static final String STR_TLEFT = "tleft";

	public static final String STR_TRIGHT = "tright";

	public static final String STR_LEVEL = "LEVEL";

	public static final String STR_BLANK = " ";

	public static final String STR_SEM = ";"; // '分号

	public static final String STR_COMMA = ","; // '逗号

	public static final String STR_SLASH = "\\"; // '斜杠

	public static final String STR_DOT = "."; // '点号

	public static final String STR_REG_ITEM_LASTUSERROLE = "LastUserRole"; // '操作员登陆系统选择的角色ID

	public static final String STR_SUBSYSTEM_GL = "GL";// 总账子系统的代码

	public static final String STR_OPSTATUS = "OpStatus";// 控制状态，表示当前纪录的操作状态，要小心维护

	public static final String STR_SEPARATOR_2 = "€";

	// ////////////////////////////////////////////////////////////////

	public static final String C_INIT = "_c"; // ' 期初

	public static final String C_IN = "_i"; // ' 入

	public static final String C_OUT = "_o"; // ' 出

	public static final String C_END = "_e"; // ' 期未

	// ///////////////////////////////////////////////////////

	public final static String CLASS_PREFIX = "com.bokesoft.myerp.";

	public final static String UI_CLASS_PREFIX = CLASS_PREFIX + "ui.";

	public final static String BILLUI_CLASS_PREFIX = CLASS_PREFIX + "billui.";

	public final static String MID_CLASS_PREFIX = CLASS_PREFIX + "mid.";

	// public final static String EX_CLASS_PREFIX =CLASS_PREFIX+ "ex.";

	// ///////////////////////////////////////////////////////

	public static final String adFilterNone_V = "";

	public static final String adFilterPendingRecords_V = "AdFilterPendingRecords";

	public static char SEPERATOR = 127;

	// ////////////////////////////////////////////////////////////////

	public static final Integer SQLSERVER_LIMITEDRSTCOUNT = 500;

	public static final Integer DEFAULT_LANGID = 2052;

	public static final Integer LNG_STATUS_DELETE = -100; // 标识为已删除状态

	public static final Integer LNG_IDX_AMOUNT = 0;

	public static final Integer LNG_IDX_MONEY = 1;

	public static final Integer LNG_BEFORE_START_PERIOD = -1; // '程序中表示启用前的期间

	public static final Integer LNG_PRICE_PERCISION = 18; // 单价和金额的小数精度

	public static final Integer LNG_PRICE_SCALE = 10;

	public static final Integer LNG_MONEY_PERCISION = 18;

	public static final Integer LNG_MONEY_SCALE = 4;

	//
	// ' 总账对象
	// ' 字典类对象
	// public static final String STR_DICTPROJECT = "BKGNTMidDictLib"
	// ' 银行对象
	// public static final String STR_BANKOBJECT = "BKMBankControl"
	// ' 现金流量对象
	// public static final String STR_CASHITEMOBJECT = "BKMCashItem"
	// '公式对象
	// public static final String STR_FORMULAPROJECT = "BKMFormula"
	// public static final String STR_GLPROJECT = "Temporary"
	// public static final String STR_SYSTEMPROJECT = "Temporary"
	// '总帐字典类其他
	// public static final String STR_GLDICTOTHERPROJECT = "BKMGLDictOther"
	//
	// ' Object Define
	// ' 基本模块里定义的对象
	// ' 系统对象
	//
	// '== 系统基本模块 ==
	// public static final String STR_CDictionaryPROGID =
	// "BKMSysBase.CDictionary" '字典类结构查询类
	// public static final String STR_utilDictStaticPROGID =
	// "BKMSysBase.utilDictStatic" '字典类静态信息查询类
	// public static final String STR_UtilDictDynamicPROGID =
	// "BKMSysBase.utilDictDynamic" '字典类动态信息查询类
	// public static final String STR_utilDictEditorPROGID =
	// "BKMSysBase.utilDictEditor" '字典类（状态）修改类
	// public static final String STR_SYSAUTONUMBERPROGID =
	// "BKMSysBase.SysAutoNumber" 'ϵͳID
	// public static final String STR_SYSBLOBPROGID = "BKMSysBase.CBlob" '大块数据访问
	// public static final String STR_SysDataRightsPROGID =
	// "BKMSysBase.SysDataRights" '数据权限
	// public static final String STR_SYSDBQUERYPROGID = "BKMSysBase.SysDBQuery"
	// '系统数据库查询
	// public static final String STR_SYSDSNPROGID = "BKMSysBase.SysDSN" 'DSN操作
	// public static final String STR_SysLoginPROGID = "BKMSysBase.SysLogin" '登录
	// public static final String STR_SYSUSERRIGHTPROGID =
	// "BKMSysBase.SysUserRight" '用户权限
	// public static final String STR_SysVersionPROGID = "BKMSysBase.SysVersion"
	// '系统（中间层）版本
	// public static final String STR_SYSWARNINGPROGID = "BKMSysBase.SysWarning"
	// '警告处理
	// public static final String STR_UTILACCOUNTPROGID =
	// "BKMSysBase.utilAccount" '科目相关
	// public static final String STR_UTILANALYSISPROGID =
	// "BKMSysBase.utilAnalysis" '分析点相关
	// public static final String STR_UTILLOCKPROGID = "BKMSysBase.utilLock"
	// '锁功能相关
	// public static final String STR_UTILDICTIONARYPROGID =
	// "BKMSysBase.utilDictionary" '字典类总体相关
	// public static final String STR_UTILDICTTPROGID = "BKMSysBase.utilDictT"
	// '字典类统一操作相关
	// public static final String STR_UTILPERIODPROGID = "BKMSysBase.utilPeriod"
	// '会计期相关
	// public static final String STR_UTILPROPERTIESPROGID =
	// "BKMSysBase.utilProperties" 'BaseInf访问
	//
	// ' 凭证模块
	// public static final String STR_BKMVOUCHERPROJECT = "BKMVoucher"
	// public static final String STR_DBGLBALANOBJ = "DBGLBalan"
	// public static final String STR_DBGLBALANPROGID = STR_BKMVOUCHERPROJECT &
	// "." & STR_DBGLBALANOBJ
	// public static final String STR_DBGLBUSISUMMARYOBJ = "DBGLBusiSummary"
	// public static final String STR_DBGLBUSISUMMARYPROGID =
	// STR_BKMVOUCHERPROJECT & "." & STR_DBGLBUSISUMMARYOBJ
	// public static final String STR_DBVOUCHERDTLOBJ = "DBVoucherDtl"
	// public static final String STR_DBVOUCHERDTLPROGID = STR_BKMVOUCHERPROJECT
	// & "." & STR_DBVOUCHERDTLOBJ
	// public static final String STR_DBVOUCHERHEADOBJ = "DBVoucherHead"
	// public static final String STR_DBVOUCHERHEADPROGID =
	// STR_BKMVOUCHERPROJECT & "." & STR_DBVOUCHERHEADOBJ
	// public static final String STR_FUNVOUCHERHEADOBJ = "funVoucherHead"
	// public static final String STR_FUNVOUCHERDTLOBJ = "funVoucherDtl"
	// public static final String STR_FUNVOUCHERHEADPROGID =
	// STR_BKMVOUCHERPROJECT & "." & STR_FUNVOUCHERHEADOBJ
	// public static final String STR_FUNVOUCHERDTLPROGID =
	// STR_BKMVOUCHERPROJECT & "." & STR_FUNVOUCHERDTLOBJ
	// public static final String STR_GNTVOUCHEROBJ = "GNTVoucher"
	// public static final String STR_GNTVOUCHERPROGID = STR_BKMVOUCHERPROJECT &
	// "." & STR_GNTVOUCHEROBJ
	// public static final String STR_GNTVOUCHERCTRLPROGID =
	// STR_BKMVOUCHERPROJECT & ".GNTVoucherCtrl"
	// public static final String STR_CMIDVOUCHERSAVE = STR_BKMVOUCHERPROJECT &
	// ".CMidVoucherSave"
	// ' 凭证的功能
	// public static final String STR_UTILVOUCHEROBJ = "UTILVOUCHER"
	// public static final String STR_UTILVOUCHERPROGID = STR_BKMVOUCHERPROJECT
	// & "." & STR_UTILVOUCHEROBJ
	//
	// ' 总账系统的功能
	// public static final String STR_UTILGLPROGID = "BKMGLUtil.utilGL"
	//
	// ' 字典类
	// ' 银行账号
	// public static final String STR_GNTBANKACCOUNTPROGID =
	// "BKMDictOther.GNTBankAccount"
	// public static final String STR_DBBANKACCOUNTPROGID =
	// "BKMDictOther.DBBankAccount"
	// ' 地区
	// public static final String STR_GNTAREAPROGID = "BKMDictOther.GNTArea"
	// public static final String STR_DBAREAPROGID = "BKMDictOther.DBArea"
	// public static final String STR_FUNAREAPROGID = "BKMDictOther.funArea"
	// ' 汇率
	// public static final String STR_GNTCURRENCYPROGID =
	// "BKMDictCurrency.GNTCurrency"
	// public static final String STR_DBCURRENCYPROGID =
	// "BKMDictCurrency.DBCurrency"
	// public static final String STR_FUNCURRENCYPROGID =
	// "BKMDictCurrency.funCurrency"
	// ' 公司
	// public static final String STR_GNTCORPORATIONPROGID =
	// "BKMDictOther.GNTCorporation"
	// public static final String STR_DBCORPORATIONPROGID =
	// "BKMDictOther.DBCorporation"
	// public static final String STR_FUNCORPORATIONPROGID =
	// "BKMDictOther.funCorporation"
	// ' 税率
	// public static final String STR_GNTTAXPROGID = "BKMDictOther.GNTTax"
	// public static final String STR_DBTAXPROGID = "BKMDictOther.DBTax"
	// ' 结算方式
	// public static final String STR_GNTSETTLEMODEPROGID =
	// "BKMDictOther.GNTSettleMode"
	// public static final String STR_DBSETTLEMODEPROGID =
	// "BKMDictOther.DBSettleMode"
	// ' 凭证类别对应科目
	// public static final String STR_GNTVTACCOUNTPROGID =
	// "BKMDictOther.GNTVTAccount"
	// public static final String STR_DBVTACCOUNTPROGID =
	// "BKMDictOther.DBVTAccount"
	// public static final String STR_FUNVTACCOUNTPROGID =
	// "BKMDictOther.funVTAccount"
	// ' 项目
	// public static final String STR_GNTITEMPROGID = "BKMDictOther.GNTItem"
	// public static final String STR_DBITEMPROGID = "BKMDictOther.DBItem"
	// public static final String STR_FUNITEMPROGID = "BKMDictOther.funItem"
	// public static final String STR_GNTITEMTYPEPROGID =
	// "BKMDictOther.GNTItemType"
	// ' 行业
	// public static final String STR_GNTTRADEPROGID = "BKMDictOther.GNTTrade"
	// public static final String STR_DBTRADEPROGID = "BKMDictOther.DBTrade"
	// ' Businss
	// public static final String STR_GNTBUSINESSPROGID =
	// "BKMDictOther.GNTBusiness"
	// public static final String STR_DBBUSINESSPROGID =
	// "BKMDictOther.DBBusiness"
	// public static final String STR_GNTBUSINESSTYPEPROGID =
	// "BKMDictOther.GNTBusinessType"
	//
	// ' 汇率
	// public static final String STR_GNTEXCHANGERATEPROGID =
	// "BKMDictExchRate.GNTExchangeRate"
	// public static final String STR_DBEXCHANGERATEPROGID =
	// "BKMDictExchRate.DBExchangeRate"
	// ' 汇率数据
	// public static final String STR_GNTEXCHRDATAPROGID =
	// "BKMDictExchRate.GNTExchRData"
	// public static final String STR_DBEXCHRDATAPROGID =
	// "BKMDictExchRate.DBExchRData"
	//
	// ' 功能权限
	// public static final String STR_GNTFUNRIGHTPROGID =
	// "BKMDictOperator.GNTFunRight"
	// public static final String STR_DBFUNRIGHTPROGID =
	// "BKMDictOperator.DBFunRight"
	// ' 操作员
	// public static final String STR_GNTOPERATORPROGID =
	// "BKMDictOperator.GNTOperator"
	// public static final String STR_DBOPERATORPROGID =
	// "BKMDictOperator.DBOperator"
	// public static final String STR_FUNOPERATORPROGID =
	// "BKMDictOperator.funOperator"
	// ' 操作员权限
	// public static final String STR_GNTOPTRIGHTPROGID =
	// "BKMDictOperator.GNTOptRight"
	// public static final String STR_DBOPTRIGHTPROGID =
	// "BKMDictOperator.DBOptRight"
	// ' 操作员，组的关系
	// public static final String STR_GNTOPTGRPOPTPROGID =
	// "BKMDictOperator.GNTOptGrpOpt"
	// public static final String STR_DBOPTGROOPTPROGID =
	// "BKMDictOperator.DBOptGrpOpt"
	// ' 操作员的数据权限
	// public static final String STR_GNTOPTDATARIGHT =
	// "BKMDictOperator.GNTDataRight"
	//
	//
	// ' 凭证类别
	// public static final String STR_GNTVOUCHERTYPEPROGID =
	// "BKMDictVoucherType.GNTVoucherType"
	// public static final String STR_DBVOUCHERTYPEPROGID =
	// "BKMDictVoucherType.DBVoucherType"
	// public static final String STR_FUNVOUCHERTYPEPROGID =
	// "BKMDictVoucherType.funVoucherType"
	//
	// ' 部门
	// public static final String STR_GNTDEPARTMENTPROGID =
	// "BKMDictDepartment.GNTDepartment"
	// public static final String STR_DBDEPARTMENTPROGID =
	// "BKMDictDepartment.DBDepartment"
	// public static final String STR_FUNDEPARTMENTPROGID =
	// "BKMDictDeaprtment.funDepartment"
	//
	// ' ְԱ
	// public static final String STR_GNTEMPLOYEEPROGID =
	// "BKMDictEmployee.GNTEmployee"
	// public static final String STR_DBEMPLOYEEPROGID =
	// "BKMDictEmployee.DBEmployee"
	// public static final String STR_FUNEMPLOYEEPROGID =
	// "BKMDictEmployee.funEmployee"
	//
	// ' 仓库
	// public static final String STR_GNTWAREHOUSEPROGID =
	// "BKMDictWareHouse.GNTWareHouse"
	// public static final String STR_DBWAREHOUSEPROGID =
	// "BKMDictWareHouse.DBWareHouse"
	// public static final String STR_FUNWAREHOUSEPROGID =
	// "BKMDictWareHouse.funWareHouse"
	//
	// ' 物料
	// public static final String STR_GNTMATERIALPROGID =
	// "BKMDictMaterial.GNTMaterial"
	// public static final String STR_DBMATERIALPROGID =
	// "BKMDictMaterial.DBMaterial"
	// public static final String STR_DBMATERIALUNITPROGID =
	// "BKMDictMaterial.DBMaterialUnit"
	// public static final String STR_GNTMATERIALTYPEPROGID =
	// "BKMDictMaterial.GNTMaterialType"
	//
	// ' 往来户
	// public static final String STR_GNTDEALERPROGID =
	// "BKMDictDealer.GNTDealer"
	// public static final String STR_DBDEALERADRPROGID =
	// "BKMDictDealer.DBDealerAdr"
	// public static final String STR_DBDEALERBANKPROGID =
	// "BKMDictDealer.DBDealerBank"
	// public static final String STR_DBDEALERPROGID = "BKMDictDealer.DBDealer"
	// public static final String STR_GNTDEALERTYPEPROGID =
	// "BKMDictDealer.GNTDealerType"
	//
	// ' 科目
	// public static final String STR_GNTACCOUNTPROGID =
	// "BKMDictAccount.GNTAccount"
	// public static final String STR_DBACCOUNTPROGID =
	// "BKMDictAccount.DBAccount"
	// public static final String STR_GNTACCOUNTTYPEPROGID =
	// "BKMDictAccount.GNTAccountType"
	// '科目的数据权限
	// public static final String STR_GNTACCOUNTDATARIGHT =
	// "BKMDictAccount.GNTAccDataRight"
	//
	// ' 会计期
	// public static final String STR_GNTPERIODPROGID =
	// "BKMDictPeriod.GNTPeriod"
	// public static final String STR_DBPERIODPROGID = "BKMDictPeriod.DBPeriod"
	//
	// ' 总帐字典
	// ' 科目于额控制
	// public static final String STR_GNTACCBLNCTRLPROGID =
	// "BKMAccountBalCtrl.GNTAccBlnCtrl"
	// public static final String STR_DBACCBLNCTRLPROGID =
	// "BKMAccountBalCtrl.DBAccBlnCtrl"
	//
	// ' 杂项，
	// public static final String STR_UTILACCOUNTINITPROGID =
	// "BKMAccountInit.utilAccountInit"
	// public static final String STR_UTILMULTIREPORTPROGID =
	// "BKMMisc.utilMultiReport"
	// public static final String STR_UTILGLINITPROGID = "BKMMisc.utilGLINIT"
	//
	// '
	// ' ---------------------------------------- 调整完的ProgID
	// --------------------------------
	//
	// ' 总账系统模块
	// public static final String STR_DBACCCASHITEMOBJ = "DBAccCashItem"
	// public static final String STR_DBACCCASHITEMPROGID =
	// STR_BKMVOUCHERPROJECT & "." & STR_DBACCCASHITEMOBJ
	//
	//
	// '' 地区
	// 'public static final String STR_GNTAREAOBJ = "GNTArea"
	// 'public static final String STR_DBAREAOBJ = "DBArea"
	// 'public static final String STR_FUNAREAOBJ = "funArea"
	// '' 汇率
	// 'public static final String STR_GNTCURRENCYOBJ = "GNTCurrency"
	// 'public static final String STR_DBCURRENCYOBJ = "DBCurrency"
	// 'public static final String STR_FUNCURRENCYOBJ = "funCurrency"
	// '' 公司
	// 'public static final String STR_GNTCORPORATIONOBJ = "GNTCorporation"
	// 'public static final String STR_DBCORPORATIONOBJ = "DBCorporation"
	// 'public static final String STR_FUNCORPORATIONOBJ = "funCorporation"
	// '' 税率
	// 'public static final String STR_GNTTAXOBJ = "GNTTax"
	// 'public static final String STR_DBTAXOBJ = "DBTax"
	// '' 结算方式
	// 'public static final String STR_GNTSETTLEMODEOBJ = "GNTSettleMode"
	// 'public static final String STR_DBSETTLEMODEOBJ = "DBSettleMode"
	// '' 汇率
	// 'public static final String STR_GNTEXCHANGERATEOBJ = "GNTExchangeRate"
	// 'public static final String STR_DBEXCHANGERATEOBJ = "DBExchangeRate"
	// '' 汇率数据
	// 'public static final String STR_GNTEXCHRDATAOBJ = "GNTExchRData"
	// 'public static final String STR_DBEXCHRDATAOBJ = "DBExchRData"
	// '' 功能权限
	// 'public static final String STR_GNTFUNRIGHTOBJ = "GNTFunRight"
	// 'public static final String STR_DBFUNRIGHTOBJ = "DBFunRight"
	// '' 凭证类别
	// 'public static final String STR_GNTVOUCHERTYPEOBJ = "GNTVouchertype"
	// 'public static final String STR_DBVOUCHERTYPEOBJ = "DBVouchertype"
	// 'public static final String STR_FUNVOUCHERTYPEOBJ = "funVoucherType"
	// '' 凭证类别对应科目
	// 'public static final String STR_GNTVTACCOUNTOBJ = "GNTVTAccount"
	// 'public static final String STR_DBVTACCOUNTOBJ = "DBVTAccount"
	// 'public static final String STR_FUNVTACCOUNTOBJ = "funVTAccount"
	// '' 项目
	// 'public static final String STR_GNTITEMOBJ = "GNTItem"
	// 'public static final String STR_DBITEMOBJ = "DBItem"
	// 'public static final String STR_FUNITEMOBJ = "funItem"
	// '' 部门
	// 'public static final String STR_GNTDEPARTMENTOBJ = "GNTDepartment"
	// 'public static final String STR_DBDEPARTMENTOBJ = "DBDepartment"
	// 'public static final String STR_FUNDEPARTMENTOBJ = "funDepartment"
	// '' 操作员
	// 'public static final String STR_GNTOPERATOROBJ = "GNTOperator"
	// 'public static final String STR_DBOPERATOROBJ = "DBOperator"
	// 'public static final String STR_FUNOPERATOROBJ = "funoperator"
	// '' ְԱ
	// 'public static final String STR_GNTEMPLOYEEOBJ = "GNTEmployee"
	// 'public static final String STR_DBEMPLOYEEOBJ = "DBEmployee"
	// 'public static final String STR_FUNEMPLOYEEOBJ = "funEmployee"
	// '' 仓库
	// 'public static final String STR_GNTWAREHOUSEOBJ = "GNTWarehouse"
	// 'public static final String STR_DBWAREHOUSEOBJ = "DBWarehouse"
	// 'public static final String STR_FUNWAREHOUSEOBJ = "funWarehouse"
	// '' 物料
	// 'public static final String STR_GNTMATERIALOBJ = "GNTMaterial"
	// 'public static final String STR_DBMATERIALOBJ = "DBMaterial"
	// 'public static final String STR_DBMATERIALUNITOBJ = "DBMaterialUnit"
	// '' 往来户
	// 'public static final String STR_GNTDEALEROBJ = "GNTDealer"
	// 'public static final String STR_DBDEALERADROBJ = "DBDealerAdr"
	// 'public static final String STR_DBDEALERBANKOBJ = "DBDealerBank"
	// 'public static final String STR_DBDEALEROBJ = "DBDealer"
	// '' 科目
	// 'public static final String STR_GNTACCOUNTOBJ = "GNTAccount"
	// 'public static final String STR_DBACCOUNTOBJ = "DBAccount"
	// '' 会计期
	// 'public static final String STR_GNTPERIODOBJ = "GNTPeriod"
	// 'public static final String STR_DBPERIODOBJ = "DBPeriod"
	// '' 操作员权限
	// 'public static final String STR_GNTOPTRIGHTOBJ = "GNTOptRight"
	// 'public static final String STR_DBOPTRIGHTOBJ = "DBOptRight"
	// '' 操作员，组的关系
	// 'public static final String STR_GNTOPTGRPOPTOBJ = "GNTOptGrpOpt"
	// 'public static final String STR_DBOPTGROOPTOBJ = "DBOptGroOpt"
	// '' 行业
	// 'public static final String STR_GNTTRADEOBJ = "GNTTrade"
	// 'public static final String STR_DBTRADEOBJ = "DBTrade"
	//
	// '现金流量对象
	// 'DB对象
	// public static final String STR_DBACCCSHITMOBJ = "DBAccCashItem"
	// public static final String STR_DBAVERAGETAXOBJ = "DBAverageTax"
	// public static final String STR_DBCSHITMDTLRPTOBJ = "DBCshItmDtlRpt"
	// public static final String STR_DBNOCSHIFACCOBJ = "DBNoCshIFAcc"
	// public static final String STR_DBPRCTAXAPARTOBJ = "DBPrcTaxApart"
	// public static final String STR_DBPRCTAXAPTACCOBJ = "DBPrcTaxAptAcc"
	// public static final String STR_DBRPCSHITMACCOBJ = "DBRPCshItmAcc"
	// 'GNT对象
	// public static final String STR_GNTACCCSHITMOBJ = "GNTAccCashItem"
	// public static final String STR_GNTCSHITMDTLRPTOBJ = "GNTCshItmDtlRpt"
	// public static final String STR_GNTCSHITMDTLDATAOBJ = "GNTCshItmDtlData"
	// public static final String STR_GNTNOCSHIFACCOBJ = "GNTNoCshIFAcc"
	// public static final String STR_GNTRPCSHITMACCOBJ = "GNTRPCshItmAcc"
	// 'util对象
	// public static final String STR_UTILCASHITEMOBJ = "utilCashItem"
	// public static final String STR_UTILCSHITMDTLOBJ = "utilCashItemDetail"
	// public static final String STR_UTILPRCTAXAPTOBJ = "utilPrcTaxApart"
	// '价税分离
	// public static final String STR_GNTPRCTAXAPARTOBJ = "GNTPrcTaxApart"
	//
	// ' 凭证摘要字典
	// public static final String STR_GNTVOUCHERRESUMEOBJ = "GNTVoucherResume"
	// public static final String STR_DBVOUCHERRESUMEOBJ = "DBVoucherResume"
	// public static final String STR_FUNVOUCHERRESUMEOBJ = "funVoucherResume"
	// ' 帐组
	// public static final String STR_GNTACCOUNTGROUPOBJ = "GNTAccountGroup"
	// public static final String STR_DBACCOUNTGROUPOBJ = "DBAccountGroup"
	// public static final String STR_FUNACCOUNTGROUPOBJ = "funAccountGroup"
	// ' 帐组对应科目
	// public static final String STR_GNTACCGROUPACCOBJ = "GNTAccGroupAcc"
	// public static final String STR_DBACCGROUPACCOBJ = "DBAccGroupAcc"
	// public static final String STR_FUNACCGROUPACCOBJ = "funAccGroupAcc"
	// ' 现金流量项目
	// public static final String STR_GNTCASHITEMOBJ = "GNTCashItem"
	// public static final String STR_DBCASHITEMOBJ = "DBCashItem"
	// ' 科目于额控制
	// public static final String STR_GNTACCBLNCTRLOBJ = "GNTAccBlnCtrl"
	// public static final String STR_DBACCBLNCTRLOBJ = "DBAccBlnCtrl"
	//
	// public static final String STR_GNTFORMULAOBJ = "GNTFormula"
	// public static final String STR_GNTFORMULAMIDOBJ = "GNTFormulaMid"
	// public static final String STR_GNTFORMULANATIVEOBJ = "GNTFormulaNative"
	//
	// ' 银行模块
	//
	// public static final String STR_GNTBCAACCOUNTOBJ = "GNTBCAAccount"
	// public static final String STR_GNTBCASETUPOBJ = "GNTBCASetup"
	// public static final String STR_GNTBCBALANCEOBJ = "GNTBCBalance"
	// public static final String STR_GNTBCBILLOBJ = "GNTBCBill"
	// public static final String STR_GNTBCBILLFORMATOBJ = "GNTBCBillFormat"
	// public static final String STR_GNTBCCANCELOBJ = "GNTBCCancel"
	//
	// public static final String STR_DBBCBALANCEOBJ = "DBBCBalance"
	// public static final String STR_DBBCASETUPOBJ = "DBBCASetup"
	// public static final String STR_DBBCAACCOUNTOBJ = "DBBCAAccount"
	// public static final String STR_DBBCBILLOBJ = "DBBCBill"
	// public static final String STR_DBBCCANCELOBJ = "DBBCCancel"
	// public static final String STR_DBBCBILLFORMATOBJ = "DBBCBillFormat"
	//
	//
	// ' ProgID Define
	// ' 系统对象
	//
	// ' 字典类
	//
	//
	//
	// ' 凭证摘要字典
	// public static final String STR_GNTVOUCHERRESUMEPROGID = "BKMGLDictOther"
	// & "." & STR_GNTVOUCHERRESUMEOBJ
	// public static final String STR_DBVOUCHERRESUMEPROGID = "BKMGLDictOther" &
	// "." & STR_DBVOUCHERRESUMEOBJ
	// public static final String STR_FUNVOUCHERRESUMEPROGID = "BKMGLDictOther"
	// & "." & STR_FUNVOUCHERRESUMEOBJ
	// ' 帐组
	// public static final String STR_GNTACCOUNTGROUPPROGID = STR_GLPROJECT &
	// "." & STR_GNTACCOUNTGROUPOBJ
	// public static final String STR_DBACCOUNTGROUPPROGID = STR_GLPROJECT & "."
	// & STR_DBACCOUNTGROUPOBJ
	// public static final String STR_FUNACCOUNTGROUPPROGID = STR_GLPROJECT &
	// "." & STR_FUNACCOUNTGROUPOBJ
	// ' 现金流量项目
	// public static final String STR_GNTCASHITEMPROGID = STR_CASHITEMOBJECT &
	// "." & STR_GNTCASHITEMOBJ
	// public static final String STR_DBCASHITEMPROGID = STR_CASHITEMOBJECT &
	// "." & STR_DBCASHITEMOBJ
	// public static final String STR_UTILCASHITEMPROGID = STR_CASHITEMOBJECT &
	// "." & STR_UTILCASHITEMOBJ
	// ' 现金流量维护
	// public static final String STR_GNTACCCSHITMPROGID = STR_CASHITEMOBJECT &
	// "." & STR_GNTACCCSHITMOBJ
	// public static final String STR_GNTNOCSHIFACCPROGID = STR_CASHITEMOBJECT &
	// "." & STR_GNTNOCSHIFACCOBJ
	// public static final String STR_GNTRPCSHITMACCPROGID = STR_CASHITEMOBJECT
	// & "." & STR_GNTRPCSHITMACCOBJ
	// public static final String STR_UTILCSHITMDTLPROGID = STR_CASHITEMOBJECT &
	// "." & STR_UTILCSHITMDTLOBJ
	// ' 现金流量调整
	// public static final String STR_GNTCSHITMDTLDATAPROGID =
	// STR_CASHITEMOBJECT & "." & STR_GNTCSHITMDTLDATAOBJ
	// public static final String STR_GNTCSHITMDTLRPTPROGID = STR_CASHITEMOBJECT
	// & "." & STR_GNTCSHITMDTLRPTOBJ
	// public static final String STR_DBCSHITMDTLRPTPROGID = STR_CASHITEMOBJECT
	// & "." & STR_DBCSHITMDTLRPTOBJ
	// ' 价税分离
	// public static final String STR_GNTPRCTAXAPTPROGID = STR_CASHITEMOBJECT &
	// "." & STR_GNTPRCTAXAPARTOBJ
	// public static final String STR_UTILPRCTAXAPTPROGID = STR_CASHITEMOBJECT &
	// "." & STR_UTILPRCTAXAPTOBJ
	//
	// ' 银行
	// public static final String STR_GNTBCAACCOUNTPROGID = STR_BANKOBJECT & "."
	// & STR_GNTBCAACCOUNTOBJ
	// public static final String STR_GNTBCASETUPPROGID = STR_BANKOBJECT & "." &
	// STR_GNTBCASETUPOBJ
	// public static final String STR_GNTBCBALANCEPROGID = STR_BANKOBJECT & "."
	// & STR_GNTBCBALANCEOBJ
	// public static final String STR_GNTBCBILLPROGID = STR_BANKOBJECT & "." &
	// STR_GNTBCBILLOBJ
	// public static final String STR_GNTBCBILLFORMATPROGID = STR_BANKOBJECT &
	// "." & STR_GNTBCBILLFORMATOBJ
	// public static final String STR_GNTBCCANCELPROGID = STR_BANKOBJECT & "." &
	// STR_GNTBCCANCELOBJ
	//
	// public static final String STR_DBBCBALANCEPROGID = STR_BANKOBJECT & "." &
	// STR_DBBCBALANCEOBJ
	// public static final String STR_DBBCASETUPPROGID = STR_BANKOBJECT & "." &
	// STR_DBBCASETUPOBJ
	// public static final String STR_DBBCAACCOUNTPROGID = STR_BANKOBJECT & "."
	// & STR_DBBCAACCOUNTOBJ
	// public static final String STR_DBBCBILLPROGID = STR_BANKOBJECT & "." &
	// STR_DBBCBILLOBJ
	// public static final String STR_DBBCCANCELPROGID = STR_BANKOBJECT & "." &
	// STR_DBBCCANCELOBJ
	// public static final String STR_DBBCBILLFORMATPROGID = STR_BANKOBJECT &
	// "." & STR_DBBCBILLFORMATOBJ
	//
	// '公式中间层接口
	// public static final String STR_GNTFORMULAMIDPROGID = STR_FORMULAPROJECT &
	// "." & STR_GNTFORMULAMIDOBJ
	//
	//
	// '' 帐组对应科目
	// 'public static final String STR_GNTACCGROUPACCOBJ = "GNTAccGroupAcc"
	// 'public static final String STR_DBACCGROUPACCOBJ = "DBAccGroupAcc"
	// 'public static final String STR_FUNACCGROUPACCOBJ = "funAccGroupAcc"
	// '
	// 'public static final String STR_GNTFORMULAOBJ = "GNTFormula"
	// 'public static final String STR_GNTFORMULAMIDOBJ = "GNTFormulaMid"
	// 'public static final String STR_GNTFORMULANATIVEOBJ = "GNTFormulaNative"
	// 'public static final String STR_DBBCBALANCEOBJ = "DBBCBalance"
	// 'public static final String STR_DBBCASETUPOBJ = "DBBCASetup"
	// 'public static final String STR_DBBCAACCOUNTOBJ = "DBBCAAccount"
	// 'public static final String STR_DBBCBILLOBJ = "DBBCBill"
	// 'public static final String STR_DBBCCANCELOBJ = "DBBCCancel"
	// 'public static final String STR_GNTBCAACCOUNTOBJ = "GNTBCAAccount"
	// 'public static final String STR_GNTBCBALANCEOBJ = "gntBCBalance"
	// 'public static final String STR_GNTBCBILLOBJ = "GNTBCBill"
	// 'public static final String STR_GNTBCASETUPOBJ = "GNTBCASetup"
	// 'public static final String STR_GNTBCCANCELOBJ = "gntBCCancel"
	// 'public static final String STR_UTILCASHITEMOBJ = "utilCashItem"
	// 'public static final String STR_DBRPCSHITMACCOBJ = "DBRPCshItmAcc"
	// 'public static final String STR_DBNOCSHIFACCOBJ = "DBNoCshIFAcc"
	// 'public static final String STR_GNTACCCASHITEMOBJ = "GNTAccCashItem"
	// 'public static final String STR_GNTNOCSHIFACCOBJ = "GNTNoCshIFAcc"
	// 'public static final String STR_GNTRPCSHITMACCOBJ = "GNTRPCshItmAcc"
	// '
	//
	// public static final String STR_GNTLOGPROGID = "BKMLOG" & "." & "GNTLOG"
	// public static final String STR_VCHMODELPROGID = "BKMVCHMODULE" & "." &
	// "GNTVCHMODEL"
	//
	// '非法科目组字典
	// public static final String STR_GNTILLGLGRPACCOBJ = "GNTIllglGrpAcc"
	// public static final String STR_GNTILLGLGRPACCPROGID =
	// STR_GLDICTOTHERPROJECT & "." & STR_GNTILLGLGRPACCOBJ
	//
	// '下面这个常量是用于数据解锁用的,加锁时我用的是ObjectName,于是在这里也要定义一下ObjectName
	// public static final String STR_GNTCSHITMDTLRPT_OBJECTNAME =
	// "CashItemDetailReport"
	// 'Private Const STR_GNTOBJECTNAME = "CashItemDetailReport" '这是中间层的定义
	//
	// public static final String STR_GNTCACONTRAPROGID = "BKMCAControl" & "." &
	// "GNTCAContrl"
	// public static final String STR_CCACONTROLPROGID =
	// "BKMCAControl.CCAControl"
	// public static final String STR_GNTCACHECKPROGID = "BKMCAControl" & "." &
	// "GNTCACheck"
	// '科目销帐分析点设置
	// public static final String STR_GNTACCCAPOINT =
	// "BKMCAControl.GNTAccCAPoint"
	//
	// '新的凭证中间层对象
	// public static final String STR_CMIDVOUCHERPROGID =
	// "BKMVoucher.CMidVoucher"
	// public static final String STR_CMIDVOUCHERSAVEPROGID =
	// "BKMVoucher.CMidVoucherSave"
	// public static final String STR_CMIDKEEPVOUCHERPROGID =
	// "BKMVoucher.CMidKeepVoucher"
	//
	// '///////////////////////////////////////////////////////////
	// ' 以下是各个使用 cell 和 spread 的模块的打印模板文件名
	//
	// ' 帐龄分析
	// public static final String STR_PRINT_ACCOUNT_AGE_ANALYSIS =
	// "uprint_account_age_analysis.xml"
	// ' 现金流量附注项目调整
	// public static final String STR_PRINT_ANNCASHITEM_ADJUST =
	// "uprint_anncashitem_adjust.xml"
	// ' 现金流量正文项目调整
	// public static final String STR_PRINT_STRCASHITEM_ADJUST =
	// "uprint_strcashitem_adjust.xml"
	// '已删除凭证处理
	// public static final String STR_PRINT_DEL_VOUCHER =
	// "uprint_del_voucher.xml"
	// '凭证自动冲销处理
	// public static final String STR_PRINT_AUTORVS_VOUCHER =
	// "uprint_autorvs_voucher.xml"
	// '日志
	// public static final String STR_PRINT_LOG = "uprint_log.xml"
	// ' 常用凭证
	// public static final String STR_PRINT_VCHMODEL = "uprint_vchmodel.xml"
	// ' 余额初始化查询
	// public static final String STR_PRINT_INITSEARCH = "uprint_initsearch.xml"
	//
	// '///////////////////////////////////////////////////////////
	//
	// ' 为实现在SCM里调用凭证模板的功能而加 zly 2004-4-20
	// public static final String STR_SCM = "SCM"

	public static final String RFC_PARAM_PREFIX = "rfc_p__";

	public static final String STR_PWDTYPE = "pwdtype";

	public static final String WEB_BILLDOCARRAY = "WEB_BILLDOCARRAY";

	public static final String WEB_BILLDOC = "WEB_BILLDOC";

	public static final String WEB_PROMPT = "WEB_PROMPT";

	// public static final String WEB_JS = "WEB_JS";

	public static final String WEB_JSLIST = "WEB_JSLIST";

	public static final String WEB_EXP = "WEB_EXP";

	public static final String WEB_NEWTAB = "WEB_NEWTAB";

	public static final String WEB_FAVORITE = "WEB_FAVORITE";
	/**
	 * 开启调试
	 */
	public static boolean ISDEBUG = false;

	/**
	 * 开启web容器session
	 */
	public static boolean USESESSION = true;

	/**
	 * 开启监视器
	 */
	public static boolean SHOWMONITOR = false;

	/**
	 * 开启多预览窗口
	 */
	public static boolean SHOWMULTI = false;

	/**
	 * web applet 中间件开关
	 */
	public static boolean STARTWITHAPP = true;

	/**
	 * 是否是新配置工具
	 */
	public static boolean isNewDesigner = false;

	/**
	 * web 安全请求开关
	 */
	public static boolean SAFECALL = true;
	// /**
	// * web 长连接开关
	// */
	// public static boolean USELONGCONN = false;

	/**
	 * App.Swing绘制的窗口不可拖动大小
	 */
	public static final String SWING_WINDOW = "|dialog|audit|dicfilter|dicselect|dictreebrowse|dictreefind|printmanage4business|printmanage|";

	/**
	 * Web版多登陆配置,用于合法请求判断
	 */
	public static String LOGIN_CONFIG = "|login|";
}
