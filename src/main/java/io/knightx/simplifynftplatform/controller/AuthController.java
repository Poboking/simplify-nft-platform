package io.knightx.simplifynftplatform.controller;

import io.knightx.simplifynftplatform.dto.auth.LoginReqDto;
import io.knightx.simplifynftplatform.dto.auth.TokenInfo;
import io.knightx.simplifynftplatform.dto.manager.ManagerRegisterReqDto;
import io.knightx.simplifynftplatform.dto.member.MemberRegisterReqDto;
import io.knightx.simplifynftplatform.profile.StpAdminUtil;
import io.knightx.simplifynftplatform.profile.StpUserUtil;
import io.knightx.simplifynftplatform.service.AuthService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 上午11:37
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginReqDto dto) {
        return service.login(dto);
    }

    @GetMapping("/logout")
    public Boolean logout() {
        if (StpUserUtil.isLogin()) {
            StpUserUtil.logout();
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/register")
    public TokenInfo register(@RequestBody MemberRegisterReqDto dto) {
        service.register(dto);
        return service.login(new LoginReqDto(dto.getMemberName(), dto.getPassword()));
    }

    @PostMapping("/back/login")
    public TokenInfo managerLogin(@RequestBody LoginReqDto dto) {
        return service.managerLogin(dto);
    }

    @PostMapping("/back/register")
    public TokenInfo managerRegister(@RequestBody ManagerRegisterReqDto dto) {
        service.managerRegister(dto);
        return service.managerLogin(new LoginReqDto(dto.getManagerName(), dto.getPassword()));
    }

    @GetMapping("/back/logout")
    public Boolean managerLogout() {
        if (StpAdminUtil.isLogin()) {
            StpAdminUtil.logout();
            return true;
        } else {
            return false;
        }
    }
}
