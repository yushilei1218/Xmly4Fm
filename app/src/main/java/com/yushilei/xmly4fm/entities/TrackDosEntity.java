package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/25.
 */
public class TrackDosEntity {
    private int numFound;
    private int start;
    private String sq;
    private String spellcheckerNumFound;
    private List<TrackEntity> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }

    public String getSpellcheckerNumFound() {
        return spellcheckerNumFound;
    }

    public void setSpellcheckerNumFound(String spellcheckerNumFound) {
        this.spellcheckerNumFound = spellcheckerNumFound;
    }

    public List<TrackEntity> getDocs() {
        return docs;
    }

    public void setDocs(List<TrackEntity> docs) {
        this.docs = docs;
    }
}
