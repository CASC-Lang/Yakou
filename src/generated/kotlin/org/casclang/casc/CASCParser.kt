// Generated from java-escape by ANTLR 4.7.1
package org.casclang.casc;
import com.strumenta.kotlinmultiplatform.asCharArray
import com.strumenta.kotlinmultiplatform.getType
import com.strumenta.kotlinmultiplatform.TypeDeclarator
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.atn.*
import org.antlr.v4.kotlinruntime.atn.ATNDeserializer
import org.antlr.v4.kotlinruntime.atn.ParserATNSimulator
import org.antlr.v4.kotlinruntime.atn.PredictionContextCache
import org.antlr.v4.kotlinruntime.dfa.*
import org.antlr.v4.kotlinruntime.tree.ParseTreeListener
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.antlr.v4.kotlinruntime.atn.ATN.Companion.INVALID_ALT_NUMBER
import org.antlr.v4.kotlinruntime.tree.ParseTreeVisitor
import kotlin.reflect.KClass

class CASCParser(input: TokenStream) : Parser(input) {

    object solver : TypeDeclarator {
        override val classesByName : List<KClass<*>> = listOf(CASCParser.CompilationUnitContext::class,
                                                              CASCParser.ModuleDeclaraionContext::class,
                                                              CASCParser.UseReferenceContext::class,
                                                              CASCParser.ReferenceContext::class,
                                                              CASCParser.ClassDeclarationContext::class,
                                                              CASCParser.PrimaryConstructorContext::class,
                                                              CASCParser.ConstructorParameterContext::class,
                                                              CASCParser.ClassNameContext::class,
                                                              CASCParser.ClassBodyContext::class,
                                                              CASCParser.FieldContext::class,
                                                              CASCParser.ConstructorContext::class,
                                                              CASCParser.ConstructorDeclarationContext::class,
                                                              CASCParser.FunctionContext::class,
                                                              CASCParser.FunctionDeclarationContext::class,
                                                              CASCParser.FunctionNameContext::class,
                                                              CASCParser.ParameterSetContext::class,
                                                              CASCParser.ParameterContext::class,
                                                              CASCParser.TypeContext::class,
                                                              CASCParser.TypeReferenceContext::class,
                                                              CASCParser.FieldDeclarationContext::class,
                                                              CASCParser.OuterAccessModsContext::class,
                                                              CASCParser.InnerAccessModsContext::class,
                                                              CASCParser.PrimitiveTypeContext::class,
                                                              CASCParser.ClassTypeContext::class,
                                                              CASCParser.QualifiedNameContext::class,
                                                              CASCParser.BlockContext::class,
                                                              CASCParser.StatementContext::class,
                                                              CASCParser.VariableDeclarationContext::class,
                                                              CASCParser.AssignmentContext::class,
                                                              CASCParser.PrintStatementContext::class,
                                                              CASCParser.PrintlnStatementContext::class,
                                                              CASCParser.ReturnStatementContext::class,
                                                              CASCParser.IfStatementContext::class,
                                                              CASCParser.ForStatementContext::class,
                                                              CASCParser.ForExpressionsContext::class,
                                                              CASCParser.ForRangedExpressionContext::class,
                                                              CASCParser.ForArrowContext::class,
                                                              CASCParser.ForLoopExpressionContext::class,
                                                              CASCParser.NameContext::class,
                                                              CASCParser.ArgumentContext::class,
                                                              CASCParser.ExpressionContext::class,
                                                              CASCParser.VarReferenceContext::class)
    }

	// TODO verify version of runtime is compatible

    override val grammarFileName: String
        get() = "CASC.g4"

    override val tokenNames: Array<String?>?
        get() = CASCParser.Companion.tokenNames
    override val ruleNames: Array<String>?
        get() = CASCParser.Companion.ruleNames
    override val atn: ATN
        get() = CASCParser.Companion.ATN
    override val vocabulary: Vocabulary
        get() = CASCParser.Companion.VOCABULARY

    enum class Tokens(val id: Int) {
        EOF(-1),
        T__0(1),
        T__1(2),
        T__2(3),
        T__3(4),
        T__4(5),
        T__5(6),
        T__6(7),
        T__7(8),
        T__8(9),
        T__9(10),
        T__10(11),
        T__11(12),
        T__12(13),
        T__13(14),
        T__14(15),
        T__15(16),
        MOD(17),
        USE(18),
        CLASS(19),
        FN(20),
        CTOR(21),
        SELF(22),
        COMP(23),
        IF(24),
        ELSE(25),
        AS(26),
        RETURN(27),
        FOR(28),
        DOWN(29),
        TO(30),
        UNTIL(31),
        PUB(32),
        PROT(33),
        INTL(34),
        PRIV(35),
        MUT(36),
        PRINT(37),
        PRINTLN(38),
        PLUS(39),
        MINUS(40),
        STAR(41),
        SLASH(42),
        EQUALS(43),
        ASSIGN_EQ(44),
        QUETION_MK(45),
        EXCLAMATION_MK(46),
        COLON(47),
        GREATER(48),
        LESS(49),
        GREATER_EQ(50),
        LESS_EQ(51),
        EQ(52),
        NOT_EQ(53),
        TYPES(54),
        NUMBER(55),
        STRING(56),
        BOOL(57),
        NULL(58),
        ID(59),
        WS(60)
    }

    enum class Rules(val id: Int) {
        RULE_compilationUnit(0),
        RULE_moduleDeclaraion(1),
        RULE_useReference(2),
        RULE_reference(3),
        RULE_classDeclaration(4),
        RULE_primaryConstructor(5),
        RULE_constructorParameter(6),
        RULE_className(7),
        RULE_classBody(8),
        RULE_field(9),
        RULE_constructor(10),
        RULE_constructorDeclaration(11),
        RULE_function(12),
        RULE_functionDeclaration(13),
        RULE_functionName(14),
        RULE_parameterSet(15),
        RULE_parameter(16),
        RULE_type(17),
        RULE_typeReference(18),
        RULE_fieldDeclaration(19),
        RULE_outerAccessMods(20),
        RULE_innerAccessMods(21),
        RULE_primitiveType(22),
        RULE_classType(23),
        RULE_qualifiedName(24),
        RULE_block(25),
        RULE_statement(26),
        RULE_variableDeclaration(27),
        RULE_assignment(28),
        RULE_printStatement(29),
        RULE_printlnStatement(30),
        RULE_returnStatement(31),
        RULE_ifStatement(32),
        RULE_forStatement(33),
        RULE_forExpressions(34),
        RULE_forRangedExpression(35),
        RULE_forArrow(36),
        RULE_forLoopExpression(37),
        RULE_name(38),
        RULE_argument(39),
        RULE_expression(40),
        RULE_varReference(41)
    }

	@ThreadLocal
	companion object {
	    protected val decisionToDFA : Array<DFA>
    	protected val sharedContextCache = PredictionContextCache()

        val ruleNames = arrayOf("compilationUnit", "moduleDeclaraion", "useReference", 
                                "reference", "classDeclaration", "primaryConstructor", 
                                "constructorParameter", "className", "classBody", 
                                "field", "constructor", "constructorDeclaration", 
                                "function", "functionDeclaration", "functionName", 
                                "parameterSet", "parameter", "type", "typeReference", 
                                "fieldDeclaration", "outerAccessMods", "innerAccessMods", 
                                "primitiveType", "classType", "qualifiedName", 
                                "block", "statement", "variableDeclaration", 
                                "assignment", "printStatement", "printlnStatement", 
                                "returnStatement", "ifStatement", "forStatement", 
                                "forExpressions", "forRangedExpression", 
                                "forArrow", "forLoopExpression", "name", 
                                "argument", "expression", "varReference")

        private val LITERAL_NAMES: List<String?> = listOf(null, "'::'", 
                                                          "'{'", "','", 
                                                          "'}'", "'('", 
                                                          "')'", "'param'", 
                                                          "'[]'", "';'", 
                                                          "'->'", "'<-'", 
                                                          "'|>'", "'<|'", 
                                                          "'.'", "'['", 
                                                          "']'", "'mod'", 
                                                          "'use'", "'class'", 
                                                          "'fn'", "'ctor'", 
                                                          "'self'", "'comp'", 
                                                          "'if'", "'else'", 
                                                          "'as'", "'return'", 
                                                          "'for'", "'down'", 
                                                          "'to'", "'until'", 
                                                          "'pub'", "'prot'", 
                                                          "'intl'", "'priv'", 
                                                          "'mut'", "'print'", 
                                                          "'println'", "'+'", 
                                                          "'-'", "'*'", 
                                                          "'/'", "'='", 
                                                          "':='", "'?'", 
                                                          "'!'", "':'", 
                                                          "'>'", "'<'", 
                                                          "'>='", "'<='", 
                                                          "'=='", "'!='", 
                                                          null, null, null, 
                                                          null, "'null'")
        private val SYMBOLIC_NAMES: List<String?> = listOf(null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, "MOD", 
                                                           "USE", "CLASS", 
                                                           "FN", "CTOR", 
                                                           "SELF", "COMP", 
                                                           "IF", "ELSE", 
                                                           "AS", "RETURN", 
                                                           "FOR", "DOWN", 
                                                           "TO", "UNTIL", 
                                                           "PUB", "PROT", 
                                                           "INTL", "PRIV", 
                                                           "MUT", "PRINT", 
                                                           "PRINTLN", "PLUS", 
                                                           "MINUS", "STAR", 
                                                           "SLASH", "EQUALS", 
                                                           "ASSIGN_EQ", 
                                                           "QUETION_MK", 
                                                           "EXCLAMATION_MK", 
                                                           "COLON", "GREATER", 
                                                           "LESS", "GREATER_EQ", 
                                                           "LESS_EQ", "EQ", 
                                                           "NOT_EQ", "TYPES", 
                                                           "NUMBER", "STRING", 
                                                           "BOOL", "NULL", 
                                                           "ID", "WS")

        val VOCABULARY = VocabularyImpl(LITERAL_NAMES.toTypedArray(), SYMBOLIC_NAMES.toTypedArray())

        val tokenNames: Array<String?> = Array<String?>(SYMBOLIC_NAMES.size) {
            var el = VOCABULARY.getLiteralName(it)
            if (el == null) {
                el = VOCABULARY.getSymbolicName(it)
            }

            if (el == null) {
                el = "<INVALID>"
            }
            el
        }

        private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0003\u003e\u0266\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0004\u001d\u0009\u001d\u0004\u001e\u0009\u001e\u0004\u001f\u0009\u001f\u0004\u0020\u0009\u0020\u0004\u0021\u0009\u0021\u0004\u0022\u0009\u0022\u0004\u0023\u0009\u0023\u0004\u0024\u0009\u0024\u0004\u0025\u0009\u0025\u0004\u0026\u0009\u0026\u0004\u0027\u0009\u0027\u0004\u0028\u0009\u0028\u0004\u0029\u0009\u0029\u0004\u002a\u0009\u002a\u0004\u002b\u0009\u002b\u0003\u0002\u0005\u0002\u0058\u000a\u0002\u0003\u0002\u0007\u0002\u005b\u000a\u0002\u000c\u0002\u000e\u0002\u005e\u000b\u0002\u0003\u0002\u0003\u0002\u0005\u0002\u0062\u000a\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0005\u0005\u006d\u000a\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005\u0075\u000a\u0005\u000c\u0005\u000e\u0005\u0078\u000b\u0005\u0003\u0005\u0003\u0005\u0005\u0005\u007c\u000a\u0005\u0003\u0006\u0005\u0006\u007f\u000a\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0005\u0006\u0084\u000a\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0005\u0007\u008b\u000a\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0007\u0007\u0091\u000a\u0007\u000c\u0007\u000e\u0007\u0094\u000b\u0007\u0005\u0007\u0096\u000a\u0007\u0003\u0007\u0003\u0007\u0003\u0008\u0005\u0008\u009b\u000a\u0008\u0003\u0008\u0005\u0008\u009e\u000a\u0008\u0003\u0008\u0005\u0008\u00a1\u000a\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0007\u000a\u00ab\u000a\u000a\u000c\u000a\u000e\u000a\u00ae\u000b\u000a\u0003\u000b\u0005\u000b\u00b1\u000a\u000b\u0003\u000b\u0005\u000b\u00b4\u000a\u000b\u0003\u000b\u0005\u000b\u00b7\u000a\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000b\u00be\u000a\u000b\u0003\u000c\u0003\u000c\u0005\u000c\u00c2\u000a\u000c\u0003\u000d\u0005\u000d\u00c5\u000a\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0005\u000d\u00cd\u000a\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00d1\u000a\u000d\u000c\u000d\u000e\u000d\u00d4\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0005\u000f\u00dc\u000a\u000f\u0003\u000f\u0005\u000f\u00df\u000a\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0005\u000f\u00e6\u000a\u000f\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0007\u0011\u00ee\u000a\u0011\u000c\u0011\u000e\u0011\u00f1\u000b\u0011\u0005\u0011\u00f3\u000a\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0005\u0012\u00fc\u000a\u0012\u0003\u0013\u0003\u0013\u0005\u0013\u0100\u000a\u0013\u0003\u0014\u0003\u0014\u0007\u0014\u0104\u000a\u0014\u000c\u0014\u000e\u0014\u0107\u000b\u0014\u0003\u0015\u0003\u0015\u0005\u0015\u010b\u000a\u0015\u0003\u0015\u0005\u0015\u010e\u000a\u0015\u0003\u0015\u0003\u0015\u0007\u0015\u0112\u000a\u0015\u000c\u0015\u000e\u0015\u0115\u000b\u0015\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0007\u001a\u0122\u000a\u001a\u000c\u001a\u000e\u001a\u0125\u000b\u001a\u0003\u001b\u0003\u001b\u0007\u001b\u0129\u000a\u001b\u000c\u001b\u000e\u001b\u012c\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u0133\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u0137\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u013b\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u013f\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u0143\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u0147\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u014b\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u014f\u000a\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u0153\u000a\u001c\u0005\u001c\u0155\u000a\u001c\u0003\u001d\u0005\u001d\u0158\u000a\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u001f\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0020\u0003\u0021\u0003\u0021\u0003\u0021\u0005\u0021\u016f\u000a\u0021\u0003\u0022\u0003\u0022\u0005\u0022\u0173\u000a\u0022\u0003\u0022\u0003\u0022\u0005\u0022\u0177\u000a\u0022\u0003\u0022\u0003\u0022\u0003\u0022\u0005\u0022\u017c\u000a\u0022\u0003\u0023\u0003\u0023\u0005\u0023\u0180\u000a\u0023\u0003\u0023\u0003\u0023\u0003\u0024\u0005\u0024\u0185\u000a\u0024\u0003\u0024\u0003\u0024\u0005\u0024\u0189\u000a\u0024\u0003\u0024\u0005\u0024\u018c\u000a\u0024\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0025\u0003\u0026\u0003\u0026\u0003\u0027\u0005\u0027\u0197\u000a\u0027\u0003\u0027\u0003\u0027\u0005\u0027\u019b\u000a\u0027\u0003\u0027\u0003\u0027\u0005\u0027\u019f\u000a\u0027\u0003\u0028\u0003\u0028\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u0029\u0003\u0029\u0005\u0029\u01a8\u000a\u0029\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0005\u002a\u01b0\u000a\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u01b4\u000a\u002a\u000c\u002a\u000e\u002a\u01b7\u000b\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0005\u002a\u01be\u000a\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u01c2\u000a\u002a\u000c\u002a\u000e\u002a\u01c5\u000b\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u01df\u000a\u002a\u000c\u002a\u000e\u002a\u01e2\u000b\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u01e8\u000a\u002a\u000c\u002a\u000e\u002a\u01eb\u000b\u002a\u0005\u002a\u01ed\u000a\u002a\u0003\u002a\u0003\u002a\u0005\u002a\u01f1\u000a\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0005\u002a\u024c\u000a\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u0250\u000a\u002a\u000c\u002a\u000e\u002a\u0253\u000b\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0003\u002a\u0007\u002a\u025f\u000a\u002a\u000c\u002a\u000e\u002a\u0262\u000b\u002a\u0003\u002b\u0003\u002b\u0003\u002b\u0002\u0003\u0052\u002c\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0038\u003a\u003c\u003e\u0040\u0042\u0044\u0046\u0048\u004a\u004c\u004e\u0050\u0052\u0054\u0002\u0007\u0004\u0002\u0022\u0022\u0024\u0025\u0003\u0002\u0022\u0025\u0003\u0002\u000c\u000f\u0003\u0002\u0039\u003c\u0004\u0002\u0018\u0018\u003d\u003d\u0002\u02a7\u0002\u0057\u0003\u0002\u0002\u0002\u0004\u0063\u0003\u0002\u0002\u0002\u0006\u0066\u0003\u0002\u0002\u0002\u0008\u007b\u0003\u0002\u0002\u0002\u000a\u007e\u0003\u0002\u0002\u0002\u000c\u008a\u0003\u0002\u0002\u0002\u000e\u00a0\u0003\u0002\u0002\u0002\u0010\u00a4\u0003\u0002\u0002\u0002\u0012\u00ac\u0003\u0002\u0002\u0002\u0014\u00b0\u0003\u0002\u0002\u0002\u0016\u00bf\u0003\u0002\u0002\u0002\u0018\u00c4\u0003\u0002\u0002\u0002\u001a\u00d7\u0003\u0002\u0002\u0002\u001c\u00db\u0003\u0002\u0002\u0002\u001e\u00e7\u0003\u0002\u0002\u0002\u0020\u00e9\u0003\u0002\u0002\u0002\u0022\u00f6\u0003\u0002\u0002\u0002\u0024\u00ff\u0003\u0002\u0002\u0002\u0026\u0101\u0003\u0002\u0002\u0002\u0028\u0108\u0003\u0002\u0002\u0002\u002a\u0116\u0003\u0002\u0002\u0002\u002c\u0118\u0003\u0002\u0002\u0002\u002e\u011a\u0003\u0002\u0002\u0002\u0030\u011c\u0003\u0002\u0002\u0002\u0032\u011e\u0003\u0002\u0002\u0002\u0034\u0126\u0003\u0002\u0002\u0002\u0036\u0154\u0003\u0002\u0002\u0002\u0038\u0157\u0003\u0002\u0002\u0002\u003a\u015d\u0003\u0002\u0002\u0002\u003c\u0161\u0003\u0002\u0002\u0002\u003e\u0166\u0003\u0002\u0002\u0002\u0040\u016e\u0003\u0002\u0002\u0002\u0042\u0170\u0003\u0002\u0002\u0002\u0044\u017d\u0003\u0002\u0002\u0002\u0046\u0184\u0003\u0002\u0002\u0002\u0048\u018d\u0003\u0002\u0002\u0002\u004a\u0193\u0003\u0002\u0002\u0002\u004c\u0196\u0003\u0002\u0002\u0002\u004e\u01a0\u0003\u0002\u0002\u0002\u0050\u01a7\u0003\u0002\u0002\u0002\u0052\u01f0\u0003\u0002\u0002\u0002\u0054\u0263\u0003\u0002\u0002\u0002\u0056\u0058\u0005\u0004\u0003\u0002\u0057\u0056\u0003\u0002\u0002\u0002\u0057\u0058\u0003\u0002\u0002\u0002\u0058\u005c\u0003\u0002\u0002\u0002\u0059\u005b\u0005\u0006\u0004\u0002\u005a\u0059\u0003\u0002\u0002\u0002\u005b\u005e\u0003\u0002\u0002\u0002\u005c\u005a\u0003\u0002\u0002\u0002\u005c\u005d\u0003\u0002\u0002\u0002\u005d\u005f\u0003\u0002\u0002\u0002\u005e\u005c\u0003\u0002\u0002\u0002\u005f\u0061\u0005\u000a\u0006\u0002\u0060\u0062\u0007\u0002\u0002\u0003\u0061\u0060\u0003\u0002\u0002\u0002\u0061\u0062\u0003\u0002\u0002\u0002\u0062\u0003\u0003\u0002\u0002\u0002\u0063\u0064\u0007\u0013\u0002\u0002\u0064\u0065\u0005\u0032\u001a\u0002\u0065\u0005\u0003\u0002\u0002\u0002\u0066\u0067\u0007\u0014\u0002\u0002\u0067\u0068\u0005\u0008\u0005\u0002\u0068\u0007\u0003\u0002\u0002\u0002\u0069\u006c\u0005\u0032\u001a\u0002\u006a\u006b\u0007\u001c\u0002\u0002\u006b\u006d\u0007\u003d\u0002\u0002\u006c\u006a\u0003\u0002\u0002\u0002\u006c\u006d\u0003\u0002\u0002\u0002\u006d\u007c\u0003\u0002\u0002\u0002\u006e\u006f\u0005\u0032\u001a\u0002\u006f\u0070\u0007\u0003\u0002\u0002\u0070\u0071\u0007\u0004\u0002\u0002\u0071\u0076\u0005\u0008\u0005\u0002\u0072\u0073\u0007\u0005\u0002\u0002\u0073\u0075\u0005\u0008\u0005\u0002\u0074\u0072\u0003\u0002\u0002\u0002\u0075\u0078\u0003\u0002\u0002\u0002\u0076\u0074\u0003\u0002\u0002\u0002\u0076\u0077\u0003\u0002\u0002\u0002\u0077\u0079\u0003\u0002\u0002\u0002\u0078\u0076\u0003\u0002\u0002\u0002\u0079\u007a\u0007\u0006\u0002\u0002\u007a\u007c\u0003\u0002\u0002\u0002\u007b\u0069\u0003\u0002\u0002\u0002\u007b\u006e\u0003\u0002\u0002\u0002\u007c\u0009\u0003\u0002\u0002\u0002\u007d\u007f\u0005\u002a\u0016\u0002\u007e\u007d\u0003\u0002\u0002\u0002\u007e\u007f\u0003\u0002\u0002\u0002\u007f\u0080\u0003\u0002\u0002\u0002\u0080\u0081\u0007\u0015\u0002\u0002\u0081\u0083\u0005\u0010\u0009\u0002\u0082\u0084\u0005\u000c\u0007\u0002\u0083\u0082\u0003\u0002\u0002\u0002\u0083\u0084\u0003\u0002\u0002\u0002\u0084\u0085\u0003\u0002\u0002\u0002\u0085\u0086\u0007\u0004\u0002\u0002\u0086\u0087\u0005\u0012\u000a\u0002\u0087\u0088\u0007\u0006\u0002\u0002\u0088\u000b\u0003\u0002\u0002\u0002\u0089\u008b\u0005\u002c\u0017\u0002\u008a\u0089\u0003\u0002\u0002\u0002\u008a\u008b\u0003\u0002\u0002\u0002\u008b\u008c\u0003\u0002\u0002\u0002\u008c\u0095\u0007\u0007\u0002\u0002\u008d\u0092\u0005\u000e\u0008\u0002\u008e\u008f\u0007\u0005\u0002\u0002\u008f\u0091\u0005\u000e\u0008\u0002\u0090\u008e\u0003\u0002\u0002\u0002\u0091\u0094\u0003\u0002\u0002\u0002\u0092\u0090\u0003\u0002\u0002\u0002\u0092\u0093\u0003\u0002\u0002\u0002\u0093\u0096\u0003\u0002\u0002\u0002\u0094\u0092\u0003\u0002\u0002\u0002\u0095\u008d\u0003\u0002\u0002\u0002\u0095\u0096\u0003\u0002\u0002\u0002\u0096\u0097\u0003\u0002\u0002\u0002\u0097\u0098\u0007\u0008\u0002\u0002\u0098\u000d\u0003\u0002\u0002\u0002\u0099\u009b\u0005\u002c\u0017\u0002\u009a\u0099\u0003\u0002\u0002\u0002\u009a\u009b\u0003\u0002\u0002\u0002\u009b\u009d\u0003\u0002\u0002\u0002\u009c\u009e\u0007\u0026\u0002\u0002\u009d\u009c\u0003\u0002\u0002\u0002\u009d\u009e\u0003\u0002\u0002\u0002\u009e\u00a1\u0003\u0002\u0002\u0002\u009f\u00a1\u0007\u0009\u0002\u0002\u00a0\u009a\u0003\u0002\u0002\u0002\u00a0\u009f\u0003\u0002\u0002\u0002\u00a1\u00a2\u0003\u0002\u0002\u0002\u00a2\u00a3\u0005\u0022\u0012\u0002\u00a3\u000f\u0003\u0002\u0002\u0002\u00a4\u00a5\u0007\u003d\u0002\u0002\u00a5\u0011\u0003\u0002\u0002\u0002\u00a6\u00ab\u0005\u001a\u000e\u0002\u00a7\u00ab\u0005\u0016\u000c\u0002\u00a8\u00ab\u0005\u0014\u000b\u0002\u00a9\u00ab\u0005\u0028\u0015\u0002\u00aa\u00a6\u0003\u0002\u0002\u0002\u00aa\u00a7\u0003\u0002\u0002\u0002\u00aa\u00a8\u0003\u0002\u0002\u0002\u00aa\u00a9\u0003\u0002\u0002\u0002\u00ab\u00ae\u0003\u0002\u0002\u0002\u00ac\u00aa\u0003\u0002\u0002\u0002\u00ac\u00ad\u0003\u0002\u0002\u0002\u00ad\u0013\u0003\u0002\u0002\u0002\u00ae\u00ac\u0003\u0002\u0002\u0002\u00af\u00b1\u0005\u002c\u0017\u0002\u00b0\u00af\u0003\u0002\u0002\u0002\u00b0\u00b1\u0003\u0002\u0002\u0002\u00b1\u00b3\u0003\u0002\u0002\u0002\u00b2\u00b4\u0007\u0019\u0002\u0002\u00b3\u00b2\u0003\u0002\u0002\u0002\u00b3\u00b4\u0003\u0002\u0002\u0002\u00b4\u00b6\u0003\u0002\u0002\u0002\u00b5\u00b7\u0007\u0026\u0002\u0002\u00b6\u00b5\u0003\u0002\u0002\u0002\u00b6\u00b7\u0003\u0002\u0002\u0002\u00b7\u00b8\u0003\u0002\u0002\u0002\u00b8\u00b9\u0005\u004e\u0028\u0002\u00b9\u00ba\u0007\u0031\u0002\u0002\u00ba\u00bd\u0005\u0026\u0014\u0002\u00bb\u00bc\u0007\u002d\u0002\u0002\u00bc\u00be\u0005\u0052\u002a\u0002\u00bd\u00bb\u0003\u0002\u0002\u0002\u00bd\u00be\u0003\u0002\u0002\u0002\u00be\u0015\u0003\u0002\u0002\u0002\u00bf\u00c1\u0005\u0018\u000d\u0002\u00c0\u00c2\u0005\u0034\u001b\u0002\u00c1\u00c0\u0003\u0002\u0002\u0002\u00c1\u00c2\u0003\u0002\u0002\u0002\u00c2\u0017\u0003\u0002\u0002\u0002\u00c3\u00c5\u0005\u002c\u0017\u0002\u00c4\u00c3\u0003\u0002\u0002\u0002\u00c4\u00c5\u0003\u0002\u0002\u0002\u00c5\u00c6\u0003\u0002\u0002\u0002\u00c6\u00c7\u0007\u0017\u0002\u0002\u00c7\u00c8\u0005\u0020\u0011\u0002\u00c8\u00c9\u0007\u0031\u0002\u0002\u00c9\u00ca\u0007\u0018\u0002\u0002\u00ca\u00cc\u0007\u0007\u0002\u0002\u00cb\u00cd\u0005\u0050\u0029\u0002\u00cc\u00cb\u0003\u0002\u0002\u0002\u00cc\u00cd\u0003\u0002\u0002\u0002\u00cd\u00d2\u0003\u0002\u0002\u0002\u00ce\u00cf\u0007\u0005\u0002\u0002\u00cf\u00d1\u0005\u0050\u0029\u0002\u00d0\u00ce\u0003\u0002\u0002\u0002\u00d1\u00d4\u0003\u0002\u0002\u0002\u00d2\u00d0\u0003\u0002\u0002\u0002\u00d2\u00d3\u0003\u0002\u0002\u0002\u00d3\u00d5\u0003\u0002\u0002\u0002\u00d4\u00d2\u0003\u0002\u0002\u0002\u00d5\u00d6\u0007\u0008\u0002\u0002\u00d6\u0019\u0003\u0002\u0002\u0002\u00d7\u00d8\u0005\u001c\u000f\u0002\u00d8\u00d9\u0005\u0034\u001b\u0002\u00d9\u001b\u0003\u0002\u0002\u0002\u00da\u00dc\u0005\u002c\u0017\u0002\u00db\u00da\u0003\u0002\u0002\u0002\u00db\u00dc\u0003\u0002\u0002\u0002\u00dc\u00de\u0003\u0002\u0002\u0002\u00dd\u00df\u0007\u0019\u0002\u0002\u00de\u00dd\u0003\u0002\u0002\u0002\u00de\u00df\u0003\u0002\u0002\u0002\u00df\u00e0\u0003\u0002\u0002\u0002\u00e0\u00e1\u0007\u0016\u0002\u0002\u00e1\u00e2\u0005\u001e\u0010\u0002\u00e2\u00e5\u0005\u0020\u0011\u0002\u00e3\u00e4\u0007\u0031\u0002\u0002\u00e4\u00e6\u0005\u0026\u0014\u0002\u00e5\u00e3\u0003\u0002\u0002\u0002\u00e5\u00e6\u0003\u0002\u0002\u0002\u00e6\u001d\u0003\u0002\u0002\u0002\u00e7\u00e8\u0007\u003d\u0002\u0002\u00e8\u001f\u0003\u0002\u0002\u0002\u00e9\u00f2\u0007\u0007\u0002\u0002\u00ea\u00ef\u0005\u0022\u0012\u0002\u00eb\u00ec\u0007\u0005\u0002\u0002\u00ec\u00ee\u0005\u0022\u0012\u0002\u00ed\u00eb\u0003\u0002\u0002\u0002\u00ee\u00f1\u0003\u0002\u0002\u0002\u00ef\u00ed\u0003\u0002\u0002\u0002\u00ef\u00f0\u0003\u0002\u0002\u0002\u00f0\u00f3\u0003\u0002\u0002\u0002\u00f1\u00ef\u0003\u0002\u0002\u0002\u00f2\u00ea\u0003\u0002\u0002\u0002\u00f2\u00f3\u0003\u0002\u0002\u0002\u00f3\u00f4\u0003\u0002\u0002\u0002\u00f4\u00f5\u0007\u0008\u0002\u0002\u00f5\u0021\u0003\u0002\u0002\u0002\u00f6\u00f7\u0007\u003d\u0002\u0002\u00f7\u00f8\u0007\u0031\u0002\u0002\u00f8\u00fb\u0005\u0026\u0014\u0002\u00f9\u00fa\u0007\u002d\u0002\u0002\u00fa\u00fc\u0005\u0052\u002a\u0002\u00fb\u00f9\u0003\u0002\u0002\u0002\u00fb\u00fc\u0003\u0002\u0002\u0002\u00fc\u0023\u0003\u0002\u0002\u0002\u00fd\u0100\u0005\u002e\u0018\u0002\u00fe\u0100\u0005\u0030\u0019\u0002\u00ff\u00fd\u0003\u0002\u0002\u0002\u00ff\u00fe\u0003\u0002\u0002\u0002\u0100\u0025\u0003\u0002\u0002\u0002\u0101\u0105\u0005\u0024\u0013\u0002\u0102\u0104\u0007\u000a\u0002\u0002\u0103\u0102\u0003\u0002\u0002\u0002\u0104\u0107\u0003\u0002\u0002\u0002\u0105\u0103\u0003\u0002\u0002\u0002\u0105\u0106\u0003\u0002\u0002\u0002\u0106\u0027\u0003\u0002\u0002\u0002\u0107\u0105\u0003\u0002\u0002\u0002\u0108\u010a\u0005\u002c\u0017\u0002\u0109\u010b\u0007\u0019\u0002\u0002\u010a\u0109\u0003\u0002\u0002\u0002\u010a\u010b\u0003\u0002\u0002\u0002\u010b\u010d\u0003\u0002\u0002\u0002\u010c\u010e\u0007\u0026\u0002\u0002\u010d\u010c\u0003\u0002\u0002\u0002\u010d\u010e\u0003\u0002\u0002\u0002\u010e\u010f\u0003\u0002\u0002\u0002\u010f\u0113\u0007\u0031\u0002\u0002\u0110\u0112\u0005\u0014\u000b\u0002\u0111\u0110\u0003\u0002\u0002\u0002\u0112\u0115\u0003\u0002\u0002\u0002\u0113\u0111\u0003\u0002\u0002\u0002\u0113\u0114\u0003\u0002\u0002\u0002\u0114\u0029\u0003\u0002\u0002\u0002\u0115\u0113\u0003\u0002\u0002\u0002\u0116\u0117\u0009\u0002\u0002\u0002\u0117\u002b\u0003\u0002\u0002\u0002\u0118\u0119\u0009\u0003\u0002\u0002\u0119\u002d\u0003\u0002\u0002\u0002\u011a\u011b\u0007\u0038\u0002\u0002\u011b\u002f\u0003\u0002\u0002\u0002\u011c\u011d\u0005\u0032\u001a\u0002\u011d\u0031\u0003\u0002\u0002\u0002\u011e\u0123\u0007\u003d\u0002\u0002\u011f\u0120\u0007\u0003\u0002\u0002\u0120\u0122\u0007\u003d\u0002\u0002\u0121\u011f\u0003\u0002\u0002\u0002\u0122\u0125\u0003\u0002\u0002\u0002\u0123\u0121\u0003\u0002\u0002\u0002\u0123\u0124\u0003\u0002\u0002\u0002\u0124\u0033\u0003\u0002\u0002\u0002\u0125\u0123\u0003\u0002\u0002\u0002\u0126\u012a\u0007\u0004\u0002\u0002\u0127\u0129\u0005\u0036\u001c\u0002\u0128\u0127\u0003\u0002\u0002\u0002\u0129\u012c\u0003\u0002\u0002\u0002\u012a\u0128\u0003\u0002\u0002\u0002\u012a\u012b\u0003\u0002\u0002\u0002\u012b\u012d\u0003\u0002\u0002\u0002\u012c\u012a\u0003\u0002\u0002\u0002\u012d\u012e\u0007\u0006\u0002\u0002\u012e\u0035\u0003\u0002\u0002\u0002\u012f\u0155\u0005\u0034\u001b\u0002\u0130\u0132\u0005\u0006\u0004\u0002\u0131\u0133\u0007\u000b\u0002\u0002\u0132\u0131\u0003\u0002\u0002\u0002\u0132\u0133\u0003\u0002\u0002\u0002\u0133\u0155\u0003\u0002\u0002\u0002\u0134\u0136\u0005\u0038\u001d\u0002\u0135\u0137\u0007\u000b\u0002\u0002\u0136\u0135\u0003\u0002\u0002\u0002\u0136\u0137\u0003\u0002\u0002\u0002\u0137\u0155\u0003\u0002\u0002\u0002\u0138\u013a\u0005\u003a\u001e\u0002\u0139\u013b\u0007\u000b\u0002\u0002\u013a\u0139\u0003\u0002\u0002\u0002\u013a\u013b\u0003\u0002\u0002\u0002\u013b\u0155\u0003\u0002\u0002\u0002\u013c\u013e\u0005\u003c\u001f\u0002\u013d\u013f\u0007\u000b\u0002\u0002\u013e\u013d\u0003\u0002\u0002\u0002\u013e\u013f\u0003\u0002\u0002\u0002\u013f\u0155\u0003\u0002\u0002\u0002\u0140\u0142\u0005\u003e\u0020\u0002\u0141\u0143\u0007\u000b\u0002\u0002\u0142\u0141\u0003\u0002\u0002\u0002\u0142\u0143\u0003\u0002\u0002\u0002\u0143\u0155\u0003\u0002\u0002\u0002\u0144\u0146\u0005\u0044\u0023\u0002\u0145\u0147\u0007\u000b\u0002\u0002\u0146\u0145\u0003\u0002\u0002\u0002\u0146\u0147\u0003\u0002\u0002\u0002\u0147\u0155\u0003\u0002\u0002\u0002\u0148\u014a\u0005\u0040\u0021\u0002\u0149\u014b\u0007\u000b\u0002\u0002\u014a\u0149\u0003\u0002\u0002\u0002\u014a\u014b\u0003\u0002\u0002\u0002\u014b\u0155\u0003\u0002\u0002\u0002\u014c\u014e\u0005\u0042\u0022\u0002\u014d\u014f\u0007\u000b\u0002\u0002\u014e\u014d\u0003\u0002\u0002\u0002\u014e\u014f\u0003\u0002\u0002\u0002\u014f\u0155\u0003\u0002\u0002\u0002\u0150\u0152\u0005\u0052\u002a\u0002\u0151\u0153\u0007\u000b\u0002\u0002\u0152\u0151\u0003\u0002\u0002\u0002\u0152\u0153\u0003\u0002\u0002\u0002\u0153\u0155\u0003\u0002\u0002\u0002\u0154\u012f\u0003\u0002\u0002\u0002\u0154\u0130\u0003\u0002\u0002\u0002\u0154\u0134\u0003\u0002\u0002\u0002\u0154\u0138\u0003\u0002\u0002\u0002\u0154\u013c\u0003\u0002\u0002\u0002\u0154\u0140\u0003\u0002\u0002\u0002\u0154\u0144\u0003\u0002\u0002\u0002\u0154\u0148\u0003\u0002\u0002\u0002\u0154\u014c\u0003\u0002\u0002\u0002\u0154\u0150\u0003\u0002\u0002\u0002\u0155\u0037\u0003\u0002\u0002\u0002\u0156\u0158\u0007\u0026\u0002\u0002\u0157\u0156\u0003\u0002\u0002\u0002\u0157\u0158\u0003\u0002\u0002\u0002\u0158\u0159\u0003\u0002\u0002\u0002\u0159\u015a\u0005\u004e\u0028\u0002\u015a\u015b\u0007\u002e\u0002\u0002\u015b\u015c\u0005\u0052\u002a\u0002\u015c\u0039\u0003\u0002\u0002\u0002\u015d\u015e\u0005\u0052\u002a\u0002\u015e\u015f\u0007\u002d\u0002\u0002\u015f\u0160\u0005\u0052\u002a\u0002\u0160\u003b\u0003\u0002\u0002\u0002\u0161\u0162\u0007\u0027\u0002\u0002\u0162\u0163\u0007\u0007\u0002\u0002\u0163\u0164\u0005\u0052\u002a\u0002\u0164\u0165\u0007\u0008\u0002\u0002\u0165\u003d\u0003\u0002\u0002\u0002\u0166\u0167\u0007\u0028\u0002\u0002\u0167\u0168\u0007\u0007\u0002\u0002\u0168\u0169\u0005\u0052\u002a\u0002\u0169\u016a\u0007\u0008\u0002\u0002\u016a\u003f\u0003\u0002\u0002\u0002\u016b\u016c\u0007\u001d\u0002\u0002\u016c\u016f\u0005\u0052\u002a\u0002\u016d\u016f\u0007\u001d\u0002\u0002\u016e\u016b\u0003\u0002\u0002\u0002\u016e\u016d\u0003\u0002\u0002\u0002\u016f\u0041\u0003\u0002\u0002\u0002\u0170\u0172\u0007\u001a\u0002\u0002\u0171\u0173\u0007\u0007\u0002\u0002\u0172\u0171\u0003\u0002\u0002\u0002\u0172\u0173\u0003\u0002\u0002\u0002\u0173\u0174\u0003\u0002\u0002\u0002\u0174\u0176\u0005\u0052\u002a\u0002\u0175\u0177\u0007\u0008\u0002\u0002\u0176\u0175\u0003\u0002\u0002\u0002\u0176\u0177\u0003\u0002\u0002\u0002\u0177\u0178\u0003\u0002\u0002\u0002\u0178\u017b\u0005\u0036\u001c\u0002\u0179\u017a\u0007\u001b\u0002\u0002\u017a\u017c\u0005\u0036\u001c\u0002\u017b\u0179\u0003\u0002\u0002\u0002\u017b\u017c\u0003\u0002\u0002\u0002\u017c\u0043\u0003\u0002\u0002\u0002\u017d\u017f\u0007\u001e\u0002\u0002\u017e\u0180\u0005\u0046\u0024\u0002\u017f\u017e\u0003\u0002\u0002\u0002\u017f\u0180\u0003\u0002\u0002\u0002\u0180\u0181\u0003\u0002\u0002\u0002\u0181\u0182\u0005\u0036\u001c\u0002\u0182\u0045\u0003\u0002\u0002\u0002\u0183\u0185\u0007\u0007\u0002\u0002\u0184\u0183\u0003\u0002\u0002\u0002\u0184\u0185\u0003\u0002\u0002\u0002\u0185\u0188\u0003\u0002\u0002\u0002\u0186\u0189\u0005\u0048\u0025\u0002\u0187\u0189\u0005\u004c\u0027\u0002\u0188\u0186\u0003\u0002\u0002\u0002\u0188\u0187\u0003\u0002\u0002\u0002\u0189\u018b\u0003\u0002\u0002\u0002\u018a\u018c\u0007\u0008\u0002\u0002\u018b\u018a\u0003\u0002\u0002\u0002\u018b\u018c\u0003\u0002\u0002\u0002\u018c\u0047\u0003\u0002\u0002\u0002\u018d\u018e\u0005\u0054\u002b\u0002\u018e\u018f\u0007\u0031\u0002\u0002\u018f\u0190\u0005\u0052\u002a\u0002\u0190\u0191\u0005\u004a\u0026\u0002\u0191\u0192\u0005\u0052\u002a\u0002\u0192\u0049\u0003\u0002\u0002\u0002\u0193\u0194\u0009\u0004\u0002\u0002\u0194\u004b\u0003\u0002\u0002\u0002\u0195\u0197\u0005\u0036\u001c\u0002\u0196\u0195\u0003\u0002\u0002\u0002\u0196\u0197\u0003\u0002\u0002\u0002\u0197\u0198\u0003\u0002\u0002\u0002\u0198\u019a\u0007\u000b\u0002\u0002\u0199\u019b\u0005\u0052\u002a\u0002\u019a\u0199\u0003\u0002\u0002\u0002\u019a\u019b\u0003\u0002\u0002\u0002\u019b\u019c\u0003\u0002\u0002\u0002\u019c\u019e\u0007\u000b\u0002\u0002\u019d\u019f\u0005\u0036\u001c\u0002\u019e\u019d\u0003\u0002\u0002\u0002\u019e\u019f\u0003\u0002\u0002\u0002\u019f\u004d\u0003\u0002\u0002\u0002\u01a0\u01a1\u0007\u003d\u0002\u0002\u01a1\u004f\u0003\u0002\u0002\u0002\u01a2\u01a8\u0005\u0052\u002a\u0002\u01a3\u01a4\u0005\u004e\u0028\u0002\u01a4\u01a5\u0007\u002d\u0002\u0002\u01a5\u01a6\u0005\u0052\u002a\u0002\u01a6\u01a8\u0003\u0002\u0002\u0002\u01a7\u01a2\u0003\u0002\u0002\u0002\u01a7\u01a3\u0003\u0002\u0002\u0002\u01a8\u0051\u0003\u0002\u0002\u0002\u01a9\u01aa\u0008\u002a\u0001\u0002\u01aa\u01ab\u0005\u0032\u001a\u0002\u01ab\u01ac\u0007\u0003\u0002\u0002\u01ac\u01ad\u0005\u001e\u0010\u0002\u01ad\u01af\u0007\u0007\u0002\u0002\u01ae\u01b0\u0005\u0050\u0029\u0002\u01af\u01ae\u0003\u0002\u0002\u0002\u01af\u01b0\u0003\u0002\u0002\u0002\u01b0\u01b5\u0003\u0002\u0002\u0002\u01b1\u01b2\u0007\u0005\u0002\u0002\u01b2\u01b4\u0005\u0050\u0029\u0002\u01b3\u01b1\u0003\u0002\u0002\u0002\u01b4\u01b7\u0003\u0002\u0002\u0002\u01b5\u01b3\u0003\u0002\u0002\u0002\u01b5\u01b6\u0003\u0002\u0002\u0002\u01b6\u01b8\u0003\u0002\u0002\u0002\u01b7\u01b5\u0003\u0002\u0002\u0002\u01b8\u01b9\u0007\u0008\u0002\u0002\u01b9\u01f1\u0003\u0002\u0002\u0002\u01ba\u01bb\u0005\u001e\u0010\u0002\u01bb\u01bd\u0007\u0007\u0002\u0002\u01bc\u01be\u0005\u0050\u0029\u0002\u01bd\u01bc\u0003\u0002\u0002\u0002\u01bd\u01be\u0003\u0002\u0002\u0002\u01be\u01c3\u0003\u0002\u0002\u0002\u01bf\u01c0\u0007\u0005\u0002\u0002\u01c0\u01c2\u0005\u0050\u0029\u0002\u01c1\u01bf\u0003\u0002\u0002\u0002\u01c2\u01c5\u0003\u0002\u0002\u0002\u01c3\u01c1\u0003\u0002\u0002\u0002\u01c3\u01c4\u0003\u0002\u0002\u0002\u01c4\u01c6\u0003\u0002\u0002\u0002\u01c5\u01c3\u0003\u0002\u0002\u0002\u01c6\u01c7\u0007\u0008\u0002\u0002\u01c7\u01f1\u0003\u0002\u0002\u0002\u01c8\u01c9\u0005\u0032\u001a\u0002\u01c9\u01ca\u0007\u0003\u0002\u0002\u01ca\u01cb\u0007\u003d\u0002\u0002\u01cb\u01f1\u0003\u0002\u0002\u0002\u01cc\u01cd\u0007\u002a\u0002\u0002\u01cd\u01f1\u0005\u0052\u002a\u001b\u01ce\u01cf\u0007\u0030\u0002\u0002\u01cf\u01f1\u0005\u0052\u002a\u001a\u01d0\u01d1\u0007\u0007\u0002\u0002\u01d1\u01d2\u0005\u0052\u002a\u0002\u01d2\u01d3\u0007\u0008\u0002\u0002\u01d3\u01f1\u0003\u0002\u0002\u0002\u01d4\u01f1\u0005\u0054\u002b\u0002\u01d5\u01d6\u0005\u0024\u0013\u0002\u01d6\u01d7\u0007\u0031\u0002\u0002\u01d7\u01d8\u0007\u0011\u0002\u0002\u01d8\u01d9\u0005\u0052\u002a\u0002\u01d9\u01e0\u0007\u0012\u0002\u0002\u01da\u01db\u0007\u0011\u0002\u0002\u01db\u01dc\u0005\u0052\u002a\u0002\u01dc\u01dd\u0007\u0012\u0002\u0002\u01dd\u01df\u0003\u0002\u0002\u0002\u01de\u01da\u0003\u0002\u0002\u0002\u01df\u01e2\u0003\u0002\u0002\u0002\u01e0\u01de\u0003\u0002\u0002\u0002\u01e0\u01e1\u0003\u0002\u0002\u0002\u01e1\u01f1\u0003\u0002\u0002\u0002\u01e2\u01e0\u0003\u0002\u0002\u0002\u01e3\u01ec\u0007\u0004\u0002\u0002\u01e4\u01e9\u0005\u0052\u002a\u0002\u01e5\u01e6\u0007\u0005\u0002\u0002\u01e6\u01e8\u0005\u0052\u002a\u0002\u01e7\u01e5\u0003\u0002\u0002\u0002\u01e8\u01eb\u0003\u0002\u0002\u0002\u01e9\u01e7\u0003\u0002\u0002\u0002\u01e9\u01ea\u0003\u0002\u0002\u0002\u01ea\u01ed\u0003\u0002\u0002\u0002\u01eb\u01e9\u0003\u0002\u0002\u0002\u01ec\u01e4\u0003\u0002\u0002\u0002\u01ec\u01ed\u0003\u0002\u0002\u0002\u01ed\u01ee\u0003\u0002\u0002\u0002\u01ee\u01f1\u0007\u0006\u0002\u0002\u01ef\u01f1\u0009\u0005\u0002\u0002\u01f0\u01a9\u0003\u0002\u0002\u0002\u01f0\u01ba\u0003\u0002\u0002\u0002\u01f0\u01c8\u0003\u0002\u0002\u0002\u01f0\u01cc\u0003\u0002\u0002\u0002\u01f0\u01ce\u0003\u0002\u0002\u0002\u01f0\u01d0\u0003\u0002\u0002\u0002\u01f0\u01d4\u0003\u0002\u0002\u0002\u01f0\u01d5\u0003\u0002\u0002\u0002\u01f0\u01e3\u0003\u0002\u0002\u0002\u01f0\u01ef\u0003\u0002\u0002\u0002\u01f1\u0260\u0003\u0002\u0002\u0002\u01f2\u01f3\u000c\u0017\u0002\u0002\u01f3\u01f4\u0007\u0032\u0002\u0002\u01f4\u025f\u0005\u0052\u002a\u0018\u01f5\u01f6\u000c\u0016\u0002\u0002\u01f6\u01f7\u0007\u0033\u0002\u0002\u01f7\u025f\u0005\u0052\u002a\u0017\u01f8\u01f9\u000c\u0015\u0002\u0002\u01f9\u01fa\u0007\u0036\u0002\u0002\u01fa\u025f\u0005\u0052\u002a\u0016\u01fb\u01fc\u000c\u0014\u0002\u0002\u01fc\u01fd\u0007\u0037\u0002\u0002\u01fd\u025f\u0005\u0052\u002a\u0015\u01fe\u01ff\u000c\u0013\u0002\u0002\u01ff\u0200\u0007\u0034\u0002\u0002\u0200\u025f\u0005\u0052\u002a\u0014\u0201\u0202\u000c\u0012\u0002\u0002\u0202\u0203\u0007\u0035\u0002\u0002\u0203\u025f\u0005\u0052\u002a\u0013\u0204\u0205\u000c\u0011\u0002\u0002\u0205\u0206\u0007\u0032\u0002\u0002\u0206\u0207\u0005\u0052\u002a\u0002\u0207\u0208\u0007\u002f\u0002\u0002\u0208\u0209\u0005\u0052\u002a\u0002\u0209\u020a\u0007\u0031\u0002\u0002\u020a\u020b\u0005\u0052\u002a\u0012\u020b\u025f\u0003\u0002\u0002\u0002\u020c\u020d\u000c\u0010\u0002\u0002\u020d\u020e\u0007\u0033\u0002\u0002\u020e\u020f\u0005\u0052\u002a\u0002\u020f\u0210\u0007\u002f\u0002\u0002\u0210\u0211\u0005\u0052\u002a\u0002\u0211\u0212\u0007\u0031\u0002\u0002\u0212\u0213\u0005\u0052\u002a\u0011\u0213\u025f\u0003\u0002\u0002\u0002\u0214\u0215\u000c\u000f\u0002\u0002\u0215\u0216\u0007\u0036\u0002\u0002\u0216\u0217\u0005\u0052\u002a\u0002\u0217\u0218\u0007\u002f\u0002\u0002\u0218\u0219\u0005\u0052\u002a\u0002\u0219\u021a\u0007\u0031\u0002\u0002\u021a\u021b\u0005\u0052\u002a\u0010\u021b\u025f\u0003\u0002\u0002\u0002\u021c\u021d\u000c\u000e\u0002\u0002\u021d\u021e\u0007\u0037\u0002\u0002\u021e\u021f\u0005\u0052\u002a\u0002\u021f\u0220\u0007\u002f\u0002\u0002\u0220\u0221\u0005\u0052\u002a\u0002\u0221\u0222\u0007\u0031\u0002\u0002\u0222\u0223\u0005\u0052\u002a\u000f\u0223\u025f\u0003\u0002\u0002\u0002\u0224\u0225\u000c\u000d\u0002\u0002\u0225\u0226\u0007\u0034\u0002\u0002\u0226\u0227\u0005\u0052\u002a\u0002\u0227\u0228\u0007\u002f\u0002\u0002\u0228\u0229\u0005\u0052\u002a\u0002\u0229\u022a\u0007\u0031\u0002\u0002\u022a\u022b\u0005\u0052\u002a\u000e\u022b\u025f\u0003\u0002\u0002\u0002\u022c\u022d\u000c\u000c\u0002\u0002\u022d\u022e\u0007\u0035\u0002\u0002\u022e\u022f\u0005\u0052\u002a\u0002\u022f\u0230\u0007\u002f\u0002\u0002\u0230\u0231\u0005\u0052\u002a\u0002\u0231\u0232\u0007\u0031\u0002\u0002\u0232\u0233\u0005\u0052\u002a\u000d\u0233\u025f\u0003\u0002\u0002\u0002\u0234\u0235\u000c\u000b\u0002\u0002\u0235\u0236\u0007\u002f\u0002\u0002\u0236\u0237\u0005\u0052\u002a\u0002\u0237\u0238\u0007\u0031\u0002\u0002\u0238\u0239\u0005\u0052\u002a\u000c\u0239\u025f\u0003\u0002\u0002\u0002\u023a\u023b\u000c\u000a\u0002\u0002\u023b\u023c\u0007\u002b\u0002\u0002\u023c\u025f\u0005\u0052\u002a\u000b\u023d\u023e\u000c\u0009\u0002\u0002\u023e\u023f\u0007\u002c\u0002\u0002\u023f\u025f\u0005\u0052\u002a\u000a\u0240\u0241\u000c\u0008\u0002\u0002\u0241\u0242\u0007\u0029\u0002\u0002\u0242\u025f\u0005\u0052\u002a\u0009\u0243\u0244\u000c\u0007\u0002\u0002\u0244\u0245\u0007\u002a\u0002\u0002\u0245\u025f\u0005\u0052\u002a\u0008\u0246\u0247\u000c\u0020\u0002\u0002\u0247\u0248\u0007\u0010\u0002\u0002\u0248\u0249\u0005\u001e\u0010\u0002\u0249\u024b\u0007\u0007\u0002\u0002\u024a\u024c\u0005\u0050\u0029\u0002\u024b\u024a\u0003\u0002\u0002\u0002\u024b\u024c\u0003\u0002\u0002\u0002\u024c\u0251\u0003\u0002\u0002\u0002\u024d\u024e\u0007\u0005\u0002\u0002\u024e\u0250\u0005\u0050\u0029\u0002\u024f\u024d\u0003\u0002\u0002\u0002\u0250\u0253\u0003\u0002\u0002\u0002\u0251\u024f\u0003\u0002\u0002\u0002\u0251\u0252\u0003\u0002\u0002\u0002\u0252\u0254\u0003\u0002\u0002\u0002\u0253\u0251\u0003\u0002\u0002\u0002\u0254\u0255\u0007\u0008\u0002\u0002\u0255\u025f\u0003\u0002\u0002\u0002\u0256\u0257\u000c\u001c\u0002\u0002\u0257\u0258\u0007\u0010\u0002\u0002\u0258\u025f\u0007\u003d\u0002\u0002\u0259\u025a\u000c\u0006\u0002\u0002\u025a\u025b\u0007\u0011\u0002\u0002\u025b\u025c\u0005\u0052\u002a\u0002\u025c\u025d\u0007\u0012\u0002\u0002\u025d\u025f\u0003\u0002\u0002\u0002\u025e\u01f2\u0003\u0002\u0002\u0002\u025e\u01f5\u0003\u0002\u0002\u0002\u025e\u01f8\u0003\u0002\u0002\u0002\u025e\u01fb\u0003\u0002\u0002\u0002\u025e\u01fe\u0003\u0002\u0002\u0002\u025e\u0201\u0003\u0002\u0002\u0002\u025e\u0204\u0003\u0002\u0002\u0002\u025e\u020c\u0003\u0002\u0002\u0002\u025e\u0214\u0003\u0002\u0002\u0002\u025e\u021c\u0003\u0002\u0002\u0002\u025e\u0224\u0003\u0002\u0002\u0002\u025e\u022c\u0003\u0002\u0002\u0002\u025e\u0234\u0003\u0002\u0002\u0002\u025e\u023a\u0003\u0002\u0002\u0002\u025e\u023d\u0003\u0002\u0002\u0002\u025e\u0240\u0003\u0002\u0002\u0002\u025e\u0243\u0003\u0002\u0002\u0002\u025e\u0246\u0003\u0002\u0002\u0002\u025e\u0256\u0003\u0002\u0002\u0002\u025e\u0259\u0003\u0002\u0002\u0002\u025f\u0262\u0003\u0002\u0002\u0002\u0260\u025e\u0003\u0002\u0002\u0002\u0260\u0261\u0003\u0002\u0002\u0002\u0261\u0053\u0003\u0002\u0002\u0002\u0262\u0260\u0003\u0002\u0002\u0002\u0263\u0264\u0009\u0006\u0002\u0002\u0264\u0055\u0003\u0002\u0002\u0002\u004a\u0057\u005c\u0061\u006c\u0076\u007b\u007e\u0083\u008a\u0092\u0095\u009a\u009d\u00a0\u00aa\u00ac\u00b0\u00b3\u00b6\u00bd\u00c1\u00c4\u00cc\u00d2\u00db\u00de\u00e5\u00ef\u00f2\u00fb\u00ff\u0105\u010a\u010d\u0113\u0123\u012a\u0132\u0136\u013a\u013e\u0142\u0146\u014a\u014e\u0152\u0154\u0157\u016e\u0172\u0176\u017b\u017f\u0184\u0188\u018b\u0196\u019a\u019e\u01a7\u01af\u01b5\u01bd\u01c3\u01e0\u01e9\u01ec\u01f0\u024b\u0251\u025e\u0260"

        val ATN = ATNDeserializer().deserialize(serializedATN.asCharArray())
        init {
        	decisionToDFA = Array<DFA>(ATN.numberOfDecisions, {
        		DFA(ATN.getDecisionState(it)!!, it)
        	})


        }
	}

    private val T__0 = Tokens.T__0.id
    private val T__1 = Tokens.T__1.id
    private val T__2 = Tokens.T__2.id
    private val T__3 = Tokens.T__3.id
    private val T__4 = Tokens.T__4.id
    private val T__5 = Tokens.T__5.id
    private val T__6 = Tokens.T__6.id
    private val T__7 = Tokens.T__7.id
    private val T__8 = Tokens.T__8.id
    private val T__9 = Tokens.T__9.id
    private val T__10 = Tokens.T__10.id
    private val T__11 = Tokens.T__11.id
    private val T__12 = Tokens.T__12.id
    private val T__13 = Tokens.T__13.id
    private val T__14 = Tokens.T__14.id
    private val T__15 = Tokens.T__15.id
    private val MOD = Tokens.MOD.id
    private val USE = Tokens.USE.id
    private val CLASS = Tokens.CLASS.id
    private val FN = Tokens.FN.id
    private val CTOR = Tokens.CTOR.id
    private val SELF = Tokens.SELF.id
    private val COMP = Tokens.COMP.id
    private val IF = Tokens.IF.id
    private val ELSE = Tokens.ELSE.id
    private val AS = Tokens.AS.id
    private val RETURN = Tokens.RETURN.id
    private val FOR = Tokens.FOR.id
    private val DOWN = Tokens.DOWN.id
    private val TO = Tokens.TO.id
    private val UNTIL = Tokens.UNTIL.id
    private val PUB = Tokens.PUB.id
    private val PROT = Tokens.PROT.id
    private val INTL = Tokens.INTL.id
    private val PRIV = Tokens.PRIV.id
    private val MUT = Tokens.MUT.id
    private val PRINT = Tokens.PRINT.id
    private val PRINTLN = Tokens.PRINTLN.id
    private val PLUS = Tokens.PLUS.id
    private val MINUS = Tokens.MINUS.id
    private val STAR = Tokens.STAR.id
    private val SLASH = Tokens.SLASH.id
    private val EQUALS = Tokens.EQUALS.id
    private val ASSIGN_EQ = Tokens.ASSIGN_EQ.id
    private val QUETION_MK = Tokens.QUETION_MK.id
    private val EXCLAMATION_MK = Tokens.EXCLAMATION_MK.id
    private val COLON = Tokens.COLON.id
    private val GREATER = Tokens.GREATER.id
    private val LESS = Tokens.LESS.id
    private val GREATER_EQ = Tokens.GREATER_EQ.id
    private val LESS_EQ = Tokens.LESS_EQ.id
    private val EQ = Tokens.EQ.id
    private val NOT_EQ = Tokens.NOT_EQ.id
    private val TYPES = Tokens.TYPES.id
    private val NUMBER = Tokens.NUMBER.id
    private val STRING = Tokens.STRING.id
    private val BOOL = Tokens.BOOL.id
    private val NULL = Tokens.NULL.id
    private val ID = Tokens.ID.id
    private val WS = Tokens.WS.id

    /* Named actions */
	init {
		interpreter = ParserATNSimulator(this, ATN, decisionToDFA, sharedContextCache)
	}
	/* Funcs */
	open class CompilationUnitContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_compilationUnit.id
	        set(value) { throw RuntimeException() }
		fun findClassDeclaration() : ClassDeclarationContext? = getRuleContext(solver.getType("ClassDeclarationContext"),0)
		fun findModuleDeclaraion() : ModuleDeclaraionContext? = getRuleContext(solver.getType("ModuleDeclaraionContext"),0)
		fun findUseReference() : List<UseReferenceContext> = getRuleContexts(solver.getType("UseReferenceContext"))
		fun findUseReference(i: Int) : UseReferenceContext? = getRuleContext(solver.getType("UseReferenceContext"),i)
		fun EOF() : TerminalNode? = getToken(CASCParser.Tokens.EOF.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitCompilationUnit(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  compilationUnit() : CompilationUnitContext {
		var _localctx : CompilationUnitContext = CompilationUnitContext(context, state)
		enterRule(_localctx, 0, Rules.RULE_compilationUnit.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 85
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==MOD) {
				if (true){
				this.state = 84
				moduleDeclaraion()
				}
			}

			this.state = 90
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==USE) {
				if (true){
				if (true){
				this.state = 87
				useReference()
				}
				}
				this.state = 92
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 93
			classDeclaration()
			this.state = 95
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,2,context) ) {
			1   -> if (true){
			this.state = 94
			match(EOF) as Token
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ModuleDeclaraionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_moduleDeclaraion.id
	        set(value) { throw RuntimeException() }
		fun MOD() : TerminalNode? = getToken(CASCParser.Tokens.MOD.id, 0)
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitModuleDeclaraion(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  moduleDeclaraion() : ModuleDeclaraionContext {
		var _localctx : ModuleDeclaraionContext = ModuleDeclaraionContext(context, state)
		enterRule(_localctx, 2, Rules.RULE_moduleDeclaraion.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 97
			match(MOD) as Token
			this.state = 98
			qualifiedName()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class UseReferenceContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_useReference.id
	        set(value) { throw RuntimeException() }
		fun USE() : TerminalNode? = getToken(CASCParser.Tokens.USE.id, 0)
		fun findReference() : ReferenceContext? = getRuleContext(solver.getType("ReferenceContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitUseReference(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  useReference() : UseReferenceContext {
		var _localctx : UseReferenceContext = UseReferenceContext(context, state)
		enterRule(_localctx, 4, Rules.RULE_useReference.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 100
			match(USE) as Token
			this.state = 101
			reference()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ReferenceContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_reference.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: ReferenceContext) {
			super.copyFrom(ctx)
		}
	}
	open class MultiReferenceContext : ReferenceContext {
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		fun findReference() : List<ReferenceContext> = getRuleContexts(solver.getType("ReferenceContext"))
		fun findReference(i: Int) : ReferenceContext? = getRuleContext(solver.getType("ReferenceContext"),i)
		constructor(ctx: ReferenceContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitMultiReference(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SimpleReferenceContext : ReferenceContext {
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		fun AS() : TerminalNode? = getToken(CASCParser.Tokens.AS.id, 0)
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(ctx: ReferenceContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitSimpleReference(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  reference() : ReferenceContext {
		var _localctx : ReferenceContext = ReferenceContext(context, state)
		enterRule(_localctx, 6, Rules.RULE_reference.id)
		var _la: Int
		try {
			this.state = 121
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,5,context) ) {
			1 -> {_localctx = SimpleReferenceContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 103
			qualifiedName()
			this.state = 106
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==AS) {
				if (true){
				this.state = 104
				match(AS) as Token
				this.state = 105
				match(ID) as Token
				}
			}

			}}
			2 -> {_localctx = MultiReferenceContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 108
			qualifiedName()
			this.state = 109
			match(T__0) as Token
			this.state = 110
			match(T__1) as Token
			this.state = 111
			reference()
			this.state = 116
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__2) {
				if (true){
				if (true){
				this.state = 112
				match(T__2) as Token
				this.state = 113
				reference()
				}
				}
				this.state = 118
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 119
			match(T__3) as Token
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classDeclaration.id
	        set(value) { throw RuntimeException() }
		fun CLASS() : TerminalNode? = getToken(CASCParser.Tokens.CLASS.id, 0)
		fun findClassName() : ClassNameContext? = getRuleContext(solver.getType("ClassNameContext"),0)
		fun findClassBody() : ClassBodyContext? = getRuleContext(solver.getType("ClassBodyContext"),0)
		fun findOuterAccessMods() : OuterAccessModsContext? = getRuleContext(solver.getType("OuterAccessModsContext"),0)
		fun findPrimaryConstructor() : PrimaryConstructorContext? = getRuleContext(solver.getType("PrimaryConstructorContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classDeclaration() : ClassDeclarationContext {
		var _localctx : ClassDeclarationContext = ClassDeclarationContext(context, state)
		enterRule(_localctx, 8, Rules.RULE_classDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 124
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 123
				outerAccessMods()
				}
			}

			this.state = 126
			match(CLASS) as Token
			this.state = 127
			className()
			this.state = 129
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__4) or (1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 128
				primaryConstructor()
				}
			}

			this.state = 131
			match(T__1) as Token
			this.state = 132
			classBody()
			this.state = 133
			match(T__3) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrimaryConstructorContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_primaryConstructor.id
	        set(value) { throw RuntimeException() }
		var ctorAccessMod: InnerAccessModsContext? = null
		fun findConstructorParameter() : List<ConstructorParameterContext> = getRuleContexts(solver.getType("ConstructorParameterContext"))
		fun findConstructorParameter(i: Int) : ConstructorParameterContext? = getRuleContext(solver.getType("ConstructorParameterContext"),i)
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrimaryConstructor(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  primaryConstructor() : PrimaryConstructorContext {
		var _localctx : PrimaryConstructorContext = PrimaryConstructorContext(context, state)
		enterRule(_localctx, 10, Rules.RULE_primaryConstructor.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 136
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 135
				(_localctx as PrimaryConstructorContext).ctorAccessMod = innerAccessMods()
				}
			}

			this.state = 138
			match(T__4) as Token
			this.state = 147
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__6) or (1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV) or (1L shl MUT) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 139
				constructorParameter()
				this.state = 144
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__2) {
					if (true){
					if (true){
					this.state = 140
					match(T__2) as Token
					this.state = 141
					constructorParameter()
					}
					}
					this.state = 146
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 149
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ConstructorParameterContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_constructorParameter.id
	        set(value) { throw RuntimeException() }
		var PARAM: Token? = null
		fun findParameter() : ParameterContext? = getRuleContext(solver.getType("ParameterContext"),0)
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		fun MUT() : TerminalNode? = getToken(CASCParser.Tokens.MUT.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructorParameter(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  constructorParameter() : ConstructorParameterContext {
		var _localctx : ConstructorParameterContext = ConstructorParameterContext(context, state)
		enterRule(_localctx, 12, Rules.RULE_constructorParameter.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 158
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			PUB , PROT , INTL , PRIV , MUT , ID  ->  /*LL1AltBlock*/{if (true){
			this.state = 152
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 151
				innerAccessMods()
				}
			}

			this.state = 155
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==MUT) {
				if (true){
				this.state = 154
				match(MUT) as Token
				}
			}

			}}
			T__6  ->  /*LL1AltBlock*/{if (true){
			this.state = 157
			(_localctx as ConstructorParameterContext).PARAM = match(T__6) as Token
			}}
			else -> throw NoViableAltException(this)
			}
			this.state = 160
			parameter()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_className.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  className() : ClassNameContext {
		var _localctx : ClassNameContext = ClassNameContext(context, state)
		enterRule(_localctx, 14, Rules.RULE_className.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 162
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassBodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classBody.id
	        set(value) { throw RuntimeException() }
		fun findFunction() : List<FunctionContext> = getRuleContexts(solver.getType("FunctionContext"))
		fun findFunction(i: Int) : FunctionContext? = getRuleContext(solver.getType("FunctionContext"),i)
		fun findConstructor() : List<ConstructorContext> = getRuleContexts(solver.getType("ConstructorContext"))
		fun findConstructor(i: Int) : ConstructorContext? = getRuleContext(solver.getType("ConstructorContext"),i)
		fun findField() : List<FieldContext> = getRuleContexts(solver.getType("FieldContext"))
		fun findField(i: Int) : FieldContext? = getRuleContext(solver.getType("FieldContext"),i)
		fun findFieldDeclaration() : List<FieldDeclarationContext> = getRuleContexts(solver.getType("FieldDeclarationContext"))
		fun findFieldDeclaration(i: Int) : FieldDeclarationContext? = getRuleContext(solver.getType("FieldDeclarationContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassBody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classBody() : ClassBodyContext {
		var _localctx : ClassBodyContext = ClassBodyContext(context, state)
		enterRule(_localctx, 16, Rules.RULE_classBody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 170
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl FN) or (1L shl CTOR) or (1L shl COMP) or (1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV) or (1L shl MUT) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 168
				errorHandler.sync(this)
				when ( interpreter!!.adaptivePredict(_input!!,14,context) ) {
				1 -> {if (true){
				this.state = 164
				function()
				}}
				2 -> {if (true){
				this.state = 165
				constructor()
				}}
				3 -> {if (true){
				this.state = 166
				field()
				}}
				4 -> {if (true){
				this.state = 167
				fieldDeclaration()
				}}
				}
				}
				this.state = 172
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_field.id
	        set(value) { throw RuntimeException() }
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findTypeReference() : TypeReferenceContext? = getRuleContext(solver.getType("TypeReferenceContext"),0)
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		fun COMP() : TerminalNode? = getToken(CASCParser.Tokens.COMP.id, 0)
		fun MUT() : TerminalNode? = getToken(CASCParser.Tokens.MUT.id, 0)
		fun EQUALS() : TerminalNode? = getToken(CASCParser.Tokens.EQUALS.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitField(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  field() : FieldContext {
		var _localctx : FieldContext = FieldContext(context, state)
		enterRule(_localctx, 18, Rules.RULE_field.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 174
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 173
				innerAccessMods()
				}
			}

			this.state = 177
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==COMP) {
				if (true){
				this.state = 176
				match(COMP) as Token
				}
			}

			this.state = 180
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==MUT) {
				if (true){
				this.state = 179
				match(MUT) as Token
				}
			}

			this.state = 182
			name()
			this.state = 183
			match(COLON) as Token
			this.state = 184
			typeReference()
			this.state = 187
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==EQUALS) {
				if (true){
				this.state = 185
				match(EQUALS) as Token
				this.state = 186
				expression(0)
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ConstructorContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_constructor.id
	        set(value) { throw RuntimeException() }
		fun findConstructorDeclaration() : ConstructorDeclarationContext? = getRuleContext(solver.getType("ConstructorDeclarationContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructor(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  constructor() : ConstructorContext {
		var _localctx : ConstructorContext = ConstructorContext(context, state)
		enterRule(_localctx, 20, Rules.RULE_constructor.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 189
			constructorDeclaration()
			this.state = 191
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__1) {
				if (true){
				this.state = 190
				block()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ConstructorDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_constructorDeclaration.id
	        set(value) { throw RuntimeException() }
		fun CTOR() : TerminalNode? = getToken(CASCParser.Tokens.CTOR.id, 0)
		fun findParameterSet() : ParameterSetContext? = getRuleContext(solver.getType("ParameterSetContext"),0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun SELF() : TerminalNode? = getToken(CASCParser.Tokens.SELF.id, 0)
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		fun findArgument() : List<ArgumentContext> = getRuleContexts(solver.getType("ArgumentContext"))
		fun findArgument(i: Int) : ArgumentContext? = getRuleContext(solver.getType("ArgumentContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructorDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  constructorDeclaration() : ConstructorDeclarationContext {
		var _localctx : ConstructorDeclarationContext = ConstructorDeclarationContext(context, state)
		enterRule(_localctx, 22, Rules.RULE_constructorDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 194
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 193
				innerAccessMods()
				}
			}

			this.state = 196
			match(CTOR) as Token
			this.state = 197
			parameterSet()
			this.state = 198
			match(COLON) as Token
			this.state = 199
			match(SELF) as Token
			this.state = 200
			match(T__4) as Token
			this.state = 202
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 201
				argument()
				}
			}

			this.state = 208
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__2) {
				if (true){
				if (true){
				this.state = 204
				match(T__2) as Token
				this.state = 205
				argument()
				}
				}
				this.state = 210
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 211
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_function.id
	        set(value) { throw RuntimeException() }
		fun findFunctionDeclaration() : FunctionDeclarationContext? = getRuleContext(solver.getType("FunctionDeclarationContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunction(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  function() : FunctionContext {
		var _localctx : FunctionContext = FunctionContext(context, state)
		enterRule(_localctx, 24, Rules.RULE_function.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 213
			functionDeclaration()
			this.state = 214
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functionDeclaration.id
	        set(value) { throw RuntimeException() }
		fun FN() : TerminalNode? = getToken(CASCParser.Tokens.FN.id, 0)
		fun findFunctionName() : FunctionNameContext? = getRuleContext(solver.getType("FunctionNameContext"),0)
		fun findParameterSet() : ParameterSetContext? = getRuleContext(solver.getType("ParameterSetContext"),0)
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		fun COMP() : TerminalNode? = getToken(CASCParser.Tokens.COMP.id, 0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findTypeReference() : TypeReferenceContext? = getRuleContext(solver.getType("TypeReferenceContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functionDeclaration() : FunctionDeclarationContext {
		var _localctx : FunctionDeclarationContext = FunctionDeclarationContext(context, state)
		enterRule(_localctx, 26, Rules.RULE_functionDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 217
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) {
				if (true){
				this.state = 216
				innerAccessMods()
				}
			}

			this.state = 220
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==COMP) {
				if (true){
				this.state = 219
				match(COMP) as Token
				}
			}

			this.state = 222
			match(FN) as Token
			this.state = 223
			functionName()
			this.state = 224
			parameterSet()
			this.state = 227
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==COLON) {
				if (true){
				this.state = 225
				match(COLON) as Token
				this.state = 226
				typeReference()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functionName.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functionName() : FunctionNameContext {
		var _localctx : FunctionNameContext = FunctionNameContext(context, state)
		enterRule(_localctx, 28, Rules.RULE_functionName.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 229
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ParameterSetContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_parameterSet.id
	        set(value) { throw RuntimeException() }
		fun findParameter() : List<ParameterContext> = getRuleContexts(solver.getType("ParameterContext"))
		fun findParameter(i: Int) : ParameterContext? = getRuleContext(solver.getType("ParameterContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitParameterSet(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  parameterSet() : ParameterSetContext {
		var _localctx : ParameterSetContext = ParameterSetContext(context, state)
		enterRule(_localctx, 30, Rules.RULE_parameterSet.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 231
			match(T__4) as Token
			this.state = 240
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==ID) {
				if (true){
				this.state = 232
				parameter()
				this.state = 237
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__2) {
					if (true){
					if (true){
					this.state = 233
					match(T__2) as Token
					this.state = 234
					parameter()
					}
					}
					this.state = 239
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 242
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ParameterContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_parameter.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findTypeReference() : TypeReferenceContext? = getRuleContext(solver.getType("TypeReferenceContext"),0)
		fun EQUALS() : TerminalNode? = getToken(CASCParser.Tokens.EQUALS.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitParameter(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  parameter() : ParameterContext {
		var _localctx : ParameterContext = ParameterContext(context, state)
		enterRule(_localctx, 32, Rules.RULE_parameter.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 244
			match(ID) as Token
			this.state = 245
			match(COLON) as Token
			this.state = 246
			typeReference()
			this.state = 249
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==EQUALS) {
				if (true){
				this.state = 247
				match(EQUALS) as Token
				this.state = 248
				expression(0)
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class TypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_type.id
	        set(value) { throw RuntimeException() }
		fun findPrimitiveType() : PrimitiveTypeContext? = getRuleContext(solver.getType("PrimitiveTypeContext"),0)
		fun findClassType() : ClassTypeContext? = getRuleContext(solver.getType("ClassTypeContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  type() : TypeContext {
		var _localctx : TypeContext = TypeContext(context, state)
		enterRule(_localctx, 34, Rules.RULE_type.id)
		try {
			this.state = 253
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			TYPES  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 251
			primitiveType()
			}}
			ID  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 252
			classType()
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class TypeReferenceContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_typeReference.id
	        set(value) { throw RuntimeException() }
		fun findType() : TypeContext? = getRuleContext(solver.getType("TypeContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitTypeReference(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  typeReference() : TypeReferenceContext {
		var _localctx : TypeReferenceContext = TypeReferenceContext(context, state)
		enterRule(_localctx, 36, Rules.RULE_typeReference.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 255
			type()
			this.state = 259
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 256
				match(T__7) as Token
				}
				}
				this.state = 261
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_fieldDeclaration.id
	        set(value) { throw RuntimeException() }
		fun findInnerAccessMods() : InnerAccessModsContext? = getRuleContext(solver.getType("InnerAccessModsContext"),0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun COMP() : TerminalNode? = getToken(CASCParser.Tokens.COMP.id, 0)
		fun MUT() : TerminalNode? = getToken(CASCParser.Tokens.MUT.id, 0)
		fun findField() : List<FieldContext> = getRuleContexts(solver.getType("FieldContext"))
		fun findField(i: Int) : FieldContext? = getRuleContext(solver.getType("FieldContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFieldDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  fieldDeclaration() : FieldDeclarationContext {
		var _localctx : FieldDeclarationContext = FieldDeclarationContext(context, state)
		enterRule(_localctx, 38, Rules.RULE_fieldDeclaration.id)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 262
			innerAccessMods()
			this.state = 264
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==COMP) {
				if (true){
				this.state = 263
				match(COMP) as Token
				}
			}

			this.state = 267
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==MUT) {
				if (true){
				this.state = 266
				match(MUT) as Token
				}
			}

			this.state = 269
			match(COLON) as Token
			this.state = 273
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,34,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 270
					field()
					}
					} 
				}
				this.state = 275
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,34,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class OuterAccessModsContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_outerAccessMods.id
	        set(value) { throw RuntimeException() }
		fun PUB() : TerminalNode? = getToken(CASCParser.Tokens.PUB.id, 0)
		fun INTL() : TerminalNode? = getToken(CASCParser.Tokens.INTL.id, 0)
		fun PRIV() : TerminalNode? = getToken(CASCParser.Tokens.PRIV.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitOuterAccessMods(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  outerAccessMods() : OuterAccessModsContext {
		var _localctx : OuterAccessModsContext = OuterAccessModsContext(context, state)
		enterRule(_localctx, 40, Rules.RULE_outerAccessMods.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 276
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl INTL) or (1L shl PRIV))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class InnerAccessModsContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_innerAccessMods.id
	        set(value) { throw RuntimeException() }
		fun PUB() : TerminalNode? = getToken(CASCParser.Tokens.PUB.id, 0)
		fun PROT() : TerminalNode? = getToken(CASCParser.Tokens.PROT.id, 0)
		fun INTL() : TerminalNode? = getToken(CASCParser.Tokens.INTL.id, 0)
		fun PRIV() : TerminalNode? = getToken(CASCParser.Tokens.PRIV.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitInnerAccessMods(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  innerAccessMods() : InnerAccessModsContext {
		var _localctx : InnerAccessModsContext = InnerAccessModsContext(context, state)
		enterRule(_localctx, 42, Rules.RULE_innerAccessMods.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 278
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl PUB) or (1L shl PROT) or (1L shl INTL) or (1L shl PRIV))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrimitiveTypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_primitiveType.id
	        set(value) { throw RuntimeException() }
		fun TYPES() : TerminalNode? = getToken(CASCParser.Tokens.TYPES.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrimitiveType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  primitiveType() : PrimitiveTypeContext {
		var _localctx : PrimitiveTypeContext = PrimitiveTypeContext(context, state)
		enterRule(_localctx, 44, Rules.RULE_primitiveType.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 280
			match(TYPES) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassTypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classType.id
	        set(value) { throw RuntimeException() }
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classType() : ClassTypeContext {
		var _localctx : ClassTypeContext = ClassTypeContext(context, state)
		enterRule(_localctx, 46, Rules.RULE_classType.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 282
			qualifiedName()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class QualifiedNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_qualifiedName.id
	        set(value) { throw RuntimeException() }
		fun ID() : List<TerminalNode> = getTokens(CASCParser.Tokens.ID.id)
		fun ID(i: Int) : TerminalNode = getToken(CASCParser.Tokens.ID.id, i) as TerminalNode
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitQualifiedName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  qualifiedName() : QualifiedNameContext {
		var _localctx : QualifiedNameContext = QualifiedNameContext(context, state)
		enterRule(_localctx, 48, Rules.RULE_qualifiedName.id)
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 284
			match(ID) as Token
			this.state = 289
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,35,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 285
					match(T__0) as Token
					this.state = 286
					match(ID) as Token
					}
					} 
				}
				this.state = 291
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,35,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class BlockContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_block.id
	        set(value) { throw RuntimeException() }
		fun findStatement() : List<StatementContext> = getRuleContexts(solver.getType("StatementContext"))
		fun findStatement(i: Int) : StatementContext? = getRuleContext(solver.getType("StatementContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitBlock(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  block() : BlockContext {
		var _localctx : BlockContext = BlockContext(context, state)
		enterRule(_localctx, 50, Rules.RULE_block.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 292
			match(T__1) as Token
			this.state = 296
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl USE) or (1L shl SELF) or (1L shl IF) or (1L shl RETURN) or (1L shl FOR) or (1L shl MUT) or (1L shl PRINT) or (1L shl PRINTLN) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				if (true){
				this.state = 293
				statement()
				}
				}
				this.state = 298
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 299
			match(T__3) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class StatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_statement.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun findUseReference() : UseReferenceContext? = getRuleContext(solver.getType("UseReferenceContext"),0)
		fun findVariableDeclaration() : VariableDeclarationContext? = getRuleContext(solver.getType("VariableDeclarationContext"),0)
		fun findAssignment() : AssignmentContext? = getRuleContext(solver.getType("AssignmentContext"),0)
		fun findPrintStatement() : PrintStatementContext? = getRuleContext(solver.getType("PrintStatementContext"),0)
		fun findPrintlnStatement() : PrintlnStatementContext? = getRuleContext(solver.getType("PrintlnStatementContext"),0)
		fun findForStatement() : ForStatementContext? = getRuleContext(solver.getType("ForStatementContext"),0)
		fun findReturnStatement() : ReturnStatementContext? = getRuleContext(solver.getType("ReturnStatementContext"),0)
		fun findIfStatement() : IfStatementContext? = getRuleContext(solver.getType("IfStatementContext"),0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  statement() : StatementContext {
		var _localctx : StatementContext = StatementContext(context, state)
		enterRule(_localctx, 52, Rules.RULE_statement.id)
		try {
			this.state = 338
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,46,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 301
			block()
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 302
			useReference()
			this.state = 304
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,37,context) ) {
			1   -> if (true){
			this.state = 303
			match(T__8) as Token
			}
			}
			}}
			3 -> {
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 306
			variableDeclaration()
			this.state = 308
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,38,context) ) {
			1   -> if (true){
			this.state = 307
			match(T__8) as Token
			}
			}
			}}
			4 -> {
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 310
			assignment()
			this.state = 312
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,39,context) ) {
			1   -> if (true){
			this.state = 311
			match(T__8) as Token
			}
			}
			}}
			5 -> {
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 314
			printStatement()
			this.state = 316
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,40,context) ) {
			1   -> if (true){
			this.state = 315
			match(T__8) as Token
			}
			}
			}}
			6 -> {
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 318
			printlnStatement()
			this.state = 320
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,41,context) ) {
			1   -> if (true){
			this.state = 319
			match(T__8) as Token
			}
			}
			}}
			7 -> {
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 322
			forStatement()
			this.state = 324
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,42,context) ) {
			1   -> if (true){
			this.state = 323
			match(T__8) as Token
			}
			}
			}}
			8 -> {
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 326
			returnStatement()
			this.state = 328
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,43,context) ) {
			1   -> if (true){
			this.state = 327
			match(T__8) as Token
			}
			}
			}}
			9 -> {
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 330
			ifStatement()
			this.state = 332
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,44,context) ) {
			1   -> if (true){
			this.state = 331
			match(T__8) as Token
			}
			}
			}}
			10 -> {
			enterOuterAlt(_localctx, 10)
			if (true){
			this.state = 334
			expression(0)
			this.state = 336
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,45,context) ) {
			1   -> if (true){
			this.state = 335
			match(T__8) as Token
			}
			}
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class VariableDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_variableDeclaration.id
	        set(value) { throw RuntimeException() }
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun ASSIGN_EQ() : TerminalNode? = getToken(CASCParser.Tokens.ASSIGN_EQ.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun MUT() : TerminalNode? = getToken(CASCParser.Tokens.MUT.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVariableDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  variableDeclaration() : VariableDeclarationContext {
		var _localctx : VariableDeclarationContext = VariableDeclarationContext(context, state)
		enterRule(_localctx, 54, Rules.RULE_variableDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 341
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==MUT) {
				if (true){
				this.state = 340
				match(MUT) as Token
				}
			}

			this.state = 343
			name()
			this.state = 344
			match(ASSIGN_EQ) as Token
			this.state = 345
			expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class AssignmentContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_assignment.id
	        set(value) { throw RuntimeException() }
		var ref: ExpressionContext? = null
		var toAssign: ExpressionContext? = null
		fun EQUALS() : TerminalNode? = getToken(CASCParser.Tokens.EQUALS.id, 0)
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitAssignment(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  assignment() : AssignmentContext {
		var _localctx : AssignmentContext = AssignmentContext(context, state)
		enterRule(_localctx, 56, Rules.RULE_assignment.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 347
			(_localctx as AssignmentContext).ref = expression(0)
			this.state = 348
			match(EQUALS) as Token
			this.state = 349
			(_localctx as AssignmentContext).toAssign = expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrintStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_printStatement.id
	        set(value) { throw RuntimeException() }
		fun PRINT() : TerminalNode? = getToken(CASCParser.Tokens.PRINT.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrintStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  printStatement() : PrintStatementContext {
		var _localctx : PrintStatementContext = PrintStatementContext(context, state)
		enterRule(_localctx, 58, Rules.RULE_printStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 351
			match(PRINT) as Token
			this.state = 352
			match(T__4) as Token
			this.state = 353
			expression(0)
			this.state = 354
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrintlnStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_printlnStatement.id
	        set(value) { throw RuntimeException() }
		fun PRINTLN() : TerminalNode? = getToken(CASCParser.Tokens.PRINTLN.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrintlnStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  printlnStatement() : PrintlnStatementContext {
		var _localctx : PrintlnStatementContext = PrintlnStatementContext(context, state)
		enterRule(_localctx, 60, Rules.RULE_printlnStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 356
			match(PRINTLN) as Token
			this.state = 357
			match(T__4) as Token
			this.state = 358
			expression(0)
			this.state = 359
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ReturnStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_returnStatement.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: ReturnStatementContext) {
			super.copyFrom(ctx)
		}
	}
	open class ReturnVoidContext : ReturnStatementContext {
		fun RETURN() : TerminalNode? = getToken(CASCParser.Tokens.RETURN.id, 0)
		constructor(ctx: ReturnStatementContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitReturnVoid(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ReturnWithValueContext : ReturnStatementContext {
		fun RETURN() : TerminalNode? = getToken(CASCParser.Tokens.RETURN.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ReturnStatementContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitReturnWithValue(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  returnStatement() : ReturnStatementContext {
		var _localctx : ReturnStatementContext = ReturnStatementContext(context, state)
		enterRule(_localctx, 62, Rules.RULE_returnStatement.id)
		try {
			this.state = 364
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,48,context) ) {
			1 -> {_localctx = ReturnWithValueContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 361
			match(RETURN) as Token
			this.state = 362
			expression(0)
			}}
			2 -> {_localctx = ReturnVoidContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 363
			match(RETURN) as Token
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class IfStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_ifStatement.id
	        set(value) { throw RuntimeException() }
		var condition: ExpressionContext? = null
		var trueStatement: StatementContext? = null
		var falseStatement: StatementContext? = null
		fun IF() : TerminalNode? = getToken(CASCParser.Tokens.IF.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun findStatement() : List<StatementContext> = getRuleContexts(solver.getType("StatementContext"))
		fun findStatement(i: Int) : StatementContext? = getRuleContext(solver.getType("StatementContext"),i)
		fun ELSE() : TerminalNode? = getToken(CASCParser.Tokens.ELSE.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitIfStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  ifStatement() : IfStatementContext {
		var _localctx : IfStatementContext = IfStatementContext(context, state)
		enterRule(_localctx, 64, Rules.RULE_ifStatement.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 366
			match(IF) as Token
			this.state = 368
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,49,context) ) {
			1   -> if (true){
			this.state = 367
			match(T__4) as Token
			}
			}
			this.state = 370
			(_localctx as IfStatementContext).condition = expression(0)
			this.state = 372
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 371
				match(T__5) as Token
				}
			}

			this.state = 374
			(_localctx as IfStatementContext).trueStatement = statement()
			this.state = 377
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,51,context) ) {
			1   -> if (true){
			this.state = 375
			match(ELSE) as Token
			this.state = 376
			(_localctx as IfStatementContext).falseStatement = statement()
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forStatement.id
	        set(value) { throw RuntimeException() }
		fun FOR() : TerminalNode? = getToken(CASCParser.Tokens.FOR.id, 0)
		fun findStatement() : StatementContext? = getRuleContext(solver.getType("StatementContext"),0)
		fun findForExpressions() : ForExpressionsContext? = getRuleContext(solver.getType("ForExpressionsContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forStatement() : ForStatementContext {
		var _localctx : ForStatementContext = ForStatementContext(context, state)
		enterRule(_localctx, 66, Rules.RULE_forStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 379
			match(FOR) as Token
			this.state = 381
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,52,context) ) {
			1   -> if (true){
			this.state = 380
			forExpressions()
			}
			}
			this.state = 383
			statement()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForExpressionsContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forExpressions.id
	        set(value) { throw RuntimeException() }
		fun findForRangedExpression() : ForRangedExpressionContext? = getRuleContext(solver.getType("ForRangedExpressionContext"),0)
		fun findForLoopExpression() : ForLoopExpressionContext? = getRuleContext(solver.getType("ForLoopExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForExpressions(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forExpressions() : ForExpressionsContext {
		var _localctx : ForExpressionsContext = ForExpressionsContext(context, state)
		enterRule(_localctx, 68, Rules.RULE_forExpressions.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 386
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,53,context) ) {
			1   -> if (true){
			this.state = 385
			match(T__4) as Token
			}
			}
			this.state = 390
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,54,context) ) {
			1 -> {if (true){
			this.state = 388
			forRangedExpression()
			}}
			2 -> {if (true){
			this.state = 389
			forLoopExpression()
			}}
			}
			this.state = 393
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 392
				match(T__5) as Token
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForRangedExpressionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forRangedExpression.id
	        set(value) { throw RuntimeException() }
		var iterator: VarReferenceContext? = null
		var startExpr: ExpressionContext? = null
		var arrow: ForArrowContext? = null
		var endExpr: ExpressionContext? = null
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findVarReference() : VarReferenceContext? = getRuleContext(solver.getType("VarReferenceContext"),0)
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun findForArrow() : ForArrowContext? = getRuleContext(solver.getType("ForArrowContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForRangedExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forRangedExpression() : ForRangedExpressionContext {
		var _localctx : ForRangedExpressionContext = ForRangedExpressionContext(context, state)
		enterRule(_localctx, 70, Rules.RULE_forRangedExpression.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 395
			(_localctx as ForRangedExpressionContext).iterator = varReference()
			this.state = 396
			match(COLON) as Token
			this.state = 397
			(_localctx as ForRangedExpressionContext).startExpr = expression(0)
			this.state = 398
			(_localctx as ForRangedExpressionContext).arrow = forArrow()
			this.state = 399
			(_localctx as ForRangedExpressionContext).endExpr = expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForArrowContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forArrow.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForArrow(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forArrow() : ForArrowContext {
		var _localctx : ForArrowContext = ForArrowContext(context, state)
		enterRule(_localctx, 72, Rules.RULE_forArrow.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 401
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__9) or (1L shl T__10) or (1L shl T__11) or (1L shl T__12))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForLoopExpressionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forLoopExpression.id
	        set(value) { throw RuntimeException() }
		var initStatement: StatementContext? = null
		var conditionExpr: ExpressionContext? = null
		var postStatement: StatementContext? = null
		fun findStatement() : List<StatementContext> = getRuleContexts(solver.getType("StatementContext"))
		fun findStatement(i: Int) : StatementContext? = getRuleContext(solver.getType("StatementContext"),i)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForLoopExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forLoopExpression() : ForLoopExpressionContext {
		var _localctx : ForLoopExpressionContext = ForLoopExpressionContext(context, state)
		enterRule(_localctx, 74, Rules.RULE_forLoopExpression.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 404
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl USE) or (1L shl SELF) or (1L shl IF) or (1L shl RETURN) or (1L shl FOR) or (1L shl MUT) or (1L shl PRINT) or (1L shl PRINTLN) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 403
				(_localctx as ForLoopExpressionContext).initStatement = statement()
				}
			}

			this.state = 406
			match(T__8) as Token
			this.state = 408
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 407
				(_localctx as ForLoopExpressionContext).conditionExpr = expression(0)
				}
			}

			this.state = 410
			match(T__8) as Token
			this.state = 412
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,58,context) ) {
			1   -> if (true){
			this.state = 411
			(_localctx as ForLoopExpressionContext).postStatement = statement()
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class NameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_name.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  name() : NameContext {
		var _localctx : NameContext = NameContext(context, state)
		enterRule(_localctx, 76, Rules.RULE_name.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 414
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ArgumentContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_argument.id
	        set(value) { throw RuntimeException() }
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun EQUALS() : TerminalNode? = getToken(CASCParser.Tokens.EQUALS.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitArgument(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  argument() : ArgumentContext {
		var _localctx : ArgumentContext = ArgumentContext(context, state)
		enterRule(_localctx, 78, Rules.RULE_argument.id)
		try {
			this.state = 421
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,59,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 416
			expression(0)
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 417
			name()
			this.state = 418
			match(EQUALS) as Token
			this.state = 419
			expression(0)
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ExpressionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_expression.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: ExpressionContext) {
			super.copyFrom(ctx)
		}
	}
	open class AddContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun PLUS() : TerminalNode? = getToken(CASCParser.Tokens.PLUS.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitAdd(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class NegativeExpressionContext : ExpressionContext {
		public var NEG: Token? = null
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun MINUS() : TerminalNode? = getToken(CASCParser.Tokens.MINUS.id, 0)
		fun EXCLAMATION_MK() : TerminalNode? = getToken(CASCParser.Tokens.EXCLAMATION_MK.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitNegativeExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ConditionalExpressionContext : ExpressionContext {
		public var cmp: Token? = null
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun GREATER() : TerminalNode? = getToken(CASCParser.Tokens.GREATER.id, 0)
		fun LESS() : TerminalNode? = getToken(CASCParser.Tokens.LESS.id, 0)
		fun EQ() : TerminalNode? = getToken(CASCParser.Tokens.EQ.id, 0)
		fun NOT_EQ() : TerminalNode? = getToken(CASCParser.Tokens.NOT_EQ.id, 0)
		fun GREATER_EQ() : TerminalNode? = getToken(CASCParser.Tokens.GREATER_EQ.id, 0)
		fun LESS_EQ() : TerminalNode? = getToken(CASCParser.Tokens.LESS_EQ.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConditionalExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SubtractContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun MINUS() : TerminalNode? = getToken(CASCParser.Tokens.MINUS.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitSubtract(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class VarRefContext : ExpressionContext {
		fun findVarReference() : VarReferenceContext? = getRuleContext(solver.getType("VarReferenceContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVarRef(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class IndexEpxressionContext : ExpressionContext {
		public var referenceExpression: ExpressionContext? = null
		public var indexExpression: ExpressionContext? = null
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitIndexEpxression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class FunctionCallContext : ExpressionContext {
		public var owner: ExpressionContext? = null
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		fun findFunctionName() : FunctionNameContext? = getRuleContext(solver.getType("FunctionNameContext"),0)
		fun findArgument() : List<ArgumentContext> = getRuleContexts(solver.getType("ArgumentContext"))
		fun findArgument(i: Int) : ArgumentContext? = getRuleContext(solver.getType("ArgumentContext"),i)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionCall(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class FieldCallContext : ExpressionContext {
		public var owner: ExpressionContext? = null
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFieldCall(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ArrayDeclarationContext : ExpressionContext {
		fun findType() : TypeContext? = getRuleContext(solver.getType("TypeContext"),0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitArrayDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class DivideContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun SLASH() : TerminalNode? = getToken(CASCParser.Tokens.SLASH.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitDivide(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ArrayInitializationContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitArrayInitialization(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class IfExpressionContext : ExpressionContext {
		public var left: ExpressionContext? = null
		public var condition: ExpressionContext? = null
		public var cmp: Token? = null
		public var right: ExpressionContext? = null
		public var trueExpression: ExpressionContext? = null
		public var falseExpression: ExpressionContext? = null
		fun QUETION_MK() : TerminalNode? = getToken(CASCParser.Tokens.QUETION_MK.id, 0)
		fun COLON() : TerminalNode? = getToken(CASCParser.Tokens.COLON.id, 0)
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun GREATER() : TerminalNode? = getToken(CASCParser.Tokens.GREATER.id, 0)
		fun LESS() : TerminalNode? = getToken(CASCParser.Tokens.LESS.id, 0)
		fun EQ() : TerminalNode? = getToken(CASCParser.Tokens.EQ.id, 0)
		fun NOT_EQ() : TerminalNode? = getToken(CASCParser.Tokens.NOT_EQ.id, 0)
		fun GREATER_EQ() : TerminalNode? = getToken(CASCParser.Tokens.GREATER_EQ.id, 0)
		fun LESS_EQ() : TerminalNode? = getToken(CASCParser.Tokens.LESS_EQ.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitIfExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class MultiplyContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun STAR() : TerminalNode? = getToken(CASCParser.Tokens.STAR.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitMultiply(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ValueContext : ExpressionContext {
		fun NUMBER() : TerminalNode? = getToken(CASCParser.Tokens.NUMBER.id, 0)
		fun BOOL() : TerminalNode? = getToken(CASCParser.Tokens.BOOL.id, 0)
		fun STRING() : TerminalNode? = getToken(CASCParser.Tokens.STRING.id, 0)
		fun NULL() : TerminalNode? = getToken(CASCParser.Tokens.NULL.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitValue(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class WrappedExpressionContext : ExpressionContext {
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitWrappedExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}

	 fun expression() : ExpressionContext {
		return expression(0);
	}

	private fun expression(_p: Int) : ExpressionContext {
		var _parentctx : ParserRuleContext? = context
		var _parentState : Int = state
		var _localctx : ExpressionContext= ExpressionContext(context, _parentState)
		var _prevctx : ExpressionContext= _localctx
		var _startState : Int = 80
		enterRecursionRule(_localctx, 80, Rules.RULE_expression.id, _p)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 494
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,67,context) ) {
			1 -> {if (true){
			_localctx = FunctionCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx

			this.state = 424
			qualifiedName()
			this.state = 425
			match(T__0) as Token
			this.state = 426
			functionName()
			this.state = 427
			match(T__4) as Token
			this.state = 429
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 428
				argument()
				}
			}

			this.state = 435
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__2) {
				if (true){
				if (true){
				this.state = 431
				match(T__2) as Token
				this.state = 432
				argument()
				}
				}
				this.state = 437
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 438
			match(T__5) as Token
			}}
			2 -> {if (true){
			_localctx = FunctionCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 440
			functionName()
			this.state = 441
			match(T__4) as Token
			this.state = 443
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 442
				argument()
				}
			}

			this.state = 449
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__2) {
				if (true){
				if (true){
				this.state = 445
				match(T__2) as Token
				this.state = 446
				argument()
				}
				}
				this.state = 451
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 452
			match(T__5) as Token
			}}
			3 -> {if (true){
			_localctx = FieldCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 454
			qualifiedName()
			this.state = 455
			match(T__0) as Token
			this.state = 456
			match(ID) as Token
			}}
			4 -> {if (true){
			_localctx = NegativeExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 458
			(_localctx as NegativeExpressionContext).NEG = match(MINUS) as Token
			this.state = 459
			expression(25)
			}}
			5 -> {if (true){
			_localctx = NegativeExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 460
			(_localctx as NegativeExpressionContext).NEG = match(EXCLAMATION_MK) as Token
			this.state = 461
			expression(24)
			}}
			6 -> {if (true){
			_localctx = WrappedExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 462
			match(T__4) as Token
			this.state = 463
			expression(0)
			this.state = 464
			match(T__5) as Token
			}}
			7 -> {if (true){
			_localctx = VarRefContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 466
			varReference()
			}}
			8 -> {if (true){
			_localctx = ArrayDeclarationContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 467
			type()
			this.state = 468
			match(COLON) as Token
			this.state = 469
			match(T__14) as Token
			this.state = 470
			expression(0)
			this.state = 471
			match(T__15) as Token
			this.state = 478
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,64,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if (true){
					if (true){
					this.state = 472
					match(T__14) as Token
					this.state = 473
					expression(0)
					this.state = 474
					match(T__15) as Token
					}
					} 
				}
				this.state = 480
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,64,context)
			}
			}}
			9 -> {if (true){
			_localctx = ArrayInitializationContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 481
			match(T__1) as Token
			this.state = 490
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 482
				expression(0)
				this.state = 487
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__2) {
					if (true){
					if (true){
					this.state = 483
					match(T__2) as Token
					this.state = 484
					expression(0)
					}
					}
					this.state = 489
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 492
			match(T__3) as Token
			}}
			10 -> {if (true){
			_localctx = ValueContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 493
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}}
			}
			this.context!!.stop = _input!!.LT(-1)
			this.state = 606
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,71,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent()
					    _prevctx = _localctx
					if (true){
					this.state = 604
					errorHandler.sync(this)
					when ( interpreter!!.adaptivePredict(_input!!,70,context) ) {
					1 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 496
					if (!(precpred(context!!, 21))) throw FailedPredicateException(this, "precpred(context!!, 21)")
					this.state = 497
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER) as Token
					this.state = 498
					expression(22)
					}}
					2 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 499
					if (!(precpred(context!!, 20))) throw FailedPredicateException(this, "precpred(context!!, 20)")
					this.state = 500
					(_localctx as ConditionalExpressionContext).cmp = match(LESS) as Token
					this.state = 501
					expression(21)
					}}
					3 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 502
					if (!(precpred(context!!, 19))) throw FailedPredicateException(this, "precpred(context!!, 19)")
					this.state = 503
					(_localctx as ConditionalExpressionContext).cmp = match(EQ) as Token
					this.state = 504
					expression(20)
					}}
					4 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 505
					if (!(precpred(context!!, 18))) throw FailedPredicateException(this, "precpred(context!!, 18)")
					this.state = 506
					(_localctx as ConditionalExpressionContext).cmp = match(NOT_EQ) as Token
					this.state = 507
					expression(19)
					}}
					5 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 508
					if (!(precpred(context!!, 17))) throw FailedPredicateException(this, "precpred(context!!, 17)")
					this.state = 509
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER_EQ) as Token
					this.state = 510
					expression(18)
					}}
					6 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 511
					if (!(precpred(context!!, 16))) throw FailedPredicateException(this, "precpred(context!!, 16)")
					this.state = 512
					(_localctx as ConditionalExpressionContext).cmp = match(LESS_EQ) as Token
					this.state = 513
					expression(17)
					}}
					7 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 514
					if (!(precpred(context!!, 15))) throw FailedPredicateException(this, "precpred(context!!, 15)")
					this.state = 515
					(_localctx as IfExpressionContext).cmp = match(GREATER) as Token
					this.state = 516
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 517
					match(QUETION_MK) as Token
					this.state = 518
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 519
					match(COLON) as Token
					this.state = 520
					(_localctx as IfExpressionContext).falseExpression = expression(16)
					}}
					8 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 522
					if (!(precpred(context!!, 14))) throw FailedPredicateException(this, "precpred(context!!, 14)")
					this.state = 523
					(_localctx as IfExpressionContext).cmp = match(LESS) as Token
					this.state = 524
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 525
					match(QUETION_MK) as Token
					this.state = 526
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 527
					match(COLON) as Token
					this.state = 528
					(_localctx as IfExpressionContext).falseExpression = expression(15)
					}}
					9 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 530
					if (!(precpred(context!!, 13))) throw FailedPredicateException(this, "precpred(context!!, 13)")
					this.state = 531
					(_localctx as IfExpressionContext).cmp = match(EQ) as Token
					this.state = 532
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 533
					match(QUETION_MK) as Token
					this.state = 534
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 535
					match(COLON) as Token
					this.state = 536
					(_localctx as IfExpressionContext).falseExpression = expression(14)
					}}
					10 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 538
					if (!(precpred(context!!, 12))) throw FailedPredicateException(this, "precpred(context!!, 12)")
					this.state = 539
					(_localctx as IfExpressionContext).cmp = match(NOT_EQ) as Token
					this.state = 540
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 541
					match(QUETION_MK) as Token
					this.state = 542
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 543
					match(COLON) as Token
					this.state = 544
					(_localctx as IfExpressionContext).falseExpression = expression(13)
					}}
					11 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 546
					if (!(precpred(context!!, 11))) throw FailedPredicateException(this, "precpred(context!!, 11)")
					this.state = 547
					(_localctx as IfExpressionContext).cmp = match(GREATER_EQ) as Token
					this.state = 548
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 549
					match(QUETION_MK) as Token
					this.state = 550
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 551
					match(COLON) as Token
					this.state = 552
					(_localctx as IfExpressionContext).falseExpression = expression(12)
					}}
					12 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 554
					if (!(precpred(context!!, 10))) throw FailedPredicateException(this, "precpred(context!!, 10)")
					this.state = 555
					(_localctx as IfExpressionContext).cmp = match(LESS_EQ) as Token
					this.state = 556
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 557
					match(QUETION_MK) as Token
					this.state = 558
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 559
					match(COLON) as Token
					this.state = 560
					(_localctx as IfExpressionContext).falseExpression = expression(11)
					}}
					13 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).condition = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 562
					if (!(precpred(context!!, 9))) throw FailedPredicateException(this, "precpred(context!!, 9)")
					this.state = 563
					match(QUETION_MK) as Token
					this.state = 564
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 565
					match(COLON) as Token
					this.state = 566
					(_localctx as IfExpressionContext).falseExpression = expression(10)
					}}
					14 -> {if (true){
					_localctx = MultiplyContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 568
					if (!(precpred(context!!, 8))) throw FailedPredicateException(this, "precpred(context!!, 8)")
					this.state = 569
					match(STAR) as Token
					this.state = 570
					expression(9)
					}}
					15 -> {if (true){
					_localctx = DivideContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 571
					if (!(precpred(context!!, 7))) throw FailedPredicateException(this, "precpred(context!!, 7)")
					this.state = 572
					match(SLASH) as Token
					this.state = 573
					expression(8)
					}}
					16 -> {if (true){
					_localctx = AddContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 574
					if (!(precpred(context!!, 6))) throw FailedPredicateException(this, "precpred(context!!, 6)")
					this.state = 575
					match(PLUS) as Token
					this.state = 576
					expression(7)
					}}
					17 -> {if (true){
					_localctx = SubtractContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 577
					if (!(precpred(context!!, 5))) throw FailedPredicateException(this, "precpred(context!!, 5)")
					this.state = 578
					match(MINUS) as Token
					this.state = 579
					expression(6)
					}}
					18 -> {if (true){
					_localctx = FunctionCallContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as FunctionCallContext).owner = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 580
					if (!(precpred(context!!, 30))) throw FailedPredicateException(this, "precpred(context!!, 30)")
					this.state = 581
					match(T__13) as Token
					this.state = 582
					functionName()
					this.state = 583
					match(T__4) as Token
					this.state = 585
					errorHandler.sync(this)
					_la = _input!!.LA(1)
					if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__1) or (1L shl T__4) or (1L shl SELF) or (1L shl MINUS) or (1L shl EXCLAMATION_MK) or (1L shl TYPES) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl NULL) or (1L shl ID))) != 0L)) {
						if (true){
						this.state = 584
						argument()
						}
					}

					this.state = 591
					errorHandler.sync(this);
					_la = _input!!.LA(1)
					while (_la==T__2) {
						if (true){
						if (true){
						this.state = 587
						match(T__2) as Token
						this.state = 588
						argument()
						}
						}
						this.state = 593
						errorHandler.sync(this)
						_la = _input!!.LA(1)
					}
					this.state = 594
					match(T__5) as Token
					}}
					19 -> {if (true){
					_localctx = FieldCallContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as FieldCallContext).owner = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 596
					if (!(precpred(context!!, 26))) throw FailedPredicateException(this, "precpred(context!!, 26)")
					this.state = 597
					match(T__13) as Token
					this.state = 598
					match(ID) as Token
					}}
					20 -> {if (true){
					_localctx = IndexEpxressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IndexEpxressionContext).referenceExpression = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 599
					if (!(precpred(context!!, 4))) throw FailedPredicateException(this, "precpred(context!!, 4)")
					this.state = 600
					match(T__14) as Token
					this.state = 601
					(_localctx as IndexEpxressionContext).indexExpression = expression(0)
					this.state = 602
					match(T__15) as Token
					}}
					}
					} 
				}
				this.state = 608
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,71,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			unrollRecursionContexts(_parentctx)
		}
		return _localctx
	}

	open class VarReferenceContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_varReference.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		fun SELF() : TerminalNode? = getToken(CASCParser.Tokens.SELF.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVarReference(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  varReference() : VarReferenceContext {
		var _localctx : VarReferenceContext = VarReferenceContext(context, state)
		enterRule(_localctx, 82, Rules.RULE_varReference.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 609
			_la = _input!!.LA(1)
			if ( !(_la==SELF || _la==ID) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	override fun sempred(_localctx: RuleContext?, ruleIndex: Int, predIndex: Int) : Boolean {
		when (ruleIndex) {
		40 -> return expression_sempred(_localctx as ExpressionContext, predIndex)
		}
		return true
	}
	private fun expression_sempred( _localctx : ExpressionContext, predIndex: Int) : Boolean {
		when (predIndex) {
		    0 -> return precpred(context!!, 21)
		    1 -> return precpred(context!!, 20)
		    2 -> return precpred(context!!, 19)
		    3 -> return precpred(context!!, 18)
		    4 -> return precpred(context!!, 17)
		    5 -> return precpred(context!!, 16)
		    6 -> return precpred(context!!, 15)
		    7 -> return precpred(context!!, 14)
		    8 -> return precpred(context!!, 13)
		    9 -> return precpred(context!!, 12)
		    10 -> return precpred(context!!, 11)
		    11 -> return precpred(context!!, 10)
		    12 -> return precpred(context!!, 9)
		    13 -> return precpred(context!!, 8)
		    14 -> return precpred(context!!, 7)
		    15 -> return precpred(context!!, 6)
		    16 -> return precpred(context!!, 5)
		    17 -> return precpred(context!!, 30)
		    18 -> return precpred(context!!, 26)
		    19 -> return precpred(context!!, 4)
		}
		return true
	}

}