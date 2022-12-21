package study.pojo;

import lombok.Data;
import java.util.Date;
@Data
public class Dept {
    private Integer id;
    private String name;
    private String addr;
    private Integer deleted;
    private Date gmtCreate;
    private Date gmtModified;
}
