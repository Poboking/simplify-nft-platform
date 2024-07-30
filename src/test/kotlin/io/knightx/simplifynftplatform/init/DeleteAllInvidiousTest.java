package io.knightx.simplifynftplatform.init;

import io.knightx.simplifynftplatform.po.repo.impl.MemberRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/24 下午4:35
 */
@SpringBootTest
public class DeleteAllInvidiousTest {
    public final MemberRepoImpl repo;

    @Autowired
    public DeleteAllInvidiousTest(MemberRepoImpl repo) {
        this.repo = repo;
    }

    @Test
    public void Test() {
    }
    
}
