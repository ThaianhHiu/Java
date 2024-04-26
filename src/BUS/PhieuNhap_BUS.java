package BUS;

import DAO.PhieuNhap_DAO;
import DTO.PhieuNhap_DTO;
import java.util.ArrayList;

public class PhieuNhap_BUS {

    // get all PhieuNhap
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        PhieuNhap_DAO phieuNhapDAO = new PhieuNhap_DAO();
        return phieuNhapDAO.getAllPhieuNhap();
    }

    // get PhieuNhap by MaPN
    public PhieuNhap_DTO getPhieuNhapByMaPN(String MaPN) {
        PhieuNhap_DAO phieuNhapDAO = new PhieuNhap_DAO();
        return phieuNhapDAO.getPhieuNhapByMaPN(MaPN);
    }

    // add new PhieuNhap
    public void addPhieuNhap(PhieuNhap_DTO phieuNhap) {
        PhieuNhap_DAO phieuNhapDAO = new PhieuNhap_DAO();
        phieuNhapDAO.addPhieuNhap(phieuNhap);
    }

    // tạo mã PN tự động
    public String autoGenerateMaPN() {
        PhieuNhap_DAO phieuNhapDAO = new PhieuNhap_DAO();
        return phieuNhapDAO.taoMaHDTuDong();
    }
    
}
