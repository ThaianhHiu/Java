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
            String sql = "INSERT INTO LoaiSP(MaLoaiSP, TenLoaiSP, Mota) VALUES(?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loaiSP.getMaLoaiSP());
            stmt.setString(2, loaiSP.getTenLoaiSP());
            stmt.setString(3, loaiSP.getMoTa());
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

    

}
