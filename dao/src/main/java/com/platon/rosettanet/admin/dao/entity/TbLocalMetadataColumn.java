package com.platon.rosettanet.admin.dao.entity;

import java.time.LocalDateTime;

public class TbLocalMetadataColumn {
    private Integer id;

    private String metadataId;

    private Integer cindex;

    private String cname;

    private String ctype;

    private Long csize;

    private String ccomment;

    private String visible;

    private LocalDateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public Integer getCindex() {
        return cindex;
    }

    public void setCindex(Integer cindex) {
        this.cindex = cindex;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Long getCsize() {
        return csize;
    }

    public void setCsize(Long csize) {
        this.csize = csize;
    }

    public String getCcomment() {
        return ccomment;
    }

    public void setCcomment(String ccomment) {
        this.ccomment = ccomment;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}