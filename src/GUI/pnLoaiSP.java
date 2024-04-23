/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.LoaiSP_BUS;
import DTO.LoaiSP_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author sinht
 */
public class pnLoaiSP extends javax.swing.JPanel {

    /**
     * Creates new form pnLoaiSP
     * 
     */
    LoaiSP_BUS lsp = new LoaiSP_BUS();
    
    public pnLoaiSP() {
        initComponents();
        loadLoaiSP();
        txtMaLoai.setEnabled(false);
    }

    private void loadLoaiSP() {
        ArrayList<LoaiSP_DTO> arr = lsp.getAllSP();
        DefaultTableModel model = (DefaultTableModel) tblLoaiSP.getModel();
        model.setRowCount(0);
        for (int i = 0; i < arr.size(); i++) {
           LoaiSP_DTO loaiSP = arr.get(i);
           model.addRow(new Object[]{
             loaiSP.getMaLoaiSP(), loaiSP.getTenLoaiSP(), loaiSP.getMoTa(),loaiSP.getTrangThai()
           });
        }
        tblLoaiSP.setModel(model);
        
        tblLoaiSP.getSelectionModel().addListSelectionListener((e) -> {
            if(tblLoaiSP.getSelectedRow() >= 0){
                txtMaLoai.setText(tblLoaiSP.getValueAt(tblLoaiSP.getSelectedRow(), 0).toString());
                txtTenLoai.setText(tblLoaiSP.getValueAt(tblLoaiSP.getSelectedRow(), 1).toString());
                txtMota.setText(tblLoaiSP.getValueAt(tblLoaiSP.getSelectedRow(), 2).toString());
            }
        });
    }

    private void reload() {
        clear();
        tblLoaiSP.getSelectionModel().clearSelection();
        loadLoaiSP();
    }

    private void clear() {
        txtMaLoai.setText("");
        txtTenLoai.setText("");
        txtMota.setText("");
        txtTimkiem.setText("");
    }

    private void themLoaiSP(){
        if( txtTenLoai.getText().equals("") || txtMota.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
        }
        else{
            LoaiSP_DTO loaiSP = new LoaiSP_DTO();
            loaiSP.setMaLoaiSP(txtMaLoai.getText());
            loaiSP.setTenLoaiSP(txtTenLoai.getText());
            loaiSP.setMoTa(txtMota.getText());
            lsp.insertLoaiSP(loaiSP);
            loadLoaiSP();
            clear();
        }
    }

    private void xoaLoaiSP(){
        if(txtMaLoai.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm cần xóa");
        }
        else{
            lsp.deleteLoaiSP(txtMaLoai.getText());
            loadLoaiSP();
            reload();
        }
    }

    private void suaLoaiSP(){
        if(txtMaLoai.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm cần sửa");
        }
        else{
            LoaiSP_DTO loaiSP = new LoaiSP_DTO();
            loaiSP.setMaLoaiSP(txtMaLoai.getText());
            loaiSP.setTenLoaiSP(txtTenLoai.getText());
            loaiSP.setMoTa(txtMota.getText());
            lsp.updateLoaiSP(loaiSP);
            loadLoaiSP();
            reload();
        }
    }

    // tìm các loại sản phẩm theo txtTimkiem so sánh với txtTenLoai 
    private void timLoaiSP(){
        ArrayList<LoaiSP_DTO> arr = lsp.searchLoaiSP(txtTimkiem.getText());
        DefaultTableModel model = (DefaultTableModel) tblLoaiSP.getModel();
        model.setRowCount(0);
        for (int i = 0; i < arr.size(); i++) {
           LoaiSP_DTO loaiSP = arr.get(i);
           model.addRow(new Object[]{
             loaiSP.getMaLoaiSP(), loaiSP.getTenLoaiSP(), loaiSP.getMoTa(),loaiSP.getTrangThai()
           });
        }
        tblLoaiSP.setModel(model);
    }

    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblLoaiSP.getModel();
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

    private void nhapExcel(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                Workbook workbook = new XSSFWorkbook(path);
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    LoaiSP_DTO loaiSP = new LoaiSP_DTO();
                    //loaiSP.setMaLoaiSP(row.getCell(0).getStringCellValue());
                    // mã loại tăng tự động
                    loaiSP.setTenLoaiSP(row.getCell(0).getStringCellValue());
                    loaiSP.setMoTa(row.getCell(1).getStringCellValue());
                    loaiSP.setTrangThai((int) row.getCell(2).getNumericCellValue());
                    lsp.insertLoaiSP(loaiSP);
                }
                loadLoaiSP();
                reload();
                JOptionPane.showMessageDialog(this, "Import successfully");
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

        pnLoaiSP = new javax.swing.JPanel();
        pnTop1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        btnNhapEx = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        pnCenter1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblLoaiSP = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtMaLoai = new javax.swing.JTextField();
        txtTenLoai = new javax.swing.JTextField();
        txtMota = new javax.swing.JTextField();

        pnLoaiSP.setLayout(new java.awt.BorderLayout());

        pnTop1.setBackground(new java.awt.Color(204, 255, 255));
        pnTop1.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop1.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(153, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add1.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel11.add(btnThem);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel11.add(btnXoa);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel11.add(btnSua);

        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatEx.setText("Xuất Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel11.add(btnXuatEx);

        btnNhapEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhapEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnNhapEx.setText("Nhập Excel ");
        btnNhapEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExActionPerformed(evt);
            }
        });
        jPanel11.add(btnNhapEx);

        pnTop1.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel12.setBackground(new java.awt.Color(153, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel13.setBackground(new java.awt.Color(204, 255, 255));
        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTimkiem.setBackground(new java.awt.Color(153, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 102));
        jLabel7.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jPanel12.add(jPanel13);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 255));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel12.add(btnTimkiem);

        jButton13.setBackground(new java.awt.Color(153, 255, 153));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        jButton13.setText("Làm mới");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton13);

        pnTop1.add(jPanel12, java.awt.BorderLayout.CENTER);

        pnLoaiSP.add(pnTop1, java.awt.BorderLayout.PAGE_START);

        pnCenter1.setBackground(new java.awt.Color(255, 255, 255));
        pnCenter1.setPreferredSize(new java.awt.Dimension(161, 450));
        pnCenter1.setLayout(new java.awt.BorderLayout());

        tblLoaiSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Loại", "Tên Loại", "Mô tả", "Trạng thái"
            }
        ));
        tblLoaiSP.setShowGrid(true);
        tblLoaiSP.setSurrendersFocusOnKeystroke(true);
        jScrollPane6.setViewportView(tblLoaiSP);

        pnCenter1.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        txtMaLoai.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã Loại"));
        txtMaLoai.setPreferredSize(new java.awt.Dimension(120, 60));
        jPanel1.add(txtMaLoai);

        txtTenLoai.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên Loại"));
        txtTenLoai.setPreferredSize(new java.awt.Dimension(120, 60));
        jPanel1.add(txtTenLoai);

        txtMota.setBorder(javax.swing.BorderFactory.createTitledBorder(" Mô Tả"));
        txtMota.setPreferredSize(new java.awt.Dimension(300, 60));
        jPanel1.add(txtMota);

        pnCenter1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnLoaiSP.add(pnCenter1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1361, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 857, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaLoaiSP();
        
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        suaLoaiSP();
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void btnNhapExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExActionPerformed
        // TODO add your handling code here:
        nhapExcel();
    }//GEN-LAST:event_btnNhapExActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        reload();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        timLoaiSP();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        themLoaiSP();
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNhapEx;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel pnCenter1;
    private javax.swing.JPanel pnLoaiSP;
    private javax.swing.JPanel pnTop1;
    private javax.swing.JTable tblLoaiSP;
    private javax.swing.JTextField txtMaLoai;
    private javax.swing.JTextField txtMota;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
