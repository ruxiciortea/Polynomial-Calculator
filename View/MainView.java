package View;

import Controller.MainViewController;
import Model.Operation;
import Model.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JPanel Polynomial1;
    private JPanel Polynomial2;
    private JPanel Operation;
    private JPanel Result;
    private JComboBox operationsComboBox;
    private JButton calculateButton;
    private JTextField polynomialInput1;
    private JTextField polinomialInput2;
    private JLabel firstResult;
    private JLabel secondResult;

    private MainViewController controller;

    public MainView() {
        setTitle("Polynomial Calculator");
        setSize(500, 350);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        controller = new MainViewController(this);
        calculateButton.addActionListener(controller);

        firstResult.setVisible(false);
        secondResult.setVisible(false);
    }

    // Getters
    public JTextField getPolynomialInput1() {
        return polynomialInput1;
    }

    public JTextField getPolynomialInput2() {
        return polinomialInput2;
    }

    public JComboBox getOperationsComboBox() {
        return operationsComboBox;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

    public JLabel getFirstResult() {
        return firstResult;
    }

    public JLabel getSecondResult() {
        return secondResult;
    }
}
