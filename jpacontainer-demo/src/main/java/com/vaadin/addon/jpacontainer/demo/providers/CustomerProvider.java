/*
 * JPAContainer
 * Copyright (C) 2010 Oy IT Mill Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.vaadin.addon.jpacontainer.demo.providers;

import com.vaadin.addon.jpacontainer.demo.domain.Customer;
import org.springframework.stereotype.Repository;

/**
 * Entity provider for {@link Customer}s that uses Spring's declarative
 * transaction annotations. It is also annotated with the {@link Repository} annotation,
 * which means that the Spring container will automatically detect it and
 * add it to the container.
 *
 * TODO Add a more detailed description of this class.
 *
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
@Repository(value = "customerProvider")
public class CustomerProvider extends LocalEntityProviderBean<Customer> {

    /**
     * Creates a new <code>CustomerProvider</code>.
     */
    public CustomerProvider() {
        super(Customer.class);
    }
}