package BUS;

import java.util.ArrayList;
import DAO.KM_DAO;
import DTO.KM_DTO;


public class KM_BUS {
    // Get list of all promotions
    public ArrayList<KM_DTO> getListKM() {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getListKM();
    }

    // Add new promotion
    public void addKM(KM_DTO km) {
        KM_DAO kmDAO = new KM_DAO();
        kmDAO.addKM(km);
    }

    // Update promotion
    public void updateKM(KM_DTO km) {
        KM_DAO kmDAO = new KM_DAO();
        kmDAO.updateKM(km);
    }

    // Delete promotion
    public void deleteKM(String maKM) {
        KM_DAO kmDAO = new KM_DAO();
        kmDAO.deleteKM(maKM);
    }

    // Search promotion
    public ArrayList<KM_DTO> searchKM(String search) {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.searchKM(search);
    }
    
    // lấy tất cả mã khuyến mãi
    public ArrayList<String> getAllMaKM() {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getAllMaKM();
    }

    // lấy giá trị điều kiện khuyến mãi
    public float getDKKM(String maKM) {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getDKKM(maKM);
    }

    // lấy giá trị số khuyến mãi
    public float getSoKM(String maKM) {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getSoKM(maKM);
    }
    
    // lấy ngày bắt đầu khuyến mãi
    public String getNgayBDKM(String maKM) {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getNgayBD(maKM);
    }

    // lấy ngày kết thúc khuyến mãi
    public String getNgayKTKM(String maKM) {
        KM_DAO kmDAO = new KM_DAO();
        return kmDAO.getNgayKT(maKM);
    }
  
}
