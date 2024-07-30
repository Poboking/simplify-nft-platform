package io.knightx.simplifynftplatform.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 申请记录, 仅在申请成功做记录
 *
 * @TableName apply_record
 */
@TableName(value = "apply_record")
public class ApplyRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long collectionId;
    private Long fromMemberId;
    private Long applyMemberId;
    private LocalDateTime createAt;
    private String transactionHash;
    private Integer status;
    private Integer deleteFlag;
    private LocalDateTime deleteTime;
    private Integer type;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getCollectionId() {
        return collectionId;
    }


    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }


    public Long getFromMemberId() {
        return fromMemberId;
    }
    
    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTransactionHash() {
        return this.transactionHash;
    }

    public Long getApplyMemberId() {
        return this.applyMemberId;
    }

    public void setApplyMemberId(Long applyMemberId) {
        this.applyMemberId = applyMemberId;
    }
    
    public LocalDateTime getCreateAt() {
        return createAt;
    }


    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    
    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getDeleteFlag() {
        return deleteFlag;
    }


    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }


    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }


    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApplyRecord other = (ApplyRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCollectionId() == null ? other.getCollectionId() == null : this.getCollectionId().equals(other.getCollectionId()))
                && (this.getApplyMemberId() == null ? other.getApplyMemberId() == null : this.getApplyMemberId().equals(other.getApplyMemberId()))
                && (this.getFromMemberId() == null ? other.getFromMemberId() == null : this.getFromMemberId().equals(other.getFromMemberId()))
                && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
                && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCollectionId() == null) ? 0 : getCollectionId().hashCode());
        result = prime * result + ((getApplyMemberId() == null) ? 0 : getApplyMemberId().hashCode());
        result = prime * result + ((getFromMemberId() == null) ? 0 : getFromMemberId().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", collectionId=" + collectionId +
                ", applyMemberId=" + applyMemberId +
                ", fromMemberId=" + fromMemberId +
                ", createAt=" + createAt +
                ", status=" + status +
                ", type=" + type +
                ", deleteFlag=" + deleteFlag +
                ", deleteTime=" + deleteTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}