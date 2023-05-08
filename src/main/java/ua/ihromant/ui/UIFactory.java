package ua.ihromant.ui;

import ua.ihromant.ui.composite.Board;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.Checkbox;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Container;
import ua.ihromant.ui.composite.ImgData;
import ua.ihromant.ui.composite.Input;
import ua.ihromant.ui.composite.Select;
import ua.ihromant.ui.composite.Text;
import ua.ihromant.ui.composite.TextButton;

public interface UIFactory {
    Container root();

    Board board();

    default Container horizontal() {
        return horizontal(LineConf.EMPTY);
    }

    Container horizontal(LineConf conf);

    default Container vertical() {
        return vertical(LineConf.EMPTY);
    }

    Container vertical(LineConf conf);

    Component placeholder(Integer width, Integer height);

    Text text();

    Canvas canvas();

    TextButton txtButton(String text);

    Select select();

    Checkbox checkbox();

    Input input();

    Container modal();

    ImgData imgData(int width, int height);
}
