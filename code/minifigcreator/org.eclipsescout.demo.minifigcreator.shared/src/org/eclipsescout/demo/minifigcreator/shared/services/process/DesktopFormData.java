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
package org.eclipsescout.demo.minifigcreator.shared.services.process;

import java.util.Map;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.HeadLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.LegsLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.TorsoLookupCall;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated, no manual modifications recommended.
 * 
 * @generated
 */
public class DesktopFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public DesktopFormData() {
  }

  public Head getHead() {
    return getFieldByClass(Head.class);
  }

  public Legs getLegs() {
    return getFieldByClass(Legs.class);
  }

  public Name getName() {
    return getFieldByClass(Name.class);
  }

  public Placeholder getPlaceholder() {
    return getFieldByClass(Placeholder.class);
  }

  public Placeholder2 getPlaceholder2() {
    return getFieldByClass(Placeholder2.class);
  }

  /**
   * access method for property State.
   */
  public FormState getState() {
    return getStateProperty().getValue();
  }

  /**
   * access method for property State.
   */
  public void setState(FormState state) {
    getStateProperty().setValue(state);
  }

  public StateProperty getStateProperty() {
    return getPropertyByClass(StateProperty.class);
  }

  public Summary getSummary() {
    return getFieldByClass(Summary.class);
  }

  public Torso getTorso() {
    return getFieldByClass(Torso.class);
  }

  public static class Head extends AbstractValueFieldData<Part> {

    private static final long serialVersionUID = 1L;

    public Head() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, HeadLookupCall.class);
      ruleMap.put(ValidationRule.MANDATORY, true);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }

  public static class Legs extends AbstractValueFieldData<Part> {

    private static final long serialVersionUID = 1L;

    public Legs() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, LegsLookupCall.class);
      ruleMap.put(ValidationRule.MANDATORY, true);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }

  public static class Name extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;

    public Name() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MANDATORY, true);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }

  public static class Placeholder extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;

    public Placeholder() {
    }
  }

  public static class Placeholder2 extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;

    public Placeholder2() {
    }
  }

  public static class StateProperty extends AbstractPropertyData<FormState> {

    private static final long serialVersionUID = 1L;

    public StateProperty() {
    }
  }

  public static class Summary extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;

    public Summary() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }

  public static class Torso extends AbstractValueFieldData<Part> {

    private static final long serialVersionUID = 1L;

    public Torso() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, TorsoLookupCall.class);
      ruleMap.put(ValidationRule.MANDATORY, true);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }
}
