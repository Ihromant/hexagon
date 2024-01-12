package ua.ihromant.domain;

import java.util.Arrays;
import java.util.BitSet;

public class DennistonArcVisualizer implements Visualizer {
    private static final BitSet points = of(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 28, 29, 30, 31, 32, 33, 36, 37, 42, 43,
            46, 47, 48, 49, 54, 55, 58, 59, 60, 61, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 85, 87, 88, 90, 93, 95, 96,
            99, 100, 103, 104, 107, 108, 111, 112, 115, 117, 118, 120, 123, 125, 126, 132, 133, 134, 135, 140, 141, 142,
            143, 162, 163, 164, 165, 170, 171, 172, 173, 178, 179, 182, 183, 186, 187, 190, 191, 193, 195, 196, 198, 200,
            202, 205, 207, 209, 211, 213, 215, 216, 218, 220, 222, 225, 226, 228, 231, 232, 235, 237, 238, 241, 242, 245,
            246, 248, 251, 252, 255});
    private static final BitSet[] lines = Arrays.stream(new int[][]{{0, 16, 32, 48, 64, 80, 96, 112}, {0, 17, 68, 85, 170, 187, 238, 255}, {0, 18, 36, 54, 72, 90, 108, 126}, {0, 19, 76, 95, 173, 190, 225, 242}, {0, 60, 87, 107, 134, 186, 209, 237}, {0, 42, 82, 120, 142, 164, 220, 246}, {0, 58, 93, 103, 133, 191, 216, 226}, {0, 46, 88, 118, 141, 163, 213, 251}, {0, 59, 70, 125, 140, 183, 202, 241}, {0, 33, 66, 99, 132, 165, 198, 231}, {0, 61, 78, 115, 143, 178, 193, 252}, {0, 37, 74, 111, 135, 162, 205, 232}, {0, 28, 43, 55, 207, 211, 228, 248}, {0, 29, 104, 117, 171, 182, 195, 222}, {0, 30, 47, 49, 196, 218, 235, 245}, {0, 31, 100, 123, 172, 179, 200, 215}, {1, 17, 33, 49, 193, 209, 225, 241}, {1, 16, 103, 118, 171, 186, 205, 220}, {1, 19, 37, 55, 202, 216, 238, 252}, {1, 18, 107, 120, 172, 191, 198, 213}, {1, 61, 66, 126, 135, 187, 196, 248}, {1, 43, 70, 108, 143, 165, 200, 226}, {1, 59, 74, 112, 132, 190, 207, 245}, {1, 47, 78, 96, 140, 162, 195, 237}, {1, 58, 95, 100, 141, 182, 211, 232}, {1, 32, 90, 123, 133, 164, 222, 255}, {1, 60, 85, 104, 142, 179, 218, 231}, {1, 36, 80, 117, 134, 163, 215, 242}, {1, 29, 42, 54, 68, 88, 111, 115}, {1, 28, 64, 93, 170, 183, 235, 246}, {1, 31, 46, 48, 76, 82, 99, 125}, {1, 30, 72, 87, 173, 178, 228, 251}, {2, 18, 66, 82, 162, 178, 226, 242}, {2, 19, 32, 49, 70, 87, 100, 117}, {2, 16, 74, 88, 165, 183, 237, 255}, {2, 17, 36, 55, 78, 93, 104, 123}, {2, 42, 85, 125, 132, 172, 211, 251}, {2, 61, 80, 111, 140, 179, 222, 225}, {2, 46, 95, 115, 135, 171, 218, 246}, {2, 59, 90, 99, 143, 182, 215, 238}, {2, 33, 68, 103, 142, 173, 200, 235}, {2, 58, 64, 120, 134, 190, 196, 252}, {2, 37, 76, 107, 141, 170, 195, 228}, {2, 60, 72, 118, 133, 187, 207, 241}, {2, 30, 108, 112, 163, 191, 205, 209}, {2, 31, 43, 54, 193, 220, 232, 245}, {2, 28, 96, 126, 164, 186, 198, 216}, {2, 29, 47, 48, 202, 213, 231, 248}, {3, 19, 99, 115, 163, 179, 195, 211}, {3, 18, 33, 48, 207, 222, 237, 252}, {3, 17, 111, 125, 164, 182, 200, 218}, {3, 16, 37, 54, 196, 215, 226, 241}, {3, 43, 64, 104, 133, 173, 198, 238}, {3, 60, 68, 123, 141, 178, 202, 245}, {3, 47, 72, 100, 134, 170, 205, 225}, {3, 58, 76, 117, 142, 183, 193, 248}, {3, 32, 93, 126, 143, 172, 209, 242}, {3, 59, 88, 96, 135, 191, 220, 228}, {3, 36, 87, 112, 140, 171, 216, 255}, {3, 61, 82, 108, 132, 186, 213, 235}, {3, 31, 70, 90, 162, 190, 231, 251}, {3, 30, 42, 55, 66, 95, 107, 118}, {3, 29, 78, 80, 165, 187, 232, 246}, {3, 28, 46, 49, 74, 85, 103, 120}, {4, 36, 68, 100, 132, 164, 196, 228}, {4, 55, 64, 115, 140, 191, 200, 251}, {4, 32, 76, 104, 135, 163, 207, 235}, {4, 49, 72, 125, 143, 186, 195, 246}, {4, 16, 111, 123, 170, 190, 193, 213}, {4, 17, 46, 59, 205, 216, 231, 242}, {4, 18, 99, 117, 173, 187, 202, 220}, {4, 19, 42, 61, 198, 209, 232, 255}, {4, 28, 66, 90, 171, 179, 237, 245}, {4, 29, 37, 60, 70, 95, 103, 126}, {4, 30, 74, 80, 172, 182, 226, 248}, {4, 31, 33, 58, 78, 85, 107, 112}, {4, 47, 93, 118, 142, 165, 215, 252}, {4, 48, 88, 108, 134, 178, 218, 238}, {4, 43, 87, 120, 141, 162, 222, 241}, {4, 54, 82, 96, 133, 183, 211, 225}, {5, 37, 85, 117, 133, 165, 213, 245}, {5, 54, 80, 99, 141, 190, 216, 235}, {5, 33, 95, 123, 134, 162, 220, 248}, {5, 48, 90, 111, 142, 187, 209, 228}, {5, 17, 70, 82, 171, 191, 232, 252}, {5, 16, 47, 58, 66, 87, 104, 125}, {5, 19, 78, 88, 172, 186, 231, 241}, {5, 18, 43, 60, 74, 93, 100, 115}, {5, 29, 96, 120, 170, 178, 207, 215}, {5, 28, 36, 61, 195, 218, 226, 251}, {5, 31, 108, 118, 173, 183, 196, 222}, {5, 30, 32, 59, 200, 211, 237, 246}, {5, 46, 64, 107, 143, 164, 202, 225}, {5, 49, 68, 112, 135, 179, 198, 242}, {5, 42, 72, 103, 140, 163, 193, 238}, {5, 55, 76, 126, 132, 182, 205, 255}, {6, 54, 70, 118, 134, 182, 198, 246}, {6, 36, 66, 96, 142, 172, 202, 232}, {6, 48, 78, 120, 133, 179, 205, 251}, {6, 32, 74, 108, 141, 171, 193, 231}, {6, 18, 46, 58, 195, 215, 235, 255}, {6, 19, 107, 126, 162, 183, 207, 218}, {6, 16, 42, 60, 200, 222, 228, 242}, {6, 17, 103, 112, 165, 178, 196, 211}, {6, 30, 37, 61, 64, 88, 99, 123}, {6, 31, 68, 93, 163, 186, 225, 248}, {6, 28, 33, 59, 72, 82, 111, 117}, {6, 29, 76, 87, 164, 191, 238, 245}, {6, 49, 95, 104, 140, 187, 213, 226}, {6, 47, 90, 115, 132, 173, 216, 241}, {6, 55, 85, 100, 143, 190, 220, 237}, {6, 43, 80, 125, 135, 170, 209, 252}, {7, 55, 87, 103, 135, 183, 215, 231}, {7, 37, 82, 112, 143, 173, 218, 248}, {7, 49, 93, 107, 132, 178, 222, 232}, {7, 33, 88, 126, 140, 170, 211, 245}, {7, 19, 47, 59, 68, 80, 108, 120}, {7, 18, 64, 85, 163, 182, 228, 241}, {7, 17, 43, 61, 76, 90, 96, 118}, {7, 16, 72, 95, 164, 179, 235, 252}, {7, 31, 36, 60, 205, 213, 238, 246}, {7, 30, 100, 125, 162, 187, 193, 216}, {7, 29, 32, 58, 198, 220, 225, 251}, {7, 28, 104, 115, 165, 190, 202, 209}, {7, 48, 66, 117, 141, 186, 200, 255}, {7, 46, 70, 111, 133, 172, 196, 237}, {7, 54, 74, 123, 142, 191, 195, 242}, {7, 42, 78, 99, 134, 171, 207, 226}, {72, 88, 104, 120, 200, 216, 232, 248}, {42, 59, 76, 93, 162, 179, 196, 213}, {64, 82, 100, 118, 195, 209, 231, 245}, {46, 61, 68, 87, 165, 182, 207, 220}, {28, 32, 95, 99, 142, 178, 205, 241}, {29, 55, 90, 112, 134, 172, 193, 235}, {30, 36, 85, 111, 141, 183, 198, 252}, {31, 49, 80, 126, 133, 171, 202, 228}, {16, 43, 78, 117, 132, 191, 218, 225}, {17, 48, 74, 107, 140, 173, 215, 246}, {18, 47, 70, 123, 135, 186, 211, 238}, {19, 54, 66, 103, 143, 170, 222, 251}, {33, 60, 96, 125, 163, 190, 226, 255}, {37, 58, 108, 115, 164, 187, 237, 242}, {43, 58, 111, 126, 163, 178, 231, 246}, {47, 60, 99, 112, 164, 183, 232, 251}, {29, 33, 74, 118, 143, 179, 216, 228}, {28, 54, 78, 100, 135, 173, 213, 255}, {31, 37, 66, 120, 140, 182, 209, 235}, {30, 48, 70, 104, 132, 170, 220, 242}, {17, 42, 87, 108, 133, 190, 195, 248}, {16, 49, 82, 115, 141, 172, 207, 238}, {19, 46, 93, 96, 134, 187, 200, 245}, {18, 55, 88, 125, 142, 171, 196, 225}, {76, 80, 103, 123, 198, 218, 237, 241}, {32, 61, 72, 85, 162, 191, 202, 215}, {68, 90, 107, 117, 205, 211, 226, 252}, {36, 59, 64, 95, 165, 186, 193, 222}, {42, 58, 74, 90, 170, 186, 202, 218}, {78, 95, 108, 125, 198, 215, 228, 245}, {46, 60, 66, 80, 173, 191, 193, 211}, {70, 85, 96, 115, 205, 222, 235, 248}, {30, 54, 93, 117, 140, 164, 207, 231}, {31, 32, 88, 103, 132, 187, 195, 252}, {28, 48, 87, 123, 143, 163, 196, 232}, {29, 36, 82, 107, 135, 190, 200, 241}, {18, 49, 76, 111, 134, 165, 216, 251}, {19, 43, 72, 112, 142, 182, 213, 237}, {16, 55, 68, 99, 133, 162, 209, 246}, {17, 47, 64, 126, 141, 179, 220, 226}, {33, 61, 100, 120, 171, 183, 238, 242}, {37, 59, 104, 118, 172, 178, 225, 255}, {43, 59, 107, 123, 171, 187, 235, 251}, {47, 61, 103, 117, 172, 190, 228, 246}, {31, 55, 72, 96, 141, 165, 218, 242}, {30, 33, 76, 115, 133, 186, 215, 232}, {29, 49, 64, 108, 142, 162, 211, 255}, {28, 37, 68, 125, 134, 191, 222, 231}, {19, 48, 85, 118, 135, 164, 193, 226}, {18, 42, 80, 104, 143, 183, 205, 245}, {17, 54, 95, 120, 132, 163, 202, 237}, {16, 46, 90, 100, 140, 178, 198, 248}, {32, 60, 78, 82, 170, 182, 196, 216}, {74, 87, 99, 126, 200, 213, 225, 252}, {36, 58, 70, 88, 173, 179, 207, 209}, {66, 93, 111, 112, 195, 220, 238, 241}, {28, 60, 76, 108, 140, 172, 220, 252}, {29, 46, 72, 123, 132, 183, 209, 226}, {30, 58, 68, 96, 143, 171, 213, 241}, {31, 42, 64, 117, 135, 178, 216, 237}, {36, 48, 103, 115, 162, 182, 225, 245}, {32, 54, 107, 125, 165, 179, 238, 248}, {47, 55, 74, 82, 163, 187, 198, 222}, {78, 87, 111, 118, 202, 211, 235, 242}, {43, 49, 66, 88, 164, 190, 205, 215}, {70, 93, 99, 120, 193, 218, 228, 255}, {16, 59, 85, 126, 134, 173, 195, 232}, {17, 37, 80, 100, 142, 186, 207, 251}, {18, 61, 95, 112, 133, 170, 200, 231}, {19, 33, 90, 104, 141, 191, 196, 246}, {29, 61, 93, 125, 141, 173, 205, 237}, {28, 47, 88, 107, 133, 182, 193, 242}, {31, 59, 87, 115, 142, 170, 198, 226}, {30, 43, 82, 103, 134, 179, 202, 255}, {37, 49, 78, 90, 163, 183, 200, 220}, {74, 95, 96, 117, 196, 209, 238, 251}, {33, 55, 70, 80, 164, 178, 195, 213}, {66, 85, 108, 123, 207, 216, 225, 246}, {46, 54, 104, 112, 162, 186, 228, 252}, {42, 48, 100, 126, 165, 191, 235, 241}, {17, 58, 72, 99, 135, 172, 222, 245}, {16, 36, 76, 120, 143, 187, 211, 231}, {19, 60, 64, 111, 132, 171, 215, 248}, {18, 32, 68, 118, 140, 190, 218, 232}, {30, 46, 78, 126, 142, 190, 222, 238}, {31, 61, 74, 104, 134, 164, 211, 241}, {28, 42, 70, 112, 141, 187, 215, 225}, {29, 59, 66, 100, 133, 163, 218, 252}, {36, 49, 99, 118, 170, 191, 237, 248}, {32, 55, 111, 120, 173, 186, 226, 245}, {72, 80, 107, 115, 196, 220, 231, 255}, {47, 54, 76, 85, 171, 178, 200, 209}, {64, 90, 103, 125, 207, 213, 232, 242}, {43, 48, 68, 95, 172, 183, 195, 216}, {18, 37, 87, 96, 132, 179, 193, 246}, {19, 58, 82, 123, 140, 165, 205, 228}, {16, 33, 93, 108, 135, 182, 202, 251}, {17, 60, 88, 117, 143, 162, 198, 235}, {31, 47, 95, 111, 143, 191, 207, 255}, {30, 60, 90, 120, 135, 165, 195, 225}, {29, 43, 85, 99, 140, 186, 196, 242}, {28, 58, 80, 118, 132, 162, 200, 238}, {76, 88, 100, 112, 202, 222, 226, 246}, {37, 48, 72, 93, 171, 190, 198, 211}, {68, 82, 104, 126, 193, 215, 237, 251}, {33, 54, 64, 87, 172, 187, 205, 218}, {46, 55, 108, 117, 170, 179, 232, 241}, {42, 49, 96, 123, 173, 182, 231, 252}, {19, 36, 74, 125, 133, 178, 220, 235}, {18, 59, 78, 103, 141, 164, 209, 248}, {17, 32, 66, 115, 134, 183, 213, 228}, {16, 61, 70, 107, 142, 163, 216, 245}, {0, 1, 2, 3, 4, 5, 6, 7}, {16, 17, 18, 19, 28, 29, 30, 31}, {32, 33, 36, 37, 42, 43, 46, 47}, {48, 49, 54, 55, 58, 59, 60, 61}, {64, 66, 68, 70, 72, 74, 76, 78}, {80, 82, 85, 87, 88, 90, 93, 95}, {96, 99, 100, 103, 104, 107, 108, 111}, {112, 115, 117, 118, 120, 123, 125, 126}, {132, 133, 134, 135, 140, 141, 142, 143}, {162, 163, 164, 165, 170, 171, 172, 173}, {178, 179, 182, 183, 186, 187, 190, 191}, {193, 195, 196, 198, 200, 202, 205, 207}, {209, 211, 213, 215, 216, 218, 220, 222}, {225, 226, 228, 231, 232, 235, 237, 238}, {241, 242, 245, 246, 248, 251, 252, 255}})
            .map(DennistonArcVisualizer::of).toArray(BitSet[]::new);

    @Override
    public Point coordinate(int p) {
        int dx = p / 16;
        int dy = p % 16;
        return new Point(20 + dx * 40, 20 + dy * 40);
    }

    @Override
    public BitSet[] lines() {
        return lines;
    }

    @Override
    public BitSet points() {
        return points;
    }

    private static BitSet of(int... vals) {
        BitSet result = new BitSet();
        for (int val : vals) {
            result.set(val);
        }
        return result;
    }
}
