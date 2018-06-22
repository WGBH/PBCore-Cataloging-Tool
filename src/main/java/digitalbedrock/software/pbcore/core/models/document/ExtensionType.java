package digitalbedrock.software.pbcore.core.models.document;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition: The extensionType schema type uses a common structure to allow
 * for the use of multiple, qualified extensions at the asset, instantiation and
 * essence levels.
 *
 * <p>
 * Java class for extensionType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="extensionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="extensionWrap" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="extensionElement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="extensionValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="extensionAuthorityUsed" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attGroup ref="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}sourceVersionGroup"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="extensionEmbedded" type="{http://www.pbcore.org/PBCore/PBCoreNamespace.html}embeddedType" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@SuppressWarnings("WeakerAccess")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extensionType", namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", propOrder = {
    "extensionWrap",
    "extensionEmbedded"
})
public class ExtensionType {

    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<ExtensionType.ExtensionWrap> extensionWrap;
    @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
    protected List<EmbeddedType> extensionEmbedded;

    /**
     * Gets the value of the extensionWrap property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the extensionWrap property.
     *
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getExtensionWrap().onAdd(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType.ExtensionWrap }
     *
     *
     */
    public List<ExtensionType.ExtensionWrap> getExtensionWrap() {
        if (extensionWrap == null) {
            extensionWrap = new ArrayList<>();
        }
        return this.extensionWrap;
    }

    /**
     * Gets the value of the extensionEmbedded property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the extensionEmbedded property.
     *
     * <p>
     * For example, to onAdd a new item, do as follows:
     * <pre>
     *    getExtensionEmbedded().onAdd(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmbeddedType }
     *
     *
     */
    public List<EmbeddedType> getExtensionEmbedded() {
        if (extensionEmbedded == null) {
            extensionEmbedded = new ArrayList<>();
        }
        return this.extensionEmbedded;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="extensionElement" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="extensionValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="extensionAuthorityUsed" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "extensionElement",
        "extensionValue",
        "extensionAuthorityUsed"
    })
    public static class ExtensionWrap {

        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected String extensionElement;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html", required = true)
        protected String extensionValue;
        @XmlElement(namespace = "http://www.pbcore.org/PBCore/PBCoreNamespace.html")
        @XmlSchemaType(name = "anyURI")
        protected String extensionAuthorityUsed;
        @XmlAttribute(name = "source")
        protected String source;
        @XmlAttribute(name = "ref")
        protected String ref;
        @XmlAttribute(name = "version")
        protected String version;
        @XmlAttribute(name = "annotation")
        protected String annotation;

        /**
         * Gets the value of the extensionElement property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getExtensionElement() {
            return extensionElement;
        }

        /**
         * Sets the value of the extensionElement property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setExtensionElement(String value) {
            this.extensionElement = value;
        }

        /**
         * Gets the value of the extensionValue property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getExtensionValue() {
            return extensionValue;
        }

        /**
         * Sets the value of the extensionValue property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setExtensionValue(String value) {
            this.extensionValue = value;
        }

        /**
         * Gets the value of the extensionAuthorityUsed property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getExtensionAuthorityUsed() {
            return extensionAuthorityUsed;
        }

        /**
         * Sets the value of the extensionAuthorityUsed property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setExtensionAuthorityUsed(String value) {
            this.extensionAuthorityUsed = value;
        }

        /**
         * Gets the value of the source property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setSource(String value) {
            this.source = value;
        }

        /**
         * Gets the value of the ref property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setRef(String value) {
            this.ref = value;
        }

        /**
         * Gets the value of the version property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getVersion() {
            return version;
        }

        /**
         * Sets the value of the version property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setVersion(String value) {
            this.version = value;
        }

        /**
         * Gets the value of the annotation property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getAnnotation() {
            return annotation;
        }

        /**
         * Sets the value of the annotation property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setAnnotation(String value) {
            this.annotation = value;
        }

    }

}
