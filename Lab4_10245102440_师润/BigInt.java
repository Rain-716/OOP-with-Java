import java.util.*;
import java.math.BigInteger;

public class BigInt {
    private static final int BASE = 1000000000;
    private List<Integer> digits; // 低位在前
    private int sign; // 1 或 -1, 0 用于零

    // 默认构造，值为0
    public BigInt() {
        this.digits = new ArrayList<>();
        this.digits.add(0);
        this.sign = 0;
    }

    // 从字符串构造
    public BigInt(String s) {
        if (s == null || s.isEmpty()) throw new NumberFormatException("空字符串");
        int pos = 0;
        sign = 1;
        if (s.charAt(0) == '-') { sign = -1; pos++; }
        else if (s.charAt(0) == '+') { pos++; }
        digits = new ArrayList<>();
        for (int i = s.length(); i > pos; i -= 9) {
            int start = Math.max(pos, i - 9);
            String part = s.substring(start, i);
            digits.add(Integer.parseInt(part));
        }
        normalize();
    }

    // 去除高位零，调整sign
    private void normalize() {
        while (digits.size() > 1 && digits.get(digits.size() - 1) == 0) {
            digits.remove(digits.size() - 1);
        }
        if (digits.size() == 1 && digits.get(0) == 0) sign = 0;
    }

    // 比较绝对值，用于后面实现加法运算
    private int absCompare(BigInt b) {
        if (digits.size() != b.digits.size())
            return digits.size() < b.digits.size() ? -1 : 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            if (!digits.get(i).equals(b.digits.get(i)))
                return digits.get(i) < b.digits.get(i) ? -1 : 1;
        }
        return 0;
    }

    // 加法绝对值: |this| + |b|，用于后面实现加法运算
    private BigInt absAdd(BigInt b) {
        BigInt res = new BigInt();
        res.sign = 1;
        res.digits.clear();
        long carry = 0;
        int n = Math.max(digits.size(), b.digits.size());
        for (int i = 0; i < n || carry != 0; i++) {
            long av = carry;
            if (i < digits.size()) av += digits.get(i);
            if (i < b.digits.size()) av += b.digits.get(i);
            res.digits.add((int)(av % BASE));
            carry = av / BASE;
        }
        return res;
    }

    // 减法绝对值: |this| - |b|, 需保证 |this| >= |b|，用于后面实现加法运算
    private BigInt absSubtract(BigInt b) {
        BigInt res = new BigInt();
        res.sign = 1;
        res.digits.clear();
        long carry = 0;
        for (int i = 0; i < digits.size(); i++) {
            long av = digits.get(i) - carry - (i < b.digits.size() ? b.digits.get(i) : 0);
            if (av < 0) { av += BASE; carry = 1; }
            else carry = 0;
            res.digits.add((int)av);
        }
        res.normalize();
        return res;
    }

    // 加法
    public BigInt add(BigInt b) {
        if (sign == 0) return b;
        if (b.sign == 0) return this;
        if (sign == b.sign) {
            BigInt res = absAdd(b);
            res.sign = sign;
            return res;
        } else {
            if (absCompare(b) >= 0) {
                BigInt res = absSubtract(b);
                res.sign = (res.sign == 0 ? 0 : sign);
                return res;
            } else {
                BigInt res = b.absSubtract(this);
                res.sign = (res.sign == 0 ? 0 : b.sign);
                return res;
            }
        }
    }

    // 减法，通过加相反数实现
    public BigInt subtract(BigInt b) {
        BigInt negB = new BigInt();
        negB.digits = new ArrayList<>(b.digits);
        negB.sign = -b.sign;
        return add(negB);
    }

    // 乘法
    public BigInt multiply(BigInt b) {
        if (sign == 0 || b.sign == 0) return new BigInt();
        BigInt res = new BigInt();
        res.sign = sign * b.sign;
        int n = digits.size(), m = b.digits.size();
        res.digits = new ArrayList<>(Collections.nCopies(n + m, 0));
        for (int i = 0; i < n; i++) {
            long carry = 0;
            for (int j = 0; j < m || carry != 0; j++) {
                long cur = res.digits.get(i+j) + carry + (long)digits.get(i) * (j < m ? b.digits.get(j) : 0);
                res.digits.set(i+j, (int)(cur % BASE));
                carry = cur / BASE;
            }
        }
        res.normalize();
        return res;
    }

    // 商和余数
    private static class DivMod { BigInt quot, rem; }

    // 除法与取模 (长除法)
    private DivMod divMod(BigInt b) {
        if (b.sign == 0) throw new ArithmeticException("除以零");
        DivMod dm = new DivMod();
        BigInt a = this.abs();
        BigInt divisor = b.abs();
        dm.quot = new BigInt();
        dm.quot.sign = (sign == 0 ? 0 : sign * b.sign);
        dm.quot.digits = new ArrayList<>(Collections.nCopies(a.digits.size(), 0));
        dm.rem = new BigInt();
        for (int i = a.digits.size() -1; i >= 0; i--) {
            dm.rem.digits.add(0, a.digits.get(i));
            dm.rem.normalize();
            int low = 0, high = BASE-1, best = 0;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                BigInt t = divisor.multiply(new BigInt(Integer.toString(mid)));
                if (t.absCompare(dm.rem) <= 0) { best = mid; low = mid + 1; }
                else high = mid -1;
            }
            dm.quot.digits.set(i, best);
            BigInt sub = divisor.multiply(new BigInt(Integer.toString(best)));
            dm.rem = dm.rem.subtract(sub);
        }
        dm.quot.normalize();
        dm.rem.sign = (dm.rem.sign == 0 ? 0 : sign);
        return dm;
    }

    public BigInt divide(BigInt b) { return divMod(b).quot; }
    public BigInt mod(BigInt b)    { return divMod(b).rem; }

    // 取绝对值
    private BigInt abs() {
        BigInt r = new BigInt();
        r.digits = new ArrayList<>(this.digits);
        r.sign = this.sign == 0 ? 0 : 1;
        return r;
    }

    // 比较
    public int compare(BigInt b) {
        if (sign != b.sign) return sign < b.sign ? -1 : 1;
        if (sign == 0) return 0;
        int cmp = absCompare(b);
        return sign > 0 ? cmp : -cmp;
    }

    public boolean equals(BigInt b) {
        return compare(b) == 0;
    }

    @Override
    public String toString() {
        if (sign == 0) return "0";
        StringBuilder sb = new StringBuilder();
        if (sign < 0) sb.append('-');
        sb.append(digits.get(digits.size()-1));
        for (int i = digits.size()-2; i >=0; i--) {
            sb.append(String.format("%09d", digits.get(i)));
        }
        return sb.toString();
    }

    // 计算n! (阶乘)
    public static BigInt factorial(int n) {
        BigInt res = new BigInt("1");
        for (int i = 2; i <= n; i++) {
            res = res.multiply(new BigInt(Integer.toString(i)));
        }
        return res;
    }

    // 计算2^n
    public static BigInt pow2(int n) {
        BigInt res = new BigInt("1");
        BigInt two = new BigInt("2");
        int exp = n;
        while (exp > 0) {
            if ((exp & 1) == 1) res = res.multiply(two);
            two = two.multiply(two);
            exp >>= 1;
        }
        return res;
    }

    // 测试及性能比较
    public static void main(String[] args) {
        // int 类型测试
        long t0 = System.nanoTime();
        long r = 1;
        for (int i = 2; i <= 20; i++) r *= i;
        long t1 = System.nanoTime();
        System.out.println("int 20! = " + r + ", 耗时=" + (t1 - t0) + "ns");

        // BigInt 测试
        t0 = System.nanoTime();
        BigInt f = BigInt.factorial(1000);
        t1 = System.nanoTime();
        System.out.println("BigInt 1000! 位数=" + f.toString().length() + ", 耗时=" + (t1 - t0) + "ns");

        // Java BigInteger 测试
        t0 = System.nanoTime();
        BigInteger bi = BigInteger.ONE;
        for (int i = 2; i <= 1000; i++) bi = bi.multiply(BigInteger.valueOf(i));
        t1 = System.nanoTime();
        System.out.println("BigInteger 1000! 位数=" + bi.toString().length() + ", 耗时=" + (t1 - t0) + "ns");

        // 2^n 测试
        t0 = System.nanoTime();
        BigInt p = BigInt.pow2(10000);
        t1 = System.nanoTime();
        System.out.println("BigInt 2^10000 位数=" + p.toString().length() + ", 耗时=" + (t1 - t0) + "ns");
    }
}