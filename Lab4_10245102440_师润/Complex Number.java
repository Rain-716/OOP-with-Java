//笛卡尔坐标系
class ComplexCart {
    private final double real;
    private final double imag;

    public ComplexCart(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public ComplexCart add(ComplexCart x) {
        return new ComplexCart(this.real + x.real, this.imag + x.imag);
    }

    public ComplexCart subtract(ComplexCart x) {
        return new ComplexCart(this.real - x.real, this.imag - x.imag);
    }

    public ComplexCart multiply(ComplexCart x) {
        double r = this.real * x.real - this.imag * x.imag;
        double i = this.real * x.imag + this.imag * x.real;
        return new ComplexCart(r, i);
    }

    public ComplexCart divide(ComplexCart x) {
        double denom = x.real*x.real + x.imag*x.imag;
        double r = (this.real * x.real + this.imag * x.imag) / denom;
        double i = (this.imag * x.real - this.real * x.imag) / denom;
        return new ComplexCart(r, i);
    }

    public ComplexCart reciprocal() {
        double denom = real*real + imag*imag;
        return new ComplexCart(real/denom, -imag/denom);
    }

    public ComplexCart conjugate() {
        return new ComplexCart(real, -imag);
    }

    public double abs() {
        return Math.hypot(real, imag);
    }

    public double getRealPart() {
        return real;
    }

    public double getImaginaryPart() {
        return imag;
    }

    public boolean equals(ComplexCart x) {
        return Double.compare(real, x.real) == 0 && Double.compare(imag, x.imag) == 0;
    }

    @Override
    public String toString() {
        if (imag >= 0) return real + " + " + imag + "i";
        else return real + " - " + (-imag) + "i";
    }
    public ComplexPolar toPolar() {
        double r = Math.hypot(real, imag);
        double phi = Math.atan2(imag, real);
        return new ComplexPolar(r, phi);
    }

    public ComplexCart exp() {
        double realPart = Math.exp(this.real) * Math.cos(this.imag);
        double imaginaryPart = Math.exp(this.real) * Math.sin(this.imag);
        return new ComplexCart(realPart, imaginaryPart);
    }

    public ComplexCart log() {
        double modulus = Math.sqrt(this.real * this.real + this.imag * this.imag);
        double argument = Math.atan2(this.imag, this.real);
        return new ComplexCart(Math.log(modulus), argument);
    }

    public ComplexCart pow(double a) {
        ComplexCart logZ = this.log();
        double realPart = Math.exp(a * logZ.getRealPart()) * Math.cos(a * logZ.getImaginaryPart());
        double imaginaryPart = Math.exp(a * logZ.getRealPart()) * Math.sin(a * logZ.getImaginaryPart());
        return new ComplexCart(realPart, imaginaryPart);
    }
}

//极坐标系
class ComplexPolar {
    private final double r;
    private final double phi;

    public ComplexPolar(double r, double phi) {
        if (r < 0) throw new IllegalArgumentException("半径不能为负数");
        this.r = r;
        this.phi = normalize(phi);
    }

    private double normalize(double angle) {
        double a = angle;
        while (a <= -Math.PI) a += 2*Math.PI;
        while (a > Math.PI) a -= 2*Math.PI;
        return a;
    }

    public ComplexPolar add(ComplexPolar x) {
        // 转换为笛卡尔坐标做加法，再转换回来
        ComplexCart a = this.toCartesian();
        ComplexCart b = x.toCartesian();
        ComplexCart sum = a.add(b);
        return sum.toPolar();
    }

    public ComplexPolar subtract(ComplexPolar x) {
        ComplexCart a = this.toCartesian();
        ComplexCart b = x.toCartesian();
        ComplexCart diff = a.subtract(b);
        return diff.toPolar();
    }

    public ComplexPolar multiply(ComplexPolar x) {
        return new ComplexPolar(this.r * x.r, this.phi + x.phi);
    }

    public ComplexPolar divide(ComplexPolar x) {
        return new ComplexPolar(this.r / x.r, this.phi - x.phi);
    }

    public ComplexPolar reciprocal() {
        return new ComplexPolar(1.0 / r, -phi);
    }

    public ComplexPolar conjugate() {
        return new ComplexPolar(r, -phi);
    }

    public double abs() {
        return r;
    }

    public double getRealPart() {
        return r * Math.cos(phi);
    }

    public double getImaginaryPart() {
        return r * Math.sin(phi);
    }

    public boolean equals(ComplexPolar x) {
        return Double.compare(r, x.r) == 0 && Double.compare(phi, x.phi) == 0;
    }

    @Override
    public String toString() {
        return String.format("%.4f e^{i%.4f}", r, phi);
    }

    public ComplexCart toCartesian() {
        return new ComplexCart(getRealPart(), getImaginaryPart());
    }

    public ComplexPolar exp() {
        // 用笛卡尔坐标系解释 z = x + iy
        ComplexCart cart = this.toCartesian();
        double x = cart.getRealPart(), y = cart.getImaginaryPart();
        double expx = Math.exp(x);
        return new ComplexPolar(expx * Math.hypot(1,0), normalize(y));
    }

    public ComplexPolar log() {
        return new ComplexPolar(Math.log(r), phi);
    }

    public ComplexPolar pow(double a) {
        double newR = Math.pow(r, a);
        double newPhi = a * phi;
        return new ComplexPolar(newR, newPhi);
    }
}

class ComplexNumberProject {
    public static ComplexCart[] solveQuadratic(double a, double b, double c) {
        if (a == 0) throw new IllegalArgumentException("不是一元二次方程");
        double disc = b*b - 4*a*c;
        if (disc >= 0) {
            double sqrt = Math.sqrt(disc);
            return new ComplexCart[]{
                new ComplexCart((-b + sqrt)/(2*a), 0),
                new ComplexCart((-b - sqrt)/(2*a), 0)
            };
        } else {
            double sqrt = Math.sqrt(-disc);
            return new ComplexCart[]{
                new ComplexCart(-b/(2*a), sqrt/(2*a)),
                new ComplexCart(-b/(2*a), -sqrt/(2*a))
            };
        }
    }

    public static void main(String[] args) {
        // 示例
        ComplexCart a = new ComplexCart(1, 2);
        ComplexCart b = new ComplexCart(3, 4);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a * b = " + a.multiply(b));

        ComplexPolar p = a.toPolar().multiply(b.toPolar());
        System.out.println("(a*b) from polar = " + p.toCartesian());

        ComplexCart[] roots = solveQuadratic(1, -2, 5);
        System.out.println("Roots of x^2 -2x +5 = 0: " + roots[0] + ", " + roots[1]);
    }
}