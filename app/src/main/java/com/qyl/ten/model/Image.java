package com.qyl.ten.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by qiuyunlong on 16/4/1.
 * {
 "id": 10473,
 "title": "自己喜欢的方式度过一生",
 "author": "Emma Block",
 "authorbrief": "来自英国插画师Emma Block",
 "text1": "“我爱你，从你帮我接到帽子的那一刻开始。”

 “我也爱你，从风将你送到我身边的那一刻开始。”",
 "image1": "images/30510C73EFDAD1ED17D7C7AF4DE705D7.jpg",
 "text2": "—宫崎骏《起风了》",
 "times": 6529,
 "publishtime": 635949792000000000,
 "status": 0,
 "errMsg": null
 }
 */
public class Image implements Parcelable{
    private long id;
    private String title;
    private String author;
    private String authorBrief;
    private String text1;
    private String image1;
    private String text2;
    private long times;
    private long publishTime;
    private int status;
    private String errMsg;


    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public Image() {

    }

    private Image(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.author = in.readString();
        this.authorBrief = in.readString();
        this.text1 = in.readString();
        this.image1 = in.readString();
        this.text2 = in.readString();
        this.times = in.readLong();
        this.publishTime = in.readLong();
        this.status = in.readInt();
        this.errMsg = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorBrief() {
        return authorBrief;
    }

    public void setAuthorBrief(String authorBrief) {
        this.authorBrief = authorBrief;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorBrief='" + authorBrief + '\'' +
                ", text1='" + text1 + '\'' +
                ", image1='" + image1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", times=" + times +
                ", publishTime=" + publishTime +
                ", status=" + status +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.authorBrief);
        dest.writeString(this.text1);
        dest.writeString(this.image1);
        dest.writeString(this.text2);
        dest.writeLong(this.times);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.status);
        dest.writeString(this.errMsg);
    }

    public static Image valueOf(Pojo pojo){
        Image image = new Image();
        image.id = pojo.id;
        image.title = pojo.title;
        image.author = pojo.author;
        image.authorBrief = pojo.authorbrief;
        image.text1 = pojo.text1;
        image.image1 = pojo.image1;
        image.text2 = pojo.text2;
        image.times = pojo.times;
        image.publishTime = pojo.publishtime;
        image.status = pojo.status;
        image.errMsg = pojo.errMsg;
        return image;
    }

    @JsonObject
    public static class Pojo {
        @JsonField
        public long id;
        @JsonField
        public String title;
        @JsonField
        public String author;
        @JsonField
        public String authorbrief;
        @JsonField
        public String text1;
        @JsonField
        public String image1;
        @JsonField
        public String text2;
        @JsonField
        public long times;
        @JsonField
        public long publishtime;
        @JsonField
        public int status;
        @JsonField
        public String errMsg;
    }
}
