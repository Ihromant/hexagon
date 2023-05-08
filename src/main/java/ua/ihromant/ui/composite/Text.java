package ua.ihromant.ui.composite;

import ua.ihromant.ui.Color;

public interface Text extends Component {
    Text setText(String text);

    Text setText(Integer text);

    Text clear();

    Text setFontSize(int size);

    Text setColor(Color color);

    Text centered();

    Text background(Color background);

    Text scroll(int height);
}
