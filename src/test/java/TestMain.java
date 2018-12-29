import com.yqb.ice.prs.riskmanage.ZrRequest;
import com.yqb.ice.prs.riskmanage.ZrResultParser;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class TestMain {

    @Test
    public void testRun() throws Exception {

        int appId = 0; // 填写你的app_id

        // 配置访问服务地址
        String urlBase = "http://xxx/xxx/xxx";

        // 填写zr 公钥
        String zrPubKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAmwnK3DGBT+35cG7/xf8qDoar\n" +
                "NIMnSNjr+TM/Q+b/4W0JfkUVGgcOu9pVqruUMu/o31jt6SMfFhhjzTVOosuTzCRQ\n" +
                "qztb1QL4vQ41+f3q6hoCHSoAEwQDZ+KAr3SlYV4y263CNaIjff0oBUQt/UP6wnhz\n" +
                "YGNVfRgzgo+bPn7ojQIDAQAB\n" +
                "-----END PUBLIC KEY-----";


        // 填写 你的私钥
        String myPriKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIICWwIBAAKBgQCAmwnK3DGBT+35cG7/xf8qDoarNIMnSNjr+TM/Q+b/4W0JfkUV\n" +
                "GgcOu9pVqruUMu/o31jt6SMfFhhjzTVOosuTzCRQqztb1QL4vQ41+f3q6hoCHSoA\n" +
                "EwQDZ+KAr3SlYV4y263CNaIjff0oBUQt/UP6wnhzYGNVfRgzgo+bPn7ojQIDAQAB\n" +
                "AoGABekaNhyQmeTWhxRHxbGP3Jr2TYAIVxHeBlpZpJq2TyRJIpqPCj07/c1cZ3uF\n" +
                "S3Zb2FQLfkOsvY0CafPQc2DAAmXRqfti6RlWzQoI5ZWd6mVAD6XQreuIG/5J3k/w\n" +
                "i/hsbvVQM8bbArw3yUr6R5O+xZLHL/ON+0g96C01QVk2oAECQQCQ81JPpJYLqh/Q\n" +
                "KepZyrc70L6+0+h3WS8OWT3w1QRi/RIqRjPTGjXwXvB1p2hql3QW1ESUsj9ZFjGw\n" +
                "EixcHB4BAkEA4yINOjIZZhVUmssrdGLMD09ObZt4JIENmfkPmE2z8yA+zVqLZ7vP\n" +
                "BWoSm+NE5BFi127qOlvaHcmJNPXoC4ZijQJAQ6TJrNEqFJd38TUeYHjxucBYw8Uw\n" +
                "n0HHa/LJrTI7sdZYO9I3LTT2Jw6nOMC/Qb8cNespdhsY5QIuffR9Lx8QAQJARo08\n" +
                "OJh5RTlY92Amlldd9V94CBnjvssLc4lmq7NvMxUQDO+UH9u5mX/yWdPFgWZZInwE\n" +
                "a64+WURRx06fDn1mvQJAPZpHCacU7eWh1HP1Xb2+GJNeKasuqsH+w5TR7gzIO32Q\n" +
                "vy4pWMM6OEBU30O1+o7nt2LBrAXZVwBgcNwuzpnhNA==\n" +
                "-----END RSA PRIVATE KEY-----";


        ZrRequest req = null;


        try {
            Properties config = new Properties();
            config.load(new FileInputStream("interface.properties"));
            urlBase = config.getProperty("url");
            appId = Integer.valueOf(config.getProperty("appId"));
        } catch (Exception e) {
            // skip
        }

        System.out.println(urlBase);
        System.out.println(appId);


        req = new ZrRequest(appId, urlBase, zrPubKey, myPriKey);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("identity", "441424198***202232");
        params.put("name", "张*红");
        params.put("phone", "189***05318");

        String bizMethod = "data.repay_assess.tax";

        ZrResultParser.ZrResult result = req.run(bizMethod, params);

        System.out.println(result.getCode());
        System.out.println(result.getMsg());
        System.out.println(result.isSignatureOk());
        System.out.println(result.getData());

    }

}
