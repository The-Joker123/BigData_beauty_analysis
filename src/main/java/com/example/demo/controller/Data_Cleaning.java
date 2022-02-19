package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Data_Cleaning {

    public static final Logger logger = LoggerFactory.getLogger(Data_Cleaning.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;

    //查询数据表
    @RequestMapping("/table/show")
    public List<String> showtables() {
        List<String> list = new ArrayList<String>();
        Statement statement = null;
        try {
            statement = druidDataSource.getConnection().createStatement();
            String sql = "show tables";
            logger.info("Running: " + sql);
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                list.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询数据库
    @RequestMapping("/databases/showdatabases")
    public List<String> showdatabases() {
        List<String> list = new ArrayList<String>();
        Statement statement = null;
        try {
            statement = druidDataSource.getConnection().createStatement();
            String sql = "show databases";
            logger.info("Running: " + sql);
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                list.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

   //创建新表
    @RequestMapping("/table/create")
    public String createTable() {
        StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("cosmetics_data");
        sql.append("(update_time STRING, id STRING, title STRING, price INT,sale_count INT,comment_count INT,trade_name STRING )");
        sql.append("ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' "); // 定义分隔符
        sql.append("STORED AS TEXTFILE"); // 作为文本存储

        logger.info("Running: " + sql);
        String result = "Create table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql.toString());
            jdbcTemplate.execute(sql.toString());
        } catch (DataAccessException dae) {
            result = "Create table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }

 //将HDFS服务器本地文档中的数据加载到Hive表中
    @RequestMapping("/table/load")
    public String loadIntoTable() {
        String filepath = "/data/双十一淘宝美妆数据.csv";
        String sql = "load data local inpath '" + filepath + "' into table cosmetics_data";
        String result = "Load data into table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Load data into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }

//扫描cosmetics_data表中所有数据
    @RequestMapping("/table/selectAll_cosmetics_data")
    public List<String> selectAll_cosmetics_data() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select * from " + "cosmetics_data";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }

//   查看重复数据多少
    @RequestMapping("/table/countRepetition")
    public List<String> countRepetition() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select update_time,id,title,price,sale_count,comment_count,trade_name,count(*) from " + "cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name having count(*)>1";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }

    //查看cosmetics_data表结构
    @RequestMapping("/table/descTable")
    public List<String> descTable() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "desc " + "cosmetics_data";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }

//向表中插入新数据
    @RequestMapping("/table/insert")
    public String insertIntoTable() {
        String sql = "INSERT INTO TABLE  cosmetics_data(update_time,id,title,price,sale_count,comment_count,trade_name) VALUES('2016/11/14','A18164178225','CHANDO/自然堂 雪域精粹纯粹滋润霜50g 补水保湿 滋润水润面霜',139,26719,2704,'自然堂')";
        String result = "Insert into table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }

//重写数据
    @RequestMapping("/table/overwrite")
    public String overwriteTable() {
        String sql = "insert overwrite table cosmetics_data "+" select update_time,id,title,price,sale_count,comment_count,trade_name,sale_amount from " +"cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name,sale_amount";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }
//修改表结构
    @RequestMapping("/table/alterTable")
    public String alterTable() {
        String sql = "alter table cosmetics_data add columns(sale_amount int)" ;
        String result = "alter table table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }
//重写数据;
    @RequestMapping("/table/overwriteColumns")
    public String overwriteColumns() {
        String sql = "insert overwrite table cosmetics_data "+" select update_time,id,title,COALESCE(price,0),COALESCE(sale_count,0),comment_count,trade_name,price*sale_count from " +"cosmetics_data ";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }
//是否有空数据
    @RequestMapping("/table/nullData")
    public List<String> nullData() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select update_time,id,title,price,sale_count,comment_count,trade_name from " + "cosmetics_data where title IS NULL OR price IS NULL OR sale_count IS NULL OR comment_count IS NULL OR trade_name IS NULL";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }
    //如果有空
    @RequestMapping("/table/ifnull")
    public List<String> ifNullData() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select COALESCE(price,0),COALESCE(sale_count,0),COALESCE(comment_count,0), from " + "cosmetics_data ";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }
//向表中修改数据
    @RequestMapping("/table/date_overwrite")
    public String date_overwriteable() {
        String sql = "insert overwrite table cosmetics_data "+" select replace(update_time,'/','-'),id,title,COALESCE(price,0),COALESCE(sale_count,0),COALESCE(comment_count,0),trade_name,sale_amount from " +"cosmetics_data ";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }



    //--------------------------------------

    //   查看重复数据多少
    @RequestMapping("/sales_order_table/countRepetition")
    public List<String> countRepetition_sales_order_table() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount,count(*) from " + "sales_order_table group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount having count(*)>1";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }


    //合并重复数据
    @RequestMapping("/sales_order_table/overwrite")
    public String overwrite_sales_order_table() {
        String sql = "insert overwrite table sales_order_table "+" select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount from " +"sales_order_table group by order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }



    //是否有空数据
    @RequestMapping("/sales_order_table/nullData")
    public List<String> nullData_sales_order_table() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select  * from " + "sales_order_table where order_code IS NULL OR order_date IS NULL OR customer_code IS NULL OR district IS NULL OR province IS NULL OR location IS NULL OR product_number IS NULL OR number_orders IS NULL OR order_price IS NULL OR amount IS NULL";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }


    @RequestMapping("/sales_order_table/ifnull_overwrite")
    public String ifnull_overwrite() {
        String sql = "insert overwrite table sales_order_table "+" select order_code,order_date,customer_code,district,province,location,product_number,COALESCE(number_orders,0),COALESCE(order_price,0),COALESCE(amount,0) from " +"sales_order_table";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }


    //如果有空
    @RequestMapping("/table/ifnull_sales_order_table")
    public List<String> ifNullData_sales_order_table() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select COALESCE(number_orders,0),COALESCE(order_price,0),COALESCE(amount,0) from " + "sales_order_table ";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }


    //修改日期格式'#','-'
    @RequestMapping("/sales_order_table/Dirty_data_date_overwrite")
    public String date_Dirty_data_date_overwrite() {
        String sql = "insert overwrite table sales_order_table "+" select order_code,replace(translate(order_date,'/',space(3)),space(1),'-'),customer_code,district,province,location,product_number,number_orders,order_price,amount from " +"sales_order_table ";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }

//    select order_date from sales_order_table where order_date is null;
    //扫描cosmetics_data表中所有数据
    @RequestMapping("/sales_order_table/Dirty_data_select")
    public List<String> selectAll_sales_order_table() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select order_date from " + "sales_order_table where order_date is null ";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            str = "";
            for (int i = 1; i < count; i++) {
                str += res.getString(i) + " ";
            }
            str += res.getString(count);
            logger.info(str);
            list.add(str);
        }
        return list;
    }


    //修改日期格式
    @RequestMapping("/sales_order_table/Dirty_data_date_overwrite_2021_1_1")
    public String Dirty_data_date_overwrite_2021_1_1() {
        String sql = "insert overwrite table sales_order_table "+" select order_code,order_date,customer_code,district,province,location,product_number,number_orders,order_price,amount from " +"sales_order_table where order_date<'2021-1-1' ";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }
    //修改provice格式
    @RequestMapping("/sales_order_table/Dirty_data_province_overwrite")
    public String Dirty_data_province_overwrite() {
        String sql = "insert overwrite table sales_order_table "+" select order_code,order_date,customer_code,district,replace(translate(province,'自治区维吾尔回族壮族省市',space(7)),space(1),space(0)),replace(translate(location,'自治区维吾尔回族壮族省市',space(7)),space(1),space(0)),product_number,number_orders,order_price,amount from " +"sales_order_table";
        String result = "Insert overwrite table successfully...";
        try {
            // hiveJdbcTemplate.execute(sql);
            jdbcTemplate.execute(sql);
        } catch (DataAccessException dae) {
            result = "Insert into table encounter an error: " + dae.getMessage();
            logger.error(result);
        }
        return result;
    }

}
