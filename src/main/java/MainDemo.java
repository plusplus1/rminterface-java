import com.github.kevinsawicki.http.HttpRequest;
import com.yqb.ice.prs.riskmanage.ZrParamsBuilder;
import com.yqb.ice.prs.riskmanage.ZrResultParser;

import java.util.HashMap;
import java.util.Map;


public class MainDemo {

    public static void main(String[] args) throws Exception {

        int appId = 0; // 填写你的app_id

        // 填写zr 公钥
        String zrPubKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1UhldW3kHNQMx0WSvkdd5BYYE\n" +
                "-----END PUBLIC KEY-----";

        // 填写 你的私钥
        String myPriKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "ycwB2pX7cFOFVPL3PR79ABnwVmp44v8GomQ4O9kg8aNbBVzZ3JeCn0taqP85ATL0\n" +
                "+MD1ct2+1I7D/t0gtqkCQGXexZj0lQj0+fqA5dpoWFJv0eZ1L0q6lWMVUhhdsx9t\n" +
                "b8F3bu++OF2sKGiTs04IqiVdsbAJS4FiFncPAMNGpjA=\n" +
                "-----END RSA PRIVATE KEY-----";


        // 构造请求参数
        ZrParamsBuilder builder = new ZrParamsBuilder(appId, zrPubKey, myPriKey); // 线上使用builder可以单例模式

        Map<String, Object> params = new HashMap<>();

        params.put("identity", "441424198***202232");
        params.put("name", "张*红");
        params.put("phone", "189***05318");

        String bizMethod = "data.repay_assess.tax";

        Map<String, Object> strParams = builder.buildParams(bizMethod, params);
        System.out.println(strParams);

        // 配置访问服务地址
        String urlBase = "http://xxx/xxx/xxx";

        // 发送请求
        String respText = HttpRequest.post(urlBase).form(strParams).body();
        System.out.println(respText);

        // 解析返回结果
        ZrResultParser parser = new ZrResultParser(appId, zrPubKey, myPriKey); // 线上parser可以采用单例模式

        ZrResultParser.ZrResult result = parser.parse(respText);
        System.out.println(result.getCode());
        System.out.println(result.getMsg());
        System.out.println(result.isSignatureOk());
        System.out.println(result.getData());


    }
}
