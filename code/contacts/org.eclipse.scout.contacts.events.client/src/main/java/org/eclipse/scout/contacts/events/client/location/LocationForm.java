package org.eclipse.scout.contacts.events.client.location;

import org.eclipse.scout.contacts.client.common.CountryLookupCall;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.OkButton;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.TopBox;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.TopBox.NameField;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.TopBox.SequenceBox;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.TopBox.SequenceBox.CityField;
import org.eclipse.scout.contacts.events.client.location.LocationForm.MainBox.TopBox.SequenceBox.CountryField;
import org.eclipse.scout.contacts.events.shared.location.CreateLocationPermission;
import org.eclipse.scout.contacts.events.shared.location.ILocationService;
import org.eclipse.scout.contacts.events.shared.location.LocationFormData;
import org.eclipse.scout.contacts.events.shared.location.UpdateLocationPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = LocationFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class LocationForm extends AbstractForm {

  String locationId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Location");
  }

  @FormData
  public String getLocationId() {
    return locationId;
  }

  @FormData
  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  public TopBox getTopBox() {
    return getFieldByClass(TopBox.class);
  }

  public SequenceBox getSequenceBox() {
    return getFieldByClass(SequenceBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(0)
    public class TopBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(1000)
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(1500)
      public class SequenceBox extends AbstractSequenceBox {
        @Order(2000)
        public class CityField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("City");
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 128;
          }

          @Override
          protected String execValidateValue(String rawValue) {
            if (rawValue != null && !rawValue.startsWith("L")) {
              throw new VetoException(TEXTS.get("VetoCityNotWithL"));
            }
            else {
              clearErrorStatus();
            }
            return rawValue;
          }

        }

        @Order(3000)
        public class CountryField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Country");
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return CountryLookupCall.class;
          }
        }

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }
      }
    }

    @Order(100000)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      ILocationService service = BEANS.get(ILocationService.class);
      LocationFormData formData = new LocationFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateLocationPermission());
    }

    @Override
    protected void execStore() {
      ILocationService service = BEANS.get(ILocationService.class);
      LocationFormData formData = new LocationFormData();
      exportFormData(formData);
      service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      ILocationService service = BEANS.get(ILocationService.class);
      LocationFormData formData = new LocationFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateLocationPermission());
    }

    @Override
    protected void execStore() {
      ILocationService service = BEANS.get(ILocationService.class);
      LocationFormData formData = new LocationFormData();
      exportFormData(formData);
      service.create(formData);
    }
  }
}
