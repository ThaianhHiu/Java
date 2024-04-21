/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.SP_BUS;
import DTO.SP_DTO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class pnSanPham extends javax.swing.JPanel {

    SP_BUS spbus = new SP_BUS();

    public pnSanPham() {
        initComponents();
        loadcombo();
        loaddata();
        txtHienthiMa1.setEnabled(false);
    }

    private void loadcombo(){
        ArrayList<String> arr = new ArrayList<String>();
        arr = spbus.getMaLoaiSP();
        for(int i = 0; i < arr.size(); i++){
            cmbLoaiSP.addItem(arr.get(i));
        }
    }

    public void reload(){
        txtHienthiMa1.setText("");
        txtHienthiLoai1.setText("");
        txtHienthiTen1.setText("");
        txtHienthiGia1.setText("");
        txtHienthiSoluong1.setText("");
        txtHienthiHinhAnh.setText("");
        txtHienthiNgaySX.setText("");
        txtHienthiNgayHH.setText("");
        txtHienthiNoiSX.setText("");
        txtTimkiem.setText("");
        cmbLoaiSP.setSelectedIndex(0);
        anh.setIcon(null);
        if (SP_tblsp.getSelectedRow() >= 0) {
            SP_tblsp.removeRowSelectionInterval(SP_tblsp.getSelectedRow(), SP_tblsp.getSelectedRow());
        }
        loaddata();
    }
    public void loaddata(){
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Mã SP");
        dtm.addColumn("Mã Loại");
        dtm.addColumn("Tên SP");
        dtm.addColumn("Đơn giá");
        dtm.addColumn("Số lượng");
        dtm.addColumn("Hình ảnh");
        dtm.addColumn("Nơi SX");
        dtm.addColumn("Ngày SX");
        dtm.addColumn("Ngày hết hạn");
        dtm.addColumn("isDelete");
        SP_tblsp.setModel(dtm);
        ArrayList<SP_DTO> arr = new ArrayList<SP_DTO>();
        arr = spbus.getAllSP();
        for(int i = 0; i < arr.size(); i++){
                SP_DTO em = arr.get(i);
                String maSP = em.getMaSP();
                String maLoai = em.getMaLoaiSP();
                String tenSP = em.getTenSP();
                float donGia = em.getDonGia();
                int soLuong = em.getSoLuong();
                String hinhAnh = em.getHinhAnh();
                String noiSX = em.getNoiSX();
                String ngaySX = em.getNgaySX();
                String ngayHetHan = em.getNgayHH();
                int isDelete = em.getIsDelete();
                Object[] row = {maSP, maLoai, tenSP, donGia, soLuong, hinhAnh, noiSX, ngaySX, ngayHetHan, isDelete };
                dtm.addRow(row);
        }
        SP_tblsp.setModel(dtm);
    
    
        SP_tblsp.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = SP_tblsp.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    txtHienthiMa1.setText(SP_tblsp.getValueAt(selectedRow, 0).toString());
                    txtHienthiMa1.setEnabled(false);
                    txtHienthiLoai1.setText(SP_tblsp.getValueAt(selectedRow, 1).toString());
                    txtHienthiTen1.setText(SP_tblsp.getValueAt(selectedRow, 2).toString());
                    txtHienthiGia1.setText(SP_tblsp.getValueAt(selectedRow, 3).toString());
                    txtHienthiSoluong1.setText(SP_tblsp.getValueAt(selectedRow, 4).toString());
                    txtHienthiHinhAnh.setText(SP_tblsp.getValueAt(selectedRow, 5).toString());
                    txtHienthiNoiSX.setText(SP_tblsp.getValueAt(selectedRow, 6).toString());
                    txtHienthiNgaySX.setText(SP_tblsp.getValueAt(selectedRow, 7).toString());
                    txtHienthiNgayHH.setText(SP_tblsp.getValueAt(selectedRow, 8).toString());
                    String imagePath = "src/image/" + SP_tblsp.getValueAt(selectedRow, 5).toString() + ".png";
                    Icon icon = createIconFromPath(imagePath);
                    anh.setIcon(icon);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });   
    }  
    
    private static Icon createIconFromPath(String imagePath) {
        // Tạo biến Icon
        Icon icon = new ImageIcon(imagePath);
        return icon;
    }
    
    private void themSP(){
    try {
    
        if (
        txtHienthiLoai1.getText().trim().equals("") ||
        txtHienthiGia1.getText().trim().equals("") ||
        txtHienthiHinhAnh.getText().trim().equals("") ||
        txtHienthiNgaySX.getText().trim().equals("") ||
        txtHienthiNgayHH.getText().trim().equals("") ||
        txtHienthiNoiSX.getText().trim().equals("") )
        JOptionPane.showMessageDialog(this, "Please fill in all fields ");
    
        else {
        SP_BUS spbus = new SP_BUS();
        SP_DTO sp = new SP_DTO();
        sp.setMaSP(txtHienthiMa1.getText());
        sp.setMaLoaiSP(txtHienthiLoai1.getText());
        sp.setTenSP(txtHienthiTen1.getText());
        sp.setDonGia(Float.parseFloat(txtHienthiGia1.getText()));
        sp.setSoLuong(Integer.parseInt(txtHienthiSoluong1.getText()));
        sp.setHinhAnh(txtHienthiHinhAnh.getText());
        sp.setNgaySX(txtHienthiNgaySX.getText());
        sp.setNgayHH(txtHienthiNgayHH.getText());
        sp.setNoiSX(txtHienthiNoiSX.getText());
        sp.setIsDelete(1);
        spbus.addsanpham(sp);
        loaddata();
        reload();
        }
        } catch (NumberFormatException ex) { }
    }
    
    private void suaSP(){
    try {
        if (
        txtHienthiLoai1.getText().trim().equals("") ||
        txtHienthiGia1.getText().trim().equals("") ||
        txtHienthiHinhAnh.getText().trim().equals("") ||
        txtHienthiNgaySX.getText().trim().equals("") ||
        txtHienthiNgayHH.getText().trim().equals("") ||
        txtHienthiNoiSX.getText().trim().equals("") )
        JOptionPane.showMessageDialog(this, "Please fill in all fields ");
        else {
        SP_BUS spbus = new SP_BUS();
        SP_DTO sp = new SP_DTO();
        sp.setMaSP(txtHienthiMa1.getText());
        sp.setMaLoaiSP(txtHienthiLoai1.getText());
        sp.setTenSP(txtHienthiTen1.getText());
        sp.setDonGia(Float.parseFloat(txtHienthiGia1.getText()));
        sp.setSoLuong(Integer.parseInt(txtHienthiSoluong1.getText()));
        sp.setHinhAnh(txtHienthiHinhAnh.getText());
        sp.setNgaySX(txtHienthiNgaySX.getText());
        sp.setNgayHH(txtHienthiNgayHH.getText());
        sp.setNoiSX(txtHienthiNoiSX.getText());
        sp.setIsDelete(1);
        spbus.updatesanpham(sp);
        loaddata();
        reload();
        }
        } catch (NumberFormatException ex) { }
    }
    
    private void xoaSP(){
        JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this product?","Warning",JOptionPane.YES_NO_OPTION);
        if (SP_tblsp.getSelectedRow() >= 0) {
        SP_BUS spbus = new SP_BUS();
        SP_DTO sp = new SP_DTO();
        sp.setMaSP(SP_tblsp.getValueAt(SP_tblsp.getSelectedRow(), 0).toString());
        sp.setIsDelete(0);
        spbus.deletesanpham(sp);
        loaddata();
        reload();
        }
        else {
        JOptionPane.showMessageDialog(this, "Please select a row");
        }
    }

    // hàm tìm kiếm dựa trên txtTimkiem và cmbLoaiSP , nếu txtTimkiem rỗng thì tìm kiếm theo cmbLoaiSP , ngược lại tìm kiếm theo txtTimkiem được điền thì so sánh chuỗi nhập với tên các sản phẩm , lưu ý nếu cmbLoaiSP.setSelectedIndex(0); thì tìm kiếm theo tất cả sản phẩm còn nếu cmbLoaiSP.getSelectedIndex() > 0 thì tìm kiếm theo loại sản phẩm được chọn
    private void timSP(){
        if (txtTimkiem.getText().trim().equals("")) {
        if (cmbLoaiSP.getSelectedIndex() == 0) {
        loaddata();
        }
        else {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Mã SP");
        dtm.addColumn("Mã Loại");
        dtm.addColumn("Tên SP");
        dtm.addColumn("Đơn giá");
        dtm.addColumn("Số lượng");
        dtm.addColumn("Hình ảnh");
        dtm.addColumn("Nơi SX");
        dtm.addColumn("Ngày SX");
        dtm.addColumn("Ngày hết hạn");
        dtm.addColumn("isDelete");
        SP_tblsp.setModel(dtm);
        ArrayList<SP_DTO> arr = new ArrayList<SP_DTO>();
        arr = spbus.getcmbMaLoaiSP(cmbLoaiSP.getSelectedItem().toString());
        for(int i = 0; i < arr.size(); i++){
        SP_DTO em = arr.get(i);
        String maSP = em.getMaSP();
        String maLoai = em.getMaLoaiSP();
        String tenSP = em.getTenSP();
        float donGia = em.getDonGia();
        int soLuong = em.getSoLuong();
        String hinhAnh = em.getHinhAnh();
        String noiSX = em.getNoiSX();
        String ngaySX = em.getNgaySX();
        String ngayHetHan = em.getNgayHH();
        int isDelete = em.getIsDelete();
        Object[] row = {maSP, maLoai, tenSP, donGia, soLuong, hinhAnh, noiSX, ngaySX, ngayHetHan, isDelete };
        dtm.addRow(row);
        }
        SP_tblsp.setModel(dtm);
        SP_tblsp.getSelectionModel().addListSelectionListener(e -> {
        int selectedRow = SP_tblsp.getSelectedRow();
        if (selectedRow >= 0) {
        try {
        txtHienthiMa1.setText(SP_tblsp.getValueAt(selectedRow, 0).toString());
        txtHienthiMa1.setEnabled(false);
        txtHienthiLoai1.setText(SP_tblsp.getValueAt(selectedRow, 1).toString());
        txtHienthiTen1.setText(SP_tblsp.getValueAt(selectedRow, 2).toString());
        txtHienthiGia1.setText(SP_tblsp.getValueAt(selectedRow, 3).toString());
        txtHienthiSoluong1.setText(SP_tblsp.getValueAt(selectedRow, 4).toString());
        txtHienthiHinhAnh.setText(SP_tblsp.getValueAt(selectedRow, 5).toString());
        txtHienthiNoiSX.setText(SP_tblsp.getValueAt(selectedRow, 6).toString());
        txtHienthiNgaySX.setText(SP_tblsp.getValueAt(selectedRow, 7).toString());
        txtHienthiNgayHH.setText(SP_tblsp.getValueAt(selectedRow, 8).toString());
        String imagePath = "src/image/" + SP_tblsp.getValueAt(selectedRow, 5).toString() + ".png";
        Icon icon = createIconFromPath(imagePath);
        anh.setIcon(icon);
        } catch (Exception ex) {
        ex.printStackTrace();
        }
        }
        });
        }
        }
        else {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Mã SP");
        dtm.addColumn("Mã Loại");
        dtm.addColumn("Tên SP");
        dtm.addColumn("Đơn giá");
        dtm.addColumn("Số lượng");
        dtm.addColumn("Hình ảnh");
        dtm.addColumn("Nơi SX");
        dtm.addColumn("Ngày SX");
        dtm.addColumn("Ngày hết hạn");
        dtm.addColumn("isDelete");
        SP_tblsp.setModel(dtm);
        ArrayList<SP_DTO> arr = new ArrayList<SP_DTO>();
        arr = spbus.getAllSP();
        for(int i = 0; i < arr.size(); i++){
        SP_DTO em = arr.get(i);
        String maSP = em.getMaSP();
        String maLoai = em.getMaLoaiSP();
        String tenSP = em.getTenSP();
        float donGia = em.getDonGia();
        int soLuong = em.getSoLuong();
        String hinhAnh = em.getHinhAnh();
        String noiSX = em.getNoiSX();
        String ngaySX = em.getNgaySX();
        String ngayHetHan = em.getNgayHH();
        int isDelete = em.getIsDelete();
        if (tenSP.toLowerCase().contains(txtTimkiem.getText().toLowerCase())) {
        Object[] row = {maSP, maLoai, tenSP, donGia, soLuong, hinhAnh, noiSX, ngaySX, ngayHetHan, isDelete };
        dtm.addRow(row);
        }
        }
        SP_tblsp.setModel(dtm);
        SP_tblsp.getSelectionModel().addListSelectionListener(e -> {
        int selectedRow = SP_tblsp.getSelectedRow();
        if (selectedRow >= 0) {
        try {
        txtHienthiMa1.setText(SP_tblsp.getValueAt(selectedRow, 0).toString());
        txtHienthiMa1.setEnabled(false);
        txtHienthiLoai1.setText(SP_tblsp.getValueAt(selectedRow, 1).toString());
        txtHienthiTen1.setText(SP_tblsp.getValueAt(selectedRow, 2).toString());
        txtHienthiGia1.setText(SP_tblsp.getValueAt(selectedRow, 3).toString());
        txtHienthiSoluong1.setText(SP_tblsp.getValueAt(selectedRow, 4).toString());
        txtHienthiHinhAnh.setText(SP_tblsp.getValueAt(selectedRow, 5).toString());
        txtHienthiNoiSX.setText(SP_tblsp.getValueAt(selectedRow, 6).toString());
        txtHienthiNgaySX.setText(SP_tblsp.getValueAt(selectedRow, 7).toString());
        txtHienthiNgayHH.setText(SP_tblsp.getValueAt(selectedRow, 8).toString());
        String imagePath = "src/image/" + SP_tblsp.getValueAt(selectedRow, 5).toString() + ".png";
        Icon icon = createIconFromPath(imagePath);
        anh.setIcon(icon);
        } catch (Exception ex) {
        ex.printStackTrace();
        }
        }
        });
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

        pnSanpham = new javax.swing.JPanel();
        pnTop2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        btnNhapEx = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        cmbLoaiSP = new javax.swing.JComboBox<>();
        txtTimkiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jTextField20 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        pnBottom1 = new javax.swing.JPanel();
        anh = new javax.swing.JLabel();
        txtHienthiMa1 = new javax.swing.JTextField();
        txtHienthiLoai1 = new javax.swing.JTextField();
        txtHienthiTen1 = new javax.swing.JTextField();
        txtHienthiGia1 = new javax.swing.JTextField();
        txtHienthiSoluong1 = new javax.swing.JTextField();
        txtHienthiHinhAnh = new javax.swing.JTextField();
        txtHienthiNoiSX = new javax.swing.JTextField();
        txtHienthiNgaySX = new javax.swing.JTextField();
        txtHienthiNgayHH = new javax.swing.JTextField();
        pnCenter2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        SP_tblsp = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1373, 700));
        setLayout(new java.awt.BorderLayout());

        pnSanpham.setLayout(new java.awt.BorderLayout());

        pnTop2.setBackground(new java.awt.Color(204, 255, 255));
        pnTop2.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop2.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(153, 255, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add1.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel14.add(btnThem);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel14.add(btnXoa);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel14.add(btnSua);

        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatEx.setText("Xuất Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel14.add(btnXuatEx);

        btnNhapEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhapEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnNhapEx.setText("Nhập Excel ");
        btnNhapEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExActionPerformed(evt);
            }
        });
        jPanel14.add(btnNhapEx);

        pnTop2.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(153, 255, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel16.setBackground(new java.awt.Color(204, 255, 255));
        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cmbLoaiSP.setBackground(new java.awt.Color(204, 255, 255));
        cmbLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));

        txtTimkiem.setBackground(new java.awt.Color(153, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 102));
        jLabel8.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTimkiem)))
                .addContainerGap())
        );

        jPanel15.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(204, 255, 255));
        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField18.setBackground(new java.awt.Color(153, 255, 255));
        jTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jTextField19.setBackground(new java.awt.Color(153, 255, 255));
        jTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("Số lượng");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel15.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(204, 255, 255));
        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField20.setBackground(new java.awt.Color(153, 255, 255));
        jTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Đơn giá");

        jTextField21.setBackground(new java.awt.Color(153, 255, 255));
        jTextField21.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel15.add(jPanel18);

        btnTim.setBackground(new java.awt.Color(204, 255, 255));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });
        jPanel15.add(btnTim);

        btnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });
        jPanel15.add(btnLammoi);

        pnTop2.add(jPanel15, java.awt.BorderLayout.CENTER);

        pnSanpham.add(pnTop2, java.awt.BorderLayout.PAGE_START);

        pnBottom1.setBackground(new java.awt.Color(255, 255, 255));
        pnBottom1.setMinimumSize(new java.awt.Dimension(100, 150));
        pnBottom1.setPreferredSize(new java.awt.Dimension(603, 166));
        pnBottom1.add(anh);

        txtHienthiMa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã SP"));
        txtHienthiMa1.setPreferredSize(new java.awt.Dimension(80, 60));
        pnBottom1.add(txtHienthiMa1);

        txtHienthiLoai1.setBorder(javax.swing.BorderFactory.createTitledBorder("Loại SP"));
        txtHienthiLoai1.setPreferredSize(new java.awt.Dimension(90, 60));
        txtHienthiLoai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHienthiLoai1ActionPerformed(evt);
            }
        });
        pnBottom1.add(txtHienthiLoai1);

        txtHienthiTen1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên SP"));
        txtHienthiTen1.setPreferredSize(new java.awt.Dimension(160, 60));
        pnBottom1.add(txtHienthiTen1);

        txtHienthiGia1.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn giá"));
        txtHienthiGia1.setPreferredSize(new java.awt.Dimension(120, 60));
        pnBottom1.add(txtHienthiGia1);

        txtHienthiSoluong1.setBorder(javax.swing.BorderFactory.createTitledBorder("Số lượng"));
        txtHienthiSoluong1.setPreferredSize(new java.awt.Dimension(120, 60));
        pnBottom1.add(txtHienthiSoluong1);

        txtHienthiHinhAnh.setBorder(javax.swing.BorderFactory.createTitledBorder("Hình ảnh"));
        txtHienthiHinhAnh.setPreferredSize(new java.awt.Dimension(120, 60));
        pnBottom1.add(txtHienthiHinhAnh);

        txtHienthiNoiSX.setBorder(javax.swing.BorderFactory.createTitledBorder("Nơi SX"));
        txtHienthiNoiSX.setPreferredSize(new java.awt.Dimension(160, 60));
        txtHienthiNoiSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHienthiNoiSXActionPerformed(evt);
            }
        });
        pnBottom1.add(txtHienthiNoiSX);

        txtHienthiNgaySX.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày SX"));
        txtHienthiNgaySX.setPreferredSize(new java.awt.Dimension(130, 60));
        pnBottom1.add(txtHienthiNgaySX);

        txtHienthiNgayHH.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày HH"));
        txtHienthiNgayHH.setPreferredSize(new java.awt.Dimension(130, 60));
        pnBottom1.add(txtHienthiNgayHH);

        pnSanpham.add(pnBottom1, java.awt.BorderLayout.PAGE_END);

        pnCenter2.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter2.setPreferredSize(new java.awt.Dimension(161, 300));

        SP_tblsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Mã Loại", "Tên", "Đơn giá", "Số lượng", "Hình ảnh", "Nơi SX", "Ngày SX", "Ngày hết hạn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SP_tblsp.setShowGrid(true);
        SP_tblsp.setSurrendersFocusOnKeystroke(true);
        jScrollPane7.setViewportView(SP_tblsp);

        javax.swing.GroupLayout pnCenter2Layout = new javax.swing.GroupLayout(pnCenter2);
        pnCenter2.setLayout(pnCenter2Layout);
        pnCenter2Layout.setHorizontalGroup(
            pnCenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCenter2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnCenter2Layout.setVerticalGroup(
            pnCenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCenter2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pnSanpham.add(pnCenter2, java.awt.BorderLayout.CENTER);

        add(pnSanpham, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        suaSP();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void btnNhapExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNhapExActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void txtHienthiNoiSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHienthiNoiSXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHienthiNoiSXActionPerformed
    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        reload();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void txtHienthiLoai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHienthiLoai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHienthiLoai1ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        themSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        timSP();
    }//GEN-LAST:event_btnTimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable SP_tblsp;
    private javax.swing.JLabel anh;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnNhapEx;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JComboBox<String> cmbLoaiSP;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JPanel pnBottom1;
    private javax.swing.JPanel pnCenter2;
    private javax.swing.JPanel pnSanpham;
    private javax.swing.JPanel pnTop2;
    private javax.swing.JTextField txtHienthiGia1;
    private javax.swing.JTextField txtHienthiHinhAnh;
    private javax.swing.JTextField txtHienthiLoai1;
    private javax.swing.JTextField txtHienthiMa1;
    private javax.swing.JTextField txtHienthiNgayHH;
    private javax.swing.JTextField txtHienthiNgaySX;
    private javax.swing.JTextField txtHienthiNoiSX;
    private javax.swing.JTextField txtHienthiSoluong1;
    private javax.swing.JTextField txtHienthiTen1;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
