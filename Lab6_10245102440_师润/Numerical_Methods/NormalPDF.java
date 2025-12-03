package Numerical_Methods;

public class NormalPDF implements DifferentiableFunction {
    private final double mu, sigma;
    public NormalPDF(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }
    @Override
    public double eval(double x) {
        return Math.exp(-Math.pow(x - mu, 2) / (2 * sigma * sigma));
    }
    @Override
    public double diff(double x) {
        return eval(x) * (-(x - mu) / (sigma * sigma));
    }
}