package study.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserQuery {
    private Integer page;
    private Integer limit;
    private String name;
    private String email;
    private String phone;
    private Date beginDate;
    private Date endDate;
    //1-超级管理员 2-普通管理员
    private Integer type;
}
