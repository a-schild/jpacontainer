/*
 * JPAContainer
 * Copyright (C) 2010 Oy IT Mill Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.vaadin.addons.jpacontainer.demo;

import com.vaadin.addons.jpacontainer.EntityItem;
import com.vaadin.addons.jpacontainer.demo.domain.Customer;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author peholmst
 */
public class CustomerWindow extends Window {

    private EntityItem<Customer> customer;

    public CustomerWindow(EntityItem<Customer> customer) {
        super("Customer Details");
        setModal(true);
        customer.setWriteThrough(false); // We want an Apply feature
        this.customer = customer;
        initWindow();
    }

    protected static class CustomerFieldFactory extends DefaultFieldFactory {

        public static final String COMMON_FIELD_WIDTH = "12em";

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            Field f = super.createField(item, propertyId, uiContext);

            if ("custNo".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setReadOnly(true);
                tf.setWidth("5em");
            } else if ("customerName".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a customer name");
                tf.setWidth(COMMON_FIELD_WIDTH);
                tf.addValidator(new StringLengthValidator(
                        "Company name must be at least 2 characters", 2, 255,
                        false));
            } else if ("notes".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setWidth(COMMON_FIELD_WIDTH);
            } else if ("lastInvoiceDate".equals(propertyId) || "lastOrderDate".
                    equals(propertyId)) {
                f.setReadOnly(true);
            } else if (propertyId.toString().endsWith("streetOrBox")) {
                f.setCaption("Street or Box");
                f.setWidth(COMMON_FIELD_WIDTH);
            } else if (propertyId.toString().endsWith("postalCode")) {
                f.setCaption("Postal Code");
                f.addValidator(new StringLengthValidator(
                        "Postal code must be 5 characters", 5,
                        5, true));
                f.setWidth("5em");
            } else if (propertyId.toString().endsWith("postOffice")) {
                f.setCaption("Post Office");
                f.setWidth(COMMON_FIELD_WIDTH);
            } else if (propertyId.toString().endsWith("country")) {
                f.setCaption("Country");
                f.setWidth(COMMON_FIELD_WIDTH);
            }

            return f;
        }
    }

    protected void initWindow() {
        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setMargin(true);
        layout.setSpacing(true);

        // Create the forms
        CustomerFieldFactory fieldFactory = new CustomerFieldFactory();
        final Form generalForm = new Form();
        {
            generalForm.setCaption("General information");
            generalForm.setWriteThrough(true); // We use the buffering of the EntityItem instead
            generalForm.setFormFieldFactory(fieldFactory);
            generalForm.setItemDataSource(customer);
            generalForm.setVisibleItemProperties(new String[]{"custNo",
                        "customerName",
                        "notes",
                        "lastInvoiceDate",
                        "lastOrderDate"});
            addComponent(generalForm);
        }
        final Form billingForm = new Form();
        {
            billingForm.setCaption("Billing Address");
            billingForm.setWriteThrough(true); // We use the buffering of the EntityItem instead
            billingForm.setFormFieldFactory(fieldFactory);
            billingForm.setItemDataSource(customer);
            billingForm.setVisibleItemProperties(new String[]{"billingAddress.streetOrBox",
                        "billingAddress.postalCode",
                        "billingAddress.postOffice",
                        "billingAddress.country"});
            addComponent(billingForm);
        }
        final Form shippingForm = new Form();
        {
            shippingForm.setCaption("Shipping Address");
            shippingForm.setWriteThrough(true); // We use the buffering of the EntityItem instead
            shippingForm.setFormFieldFactory(fieldFactory);
            shippingForm.setItemDataSource(customer);
            shippingForm.setVisibleItemProperties(new String[]{"shippingAddress.streetOrBox",
                        "shippingAddress.postalCode",
                        "shippingAddress.postOffice",
                        "shippingAddress.country"});
            addComponent(shippingForm);
        }

        HorizontalLayout buttons = new HorizontalLayout();
        {
            buttons.setSpacing(true);
            Button applyBtn = new Button("Apply and Close", new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    try {
                        generalForm.validate();
                        billingForm.validate();
                        shippingForm.validate();
                        try {
                            customer.commit();
                            ((Window) getParent()).removeWindow(
                                    CustomerWindow.this);
                        } catch (Exception e) {
                            showNotification("Could not apply changes",
                                    e.getMessage(),
                                    Notification.TYPE_ERROR_MESSAGE);
                        }
                    } catch (InvalidValueException e) {
                        // Ignore it, let the form handle it
                    }
                }
            });
            buttons.addComponent(applyBtn);
            buttons.setComponentAlignment(applyBtn, Alignment.MIDDLE_RIGHT);

            Button discardBtn = new Button("Discard and Close", new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    customer.discard();
                    ((Window) getParent()).removeWindow(CustomerWindow.this);
                }
            });
            buttons.addComponent(discardBtn);
            layout.addComponent(buttons);
        }
        setSizeUndefined();
        setWidth("30em");
    }
}
