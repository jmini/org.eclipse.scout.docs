/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.organization;

import org.eclipse.scout.contacts.server.datasource.AbstractDataStore;
import org.eclipse.scout.contacts.server.datasource.AbstractDatasourceLookupService;
import org.eclipse.scout.contacts.server.datasource.DatastoreOrganization;
import org.eclipse.scout.contacts.shared.organization.IOrganizationLookupService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.rt.platform.BEANS;

//tag::all[]
public class OrganizationLookupService extends AbstractDatasourceLookupService<OrganizationFormData> implements IOrganizationLookupService {

  @Override
  protected AbstractDataStore<OrganizationFormData> provideDataStore() {
    return BEANS.get(DatastoreOrganization.class);
  }

  @Override
  protected String provideKey(OrganizationFormData formData) {
    return formData.getOrganizationId();
  }

  @Override
  protected String provideText(OrganizationFormData formData) {
    return formData.getName().getValue();
  }
}
