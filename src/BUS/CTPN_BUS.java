package BUS;

import java.util.ArrayList;
import DTO.CTPN_DTO;
import DAO.CTPN_DAO;

public class CTPN_BUS {
    public ArrayList<CTPN_DTO> getAllCTPN(String MaPN) {
        return new CTPN_DAO().getAllCTPN(MaPN);
    }

    public void insertCTPN(CTPN_DTO ctpn) {
        new CTPN_DAO().insertCTPN(ctpn);
    }
}
