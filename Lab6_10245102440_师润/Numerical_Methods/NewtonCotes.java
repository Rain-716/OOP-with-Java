package Numerical_Methods;

public class NewtonCotes {
    //梯形公式 (单段)
    public double Trapozoidal(Function f, double a, double b) {
        return (b - a) * (f.eval(a) + f.eval(b)) / 2.0;
    }
    //Simpson 公式 (单段)
    public double Simpson(Function f, double a, double b) {
        double mid = (a + b) / 2.0;
        return (b - a) * (f.eval(a) + 4 * f.eval(mid) + f.eval(b)) / 6.0;
    }
}