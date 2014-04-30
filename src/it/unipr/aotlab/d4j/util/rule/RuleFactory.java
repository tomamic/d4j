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

package it.unipr.aotlab.d4j.util.rule;

import org.drools.rule.*;
import org.drools.semantics.base.*;
import org.drools.semantics.java.*;
import org.drools.smf.*;
import org.drools.spi.*;

import java.util.*;

/**
 * <p>This class is intended to help the programmer to create rules more easily than using
 * the features documented in the Drools manual.<br>
 * By using this helper class the programmer can ignore a lot of details and concentrate
 * upon the rules. Moreover this helper class enables the programmer to create rules at
 * runtime.</p>
 * <p>Before using this helper class some comcepts need to be clarified: please refer to
 * the provided tutorial for the necessary explanations.</p>
 *
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">Università degli Studi di Parma</a>
 *
 * @version $Id: RuleFactory.java,v 1.1 2004/10/01 09:46:38 mic Exp $
 */
public class RuleFactory {
    private static final BaseRuleFactory rf = new org.drools.semantics.base.BaseRuleFactory();
    private static final JavaConditionFactory cf = new JavaConditionFactory();
    private static final JavaBlockConsequenceFactory bf = new JavaBlockConsequenceFactory();

    private Rule rule = null;
    private RuleSet ruleSet = null;
    private RuleBaseContext context = null;

    /**
     * Constructor of the rule. The name provided must be unique within the <tt>RuleSet</tt>
     *
     * @param ruleName name of the rule
     * @param ruleSet ruleSet of the rule
     */
    public RuleFactory(String ruleName, RuleSet ruleSet) throws RuleCreationException {
        try {
            this.ruleSet = ruleSet;
            context = ruleSet.getRuleBaseContext();
            //rule = new Rule(ruleName, ruleSet);

            DefaultConfiguration config = new DefaultConfiguration("conf");
            config.setAttribute("name", ruleName);

            rule = rf.newRule(ruleSet, context, config);

            if (ruleSet.getImporter() == null) {
                ruleSet.setImporter(new DefaultImporter());
            }
            rule.setImporter(ruleSet.getImporter());
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void addParameter(String id, Class type) throws RuleCreationException {
        try {
            ClassObjectType paramType = new ClassObjectType(type);
            rule.addParameterDeclaration(id, paramType);
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void addParameter(String id, String type) throws RuleCreationException {
        try {
            Class cls = Class.forName(type);
            addParameter(id, cls);
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void addCondition(String expression) throws RuleCreationException {
        try {
            DefaultConfiguration config = new DefaultConfiguration("conf");
            config.setText(expression);
            Condition[] condition = cf.newCondition(rule, context, config);
            if (condition.length > 0) {
                rule.addCondition(condition[0]);
            }
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void setConsequence(String script) throws RuleCreationException {
        try {
            DefaultConfiguration config = new DefaultConfiguration("conf");
            config.setText(script);
            Consequence consequence = bf.newConsequence(rule, context, config);
            rule.setConsequence(consequence);
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void setSalience(int salience) throws RuleCreationException {
        try {
            rule.setSalience(salience);
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public void setDuration(int duration) throws RuleCreationException {
        try {
            rule.setDuration(duration);
        }
        catch (Exception e) {
            throw new RuleCreationException(e);
        }
    }

    public Rule getRule() throws RuleCreationException {
        if (! rule.isValid()) {
            /* The consistency of the rule is checked before being returned.
               If there is something wrong, then this exception is thrown.*/
            throw new RuleCreationException(new InvalidRuleException(rule));
        }
        return rule;
    }

    /**
     * Creates a debug string.
     *
     * @return Debug string.
     */
    public String toString() {
        return rule.toString();
    }
}
