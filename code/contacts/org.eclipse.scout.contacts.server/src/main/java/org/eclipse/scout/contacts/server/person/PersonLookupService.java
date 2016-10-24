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

import org.eclipse.scout.contacts.server.datasource.AbstractDataStore;
import org.eclipse.scout.contacts.server.datasource.AbstractDatasourceLookupService;
import org.eclipse.scout.contacts.server.datasource.DatastorePerson;
import org.eclipse.scout.contacts.shared.person.IPersonLookupService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.StringUtility;

public class PersonLookupService extends AbstractDatasourceLookupService<PersonFormData> implements IPersonLookupService {

  @Override
  protected AbstractDataStore<PersonFormData> provideDataStore() {
    return BEANS.get(DatastorePerson.class);
  }

  @Override
  protected String provideKey(PersonFormData formData) {
    return formData.getPersonId();
  }

  @Override
  protected String provideText(PersonFormData formData) {
    return StringUtility.join(" ", formData.getFirstName().getValue(), formData.getLastName().getValue());
  }
}
