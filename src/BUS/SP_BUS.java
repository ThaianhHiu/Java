package BUS;

import DAO.SP_DAO;
import DTO.SP_DTO;
import java.util.ArrayList;

import javax.swing.table.TableModel;

public class SP_BUS {
        SP_DAO SPDAO = new SP_DAO();
        public ArrayList<SP_DTO> getAllSP(){
            return SPDAO.getAllSP();
        }
        
        public ArrayList<String> getMaLoaiSP(){
            return SPDAO.getMaLoaiSP();
        }

        
        
        public String addsanpham(SP_DTO sp) { 
            if (SPDAO.addSP(sp)) 
            return "Thêm thành công"; 
            else
            return "Thêm thất bại";
        } 

        public String updatesanpham(SP_DTO sp) {
            if (SPDAO.updateSP(sp)) 
            return "Sửa thành công"; 
            else
            return "Sửa thất bại";
        }

        public String deletesanpham(SP_DTO sp) {
            if (SPDAO.deleteSP(sp)) 
            return "Xóa thành công"; 
            else
            return "Xóa thất bại";
        }

        public ArrayList<SP_DTO> searchSP(SP_DTO sp){
            return SPDAO.searchSP();
        }

        public String checkMaSP(String maSP){
            if(SPDAO.checkMaSP(maSP))
                return "Mã sản phẩm đã tồn tại";
            else
                return "Mã sản phẩm hợp lệ";
        }

        public ArrayList<SP_DTO> danhsachSP(){
            return SPDAO.danhsachSP();
        }

        // update so luong
        public String updatesoluong(SP_DTO sp) {
            if (SPDAO.updatesoluong(sp)) 
            return "Sửa thành công"; 
            else
            return "Sửa thất bại";
        }

        // tìm kiếm sản phẩm 
        public ArrayList<SP_DTO> timkiemSP(String tensp){
            return SPDAO.searchSP(tensp);
        }
    }