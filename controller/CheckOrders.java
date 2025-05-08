package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CheckOrders extends JFrame {
    public CheckOrders() {
        setTitle("Consulta de Pedidos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color azulLavanderia = new Color(173, 216, 230);
        Color verdeAgua = new Color(102, 205, 170);
        Color branco = new Color(255, 255, 255);
        Color azulEscuro = new Color(0, 102, 153);

        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        Font headerFont = new Font("Arial", Font.BOLD, 14);

        String[] colunas = {"ID", "Cliente", "Entrada", "Saída", "Valor"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        try {
            String url = "jdbc:mysql://localhost:3306/lavandeira";
            String usuario = "root";
            String senha = "cerri7";
            Connection conn = DriverManager.getConnection(url, usuario, senha);

            String sql = "SELECT id, cliente, data_entrada, data_saida, valor FROM pedidos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String cliente = rs.getString("cliente");
                String entrada = rs.getString("data_entrada");
                String saida = rs.getString("data_saida");
                String valor = rs.getString("valor");

                modeloTabela.addRow(new Object[]{id, cliente, entrada, saida, valor});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        JTable tabela = new JTable(modeloTabela);
        tabela.setFont(tableFont);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(headerFont);
        tabela.getTableHeader().setBackground(azulLavanderia);
        tabela.getTableHeader().setForeground(azulEscuro);
        tabela.setGridColor(azulLavanderia);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(580, 250));

        JButton btnVoltar = new JButton("Voltar");
        styleButton(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(branco);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(branco);
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(btnVoltar);

        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CheckOrders();
    }

    private void styleButton(JButton button) {
        Color azulLavanderia = new Color(173, 216, 230);
        Color verdeAgua = new Color(102, 205, 170);
        Color branco = new Color(255, 255, 255);

        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(azulLavanderia);
        button.setForeground(branco);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(azulLavanderia, 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(verdeAgua);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(azulLavanderia);
            }
        });
    }
}
