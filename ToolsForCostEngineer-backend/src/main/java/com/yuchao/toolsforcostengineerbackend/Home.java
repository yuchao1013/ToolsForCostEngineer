package com.yuchao.toolsforcostengineerbackend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName Home
 * @Description TODO
 * @Author YuChao
 * @Date 2023/7/13 22:23
 * @Version 1.0
 */
@Controller
public class Home {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
