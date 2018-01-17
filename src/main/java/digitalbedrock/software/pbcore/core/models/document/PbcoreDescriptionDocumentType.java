
package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Definition: The pbcoreDescriptionDocumentType schema
 *                 type allows its use as a single asset or repeated use in the
 *                 pbcoreCollection.
 *  r
 * <p>Java class for pbcoreDescriptionDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pbcoreDescriptionDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pbcoreAssetType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreAssetDate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}dateStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}requiredSourceVersionStringType" maxOccurs="unbounded"/>
 *         &lt;element name="pbcoreTitle" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}titleStringType" maxOccurs="unbounded"/>
 *         &lt;element name="pbcoreSubject" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}subjectStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreDescription" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}descriptionStringType" maxOccurs="unbounded"/>
 *         &lt;element name="pbcoreGenre" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStartEndStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreRelation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="pbcoreRelationType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
 *                   &lt;element name="pbcoreRelationIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pbcoreCoverage" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="coverage" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStartEndStringType"/>
 *                   &lt;element name="coverageType" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Spatial"/>
 *                         &lt;enumeration value="Temporal"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pbcoreAudienceLevel" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreAudienceRating" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreCreator" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="creator" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
 *                   &lt;element name="creatorRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pbcoreContributor" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="contributor" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
 *                   &lt;element name="contributorRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}contributorStringType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pbcorePublisher" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="publisher" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
 *                   &lt;element name="publisherRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pbcoreRightsSummary" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}rightsSummaryType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreInstantiation" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}instantiationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreAnnotation" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}annotationStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcorePart" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}pbcorePartType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pbcoreExtension" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}extensionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pbcoreDescriptionDocumentType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "pbcoreAssetType",
    "pbcoreAssetDate",
    "pbcoreIdentifier",
    "pbcoreTitle",
    "pbcoreSubject",
    "pbcoreDescription",
    "pbcoreGenre",
    "pbcoreRelation",
    "pbcoreCoverage",
    "pbcoreAudienceLevel",
    "pbcoreAudienceRating",
    "pbcoreCreator",
    "pbcoreContributor",
    "pbcorePublisher",
    "pbcoreRightsSummary",
    "pbcoreInstantiation",
    "pbcoreAnnotation",
    "pbcorePart",
    "pbcoreExtension"
})
@XmlSeeAlso({
    PbcorePartType.class
})
public class PbcoreDescriptionDocumentType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStringType> pbcoreAssetType;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<DateStringType> pbcoreAssetDate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected List<RequiredSourceVersionStringType> pbcoreIdentifier;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected List<TitleStringType> pbcoreTitle;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SubjectStringType> pbcoreSubject;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected List<DescriptionStringType> pbcoreDescription;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStartEndStringType> pbcoreGenre;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcoreDescriptionDocumentType.PbcoreRelation> pbcoreRelation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcoreDescriptionDocumentType.PbcoreCoverage> pbcoreCoverage;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStringType> pbcoreAudienceLevel;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStringType> pbcoreAudienceRating;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcoreDescriptionDocumentType.PbcoreCreator> pbcoreCreator;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcoreDescriptionDocumentType.PbcoreContributor> pbcoreContributor;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcoreDescriptionDocumentType.PbcorePublisher> pbcorePublisher;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<RightsSummaryType> pbcoreRightsSummary;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<InstantiationType> pbcoreInstantiation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<AnnotationStringType> pbcoreAnnotation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<PbcorePartType> pbcorePart;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ExtensionType> pbcoreExtension;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;

    /**
     * Gets the value of the pbcoreAssetType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreAssetType property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreAssetType().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStringType }
     * 
     * 
     */
    public List<SourceVersionStringType> getPbcoreAssetType() {
        if (pbcoreAssetType == null) {
            pbcoreAssetType = new ArrayList<SourceVersionStringType>();
        }
        return this.pbcoreAssetType;
    }

    /**
     * Gets the value of the pbcoreAssetDate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreAssetDate property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreAssetDate().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateStringType }
     * 
     * 
     */
    public List<DateStringType> getPbcoreAssetDate() {
        if (pbcoreAssetDate == null) {
            pbcoreAssetDate = new ArrayList<DateStringType>();
        }
        return this.pbcoreAssetDate;
    }

    /**
     * Gets the value of the pbcoreIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreIdentifier property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreIdentifier().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequiredSourceVersionStringType }
     * 
     * 
     */
    public List<RequiredSourceVersionStringType> getPbcoreIdentifier() {
        if (pbcoreIdentifier == null) {
            pbcoreIdentifier = new ArrayList<RequiredSourceVersionStringType>();
        }
        return this.pbcoreIdentifier;
    }

    /**
     * Gets the value of the pbcoreTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreTitle property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreTitle().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TitleStringType }
     * 
     * 
     */
    public List<TitleStringType> getPbcoreTitle() {
        if (pbcoreTitle == null) {
            pbcoreTitle = new ArrayList<TitleStringType>();
        }
        return this.pbcoreTitle;
    }

    /**
     * Gets the value of the pbcoreSubject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreSubject property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreSubject().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjectStringType }
     * 
     * 
     */
    public List<SubjectStringType> getPbcoreSubject() {
        if (pbcoreSubject == null) {
            pbcoreSubject = new ArrayList<SubjectStringType>();
        }
        return this.pbcoreSubject;
    }

    /**
     * Gets the value of the pbcoreDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreDescription property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreDescription().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionStringType }
     * 
     * 
     */
    public List<DescriptionStringType> getPbcoreDescription() {
        if (pbcoreDescription == null) {
            pbcoreDescription = new ArrayList<DescriptionStringType>();
        }
        return this.pbcoreDescription;
    }

    /**
     * Gets the value of the pbcoreGenre property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreGenre property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreGenre().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStartEndStringType }
     * 
     * 
     */
    public List<SourceVersionStartEndStringType> getPbcoreGenre() {
        if (pbcoreGenre == null) {
            pbcoreGenre = new ArrayList<SourceVersionStartEndStringType>();
        }
        return this.pbcoreGenre;
    }

    /**
     * Gets the value of the pbcoreRelation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreRelation property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreRelation().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType.PbcoreRelation }
     * 
     * 
     */
    public List<PbcoreDescriptionDocumentType.PbcoreRelation> getPbcoreRelation() {
        if (pbcoreRelation == null) {
            pbcoreRelation = new ArrayList<PbcoreDescriptionDocumentType.PbcoreRelation>();
        }
        return this.pbcoreRelation;
    }

    /**
     * Gets the value of the pbcoreCoverage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreCoverage property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreCoverage().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType.PbcoreCoverage }
     * 
     * 
     */
    public List<PbcoreDescriptionDocumentType.PbcoreCoverage> getPbcoreCoverage() {
        if (pbcoreCoverage == null) {
            pbcoreCoverage = new ArrayList<PbcoreDescriptionDocumentType.PbcoreCoverage>();
        }
        return this.pbcoreCoverage;
    }

    /**
     * Gets the value of the pbcoreAudienceLevel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreAudienceLevel property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreAudienceLevel().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStringType }
     * 
     * 
     */
    public List<SourceVersionStringType> getPbcoreAudienceLevel() {
        if (pbcoreAudienceLevel == null) {
            pbcoreAudienceLevel = new ArrayList<SourceVersionStringType>();
        }
        return this.pbcoreAudienceLevel;
    }

    /**
     * Gets the value of the pbcoreAudienceRating property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreAudienceRating property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreAudienceRating().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStringType }
     * 
     * 
     */
    public List<SourceVersionStringType> getPbcoreAudienceRating() {
        if (pbcoreAudienceRating == null) {
            pbcoreAudienceRating = new ArrayList<SourceVersionStringType>();
        }
        return this.pbcoreAudienceRating;
    }

    /**
     * Gets the value of the pbcoreCreator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreCreator property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreCreator().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType.PbcoreCreator }
     * 
     * 
     */
    public List<PbcoreDescriptionDocumentType.PbcoreCreator> getPbcoreCreator() {
        if (pbcoreCreator == null) {
            pbcoreCreator = new ArrayList<PbcoreDescriptionDocumentType.PbcoreCreator>();
        }
        return this.pbcoreCreator;
    }

    /**
     * Gets the value of the pbcoreContributor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreContributor property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreContributor().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType.PbcoreContributor }
     * 
     * 
     */
    public List<PbcoreDescriptionDocumentType.PbcoreContributor> getPbcoreContributor() {
        if (pbcoreContributor == null) {
            pbcoreContributor = new ArrayList<PbcoreDescriptionDocumentType.PbcoreContributor>();
        }
        return this.pbcoreContributor;
    }

    /**
     * Gets the value of the pbcorePublisher property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcorePublisher property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcorePublisher().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcoreDescriptionDocumentType.PbcorePublisher }
     * 
     * 
     */
    public List<PbcoreDescriptionDocumentType.PbcorePublisher> getPbcorePublisher() {
        if (pbcorePublisher == null) {
            pbcorePublisher = new ArrayList<PbcoreDescriptionDocumentType.PbcorePublisher>();
        }
        return this.pbcorePublisher;
    }

    /**
     * Gets the value of the pbcoreRightsSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreRightsSummary property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreRightsSummary().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsSummaryType }
     * 
     * 
     */
    public List<RightsSummaryType> getPbcoreRightsSummary() {
        if (pbcoreRightsSummary == null) {
            pbcoreRightsSummary = new ArrayList<RightsSummaryType>();
        }
        return this.pbcoreRightsSummary;
    }

    /**
     * Gets the value of the pbcoreInstantiation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreInstantiation property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreInstantiation().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstantiationType }
     * 
     * 
     */
    public List<InstantiationType> getPbcoreInstantiation() {
        if (pbcoreInstantiation == null) {
            pbcoreInstantiation = new ArrayList<InstantiationType>();
        }
        return this.pbcoreInstantiation;
    }

    /**
     * Gets the value of the pbcoreAnnotation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreAnnotation property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreAnnotation().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnotationStringType }
     * 
     * 
     */
    public List<AnnotationStringType> getPbcoreAnnotation() {
        if (pbcoreAnnotation == null) {
            pbcoreAnnotation = new ArrayList<AnnotationStringType>();
        }
        return this.pbcoreAnnotation;
    }

    /**
     * Gets the value of the pbcorePart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcorePart property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcorePart().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PbcorePartType }
     * 
     * 
     */
    public List<PbcorePartType> getPbcorePart() {
        if (pbcorePart == null) {
            pbcorePart = new ArrayList<PbcorePartType>();
        }
        return this.pbcorePart;
    }

    /**
     * Gets the value of the pbcoreExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pbcoreExtension property.
     * 
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getPbcoreExtension().onAdd(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getPbcoreExtension() {
        if (pbcoreExtension == null) {
            pbcoreExtension = new ArrayList<ExtensionType>();
        }
        return this.pbcoreExtension;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="contributor" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
     *         &lt;element name="contributorRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}contributorStringType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "contributor",
        "contributorRole"
    })
    public static class PbcoreContributor {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected AffiliatedStringType contributor;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
        protected List<ContributorStringType> contributorRole;

        /**
         * Gets the value of the contributor property.
         * 
         * @return
         *     possible object is
         *     {@link AffiliatedStringType }
         *     
         */
        public AffiliatedStringType getContributor() {
            return contributor;
        }

        /**
         * Sets the value of the contributor property.
         * 
         * @param value
         *     allowed object is
         *     {@link AffiliatedStringType }
         *     
         */
        public void setContributor(AffiliatedStringType value) {
            this.contributor = value;
        }

        /**
         * Gets the value of the contributorRole property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contributorRole property.
         * 
         * <p>
         * For example, to onAdd a new item, do as follows:
         * <pre>
         *    getContributorRole().onAdd(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContributorStringType }
         * 
         * 
         */
        public List<ContributorStringType> getContributorRole() {
            if (contributorRole == null) {
                contributorRole = new ArrayList<ContributorStringType>();
            }
            return this.contributorRole;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="coverage" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStartEndStringType"/>
     *         &lt;element name="coverageType" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Spatial"/>
     *               &lt;enumeration value="Temporal"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "coverage",
        "coverageType"
    })
    public static class PbcoreCoverage {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected SourceVersionStartEndStringType coverage;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
        protected String coverageType;

        /**
         * Gets the value of the coverage property.
         * 
         * @return
         *     possible object is
         *     {@link SourceVersionStartEndStringType }
         *     
         */
        public SourceVersionStartEndStringType getCoverage() {
            return coverage;
        }

        /**
         * Sets the value of the coverage property.
         * 
         * @param value
         *     allowed object is
         *     {@link SourceVersionStartEndStringType }
         *     
         */
        public void setCoverage(SourceVersionStartEndStringType value) {
            this.coverage = value;
        }

        /**
         * Gets the value of the coverageType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCoverageType() {
            return coverageType;
        }

        /**
         * Sets the value of the coverageType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCoverageType(String value) {
            this.coverageType = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="creator" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
     *         &lt;element name="creatorRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "creator",
        "creatorRole"
    })
    public static class PbcoreCreator {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected AffiliatedStringType creator;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
        protected List<SourceVersionStringType> creatorRole;

        /**
         * Gets the value of the creator property.
         * 
         * @return
         *     possible object is
         *     {@link AffiliatedStringType }
         *     
         */
        public AffiliatedStringType getCreator() {
            return creator;
        }

        /**
         * Sets the value of the creator property.
         * 
         * @param value
         *     allowed object is
         *     {@link AffiliatedStringType }
         *     
         */
        public void setCreator(AffiliatedStringType value) {
            this.creator = value;
        }

        /**
         * Gets the value of the creatorRole property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the creatorRole property.
         * 
         * <p>
         * For example, to onAdd a new item, do as follows:
         * <pre>
         *    getCreatorRole().onAdd(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SourceVersionStringType }
         * 
         * 
         */
        public List<SourceVersionStringType> getCreatorRole() {
            if (creatorRole == null) {
                creatorRole = new ArrayList<SourceVersionStringType>();
            }
            return this.creatorRole;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="publisher" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}affiliatedStringType"/>
     *         &lt;element name="publisherRole" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "publisher",
        "publisherRole"
    })
    public static class PbcorePublisher {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected AffiliatedStringType publisher;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
        protected List<SourceVersionStringType> publisherRole;

        /**
         * Gets the value of the publisher property.
         * 
         * @return
         *     possible object is
         *     {@link AffiliatedStringType }
         *     
         */
        public AffiliatedStringType getPublisher() {
            return publisher;
        }

        /**
         * Sets the value of the publisher property.
         * 
         * @param value
         *     allowed object is
         *     {@link AffiliatedStringType }
         *     
         */
        public void setPublisher(AffiliatedStringType value) {
            this.publisher = value;
        }

        /**
         * Gets the value of the publisherRole property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the publisherRole property.
         * 
         * <p>
         * For example, to onAdd a new item, do as follows:
         * <pre>
         *    getPublisherRole().onAdd(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SourceVersionStringType }
         * 
         * 
         */
        public List<SourceVersionStringType> getPublisherRole() {
            if (publisherRole == null) {
                publisherRole = new ArrayList<SourceVersionStringType>();
            }
            return this.publisherRole;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="pbcoreRelationType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
     *         &lt;element name="pbcoreRelationIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pbcoreRelationType",
        "pbcoreRelationIdentifier"
    })
    public static class PbcoreRelation {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected SourceVersionStringType pbcoreRelationType;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected SourceVersionStringType pbcoreRelationIdentifier;

        /**
         * Gets the value of the pbcoreRelationType property.
         * 
         * @return
         *     possible object is
         *     {@link SourceVersionStringType }
         *     
         */
        public SourceVersionStringType getPbcoreRelationType() {
            return pbcoreRelationType;
        }

        /**
         * Sets the value of the pbcoreRelationType property.
         * 
         * @param value
         *     allowed object is
         *     {@link SourceVersionStringType }
         *     
         */
        public void setPbcoreRelationType(SourceVersionStringType value) {
            this.pbcoreRelationType = value;
        }

        /**
         * Gets the value of the pbcoreRelationIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link SourceVersionStringType }
         *     
         */
        public SourceVersionStringType getPbcoreRelationIdentifier() {
            return pbcoreRelationIdentifier;
        }

        /**
         * Sets the value of the pbcoreRelationIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link SourceVersionStringType }
         *     
         */
        public void setPbcoreRelationIdentifier(SourceVersionStringType value) {
            this.pbcoreRelationIdentifier = value;
        }

    }

}
