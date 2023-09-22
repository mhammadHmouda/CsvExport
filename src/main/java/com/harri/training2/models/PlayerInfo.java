package com.harri.training2.models;

import com.harri.training2.annotations.FieldMapping;
import lombok.Data;

@Data
public class PlayerInfo {
    @FieldMapping("name")
    private String name;

    @FieldMapping("age")
    private int age;

    @FieldMapping("nationality")
    private String nationality;

    @FieldMapping("fifa_score")
    private double score;

    @FieldMapping("salary")
    private int salary;
}
