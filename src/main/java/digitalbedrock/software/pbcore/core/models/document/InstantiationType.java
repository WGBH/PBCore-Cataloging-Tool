
package digitalbedrock.software.pbcore.core.models.document;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Definition: The pbcoreinstantiationType schema type
 *                 uses a common structure to allow for a single instantiation or multiple
 *                 instantiations within a pbcoreDocumentDescription.
 * 
 * <p>Java class for instantiationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="instantiationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="instantiationIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}requiredSourceVersionStringType" maxOccurs="unbounded"/>
 *         &lt;element name="instantiationDate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}dateStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationDimensions" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationPhysical" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationDigital" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationStandard" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}instantiationStandardStringType" minOccurs="0"/>
 *         &lt;element name="instantiationLocation" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
 *         &lt;element name="instantiationMediaType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationGenerations" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationFileSize" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="instantiationTimeStart" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationDuration" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationDataRate" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}technicalStringType" minOccurs="0"/>
 *         &lt;element name="instantiationColors" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationTracks" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationChannelConfiguration" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationLanguage" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}threeLetterStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationAlternativeModes" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType" minOccurs="0"/>
 *         &lt;element name="instantiationEssenceTrack" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}essenceTrackType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationRelation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="instantiationRelationType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
 *                   &lt;element name="instantiationRelationIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="instantiationRights" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}rightsSummaryType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationAnnotation" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}annotationStringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationPart" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}instantiationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instantiationExtension" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}extensionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}startEndTimeGroup"/>
 *       &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instantiationType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "instantiationIdentifier",
    "instantiationDate",
    "instantiationDimensions",
    "instantiationPhysical",
    "instantiationDigital",
    "instantiationStandard",
    "instantiationLocation",
    "instantiationMediaType",
    "instantiationGenerations",
    "instantiationFileSize",
    "instantiationTimeStart",
    "instantiationDuration",
    "instantiationDataRate",
    "instantiationColors",
    "instantiationTracks",
    "instantiationChannelConfiguration",
    "instantiationLanguage",
    "instantiationAlternativeModes",
    "instantiationEssenceTrack",
    "instantiationRelation",
    "instantiationRights",
    "instantiationAnnotation",
    "instantiationPart",
    "instantiationExtension"
})
public class InstantiationType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected List<RequiredSourceVersionStringType> instantiationIdentifier;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<DateStringType> instantiationDate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<TechnicalStringType> instantiationDimensions;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationPhysical;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationDigital;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected InstantiationStandardStringType instantiationStandard;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
    protected SourceVersionStringType instantiationLocation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationMediaType;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<SourceVersionStringType> instantiationGenerations;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType instantiationFileSize;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationTimeStart;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationDuration;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected TechnicalStringType instantiationDataRate;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationColors;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationTracks;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationChannelConfiguration;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ThreeLetterStringType> instantiationLanguage;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected SourceVersionStringType instantiationAlternativeModes;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<EssenceTrackType> instantiationEssenceTrack;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<InstantiationType.InstantiationRelation> instantiationRelation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<RightsSummaryType> instantiationRights;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<AnnotationStringType> instantiationAnnotation;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<InstantiationType> instantiationPart;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ExtensionType> instantiationExtension;
    @XmlAttribute(name = "startTime")
    protected String startTime;
    @XmlAttribute(name = "endTime")
    protected String endTime;
    @XmlAttribute(name = "timeAnnotation")
    protected String timeAnnotation;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "annotation")
    protected String annotation;

    /**
     * Gets the value of the instantiationIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequiredSourceVersionStringType }
     * 
     * 
     */
    public List<RequiredSourceVersionStringType> getInstantiationIdentifier() {
        if (instantiationIdentifier == null) {
            instantiationIdentifier = new ArrayList<RequiredSourceVersionStringType>();
        }
        return this.instantiationIdentifier;
    }

    /**
     * Gets the value of the instantiationDate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationDate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateStringType }
     * 
     * 
     */
    public List<DateStringType> getInstantiationDate() {
        if (instantiationDate == null) {
            instantiationDate = new ArrayList<DateStringType>();
        }
        return this.instantiationDate;
    }

    /**
     * Gets the value of the instantiationDimensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationDimensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationDimensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TechnicalStringType }
     * 
     * 
     */
    public List<TechnicalStringType> getInstantiationDimensions() {
        if (instantiationDimensions == null) {
            instantiationDimensions = new ArrayList<TechnicalStringType>();
        }
        return this.instantiationDimensions;
    }

    /**
     * Gets the value of the instantiationPhysical property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationPhysical() {
        return instantiationPhysical;
    }

    /**
     * Sets the value of the instantiationPhysical property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationPhysical(SourceVersionStringType value) {
        this.instantiationPhysical = value;
    }

    /**
     * Gets the value of the instantiationDigital property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationDigital() {
        return instantiationDigital;
    }

    /**
     * Sets the value of the instantiationDigital property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationDigital(SourceVersionStringType value) {
        this.instantiationDigital = value;
    }

    /**
     * Gets the value of the instantiationStandard property.
     * 
     * @return
     *     possible object is
     *     {@link InstantiationStandardStringType }
     *     
     */
    public InstantiationStandardStringType getInstantiationStandard() {
        return instantiationStandard;
    }

    /**
     * Sets the value of the instantiationStandard property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstantiationStandardStringType }
     *     
     */
    public void setInstantiationStandard(InstantiationStandardStringType value) {
        this.instantiationStandard = value;
    }

    /**
     * Gets the value of the instantiationLocation property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationLocation() {
        return instantiationLocation;
    }

    /**
     * Sets the value of the instantiationLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationLocation(SourceVersionStringType value) {
        this.instantiationLocation = value;
    }

    /**
     * Gets the value of the instantiationMediaType property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationMediaType() {
        return instantiationMediaType;
    }

    /**
     * Sets the value of the instantiationMediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationMediaType(SourceVersionStringType value) {
        this.instantiationMediaType = value;
    }

    /**
     * Gets the value of the instantiationGenerations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationGenerations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationGenerations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceVersionStringType }
     * 
     * 
     */
    public List<SourceVersionStringType> getInstantiationGenerations() {
        if (instantiationGenerations == null) {
            instantiationGenerations = new ArrayList<SourceVersionStringType>();
        }
        return this.instantiationGenerations;
    }

    /**
     * Gets the value of the instantiationFileSize property.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalStringType }
     *     
     */
    public TechnicalStringType getInstantiationFileSize() {
        return instantiationFileSize;
    }

    /**
     * Sets the value of the instantiationFileSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalStringType }
     *     
     */
    public void setInstantiationFileSize(TechnicalStringType value) {
        this.instantiationFileSize = value;
    }

    /**
     * Gets the value of the instantiationTimeStart property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationTimeStart() {
        return instantiationTimeStart;
    }

    /**
     * Sets the value of the instantiationTimeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationTimeStart(SourceVersionStringType value) {
        this.instantiationTimeStart = value;
    }

    /**
     * Gets the value of the instantiationDuration property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationDuration() {
        return instantiationDuration;
    }

    /**
     * Sets the value of the instantiationDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationDuration(SourceVersionStringType value) {
        this.instantiationDuration = value;
    }

    /**
     * Gets the value of the instantiationDataRate property.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalStringType }
     *     
     */
    public TechnicalStringType getInstantiationDataRate() {
        return instantiationDataRate;
    }

    /**
     * Sets the value of the instantiationDataRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalStringType }
     *     
     */
    public void setInstantiationDataRate(TechnicalStringType value) {
        this.instantiationDataRate = value;
    }

    /**
     * Gets the value of the instantiationColors property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationColors() {
        return instantiationColors;
    }

    /**
     * Sets the value of the instantiationColors property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationColors(SourceVersionStringType value) {
        this.instantiationColors = value;
    }

    /**
     * Gets the value of the instantiationTracks property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationTracks() {
        return instantiationTracks;
    }

    /**
     * Sets the value of the instantiationTracks property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationTracks(SourceVersionStringType value) {
        this.instantiationTracks = value;
    }

    /**
     * Gets the value of the instantiationChannelConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationChannelConfiguration() {
        return instantiationChannelConfiguration;
    }

    /**
     * Sets the value of the instantiationChannelConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationChannelConfiguration(SourceVersionStringType value) {
        this.instantiationChannelConfiguration = value;
    }

    /**
     * Gets the value of the instantiationLanguage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationLanguage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationLanguage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ThreeLetterStringType }
     * 
     * 
     */
    public List<ThreeLetterStringType> getInstantiationLanguage() {
        if (instantiationLanguage == null) {
            instantiationLanguage = new ArrayList<ThreeLetterStringType>();
        }
        return this.instantiationLanguage;
    }

    /**
     * Gets the value of the instantiationAlternativeModes property.
     * 
     * @return
     *     possible object is
     *     {@link SourceVersionStringType }
     *     
     */
    public SourceVersionStringType getInstantiationAlternativeModes() {
        return instantiationAlternativeModes;
    }

    /**
     * Sets the value of the instantiationAlternativeModes property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceVersionStringType }
     *     
     */
    public void setInstantiationAlternativeModes(SourceVersionStringType value) {
        this.instantiationAlternativeModes = value;
    }

    /**
     * Gets the value of the instantiationEssenceTrack property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationEssenceTrack property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationEssenceTrack().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EssenceTrackType }
     * 
     * 
     */
    public List<EssenceTrackType> getInstantiationEssenceTrack() {
        if (instantiationEssenceTrack == null) {
            instantiationEssenceTrack = new ArrayList<EssenceTrackType>();
        }
        return this.instantiationEssenceTrack;
    }

    /**
     * Gets the value of the instantiationRelation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationRelation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationRelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstantiationType.InstantiationRelation }
     * 
     * 
     */
    public List<InstantiationType.InstantiationRelation> getInstantiationRelation() {
        if (instantiationRelation == null) {
            instantiationRelation = new ArrayList<InstantiationType.InstantiationRelation>();
        }
        return this.instantiationRelation;
    }

    /**
     * Gets the value of the instantiationRights property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationRights property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationRights().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RightsSummaryType }
     * 
     * 
     */
    public List<RightsSummaryType> getInstantiationRights() {
        if (instantiationRights == null) {
            instantiationRights = new ArrayList<RightsSummaryType>();
        }
        return this.instantiationRights;
    }

    /**
     * Gets the value of the instantiationAnnotation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationAnnotation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationAnnotation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnnotationStringType }
     * 
     * 
     */
    public List<AnnotationStringType> getInstantiationAnnotation() {
        if (instantiationAnnotation == null) {
            instantiationAnnotation = new ArrayList<AnnotationStringType>();
        }
        return this.instantiationAnnotation;
    }

    /**
     * Gets the value of the instantiationPart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationPart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstantiationType }
     * 
     * 
     */
    public List<InstantiationType> getInstantiationPart() {
        if (instantiationPart == null) {
            instantiationPart = new ArrayList<InstantiationType>();
        }
        return this.instantiationPart;
    }

    /**
     * Gets the value of the instantiationExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiationExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiationExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getInstantiationExtension() {
        if (instantiationExtension == null) {
            instantiationExtension = new ArrayList<ExtensionType>();
        }
        return this.instantiationExtension;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the timeAnnotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeAnnotation() {
        return timeAnnotation;
    }

    /**
     * Sets the value of the timeAnnotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeAnnotation(String value) {
        this.timeAnnotation = value;
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
     *         &lt;element name="instantiationRelationType" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
     *         &lt;element name="instantiationRelationIdentifier" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionStringType"/>
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
        "instantiationRelationType",
        "instantiationRelationIdentifier"
    })
    public static class InstantiationRelation {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected SourceVersionStringType instantiationRelationType;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected SourceVersionStringType instantiationRelationIdentifier;

        /**
         * Gets the value of the instantiationRelationType property.
         * 
         * @return
         *     possible object is
         *     {@link SourceVersionStringType }
         *     
         */
        public SourceVersionStringType getInstantiationRelationType() {
            return instantiationRelationType;
        }

        /**
         * Sets the value of the instantiationRelationType property.
         * 
         * @param value
         *     allowed object is
         *     {@link SourceVersionStringType }
         *     
         */
        public void setInstantiationRelationType(SourceVersionStringType value) {
            this.instantiationRelationType = value;
        }

        /**
         * Gets the value of the instantiationRelationIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link SourceVersionStringType }
         *     
         */
        public SourceVersionStringType getInstantiationRelationIdentifier() {
            return instantiationRelationIdentifier;
        }

        /**
         * Sets the value of the instantiationRelationIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link SourceVersionStringType }
         *     
         */
        public void setInstantiationRelationIdentifier(SourceVersionStringType value) {
            this.instantiationRelationIdentifier = value;
        }

    }

}
