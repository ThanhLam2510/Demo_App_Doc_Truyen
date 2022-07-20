package com.example.app_doc_truyen.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comic implements Serializable {
    public int IDcomic;
    public int IDTL;
    public String NameCM;
    public String Content;
    public String ImgCM;
    public String Author;
    public String Favourite;
    public String DateUp;
    public Comic() {
    }

    public Comic(int IDcomic, int IDTL, String nameCM, String content, String imgCM, String author, String favourite, String dateUp) {
        this.IDcomic = IDcomic;
        this.IDTL = IDTL;
        NameCM = nameCM;
        Content = content;
        ImgCM = imgCM;
        Author = author;
        Favourite = favourite;
        DateUp = dateUp;
    }

    public int getIDcomic() {
        return IDcomic;
    }

    public void setIDcomic(int IDcomic) {
        this.IDcomic = IDcomic;
    }

    public int getIDTL() {
        return IDTL;
    }

    public void setIDTL(int IDTL) {
        this.IDTL = IDTL;
    }

    public String getNameCM() {
        return NameCM;
    }

    public void setNameCM(String nameCM) {
        NameCM = nameCM;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImgCM() {
        return ImgCM;
    }

    public void setImgCM(String imgCM) {
        ImgCM = imgCM;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }

    public String getDateUp() {
        return DateUp;
    }

    public void setDateUp(String dateUp) {
        DateUp = dateUp;
    }
}
