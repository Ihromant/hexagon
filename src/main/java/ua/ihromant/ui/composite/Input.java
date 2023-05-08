package ua.ihromant.ui.composite;

public interface Input extends Component {
    Input setDisabled(boolean disabled);

    Input setValue(String text);

    String getValue();
}
