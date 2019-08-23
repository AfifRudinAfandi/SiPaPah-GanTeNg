package com.sipapah.simpel;


public class RiwayatLapor {
    private String jenis;
    private String keterangan;
    private String tanggal;
    private String status;
    private String lokasi;



    public RiwayatLapor() {

    }

    public RiwayatLapor(String jenis, String keterangan, String tanggal, String status, String lokasi) {
        this.jenis = jenis;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.status = status;
        this.lokasi = lokasi;
    }


    public String getJenis() {
        return jenis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }
    public String getLokasi() {
        return lokasi;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}

