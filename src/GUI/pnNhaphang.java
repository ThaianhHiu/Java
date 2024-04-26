/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import BUS.CTPN_BUS;
import BUS.NV_BUS;
import BUS.PhieuNhap_BUS;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;
import DTO.SP_DTO;
import BUS.NCC_BUS;
import DTO.NCC_DTO;

import java.util.ArrayList;


/**
 *
 * @author sinht
 */
public class pnNhaphang extends javax.swing.JPanel {

    /**
     * Creates new form pnNhaphang
     */


    public pnNhaphang() {
        initComponents();
        showTime();
        loadMaNV();
        loadMaNCC();
        taoMaPN();
        txtGioLap.setEnabled(false);
        txtNgayLap.setEnabled(false);
        txtMaPN.setEnabled(false);
        txtTenNV.setEnabled(false);
        txtTenNCC.setEnabled(false);
        txtTongTien.setEnabled(false);
    }


     public void showTime() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                txtNgayLap.setText(dtf.format(now));
                DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now1 = LocalDateTime.now();
                txtGioLap.setText(dtf1.format(now1));
            }
        });
        timer.start();
    }

    // nhập thông tin các sản phẩm từ file excel vào bảng sản phẩm nhập
    public void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (path == null) {
            return;
        }
        try {
            Workbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            DefaultTableModel model = (DefaultTableModel) tblSPnhap.getModel();
            model.setRowCount(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String maSP = row.getCell(0).getStringCellValue();
                String loaiSP = row.getCell(1).getStringCellValue();
                String tenSP = row.getCell(2).getStringCellValue();
                double donGia = row.getCell(3).getNumericCellValue();
                int soLuong = (int) row.getCell(4).getNumericCellValue();
                String hinhAnh = row.getCell(5).getStringCellValue();
                model.addRow(new Object[]{maSP, loaiSP, tenSP, donGia, soLuong, hinhAnh});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi nhập file");
        }
    } 


    // thêm sản phẩm vào bảng chi tiết phiếu nhập
    public void themSP() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        int row = tblSPnhap.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần thêm");
            return;
        }
        String maSP = tblSPnhap.getValueAt(row, 0).toString();
        String loaiSP = tblSPnhap.getValueAt(row, 1).toString();
        String tenSP = tblSPnhap.getValueAt(row, 2).toString();
        double donGia = Double.parseDouble(tblSPnhap.getValueAt(row, 3).toString());
        int soLuong = Integer.parseInt(tblSPnhap.getValueAt(row, 4).toString());
        model.addRow(new Object[]{maSP, loaiSP, tenSP, soLuong, donGia, donGia * soLuong});
        tblchitietphieunhap.setModel(model);
        tinhTongTien();
    }

    // khi nhấn nút xóa thì sẽ xóa sản phẩm khỏi bảng chi tiết phiếu nhập hàng
    public void xoaSP() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        int row = tblchitietphieunhap.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần xóa");
            return;
        }
        model.removeRow(row);
        tblchitietphieunhap.setModel(model);
        tinhTongTien();
    }

    // khi nhấn nút sửa thì sẽ hiện lên nhập vào số lượng mới
    public void suaSP() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        int row = tblchitietphieunhap.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần sửa");
            return;
        }
        int soLuongMoi = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng mới"));
        model.setValueAt(soLuongMoi, row, 3);
        model.setValueAt(soLuongMoi * Float.parseFloat(model.getValueAt(row, 4).toString()), row, 5);
        tblchitietphieunhap.setModel(model);
        tinhTongTien();
    }

    // làm mới bảng chi tiết phiếu nhập hàng
    public void reload() {
       tblchitietphieunhap.clearSelection();
    }

    public void loadMaNV() {
        NV_BUS nvBUS = new NV_BUS();
        ArrayList<String> listTenNV = nvBUS.getAllMaNV();
        for (String tenNV : listTenNV) {
            cmbTenNV.addItem(tenNV);
        }
    }
    
    public void loadMaNCC() {
        NCC_BUS nccBUS = new NCC_BUS();
        ArrayList<String> listTenNCC = nccBUS.getAllMaNCC();
        for (String tenNCC : listTenNCC) {
            cmbNCC.addItem(tenNCC);
        }
    }
    
    // khi chọn mã nhân viên thì hiển thị tên nhân viên
    public void loadTenNVTheoMaNV() {
        NV_BUS nvBUS = new NV_BUS();
        String maNV = cmbTenNV.getSelectedItem().toString();
        String tenNV = nvBUS.getTenNV(maNV);
        txtTenNV.setText(tenNV);
    }

    // khi chọn mã nhà cung cấp thì hiển thị tên nhà cung cấp
    public void loadTenNCCTheoMaNCC() {
        NCC_BUS nccBUS = new NCC_BUS();
        String maNCC = cmbNCC.getSelectedItem().toString();
        String tenNCC = nccBUS.getTenNCC(maNCC);
        txtTenNCC.setText(tenNCC);
    }

    // tính tổng tiền của phiếu nhập hàng 
    public void tinhTongTien() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        double tongTien = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            tongTien += Double.parseDouble(model.getValueAt(i, 5).toString());
        }
        txtTongTien.setText(String.valueOf(tongTien));
    }

    // lấy mã phiếu nhập hàng gắn vào txtMaPN
    public void taoMaPN() {
        PhieuNhap_BUS pnBUS = new PhieuNhap_BUS();
        txtMaPN.setText(pnBUS.autoGenerateMaPN());
    }

    // lưu thông tin phiếu nhập hàng vào database
    public void luuPhieuNhap() {
        PhieuNhap_BUS pnBUS = new PhieuNhap_BUS();
        PhieuNhap_DTO pnDTO = new PhieuNhap_DTO();
        pnDTO.setMaPN(txtMaPN.getText());
        pnDTO.setMaNV(cmbTenNV.getSelectedItem().toString());
        pnDTO.setMaNCC(cmbNCC.getSelectedItem().toString());
        pnDTO.setNgayNhap(txtNgayLap.getText());
        pnDTO.setGioNhap(txtGioLap.getText());
        pnDTO.setTongTien(Float.parseFloat(txtTongTien.getText()));
        pnBUS.addPhieuNhap(pnDTO);
        luuCTPN();
        JOptionPane.showMessageDialog(null, "Nhập hàng thành công");
        taoMaPN();
        huyHoaDon();
    }

    // lưu thông tin chi tiết phiếu nhập hàng vào database
    public void luuCTPN() {
        CTPN_BUS ctpnBUS = new CTPN_BUS();
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            CTPN_DTO ctpnDTO = new CTPN_DTO();
            ctpnDTO.setMaPN(txtMaPN.getText());
            ctpnDTO.setMaSP(model.getValueAt(i, 0).toString());
            ctpnDTO.setSoLuong(Integer.parseInt(model.getValueAt(i, 3).toString()));
            ctpnDTO.setDonGia(Float.parseFloat(model.getValueAt(i, 4).toString()));
            ctpnBUS.insertCTPN(ctpnDTO);
        }
    }
    // hủy tất cả các sản phẩm trên bảng chi tiết phiếu nhập hàng cập nhật lại số lượng cũng như tổng tiền
    public void huyHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
        model.setRowCount(0);
        txtTongTien.setText("");
    }
    
    // in hóa đơn nhập hàng sang pdf 
    public void inHoaDon() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("HoaDonNhapHang.pdf"));
            writer.write("Mã phiếu nhập: " + txtMaPN.getText());
            writer.newLine();
            writer.write("Tên nhân viên: " + txtTenNV.getText());
            writer.newLine();
            writer.write("Tên nhà cung cấp: " + txtTenNCC.getText());
            writer.newLine();
            writer.write("Ngày nhập: " + txtNgayLap.getText());
            writer.newLine();
            writer.write("Giờ nhập: " + txtGioLap.getText());
            writer.newLine();
            writer.write("Tổng tiền: " + txtTongTien.getText());
            writer.newLine();
            writer.write("Danh sách sản phẩm: ");
            writer.newLine();
            DefaultTableModel model = (DefaultTableModel) tblchitietphieunhap.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(model.getValueAt(i, 0).toString() + " - " + model.getValueAt(i, 2).toString() + " - " + model.getValueAt(i, 3).toString() + " - " + model.getValueAt(i, 4).toString() + " - " + model.getValueAt(i, 5).toString());
                writer.newLine();
            }
            writer.close();
            JOptionPane.showMessageDialog(null, "In hóa đơn thành công");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi in hóa đơn");
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

        pnNhaphang = new javax.swing.JPanel();
        jScrollBar2 = new javax.swing.JScrollBar();
        pnInHĐ1 = new javax.swing.JPanel();
        pnTTHĐ1 = new javax.swing.JPanel();
        txtMaPN = new javax.swing.JTextField();
        lbMaHĐ1 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        lbThanhtien1 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
        lbTenKH1 = new javax.swing.JLabel();
        lbTenNV1 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        lbNgayLap1 = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        lbGioLap1 = new javax.swing.JLabel();
        txtGioLap = new javax.swing.JTextField();
        cmbNCC = new javax.swing.JComboBox<>();
        cmbTenNV = new javax.swing.JComboBox<>();
        pnLsSPmua1 = new javax.swing.JPanel();
        pnLsSPdachon1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblchitietphieunhap = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        pnThanhtoan1 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnNhaphang = new javax.swing.JButton();
        pnLsSP1 = new javax.swing.JPanel();
        pnTimkiem1 = new javax.swing.JPanel();
        btnNhapEx = new javax.swing.JButton();
        pnHienthiSP1 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        pnLsBanh1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSPnhap = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnNhaphang.setBackground(new java.awt.Color(255, 255, 255));
        pnNhaphang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnNhaphang.setLayout(new java.awt.BorderLayout());
        pnNhaphang.add(jScrollBar2, java.awt.BorderLayout.LINE_START);

        pnInHĐ1.setBackground(new java.awt.Color(255, 255, 204));
        pnInHĐ1.setPreferredSize(new java.awt.Dimension(400, 631));
        pnInHĐ1.setLayout(new java.awt.BorderLayout());

        pnTTHĐ1.setPreferredSize(new java.awt.Dimension(400, 185));

        txtMaPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPNActionPerformed(evt);
            }
        });

        lbMaHĐ1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbMaHĐ1.setText("Mã PN");

        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });

        lbThanhtien1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbThanhtien1.setText("Tổng tiền");

        txtTenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNCCActionPerformed(evt);
            }
        });

        lbTenKH1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTenKH1.setText("Tên NCC");

        lbTenNV1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTenNV1.setText("Tên NV");

        lbNgayLap1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbNgayLap1.setText("Ngày nhập");

        txtNgayLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLapActionPerformed(evt);
            }
        });

        lbGioLap1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbGioLap1.setText("Giờ nhập");

        cmbNCC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbNCCItemStateChanged(evt);
            }
        });

        cmbTenNV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenNVItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnTTHĐ1Layout = new javax.swing.GroupLayout(pnTTHĐ1);
        pnTTHĐ1.setLayout(pnTTHĐ1Layout);
        pnTTHĐ1Layout.setHorizontalGroup(
            pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                        .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbTenNV1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(lbMaHĐ1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTenKH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                                        .addComponent(txtMaPN, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbThanhtien1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lbGioLap1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtGioLap, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTTHĐ1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))))
                    .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                        .addComponent(lbNgayLap1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnTTHĐ1Layout.setVerticalGroup(
            pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTHĐ1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMaHĐ1)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbThanhtien1))
                .addGap(18, 18, 18)
                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTenKH1)
                    .addComponent(cmbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenNV1)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(pnTTHĐ1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNgayLap1)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbGioLap1))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pnInHĐ1.add(pnTTHĐ1, java.awt.BorderLayout.PAGE_START);

        pnLsSPmua1.setBackground(new java.awt.Color(255, 255, 153));
        pnLsSPmua1.setLayout(new java.awt.BorderLayout());

        pnLsSPdachon1.setBackground(new java.awt.Color(255, 255, 153));

        tblchitietphieunhap.setBorder(new javax.swing.border.MatteBorder(null));
        tblchitietphieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Mã Loại", "Tên", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        tblchitietphieunhap.setShowGrid(true);
        tblchitietphieunhap.setSurrendersFocusOnKeystroke(true);
        jScrollPane3.setViewportView(tblchitietphieunhap);

        btnXoa.setBackground(new java.awt.Color(255, 51, 51));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(51, 255, 204));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(204, 255, 204));
        btnReload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReload.setText("Làm mới");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnLsSPdachon1Layout = new javax.swing.GroupLayout(pnLsSPdachon1);
        pnLsSPdachon1.setLayout(pnLsSPdachon1Layout);
        pnLsSPdachon1Layout.setHorizontalGroup(
            pnLsSPdachon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLsSPdachon1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnReload)
                .addContainerGap())
        );
        pnLsSPdachon1Layout.setVerticalGroup(
            pnLsSPdachon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLsSPdachon1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addGroup(pnLsSPdachon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnReload))
                .addGap(17, 17, 17))
        );

        pnLsSPmua1.add(pnLsSPdachon1, java.awt.BorderLayout.PAGE_START);

        pnThanhtoan1.setBackground(new java.awt.Color(0, 0, 0));
        pnThanhtoan1.setPreferredSize(new java.awt.Dimension(200, 20));

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnNhaphang.setBackground(new java.awt.Color(255, 204, 255));
        btnNhaphang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNhaphang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/import (1).png"))); // NOI18N
        btnNhaphang.setText("Nhập hàng");
        btnNhaphang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaphangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThanhtoan1Layout = new javax.swing.GroupLayout(pnThanhtoan1);
        pnThanhtoan1.setLayout(pnThanhtoan1Layout);
        pnThanhtoan1Layout.setHorizontalGroup(
            pnThanhtoan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThanhtoan1Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(btnHuy)
                .addGap(27, 27, 27)
                .addComponent(btnNhaphang)
                .addContainerGap())
        );
        pnThanhtoan1Layout.setVerticalGroup(
            pnThanhtoan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThanhtoan1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnThanhtoan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNhaphang, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy))
                .addContainerGap())
        );

        pnLsSPmua1.add(pnThanhtoan1, java.awt.BorderLayout.CENTER);

        pnInHĐ1.add(pnLsSPmua1, java.awt.BorderLayout.CENTER);

        pnNhaphang.add(pnInHĐ1, java.awt.BorderLayout.LINE_END);

        pnLsSP1.setBackground(new java.awt.Color(0, 255, 204));
        pnLsSP1.setLayout(new java.awt.BorderLayout());

        pnTimkiem1.setBackground(new java.awt.Color(204, 255, 204));
        pnTimkiem1.setPreferredSize(new java.awt.Dimension(597, 50));

        btnNhapEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhapEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnNhapEx.setText("Nhập Excel ");
        btnNhapEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExActionPerformed(evt);
            }
        });
        pnTimkiem1.add(btnNhapEx);

        pnLsSP1.add(pnTimkiem1, java.awt.BorderLayout.PAGE_START);

        pnHienthiSP1.setPreferredSize(new java.awt.Dimension(200, 225));
        pnHienthiSP1.setLayout(new java.awt.BorderLayout());

        btnThemSP.setBackground(new java.awt.Color(0, 102, 102));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(0, 51, 51));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        btnThemSP.setText("Thêm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });
        pnHienthiSP1.add(btnThemSP, java.awt.BorderLayout.CENTER);

        pnLsSP1.add(pnHienthiSP1, java.awt.BorderLayout.PAGE_END);

        pnLsBanh1.setBackground(new java.awt.Color(51, 255, 204));
        pnLsBanh1.setPreferredSize(new java.awt.Dimension(620, 800));

        tblSPnhap.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Loại", "Tên", "Đơn giá", "Số lượng", "Hình ảnh"
            }
        ));
        tblSPnhap.setSelectionBackground(new java.awt.Color(102, 204, 255));
        tblSPnhap.setShowGrid(true);
        tblSPnhap.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(tblSPnhap);

        javax.swing.GroupLayout pnLsBanh1Layout = new javax.swing.GroupLayout(pnLsBanh1);
        pnLsBanh1.setLayout(pnLsBanh1Layout);
        pnLsBanh1Layout.setHorizontalGroup(
            pnLsBanh1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLsBanh1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnLsBanh1Layout.setVerticalGroup(
            pnLsBanh1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLsBanh1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnLsSP1.add(pnLsBanh1, java.awt.BorderLayout.CENTER);

        pnNhaphang.add(pnLsSP1, java.awt.BorderLayout.CENTER);

        add(pnNhaphang, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPNActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

    private void txtTenNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNCCActionPerformed

    private void txtNgayLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        huyHoaDon();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnNhaphangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhaphangActionPerformed
        // TODO add your handling code here:
        luuPhieuNhap();
        inHoaDon();
    }//GEN-LAST:event_btnNhaphangActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        themSP();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnNhapExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExActionPerformed
        // TODO add your handling code here:
        importExcel();
    }//GEN-LAST:event_btnNhapExActionPerformed

    private void cmbNCCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbNCCItemStateChanged
        // TODO add your handling code here:
        // khi chọn mã nhà cung cấp thì hiển thị tên nhà cung cấp
        loadTenNCCTheoMaNCC();
    }//GEN-LAST:event_cmbNCCItemStateChanged

    private void cmbTenNVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenNVItemStateChanged
        // TODO add your handling code here:
        // khi chọn mã nhân viên thì hiển thị tên nhân viên
        loadTenNVTheoMaNV();
    }//GEN-LAST:event_cmbTenNVItemStateChanged

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        suaSP();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        // TODO add your handling code here:
        reload();
    }//GEN-LAST:event_btnReloadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnNhapEx;
    private javax.swing.JButton btnNhaphang;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbNCC;
    private javax.swing.JComboBox<String> cmbTenNV;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbGioLap1;
    private javax.swing.JLabel lbMaHĐ1;
    private javax.swing.JLabel lbNgayLap1;
    private javax.swing.JLabel lbTenKH1;
    private javax.swing.JLabel lbTenNV1;
    private javax.swing.JLabel lbThanhtien1;
    private javax.swing.JPanel pnHienthiSP1;
    private javax.swing.JPanel pnInHĐ1;
    private javax.swing.JPanel pnLsBanh1;
    private javax.swing.JPanel pnLsSP1;
    private javax.swing.JPanel pnLsSPdachon1;
    private javax.swing.JPanel pnLsSPmua1;
    private javax.swing.JPanel pnNhaphang;
    private javax.swing.JPanel pnTTHĐ1;
    private javax.swing.JPanel pnThanhtoan1;
    private javax.swing.JPanel pnTimkiem1;
    private javax.swing.JTable tblSPnhap;
    private javax.swing.JTable tblchitietphieunhap;
    private javax.swing.JTextField txtGioLap;
    private javax.swing.JTextField txtMaPN;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
