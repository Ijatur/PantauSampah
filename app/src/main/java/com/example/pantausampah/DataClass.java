package com.example.pantausampah;

public class DataClass {

    private String id;
    private String poin;
    private String nama;

    public String getId() {
        return id;
    }

    public String getPoin() {
        return poin;
    }

    public String getNama() {
        return nama;
    }


    public DataClass(String id, String poin, String nama) {
        this.id = id;
        this.poin = poin;
        this.nama = nama;
    }
}
