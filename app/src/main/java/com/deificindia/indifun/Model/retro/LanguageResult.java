
package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("language_id")
    @Expose
    private Object languageId;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Object languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
