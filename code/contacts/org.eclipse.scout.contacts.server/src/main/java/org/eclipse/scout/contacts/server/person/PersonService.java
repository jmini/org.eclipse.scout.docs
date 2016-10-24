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
package org.eclipse.scout.contacts.server.person;

import java.util.UUID;

import org.eclipse.scout.contacts.server.datasource.DatastorePerson;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonCreatePermission;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.contacts.shared.person.PersonReadPermission;
import org.eclipse.scout.contacts.shared.person.PersonSearchFormData;
import org.eclipse.scout.contacts.shared.person.PersonTablePageData;
import org.eclipse.scout.contacts.shared.person.PersonUpdatePermission;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

//tag::all[]
//tag::getTableData[]
public class PersonService implements IPersonService {

  //end::all[]
  @Override
  public PersonTablePageData getPersonTableData(SearchFilter filter, String organizationId) {
    PersonTablePageData pageData = new PersonTablePageData();
    PersonSearchFormData searchData = (PersonSearchFormData) filter.getFormData();

    // end::getTableData[]
    // tag::addOrganizationCriteria[]
//    sql.append(" WHERE 1 = 1 ");
//    addToWhere(sql, organizationId, "organization_id", "organizationId");
    // end::addOrganizationCriteria[]

//    if (searchData != null) {
//      addToWhere(sql, searchData.getFirstName().getValue(), "first_name", "firstName");
//      addToWhere(sql, searchData.getLastName().getValue(), "last_name", "lastName");
//      addToWhere(sql, searchData.getLocation().getCity().getValue(), "city", "location.city");
//      addToWhere(sql, searchData.getLocation().getCountry().getValue(), "country", "location.country");
//      addToWhere(sql, searchData.getOrganization().getValue(), "organization_id", "organization");
//    }
    // tag::getTableData[]

    DatastorePerson datastore = BEANS.get(DatastorePerson.class);
    datastore.loadAllInto(pageData, organizationId);

    return pageData;
  }
  // end::getTableData[]
  // tag::addOrganizationCriteria[]

  // end::addOrganizationCriteria[]

  //tag::all[]
  @Override
  public PersonFormData create(PersonFormData formData) {
    if (!ACCESS.check(new PersonCreatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    // add a unique person id if necessary
    if (StringUtility.isNullOrEmpty(formData.getPersonId())) {
      formData.setPersonId(UUID.randomUUID().toString());
    }

    DatastorePerson datastore = BEANS.get(DatastorePerson.class);
    datastore.store(formData);

    return store(formData); // <2>
  }

  @Override
  public PersonFormData load(PersonFormData formData) {
    if (!ACCESS.check(new PersonReadPermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    DatastorePerson datastore = BEANS.get(DatastorePerson.class);
    return datastore.load(formData);
  }

  @Override
  public PersonFormData store(PersonFormData formData) {
    if (!ACCESS.check(new PersonUpdatePermission())) {
      throw new VetoException(TEXTS.get("InsufficientPrivileges"));
    }

    DatastorePerson datastore = BEANS.get(DatastorePerson.class);
    datastore.store(formData);

    return formData;
  }
  // tag::getTableData[]
}
// end::all[]
// end::getTableData[]
