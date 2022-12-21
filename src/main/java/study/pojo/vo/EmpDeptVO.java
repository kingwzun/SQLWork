package study.pojo.vo;

import lombok.Data;

@Data
public class EmpDeptVO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer deptId;
    private String deptName;
}