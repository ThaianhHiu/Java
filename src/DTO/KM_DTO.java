package DTO;

public class KM_DTO {
    private String MaKM;
    private String TenKM;
    private float DKKM;
    private float SoKM;
    private String NgayBD;
    private String NgayKT;
    private int isDelete;

    
    public String getMaKM() {
        return MaKM;
    }
    public void setMaKM(String maKM) {
        MaKM = maKM;
    }
    public String getTenKM() {
        return TenKM;
    }
    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }
    public float getDKKM() {
        return DKKM;
    }
    public void setDKKM(float dKKM) {
        DKKM = dKKM;
    }
    public float getSoKM() {
        return SoKM;
    }
    public void setSoKM(float soKM) {
        SoKM = soKM;
    }
    public String getNgayBD() {
        return NgayBD;
    }
    public void setNgayBD(String ngayBD) {
        NgayBD = ngayBD;
    }
    public String getNgayKT() {
        return NgayKT;
    }
    public void setNgayKT(String ngayKT) {
        NgayKT = ngayKT;
    }
    public int getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }


}
