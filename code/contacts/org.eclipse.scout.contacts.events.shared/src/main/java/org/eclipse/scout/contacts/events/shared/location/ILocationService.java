package org.eclipse.scout.contacts.events.shared.location;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

/**
 * <h3>{@link ILocationService}</h3>
 *
 * @author jbr
 */
@TunnelToServer
public interface ILocationService extends IService {

  /**
   * @param formData
   * @return
   */
  LocationFormData prepareCreate(LocationFormData formData);

  /**
   * @param formData
   * @return
   */
  LocationFormData create(LocationFormData formData);

  /**
   * @param formData
   * @return
   */
  LocationFormData load(LocationFormData formData);

  /**
   * @param formData
   * @return
   */
  LocationFormData store(LocationFormData formData);

  /**
   * @param filter
   * @return
   */
  LocationTablePageData getLocationTableData(SearchFilter filter);
}
