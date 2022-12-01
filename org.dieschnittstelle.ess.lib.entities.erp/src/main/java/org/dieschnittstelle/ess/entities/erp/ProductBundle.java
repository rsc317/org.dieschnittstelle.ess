package org.dieschnittstelle.ess.entities.erp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import java.io.Serializable;
@Schema(name = "ProductBundle")
public class ProductBundle implements Serializable {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductBundle.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1501911067906145681L;

	private long id;

	// this had been changed to AbstractProduct due to some jboss/jackson serialisation issue
	// in wildfly 18, which throws an error on unmarshalling, probably due to @JsonTypeInfo,
	// but as we have migrated to TomEE in the meantime, we changed it back to the concrete class
	private IndividualisedProductItem product;

	private int units;

	public ProductBundle() {
		logger.debug("<constructor>");
	}

	public ProductBundle(IndividualisedProductItem product, int units) {
		this.units = units;
		this.setProduct(product);
	}

	public IndividualisedProductItem getProduct() {
		return this.product;
	}

	public void setProduct(IndividualisedProductItem product) {
		this.product = product;
	}

	public int getUnits() {
		return this.units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String toString() {
		return "<ProductBundle " + this.product + " (" + this.units + ")>";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
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
