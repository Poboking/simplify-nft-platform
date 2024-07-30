package io.knightx.simplifynftplatform.po.repo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.knightx.simplifynftplatform.po.Member;
import io.knightx.simplifynftplatform.po.mapper.MemberMapper;
import io.knightx.simplifynftplatform.po.repo.MemberRepo;
import org.springframework.stereotype.Service;

/**
* @author NaGaYa
* @description 针对表【member】的数据库操作Service实现
* @createDate 2024-07-11 14:40:40
*/
@Service
public class MemberRepoImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberRepo {

}




