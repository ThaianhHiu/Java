package DAO;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.KH_DTO;

public class KH_DAO {
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

    public ArrayList<KH_DTO> getListKH() {
        ArrayList<KH_DTO> listKH = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KH_DTO kh = new KH_DTO();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSDT(rs.getString("SDT"));
                kh.setTrangThai(rs.getInt("TrangThai"));
                listKH.add(kh);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listKH;
    }

    public void addKH(KH_DTO kh) {
        String sql = "INSERT INTO KhachHang VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, taoMaSVTuDong());
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getDiaChi());
            ps.setString(4, kh.getSDT());
            ps.setInt(5, kh.getTrangThai());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateKH(KH_DTO kh) {
        String sql = "UPDATE KhachHang SET TenKH = ?, DiaChi = ?, SDT = ?, TrangThai = ? WHERE MaKH = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getDiaChi());
            ps.setString(3, kh.getSDT());
            ps.setInt(4, kh.getTrangThai());
            ps.setString(5, kh.getMaKH());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteKH(String maKH) {
        String sql = "UPDATE KhachHang SET TrangThai = 0 WHERE MaKH = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKH);
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<KH_DTO> searchKH(String search) {
        ArrayList<KH_DTO> listKH = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE MaKH LIKE ? OR TenKH LIKE ? OR DiaChi LIKE ? OR SDT LIKE ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ps.setString(4, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KH_DTO kh = new KH_DTO();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSDT(rs.getString("SDT"));
                kh.setTrangThai(rs.getInt("TrangThai"));
                listKH.add(kh);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listKH;
    }

    // lấy mã tất cả mã khách hàng
    public ArrayList<String> getAllMaKH() {
        ArrayList<String> listMaKH = new ArrayList<>();
        String sql = "SELECT MaKH FROM KhachHang";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listMaKH.add(rs.getString("MaKH"));
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listMaKH;
    }

    // lấy tên khách hàng theo mã
    public String getTenKH(String maKH) {
        String sql = "SELECT TenKH FROM KhachHang WHERE MaKH = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenKH");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    private String taoMaSVTuDong() {  
                String sql = "SELECT MaKH FROM KhachHang";
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
                    return "KH" + String.format("%02d", max);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "KH01" + String.format("%02d", 1);
        }





}
