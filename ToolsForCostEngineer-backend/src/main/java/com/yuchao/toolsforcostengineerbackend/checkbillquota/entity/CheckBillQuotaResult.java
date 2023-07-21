package com.yuchao.toolsforcostengineerbackend.checkbillquota.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Classname CheckBillQuotaResult
 * @Description TODO
 * @Date 2023/7/18 22:02
 * @Created by YuChao
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class CheckBillQuotaResult {
    private String projectName; //工程名称
    private String billSN; //序号
    private String billName; //清单名称
    private BigDecimal billCount; //清单工程量
    private String quotaName; //定额名称
    private BigDecimal quotaCount; //定额工程量

}
