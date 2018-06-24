parser grammar CarbonParser;

options { tokenVocab=CarbonLexer; }

compilationUnit
    : statement*
    ;

typeLiteral
    : '{' (members+=parameter (',' members+=parameter)* ','?) ? '}'
    //| enum_list
    ;

statement
    : declaration ('(' parameters+=parameter (',' parameters+=parameter)* ')')? '=' expression ';'
    ;

expression
    : base=expression '(' arguments+=expression (',' arguments+=expression)* ')' # ApplicationExpression
    | lhs=expression op='*' rhs=expression # InfixExpression // TODO Support arbitrary operations
    | lhs=expression op=('+' | '-') rhs=expression # InfixExpression
    | base=expression '.' identifier # DotExpression
    | terminalExpression # Terminal
    ;

terminalExpression
    : typeLiteral
    | numberLiteral
    | identifier
    ;

numberLiteral: '-'? NUMBER;

//expression
//    : value_expression
//    //| compound_expression
//    ;

//value_expression
//    : term_expression (SYMBOL term_expression)*
//    ;
//
//term_expression
//    : NUMBER
//    | '(' value_expression ')'
//    | identifier
//    | term_expression '.' identifier
//    | term_expression '(' expression_list ')'
//    ;

parameter
    : name=LABEL ':' type
    | type
    ;

declaration
    : LABEL ':' type
    | LABEL
    ;

type
    : identifier ('[' refinement_list ']')?
    ;

refinement_list
    : refinement (',' refinement)* // that must typecheck to bool
    ;

refinement         // What about Size == 5
    : //SYMBOL expression
    | identifier
//    | identifier '(' expression_list ')'
    ;


//compound_expression
//    : '{' statement_list '}'
//    ;

//statement_list
//    : statement*
//    ;

//parameterization
//    : '(' parameter_list ')'
//    ;

//parameter_list
//    : parameter (',' parameter)*
//    ;


identifier
    : LABEL
    | '*' | '-' | '+' // SYMBOL // Is this even an identifier?
    //: LABEL ('.' LABEL)* // Just one level?
    ;