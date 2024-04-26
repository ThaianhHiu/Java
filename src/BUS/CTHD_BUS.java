package BUS;

import DAO.CTHD_DAO;
import DTO.CTHD_DTO;
import java.util.ArrayList;

public class CTHD_BUS {
    
    public ArrayList<CTHD_DTO> getAllCTHD(String MaHD) {
        return new CTHD_DAO().getAllCTHD(MaHD);
    }

    public void insertCTHD(CTHD_DTO cthd) {
        new CTHD_DAO().addCTHD(cthd);
    }

}
