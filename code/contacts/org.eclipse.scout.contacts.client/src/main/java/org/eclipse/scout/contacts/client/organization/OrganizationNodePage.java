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
package org.eclipse.scout.contacts.client.organization;

import java.util.List;

import org.eclipse.scout.contacts.client.person.PersonTablePage;
import org.eclipse.scout.rt.client.deeplink.OutlineDeepLinkHandler;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.BEANS;

// tag::all[]
public class OrganizationNodePage extends AbstractPageWithNodes {

  private String organizationId; // <1>

  public String getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return OrganizationOverviewForm.class;
  }

  @Override
  protected void execInitDetailForm() {
    OrganizationOverviewForm form = (OrganizationOverviewForm) getDetailForm();
    form.setOrganizationId(getOrganizationId());
    form.startDisplay();
  }

  @Override
  protected void execPageActivated() {
    OrganizationDeepLinkHandler deepLinkHandler = BEANS.get(OrganizationDeepLinkHandler.class);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    desktop.setBrowserHistoryEntry(deepLinkHandler.createBrowserHistoryEntry(this));
  }

  @Override
  protected void execPageDeactivated() {
    OutlineDeepLinkHandler deepLinkHandler = BEANS.get(OutlineDeepLinkHandler.class);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    desktop.setBrowserHistoryEntry(deepLinkHandler.createBrowserHistoryEntry(getOutline()));
  }

  @Override // <2>
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    PersonTablePage personTablePage = new PersonTablePage();
    personTablePage.setOrganizationId(getOrganizationId()); // <3>
    pageList.add(personTablePage);
  }
}
// end::all[]
