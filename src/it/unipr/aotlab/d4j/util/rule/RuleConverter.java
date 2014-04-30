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

import it.unipr.aotlab.d4j.rule.*;

import org.drools.rule.Declaration;
import org.drools.semantics.base.*;
import org.drools.semantics.java.*;
import org.drools.spi.*;

import jade.util.leap.*;

/**
 * This class converts an instance of a <tt>it.unipr.aotlab.d4j.rule.Rule</tt> into
 * an instance of a <tt>org.drools4jade.rule.Rule</tt> object.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: RuleConverter.java,v 1.2 2004/10/03 13:12:39 mic Exp $
 *
 * @see it.unipr.aotlab.d4j.rule.Rule
 * @see org.drools.rule.Rule
 */
public class RuleConverter {

    // drools-rule -> jade-rule
    public static Rule convert(org.drools.rule.Rule dRule) throws ConversionException {
        Rule rule = new Rule();
        rule.setName(dRule.getName());

        // parameters
        rule.setParameters(new ArrayList());
        java.util.List dParameters = dRule.getParameterDeclarations();
        for (int i = 0; i < dParameters.size(); i++) {
            Declaration dParam = (Declaration) dParameters.get(i);
            RuleParameter param = new RuleParameter();

            param.setIdentifier(dParam.getIdentifier());
            param.setType(dParam.getObjectType().toString());

            rule.getParameters().add(param);
        }

        // conditions
        rule.setConditions(new ArrayList());
        java.util.List dConditions = dRule.getConditions();
        for (int i = 0; i < dConditions.size(); i++) {
            Condition dCondition = (Condition) dConditions.get(i);

            String expr = dCondition.toString();
            expr = removePrefix(expr, "[Condition: ");
            expr = removePostfix(expr, "]");

            rule.getConditions().add(expr);
        }

        // consequence
        String consequence = dRule.getConsequence().toString();
        consequence = removePrefix(consequence, "[Consequence: ");
        consequence = removePostfix(consequence, "]");
        rule.setConsequence(consequence);

        // salience
        rule.setSalience(dRule.getSalience());

        // duration
        if (dRule.getDuration() != null) {
            rule.setDuration((int) dRule.getDuration().getDuration(null));
        }
        else {
            rule.setDuration(0);
        }

        return rule;
    }

    // drools-ruleset -> jade-ruleset
    public static RuleSet convert(org.drools.rule.RuleSet dRuleSet) throws ConversionException {
        RuleSet ruleSet = new RuleSet();
        ruleSet.setName(dRuleSet.getName());

        // rules
        List rules = new ArrayList();
        ruleSet.setRules(rules);
        org.drools.rule.Rule[] dRules = dRuleSet.getRules();
        for (int i = 0; i < dRules.length; i++) {
            Rule rule = convert(dRules[i]);
            rules.add(rule);
        }

        return ruleSet;
    }

    // jade-rule -> drools-rule
    public static org.drools.rule.Rule convert(it.unipr.aotlab.d4j.rule.Rule rule, org.drools.rule.RuleSet ruleSet) throws ConversionException {
        try {
            RuleFactory rf = new RuleFactory(rule.getName(), ruleSet);

            // parameters
            if (rule.getParameters() != null) {
                for (Iterator i = rule.getParameters().iterator(); i.hasNext();) {
                    RuleParameter param = (RuleParameter) i.next();

                    Class cls = Class.forName(param.getType());
                    rf.addParameter(param.getIdentifier(), cls);
                }
            }

            // conditions
            if (rule.getConditions() != null) {
                for (Iterator i = rule.getConditions().iterator(); i.hasNext();) {
                    String ruleCondition = (String) i.next();
                    rf.addCondition(ruleCondition);
                }
            }

            // consequence
            rf.setConsequence(rule.getConsequence());

            // salience
            rf.setSalience(rule.getSalience());

            // duration
            int duration = rule.getDuration();
            if (duration > 0) {
                rf.setDuration(duration);
            }

            return rf.getRule();
        }
        catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    // jade-ruleset -> drools-ruleset
    public static org.drools.rule.RuleSet convert(it.unipr.aotlab.d4j.rule.RuleSet ruleSet) throws ConversionException {

        org.drools.rule.RuleSet result = new org.drools.rule.RuleSet(ruleSet.getName());

        List rules = ruleSet.getRules();
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = (Rule) rules.get(i);

            org.drools.rule.Rule dRule = convert(rule, result);

            try {
                result.addRule(dRule);
            }
            catch (Exception e) {
                throw new ConversionException(e);
            }
        }

        return result;
    }

    static String removePrefix(String str, String c) {
        if (str.startsWith(c)) {
            str = str.substring(c.length());
            str = str.trim();
        }
        return str;
    }

    static String removePostfix(String str, String c) {
        if (str.endsWith(c)) {
            str = str.substring(0, str.length() - c.length());
            str = str.trim();
        }
        return str;
    }
}
