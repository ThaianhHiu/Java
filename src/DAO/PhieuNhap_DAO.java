package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.PhieuNhap_DTO;

public class PhieuNhap_DAO {
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

    // get all PhieuNhap 
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        ArrayList<PhieuNhap_DTO> arr = new ArrayList<>();

        try {
            con = getConnection();
            String sql = "SELECT * FROM PhieuNhap";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhap_DTO phieuNhap = new PhieuNhap_DTO();
                phieuNhap.setMaPN(rs.getString("MaPN"));
                phieuNhap.setMaNV(rs.getString("MaNV"));
                phieuNhap.setMaNCC(rs.getString("MaNCC"));
                phieuNhap.setNgayNhap(rs.getString("NgayNhap"));
                phieuNhap.setGioNhap(rs.getString("GioNhap"));
                phieuNhap.setTongTien(rs.getFloat("TongTien"));
                arr.add(phieuNhap);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    // get PhieuNhap by MaPN
    public PhieuNhap_DTO getPhieuNhapByMaPN(String MaPN) {
        PhieuNhap_DTO phieuNhap = new PhieuNhap_DTO();

        try {
            con = getConnection();
            String sql = "SELECT * FROM PhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, MaPN);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                phieuNhap.setMaPN(rs.getString("MaPN"));
                phieuNhap.setMaNV(rs.getString("MaNV"));
                phieuNhap.setMaNCC(rs.getString("MaNCC"));
                phieuNhap.setNgayNhap(rs.getString("NgayNhap"));
                phieuNhap.setGioNhap(rs.getString("GioNhap"));
                phieuNhap.setTongTien(rs.getFloat("TongTien"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return phieuNhap;
    }

    // add PhieuNhap gồm mã PN , mã NV , mã NCC , ngày nhập , giờ nhập , tổng tiền
    public int addPhieuNhap(PhieuNhap_DTO phieuNhap) {
        int result = 0;
        try {
            con = getConnection();
            String sql = "INSERT INTO PhieuNhap(MaPN, MaNV, MaNCC, NgayNhap, GioNhap, TongTien) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, phieuNhap.getMaPN());
            stmt.setString(2, phieuNhap.getMaNV());
            stmt.setString(3, phieuNhap.getMaNCC());
            stmt.setString(4, phieuNhap.getNgayNhap());
            stmt.setString(5, phieuNhap.getGioNhap());
            stmt.setFloat(6, phieuNhap.getTongTien());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    // update PhieuNhap

    public int updatePhieuNhap(PhieuNhap_DTO phieuNhap) {
        int result = 0;
        try {
            con = getConnection();
            String sql = "UPDATE PhieuNhap SET MaNV = ?, NgayNhap = ?, TongTien = ? WHERE MaPN = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, phieuNhap.getMaNV());
            stmt.setString(2, phieuNhap.getNgayNhap());
            stmt.setFloat(3, phieuNhap.getTongTien());
            stmt.setString(4, phieuNhap.getMaPN());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    // delete PhieuNhap

    public int deletePhieuNhap(String MaPN) {
        int result = 0;
        try {
            con = getConnection();
            String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, MaPN);
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public ArrayList<PhieuNhap_DTO> searchPhieuNhapByMaNV(String maNV) {
        ArrayList<PhieuNhap_DTO> arr = new ArrayList<>();
        try {
            con = getConnection();
            String sql = "SELECT * FROM PhieuNhap WHERE MaNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhap_DTO phieuNhap = new PhieuNhap_DTO();
                phieuNhap.setMaPN(rs.getString("MaPN"));
                phieuNhap.setMaNV(rs.getString("MaNV"));
                phieuNhap.setNgayNhap(rs.getString("NgayNhap"));
                phieuNhap.setTongTien(rs.getFloat("TongTien"));
                arr.add(phieuNhap);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    public String taoMaHDTuDong() {  
        String sql = "SELECT MaPN FROM PhieuNhap";
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int max = 0;
            while (rs.next()) {
                String maSV = rs.getString(1);
                int num = Integer.parseInt(maSV.trim().substring(2));
                if (num > max) {
                    max = num;
                }
            }
            max++;
            return "PN" + String.format("%02d", max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PN01" + String.format("%02d", 1);
}
    
}
