package ua.ihromant.ui;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.HTMLSelectElement;
import org.teavm.jso.typedarrays.Uint8ClampedArray;
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
import ua.ihromant.ui.composite.impl.HTMLBoard;
import ua.ihromant.ui.composite.impl.HTMLCanvas;
import ua.ihromant.ui.composite.impl.HTMLCheckbox;
import ua.ihromant.ui.composite.impl.HTMLComponent;
import ua.ihromant.ui.composite.impl.HTMLContainer;
import ua.ihromant.ui.composite.impl.HTMLImgData;
import ua.ihromant.ui.composite.impl.HTMLInput;
import ua.ihromant.ui.composite.impl.HTMLSelect;
import ua.ihromant.ui.composite.impl.HTMLText;
import ua.ihromant.ui.composite.impl.HTMLTextButton;

public class HTMLUIFactory implements UIFactory {
    private static final Container ROOT = new HTMLContainer(HTMLDocument.current().getBody());

    public HTMLUIFactory() {

    }

    @Override
    public Container root() {
        return ROOT;
    }

    @Override
    public Board board() {
        HTMLElement elem = HTMLDocument.current().createElement("div");
        elem.getClassList().add("board");
        return new HTMLBoard(elem);
    }

    private Container lineLayout(boolean horizontal, LineConf conf) {
        HTMLElement elem = HTMLDocument.current().createElement("div");
        elem.getClassList().add(horizontal ? "horizontal" : "vertical");
        if (conf.getAlign() != null) {
            elem.getClassList().add(conf.getAlign().name().replace('_', '-').toLowerCase());
        }
        if (conf.getChildren() == LineConf.Children.MAX_DIST) {
            elem.getClassList().add("max-distance");
        } else if (conf.getChildren() == LineConf.Children.SPLIT_DIST) {
            elem.getClassList().add("split-distance");
        } else if (conf.getGap() != 0) {
            elem.getStyle().setProperty("gap", conf.getGap() + "px");
        }
        if (conf.isStretch()) {
            elem.getClassList().add("stretch");
        }
        return new HTMLContainer(elem);
    }

    @Override
    public Container horizontal(LineConf conf) {
        return lineLayout(true, conf);
    }

    @Override
    public Container vertical(LineConf conf) {
        return lineLayout(false, conf);
    }

    @Override
    public Component placeholder(Integer width, Integer height) {
        HTMLElement elem = HTMLDocument.current().createElement("div");
        if (width != null) {
            elem.getStyle().setProperty("width", width + "px");
        }
        if (height != null) {
            elem.getStyle().setProperty("height", height + "px");
        }
        return new HTMLComponent<>(elem);
    }

    @Override
    public Text text() {
        return new HTMLText(HTMLDocument.current().createElement("span"));
    }

    @Override
    public Canvas canvas() {
        HTMLCanvasElement canvas = HTMLDocument.current().createElement("canvas").cast();
        return new HTMLCanvas(canvas);
    }

    @Override
    public TextButton txtButton(String text) {
        HTMLInputElement input = HTMLDocument.current().createElement("input").cast();
        input.setType("button");
        input.getClassList().add("text-button");
        input.setValue(text);
        return new HTMLTextButton(input);
    }

    @Override
    public Select select() {
        HTMLSelectElement select = HTMLDocument.current().createElement("select").cast();
        return new HTMLSelect(select);
    }

    @Override
    public Checkbox checkbox() {
        HTMLElement label = HTMLDocument.current().createElement("label");
        label.getClassList().add("board");
        label.getStyle().setProperty("border", "3px inset rgba(128,128,128,0.5)");
        HTMLInputElement input = HTMLDocument.current().createElement("input").cast();
        input.setType("checkbox");
        input.getClassList().add("image-checkbox");
        label.appendChild(input);
        return new HTMLCheckbox(label, input);
    }

    @Override
    public Input input() {
        HTMLInputElement input = HTMLDocument.current().createElement("input").cast();
        input.setType("text");
        input.getClassList().add("text-input");
        return new HTMLInput(input);
    }

    @Override
    public Container modal() {
        HTMLElement background = HTMLDocument.current().createElement("div");
        background.getClassList().add("modal-window");
        return new HTMLContainer(background);
    }

    @Override
    public ImgData imgData(int width, int height) {
        Uint8ClampedArray arr = Uint8ClampedArray.create(width * height * 4);
        return new HTMLImgData(arr, width);
    }
}
