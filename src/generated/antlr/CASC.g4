//header
grammar CASC;

//RULES
compilationUnit                 : moduleDeclaraion? useReference* classDeclaration implDeclaration* EOF? ;
moduleDeclaraion                : MOD qualifiedName ;
useReference                    : USE reference ;
reference                       : qualifiedName (AS ID)?                                #simpleReference
                                | qualifiedName '::' '{' reference (',' reference)* '}' #multiReference
                                ;
classDeclaration                : outerAccessMods? CLASS className primaryConstructor? '{' classBody '}' ;
implDeclaration                 : IMPL qualifiedName superCtor=arguments? ':' className '{' function* '}' ;
primaryConstructor              : ctorAccessMod=innerAccessMods? '('(constructorParameter (',' constructorParameter)*)?')' ;
constructorParameter            : (innerAccessMods? MUT? | PARAM='param') parameter ;
className                       : ID ;
classBody                       : (function | constructor | field | fieldDeclaration)* ;
field                           : innerAccessMods? COMP? MUT? name COLON typeReference (EQUALS expression)? ;
constructor                     : constructorDeclaration block? ;
constructorDeclaration          : innerAccessMods? CTOR parameterSet COLON SELF arguments ;
function                        : functionDeclaration block ;
functionDeclaration             : innerAccessMods? COMP? FN functionName parameterSet (COLON typeReference)? ;
functionName                    : ID ;
parameterSet                    : '('(parameter (',' parameter)*)?')' ;
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
                | useReference ';'?
                | variableDeclaration ';'?
                | assignment ';'?
                | printStatement ';'?
                | printlnStatement ';'?
                | forStatement ';'?
                | returnStatement ';'?
                | ifStatement ';'?
                | expression ';'?
                ;

variableDeclaration     : MUT? name ASSIGN_EQ expression                    ;
assignment              : ref=expression EQUALS toAssign=expression         ;
printStatement          : PRINT '('expression')'                            ;
printlnStatement        : PRINTLN '('expression')'                          ;
returnStatement         : RETURN expression                                 #ReturnWithValue
                        | RETURN                                            #ReturnVoid
                        ;
ifStatement             : IF ('(')? condition=expression (')')? trueStatement=statement (ELSE falseStatement=statement)?;
forStatement            : FOR forExpressions? statement ;
forExpressions          : ('(')? (forRangedExpression | forLoopExpression) (')')? ;
forRangedExpression     : iterator=varReference COLON startExpr=expression arrow=forArrow endExpr=expression ;
forArrow                : '->' | '<-' | '|>' | '<|' ;
forLoopExpression       : initStatement=statement? ';' conditionExpr=expression? ';' postStatement=statement? ;
name                    : ID ;
arguments               : '('argument? (',' argument)*')' ;
argument                : expression
                        | name EQUALS expression ;

expression              : owner=expression '.' functionName arguments                                           #functionCall
                        | qualifiedName '::' functionName arguments                                             #functionCall
                        | functionName arguments                                                                #functionCall
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

varReference        : ID | SELF ;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z')        ;
fragment DIGIT    :  ('0'..'9')                     ;
fragment UNICODE  :  '\u0080'..'\uFFFF'             ;

MOD             : 'mod'                             ;
USE             : 'use'                             ;
CLASS           : 'class'                           ;
IMPL            : 'impl'                            ;
FN              : 'fn'                              ;
CTOR            : 'ctor'                            ;
SELF            : 'self'                            ;
COMP            : 'comp'                            ;
IF              : 'if'                              ;
ELSE            : 'else'                            ;
AS              : 'as'                              ;
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

NUMBER          : ('0'..'9')+ ('L' | 'l' | 'F' | 'f')?          ;
STRING          : '"'~('\r' | '\n' | '"')*'"'               ;
BOOL            : 'true' | 'false'                          ;
NULL            : 'null'                                    ;
ID              : (CHAR|DIGIT|UNICODE|'_')+                 ;
WS              : [ \t\n\r]+ -> skip                        ;