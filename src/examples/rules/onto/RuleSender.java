package examples.rules.onto;

import it.unipr.aotlab.d4j.rule.*;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import starlight.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleSender.java,v 1.3 2004/10/03 14:47:40 a_beneventi Exp $
 */
public class RuleSender extends Agent {

    protected void setup() {

        Codec codec = new SLCodec();
        Ontology droolsOntology = RuleOntology.getInstance();

        ContentManager contentManager = getContentManager();
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(droolsOntology);

        // First rule...
        Rule rule = new Rule("Lower Rule");

        List parameters = new ArrayList();
        RuleParameter parameter = new RuleParameter("number", "java.lang.Integer");
        parameters.add(parameter);
        rule.setParameters(parameters);

        List conditions = new ArrayList();
        conditions.add("number.intValue() < 10");
        rule.setConditions(conditions);

        rule.setConsequence("System.out.println(\"The number is lower than 10\");");

        List rules = new ArrayList();
        rules.add(rule);

        // Second rule...
        rule = new Rule("Greater on Equal Rule");

        parameters = new ArrayList();
        parameter = new RuleParameter("number", "java.lang.Integer");
        parameters.add(parameter);
        rule.setParameters(parameters);

        conditions = new ArrayList();
        conditions.add("number.intValue() >= 10");
        rule.setConditions(conditions);

        rule.setConsequence("System.out.println(\"The number is equal or greater than 10\");");

        rules.add(rule);

        RuleSet ruleSet = new RuleSet();
        ruleSet.setName("Number Rules");
        ruleSet.setRules(rules);

        List ruleSets = new ArrayList();
        ruleSets.add(ruleSet);


        // Let's define the objects...
        Integer integer = new Integer(5);
        ByteArrayOutputStream baOutStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objOutStream = new ObjectOutputStream(baOutStream);
            objOutStream.writeObject(integer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] charSeqObject = Base64.encode(baOutStream.toByteArray());
        String strObject = new String(charSeqObject);

        List objects = new ArrayList();
        objects.add(strObject);

        Meditate meditate = new Meditate();
        meditate.setRuleSets(ruleSets);
        meditate.setFacts(objects);

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("receiver", AID.ISLOCALNAME));
        msg.addReceiver(new AID("da0", AID.ISLOCALNAME));
        msg.setLanguage(codec.getName());
        msg.setOntology(RuleVocabulary.NAME);

        jade.content.onto.basic.Action meditateObjectAction =
                new jade.content.onto.basic.Action(getAID(), meditate);

        try {

            getContentManager().fillContent(msg, meditateObjectAction);
            send(msg);
        } catch (CodecException ce) {
            ce.printStackTrace();
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }

        ACLMessage replyMsg = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        System.out.println(replyMsg);
    }
}
