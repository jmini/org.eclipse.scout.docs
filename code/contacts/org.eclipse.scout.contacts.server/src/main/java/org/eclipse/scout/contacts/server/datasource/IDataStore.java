package org.eclipse.scout.contacts.server.datasource;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

/**
 * <h3>{@link IDataStore}</h3>
 *
 * @author jbr
 */
public interface IDataStore<FD extends AbstractFormData> {

  /**
   * @param formData
   */
  void store(FD formData);

  /**
   * @param initFD
   * @return
   */
  FD load(FD initFD);

}
