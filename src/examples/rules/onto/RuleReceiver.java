package examples.rules.onto;

import it.unipr.aotlab.d4j.rule.RuleOntology;
import it.unipr.aotlab.d4j.behaviour.CyclicDroolsBehaviour;
import it.unipr.aotlab.d4j.behaviour.HandleActionRequestBehaviour;
import it.unipr.aotlab.d4j.behaviour.MsgAsserterBehaviour;
import it.unipr.aotlab.d4j.rule.RuleVocabulary;

import jade.core.Agent;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleReceiver.java,v 1.2 2004/10/06 23:12:05 mic Exp $
 */
public class RuleReceiver extends Agent {

    protected void setup() {

        super.setup();

        Codec codec = new SLCodec();
        Ontology droolsOntology = RuleOntology.getInstance();

        ContentManager contentManager = getContentManager();
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(droolsOntology);

        CyclicDroolsBehaviour droolsBehaviour = new CyclicDroolsBehaviour(this);
        HandleActionRequestBehaviour handlerBehaviour =
                new HandleActionRequestBehaviour(this, droolsBehaviour);

        /* In order to be asserted the arriving message must match both the
           name of the ontology and the performative. */
        MessageTemplate matchesOntology = MessageTemplate.MatchOntology(RuleVocabulary.NAME);
        MessageTemplate matchesPerformative = MessageTemplate.MatchPerformative(ACLMessage.INFORM);

        MessageTemplate template = MessageTemplate.and(
                matchesPerformative,
                MessageTemplate.not(matchesOntology)
        );

        /* The behaviour that receives the messages and assert them in drools4jade */
        MsgAsserterBehaviour recBehaviour = new MsgAsserterBehaviour(this, droolsBehaviour, template);

        addBehaviour(droolsBehaviour);
        addBehaviour(handlerBehaviour);
        addBehaviour(recBehaviour);
    }
}
