package com.wosloveslife.takemeizi.bean;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public class MeizhiData {
    public String error;
    public ArrayList<Meizi> results;

    public String getError() {
        return error;
    }
    public ArrayList<Meizi> getResults() {
        return results;
    }

    public interface APiMeizi {
        @GET("/api/data/福利/10/{page}")
        Observable<MeizhiData> getMeizhiData(@Path("page") int page);
    }

    public class Meizi {
        private String _id;
        private String createdAt;
        private String desc;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
