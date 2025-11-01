package lottery.backend;

import cn.hutool.core.img.ImgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import lottery.backend.CommonUtil;

import java.awt.color.CMMException;
import java.awt.image.BufferedImage;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {
    @Test
    public void testAvatarGen() {
        BufferedImage img = CommonUtil.generateAvatar("陈爽");
        System.out.println(ImgUtil.toBase64DataUri(img, "jpg"));
    }
}
