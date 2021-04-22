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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, CLASS=20, VARIABLE=21, PRINT=22, PRINTLN=23, EQUALS=24, 
		NUMBER=25, STRING=26, ID=27, QUALIFIED_NAME=28, WS=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "CHAR", "DIGIT", "UNICODE", "CLASS", "VARIABLE", "PRINT", 
			"PRINTLN", "EQUALS", "NUMBER", "STRING", "ID", "QUALIFIED_NAME", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "':'", "'('", "')'", "'='", "'boolean'", "'['", "']'", 
			"'string'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", 
			"'double'", "'void'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "CLASS", "VARIABLE", 
			"PRINT", "PRINTLN", "EQUALS", "NUMBER", "STRING", "ID", "QUALIFIED_NAME", 
			"WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u00e0\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\25\5\25\u0091\n\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\5\30\u009e\n\30\3\31\3\31\3\31\3\31\3\31\5\31\u00a5\n\31\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u00ae\n\32\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00ba\n\33\3\34\3\34\3\35\6\35\u00bf"+
		"\n\35\r\35\16\35\u00c0\3\36\3\36\7\36\u00c5\n\36\f\36\16\36\u00c8\13\36"+
		"\3\36\3\36\3\37\3\37\3\37\6\37\u00cf\n\37\r\37\16\37\u00d0\3 \3 \3 \6"+
		" \u00d6\n \r \16 \u00d7\3!\6!\u00db\n!\r!\16!\u00dc\3!\3!\2\2\"\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\2+\2-\2/\26\61\27\63\30\65\31\67\329\33;\34=\35?\36"+
		"A\37\3\2\6\4\2C\\c|\4\2??\u8ce8\u8ce8\3\2\62;\5\2\13\f\17\17\"\"\2\u00e7"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\3C\3\2\2\2\5E\3\2\2\2\7G\3\2\2\2\tI\3\2\2\2\13K\3\2\2\2\rM\3\2"+
		"\2\2\17O\3\2\2\2\21W\3\2\2\2\23Y\3\2\2\2\25[\3\2\2\2\27b\3\2\2\2\31g\3"+
		"\2\2\2\33l\3\2\2\2\35r\3\2\2\2\37v\3\2\2\2!{\3\2\2\2#\u0081\3\2\2\2%\u0088"+
		"\3\2\2\2\'\u008d\3\2\2\2)\u0090\3\2\2\2+\u0092\3\2\2\2-\u0094\3\2\2\2"+
		"/\u009d\3\2\2\2\61\u00a4\3\2\2\2\63\u00ad\3\2\2\2\65\u00b9\3\2\2\2\67"+
		"\u00bb\3\2\2\29\u00be\3\2\2\2;\u00c2\3\2\2\2=\u00ce\3\2\2\2?\u00d2\3\2"+
		"\2\2A\u00da\3\2\2\2CD\7}\2\2D\4\3\2\2\2EF\7\177\2\2F\6\3\2\2\2GH\7<\2"+
		"\2H\b\3\2\2\2IJ\7*\2\2J\n\3\2\2\2KL\7+\2\2L\f\3\2\2\2MN\7?\2\2N\16\3\2"+
		"\2\2OP\7d\2\2PQ\7q\2\2QR\7q\2\2RS\7n\2\2ST\7g\2\2TU\7c\2\2UV\7p\2\2V\20"+
		"\3\2\2\2WX\7]\2\2X\22\3\2\2\2YZ\7_\2\2Z\24\3\2\2\2[\\\7u\2\2\\]\7v\2\2"+
		"]^\7t\2\2^_\7k\2\2_`\7p\2\2`a\7i\2\2a\26\3\2\2\2bc\7e\2\2cd\7j\2\2de\7"+
		"c\2\2ef\7t\2\2f\30\3\2\2\2gh\7d\2\2hi\7{\2\2ij\7v\2\2jk\7g\2\2k\32\3\2"+
		"\2\2lm\7u\2\2mn\7j\2\2no\7q\2\2op\7t\2\2pq\7v\2\2q\34\3\2\2\2rs\7k\2\2"+
		"st\7p\2\2tu\7v\2\2u\36\3\2\2\2vw\7n\2\2wx\7q\2\2xy\7p\2\2yz\7i\2\2z \3"+
		"\2\2\2{|\7h\2\2|}\7n\2\2}~\7q\2\2~\177\7c\2\2\177\u0080\7v\2\2\u0080\""+
		"\3\2\2\2\u0081\u0082\7f\2\2\u0082\u0083\7q\2\2\u0083\u0084\7w\2\2\u0084"+
		"\u0085\7d\2\2\u0085\u0086\7n\2\2\u0086\u0087\7g\2\2\u0087$\3\2\2\2\u0088"+
		"\u0089\7x\2\2\u0089\u008a\7q\2\2\u008a\u008b\7k\2\2\u008b\u008c\7f\2\2"+
		"\u008c&\3\2\2\2\u008d\u008e\7.\2\2\u008e(\3\2\2\2\u008f\u0091\t\2\2\2"+
		"\u0090\u008f\3\2\2\2\u0091*\3\2\2\2\u0092\u0093\4\62;\2\u0093,\3\2\2\2"+
		"\u0094\u0095\4\u0082\1\2\u0095.\3\2\2\2\u0096\u0097\7e\2\2\u0097\u0098"+
		"\7n\2\2\u0098\u0099\7c\2\2\u0099\u009a\7u\2\2\u009a\u009e\7u\2\2\u009b"+
		"\u009c\7\u9860\2\2\u009c\u009e\7\u5227\2\2\u009d\u0096\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009e\60\3\2\2\2\u009f\u00a0\7x\2\2\u00a0\u00a1\7c\2\2\u00a1"+
		"\u00a5\7t\2\2\u00a2\u00a3\7\u8b8c\2\2\u00a3\u00a5\7\u657a\2\2\u00a4\u009f"+
		"\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\62\3\2\2\2\u00a6\u00a7\7r\2\2\u00a7"+
		"\u00a8\7t\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7p\2\2\u00aa\u00ae\7v\2\2"+
		"\u00ab\u00ac\7\u5372\2\2\u00ac\u00ae\7\u51fc\2\2\u00ad\u00a6\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ae\64\3\2\2\2\u00af\u00b0\7r\2\2\u00b0\u00b1\7t\2\2"+
		"\u00b1\u00b2\7k\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b4\7v\2\2\u00b4\u00b5"+
		"\7n\2\2\u00b5\u00ba\7p\2\2\u00b6\u00b7\7\u5372\2\2\u00b7\u00b8\7\u51fc"+
		"\2\2\u00b8\u00ba\7\u884e\2\2\u00b9\u00af\3\2\2\2\u00b9\u00b6\3\2\2\2\u00ba"+
		"\66\3\2\2\2\u00bb\u00bc\t\3\2\2\u00bc8\3\2\2\2\u00bd\u00bf\t\4\2\2\u00be"+
		"\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2"+
		"\2\2\u00c1:\3\2\2\2\u00c2\u00c6\7$\2\2\u00c3\u00c5\13\2\2\2\u00c4\u00c3"+
		"\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7"+
		"\u00c9\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c9\u00ca\7$\2\2\u00ca<\3\2\2\2\u00cb"+
		"\u00cf\5)\25\2\u00cc\u00cf\5+\26\2\u00cd\u00cf\5-\27\2\u00ce\u00cb\3\2"+
		"\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0"+
		"\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1>\3\2\2\2\u00d2\u00d5\5=\37\2"+
		"\u00d3\u00d4\7\60\2\2\u00d4\u00d6\5=\37\2\u00d5\u00d3\3\2\2\2\u00d6\u00d7"+
		"\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8@\3\2\2\2\u00d9"+
		"\u00db\t\5\2\2\u00da\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00da\3\2"+
		"\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\b!\2\2\u00df"+
		"B\3\2\2\2\16\2\u0090\u009d\u00a4\u00ad\u00b9\u00c0\u00c6\u00ce\u00d0\u00d7"+
		"\u00dc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}