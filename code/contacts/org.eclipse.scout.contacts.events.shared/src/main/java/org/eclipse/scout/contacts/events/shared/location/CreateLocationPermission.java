package org.eclipse.scout.contacts.events.shared.location;

import java.security.BasicPermission;

public class CreateLocationPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public CreateLocationPermission() {
    super(CreateLocationPermission.class.getSimpleName());
  }
}
