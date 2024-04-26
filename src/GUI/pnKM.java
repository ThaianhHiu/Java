/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.KM_BUS;
import DTO.KM_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author sinht
 */
public class pnKM extends javax.swing.JPanel {

    /**
     * Creates new form pnKM
     */

    KM_BUS km_BUS = new KM_BUS();
    public pnKM() {
        initComponents();
        showKM();
        txtMaKM.setEnabled(false);
    }

    public void showKM() {
        ArrayList<KM_DTO> listKM = km_BUS.getListKM();
        DefaultTableModel model = (DefaultTableModel) tblKM.getModel();
        model.setRowCount(0);
        for (KM_DTO km : listKM) {
            model.addRow(new Object[]{model.getRowCount() + 1, km.getMaKM(), km.getTenKM(), km.getDKKM(), km.getSoKM(), km.getNgayBD(), km.getNgayKT(), km.getIsDelete()});
        }

        tblKM.getSelectionModel().addListSelectionListener((e) -> {
            if (tblKM.getSelectedRow() >= 0) {
                int row = tblKM.getSelectedRow();
                txtMaKM.setText(tblKM.getValueAt(row, 1).toString());
                txtTenKM.setText(tblKM.getValueAt(row, 2).toString());
                txtDKKM.setText(tblKM.getValueAt(row, 3).toString());
                txtGiamgia.setText(tblKM.getValueAt(row, 4).toString());
                txtNgayBatDau.setText(tblKM.getValueAt(row, 5).toString());
                txtNgayKetThuc.setText(tblKM.getValueAt(row, 6).toString());
            }
        });
    }

    public void addKM() {
        KM_DTO km = new KM_DTO();
        km.setMaKM(txtMaKM.getText());
        km.setTenKM(txtTenKM.getText());
        km.setDKKM(Float.parseFloat(txtDKKM.getText()));
        km.setSoKM(Float.parseFloat(txtGiamgia.getText()));
        km.setNgayBD(txtNgayBatDau.getText());
        km.setNgayKT(txtNgayKetThuc.getText());
        km.setIsDelete(0);
        km_BUS.addKM(km);
        showKM();
    }

    public void updateKM() {
        KM_DTO km = new KM_DTO();
        km.setMaKM(txtMaKM.getText());
        km.setTenKM(txtTenKM.getText());
        km.setDKKM(Float.parseFloat(txtDKKM.getText()));
        km.setSoKM(Float.parseFloat(txtGiamgia.getText()));
        km.setNgayBD(txtNgayBatDau.getText());
        km.setNgayKT(txtNgayKetThuc.getText());
        km.setIsDelete(0);
        km_BUS.updateKM(km);
        showKM();
    }

    public void deleteKM() {
        String maKM = txtMaKM.getText();
        km_BUS.deleteKM(maKM);
        showKM();
    }

    public void searchKM() {
        String search = txtTimkiem.getText();
        ArrayList<KM_DTO> listKM = km_BUS.searchKM(search);
        DefaultTableModel model = (DefaultTableModel) tblKM.getModel();
        model.setRowCount(0);
        for (KM_DTO km : listKM) {
            model.addRow(new Object[]{model.getRowCount() + 1, km.getMaKM(), km.getTenKM(), km.getDKKM(), km.getSoKM(), km.getNgayBD(), km.getNgayKT(), km.getIsDelete()});
        }
    }

    public void refresh() {
        // Clear all text fields
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtDKKM.setText("");
        txtGiamgia.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtTimkiem.setText("");
        showKM();
        tblKM.clearSelection();
    }

    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblKM.getModel();
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

        pnKM = new javax.swing.JPanel();
        pnTop4 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnKetthuc = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        brnLammoi = new javax.swing.JButton();
        pnCenter4 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblKM = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtMaKM = new javax.swing.JTextField();
        txtTenKM = new javax.swing.JTextField();
        txtDKKM = new javax.swing.JTextField();
        txtGiamgia = new javax.swing.JTextField();
        txtNgayBatDau = new javax.swing.JTextField();
        txtNgayKetThuc = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        pnKM.setLayout(new java.awt.BorderLayout());

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

        btnKetthuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKetthuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exit.png"))); // NOI18N
        btnKetthuc.setText("Kết thúc");
        jPanel24.add(btnKetthuc);

        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel24.add(btnXuatExcel);

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

        brnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        brnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        brnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        brnLammoi.setText("Làm mới");
        brnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnLammoiActionPerformed(evt);
            }
        });
        jPanel25.add(brnLammoi);

        pnTop4.add(jPanel25, java.awt.BorderLayout.CENTER);

        pnKM.add(pnTop4, java.awt.BorderLayout.PAGE_START);

        pnCenter4.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter4.setPreferredSize(new java.awt.Dimension(161, 450));
        pnCenter4.setLayout(new java.awt.BorderLayout());

        tblKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KM", "Tên KM", "Điều kiện", "Giảm giá", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ));
        tblKM.setShowGrid(true);
        tblKM.setSurrendersFocusOnKeystroke(true);
        jScrollPane9.setViewportView(tblKM);

        pnCenter4.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        txtMaKM.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã KM"));
        txtMaKM.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtMaKM);

        txtTenKM.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên KM"));
        txtTenKM.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtTenKM);

        txtDKKM.setBorder(javax.swing.BorderFactory.createTitledBorder("ĐKKM"));
        txtDKKM.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtDKKM);

        txtGiamgia.setBorder(javax.swing.BorderFactory.createTitledBorder("Giảm giá"));
        txtGiamgia.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtGiamgia);

        txtNgayBatDau.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày bắt đầu"));
        txtNgayBatDau.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtNgayBatDau);

        txtNgayKetThuc.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày Kết Thúc"));
        txtNgayKetThuc.setPreferredSize(new java.awt.Dimension(140, 80));
        jPanel1.add(txtNgayKetThuc);

        pnCenter4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnKM.add(pnCenter4, java.awt.BorderLayout.CENTER);

        add(pnKM, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteKM();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateKM();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        searchKM();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void brnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnLammoiActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_brnLammoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addKM();
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnLammoi;
    private javax.swing.JButton btnKetthuc;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel pnCenter4;
    private javax.swing.JPanel pnKM;
    private javax.swing.JPanel pnTop4;
    private javax.swing.JTable tblKM;
    private javax.swing.JTextField txtDKKM;
    private javax.swing.JTextField txtGiamgia;
    private javax.swing.JTextField txtMaKM;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    private javax.swing.JTextField txtTenKM;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
