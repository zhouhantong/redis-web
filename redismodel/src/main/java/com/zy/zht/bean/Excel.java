package com.zy.zht.bean;

import java.io.Serializable;
import java.util.Date;

public class Excel implements Serializable{
    private Integer pkid;

    private String evectionSite;

    private Integer projectType;

    private Date evectionTime;

    private Date endTime;

    private Integer evectionDate;

    private String evectionPorson;

    private String endWork;

    private String summaryIssues;

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getEvectionSite() {
        return evectionSite;
    }

    public void setEvectionSite(String evectionSite) {
        this.evectionSite = evectionSite == null ? null : evectionSite.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Date getEvectionTime() {
        return evectionTime;
    }

    public void setEvectionTime(Date evectionTime) {
        this.evectionTime = evectionTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEvectionDate() {
        return evectionDate;
    }

    public void setEvectionDate(Integer evectionDate) {
        this.evectionDate = evectionDate;
    }

    public String getEvectionPorson() {
        return evectionPorson;
    }

    public void setEvectionPorson(String evectionPorson) {
        this.evectionPorson = evectionPorson == null ? null : evectionPorson.trim();
    }

    public String getEndWork() {
        return endWork;
    }

    public void setEndWork(String endWork) {
        this.endWork = endWork == null ? null : endWork.trim();
    }

    public String getSummaryIssues() {
        return summaryIssues;
    }

    public void setSummaryIssues(String summaryIssues) {
        this.summaryIssues = summaryIssues == null ? null : summaryIssues.trim();
    }
}