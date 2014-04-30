package it.unipr.aotlab.d4j.rule;

import jade.content.AgentAction;

import java.io.Serializable;

/**
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: AddRuleSet.java,v 1.1 2004/10/01 15:24:07 mic Exp $
 */
public class AddRuleSet implements AgentAction, Serializable{

    private RuleSet ruleSet = null;

    public AddRuleSet() {
        /* Intentionally left blank */
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }
}
