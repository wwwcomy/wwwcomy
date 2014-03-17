create or replace view V_DIC_SDBPROGOODS as 
	(
		select t_1.PRODUCT_ID,t_1.GOODS_ID,t_1.STORE as STORE1,t_1.BN as WMCODE,t_1.COST as IN_PRICE,t_1.PRICE as OUT_PRICE,t_1.MARKETABLE,
		t_2.NAME,t_2.P_21,t_2.P_26 as PACKING_NUM,t_2.P_27,t_2.WEIGHT as WEIGHTNUM,t_2.P_22 as SPEC,t_2.P_2 as BHZQ,t_2.BIG_PIC,t_2.INTRO as MEMO,t_2.STORE as STORE2,t_2.P_1 as MODEL,t_2.TYPE_ID,t_2.P_24,
		t_3.CAT_NAME as TYPE_0,t_1.PDT_DESC as COLOR
		
		from SDB_PRODUCTS t_1 left join SDB_GOODS t_2 on t_1.GOODS_ID=t_2.GOODS_ID left join SDB_GOODS_CAT t_3 on t_2.CAT_ID=t_3.CAT_ID
		where t_2.goods_type='normal' and t_2.disabled='false'
	);
	
create or replace view V_WM_SDBPRODUCT as 
	(
		select t_1.ID_INT as ID_INT,t_1.SDB_PRODUCT_ID,t_1.HYVERSION,t_1.ID,t_1.NAME,t_1.CODE,t_1.SUPPLIER,t_1.STATUS,t_1.WHOWNER,t_1.C1,t_1.C2,t_1.C3,t_2.COLOR,t_1.ZDKC,t_1.CAPACITYNUM,t_1.EDPMODEL,t_1.P_27 as P_27_LAST,
		t_2.P_27,t_2.P_21,t_2.PRODUCT_ID,t_2.NAME as SDB_NAME,t_2.WMCODE,t_2.PACKING_NUM,t_2.WEIGHTNUM, t_2.P_24,t_2.SPEC,t_2.BHZQ,t_2.BIG_PIC,t_2.IN_PRICE,t_2.OUT_PRICE,t_2.TYPE_0,t_2.MEMO,t_2.MARKETABLE,t_2.MODEL,t_2.TYPE_ID,
		t_1.SUPP_CODE,t_1.LATEST_BATCH,
		t_1.CONTRA_INPRICE
		
		from T_WM_PRO t_1 left join T_DIC_SDBPROGOODS t_2 on t_1.SDB_PRODUCT_ID=t_2.PRODUCT_ID
		and t_2.MARKETABLE='true'
	);

create or replace view V_WM_OUTPART_INIT as 
		(
			select * from T_WM_OUTPART where (BILL_STATUS='init' or BILL_STATUS is null)
		);
create or replace view V_WM_OUTPART_HISTORY as 
		(
			select * from T_WM_OUTPART where BILL_STATUS='audit' 
		);
create or replace view V_SDB_ORDERS_INIT as 
		(
			select * from SDB_ORDERS where ((STATUS='active' and PAY_STATUS='1' and DISABLED='false') or (STATUS='finish' and DISABLED='false'))
				and ORDER_ID not in (select ORDER_ID from V_WM_OUTPART_HISTORY)
				and ORDER_ID not like '2011%'
		);
create or replace view V_WM_OUT as 
		(
			select t_1.ORDER_ID as ID,t_1.ORDER_ID,t_2.ORDER_ID_INT,t_1.MEMBER_ID,t_1.SHIP_NAME,t_1.SHIP_ADDR,t_1.SHIP_TEL,t_1.SHIP_MOBILE,t_1.FINAL_AMOUNT ,t_1.PMT_AMOUNT,t_1.COST_FREIGHT,t_1.SHIPPING,
			t_2.ORDER_MEMO,t_2.AUDIT_TIME,t_2.OUT_TYPE,t_2.AUDIT_USER_ID,t_2.BILL_STATUS,t_2.BILL_STATUS1,t_2.WHOWNER,t_2.LOGISTICS_NAME,t_2.LOGISTICS_CODE,t_2.LOGISTICS_MEMO,t_2.HYVERSION
			from SDB_ORDERS t_1 
			left join T_WM_OUTPART t_2 
			on t_1.ORDER_ID=t_2.ORDER_ID_INT
			where (t_2.BILL_STATUS='init' or t_2.BILL_STATUS is null)
			and ((t_1.STATUS='active' and t_1.PAY_STATUS='1' and t_1.DISABLED='false') or (t_1.STATUS='finish' and t_1.DISABLED='false'))
			and t_1.ORDER_ID not like '2011%'
			and (t_2.ORDER_MEMO not like '作废%' or t_2.ORDER_MEMO is null)
		)
		union all
		(
			select t_1.ID,t_1.ORDER_ID,0 as ORDER_ID_INT,t_1.MEMBER_ID,t_1.SHIP_NAME,t_1.SHIP_ADDR,t_1.SHIP_TEL,t_1.SHIP_MOBILE,t_1.FINAL_AMOUNT ,t_1.PMT_AMOUNT,t_1.COST_FREIGHT,t_1.SHIPPING,t_1.ORDER_MEMO,
			t_1.AUDIT_TIME,t_1.OUT_TYPE,t_1.AUDIT_USER_ID,t_1.BILL_STATUS,t_1.BILL_STATUS1,t_1.WHOWNER,t_1.LOGISTICS_NAME,t_1.LOGISTICS_CODE,t_1.LOGISTICS_MEMO,t_1.HYVERSION
			from V_WM_OUTPART_INIT t_1 
			where t_1.ORDER_ID like 'CK%' and (t_1.ORDER_MEMO not like '作废%' or t_1.ORDER_MEMO is null)
		);
		
create or replace view V_SDB_ORDERS_HISTORY as 
		(
			select t_1.* from SDB_ORDERS t_1 inner join V_WM_OUTPART_HISTORY t_2
			on t_1.ORDER_ID=t_2.ORDER_ID
			where ((t_1.STATUS='active' and t_1.PAY_STATUS='1' and t_1.DISABLED='false') or (t_1.STATUS='finish' and t_1.DISABLED='false'))
		);
		
create or replace view V_WM_OUT_HISTORY as 
		(
			select t_1.ORDER_ID as ID,t_1.ORDER_ID,t_1.MEMBER_ID,t_1.SHIP_NAME,t_1.SHIP_ADDR,t_1.SHIP_TEL,t_1.SHIP_MOBILE,t_1.FINAL_AMOUNT ,t_1.PMT_AMOUNT,t_1.COST_FREIGHT,t_1.SHIPPING,
			t_2.ORDER_MEMO,t_2.AUDIT_TIME,t_2.OUT_TYPE,t_2.AUDIT_USER_ID,t_2.BILL_STATUS,t_2.WHOWNER,t_2.LOGISTICS_NAME,t_2.LOGISTICS_CODE,t_2.LOGISTICS_MEMO,t_2.HYVERSION
			from T_WM_OUTPART t_2
			left JOIN SDB_ORDERS t_1
			on t_1.ORDER_ID=t_2.ORDER_ID where t_2.BILL_STATUS='audit' 
		)
		union all
		(
			select t_1.ID,t_1.ORDER_ID,t_1.MEMBER_ID,t_1.SHIP_NAME,t_1.SHIP_ADDR,t_1.SHIP_TEL,t_1.SHIP_MOBILE,t_1.FINAL_AMOUNT ,t_1.PMT_AMOUNT,t_1.COST_FREIGHT,t_1.SHIPPING,t_1.ORDER_MEMO,
			t_1.AUDIT_TIME,t_1.OUT_TYPE,t_1.AUDIT_USER_ID,t_1.BILL_STATUS,t_1.WHOWNER,t_1.LOGISTICS_NAME,t_1.LOGISTICS_CODE,t_1.LOGISTICS_MEMO,t_1.HYVERSION
			from V_WM_OUTPART_HISTORY t_1 
			where t_1.ORDER_ID like 'CK%'
		);
		
create or replace view V_WM_STOCK1 as 
	(
		select 1 as BT,concat("RK",t_1.ID) as ID,(CASE WHEN t_1.headID=t_4.TRANS_ID THEN t_1.HEADID ELSE t_2.IN_NO END) as HEADID,
		t_1.PRODUCTID,t_1.PRO_WMCODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,t_1.PRO_NAME,
		t_3.SUPP_CODE,
		t_3.IN_PRICE as PRO_IN_PRICE,t_1.PRO_COUNT,
		t_1.PRO_COUNT*t_1.PRO_IN_PRICE as ALL_IN_COST, 0 as PRO_OUT_PRICE,0 as ALL_OUT_COST,
		t_2.BILL_STATUS,t_1.PRO_WH AS WAREWHERE,t_3.WHOWNER,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN  t_4.WAREHOUSE ELSE t_2.WAREHOUSE END ) as WAREHOUSE,
		t_3.SUPPLIER,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN  t_4.AUDIT_TIME ELSE t_2.AUDIT_TIME END )as WM_DATE,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN  1 ELSE -1 END) as YK ,
		t_1.BATCH,
		1 as STOCKCHANGE,0 as FINANCECHANGE
		from (T_WM_INDETAIL t_1 left join T_WM_SDBIN t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		left join T_WM_STOCKTRANS t_4 on t_1.headID=t_4.TRANS_ID
		where t_2.BILL_STATUS='audit' or t_1.headID=t_4.TRANS_ID
	)
	union all
	(
		select (CASE WHEN t_2.OUT_TYPE='consign' THEN -1 WHEN t_2.OUT_TYPE ='reject' THEN 0
		 WHEN t_2.OUT_TYPE ='loss' THEN -2
		 ELSE -1 END) as BT,concat("CK",t_1.ID) as ID,t_1.HEADID,t_1.PRODUCTID,
		t_1.PRO_WMCODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,t_1.PRO_NAME,
		t_3.SUPP_CODE,t_1.PRO_CALC_PRICE as PRO_IN_PRICE,t_1.PRO_COUNT,
		0 as ALL_IN_COST,
		t_1.PRO_OUT_PRICE,
		(CASE WHEN t_2.OUT_TYPE='consign' THEN t_1.PRO_COUNT*t_1.PRO_OUT_PRICE WHEN t_2.OUT_TYPE ='reject' THEN 0 ELSE  t_1.PRO_COUNT*t_1.PRO_OUT_PRICE END) as ALL_OUT_COST,
		t_1.BILL_STATUS,t_1.PRO_WH AS WAREWHERE,t_3.WHOWNER,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN  t_4.WAREHOUSE ELSE t_2.WAREHOUSE END ) as WAREHOUSE,
		t_3.SUPPLIER,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN t_4.AUDIT_TIME ELSE t_2.AUDIT_TIME END )as WM_DATE,
		(CASE WHEN t_1.headID=t_4.TRANS_ID THEN 1 ELSE -1 END) as YK ,
		t_1.BATCH,
		-1 as STOCKCHANGE,
		(CASE WHEN t_2.OUT_TYPE='consign' THEN 1 WHEN t_2.OUT_TYPE ='reject' THEN 0 ELSE 0 END) as FINANCECHANGE
		
		from (T_WM_OUTDETAIL t_1 left join T_WM_OUTPART t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		left join T_WM_STOCKTRANS t_4 on t_1.headID=t_4.TRANS_ID
		where t_1.BILL_STATUS='audit' and t_2.BILL_STATUS<>'reject' or t_1.headID=t_4.TRANS_ID
	)
	union all
	(
		select 2 as BT,concat("TH",t_1.ID) as ID,t_2.TH_NO,t_1.PRODUCTID,
		t_3.WMCODE as PRO_WMCODE,t_3.CAPACITYNUM as PRO_CAPACITYNUM,t_3.WEIGHTNUM as PRO_WEIGHTNUM,t_3.NAME as PRO_NAME,
		t_3.SUPP_CODE,t_3.IN_PRICE as PRO_IN_PRICE,t_1.PRO_COUNT,
		0 as ALL_IN_COST,
		t_1.PRO_OUT_PRICE,
		t_1.PRO_COUNT*t_1.PRO_OUT_PRICE*-1 as ALL_OUT_COST,
		t_1.BILL_STATUS,t_1.PRO_WH AS WAREWHERE,t_3.WHOWNER,t_2.WAREHOUSE,t_3.SUPPLIER,
		t_2.AUDIT_TIME as WM_DATE,
		-1 as YK ,
		t_1.BATCH,
		1 as STOCKCHANGE,
		-1 as FINANCECHANGE
		
		from (T_WM_SDBRETURNDETAIL t_1 left join T_WM_SDBRETURN t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		where t_2.BILL_STATUS='audit'
	)
	union all
	(
		select -95 as BT,concat("THPRE",t_1.ID) as ID,t_2.TH_NO,t_1.PRODUCTID,
		t_3.WMCODE as PRO_WMCODE,t_3.CAPACITYNUM as PRO_CAPACITYNUM,t_3.WEIGHTNUM as PRO_WEIGHTNUM,t_3.NAME as PRO_NAME,
		t_3.SUPP_CODE,t_3.IN_PRICE as PRO_IN_PRICE,t_1.RETURN_RCOUNT,
		t_1.SUP_ALL_RETURNRPRICE*-1 as ALL_IN_COST,
		t_1.EDP_RETURN_PRICE as PRO_OUT_PRICE,
		t_1.EDP_ALL_RETURNRPRICE*-1 as ALL_OUT_COST,
		t_1.BILL_STATUS,-1 AS WAREWHERE,t_3.WHOWNER,t_2.WAREHOUSE,t_3.SUPPLIER,
		t_2.AUDIT_TIME as WM_DATE,
		-1 as YK ,
		-99 as BATCH,
		1 as STOCKCHANGE,
		-1 as FINANCECHANGE
		
		from (T_WM_SDBRETURNPRE t_1 left join T_WM_SDBRETURN t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		where t_2.BILL_STATUS='audit'
	);
	
create or replace view V_WM_STOCK2 as 
	(
		select 1 as BT,t_1.ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,t_1.PRO_NAME,
		t_1.PRO_IN_PRICE,sum(t_1.PRO_COUNT) as PRO_COUNT,
		sum(t_1.PRO_COUNT*t_1.PRO_IN_PRICE) as ALL_IN_COST, 0 as PRO_OUT_PRICE,0 as ALL_OUT_COST,
		t_2.BILL_STATUS,t_3.WHOWNER,t_3.WAREHOUSE,t_3.SUPPLIER
		from (T_WM_INDETAIL t_1 left join T_WM_SDBIN t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		where t_2.BILL_STATUS='audit'
		group by PRODUCTID
	)
	union
	(
		select -1 as BT,t_1.ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,t_1.PRO_NAME,
		t_1.PRO_IN_PRICE,sum(t_1.PRO_COUNT) as PRO_COUNT,
		t_1.PRO_COUNT*t_1.PRO_IN_PRICE as ALL_IN_COST,
		t_1.PRO_OUT_PRICE,
		sum(t_1.PRO_COUNT*t_1.PRO_OUT_PRICE) as ALL_OUT_COST,
		t_1.BILL_STATUS,t_3.WHOWNER,t_3.WAREHOUSE,t_3.SUPPLIER
		from (T_WM_OUTDETAIL t_1 left join V_WM_OUT t_2 on t_1.HEADID=t_2.ID) left join T_WM_SDBPRODUCT t_3 on t_3.ID=t_1.PRODUCTID
		where t_1.BILL_STATUS='audit' and t_2.BILL_STATUS<>'reject'
		group by PRODUCTID
	);
	
create or replace view V_WM_SDBITEMCOUNT as 
(
	select `t2`.`ID` AS `productid`,`t`.`product_id` AS `sdb_product_id`,`t`.`freez` AS `nums`,sum((case when (`t4`.`member_id` = 51) then `t3`.`nums` else 0 end)) AS `XD_nums`,sum((case when (`t4`.`pay_status` = _utf8'1') then `t3`.`nums` else 0 end)) AS `other_nums` from (((`sdb_products` `t` join `t_wm_pro` `t2` on((`t`.`product_id` = `t2`.`SDB_PRODUCT_ID`))) join `sdb_order_items` `t3` on((`t2`.`SDB_PRODUCT_ID` = `t3`.`product_id`))) join `sdb_orders` `t4` on((`t4`.`order_id` = `t3`.`order_id`))) where ((`t4`.`status` = _utf8'active') and (`t4`.`disabled` = _utf8'false') and ((`t4`.`pay_status` = _utf8'0') or ((`t4`.`pay_status` = _utf8'1') and (`t4`.`ship_status` = _utf8'0')))) group by `t2`.`ID`,`t`.`product_id`,`t`.`freez`
);
create or replace view V_WM_IN as
(
	select `t_3`.`ID` AS `PRODUCTID`,`t_3`.`CODE` AS `code`,`t_3`.`SUPP_CODE` AS `supp_code`,(case when isnull(sum(`t_1`.`PRO_COUNT`)) then 0 else sum(`t_1`.`PRO_COUNT`) end) AS `PRO_IN_SUM` from (`t_wm_pro` `t_3` left join (`t_wm_sdbinpre` `t_1` join `t_wm_sdbin` `t_2` on(((`t_1`.`HEADID` = `t_2`.`ID`) and (`t_2`.`BILL_STATUS` = _utf8'init')))) on((`t_3`.`ID` = `t_1`.`PRODUCTID`))) group by `t_3`.`ID`
);
create or replace view V_WM_STOCK3 as 
	(
		select concat(t_1.ID,t_1.STOCKCHANGE) as ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE, t_1.PRO_NAME,t_3.supp_code as supp_code,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,
		sum(t_1.PRO_COUNT*t_1.STOCKCHANGE) as PRO_COUNT, t_2.NUMS as ORDER_COUNT,t_2.XD_nums AS XD_ORDER_COUNT,t_2.other_nums AS OTHER_ORDER_COUNT,
		t_3.PRO_IN_SUM as PRO_IN_SUM,
        t_1.WAREHOUSE, t_1.SUPPLIER
		from V_WM_STOCK1 t_1 
		left join V_WM_SDBITEMCOUNT t_2 on t_1.PRODUCTID=t_2.PRODUCTID
		left join V_WM_IN t_3 on t_1.PRODUCTID=t_3.PRODUCTID
		where t_1.BT>-95
		group by t_1.PRODUCTID,t_1.WAREHOUSE
	);
	
create or replace view V_WM_STOCKRETURN as 
	(
		select t_1.ID as ID,t_1.HEADID,t_1.OUT_CODE,t_1.ORDER_ID,
		t_1.PRODUCTID,t_1.WMCODE,t_1.PRO_NAME,t_1.SUPP_CODE,
		t_3.AUDIT_TIME as OUT_DATE,
		t_2.AUDIT_TIME as WM_DATE,
		sum(t_1.RETURN_RCOUNT) as RETURN_RCOUNT,
		sum(t_1.RETURN_COUNT) as RETURN_COUNT,
		SUM(OUT_COUNT) as OUT_COUNT,
		t_1.OUT_PRICE,
		t_1.IN_PRICE,
		DATE_FORMAT(date_add(date_sub(t_2.AUDIT_TIME,INTERVAL 20 day),INTERVAL 1 month),'%Y%m') as WM_MONTH,
		sum(t_1.OUT_ALL_PRICE) as OUT_ALL_PRICE,
		
		t_1.EDP_RETURN_PRICE,
		t_1.SUP_RETURN_PRICE,
		sum(t_1.EDP_OTHERPRICE) as EDP_OTHERPRICE,
		sum(t_1.SUP_OTHERPRICE) as SUP_OTHERPRICE,
		sum(t_1.EDP_ALL_RETURNRPRICE) as EDP_ALL_RETURNRPRICE,
		sum(t_1.SUP_ALL_RETURNRPRICE) as SUP_ALL_RETURNRPRICE,
		t_4.SUPPLIER,
		t_2.WAREHOUSE,
		concat(t_4.SUPPLIER,'_',t_1.PRODUCTID,'_',DATE_FORMAT(date_add(date_sub(t_2.AUDIT_TIME,INTERVAL 20 day),INTERVAL 1 month),'%Y%m')) as SUPP_MONTH,
		concat(t_4.SUPPLIER,'_',t_1.PRODUCTID,'_',DATE_FORMAT(date_add(date_sub(t_2.AUDIT_TIME,INTERVAL 20 day),INTERVAL 1 month),'%Y%m')) as SUPP_PRO_MONTH
		from T_WM_SDBRETURNPRE t_1
		left join T_WM_SDBRETURN t_2 on t_1.HEADID=t_2.ID 
		left join T_WM_OUTPART t_3 on t_1.ORDER_ID=t_3.ID
		left join T_WM_PRO t_4 on t_1.PRODUCTID=t_4.ID
		where t_2.BILL_STATUS='audit'
		group by t_1.PRODUCTID,WM_MONTH,SUPP_MONTH,SUPPLIER
	);
	
create or replace view V_WM_STOCKWHERE as 
	(
		select concat(t_1.ID,t_1.BT) as ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE, t_1.PRO_NAME,
		t_1.SUPP_CODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,
		sum(t_1.PRO_COUNT*t_1.STOCKCHANGE) as PRO_COUNT, 0 as OUT_COUNT,
        t_1.WAREHOUSE, t_1.WAREWHERE, t_1.SUPPLIER, t_1.PRO_IN_PRICE,t_1.PRO_OUT_PRICE
		from V_WM_STOCK1 t_1
		where t_1.BT>-95
		group by t_1.PRODUCTID,t_1.WAREHOUSE,t_1.WAREWHERE
	);
	
create or replace view V_WM_STOCKWHEREBATCH as 
	(
		select concat(t_1.ID,t_1.BT) as ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE, t_1.PRO_NAME,
		t_1.BATCH,t_1.SUPP_CODE,t_1.PRO_CAPACITYNUM,t_1.PRO_WEIGHTNUM,
		sum(t_1.PRO_COUNT*t_1.STOCKCHANGE) as PRO_COUNT, 0 as OUT_COUNT,
        t_1.WAREHOUSE, t_1.WAREWHERE, t_1.SUPPLIER, t_1.PRO_IN_PRICE,t_1.PRO_OUT_PRICE
		from V_WM_STOCK1 t_1
		where t_1.BT>-95
		group by t_1.PRODUCTID,t_1.WAREHOUSE,t_1.WAREWHERE,t_1.BATCH order by PRO_COUNT desc
	);

create or replace view V_WM_STORESALETMP as 
	(
		select concat(t_1.ID,t_1.BT) as ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE,t_1.PRO_WMCODE as WMCODE,
		sum(t_1.PRO_COUNT*t_1.STOCKCHANGE) as PRO_COUNT,
		sum(t_1.PRO_COUNT*t_1.STOCKCHANGE*t_1.PRO_IN_PRICE) as PRO_VALUE,
		DATE_FORMAT(date_add(date_sub(t_1.WM_DATE,INTERVAL 20 day),INTERVAL 1 month),'%Y%m') as WM_MONTH,
		SUM( CASE WHEN BT = -1 THEN t_1.PRO_COUNT WHEN BT = -2 THEN t_1.PRO_COUNT  WHEN BT=-95 THEN t_1.PRO_COUNT*-1 ELSE 0 END) as SALE_COUNT,
		SUM( CASE WHEN BT = -1 THEN t_1.PRO_COUNT*t_1.PRO_IN_PRICE WHEN BT = -2 THEN t_1.PRO_COUNT*t_1.PRO_IN_PRICE WHEN BT=-95 THEN t_1.ALL_IN_COST ELSE 0 END) as SALE_VALUE,
		
		SUM( CASE WHEN BT = -1 THEN t_1.ALL_OUT_COST WHEN BT=-95 THEN t_1.ALL_OUT_COST ELSE 0 END) as EDP_SALE_VALUE, 
		(SUM( CASE WHEN BT = -1 THEN t_1.ALL_OUT_COST WHEN BT=-95 THEN t_1.ALL_OUT_COST ELSE 0 END)
		-SUM( CASE WHEN BT = -1 THEN t_1.PRO_COUNT*t_1.PRO_IN_PRICE WHEN BT = -2 THEN t_1.PRO_COUNT*t_1.PRO_IN_PRICE WHEN BT=-95 THEN t_1.ALL_IN_COST ELSE 0 END)) as PROFIT_STATISTICS,
		
        t_1.WAREHOUSE, '产品详细信息' as PRO_MEMO, t_1.SUPPLIER, t_1.SUPP_CODE,
        concat(t_1.SUPPLIER,'_',DATE_FORMAT(date_add(date_sub(t_1.WM_DATE,INTERVAL 20 day),INTERVAL 1 month),'%Y%m')) as SUPP_MONTH,
        concat(t_1.SUPPLIER,'_',t_1.PRODUCTID,'_',DATE_FORMAT(date_add(date_sub(t_1.WM_DATE,INTERVAL 20 day),INTERVAL 1 month),'%Y%m')) as SUPP_PRO_MONTH
		from V_WM_STOCK1 t_1 
		where YK=-1
		and t_1.BT<2 and t_1.BT>=-95
		group by t_1.PRODUCTID,t_1.WAREHOUSE,WM_MONTH
	);
	
create or replace view V_WM_STORESALE as 
	(
		select t_1.ID,t_1.HEADID,t_1.PRODUCTID,t_1.PRO_WMCODE,t_1.WMCODE,
		t_1.PRO_COUNT,
		t_1.PRO_VALUE,
		t_1.WM_MONTH,
		t_1.SALE_COUNT,
		t_1.SALE_VALUE,
		
		t_1.EDP_SALE_VALUE, 
		t_1.PROFIT_STATISTICS,
        t_1.WAREHOUSE, t_1.PRO_MEMO, t_1.SUPPLIER, t_1.SUPP_CODE,
        t_1.SUPP_MONTH,
        t_1.SUPP_PRO_MONTH,
        t_2.ID as ACTSTMTID
		from V_WM_STORESALETMP t_1 
		left join T_WM_ACCOUNTSTATEMENT t_2 on t_1.SUPP_MONTH=t_2.SUPP_MONTH
	);
	
create or replace view V_WM_MONTHDTL as 
	(
		select t_1.ID as ID,t_1.HEADID as SUPP_MONTH_HEAD,t_1.WM_MONTH,
		t_1.PRODUCTID,t_1.WMCODE,t_1.SUPP_CODE,
		t_1.SALE_COUNT as PRO_COUNT,
		'' as IN_PRICE,'' as OUT_PRICE,
		t_1.SALE_VALUE as OUT_ALL_AMOUNT,
		t_1.SALE_VALUE+SUP_ALL_RETURNRPRICE as OUT_ALL_PRICE,
		t_2.SUP_ALL_RETURNRPRICE,t_1.SUPPLIER,t_1.WAREHOUSE,
		t_1.SUPP_MONTH,t_1.SUPP_PRO_MONTH
		from V_WM_STORESALE t_1 
		left join V_WM_STOCKRETURN t_2 on t_1.SUPP_PRO_MONTH=t_2.SUPP_PRO_MONTH
		group by t_1.SUPP_PRO_MONTH
	);

	
create or replace view V_SDB_ORDERS_ALL as 
		(
			select * from SDB_ORDERS where (STATUS='active' and PAY_STATUS='1' and DISABLED='false') or (STATUS='finish' and DISABLED='false')
		);
create or replace view V_SDB_ORDER_ITEMS_ALL as 
		(
			select * from SDB_ORDER_ITEMS where ORDER_ID in (select ORDER_ID from V_SDB_ORDERS_ALL)
		);
create or replace view V_WM_OUTPRE as 
	(
		select t_1.WAREHOUSE,t_1.HYVERSION,
		(CASE WHEN t_1.OUT_COUNT is null THEN t_2.NUMS ELSE t_1.OUT_COUNT END ) as OUT_COUNT,
		t_1.OUT_PCOUNT,t_1.OUT_RCOUNT,t_1.ITEM_ID_PK,
		t_1.All_CAPACITYNUM,t_1.All_WEIGHTNUM,t_1.BILL_STATUS,t_1.REMAINDER_NUM,
		t_1.LOGISTICS_NAME,t_1.LOGISTICS_CODE,t_1.LOGISTICS_MEMO,
		
		t_3.PACKING_NUM,t_3.CAPACITYNUM,t_3.WEIGHTNUM,t_3.SUPPLIER,t_3.WMCODE,t_3.CONTRA_INPRICE,t_3.IN_PRICE as IN_PRICE,t_3.ID as PRODUCTID,t_3.PRODUCT_ID as PRODUCT_ID,
		t_3.SUPP_CODE,
		
		t_2.ITEM_ID,t_2.ORDER_ID,t_2.NAME,t_2.PRICE as OUT_PRICE,t_2.NUMS as PRO_COUNT,t_2.AMOUNT as OUT_ALL_PRICE,
		concat(CONVERT(t_2.ORDER_ID,char),':',t_3.WMCODE,'(' ,t_2.NAME,')') as OUT_CODE
		
		from (V_SDB_ORDER_ITEMS_ALL t_2 left join T_WM_SDBPRODUCT t_3 on t_2.PRODUCT_ID=t_3.SDB_PRODUCT_ID) left join T_WM_OUTPREPART t_1 on t_1.ITEM_ID_PK=t_2.ITEM_ID
	)
	union all
	(
		select t_2.WAREHOUSE,t_2.HYVERSION,t_2.OUT_COUNT,t_2.OUT_PCOUNT,t_2.OUT_RCOUNT,0 as ITEM_ID_PK,
		t_2.All_CAPACITYNUM,t_2.All_WEIGHTNUM,t_2.BILL_STATUS,t_2.REMAINDER_NUM,
		t_2.LOGISTICS_NAME,t_2.LOGISTICS_CODE,t_2.LOGISTICS_MEMO,
		
		t_3.PACKING_NUM,t_3.CAPACITYNUM,t_3.WEIGHTNUM,t_3.SUPPLIER,t_3.WMCODE,t_3.CONTRA_INPRICE,t_3.IN_PRICE as IN_PRICE,t_3.ID as PRODUCTID,t_3.PRODUCT_ID as PRODUCT_ID,
		t_3.SUPP_CODE,
		
		t_2.ITEM_ID,t_2.ORDER_ID,'' as NAME,0 as OUT_PRICE,0 as PRO_COUNT,0 as OUT_ALL_PRICE,
		concat(CONVERT(t_2.ORDER_ID,char),':',t_3.WMCODE,'(' ,t_3.NAME,')') as OUT_CODE
		
		from T_WM_OUTPREPART t_2 left join T_WM_SDBPRODUCT t_3 on t_2.PRODUCTID=t_3.ID 
		where t_2.ITEM_ID like "CKItem%"
	);
create or replace view V_WM_OUT_FK_QUERY as 
		(
			select concat(t_1.ORDER_ID,'_',t_1.SUBORDERID) as ID,t_1.ORDER_ID_SUB,t_1.SUBORDERID,t_1.ORDER_ID,t_2.OUT_TYPE,t_2.MEMBER_ID,t_2.SHIP_NAME,t_2.SHIP_ADDR,t_2.SHIP_TEL,t_2.SHIP_MOBILE,
			t_2.FINAL_AMOUNT,t_2.SHIPPING,t_2.ORDER_MEMO,t_1.BILL_STATUS1, t_1.BILL_STATUS from t_wm_outpredtl t_1 
			LEFT JOIN t_wm_outpart t_2 on t_1.ORDER_ID=t_2.ORDER_ID GROUP BY t_1.SUBORDERID,t_1.ORDER_ID
		);