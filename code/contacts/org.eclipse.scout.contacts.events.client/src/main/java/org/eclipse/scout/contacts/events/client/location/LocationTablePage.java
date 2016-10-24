package org.eclipse.scout.contacts.events.client.location;

import org.eclipse.scout.contacts.client.common.AbstractEditMenu;
import org.eclipse.scout.contacts.client.common.AbstractNewMenu;
import org.eclipse.scout.contacts.events.client.location.LocationTablePage.Table;
import org.eclipse.scout.contacts.events.shared.location.ILocationService;
import org.eclipse.scout.contacts.events.shared.location.LocationTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(LocationTablePageData.class)
public class LocationTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LocationTablePage");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ILocationService.class).getLocationTableData(filter));
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  public class Table extends AbstractTable {

    public LocationIdColumn getLocationIdColumn() {
      return getColumnSet().getColumnByClass(LocationIdColumn.class);
    }

    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(1000)
    public class NewMenu extends AbstractNewMenu {

      @Override
      protected void execAction() {
        LocationForm form = new LocationForm();
        form.addFormListener(new FormListener() {

          @Override
          public void formChanged(FormEvent e) {
            if (FormEvent.TYPE_CLOSED == e.getType() && form.isFormStored()) {
              reloadPage();
            }
          }
        });
        form.startNew();
      }
    }

    @Order(2000)
    public class ModifyMenu extends AbstractEditMenu {

      @Override
      protected void execAction() {
        LocationForm form = new LocationForm();
        form.setLocationId(getLocationIdColumn().getSelectedValue());
        form.addFormListener(new FormListener() {

          @Override
          public void formChanged(FormEvent e) {
            if (FormEvent.TYPE_CLOSED == e.getType() && form.isFormStored()) {
              reloadPage();
            }
          }
        });
        form.startModify();
      }
    }

    @Order(0)
    public class LocationIdColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(1000)
    public class NameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(2000)
    public class CityColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("City");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(3000)
    public class CountryColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }
  }
}
