package main;

import Types.NodeName;
import parser.Node;
import parser.ParserTreeConstants;
import parser.SimpleNode;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static String getMethodIdentifier(SimpleNode simpleNode) {
        StringBuilder stringBuilder = new StringBuilder();

        Node[] children = simpleNode.jjtGetChildren();

        stringBuilder.append(((SimpleNode) children[1]).jjtGetVal()); // Method Name
        stringBuilder.append("&");

        if (!ParserTreeConstants.jjtNodeName[((SimpleNode) children[2]).getId()].equals(NodeName.ARGS))
            return stringBuilder.toString();

        Node[] grandchildren = ((SimpleNode) children[2]).jjtGetChildren();
        for (Node node : grandchildren) {
            SimpleNode simpleNode1 = (SimpleNode) node;
            stringBuilder.append(((SimpleNode) simpleNode1.jjtGetChildren()[0]).jjtGetVal());
        }

        return stringBuilder.toString();
    }

    public static boolean isClassVariable(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor) throws Exception {
        String nodeName = ParserTreeConstants.jjtNodeName[simpleNode.getId()];

        switch (nodeName) {
            case NodeName.THIS:
                return true;
            case NodeName.IDENTIFIER: {
                TypeDescriptor typeDescriptor = functionDescriptor.getTypeDescriptor(simpleNode.jjtGetVal());
                if (typeDescriptor != null) {
                    String typeIdentifier = typeDescriptor.getTypeIdentifier();
                    return typeIdentifier.equals(symbolTables.getClassName());
                }
                return false;
            }
            case NodeName.NEW: {
                SimpleNode firstChild = (SimpleNode) simpleNode.jjtGetChildren()[0];
                return firstChild.jjtGetVal().equals(symbolTables.getClassName());
            }
            case NodeName.DOTMETHOD: {
                SemanticAnalyser sa = new SemanticAnalyser(symbolTables, simpleNode, true);
                String res = sa.analyseDotMethod(simpleNode, functionDescriptor);
                return res != null && res.equals(symbolTables.getClassName());
            }
        }

        return false;
    }

    public static boolean isArithmeticExpression(SimpleNode simpleNode) {
        switch (ParserTreeConstants.jjtNodeName[simpleNode.getId()]) {
            case NodeName.ADD:
            case NodeName.SUB:
            case NodeName.MUL:
            case NodeName.DIV:
            case NodeName.AND:
            case NodeName.LESS:
            case NodeName.NOT:
                return true;
            default:
                return false;
        }
    }

    public static ImportDescriptor getImportedMethod(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor) throws Exception {
        return getImportedMethod(symbolTables,simpleNode,functionDescriptor, new HashSet<>(), true);
    }

    public static ImportDescriptor getImportedMethod(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor, Set<String> varInitScope) throws Exception {
        return getImportedMethod(symbolTables,simpleNode,functionDescriptor, varInitScope, false);
    }

    public static ImportDescriptor getImportedMethod(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor, Set<String> varInitScope, boolean ignore_init) throws Exception {
        SimpleNode firstChild = (SimpleNode) simpleNode.jjtGetChildren()[0];
        SimpleNode secondChild = (SimpleNode) simpleNode.jjtGetChildren()[1];

        String className = firstChild.jjtGetVal();
        String methodIdentifier = parseMethodIdentifier(symbolTables, secondChild, functionDescriptor,varInitScope);

        ImportDescriptor importDescriptor =  symbolTables.getImportDescriptor(className + "&" + methodIdentifier);

        if (importDescriptor == null) {
            SemanticAnalyser semanticAnalyser = new SemanticAnalyser(symbolTables, null, true);
            className = semanticAnalyser.analyseExpression(firstChild, functionDescriptor, ignore_init, varInitScope);
            importDescriptor = symbolTables.getImportDescriptor(className + "&" + methodIdentifier);
        }

        return importDescriptor;
    }

    public static String parseMethodIdentifier(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor) throws Exception {
        SemanticAnalyser semanticAnalyser = new SemanticAnalyser(symbolTables, null, true);
        return semanticAnalyser.parseMethodIdentifier(simpleNode, functionDescriptor);
    }

    public static String parseMethodIdentifier(SymbolTables symbolTables, SimpleNode simpleNode, FunctionDescriptor functionDescriptor,Set<String> varInitScope) throws Exception {
        SemanticAnalyser semanticAnalyser = new SemanticAnalyser(symbolTables, null, true);
        return semanticAnalyser.parseMethodIdentifier(simpleNode, functionDescriptor,varInitScope);
    }

    public static String getExpressionType(SymbolTables symbolTables, SimpleNode expressionNode, FunctionDescriptor functionDescriptor) throws Exception {
        SemanticAnalyser semanticAnalyser = new SemanticAnalyser(symbolTables, null, true);
        return semanticAnalyser.analyseExpression(expressionNode, functionDescriptor, true);
    }

}