package com.fouj.server;

import com.fouj.objects.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServerView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ServerView() {
        setTitle("QLSV Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));

        setContentPane(mainView());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel mainView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("DANH SACH SINH VIEN");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(20, 10, 20, 10));
        panel.add(label, BorderLayout.NORTH);
        String columnNames[] = {"ID", "Ho va ten", "Ma sinh vien", "Nganh hoc", "Ngoai ngu", "Diem tong ket"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public void updateTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student: students) {
            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getStudent_code(),
                    student.getMajor(),
                    student.getLanguages(),
                    student.getFinal_score()
            });
        }
    }

}
