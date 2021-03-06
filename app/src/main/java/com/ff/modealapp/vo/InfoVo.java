package com.ff.modealapp.vo;

import java.util.List;

public class InfoVo<ItemVo> {

    private int totalCount;
    private String link;
    private int result;
    private String generator;
    private int pageCount;
    private String lastBuildDate;
    private List<ItemVo> item;
    private String title;
    private String description;

    public InfoVo(int totalCount, String link, int result, String generator, int pageCount, String lastBuildDate, List<ItemVo> item, String title, String description) {
        this.totalCount = totalCount;
        this.link = link;
        this.result = result;
        this.generator = generator;
        this.pageCount = pageCount;
        this.lastBuildDate = lastBuildDate;
        this.item = item;
        this.title = title;
        this.description = description;


    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public List<ItemVo> getItem() {
        return item;
    }

    public void setItem(List<ItemVo> item) {
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "InfoVo{" +
                "totalCount=" + totalCount +
                ", link='" + link + '\'' +
                ", result=" + result +
                ", generator='" + generator + '\'' +
                ", pageCount=" + pageCount +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", item=" + item +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
