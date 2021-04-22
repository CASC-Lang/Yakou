//header
grammar CASC;

@header {
package io.github.chaosunity.antlr;
}

//RULES
compilationUnit     : classDeclaration EOF;
classDeclaration    : CLASS className superClassName* '{' classBody '}';
className           : ID;
superClassName      : ':' className;
classBody           : ( variable | print | println)* ;
variable            : VARIABLE ID EQUALS value;
print               : PRINT '(' ID ')' ;
println             : PRINTLN '(' ID ')';
value               : op=NUMBER
                    | op=STRING;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z');
fragment DIGIT    :  ('0'..'9');
fragment UNICODE  :  '\u0080'..'\uFFFF';

CLASS           : 'class' | '\u985e\u5225';             // class, 類別
VARIABLE        : 'var' | '\u8b8a\u6578' ;              // var, 變數
PRINT           : 'print' | '\u5370\u51fa' ;            // print, 印出
PRINTLN         : 'println' | '\u5370\u51fa\u884c';     // println, 印出行
EQUALS          : '=' | '\u8ce6' ;
NUMBER          : [0-9]+ ;
STRING          : '"'.*'"' ;
ID              : (CHAR|DIGIT|UNICODE)+ ;
WS              : [ \t\n\r]+ -> skip ;