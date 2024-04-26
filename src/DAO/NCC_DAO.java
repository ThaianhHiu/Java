package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.NCC_DTO;

public class NCC_DAO {
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

    public ArrayList<NCC_DTO> getListNCC() {
        ArrayList<NCC_DTO> listNCC = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NCC_DTO ncc = new NCC_DTO();
                ncc.setMaNCC(rs.getString("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSDT(rs.getString("SDT"));
                ncc.setIsDelete(rs.getInt("isDelete"));
                listNCC.add(ncc);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listNCC;
    }

    //get all mã NCC
    public ArrayList<String> getAllMaNCC() {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "SELECT MaNCC FROM NhaCungCap";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString("MaNCC"));
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return arr;
    }

    //get Ten NCC
    public String getTenNCC(String MaNCC) {
        String sql = "SELECT TenNCC FROM NhaCungCap WHERE MaNCC = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaNCC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenNCC");
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    // Add new NCC
    public void addNCC(NCC_DTO ncc) {
        String sql = "INSERT INTO NhaCungCap VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, taoMaSVTuDong());
            ps.setString(2, ncc.getTenNCC());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSDT());
            ps.setInt(5, ncc.getIsDelete());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // Update NCC

    public void updateNCC(NCC_DTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNCC = ?, DiaChi = ?, SDT = ? WHERE MaNCC = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSDT());
            ps.setString(4, ncc.getMaNCC());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // Delete NCC
    public void deleteNCC(String MaNCC) {
        String sql = "UPDATE NhaCungCap SET isDelete = 0 WHERE MaNCC = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, MaNCC);
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // search NCC

    public ArrayList<NCC_DTO> searchNCC(String TenNCC) {
        ArrayList<NCC_DTO> listNCC = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE TenNCC LIKE ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, "%" + TenNCC + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NCC_DTO ncc = new NCC_DTO();
                ncc.setMaNCC(rs.getString("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSDT(rs.getString("SDT"));
                ncc.setIsDelete(rs.getInt("isDelete"));
                listNCC.add(ncc);
            }
            rs.close();
            ps.close();
            closeConnection();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listNCC;
    }

    private String taoMaSVTuDong() {  
        String sql = "SELECT MaNCC FROM NhaCungCap";
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
            return "NCC" + String.format("%02d", max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NCC01" + String.format("%02d", 1);
}

}
