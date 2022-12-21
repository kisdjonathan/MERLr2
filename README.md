# MERLr2
## parser.SymbolReader
Reads in a file word by word, where every word holds exactly one piece of information (eg, a value, a suffix, a word, or an operator)
*string and character literals are returned as the quotation mark followed by its value
*numbers are returned as a whole without seperating out suffixes

All comments are taken out of the file by the parser

## lexer.TokenReader
Creates a tree from the words returned by the parser
The subtrees created by the TokenReader holds nodes with immediately recognizable characteristics, 
  ie variables, operators, tuples, function calls, control statements, and literals

*variables created are separate instances and will require more analysis to map them together

## AST.abstractNode.SyntaxNode
Stores the general node of the tree from variables to control statements
Each part should be able to execute
Variables and Functions are stored in Locality nodes

## AST.baseTypes
These are the builtin types
