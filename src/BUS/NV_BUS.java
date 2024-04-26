package BUS;

import java.util.ArrayList;
import DAO.NV_DAO;
import DTO.NV_DTO;


public class NV_BUS {
     // Get list of employees
    public ArrayList<NV_DTO> getListNV() {
        NV_DAO nvDAO = new NV_DAO();
        return nvDAO.getListNV();
    }

    // Add an employee
    public void addNV(NV_DTO nv) {
        NV_DAO nvDAO = new NV_DAO();
        nvDAO.addNV(nv);
    }

    // Update an employee
    public void updateNV(NV_DTO nv) {
        NV_DAO nvDAO = new NV_DAO();
        nvDAO.updateNV(nv);
    }

    // Delete an employee

    public void deleteNV(String MaNV) {
        NV_DAO nvDAO = new NV_DAO();
        nvDAO.deleteNV(MaNV);
    }
    
    // Search employee by name
    public ArrayList<NV_DTO> searchNV(String TenNV) {
        NV_DAO nvDAO = new NV_DAO();
        return nvDAO.searchNV(TenNV);
    }

    // lấy tất cả mã nhân viên
    public ArrayList<String> getAllMaNV() {
        NV_DAO nvDAO = new NV_DAO();
        return nvDAO.getAllMaNV();
    }

    // get tên nhân viên 
    public String getTenNV(String MaNV) {
        NV_DAO nvDAO = new NV_DAO();
        return nvDAO.getTenNV(MaNV);
    }
}
