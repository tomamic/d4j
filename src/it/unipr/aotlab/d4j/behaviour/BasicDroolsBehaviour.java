/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 Università degli Studi di Parma - 2003

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

package it.unipr.aotlab.d4j.behaviour;

import it.unipr.aotlab.d4j.core.DroolsEngine;
import it.unipr.aotlab.d4j.core.DroolsEngineException;
import it.unipr.aotlab.d4j.util.rule.RuleConverter;
import it.unipr.aotlab.d4j.util.id.IdentifierGenerator;
import it.unipr.aotlab.d4j.util.id.UUIDIdentifierGenerator;

import org.drools.event.WorkingMemoryEventListener;
import org.drools.rule.RuleSet;
import org.drools.spi.AgendaFilter;
import org.drools.spi.ConflictResolver;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class models an abstract Drools-based behavior for Jade.<br>
 * The class is defined <tt>abstract</tt> because it does not implement
 * the <tt>done()</tt> method. The programmer willing to create a custom
 * Drools-based behaviour must extend this class and implement it.</p>
 * <p/>
 * <p>As the constructor executes, it creates the environment suitable for
 * the knowledge work. This task is achieved by creating a <tt>RuleBase</tt>,
 * a container for the rules loaded from a <tt>.drl</tt> file or created
 * on-the-fly, and a <tt>WorkingMemory</tt> which is the field in which the
 * knowledge work will take place.</p>
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: BasicDroolsBehaviour.java,v 1.10 2004/10/03 14:39:08 a_beneventi Exp $
 */
public abstract class BasicDroolsBehaviour extends Behaviour {

    private static Logger logger = Logger.getLogger(BasicDroolsBehaviour.class);

    public static final String LOG_CONFIG_FILE = "log4j.properties";

    private DroolsEngine engine = new DroolsEngine();

    /**
     * The Agent owner of this behaviour.
     *
     * @see "jade.core.Agent"
     */
    private Agent myAgent = null;

    /**
     * A fifo queue of the objects that are to be asserted in the <tt>WorkingMemory</tt>.
     */
    private LinkedList workQueue = new LinkedList();

    private boolean ruleSetsUpdated = true;

    private IdentifierGenerator idGen = new UUIDIdentifierGenerator();

    /**
     * A basic Jade Drools-based behaviour is created.
     * <p/>
     * This constructor prepares the working environment without <tt>Rule</tt>s.
     * <p/>
     * <br><br>
     * No data (i.e. Java Objects) is available at the time. Java Objects must be asserted
     * separately by calling <tt>assertFacts()</tt> or <tt>assertFact()</tt> methods.<br>
     * No rule is analyzed until at least one object is asserted.
     *
     * @param agent The Agent owner of the behaviour
     */
    public BasicDroolsBehaviour(Agent agent) {
        super(agent);
        PropertyConfigurator.configure(LOG_CONFIG_FILE);
        this.myAgent = agent;

        // setApplicationData("agent", agent);
        /* next row is needed because some examples are not updated;
           they search for myAgent among facts, not in app-data */
        assertFact(agent);

        logger.debug("Starting the Drools engine without rules or root fact-object");
    }

    /**
     * Adds a new <tt>RuleSet</tt> to the <tt>RuleBase</tt> using
     * provided identity and permissions.
     *
     * @param ruleSet new <tt>RuleSet</tt> to be inserted
     */
    public void addRuleSet(it.unipr.aotlab.d4j.rule.RuleSet ruleSet) throws DroolsEngineException {

        logger.debug("Adding the d4j rule set'" + ruleSet.getName() + "' to the local cache. The rule base must be updated.");

        RuleSet dRuleSet = null;
        try {
            dRuleSet = RuleConverter.convert(ruleSet);
        } catch (Exception e) {
            throw new DroolsEngineException(e);
        }
        addRuleSet(dRuleSet);
    }

    /**
     * Adds a new <tt>RuleSet</tt> to the <tt>RuleBase</tt> using
     * provided identity and permissions.
     *
     * @param ruleSet new <tt>RuleSet</tt> to be inserted
     */
    public void addRuleSet(RuleSet ruleSet) throws DroolsEngineException {
        logger.debug("Adding the rule set '" + ruleSet.getName() + "' to the local cache. The rule base must be updated.");

        engine.addRuleSet(ruleSet);
        ruleSetsUpdated = true;
    }

    /**
     * Removes a <tt>RuleSet</tt> from the <tt>RuleBase</tt>.
     *
     * @param ruleSetName the name of the <tt>RuleSet</tt> to be removed
     */
    public void removeRuleSet(String ruleSetName) throws DroolsEngineException {
        logger.debug("Removing the rule set '" + ruleSetName + "' from the local cache. The rule base must be updated.");

        engine.removeRuleSet(ruleSetName);
        ruleSetsUpdated = true;
    }

    /**
     * set the conflict resolver for the rule base
     * @param conflictResolver
     */
    public void setConflictResolver(ConflictResolver conflictResolver) {
        engine.setConflictResolver(conflictResolver);
    }

    /**
     * set the agenda filter for the rule base
     * @param agendaFilter
     */
    public void setAgendaFilter(AgendaFilter agendaFilter) {
        engine.setAgendaFilter(agendaFilter);
    }

    /**
     * add event listener to listeners ArrayList
     * @param listener
     */
    public void addEventListener(WorkingMemoryEventListener listener) {
        engine.addEventListener(listener);
    }

    /**
     * remove event listener from listeners ArrayList
     * @param listener
     */
    public void removeEventListener(WorkingMemoryEventListener listener) {
        engine.removeEventListener(listener);
    }

    /**
     * Returns a list of listeners
     * @return listeners
     */
    public List getListeners() {
        return engine.getListeners();
    }

    /**
     * The behaviour action is performed by locking the FIFO-like queue of objects
     * that are to be asserted, then each one is asserted in the <tt>WorkingMemory</tt>.
     * <br><br>
     * After being asserted, each object is removed from the queue
     */
    public void action() {

        synchronized (workQueue) {

            if (workQueue.size() == 0) {
                logger.debug("The work queue contains no elements. I'going to block.");
                /* If there is no object to be asserted, this behaviour
                   blocks in order to prevent busy waiting.
                   Wake up is performed by assertFact() and assertFacts().*/
                block();
            }
            else {

                logger.debug("The work queue contains " + workQueue.size() + " elements.");

                if (ruleSetsUpdated) {

                    logger.debug("The rule base must be updated.");

                    try {
                        engine.update(true, true);
                        ruleSetsUpdated = false;
                    }
                    catch (DroolsEngineException e) {
                        e.printStackTrace();
                    }

                    logger.debug("Finished updating the rule base");
                }

                for (int i = 0; i < workQueue.size(); i++) {

                    logger.debug("The work queue contains " + workQueue.size() + " elements.");

                    WorkQueueObject workQueueObject = (WorkQueueObject) workQueue.get(i);
                    workQueueObject.run();
                }

                try {
                    engine.meditate();
                }
                catch (DroolsEngineException e) {
                    e.printStackTrace();
                }

                /* This is why the syncronization is necessary. */
                workQueue.clear();
                logger.debug("The work queue has been cleared");

                onAllRulesFired();
            }
        }
    }

    /**
     * Called after the execution of <tt>DroolsEngine.fireAllRules()</tt>.
     */
    public void onAllRulesFired() {
    }

    /**
     * Inserts the <tt>List</tt> of objects provided as a parameter in the FIFO-like queue
     * of objects that are to be asserted in the <tt>WorkingMemory</tt>.
     * <br><br>
     * Then the behaviour is notified that the work can start.<br>
     * The provided identity and permissions are used.
     *
     * @param facts List of objects to be added to the list of objects to be asserted.
     */
    public void assertFacts(List facts) {

        if (facts != null) {

            boolean wakeUpBehaviour = false;

            /* The application willing to add a bunch of objects to the assertion
               list must first acquire a mutex: this is necessary when the
               computation is in progress and objects might be under removal.
               This might end up in a queue with lack of consistency. */
            synchronized (workQueue) {

                for (int x = 0; x < facts.size(); x++) {
                    Object fact = facts.get(x);

                    wakeUpBehaviour = true;
                    workQueue.add(new WorkQueueAsserter().init(idGen.getIdentifier(), fact, engine));
                }
            }

            /* Notify the behaviour that there is work to do... */
            if (wakeUpBehaviour)
                restart();
        }
    }

    /**
     * Inserts the object provided as a parameter in the FIFO-like queue
     * of objects that are to be asserted in the <tt>WorkingMemory</tt>.
     * <br><br>
     * Then the behaviour is notified that there is new work to do.<br>
     * The provided identity and permissions are used.
     *
     * @param fact fact to be added in the list of facts to be asserted
     * @return Returns the id assigned to the fact.
     */
    public String assertFact(Object fact) {
        return assertFact(null, fact);
    }

    /**
     * Inserts the object provided as a parameter in the FIFO-like queue
     * of objects that are to be asserted in the <tt>WorkingMemory</tt>.
     * <br><br>
     * Then the behaviour is notified that there is new work to do.<br>
     * The provided identity and permissions are used.
     *
     * @param id
     * @param fact fact to be added in the list of facts to be asserted
     * @return Returns the id assigned to the fact.
     */
    public String assertFact(String id, Object fact) {
        if (id == null) id = idGen.getIdentifier();

        if (fact != null) {

            /* The application willing to add an object to the assertion
               list must first acquire a mutex: this is necessary when the
               computation is in progress and objects might be under removal.
               This might end up in a queue with lack of consistency. */
            synchronized (workQueue) {
                workQueue.add(new WorkQueueAsserter().init(id, fact, engine));
            }
            /* Notify the behaviour that there is work to do... */
            restart();
        }
        return id;
    }

    /**
     * Notifies the rule engine that the object passed as a parameter
     * must be modified.<br>
     * The provided identity and permissions are used.
     *
     * @param id
     * @param fact fact to be modified
     */
    public void modifyFact(String id, Object fact) {

        if (fact != null && id != null) {

            /* The application willing to add an object to the assertion
               list must first acquire a mutex: this is necessary when the
               computation is in progress and objects might be under removal.
               This might end up in a queue with lack of consistency. */
            synchronized (workQueue) {
                workQueue.add(new WorkQueueModifier().init(id, fact, engine));
            }
            /* Notify the behaviour that there is work to do... */
            restart();
        }
    }

    /**
     * Notifies the rule engine that the object passed as a parameter
     * must be removed from the <tt>WorkingMemory</tt>.<br>
     * The provided identity and permissions are used.
     *
     * @param fact fact to be retracted
     */
    public void retractFact(String id, Object fact) {

        if (fact != null && id != null) {

            /* The application willing to add an object to the assertion
               list must first acquire a mutex: this is necessary when the
               computation is in progress and objects might be under removal.
               This might end up in a queue with lack of consistency. */
            synchronized (workQueue) {
                workQueue.add(new WorkQueueRetracter().init(id, fact, engine));
            }
            /* Notify the behaviour that there is work to do... */
            restart();
        }
    }

    /**
     * Sets application data into the <code>engine</code>. This is an object
     * that can be used into the <code>Rule</code> (i.e. in the Consequence
     * part) without having to assert it as a fact.
     *
     * @param name the name of the object used to reference it in the <code>Rule</code>
     * @param data the object to be used in the engine
     */
    public void setApplicationData(String name, Object data) {

        if (name != null) {

            synchronized (workQueue) {
                workQueue.add(new WorkQueueAppData().init(name, data, engine));
            }
            /* Notify the behaviour that there is work to do... */
            restart();
        }
    }

    /**
     * Private class that models requests of fact management. These requests must
     * be inserted into the workQueue to be perfomed in the next execution of the
     * action() method.
     */
    private abstract class WorkQueueObject {

        String id = null;
        Object fact = null;
        DroolsEngine engine = null;

        public WorkQueueObject init(String id, Object fact, DroolsEngine engine) {
            this.id = id;
            this.fact = fact;
            this.engine = engine;
            return this;
        }

        public abstract void run();
    }

    private class WorkQueueAsserter extends WorkQueueObject {

        public void run() {
            logger.debug("Asserting the root fact object " + fact.toString());
            try {
                // if id is null, engine assigns one automatically
                engine.assertFact(id, fact);
            } catch (DroolsEngineException e) {
                e.printStackTrace();
            }
        }
    }

    private class WorkQueueModifier extends WorkQueueObject {

        public void run() {
            logger.debug("Modifying the root fact object " + fact.toString());
            try {
                engine.modifyFact(id, fact);
            } catch (DroolsEngineException e) {
                e.printStackTrace();
            }
        }
    }

    private class WorkQueueRetracter extends WorkQueueObject {

        public void run() {
            logger.debug("Retracting the root fact object " + fact.toString());
            try {
                engine.retractFact(id);
            } catch (DroolsEngineException e) {
                e.printStackTrace();
            }
        }
    }

    private class WorkQueueAppData extends WorkQueueObject {

        public void run() {
            logger.debug("Setting application data " + fact.toString());
            try {
                engine.setApplicationData(id, fact);
            } catch (DroolsEngineException e) {
                e.printStackTrace();
            }
        }
    }

}
