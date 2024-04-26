package DTO;

public class NV_DTO {
    private String MaNV;
    private String TenNV;
    private String DiaChi;
    private String SDT;
    private String NgaySinh;
    private int TrangThai;

    public String getMaNV() {
        return MaNV;
    }
    public void setMaNV(String maNV) {
        MaNV = maNV;
    }
    public String getTenNV() {
        return TenNV;
    }
    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }
    public String getNgaySinh() {
        return NgaySinh;
    }
    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }
    public String getDiaChi() {
        return DiaChi;
    }
    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
    public String getSDT() {
        return SDT;
    }
    public void setSDT(String sDT) {
        SDT = sDT;
    }

    public int getTrangThai() {
        return TrangThai;
    }
    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

}
