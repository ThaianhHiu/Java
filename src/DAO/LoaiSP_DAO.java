package DAO;

import DTO.LoaiSP_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LoaiSP_DAO {
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

    public ArrayList<LoaiSP_DTO> getAllLoaiSP() {
        ArrayList<LoaiSP_DTO> arr = new ArrayList<>();

        try {
            con = getConnection();
            String sql = "SELECT * FROM LoaiSP";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiSP_DTO loaiSP = new LoaiSP_DTO();
                loaiSP.setMaLoaiSP(rs.getString("MaLoaiSP"));
                loaiSP.setTenLoaiSP(rs.getString("TenLoaiSP"));
                loaiSP.setMoTa(rs.getString("Mota"));
                loaiSP.setTrangThai(rs.getInt("TrangThai"));
                arr.add(loaiSP);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }


    public void insertLoaiSP(LoaiSP_DTO loaiSP) {
        try {
            con = getConnection();
            String sql = "INSERT INTO LoaiSP(MaLoaiSP, TenLoaiSP, Mota, TrangThai) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, taoMaLoaiSPTuDong());
            stmt.setString(2, loaiSP.getTenLoaiSP());
            stmt.setString(3, loaiSP.getMoTa());
            stmt.setInt(4, 1);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateLoaiSP(LoaiSP_DTO loaiSP) {
        try {
            con = getConnection();
            String sql = "UPDATE LoaiSP SET TenLoaiSP = ?, Mota = ? WHERE MaLoaiSP = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loaiSP.getTenLoaiSP());
            stmt.setString(2, loaiSP.getMoTa());
            stmt.setString(3, loaiSP.getMaLoaiSP());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteLoaiSP(String maLoaiSP) {
        try {
            con = getConnection();
            String sql = "UPDATE LoaiSP SET TrangThai = 0 WHERE MaLoaiSP = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maLoaiSP);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<LoaiSP_DTO> searchLoaiSP(String txtSearch) {
        ArrayList<LoaiSP_DTO> arr = new ArrayList<>();
        try {
            con = getConnection();
            String sql = "SELECT * FROM LoaiSP WHERE TenLoaiSP LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + txtSearch + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiSP_DTO loaiSP = new LoaiSP_DTO();
                loaiSP.setMaLoaiSP(rs.getString("MaLoaiSP"));
                loaiSP.setTenLoaiSP(rs.getString("TenLoaiSP"));
                loaiSP.setMoTa(rs.getString("Mota"));
                loaiSP.setTrangThai(rs.getInt("TrangThai"));
                arr.add(loaiSP);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    private String taoMaLoaiSPTuDong() {  
        String sql = "SELECT MaLoaiSP FROM LoaiSP";
                try {
                    con = getConnection();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    int max = 0;
                    while (rs.next()) {
                        String maSV = rs.getString(1);
                        int num = Integer.parseInt(maSV.trim().substring(3));
                        if (num > max) {
                            max = num;
                        }
                    }
                    max++;
                    return "LSP" + String.format("%02d", max);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "LSP01" + String.format("%02d", 1);
        }
}
