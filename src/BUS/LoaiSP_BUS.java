package BUS;

import DAO.LoaiSP_DAO;
import DTO.LoaiSP_DTO;
import java.util.ArrayList;

import javax.swing.table.TableModel;

public class LoaiSP_BUS {
        LoaiSP_DAO LoaiSPDAO = new LoaiSP_DAO();
        public ArrayList<LoaiSP_DTO> getAllSP(){
            return LoaiSPDAO.getAllLoaiSP();
        }

        public void insertLoaiSP(LoaiSP_DTO loaiSP){
            LoaiSPDAO.insertLoaiSP(loaiSP);
        }

        public void updateLoaiSP(LoaiSP_DTO loaiSP){
            LoaiSPDAO.updateLoaiSP(loaiSP);
        }

        public void deleteLoaiSP(String MaLoaiSP){
            LoaiSPDAO.deleteLoaiSP(MaLoaiSP);
        }
        
        public ArrayList<LoaiSP_DTO> searchLoaiSP( String TenLoaiSP){
            return LoaiSPDAO.searchLoaiSP(TenLoaiSP);
        }

    }