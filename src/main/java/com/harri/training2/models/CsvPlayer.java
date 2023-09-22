package com.harri.training2.models;

import com.harri.training2.annotations.CSVMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvPlayer {

    @CSVMapping("name")
    private String name;

    @CSVMapping("age")
    private int age;

    @CSVMapping("nationality")
    private String nationality;

    @CSVMapping("fifa_score")
    private double score;

    @CSVMapping("salary")
    private int salary;
}
