package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@RestController
@CrossOrigin
public class dataAnalysis {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;

//    @RequestMapping("/table/show")
//    public List<String> showtables() {
//        List<String> list = new ArrayList<String>();
//        Statement statement = null;
//        try {
//            statement = druidDataSource.getConnection().createStatement();
//            String sql = "show tables";
//            logger.info("Running: " + sql);
//            ResultSet res = statement.executeQuery(sql);
//            while (res.next()) {
//                list.add(res.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//    @RequestMapping("/databases/show")
//    public List<String> showdatabases() {
//        List<String> list = new ArrayList<String>();
//        Statement statement = null;
//        try {
//            statement = druidDataSource.getConnection().createStatement();
//            String sql = "show databases";
//            logger.info("Running: " + sql);
//            ResultSet res = statement.executeQuery(sql);
//            while (res.next()) {
//                list.add(res.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    /**
//     * 示例：创建新表
//     */
//    @RequestMapping("/table/create")
//    public String createTable() {
//        StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
//        sql.append("cosmetics_data");
//        sql.append("(update_time STRING, id STRING, title STRING, price INT,sale_count INT,comment_count INT,trade_name STRING )");
//        sql.append("ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' "); // 定义分隔符
//        sql.append("STORED AS TEXTFILE"); // 作为文本存储
//
//        logger.info("Running: " + sql);
//        String result = "Create table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql.toString());
//            jdbcTemplate.execute(sql.toString());
//        } catch (DataAccessException dae) {
//            result = "Create table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//
//    }
//
//    /**
//     * 示例：将Hive服务器本地文档中的数据加载到Hive表中
//     */
//    @RequestMapping("/table/load")
//    public String loadIntoTable() {
//        String filepath = "/data/双十一淘宝美妆数据.csv";
//        String sql = "load data local inpath '" + filepath + "' into table cosmetics_data";
//        String result = "Load data into table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Load data into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//
//    @RequestMapping("/table/selectAll")
//    public List<String> selectFromTable() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select * from " + "cosmetics_data";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        List<String> list = new ArrayList<String>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            str = "";
//            for (int i = 1; i < count; i++) {
//                str += res.getString(i) + " ";
//            }
//            str += res.getString(count);
//            logger.info(str);
//            list.add(str);
//        }
//        return list;
//    }
//
//    //    select count(*) from (select update_time,id,title,price,sale_count,comment_count,trade_name,count(*) from cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name having count(*)>1)
//    @RequestMapping("/table/countRepetition")
//    public List<String> countRepetition() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select update_time,id,title,price,sale_count,comment_count,trade_name,count(*) from " + "cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name having count(*)>1";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        List<String> list = new ArrayList<String>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            str = "";
//            for (int i = 1; i < count; i++) {
//                str += res.getString(i) + " ";
//            }
//            str += res.getString(count);
//            logger.info(str);
//            list.add(str);
//        }
//        return list;
//    }
//
//    @RequestMapping("/table/descTable")
//    public List<String> descTable() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "desc " + "cosmetics_data";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        List<String> list = new ArrayList<String>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            str = "";
//            for (int i = 1; i < count; i++) {
//                str += res.getString(i) + " ";
//            }
//            str += res.getString(count);
//            logger.info(str);
//            list.add(str);
//        }
//        return list;
//    }
//
//    @RequestMapping("/table/insert")
//    public String insertIntoTable() {
//        String sql = "INSERT INTO TABLE  cosmetics_data(update_time,id,title,price,sale_count,comment_count,trade_name) VALUES('2016/11/14','A18164178225','CHANDO/自然堂 雪域精粹纯粹滋润霜50g 补水保湿 滋润水润面霜',139,26719,2704,'自然堂')";
//        String result = "Insert into table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Insert into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//
//    @RequestMapping("/table/overwrite")
//    public String overwriteTable() {
//        String sql = "insert overwrite table cosmetics_data "+" select update_time,id,title,price,sale_count,comment_count,trade_name from " +"cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name";
//        String result = "Insert overwrite table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Insert into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//
//    @RequestMapping("/table/alterTable")
//    public String alterTable() {
//        String sql = "alter table cosmetics_data add columns(sale_amount int)" ;
//        String result = "alter table table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Insert into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//    //    select update_time,id,title,COALESCE(price,0),COALESCE(sale_count,0),comment_count,trade_name,price*sale_count from cosmetics_data;
//    @RequestMapping("/table/overwriteColumns")
//    public String overwriteColumns() {
//        String sql = "insert overwrite table cosmetics_data "+" select update_time,id,title,COALESCE(price,0),COALESCE(sale_count,0),comment_count,trade_name,price*sale_count from " +"cosmetics_data ";
//        String result = "Insert overwrite table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Insert into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//
//    @RequestMapping("/table/nullData")
//    public List<String> nullData() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select update_time,id,title,price,sale_count,comment_count,trade_name from " + "cosmetics_data where title IS NULL OR price IS NULL OR sale_count IS NULL OR comment_count IS NULL OR trade_name IS NULL";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        List<String> list = new ArrayList<String>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            str = "";
//            for (int i = 1; i < count; i++) {
//                str += res.getString(i) + " ";
//            }
//            str += res.getString(count);
//            logger.info(str);
//            list.add(str);
//        }
//        return list;
//    }
//    @RequestMapping("/table/ifnull")
//    public List<String> ifNullData() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select COALESCE(price,0),COALESCE(sale_count,0),COALESCE(comment_count,0) from " + "cosmetics_data ";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        List<String> list = new ArrayList<String>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            str = "";
//            for (int i = 1; i < count; i++) {
//                str += res.getString(i) + " ";
//            }
//            str += res.getString(count);
//            logger.info(str);
//            list.add(str);
//        }
//        return list;
//    }
//
//    @RequestMapping("/table/date_overwrite")
//    public String date_overwriteable() {
//        String sql = "insert overwrite table cosmetics_data "+" select from_unixtime(update_time, 'yyyy-MM-dd'),id,title,price,sale_count,comment_count,trade_name from " +"cosmetics_data group by update_time,id,title,price,sale_count,comment_count,trade_name";
//        String result = "Insert overwrite table successfully...";
//        try {
//            // hiveJdbcTemplate.execute(sql);
//            jdbcTemplate.execute(sql);
//        } catch (DataAccessException dae) {
//            result = "Insert into table encounter an error: " + dae.getMessage();
//            logger.error(result);
//        }
//        return result;
//    }
//select update_time,Sum(COALESCE(sale_count,0)) from cosmetics_data group by update_time order by update_time;
    @RequestMapping("/table/dailySales")
    public HashMap<String,Integer> dailySales() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select cast(update_time as date) as day,Sum(COALESCE(sale_count,0)) from " + "cosmetics_data " +
                "group by cast(update_time as date)  order by day";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        HashMap<String,Integer> map= new LinkedHashMap<>();
        int count = res.getMetaData().getColumnCount();
        String str = null;
        while (res.next()) {
            map.put(res.getString(1),res.getInt(2));
            logger.info(str);
        }
        System.out.println(map);
        return map;
    }


        @RequestMapping("/table/Top20_Day_Brand")
    public List<String> Top20_Day_Brand() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
            String sql = "select cast(update_time as date) as day,trade_name,Sum(COALESCE(sale_amount,0)) as o1,Sum(COALESCE(sale_count,0)) as o2 from " + "cosmetics_data " +
                    "group by cast(update_time as date),trade_name order by day,o1 desc";
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
}
