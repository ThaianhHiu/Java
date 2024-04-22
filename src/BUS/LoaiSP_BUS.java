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

    }