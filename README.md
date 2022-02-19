## 项目介绍

1. 使用Hadoop技术可快速生成分析结果，对6万条美妆销售数据分析，将数据转化为有价值的数据。
2. 在Centos7中搭建Hadoop和Hive环境。SpringBoot整合HiveJdbc, 远程连接HiveServer2，并结合Java和HiveSQL
   对存储在Hadoop 的6万条电商数据，进行数据分析。后端生成Json 格式，前端解析Json格式，并实现数据大屏可视化。
3. 通过大数据分析，生成有价值数据：每日订单量走势，前10销售品牌，哪里人最爱美等。


## 1.数据集说明
对6万条美妆销售数据分析，将数据转化为有价值的数据。
## 2.数据处理

### 2.1数据导入

将数据加载到 hive, 然后通过 hive 对数据进行数据处理。

```SQL
-- 建表销售订单表
Create table  sales_order_table(
`order_code` string comment '订单编号',
`order_date` string comment '订单日期',
`customer_code` string comment '客户编码',
`district` string  comment  '所在区',
`province` string  comment '所在省',
`location` string comment '所在地',
`product_number` string comment '商品编号',
`number_orders` int comment '订购数',
`order_price` int comment '订购单价',
`amount` int comment '金额'
)
row format delimited
fields terminated by '\t' lines terminated by '\n';



-- 将数据加载到Hive中
LOAD DATA  INPATH  '/data/销售订单表.txt' OVERWRITE INTO TABLE sales_order_table;     

```
### 2.2数据清洗

数据处理主要包括：删除重复值，时间戳格式化，删除异常值。

#### 商品信息表

```SQL

--查看重复数据有多少
select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount,count(*) from sales_order_table 
group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount having count(*)>1

--去掉完全重复的数据
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount from sales_order_table 
group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount
--查看是否有空数据
select  * from sales_order_table 
where order_code IS NULL OR order_date IS NULL OR customer_code IS NULL OR district IS NULL OR province IS NULL OR location IS NULL OR product_number IS NULL OR number_orders IS NULL OR order_price IS NULL OR amount IS NULL
--清除空数据
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,province,location,product_number,COALESCE(number_orders,0),COALESCE(order_price,0),COALESCE(amount,0) 
from sales_order_table

--修改日期格式
insert overwrite table sales_order_table
select order_code,replace(translate(order_date,'/',space(3)),space(1),'-'),customer_code,district,province,location,product_number,number_orders,order_price,amount from sales_order_table

--修改省份格式
insert overwrite table sales_order_table 
select order_code,order_date,customer_code,district,replace(translate(province,'自治区维吾尔回族壮族省市',space(7)),space(1),space(0)),replace(translate(location,'自治区维吾尔回族壮族省市',space(7)),space(1),space(0)),product_number,number_orders,order_price,amount 
from sales_order_table
```
## 3.数据分析可视化

### Top10各美妆品牌销售量

```sql
select * from(
    select trade_name,Sum(COALESCE(sale_count,0)) as o2,rank() over ( order by   		Sum(COALESCE(sale_count,0)) desc) as ranking from cosmetics_data group by trade_name ) as t1 
    where t1.ranking<=10

```
![image.png](https://s2.loli.net/2022/02/15/QHKe6Ys1o9dDImw.png)

### 每日订单量走势

```sql
select cast(update_time as date) as day,Sum(COALESCE(sale_count,0)) from cosmetics_data 
group by cast(update_time as date)  order by day"
```
![image.png](https://s2.loli.net/2022/02/15/hnMYq4CAoUB5sk9.png)

### 每月各美妆销售量

```sql
select * from(select month(order_date) as m,Sum(number_orders) ,Sum(amount) from sales_order_table 
group by month(order_date) order by month(order_date)) as t1 where t1.m>=1 and t1.m<=9 
```
![image.png](https://s2.loli.net/2022/02/15/UuxDeg1bZysYiWf.png)

**未完待续......**