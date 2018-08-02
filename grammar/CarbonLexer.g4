lexer grammar CarbonLexer;

// Whitespace and comments

WS:                 [ \t\r\n\u000C]+ -> skip;
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);

// Identifiers

    LABEL:         Letter (Letter | Digit)*;
    NUMBER:        Digit (Letter | Digit)*;
    STRING:        '"' (~["\\\r\n] | '\\n')*? '"';

    SYMBOL1:       '*' | '/'; // To support two levels of precedence
    SYMBOL2:       '+' | '&' | '<' | '>' | ';';

//    GRAMMAR:       GrammarPart;

LEFT_CURLY: '{';
RIGHT_CURLY: '}';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
COMMA: ',';
DOT: '.';
SEMI: ';';
EQUALS: '=';
LEFT_SQUARE: '[';
RIGHT_SQUARE: ']';
COLON: ':';
MINUS: '-';
QUOTE: '"';
PIPE: '|';

// Fragment rules

fragment Digit
    : [0-9]
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;

//fragment SymbolPart // Part of a symbol
//    : [\p{Symbol}^{}.()[]|,:=;]
//    ;

//fragment GrammarPart // Characters used for the language itself
//    : [{}.()[],|:=;]
//    ;