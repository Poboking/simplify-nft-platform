package io.knightx.simplifynftplatform.controller;

import com.github.pagehelper.PageInfo;
import io.knightx.simplifynftplatform.annotation.CheckUserLogin;
import io.knightx.simplifynftplatform.dto.collection.CollectionPageReqDto;
import io.knightx.simplifynftplatform.dto.collection.CollectionUserCreateReqDto;
import io.knightx.simplifynftplatform.dto.collection.MyCollectionPageReqDto;
import io.knightx.simplifynftplatform.dto.general.PageDto;
import io.knightx.simplifynftplatform.dto.hold.MemberHoldCollectionDetailRespDto;
import io.knightx.simplifynftplatform.dto.hold.MemberHoldCollectionPageReqDto;
import io.knightx.simplifynftplatform.po.Collection;
import io.knightx.simplifynftplatform.profile.StpUserUtil;
import io.knightx.simplifynftplatform.service.CollectionService;
import io.knightx.simplifynftplatform.service.HoldCollectionService;
import io.knightx.simplifynftplatform.service.biz.ManagementTokenService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 上午11:39
 */
@RestController
@CheckUserLogin
@RequestMapping("/collections")
public class CollectionController {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CollectionController.class);
    private final CollectionService collection;
    private final HoldCollectionService holdCollection;
    private final ManagementTokenService tokenService;

    @Autowired
    public CollectionController(CollectionService collection, HoldCollectionService holdCollection, ManagementTokenService tokenService) {
        this.collection = collection;
        this.holdCollection = holdCollection;
        this.tokenService = tokenService;
    }

    @PostMapping("/create")
    public Boolean createCollection(
            @RequestBody CollectionUserCreateReqDto dto) {
        long id = StpUserUtil.getLoginIdAsLong();
        return tokenService.userCreateToken(dto.getName(), dto.getContent(), id);
    }

    @GetMapping("/soldOut")
    public Boolean soldOutCollection(
            @RequestParam("id") Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return tokenService.userSoldOutToken(id, collectionId);
    }

    @GetMapping("/get")
    public Collection getCollection(
            @RequestParam("id") Long collectionId) {
        return collection.getCollection(collectionId);
    }

    @PostMapping("/page")
    public PageInfo<Collection> getCollectionPage(
            @RequestBody PageDto<Collection, CollectionPageReqDto> dto) {
        return collection.getCollectionPage(dto);
    }

    @GetMapping("/my")
    public Collection getMyCollection(
            @RequestParam("id") Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return collection.getMyCollection(collectionId, id);
    }

    @PostMapping("/my/page")
    public PageInfo<Collection> getMyCollectionPage(
            @RequestBody PageDto<Collection, MyCollectionPageReqDto> dto) {
        long id = StpUserUtil.getLoginIdAsLong();
        return collection.getMyCollectionPage(dto, id);
    }


    @PostMapping("/member/page")
    public PageInfo<MemberHoldCollectionDetailRespDto> getMemberHoldCollectionPage(
            @RequestBody PageDto<Collection, MemberHoldCollectionPageReqDto> dto) {
        return holdCollection.getMemberHoldCollectionPage(dto);
    }


}
