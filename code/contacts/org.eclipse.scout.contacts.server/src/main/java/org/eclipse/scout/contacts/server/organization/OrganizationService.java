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

import java.util.UUID;

import org.eclipse.scout.contacts.server.datasource.DatastoreOrganization;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationCreatePermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.contacts.shared.organization.OrganizationReadPermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationTablePageData;
import org.eclipse.scout.contacts.shared.organization.OrganizationUpdatePermission;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

// tag::getTableData[]
// tag::all[]
public class OrganizationService implements IOrganizationService {
  // end::all[]

  @Override
  public OrganizationTablePageData getOrganizationTableData(SearchFilter filter) {
    OrganizationTablePageData pageData = new OrganizationTablePageData();
    // end::getTableData[]
    // tag::allOrgs[]

    DatastoreOrganization datastore = BEANS.get(DatastoreOrganization.class);
    datastore.loadAllInto(pageData);

    // end::allOrgs[]
    // tag::getTableData[]
    return pageData;
  }
  // end::getTableData[]
  // tag::all[]

  @Override
  public OrganizationFormData create(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationCreatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    if (StringUtility.isNullOrEmpty(formData.getOrganizationId())) {
      formData.setOrganizationId(UUID.randomUUID().toString());
    }

    DatastoreOrganization datastore = BEANS.get(DatastoreOrganization.class);
    datastore.store(formData);

    return store(formData);
  }

  @Override
  public OrganizationFormData load(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationReadPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    DatastoreOrganization datastore = BEANS.get(DatastoreOrganization.class);
    return datastore.load(formData);
  }

  @Override
  public OrganizationFormData store(OrganizationFormData formData) {
    if (!ACCESS.check(new OrganizationUpdatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    DatastoreOrganization datastore = BEANS.get(DatastoreOrganization.class);
    datastore.store(formData);

    return formData;
  }
  //tag::getTableData[]
}
//end::getTableData[]
//end::all[]
