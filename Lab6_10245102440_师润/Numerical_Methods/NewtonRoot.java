package Numerical_Methods;

public class NewtonRoot {
    private final double tol;
    private final int maxIter;
    private final double initialGuess;

    public NewtonRoot() {
        this(1e-6, 100, 0.0);
    }
    public NewtonRoot(double tol, int maxIter, double initialGuess) {
        this.tol = tol;
        this.maxIter = maxIter;
        this.initialGuess = initialGuess;
    }
    public double findRoot(DifferentiableFunction f) {
        double x = initialGuess;
        for (int i = 0; i < maxIter; i++) {
            double fx = f.eval(x);
            double dfx = f.diff(x);
            if (Math.abs(dfx) < 1e-12) {
                throw new ArithmeticException("Derivative too small");
            }
            double xNew = x - fx / dfx;
            if (Math.abs(xNew - x) < tol) {
                return xNew;
            }
            x = xNew;
        }
        throw new RuntimeException("Newton method did not converge");
    }
}