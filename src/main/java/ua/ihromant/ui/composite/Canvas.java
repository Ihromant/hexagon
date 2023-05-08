package ua.ihromant.ui.composite;

public interface Canvas extends Component {
    GraphicsContext getContext();

    Canvas pixelSize(int width, int height);

    int pixelWidth();

    int pixelHeight();
}
