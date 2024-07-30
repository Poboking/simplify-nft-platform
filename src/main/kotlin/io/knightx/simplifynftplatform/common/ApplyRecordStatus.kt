package io.knightx.simplifynftplatform.common

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 上午11:55
 */
enum class ApplyRecordStatus(val value: Int, val respName: String) {
    APPLYING(1, "申请中"),
    APPROVED(2, "申请成功"),
    REJECTED(3, "已拒绝"),
    CANCEL(4, "已取消");

    companion object {
        fun from(status: Int): ApplyRecordStatus {
            if (status == 1)
                return APPLYING
            if (status == 2)
                return APPROVED
            if (status == 3)
                return REJECTED
            if (status == 4)
                return CANCEL
            return APPLYING
        }
    }
}