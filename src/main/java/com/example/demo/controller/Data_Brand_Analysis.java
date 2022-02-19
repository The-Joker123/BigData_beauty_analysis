package com.example.demo.controller;

import com.example.demo.pojo.Top10_Day_Brand;
import com.example.demo.pojo.Top10_Sum_Brand;
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
public class Data_Brand_Analysis {

    public static final Logger logger = LoggerFactory.getLogger(Data_Cleaning.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate jdbcTemplate;

    //每日销售走势
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

//Top10的销售记录
//    select cast(update_time as date),trade_name,Sum(COALESCE(sale_count,0)),Sum(COALESCE(sale_amount,0)),row_number() over (partition by cast(update_time as date)  order by cast(update_time as date), Sum(COALESCE(sale_count,0)) desc) as ranking from cosmetics_data ;
//cast(update_time as date),trade_name,Sum(COALESCE(sale_amount,0)),Sum(COALESCE(sale_count,0)),rank() over(partition by cast(update_time as date)  order by Sum(COALESCE(sale_count,0))  desc ) as ranking
    @RequestMapping("/table/Top10_Day_Brand")
    public ArrayList<ArrayList<Top10_Day_Brand>>  Top10_Day_Brand() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
            String sql = "select * from(select cast(update_time as date) as day,trade_name,Sum(COALESCE(sale_amount,0)) as o1,Sum(COALESCE(sale_count,0)) as o2,rank() over (partition by cast(update_time as date) order by Sum(COALESCE(sale_count,0)) desc) as ranking from cosmetics_data group by cast(update_time as date),trade_name order by day desc) as t1 where t1.ranking<=10";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        ArrayList<ArrayList<Top10_Day_Brand>> big_list = new ArrayList<ArrayList<Top10_Day_Brand>>();

        int count = res.getMetaData().getColumnCount();

        while (res.next()) {
            ArrayList<Top10_Day_Brand> small_list = new ArrayList<Top10_Day_Brand>();
            for (int i = 1; i<= 10; i++) {
                Top10_Day_Brand top10_day_brand=new Top10_Day_Brand();
                top10_day_brand.setYear(res.getString(1));
                top10_day_brand.setBrand(res.getString(2));
                top10_day_brand.setSale_count(res.getInt(4));
                top10_day_brand.setSale_amount(res.getInt(3));
                top10_day_brand.setRank(res.getInt(5));
                small_list.add(top10_day_brand);
                if(i==10){
                    break;
                }else {
                    res.next();
                }

            }
            big_list.add(small_list);
        }

        return big_list;

    }

    //前10美妆销售量排名
    @RequestMapping("/table/Top10_Sum_Brand")
    public ArrayList<Top10_Sum_Brand>  Top10_Sum_Brand() throws SQLException {
        // Statement statement = jdbcDataSource.getConnection().createStatement();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = " select * from(select trade_name,Sum(COALESCE(sale_count,0)) as o2,rank() over ( order by Sum(COALESCE(sale_count,0)) desc) as ranking from cosmetics_data group by trade_name ) as t1 where t1.ranking<=10";
        logger.info("Running: " + sql);
        ResultSet res = statement.executeQuery(sql);
        ArrayList<Top10_Sum_Brand> list = new ArrayList<>();

        int count = res.getMetaData().getColumnCount();

        while (res.next()) {

            Top10_Sum_Brand top10_Sum_Brand=new Top10_Sum_Brand();
            top10_Sum_Brand.setName(res.getString(1));
            top10_Sum_Brand.setValue((res.getDouble(2))/10000);
            list.add( top10_Sum_Brand);
        }
        return list;
    }
}
