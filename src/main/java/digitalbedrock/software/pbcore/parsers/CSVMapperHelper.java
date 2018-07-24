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


    private static final String DESCRIPTION = "Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION = "pbcoreDescriptionDocument/pbcoreDescription";
    private static final String DESCRIPTION_TYPE_DESCRIPTION_ATTR = "@descriptionType_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE = "pbcoreDescriptionDocument/pbcoreDescription/descriptionType";
    private static final String DESCRIPTION_TYPE_SOURCE_DESCRIPTION_ATTR = "@descriptionTypeSource_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreDescription/descriptionTypeSource";
    private static final String DESCRIPTION_TYPE_REF_DESCRIPTION_ATTR = "@descriptionTypeRef_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_REF = "pbcoreDescriptionDocument/pbcoreDescription/descriptionTypeRef";
    private static final String DESCRIPTION_TYPE_VERSION_DESCRIPTION_ATTR = "@descriptionTypeVersion_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreDescription/descriptionTypeVersion";
    private static final String DESCRIPTION_TYPE_ANNOTATION_DESCRIPTION_ATTR = "@descriptionTypeAnnotation_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreDescription/descriptionTypeAnnotation";
    private static final String SEGMENT_TYPE_DESCRIPTION_ATTR = "@segmentType_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE = "pbcoreDescriptionDocument/pbcoreDescription/segmentType";
    private static final String SEGMENT_TYPE_SOURCE_DESCRIPTION_ATTR = "@segmentTypeSource_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreDescription/segmentTypeSource";
    private static final String SEGMENT_TYPE_REF_DESCRIPTION_ATTR = "@segmentTypeRef_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_REF = "pbcoreDescriptionDocument/pbcoreDescription/segmentTypeRef";
    private static final String SEGMENT_TYPE_VERSION_DESCRIPTION_ATTR = "@segmentTypeVersion_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreDescription/segmentTypeVersion";
    private static final String SEGMENT_TYPE_ANNOTATION_DESCRIPTION_ATTR = "@segmentTypeAnnotation_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreDescription/segmentTypeAnnotation";
    private static final String START_TIME_DESCRIPTION_ATTR = "@startTime_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_START_TIME = "pbcoreDescriptionDocument/pbcoreDescription/startTime";
    private static final String END_TIME_DESCRIPTION_ATTR = "@endTime_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_END_TIME = "pbcoreDescriptionDocument/pbcoreDescription/endTime";
    private static final String TIME_ANNOTATION_DESCRIPTION_ATTR = "@timeAnnotation_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreDescription/timeAnnotation";
    private static final String SOURCE_DESCRIPTION_ATTR = "@source_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SOURCE = "pbcoreDescriptionDocument/pbcoreDescription/source";
    private static final String REF_DESCRIPTION_ATTR = "@ref_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_REF = "pbcoreDescriptionDocument/pbcoreDescription/ref";
    private static final String VERSION_DESCRIPTION_ATTR = "@version_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_VERSION = "pbcoreDescriptionDocument/pbcoreDescription/version";
    private static final String ANNOTATION_DESCRIPTION_ATTR = "@annotation_Description";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreDescription/annotation";
//
//    private static final String SOURCE = "Source";
//    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE = "pbcoreDescriptionDocument/pbcoreSource";
//    private static final String SOURCE_SOURCE_ATTR = "@source_Source";
//    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_SOURCE = "pbcoreDescriptionDocument/pbcoreSource/source";
//    private static final String REF_SOURCE_ATTR = "@ref_Source";
//    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_REF = "pbcoreDescriptionDocument/pbcoreSource/ref";
//    private static final String VERSION_SOURCE_ATTR = "@version_Source";
//    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_VERSION = "pbcoreDescriptionDocument/pbcoreSource/version";
//    private static final String ANNOTATION_SOURCE_ATTR = "@annotation_Source";
//    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreSource/annotation";


    private static final String GENRE = "Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE = "pbcoreDescriptionDocument/pbcoreGenre";
    private static final String SOURCE_GENRE_ATTR = "@source_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_SOURCE = "pbcoreDescriptionDocument/pbcoreGenre/source";
    private static final String REF_GENRE_ATTR = "@ref_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_REF = "pbcoreDescriptionDocument/pbcoreGenre/ref";
    private static final String VERSION_GENRE_ATTR = "@version_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_VERSION = "pbcoreDescriptionDocument/pbcoreGenre/version";
    private static final String ANNOTATION_GENRE_ATTR = "@annotation_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreGenre/annotation";
    private static final String START_TIME_GENRE_ATTR = "@startTime_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_START_TIME = "pbcoreDescriptionDocument/pbcoreGenre/startTime";
    private static final String END_TIME_GENRE_ATTR = "@endTime_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_END_TIME = "pbcoreDescriptionDocument/pbcoreGenre/endTime";
    private static final String TIME_ANNOTATION_GENRE_ATTR = "@timeAnnotation_Genre";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreGenre/timeAnnotation";


    private static final String RELATION_TYPE = "RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationType";
    private static final String SOURCE_RELATION_TYPE_ATTR = "@source_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RELATION_TYPE = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationType/source";
    private static final String REF_RELATION_TYPE_ATTR = "@ref_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_REF = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationType/ref";
    private static final String VERSION_RELATION_TYPE_ATTR = "@version_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationType/version";
    private static final String ANNOTATION_RELATION_TYPE_ATTR = "@annotation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationType/annotation";


    private static final String RELATION_IDENTIFIER = "RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationIdentifier";
    private static final String SOURCE_RELATION_IDENTIFIER_ATTR = "@source_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RELATION_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationIdentifier/source";
    private static final String REF_RELATION_IDENTIFIER_ATTR = "@ref_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_REF = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationIdentifier/ref";
    private static final String VERSION_RELATION_IDENTIFIER_ATTR = "@version_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_VERSION = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationIdentifier/version";
    private static final String ANNOTATION_RELATION_IDENTIFIER_ATTR = "@annotation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_ANNOTATION = "pbcoreDescriptionDocument/pbcoreRelation/pbcoreRelationIdentifier/annotation";

    private static final String COVERAGE = "Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE = "pbcoreDescriptionDocument/pbcoreCoverage/coverage";
    private static final String SOURCE_COVERAGE_ATTR = "@source_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_COVERAGE = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/source";
    private static final String REF_COVERAGE_ATTR = "@ref_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_REF = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/ref";
    private static final String VERSION_COVERAGE_ATTR = "@version_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_VERSION = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/version";
    private static final String ANNOTATION_COVERAGE_ATTR = "@annotation_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/annotation";
    private static final String START_TIME_COVERAGE_ATTR = "@startTime_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_START_TIME = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/startTime";
    private static final String END_TIME_COVERAGE_ATTR = "@endTime_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_END_TIME = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/endTime";
    private static final String TIME_ANNOTATION_COVERAGE_ATTR = "@timeAnnotation_Coverage";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCoverage/coverage/timeAnnotation";

    private static final String COVERAGE_TYPE = "CoverageType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_TYPE = "pbcoreDescriptionDocument/pbcoreCoverage/coverageType";

    private static final String AUDIENCE_LEVEL = "AudienceLevel";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL = "pbcoreDescriptionDocument/pbcoreAudienceLevel";
    private static final String SOURCE_AUDIENCE_LEVEL_ATTR = "@source_AudienceLevel";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_AUDIENCE_LEVEL = "pbcoreDescriptionDocument/pbcoreAudienceLevel/source";
    private static final String REF_AUDIENCE_LEVEL_ATTR = "@ref_AudienceLevel";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_REF = "pbcoreDescriptionDocument/pbcoreAudienceLevel/ref";
    private static final String VERSION_AUDIENCE_LEVEL_ATTR = "@version_AudienceLevel";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_VERSION = "pbcoreDescriptionDocument/pbcoreAudienceLevel/version";
    private static final String ANNOTATION_AUDIENCE_LEVEL_ATTR = "@annotation_AudienceLevel";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAudienceLevel/annotation";

    private static final String AUDIENCE_RATING = "AudienceRating";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING = "pbcoreDescriptionDocument/pbcoreAudienceRating";
    private static final String SOURCE_AUDIENCE_RATING_ATTR = "@source_AudienceRating";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_AUDIENCE_RATING = "pbcoreDescriptionDocument/pbcoreAudienceRating/source";
    private static final String REF_AUDIENCE_RATING_ATTR = "@ref_AudienceRating";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_REF = "pbcoreDescriptionDocument/pbcoreAudienceRating/ref";
    private static final String VERSION_AUDIENCE_RATING_ATTR = "@version_AudienceRating";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_VERSION = "pbcoreDescriptionDocument/pbcoreAudienceRating/version";
    private static final String ANNOTATION_AUDIENCE_RATING_ATTR = "@annotation_AudienceRating";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAudienceRating/annotation";

    private static final String CREATOR = "Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR = "pbcoreDescriptionDocument/pbcoreCreator/creator";
    private static final String AFFILIATION_CREATOR_ATTR = "@affiliation_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION = "pbcoreDescriptionDocument/pbcoreCreator/creator/affiliation";
    private static final String AFFILIATION_SOURCE_CREATOR_ATTR = "@affiliationSource_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_SOURCE = "pbcoreDescriptionDocument/pbcoreCreator/creator/affiliationSource";
    private static final String AFFILIATION_REF_CREATOR_ATTR = "@affiliationRef_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_REF = "pbcoreDescriptionDocument/pbcoreCreator/creator/affiliationRef";
    private static final String AFFILIATION_VERSION_CREATOR_ATTR = "@affiliationVersion_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_VERSION = "pbcoreDescriptionDocument/pbcoreCreator/creator/affiliationVersion";
    private static final String AFFILIATION_ANNOTATION_CREATOR_ATTR = "@affiliationAnnotation_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCreator/creator/affiliationAnnotation";
    private static final String SOURCE_CREATOR_ATTR = "@source_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CREATOR = "pbcoreDescriptionDocument/pbcoreCreator/creator/source";
    private static final String REF_CREATOR_ATTR = "@ref_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_REF = "pbcoreDescriptionDocument/pbcoreCreator/creator/ref";
    private static final String VERSION_CREATOR_ATTR = "@version_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_VERSION = "pbcoreDescriptionDocument/pbcoreCreator/creator/version";
    private static final String ANNOTATION_CREATOR_ATTR = "@annotation_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCreator/creator/annotation";
    private static final String START_TIME_CREATOR_ATTR = "@startTime_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_START_TIME = "pbcoreDescriptionDocument/pbcoreCreator/creator/startTime";
    private static final String END_TIME_CREATOR_ATTR = "@endTime_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_END_TIME = "pbcoreDescriptionDocument/pbcoreCreator/creator/endTime";
    private static final String TIME_ANNOTATION_CREATOR_ATTR = "@timeAnnotation_Creator";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCreator/creator/timeAnnotation";

    private static final String CREATOR_ROLE = "CreatorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole";
    private static final String SOURCE_CREATOR_ROLE_ATTR = "@source_CreatorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CREATOR_ROLE = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole/source";
    private static final String REF_CREATOR_ROLE_ATTR = "@ref_CreatorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_REF = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole/ref";
    private static final String VERSION_CREATOR_ROLE_ATTR = "@version_CreatorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_VERSION = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole/version";
    private static final String ANNOTATION_CREATOR_ROLE_ATTR = "@annotation_CreatorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreCreator/creatorRole/annotation";


    private static final String CONTRIBUTOR = "Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR = "pbcoreDescriptionDocument/pbcoreContributor/contributor";
    private static final String AFFILIATION_CONTRIBUTOR_ATTR = "@affiliation_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/affiliation";
    private static final String AFFILIATION_SOURCE_CONTRIBUTOR_ATTR = "@affiliationSource_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_SOURCE = "pbcoreDescriptionDocument/pbcoreContributor/contributor/affiliationSource";
    private static final String AFFILIATION_REF_CONTRIBUTOR_ATTR = "@affiliationRef_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_REF = "pbcoreDescriptionDocument/pbcoreContributor/contributor/affiliationRef";
    private static final String AFFILIATION_VERSION_CONTRIBUTOR_ATTR = "@affiliationVersion_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_VERSION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/affiliationVersion";
    private static final String AFFILIATION_ANNOTATION_CONTRIBUTOR_ATTR = "@affiliationAnnotation_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/affiliationAnnotation";
    private static final String SOURCE_CONTRIBUTOR_ATTR = "@source_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CONTRIBUTOR = "pbcoreDescriptionDocument/pbcoreContributor/contributor/source";
    private static final String REF_CONTRIBUTOR_ATTR = "@ref_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_REF = "pbcoreDescriptionDocument/pbcoreContributor/contributor/ref";
    private static final String VERSION_CONTRIBUTOR_ATTR = "@version_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_VERSION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/version";
    private static final String ANNOTATION_CONTRIBUTOR_ATTR = "@annotation_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ANNOTATION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/annotation";
    private static final String START_TIME_CONTRIBUTOR_ATTR = "@startTime_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_START_TIME = "pbcoreDescriptionDocument/pbcoreContributor/contributor/startTime";
    private static final String END_TIME_CONTRIBUTOR_ATTR = "@endTime_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_END_TIME = "pbcoreDescriptionDocument/pbcoreContributor/contributor/endTime";
    private static final String TIME_ANNOTATION_CONTRIBUTOR_ATTR = "@timeAnnotation_Contributor";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcoreContributor/contributor/timeAnnotation";

    private static final String CONTRIBUTOR_ROLE = "ContributorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole";
    private static final String SOURCE_CONTRIBUTOR_ROLE_ATTR = "@source_ContributorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CONTRIBUTOR_ROLE = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole/source";
    private static final String REF_CONTRIBUTOR_ROLE_ATTR = "@ref_ContributorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_REF = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole/ref";
    private static final String VERSION_CONTRIBUTOR_ROLE_ATTR = "@version_ContributorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_VERSION = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole/version";
    private static final String ANNOTATION_CONTRIBUTOR_ROLE_ATTR = "@annotation_ContributorRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreContributor/contributorRole/annotation";


    private static final String PUBLISHER = "Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER = "pbcoreDescriptionDocument/pbcorePublisher/publisher";
    private static final String AFFILIATION_PUBLISHER_ATTR = "@affiliation_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/affiliation";
    private static final String AFFILIATION_SOURCE_PUBLISHER_ATTR = "@affiliationSource_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_SOURCE = "pbcoreDescriptionDocument/pbcorePublisher/publisher/affiliationSource";
    private static final String AFFILIATION_REF_PUBLISHER_ATTR = "@affiliationRef_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_REF = "pbcoreDescriptionDocument/pbcorePublisher/publisher/affiliationRef";
    private static final String AFFILIATION_VERSION_PUBLISHER_ATTR = "@affiliationVersion_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_VERSION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/affiliationVersion";
    private static final String AFFILIATION_ANNOTATION_PUBLISHER_ATTR = "@affiliationAnnotation_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_ANNOTATION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/affiliationAnnotation";
    private static final String SOURCE_PUBLISHER_ATTR = "@source_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_PUBLISHER = "pbcoreDescriptionDocument/pbcorePublisher/publisher/source";
    private static final String REF_PUBLISHER_ATTR = "@ref_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_REF = "pbcoreDescriptionDocument/pbcorePublisher/publisher/ref";
    private static final String VERSION_PUBLISHER_ATTR = "@version_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_VERSION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/version";
    private static final String ANNOTATION_PUBLISHER_ATTR = "@annotation_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ANNOTATION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/annotation";
    private static final String START_TIME_PUBLISHER_ATTR = "@startTime_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_START_TIME = "pbcoreDescriptionDocument/pbcorePublisher/publisher/startTime";
    private static final String END_TIME_PUBLISHER_ATTR = "@endTime_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_END_TIME = "pbcoreDescriptionDocument/pbcorePublisher/publisher/endTime";
    private static final String TIME_ANNOTATION_PUBLISHER_ATTR = "@timeAnnotation_Publisher";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_TIME_ANNOTATION = "pbcoreDescriptionDocument/pbcorePublisher/publisher/timeAnnotation";


    private static final String PUBLISHER_ROLE = "PublisherRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE = "pbcoreDescriptionDocument/pbcorePublisher/publisherRole";
    private static final String SOURCE_PUBLISHER_ROLE_ATTR = "@source_PublisherRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_PUBLISHER_ROLE = "pbcoreDescriptionDocument/pbcorePublisher/publisherRole/source";
    private static final String REF_PUBLISHER_ROLE_ATTR = "@ref_PublisherRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_REF = "pbcoreDescriptionDocument/pbcorePublisher/publisherRole/ref";
    private static final String VERSION_PUBLISHER_ROLE_ATTR = "@version_PublisherRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_VERSION = "pbcoreDescriptionDocument/pbcorePublisher/publisherRole/version";
    private static final String ANNOTATION_PUBLISHER_ROLE_ATTR = "@annotation_PublisherRole";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_ANNOTATION = "pbcoreDescriptionDocument/pbcorePublisher/publisherRole/annotation";


    private static final String RIGHTS_SUMMARY = "RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsSummary";
    private static final String SOURCE_RIGHTS_SUMMARY_ATTR = "@source_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RIGHTS_SUMMARY = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsSummary/source";
    private static final String REF_RIGHTS_SUMMARY_ATTR = "@ref_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_REF = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsSummary/ref";
    private static final String VERSION_RIGHTS_SUMMARY_ATTR = "@version_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_VERSION = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsSummary/version";
    private static final String ANNOTATION_RIGHTS_SUMMARY_ATTR = "@annotation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_ANNOTATION = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsSummary/annotation";


    private static final String RIGHTS_LINK = "RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsLink";
    private static final String SOURCE_RIGHTS_LINK_ATTR = "@source_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RIGHTS_LINK = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsLink/source";
    private static final String REF_RIGHTS_LINK_ATTR = "@ref_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_REF = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsLink/ref";
    private static final String VERSION_RIGHTS_LINK_ATTR = "@version_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_VERSION = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsLink/version";
    private static final String ANNOTATION_RIGHTS_LINK_ATTR = "@annotation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_ANNOTATION = "pbcoreDescriptionDocument/pbcoreRightsSummary/rightsLink/annotation";


    private static final String ANNOTATION = "Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAnnotation";
    private static final String ANNOTATION_TYPE_ANNOTATION_ATTR = "@annotationType_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_ANNOTATION_TYPE = "pbcoreDescriptionDocument/pbcoreAnnotation/annotationType";
    private static final String SOURCE_ANNOTATION_ATTR = "@source_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_SOURCE = "pbcoreDescriptionDocument/pbcoreAnnotation/source";
    private static final String REF_ANNOTATION_ATTR = "@ref_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_REF = "pbcoreDescriptionDocument/pbcoreAnnotation/ref";
    private static final String VERSION_ANNOTATION_ATTR = "@version_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_VERSION = "pbcoreDescriptionDocument/pbcoreAnnotation/version";
    private static final String ANNOTATION_ANNOTATION_ATTR = "@annotation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreAnnotation/annotation";


    private static final String INSTANTIATION_IDENTIFIER = "Instantiation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier";
    private static final String SOURCE_INSTANTIATION_IDENTIFIER_ATTR = "@source_Instantiation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier/source";
    private static final String REF_INSTANTIATION_IDENTIFIER_ATTR = "@ref_Instantiation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier/ref";
    private static final String VERSION_INSTANTIATION_IDENTIFIER_ATTR = "@version_Instantiation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier/version";
    private static final String INSTANTIATION_IDENTIFIER_INSTANTIATION_IDENTIFIER_ATTR = "@annotation_Instantiation_Identifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationIdentifier/annotation";

    private static final String INSTANTIATION_DATE = "Instantiation_Date";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate";
    private static final String DATE_TYPE_INSTANTIATIONDATE_ATTR = "@dateType_InstantiationDate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_DATE_TYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/dateType";
    private static final String SOURCE_INSTANTIATION_DATE_ATTR = "@source_Instantiation_Date";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/source";
    private static final String REF_INSTANTIATION_DATE_ATTR = "@ref_Instantiation_Date";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/ref";
    private static final String VERSION_INSTANTIATION_DATE_ATTR = "@version_Instantiation_Date";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/version";
    private static final String ANNOTATION_INSTANTIATION_DATE_ATTR = "@annotation_Instantiation_Date";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDate/annotation";

    private static final String INSTANTIATION_DIMENSIONS = "Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions";
    private static final String UNITSOF_MEASURE_INSTANTIATION_DIMENSIONS_ATTR = "@unitsOfMeasure_Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_UNITSOFMEASURE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions/unitsOfMeasure";
    private static final String SOURCE_INSTANTIATION_DIMENSIONS_ATTR = "@source_Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions/source";
    private static final String REF_INSTANTIATION_DIMENSIONS_ATTR = "@ref_Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions/ref";
    private static final String VERSION_INSTANTIATION_DIMENSIONS_ATTR = "@version_Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions/version";
    private static final String ANNOTATION_INSTANTIATION_DIMENSIONS_ATTR = "@annotation_Dimensions";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDimensions/annotation";

    private static final String INSTANTIATION_PHYSICAL = "Instantiation_Physical";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical";
    private static final String SOURCE_INSTANTIATIONPHYSICAL_ATTR = "@source_InstantiationPhysical";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical/source";
    private static final String REF_INSTANTIATION_PHYSICAL_ATTR = "@ref_Instantiation_Physical";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical/ref";
    private static final String VERSION_INSTANTIATION_PHYSICAL_ATTR = "@version_Instantiation_Physical";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical/version";
    private static final String ANNOTATION_INSTANTIATION_PHYSICAL_ATTR = "@annotation_Instantiation_Physical";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationPhysical/annotation";

    private static final String INSTANTIATION_DIGITAL = "Instantiation_Digital";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital";
    private static final String SOURCE_INSTANTIATIONDIGITAL_ATTR = "@source_InstantiationDigital";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital/source";
    private static final String REF_INSTANTIATION_DIGITAL_ATTR = "@ref_Instantiation_Digital";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital/ref";
    private static final String VERSION_INSTANTIATION_DIGITAL_ATTR = "@version_Instantiation_Digital";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital/version";
    private static final String ANNOTATION_INSTANTIATION_DIGITAL_ATTR = "@annotation_Instantiation_Digital";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDigital/annotation";

    private static final String INSTANTIATION_STANDARD = "Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard";
    private static final String PROFILE_STANDARD_ATTR = "@profile_Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_PROFILE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard/profile";
    private static final String SOURCE_STANDARD_ATTR = "@source_Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard/source";
    private static final String REF_STANDARD_ATTR = "@ref_Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard/ref";
    private static final String VERSION_STANDARD_ATTR = "@version_Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard/version";
    private static final String ANNOTATION_STANDARD_ATTR = "@annotation_Standard";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationStandard/annotation";

    private static final String INSTANTIATION_LOCATION = "Location";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation";
    private static final String SOURCE_LOCATION_ATTR = "@source_Location";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation/source";
    private static final String REF_LOCATION_ATTR = "@ref_Location";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation/ref";
    private static final String VERSION_LOCATION_ATTR = "@version_Location";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation/version";
    private static final String ANNOTATION_LOCATION_ATTR = "@annotation_Location";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLocation/annotation";

    private static final String INSTANTIATION_MEDIATYPE = "MediaType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType";
    private static final String SOURCE_MEDIATYPE_ATTR = "@source_MediaType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType/source";
    private static final String REF_MEDIATYPE_ATTR = "@ref_MediaType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType/ref";
    private static final String VERSION_MEDIATYPE_ATTR = "@version_MediaType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType/version";
    private static final String ANNOTATION_MEDIATYPE_ATTR = "@annotation_MediaType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationMediaType/annotation";

    private static final String INSTANTIATION_GENERATIONS = "Generations";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations";
    private static final String SOURCE_GENERATIONS_ATTR = "@source_Generations";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations/source";
    private static final String REF_GENERATIONS_ATTR = "@ref_Generations";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations/ref";
    private static final String VERSION_GENERATIONS_ATTR = "@version_Generations";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations/version";
    private static final String ANNOTATION_GENERATIONS_ATTR = "@annotation_Generations";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationGenerations/annotation";

    private static final String INSTANTIATION_FILE_SIZE = "FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize";
    private static final String UNITS_OFMEASURE_FILESIZE_ATTR = "@unitsOfMeasure_FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_UNITSOFMEASURE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize/unitsOfMeasure";
    private static final String SOURCE_FILESIZE_ATTR = "@source_FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize/source";
    private static final String REF_FILESIZE_ATTR = "@ref_FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize/ref";
    private static final String VERSION_FILESIZE_ATTR = "@version_FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize/version";
    private static final String ANNOTATION_FILESIZE_ATTR = "@annotation_FileSize";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationFileSize/annotation";

    private static final String INSTANTIATION_TIMESTART = "TimeStart";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTimeStart";
    private static final String SOURCE_TIMESTART_ATTR = "@source_TimeStart";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTimeStart/source";
    private static final String REF_TIMESTART_ATTR = "@ref_TimeStart";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTimeStart/ref";
    private static final String VERSION_TIMESTART_ATTR = "@version_TimeStart";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTimeStart/version";
    private static final String ANNOTATION_TIMESTART_ATTR = "@annotation_TimeStart";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTimeStart/annotation";

    private static final String INSTANTIATION_DURATION = "Duration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration";
    private static final String SOURCE_DURATION_ATTR = "@source_Duration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration/source";
    private static final String REF_DURATION_ATTR = "@ref_Duration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration/ref";
    private static final String VERSION_DURATION_ATTR = "@version_Duration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration/version";
    private static final String ANNOTATION_DURATION_ATTR = "@annotation_Duration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDuration/annotation";

    private static final String INSTANTIATION_DATARATE = "DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate";
    private static final String UNITS_OFMEASURE_DATARATE_ATTR = "@unitsOfMeasure_DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_UNITSOFMEASURE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate/unitsOfMeasure";
    private static final String SOURCE_DATARATE_ATTR = "@source_DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate/source";
    private static final String REF_DATARATE_ATTR = "@ref_DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate/ref";
    private static final String VERSION_DATARATE_ATTR = "@version_DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate/version";
    private static final String ANNOTATION_DATARATE_ATTR = "@annotation_DataRate";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationDataRate/annotation";

    private static final String INSTANTIATION_COLORS = "Colors";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors";
    private static final String SOURCE_COLORS_ATTR = "@source_Colors";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors/source";
    private static final String REF_COLORS_ATTR = "@ref_Colors";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors/ref";
    private static final String VERSIONS_COLORS_ATTR = "@versions_Colors";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_VERSIONS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors/version";
    private static final String ANNOTATION_COLORS_ATTR = "@annotation_Colors";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationColors/annotation";

    private static final String INSTANTIATION_TRACKS = "Tracks";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTracks";
    private static final String SOURCE_TRACKS_ATTR = "@source_Tracks";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTracks/source";
    private static final String REF_TRACKS_ATTR = "@ref_Tracks";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTracks/ref";
    private static final String VERSION_TRACKS_ATTR = "@version_Tracks";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTracks/version";
    private static final String ANNOTATION_TRACKS_ATTR = "@annotation_Tracks";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationTracks/annotation";

    private static final String INSTANTIATION_CHANNELCONFIGURATION = "ChannelConfiguration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationChannelConfiguration";
    private static final String SOURCE_CHANNELCONFIGURATION_ATTR = "@source_ChannelConfiguration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationChannelConfiguration/source";
    private static final String REF_CHANNELCONFIGURATION_ATTR = "@ref_ChannelConfiguration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationChannelConfiguration/ref";
    private static final String VERSION_CHANNELCONFIGURATION_ATTR = "@version_ChannelConfiguration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationChannelConfiguration/version";
    private static final String ANNOTATION_CHANNELCONFIGURATION_ATTR = "@annotation_ChannelConfiguration";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationChannelConfiguration/annotation";

    private static final String INSTANTIATION_LANGUAGE = "Language";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage";
    private static final String SOURCE_LANGUAGE_ATTR = "@source_Language";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage/source";
    private static final String REF_LANGUAGE_ATTR = "@ref_Language";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage/ref";
    private static final String VERSION_LANGUAGE_ATTR = "@version_Language";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage/version";
    private static final String ANNOTATION_LANGUAGE_ATTR = "@annotation_Language";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationLanguage/annotation";

    private static final String INSTANTIATION_ALTERNATIVEMODES = "AlternativeModes";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAlternativeModes";
    private static final String SOURCE_ALTERNATIVEMODES_ATTR = "@source_AlternativeModes";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAlternativeModes/source";
    private static final String REF_ALTERNATIVEMODES_ATTR = "@ref_AlternativeModes";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAlternativeModes/ref";
    private static final String VERSION_ALTERNATIVEMODES_ATTR = "@version_AlternativeModes";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAlternativeModes/version";
    private static final String ANNOTATION_ALTERNATIVEMODES_ATTR = "@annotation_AlternativeModes";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAlternativeModes/annotation";

    private static final String INSTANTIATION_RELATION_TYPE = "Instantiation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationType";
    private static final String SOURCE_INSTANTIATION_RELATIONTYPE_ATTR = "@source_Instantiation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationType/source";
    private static final String REF_INSTANTIATION_RELATIONTYPE_ATTR = "@ref_Instantiation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationType/ref";
    private static final String VERSION_INSTANTIATION_RELATIONTYPE_ATTR = "@version_Instantiation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationType/version";
    private static final String ANNOTATION_INSTANTIATION_RELATIONTYPE_ATTR = "@annotation_Instantiation_RelationType";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationType/annotation";

    private static final String INSTANTIATION_RELATIONIDENTIFIER = "Instantiation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationIdentifier";
    private static final String SOURCE_INSTANTIATION_RELATIONIDENTIFIER_ATTR = "@source_Instantiation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationIdentifier/source";
    private static final String REF_INSTANTIATION_RELATIONIDENTIFIER_ATTR = "@ref_Instantiation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationIdentifier/ref";
    private static final String VERSION_INSTANTIATION_RELATIONIDENTIFIER_ATTR = "@version_Instantiation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationIdentifier/version";
    private static final String ANNOTATION_INSTANTIATION_RELATIONIDENTIFIER_ATTR = "@annotation_Instantiation_RelationIdentifier";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRelation/instantiationRelationIdentifier/annotation";

    private static final String INSTANTIATION_RIGHTSSUMMARY = "Instantiation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsSummary";
    private static final String SOURCE_INSTANTIATION_RIGHTSSUMMARY_ATTR = "@source_Instantiation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsSummary/source";
    private static final String REF_INSTANTIATION_RIGHTSSUMMARY_ATTR = "@ref_Instantiation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsSummary/ref";
    private static final String VERSION_INSTANTIATION_RIGHTSSUMMARY_ATTR = "@version_Instantiation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsSummary/version";
    private static final String ANNOTATION_INSTANTIATION_RIGHTSSUMMARY_ATTR = "@annotation_Instantiation_RightsSummary";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsSummary/annotation";

    private static final String INSTANTIATION_RIGHTSLINK = "Instantiation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsLink";
    private static final String SOURCE_INSTANTIATION_RIGHTSLINK_ATTR = "@source_Instantiation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsLink/source";
    private static final String REF_INSTANTIATION_RIGHTSLINK_ATTR = "@ref_Instantiation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsLink/ref";
    private static final String VERSION_INSTANTIATION_RIGHTSLINK_ATTR = "@version_Instantiation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsLink/version";
    private static final String ANNOTATION_INSTANTIATION_RIGHTSLINK_ATTR = "@annotation_Instantiation_RightsLink";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationRights/rightsLink/annotation";

    private static final String INSTANTIATION_ANNOTATION = "Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation";
    private static final String ANNOTATION_TYPE_INSTANTIATION_ANNOTATION_ATTR = "@annotationType_Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATIONTYPE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/annotationType";
    private static final String SOURCE_INSTANTIATION_ANNOTATION_ATTR = "@source_Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_SOURCE = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/source";
    private static final String REF_INSTANTIATION_ANNOTATION_ATTR = "@ref_Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_REF = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/ref";
    private static final String VERSION_INSTANTIATION_ANNOTATION_ATTR = "@version_Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_VERSION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/version";
    private static final String ANNOTATION_INSTANTIATION_ANNOTATION_ATTR = "@annotation_Instantiation_Annotation";
    private static final String PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_ANNOTATION = "pbcoreDescriptionDocument/pbcoreInstantiation/instantiationAnnotation/annotation";


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

        CSVElementMapper descriptionElementMapper = new CSVElementMapper(DESCRIPTION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION, Arrays.asList(
                new CSVAttributeMapper(DESCRIPTION_TYPE_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE),
                new CSVAttributeMapper(DESCRIPTION_TYPE_SOURCE_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_SOURCE),
                new CSVAttributeMapper(DESCRIPTION_TYPE_REF_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_REF),
                new CSVAttributeMapper(DESCRIPTION_TYPE_VERSION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_VERSION),
                new CSVAttributeMapper(DESCRIPTION_TYPE_ANNOTATION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_DESCRIPTION_TYPE_ANNOTATION),
                new CSVAttributeMapper(SEGMENT_TYPE_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE),
                new CSVAttributeMapper(SEGMENT_TYPE_SOURCE_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_SOURCE),
                new CSVAttributeMapper(SEGMENT_TYPE_REF_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_REF),
                new CSVAttributeMapper(SEGMENT_TYPE_VERSION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_VERSION),
                new CSVAttributeMapper(SEGMENT_TYPE_ANNOTATION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SEGMENT_TYPE_ANNOTATION),
                new CSVAttributeMapper(START_TIME_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_START_TIME),
                new CSVAttributeMapper(END_TIME_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_TIME_ANNOTATION),
                new CSVAttributeMapper(SOURCE_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_SOURCE),
                new CSVAttributeMapper(REF_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_REF),
                new CSVAttributeMapper(VERSION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_VERSION),
                new CSVAttributeMapper(ANNOTATION_DESCRIPTION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_DESCRIPTION_ANNOTATION)
        ));

//        CSVElementMapper sourceElementMapper = new CSVElementMapper(SOURCE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE, Arrays.asList(
//                new CSVAttributeMapper(SOURCE_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_SOURCE),
//                new CSVAttributeMapper(REF_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_REF),
//                new CSVAttributeMapper(VERSION_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_VERSION),
//                new CSVAttributeMapper(ANNOTATION_SOURCE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_ANNOTATION)
//        ));

        CSVElementMapper genreElementMapper = new CSVElementMapper(GENRE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_SOURCE),
                new CSVAttributeMapper(REF_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_REF),
                new CSVAttributeMapper(VERSION_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_VERSION),
                new CSVAttributeMapper(ANNOTATION_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_ANNOTATION),
                new CSVAttributeMapper(START_TIME_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_START_TIME),
                new CSVAttributeMapper(END_TIME_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_GENRE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_GENRE_TIME_ANNOTATION)
        ));


        CSVElementMapper relationTypeElementMapper = new CSVElementMapper(RELATION_TYPE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_RELATION_TYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RELATION_TYPE),
                new CSVAttributeMapper(REF_RELATION_TYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_REF),
                new CSVAttributeMapper(VERSION_RELATION_TYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_VERSION),
                new CSVAttributeMapper(ANNOTATION_RELATION_TYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_TYPE_ANNOTATION)
        ),  true);

        CSVElementMapper relationIdentifierElementMapper = new CSVElementMapper(RELATION_IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER, Arrays.asList(
                new CSVAttributeMapper(SOURCE_RELATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RELATION_IDENTIFIER),
                new CSVAttributeMapper(REF_RELATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_REF),
                new CSVAttributeMapper(VERSION_RELATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_VERSION),
                new CSVAttributeMapper(ANNOTATION_RELATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RELATION_IDENTIFIER_ANNOTATION)
        ),  true);

        CSVElementMapper coverageElementMapper = new CSVElementMapper(COVERAGE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_COVERAGE),
                new CSVAttributeMapper(REF_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_REF),
                new CSVAttributeMapper(VERSION_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_VERSION),
                new CSVAttributeMapper(ANNOTATION_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_ANNOTATION),
                new CSVAttributeMapper(START_TIME_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_START_TIME),
                new CSVAttributeMapper(END_TIME_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_COVERAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_TIME_ANNOTATION)
        ),  true);

        CSVElementMapper coverageTypeElementMapper = new CSVElementMapper(COVERAGE_TYPE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_COVERAGE_TYPE, Arrays.asList(),  true);

        CSVElementMapper audienceLevelElementMapper = new CSVElementMapper(AUDIENCE_LEVEL, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL, Arrays.asList(
                new CSVAttributeMapper(SOURCE_AUDIENCE_LEVEL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_AUDIENCE_LEVEL),
                new CSVAttributeMapper(REF_AUDIENCE_LEVEL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_REF),
                new CSVAttributeMapper(VERSION_AUDIENCE_LEVEL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_VERSION),
                new CSVAttributeMapper(ANNOTATION_AUDIENCE_LEVEL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_LEVEL_ANNOTATION)));

        CSVElementMapper audienceRatingElementMapper = new CSVElementMapper(AUDIENCE_RATING, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING, Arrays.asList(
                new CSVAttributeMapper(SOURCE_AUDIENCE_RATING_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_AUDIENCE_RATING),
                new CSVAttributeMapper(REF_AUDIENCE_RATING_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_REF),
                new CSVAttributeMapper(VERSION_AUDIENCE_RATING_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_VERSION),
                new CSVAttributeMapper(ANNOTATION_AUDIENCE_RATING_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_AUDIENCE_RATING_ANNOTATION)));


        CSVElementMapper creatorElementMapper = new CSVElementMapper(CREATOR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR, Arrays.asList(
                new CSVAttributeMapper(AFFILIATION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION),
                new CSVAttributeMapper(AFFILIATION_SOURCE_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_SOURCE),
                new CSVAttributeMapper(AFFILIATION_REF_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_REF),
                new CSVAttributeMapper(AFFILIATION_VERSION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_VERSION),
                new CSVAttributeMapper(AFFILIATION_ANNOTATION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_AFFFILIATION_ANNOTATION),
                new CSVAttributeMapper(SOURCE_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CREATOR),
                new CSVAttributeMapper(REF_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_REF),
                new CSVAttributeMapper(VERSION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_VERSION),
                new CSVAttributeMapper(ANNOTATION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ANNOTATION),
                new CSVAttributeMapper(START_TIME_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_START_TIME),
                new CSVAttributeMapper(END_TIME_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_CREATOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_TIME_ANNOTATION)
        ),  true);


        CSVElementMapper creatorRoleElementMapper = new CSVElementMapper(CREATOR_ROLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_CREATOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CREATOR_ROLE),
                new CSVAttributeMapper(REF_CREATOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_REF),
                new CSVAttributeMapper(VERSION_CREATOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_VERSION),
                new CSVAttributeMapper(ANNOTATION_CREATOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CREATOR_ROLE_ANNOTATION)
        ),  true);

        CSVElementMapper contributorElementMapper = new CSVElementMapper(CONTRIBUTOR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR, Arrays.asList(
                new CSVAttributeMapper(AFFILIATION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION),
                new CSVAttributeMapper(AFFILIATION_SOURCE_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_SOURCE),
                new CSVAttributeMapper(AFFILIATION_REF_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_REF),
                new CSVAttributeMapper(AFFILIATION_VERSION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_VERSION),
                new CSVAttributeMapper(AFFILIATION_ANNOTATION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_AFFFILIATION_ANNOTATION),
                new CSVAttributeMapper(SOURCE_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CONTRIBUTOR),
                new CSVAttributeMapper(REF_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_REF),
                new CSVAttributeMapper(VERSION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_VERSION),
                new CSVAttributeMapper(ANNOTATION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ANNOTATION),
                new CSVAttributeMapper(START_TIME_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_START_TIME),
                new CSVAttributeMapper(END_TIME_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_CONTRIBUTOR_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_TIME_ANNOTATION)
        ),  true);


        CSVElementMapper contributorRoleElementMapper = new CSVElementMapper(CONTRIBUTOR_ROLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_CONTRIBUTOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_CONTRIBUTOR_ROLE),
                new CSVAttributeMapper(REF_CONTRIBUTOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_REF),
                new CSVAttributeMapper(VERSION_CONTRIBUTOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_VERSION),
                new CSVAttributeMapper(ANNOTATION_CONTRIBUTOR_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_CONTRIBUTOR_ROLE_ANNOTATION)
        ),  true);


        CSVElementMapper publisherElementMapper = new CSVElementMapper(PUBLISHER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER, Arrays.asList(
                new CSVAttributeMapper(AFFILIATION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION),
                new CSVAttributeMapper(AFFILIATION_SOURCE_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_SOURCE),
                new CSVAttributeMapper(AFFILIATION_REF_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_REF),
                new CSVAttributeMapper(AFFILIATION_VERSION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_VERSION),
                new CSVAttributeMapper(AFFILIATION_ANNOTATION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_AFFFILIATION_ANNOTATION),
                new CSVAttributeMapper(SOURCE_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_PUBLISHER),
                new CSVAttributeMapper(REF_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_REF),
                new CSVAttributeMapper(VERSION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_VERSION),
                new CSVAttributeMapper(ANNOTATION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ANNOTATION),
                new CSVAttributeMapper(START_TIME_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_START_TIME),
                new CSVAttributeMapper(END_TIME_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_END_TIME),
                new CSVAttributeMapper(TIME_ANNOTATION_PUBLISHER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_TIME_ANNOTATION)
        ),  true);


        CSVElementMapper publisherRoleElementMapper = new CSVElementMapper(PUBLISHER_ROLE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_PUBLISHER_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_PUBLISHER_ROLE),
                new CSVAttributeMapper(REF_PUBLISHER_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_REF),
                new CSVAttributeMapper(VERSION_PUBLISHER_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_VERSION),
                new CSVAttributeMapper(ANNOTATION_PUBLISHER_ROLE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_PUBLISHER_ROLE_ANNOTATION)
        ),  true);


        CSVElementMapper rightsSummaryElementMapper = new CSVElementMapper(RIGHTS_SUMMARY, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY, Arrays.asList(
                new CSVAttributeMapper(SOURCE_RIGHTS_SUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RIGHTS_SUMMARY),
                new CSVAttributeMapper(REF_RIGHTS_SUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_REF),
                new CSVAttributeMapper(VERSION_RIGHTS_SUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_VERSION),
                new CSVAttributeMapper(ANNOTATION_RIGHTS_SUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_SUMMARY_ANNOTATION)
        ),  true);


        CSVElementMapper rightsLinkElementMapper = new CSVElementMapper(RIGHTS_LINK, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK, Arrays.asList(
                new CSVAttributeMapper(SOURCE_RIGHTS_LINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_SOURCE_RIGHTS_LINK),
                new CSVAttributeMapper(REF_RIGHTS_LINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_REF),
                new CSVAttributeMapper(VERSION_RIGHTS_LINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_VERSION),
                new CSVAttributeMapper(ANNOTATION_RIGHTS_LINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_RIGHTS_LINK_ANNOTATION)
        ),  true);

        CSVElementMapper annotationElementMapper = new CSVElementMapper(ANNOTATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION, Arrays.asList(
                new CSVAttributeMapper(ANNOTATION_TYPE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_ANNOTATION_TYPE),
                new CSVAttributeMapper(SOURCE_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_SOURCE),
                new CSVAttributeMapper(REF_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_REF),
                new CSVAttributeMapper(VERSION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_VERSION),
                new CSVAttributeMapper(ANNOTATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_ANNOTATION_ANNOTATION)
        ));


        CSVElementMapper instantiationIdentifierElementMapper = new CSVElementMapper(INSTANTIATION_IDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_VERSION),
                new CSVAttributeMapper(INSTANTIATION_IDENTIFIER_INSTANTIATION_IDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_IDENTIFIER_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationDateElementMapper = new CSVElementMapper(INSTANTIATION_DATE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE, Arrays.asList(
                new CSVAttributeMapper(DATE_TYPE_INSTANTIATIONDATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_DATE_TYPE),
                new CSVAttributeMapper(SOURCE_INSTANTIATION_DATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_DATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_DATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_DATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationDimensionsElementMapper = new CSVElementMapper(INSTANTIATION_DIMENSIONS, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS, Arrays.asList(
                new CSVAttributeMapper(UNITSOF_MEASURE_INSTANTIATION_DIMENSIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_UNITSOFMEASURE),
                new CSVAttributeMapper(SOURCE_INSTANTIATION_DIMENSIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_DIMENSIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_DIMENSIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_DIMENSIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIMENSIONS_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationPhysicalElementMapper = new CSVElementMapper(INSTANTIATION_PHYSICAL, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATIONPHYSICAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_PHYSICAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_PHYSICAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_PHYSICAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_PHYSICAL_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationDigitalElementMapper = new CSVElementMapper(INSTANTIATION_DIGITAL, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATIONDIGITAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_DIGITAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_DIGITAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_DIGITAL_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DIGITAL_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationStandardElementMapper = new CSVElementMapper(INSTANTIATION_STANDARD, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD, Arrays.asList(
                new CSVAttributeMapper(PROFILE_STANDARD_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_PROFILE),
                new CSVAttributeMapper(SOURCE_STANDARD_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_SOURCE),
                new CSVAttributeMapper(REF_STANDARD_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_REF),
                new CSVAttributeMapper(VERSION_STANDARD_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_VERSION),
                new CSVAttributeMapper(ANNOTATION_STANDARD_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_STANDARD_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationLocationElementMapper = new CSVElementMapper(INSTANTIATION_LOCATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION, Arrays.asList(
                new CSVAttributeMapper(SOURCE_LOCATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_SOURCE),
                new CSVAttributeMapper(REF_LOCATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_REF),
                new CSVAttributeMapper(VERSION_LOCATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_VERSION),
                new CSVAttributeMapper(ANNOTATION_LOCATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LOCATION_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationMediaTypeElementMapper = new CSVElementMapper(INSTANTIATION_MEDIATYPE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_MEDIATYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_SOURCE),
                new CSVAttributeMapper(REF_MEDIATYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_REF),
                new CSVAttributeMapper(VERSION_MEDIATYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_VERSION),
                new CSVAttributeMapper(ANNOTATION_MEDIATYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_MEDIATYPE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationGenerationsElementMapper = new CSVElementMapper(INSTANTIATION_GENERATIONS, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS, Arrays.asList(
                new CSVAttributeMapper(SOURCE_GENERATIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_SOURCE),
                new CSVAttributeMapper(REF_GENERATIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_REF),
                new CSVAttributeMapper(VERSION_GENERATIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_VERSION),
                new CSVAttributeMapper(ANNOTATION_GENERATIONS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_GENERATIONS_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationFileSizeElementMapper = new CSVElementMapper(INSTANTIATION_FILE_SIZE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE, Arrays.asList(
                new CSVAttributeMapper(UNITS_OFMEASURE_FILESIZE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_UNITSOFMEASURE),
                new CSVAttributeMapper(SOURCE_FILESIZE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_SOURCE),
                new CSVAttributeMapper(REF_FILESIZE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_REF),
                new CSVAttributeMapper(VERSION_FILESIZE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_VERSION),
                new CSVAttributeMapper(ANNOTATION_FILESIZE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_FILE_SIZE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationTimeStartElementMapper = new CSVElementMapper(INSTANTIATION_TIMESTART, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART, Arrays.asList(
                new CSVAttributeMapper(SOURCE_TIMESTART_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_SOURCE),
                new CSVAttributeMapper(REF_TIMESTART_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_REF),
                new CSVAttributeMapper(VERSION_TIMESTART_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_VERSION),
                new CSVAttributeMapper(ANNOTATION_TIMESTART_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TIMESTART_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationDurationElementMapper = new CSVElementMapper(INSTANTIATION_DURATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION, Arrays.asList(
                new CSVAttributeMapper(SOURCE_DURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_SOURCE),
                new CSVAttributeMapper(REF_DURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_REF),
                new CSVAttributeMapper(VERSION_DURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_VERSION),
                new CSVAttributeMapper(ANNOTATION_DURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DURATION_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationDataRateElementMapper = new CSVElementMapper(INSTANTIATION_DATARATE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE, Arrays.asList(
                new CSVAttributeMapper(UNITS_OFMEASURE_DATARATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_UNITSOFMEASURE),
                new CSVAttributeMapper(SOURCE_DATARATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_SOURCE),
                new CSVAttributeMapper(REF_DATARATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_REF),
                new CSVAttributeMapper(VERSION_DATARATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_VERSION),
                new CSVAttributeMapper(ANNOTATION_DATARATE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_DATARATE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationColorsElementMapper = new CSVElementMapper(INSTANTIATION_COLORS, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS, Arrays.asList(
                new CSVAttributeMapper(SOURCE_COLORS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_SOURCE),
                new CSVAttributeMapper(REF_COLORS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_REF),
                new CSVAttributeMapper(VERSIONS_COLORS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_VERSIONS),
                new CSVAttributeMapper(ANNOTATION_COLORS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_COLORS_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationTracksElementMapper = new CSVElementMapper(INSTANTIATION_TRACKS, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS, Arrays.asList(
                new CSVAttributeMapper(SOURCE_TRACKS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_SOURCE),
                new CSVAttributeMapper(REF_TRACKS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_REF),
                new CSVAttributeMapper(VERSION_TRACKS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_VERSION),
                new CSVAttributeMapper(ANNOTATION_TRACKS_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_TRACKS_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationChannelConfigurationElementMapper = new CSVElementMapper(INSTANTIATION_CHANNELCONFIGURATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION, Arrays.asList(
                new CSVAttributeMapper(SOURCE_CHANNELCONFIGURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_SOURCE),
                new CSVAttributeMapper(REF_CHANNELCONFIGURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_REF),
                new CSVAttributeMapper(VERSION_CHANNELCONFIGURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_VERSION),
                new CSVAttributeMapper(ANNOTATION_CHANNELCONFIGURATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_CHANNELCONFIGURATION_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationLanguageElementMapper = new CSVElementMapper(INSTANTIATION_LANGUAGE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_LANGUAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_SOURCE),
                new CSVAttributeMapper(REF_LANGUAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_REF),
                new CSVAttributeMapper(VERSION_LANGUAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_VERSION),
                new CSVAttributeMapper(ANNOTATION_LANGUAGE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_LANGUAGE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationAlternativeModesElementMapper = new CSVElementMapper(INSTANTIATION_ALTERNATIVEMODES, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES, Arrays.asList(
                new CSVAttributeMapper(SOURCE_ALTERNATIVEMODES_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_SOURCE),
                new CSVAttributeMapper(REF_ALTERNATIVEMODES_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_REF),
                new CSVAttributeMapper(VERSION_ALTERNATIVEMODES_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_VERSION),
                new CSVAttributeMapper(ANNOTATION_ALTERNATIVEMODES_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ALTERNATIVEMODES_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationRelationTypeElementMapper = new CSVElementMapper(INSTANTIATION_RELATION_TYPE, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATION_RELATIONTYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_RELATIONTYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_RELATIONTYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_RELATIONTYPE_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATION_TYPE_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationRelationIdentifierElementMapper = new CSVElementMapper(INSTANTIATION_RELATIONIDENTIFIER, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATION_RELATIONIDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_RELATIONIDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_RELATIONIDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_RELATIONIDENTIFIER_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RELATIONIDENTIFIER_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationRightsSummaryElementMapper = new CSVElementMapper(INSTANTIATION_RIGHTSSUMMARY, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATION_RIGHTSSUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_RIGHTSSUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_RIGHTSSUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_RIGHTSSUMMARY_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSSUMMARY_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationRightsLinkElementMapper = new CSVElementMapper(INSTANTIATION_RIGHTSLINK, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK, Arrays.asList(
                new CSVAttributeMapper(SOURCE_INSTANTIATION_RIGHTSLINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_RIGHTSLINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_RIGHTSLINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_RIGHTSLINK_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_RIGHTSLINK_ANNOTATION)
        ),  true);


        CSVElementMapper instantiationAnnotationElementMapper = new CSVElementMapper(INSTANTIATION_ANNOTATION, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION, Arrays.asList(
                new CSVAttributeMapper(ANNOTATION_TYPE_INSTANTIATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATIONTYPE),
                new CSVAttributeMapper(SOURCE_INSTANTIATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_SOURCE),
                new CSVAttributeMapper(REF_INSTANTIATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_REF),
                new CSVAttributeMapper(VERSION_INSTANTIATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_VERSION),
                new CSVAttributeMapper(ANNOTATION_INSTANTIATION_ANNOTATION_ATTR, PBCORE_DESCRIPTION_DOCUMENT_PBCORE_INSTANTIATION_ANNOTATION_ANNOTATION)
        ),  true);

        String file = File.separator + "v01" + File.separator + "mappers.json";
        ObjectMapper mapper = new ObjectMapper();
        List<CSVElementMapper> csvElementMappers = Arrays.asList(assetTypeElementMapper,
                assetDateElementMapper,
                identifierElementMapper,
                titleElementMapper,
                subjectElementMapper,
                descriptionElementMapper,
//                sourceElementMapper,
                genreElementMapper,
                relationTypeElementMapper,
                relationIdentifierElementMapper,
                coverageElementMapper,
                coverageTypeElementMapper,
                audienceLevelElementMapper,
                audienceRatingElementMapper,
                creatorElementMapper,
                creatorRoleElementMapper,
                contributorElementMapper,
                contributorRoleElementMapper,
                publisherElementMapper,
                publisherRoleElementMapper,
                rightsSummaryElementMapper,
                rightsLinkElementMapper,
                annotationElementMapper,
                instantiationIdentifierElementMapper,
                instantiationDateElementMapper,
                instantiationDimensionsElementMapper,
                instantiationPhysicalElementMapper,
                instantiationDigitalElementMapper,
                instantiationStandardElementMapper,
                instantiationLocationElementMapper,
                instantiationMediaTypeElementMapper,
                instantiationGenerationsElementMapper,
                instantiationFileSizeElementMapper,
                instantiationTimeStartElementMapper,
                instantiationDurationElementMapper,
                instantiationDataRateElementMapper,
                instantiationColorsElementMapper,
                instantiationTracksElementMapper,
                instantiationChannelConfigurationElementMapper,
                instantiationLanguageElementMapper,
                instantiationAlternativeModesElementMapper,
                instantiationRelationTypeElementMapper,
                instantiationRelationIdentifierElementMapper,
                instantiationRightsSummaryElementMapper,
                instantiationRightsLinkElementMapper,
                instantiationAnnotationElementMapper);
        try {
            mapper.writeValue(new File(file), csvElementMappers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateElementMappers();
    }
}
