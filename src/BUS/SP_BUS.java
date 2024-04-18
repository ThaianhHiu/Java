package BUS;

import DAO.SP_DAO;
import DTO.SP_DTO;
import java.util.ArrayList;

public class SP_BUS {
        SP_DAO SPDAO = new SP_DAO();
        public ArrayList<SP_DTO> getAllSP(){
        return SPDAO.getAllSP();
        }
}
