package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.KM_DTO;

public class KM_DAO {

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

    public ArrayList<KM_DTO> getListKM() {
        ArrayList<KM_DTO> listKM = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KM_DTO km = new KM_DTO();
                km.setMaKM(rs.getString("MaKM"));
                km.setTenKM(rs.getString("TenKM"));
                km.setDKKM(rs.getFloat("DKKM"));
                km.setSoKM(rs.getFloat("SoKM"));
                km.setNgayBD(rs.getString("NgayBD"));
                km.setNgayKT(rs.getString("NgayKT"));
                km.setIsDelete(rs.getInt("isDelete"));
                listKM.add(km);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listKM;
    }

    public void addKM(KM_DTO km) {
        String sql = "INSERT INTO KhuyenMai VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, taoMaSVTuDong());
            ps.setString(2, km.getTenKM());
            ps.setFloat(3, km.getDKKM());
            ps.setFloat(4, km.getSoKM());
            ps.setString(5, km.getNgayBD());
            ps.setString(6, km.getNgayKT());
            ps.setInt(7, km.getIsDelete());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateKM(KM_DTO km) {
        String sql = "UPDATE KhuyenMai SET TenKM = ?, DKKM = ?, SoKM = ?, NgayBD = ?, NgayKT = ?, isDelete = ? WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, km.getTenKM());
            ps.setFloat(2, km.getDKKM());
            ps.setFloat(3, km.getSoKM());
            ps.setString(4, km.getNgayBD());
            ps.setString(5, km.getNgayKT());
            ps.setInt(6, km.getIsDelete());
            ps.setString(7, km.getMaKM());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteKM(String maKM) {
        String sql = "UPDATE KhuyenMai SET isDelete = 0 WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKM);
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<KM_DTO> searchKM(String search) {
        ArrayList<KM_DTO> listKM = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai WHERE TenKM LIKE ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KM_DTO km = new KM_DTO();
                km.setMaKM(rs.getString("MaKM"));
                km.setTenKM(rs.getString("TenKM"));
                km.setDKKM(rs.getFloat("DKKM"));
                km.setSoKM(rs.getFloat("SoKM"));
                km.setNgayBD(rs.getString("NgayBD"));
                km.setNgayKT(rs.getString("NgayKT"));
                km.setIsDelete(rs.getInt("isDelete"));
                listKM.add(km);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listKM;
    }

    private String taoMaSVTuDong() {  
        String sql = "SELECT MaKM FROM KhuyenMai";
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int max = 0;
            while (rs.next()) {
                String maSV = rs.getString(1);
                int num = Integer.parseInt(maSV.trim().substring(3));
                if (num > max) {
                    max = num;
                }
            }
            max++;
            return "KM" + String.format("%02d", max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "KM01" + String.format("%02d", 1);
}

    // lấy tất cả mã khuyến mãi
    public ArrayList<String> getAllMaKM() {
        ArrayList<String> listMaKM = new ArrayList<>();
        String sql = "SELECT MaKM FROM KhuyenMai";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listMaKM.add(rs.getString("MaKM"));
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listMaKM;
    }

    // lấy giá trị điều kiện khuyến mãi
    public float getDKKM(String maKM) {
        String sql = "SELECT DKKM FROM KhuyenMai WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("DKKM");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }

    // lấy giá trị số khuyến mãi
    public float getSoKM(String maKM) {
        String sql = "SELECT SoKM FROM KhuyenMai WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("SoKM");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }

    // lấy ngày bắt đầu khuyến mãi
    public String getNgayBD(String maKM) {
        String sql = "SELECT NgayBD FROM KhuyenMai WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("NgayBD");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return "";
    }

    // lấy ngày kết thúc khuyến mãi

    public String getNgayKT(String maKM) {
        String sql = "SELECT NgayKT FROM KhuyenMai WHERE MaKM = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, maKM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("NgayKT");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return "";
    }
    
}
