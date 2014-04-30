package it.unipr.aotlab.d4j.rule;

import jade.content.AgentAction;

import java.io.Serializable;

/**
 * @author <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RemoveRuleSet.java,v 1.1 2004/10/01 18:39:38 mic Exp $
 */
public class RemoveRuleSet implements AgentAction, Serializable {

    private String ruleSetName = null;

    public RemoveRuleSet() {
        /* Intentionally left blank */
    }

    public String getRuleSetName() {
        return ruleSetName;
    }

    public void setRuleSetName(String ruleSetName) {
        this.ruleSetName = ruleSetName;
    }
}
