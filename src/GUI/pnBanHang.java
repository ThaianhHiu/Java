/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import DTO.SP_DTO;
import BUS.SP_BUS;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import BUS.LoaiSP_BUS;
import DTO.LoaiSP_DTO;   
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import BUS.CTHD_BUS;
import DTO.CTHD_DTO;
import BUS.HoaDon_BUS;
import DTO.HoaDon_DTO;
import GUI.pnHoaDon;
import BUS.KM_BUS;
import DTO.KM_DTO;
import BUS.NV_BUS;
import DTO.NV_DTO;
import BUS.SP_BUS;
import DTO.SP_DTO;
import BUS.KH_BUS;
import DTO.KH_DTO;


/**
 *
 * @author sinht
 */
public class pnBanHang extends javax.swing.JPanel {

    /**
     * Creates new form pnBanHang
     */
    public pnBanHang() {
        initComponents();
        //set txtNgayLap thanh gia tri co the format thanh DateTime
        txtNgayLap.setText("1999/01/01");
        showTime();
        hienthiSP();
        txtThanhtien.setEnabled(false);
        txtGioLap.setEnabled(false);
        txtNgayLap.setEnabled(false);
        txtTenKH.setEnabled(false);
        txtTenNV.setEnabled(false);
        txtLoaiSP.setEnabled(false);
        txtTenSP.setEnabled(false);
        txtDonGia.setEnabled(false);
        txtThanhtien.setText("0");
        loadMaKM();
        loadTenNV();
        loadTenKH();
        txtMaHĐ.setEnabled(false);
        chonTenNV();
        layMaHoaDon();
    }

    // hàm reload lại các dòng đang được chọn trong bảng sản phẩm và các ô nhập liệu
    public void reload() {
        txtMaSP.setText("");
        txtLoaiSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        anh.setIcon(null);
        tblLsSP.clearSelection();
        hienthiSP();
    }
    // Hàm hiển thị thời gian
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

    // hàm reload lại các dòng đang được chọn trong bảng sản phẩm và các ô nhập liệu
    // Hàm hiển thị danh sách sản phẩm
    public void hienthiSP() {
        txtMaSP.setEnabled(false);
        SP_BUS spBUS = new SP_BUS();
        ArrayList<SP_DTO> listSP = spBUS.danhsachSP();
        DefaultTableModel model = (DefaultTableModel) tblLsSP.getModel();
        model.setRowCount(0);
        for (SP_DTO sp : listSP) {
            model.addRow(new Object[]{sp.getMaSP(), sp.getMaLoaiSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuong(),sp.getHinhAnh()});
        }
        tblLsSP.setModel(model);

        tblLsSP.getSelectionModel().addListSelectionListener((e) -> {
            if (tblLsSP.getSelectedRow() >= 0) {
                txtMaSP.setText(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 0).toString());
                txtLoaiSP.setText(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 1).toString());
                txtTenSP.setText(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 2).toString());
                txtDonGia.setText(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 3).toString());
                txtSoLuong.setText(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 4).toString());
                String imagePath = "src/image/" + tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 5).toString() + ".png";
                Icon icon = createIconFromPath(imagePath);
                anh.setIcon(icon);
            }
        });
    }

    private static Icon createIconFromPath(String imagePath) {
        // Tạo biến Icon
        Icon icon = new ImageIcon(imagePath);
        return icon;
    }

    // hàm tìm kiếm sản phẩm theo txtTimkiem và hiển thị ra bảng sản phẩm
    public void timkiemSP() {
        SP_BUS spBUS = new SP_BUS();
        ArrayList<SP_DTO> listSP = spBUS.timkiemSP(txtTimkiem.getText());
        DefaultTableModel model = (DefaultTableModel) tblLsSP.getModel();
        model.setRowCount(0);
        for (SP_DTO sp : listSP) {
            model.addRow(new Object[]{sp.getMaSP(), sp.getMaLoaiSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuong(),sp.getHinhAnh()});
        }
        tblLsSP.setModel(model);
    }



    public void themSPvaoHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        float a = Float.parseFloat(txtSoLuong.getText());
        float b = Float.parseFloat(txtDonGia.getText());
        float c = a * b;
        txtThanhtien.setText(String.valueOf(c));
        try {
                if (Integer.parseInt(txtSoLuong.getText()) > Integer.parseInt(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 4).toString())) {
                    JOptionPane.showMessageDialog(this, "Số lượng nhập vào lớn hơn số lượng tồn");
                } else {
                    model.addRow(new Object[]{txtMaSP.getText(), txtLoaiSP.getText(), txtTenSP.getText(), txtSoLuong.getText(), txtDonGia.getText(), txtThanhtien.getText()});
                    tblHoadon.setModel(model);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }

        int soluongsaukhiban = Integer.parseInt(tblLsSP.getValueAt(tblLsSP.getSelectedRow(), 4).toString()) - Integer.parseInt(txtSoLuong.getText());
        // số lượng trong bảng sản phẩm sau khi thêm vào hóa đơn sẽ bằng số lượng ban đầu - số lượng đã chọn
        SP_BUS spBUS = new SP_BUS();
        SP_DTO sp = new SP_DTO();
        sp.setMaSP(txtMaSP.getText());
        sp.setSoLuong(soluongsaukhiban);
        spBUS.updatesoluong(sp);
        updateThanhTien();
        reload();
        hienthiSP();
    }

    public float tinhTongTien() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        float tongTien = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            tongTien += Float.parseFloat(model.getValueAt(i, 5).toString());
        }
        return tongTien;
    }

    public void updateThanhTien() {
        txtThanhtien.setText(String.valueOf(tinhTongTien()));
    }
    
    // Hàm xóa sản phẩm trong hóa đơn , cập nhật lại số lượng sản phẩm trong bảng sản phẩm và cập nhật lại tổng tiền 
    // public void xoaSPtrongHoaDon() {
    //     DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
    //     int selectedRow = tblHoadon.getSelectedRow();
    //     if (selectedRow >= 0) {
    //         int soluong = Integer.parseInt(tblHoadon.getValueAt(selectedRow, 3).toString());
    //         int soluongtrongbangSP = Integer.parseInt(tblLsSP.getValueAt(selectedRow, 4).toString());
    //         int soluongmoi = soluong + soluongtrongbangSP;
    //         SP_BUS spBUS = new SP_BUS();
    //         SP_DTO sp = new SP_DTO();
    //         sp.setMaSP(tblHoadon.getValueAt(selectedRow, 0).toString());
    //         sp.setSoLuong(soluongmoi);
    //         spBUS.updatesoluong(sp);
    //         model.removeRow(selectedRow);
    //         updateThanhTien();
    //         hienthiSP();
    //     } else {
    //         JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần xóa");
    //     }
    // }

    public void xoaSPtrongHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        int selectedRow = tblHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            int soluong = Integer.parseInt(tblHoadon.getValueAt(selectedRow, 3).toString());
            String maSP = tblHoadon.getValueAt(selectedRow, 0).toString();
            int soluongtrongbangSP = timSoLuongSPTrongBangSP(maSP); // Tìm số lượng sản phẩm trong bảng sản phẩm
            int soluongmoi = soluongtrongbangSP + soluong;
            
            SP_BUS spBUS = new SP_BUS();
            SP_DTO sp = new SP_DTO();
            sp.setMaSP(maSP);
            sp.setSoLuong(soluongmoi);
            spBUS.updatesoluong(sp);
            
            model.removeRow(selectedRow);
            updateThanhTien();
            hienthiSP();
        } else {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần xóa");
        }
    }

    // hàm hủy hóa đơn , xóa tất cả các dòng trong bảng hóa đơn và cập nhật lại số lượng sản phẩm trong bảng sản phẩm
    public void huyHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int soluong = Integer.parseInt(model.getValueAt(i, 3).toString());
            String maSP = model.getValueAt(i, 0).toString();
            int soluongtrongbangSP = timSoLuongSPTrongBangSP(maSP); // Tìm số lượng sản phẩm trong bảng sản phẩm
            int soluongmoi = soluongtrongbangSP + soluong;
            SP_BUS spBUS = new SP_BUS();
            SP_DTO sp = new SP_DTO();
            sp.setMaSP(maSP);
            sp.setSoLuong(soluongmoi);
            spBUS.updatesoluong(sp);
        }
        model.setRowCount(0);
        updateThanhTien();
        hienthiSP();
    }
    
    // Hàm tìm số lượng sản phẩm trong bảng sản phẩm dựa trên mã sản phẩm
    private int timSoLuongSPTrongBangSP(String maSP) {
        DefaultTableModel model = (DefaultTableModel) tblLsSP.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maSPTrongBang = model.getValueAt(i, 0).toString();
            if (maSPTrongBang.equals(maSP)) {
                return Integer.parseInt(model.getValueAt(i, 4).toString());
            }
        }
        return 0; // Trả về 0 nếu không tìm thấy
    }

    // sửa trực tiếp số lượng sản phẩm trong bảng hóa đơn và cập nhật lại tổng tiền , số lượng sản phẩm trong bảng sản phẩm 
    public void suaSPtrongHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        int selectedRow = tblHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            int soluong = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng mới"));
            String maSP = tblHoadon.getValueAt(selectedRow, 0).toString();
            int soluongtrongbangSP = timSoLuongSPTrongBangSP(maSP); // Tìm số lượng sản phẩm trong bảng sản phẩm
            if (soluong > soluongtrongbangSP) {
                JOptionPane.showMessageDialog(this, "Số lượng nhập vào lớn hơn số lượng tồn");
            } else {
                int soluongcu = Integer.parseInt(tblHoadon.getValueAt(selectedRow, 3).toString());
                int soluongmoi = soluongtrongbangSP - (soluong - soluongcu);
                SP_BUS spBUS = new SP_BUS();
                SP_DTO sp = new SP_DTO();
                sp.setMaSP(maSP);
                sp.setSoLuong(soluongmoi);
                spBUS.updatesoluong(sp);
                model.setValueAt(soluong, selectedRow, 3);
                float dongia = Float.parseFloat(tblHoadon.getValueAt(selectedRow, 4).toString());
                float thanhtien = soluong * dongia;
                model.setValueAt(thanhtien, selectedRow, 5);
                updateThanhTien();
                hienthiSP();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần sửa");
        }
    
        tblHoadon.clearSelection();
    }

    // load mã khuyến mãi vào cbmKM
    public void loadMaKM() {
        KM_BUS kmBUS = new KM_BUS();
        ArrayList<String> listMaKM = kmBUS.getAllMaKM();
        // lay ngay hien tai
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.parse(txtNgayLap.getText(), dtf);
        
        for (String maKM : listMaKM) {
            //chuyen gia tri tra ve cua ham getNgayBDKM ve dang DateTime
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayBD = LocalDate.parse(kmBUS.getNgayBDKM(maKM), dtf2);
            //ngay ket thuc
            LocalDate ngayKT = LocalDate.parse(kmBUS.getNgayKTKM(maKM), dtf2);
            //kiem tra ngay hien tai co nam trong khoang ngay khuyen mai hay khong
            if (now.isAfter(ngayBD) && now.isBefore(ngayKT)) {
                cmbKM.addItem(maKM);
            }
        }
    }

    // load mã nhân viên vào cbmTenNV
    public void loadTenNV() {
        NV_BUS nvBUS = new NV_BUS();
        ArrayList<String> listTenNV = nvBUS.getAllMaNV();
        for (String tenNV : listTenNV) {
            cmbTenNV.addItem(tenNV);
        }
    }

    // load mã khách hàng vào cbmTenKH
    public void loadTenKH() {
        KH_BUS khBUS = new KH_BUS();
        ArrayList<String> listTenKH = khBUS.getAllMaKH();
        for (String tenKH : listTenKH) {
            cmbTenKH.addItem(tenKH);
        }
    }

    // khi chọn mã khách hàng thì tên khách hàng sẽ tự động hiển thị
    public void chonTenKH() {
        KH_BUS khBUS = new KH_BUS();
        String tenKH = khBUS.getTenKH(cmbTenKH.getSelectedItem().toString());
        txtTenKH.setText(tenKH);
    }



    // khi chọn mã nhân viên thì tên nhân viên sẽ tự động hiển thị
    public void chonTenNV() {
        NV_BUS nvBUS = new NV_BUS();
        String tenNV = nvBUS.getTenNV(cmbTenNV.getSelectedItem().toString());
        txtTenNV.setText(tenNV);
    }

    // lấy mã hóa đơn tự động gắn vào txtMaHĐ
    public void layMaHoaDon() {
        HoaDon_BUS hdBUS = new HoaDon_BUS();
        String maHD = hdBUS.autoGenerateID();
        txtMaHĐ.setText(maHD);
    }

    // so sánh tổng tiền với điều kiện khuyến mãi , nếu lớn hơn thì giảm giá , nếu nhỏ hơn thì không giảm giá ; tongtien = tongtien - giamgia
    public void soSanhTongTien() {
        float tongtien = tinhTongTien();
        KM_BUS kmBUS = new KM_BUS();
        float dkkm = kmBUS.getDKKM(cmbKM.getSelectedItem().toString());
        float sokm = kmBUS.getSoKM(cmbKM.getSelectedItem().toString());
        if (tongtien >= dkkm) {
            tongtien = tongtien - sokm;
            txtThanhtien.setText(String.valueOf(tongtien));
        }
    }

    // hàm lưu hóa đơn vào database
    public void luuHoaDon() {
        HoaDon_BUS hdBUS = new HoaDon_BUS();
        HoaDon_DTO hd = new HoaDon_DTO();
        hd.setMaHD(txtMaHĐ.getText());
        // nếu chọn khách hàng thì lấy mã khách hàng , nếu không chọn thì lấy giá trị null
        if (cmbTenKH.getSelectedItem().toString().equals("Null")) {
            hd.setMaKH(null);
        } else {
            hd.setMaKH(cmbTenKH.getSelectedItem().toString());
        }
        hd.setMaKM(cmbKM.getSelectedItem().toString());
        hd.setMaNV(cmbTenNV.getSelectedItem().toString());
        hd.setNgayLap(txtNgayLap.getText());
        hd.setGioLap(txtGioLap.getText());
        hd.setTongTien(Float.parseFloat(txtThanhtien.getText()));
        hdBUS.insertHoaDon(hd);
        luuCTHD();
        JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công");
        inHoaDonToFile( txtMaHĐ.getText() + ".pdf");
        layMaHoaDon();
        huyHoaDon();
    }

    // hàm lưu chi tiết hóa đơn vào database
    public void luuCTHD() {
        CTHD_BUS cthdBUS = new CTHD_BUS();
        DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            CTHD_DTO cthd = new CTHD_DTO();
            cthd.setMaHD(txtMaHĐ.getText());
            cthd.setMaSP(model.getValueAt(i, 0).toString());
            cthd.setSoLuong(Integer.parseInt(model.getValueAt(i, 3).toString()));
            cthd.setDonGia(Float.parseFloat(model.getValueAt(i, 4).toString()));
            cthdBUS.insertCTHD(cthd);
        }
    }



public void inHoaDonToFile(String filePath) {
    DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
    String mahd = txtMaHĐ.getText();
    String tenkh = txtTenKH.getText();
    String tennv = txtTenNV.getText();
    String ngaylap = txtNgayLap.getText();
    String giolap = txtGioLap.getText();
    String tongtien = txtThanhtien.getText();
    String hoadon = "Mã hóa đơn: " + mahd + "\n" + "Tên khách hàng: " + tenkh + "\n" + "Tên nhân viên: " + tennv + "\n" + "Ngày lập: " + ngaylap + "\n" + "Giờ lập: " + giolap + "\n" + "Tổng tiền: " + tongtien + "\n";
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(hoadon);
        for (int i = 0; i < model.getRowCount(); i++) {
            String line = "Mã sản phẩm: " + model.getValueAt(i, 0).toString() + " - " + "Tên sản phẩm: " + model.getValueAt(i, 2).toString() + " - " + "Số lượng: " + model.getValueAt(i, 3).toString() + " - " + "Đơn giá: " + model.getValueAt(i, 4).toString() + " - " + "Thành tiền: " + model.getValueAt(i, 5).toString() + "\n";
            writer.write(line);
        }
        JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu vào file: " + filePath);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi ghi file: " + e.getMessage());
        e.printStackTrace();
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

        pnBanHang = new javax.swing.JPanel();
        jScrollBar1 = new javax.swing.JScrollBar();
        pnInHĐ = new javax.swing.JPanel();
        pnTTHĐ = new javax.swing.JPanel();
        txtMaHĐ = new javax.swing.JTextField();
        lbMaHĐ = new javax.swing.JLabel();
        txtThanhtien = new javax.swing.JTextField();
        lbThanhtien = new javax.swing.JLabel();
        lbTenKH = new javax.swing.JLabel();
        lbTenNV = new javax.swing.JLabel();
        lbKM = new javax.swing.JLabel();
        cmbKM = new javax.swing.JComboBox<>();
        lbNgayLap = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        lbGioLap = new javax.swing.JLabel();
        txtGioLap = new javax.swing.JTextField();
        cmbTenKH = new javax.swing.JComboBox<>();
        cmbTenNV = new javax.swing.JComboBox<>();
        txtTenKH = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        pnLsSPmua = new javax.swing.JPanel();
        pnLsSPdachon = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoadon = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        pnThanhtoan = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        pnLsSP = new javax.swing.JPanel();
        pnTimkiem = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        btnTimkiem = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        pnHienthiSP = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        anh = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtLoaiSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        pnLsBanh = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLsSP = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        pnBanHang.setBackground(new java.awt.Color(255, 255, 255));
        pnBanHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnBanHang.setLayout(new java.awt.BorderLayout());
        pnBanHang.add(jScrollBar1, java.awt.BorderLayout.LINE_START);

        pnInHĐ.setBackground(new java.awt.Color(255, 255, 204));
        pnInHĐ.setPreferredSize(new java.awt.Dimension(400, 631));
        pnInHĐ.setLayout(new java.awt.BorderLayout());

        pnTTHĐ.setPreferredSize(new java.awt.Dimension(400, 185));

        txtMaHĐ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHĐActionPerformed(evt);
            }
        });

        lbMaHĐ.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbMaHĐ.setText("Mã HĐ");

        txtThanhtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhtienActionPerformed(evt);
            }
        });

        lbThanhtien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbThanhtien.setText("Thành tiền");

        lbTenKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTenKH.setText("Tên KH");

        lbTenNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTenNV.setText("Tên NV");

        lbKM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbKM.setText("Khuyến mãi");

        cmbKM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKMItemStateChanged(evt);
            }
        });
        cmbKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKMActionPerformed(evt);
            }
        });

        lbNgayLap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbNgayLap.setText("Ngày Lập");

        txtNgayLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLapActionPerformed(evt);
            }
        });

        lbGioLap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbGioLap.setText("Giờ Lập");

        cmbTenKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null" }));
        cmbTenKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenKHItemStateChanged(evt);
            }
        });

        cmbTenNV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTenNVItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnTTHĐLayout = new javax.swing.GroupLayout(pnTTHĐ);
        pnTTHĐ.setLayout(pnTTHĐLayout);
        pnTTHĐLayout.setHorizontalGroup(
            pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTHĐLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTTHĐLayout.createSequentialGroup()
                        .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbTenNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(lbMaHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnTTHĐLayout.createSequentialGroup()
                                .addComponent(txtMaHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbThanhtien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(txtThanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnTTHĐLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbTenNV, 0, 86, Short.MAX_VALUE)
                                    .addComponent(cmbTenKH, 0, 1, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnTTHĐLayout.createSequentialGroup()
                        .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbNgayLap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbKM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbKM, 0, 137, Short.MAX_VALUE)
                            .addComponent(txtNgayLap))
                        .addGap(18, 18, 18)
                        .addComponent(lbGioLap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGioLap, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnTTHĐLayout.setVerticalGroup(
            pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTHĐLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMaHĐ)
                    .addComponent(txtThanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbThanhtien))
                .addGap(18, 18, 18)
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenKH)
                    .addComponent(cmbTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenNV)
                    .addComponent(cmbTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbKM)
                    .addComponent(cmbKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTTHĐLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNgayLap)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbGioLap)
                    .addComponent(txtGioLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pnInHĐ.add(pnTTHĐ, java.awt.BorderLayout.PAGE_START);

        pnLsSPmua.setBackground(new java.awt.Color(255, 255, 153));
        pnLsSPmua.setLayout(new java.awt.BorderLayout());

        pnLsSPdachon.setBackground(new java.awt.Color(255, 255, 153));

        tblHoadon.setBorder(new javax.swing.border.MatteBorder(null));
        tblHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Mã Loại", "Tên", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        tblHoadon.setShowGrid(true);
        tblHoadon.setSurrendersFocusOnKeystroke(true);
        jScrollPane2.setViewportView(tblHoadon);

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

        javax.swing.GroupLayout pnLsSPdachonLayout = new javax.swing.GroupLayout(pnLsSPdachon);
        pnLsSPdachon.setLayout(pnLsSPdachonLayout);
        pnLsSPdachonLayout.setHorizontalGroup(
            pnLsSPdachonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLsSPdachonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnReload)
                .addContainerGap())
        );
        pnLsSPdachonLayout.setVerticalGroup(
            pnLsSPdachonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLsSPdachonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnLsSPdachonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnReload))
                .addGap(17, 17, 17))
        );

        pnLsSPmua.add(pnLsSPdachon, java.awt.BorderLayout.PAGE_START);

        pnThanhtoan.setBackground(new java.awt.Color(0, 0, 0));
        pnThanhtoan.setPreferredSize(new java.awt.Dimension(200, 20));

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(255, 204, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/money.png"))); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThanhtoanLayout = new javax.swing.GroupLayout(pnThanhtoan);
        pnThanhtoan.setLayout(pnThanhtoanLayout);
        pnThanhtoanLayout.setHorizontalGroup(
            pnThanhtoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThanhtoanLayout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnThanhtoanLayout.setVerticalGroup(
            pnThanhtoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThanhtoanLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnThanhtoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy))
                .addContainerGap())
        );

        pnLsSPmua.add(pnThanhtoan, java.awt.BorderLayout.CENTER);

        pnInHĐ.add(pnLsSPmua, java.awt.BorderLayout.CENTER);

        pnBanHang.add(pnInHĐ, java.awt.BorderLayout.LINE_END);

        pnLsSP.setBackground(new java.awt.Color(0, 255, 204));
        pnLsSP.setLayout(new java.awt.BorderLayout());

        pnTimkiem.setBackground(new java.awt.Color(204, 255, 204));
        pnTimkiem.setPreferredSize(new java.awt.Dimension(597, 50));

        txtTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimkiemActionPerformed(evt);
            }
        });

        btnTimkiem.setBackground(new java.awt.Color(255, 255, 102));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searchicon.png"))); // NOI18N
        btnTimkiem.setText("Tìm kiếm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });

        btnLammoi.setBackground(new java.awt.Color(255, 255, 204));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnTimkiemLayout = new javax.swing.GroupLayout(pnTimkiem);
        pnTimkiem.setLayout(pnTimkiemLayout);
        pnTimkiemLayout.setHorizontalGroup(
            pnTimkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTimkiemLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimkiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLammoi)
                .addContainerGap(319, Short.MAX_VALUE))
        );
        pnTimkiemLayout.setVerticalGroup(
            pnTimkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTimkiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTimkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimkiem)
                    .addComponent(btnLammoi))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnLsSP.add(pnTimkiem, java.awt.BorderLayout.PAGE_START);

        pnHienthiSP.setPreferredSize(new java.awt.Dimension(597, 225));
        pnHienthiSP.setLayout(new java.awt.BorderLayout());

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThem.setForeground(new java.awt.Color(51, 51, 51));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnHienthiSP.add(btnThem, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(949, 150));
        jPanel5.add(anh);

        txtMaSP.setBackground(new java.awt.Color(204, 255, 255));
        txtMaSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã SP"));
        txtMaSP.setPreferredSize(new java.awt.Dimension(120, 80));
        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });
        jPanel5.add(txtMaSP);

        txtLoaiSP.setBackground(new java.awt.Color(204, 255, 255));
        txtLoaiSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Loại SP"));
        txtLoaiSP.setPreferredSize(new java.awt.Dimension(120, 80));
        txtLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiSPActionPerformed(evt);
            }
        });
        jPanel5.add(txtLoaiSP);

        txtTenSP.setBackground(new java.awt.Color(204, 255, 255));
        txtTenSP.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên SP"));
        txtTenSP.setPreferredSize(new java.awt.Dimension(120, 80));
        jPanel5.add(txtTenSP);

        txtDonGia.setBackground(new java.awt.Color(204, 255, 255));
        txtDonGia.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn giá"));
        txtDonGia.setPreferredSize(new java.awt.Dimension(120, 80));
        jPanel5.add(txtDonGia);

        txtSoLuong.setBackground(new java.awt.Color(204, 255, 255));
        txtSoLuong.setBorder(javax.swing.BorderFactory.createTitledBorder("Số lượng"));
        txtSoLuong.setPreferredSize(new java.awt.Dimension(120, 80));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        jPanel5.add(txtSoLuong);

        pnHienthiSP.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        pnLsSP.add(pnHienthiSP, java.awt.BorderLayout.PAGE_END);

        pnLsBanh.setBackground(new java.awt.Color(51, 255, 204));
        pnLsBanh.setPreferredSize(new java.awt.Dimension(620, 200));
        pnLsBanh.setLayout(new java.awt.BorderLayout());

        tblLsSP.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Loại SP", "Tên SP", "Đơn giá", "Số lượng ", "Ảnh"
            }
        ));
        tblLsSP.setSelectionBackground(new java.awt.Color(102, 204, 255));
        tblLsSP.setShowGrid(true);
        tblLsSP.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(tblLsSP);

        pnLsBanh.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnLsSP.add(pnLsBanh, java.awt.BorderLayout.CENTER);

        pnBanHang.add(pnLsSP, java.awt.BorderLayout.CENTER);

        add(pnBanHang, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaHĐActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHĐActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHĐActionPerformed

    private void txtThanhtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhtienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhtienActionPerformed

    private void cmbKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKMActionPerformed

    private void txtNgayLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoaSPtrongHoaDon();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        huyHoaDon();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        luuHoaDon();
        //nHoaDonToFile(txtMaHĐ.getText());
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimkiemActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        timkiemSP();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        themSPvaoHoaDon();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiSPActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        suaSPtrongHoaDon();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        reload();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        // TODO add your handling code here:
        tblHoadon.clearSelection();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void cmbTenNVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenNVItemStateChanged
        // TODO add your handling code here:
        chonTenNV();
    }//GEN-LAST:event_cmbTenNVItemStateChanged

    private void cmbTenKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTenKHItemStateChanged
        // TODO add your handling code here:
        chonTenKH();
    }//GEN-LAST:event_cmbTenKHItemStateChanged

    private void cmbKMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKMItemStateChanged
        // TODO add your handling code here:
        soSanhTongTien();
    }//GEN-LAST:event_cmbKMItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbKM;
    private javax.swing.JComboBox<String> cmbTenKH;
    private javax.swing.JComboBox<String> cmbTenNV;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbGioLap;
    private javax.swing.JLabel lbKM;
    private javax.swing.JLabel lbMaHĐ;
    private javax.swing.JLabel lbNgayLap;
    private javax.swing.JLabel lbTenKH;
    private javax.swing.JLabel lbTenNV;
    private javax.swing.JLabel lbThanhtien;
    private javax.swing.JPanel pnBanHang;
    private javax.swing.JPanel pnHienthiSP;
    private javax.swing.JPanel pnInHĐ;
    private javax.swing.JPanel pnLsBanh;
    private javax.swing.JPanel pnLsSP;
    private javax.swing.JPanel pnLsSPdachon;
    private javax.swing.JPanel pnLsSPmua;
    private javax.swing.JPanel pnTTHĐ;
    private javax.swing.JPanel pnThanhtoan;
    private javax.swing.JPanel pnTimkiem;
    private javax.swing.JTable tblHoadon;
    private javax.swing.JTable tblLsSP;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGioLap;
    private javax.swing.JTextField txtLoaiSP;
    private javax.swing.JTextField txtMaHĐ;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThanhtien;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
