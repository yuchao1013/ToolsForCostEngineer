package com.yuchao.toolsforcostengineerbackend.checkbillquota.controller;

import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResult;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.entity.CheckBillQuotaResultV2;
import com.yuchao.toolsforcostengineerbackend.checkbillquota.service.CheckBillQuota;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Classname FileUploadController
 * @Description TODO
 * @Date 2023/7/18 21:19
 * @Created by YuChao
 * @Version 1.0
 */
@RestController
@CrossOrigin
public class FileUploadController {
    private final CheckBillQuota checkBillQuota;
    @Autowired
    public FileUploadController(CheckBillQuota checkBillQuota) {
        this.checkBillQuota = checkBillQuota;
    }

    @PostMapping("/upload/excel")
    public List<CheckBillQuotaResult> excelUpload(@RequestParam("file") MultipartFile excelFile, HttpServletRequest request){
        return checkBillQuota.getResult(excelFile);
    }

    @PostMapping("/upload/CheckBillQuotaV2")
    public List<CheckBillQuotaResultV2> checkBillQuotaV2(@RequestParam("file") MultipartFile excelFile, HttpServletRequest request){
        return checkBillQuota.getResultV2(excelFile);
    }
}
