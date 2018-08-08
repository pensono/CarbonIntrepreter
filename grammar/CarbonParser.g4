parser grammar CarbonParser;

options { tokenVocab=CarbonLexer; }

compilationUnit
    : statement*
    ;

statement
    // Can this grammar rule be simplified?
    // hasParameterList is a horrible solution to determining if parens were used, but the only one I can think of
    : isReg='reg'? variable_declaration (hasParameterList='(' (parameters+=parameter (',' parameters+=parameter)*)? ')')?
        (guards+=guard)*
        '=' body=expression
    ;

guard : '|' predicate=expression '=' body=expression;

expression
    : typeLiteral # Terminal
    | base=expression '(' first_argument=expression? other_arguments+=expression_item* ')' # ApplicationExpression
    | lhs=expression symbol1 rhs=expression # InfixExpression // Break into two to support prescidence at the syntax level
    | lhs=expression symbol2 rhs=expression # InfixExpression // I would like to make the symbol assigned to a variable 'op', but because the symbol is two seperate syntax nodes, this cannot be done.
    | lhs=expression symbol3 rhs=expression # InfixExpression
    | base=expression '.' identifier # DotExpression
    | terminalExpression # Terminal
    ;

assingment : rvalue=expression ':' '=' lvalue=expression;

expression_item
    : ',' expression
    | ','
    ;

terminalExpression
    : typeLiteral
    | numberLiteral
    | stringLiteral
    | identifier
    ;

typeLiteral
    : '{' ((members+=parameter | derivedMembers+=statement) (',' (members+=parameter | derivedMembers+=statement))* ','?) ? '}' #ProductType
    | enumOption+ #SumType // Like a statement, but not quite. It would be great if those were the same
    ;

enumOption
    : '|' name=LABEL ('=' type)?
    ;

numberLiteral: '-'? NUMBER;

stringLiteral: STRING;

parameter
    : name=LABEL ':' type
    | type
    ;

variable_declaration
    : LABEL ':' type
    | LABEL
    ;

symbol1 : SYMBOL1;
// Exclude =, but not ==
symbol2 : ((SYMBOL2 | ':' | '|' | '-') (SYMBOL1 | SYMBOL2 | ':' | '|' | '-' | '=')*)
        | ('=' (SYMBOL1 | SYMBOL2 | ':' | '|' | '-' | '=')+);

symbol3 : SYMBOL3;

type
    : identifier ('[' refinement_list ']')?
    | typeLiteral
    ;

refinement_list
    : refinement (',' refinement)* // that must typecheck to bool
    ;

refinement         // What about Size == 5
    : //SYMBOL expression
    | identifier
//    | identifier '(' expression_list ')'
    ;


identifier
    : LABEL
    | symbol1 | symbol2 | symbol3 // Is this even an identifier?
    //: LABEL ('.' LABEL)* // Just one level?
    ;