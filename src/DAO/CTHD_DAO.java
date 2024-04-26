package DAO;

import DTO.CTHD_DTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CTHD_DAO {
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

    //get tất cả chi tiết hóa đơn theo mã hóa đơn
    public ArrayList<CTHD_DTO> getAllCTHD(String MaHD) {
        ArrayList<CTHD_DTO> result = new ArrayList<CTHD_DTO>();
        String sql = "SELECT * FROM CTHD WHERE MaHD = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CTHD_DTO cthd = new CTHD_DTO();
                cthd.setMaHD(rs.getString("MaHD"));
                cthd.setMaSP(rs.getString("MaSP"));
                cthd.setSoLuong(rs.getInt("SoLuong"));
                cthd.setDonGia(rs.getFloat("DonGia"));
                result.add(cthd);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public void addCTHD(CTHD_DTO cthd) {
        String sql = "INSERT INTO CTHD VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, cthd.getMaHD());
            ps.setString(2, cthd.getMaSP());
            ps.setInt(3, cthd.getSoLuong());
            ps.setFloat(4, cthd.getDonGia());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateCTHD(CTHD_DTO cthd) {
        String sql = "UPDATE CTHD SET SoLuong = ?, DonGia = ? WHERE MaHD = ? AND MaSP = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, cthd.getSoLuong());
            ps.setFloat(2, cthd.getDonGia());
            ps.setString(3, cthd.getMaHD());
            ps.setString(4, cthd.getMaSP());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteCTHD(String MaHD, String MaSP) {
        String sql = "DELETE FROM CTHD WHERE MaHD = ? AND MaSP = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaHD);
            ps.setString(2, MaSP);
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }





}