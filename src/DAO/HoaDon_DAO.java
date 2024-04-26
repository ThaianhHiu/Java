package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.HoaDon_DTO;

public class HoaDon_DAO {
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

    // get all HoaDon
    public ArrayList<HoaDon_DTO> getAllHoaDon() {
        ArrayList<HoaDon_DTO> arr = new ArrayList<>();

        try {
            con = getConnection();
            String sql = "SELECT * FROM HoaDon";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon_DTO hoaDon = new HoaDon_DTO();
                hoaDon.setMaHD(rs.getString("MaHD"));
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setMaKH(rs.getString("MaKH"));
                hoaDon.setMaKM(rs.getString("MaKM"));
                hoaDon.setNgayLap(rs.getString("NgayLap"));
                hoaDon.setGioLap(rs.getString("GioLap"));
                hoaDon.setTongTien(rs.getFloat("TongTien"));
                arr.add(hoaDon);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    // insert HoaDon
    public int insertHoaDon(HoaDon_DTO hoaDon) {
        int result = 0;
        try {
            con = getConnection();
            String sql = "INSERT INTO HoaDon(MaHD, MaNV, MaKH, MaKM, NgayLap, GioLap, TongTien) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHD());
            stmt.setString(2, hoaDon.getMaNV());
            stmt.setString(3, hoaDon.getMaKH());
            stmt.setString(4, hoaDon.getMaKM());
            stmt.setString(5, hoaDon.getNgayLap());
            stmt.setString(6, hoaDon.getGioLap());
            stmt.setFloat(7, hoaDon.getTongTien());
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public ArrayList<HoaDon_DTO> searchHoaDonByMaNV(String maNV) {
        ArrayList<HoaDon_DTO> arr = new ArrayList<>();
        try {
            con = getConnection();
            String sql = "SELECT * FROM HoaDon WHERE MaNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon_DTO hoaDon = new HoaDon_DTO();
                hoaDon.setMaHD(rs.getString("MaHD"));
                hoaDon.setMaNV(rs.getString("MaNV"));
                hoaDon.setMaKH(rs.getString("MaKH"));
                hoaDon.setMaKM(rs.getString("MaKM"));
                hoaDon.setNgayLap(rs.getString("NgayLap"));
                hoaDon.setGioLap(rs.getString("GioLap"));
                hoaDon.setTongTien(rs.getFloat("TongTien"));
                arr.add(hoaDon);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    public String taoMaHDTuDong() {  
        String sql = "SELECT MaHD FROM HoaDon";
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
            return "HD" + String.format("%02d", max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "HD01" + String.format("%02d", 1);
}
}
