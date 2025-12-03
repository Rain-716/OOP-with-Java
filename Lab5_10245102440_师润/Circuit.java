public abstract class Circuit {
    // 返回本网络的等效电阻
    public abstract double resistance();

    // 在两端施加电压，返回通过本网络的电流
    public double current(double voltage) {
        return voltage / resistance();
    }

    // 在两端施加电压 voltage 时，打印本节点及子节点的电压、电流
    public abstract void printVoltageCurrent(double voltage);

    // 解析字符串表达式，构造对应的 Circuit 对象树。
    public static Circuit parse(String expr) {
        expr = expr.trim();
        // 如果不是以 '(' 开头，则认为是单个电阻值
        if (!expr.startsWith("(")) {
            double val = Double.parseDouble(expr);
            return new Resistor(val);
        }
        // 去掉最外层括号
        String inside = expr.substring(1, expr.length() - 1).trim();

        // 第一个字符即运算符：'-' 或 '/'
        char op = inside.charAt(0);
        // 跳过 "op,"，从索引2开始解析子表达式
        String rest = inside.substring(2).trim();

        // 找到分隔左右子表达式的那个逗号
        int depth = 0;
        int splitPos = -1;
        for (int i = 0; i < rest.length(); i++) {
            char c = rest.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (c == ',' && depth == 0) {
                splitPos = i;
                break;
            }
        }
        if (splitPos < 0) {
            throw new IllegalArgumentException("无法解析表达式: " + expr);
        }

        // 左子表达式：[0, splitPos)，右子表达式：(splitPos+1, end]
        String leftExpr  = rest.substring(0, splitPos).trim();
        String rightExpr = rest.substring(splitPos + 1).trim();

        Circuit left  = parse(leftExpr);
        Circuit right = parse(rightExpr);
        if (op == '-') {
            return new Series(left, right);
        } else if (op == '/') {
            return new Parallel(left, right);
        } else {
            throw new IllegalArgumentException("未知运算符: " + op);
        }
    }

    // 测试演示
    public static void main(String[] args) {
        String[] tests = {
            "(-,3,5)",
            "(/,3,5)",
            "(-,(-,3,5),7)",
            "(-, (/, (/,4,8), 5), 3)"
        };
        double Vbat = 12.0;

        for (String expr : tests) {
            Circuit c = Circuit.parse(expr);
            System.out.println("表达式: " + expr);
            System.out.printf("  等效电阻 R = %.3f Ω%n", c.resistance());
            System.out.printf("  在 %.1f V 下总电流 I = %.3f A%n", Vbat, c.current(Vbat));
            System.out.println("  详细分布：");
            c.printVoltageCurrent(Vbat);
            System.out.println("-------------------------------");
        }
    }
}

// 单个电阻
class Resistor extends Circuit {
    private final double r;
    public Resistor(double r) { this.r = r; }
    @Override
    public double resistance() { return r; }

    @Override
    public void printVoltageCurrent(double voltage) {
        double i = current(voltage);
        System.out.printf("    Resistor %.3fΩ: U=%.3fV, I=%.3fA%n", r, voltage, i);
    }
}

// 串联电路
class Series extends Circuit {
    private final Circuit a, b;
    public Series(Circuit a, Circuit b) { this.a = a; this.b = b; }

    @Override
    public double resistance() {
        return a.resistance() + b.resistance();
    }

    @Override
    public void printVoltageCurrent(double voltage) {
        double i = current(voltage);
        System.out.printf("  Series: U_total=%.3fV, I=%.3fA%n", voltage, i);
        double ua = a.resistance() * i;
        double ub = b.resistance() * i;
        a.printVoltageCurrent(ua);
        b.printVoltageCurrent(ub);
    }
}

// 并联电路
class Parallel extends Circuit {
    private final Circuit a, b;
    public Parallel(Circuit a, Circuit b) { this.a = a; this.b = b; }

    @Override
    public double resistance() {
        double r1 = a.resistance(), r2 = b.resistance();
        return 1.0 / (1.0/r1 + 1.0/r2);
    }

    @Override
    public void printVoltageCurrent(double voltage) {
        double itot = current(voltage);
        System.out.printf("  Parallel: U=%.3fV, I_total=%.3fA%n", voltage, itot);
        a.printVoltageCurrent(voltage);
        b.printVoltageCurrent(voltage);
    }
}