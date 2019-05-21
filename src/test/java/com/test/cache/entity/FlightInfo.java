package com.test.cache.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/5/13 <br>
 */
@Getter
@Setter
public class FlightInfo {
    private String flightNo;
    private String depCode;
    private String arrCode;
    private LocalDate localDate;
}
