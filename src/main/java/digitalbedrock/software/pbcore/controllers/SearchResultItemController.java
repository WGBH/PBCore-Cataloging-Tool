package digitalbedrock.software.pbcore.controllers;

import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.lucene.HitDocument;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Set;
import java.util.stream.Collectors;

public class SearchResultItemController {

    @FXML
    private Label lblElementType;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblFilepath;

    public void bind(HitDocument hitDocument) {
        PBCoreElement pbCoreElement = hitDocument.getPbCoreElement();
        String title = getTitle(pbCoreElement);

        lblTitle.setText(title);
        lblFilepath.setText(hitDocument.getFilename());
        lblElementType.setText(pbCoreElement.getScreenName());
    }

    private String getTitle(PBCoreElement pbCoreElement) {

        Set<PBCoreElement> pbcoreTitles = pbCoreElement.getSubElements().stream().filter(pbCoreElement1 -> pbCoreElement1.getName().equals("pbcoreTitle")).collect(Collectors.toSet());
        PBCoreElement seriesTitle = pbcoreTitles.stream().filter(pbCoreElement1 -> {
            PBCoreAttribute pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
            return pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("series");
        }).findFirst().orElse(null);
        if (seriesTitle != null) {
            return seriesTitle.getValue();
        }
        PBCoreElement episodeTitle = pbcoreTitles.stream().filter(pbCoreElement1 -> {
            PBCoreAttribute pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
            return pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("episode");
        }).findFirst().orElse(null);
        if (episodeTitle != null) {
            return episodeTitle.getValue();
        }
        PBCoreElement programTitle = pbcoreTitles.stream().filter(pbCoreElement1 -> {
            PBCoreAttribute pbCoreAttribute1 = pbCoreElement.getAttributes().stream().filter(pbCoreAttribute -> pbCoreAttribute.getName().equalsIgnoreCase("titleType")).findFirst().orElse(null);
            return pbCoreAttribute1 != null && pbCoreAttribute1.getValue().equalsIgnoreCase("program");
        }).findFirst().orElse(null);
        if (programTitle != null) {
            return programTitle.getValue();
        }
        PBCoreElement lastConditionElement = pbcoreTitles.stream().findFirst().orElse(null);
        return lastConditionElement == null ? "" : lastConditionElement.getValue();
    }
}
