package digitalbedrock.software.pbcore.parsers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import digitalbedrock.software.pbcore.core.models.NewDocumentType;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreAttribute;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreElement;
import digitalbedrock.software.pbcore.core.models.entity.PBCoreStructure;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVPBCoreParser {

    public static final String ASSET_TYPE = "AssetType";
    public static final String ASSET_DATE = "AssetDate";
    public static final String DATE_TYPE_ASSET = "@dateType_asset";
    public static final String IDENTIFIER1 = "Identifier1";
    public static final String SOURCE_ID1 = "@source_id1";
    public static final String IDENTIFIER2 = "Identifier2";
    public static final String SOURCE_ID2 = "@source_id2";
    public static final String TITLE1 = "Title1";
    public static final String TITLE_TYPE1 = "@titleType1";
    public static final String TITLE2 = "Title2";
    public static final String TITLE_TYPE2 = "@titleType2";
    public static final String DESCRIPTION = "Description";
    public static final String DESCRIPTION_TYPE = "@descriptionType";
    public static final String GENRE = "Genre";
    public static final String SOURCE_GENRE = "@source_genre";
    public static final String COVERAGE = "Coverage";
    public static final String COVERAGE_TYPE = "CoverageType";
    public static final String ANNOTATION = "Annotation";
    public static final String ANNOTATION_TYPE = "@annotationType";
    public static final String CREATOR = "Creator";
    public static final String CREATOR_ROLE = "CreatorRole";
    public static final String CONTRIBUTOR = "Contributor";
    public static final String CONTRIBUTOR_ROLE = "ContributorRole";
    public static final String RIGHTS_SUMMARY = "RightsSummary";
    public static final String INSTANTIATION_IDENTIFIER = "InstantiationIdentifier";
    public static final String SOURCE_INSTANTIATION_ID = "@source_instantiationID";
    public static final String INSTANTIATION_DATE = "InstantiationDate";
    public static final String DATE_TYPE_INSTANTIATION = "@dateType_instantiation";
    public static final String INSTANTIATION_PHYSICAL = "InstantiationPhysical";
    public static final String INSTANTIATION_DIGITAL = "InstantiationDigital";
    public static final String STANDARD = "Standard";
    public static final String LOCATION = "Location";
    public static final String MEDIA_TYPE = "MediaType";
    public static final String GENERATIONS = "Generations";
    public static final String DURATION = "Duration";
    public static final String COLORS = "Colors";
    public static final String LANGUAGE = "Language";
    public static final String[] HEADER_RECORDS = {ASSET_TYPE,
        ASSET_DATE,
        DATE_TYPE_ASSET,
        IDENTIFIER1,
        SOURCE_ID1,
        IDENTIFIER2,
        SOURCE_ID2,
        TITLE1,
        TITLE_TYPE1,
        TITLE2,
        TITLE_TYPE2,
        DESCRIPTION,
        DESCRIPTION_TYPE,
        GENRE,
        SOURCE_GENRE,
        COVERAGE,
        COVERAGE_TYPE,
        ANNOTATION,
        ANNOTATION_TYPE,
        CREATOR,
        CREATOR_ROLE,
        CONTRIBUTOR,
        CONTRIBUTOR_ROLE,
        RIGHTS_SUMMARY,
        INSTANTIATION_IDENTIFIER,
        SOURCE_INSTANTIATION_ID,
        INSTANTIATION_DATE,
        DATE_TYPE_INSTANTIATION,
        INSTANTIATION_PHYSICAL,
        INSTANTIATION_DIGITAL,
        STANDARD,
        LOCATION,
        MEDIA_TYPE,
        GENERATIONS,
        DURATION,
        COLORS,
        LANGUAGE};
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE = "pbcoreDescriptionDocument/pbcoreAssetType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE = "pbcoreDescriptionDocument/pbcoreAssetDate";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreIdentifier";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE = "pbcoreDescriptionDocument/pbcoreTitle";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION = "pbcoreDescriptionDocument/pbcoreDescription";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENR = "pbcoreDescriptionDocument/pbcoreGenre";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE = "pbcoreDescriptionDocument/pbcoreCoverage/coverage";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE_TYPE = "pbcoreDescriptionDocument/pbcoreCoverage/coverageType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAnnotation";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR = "pbcoreDescriptionDocument/pbcoreCreator/creator";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR_ROLE = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR = "pbcoreDescriptionDocument/pbcoreContributor/contributor";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR_ROLE = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY = "pbcoreDescriptionDocument/pbcoreRightsSummary";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_PHYSICA = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DIGITAL = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_STANDARD = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LOCATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_MEDIA_TYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_GENERATIONS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DURATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_COLORS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LANGUAGE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR = "pbcoreDescriptionDocument/pbcoreCreator";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE = "pbcoreDescriptionDocument/pbcoreCoverage";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION = "pbcoreDescriptionDocument/pbcoreInstantiation";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_SOURCE = "pbcoreDescriptionDocument/pbcoreAssetDate/source";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE = "pbcoreDescriptionDocument/pbcoreIdentifier/source";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE = "pbcoreDescriptionDocument/pbcoreTitle/titleType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE = "pbcoreDescriptionDocument/pbcoreDescription/descriptionType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_SOURCE = "pbcoreDescriptionDocument/pbcoreGenre/source";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION_ANNOTATION_TYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/annotationType";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier/source";
    public static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE_DATE_TYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/dateType";

    public static void writeFile(PBCoreElement pbCoreElement, String csvFile) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(csvFile));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);) {
            String[] headerRecord = HEADER_RECORDS;
            csvWriter.writeNext(headerRecord);
            String[] records = new String[headerRecord.length];
            int c = 0;
            for (String s : headerRecord) {
                PBCoreElement pbCoreE = mapPBCoreElementToString(pbCoreElement, s);
                if (pbCoreE == null) {
                    PBCoreAttribute pbCoreAttribute = mapPBCoreAttributeString(pbCoreElement, s);
                    if (pbCoreAttribute != null) {
                        records[c] = pbCoreAttribute.getValue();
                        System.out.println(pbCoreAttribute);
                    }
                } else {
                    records[c] = pbCoreE.getValue();
                    System.out.println(pbCoreE);
                }
                c++;
            }
            csvWriter.writeNext(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PBCoreElement parseFile(String csvFile) throws IOException {
        PBCoreElement copy = PBCoreStructure.getInstance().getRootElement(NewDocumentType.DESCRIPTION_DOCUMENT).copy(true);
        copy.getSubElements().clear();

        CSVReader csvReader;
        String[] nextRecord;
        List<String> mappedFullPaths;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFile))) {
            csvReader = new CSVReader(reader);
            mappedFullPaths = Arrays.asList(csvReader.readNext());
            if (mappedFullPaths.size() != 37) {
                throw new IOException();
            }
            while ((nextRecord = csvReader.readNext()) != null) {
                int c = 0;
                for (String value : nextRecord) {
                    if (mappedFullPaths.size() <= c) {
                        break;
                    }
                    String s = mappedFullPaths.get(c++);
                    PBCoreElement pbCoreElement = mapStringToPBCoreElement(copy, s, value);
                    if (pbCoreElement == null) {
                        mapStringToPBCoreAttribute(copy, s, value);
                    } else {
                        if (copy.getSubElements().stream().noneMatch(pbc -> pbc.getFullPath().equalsIgnoreCase(pbCoreElement.getFullPath()))
                                || Arrays.asList(IDENTIFIER1,
                                        IDENTIFIER2,
                                        TITLE1,
                                        TITLE2).contains(s)) {
                            copy.addSubElement(pbCoreElement);
                        }
                    }
                }
            }
        }
        return copy;
    }

    private static PBCoreElement mapStringToPBCoreElement(PBCoreElement rootElement, String string, String value) {
        PBCoreElement element = null;
        switch (string) {
            case ASSET_TYPE:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE).copy(false);
                break;
            case ASSET_DATE:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE).copy(false);
                break;
            case IDENTIFIER1:
            case IDENTIFIER2:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER).copy(false);
                break;
            case TITLE1:
            case TITLE2:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE).copy(false);
                break;
            case DESCRIPTION:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION).copy(false);
                break;
            case GENRE:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENR).copy(false);
                break;
            case COVERAGE:
                element = verifyCoverageElement(rootElement);
                PBCoreElement element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case COVERAGE_TYPE:
                element = verifyCoverageElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE_TYPE).copy(false);
                element1.setValue(value == null || value.trim().isEmpty() ? "Spatial" : value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case ANNOTATION:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION).copy(false);
                break;
            case CREATOR:
                element = verifyCreatorElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case CREATOR_ROLE:
                element = verifyCreatorElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR_ROLE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case CONTRIBUTOR:
                element = verifyContributorElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case CONTRIBUTOR_ROLE:
                element = verifyContributorElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR_ROLE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case RIGHTS_SUMMARY:
                element = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY).copy(false);
                break;
            case INSTANTIATION_IDENTIFIER:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case INSTANTIATION_DATE:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case INSTANTIATION_PHYSICAL:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_PHYSICA).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case INSTANTIATION_DIGITAL:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DIGITAL).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case STANDARD:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_STANDARD).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case LOCATION:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LOCATION).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case MEDIA_TYPE:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_MEDIA_TYPE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case GENERATIONS:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_GENERATIONS).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case DURATION:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DURATION).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case COLORS:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_COLORS).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
            case LANGUAGE:
                element = verifyInstantiationElement(rootElement);
                element1 = PBCoreStructure.getInstance().getElement(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LANGUAGE).copy(false);
                element1.setValue(value);
                element1.setValid(element1.getValue() != null && !element1.getValue().trim().isEmpty());
                element.addSubElement(element1);
                return element;
        }
        if (element != null) {
            element.setValue(value);
            element.setValid(element.getValue() != null && !element.getValue().trim().isEmpty());
        }
        return element;
    }

    private static PBCoreElement verifyCoverageElement(PBCoreElement rootElement) {
        return verifyElement(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE);
    }

    private static PBCoreElement verifyCreatorElement(PBCoreElement rootElement) {
        return verifyElement(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR);
    }

    private static PBCoreElement verifyContributorElement(PBCoreElement rootElement) {
        return verifyElement(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR);
    }

    private static PBCoreElement verifyInstantiationElement(PBCoreElement rootElement) {
        return verifyElement(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION);
    }

    private static PBCoreElement verifyElement(PBCoreElement rootElement, String fullPathToVerify) {
        PBCoreElement element = rootElement.getSubElements().stream().filter(pbc -> pbc.getFullPath().equalsIgnoreCase(fullPathToVerify)).findFirst().orElse(null);
        if (element == null) {
            element = PBCoreStructure.getInstance().getElement(fullPathToVerify).copy(false);
            element.getSubElements().clear();
            element.getAttributes().clear();
        }
        return element;
    }

    private static PBCoreElement processElementAttribute(PBCoreElement rootElement, String elem, String attr, String value) {
        return processElementAttribute(rootElement, elem, attr, value, false);
    }

    private static PBCoreElement processElementAttribute(PBCoreElement rootElement, String elem, String attr, String value, boolean ignoreFirstItemOfSameType) {
        PBCoreElement element = findElement(rootElement, elem, ignoreFirstItemOfSameType);
        if (element != null) {
            PBCoreAttribute pbCoreAttribute = element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(attr)).findFirst().orElse(null);
            if (pbCoreAttribute != null) {
                pbCoreAttribute.setValue(value);
            } else {
                PBCoreAttribute pbCoreAttribute1 = PBCoreStructure.getInstance().getElement(elem).getAttributes().stream().filter(at -> at.getFullPath().equals(attr)).findFirst().orElse(null).copy();
                pbCoreAttribute1.setValue(value);
                element.addAttribute(pbCoreAttribute1);
            }
        }
        return element;
    }

    private static PBCoreElement mapStringToPBCoreAttribute(PBCoreElement rootElement, String string, String value) {
        switch (string) {
            case DATE_TYPE_ASSET:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_SOURCE, value);
            case SOURCE_ID1:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE, value);
            case SOURCE_ID2:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE, value, true);
            case TITLE_TYPE1:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE, value);
            case TITLE_TYPE2:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE, value, true);
            case DESCRIPTION_TYPE:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE, value);
            case SOURCE_GENRE:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_SOURCE, value);
            case ANNOTATION_TYPE:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION_ANNOTATION_TYPE, value);
            case SOURCE_INSTANTIATION_ID:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER_SOURCE, value);
            case DATE_TYPE_INSTANTIATION:
                return processElementAttribute(rootElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE_DATE_TYPE, value);
        }
        return null;
    }

    private static PBCoreAttribute mapPBCoreAttributeString(PBCoreElement pbCoreElement, String string) {
        switch (string) {
            case DATE_TYPE_ASSET:
                PBCoreElement element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_SOURCE)).findFirst().orElse(null);
            case SOURCE_ID1:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE)).findFirst().orElse(null);
            case SOURCE_ID2:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER, true);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE)).findFirst().orElse(null);
            case TITLE_TYPE1:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE)).findFirst().orElse(null);
            case TITLE_TYPE2:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE, true);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE)).findFirst().orElse(null);
            case DESCRIPTION_TYPE:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE)).findFirst().orElse(null);
            case SOURCE_GENRE:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENR);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_SOURCE)).findFirst().orElse(null);
            case ANNOTATION_TYPE:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_ANNOTATION_ANNOTATION_TYPE)).findFirst().orElse(null);
            case SOURCE_INSTANTIATION_ID:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER_SOURCE)).findFirst().orElse(null);
            case DATE_TYPE_INSTANTIATION:
                element = findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE);
                return element == null ? null : element.getAttributes().stream().filter(pbca -> pbca.getFullPath().equalsIgnoreCase(PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE_DATE_TYPE)).findFirst().orElse(null);
        }
        return null;
    }

    public static PBCoreElement findElement(PBCoreElement pbCoreElement, String fullpath) {
        return findElement(pbCoreElement, fullpath, false);
    }

    public static PBCoreElement findElement(PBCoreElement pbCoreElement, String fullpath, boolean ignoreFirstItemOfSameType) {
        List<String> split = new ArrayList<>(Arrays.asList(fullpath.split("/")));
        List<PBCoreElement> elementsToProcess = Arrays.asList(pbCoreElement);
        PBCoreElement pbCoreElementToReturn = null;
        while (!split.isEmpty()) {
            String remove = split.remove(0);
            List<PBCoreElement> collect = elementsToProcess.stream()
                    .filter(pbce
                            -> pbce.getName()
                            .equals(remove))
                    .collect(Collectors.toList());
            pbCoreElementToReturn = collect.isEmpty()
                    ? null
                    : (split.isEmpty() && ignoreFirstItemOfSameType) && collect.size() < 2 ? null : (split.isEmpty() && ignoreFirstItemOfSameType) ? collect.get(1) : collect.get(0);
            if (pbCoreElementToReturn == null) {
                return null;
            }
            elementsToProcess = pbCoreElementToReturn.getOrderedSubElements();
        }
        return pbCoreElementToReturn;
    }

    private static PBCoreElement mapPBCoreElementToString(PBCoreElement pbCoreElement, String string) {
        switch (string) {
            case ASSET_TYPE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE);
            case ASSET_DATE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE);
            case IDENTIFIER1:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER);
            case IDENTIFIER2:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER, true);
            case TITLE1:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE);
            case TITLE2:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE, true);
            case DESCRIPTION:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION);
            case GENRE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENR);
            case COVERAGE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE);
            case COVERAGE_TYPE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_COVERAGE_TYPE);
            case ANNOTATION:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION);
            case CREATOR:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR);
            case CREATOR_ROLE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_CREATOR_ROLE);
            case CONTRIBUTOR:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR);
            case CONTRIBUTOR_ROLE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_CONTRIBUTOR_ROLE);
            case RIGHTS_SUMMARY:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY);
            case INSTANTIATION_IDENTIFIER:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_IDENTIFIER);
            case INSTANTIATION_DATE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DATE);
            case INSTANTIATION_PHYSICAL:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_PHYSICA);
            case INSTANTIATION_DIGITAL:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DIGITAL);
            case STANDARD:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_STANDARD);
            case LOCATION:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LOCATION);
            case MEDIA_TYPE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_MEDIA_TYPE);
            case GENERATIONS:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_GENERATIONS);
            case DURATION:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_DURATION);
            case COLORS:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_COLORS);
            case LANGUAGE:
                return findElement(pbCoreElement, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_INSTANTIATION_LANGUAGE);
        }
        return null;
    }
}
