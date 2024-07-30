package io.knightx.simplifynftplatform.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName hold_collection
 */
@TableName(value ="hold_collection")
public class HoldCollection implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private Long collectionId;

    private Long applyRecordId;
    
    private Integer status;
    private LocalDateTime createAt;

    private Integer hideFlag;

    private Integer deleteFlag;

    private LocalDateTime deleteTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 
     */
    public Long getCollectionId() {
        return collectionId;
    }

    /**
     * 
     */
    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * 
     */
    public Long getApplyRecordId() {
        return applyRecordId;
    }

    /**
     * 
     */
    public void setApplyRecordId(Long applyRecordId) {
        this.applyRecordId = applyRecordId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    /**
     * 
     */
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    /**
     * 
     */
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * 
     */
    public Integer getHideFlag() {
        return hideFlag;
    }

    /**
     * 
     */
    public void setHideFlag(Integer hideFlag) {
        this.hideFlag = hideFlag;
    }

    /**
     * 
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 
     */
    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    /**
     * 
     */
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
        HoldCollection other = (HoldCollection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getCollectionId() == null ? other.getCollectionId() == null : this.getCollectionId().equals(other.getCollectionId()))
            && (this.getApplyRecordId() == null ? other.getApplyRecordId() == null : this.getApplyRecordId().equals(other.getApplyRecordId()))
            && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()))
            && (this.getHideFlag() == null ? other.getHideFlag() == null : this.getHideFlag().equals(other.getHideFlag()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getCollectionId() == null) ? 0 : getCollectionId().hashCode());
        result = prime * result + ((getApplyRecordId() == null) ? 0 : getApplyRecordId().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getHideFlag() == null) ? 0 : getHideFlag().hashCode());
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
                ", memberId=" + memberId +
                ", collectionId=" + collectionId +
                ", applyRecordId=" + applyRecordId +
                ", createAt=" + createAt +
                ", hideFlag=" + hideFlag +
                ", deleteFlag=" + deleteFlag +
                ", deleteTime=" + deleteTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}