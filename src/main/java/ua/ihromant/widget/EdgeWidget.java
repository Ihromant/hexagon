package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.domain.ColorEdge;
import ua.ihromant.domain.Edge;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Input;
import ua.ihromant.ui.composite.TextButton;

public class EdgeWidget implements Widget {
    @Getter
    private final Component container;
    private final Input fromId;
    private final Input toId;
    private final ColorSelectionWidget colorSelection;
    @Getter
    private final TextButton update;
    @Getter
    private final TextButton close;
    @Getter
    private final TextButton delete;

    public EdgeWidget(UIFactory ui) {
        this.container = ui.vertical().add(
                ui.horizontal().add(
                        fromId = ui.input(),
                        ui.text().setText("From")
                ),
                ui.horizontal().add(
                        toId = ui.input(),
                        ui.text().setText("To")
                ),
                (colorSelection = new ColorSelectionWidget(ui)).setSelected(TextColor.black).getContainer(),
                ui.horizontal().add(
                        update = ui.txtButton("Update"),
                        (close = ui.txtButton("Close")).setVisible(false),
                        (delete = ui.txtButton("Delete")).setVisible(false)
                )
        ).box(Box.border(Border.color(Color.GREEN)));
    }

    public void fill(ColorEdge ce) {
        fromId.setValue(Integer.toString(ce.getEdge().from())).setDisabled(true);
        toId.setValue(Integer.toString(ce.getEdge().to())).setDisabled(true);
        if (ce.getColor() != null) {
            colorSelection.setSelected(ce.getColor());
        }
    }

    public ColorEdge collect() {
        try {
            int from = Integer.parseInt(fromId.getValue());
            int to = Integer.parseInt(toId.getValue());
            if (from == to) {
                throw new Exception();
            }
            return new ColorEdge(Edge.from(Math.min(from, to), Math.max(from, to)))
                    .setColor(colorSelection.getSelected());
        } catch (Exception e) {
            return null;
        }
    }
}
