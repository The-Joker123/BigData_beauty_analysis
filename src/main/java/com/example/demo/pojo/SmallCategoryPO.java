package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author OverChat
 * @date
 */
@Component
@Data
@NoArgsConstructor
public class SmallCategoryPO {

    private  int value;
    private String name;

}
