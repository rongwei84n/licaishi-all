package com.auts.lcs.model.response;

import java.util.List;

public class BannerResponseModel {

    private int status;

    private String message;

    private Result result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private List<Banner> list;

        public List<Banner> getList() {
            return list;
        }

        public void setList(List<Banner> list) {
            this.list = list;
        }
    }

    public static class Banner {

        private String name;

        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
