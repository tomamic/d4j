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

package it.unipr.aotlab.d4j.tools.rulebuilder;

import javax.swing.*;

/**
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: GuiProperties.java,v 1.1 2004/10/01 10:03:42 mic Exp $
 */
public class GuiProperties {

    protected static UIDefaults uiDefaults;
    protected static GuiProperties foo = new GuiProperties();
    public static final String ImagePath = "";

    static {
        Object[] icons = {
            "OpenFileActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/open.gif"),
            "NewRuleActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/new.gif"),
            "DeleteRuleActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/delete.gif"),
            "DeleteAllRulesActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/deleteall.gif"),
            "SaveFileActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/save.gif"),
            "SendRuleActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/send.gif"),
            "SendAllRulesActionIcon", LookAndFeel.makeIcon(foo.getClass(), "images/sendall.gif"),
            "PanelIcon", LookAndFeel.makeIcon(foo.getClass(), "images/panel.gif"),
        };

        uiDefaults = new UIDefaults(icons);
    }

    public static final Icon getIcon(String key) {

        Icon i = uiDefaults.getIcon(key);

        if (i == null) {
            System.out.println("Mistake with Icon");
            System.exit(-1);
            return null;
        } else
            return uiDefaults.getIcon(key);
    }
}
