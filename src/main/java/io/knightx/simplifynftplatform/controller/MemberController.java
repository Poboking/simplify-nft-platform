package io.knightx.simplifynftplatform.controller;

import com.github.pagehelper.PageInfo;
import io.knightx.simplifynftplatform.annotation.CheckAdminLogin;
import io.knightx.simplifynftplatform.dto.general.PageDto;
import io.knightx.simplifynftplatform.dto.member.MemberPageReqDto;
import io.knightx.simplifynftplatform.dto.member.MemberRegisterReqDto;
import io.knightx.simplifynftplatform.dto.member.MemberUpdateReqDto;
import io.knightx.simplifynftplatform.po.Member;
import io.knightx.simplifynftplatform.service.MemberService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 上午11:37
 */

@RestController
@CheckAdminLogin
@RequestMapping("/users")
public class MemberController {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(MemberController.class);
    private final MemberService member;

    @Autowired
    public MemberController(MemberService memberService) {
        this.member = memberService;
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody MemberRegisterReqDto dto) {
        return member.registerMember(dto);
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody MemberUpdateReqDto dto) {
        return member.updateMemberInfo(dto);
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam("id") Long id) {
        return member.deleteMember(id);
    }

    @GetMapping("/get")
    public Member getMember(@RequestParam("id") Long id) {
        return member.getMember(id);
    }

    @PostMapping("/page")
    public PageInfo<Member> pageMember(@RequestBody PageDto<Member, MemberPageReqDto> dto) {
        return member.getMemberPage(dto);
    }
}
