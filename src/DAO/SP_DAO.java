package DAO;

import DTO.SP_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

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

    public ArrayList<String> getMaLoaiSP() {
        ArrayList<String> arr = new ArrayList<>();
        try {
            con = getConnection();
            String sql = "SELECT MaLoaiSP FROM LoaiSP";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString("MaLoaiSP"));
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
            stmt.setString(1, taoMaSVTuDong());
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

    public boolean updateSP(SP_DTO sp) {
        boolean result = false;
        try {
            con = getConnection();
            String sql = "UPDATE SanPham SET MaLoaiSP = ?, TenSP = ?, DonGia = ?, SoLuong = ?, HinhAnh = ?, NgaySX = ?, NgayHH = ?, NoiSX = ?, isDelete = ? WHERE MaSP = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMaLoaiSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setFloat(3, sp.getDonGia());
            stmt.setInt(4, sp.getSoLuong());
            stmt.setString(5, sp.getHinhAnh());
            stmt.setString(6, sp.getNgaySX());
            stmt.setString(7, sp.getNgayHH());
            stmt.setString(8, sp.getNoiSX());
            stmt.setInt(9, sp.getIsDelete());
            stmt.setString(10, sp.getMaSP());
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

    // public boolean deleteSP(SP_DTO sp) {
    //     boolean result = false;
    //     try {
    //         con = getConnection();
    //         String sql = "DELETE FROM SanPham WHERE MaSP = ?";
    //         PreparedStatement stmt = con.prepareStatement(sql);
    //         stmt.setString(1, sp.getMaSP());
    //         int rowsAffected = stmt.executeUpdate();
    //         if (rowsAffected >= 1)
    //             result = true;
    //     } catch (SQLException ex) {
    //         System.out.println(ex);
    //     } finally {
    //         closeConnection();
    //     }
    //     return result;
    // }

    public boolean deleteSP(SP_DTO sp){
        boolean result = false;
        try {
            con = getConnection();
            String sql = "UPDATE SanPham SET isDelete = 0 WHERE MaSP = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMaSP());
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

    public ArrayList<SP_DTO> searchSP(){
        ArrayList<SP_DTO> arr = new ArrayList<>();
        try {
            con = getConnection();
            String sql = "SELECT * FROM SanPham WHERE isDelete = 1";
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

    private String taoMaSVTuDong() {  
        String sql = "Select MaSP from SanPham";
                try {
                    con = getConnection();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    int max = 0;
                    while (rs.next()) {
                        String maSV = rs.getString(1);
                        int num = Integer.parseInt(maSV.trim().substring(2));
                        if (num > max) {
                            max = num;
                        }
                    }
                    max++;
                    return "SP" + String.format("%02d", max);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "SP01" + String.format("%02d", 1);
        }

        // public void ExportExcel(javax.swing.table.TableModel model, String filename) {
        //     try {
        //         HSSFWorkbook workbook = new HSSFWorkbook();
        //         HSSFSheet sheet = workbook.createSheet("Data");
        
        //         // Create header row
        //         HSSFRow headerRow = sheet.createRow(0);
        //         for (int i = 0; i < model.getColumnCount(); i++) {
        //             HSSFCell cell = headerRow.createCell(i);
        //             cell.setCellValue(model.getColumnName(i));
        //         }
        
        //         // Fill data rows
        //         for (int i = 0; i < model.getRowCount(); i++) {
        //             HSSFRow dataRow = sheet.createRow(i + 1);
        //             for (int j = 0; j < model.getColumnCount(); j++) {
        //                 HSSFCell cell = dataRow.createCell(j);
        //                 cell.setCellValue(model.getValueAt(i, j).toString());
        //             }
        //         }
        
        //         // Write the workbook content to a file
        //         FileOutputStream excel = new FileOutputStream(filename);
        //         workbook.write(excel);
        //         excel.close();
        //     } catch (IOException e) {
        //         System.out.println(e);
        //     }
        // }

        // kiểm tra mã sản phẩm đã tồn tại chưa
        public boolean checkMaSP(String maSP) {
            try {
                con = getConnection();
                String sql = "SELECT * FROM SanPham WHERE MaSP = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, maSP);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            } finally {
                closeConnection();
            }
            return false;
        }

}
