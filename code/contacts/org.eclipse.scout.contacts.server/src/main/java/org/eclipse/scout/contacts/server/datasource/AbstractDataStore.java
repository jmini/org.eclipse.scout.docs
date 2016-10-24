package org.eclipse.scout.contacts.server.datasource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <h3>{@link AbstractDataStore}</h3>
 *
 * @author jbr
 */
public abstract class AbstractDataStore<FD extends AbstractFormData> implements IDataStore<FD> {

  Map<String, FD> m_map = new HashMap<>();

  @Override
  public void store(FD formData) {
    AbstractPropertyData<String> idPropertyData = computeId(formData);
    if (idPropertyData.getValue() == null) {
      idPropertyData.setValue(UUID.randomUUID().toString());
    }
    m_map.put(idPropertyData.getValue(), formData);
  }

  @Override
  public FD load(FD initFD) {
    String id = computeId(initFD).getValue();
    FD formData = m_map.get(id);
    if (formData != null) {
      @SuppressWarnings("unchecked")
      FD clone = (FD) formData.clone();
      return clone;
    }
    return null;
  }

  public Collection<FD> getAll() {
    return m_map.values();
  }

  public void clear() {
    m_map.clear();
  }

  protected abstract AbstractPropertyData<String> computeId(FD formData);
}
