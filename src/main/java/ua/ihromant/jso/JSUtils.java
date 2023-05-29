package ua.ihromant.jso;

import org.teavm.interop.NoSideEffects;
import org.teavm.jso.JSBody;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import ua.ihromant.ui.UIFactory;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.impl.HTMLGraphicsContext;

public class JSUtils {
    private final GraphicsContext context;
    public JSUtils(UIFactory ui) {
        this.context = ui.canvas().getContext();
    }

    @JSBody(params = { "context", "text" }, script = "return context.measureText(text);")
    @NoSideEffects
    static native TextMetrics metrics(CanvasRenderingContext2D context, String text);

    public TextMetrics measureText(String text) {
        return metrics(((HTMLGraphicsContext) context).getContext(), text);
    }
}
