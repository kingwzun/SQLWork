package study.pojo;

import lombok.*;

import java.util.Date;
//JavaBean
@Data

public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private Integer deleted;
    private Date gmtCreate;
    private Date gmtModified;
}

