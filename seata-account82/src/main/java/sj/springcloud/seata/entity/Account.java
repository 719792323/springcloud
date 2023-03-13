package sj.springcloud.seata.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;

    //用户ID
    private Long userId;

    //总额度
    private Integer total;

    //已用额度
    private Integer used;

    //剩余额度
    private Integer residue;
}