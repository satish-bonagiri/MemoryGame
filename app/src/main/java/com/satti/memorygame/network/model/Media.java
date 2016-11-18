
package com.satti.memorygame.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("m")
    @Expose
    private String m;

    /**
     * 
     * @return
     *     The m
     */
    public String getM() {
        return m;
    }

    /**
     * 
     * @param m
     *     The m
     */
    public void setM(String m) {
        this.m = m;
    }

}
