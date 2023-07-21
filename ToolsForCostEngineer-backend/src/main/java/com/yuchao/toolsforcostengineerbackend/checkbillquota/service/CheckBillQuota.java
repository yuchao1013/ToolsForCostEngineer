package com.yuchao.toolsforcostengineerbackend.checkbillquota.service;

import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CheckBillQuota {
    List<CheckBillQuotaResult> getResult(MultipartFile excelFile);

}
