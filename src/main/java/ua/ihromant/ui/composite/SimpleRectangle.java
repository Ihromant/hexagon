package ua.ihromant.ui.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleRectangle implements Rectangle {
    public SimpleRectangle() {
        this(Integer.MIN_VALUE / 2, Integer.MIN_VALUE / 2, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private int x;
    private int y;
    private int width;
    private int height;
}
