package org.eclipse.scout.contacts.server.datasource;

import java.util.List;

import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

/**
 * <h3>{@link AbstractDatasourceLookupService}</h3>
 *
 * @author jbr
 */
public abstract class AbstractDatasourceLookupService<T> extends AbstractLookupService<T> {
  @Override
  public List<? extends ILookupRow<T>> getDataByKey(ILookupCall<T> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<T>> getDataByText(ILookupCall<T> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<T>> getDataByAll(ILookupCall<T> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<T>> getDataByRec(ILookupCall<T> call) {
    return null;
  }
}
