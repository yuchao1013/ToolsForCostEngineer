package com.yuchao.toolsforcostengineerbackend.checkbillquota.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname CheckBillQuotaResultV2
 * @Description TODO
 * @Date 2023/7/18 22:02
 * @Created by YuChao
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBillQuotaResultV2 {
    private String SN; //序号
    private String code; //编码
    private String name; //项目名称
    private String unit; //单位
    private String count; //工程量
    private String isTitle; //是否为标题行（单位工程名称作为分部标题）
}
