package ua.ihromant.widget;

import lombok.Getter;
import ua.ihromant.ColorEdge;
import ua.ihromant.Edge;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.RGBAColor;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Input;
import ua.ihromant.ui.composite.TextButton;

public class EdgeWidget implements Widget {
    @Getter
    private final Component container;
    private final Input fromId;
    private final Input toId;
    private final Input r;
    private final Input g;
    private final Input b;
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
                ui.horizontal().add(
                        r = ui.input(),
                        ui.text().setText("r")
                ),
                ui.horizontal().add(
                        g = ui.input(),
                        ui.text().setText("g")
                ),
                ui.horizontal().add(
                        b = ui.input(),
                        ui.text().setText("b")
                ),
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
            r.setValue(Integer.toString(ce.getColor().r()));
            g.setValue(Integer.toString(ce.getColor().g()));
            b.setValue(Integer.toString(ce.getColor().b()));
        }
    }

    public ColorEdge collect() {
        try {
            return new ColorEdge(Edge.from(Integer.parseInt(fromId.getValue()), Integer.parseInt(toId.getValue())))
                    .setColor(new RGBAColor(Integer.parseInt(r.getValue()), Integer.parseInt(g.getValue()), Integer.parseInt(b.getValue())));
        } catch (Exception e) {
            return null;
        }
    }
}
