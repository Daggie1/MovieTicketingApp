package com.franktan.popularmovies.model;

/**
 * Created by tan on 15/09/2015.
 */
public enum MovieGroup {
    POPULAR ("Playing Today"),
    TOP_RATED ("Comming Soon"),
    FAVORITE ("Favourite");

    final String title;

    MovieGroup(String title) {
        this.title = title;
    }

    public String getTitle (){
        return this.title;
    }
}
