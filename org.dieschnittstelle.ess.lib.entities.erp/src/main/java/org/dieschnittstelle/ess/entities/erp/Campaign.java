package org.dieschnittstelle.ess.entities.erp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.persistence.*;

//@JsonbTypeSerializer(JsonbJsonTypeInfoHandler.class)
@Schema(name = "Campaign")
public class Campaign extends AbstractProduct implements Serializable {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(Campaign.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 4407600000386810001L;

	private List<ProductBundle> bundles;

	public Campaign() {
		logger.debug("<constructor>");
		this.bundles = new ArrayList<ProductBundle>();
	}

	public Campaign(String name) {
		super(name);
		this.bundles = new ArrayList<ProductBundle>();
	}

	public Collection<ProductBundle> getBundles() {
		return this.bundles;
	}

	public void setBundles(List<ProductBundle> bundles) {
		this.bundles = bundles;
	}

	public void addBundle(ProductBundle bundle) {
		this.bundles.add(bundle);
	}

	public String toString() {
		return "<Campaign " + this.getId() + ", " + this.getName() + ", "
				+ this.bundles + ">";
	}

//	public boolean equals(Object other) {
//		return EqualsBuilder.reflectionEquals(this, other,
//				new String[] { "bundles" })
//				&& LangUtils
//						.setequals(this.bundles, ((Campaign) other).bundles);
//	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this,
				new String[] { "bundles" });
	}

	@PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}

	@PostPersist
	public void onPostPersist() {
		logger.info("@PostPersist: " + this);
	}

	@PostRemove
	public void onPostRemove() {
		logger.info("@PostRemove: " + this);
	}

	@PostUpdate
	public void onPostUpdate() {
		logger.info("@PostUpdate: " + this);
	}

	@PrePersist
	public void onPrePersist() {
		logger.info("@PrePersist: " + this);
	}

	@PreRemove
	public void onPreRemove() {
		logger.info("@PreRemove: " + this);
	}

	@PreUpdate
	public void onPreUpdate() {
		logger.info("@PreUpdate: " + this);
	}

}
