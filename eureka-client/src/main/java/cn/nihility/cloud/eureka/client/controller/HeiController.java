package cn.nihility.cloud.eureka.client.controller;

import cn.nihility.cloud.common.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/hei")
public class HeiController {

    private static final Logger log = LoggerFactory.getLogger(HeiController.class);

    @GetMapping("/echo/{msg}")
    public CommonResult hei(@PathVariable("msg") String msg, HttpServletRequest request) {
        log.info("receive echo msg [{}]", msg);
        Random random = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(random.nextInt(10) * 1000);
        } catch (InterruptedException e) {
            // nothing
        }

        parseRequest(request);

        return CommonResult.success(msg, "success");
    }

    private void parseRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String hd = headerNames.nextElement();
            log.info("[{}] : [{}]", hd, enumerationToString(request.getHeaders(hd)));
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (null != parameterMap && parameterMap.size() > 0) {
            parameterMap.forEach((k, v) -> log.info("param [{}] : [{}]", k, arrayToString(v)) );
        }
    }

    public String arrayToString(Object[] array) {
        StringJoiner joiner = new StringJoiner(",");
        if (null != array) {
            for (Object obj : array) {
                joiner.add(Objects.toString(obj, "null"));
            }
        }
        return joiner.toString();
    }

    public String enumerationToString(Enumeration<String> headers) {
        StringJoiner joiner = new StringJoiner(",");
        while (headers.hasMoreElements()) {
            joiner.add(headers.nextElement());
        }
        return joiner.toString();
    }

}
