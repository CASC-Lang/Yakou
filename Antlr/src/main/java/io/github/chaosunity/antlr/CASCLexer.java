// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CASCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, CLASS=6, VARIABLE=7, PRINT=8, 
		PRINTLN=9, EQUALS=10, NUMBER=11, STRING=12, ID=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "CHAR", "DIGIT", "UNICODE", "CLASS", 
			"VARIABLE", "PRINT", "PRINTLN", "EQUALS", "NUMBER", "STRING", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "':'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "CLASS", "VARIABLE", "PRINT", "PRINTLN", 
			"EQUALS", "NUMBER", "STRING", "ID", "WS"
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


	public CASCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CASC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20y\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\5\7\61\n\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n>\n\n\3\13\3\13\3\13\3\13\3\13\5\13"+
		"E\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fN\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\5\rZ\n\r\3\16\3\16\3\17\6\17_\n\17\r\17\16\17`\3\20\3\20"+
		"\7\20e\n\20\f\20\16\20h\13\20\3\20\3\20\3\21\3\21\3\21\6\21o\n\21\r\21"+
		"\16\21p\3\22\6\22t\n\22\r\22\16\22u\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13"+
		"\7\r\2\17\2\21\2\23\b\25\t\27\n\31\13\33\f\35\r\37\16!\17#\20\3\2\6\4"+
		"\2C\\c|\4\2??\u8ce8\u8ce8\3\2\62;\5\2\13\f\17\17\"\"\2\177\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t+\3\2\2\2"+
		"\13-\3\2\2\2\r\60\3\2\2\2\17\62\3\2\2\2\21\64\3\2\2\2\23=\3\2\2\2\25D"+
		"\3\2\2\2\27M\3\2\2\2\31Y\3\2\2\2\33[\3\2\2\2\35^\3\2\2\2\37b\3\2\2\2!"+
		"n\3\2\2\2#s\3\2\2\2%&\7}\2\2&\4\3\2\2\2\'(\7\177\2\2(\6\3\2\2\2)*\7<\2"+
		"\2*\b\3\2\2\2+,\7*\2\2,\n\3\2\2\2-.\7+\2\2.\f\3\2\2\2/\61\t\2\2\2\60/"+
		"\3\2\2\2\61\16\3\2\2\2\62\63\4\62;\2\63\20\3\2\2\2\64\65\4\u0082\1\2\65"+
		"\22\3\2\2\2\66\67\7e\2\2\678\7n\2\289\7c\2\29:\7u\2\2:>\7u\2\2;<\7\u9860"+
		"\2\2<>\7\u5227\2\2=\66\3\2\2\2=;\3\2\2\2>\24\3\2\2\2?@\7x\2\2@A\7c\2\2"+
		"AE\7t\2\2BC\7\u8b8c\2\2CE\7\u657a\2\2D?\3\2\2\2DB\3\2\2\2E\26\3\2\2\2"+
		"FG\7r\2\2GH\7t\2\2HI\7k\2\2IJ\7p\2\2JN\7v\2\2KL\7\u5372\2\2LN\7\u51fc"+
		"\2\2MF\3\2\2\2MK\3\2\2\2N\30\3\2\2\2OP\7r\2\2PQ\7t\2\2QR\7k\2\2RS\7p\2"+
		"\2ST\7v\2\2TU\7n\2\2UZ\7p\2\2VW\7\u5372\2\2WX\7\u51fc\2\2XZ\7\u884e\2"+
		"\2YO\3\2\2\2YV\3\2\2\2Z\32\3\2\2\2[\\\t\3\2\2\\\34\3\2\2\2]_\t\4\2\2^"+
		"]\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2\2a\36\3\2\2\2bf\7$\2\2ce\13\2\2"+
		"\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2ij\7$\2"+
		"\2j \3\2\2\2ko\5\r\7\2lo\5\17\b\2mo\5\21\t\2nk\3\2\2\2nl\3\2\2\2nm\3\2"+
		"\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\"\3\2\2\2rt\t\5\2\2sr\3\2\2\2tu\3"+
		"\2\2\2us\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\b\22\2\2x$\3\2\2\2\r\2\60=DMY`"+
		"fnpu\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}