package DTO;

public class HoaDon_DTO {
    private String MaHD;
    private String MaNV;
    private String MaKH;
    private String MaKM;
    private String NgayLap;
    private String GioLap;
    private Float tongTien;

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String maKM) {
        MaKM = maKM;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getGioLap() {
        return GioLap;
    }

    public void setGioLap(String gioLap) {
        GioLap = gioLap;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float tongTien) {
        this.tongTien = tongTien;
    }

    public String toString() {
        return MaHD + " " + MaNV + " " + MaKH + " " + MaKM + " " + NgayLap + " " + GioLap + " " + tongTien;
    }

}
