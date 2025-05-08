package controller;

import conection.Conexao;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class OrderService extends JFrame {

    public OrderService() {
        setTitle("Nova Ordem de Serviço");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 255, 255));

        Color azulLavanderia = new Color(173, 216, 230);
        Color verdeAgua = new Color(102, 205, 170);
        Color branco = new Color(255, 255, 255);
        Color azulEscuro = new Color(0, 102, 153);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblClienteId = new JLabel("Cliente ID:");
        lblClienteId.setFont(labelFont);
        lblClienteId.setForeground(azulEscuro);
        JTextField txtClienteId = new JTextField();

        JLabel lblEntrada = new JLabel("Data Entrada:");
        lblEntrada.setFont(labelFont);
        lblEntrada.setForeground(azulEscuro);

        JFormattedTextField txtEntrada = createDateField();

        JLabel lblSaida = new JLabel("Data Saída:");
        lblSaida.setFont(labelFont);
        lblSaida.setForeground(azulEscuro);

        JFormattedTextField txtSaida = createDateField();

        JLabel lblValor = new JLabel("Valor Total:");
        lblValor.setFont(labelFont);
        lblValor.setForeground(azulEscuro);
        JTextField txtValor = new JTextField();

        txtClienteId.setFont(inputFont);
        txtEntrada.setFont(inputFont);
        txtSaida.setFont(inputFont);
        txtValor.setFont(inputFont);

        JButton btnSalvar = new JButton("Salvar");
        styleButton(btnSalvar);
        btnSalvar.addActionListener(e -> salvarOrdemServico(txtClienteId, txtEntrada, txtSaida, txtValor));

        JButton btnVoltar = new JButton("Voltar");
        styleButton(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(branco);

        panel.add(lblClienteId);
        panel.add(txtClienteId);
        panel.add(lblEntrada);
        panel.add(txtEntrada);
        panel.add(lblSaida);
        panel.add(txtSaida);
        panel.add(lblValor);
        panel.add(txtValor);
        panel.add(btnSalvar);
        panel.add(btnVoltar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new OrderService();
    }

    private JFormattedTextField createDateField() {
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter(' ');
            return new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JFormattedTextField();
    }

    private void salvarOrdemServico(JTextField txtClienteId, JFormattedTextField txtEntrada, JFormattedTextField txtSaida, JTextField txtValor) {
        String clienteId = txtClienteId.getText().trim();
        String entrada = txtEntrada.getText().trim();
        String saida = txtSaida.getText().trim();
        String valorText = txtValor.getText().trim();

        if (clienteId.isEmpty() || entrada.isEmpty() || saida.isEmpty() || valorText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double valorTotal = Double.parseDouble(valorText.replace(",", "."));

            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO ordem_servico (cliente_id, data_entrada, data_saida, valor_total) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(clienteId)); // Configura o Cliente ID
            stmt.setString(2, entrada); // Data de Entrada
            stmt.setString(3, saida); // Data de Saída
            stmt.setDouble(4, valorTotal); // Valor Total

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Ordem de Serviço cadastrada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar ordem de serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            stmt.close();
            conn.close();

            limparCampos(txtClienteId, txtEntrada, txtSaida, txtValor);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos(JTextField txtClienteId, JFormattedTextField txtEntrada, JFormattedTextField txtSaida, JTextField txtValor) {
        txtClienteId.setText("");
        txtEntrada.setText("");
        txtSaida.setText("");
        txtValor.setText("");
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
