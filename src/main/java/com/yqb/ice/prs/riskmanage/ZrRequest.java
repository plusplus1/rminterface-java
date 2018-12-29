package com.yqb.ice.prs.riskmanage;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.Map;

public class ZrRequest {


    private int appId = 0; // 填写你的app_id

    // 配置访问服务地址
    private String urlBase = "http://xxx/xxx/xxx";

    // 填写zr 公钥
    private String zrPubKey = "";

    // 填写 你的私钥
    private String myPriKey = "";

    private ZrResultParser parser = null;

    public ZrRequest(int appId, String url, String zrPubKey, String myPriKey) throws Exception {
        this.appId = appId;
        this.urlBase = url;
        this.zrPubKey = zrPubKey;
        this.myPriKey = myPriKey;
        this.parser = new ZrResultParser(this.appId, this.zrPubKey, this.myPriKey);
    }

    public ZrResultParser.ZrResult run(String bizMethod, Map<String, Object> params) throws Exception {

        // 构造请求参数
        ZrParamsBuilder builder = new ZrParamsBuilder(appId, zrPubKey, myPriKey); // 线上使用builder可以单例模式
        Map<String, Object> formParams = builder.buildParams(bizMethod, params);

        // 发送请求
        String respText = HttpRequest.post(urlBase).form(formParams).body();

        // 解析返回结果
        return this.parser.parse(respText);

    }

}
