/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.KH_BUS;
import DTO.KH_DTO;

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
public class pnKH extends javax.swing.JPanel {

    /**
     * Creates new form pnKH
     */

    KH_BUS khBUS = new KH_BUS();

    public pnKH() {
        initComponents();
        showKH();
        txtMaKH.setEnabled(false);
    }

    public void reload(){
        // các ô textfield set về rỗng
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiachi.setText("");
        txtSĐT.setText("");
        txtTimkiem.setText("");
        tblKhachHang.clearSelection();
        showKH();
    }

    public void showKH() {
        ArrayList<KH_DTO> listKH = khBUS.getListKH();
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        for (int i = 0; i < listKH.size(); i++) {
            KH_DTO kh = listKH.get(i);
            model.addRow(new Object[]{
                i + 1, kh.getMaKH(), kh.getTenKH(), kh.getSDT(), kh.getDiaChi(), kh.getTrangThai()
            });
        }

        tblKhachHang.getSelectionModel().addListSelectionListener((e) -> {
            if (tblKhachHang.getSelectedRow() >= 0) {
                txtMaKH.setText(tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 1).toString());
                txtTenKH.setText(tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 2).toString());
                txtSĐT.setText(tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 3).toString());
                txtDiachi.setText(tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 4).toString());
            }
        });
    }

    public void addKH() {
        KH_DTO kh = new KH_DTO();
        kh.setMaKH(txtMaKH.getText());
        kh.setTenKH(txtTenKH.getText());
        kh.setDiaChi(txtDiachi.getText());
        kh.setSDT(txtSĐT.getText());
        kh.setTrangThai(1);
        khBUS.addKH(kh);
        showKH();
    }

    public void deleteKH() {
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        String MaKH = tblKhachHang.getValueAt(row, 1).toString();
        khBUS.deleteKH(MaKH);
        showKH();
    }

    public void updateKH() {
        KH_DTO kh = new KH_DTO();
        kh.setMaKH(txtMaKH.getText());
        kh.setTenKH(txtTenKH.getText());
        kh.setDiaChi(txtDiachi.getText());
        kh.setSDT(txtSĐT.getText());
        kh.setTrangThai(1);
        khBUS.updateKH(kh);
        showKH();
    }

    public void searchKH() {
        ArrayList<KH_DTO> listKH = khBUS.searchKH(txtTimkiem.getText());
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        for (int i = 0; i < listKH.size(); i++) {
            KH_DTO kh = listKH.get(i);
            model.addRow(new Object[]{
                i + 1, kh.getMaKH(), kh.getTenKH(), kh.getSDT(), kh.getDiaChi(), kh.getTrangThai()
            });
        }
    }

    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
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

        pnKH = new javax.swing.JPanel();
        pnTop4 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        pnCenter4 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtDiachi = new javax.swing.JTextField();
        txtSĐT = new javax.swing.JTextField();

        pnKH.setLayout(new java.awt.BorderLayout());

        pnTop4.setBackground(new java.awt.Color(204, 255, 255));
        pnTop4.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop4.setLayout(new java.awt.BorderLayout());

        jPanel24.setBackground(new java.awt.Color(153, 255, 255));
        jPanel24.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add1.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel24.add(btnThem);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel24.add(btnXoa);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel24.add(btnSua);

        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatEx.setText("Xuất Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel24.add(btnXuatEx);

        pnTop4.add(jPanel24, java.awt.BorderLayout.PAGE_START);

        jPanel25.setBackground(new java.awt.Color(153, 255, 255));
        jPanel25.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel26.setBackground(new java.awt.Color(204, 255, 255));
        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTimkiem.setBackground(new java.awt.Color(153, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 102));
        jLabel14.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel14))
                .addContainerGap())
        );

        jPanel25.add(jPanel26);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel25.add(btnTimkiem);

        jButton40.setBackground(new java.awt.Color(153, 255, 153));
        jButton40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        jButton40.setText("Làm mới");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        jPanel25.add(jButton40);

        pnTop4.add(jPanel25, java.awt.BorderLayout.CENTER);

        pnKH.add(pnTop4, java.awt.BorderLayout.PAGE_START);

        pnCenter4.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter4.setPreferredSize(new java.awt.Dimension(161, 450));
        pnCenter4.setLayout(new java.awt.BorderLayout());

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "SĐT", "Địa chỉ", "Trạng Thái"
            }
        ));
        tblKhachHang.setShowGrid(true);
        tblKhachHang.setSurrendersFocusOnKeystroke(true);
        jScrollPane9.setViewportView(tblKhachHang);

        pnCenter4.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        txtMaKH.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã KH"));
        txtMaKH.setPreferredSize(new java.awt.Dimension(120, 60));
        jPanel1.add(txtMaKH);

        txtTenKH.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên KH"));
        txtTenKH.setPreferredSize(new java.awt.Dimension(120, 60));
        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });
        jPanel1.add(txtTenKH);

        txtDiachi.setBorder(javax.swing.BorderFactory.createTitledBorder("Địa chỉ"));
        txtDiachi.setPreferredSize(new java.awt.Dimension(120, 60));
        jPanel1.add(txtDiachi);

        txtSĐT.setBorder(javax.swing.BorderFactory.createTitledBorder("SĐT"));
        txtSĐT.setPreferredSize(new java.awt.Dimension(120, 60));
        txtSĐT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSĐTActionPerformed(evt);
            }
        });
        jPanel1.add(txtSĐT);

        pnCenter4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnKH.add(pnCenter4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1361, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 857, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnKH, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteKH();
            
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateKH();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        searchKH();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

    private void txtSĐTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSĐTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSĐTActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        reload();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addKH();
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JButton jButton40;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel pnCenter4;
    private javax.swing.JPanel pnKH;
    private javax.swing.JPanel pnTop4;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSĐT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
