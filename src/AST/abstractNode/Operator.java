package AST.abstractNode;

import interpreter.Value;
import type.InferredType;
import type.Tuple;
import type.Type;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Operator extends SyntaxNode{
    protected class Situation extends Type {

        public Situation(){}
        public Situation(Type input) {
            addChild(input);
            addChild(new InferredType());
        }
        public Situation(Type input, Type output) {
            addChild(input);
            addChild(output);
        }

        public String getName(){
            return "situation";
        }
        public SyntaxNode emptyClone() {
            return new Situation();
        }

        public boolean typeEquals(Type o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Situation situation = (Situation) o;
            return Type.typeEquals(situation.getChild(0).getType(), getChild(0).getType()) &&
                    Type.typeEquals(situation.getChild(1).getType(), getChild(1).getType());
        }
    }

    protected class Application {
        public Situation situation;
        public Function<Value, Value> interpret;
        public Function<Type, Type> compile;

        public Application(Situation situation, Function<Value, Value> interpret, Function<Type, Type> compile) {
            this.situation = situation;
            this.interpret = interpret;
            this.compile = compile;
        }
    }

    /**
     * returns a list of applications of the operator in the order of top to bottom
     */
    protected abstract List<Application> getEvaluationList();
    protected Application getEvaluation() {
        Situation situ = new Situation(new Tuple(getChildren()), getType());
        for(Application eval : getEvaluationList()) {
            if(Type.typeEquals(situ, eval.situation))
                return eval;
        }
        throw new Error(errorString("unable to find a matching operator for evaluation"));
    }


    public Value interpret() {
        Application app = getEvaluation();
        return app.interpret.apply(new Tuple(getChildren()).interpret());
    }
}
