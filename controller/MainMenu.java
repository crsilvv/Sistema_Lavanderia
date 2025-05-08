package controller;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Sistema de Lavanderia");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Menu Principal", JLabel.CENTER);
        lblTitulo.setBounds(100, 20, 200, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 153));
        add(lblTitulo);

        JButton btnClientes = new JButton("Cadastro de Clientes");
        styleButton(btnClientes);
        btnClientes.setBounds(100, 70, 200, 30);
        btnClientes.addActionListener(e -> new RegisterClient());
        add(btnClientes);

        JButton btnServicos = new JButton("Cadastro de Serviços");
        styleButton(btnServicos);
        btnServicos.setBounds(100, 110, 200, 30);
        btnServicos.addActionListener(e -> new RegisterService());
        add(btnServicos);

        JButton btnOrdem = new JButton("Nova Ordem de Serviço");
        styleButton(btnOrdem);
        btnOrdem.setBounds(100, 150, 200, 30);
        btnOrdem.addActionListener(e -> new OrderService());
        add(btnOrdem);

        JButton btnConsulta = new JButton("Consultar Pedidos");
        styleButton(btnConsulta);
        btnConsulta.setBounds(100, 190, 200, 30);
        btnConsulta.addActionListener(e -> new CheckOrders());
        add(btnConsulta);

        JButton btnSair = new JButton("Sair");
        styleButton(btnSair);
        btnSair.setBounds(100, 230, 200, 30);
        btnSair.addActionListener(e -> System.exit(0));
        add(btnSair);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(134, 201, 226));
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
                button.setBackground(new Color(148, 201, 224));
            }
        });
    }
}
