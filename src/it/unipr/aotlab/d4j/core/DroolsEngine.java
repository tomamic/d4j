/*****************************************************************
 JADE - Java Agent DEvelopment Framework is a framework to develop
 multi-agent systems in compliance with the FIPA specifications.
 Copyright (C) 2000 CSELT S.p.A.

 GNU Lesser General Public License

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation,
 version 2.1 of the License.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the
 Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 Boston, MA  02111-1307, USA.
 *****************************************************************/

package it.unipr.aotlab.d4j.core;

import it.unipr.aotlab.d4j.core.factsmanager.FactsManager;
import it.unipr.aotlab.d4j.core.rulesmanager.RulesManager;
import it.unipr.aotlab.d4j.core.rulesmanager.RulesManagerException;
import it.unipr.aotlab.d4j.util.rule.RuleConverter;
import it.unipr.aotlab.d4j.util.id.*;

import org.drools.*;
import org.drools.event.*;
import org.drools.rule.RuleSet;
import org.drools.spi.AgendaFilter;
import org.drools.spi.ConflictResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The <code>DroolsEngine</code> is the class that embeds all the fetaures offered
 * by <tt>Drools</tt> into a service-like structure.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: DroolsEngine.java,v 1.7 2005/03/03 10:16:08 mic Exp $
 */
public class DroolsEngine {

    private IdentifierGenerator idGen = null;
    private RulesManager rulesManager = null;
    private FactsManager factsManager = null;
    private WorkingMemory wm = null;
    private ConflictResolver conflictResolver = null;
    private AgendaFilter agendaFilter = null;
    private List listeners = new ArrayList();
    private Map data = new HashMap();

    /**
     * Creates a new Brain
     */
    public DroolsEngine() {
        idGen = new UUIDIdentifierGenerator();

        rulesManager = new RulesManager();
        factsManager = new FactsManager();
    }

    /**
     * Adds a <code>RuleSet</code> to the <code>Brain</code>
     *
     * @param ruleSet the <code>RuleSet</code> to add to the <code>Brain</code>
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the name of the <code>RuleSet</code> is blank or null.
     */
    public void addRuleSet(it.unipr.aotlab.d4j.rule.RuleSet ruleSet) throws DroolsEngineException {
        RuleSet dRuleSet = null;
        try {
            dRuleSet = RuleConverter.convert(ruleSet);
        } catch (Exception e) {
            throw new DroolsEngineException(e);
        }
        addRuleSet(ruleSet);
    }

    /**
     * Adds a <code>RuleSet</code> to the <code>Brain</code>
     *
     * @param ruleSet the <code>RuleSet</code> to add to the <code>Brain</code>
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the name of the <code>RuleSet</code> is blank or null.
     */
    public void addRuleSet(RuleSet ruleSet) throws DroolsEngineException {
        try {
            rulesManager.addRuleSet(ruleSet);
        } catch (RulesManagerException e) {
            throw new DroolsEngineException(e);
        }
    }

    /**
     * Removes the provided <code>RuleSet</code> from the <code>Brain</code>
     *
     * @param ruleSet <code>RuleSet</code> to be removed from the <code>Brain</code>
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the name of the <code>RuleSet</code> is blank or null.
     */
    public void removeRuleSet(RuleSet ruleSet) throws DroolsEngineException {
        try {
            rulesManager.removeRuleSet(ruleSet);
        } catch (Exception e) {
            throw new DroolsEngineException(e);
        }
    }

    /**
     * Removes the provided <code>RuleSet</code> from the <code>Brain</code>.<br>
     *
     * @param name name of the <code>RuleSet</code> to remove from the <code>Brain</code>
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the name of the <code>RuleSet</code> is blank or null.
     */
    public void removeRuleSet(String name) throws DroolsEngineException {
        try {
            rulesManager.removeRuleSet(name);
        } catch (Exception e) {
            throw new DroolsEngineException(e);
        }
    }

    /**
     * Retrieves the <code>RuleSet</code> from the <code>Brain</code>
     * corresponding to the provided name.<br>
     * If no matching name is found <code>null</code> is returned.
     *
     * @param name name of the <code>RuleSet</code> to fetch
     * @return <code>RuleSet</code> from the <code>Brain</code>
     */
    public RuleSet getRuleSet(String name) {
        return rulesManager.getRuleSet(name);
    }

    /**
     * Returns an array containing the <code>Rule</code>s in the <code>Brain</code>
     *
     * @return array containing the <code>Rule</code>s in the <code>Brain</code>
     */
    public RuleSet[] getRuleSets() {
        return rulesManager.getRuleSets();
    }

    /**
     * Asserts a fact into the <code>Brain</code>.<br>
     * The method returns a <code>String</code> which identifies
     * uniquely the fact in the <code>Brain</code>. This is the code
     * that must be provided to remove or modify the object.
     *
     * @param object fact to be asserted in the <code>Brain</code>
     * @return identifier of the fact asserted
     * @throws DroolsEngineException exception thrown if it is not possible
     *                        to assert the fact
     */
    public String assertFact(Object object) throws DroolsEngineException {
        return assertFact(null, object);
    }

    /**
     * Asserts a fact into the <code>Brain</code>.<br>
     * The method returns a <code>String</code> which identifies
     * uniquely the fact in the <code>Brain</code>. This is the code
     * that must be provided to remove or modify the object.
     *
     * @param object fact to be asserted in the <code>Brain</code>
     * @return identifier of the fact asserted
     * @throws DroolsEngineException exception thrown if it is not possible
     *                        to assert the fact
     */
    public String assertFact(String id, Object object) throws DroolsEngineException {
        if (id == null) {
            id = idGen.getIdentifier();
        }
        if (isReady()) {
            try {

                FactHandle fh = wm.assertObject(object);
                factsManager.putFact(id, object);
                factsManager.putHandle(id, fh);

            } catch (FactException e) {
                throw new DroolsEngineException(e);
            }
        } else {
            throw new DroolsEngineException("The Brain has not been created yet");
        }
        return id;
    }

    /**
     * Modifies the fact corresponding to the provided id with the object passed
     * as a parameter.
     *
     * @param id     identifier of the object that must be modified
     * @param object new reference of the object
     * @throws DroolsEngineException exception thrown if it is not possible
     *                        to assert the fact
     */
    public void modifyFact(String id, Object object) throws DroolsEngineException {

        if (isReady()) {
            try {

                FactHandle handle = factsManager.getHandle(id);
                if (handle != null) {
                    wm.modifyObject(handle, object);
                    factsManager.putFact(id, object);
                }

            } catch (FactException e) {
                throw new DroolsEngineException(e);
            }
        } else {
            throw new DroolsEngineException("The Brain has not been created yet");
        }
    }

    /**
     * Removes from the <code>Brain</code> the fact that matches the provided id
     *
     * @param id identifier of the fact that needs to be removed.
     * @throws DroolsEngineException exception thrown if it is not possible
     *                        to retract the fact
     */
    public void retractFact(String id) throws DroolsEngineException {

        if (isReady()) {
            try {
                FactHandle handle = factsManager.getHandle(id);

                if (handle != null) {
                    wm.retractObject(handle);
                    factsManager.removeFact(id);
                    factsManager.removeHandle(id);
                }
            } catch (FactException e) {
                throw new DroolsEngineException(e);
            }
        } else {
            throw new DroolsEngineException("The Brain has not been created yet");
        }
    }

    /**
     * @throws DroolsEngineException
     */
    private void createWorkingMemory() throws DroolsEngineException {
        RuleBaseBuilder ruleBaseBuilder = new RuleBaseBuilder();
        if (conflictResolver != null) {
            ruleBaseBuilder.setConflictResolver(conflictResolver);
        }

        RuleSet[] ruleSets = rulesManager.getRuleSets();
        for (int i = 0; i < ruleSets.length; i++) {
            try {
                ruleBaseBuilder.addRuleSet(ruleSets[i]);
            } catch (Exception e) {
                throw new DroolsEngineException(e);
            }
        }

        RuleBase ruleBase = ruleBaseBuilder.build();
        wm = ruleBase.newWorkingMemory();
    }

    /**
     * @param preserveBrainData
     * @param reAssertFacts
     * @throws DroolsEngineException
     */
    public void update(boolean preserveBrainData, boolean reAssertFacts) throws DroolsEngineException {

        createWorkingMemory();

        for (int i = 0; i < listeners.size(); i++) {
            System.out.println("adding listener");
            wm.addEventListener((WorkingMemoryEventListener) listeners.get(i));
        }

        if (preserveBrainData) {
            Set nameSet = data.keySet();
            for (Iterator i = nameSet.iterator(); i.hasNext();) {
                String name = (String) i.next();
                wm.setApplicationData(name, data.get(name));
            }
        }
        else {
            data.clear();
        }

        if (reAssertFacts) {
            String[] ids = factsManager.getIds();

            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                FactHandle oldHandle = factsManager.removeHandle(id);
                Object obj = factsManager.removeFact(id);

                FactHandle newHandle = null;
                try {
                    newHandle = wm.assertObject(obj);
                    factsManager.putFact(id, obj);
                    factsManager.putHandle(id, newHandle);
                } catch (FactException e) {
                    new DroolsEngineException(e);
                }
            }

        }
        else {
            factsManager.clearFacts();
        }
    }

    /**
     * set the conflict resolver for the rule base
     * @param conflictResolver
     */
    public void setConflictResolver(ConflictResolver conflictResolver) {
        this.conflictResolver = conflictResolver;
    }

    /**
     * set the agenda filter for the rule base
     * @param agendaFilter
     */
    public void setAgendaFilter(AgendaFilter agendaFilter) {
        this.agendaFilter = agendaFilter;
    }

    /**
     * add event listener to listeners ArrayList
     * @param listener
     */
    public void addEventListener(WorkingMemoryEventListener listener) {
        if (! listeners.contains(listener)) {
            listeners.add(listener);
            if (isReady()) {
                wm.addEventListener(listener);
            }
        }
    }

    /**
     * remove event listener from listeners ArrayList
     * @param listener
     */
    public void removeEventListener(WorkingMemoryEventListener listener) {
        listeners.remove(listener);
        if (isReady()) {
            wm.removeEventListener(listener);
        }
    }

    /**
     * Returns a list of listeners
     * @return listeners
     */
    public List getListeners() {
        return listeners;
    }

    /**
     * Sets application data into the <code>Brain</code>. This is an object
     * that can be used into the <code>Rule</code> (i.e. in the Consequence
     * part) without having to assert it as a fact.
     *
     * @param name the name of the object used to reference it in the <code>Rule</code>
     * @param data the object to be used in the Brain
     * @throws DroolsEngineException exception thrown if something goes wrong.
     */
    public void setApplicationData(String name, Object data) throws DroolsEngineException {
        this.data.put(name, data);
        if (isReady()) {
            wm.setApplicationData(name, data);
        } else {
            throw new DroolsEngineException("The Brain has not been created yet");
        }
    }

    /**
     * Retrieves a reference to an object used into the <code>Brain</code>.
     *
     * @param name name used to refer to the object into the <code>Brain</code>
     * @return reference to the object
     * @throws DroolsEngineException exception thrown if something goes wrong.
     */
    public Object getApplicationData(String name) throws DroolsEngineException {
        if (isReady()) {
            return wm.getApplicationData(name);
        } else {
            return data.get(name);
        }
    }

    /**
     * Tests if the <code>Brain</code> is in a defined and consistent state.
     *
     * @return <code>true</code> if the brain is in a defined and consistent state.
     *         Please note that this does not mean that the <code>Brain>/code> has
     *         finished firing the rules.
     */
    public boolean isReady() {
        return (wm != null);
    }

    /**
     * Tells the <code>Brain</code> that is time to meditate. In this
     * process all the rules that have been activated in the fact
     * manipulation process are fired.
     *
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the Brain is in an undefined state)
     */
    public void meditate() throws DroolsEngineException {

        if (isReady()) {
            try {
                if (agendaFilter != null) {
                    wm.fireAllRules(agendaFilter);
                }
                else {
                    wm.fireAllRules();
                }
            } catch (FactException e) {
                throw new DroolsEngineException(e);
            }
        } else {
            throw new DroolsEngineException("The Brain has not been created yet");
        }
    }

    /**
     * Clears everything contained in the <code>Brain</code> (i.e. facts,
     * rules, application data) rebuilding it from scratch.
     *
     * @throws DroolsEngineException exception thrown if something goes wrong (i.e.
     *                        the Brain is in an undefined state).
     */
    public void clear() throws DroolsEngineException {

        factsManager.clearFacts();
        rulesManager.clearRuleSets();

        conflictResolver = null;
        agendaFilter = null;
        listeners.clear();
        data.clear();

        createWorkingMemory();
    }

    /**
     * Creates an empty <code>Brain</code>
     *
     * @throws DroolsEngineException
     */
    public void create() throws DroolsEngineException {
        createWorkingMemory();
    }
}
