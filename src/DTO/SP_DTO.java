package DTO;

public class SP_DTO {
    private String MaSP;
    private String MaLoaiSP;
    private String TenSP;
    private float DonGia;
    private int SoLuong;
    private String HinhAnh;
    private String NgaySX;
    private String NgayHH;
    private String NoiSX;
    private int isDelete;

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getMaLoaiSP() {
        return MaLoaiSP;
    }

    public void setMaLoaiSP(String maLoaiSP) {
        MaLoaiSP = maLoaiSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float donGia) {
        DonGia = donGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getNgaySX() {
        return NgaySX;
    }

    public void setNgaySX(String ngaySX) {
        NgaySX = ngaySX;
    }

    public String getNgayHH() {
        return NgayHH;
    }

    public void setNgayHH(String ngayHH) {
        NgayHH = ngayHH;
    }

    public String getNoiSX() {
        return NoiSX;
    }

    public void setNoiSX(String noiSX) {
        NoiSX = noiSX;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "SP_DTO{" +
                "MaSP='" + MaSP + '\'' +
                ", MaLoaiSP='" + MaLoaiSP + '\'' +
                ", DonGia=" + DonGia +
                ", SoLuong=" + SoLuong +
                ", HinhAnh='" + HinhAnh + '\'' +
                ", NgaySX='" + NgaySX + '\'' +
                ", NgayHH='" + NgayHH + '\'' +
                ", NoiSX='" + NoiSX + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

    
}
