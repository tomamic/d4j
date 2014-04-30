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

package it.unipr.aotlab.d4j.rule;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;

/**
 * This class defines the structure of the ontology used in the messages
 * that request the creation of a <tt>Rule</tt> to another agent.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it@tin.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleOntology.java,v 1.6 2004/10/03 13:12:39 mic Exp $
 */
public class RuleOntology extends Ontology implements RuleVocabulary {

    private static Ontology theInstance = new RuleOntology();

    /**
     * The ontology is a singleton pattern implementation. Only one instance is allowed
     * to exist in a certain moment. This method returns a reference to this instance.
     *
     * @return a reference to the single instance.
     */
    public static Ontology getInstance() {
        return theInstance;
    }

    private RuleOntology() {

        super(RuleVocabulary.NAME, BasicOntology.getInstance());

        try {

            ConceptSchema cs = null;

            add(new ConceptSchema(RULESET), RuleSet.class);
            add(new ConceptSchema(RULE), Rule.class);
            add(new ConceptSchema(PARAMETER), RuleParameter.class);

            /* A Rule parameter is made of an identifier and a class name.
               Both informations are mandatory. */
            cs = (ConceptSchema) getSchema(PARAMETER);
            cs.add(PARAMETER_IDENTIFIER, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(PARAMETER_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING));

            /* Now comes the structure of a Rule... */
            cs = (ConceptSchema) getSchema(RULE);
            /* It must have a name... */
            cs.add(RULE_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            /* It must have at least one parameter (root fact-object) declaration... */
            cs.add(RULE_PARAMETERS, (ConceptSchema) getSchema(PARAMETER), 1, ConceptSchema.UNLIMITED);
            /* It must have at least one condition declaration... */
            cs.add(RULE_CONDITIONS, (PrimitiveSchema)getSchema(BasicOntology.STRING), 1, ConceptSchema.UNLIMITED);
            /* It must have a consequence. */
            cs.add(RULE_CONSEQUENCE, (PrimitiveSchema) getSchema(BasicOntology.STRING));

            /* It might have a duration... */
            cs.add(RULE_DURATION, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
            /* It might have a duration... */
            cs.add(RULE_SALIENCE, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(RULESET);
            cs.add(RULESET_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(RULESET_RULES, (ConceptSchema) getSchema(RULE), 1, ConceptSchema.UNLIMITED);

            add(new AgentActionSchema(MANAGEFACT), ManageFact.class);
            AgentActionSchema as = (AgentActionSchema) getSchema(MANAGEFACT);
            as.add(MANAGEFACT_ID, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            as.add(MANAGEFACT_FACT, (PrimitiveSchema) getSchema(BasicOntology.STRING));

            add(new AgentActionSchema(ASSERTFACT), AssertFact.class);
            as = (AgentActionSchema) getSchema(ASSERTFACT);
            as.addSuperSchema((ConceptSchema) getSchema(MANAGEFACT));

            add(new AgentActionSchema(MODIFYFACT), ModifyFact.class);
            as = (AgentActionSchema) getSchema(MODIFYFACT);
            as.addSuperSchema((ConceptSchema) getSchema(MANAGEFACT));

            add(new AgentActionSchema(RETRACTFACT), RetractFact.class);
            as = (AgentActionSchema) getSchema(RETRACTFACT);
            as.addSuperSchema((ConceptSchema) getSchema(MANAGEFACT));

            add(new AgentActionSchema(MEDITATE), Meditate.class);
            as = (AgentActionSchema) getSchema(MEDITATE);
            as.add(MEDITATE_RULESETS, (ConceptSchema) getSchema(RULESET), 1, ConceptSchema.UNLIMITED);
            as.add(MEDITATE_FACTS, (PrimitiveSchema) getSchema(BasicOntology.STRING), 1, ConceptSchema.UNLIMITED);

            add(new AgentActionSchema(ADDRULESET), AddRuleSet.class);
            as = (AgentActionSchema) getSchema(ADDRULESET);
            as.add(RULESET, (ConceptSchema) getSchema(RULESET));

            add(new AgentActionSchema(REMOVERULESET), RemoveRuleSet.class);
            as = (AgentActionSchema) getSchema(REMOVERULESET);
            as.add(REMOVERULESET_RULESETNAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));

        } catch (OntologyException e) {
            e.printStackTrace();
        }
    }
}
