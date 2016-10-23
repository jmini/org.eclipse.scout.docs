package org.eclipse.scout.contacts.client.organization;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractUrlImageField;
import org.eclipse.scout.contacts.shared.organization.IOrganizationService;
import org.eclipse.scout.contacts.shared.organization.OrganizationOverviewFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = OrganizationOverviewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OrganizationOverviewForm extends AbstractForm {

  private String organizationId;

  @FormData
  public String getOrganizationId() {
    return organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Organization");
  }

  public void startDisplay() {
    startInternalExclusive(new DisplayHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(10)
    public class GeneralBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(2)
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(3)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(10)
      public class PictureField extends AbstractUrlImageField { // <1>

        @Override
        protected int getConfiguredGridH() { // <2>
          return 4;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.Organization;
        }

        @Override
        protected boolean getConfiguredEditURLMenuVisible() {
          return false;
        }
      }
    }
  }

  public class DisplayHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      OrganizationOverviewFormData formData = new OrganizationOverviewFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrganizationService.class).loadOverview(formData);
      importFormData(formData);
      setEnabledGranted(false);
    }
  }
}
