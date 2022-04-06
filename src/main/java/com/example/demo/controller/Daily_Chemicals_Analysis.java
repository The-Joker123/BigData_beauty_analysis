package com.example.demo.controller;

import com.example.demo.pojo.MonthlySubscription;
import com.example.demo.pojo.RegionalSales;
import com.example.demo.pojo.SmallCategoryPO;
import com.example.demo.pojo.WherePeopleBeauty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    private   CustomProducer customProducer;
    @Resource
    private  SmallCategoryPO smallCategoryPO;

    //每个月订阅情况
    @RequestMapping("/sales_order_table/Monthly_subscription")
    public ArrayList<MonthlySubscription>  Monthly_subscription() throws SQLException {

        String sql = "select * from(select month(order_date) as m,Sum(number_orders) ,Sum(amount) from sales_order_table " +
                "group by month(order_date) order by month(order_date)) as t1 where t1.m>=1 and t1.m<=9 ";

        ArrayList<MonthlySubscription> list = new ArrayList<>();
        ResultSet res=customProducer.Queen(sql);
        int count = res.getMetaData().getColumnCount();

        while (res.next()) {

            MonthlySubscription monthlySubscription =new MonthlySubscription();
            monthlySubscription.setOrder_date(res.getString(1));
            monthlySubscription.setNumber_orders(res.getDouble(2)/1000000);
            monthlySubscription.setAmount(res.getDouble(3)/10000000);
            list.add( monthlySubscription);
        }
        return list;
    }



    //哪里人最爱
    @RequestMapping("/sales_order_table/where_people_beauty")
    public ArrayList<WherePeopleBeauty>  where_people_beauty() throws SQLException {

        String sql = "select location, Sum(number_orders),rank() over ( order by Sum(number_orders) desc) as ranking from sales_order_table " +
                "group by location limit 0,10";

        ArrayList<WherePeopleBeauty> list = new ArrayList<>();

        ResultSet res=customProducer.Queen(sql);
        while (res.next()) {

            WherePeopleBeauty wherePeopleBeauty =new WherePeopleBeauty();
            wherePeopleBeauty.setLocation(res.getString(1));
            wherePeopleBeauty.setAmount((res.getDouble(2))/10000);
            wherePeopleBeauty.setRanking(res.getInt(3));
            list.add( wherePeopleBeauty);
        }
        return list;
    }

    //地区销售量
    @RequestMapping("/sales_order_table/Regional_sales")
    public ArrayList<RegionalSales>  Regional_sales() throws SQLException {

        String sql = "select location, Sum(number_orders),rank() over ( order by Sum(number_orders) desc) as ranking from sales_order_table " +
        "group by location";

        ArrayList<RegionalSales> list = new ArrayList<>();
        ResultSet res=customProducer.Queen(sql);
        while (res.next()) {

            RegionalSales regionalsales =new RegionalSales();
            regionalsales.setName(res.getString(1));
            regionalsales.setValue((res.getDouble(2))/10000);
            list.add( regionalsales );
        }
        return list;
    }


    @RequestMapping("/sales_order_table/Small_category")
    public ArrayList<SmallCategoryPO>  Small_category() throws SQLException {

        String sql = "select c.commodity_small_category,collect_set(c.commodity_large_category)[0],count(c.commodity_small_category) from sales_order_table s join  commodity_information_table  c on s.product_number = c.commodity_code group by c.commodity_small_category";

        ArrayList<SmallCategoryPO > list = new ArrayList<>();
        ResultSet res=customProducer.Queen(sql);
        while (res.next()) {
            SmallCategoryPO smallCategoryPO =new SmallCategoryPO();
            smallCategoryPO.setValue(res.getInt(3));
            smallCategoryPO.setName(res.getString(1));

            list.add( smallCategoryPO );
        }
        return list;
    }
}
