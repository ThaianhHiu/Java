package BUS;
import DAO.HoaDon_DAO;
import DTO.HoaDon_DTO;
import java.util.ArrayList;


public class HoaDon_BUS {
    // get all HoaDon
    public ArrayList<HoaDon_DTO> getAllHoaDon() {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        return hoaDonDAO.getAllHoaDon();
    }
    
    // insert HoaDon
    public void insertHoaDon(HoaDon_DTO hoaDon) {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        hoaDonDAO.insertHoaDon(hoaDon);
    }

    // tìm hóa đơn theo mã nhân viên 
    public ArrayList<HoaDon_DTO> searchHoaDonByMaNV(String maNV) {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        return hoaDonDAO.searchHoaDonByMaNV(maNV);
    }

    // mã hóa đơn tự động 
    public String autoGenerateID() {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        return hoaDonDAO.taoMaHDTuDong();
    }

}
