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

package it.unipr.aotlab.d4j.core.rulesmanager;

import org.drools.rule.Rule;
import org.drools.rule.RuleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the <code>RuleSet</code>s used into the Brain/WorkingMemory. This
 * class is necessary when the <code>WorkingMemory</code> needs to be restarted.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RulesManager.java,v 1.2 2004/10/01 18:39:38 mic Exp $
 */
public class RulesManager {

    /* Since the order by which the rules are provided is important for
       the final result I use a List instead of a Map. */
    private List ruleSets = new ArrayList();

    /**
     * Empty <code>RuleSet</code> array.
     */
    private static final RuleSet[] EMPTY_ARRAY = new RuleSet[0];

    /**
     * Adds a <code>RuleSet</code> to the store.
     *
     * @param ruleSet the <code>RuleSet</code> to add
     * @throws RulesManagerException thrown if something is not correct
     */
    public void addRuleSet(RuleSet ruleSet) throws RulesManagerException {

        String ruleSetName = ruleSet.getName();

        if ((ruleSetName == null) ||
                (ruleSetName != null && ruleSetName.length() < 1)) {
            throw new RulesManagerException("Rule set name cannot be null or left blank");
        }

        RuleSet rs = getRuleSet(ruleSetName);

        if (rs == null) {
            ruleSets.add(ruleSet);
        } else {
            int index = ruleSets.indexOf(rs);
            ruleSets.set(index, ruleSet);
        }
    }

    /**
     * Removes the <code>RuleSet</code> with the given name from the store
     *
     * @param name name of the <code>RuleSet</code> to remove.
     */
    public void removeRuleSet(String name) throws RulesManagerException {
        if (name != null) {
            RuleSet rs = getRuleSet(name);
            if (rs != null) {
                ruleSets.remove(rs);
            }
        }
    }

    /**
     * Removes the provided <code>RuleSet</code> from the store
     *
     * @param ruleSet the <code>RuleSet</code> to remove from the store.
     */
    public void removeRuleSet(RuleSet ruleSet) throws RulesManagerException {
        String name = ruleSet.getName();
        removeRuleSet(name);
    }

    /**
     * Returns an array with the <code>RuleSet</code>s in the store.
     *
     * @return an array with the <code>RuleSet</code>s in the store.
     */
    public RuleSet[] getRuleSets() {
        return (RuleSet[]) ruleSets.toArray(EMPTY_ARRAY);
    }

    /**
     * Clears all the <code>RuleSet</code>s in the store.
     */
    public void clearRuleSets() {
        ruleSets.clear();
    }

    /**
     * Searches the rule set database for the one with the name given
     * as a parameter.<br>
     * If no <tt>RuleSet</tt> matching the given name <tt>null</tt> is
     * returned
     *
     * @param name name of the <code>RuleSet</code> to find.
     * @return <code>RuleSet</code> for the given name or null if it
     *         cannot be found.
     */
    public RuleSet getRuleSet(String name) {

        if (name == null) {
            return null;
        }

        RuleSet ruleSet = null;

        for (int i = 0; i < ruleSets.size(); i++) {

            RuleSet set = (RuleSet) ruleSets.get(i);
            if (name.equals(set.getName())) {
                return set;
            }
        }

        return ruleSet;
    }


    /**
     * Generates a debug representation of the store.
     *
     * @return
     */
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        RuleSet[] ruleSets = getRuleSets();

        for (int i = 0; i < ruleSets.length; i++) {

            RuleSet set = ruleSets[i];
            buffer.append("RuleSet named ").append(set.getName()).append("\n");

            Rule[] rules = set.getRules();
            for (int j = 0; j < rules.length; j++) {
                Rule rule = rules[j];
                buffer.append("\t").append("Rule named ").append(rule.getName()).append("\n");
            }
        }

        return buffer.toString();
    }
}
