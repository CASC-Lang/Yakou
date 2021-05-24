//header
grammar CASC;

//RULES
compilationUnit                 : classDeclaration EOF? ;
classDeclaration                : outerAccessMods? CLASS className '{' classBody '}' ;
className                       : qualifiedName ;
classBody                       : (function | constructor | field | fieldDeclaration)* ;
field                           : innerAccessMods? COMP? MUT? name COLON typeReference (EQUALS expression)? ;
constructor                     : constructorDeclaration block? ;
constructorDeclaration          : innerAccessMods? CTOR '('(parameter (',' parameter)*)?')' ;
function                        : functionDeclaration block ;
functionDeclaration             : innerAccessMods? COMP? FUNC functionName '('(parameter (',' parameter)*)?')' (COLON typeReference)? ;
functionName                    : ID ;
parameter                       : ID COLON typeReference (EQUALS expression)? ;
type                            : primitiveType
                                | classType
                                ;
typeReference                   : type ('[]')* ;

fieldDeclaration                : innerAccessMods COMP? MUT? COLON field* ;

outerAccessMods                 : (PUB | INTL | PRIV)               ;
innerAccessMods                 : (PUB | PROT | INTL | PRIV)        ;

primitiveType   : TYPES ;

classType       : qualifiedName ;
qualifiedName   : ID ('::' ID)* ;

block           : '{' statement* '}' ;

statement       : block
                | variableDeclaration
                | assignment
                | printStatement
                | printlnStatement
                | forStatement
                | returnStatement
                | ifStatement
                | expression
                ;

variableDeclaration     : MUT? name ASSIGN_EQ expression                    ;
assignment              : expression EQUALS expression                      ;
printStatement          : PRINT '('expression')'                            ;
printlnStatement        : PRINTLN '('expression')'                          ;
returnStatement         : RETURN expression                                 #ReturnWithValue
                        | RETURN                                            #ReturnVoid
                        ;
ifStatement             : IF ('(')? condition=expression (')')? trueStatement=statement (ELSE falseStatement=statement)?;
forStatement            : FOR ('(')? forRangedExpression (')')? statement ;
forRangedExpression     : iterator=varReference COLON startExpr=expression down=DOWN? range=(TO | UNTIL) endExpr=expression ;
name                    : ID ;
argument                : expression
                        | name EQUALS expression ;

expression              : superCall=SELF '('argument? (',' argument)*')'                                        #superCall
                        | owner=expression '.' functionName '('argument? (',' argument)*')'                     #functionCall
                        | qualifiedName '::' functionName '('argument? (',' argument)*')'                       #functionCall
                        | functionName '('argument? (',' argument)*')'                                          #functionCall
                        | className '('argument? (',' argument)*')'                                             #constructorCall
                        | qualifiedName '::' ID                                                                 #fieldCall
                        | owner=expression '.' ID                                                               #fieldCall
                        | NEG=MINUS expression                                                                  #negativeExpression
                        | NEG=EXCLAMATION_MK expression                                                         #negativeExpression
                        | '(' expression ')'                                                                    #wrappedExpression
                        | varReference                                                                          #varRef
                        | expression cmp=GREATER expression                                                     #conditionalExpression
                        | expression cmp=LESS expression                                                        #conditionalExpression
                        | expression cmp=EQ expression                                                          #conditionalExpression
                        | expression cmp=NOT_EQ expression                                                      #conditionalExpression
                        | expression cmp=GREATER_EQ expression                                                  #conditionalExpression
                        | expression cmp=LESS_EQ expression                                                     #conditionalExpression
                        | left=expression cmp=GREATER right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression        #ifExpression
                        | left=expression cmp=LESS right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression           #ifExpression
                        | left=expression cmp=EQ right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression             #ifExpression
                        | left=expression cmp=NOT_EQ right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression         #ifExpression
                        | left=expression cmp=GREATER_EQ right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression     #ifExpression
                        | left=expression cmp=LESS_EQ right=expression QUETION_MK trueExpression=expression COLON falseExpression=expression        #ifExpression
                        | condition=expression QUETION_MK trueExpression=expression COLON falseExpression=expression                                #ifExpression
                        | expression STAR expression                                                            #multiply               // The order of arithmetic expression are related to its actual operator precedence.
                        | expression SLASH expression                                                           #divide
                        | expression PLUS expression                                                            #add
                        | expression MINUS expression                                                           #subtract
                        | referenceExpression=expression '[' indexExpression=expression ']'                     #indexEpxression
                        | type COLON '[' expression ']' ('[' expression ']')*                                   #arrayDeclaration
                        | '{' (expression (',' expression)*)? '}'                                               #arrayInitialization
                        | (NUMBER | BOOL | STRING | NULL)                                                       #value
                        ;

varReference        : ID ;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z')        ;
fragment DIGIT    :  ('0'..'9')                     ;
fragment UNICODE  :  '\u0080'..'\uFFFF'             ;

CLASS           : 'class'                           ;
FUNC            : 'fn'                              ;
CTOR            : 'ctor'                            ;
SELF            : 'self'                            ;
COMP            : 'comp'                            ;
IF              : 'if'                              ;
ELSE            : 'else'                            ;
RETURN          : 'return'                          ;
FOR             : 'for'                             ;
DOWN            : 'down'                            ;
TO              : 'to'                              ;
UNTIL           : 'until'                           ;

//ACCESS MODIFIERS
PUB             : 'pub'                             ;
PROT            : 'prot'                            ;
INTL            : 'intl'                            ;
PRIV            : 'priv'                            ;

//VARIABLE MODIFIER
MUT             : 'mut'                             ;
// no immutable keyword

PRINT           : 'print'                           ;
PRINTLN         : 'println'                         ;

PLUS            : '+'                               ;
MINUS           : '-'                               ;
STAR            : '*'                               ;
SLASH           : '/'                               ;
EQUALS          : '='                               ;
ASSIGN_EQ       : ':='                              ;
QUETION_MK      : '?'                               ;
EXCLAMATION_MK  : '!'                               ;
COLON           : ':'                               ;

GREATER         : '>'                               ;
LESS            : '<'                               ;
GREATER_EQ      : '>='                              ;
LESS_EQ         : '<='                              ;
EQ              : '=='                              ;
NOT_EQ          : '!='                              ;

//types
TYPES           :  'bool'
                |  'str'
                |  'char'
                |  'i8'
                |  'i16'
                |  'i32'
                |  'i64'
                |  'f32'
                |  'f64'
                |  'unit'
                ;

NUMBER          : [0-9.]+ ('L' | 'l' | 'F' | 'f')?          ;
STRING          : '"'~('\r' | '\n' | '"')*'"'               ;
BOOL            : 'true' | 'false'                          ;
NULL            : 'null'                                    ;
ID              : (CHAR|DIGIT|UNICODE|'_')+                 ;
WS              : [ \t\n\r]+ -> skip                        ;