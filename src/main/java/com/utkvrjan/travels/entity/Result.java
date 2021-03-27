package com.utkvrjan.travels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//result.setMsg(e.getMessage()).setState(false);
@Accessors(chain=true)  //链式调用
public class Result implements Serializable {
    private Boolean state = true;
    private String msg;
    private String userId;
    private String userName;
}
