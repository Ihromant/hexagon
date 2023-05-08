package ua.ihromant.ui.composite.impl;

import org.teavm.jso.typedarrays.Uint8ClampedArray;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.composite.ImgData;

public record HTMLImgData(Uint8ClampedArray arr, int width) implements ImgData {
    @Override
    public Color pixel(int x, int y) {
        int idx = y * width + x;
        return new Color() {
            @Override
            public int r() {
                return arr.get(idx * 4);
            }

            @Override
            public int g() {
                return arr.get(idx * 4 + 1);
            }

            @Override
            public int b() {
                return arr.get(idx * 4 + 2);
            }

            @Override
            public double a() {
                return 1.0 * arr.get(idx * 4 + 3) / 255;
            }

            @Override
            public boolean trans() {
                return arr.get(idx * 4 + 3) == 0;
            }
        };
    }

    @Override
    public void pixel(Color color, int x, int y) {
        int idx = y * width + x;
        arr.set(idx * 4, color.r());
        arr.set(idx * 4 + 1, color.g());
        arr.set(idx * 4 + 2, color.b());
        arr.set(idx * 4 + 3, HTMLUtil.round(color.a() * 255));
    }

    @Override
    public int height() {
        return arr.getByteLength() / 4 / width;
    }
}
