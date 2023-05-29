package ua.ihromant.ui;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    static Stream<String> split(String s, char ch) {
        Iterable<String> it = () -> new Iterator<>() {
            private int idx = s.isEmpty() || s.charAt(0) != ch ? 0 : 1;

            @Override
            public boolean hasNext() {
                return idx < s.length();
            }

            @Override
            public String next() {
                int pos = s.indexOf(ch, idx);
                if (pos < 0) {
                    pos = s.length();
                }
                String res = s.substring(idx, pos);
                idx = pos + 1;
                return res;
            }
        };
        return StreamSupport.stream(it.spliterator(), false);
    }
}
