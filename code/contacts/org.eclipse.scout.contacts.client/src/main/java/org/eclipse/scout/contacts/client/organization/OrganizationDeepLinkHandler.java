package org.eclipse.scout.contacts.client.organization;

import java.util.regex.Matcher;

import org.eclipse.scout.contacts.client.contact.ContactOutline;
import org.eclipse.scout.contacts.client.organization.OrganizationTablePage.Table;
import org.eclipse.scout.rt.client.deeplink.AbstractDeepLinkHandler;
import org.eclipse.scout.rt.client.deeplink.DeepLinkException;
import org.eclipse.scout.rt.client.deeplink.DeepLinkUriBuilder;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.BrowserHistoryEntry;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;

@Order(1100)
public class OrganizationDeepLinkHandler extends AbstractDeepLinkHandler {

  private static final String HANDLER_NAME = "organization";

  public OrganizationDeepLinkHandler() {
    super(defaultPattern(HANDLER_NAME, "[A-Za-z0-9_]+"));
  }

  @Override
  public String getName() {
    return HANDLER_NAME;
  }

  public BrowserHistoryEntry createBrowserHistoryEntry(OrganizationNodePage page) {
    return DeepLinkUriBuilder.createRelative()
        .info(page.getCell().getText())
        .parameterPath(toDeepLinkPath(page.getOrganizationId()))
        .createBrowserHistoryEntry();
  }

  @Override
  protected void handleImpl(Matcher matcher) throws DeepLinkException {
    String organizationId = matcher.group(1);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    IOutline outlineToActivate = null;
    IPage pageToActivate = null;
    for (IOutline outline : desktop.getAvailableOutlines()) {
      if (outline instanceof ContactOutline) {
        ITreeNode rootNode = outline.getRootNode();
        for (ITreeNode node : rootNode.getChildNodes()) {
          if (node.isVisible() && node instanceof OrganizationTablePage) {
            node.loadChildren();
            Table table = ((OrganizationTablePage) node).getTable();
            for (int i = 0; i < table.getRowCount(); i++) {
              String id = table.getOrganizationIdColumn().getValue(i);
              if (organizationId.equals(id)) {
                outlineToActivate = outline;
                pageToActivate = (IPage) node.getChildNode(i);
                break;
              }
            }
          }
        }
      }
    }
    if (outlineToActivate == null) {
      return;
    }
    if (desktop.getOutline() != outlineToActivate) {
      desktop.activateOutline(outlineToActivate);
    }
    outlineToActivate.selectNode(pageToActivate);
  }

}
