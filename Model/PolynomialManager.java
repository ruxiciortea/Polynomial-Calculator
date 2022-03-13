package Model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

public class PolynomialManager {

    // Static Methods
    public static Polynomial getPolynomialFromString(String polynomialString) {
        if (polynomialString.isEmpty()) {
            return null;
        }

        polynomialString = polynomialString.replaceAll("\\s", ""); // remove all the white spaces in the string
        polynomialString = polynomialString.toLowerCase(); // make all characters lowe cases
        polynomialString = addChar(polynomialString, ' ', 0); // add a space at the beginning of the string
        polynomialString = addChar(polynomialString, ' ', polynomialString.length()); // add a space at the end of the string

        // adding spaces before and after each sign in order to help separating the string
        for (int i = 2; i < polynomialString.length(); i++) {
            if (polynomialString.charAt(i) == '-') {
                polynomialString = addChar(polynomialString, ' ', i);
                polynomialString = addChar(polynomialString, ' ', i);
                i += 2;
            } else if (polynomialString.charAt(i) == '+') {
                polynomialString = addChar(polynomialString, ' ', i);
                i++;
                polynomialString = addChar(polynomialString, ' ', i + 1);
                i++;
            }
        }

        Pattern pattern = Pattern.compile(" (.*?) ");
        Matcher mather = pattern.matcher(polynomialString);
        ArrayList<Monomial> monomials = new ArrayList<Monomial>();

        while (mather.find()) {
            String potentialMonomial = mather.group();
            Monomial monomial = extractMonomial(potentialMonomial);

            if (monomial == null) {
                return null;
            } else {
                monomials.add(monomial);
            }
        }

        Polynomial polynomial = new Polynomial(monomials);

        return polynomial;
    }

    public static String getStringFromPolynomial(Polynomial p) {
        String resultString = "";
        int monomialPosition = 0;

        for (Monomial monomial : p.getMonomials()) {
            double coefficient = monomial.getCoefficient();
            int power = monomial.getPower();

            if (coefficient == 0) {
                continue;
            } else if (Math.abs(coefficient) == 1) {
                if (coefficient > 0 && monomialPosition > 0) {
                    resultString += "+";
                }

                if (coefficient < 0) {
                    resultString += "-";
                }

                if (power == 0) {
                    resultString += "1";
                }
            } else {
                if (coefficient > 0 && monomialPosition > 0) {
                    resultString += "+";
                }

                if ((coefficient == Math.floor(coefficient)) && !Double.isInfinite(coefficient)) {
                    resultString += String.valueOf((int)coefficient);
                } else {
                    resultString += String.valueOf(coefficient);
                }
            }

            if (power == 0) {
                resultString += "";
            } else if (power == 1) {
                resultString += "x";
            } else {
                resultString += "x^";
                resultString += String.valueOf(power);
            }

            monomialPosition++;
        }

        return resultString;
    }

    // Private Functions
    private static Monomial extractMonomial(String monomialString) {
        monomialString = monomialString.replaceAll("\\s", "");

        // if the monomial contains x more than once
        if (monomialString.indexOf('x') != monomialString.lastIndexOf('x')) {
            return null;
        }

        // if the monomial contains ^ more than once
        if (monomialString.indexOf('^') != monomialString.lastIndexOf('^')) {
            return null;
        }

        Integer sign = getSign(monomialString);
        Integer coefficient = getCoefficient(monomialString);
        Integer power = getPower(monomialString);

        if (sign != null && coefficient != null && power != null) {
            return new Monomial(power, coefficient);
        }

        return null;
    }

    private static Integer getSign(String monomialString) {
        int sign = 1;

        if (monomialString.charAt(0) == '-') {
            sign = -1;
            monomialString = monomialString.replaceFirst("-", "");
        }

        return sign;
    }

    private static Integer getCoefficient(String monomialString) {
        String coefficientPart = monomialString;

        if (monomialString.contains("x")) {
            coefficientPart = monomialString.substring(0, monomialString.indexOf('x'));
        }

        if (coefficientPart.isEmpty()) {
            return 1;
        }

        if (coefficientPart.contains("-") && coefficientPart.length() == 1) {
            return -1;
        }

        try {
            int number = Integer.parseInt(coefficientPart);
            return number;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Integer getPower(String monomialString) {
        String powerPart = "";

        if (monomialString.contains("x")) {
            if (monomialString.contains("^")) {
                powerPart = monomialString.substring(monomialString.indexOf('^') + 1, monomialString.length());
            } else {
                return 1;
            }
        } else {
            return 0;
        }

        if (powerPart.isEmpty()) {
            return null;
        }

        try {
            int number = Integer.parseInt(powerPart);
            return number;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static String addChar(String string, char ch, int position) {
        return string.substring(0, position) + ch + string.substring(position);
    }

}
