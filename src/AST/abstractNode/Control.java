package AST.abstractNode;

public class Control extends SyntaxNode{
    public void addChild(String chainName, SyntaxNode condition, SyntaxNode body) {
        switch (chainName) {
            case "else" -> addElse(condition, body);
            case "also" -> addNelse(condition, body);
            case ";else" -> addElse(condition, body);   //TODO
            case ";also" -> addNelse(condition, body); //TODO
            default -> throw new Error("no chain by the name of " + chainName);
        }
    }
}
