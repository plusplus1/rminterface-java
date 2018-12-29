package com.yqb.ice.prs.riskmanage;

import net.sf.json.JSONObject;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ZrResultParser {

    public static class ZrResult {

        private int code;
        private String msg;
        private boolean signatureOk;
        private Object data;


        private ZrResult() {
        }


        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }

        public boolean isSignatureOk() {
            return signatureOk;
        }

        public Object getData() {
            return data;
        }
    }


    private int appId;
    private PublicKey zrPubKey;
    private PrivateKey myPriKey;

    public ZrResultParser(int appId, String zrPublicKey, String myPrivateKey) throws Exception {
        this.appId = appId;
        this.zrPubKey = RSAUtil.loadRSAPublicKey(zrPublicKey);
        this.myPriKey = RSAUtil.loadRSAPrivateKey(myPrivateKey);
    }


    public ZrResult parse(String respText) throws Exception {

        ZrResult result = new ZrResult();
        JSONObject obj = JSONObject.fromObject(respText);
        result.code = obj.getInt("code");
        result.msg = obj.getString("msg");
        result.signatureOk = true;

        if (result.code == 200) {
            if (obj.has("result_dict")) {
                JSONObject resultDict = obj.getJSONObject("result_dict");
                if (resultDict != null && resultDict.has("signature")) {
                    String data = resultDict.getString("data");
                    result.signatureOk = RSAUtil.verify(this.appId, data, resultDict.getString("signature"), this.zrPubKey);
                    if (result.signatureOk) {
                        result.data = JSONObject.fromObject(RSAUtil.rsaDecrypt(data, this.myPriKey));
                    }
                } else {
                    result.data = resultDict;
                }
            }
        }

        return result;
    }

}
