package Controller;

import Model.*;
import View.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainViewController implements ActionListener {
    
    private MainView main;
    private Polynomial firstPolynomial;
    private Polynomial secondPolynomial;

    private static final String noPolynomialsMessage = "Please introduce at least one polynomial.";
    private static final String onlyOnePolynomialMessage = "In order to perform this operation, you need two polynomials.";
    private static final String cannotDivide = "Division cannot be performed on the given polynomials";
    private static final String divisionByZeroMessage = "Division by 0 is not permited.";

    private final OperationsManager operationsManager = new OperationsManager();

    public MainViewController(MainView mainView){
        this.main = mainView;

        for (Operation operation: Operation.values()) {
            main.getOperationsComboBox().addItem(operation);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String firstPolynomialString = main.getPolynomialInput1().getText();
        String secondPolynomialString = main.getPolynomialInput2().getText();
        Operation selectedOperation = (Operation)main.getOperationsComboBox().getSelectedItem();

        firstPolynomial = PolynomialManager.getPolynomialFromString(firstPolynomialString);
        secondPolynomial = PolynomialManager.getPolynomialFromString(secondPolynomialString);

        Object source = e.getSource();

        if (source == main.getCalculateButton()) {
            if (firstPolynomial == null && secondPolynomial == null) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), noPolynomialsMessage);
            } else if ((firstPolynomial == null && secondPolynomial != null) ||
                    (firstPolynomial != null && secondPolynomial == null)) {
                if (selectedOperation == Operation.Derivative) {
                    displayDerivative();
                } else if (selectedOperation == Operation.Integration) {
                    displayIntegration();
                } else {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), onlyOnePolynomialMessage);
                }
            } else {
                if (selectedOperation == Operation.Addition) {
                    displayAddition();
                }

                if (selectedOperation == Operation.Subtraction) {
                    displaySubtraction();
                }

                if (selectedOperation == Operation.Multiplication) {
                    displayMultiplication();
                }

                if (selectedOperation == Operation.Division) {
                    displayDivision();
                }

                if (selectedOperation == Operation.Derivative) {
                    displayDerivative();
                }

                if (selectedOperation == Operation.Integration) {
                    displayIntegration();
                }
            }
        }
    }

    // Private Functions
    private void displayAddition() {
        if (firstPolynomial != null && secondPolynomial != null) {
            Polynomial result = operationsManager.addition(firstPolynomial, secondPolynomial);

            if (result.getMonomials().size() == 0) {
                main.getFirstResult().setText("0");
            } else {
                main.getFirstResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getFirstResult().setVisible(true);
            main.getSecondResult().setVisible(false);
        }
    }

    private void displaySubtraction() {
        if (firstPolynomial != null && secondPolynomial != null) {
            Polynomial result = operationsManager.subtraction(firstPolynomial, secondPolynomial);

            if (result.getMonomials().size() == 0) {
                main.getFirstResult().setText("0");
            } else {
                main.getFirstResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getFirstResult().setVisible(true);
            main.getSecondResult().setVisible(false);
        }
    }

    private void displayMultiplication() {
        if (firstPolynomial != null && secondPolynomial != null) {
            Polynomial result = operationsManager.multiplication(firstPolynomial, secondPolynomial);

            if (result.getMonomials().size() == 0) {
                main.getFirstResult().setText("0");
            } else {
                main.getFirstResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getFirstResult().setVisible(true);
            main.getSecondResult().setVisible(false);
        }
    }

    private void displayDivision() {
        if (secondPolynomial.getMonomials().size() == 1) {
            if (secondPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                    secondPolynomial.getMonomials().get(0).getPower() == 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), divisionByZeroMessage);
                return;
            }
        }

        if (firstPolynomial.getMonomials().size() == 1) {
            if (firstPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                    firstPolynomial.getMonomials().get(0).getPower() == 0) {
                main.getFirstResult().setText("Result: 0");
                main.getSecondResult().setText("Remainder: 0");
                main.getFirstResult().setVisible(true);
                main.getSecondResult().setVisible(true);

                return;
            }
        }

        if (firstPolynomial != null && secondPolynomial != null) {
            ArrayList<Polynomial> result = operationsManager.division(firstPolynomial, secondPolynomial);

            if (result == null || result.get(0) == null || result.get(1) == null) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), cannotDivide);
                return;
            }

            String quotientString = "";
            String remainderString = "";

            if (result.get(0).getMonomials().size() == 0) {
                quotientString = "0";
            } else {
                quotientString = PolynomialManager.getStringFromPolynomial(result.get(0));
            }

            if (result.get(1).getMonomials().size() == 0) {
                remainderString = "0";
            } else {
                remainderString = PolynomialManager.getStringFromPolynomial(result.get(1));
            }

            main.getFirstResult().setText("Result: " + quotientString);
            main.getSecondResult().setText("Remainder: " + remainderString);
            main.getFirstResult().setVisible(true);
            main.getSecondResult().setVisible(true);
        }
    }

    private void displayDerivative() {
        if (firstPolynomial != null) {
            if (firstPolynomial.getMonomials().size() == 1) {
                if (firstPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                        firstPolynomial.getMonomials().get(0).getPower() == 0) {
                    main.getFirstResult().setText("0");
                }
            } else {
                Polynomial result = operationsManager.derivative(firstPolynomial);

                main.getFirstResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getFirstResult().setVisible(true);
        } else {
            main.getFirstResult().setVisible(false);
        }

        if (secondPolynomial != null) {
            if (secondPolynomial.getMonomials().size() == 1) {
                if (secondPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                        secondPolynomial.getMonomials().get(0).getPower() == 0) {
                    main.getSecondResult().setText("0");
                }
            } else {
                Polynomial result = operationsManager.derivative(secondPolynomial);

                main.getSecondResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getSecondResult().setVisible(true);
        } else {
            main.getSecondResult().setVisible(false);
        }
    }

    private void displayIntegration() {
        if (firstPolynomial != null) {
            if (firstPolynomial.getMonomials().size() == 1) {
                if (firstPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                        firstPolynomial.getMonomials().get(0).getPower() == 0) {
                    main.getFirstResult().setText("0");
                }
            } else {
                Polynomial result = operationsManager.integration(firstPolynomial);

                main.getFirstResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getFirstResult().setVisible(true);
        } else {
            main.getFirstResult().setVisible(false);
        }

        if (secondPolynomial != null) {
            if (secondPolynomial.getMonomials().size() == 1) {
                if (secondPolynomial.getMonomials().get(0).getCoefficient() == 0 &&
                        secondPolynomial.getMonomials().get(0).getPower() == 0) {
                    main.getSecondResult().setText("0");
                }
            } else {
                Polynomial result = operationsManager.integration(secondPolynomial);

                main.getSecondResult().setText(PolynomialManager.getStringFromPolynomial(result));
            }

            main.getSecondResult().setVisible(true);
        } else {
            main.getSecondResult().setVisible(false);
        }
    }

}
