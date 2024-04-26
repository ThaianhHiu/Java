/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;
import javax.swing.table.DefaultTableModel;
import BUS.HoaDon_BUS;
import DTO.HoaDon_DTO;
import BUS.CTHD_BUS;
import DTO.CTHD_DTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import BUS.NV_BUS;
import DTO.NV_DTO;
/**
 *
 * @author sinht
 */
public class pnHoaDon extends javax.swing.JPanel {

    /**
     * Creates new form pnHoaDon
     */

     HoaDon_BUS bus = new HoaDon_BUS();
    public pnHoaDon() {
        initComponents();
        HienThiDSHoaDon();
        LoadMaNV();
    }

    public void HienThiDSHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
        }
    }

    //load toàn bộ mã nhân viên lên combobox
    public void LoadMaNV() {
        NV_BUS bus = new NV_BUS();
        ArrayList<String> list = bus.getAllMaNV();
        for (String nv : list) {
            cmbMaNV.addItem(nv);
        }
    }

    // khi nhấn vào nút xem chi tiết hóa đơn thì sẽ hiển thị ra các chi tiết hóa đơn đó
    public void HienThiDSCTHD(String mahd) {
       // tôi muốn hiện trên JOptionpane 
        CTHD_BUS bus = new CTHD_BUS();
        ArrayList<CTHD_DTO> list = bus.getAllCTHD(mahd);
        String str = "";
        for (CTHD_DTO cthd : list) {
            str += "Mã hóa đơn: " + cthd.getMaHD() + "\n";
            str += "Mã sản phẩm: " + cthd.getMaSP() + "\n";
            str += "Số lượng: " + cthd.getSoLuong() + "\n";
            str += "Đơn giá: " + cthd.getDonGia() + "\n";
            str += "-----------------------------------\n";
        }
        JOptionPane.showMessageDialog(null, str);
    }

    // reload 
    public void Reload() {
        tblHoaDon.clearSelection();
        cmbMaNV.setSelectedIndex(0);
        HienThiDSHoaDon();
    }


    // tìm kiếm theo mã nhân viên
    public void TimTheoMaNV() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            if (hd.getMaNV().equals(cmbMaNV.getSelectedItem().toString())) {
                model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
            }
        }
    }

    // tìm kiếm theo ngày lập
    public void TimTheoNgayLap() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            if (hd.getNgayLap().compareTo(txtNgayLap1.getText()) >= 0 && hd.getNgayLap().compareTo(txtNgayLap2.getText()) <= 0) {
                model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
            }
        }
    }

    // tìm kiếm theo tổng tiền
    public void TimTheoTongTien() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            if (hd.getTongTien() >= Float.parseFloat(txtTongtien1.getText()) && hd.getTongTien() <= Float.parseFloat(txtTongtien2.getText())) {
                model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
            }
        }

    }

    public void TimTheoNgayLapVaTongTien() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            // Kiểm tra nếu ngày lập nằm trong khoảng từ txtNgayLap1 đến txtNgayLap2
            // và tổng tiền nằm trong khoảng từ txtTongtien1 đến txtTongtien2
            if (hd.getNgayLap().compareTo(txtNgayLap1.getText()) >= 0 &&
                hd.getNgayLap().compareTo(txtNgayLap2.getText()) <= 0 &&
                hd.getTongTien() >= Float.parseFloat(txtTongtien1.getText()) &&
                hd.getTongTien() <= Float.parseFloat(txtTongtien2.getText())) {
                model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
            }
        }
    }

    // tìm kiếm theo mã nhân viên và ngày lập và tổng tiền 
    public void TimKiemTheoAll() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        HoaDon_BUS bus = new HoaDon_BUS();
        for (HoaDon_DTO hd : bus.getAllHoaDon()) {
            if ( (cmbMaNV.getSelectedItem().toString().equals("Tất cả") || hd.getMaNV().equals(cmbMaNV.getSelectedItem().toString())) &&
                hd.getNgayLap().compareTo(txtNgayLap1.getText()) >= 0 &&
                hd.getNgayLap().compareTo(txtNgayLap2.getText()) <= 0 &&
                hd.getTongTien() >= Float.parseFloat(txtTongtien1.getText()) &&
                hd.getTongTien() <= Float.parseFloat(txtTongtien2.getText())) {
                model.addRow(new Object[]{model.getRowCount() + 1, hd.getMaHD(), hd.getMaNV(), hd.getMaKH(), hd.getMaKM(), hd.getNgayLap(), hd.getGioLap(), hd.getTongTien()});
            }
        }
    }
    

    private void xuatExcel(){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product");
            DefaultTableModel dtm = (DefaultTableModel) tblHoaDon.getModel();
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

        pnHoadon = new javax.swing.JPanel();
        pnTop = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnXuatExcel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        cmbMaNV = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtNgayLap1 = new javax.swing.JTextField();
        txtNgayLap2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtTongtien1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTongtien2 = new javax.swing.JTextField();
        btnXemchitietHoadon = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        btnTimkiem = new javax.swing.JButton();
        pnCenter = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnHoadon.setLayout(new java.awt.BorderLayout());

        pnTop.setBackground(new java.awt.Color(204, 255, 255));
        pnTop.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel3.add(btnXuatExcel);

        pnTop.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(153, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 62));

        cmbMaNV.setBackground(new java.awt.Color(204, 255, 255));
        cmbMaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cmbMaNV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMaNVItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 102));
        jLabel4.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 255, 255));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNgayLap1.setBackground(new java.awt.Color(153, 255, 255));
        txtNgayLap1.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));
        txtNgayLap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLap1ActionPerformed(evt);
            }
        });

        txtNgayLap2.setBackground(new java.awt.Color(153, 255, 255));
        txtNgayLap2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("Ngày lập");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 247, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtNgayLap1)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayLap2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayLap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayLap2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(204, 255, 255));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTongtien1.setBackground(new java.awt.Color(153, 255, 255));
        txtTongtien1.setBorder(javax.swing.BorderFactory.createTitledBorder("Từ"));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Tổng tiền");

        txtTongtien2.setBackground(new java.awt.Color(153, 255, 255));
        txtTongtien2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tới"));
        txtTongtien2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongtien2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtTongtien1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addComponent(txtTongtien2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongtien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongtien2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.add(jPanel9);

        btnXemchitietHoadon.setBackground(new java.awt.Color(204, 255, 255));
        btnXemchitietHoadon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemchitietHoadon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/watch.png"))); // NOI18N
        btnXemchitietHoadon.setText("Xem chi tiết");
        btnXemchitietHoadon.setPreferredSize(new java.awt.Dimension(125, 31));
        btnXemchitietHoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemchitietHoadonActionPerformed(evt);
            }
        });
        jPanel2.add(btnXemchitietHoadon);

        btnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnLammoi);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel2.add(btnTimkiem);

        pnTop.add(jPanel2, java.awt.BorderLayout.CENTER);

        pnHoadon.add(pnTop, java.awt.BorderLayout.PAGE_START);

        pnCenter.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter.setPreferredSize(new java.awt.Dimension(161, 450));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
                "STT", "Mã Hóa đơn", "Mã Nhân Viên", "Mã Khách hàng", "Mã Khuyến Mãi", "Ngày Lập", "Giờ Nhập", "Tổng tiền"
            }
        ));
        tblHoaDon.setShowGrid(true);
        tblHoaDon.setSurrendersFocusOnKeystroke(true);
        jScrollPane5.setViewportView(tblHoaDon);

        javax.swing.GroupLayout pnCenterLayout = new javax.swing.GroupLayout(pnCenter);
        pnCenter.setLayout(pnCenterLayout);
        pnCenterLayout.setHorizontalGroup(
            pnCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1460, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnCenterLayout.setVerticalGroup(
            pnCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnHoadon.add(pnCenter, java.awt.BorderLayout.CENTER);

        add(pnHoadon, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        xuatExcel();
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void txtNgayLap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLap1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLap1ActionPerformed

    private void txtTongtien2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongtien2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongtien2ActionPerformed

    private void btnXemchitietHoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemchitietHoadonActionPerformed
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần xem chi tiết");
        } else {
            String mahd = tblHoaDon.getValueAt(row, 1).toString();
            HienThiDSCTHD(mahd);
        }
    }//GEN-LAST:event_btnXemchitietHoadonActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        Reload();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        if(txtNgayLap1.getText().equals("") || txtNgayLap2.getText().equals("") || txtTongtien1.getText().equals("") || txtTongtien2.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin");
        }
        // tìm theo ngày lập nằm trong khoảng txtNgayLap1 và txtNgayLap2 và tìm theo tổng tiền nằm trong khoảng txtTongtien1 và txtTongtien2
        else{
            TimKiemTheoAll();
        }
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void cmbMaNVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMaNVItemStateChanged
        // TODO add your handling code here:
        TimTheoMaNV();
        if(cmbMaNV.getSelectedItem().toString().equals("Tất cả")){
            HienThiDSHoaDon();
        }
    }//GEN-LAST:event_cmbMaNVItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXemchitietHoadon;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cmbMaNV;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnCenter;
    private javax.swing.JPanel pnHoadon;
    private javax.swing.JPanel pnTop;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtNgayLap1;
    private javax.swing.JTextField txtNgayLap2;
    private javax.swing.JTextField txtTongtien1;
    private javax.swing.JTextField txtTongtien2;
    // End of variables declaration//GEN-END:variables
}
