/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.PhieuNhap_BUS;
import DTO.PhieuNhap_DTO;
import BUS.CTPN_BUS;
import DTO.CTPN_DTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import BUS.NCC_BUS;
import DTO.NCC_DTO;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author sinht
 */
public class pnPhieuNhap extends javax.swing.JPanel {

    /**
     * Creates new form pnPhieuNhap
     */

    PhieuNhap_BUS bus = new PhieuNhap_BUS();
    public pnPhieuNhap() {
        initComponents();
        showPhieuNhap();
        loadMaNCC();
    }

    public void showPhieuNhap(){
        ArrayList<PhieuNhap_DTO> list = bus.getAllPhieuNhap();
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        for(PhieuNhap_DTO pn : list){
            model.addRow(new Object[]{pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(), pn.getNgayNhap(), pn.getGioNhap(), pn.getTongTien()});
        }
    }

    // load mã nhà cung cấp lên combobox
    public void loadMaNCC(){
       NCC_BUS ncc_bus = new NCC_BUS();
         ArrayList<String> list = ncc_bus.getAllMaNCC();
            for(String maNCC : list){
                cmbNCC.addItem(maNCC);
            }
    }

    // tìm kiếm phiếu nhập theo mã nhà cung cấp , ngày lập , tổng tiền , hiển thị lên bảng bằng cách so sánh với tblPhieuNhap
    public void Timkiem(){
        // lấy thông tin từ các ô nhập
        String maNCC = cmbNCC.getSelectedItem().toString();
        String ngayLap1 = txtNgayLap1.getText();
        String ngayLap2 = txtNgaylap2.getText();
        String tongTien1 = txtTongTien1.getText();
        String tongTien2 = txtTongtien2.getText();
        // so sánh ngày lập nằm trong khoảng từ ngày lập 1 đến ngày lập 2 , tổng tiền nằm trong khoảng từ tổng tiền 1 đến tổng tiền 2
        ArrayList<PhieuNhap_DTO> list = bus.getAllPhieuNhap();
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        // tìm theo mã nhà cung cấp , ngày lập , tổng tiền 
        // nếu chọn mã nhà cung cấp là "Tất cả" thì không cần so sánh mã nhà cung cấp
        for(PhieuNhap_DTO pn : list){
            if((maNCC.equals("Tất cả") || pn.getMaNCC().equals(maNCC)) && 
                    (ngayLap1.equals("") || ngayLap2.equals("") || (pn.getNgayNhap().compareTo(ngayLap1) >= 0 && pn.getNgayNhap().compareTo(ngayLap2) <= 0)) &&
                    (tongTien1.equals("") || tongTien2.equals("") || (pn.getTongTien() >= Integer.parseInt(tongTien1) && pn.getTongTien() <= Integer.parseInt(tongTien2)))){
                model.addRow(new Object[]{pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(), pn.getNgayNhap(), pn.getGioNhap(), pn.getTongTien()});
            }
        }
    }
    // xem chi tiết phiếu nhập , hiển thị mã phiếu nhập , mã nhà cung cấp , mã nhân viên , ngày nhập , giờ nhập , các sản phẩm đã nhập từ database CTPN , hiển thị bằng JOptionPane
    public void HienThiDSCTPN(String maPN){
        // lấy danh sách chi tiết phiếu nhập từ database 
        CTPN_BUS cthd_bus = new CTPN_BUS();
        ArrayList<CTPN_DTO> list = cthd_bus.getAllCTPN(maPN);
        String message = "";
        for(CTPN_DTO ctpn : list){
            message += "Mã sản phẩm: " + ctpn.getMaSP() + " - Số lượng: " + ctpn.getSoLuong() + " - Đơn giá: " + ctpn.getDonGia() + "\n";
        }
        JOptionPane.showMessageDialog(this, message, "Chi tiết phiếu nhập", JOptionPane.INFORMATION_MESSAGE);
    }

    // reload
    public void Reload(){
        txtNgayLap1.setText("");
        txtNgaylap2.setText("");
        txtTongTien1.setText("");
        txtTongtien2.setText("");
        cmbNCC.setSelectedIndex(0);
        tblPhieuNhap.clearSelection();
        showPhieuNhap();
    }

    // tìm kiếm theo mã 

    // xuất excel
    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblPhieuNhap.getModel();
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

        pnPhieuNhap = new javax.swing.JPanel();
        pnTop3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        btnXuatEx = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        cmbNCC = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        txtNgayLap1 = new javax.swing.JTextField();
        txtNgaylap2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        txtTongTien1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTongtien2 = new javax.swing.JTextField();
        btnXemchitiet = new javax.swing.JButton();
        btnTimkiem = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        pnCenter3 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnPhieuNhap.setLayout(new java.awt.BorderLayout());

        pnTop3.setBackground(new java.awt.Color(204, 255, 255));
        pnTop3.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop3.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(153, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatEx.setText("Xuất Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel19.add(btnXuatEx);

        pnTop3.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel20.setBackground(new java.awt.Color(153, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel21.setBackground(new java.awt.Color(204, 255, 255));
        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setPreferredSize(new java.awt.Dimension(170, 60));

        cmbNCC.setBackground(new java.awt.Color(204, 255, 255));
        cmbNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 102));
        jLabel11.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel21);

        jPanel22.setBackground(new java.awt.Color(204, 255, 255));
        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNgayLap1.setBackground(new java.awt.Color(153, 255, 255));
        txtNgayLap1.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));
        txtNgayLap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLap1ActionPerformed(evt);
            }
        });

        txtNgaylap2.setBackground(new java.awt.Color(153, 255, 255));
        txtNgaylap2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Ngày lập");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 247, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(txtNgayLap1)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgaylap2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayLap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgaylap2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel20.add(jPanel22);

        jPanel23.setBackground(new java.awt.Color(204, 255, 255));
        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTongTien1.setBackground(new java.awt.Color(153, 255, 255));
        txtTongTien1.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Tổng tiền");

        txtTongtien2.setBackground(new java.awt.Color(153, 255, 255));
        txtTongtien2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));
        txtTongtien2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongtien2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(txtTongTien1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addComponent(txtTongtien2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongtien2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel20.add(jPanel23);

        btnXemchitiet.setBackground(new java.awt.Color(204, 255, 255));
        btnXemchitiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemchitiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/watch.png"))); // NOI18N
        btnXemchitiet.setText("Xem chi tiết");
        btnXemchitiet.setPreferredSize(new java.awt.Dimension(125, 31));
        btnXemchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemchitietActionPerformed(evt);
            }
        });
        jPanel20.add(btnXemchitiet);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel20.add(btnTimkiem);

        btnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });
        jPanel20.add(btnLammoi);

        pnTop3.add(jPanel20, java.awt.BorderLayout.CENTER);

        pnPhieuNhap.add(pnTop3, java.awt.BorderLayout.PAGE_START);

        pnCenter3.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter3.setPreferredSize(new java.awt.Dimension(161, 450));

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu Nhập", "Mã Nhà Cung Cấp", "Mã Nhân Viên", "Ngày Nhập", "Giờ Nhập", "Tổng tiền"
            }
        ));
        tblPhieuNhap.setShowGrid(true);
        tblPhieuNhap.setSurrendersFocusOnKeystroke(true);
        jScrollPane8.setViewportView(tblPhieuNhap);

        javax.swing.GroupLayout pnCenter3Layout = new javax.swing.GroupLayout(pnCenter3);
        pnCenter3.setLayout(pnCenter3Layout);
        pnCenter3Layout.setHorizontalGroup(
            pnCenter3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCenter3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1349, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnCenter3Layout.setVerticalGroup(
            pnCenter3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCenter3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pnPhieuNhap.add(pnCenter3, java.awt.BorderLayout.CENTER);

        add(pnPhieuNhap, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void txtNgayLap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLap1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLap1ActionPerformed

    private void txtTongtien2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongtien2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongtien2ActionPerformed

    private void btnXemchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemchitietActionPerformed
        // TODO add your handling code here:
        HienThiDSCTPN(tblPhieuNhap.getValueAt(tblPhieuNhap.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_btnXemchitietActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        Timkiem();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        Reload();
    }//GEN-LAST:event_btnLammoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXemchitiet;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JComboBox<String> cmbNCC;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel pnCenter3;
    private javax.swing.JPanel pnPhieuNhap;
    private javax.swing.JPanel pnTop3;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTextField txtNgayLap1;
    private javax.swing.JTextField txtNgaylap2;
    private javax.swing.JTextField txtTongTien1;
    private javax.swing.JTextField txtTongtien2;
    // End of variables declaration//GEN-END:variables
}
