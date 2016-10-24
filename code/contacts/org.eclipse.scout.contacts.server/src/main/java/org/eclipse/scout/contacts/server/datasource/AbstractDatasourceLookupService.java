package org.eclipse.scout.contacts.server.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.annotations.ConfigOperation;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * <h3>{@link AbstractDatasourceLookupService}</h3>
 *
 * @author jbr
 */
public abstract class AbstractDatasourceLookupService<FD extends AbstractFormData> extends AbstractLookupService<String> {
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  protected abstract AbstractDataStore<FD> provideDataStore();

  protected abstract String provideKey(FD formData);

  protected abstract String provideText(FD formData);

  @ConfigOperation
  @Order(30)
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    List<ILookupRow<String>> result = new ArrayList<ILookupRow<String>>();

    AbstractDataStore<FD> dataStore = provideDataStore();
    Collection<FD> formDatas = dataStore.getAll();
    for (FD formData : formDatas) {
      String key = provideKey(formData);
      String text = provideText(formData);
      result.add(new LookupRow<String>(key, text));
    }
    return result;
  }

  protected Pattern createSearchPattern(String s) {
    if (s == null) {
      s = "";
    }
    s = s.replace("*", "@wildcard@");
    s = s.toLowerCase();
    s = StringUtility.escapeRegexMetachars(s);
    s = s.replace("@wildcard@", ".*");
    if (!s.contains(".*")) {
      s = s + ".*";
    }
    return Pattern.compile(s, Pattern.DOTALL);
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
    if (call.getKey() == null) {
      return CollectionUtility.emptyArrayList();
    }
    Object key = call.getKey();
    List<? extends ILookupRow<String>> rows = execCreateLookupRows();
    if (rows == null) {
      return CollectionUtility.emptyArrayList();
    }
    ArrayList<ILookupRow<String>> list = new ArrayList<ILookupRow<String>>(rows.size());
    for (ILookupRow<String> row : rows) {
      if (key.equals(row.getKey())) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
    List<ILookupRow<String>> list = new ArrayList<ILookupRow<String>>();
    Pattern p = createSearchPattern(call.getText());
    for (ILookupRow<String> row : execCreateLookupRows()) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
    List<ILookupRow<String>> list = new ArrayList<ILookupRow<String>>();
    Pattern p = createSearchPattern(call.getAll());
    for (ILookupRow<String> row : execCreateLookupRows()) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
    List<ILookupRow<String>> list = new ArrayList<ILookupRow<String>>();
    Object parentKey = call.getRec();
    if (parentKey == null) {
      for (ILookupRow<String> row : execCreateLookupRows()) {
        if (row.getParentKey() == null) {
          list.add(row);
        }
      }
    }
    else {
      for (ILookupRow<String> row : execCreateLookupRows()) {
        if (parentKey.equals(row.getParentKey())) {
          list.add(row);
        }
      }
    }
    return list;
  }
}
