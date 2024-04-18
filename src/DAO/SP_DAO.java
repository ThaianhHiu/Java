package DAO;

import DTO.SP_DTO;
import java.sql.*;
import java.util.ArrayList;

public class SP_DAO {
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

    public ArrayList<SP_DTO> getAllSP() {
        ArrayList<SP_DTO> arr = new ArrayList<>();

        try {
            con = getConnection();
            String sql = "SELECT * FROM SanPham";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SP_DTO sp = new SP_DTO();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setMaLoaiSP(rs.getString("MaLoaiSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setDonGia(rs.getFloat("DonGia"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setHinhAnh(rs.getString("HinhAnh"));
                sp.setNgaySX(rs.getString("NgaySX"));
                sp.setNgayHH(rs.getString("NgayHH"));
                sp.setNoiSX(rs.getString("NoiSX"));
                sp.setIsDelete(rs.getInt("isDelete"));
                arr.add(sp);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            closeConnection();
        }
        return arr;
    }

    public boolean addSP(SP_DTO sp) {
        boolean result = false;
        try {
            con = getConnection();
            String sql = "INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getMaLoaiSP());
            stmt.setString(3, sp.getTenSP());
            stmt.setFloat(4, sp.getDonGia());
            stmt.setInt(5, sp.getSoLuong());
            stmt.setString(6, sp.getHinhAnh());
            stmt.setString(7, sp.getNgaySX());
            stmt.setString(8, sp.getNgayHH());
            stmt.setString(9, sp.getNoiSX());
            stmt.setInt(10, sp.getIsDelete());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected >= 1)
                result = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            closeConnection();
        }
        return result;
    }
    
}
