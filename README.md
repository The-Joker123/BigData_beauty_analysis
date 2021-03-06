## ðé¡¹ç®ä»ç»

1.  ð ä½¿ç¨`Hadoop`ææ¯å¯å¿«éçæåæç»æï¼å¯¹6ä¸æ¡ç¾å¦éå®æ°æ®åæï¼å°æ°æ®è½¬åä¸ºæä»·å¼çæ°æ®ã
2.  â¨ å¨Centos7ä¸­æ­å»º ` Hadoop`å ` Hive`ç¯å¢ã`SpringBoot`æ´å`HiveJdbc`, è¿ç¨è¿æ¥`HiveServer2`ï¼å¹¶ç»å`Java`å`HiveSQL`
   å¯¹å­å¨å¨`Hadoop` ç6ä¸æ¡çµåæ°æ®ï¼è¿è¡æ°æ®åæãåç«¯çæ`Json `æ ¼å¼ï¼åç«¯è§£æ`Json`æ ¼å¼ï¼å¹¶å®ç°æ°æ®å¤§å±å¯è§åã
3.  ð éè¿å¤§æ°æ®åæï¼çææä»·å¼æ°æ®ï¼**æ¯æ¥è®¢åéèµ°å¿**ï¼**å10éå®åç**ï¼**åªéäººæç±ç¾**ç­ã

## â¡æ ¸å¿ææ¯æ 

- â**Java-1.8** :æåæ¬¢è¿çä¸ä¸ªçæ¬ã
- ð¦**Hive-3.1** ï¼  å¨3.xä¹åä¹æ¯æ`update`,ä½æ¯éåº¦å¤ªæ¢ï¼è¿éè¦è¿è¡åæ¡¶ï¼ç°å¨`Hive` æ¯æå¨æ°`ACID`ï¼å¹¶ä¸åºå±éç¨`TEZå¼æ` ååå­è¿è¡æ¥è¯¢ï¼æ¯`Hive-2.*`æé«2-50åã
  `Apache Tez`å°`MapReduce`æ¿æ¢ä¸ºé»è®¤ç`Hive`æ§è¡å¼æãä¸åæ¯æ`MapReduce`ï¼å¹¶è¯æäº`Tez`çç¨³å®æ§ãéè¿æåæ ç¯å¾ï¼DAGï¼åæ°æ®ä¼ è¾åè¯­çè¡¨è¾¾å¼ï¼å¨`Tez`ä¸æ§è¡`Hive`æ¥è¯¢å¯ä»¥æé«æ§è½ã 
- ð**Hadoop-3.2.0**: `Hadoop3.x`ä»¥åå°ä¼è°æ´æ¹æ¡æ¶æï¼å°`Mapreduce` åºäºåå­+io+ç£çï¼å±åå¤çæ°æ®ã
  å¶å®æå¤§æ¹åçæ¯`HDFS`,`HDFS`éè¿æè¿`black`åè®¡ç®ï¼æ ¹æ®æè¿è®¡ç®ååï¼æ¬å°`black`åï¼å å¥å°åå­ï¼åè®¡ç®ï¼éè¿IOï¼å±äº«åå­è®¡ç®åºåï¼æåå¿«éå½¢æè®¡ç®ç»æã 
- âðð¦**HiveJDBC:** éè¿`Java`ç´æ¥è®¿é®`Hive`æ°æ®åº 
- ððð¦**HiveServer2**:ä½¿è¿ç¨å®¢æ·ç«¯å¯ä»¥æ§è¡å¯¹`Hive`çæ¥è¯¢å¹¶è¿åç»æ 
- ...


## 1.ðæ°æ®éè¯´æ
å¯¹6ä¸æ¡ç¾å¦éå®æ°æ®åæï¼å°æ°æ®è½¬åä¸ºæä»·å¼çæ°æ®ã
## 2.ð¨æ°æ®å¤ç

### 2.1æ°æ®å¯¼å¥

å°æ°æ®å è½½å° hive, ç¶åéè¿ hive å¯¹æ°æ®è¿è¡æ°æ®å¤çã

```SQL
-- å»ºè¡¨éå®è®¢åè¡¨
Create table  sales_order_table(
`order_code` string comment 'è®¢åç¼å·',
`order_date` string comment 'è®¢åæ¥æ',
`customer_code` string comment 'å®¢æ·ç¼ç ',
`district` string  comment  'æå¨åº',
`province` string  comment 'æå¨ç',
`location` string comment 'æå¨å°',
`product_number` string comment 'ååç¼å·',
`number_orders` int comment 'è®¢è´­æ°',
`order_price` int comment 'è®¢è´­åä»·',
`amount` int comment 'éé¢'
)
row format delimited
fields terminated by '\t' lines terminated by '\n';



-- å°æ°æ®å è½½å°Hiveä¸­
LOAD DATA  INPATH  '/data/éå®è®¢åè¡¨.txt' OVERWRITE INTO TABLE sales_order_table;     
```
```SQL
-- å»ºè¡¨ååä¿¡æ¯è¡¨
Create table  cosmetics_data(
`commodity_code` string comment 'ååç¼å·',
`commodity_name` string comment 'åååç§°',
`commodity_small_category` string comment 'ååå°ç±»',
`commodity_large_category` string  comment  'ååå¤§ç±»',
`sales_unit_price` int  comment 'éå®åä»·'
)
row format delimited
fields terminated by '\t' lines terminated by '\n';

-- å°æ°æ®å è½½å°Hiveä¸­
LOAD DATA  INPATH  '/data/ååä¿¡æ¯è¡¨.txt' OVERWRITE INTO TABLE cosmetics_data;   
```



### 2.2æ°æ®æ¸æ´

æ°æ®å¤çä¸»è¦åæ¬ï¼å é¤éå¤å¼ï¼æ¶é´æ³æ ¼å¼åï¼å é¤å¼å¸¸å¼ã

#### ååä¿¡æ¯è¡¨

```SQL

--æ¥çéå¤æ°æ®æå¤å°
select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount,count(*) from sales_order_table 
group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount having count(*)>1

--å»æå®å¨éå¤çæ°æ®
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount from sales_order_table 
group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount
--æ¥çæ¯å¦æç©ºæ°æ®
select  * from sales_order_table 
where order_code IS NULL OR order_date IS NULL OR customer_code IS NULL OR district IS NULL OR province IS NULL OR location IS NULL OR product_number IS NULL OR number_orders IS NULL OR order_price IS NULL OR amount IS NULL
--æ¸é¤ç©ºæ°æ®
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,province,location,product_number,COALESCE(number_orders,0),COALESCE(order_price,0),COALESCE(amount,0) 
from sales_order_table

--ä¿®æ¹æ¥ææ ¼å¼
insert overwrite table sales_order_table
select order_code,replace(translate(order_date,'/',space(3)),space(1),'-'),customer_code,district,province,location,product_number,number_orders,order_price,amount from sales_order_table

--ä¿®æ¹çä»½æ ¼å¼
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,replace(translate(province,'èªæ²»åºç»´å¾å°åæå£®æçå¸',space(7)),space(1),space(0)),replace(translate(location,'èªæ²»åºç»´å¾å°åæå£®æçå¸',space(7)),space(1),space(0)),product_number,number_orders,order_price,amount 
from sales_order_table
```
## 3.ðºæ°æ®åæå¯è§å

### Top10åç¾å¦åçéå®é

```sql
select * from(
    select trade_name,Sum(COALESCE(sale_count,0)) as o2,rank() over ( order by   		Sum(COALESCE(sale_count,0)) desc) as ranking from cosmetics_data group by trade_name ) as t1 
    where t1.ranking<=10

```
![image.png](https://s2.loli.net/2022/02/15/QHKe6Ys1o9dDImw.png)

### æ¯æ¥è®¢åéèµ°å¿

```sql
select cast(update_time as date) as day,Sum(COALESCE(sale_count,0)) from cosmetics_data 
group by cast(update_time as date)  order by day"
```
![image.png](https://s2.loli.net/2022/02/15/hnMYq4CAoUB5sk9.png)

### æ¯æåç¾å¦éå®é

```sql
select * from(select month(order_date) as m,Sum(number_orders) ,Sum(amount) from sales_order_table 
group by month(order_date) order by month(order_date)) as t1 where t1.m>=1 and t1.m<=9 
```
![image.png](https://s2.loli.net/2022/02/15/UuxDeg1bZysYiWf.png)

**æªå®å¾ç»­......**ð