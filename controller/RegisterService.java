package controller;

import conection.Conexao;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;

public class RegisterService extends JFrame {

    private JTextField txtTipo;
    private JTextField txtDescricao;
    private JFormattedTextField txtPreco;

    public RegisterService() {
        setTitle("Cadastro de Serviço");
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(labelFont);
        lblTipo.setForeground(new Color(0, 102, 153));
        txtTipo = new JTextField(20);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(labelFont);
        lblDescricao.setForeground(new Color(0, 102, 153));
        txtDescricao = new JTextField(20);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setFont(labelFont);
        lblPreco.setForeground(new Color(0, 102, 153));


        NumberFormat format = NumberFormat.getNumberInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        txtPreco = new JFormattedTextField(formatter);
        txtPreco.setColumns(20);

        JButton btnSalvar = new JButton("Salvar");
        styleButton(btnSalvar);
        btnSalvar.addActionListener(e -> salvarServico());

        JButton btnVoltar = new JButton("Voltar");
        styleButton(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(255, 255, 255));
        panel.add(lblTipo);
        panel.add(txtTipo);
        panel.add(lblDescricao);
        panel.add(txtDescricao);
        panel.add(lblPreco);
        panel.add(txtPreco);
        panel.add(btnSalvar);
        panel.add(btnVoltar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterService();
    }

    private void salvarServico() {
        String tipo = txtTipo.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String precoText = txtPreco.getText().trim();

        if (tipo.isEmpty() || descricao.isEmpty() || precoText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoText.replace(",", "."));
            Connection conexao = Conexao.conectar();

            if (conexao != null) {
                String sql = "INSERT INTO servicos (tipo, descricao, preco) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, tipo);
                stmt.setString(2, descricao);
                stmt.setDouble(3, preco);

                stmt.executeUpdate();
                stmt.close();
                conexao.close();

                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTipo.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(new Color(255, 255, 255));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(102, 205, 170));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(173, 216, 230));
            }
        });
    }
}
