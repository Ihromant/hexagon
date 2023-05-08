package ua.ihromant.ui.composite;

public interface TextButton extends Component {
    String STAR = "★"; // u2605
    String CANCEL = "⃠"; // u20E0
    String GEAR = "⚙"; // u2699

    TextButton setDisabled(boolean disabled);

    TextButton setText(String text);

    TextButton setFontSize(int size);
}
