package com.example.demo.controller;

import com.example.demo.pojo.MonthlySubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class Daily_Chemicals_Analysis {

    public static final Logger logger = LoggerFactory.getLogger(Data_Cleaning.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;


    //每个月订阅情况
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
}
