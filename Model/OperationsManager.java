package Model;

import java.util.ArrayList;

public class OperationsManager {

    // Methods
    public Polynomial addition(Polynomial p, Polynomial q) {
        Polynomial result = p;

        for (Monomial monomial : q.getMonomials()) {
            result.addMonomial(monomial);
        }

        return removeZeros(result);
    }

    public Polynomial subtraction(Polynomial p, Polynomial q) {
        // invert the sign of the coefficients in q
        for (Monomial monomial : q.getMonomials()) {
            monomial.setCoefficient(monomial.getCoefficient() * (-1));
        }

        Polynomial result = addition(p, q);

        return removeZeros(result);
    }

    public Polynomial multiplication(Polynomial p, Polynomial q) {
        Polynomial result = new Polynomial();

        for (Monomial monomialP : p.getMonomials()) {
            for (Monomial monomialQ : q.getMonomials()) {
                double newCoefficient = monomialP.getCoefficient() * monomialQ.getCoefficient();
                int newPower = monomialP.getPower() + monomialQ.getPower();

                result.addMonomial(new Monomial(newPower, newCoefficient));
            }
        }

        return removeZeros(result);
    }

    public ArrayList<Polynomial> division(Polynomial p, Polynomial q) {
        ArrayList<Polynomial> result = new ArrayList<Polynomial>();
        Polynomial quotient = new Polynomial();

        if (p.getDegree() < q.getDegree()) {
            return null;
        }

        while (p.getDegree() >= q.getDegree() &&
                p.getMonomials().size() > 0 && q.getMonomials().size() > 0) {
            Monomial monomialP = p.getMaxDegreeMonomial();
            Monomial monomialQ = q.getMaxDegreeMonomial();
            Monomial monomialQuotient = divideMonomials(monomialP, monomialQ);

            if (monomialQuotient == null) {
                return null;
            }

            quotient.addMonomial(monomialQuotient);
            Polynomial auxPolynomial = new Polynomial();
            auxPolynomial.addMonomial(monomialQuotient);
            auxPolynomial = multiplication(auxPolynomial, q);

            p = subtraction(p, auxPolynomial);
        }

        result.add(quotient);
        result.add(p); // the remainder of the division was lest in the polynomial p

        return result;
    }

    public Polynomial derivative(Polynomial p) {
        Polynomial result = new Polynomial();

        for (Monomial monomial : p.getMonomials()) {
            double newCoefficient = monomial.getCoefficient() * monomial.getPower();
            int newPower = monomial.getPower() - 1;

            if (monomial.getPower() == 0) {
                newCoefficient = 0;
                newPower = 0;
            }

            result.addMonomial(new Monomial(newPower, newCoefficient));
        }

        return removeZeros(result);
    }

    public Polynomial integration(Polynomial p) {
        Polynomial result = new Polynomial();

        for (Monomial monomial : p.getMonomials()) {
            double newCoefficient = monomial.getCoefficient() / (monomial.getPower() + 1);
            int newPower = monomial.getPower() + 1;

            result.addMonomial(new Monomial(newPower, newCoefficient));
        }

        return removeZeros(result);
    }

    // Private Functions
    private Polynomial removeZeros(Polynomial p) {
        Polynomial result = new Polynomial();

        for (Monomial monomial : p.getMonomials()) {
            if (monomial.getCoefficient() != 0) {
                result.addMonomial(monomial);
            }
        }

        return result;
    }

    private Monomial divideMonomials(Monomial p, Monomial q) {
        if (p.getPower() < q.getPower()) {
            return null;
        } else {
            int newPower = p.getPower() - q.getPower();
            double newCoefficient = p.getCoefficient() / q.getCoefficient();
            return new Monomial(newPower, newCoefficient);
        }
    }

}
