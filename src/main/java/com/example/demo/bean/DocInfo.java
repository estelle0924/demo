package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class DocInfo {

    private int docId;

    /**
     * 分类Id
     */
    private int classId;

    /**
     * 分类路径
     */
    private String classPath;

    /**
     * 标题
     */
    private String subject;

    /**
     * 副标题
     */
    private String subhead;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String source;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标宽度
     */
    private int iconWidth;

    /**
     * 图标高度
     */
    private int iconHeight;

    /**
     * PC端详情
     */
    private String textPC;

    /**
     * 手机端详情
     */
    private String textMobile;

    /**
     * 状态 0=未发布;1=正常;2=删除
     */
    @JsonIgnore
    private int status;

    /**
     * 点击次数
     */
    private int hits;

    /**
     * 发布时间
     */
    private Date published;

    /**
     * 创建人
     */
    @JsonIgnore
    private long userId;

    /**
     * 是否允许删除
     */
    @JsonIgnore
    private Boolean allowRemove;

    /**
     * 是否已经被删除
     */
    @JsonIgnore
    private Boolean deleted;
    /**
     * 创建时间
     */
    private Date addTime;

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public String getTextPC() {
        return textPC;
    }

    public void setTextPC(String textPC) {
        this.textPC = textPC;
    }

    public String getTextMobile() {
        return textMobile;
    }

    public void setTextMobile(String textMobile) {
        this.textMobile = textMobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Boolean getAllowRemove() {
        return allowRemove;
    }

    public void setAllowRemove(Boolean allowRemove) {
        this.allowRemove = allowRemove;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}