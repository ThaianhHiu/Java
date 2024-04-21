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

        //xuất excel
        public void exportExcel(TableModel model, String filename){
            SPDAO.ExportExcel(model, filename);
        }
        
    }