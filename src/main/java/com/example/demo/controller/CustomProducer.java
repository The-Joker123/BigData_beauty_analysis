package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

/**
 * @author OverChat
 * @date
 */
@Service
public class CustomProducer {

    public static final Logger logger = LoggerFactory.getLogger(Data_Cleaning.class);

    @Autowired
    @Qualifier("hiveDruidDataSource")
    private DataSource druidDataSource;


    public ResultSet Test(String sql) throws SQLException {

        Statement statement = druidDataSource.getConnection().createStatement();

        logger.info("Running: " + sql);

        Queue< ResultSet> queue = new LinkedList<ResultSet>();
        queue.offer(statement.executeQuery(sql));
        return queue.poll();
    }


}
