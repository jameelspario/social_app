package com.deificindia.indifun.Model;

import java.util.List;

public class CountryNamesResult {

    List<MyCountry> result;
    String message;
    int status;

    public class MyCountry{
        int id;
        String country;
        String flag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

    public List<MyCountry> getResult() {
        return result;
    }

    public void setResult(List<MyCountry> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
