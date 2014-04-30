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

/**
 * The implementation of the vocabulary for the <tt>Drools</tt> domain
 * of discourse.<br><br>
 * <p/>
 * In particular, this vocabulary is necessary to ask another agent to
 * integrate <tt>Rule</tt>s in the <tt>WorkingMemory</tt>.
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 * @version $Id: RuleVocabulary.java,v 1.5 2004/10/06 23:12:05 mic Exp $
 */
public interface RuleVocabulary {

    public static final String NAME = "Drools-Rule-Management";

    public static final String PARAMETER = "parameter";
    public static final String PARAMETER_IDENTIFIER = "identifier";
    public static final String PARAMETER_TYPE = "type";

    public static final String RULE = "rule";
    public static final String RULE_NAME = "name";
    public static final String RULE_SALIENCE = "salience";
    public static final String RULE_PARAMETERS = "parameters";
    public static final String RULE_CONDITIONS = "conditions";
    public static final String RULE_DURATION = "duration";
    public static final String RULE_CONSEQUENCE = "consequence";

    public static final String RULESET = "rule-set";
    public static final String RULESET_NAME = "name";
    public static final String RULESET_RULES = "rules";

    public static final String MANAGEFACT = "manage-fact";
    public static final String ASSERTFACT = "assert-fact";
    public static final String MODIFYFACT = "modify-fact";
    public static final String RETRACTFACT = "retract-fact";

    public static final String MEDITATE = "meditate";
    public static final String MEDITATE_RULESETS = "rule-sets";
    public static final String MEDITATE_FACTS = "facts";

    public static final String ADDRULESET = "add-rule-set";
    public static final String REMOVERULESET = "remove-rule-set";
    public static final String REMOVERULESET_RULESETNAME = "rule-set-name";

    public static final String MANAGEFACT_FACT = "fact";
    public static final String MANAGEFACT_ID = "id";

}
