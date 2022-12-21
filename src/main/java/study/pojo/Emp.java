package study.pojo;

import lombok.Data;
import java.util.Date;

@Data
public class Emp {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        //所在部门id
        private Integer deptId;
        private Integer deleted;
        private Date gmtCreate;
        private Date gmtModified;

}
