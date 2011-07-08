/*
 * JPAContainer
 * Copyright (C) 2010-2011 Oy Vaadin Ltd
 *
 * This program is available both under Commercial Vaadin Add-On
 * License 2.0 (CVALv2) and under GNU Affero General Public License (version
 * 3 or later) at your option.
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and CVALv2 along with this program.  If not, see
 * <http://www.gnu.org/licenses/> and <http://vaadin.com/license/cval-2.0>.
 */
package com.vaadin.addon.jpacontainer.demo.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import org.apache.commons.lang.ObjectUtils;

/**
 * Example embeddable object for the JPAContainer demo application.
 *
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
@Embeddable
public class Address implements Serializable {

    private String streetOrBox = "";
    private String postalCode = "";
    private String postOffice = "";
    private String country = "";

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetOrBox() {
        return streetOrBox;
    }

    public void setStreetOrBox(String streetOrBox) {
        this.streetOrBox = streetOrBox;
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s, %s", streetOrBox, postalCode,
                postOffice, country);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            Address o = (Address) obj;
            return ObjectUtils.equals(streetOrBox, o.streetOrBox)
                    && ObjectUtils.equals(postalCode, o.postalCode)
                    && ObjectUtils.equals(postOffice, o.postOffice)
                    && ObjectUtils.equals(country, o.country);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + ObjectUtils.hashCode(streetOrBox);
        hash = hash * 31 + ObjectUtils.hashCode(postalCode);
        hash = hash * 31 + ObjectUtils.hashCode(postOffice);
        hash = hash * 31 + ObjectUtils.hashCode(country);
        return hash;
    }
}
