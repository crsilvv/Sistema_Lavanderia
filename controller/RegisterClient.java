package controller;

import conection.Conexao;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterClient extends JFrame {

    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;

    public RegisterClient() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(labelFont);
        lblNome.setForeground(new Color(0, 102, 153));
        txtNome = new JTextField(20);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(labelFont);
        lblTelefone.setForeground(new Color(0, 102, 153));
        txtTelefone = new JTextField(15);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        lblEmail.setForeground(new Color(0, 102, 153));
        txtEmail = new JTextField(20);

        JButton btnSalvar = new JButton("Salvar");
        styleButton(btnSalvar);
        btnSalvar.addActionListener(e -> salvarCliente());

        JButton btnVoltar = new JButton("Voltar");
        styleButton(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblTelefone);
        panel.add(txtTelefone);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(btnSalvar);
        panel.add(btnVoltar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterClient();
    }

    private void salvarCliente() {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conexao = Conexao.conectar();

            if (conexao != null) {
                String sql = "INSERT INTO clientes (nome, telefone, email) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, telefone);
                stmt.setString(3, email);
                stmt.executeUpdate();
                conexao.close();

                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(Color.WHITE);
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
