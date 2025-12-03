package Numerical_Methods;

public class MainTest {
    public static void main(String[] args) {
        // 测试函数
        DifferentiableFunction[] funcs = new DifferentiableFunction[] {
            new Linear(2, -4),       // 2x - 4
            new Quadratic(1, -3, 2),  // x^2 -3x +2
            new Sin(Math.PI, 0),      // sin(pi x)
            new NormalPDF(0, 1)       // e^{-(x^2)/2}
        };

        // 牛顿法求根
        NewtonRoot rootFinder = new NewtonRoot(1e-8, 100, 1.0);
        System.out.println("-- Newton Method Roots --");
        for (DifferentiableFunction f : funcs) {
            try {
                double root = rootFinder.findRoot(f);
                System.out.printf("Root of %s: %.6f%n", f.getClass().getSimpleName(), root);
            } catch (Exception e) {
                System.out.printf("Failed to find root for %s: %s%n", f.getClass().getSimpleName(), e.getMessage());
            }
        }

        // 积分测试
        NewtonCotes nc = new NewtonCotes();
        double a = 0, b = 1;
        System.out.println("-- Numerical Integration [0,1] --");
        for (Function f : funcs) {
            double trap = nc.Trapozoidal(f, a, b);
            double simp = nc.Simpson(f, a, b);
            System.out.printf("%s: Trapezoidal=%.6f, Simpson=%.6f%n",
                f.getClass().getSimpleName(), trap, simp);
        }
    }
}