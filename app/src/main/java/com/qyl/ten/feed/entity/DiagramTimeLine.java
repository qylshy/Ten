package com.qyl.ten.feed.entity;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by qiuyunlong on 16/4/14.
 */
@JsonObject
public class DiagramTimeLine {


    @JsonField(name = "status")
    public int status;

    @JsonField(name = "errMsg")
    public String errMsg;

    @JsonField(name = "result")
    public List<DiagramItemEntity> result;

    @Override
    public String toString() {
        return "DiagramTimeLine{" +
                "status=" + status +
                ", errMsg='" + errMsg + '\'' +
                ", result=" + result +
                '}';
    }

    @JsonObject
    public static class DiagramItemEntity {
        @JsonField(name = "id")
        public int id;
        @JsonField(name = "type")
        public int type;
        @JsonField(name = "publishtime")
        public long publishtime;
        @JsonField(name = "title")
        public String title;
        @JsonField(name = "summary")
        public String summary;
        @JsonField(name = "image")
        public String image;

        @Override
        public String toString() {
            return "DiagramItemEntity{" +
                    "id=" + id +
                    ", type=" + type +
                    ", publishtime=" + publishtime +
                    ", title='" + title + '\'' +
                    ", summary='" + summary + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }


}
