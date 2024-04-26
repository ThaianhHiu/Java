package BUS;
import DAO.KH_DAO;
import DTO.KH_DTO;
import java.util.ArrayList;


public class KH_BUS {
     KH_DAO khDAO = new KH_DAO();

    public ArrayList<KH_DTO> getListKH() {
        return khDAO.getListKH();
    }

    public void addKH(KH_DTO kh) {
        khDAO.addKH(kh);
    }

    public void deleteKH(String MaKH) {
        khDAO.deleteKH(MaKH);
    }

    public void updateKH(KH_DTO kh) {
        khDAO.updateKH(kh);
    }

    public ArrayList<KH_DTO> searchKH(String search) {
        return khDAO.searchKH(search);
    }

    // lấy tất cả mã khách hàng
    public ArrayList<String> getAllMaKH() {
        return khDAO.getAllMaKH();
    }

    // get ten khach hang theo ma khach hang
    public String getTenKH(String MaKH) {
        return khDAO.getTenKH(MaKH);
    }
    
}
