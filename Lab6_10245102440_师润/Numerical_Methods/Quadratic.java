package Numerical_Methods;

public class Quadratic implements DifferentiableFunction {
    private final double a, b, c;
    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public double eval(double x) {
        return a * x * x + b * x + c;
    }
    @Override
    public double diff(double x) {
        return 2 * a * x + b;
    }
}