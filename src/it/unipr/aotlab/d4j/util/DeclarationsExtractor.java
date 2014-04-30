package it.unipr.aotlab.d4j.util;


import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import org.drools.rule.Declaration;
import org.drools.semantics.java.MissingDeclarationException;
import org.drools.semantics.java.parser.JavaLexer;
import org.drools.semantics.java.parser.JavaRecognizer;
import org.drools.semantics.java.parser.JavaTreeParser;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Expression analyzer.
 *
 * @author <a href="mailto:bob@eng.werken.com">bob mcwhirter</a>
 * @version $Id: DeclarationsExtractor.java,v 1.1 2004/10/01 09:46:38 mic Exp $
 */
public class DeclarationsExtractor {

    /**
     * Construct.
     */
    public DeclarationsExtractor() {
        // intentionally left blank.
    }

    /**
     * Analyze an expression.
     *
     * @param expr       The expression to analyze.
     * @param availDecls Total set of declarations available.
     * @return The array of declarations used by the expression.
     * @throws TokenStreamException If an error occurs in the lexer.
     * @throws RecognitionException If an error occurs in the parser.
     * @throws org.drools.semantics.java.MissingDeclarationException
     *                              If the expression requires
     *                              a declaration not present in the available declarations.
     */
    public Declaration[] analyze(String expr,
                                 Declaration[] availDecls)
            throws TokenStreamException, RecognitionException, MissingDeclarationException {

        JavaLexer lexer = new JavaLexer(new StringReader(expr));
        JavaRecognizer parser = new JavaRecognizer(lexer);

        parser.ruleCondition();

        AST ast = parser.getAST();

        return analyze(expr,
                availDecls,
                ast);
    }

    /**
     * Analyze an expression.
     *
     * @param expr       The expression to analyze.
     * @param availDecls Total set of declarations available.
     * @param ast        The AST for the expression.
     * @return The array of declarations used by the expression.
     * @throws RecognitionException        If an error occurs in the parser.
     * @throws MissingDeclarationException If the expression requires
     */
    private Declaration[] analyze(String expr,
                                  Declaration[] availDecls,
                                  AST ast) throws RecognitionException, MissingDeclarationException {
        JavaTreeParser treeParser = new JavaTreeParser();

        treeParser.init();

        treeParser.exprCondition(ast);

        Set availDeclSet = new HashSet();

        for (int i = 0; i < availDecls.length; ++i) {
            availDeclSet.add(availDecls[i]);
        }

        Set refs = new HashSet(treeParser.getVariableReferences());

        Set declSet = new HashSet();

        Iterator declIter = availDeclSet.iterator();
        Declaration eachDecl = null;

        while (declIter.hasNext()) {
            eachDecl = (Declaration) declIter.next();

            if (refs.contains(eachDecl.getIdentifier())) {
                declSet.add(eachDecl);
                declIter.remove();
                refs.remove(eachDecl.getIdentifier());
            }
        }

        Declaration[] decls = new Declaration[declSet.size()];

        declIter = declSet.iterator();
        eachDecl = null;

        int i = 0;

        while (declIter.hasNext()) {
            decls[i++] = (Declaration) declIter.next();
        }

        return decls;
    }

    /**
     * A simple static method that uses ANTLR to retrieve the list
     * of variable identifiers from a <tt>Java</tt> statement.<br><br>
     *
     * @param javaCode the <tt>Java</tt> statement code
     * @return a <tt>List</tt> of variable identifiers
     * @throws RecognitionException
     * @throws TokenStreamException
     */
    public static List getIdentifiers(String javaCode)
            throws RecognitionException, TokenStreamException {

        List identifiers = new ArrayList();

        JavaLexer lexer = new JavaLexer(new StringReader(javaCode));
        JavaRecognizer parser = new JavaRecognizer(lexer);

        parser.ruleCondition();

        AST ast = parser.getAST();

        JavaTreeParser treeParser = new JavaTreeParser();

        treeParser.init();

        treeParser.exprCondition(ast);

        java.util.List varRef = treeParser.getVariableReferences();
        for (int x = 0; x < varRef.size(); x++) {
            String ref = (String) varRef.get(x);
            identifiers.add(ref);
        }

        return identifiers;
    }


}
