package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.NV_DTO;

public class NV_DAO {
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

    public ArrayList<NV_DTO> getListNV() {
        ArrayList<NV_DTO> listNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NV_DTO nv = new NV_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setSDT(rs.getString("SDT"));
                nv.setTrangThai(rs.getInt("TrangThai"));
                listNV.add(nv);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listNV;
    }

    // thêm nhân viên
    public void addNV(NV_DTO nv) {
        String sql = "INSERT INTO NhanVien VALUES(?, ?, ?, ?, ?, 1)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, taoMaSVTuDong());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getNgaySinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSDT());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    
    public void updateNV(NV_DTO nv) {
        String sql = "UPDATE NhanVien SET TenNV = ?, NgaySinh = ?, DiaChi = ?, SDT = ? WHERE MaNV = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getNgaySinh());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getSDT());
            ps.setString(5, nv.getMaNV());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteNV(String MaNV) {
        String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNV = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaNV);
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // search employee by name
    public ArrayList<NV_DTO> searchNV(String TenNV) {
        ArrayList<NV_DTO> listNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + TenNV + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NV_DTO nv = new NV_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setSDT(rs.getString("SDT"));
                listNV.add(nv);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listNV;
    }

    public ArrayList<String> getAllMaNV() {
        ArrayList<String> listMaNV = new ArrayList<>();
        String sql = "SELECT MaNV FROM NhanVien";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listMaNV.add(rs.getString("MaNV"));
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listMaNV;
    }

    // get tên nhân viên dựa vào mã nhân viên
    public String getTenNV(String MaNV) {
        String sql = "SELECT TenNV FROM NhanVien WHERE MaNV = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenNV");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    

   

    // tạo mã nhân viên tự động

    private String taoMaSVTuDong() {  
                String sql = "SELECT MaNV FROM NhanVien";
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
                    return "NV" + String.format("%02d", max);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "NV01" + String.format("%02d", 1);
        }

}
