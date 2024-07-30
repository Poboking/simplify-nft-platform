package io.knightx.simplifynftplatform;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午1:34
 */
@SpringBootTest
public class JavaSl4jLoggerTest {
    private final Logger log = LoggerFactory.getLogger(JavaSl4jLoggerTest.class);
    
    @Test
    public void Test() {
        log.warn("test");
    }
}
