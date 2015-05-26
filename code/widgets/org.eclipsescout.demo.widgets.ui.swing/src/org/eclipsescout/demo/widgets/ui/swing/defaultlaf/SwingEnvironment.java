/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.ui.swing.defaultlaf;

import org.eclipse.scout.rt.ui.swing.DefaultSwingEnvironment;

public class SwingEnvironment extends DefaultSwingEnvironment {

  @Override
  public int getFormRowHeight() {
    return 28;
  }

}
