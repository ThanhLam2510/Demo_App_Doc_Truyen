package com.example.app_doc_truyen.Model;

public class TLcomic {
    public int IDTL;
    public String NameTL;

    public TLcomic() {
    }

    public TLcomic(int IDTL, String nameTL) {
        this.IDTL = IDTL;
        NameTL = nameTL;
    }

    public int getIDTL() {
        return IDTL;
    }

    public void setIDTL(int IDTL) {
        this.IDTL = IDTL;
    }

    public String getNameTL() {
        return NameTL;
    }

    public void setNameTL(String nameTL) {
        NameTL = nameTL;
    }
}
