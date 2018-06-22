package digitalbedrock.software.pbcore.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CSVMapperHelper {

    private static final String ASSET_TYPE = "AssetType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE = "pbcoreDescriptionDocument/pbcoreAssetType";
    private static final String ASSET_TYPE_SOURCE_ATTR = "@source_AssetType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreAssetType/source";
    private static final String ASSET_TYPE_REF_ATTR = "@ref_AssetType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_REF = "pbcoreDescriptionDocument/pbcoreAssetType/ref";
    private static final String ASSET_TYPE_VERSION_ATTR = "@version_AssetType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreAssetType/version";
    private static final String ASSET_TYPE_ANNOTATION_ATTR = "@annotation_AssetType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAssetType/annotation";


    private static final String ASSET_DATE = "AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE = "pbcoreDescriptionDocument/pbcoreAssetDate";
    private static final String ASSET_DATE_DATE_TYPE_ATTR = "@dateType_AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_DATE_TYPE = "pbcoreDescriptionDocument/pbcoreAssetDate/dateType";
    private static final String ASSET_DATE_SOURCE_ATTR = "@source_AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_SOURCE = "pbcoreDescriptionDocument/pbcoreAssetDate/source";
    private static final String ASSET_DATE_REF_ATTR = "@ref_AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_REF = "pbcoreDescriptionDocument/pbcoreAssetDate/ref";
    private static final String ASSET_DATE_VERSION_ATTR = "@version_AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_VERSION = "pbcoreDescriptionDocument/pbcoreAssetDate/version";
    private static final String ASSET_DATE_ANNOTATION_ATTR = "@annotation_AssetDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAssetDate/annotation";

    private static final String IDENTIFIER = "Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreIdentifier";
    private static final String IDENTIFIER_SOURCE_ATTR = "@source_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE = "pbcoreDescriptionDocument/pbcoreIdentifier/source";
    private static final String IDENTIFIER_REF_ATTR = "@ref_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_REF = "pbcoreDescriptionDocument/pbcoreIdentifier/ref";
    private static final String IDENTIFIER_VERSION_ATTR = "@version_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_VERSION = "pbcoreDescriptionDocument/pbcoreIdentifier/version";
    private static final String IDENTIFIER_ANNOTATION_ATTR = "@annotation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_ANNOTATION = "pbcoreDescriptionDocument/pbcoreIdentifier/annotation";

    private static final String TITLE = "Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE = "pbcoreDescriptionDocument/pbcoreTitle";
    private static final String TITLE_TYPE_TITLE_ATTR = "@titleType_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE = "pbcoreDescriptionDocument/pbcoreTitle/titleType";
    private static final String TITLE_TYPE_SOURCE_ATTR = "@titleTypeSource_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreTitle/titleTypeSource";
    private static final String TITLE_TYPE_REF_ATTR = "@titleTypeRef_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_REF = "pbcoreDescriptionDocument/pbcoreTitle/titleTypeRef";
    private static final String TITLE_TYPE_VERSION_ATTR = "@titleTypeVersion_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreTitle/titleTypeVersion";
    private static final String TITLE_TYPE_ANNOTATION_ATTR = "@titleTypeAnnotation_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreTitle/titleTypeAnnotation";
    private static final String TITLE_SOURCE_ATTR = "@source_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_SOURCE = "pbcoreDescriptionDocument/pbcoreTitle/source";
    private static final String TITLE_REF_ATTR = "@ref_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_REF = "pbcoreDescriptionDocument/pbcoreTitle/ref";
    private static final String TITLE_VERSION_ATTR = "@version_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_VERSION = "pbcoreDescriptionDocument/pbcoreTitle/version";
    private static final String TITLE_ANNOTATION_ATTR = "@annotation_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreTitle/annotation";
    private static final String TITLE_START_TIME_ATTR = "@startTime_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_START_TIME = "pbcoreDescriptionDocument/pbcoreTitle/startTime";
    private static final String TITLE_END_TIME_ATTR = "@endTime_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_END_TIME = "pbcoreDescriptionDocument/pbcoreTitle/endTime";
    private static final String TITLE_TIME_ANNOTATION_ATTR = "@timeAnnotation_Title";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreTitle/timeAnnotation";

    private static final String SUBJECT = "Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT = "pbcoreDescriptionDocument/pbcoreSubject";
    private static final String SUBJECT_TYPE_SUBJECT_ATTR = "@subjectType_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE = "pbcoreDescriptionDocument/pbcoreSubject/subjectType";
    private static final String SUBJECT_TYPE_SOURCE_ATTR = "@subjectTypeSource_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreSubject/subjectTypeSource";
    private static final String SUBJECT_TYPE_REF_ATTR = "@subjectTypeRef_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_REF = "pbcoreDescriptionDocument/pbcoreSubject/subjectTypeRef";
    private static final String SUBJECT_TYPE_VERSION_ATTR = "@subjectTypeVersion_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreSubject/subjectTypeVersion";
    private static final String SUBJECT_TYPE_ANNOTATION_ATTR = "@subjectTypeAnnotation_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreSubject/subjectTypeAnnotation";
    private static final String SUBJECT_SOURCE_ATTR = "@source_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SOURCE = "pbcoreDescriptionDocument/pbcoreSubject/source";
    private static final String SUBJECT_REF_ATTR = "@ref_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_REF = "pbcoreDescriptionDocument/pbcoreSubject/ref";
    private static final String SUBJECT_VERSION_ATTR = "@version_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_VERSION = "pbcoreDescriptionDocument/pbcoreSubject/version";
    private static final String SUBJECT_ANNOTATION_ATTR = "@annotation_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_ANNOTATION = "pbcoreDescriptionDocument/pbcoreSubject/annotation";
    private static final String SUBJECT_START_TIME_ATTR = "@startTime_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_START_TIME = "pbcoreDescriptionDocument/pbcoreSubject/startTime";
    private static final String SUBJECT_END_TIME_ATTR = "@endTime_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_END_TIME = "pbcoreDescriptionDocument/pbcoreSubject/endTime";
    private static final String SUBJECT_TIME_ANNOTATION_ATTR = "@timeAnnotation_Subject";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreSubject/timeAnnotation";


    public static void generateElementMappers() {
        CSVElementMapper assetTypeElementMapper = new CSVElementMapper(ASSET_TYPE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE, Arrays.asList(
                new CSVAttributeMapper(ASSET_TYPE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_SOURCE),
                new CSVAttributeMapper(ASSET_TYPE_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_REF),
                new CSVAttributeMapper(ASSET_TYPE_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_VERSION),
                new CSVAttributeMapper(ASSET_TYPE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_TYPE_ANNOTATION)
        ));

        CSVElementMapper assetDateElementMapper = new CSVElementMapper(ASSET_DATE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE, Arrays.asList(
                new CSVAttributeMapper(ASSET_DATE_DATE_TYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_DATE_TYPE),
                new CSVAttributeMapper(ASSET_DATE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_SOURCE),
                new CSVAttributeMapper(ASSET_DATE_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_REF),
                new CSVAttributeMapper(ASSET_DATE_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_VERSION),
                new CSVAttributeMapper(ASSET_DATE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ASSET_DATE_ANNOTATION)
        ));


        CSVElementMapper identifierElementMapper = new CSVElementMapper(IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER, Arrays.asList(
                new CSVAttributeMapper(IDENTIFIER_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_SOURCE),
                new CSVAttributeMapper(IDENTIFIER_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_REF),
                new CSVAttributeMapper(IDENTIFIER_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_VERSION),
                new CSVAttributeMapper(IDENTIFIER_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_IDENTIFIER_ANNOTATION)
                ));

        CSVElementMapper titleElementMapper = new CSVElementMapper(TITLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE, Arrays.asList(
                new CSVAttributeMapper(TITLE_TYPE_TITLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE),
                new CSVAttributeMapper(TITLE_TYPE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_SOURCE),
                new CSVAttributeMapper(TITLE_TYPE_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_REF),
                new CSVAttributeMapper(TITLE_TYPE_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_VERSION),
                new CSVAttributeMapper(TITLE_TYPE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TITLE_TYPE_ANNOTATION),
                new CSVAttributeMapper(TITLE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_SOURCE),
                new CSVAttributeMapper(TITLE_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_REF),
                new CSVAttributeMapper(TITLE_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_VERSION),
                new CSVAttributeMapper(TITLE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_ANNOTATION),
                new CSVAttributeMapper(TITLE_START_TIME_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_START_TIME),
                new CSVAttributeMapper(TITLE_END_TIME_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_END_TIME),
                new CSVAttributeMapper(TITLE_TIME_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_TITLE_TIME_ANNOTATION)
                ));

        CSVElementMapper subjectElementMapper = new CSVElementMapper(SUBJECT, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT, Arrays.asList(
                new CSVAttributeMapper(SUBJECT_TYPE_SUBJECT_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE),
                new CSVAttributeMapper(SUBJECT_TYPE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_SOURCE),
                new CSVAttributeMapper(SUBJECT_TYPE_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_REF),
                new CSVAttributeMapper(SUBJECT_TYPE_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_VERSION),
                new CSVAttributeMapper(SUBJECT_TYPE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SUBJECT_TYPE_ANNOTATION),
                new CSVAttributeMapper(SUBJECT_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_SOURCE),
                new CSVAttributeMapper(SUBJECT_REF_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_REF),
                new CSVAttributeMapper(SUBJECT_VERSION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_VERSION),
                new CSVAttributeMapper(SUBJECT_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_ANNOTATION),
                new CSVAttributeMapper(SUBJECT_START_TIME_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_START_TIME),
                new CSVAttributeMapper(SUBJECT_END_TIME_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_END_TIME),
                new CSVAttributeMapper(SUBJECT_TIME_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SUBJECT_TIME_ANNOTATION)
        ));

        String file = File.separator + "v01" + File.separator + "mappers.json";
        ObjectMapper mapper = new ObjectMapper();
        List<CSVElementMapper> csvElementMappers = Arrays.asList(assetTypeElementMapper,
                assetDateElementMapper,
                identifierElementMapper,
                titleElementMapper,
                subjectElementMapper);
        try {
            mapper.writeValue(new File(file), csvElementMappers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        generateElementMappers();
    }
}
