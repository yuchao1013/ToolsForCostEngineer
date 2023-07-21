package com.yuchao.toolsforcostengineerbackend.checkbillquota.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName TitlePosition
 * @Description 配置表头对应的列，用于检查清单和定额工程量不一致的情况
 * @Author YuChao
 * @Date 2023/6/26 22:23
 * @Version 1.0
 */
@Component
@Data
public class TitlePosition {
    private Integer SNColNum = 0; //序号
    private Integer idColNum = 1; //编码
    private Integer nameColNum = 2; //项目名称
    private Integer detailColNum = 3; //项目特征
    private Integer unitColNum = 4; //计量单位
    private Integer countColNum = 5; //工程量
    private Integer priceColNum = 6; //综合单价
    private Integer amountColNum = 7; //合价
}