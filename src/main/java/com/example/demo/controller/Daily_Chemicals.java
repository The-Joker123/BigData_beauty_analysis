package com.example.demo.controller;

import com.example.demo.pojo.MonthlySubscription;
import com.example.demo.pojo.Top10_Day_Brand;
import com.example.demo.pojo.Top10_Sum_Brand;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
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
public class Daily_Chemicals {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;

    //select update_time,Sum(COALESCE(sale_count,0)) from cosmetics_data group by update_time order by update_time;
//    @RequestMapping("/sales_order_table/Monthly_subscription")
//    public HashMap<String,Integer> Monthly_subscription() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select order_date,Sum(number_orders),Sum(amount) from " + "sales_order_table " +
//                "group by order_date  order by order_date";
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        HashMap<String,Integer> map= new LinkedHashMap<>();
//        int count = res.getMetaData().getColumnCount();
//        String str = null;
//        while (res.next()) {
//            map.put(res.getString(1),res.getInt(2));
//            logger.info(str);
//        }
//        System.out.println(map);
//        return map;
//    }

  //  select month(order_date),Sum(number_orders) ,Sum(amount) from sales_order_table group by month(order_date) order by month(order_date) having  month(order_date)>=1 and month(order_date)<=9;
    @RequestMapping("/sales_order_table/Monthly_subscription")
    public ArrayList<MonthlySubscription>  Monthly_subscription() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "select * from(select month(order_date) as m,Sum(number_orders) ,Sum(amount) from sales_order_table group by month(order_date) order by month(order_date)) as t1 where t1.m>=1 and t1.m<=9 ";
//        因此对NULL进行判断处理时，只能采用IS NULL或IS NOT NULL，而不能采用=, <, <>, !=这些操作符。
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        ArrayList<MonthlySubscription> list = new ArrayList<>();

        int count = res.getMetaData().getColumnCount();

        while (res.next()) {

            MonthlySubscription monthlySubscription =new MonthlySubscription();
            monthlySubscription.setOrder_date(res.getString(1));
            monthlySubscription.setNumber_orders(res.getInt(2));
            monthlySubscription.setAmount(res.getInt(3));
            list.add( monthlySubscription);
        }

        return list;

    }

    //  select month(order_date),Sum(number_orders) ,Sum(amount) from sales_order_table group by month(order_date) order by month(order_date) having  month(order_date)>=1 and month(order_date)<=9;
//    @RequestMapping("/sales_order_table/Monthly_subscription")
//    public ArrayList<MonthlySubscription>  Order_ranking() throws SQLException {
//        // Statement statement = jdbcDataSource.getConnection().createStatement();
//        Statement statement = druidDataSource.getConnection().createStatement();
//        String sql = "select * from(select location,Sum(number_orders), rank() over ( order by Sum(number_orders) desc) as ranking from sales_order_table group by location) as t1 where t1.ranking>=1 and t1.ranking<=9 ";
//
//        logger.info("Running: " + sql);
//        ResultSet res = statement.executeQuery(sql);
//        ArrayList<MonthlySubscription> list = new ArrayList<>();
//
//        int count = res.getMetaData().getColumnCount();
//
//        while (res.next()) {
//
//            MonthlySubscription monthlySubscription =new MonthlySubscription();
//            monthlySubscription.setOrder_date(res.getString(1));
//            monthlySubscription.setNumber_orders(res.getInt(2));
//            monthlySubscription.setAmount(res.getInt(3));
//            list.add( monthlySubscription);
//        }
//
//        return list;
//
//    }


}
