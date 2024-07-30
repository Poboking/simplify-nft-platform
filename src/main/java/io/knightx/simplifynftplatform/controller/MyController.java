package io.knightx.simplifynftplatform.controller;

import com.github.pagehelper.PageInfo;
import io.knightx.simplifynftplatform.annotation.CheckUserLogin;
import io.knightx.simplifynftplatform.common.ApplyRecordStatus;
import io.knightx.simplifynftplatform.dto.apply.ApplyRecordRespDto;
import io.knightx.simplifynftplatform.dto.general.PageDto;
import io.knightx.simplifynftplatform.dto.hold.MyHoldCollectionDetailRespDto;
import io.knightx.simplifynftplatform.dto.hold.MyHoldCollectionPageReqDto;
import io.knightx.simplifynftplatform.dto.member.MemberInfoRespDto;
import io.knightx.simplifynftplatform.dto.member.MemberUpdateReqDto;
import io.knightx.simplifynftplatform.dto.record.MyApplyRecordPageReqDto;
import io.knightx.simplifynftplatform.dto.record.MyBeRequestRecordPageReqDto;
import io.knightx.simplifynftplatform.po.ApplyRecord;
import io.knightx.simplifynftplatform.po.HoldCollection;
import io.knightx.simplifynftplatform.profile.StpUserUtil;
import io.knightx.simplifynftplatform.service.HoldCollectionService;
import io.knightx.simplifynftplatform.service.MemberService;
import io.knightx.simplifynftplatform.service.OperationLogService;
import io.knightx.simplifynftplatform.service.biz.ManagementApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用于管理个人信息\个人藏品\申请管理\创建藏品管理
 *
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午7:17
 */
@RestController
@CheckUserLogin
@RequestMapping("/my")
public class MyController {
    private final MemberService member;
    private final HoldCollectionService holdCollection;
    private final ManagementApplyService managementApply;
    private final OperationLogService operationLog;

    @Autowired
    public MyController(MemberService member, HoldCollectionService holdCollection, ManagementApplyService managementApply, OperationLogService operationLog) {
        this.member = member;
        this.holdCollection = holdCollection;
        this.managementApply = managementApply;
        this.operationLog = operationLog;
    }

    @GetMapping("/info")
    public MemberInfoRespDto getMyInfo() {
        long id = StpUserUtil.getLoginIdAsLong();
        return member.getMyInfoById(id);
    }

    @PostMapping("/info/update")
    public boolean updateMyInfo(
            @RequestBody MemberUpdateReqDto dto
    ) {
        return member.updateMemberInfo(dto);
    }

    @GetMapping("/hold")
    public MyHoldCollectionDetailRespDto getMyHoldCollection(
            @RequestParam("id") long holdCollectionId
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return holdCollection.getMyHoldCollection(holdCollectionId, id);
    }

    @PostMapping("/hold/page")
    public PageInfo<MyHoldCollectionDetailRespDto> getMyHoldCollectionPage(
            @RequestBody PageDto<HoldCollection, MyHoldCollectionPageReqDto> dto
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return holdCollection.getMyHoldCollectionPage(dto, id);
    }

    @PostMapping("/beRequest/page")
    public PageInfo<ApplyRecordRespDto> getMyBeRequestedCollectionPage(
            @RequestBody PageDto<ApplyRecord, MyBeRequestRecordPageReqDto> dto
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyBeRequestedRecordPage(dto, id);
    }

    @GetMapping("/apples/applying")
    public List<ApplyRecordRespDto> getMyApplyingCollection(
            @RequestParam(value = "id", required = false) Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyApplyRecord(collectionId, id, ApplyRecordStatus.APPLYING.getValue());
    }

    @GetMapping("/apples/applied")
    public List<ApplyRecordRespDto> getMyAppliedCollection(
            @RequestParam(value = "id", required = false) Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyApplyRecord(collectionId, id, ApplyRecordStatus.APPROVED.getValue());
    }

    @GetMapping("/apples/rejected")
    public List<ApplyRecordRespDto> getMyRejectedCollection(
            @RequestParam(value = "id", required = false) Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyApplyRecord(collectionId, id, ApplyRecordStatus.REJECTED.getValue());
    }

    @GetMapping("/apples/cancel")
    public List<ApplyRecordRespDto> getMyCancelCollection(
            @RequestParam(value = "id", required = false) Long collectionId) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyApplyRecord(collectionId, id, ApplyRecordStatus.CANCEL.getValue());
    }

    @PostMapping("/apply/page")
    public PageInfo<ApplyRecordRespDto> getMyApplyCollectionPage(
            @RequestBody PageDto<ApplyRecord, MyApplyRecordPageReqDto> dto
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return operationLog.getMyApplyRecordPage(dto, id);
    }

    @GetMapping("/apply/confirm")
    public boolean confirmApply(
            @RequestParam("id") Long applyRecordId
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return managementApply.userConfirmApply(applyRecordId, id);
    }

    @GetMapping("/apply/cancel")
    public boolean cancelApply(
            @RequestParam("id") Long applyRecordId
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return managementApply.userCancelApply(applyRecordId, id);
    }

    @GetMapping("/apply/rejected")
    public boolean rejectedApply(
            @RequestParam("id") Long applyRecordId
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return managementApply.userRejectedApply(applyRecordId, id);
    }

    @GetMapping("/apply/put")
    public boolean putForwardApply(
            @RequestParam("holdCollectionId") Long holdCollectionId
    ) {
        long id = StpUserUtil.getLoginIdAsLong();
        return managementApply.userPutForwardApply(holdCollectionId, id);
    }
}
