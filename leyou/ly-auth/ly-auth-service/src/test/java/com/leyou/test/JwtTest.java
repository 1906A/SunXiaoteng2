import com.leyou.common.JwtUtils;
import com.leyou.common.RsaUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "C:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtTest.class);

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5MjQwMjg4OH0.R-W8AF5r5LiG-0yyudaZlqajzkFUGSeUENkqZnf0Wh0QNcwelwkDd9yrLsjQ2Mk2BbKXN8gtPOZN1e4nTXBB4hw-JBpA0NUsUF5KO74yQwrpqgN7bHCeNmhrHl6oefEs4BZ2LynDem9FxitIh-7pZeyUI0-BHThDZ1bPI7vlDOA";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}