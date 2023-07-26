package com.yuchao.toolsforcostengineerbackend.checkbillquota.service;

import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResult;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResultV2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CheckBillQuota {
    List<CheckBillQuotaResult> getResult(MultipartFile excelFile);
    List<CheckBillQuotaResultV2> getResultV2(MultipartFile excelFile);

}
