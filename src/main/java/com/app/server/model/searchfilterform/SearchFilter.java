package com.app.server.model.searchfilterform;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class SearchFilter {


    Pageable pageable;
    String searchString;
    List<String> genres;
    List<String> moods;
    List<String> categories;
    Integer minTempo;
    Integer maxTempo;
    Integer minLep;
    Integer maxLep;

    public SearchFilter(Pageable pageable, String searchString, List<String> genres, List<String> moods, List<String> categories, Integer minTempo, Integer maxTempo, Integer minLep, Integer maxLep) {
        this.pageable = pageable;
        this.searchString = searchString;
        this.genres = genres;
        this.moods = moods;
        this.categories = categories;
        this.minTempo = minTempo;
        this.maxTempo = maxTempo;
        this.minLep = minLep;
        this.maxLep = maxLep;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getMoods() {
        return moods;
    }

    public void setMoods(List<String> moods) {
        this.moods = moods;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getMinTempo() {
        return minTempo;
    }

    public void setMinTempo(Integer minTempo) {
        this.minTempo = minTempo;
    }

    public Integer getMaxTempo() {
        return maxTempo;
    }

    public void setMaxTempo(Integer maxTempo) {
        this.maxTempo = maxTempo;
    }

    public Integer getMinLep() {
        return minLep;
    }

    public void setMinLep(Integer minLep) {
        this.minLep = minLep;
    }

    public Integer getMaxLep() {
        return maxLep;
    }

    public void setMaxLep(Integer maxLep) {
        this.maxLep = maxLep;
    }
}
