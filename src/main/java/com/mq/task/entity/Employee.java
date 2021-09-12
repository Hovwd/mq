package com.mq.task.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column
    @NotBlank(message = "Mail is mandatory")
    private String name;

    @Column(unique = true)
    @Email(message = "Invalid mail format")
    @NotBlank(message = "Name is mandatory")
    private String mail;

    @NotNull(message = "age is mandatory")
    private int age;

    @Column
    @NotNull(message = "age is mandatory")
    private String division;

    @Column()
    @Min(-12)
    @Max(12)
    @NotNull(message = "age is mandatory")
    private int utcOffset;


}
