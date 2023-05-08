package ua.ihromant.ui;

public interface HTMLUtil {
    static String convert(Color color) {
        int promille;
        if (color.a() == 1.0 || (promille = round(color.a() * 1000)) == 1000) {
            return "rgb(" + color.r() + "," + color.g() + "," + color.b() + ")";
        } else {
            return "rgba(" + color.r() + "," + color.g() + "," + color.b() + "," + promille(promille) + ")";
        }
    }

    private static String promille(int promille) {
        int whole = promille / 1000;
        int rest = promille % 1000;
        return whole + "." + (rest < 10 ? "00" + rest : rest < 100 ? "0" + rest : String.valueOf(rest));
    }

    static String percents(int percents) {
        int whole = percents / 100;
        int rest = percents % 100;
        return whole + "." + (rest < 10 ? "0" + rest : String.valueOf(rest));
    }

    static int round(double a) {
        return (int) (a + Math.signum(a) * 0.5);
    }
}
