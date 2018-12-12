package com.yqb.ice.prs.riskmanage;

import net.sf.json.JSONObject;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZrParamsBuilder {

    private int appId = 0;
    private PublicKey zrPublicKey = null;
    private PrivateKey myPrivateKey = null;

    public ZrParamsBuilder(int appId, String zrPublicKey, String myPrivateKey) throws Exception {
        this.appId = appId;
        this.zrPublicKey = RSAUtil.loadRSAPublicKey(zrPublicKey);
        this.myPrivateKey = RSAUtil.loadRSAPrivateKey(myPrivateKey);
    }


    public Map<String, Object> buildParams(String method, Map<String, Object> businessParams) throws Exception {


        Map<String, Object> result = new HashMap<>();
        result.put("app_id", appId);
        result.put("method", method);

        if (!businessParams.containsKey("req_id")) {
            if (method.startsWith("data.") || "yqb.black_list".equals(method)) {
                String st = String.valueOf(System.currentTimeMillis());
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String reqId = st + uuid.substring(0, 6);
                businessParams.put("req_id", reqId);
            }
        }
        String strBiz = JSONObject.fromObject(businessParams).toString();

        strBiz = RSAUtil.rsaEncrypt(strBiz, this.zrPublicKey);
        String signature = RSAUtil.signature(this.appId, strBiz, this.myPrivateKey);
        result.put("business_params", strBiz);
        result.put("signature", signature);

        return result;


    }


}
