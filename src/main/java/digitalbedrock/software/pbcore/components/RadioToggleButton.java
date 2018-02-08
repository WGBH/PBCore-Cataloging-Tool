package digitalbedrock.software.pbcore.components;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

public class RadioToggleButton extends ToggleButton {

    public RadioToggleButton(String text, Node graphic) {
        super(text, graphic);
    }

    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }
}
