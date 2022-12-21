package study.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserQuery {
    private Integer page;
    private Integer limit;
    private String name;
    private String email;
    private String phone;
}
