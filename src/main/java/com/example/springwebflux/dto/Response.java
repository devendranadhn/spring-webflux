package com.example.springwebflux.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@NoArgsConstructor
public class Response {

    private Date date = new Date();
    
    private int output;

    public Response(int output){
        this.output = output;
    }

}
