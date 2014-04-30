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

import it.unipr.aotlab.d4j.rule.*;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author <a href="mailto:a_beneventi@tin.it">Alessandro Beneventi</a>,
 *         <a href="mailto:tomamic@ce.unipr.it@tin.it">Michele Tomaiuolo</a> -
 *         <a href="http://ce.unipr.it">University of Parma</a>
 *
 * @version $Id: RuleBuilderGui.java,v 1.5 2004/10/06 23:12:05 mic Exp $
 */
public class RuleBuilderGui extends JFrame {

    private RuleBuilderAgent ruleBuilderAgent = null;

    private JPanel contentPane;
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jMenuFile = new JMenu();
    private JMenuItem jMenuFileExit = new JMenuItem();
    private JMenu jMenuHelp = new JMenu();
    private JMenuItem jMenuHelpAbout = new JMenuItem();
    private JToolBar jToolBar = new JToolBar();
    private JButton btnNewRule = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JPanel centralPanel = new JPanel();
    private JPanel ruleSetPanel = new JPanel();
    private JLabel lblAvailableRules = new JLabel();
    private JComboBox cmbAvailableRules = new JComboBox();
    private JPanel ruleStructurePanel = new JPanel();
    private JLabel lblRuleSetName = new JLabel();
    private JLabel lblRuleName = new JLabel();
    private JLabel lblRuleSalience = new JLabel();
    private JLabel lblRuleDuration = new JLabel();
    private JLabel lblRulePar = new JLabel();
    private JLabel lblRuleCond = new JLabel();
    private JLabel lblRuleDur = new JLabel();
    private JLabel lblRuleCons = new JLabel();
    private JTextField txtRuleSetName = new JTextField();
    private JTextField txtRuleName = new JTextField();
    private JTextField txtRuleSalience = new JTextField();
    private JTextField txtRuleDuration = new JTextField();
    private JComboBox cmbParameters = new JComboBox();
    private JComboBox cmbConditions = new JComboBox();
    private JScrollPane scrConsequence = new JScrollPane();
    private JTextArea txtConsequence = new JTextArea();
    private JButton btnAddPar = new JButton();
    private JButton btnEditPar = new JButton();
    private JButton btnDelPar = new JButton();
    private JButton btnDelCond = new JButton();
    private JButton btnEditCond = new JButton();
    private JButton btnAddCond = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButton btnOk = new JButton();
    private JButton btnCancel = new JButton();
    private JPanel lowerPanel = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private BorderLayout borderLayout2 = new BorderLayout();

    private JFrame thisFrame = null;
    private Action closeAgentAction = new CloseAgentAction("Close Agent");
    private Action deleteRuleAction = new DeleteRuleAction("Delete Rule...");

    public RuleBuilderGui(RuleBuilderAgent agent) {

        ruleBuilderAgent = agent;
        thisFrame = this;


        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void jbInit() throws Exception {

        cmbAvailableRules.addActionListener(new AvailableRulesActionListener());

        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setResizable(false);
        this.setSize(new Dimension(576, 585));
        this.setTitle("Drools Rule Gui Builder");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        btnNewRule.setToolTipText("Open File");
        jButton2.setToolTipText("Close File");
        jButton3.setToolTipText("Help");
        centralPanel.setLayout(borderLayout2);
        ruleSetPanel.setBorder(null);
        ruleSetPanel.setDebugGraphicsOptions(0);
        ruleSetPanel.setLayout(gridBagLayout1);
        lblAvailableRules.setToolTipText("");
        lblAvailableRules.setText("Available Rules:");
        ruleStructurePanel.setLayout(gridBagLayout3);
        lblRuleSetName.setBorder(null);
        lblRuleSetName.setText("RuleSet Name:");
        lblRuleName.setBorder(null);
        lblRuleName.setText("Rule Name:");
        lblRuleSalience.setBorder(null);
        lblRuleSalience.setText("Rule Salience:");
        lblRuleDuration.setBorder(null);
        lblRuleDuration.setText("Rule Duration:");
        lblRulePar.setBorder(null);
        lblRulePar.setText("Rule Parameters:");
        lblRuleCond.setBorder(null);
        lblRuleCond.setText("Rule Conditions:");
        lblRuleDur.setBorder(null);
        lblRuleDur.setText("Rule Duration:");
        lblRuleCons.setText("Rule Consequence:");
        txtRuleSetName.setText("");
        txtRuleName.setText("");
        txtRuleSalience.setText("0");
        txtRuleDuration.setText("0");
        scrConsequence.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrConsequence.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        txtConsequence.setText("");

        ParameterActionListener parameterActionListener = new ParameterActionListener(this, cmbParameters);

        btnAddPar.setActionCommand("Add Parameter");
        btnAddPar.setText("Add");
        btnAddPar.addActionListener(parameterActionListener);
        btnEditPar.setText("Edit");
        btnEditPar.setActionCommand("Edit Parameter");
        btnEditPar.addActionListener(parameterActionListener);
        btnDelPar.setText("Del");
        btnDelPar.setActionCommand("Del Parameter");
        btnDelPar.addActionListener(parameterActionListener);

        ConditionActionListener conditionActionListener = new ConditionActionListener(this);

        btnDelCond.setText("Del");
        btnDelCond.setActionCommand("Del Condition");
        btnDelCond.addActionListener(conditionActionListener);
        btnEditCond.setText("Edit");
        btnEditCond.setActionCommand("Edit Condition");
        btnEditCond.addActionListener(conditionActionListener);
        btnAddCond.setText("Add");
        btnAddCond.setActionCommand("Add Condition");
        btnAddCond.addActionListener(conditionActionListener);

        contentPane.setDebugGraphicsOptions(0);

        btnOk.setText("Edit");
        btnOk.setActionCommand("Add Rule");
        btnOk.addActionListener(new AddRuleActionListener());

        btnCancel.setText("Clear");
        btnCancel.setActionCommand("Clear Rule");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearPanelContent();
            }
        });


        lowerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        lowerPanel.setLayout(gridBagLayout2);
        lowerPanel.setBorder(null);
        ruleStructurePanel.add(lblRuleName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(18, 7, 8, 0), 50, 0));
        ruleStructurePanel.add(lblRuleSalience, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(18, 7, 8, 0), 50, 0));
        ruleStructurePanel.add(lblRuleDuration, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(18, 7, 8, 0), 50, 0));
        ruleStructurePanel.add(lblRulePar, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(17, 7, 7, 0), 22, 0));
        ruleStructurePanel.add(lblRuleCond, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(22, 7, 8, 0), 27, 0));
        ruleStructurePanel.add(lblRuleCons, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(39, 7, 86, 0), 10, 17));
        ruleStructurePanel.add(txtRuleName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 7, 0, 0), 234, 8));
        ruleStructurePanel.add(txtRuleSalience, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 7, 0, 0), 234, 8));
        ruleStructurePanel.add(txtRuleDuration, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 7, 0, 0), 234, 8));
        ruleStructurePanel.add(cmbParameters, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 7, 0, 0), 214, 8));
        ruleStructurePanel.add(cmbConditions, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(16, 7, 0, 0), 214, 8));
        ruleStructurePanel.add(scrConsequence, new GridBagConstraints(1, 5, 4, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 7, 17, 0), 219, 85));
        ruleStructurePanel.add(btnAddPar, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(13, 8, 0, 0), 0, -1));
        ruleStructurePanel.add(btnEditPar, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(13, 7, 0, 0), 3, -1));
        ruleStructurePanel.add(btnDelPar, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(13, 7, 0, 14), 5, -1));
        ruleStructurePanel.add(btnAddCond, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 9, 6, 0), 0, -1));
        ruleStructurePanel.add(btnEditCond, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 7, 6, 0), 3, -1));
        ruleStructurePanel.add(btnDelCond, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 7, 6, 14), 5, -1));
        scrConsequence.getViewport().add(txtConsequence, null);

        ruleSetPanel.add(lblRuleSetName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 10, 4, 0), 15, 23));
        ruleSetPanel.add(txtRuleSetName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 15, 4, 0), 216, 4));
        ruleSetPanel.add(lblAvailableRules, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 10, 4, 0), 15, 23));
        ruleSetPanel.add(cmbAvailableRules, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 15, 4, 0), 216, 4));


        contentPane.add(lowerPanel, BorderLayout.SOUTH);
        lowerPanel.add(btnOk, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 192, 4, 4), 20, 0));
        lowerPanel.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(18, 27, 22, 219), 0, 0));
        centralPanel.add(ruleStructurePanel, BorderLayout.CENTER);
        centralPanel.add(ruleSetPanel, BorderLayout.NORTH);

        populateMenuAndToolBar();

        contentPane.add(jToolBar, BorderLayout.NORTH);
        contentPane.add(centralPanel, BorderLayout.CENTER);
    }

    private void populateMenuAndToolBar() {

        JMenu jMenu = new JMenu("File");

        OpenFileAction openFileAction = new OpenFileAction(ruleBuilderAgent,
                this, "Open File...");
        JButton button = jToolBar.add(openFileAction);
        button.setText("");
        button.setToolTipText("Open File...");
        JMenuItem menuItem = jMenu.add(openFileAction);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        SaveFileAction saveFileAction = new SaveFileAction(ruleBuilderAgent,
                this, "Save As...");
        button = jToolBar.add(saveFileAction);
        button.setText("");
        button.setToolTipText("Save File...");
        menuItem = jMenu.add(saveFileAction);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        jMenu.addSeparator();
        jMenu.add(closeAgentAction);

        jToolBar.addSeparator();
        jMenuBar.add(jMenu);

        jMenu = new JMenu("Rule");

        NewRuleAction newRuleAction = new NewRuleAction("New Rule");
        button = jToolBar.add(newRuleAction);
        button.setText("");
        button.setToolTipText("New Rule");
        menuItem = jMenu.add(newRuleAction);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        button = jToolBar.add(deleteRuleAction);
        button.setText("");
        button.setToolTipText("Delete Rule...");
        menuItem = jMenu.add(deleteRuleAction);


        DeleteAllRulesAction deleteAllRulesAction = new DeleteAllRulesAction("Delete All Rules...");
        button = jToolBar.add(deleteAllRulesAction);
        button.setText("");
        button.setToolTipText("Delete All Rules...");
        menuItem = jMenu.add(deleteAllRulesAction);
        jMenu.addSeparator();

        jToolBar.addSeparator();

        SendRuleAction sendRuleAction = new SendRuleAction(ruleBuilderAgent,
                this, "Send Rule...");
        button = jToolBar.add(sendRuleAction);
        button.setText("");
        button.setToolTipText("Send Rule...");
        menuItem = jMenu.add(sendRuleAction);

        SendAllRulesAction sendAllRulesAction = new SendAllRulesAction(ruleBuilderAgent,
                this, "Send All Rules...");
        button = jToolBar.add(sendAllRulesAction);
        button.setText("");
        button.setToolTipText("Send All Rules...");
        menuItem = jMenu.add(sendAllRulesAction);

        jMenuBar.add(jMenu);

        Action aboutBoxAction = new AbstractAction("About...") {

            public void actionPerformed(ActionEvent e) {
                AboutDialog aboutDialog = new AboutDialog(thisFrame);

                Dimension dlgSize = aboutDialog.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                aboutDialog.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
                aboutDialog.setModal(true);
                aboutDialog.pack();
                aboutDialog.show();
            }
        };

        jMenu = new JMenu("?");
        jMenu.add(aboutBoxAction);

        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);
    }


    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {

            GuiEvent closeEvent = new GuiEvent(null, RuleBuilderAgent.EXIT_EVENT);
            ruleBuilderAgent.postGuiEvent(closeEvent);
        }
    }

    /**
     * Clears the content of the rule panel.
     */
    protected void clearPanelContent() {
        txtRuleSetName.setText("");
        txtRuleName.setText("");
        txtRuleSalience.setText("0");
        txtRuleDuration.setText("0");
        cmbParameters.removeAllItems();
        //cmbExtractions.removeAllItems();
        cmbConditions.removeAllItems();
        txtConsequence.setText("");
    }


    protected void setRuleSet(RuleSet ruleSet) {
          addRules(ruleSet.getRules(), true);
          txtRuleSetName.setText(ruleSet.getName());
    }

    protected RuleSet getRuleSet() {
          RuleSet ruleSet = new RuleSet();
          ruleSet.setName(txtRuleSetName.getText());
          ruleSet.setRules(getRules());
        return ruleSet;
    }

    protected jade.util.leap.List getRules() {

        jade.util.leap.List rulesList = new jade.util.leap.ArrayList();

        for (int i = 0; i < cmbAvailableRules.getItemCount(); i++) {

            Rule rule = (Rule) cmbAvailableRules.getItemAt(i);
            rulesList.add(rule);
        }

        return rulesList;
    }

    protected void addRules(jade.util.leap.List rules, boolean clearContent) {

        if (clearContent) {
            clearPanelContent();
        }

        cmbAvailableRules.removeAllItems();

        for (int x = 0; x < rules.size(); x++) {
            Rule rule = (Rule) rules.get(x);
            cmbAvailableRules.addItem(rule);
        }
    }

    protected Rule getSelectedRule() {
        return (Rule) cmbAvailableRules.getSelectedItem();
    }


    private class CloseAgentAction extends AbstractAction {

        public CloseAgentAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {

            Object[] options = {"Yes",
                                "No"};
            int answer = JOptionPane.showOptionDialog(thisFrame,
                    "Do you really want to close?", "Closing...",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (answer == JOptionPane.YES_OPTION) {
                GuiEvent closeEvent = new GuiEvent(null, RuleBuilderAgent.EXIT_EVENT);
                ruleBuilderAgent.postGuiEvent(closeEvent);

            }
        }
    }

    private class NewRuleAction extends AbstractAction {

        public NewRuleAction(String name) {
            super(name, GuiProperties.getIcon("NewRuleActionIcon"));
        }

        public void actionPerformed(ActionEvent e) {
            clearPanelContent();
        }
    }

    private class DeleteRuleAction extends AbstractAction {

        public DeleteRuleAction(String name) {
            super(name, GuiProperties.getIcon("DeleteRuleActionIcon"));
        }

        public void actionPerformed(ActionEvent e) {

            int selectedIndex = cmbAvailableRules.getSelectedIndex();

            if (selectedIndex != -1) {

                Object[] options = {"Yes",
                                    "No"};
                int answer = JOptionPane.showOptionDialog(thisFrame,
                        "Do you really want to delete this rule?", "Rule Deletion...",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                if (answer == JOptionPane.YES_OPTION) {
                    cmbAvailableRules.removeItemAt(selectedIndex);
                    clearPanelContent();
                }
            }
        }
    }

    private class DeleteAllRulesAction extends AbstractAction {

        public DeleteAllRulesAction(String name) {
            super(name, GuiProperties.getIcon("DeleteAllRulesActionIcon"));
        }

        public void actionPerformed(ActionEvent e) {

            Object[] options = {"Yes",
                                "No"};
            int answer = JOptionPane.showOptionDialog(thisFrame,
                    "Do you really want to delete all rules?", "Rules Deletion...",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (answer == JOptionPane.YES_OPTION) {
                cmbAvailableRules.removeAllItems();
                clearPanelContent();
            }
        }
    }


    private class AvailableRulesActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Rule selectedRule = (Rule) cmbAvailableRules.getSelectedItem();

            if (selectedRule != null) {

                txtRuleName.setText(selectedRule.getName());
                txtRuleSalience.setText("" + selectedRule.getSalience());
                txtRuleDuration.setText("" + selectedRule.getDuration());

                cmbParameters.removeAllItems();
                jade.util.leap.List parameters = selectedRule.getParameters();
                if (parameters != null) {

                    for (int x = 0; x < parameters.size(); x++) {

                        RuleParameter parameter = (RuleParameter) parameters.get(x);

                        cmbParameters.addItem(parameter);
                    }
                }

                cmbConditions.removeAllItems();
                jade.util.leap.List conditions = selectedRule.getConditions();
                if (conditions != null) {

                    for (int x = 0; x < conditions.size(); x++) {

                        String condition = (String) conditions.get(x);

                        cmbConditions.addItem(condition);
                    }
                }

                txtConsequence.setText(selectedRule.getConsequence());

            }
        }

    }

    private class AddRuleActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String actionCommand = evt.getActionCommand();

            if (actionCommand.startsWith("Add")) {

                Rule rule = new Rule(txtRuleName.getText().trim());

                rule.setConsequence(txtConsequence.getText());
                try {
                    rule.setSalience(Integer.parseInt(txtRuleSalience.getText()));
                } catch (Exception e) {
                    txtRuleSalience.setText("0");
                    rule.setSalience(0);
                }
                try {
                    rule.setDuration(Integer.parseInt(txtRuleDuration.getText()));
                } catch (Exception e) {
                    txtRuleDuration.setText("0");
                    rule.setDuration(0);
                }

                jade.util.leap.List parameters = new jade.util.leap.ArrayList();

                for (int x = 0; x < cmbParameters.getItemCount(); x++) {
                    RuleParameter parameter = (RuleParameter) cmbParameters.getItemAt(x);
                    parameters.add(new RuleParameter(parameter.getIdentifier(), parameter.getType()));
                }
                if (parameters.size() >= 1) {
                    rule.setParameters(parameters);
                }

                jade.util.leap.List conditions = new jade.util.leap.ArrayList();
                for (int x = 0; x < cmbConditions.getItemCount(); x++) {
                    String condition = (String) cmbConditions.getItemAt(x);
                    conditions.add(condition);
                }
                if (conditions.size() >= 1) {
                    rule.setConditions(conditions);
                }

                for (int x = 0; x < cmbAvailableRules.getItemCount(); x++) {

                    Rule containedRule = (Rule) cmbAvailableRules.getItemAt(x);

                    if (containedRule.getName().trim().equalsIgnoreCase(rule.getName().trim())) {
                        cmbAvailableRules.removeItemAt(x);
                    }
                }

                cmbAvailableRules.addItem(rule);
                clearPanelContent();
            }
        }

    }


    private class ParameterActionListener implements ActionListener {

        private JFrame owner = null;
        private JComboBox targetCombo = null;

        public ParameterActionListener(JFrame owner, JComboBox targetCombo) {
            this.owner = owner;
            this.targetCombo = targetCombo;
        }

        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            if (actionCommand.startsWith("Add") || actionCommand.startsWith("Edit")) {

                boolean edit = false;

                if (actionCommand.startsWith("Edit"))
                    edit = true;

                DeclarationDialog declDialog = null;
                int index = -1;

                if (edit) {

                    RuleParameter declaration = (RuleParameter) targetCombo.getSelectedItem();

                    if (declaration != null) {
                        index = targetCombo.getSelectedIndex();
                        declDialog = new DeclarationDialog(owner, "Edit Parameter/Declaration...", declaration);
                    } else {
                        return;
                    }

                } else {
                    RuleParameter parameter = new RuleParameter("", "");
                }

                Dimension dlgSize = declDialog.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                declDialog.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
                declDialog.pack();
                declDialog.show();

                RuleParameter declaration = declDialog.getDeclaration();
                if (declaration != null) {

                    if (edit) {
                        targetCombo.removeItemAt(index);

                    }
                    targetCombo.addItem(declaration);
                }

                declDialog.dispose();

            } else {
                /* Delete Parameter... */
                RuleParameter declaration = (RuleParameter) targetCombo.getSelectedItem();
                targetCombo.removeItem(declaration);
            }

        }

    }

        private class ConditionActionListener implements ActionListener {

        private JFrame owner = null;

        public ConditionActionListener(JFrame owner) {
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {

            String actionCommand = e.getActionCommand();

            if (actionCommand.startsWith("Add") || actionCommand.startsWith("Edit")) {

                boolean edit = false;

                if (actionCommand.startsWith("Edit"))
                    edit = true;

                ConditionDialog conditionDialog = null;
                int index = -1;

                if (edit) {

                    String condition = (String) cmbConditions.getSelectedItem();

                    if (condition != null) {
                        index = cmbConditions.getSelectedIndex();
                        conditionDialog = new ConditionDialog(owner, "Edit Condition...", condition);
                    } else {
                        return;
                    }

                } else {
                    conditionDialog = new ConditionDialog(owner, "Add Condition...");
                }

                Dimension dlgSize = conditionDialog.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                conditionDialog.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
                conditionDialog.pack();
                conditionDialog.show();

                String condition = conditionDialog.getCondition();
                if (condition != null) {

                    if (edit) {
                        cmbConditions.removeItemAt(index);

                    }
                    cmbConditions.addItem(condition);
                }

                conditionDialog.dispose();

            } else {
                String condition = (String) cmbConditions.getSelectedItem();
                cmbConditions.removeItem(condition);
            }
        }
    }


}