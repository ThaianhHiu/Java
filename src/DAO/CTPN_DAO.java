package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.CTPN_DTO;


public class CTPN_DAO {
    private Connection con;

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=quanlicuahangbanbanh;trustServerCertificate=true";
            String user = "sa";
            String pass = "sa";
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        return con;
    }

    public void closeConnection() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    //get tất cả chi tiết phiếu nhập theo mã phiếu nhập
    public ArrayList<CTPN_DTO> getAllCTPN(String MaPN) {
        ArrayList<CTPN_DTO> result = new ArrayList<CTPN_DTO>();
        String sql = "SELECT * FROM CTPN WHERE MaPN = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaPN);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CTPN_DTO ctpn = new CTPN_DTO();
                ctpn.setMaPN(rs.getString("MaPN"));
                ctpn.setMaSP(rs.getString("MaSP"));
                ctpn.setSoLuong(rs.getInt("SoLuong"));
                ctpn.setDonGia(rs.getFloat("DonGia"));
                result.add(ctpn);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    //thêm chi tiết phiếu nhập
    public boolean insertCTPN(CTPN_DTO ctpn) {
        String sql = "INSERT INTO CTPN(MaPN, MaSP, SoLuong, DonGia) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, ctpn.getMaPN());
            ps.setString(2, ctpn.getMaSP());
            ps.setInt(3, ctpn.getSoLuong());
            ps.setFloat(4, ctpn.getDonGia());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    //cập nhật chi tiết phiếu nhập
    public boolean updateCTPN(CTPN_DTO ctpn) {
        String sql = "UPDATE CTPN SET SoLuong = ?, DonGia = ? WHERE MaPN = ? AND MaSP = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, ctpn.getSoLuong());
            ps.setFloat(2, ctpn.getDonGia());
            ps.setString(3, ctpn.getMaPN());
            ps.setString(4, ctpn.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    //xóa chi tiết phiếu nhập
    public boolean deleteCTPN(String MaPN, String MaSP) {
        String sql = "DELETE FROM CTPN WHERE MaPN = ? AND MaSP = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaPN);
            ps.setString(2, MaSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    //lấy chi tiết phiếu nhập theo mã phiếu nhập và mã sản phẩm
    public CTPN_DTO getCTPN(String MaPN, String MaSP) {
        CTPN_DTO ctpn = new CTPN_DTO();
        String sql = "SELECT * FROM CTPN WHERE MaPN = ? AND MaSP = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaPN);
            ps.setString(2, MaSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ctpn.setMaPN(rs.getString("MaPN"));
                ctpn.setMaSP(rs.getString("MaSP"));
                ctpn.setSoLuong(rs.getInt("SoLuong"));
                ctpn.setDonGia(rs.getFloat("DonGia"));
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return ctpn;
    }

    
    
}
