package org.eclipse.scout.contacts.events.client.location;

import org.eclipse.scout.contacts.events.shared.location.ILocationService;
import org.eclipse.scout.contacts.events.shared.location.LocationFormData;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class LocationFormTest {

  @BeanMock
  private ILocationService m_mockSvc;

  @Before
  public void setup() {
    LocationFormData answer = new LocationFormData();
    Mockito.when(m_mockSvc.prepareCreate(Matchers.any(LocationFormData.class))).thenReturn(answer);
    Mockito.when(m_mockSvc.create(Matchers.any(LocationFormData.class))).thenReturn(answer);
    Mockito.when(m_mockSvc.load(Matchers.any(LocationFormData.class))).thenReturn(answer);
    Mockito.when(m_mockSvc.store(Matchers.any(LocationFormData.class))).thenReturn(answer);
  }

  // TODO [jbr] add test cases
}
