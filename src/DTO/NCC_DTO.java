package DTO;

public class NCC_DTO {
    private String MaNCC;
    private String TenNCC;
    private String DiaChi;
    private String SDT;
    private int isDelete;

    //  Getters and Setters
    public String getMaNCC() {
        return MaNCC;
    }
    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    
}
