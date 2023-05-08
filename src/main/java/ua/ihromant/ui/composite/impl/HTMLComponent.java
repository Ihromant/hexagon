package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.TextRectangle;
import ua.ihromant.ui.Box;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.MouseEvt;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Look;
import ua.ihromant.ui.composite.Rectangle;

import java.util.Optional;
import java.util.function.Consumer;

public class HTMLComponent<E extends HTMLElement> implements Component {
    private final E elem;

    public HTMLComponent(E elem) {
        this.elem = elem;
    }

    public E getElem() {
        return elem;
    }

    @Override
    public void detach() {
        elem.delete();
    }

    @Override
    public Component setVisible(boolean visible) {
        if (visible) {
            elem.getStyle().removeProperty("display");
        } else {
            elem.getStyle().setProperty("display", "none");
        }
        return this;
    }

    @Override
    public Component size(int width, int height) {
        elem.getStyle().setProperty("width", width + "px");
        elem.getStyle().setProperty("height", height + "px");
        return this;
    }

    @Override
    public Component position(int dx, int dy) {
        elem.getStyle().setProperty("position", "absolute");
        elem.getStyle().setProperty("left", dx + "px");
        elem.getStyle().setProperty("top", dy + "px");
        return this;
    }

    @Override
    public Component box(Box box) {
        if (box.margin() != 0) {
            elem.getStyle().setProperty("margin", box.margin() + "px");
        }
        Optional.ofNullable(box.border())
                .ifPresent(b -> {
                    elem.getStyle().setProperty("border", b.width() + "px " + b.type() + " " + HTMLUtil.convert(b.color()));
                    if (b.radius() != 0) {
                        elem.getStyle().setProperty("border-radius", b.radius() + "px");
                    }
                });
        if (box.padding() != 0) {
            elem.getStyle().setProperty("padding", box.padding() + "px");
        }
        return this;
    }

    @Override
    public void addBefore(Component comp) {
        HTMLElement compElem = ((HTMLComponent<?>) comp).getElem();
        compElem.getParentNode().insertBefore(elem, compElem);
    }

    @Override
    public Look look() {
        return new HTMLLook(elem);
    }

    @Override
    public Rectangle bounds() {
        TextRectangle rect = elem.getBoundingClientRect();
        return new Rectangle() {
            @Override
            public int getX() {
                return rect.getLeft();
            }

            @Override
            public int getY() {
                return rect.getTop();
            }

            @Override
            public int getWidth() {
                return rect.getWidth();
            }

            @Override
            public int getHeight() {
                return rect.getHeight();
            }
        };
    }

    @Override
    public Component addEventListener(String type, Consumer<?> listener) {
        switch (type) {
            case "mousecontact" -> {
                elem.addEventListener(MouseEvent.MOUSEMOVE, (MouseEvent e) -> gen(listener).accept(new HTMLMouseEvent(MouseEvt.Type.MOVE, e)));
                elem.addEventListener(MouseEvent.MOUSEOVER, (MouseEvent e) -> gen(listener).accept(new HTMLMouseEvent(MouseEvt.Type.OVER, e)));
                elem.addEventListener(MouseEvent.MOUSEOUT, (MouseEvent e) -> gen(listener).accept(new HTMLMouseEvent(MouseEvt.Type.OUT, e)));
                return this;
            }
            case "click" -> {
                elem.addEventListener(MouseEvent.CLICK, (MouseEvent e) -> gen(listener).accept(new HTMLMouseEvent(MouseEvt.Type.CLICK, e)));
                return this;
            }
            case "resize" -> {
                elem.addEventListener("resize", e -> listener.accept(null));
                return this;
            }
            default -> throw new IllegalArgumentException(type);
        }
    }

    @SuppressWarnings("unchecked")
    // implementing proper conversion allows safe generification
    protected static <T> Consumer<T> gen(Consumer<?> cons) {
        return (Consumer<T>) cons;
    }

    public static class HTMLMouseEvent implements MouseEvt {
        private final MouseEvt.Type type;
        private final MouseEvent e;

        private HTMLMouseEvent(MouseEvt.Type type, MouseEvent e) {
            this.type = type;
            this.e = e;
        }

        @Override
        public Type type() {
            return type;
        }

        @Override
        public int clientX() {
            return e.getClientX();
        }

        @Override
        public int clientY() {
            return e.getClientY();
        }

        @Override
        public int offsetX() {
            return e.getOffsetX();
        }

        @Override
        public int offsetY() {
            return e.getOffsetY();
        }

        @Override
        public void stop() {
            e.stopPropagation();
        }
    }
}
