package com.fouj.client;

import com.fouj.objects.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class ClientView extends JFrame {

    private JTextField txtName, txtStudentCode, txtScore;
    private JRadioButton rdoCNTT, rdoKTPM, rdoKHMT;
    private JCheckBox chkEnglish, chkRussian, chkFrench;
    private JTextArea txtResult;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnExit;

    private StudentClient client;

    public ClientView(StudentClient client) {
        setTitle("QLSV2024");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setContentPane(mainView());
        setLocationRelativeTo(null);
        setVisible(true);

        this.client = client;
    }

    private JPanel mainView() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel label = new JLabel("CHUONG TRINH QUAN LY SINH VIEN - 2024");
        label.setBorder(new EmptyBorder(20, 0, 20, 0));
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.BLUE);
        panel.add(label, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(leftPanel(), gbc);
        gbc.gridx = 1;
        panel.add(rightPanel(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(actionPanel(), gbc);
        return panel;
    }

    private JPanel leftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new TitledBorder("NHAP"));

        panel.add(new JLabel("Ho va ten:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Ma sinh vien:"));
        txtStudentCode = new JTextField();
        panel.add(txtStudentCode);

        panel.add(new JLabel("Nganh hoc:"));
        JPanel majorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rdoCNTT = new JRadioButton("CNTT");
        rdoKTPM = new JRadioButton("KTPM");
        rdoKHMT = new JRadioButton("KHMT");
        ButtonGroup groupMajor = new ButtonGroup();
        groupMajor.add(rdoCNTT);
        groupMajor.add(rdoKTPM);
        groupMajor.add(rdoKHMT);
        majorPanel.add(rdoCNTT);
        majorPanel.add(rdoKTPM);
        majorPanel.add(rdoKHMT);
        panel.add(majorPanel);

        panel.add(new JLabel("Ngoai ngu:"));
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkEnglish = new JCheckBox("Anh");
        chkRussian = new JCheckBox("Nga");
        chkFrench = new JCheckBox("Phap");
        languagePanel.add(chkEnglish);
        languagePanel.add(chkRussian);
        languagePanel.add(chkFrench);
        panel.add(languagePanel);

        panel.add(new JLabel("Diem tong ket:"));
        txtScore = new JTextField();
        panel.add(txtScore);

        return panel;
    }

    private JPanel rightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new TitledBorder("KET QUA"));

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResult);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel actionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnAdd = new JButton("Them");
        btnAdd.addActionListener(e -> handleAdd());
        btnEdit = new JButton("Sua");
        btnEdit.addActionListener(e -> handleEdit());
        btnDelete = new JButton("Xoa");
        btnDelete.addActionListener(e -> handleDelete());
        btnSearch = new JButton("Tim kiem");
        btnSearch.addActionListener(e -> handleSearch());
        btnExit = new JButton("Ket thuc");
        btnExit.addActionListener(e -> {
            client.close();
            dispose();
        });

        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnSearch);
        panel.add(btnExit);

        return panel;
    }

    private boolean checkValid() {
        String name = txtName.getText();
        if(name == null || name.equals("")) {
            JOptionPane.showMessageDialog(this, "Ho ten sinh vien khong hop le!", "Loi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String studentCode = txtStudentCode.getText();
        if(studentCode == null || studentCode.equals("")) {
            JOptionPane.showMessageDialog(this, "Ma sinh vien khong hop le!", "Loi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double finalScore = Double.parseDouble(txtScore.getText());
            if(finalScore < 0) {
                JOptionPane.showMessageDialog(this, "Diem tong ket phai lon hon hoac bang 0!", "Loi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Diem tong ket phai la so thuc!", "Loi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Student getStudent() {
        String name = txtName.getText().trim();
        String studentCode = txtStudentCode.getText().trim();
        double finalScore = Double.parseDouble(txtScore.getText());
        String major = "";
        if (rdoCNTT.isSelected()) {
            major = "CNTT";
        } else if (rdoKTPM.isSelected()) {
            major = "KTPM";
        } else if (rdoKHMT.isSelected()) {
            major = "KHMT";
        }

        StringBuilder languages = new StringBuilder();
        if (chkEnglish.isSelected()) {
            languages.append("Anh,");
        }
        if (chkRussian.isSelected()) {
            languages.append("Nga,");
        }
        if (chkFrench.isSelected()) {
            languages.append("Phap,");
        }

        if(languages.length() > 0) {
            languages.setLength(languages.length() - 1);
        }

        Student student = new Student()
                .setName(name)
                .setStudent_code(studentCode)
                .setFinal_score(finalScore)
                .setMajor(major)
                .setLanguages(languages.toString());

        return student;
    }

    private void handleAdd() {
        if(checkValid()) {
            Student student = getStudent();

            try {
                Map<String, Object> response = client.addStudent(student);
                String message = (String) response.get("message");
                txtResult.setText(message + "\n");
                Student result = (Student) response.get("result");
                if(result != null) {
                    txtResult.append(result.toString());
                    setFields(null);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Khong the them moi sinh vien! " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }
    }

    private void handleEdit() {
        String student_code = txtStudentCode.getText();
        if(student_code == null || student_code.equalsIgnoreCase("")) {
            student_code = JOptionPane.showInputDialog("Nhap ma sinh vien can sua");
            try {
                Map<String, Object> response = client.searchStudent(student_code);
                String message = (String) response.get("message");
                txtResult.setText(message + "\n");
                Student result = (Student) response.get("result");
                if(result != null) {
                    txtResult.append(result.toString());
                    setFields(result);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Khong the thuc hien tim kiem sinh vien! " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        } else {
            if(checkValid()) {
                Student student = getStudent();
                try {
                    Map<String, Object> response = client.searchStudent(student.getStudent_code());
                    Student result = (Student) response.get("result");
                    if(result != null) {
                        Map<String, Object> response1 = client.editStudent(student);
                        String message1 = (String) response1.get("message");
                        txtResult.setText(message1 + "\n");
                        Student result1 = (Student) response1.get("result");
                        if(result1 != null) {
                            setFields(null);
                            txtResult.append(result1.toString());
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Khong the thuc hien chinh sua sinh vien! " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void handleSearch() {
        String student_code = JOptionPane.showInputDialog("Nhap ma sinh vien can tim");
        if(student_code != null && !student_code.trim().equals("")) {
            try {
                Map<String, Object> response = client.searchStudent(student_code);
                String message = (String) response.get("message");
                txtResult.setText(message);
                Student result = (Student) response.get("result");
                if(result != null) txtResult.append(result.toString());
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Khong the thuc hien tim kiem sinh vien! " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }
    }

    private void handleDelete() {
        String student_code = JOptionPane.showInputDialog("Nhap ma sinh vien can xoa");
        if(student_code != null && !student_code.trim().equals("")) {
            try {
                Map<String, Object> response = client.searchStudent(student_code);
                String message = (String) response.get("message");
                txtResult.setText(message + "\n");
                Student result = (Student) response.get("result");
                if(result != null) {
                    int choice = JOptionPane.showConfirmDialog(this, "Ban cho chac chan muon xoa sinh vien nay\n" + result.toString(), "Xac nhan", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(choice == JOptionPane.OK_OPTION) {
                        Map<String, Object> response1 = client.deleteStudent(student_code);
                        String message1 = (String) response1.get("message");
                        txtResult.setText(message1);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,  message, "Loi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Khong the thuc hien xoa sinh vien! " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }
    }

    private void setFields(Student student) {
        if(student != null) {
            txtName.setText(student.getName());
            txtStudentCode.setText(student.getStudent_code());
            txtScore.setText(student.getFinal_score() + "");
            String major = student.getMajor().trim();
            rdoKTPM.setSelected(major.equalsIgnoreCase("KTPM"));
            rdoCNTT.setSelected(major.equalsIgnoreCase("CNTT"));
            rdoKHMT.setSelected(major.equalsIgnoreCase("KHMT"));
            String languages = student.getLanguages().toLowerCase();
            chkEnglish.setSelected(languages.contains("anh"));
            chkRussian.setSelected(languages.contains("nga"));
            chkFrench.setSelected(languages.contains("phap"));
        } else {
            txtName.setText("");
            txtStudentCode.setText("");
            txtScore.setText("");
            rdoCNTT.setSelected(false);
            rdoKHMT.setSelected(false);
            rdoKTPM.setSelected(false);
            chkEnglish.setSelected(false);
            chkFrench.setSelected(false);
            chkRussian.setSelected(false);
        }
    }

}
