/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BUS.NCC_BUS;
import DTO.NCC_DTO;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author sinht
 */
public class pnNCC extends javax.swing.JPanel {

    /**
     * Creates new form pnNCC
     */

    NCC_BUS ncc_bus = new NCC_BUS();
    public pnNCC() {
        initComponents();
        showNCC();
        txtMaNCC.setEnabled(false);
    }

    public void showNCC() {
        ArrayList<NCC_DTO> ncc = ncc_bus.getListNCC();
        DefaultTableModel model = (DefaultTableModel) tblNCC.getModel();
        model.setRowCount(0);
        for (int i = 0; i < ncc.size(); i++) {
            model.addRow(new Object[]{i + 1, ncc.get(i).getMaNCC(), ncc.get(i).getTenNCC(), ncc.get(i).getDiaChi(), ncc.get(i).getSDT(), ncc.get(i).getIsDelete()});
        }

        // khi click vào 1 dòng nào đó thì lấy dữ liệu của dòng đó lên các ô text
        tblNCC.getSelectionModel().addListSelectionListener((e) -> {
            if (tblNCC.getSelectedRow() >= 0) {
                txtMaNCC.setText(tblNCC.getValueAt(tblNCC.getSelectedRow(), 1).toString());
                txtTenNCC.setText(tblNCC.getValueAt(tblNCC.getSelectedRow(), 2).toString());
                txtDiachi.setText(tblNCC.getValueAt(tblNCC.getSelectedRow(), 3).toString());
                txtSDT.setText(tblNCC.getValueAt(tblNCC.getSelectedRow(), 4).toString());
            }
        });
    }

    public void addNCC() {
        NCC_DTO ncc = new NCC_DTO();
        ncc.setMaNCC(txtMaNCC.getText());
        ncc.setTenNCC(txtTenNCC.getText());
        ncc.setDiaChi(txtDiachi.getText());
        ncc.setSDT(txtSDT.getText());
        ncc.setIsDelete(1);
        ncc_bus.addNCC(ncc);
        showNCC();
    }

    public void updateNCC() {
        NCC_DTO ncc = new NCC_DTO();
        ncc.setMaNCC(txtMaNCC.getText());
        ncc.setTenNCC(txtTenNCC.getText());
        ncc.setDiaChi(txtDiachi.getText());
        ncc.setSDT(txtSDT.getText());
        ncc.setIsDelete(1);
        ncc_bus.updateNCC(ncc);
        showNCC();
    }

   // Delete NCC
    public void deleteNCC() {
        int row = tblNCC.getSelectedRow();
        if (row == -1) {
            return;
        }
        String MaNCC = (String) tblNCC.getValueAt(row, 1);
        ncc_bus.deleteNCC(MaNCC);
        showNCC();
    }

    public void searchNCC() {
        ArrayList<NCC_DTO> ncc = ncc_bus.searchNCC(txtTimkiem.getText());
        DefaultTableModel model = (DefaultTableModel) tblNCC.getModel();
        model.setRowCount(0);
        for (int i = 0; i < ncc.size(); i++) {
            model.addRow(new Object[]{i + 1, ncc.get(i).getMaNCC(), ncc.get(i).getTenNCC(), ncc.get(i).getDiaChi(), ncc.get(i).getSDT(), ncc.get(i).getIsDelete()});
        }
    }

    public void refresh() {
        txtMaNCC.setText("");
        txtTenNCC.setText("");
        txtDiachi.setText("");
        txtSDT.setText("");
        txtTimkiem.setText("");
        tblNCC.clearSelection();
        showNCC();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnNCC = new javax.swing.JPanel();
        pnTop7 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnNhapExcel = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnTimkiem = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        pnCenter7 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblNCC = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtMaNCC = new javax.swing.JTextField();
        txtTenNCC = new javax.swing.JTextField();
        txtDiachi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        pnNCC.setLayout(new java.awt.BorderLayout());

        pnTop7.setBackground(new java.awt.Color(204, 255, 255));
        pnTop7.setPreferredSize(new java.awt.Dimension(1361, 150));
        pnTop7.setLayout(new java.awt.BorderLayout());

        jPanel33.setBackground(new java.awt.Color(153, 255, 255));
        jPanel33.setPreferredSize(new java.awt.Dimension(1085, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add1.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel33.add(btnThem);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel33.add(btnXoa);

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel33.add(btnSua);

        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xls.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel33.add(btnXuatExcel);

        btnNhapExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhapExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnNhapExcel.setText("Nhập Excel ");
        btnNhapExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExcelActionPerformed(evt);
            }
        });
        jPanel33.add(btnNhapExcel);

        pnTop7.add(jPanel33, java.awt.BorderLayout.PAGE_START);

        jPanel34.setBackground(new java.awt.Color(153, 255, 255));
        jPanel34.setPreferredSize(new java.awt.Dimension(1085, 150));

        jPanel35.setBackground(new java.awt.Color(204, 255, 255));
        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTimkiem.setBackground(new java.awt.Color(153, 255, 255));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 102));
        jLabel17.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        jPanel34.add(jPanel35);

        btnTimkiem.setBackground(new java.awt.Color(153, 255, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });
        jPanel34.add(btnTimkiem);

        btnLammoi.setBackground(new java.awt.Color(153, 255, 153));
        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });
        jPanel34.add(btnLammoi);

        pnTop7.add(jPanel34, java.awt.BorderLayout.CENTER);

        pnNCC.add(pnTop7, java.awt.BorderLayout.PAGE_START);

        pnCenter7.setBackground(new java.awt.Color(255, 204, 255));
        pnCenter7.setPreferredSize(new java.awt.Dimension(161, 450));
        pnCenter7.setLayout(new java.awt.BorderLayout());

        tblNCC.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NCC", "Tên NCC", "Địa chỉ", "SĐT", "isDelete"
            }
        ));
        tblNCC.setShowGrid(true);
        tblNCC.setSurrendersFocusOnKeystroke(true);
        jScrollPane12.setViewportView(tblNCC);

        pnCenter7.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        txtMaNCC.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã NCC"));
        txtMaNCC.setPreferredSize(new java.awt.Dimension(140, 60));
        jPanel1.add(txtMaNCC);

        txtTenNCC.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên NCC"));
        txtTenNCC.setPreferredSize(new java.awt.Dimension(140, 60));
        jPanel1.add(txtTenNCC);

        txtDiachi.setBorder(javax.swing.BorderFactory.createTitledBorder("Địa chỉ"));
        txtDiachi.setPreferredSize(new java.awt.Dimension(140, 60));
        txtDiachi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiachiActionPerformed(evt);
            }
        });
        jPanel1.add(txtDiachi);

        txtSDT.setBorder(javax.swing.BorderFactory.createTitledBorder("SĐT"));
        txtSDT.setPreferredSize(new java.awt.Dimension(140, 60));
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        jPanel1.add(txtSDT);

        pnCenter7.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnNCC.add(pnCenter7, java.awt.BorderLayout.CENTER);

        add(pnNCC, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteNCC();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateNCC();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnNhapExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNhapExcelActionPerformed

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        // TODO add your handling code here:
        searchNCC();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void txtDiachiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiachiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiachiActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addNCC();

    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnNhapExcel;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JPanel pnCenter7;
    private javax.swing.JPanel pnNCC;
    private javax.swing.JPanel pnTop7;
    private javax.swing.JTable tblNCC;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtMaNCC;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
