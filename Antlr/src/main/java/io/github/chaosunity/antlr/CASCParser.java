// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CASCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, CLASS=37, FUNC=38, VARIABLE=39, 
		IF=40, ELSE=41, RETURN=42, FOR=43, FROM=44, TO=45, UNTIL=46, PRINT=47, 
		PRINTLN=48, PLUS=49, MINUS=50, STAR=51, SLASH=52, EQUALS=53, GREATER=54, 
		LESS=55, GREATER_EQ=56, LESS_EQ=57, EQ=58, NOT_EQ=59, NUMBER=60, STRING=61, 
		BOOL=62, ID=63, WS=64;
	public static final int
		RULE_compilationUnit = 0, RULE_classDeclaration = 1, RULE_className = 2, 
		RULE_classBody = 3, RULE_constructor = 4, RULE_constructorDeclaration = 5, 
		RULE_function = 6, RULE_functionDeclaration = 7, RULE_functionName = 8, 
		RULE_functionParameter = 9, RULE_functionParameterDefaultValue = 10, RULE_type = 11, 
		RULE_primitiveType = 12, RULE_classType = 13, RULE_qualifiedName = 14, 
		RULE_block = 15, RULE_statement = 16, RULE_variableDeclaration = 17, RULE_printStatement = 18, 
		RULE_printlnStatement = 19, RULE_returnStatement = 20, RULE_ifStatement = 21, 
		RULE_forStatement = 22, RULE_forExpression = 23, RULE_name = 24, RULE_argument = 25, 
		RULE_expressionList = 26, RULE_expression = 27, RULE_varReference = 28, 
		RULE_value = 29;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "classDeclaration", "className", "classBody", "constructor", 
			"constructorDeclaration", "function", "functionDeclaration", "functionName", 
			"functionParameter", "functionParameterDefaultValue", "type", "primitiveType", 
			"classType", "qualifiedName", "block", "statement", "variableDeclaration", 
			"printStatement", "printlnStatement", "returnStatement", "ifStatement", 
			"forStatement", "forExpression", "name", "argument", "expressionList", 
			"expression", "varReference", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'ctor'", "'('", "','", "')'", "'fn'", "':'", "'='", 
			"'boolean'", "'\u5E03\u6797'", "'['", "']'", "'string'", "'\u5B57\u4E32'", 
			"'char'", "'\u5B57\u5143'", "'byte'", "'\u4F4D\u5143'", "'short'", "'\u77ED\u6574\u6578'", 
			"'int'", "'\u6574\u6578'", "'long'", "'\u9577\u6574\u6578'", "'float'", 
			"'\u6D6E\u9EDE\u6578'", "'double'", "'\u500D\u6D6E\u9EDE\u6578'", "'void'", 
			"'\u7A7A'", "'::'", "':='", "'this'", "'.'", "'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "CLASS", "FUNC", "VARIABLE", "IF", "ELSE", "RETURN", "FOR", "FROM", 
			"TO", "UNTIL", "PRINT", "PRINTLN", "PLUS", "MINUS", "STAR", "SLASH", 
			"EQUALS", "GREATER", "LESS", "GREATER_EQ", "LESS_EQ", "EQ", "NOT_EQ", 
			"NUMBER", "STRING", "BOOL", "ID", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CASC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CASCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CompilationUnitContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CASCParser.EOF, 0); }
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			classDeclaration();
			setState(61);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(CASCParser.CLASS, 0); }
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(CLASS);
			setState(64);
			className();
			setState(65);
			match(T__0);
			setState(66);
			classBody();
			setState(67);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_className);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public List<ConstructorContext> constructor() {
			return getRuleContexts(ConstructorContext.class);
		}
		public ConstructorContext constructor(int i) {
			return getRuleContext(ConstructorContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2 || _la==T__6) {
				{
				setState(73);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__6:
					{
					setState(71);
					function();
					}
					break;
				case T__2:
					{
					setState(72);
					constructor();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorContext extends ParserRuleContext {
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorContext constructor() throws RecognitionException {
		ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			constructorDeclaration();
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(79);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(82);
			match(T__2);
			}
			setState(83);
			match(T__3);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(84);
				functionParameter();
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(85);
					match(T__4);
					setState(86);
					functionParameter();
					}
					}
					setState(91);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(94);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			functionDeclaration();
			setState(97);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(T__6);
			setState(100);
			functionName();
			setState(101);
			match(T__3);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(102);
				functionParameter();
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(103);
					match(T__4);
					setState(104);
					functionParameter();
					}
					}
					setState(109);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(112);
			match(T__5);
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(113);
				match(T__7);
				setState(114);
				type();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public FunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameContext functionName() throws RecognitionException {
		FunctionNameContext _localctx = new FunctionNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParameterContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionParameterDefaultValueContext functionParameterDefaultValue() {
			return getRuleContext(FunctionParameterDefaultValueContext.class,0);
		}
		public FunctionParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterContext functionParameter() throws RecognitionException {
		FunctionParameterContext _localctx = new FunctionParameterContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(ID);
			setState(120);
			match(T__7);
			setState(121);
			type();
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(122);
				functionParameterDefaultValue();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParameterDefaultValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionParameterDefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameterDefaultValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionParameterDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterDefaultValueContext functionParameterDefaultValue() throws RecognitionException {
		FunctionParameterDefaultValueContext _localctx = new FunctionParameterDefaultValueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_functionParameterDefaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(T__8);
			setState(126);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_type);
		try {
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
			case T__10:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__30:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				primitiveType();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				classType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_primitiveType);
		int _la;
		try {
			setState(212);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(133);
					match(T__11);
					setState(134);
					match(T__12);
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(140);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(141);
					match(T__11);
					setState(142);
					match(T__12);
					}
					}
					setState(147);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(148);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(149);
					match(T__11);
					setState(150);
					match(T__12);
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__17:
			case T__18:
				enterOuterAlt(_localctx, 4);
				{
				setState(156);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(157);
					match(T__11);
					setState(158);
					match(T__12);
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 5);
				{
				setState(164);
				_la = _input.LA(1);
				if ( !(_la==T__19 || _la==T__20) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(165);
					match(T__11);
					setState(166);
					match(T__12);
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__21:
			case T__22:
				enterOuterAlt(_localctx, 6);
				{
				setState(172);
				_la = _input.LA(1);
				if ( !(_la==T__21 || _la==T__22) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(173);
					match(T__11);
					setState(174);
					match(T__12);
					}
					}
					setState(179);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__23:
			case T__24:
				enterOuterAlt(_localctx, 7);
				{
				setState(180);
				_la = _input.LA(1);
				if ( !(_la==T__23 || _la==T__24) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(181);
					match(T__11);
					setState(182);
					match(T__12);
					}
					}
					setState(187);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__25:
			case T__26:
				enterOuterAlt(_localctx, 8);
				{
				setState(188);
				_la = _input.LA(1);
				if ( !(_la==T__25 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(189);
					match(T__11);
					setState(190);
					match(T__12);
					}
					}
					setState(195);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 9);
				{
				setState(196);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__28) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(197);
					match(T__11);
					setState(198);
					match(T__12);
					}
					}
					setState(203);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__29:
			case T__30:
				enterOuterAlt(_localctx, 10);
				{
				setState(204);
				_la = _input.LA(1);
				if ( !(_la==T__29 || _la==T__30) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(205);
					match(T__11);
					setState(206);
					match(T__12);
					}
					}
					setState(211);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_classType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			qualifiedName();
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(215);
				match(T__11);
				setState(216);
				match(T__12);
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(CASCParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(CASCParser.ID, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(ID);
			setState(225); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(223);
				match(T__31);
				setState(224);
				match(ID);
				}
				}
				setState(227); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__31 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(T__0);
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << T__33) | (1L << IF) | (1L << RETURN) | (1L << FOR) | (1L << PRINT) | (1L << PRINTLN) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
				{
				{
				setState(230);
				statement();
				}
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(236);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public PrintlnStatementContext printlnStatement() {
			return getRuleContext(PrintlnStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_statement);
		try {
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				variableDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(240);
				printStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(241);
				printlnStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(242);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(243);
				returnStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(244);
				ifStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(245);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext specType;
		public Token NEG;
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			name();
			setState(251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(249);
				match(T__7);
				setState(250);
				((VariableDeclarationContext)_localctx).specType = type();
				}
			}

			setState(253);
			match(T__32);
			setState(255);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(254);
				((VariableDeclarationContext)_localctx).NEG = match(MINUS);
				}
				break;
			}
			setState(257);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintStatementContext extends ParserRuleContext {
		public Token NEG;
		public TerminalNode PRINT() { return getToken(CASCParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_printStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(PRINT);
			setState(260);
			match(T__3);
			setState(262);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(261);
				((PrintStatementContext)_localctx).NEG = match(MINUS);
				}
				break;
			}
			setState(264);
			expression(0);
			setState(265);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintlnStatementContext extends ParserRuleContext {
		public Token NEG;
		public TerminalNode PRINTLN() { return getToken(CASCParser.PRINTLN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public PrintlnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printlnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrintlnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintlnStatementContext printlnStatement() throws RecognitionException {
		PrintlnStatementContext _localctx = new PrintlnStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_printlnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(PRINTLN);
			setState(268);
			match(T__3);
			setState(270);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(269);
				((PrintlnStatementContext)_localctx).NEG = match(MINUS);
				}
				break;
			}
			setState(272);
			expression(0);
			setState(273);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	 
		public ReturnStatementContext() { }
		public void copyFrom(ReturnStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReturnVoidContext extends ReturnStatementContext {
		public TerminalNode RETURN() { return getToken(CASCParser.RETURN, 0); }
		public ReturnVoidContext(ReturnStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitReturnVoid(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnWithValueContext extends ReturnStatementContext {
		public Token NEG;
		public TerminalNode RETURN() { return getToken(CASCParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ReturnWithValueContext(ReturnStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitReturnWithValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_returnStatement);
		try {
			setState(281);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new ReturnWithValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(275);
				match(RETURN);
				setState(277);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					{
					setState(276);
					((ReturnWithValueContext)_localctx).NEG = match(MINUS);
					}
					break;
				}
				setState(279);
				expression(0);
				}
				break;
			case 2:
				_localctx = new ReturnVoidContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				match(RETURN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public Token NEG;
		public StatementContext trueStatement;
		public StatementContext falseStatement;
		public TerminalNode IF() { return getToken(CASCParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(CASCParser.ELSE, 0); }
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(IF);
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(284);
				match(T__3);
				}
				break;
			}
			setState(288);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(287);
				((IfStatementContext)_localctx).NEG = match(MINUS);
				}
				break;
			}
			setState(290);
			expression(0);
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(291);
				match(T__5);
				}
			}

			setState(294);
			((IfStatementContext)_localctx).trueStatement = statement();
			setState(297);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(295);
				match(ELSE);
				setState(296);
				((IfStatementContext)_localctx).falseStatement = statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(CASCParser.FOR, 0); }
		public ForExpressionContext forExpression() {
			return getRuleContext(ForExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			match(FOR);
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(300);
				match(T__3);
				}
			}

			setState(303);
			forExpression();
			setState(305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(304);
				match(T__5);
				}
			}

			setState(307);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForExpressionContext extends ParserRuleContext {
		public VarReferenceContext iterator;
		public ExpressionContext startExpr;
		public Token range;
		public ExpressionContext endExpr;
		public TerminalNode FROM() { return getToken(CASCParser.FROM, 0); }
		public VarReferenceContext varReference() {
			return getRuleContext(VarReferenceContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TO() { return getToken(CASCParser.TO, 0); }
		public TerminalNode UNTIL() { return getToken(CASCParser.UNTIL, 0); }
		public ForExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitForExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForExpressionContext forExpression() throws RecognitionException {
		ForExpressionContext _localctx = new ForExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_forExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			((ForExpressionContext)_localctx).iterator = varReference();
			setState(310);
			match(FROM);
			setState(311);
			((ForExpressionContext)_localctx).startExpr = expression(0);
			setState(312);
			((ForExpressionContext)_localctx).range = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==TO || _la==UNTIL) ) {
				((ForExpressionContext)_localctx).range = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(313);
			((ForExpressionContext)_localctx).endExpr = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_argument);
		try {
			setState(322);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(317);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(318);
				name();
				setState(319);
				match(T__8);
				setState(320);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__33) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
				{
				setState(324);
				expression(0);
				}
			}

			setState(331);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(327);
				match(T__4);
				setState(328);
				expression(0);
				}
				}
				setState(333);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarRefContext extends ExpressionContext {
		public Token NEG;
		public VarReferenceContext varReference() {
			return getRuleContext(VarReferenceContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public VarRefContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVarRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValContext extends ExpressionContext {
		public Token NEG;
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(CASCParser.PLUS, 0); }
		public AddContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfExprContext extends ExpressionContext {
		public ExpressionContext condition;
		public ExpressionContext trueExpression;
		public ExpressionContext falseExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IfExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitIfExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionalExpressionContext extends ExpressionContext {
		public Token cmp;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GREATER() { return getToken(CASCParser.GREATER, 0); }
		public TerminalNode LESS() { return getToken(CASCParser.LESS, 0); }
		public TerminalNode EQ() { return getToken(CASCParser.EQ, 0); }
		public TerminalNode NOT_EQ() { return getToken(CASCParser.NOT_EQ, 0); }
		public TerminalNode GREATER_EQ() { return getToken(CASCParser.GREATER_EQ, 0); }
		public TerminalNode LESS_EQ() { return getToken(CASCParser.LESS_EQ, 0); }
		public ConditionalExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstructorCallContext extends ExpressionContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public ConstructorCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitConstructorCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModAddContext extends ExpressionContext {
		public Token NEG;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(CASCParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ModAddContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivideContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SLASH() { return getToken(CASCParser.SLASH, 0); }
		public DivideContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitDivide(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModDivideContext extends ExpressionContext {
		public Token NEG;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SLASH() { return getToken(CASCParser.SLASH, 0); }
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ModDivideContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModDivide(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModMultiplyContext extends ExpressionContext {
		public Token NEG;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(CASCParser.STAR, 0); }
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ModMultiplyContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModMultiply(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallContext extends ExpressionContext {
		public ExpressionContext owner;
		public Token NEG;
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiplyContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(CASCParser.STAR, 0); }
		public MultiplyContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitMultiply(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuperCallContext extends ExpressionContext {
		public Token superCall;
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public SuperCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitSuperCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubtractContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public SubtractContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitSubtract(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModSubtractContext extends ExpressionContext {
		public Token NEG;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> MINUS() { return getTokens(CASCParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(CASCParser.MINUS, i);
		}
		public ModSubtractContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModSubtract(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 54;
		enterRecursionRule(_localctx, 54, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				{
				_localctx = new VarRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(336);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(335);
					((VarRefContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(338);
				varReference();
				}
				break;
			case 2:
				{
				_localctx = new SuperCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(339);
				((SuperCallContext)_localctx).superCall = match(T__33);
				setState(340);
				match(T__3);
				setState(342);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__33) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
					{
					setState(341);
					argument();
					}
				}

				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(344);
					match(T__4);
					setState(345);
					argument();
					}
					}
					setState(350);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(351);
				match(T__5);
				}
				break;
			case 3:
				{
				_localctx = new ConstructorCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(352);
				className();
				setState(353);
				match(T__3);
				setState(355);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__33) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
					{
					setState(354);
					argument();
					}
				}

				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(357);
					match(T__4);
					setState(358);
					argument();
					}
					}
					setState(363);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(364);
				match(T__5);
				}
				break;
			case 4:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(367);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(366);
					((FunctionCallContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(369);
				functionName();
				setState(370);
				match(T__3);
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__33) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
					{
					setState(371);
					argument();
					}
				}

				setState(378);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(374);
					match(T__4);
					setState(375);
					argument();
					}
					}
					setState(380);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(381);
				match(T__5);
				}
				break;
			case 5:
				{
				_localctx = new ModMultiplyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(383);
					((ModMultiplyContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(386);
				match(T__3);
				setState(387);
				expression(0);
				setState(388);
				match(STAR);
				setState(389);
				expression(0);
				setState(390);
				match(T__5);
				}
				break;
			case 6:
				{
				_localctx = new ModDivideContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(393);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(392);
					((ModDivideContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(395);
				match(T__3);
				setState(396);
				expression(0);
				setState(397);
				match(SLASH);
				setState(398);
				expression(0);
				setState(399);
				match(T__5);
				}
				break;
			case 7:
				{
				_localctx = new ModAddContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(402);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(401);
					((ModAddContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(404);
				match(T__3);
				setState(405);
				expression(0);
				setState(406);
				match(PLUS);
				setState(407);
				expression(0);
				setState(408);
				match(T__5);
				}
				break;
			case 8:
				{
				_localctx = new ModSubtractContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(411);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(410);
					((ModSubtractContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(413);
				match(T__3);
				setState(414);
				expression(0);
				setState(415);
				match(MINUS);
				setState(416);
				expression(0);
				setState(417);
				match(T__5);
				}
				break;
			case 9:
				{
				_localctx = new ValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(420);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(419);
					((ValContext)_localctx).NEG = match(MINUS);
					}
				}

				setState(422);
				value();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(479);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(477);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
					case 1:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(425);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(426);
						((ConditionalExpressionContext)_localctx).cmp = match(GREATER);
						setState(427);
						expression(17);
						}
						break;
					case 2:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(428);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(429);
						((ConditionalExpressionContext)_localctx).cmp = match(LESS);
						setState(430);
						expression(16);
						}
						break;
					case 3:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(431);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(432);
						((ConditionalExpressionContext)_localctx).cmp = match(EQ);
						setState(433);
						expression(15);
						}
						break;
					case 4:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(434);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(435);
						((ConditionalExpressionContext)_localctx).cmp = match(NOT_EQ);
						setState(436);
						expression(14);
						}
						break;
					case 5:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(437);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(438);
						((ConditionalExpressionContext)_localctx).cmp = match(GREATER_EQ);
						setState(439);
						expression(13);
						}
						break;
					case 6:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(440);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(441);
						((ConditionalExpressionContext)_localctx).cmp = match(LESS_EQ);
						setState(442);
						expression(12);
						}
						break;
					case 7:
						{
						_localctx = new IfExprContext(new ExpressionContext(_parentctx, _parentState));
						((IfExprContext)_localctx).condition = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(443);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(444);
						match(T__35);
						setState(445);
						((IfExprContext)_localctx).trueExpression = expression(0);
						setState(446);
						match(T__7);
						setState(447);
						((IfExprContext)_localctx).falseExpression = expression(11);
						}
						break;
					case 8:
						{
						_localctx = new MultiplyContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(449);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(450);
						match(STAR);
						setState(451);
						expression(9);
						}
						break;
					case 9:
						{
						_localctx = new DivideContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(452);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(453);
						match(SLASH);
						setState(454);
						expression(7);
						}
						break;
					case 10:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(455);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(456);
						match(PLUS);
						setState(457);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new SubtractContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(458);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(459);
						match(MINUS);
						setState(460);
						expression(3);
						}
						break;
					case 12:
						{
						_localctx = new FunctionCallContext(new ExpressionContext(_parentctx, _parentState));
						((FunctionCallContext)_localctx).owner = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(461);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(462);
						match(T__34);
						setState(463);
						functionName();
						setState(464);
						match(T__3);
						setState(466);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__33) | (1L << MINUS) | (1L << NUMBER) | (1L << STRING) | (1L << BOOL) | (1L << ID))) != 0)) {
							{
							setState(465);
							argument();
							}
						}

						setState(472);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__4) {
							{
							{
							setState(468);
							match(T__4);
							setState(469);
							argument();
							}
							}
							setState(474);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(475);
						match(T__5);
						}
						break;
					}
					} 
				}
				setState(481);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class VarReferenceContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public VarReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVarReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarReferenceContext varReference() throws RecognitionException {
		VarReferenceContext _localctx = new VarReferenceContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_varReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(482);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(CASCParser.NUMBER, 0); }
		public TerminalNode BOOL() { return getToken(CASCParser.BOOL, 0); }
		public TerminalNode STRING() { return getToken(CASCParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(484);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUMBER) | (1L << STRING) | (1L << BOOL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 27:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 8);
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 4);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 18);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3B\u01e9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\7\5L\n\5\f\5\16\5O\13\5\3\6"+
		"\3\6\5\6S\n\6\3\7\3\7\3\7\3\7\3\7\7\7Z\n\7\f\7\16\7]\13\7\5\7_\n\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\tl\n\t\f\t\16\to\13\t\5\tq"+
		"\n\t\3\t\3\t\3\t\5\tv\n\t\3\n\3\n\3\13\3\13\3\13\3\13\5\13~\n\13\3\f\3"+
		"\f\3\f\3\r\3\r\5\r\u0085\n\r\3\16\3\16\3\16\7\16\u008a\n\16\f\16\16\16"+
		"\u008d\13\16\3\16\3\16\3\16\7\16\u0092\n\16\f\16\16\16\u0095\13\16\3\16"+
		"\3\16\3\16\7\16\u009a\n\16\f\16\16\16\u009d\13\16\3\16\3\16\3\16\7\16"+
		"\u00a2\n\16\f\16\16\16\u00a5\13\16\3\16\3\16\3\16\7\16\u00aa\n\16\f\16"+
		"\16\16\u00ad\13\16\3\16\3\16\3\16\7\16\u00b2\n\16\f\16\16\16\u00b5\13"+
		"\16\3\16\3\16\3\16\7\16\u00ba\n\16\f\16\16\16\u00bd\13\16\3\16\3\16\3"+
		"\16\7\16\u00c2\n\16\f\16\16\16\u00c5\13\16\3\16\3\16\3\16\7\16\u00ca\n"+
		"\16\f\16\16\16\u00cd\13\16\3\16\3\16\3\16\7\16\u00d2\n\16\f\16\16\16\u00d5"+
		"\13\16\5\16\u00d7\n\16\3\17\3\17\3\17\7\17\u00dc\n\17\f\17\16\17\u00df"+
		"\13\17\3\20\3\20\3\20\6\20\u00e4\n\20\r\20\16\20\u00e5\3\21\3\21\7\21"+
		"\u00ea\n\21\f\21\16\21\u00ed\13\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\5\22\u00f9\n\22\3\23\3\23\3\23\5\23\u00fe\n\23\3\23\3"+
		"\23\5\23\u0102\n\23\3\23\3\23\3\24\3\24\3\24\5\24\u0109\n\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\5\25\u0111\n\25\3\25\3\25\3\25\3\26\3\26\5\26\u0118"+
		"\n\26\3\26\3\26\5\26\u011c\n\26\3\27\3\27\5\27\u0120\n\27\3\27\5\27\u0123"+
		"\n\27\3\27\3\27\5\27\u0127\n\27\3\27\3\27\3\27\5\27\u012c\n\27\3\30\3"+
		"\30\5\30\u0130\n\30\3\30\3\30\5\30\u0134\n\30\3\30\3\30\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\5\33\u0145\n\33\3\34"+
		"\5\34\u0148\n\34\3\34\3\34\7\34\u014c\n\34\f\34\16\34\u014f\13\34\3\35"+
		"\3\35\5\35\u0153\n\35\3\35\3\35\3\35\3\35\5\35\u0159\n\35\3\35\3\35\7"+
		"\35\u015d\n\35\f\35\16\35\u0160\13\35\3\35\3\35\3\35\3\35\5\35\u0166\n"+
		"\35\3\35\3\35\7\35\u016a\n\35\f\35\16\35\u016d\13\35\3\35\3\35\3\35\5"+
		"\35\u0172\n\35\3\35\3\35\3\35\5\35\u0177\n\35\3\35\3\35\7\35\u017b\n\35"+
		"\f\35\16\35\u017e\13\35\3\35\3\35\3\35\5\35\u0183\n\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\5\35\u018c\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\5\35\u0195\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u019e\n\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u01a7\n\35\3\35\5\35\u01aa\n\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35"+
		"\u01d5\n\35\3\35\3\35\7\35\u01d9\n\35\f\35\16\35\u01dc\13\35\3\35\3\35"+
		"\7\35\u01e0\n\35\f\35\16\35\u01e3\13\35\3\36\3\36\3\37\3\37\3\37\2\38"+
		" \2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<\2\16"+
		"\3\2\f\r\3\2\20\21\3\2\22\23\3\2\24\25\3\2\26\27\3\2\30\31\3\2\32\33\3"+
		"\2\34\35\3\2\36\37\3\2 !\3\2/\60\3\2>@\2\u0223\2>\3\2\2\2\4A\3\2\2\2\6"+
		"G\3\2\2\2\bM\3\2\2\2\nP\3\2\2\2\fT\3\2\2\2\16b\3\2\2\2\20e\3\2\2\2\22"+
		"w\3\2\2\2\24y\3\2\2\2\26\177\3\2\2\2\30\u0084\3\2\2\2\32\u00d6\3\2\2\2"+
		"\34\u00d8\3\2\2\2\36\u00e0\3\2\2\2 \u00e7\3\2\2\2\"\u00f8\3\2\2\2$\u00fa"+
		"\3\2\2\2&\u0105\3\2\2\2(\u010d\3\2\2\2*\u011b\3\2\2\2,\u011d\3\2\2\2."+
		"\u012d\3\2\2\2\60\u0137\3\2\2\2\62\u013d\3\2\2\2\64\u0144\3\2\2\2\66\u0147"+
		"\3\2\2\28\u01a9\3\2\2\2:\u01e4\3\2\2\2<\u01e6\3\2\2\2>?\5\4\3\2?@\7\2"+
		"\2\3@\3\3\2\2\2AB\7\'\2\2BC\5\6\4\2CD\7\3\2\2DE\5\b\5\2EF\7\4\2\2F\5\3"+
		"\2\2\2GH\7A\2\2H\7\3\2\2\2IL\5\16\b\2JL\5\n\6\2KI\3\2\2\2KJ\3\2\2\2LO"+
		"\3\2\2\2MK\3\2\2\2MN\3\2\2\2N\t\3\2\2\2OM\3\2\2\2PR\5\f\7\2QS\5 \21\2"+
		"RQ\3\2\2\2RS\3\2\2\2S\13\3\2\2\2TU\7\5\2\2U^\7\6\2\2V[\5\24\13\2WX\7\7"+
		"\2\2XZ\5\24\13\2YW\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\_\3\2\2\2]"+
		"[\3\2\2\2^V\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\7\b\2\2a\r\3\2\2\2bc\5\20\t"+
		"\2cd\5 \21\2d\17\3\2\2\2ef\7\t\2\2fg\5\22\n\2gp\7\6\2\2hm\5\24\13\2ij"+
		"\7\7\2\2jl\5\24\13\2ki\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2nq\3\2\2\2"+
		"om\3\2\2\2ph\3\2\2\2pq\3\2\2\2qr\3\2\2\2ru\7\b\2\2st\7\n\2\2tv\5\30\r"+
		"\2us\3\2\2\2uv\3\2\2\2v\21\3\2\2\2wx\7A\2\2x\23\3\2\2\2yz\7A\2\2z{\7\n"+
		"\2\2{}\5\30\r\2|~\5\26\f\2}|\3\2\2\2}~\3\2\2\2~\25\3\2\2\2\177\u0080\7"+
		"\13\2\2\u0080\u0081\58\35\2\u0081\27\3\2\2\2\u0082\u0085\5\32\16\2\u0083"+
		"\u0085\5\34\17\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085\31\3\2"+
		"\2\2\u0086\u008b\t\2\2\2\u0087\u0088\7\16\2\2\u0088\u008a\7\17\2\2\u0089"+
		"\u0087\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2"+
		"\2\2\u008c\u00d7\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u0093\t\3\2\2\u008f"+
		"\u0090\7\16\2\2\u0090\u0092\7\17\2\2\u0091\u008f\3\2\2\2\u0092\u0095\3"+
		"\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u00d7\3\2\2\2\u0095"+
		"\u0093\3\2\2\2\u0096\u009b\t\4\2\2\u0097\u0098\7\16\2\2\u0098\u009a\7"+
		"\17\2\2\u0099\u0097\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009b"+
		"\u009c\3\2\2\2\u009c\u00d7\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a3\t\5"+
		"\2\2\u009f\u00a0\7\16\2\2\u00a0\u00a2\7\17\2\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00d7\3\2"+
		"\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00ab\t\6\2\2\u00a7\u00a8\7\16\2\2\u00a8"+
		"\u00aa\7\17\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3"+
		"\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00d7\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae"+
		"\u00b3\t\7\2\2\u00af\u00b0\7\16\2\2\u00b0\u00b2\7\17\2\2\u00b1\u00af\3"+
		"\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00d7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00bb\t\b\2\2\u00b7\u00b8\7\16"+
		"\2\2\u00b8\u00ba\7\17\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00bd\3\2\2\2\u00bb"+
		"\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00d7\3\2\2\2\u00bd\u00bb\3\2"+
		"\2\2\u00be\u00c3\t\t\2\2\u00bf\u00c0\7\16\2\2\u00c0\u00c2\7\17\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2"+
		"\2\2\u00c4\u00d7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00cb\t\n\2\2\u00c7"+
		"\u00c8\7\16\2\2\u00c8\u00ca\7\17\2\2\u00c9\u00c7\3\2\2\2\u00ca\u00cd\3"+
		"\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00d7\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00d3\t\13\2\2\u00cf\u00d0\7\16\2\2\u00d0\u00d2\7"+
		"\17\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3"+
		"\u00d4\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6\u0086\3\2"+
		"\2\2\u00d6\u008e\3\2\2\2\u00d6\u0096\3\2\2\2\u00d6\u009e\3\2\2\2\u00d6"+
		"\u00a6\3\2\2\2\u00d6\u00ae\3\2\2\2\u00d6\u00b6\3\2\2\2\u00d6\u00be\3\2"+
		"\2\2\u00d6\u00c6\3\2\2\2\u00d6\u00ce\3\2\2\2\u00d7\33\3\2\2\2\u00d8\u00dd"+
		"\5\36\20\2\u00d9\u00da\7\16\2\2\u00da\u00dc\7\17\2\2\u00db\u00d9\3\2\2"+
		"\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\35"+
		"\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e3\7A\2\2\u00e1\u00e2\7\"\2\2\u00e2"+
		"\u00e4\7A\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2"+
		"\2\2\u00e5\u00e6\3\2\2\2\u00e6\37\3\2\2\2\u00e7\u00eb\7\3\2\2\u00e8\u00ea"+
		"\5\"\22\2\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2"+
		"\u00eb\u00ec\3\2\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef"+
		"\7\4\2\2\u00ef!\3\2\2\2\u00f0\u00f9\5 \21\2\u00f1\u00f9\5$\23\2\u00f2"+
		"\u00f9\5&\24\2\u00f3\u00f9\5(\25\2\u00f4\u00f9\5.\30\2\u00f5\u00f9\5*"+
		"\26\2\u00f6\u00f9\5,\27\2\u00f7\u00f9\58\35\2\u00f8\u00f0\3\2\2\2\u00f8"+
		"\u00f1\3\2\2\2\u00f8\u00f2\3\2\2\2\u00f8\u00f3\3\2\2\2\u00f8\u00f4\3\2"+
		"\2\2\u00f8\u00f5\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9"+
		"#\3\2\2\2\u00fa\u00fd\5\62\32\2\u00fb\u00fc\7\n\2\2\u00fc\u00fe\5\30\r"+
		"\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101"+
		"\7#\2\2\u0100\u0102\7\64\2\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2\2\u0102"+
		"\u0103\3\2\2\2\u0103\u0104\58\35\2\u0104%\3\2\2\2\u0105\u0106\7\61\2\2"+
		"\u0106\u0108\7\6\2\2\u0107\u0109\7\64\2\2\u0108\u0107\3\2\2\2\u0108\u0109"+
		"\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\58\35\2\u010b\u010c\7\b\2\2\u010c"+
		"\'\3\2\2\2\u010d\u010e\7\62\2\2\u010e\u0110\7\6\2\2\u010f\u0111\7\64\2"+
		"\2\u0110\u010f\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113"+
		"\58\35\2\u0113\u0114\7\b\2\2\u0114)\3\2\2\2\u0115\u0117\7,\2\2\u0116\u0118"+
		"\7\64\2\2\u0117\u0116\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\3\2\2\2"+
		"\u0119\u011c\58\35\2\u011a\u011c\7,\2\2\u011b\u0115\3\2\2\2\u011b\u011a"+
		"\3\2\2\2\u011c+\3\2\2\2\u011d\u011f\7*\2\2\u011e\u0120\7\6\2\2\u011f\u011e"+
		"\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0122\3\2\2\2\u0121\u0123\7\64\2\2"+
		"\u0122\u0121\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0126"+
		"\58\35\2\u0125\u0127\7\b\2\2\u0126\u0125\3\2\2\2\u0126\u0127\3\2\2\2\u0127"+
		"\u0128\3\2\2\2\u0128\u012b\5\"\22\2\u0129\u012a\7+\2\2\u012a\u012c\5\""+
		"\22\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c-\3\2\2\2\u012d\u012f"+
		"\7-\2\2\u012e\u0130\7\6\2\2\u012f\u012e\3\2\2\2\u012f\u0130\3\2\2\2\u0130"+
		"\u0131\3\2\2\2\u0131\u0133\5\60\31\2\u0132\u0134\7\b\2\2\u0133\u0132\3"+
		"\2\2\2\u0133\u0134\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0136\5\"\22\2\u0136"+
		"/\3\2\2\2\u0137\u0138\5:\36\2\u0138\u0139\7.\2\2\u0139\u013a\58\35\2\u013a"+
		"\u013b\t\f\2\2\u013b\u013c\58\35\2\u013c\61\3\2\2\2\u013d\u013e\7A\2\2"+
		"\u013e\63\3\2\2\2\u013f\u0145\58\35\2\u0140\u0141\5\62\32\2\u0141\u0142"+
		"\7\13\2\2\u0142\u0143\58\35\2\u0143\u0145\3\2\2\2\u0144\u013f\3\2\2\2"+
		"\u0144\u0140\3\2\2\2\u0145\65\3\2\2\2\u0146\u0148\58\35\2\u0147\u0146"+
		"\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u014d\3\2\2\2\u0149\u014a\7\7\2\2\u014a"+
		"\u014c\58\35\2\u014b\u0149\3\2\2\2\u014c\u014f\3\2\2\2\u014d\u014b\3\2"+
		"\2\2\u014d\u014e\3\2\2\2\u014e\67\3\2\2\2\u014f\u014d\3\2\2\2\u0150\u0152"+
		"\b\35\1\2\u0151\u0153\7\64\2\2\u0152\u0151\3\2\2\2\u0152\u0153\3\2\2\2"+
		"\u0153\u0154\3\2\2\2\u0154\u01aa\5:\36\2\u0155\u0156\7$\2\2\u0156\u0158"+
		"\7\6\2\2\u0157\u0159\5\64\33\2\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2\2"+
		"\u0159\u015e\3\2\2\2\u015a\u015b\7\7\2\2\u015b\u015d\5\64\33\2\u015c\u015a"+
		"\3\2\2\2\u015d\u0160\3\2\2\2\u015e\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f"+
		"\u0161\3\2\2\2\u0160\u015e\3\2\2\2\u0161\u01aa\7\b\2\2\u0162\u0163\5\6"+
		"\4\2\u0163\u0165\7\6\2\2\u0164\u0166\5\64\33\2\u0165\u0164\3\2\2\2\u0165"+
		"\u0166\3\2\2\2\u0166\u016b\3\2\2\2\u0167\u0168\7\7\2\2\u0168\u016a\5\64"+
		"\33\2\u0169\u0167\3\2\2\2\u016a\u016d\3\2\2\2\u016b\u0169\3\2\2\2\u016b"+
		"\u016c\3\2\2\2\u016c\u016e\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u016f\7\b"+
		"\2\2\u016f\u01aa\3\2\2\2\u0170\u0172\7\64\2\2\u0171\u0170\3\2\2\2\u0171"+
		"\u0172\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0174\5\22\n\2\u0174\u0176\7"+
		"\6\2\2\u0175\u0177\5\64\33\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2\2\2\u0177"+
		"\u017c\3\2\2\2\u0178\u0179\7\7\2\2\u0179\u017b\5\64\33\2\u017a\u0178\3"+
		"\2\2\2\u017b\u017e\3\2\2\2\u017c\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"\u017f\3\2\2\2\u017e\u017c\3\2\2\2\u017f\u0180\7\b\2\2\u0180\u01aa\3\2"+
		"\2\2\u0181\u0183\7\64\2\2\u0182\u0181\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"\u0184\3\2\2\2\u0184\u0185\7\6\2\2\u0185\u0186\58\35\2\u0186\u0187\7\65"+
		"\2\2\u0187\u0188\58\35\2\u0188\u0189\7\b\2\2\u0189\u01aa\3\2\2\2\u018a"+
		"\u018c\7\64\2\2\u018b\u018a\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018d\3"+
		"\2\2\2\u018d\u018e\7\6\2\2\u018e\u018f\58\35\2\u018f\u0190\7\66\2\2\u0190"+
		"\u0191\58\35\2\u0191\u0192\7\b\2\2\u0192\u01aa\3\2\2\2\u0193\u0195\7\64"+
		"\2\2\u0194\u0193\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196\3\2\2\2\u0196"+
		"\u0197\7\6\2\2\u0197\u0198\58\35\2\u0198\u0199\7\63\2\2\u0199\u019a\5"+
		"8\35\2\u019a\u019b\7\b\2\2\u019b\u01aa\3\2\2\2\u019c\u019e\7\64\2\2\u019d"+
		"\u019c\3\2\2\2\u019d\u019e\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\7\6"+
		"\2\2\u01a0\u01a1\58\35\2\u01a1\u01a2\7\64\2\2\u01a2\u01a3\58\35\2\u01a3"+
		"\u01a4\7\b\2\2\u01a4\u01aa\3\2\2\2\u01a5\u01a7\7\64\2\2\u01a6\u01a5\3"+
		"\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01aa\5<\37\2\u01a9"+
		"\u0150\3\2\2\2\u01a9\u0155\3\2\2\2\u01a9\u0162\3\2\2\2\u01a9\u0171\3\2"+
		"\2\2\u01a9\u0182\3\2\2\2\u01a9\u018b\3\2\2\2\u01a9\u0194\3\2\2\2\u01a9"+
		"\u019d\3\2\2\2\u01a9\u01a6\3\2\2\2\u01aa\u01e1\3\2\2\2\u01ab\u01ac\f\22"+
		"\2\2\u01ac\u01ad\78\2\2\u01ad\u01e0\58\35\23\u01ae\u01af\f\21\2\2\u01af"+
		"\u01b0\79\2\2\u01b0\u01e0\58\35\22\u01b1\u01b2\f\20\2\2\u01b2\u01b3\7"+
		"<\2\2\u01b3\u01e0\58\35\21\u01b4\u01b5\f\17\2\2\u01b5\u01b6\7=\2\2\u01b6"+
		"\u01e0\58\35\20\u01b7\u01b8\f\16\2\2\u01b8\u01b9\7:\2\2\u01b9\u01e0\5"+
		"8\35\17\u01ba\u01bb\f\r\2\2\u01bb\u01bc\7;\2\2\u01bc\u01e0\58\35\16\u01bd"+
		"\u01be\f\f\2\2\u01be\u01bf\7&\2\2\u01bf\u01c0\58\35\2\u01c0\u01c1\7\n"+
		"\2\2\u01c1\u01c2\58\35\r\u01c2\u01e0\3\2\2\2\u01c3\u01c4\f\n\2\2\u01c4"+
		"\u01c5\7\65\2\2\u01c5\u01e0\58\35\13\u01c6\u01c7\f\b\2\2\u01c7\u01c8\7"+
		"\66\2\2\u01c8\u01e0\58\35\t\u01c9\u01ca\f\6\2\2\u01ca\u01cb\7\63\2\2\u01cb"+
		"\u01e0\58\35\7\u01cc\u01cd\f\4\2\2\u01cd\u01ce\7\64\2\2\u01ce\u01e0\5"+
		"8\35\5\u01cf\u01d0\f\24\2\2\u01d0\u01d1\7%\2\2\u01d1\u01d2\5\22\n\2\u01d2"+
		"\u01d4\7\6\2\2\u01d3\u01d5\5\64\33\2\u01d4\u01d3\3\2\2\2\u01d4\u01d5\3"+
		"\2\2\2\u01d5\u01da\3\2\2\2\u01d6\u01d7\7\7\2\2\u01d7\u01d9\5\64\33\2\u01d8"+
		"\u01d6\3\2\2\2\u01d9\u01dc\3\2\2\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2"+
		"\2\2\u01db\u01dd\3\2\2\2\u01dc\u01da\3\2\2\2\u01dd\u01de\7\b\2\2\u01de"+
		"\u01e0\3\2\2\2\u01df\u01ab\3\2\2\2\u01df\u01ae\3\2\2\2\u01df\u01b1\3\2"+
		"\2\2\u01df\u01b4\3\2\2\2\u01df\u01b7\3\2\2\2\u01df\u01ba\3\2\2\2\u01df"+
		"\u01bd\3\2\2\2\u01df\u01c3\3\2\2\2\u01df\u01c6\3\2\2\2\u01df\u01c9\3\2"+
		"\2\2\u01df\u01cc\3\2\2\2\u01df\u01cf\3\2\2\2\u01e0\u01e3\3\2\2\2\u01e1"+
		"\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e29\3\2\2\2\u01e3\u01e1\3\2\2\2"+
		"\u01e4\u01e5\7A\2\2\u01e5;\3\2\2\2\u01e6\u01e7\t\r\2\2\u01e7=\3\2\2\2"+
		"<KMR[^mpu}\u0084\u008b\u0093\u009b\u00a3\u00ab\u00b3\u00bb\u00c3\u00cb"+
		"\u00d3\u00d6\u00dd\u00e5\u00eb\u00f8\u00fd\u0101\u0108\u0110\u0117\u011b"+
		"\u011f\u0122\u0126\u012b\u012f\u0133\u0144\u0147\u014d\u0152\u0158\u015e"+
		"\u0165\u016b\u0171\u0176\u017c\u0182\u018b\u0194\u019d\u01a6\u01a9\u01d4"+
		"\u01da\u01df\u01e1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}