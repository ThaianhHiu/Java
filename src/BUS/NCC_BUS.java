package BUS;
import DAO.NCC_DAO;
import DTO.NCC_DTO;
import java.util.ArrayList;


public class NCC_BUS {

    public ArrayList<NCC_DTO> getListNCC() {
        NCC_DAO nccDAO = new NCC_DAO();
        return nccDAO.getListNCC();
    }

    public void addNCC(NCC_DTO ncc) {
        NCC_DAO nccDAO = new NCC_DAO();
        nccDAO.addNCC(ncc);
    }

    // Delete NCC
    public void deleteNCC(String MaNCC) {
        NCC_DAO nccDAO = new NCC_DAO();
        nccDAO.deleteNCC(MaNCC);
    }

    public void updateNCC(NCC_DTO ncc) {
        NCC_DAO nccDAO = new NCC_DAO();
        nccDAO.updateNCC(ncc);
    }
    
    public ArrayList<NCC_DTO> searchNCC(String TenNCC) {
        NCC_DAO nccDAO = new NCC_DAO();
        return nccDAO.searchNCC(TenNCC);
    }

    //get all mã NCC
    public ArrayList<String> getAllMaNCC() {
        NCC_DAO nccDAO = new NCC_DAO();
        return nccDAO.getAllMaNCC();
    }

    //get tên ncc
    public String getTenNCC(String MaNCC) {
        NCC_DAO nccDAO = new NCC_DAO();
        return nccDAO.getTenNCC(MaNCC);
    }
    
}
