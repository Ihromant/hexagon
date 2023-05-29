package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Text;
import ua.ihromant.ui.composite.TextButton;

public class ExportWidget implements Widget {
    @Getter
    private final Component container;
    @Getter
    private final Text text;
    @Getter
    private final TextButton close;

    public ExportWidget(UIFactory ui) {
        this.container = ui.vertical().add(
                text = ui.text().background(Color.WHITE),
                close = ui.txtButton("Close")
        ).box(Box.border(Border.color(Color.BLUE)));
    }
}
