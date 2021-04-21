//header
grammar CASC;

@header {
package io.github.chaosunity.antlr;
}

//RULES
compilationUnit : ( variable | print )* EOF;
variable : VARIABLE ID EQUALS value;
print : PRINT '(' ID ')' ;
value : op=NUMBER
      | op=STRING;

//TOKENS
VARIABLE : 'var' | '\u8b8a\u6578' ;
PRINT : 'print' | '\u5370\u51fa' ;
EQUALS : '=' | '\u8ce6' ;
NUMBER : [0-9]+ ;
STRING : '"'.*'"' ;
ID : [a-zA-Z0-9]+ ;
WS: [ \t\n\r]+ -> skip ;