/*****************************************************************
 The AOT Package is a rules engine add-on for Jade created by the
 University of Parma - 2003

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

import it.unipr.aotlab.d4j.core.DroolsEngineException;
import it.unipr.aotlab.d4j.rule.*;
import it.unipr.aotlab.d4j.util.base64.Base64;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Done;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SenderBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.io.IOException;

/**
 * The implementation of the behaviour that handles the incoming requests
 * to perform <tt>Drools</tt> related tasks.<br>
 * In order to be processed by this behaviour the incoming messages must
 * match both the <tt>request</tt> performative and the <tt>drools4jade-rule-management</tt>
 * ontology.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: HandleActionRequestBehaviour.java,v 1.10 2004/10/03 14:48:21 a_beneventi Exp $
 */
public class HandleActionRequestBehaviour extends CyclicBehaviour {

    private Agent ownerAgent = null;
    private ContentManager contentManager = null;
    private BasicDroolsBehaviour droolsBehaviour = null;

    /**
     * Creates a new instance of this behaviour. The parameters needed are a reference
     * to a <tt>BeanShell</tt> based agent and a reference to a <tt>Drools</tt> behaviour.
     * <br>The Reference to the <tt>BeanShell</tt> based is necessary to perform
     * <tt>BeanShell</tt> related tasks and a reference to a <tt>Drools</tt> behaviour is
     * needed to perform <tt>Drools</tt> related tasks.
     *
     * @param agent           a reference to the <tt>BeanShell</tt> based agent
     * @param droolsBehaviour a reference to the <tt>Drools</tt> based behaviour
     */
    public HandleActionRequestBehaviour(Agent agent, BasicDroolsBehaviour droolsBehaviour) {
        super(agent);
        this.ownerAgent = agent;
        this.contentManager = ownerAgent.getContentManager();
        this.droolsBehaviour = droolsBehaviour;
    }

    /**
     * The body of the behavior.
     */
    public void action() {

        /* The incoming message must be a REQUEST in the Drools domain of discourse, i.e.
           it must speak the Drools/Beanshell Ontology language. */
        MessageTemplate matchesOntology = MessageTemplate.MatchOntology(RuleVocabulary.NAME);
        MessageTemplate matchesPerformative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

        /* We are only interested in the messages that both speak the Drools language
          and have the REQUEST performative. */
        ACLMessage msg = ownerAgent.receive(MessageTemplate.and(matchesOntology, matchesPerformative));

        if (msg != null) {

            try {

                ContentElement ce = contentManager.extractContent(msg);
                if (ce instanceof Action) {

                    /* An AgentAction concept object must be wrapped into an Action
                       object to conform to Fipa-SL0 standard. */
                    Action action = (Action) ce;

                    Concept concept = action.getAction();

                    if (concept instanceof AddRuleSet) {
                        /* We are dealing with a request to add a rule set. */
                        AddRuleSet addRuleSet = (AddRuleSet) concept;
                        doAddRuleSet(action, addRuleSet, msg);
                    } else if (concept instanceof RemoveRuleSet) {
                        /* We are dealing with a request to remove a rule set. */
                        RemoveRuleSet removeRuleSet = (RemoveRuleSet) concept;
                        doRemoveRuleSet(action, removeRuleSet, msg);
                    } else if (concept instanceof ManageFact) {
                        ManageFact manageFact = (ManageFact) concept;
                        doManageFact(action, manageFact, msg);
                    } else if (concept instanceof Meditate) {
                        Meditate meditate = (Meditate) concept;
                        doMeditate(action, meditate, msg);
                    }
                }

            } catch (Codec.CodecException e) {
                e.printStackTrace();
            } catch (OntologyException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            /* No message: let's wait for the next message. */
            block();
        }
    }


    /**
     * Adds the rule set to the RuleBase of Drools
     *
     * @param action
     * @param addRuleSet
     * @param msg
     */
    private void doAddRuleSet(Action action, AddRuleSet addRuleSet, ACLMessage msg) {

        System.out.println("I've been asked to add the rule set: " + addRuleSet.getRuleSet().getName());

        try {
            droolsBehaviour.addRuleSet(addRuleSet.getRuleSet());

            /* Let's inform the requester the action has been performed.*/
            sendInformMessage(msg, action, null);

        } catch (DroolsEngineException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Removes the rule set from the RuleBase of Drools
     *
     * @param action
     * @param removeRuleSet
     * @param msg
     */
    private void doRemoveRuleSet(Action action, RemoveRuleSet removeRuleSet, ACLMessage msg) {

        System.out.println("I've been asked to remove the rule set: " + removeRuleSet.getRuleSetName());

        try {
            droolsBehaviour.removeRuleSet(removeRuleSet.getRuleSetName());

            /* Let's inform the requester the action has been performed.*/
            sendInformMessage(msg, action, null);

        } catch (DroolsEngineException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        }
    }

    private void doManageFact(Action action, ManageFact manageFact, ACLMessage msg) {

        try {
            String id = manageFact.getId();
            String encodedFact = manageFact.getFact();
            Object fact = Base64.decode2object(encodedFact);

            List factsIds = null;

            if (manageFact instanceof AssertFact) {
                String factId = droolsBehaviour.assertFact(id, fact);
                factsIds = new ArrayList();
                factsIds.add(factId);

            } else if (manageFact instanceof ModifyFact) {
                droolsBehaviour.modifyFact(id, fact);

            } else if (manageFact instanceof RetractFact) {
                droolsBehaviour.retractFact(id, fact);
            }

            /* Let's inform the requester the action has been performed.*/
            sendInformMessage(msg, action, factsIds);

        } catch (ClassNotFoundException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        }
    }

    private void doMeditate(Action action, Meditate meditate, ACLMessage msg) {

        try {
            List ruleSets = meditate.getRuleSets();

            for (int i = 0; i < ruleSets.size(); i++) {
                RuleSet ruleSet = (RuleSet) ruleSets.get(i);
                droolsBehaviour.addRuleSet(ruleSet);
            }

            List assertedFactsIds = new ArrayList();
            List objects = meditate.getFacts();
            for (int i = 0; i < objects.size(); i++) {
                String encodedFact = (String) objects.get(i);
                Object fact = Base64.decode2object(encodedFact);
                String factId = droolsBehaviour.assertFact(fact);
                assertedFactsIds.add(factId);
                System.out.println("Ass. fact: " + factId);
            }

            /* Let's inform the requester the action has been performed.*/
            sendInformMessage(msg, action, assertedFactsIds);

        } catch (DroolsEngineException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            sendFailureMessage(msg, action);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends an inform-done message telling that the request was fullfilled.
     *
     * @param perfActionMsg
     * @param action
     */
    private void sendInformMessage(ACLMessage perfActionMsg, AgentAction action, List ids) {

        try {
            ACLMessage reply = perfActionMsg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            if (ids == null || (ids != null && ids.size() == 0)) {
                Done done = new Done(action);
                contentManager.fillContent(reply, done);
            } else {
                Result resPredicate = new Result();
                resPredicate.setAction(action);
                resPredicate.setItems(ids);
                contentManager.fillContent(reply, resPredicate);
            }
            ownerAgent.addBehaviour(new SenderBehaviour(ownerAgent, reply));
        } catch (Codec.CodecException e) {
            e.printStackTrace();
        } catch (OntologyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a failure message telling that the request could not be fullfilled.
     *
     * @param perfActionMsg
     * @param action
     */
    private void sendFailureMessage(ACLMessage perfActionMsg, Action action) {

        try {
            ACLMessage failure = perfActionMsg.createReply();
            failure.setPerformative(ACLMessage.FAILURE);
            contentManager.fillContent(failure, action);
            ownerAgent.addBehaviour(new SenderBehaviour(ownerAgent, failure));
        } catch (Codec.CodecException e) {
            e.printStackTrace();
        } catch (OntologyException e) {
            e.printStackTrace();
        }
    }
}