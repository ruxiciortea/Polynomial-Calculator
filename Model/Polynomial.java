package Model;

import java.util.ArrayList;

public class Polynomial {

    private ArrayList<Monomial> monomials;
    private int degree;

    public Polynomial() {
        this.monomials = new ArrayList<Monomial>();
        this.degree = 0;
    }

    public Polynomial(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
        degree = this.calculateDegree();
    }

    public Polynomial(Monomial monomial) {
        this.monomials.add(monomial);
        degree = monomial.getPower();
    }

    // Getters and Setters
    public ArrayList<Monomial> getMonomials() {
        return monomials;
    }

    public int getDegree() {
        return degree;
    }

    public void setMonomials(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
        degree = calculateDegree();
    }

    public void addMonomial(Monomial monomial) {
        boolean added = false;

        // here I used this kind of for because in the foreach loop I was not able to modify the array list
        for (int i = 0; i < monomials.size(); i++) {
            if (monomials.get(i).getPower() == monomial.getPower()) {
                double newCoefficient = monomials.get(i).getCoefficient() + monomial.getCoefficient();
                Monomial foundMonomial = getMonomialForPower(monomial.getPower());

                if (foundMonomial != null) {
                    foundMonomial.setCoefficient(newCoefficient);
                    added = true;
                    break;
                }
            }
        }

        if (added == false) {
            monomials.add(monomial);
        }

        if (monomial.getPower() > degree) {
            degree = monomial.getPower();
        }
    }

    public void removeMonomial(Monomial monomial) {
        monomials.remove(monomial);
        degree = calculateDegree();
    }

    public Monomial getMonomialForPower(int power) {
        for (Monomial currentMonomial : monomials) {
            if (currentMonomial.getPower() == power) {
                return currentMonomial;
            }
        }

        return null;
    }

    public Monomial getMaxDegreeMonomial() {
        return getMonomialForPower(degree);
    }

    // Private Methods
    private int calculateDegree() {
        if (monomials.isEmpty()) {
            return 0;
        }

        int max = monomials.get(0).getPower();

        for (Monomial monomial : monomials) {
            if (monomial.getPower() > max) {
                max = monomial.getPower();
            }
        }

        return max;
    }

}
