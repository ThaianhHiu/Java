/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.NV_BUS;
import DTO.NV_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author sinht
 */
public class pnNhanVien extends javax.swing.JPanel {

    /**
     * Creates new form pnNhanVien
     */

    NV_BUS nvBUS = new NV_BUS();
    public pnNhanVien() {
        initComponents();
        showNV();
        txtMaNV.setEnabled(false);
    }

    public void showNV() {
        ArrayList<NV_DTO> listNV = nvBUS.getListNV();
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        for (NV_DTO nv : listNV) {
            model.addRow(new Object[]{model.getRowCount() + 1, nv.getMaNV(), nv.getTenNV(), nv.getDiaChi(), nv.getSDT(), nv.getNgaySinh(), nv.getTrangThai()});
        }

        // khi click vào 1 dòng trong bảng
        tblNV.getSelectionModel().addListSelectionListener((e) -> {
            if (tblNV.getSelectedRow() >= 0) {
                txtMaNV.setText(tblNV.getValueAt(tblNV.getSelectedRow(), 1).toString());
                txtTenNV.setText(tblNV.getValueAt(tblNV.getSelectedRow(), 2).toString());
                txtDiachi.setText(tblNV.getValueAt(tblNV.getSelectedRow(), 3).toString());
                txtSDT.setText(tblNV.getValueAt(tblNV.getSelectedRow(), 4).toString());
                txtNgaySinh.setText(tblNV.getValueAt(tblNV.getSelectedRow(), 5).toString());
            }
        });
    }

    public void addNV() {
        NV_DTO nv = new NV_DTO();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        nv.setDiaChi(txtDiachi.getText());
        nv.setSDT(txtSDT.getText());
        nv.setNgaySinh(txtNgaySinh.getText());
        nv.setTrangThai(1);
        nvBUS.addNV(nv);
        showNV();
    }

    public void updateNV() {
        NV_DTO nv = new NV_DTO();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        nv.setDiaChi(txtDiachi.getText());
        nv.setSDT(txtSDT.getText());
        nv.setNgaySinh(txtNgaySinh.getText());
        nv.setTrangThai(1);
        nvBUS.updateNV(nv);
        showNV();
    }

    public void deleteNV() {
        String maNV = txtMaNV.getText();
        nvBUS.deleteNV(maNV);
        showNV();
    }

    public void searchNV() {
        String search = txtTimkiem.getText();
        ArrayList<NV_DTO> listNV = nvBUS.searchNV(search);
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        for (NV_DTO nv : listNV) {
            model.addRow(new Object[]{model.getRowCount() + 1, nv.getMaNV(), nv.getTenNV(), nv.getDiaChi(), nv.getSDT(), nv.getNgaySinh(), nv.getTrangThai()});
        }
    }

    public void refresh() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtDiachi.setText("");
        txtSDT.setText("");
        txtNgaySinh.setText("");
        txtTimkiem.setText("");
        tblNV.clearSelection();
        showNV();
    }

    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblNV.getModel();
            Row row = sheet.createRow(0);
            for (int i = 0; i < dtm.getColumnCount(); i++) {
                row.createCell(i).setCellValue(dtm.getColumnName(i));
            }
            for (int i = 0; i < dtm.getRowCount(); i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < dtm.getColumnCount(); j++) {
                    row.createCell(j).setCellValue(dtm.getValueAt(i, j).toString());
                }
            }
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".xlsx")) {
                    path += ".xlsx";
                }
                FileOutputStream fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                JOptionPane.showMessageDialog(this, "Export successfully");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }











    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnNhanvien = new javax.swing.JPanel();
        pnTop5 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        pnCenter5 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        txtDiachi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        pnNhanvien.setLayout(new java.awt.BorderLayout());

        pnTop5.setBackground(new java.awt.Color(204, 255, 255));
        pnTop5.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop5.setLayout(new java.awt.BorderLayout());

        jPanel27.setBackground(new java.awt.Color(153, 255, 255));
        jPanel27.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add1.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel27.add(btnThem);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel27.add(btnXoa);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel27.add(btnSua);

        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel27.add(btnXuatExcel);

        pnTop5.add(jPanel27, java.awt.BorderLayout.PAGE_START);

        jPanel28.setBackground(new java.awt.Color(153, 255, 255));
        jPanel28.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel29.setBackground(new java.awt.Color(204, 255, 255));
        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTimkiem.setBackground(new java.awt.Color(153, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 102));
        jLabel15.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

        jPanel28.add(jPanel29);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel28.add(btnTimkiem);

        btnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });
        jPanel28.add(btnLammoi);

        pnTop5.add(jPanel28, java.awt.BorderLayout.CENTER);

        pnNhanvien.add(pnTop5, java.awt.BorderLayout.PAGE_START);

        pnCenter5.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter5.setPreferredSize(new java.awt.Dimension(161, 450));
        pnCenter5.setLayout(new java.awt.BorderLayout());

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Tên NV", "Địa chỉ", "SĐT", "Ngày Sinh", "Trạng Thái"
            }
        ));
        tblNV.setShowGrid(true);
        tblNV.setSurrendersFocusOnKeystroke(true);
        jScrollPane10.setViewportView(tblNV);

        pnCenter5.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        txtMaNV.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã NV"));
        txtMaNV.setPreferredSize(new java.awt.Dimension(130, 60));
        jPanel1.add(txtMaNV);

        txtTenNV.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên NV"));
        txtTenNV.setPreferredSize(new java.awt.Dimension(130, 60));
        jPanel1.add(txtTenNV);

        txtDiachi.setBorder(javax.swing.BorderFactory.createTitledBorder("Địa chỉ"));
        txtDiachi.setPreferredSize(new java.awt.Dimension(130, 60));
        jPanel1.add(txtDiachi);

        txtSDT.setBorder(javax.swing.BorderFactory.createTitledBorder("SĐT"));
        txtSDT.setPreferredSize(new java.awt.Dimension(130, 60));
        jPanel1.add(txtSDT);

        txtNgaySinh.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày Sinh"));
        txtNgaySinh.setPreferredSize(new java.awt.Dimension(130, 60));
        jPanel1.add(txtNgaySinh);

        pnCenter5.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnNhanvien.add(pnCenter5, java.awt.BorderLayout.CENTER);

        add(pnNhanvien, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteNV();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateNV();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        searchNV();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addNV();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btnLammoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JPanel pnCenter5;
    private javax.swing.JPanel pnNhanvien;
    private javax.swing.JPanel pnTop5;
    private javax.swing.JTable tblNV;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
