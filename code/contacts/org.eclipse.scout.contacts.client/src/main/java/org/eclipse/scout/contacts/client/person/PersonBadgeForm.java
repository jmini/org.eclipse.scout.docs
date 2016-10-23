package org.eclipse.scout.contacts.client.person;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.scout.contacts.client.person.PersonBadgeForm.MainBox.BrowserField;
import org.eclipse.scout.contacts.client.person.PersonBadgeForm.MainBox.CloseButton;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.browserfield.AbstractBrowserField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;

public class PersonBadgeForm extends AbstractForm {

  private String m_personId;
  private String m_firstName;
  private String m_lastName;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PersonBadge");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public CloseButton getOkButton() {
    return getFieldByClass(CloseButton.class);
  }

  public BrowserField getBrowserField() {
    return getFieldByClass(BrowserField.class);
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Order(40.0)
    public class BrowserField extends AbstractBrowserField {

      @Override
      protected boolean getConfiguredSandboxEnabled() {
        return false;
      }

      @Override
      protected int getConfiguredGridW() {
        return 3;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredScrollBarEnabled() {
        return false;
      }

      @Override
      protected int getConfiguredGridH() {
        return 25;
      }

      @Override
      protected void execPostMessage(String data, String origin) {
        if (origin == null && data == null) {
          return;
        }

        if ("SUBMIT_CLICKED".equals(data)) {
          doClose();
        }
        else if ("CANCEL_CLICKED".equals(data)) {
          doCancel();
        }
        else {
          throw new ProcessingException("Got an unexpected message: " + data);
        }
      }
    }

    @Order(100000)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      String baseUrl = "http://localhost/intapp/index.php";
      try {
        baseUrl = baseUrl + "?pid=" + URLEncoder.encode(m_personId, "UTF-8");
        baseUrl = baseUrl + "&fn=" + URLEncoder.encode(m_firstName, "UTF-8");
        baseUrl = baseUrl + "&ln=" + URLEncoder.encode(m_lastName, "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        throw new ProcessingException("Could not create the URL", e);
      }
      getBrowserField().setLocation(baseUrl);
    }

    @Override
    protected void execStore() {
    }
  }

  public void setPersonId(String personId) {
    m_personId = personId;
  }

  public void setFirstName(String firstName) {
    m_firstName = firstName;
  }

  public void setLastName(String lastName) {
    m_lastName = lastName;
  }
}
