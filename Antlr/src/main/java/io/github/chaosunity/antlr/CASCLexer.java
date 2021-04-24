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
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, CLASS=23, FUNC=24, VARIABLE=25, 
		PRINT=26, PRINTLN=27, EQUALS=28, NUMBER=29, STRING=30, ID=31, QUALIFIED_NAME=32, 
		WS=33;
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
			"T__17", "T__18", "T__19", "T__20", "T__21", "CHAR", "DIGIT", "UNICODE", 
			"CLASS", "FUNC", "VARIABLE", "PRINT", "PRINTLN", "EQUALS", "NUMBER", 
			"STRING", "ID", "QUALIFIED_NAME", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "')'", "':'", "'='", "'boolean'", "'\u5E03\u6797'", 
			"'['", "']'", "'string'", "'\u5B57\u4E32'", "'char'", "'\u5B57\u5143'", 
			"'byte'", "'short'", "'int'", "'long'", "'float'", "'double'", "'void'", 
			"','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "CLASS", 
			"FUNC", "VARIABLE", "PRINT", "PRINTLN", "EQUALS", "NUMBER", "STRING", 
			"ID", "QUALIFIED_NAME", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2#\u00f9\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\30\5\30\u00a2\n\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\5\33\u00af\n\33\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u00b7\n\34\3"+
		"\35\3\35\3\35\3\35\3\35\5\35\u00be\n\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\5\36\u00c7\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\5\37\u00d3\n\37\3 \3 \3!\6!\u00d8\n!\r!\16!\u00d9\3\"\3\"\7\"\u00de\n"+
		"\"\f\"\16\"\u00e1\13\"\3\"\3\"\3#\3#\3#\6#\u00e8\n#\r#\16#\u00e9\3$\3"+
		"$\3$\6$\u00ef\n$\r$\16$\u00f0\3%\6%\u00f4\n%\r%\16%\u00f5\3%\3%\2\2&\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"+
		"\21!\22#\23%\24\'\25)\26+\27-\30/\2\61\2\63\2\65\31\67\329\33;\34=\35"+
		"?\36A\37C E!G\"I#\3\2\6\4\2C\\c|\4\2??\u8ce8\u8ce8\3\2\62;\5\2\13\f\17"+
		"\17\"\"\2\u0101\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\3K\3\2\2\2"+
		"\5M\3\2\2\2\7O\3\2\2\2\tQ\3\2\2\2\13S\3\2\2\2\rU\3\2\2\2\17W\3\2\2\2\21"+
		"_\3\2\2\2\23b\3\2\2\2\25d\3\2\2\2\27f\3\2\2\2\31m\3\2\2\2\33p\3\2\2\2"+
		"\35u\3\2\2\2\37x\3\2\2\2!}\3\2\2\2#\u0083\3\2\2\2%\u0087\3\2\2\2\'\u008c"+
		"\3\2\2\2)\u0092\3\2\2\2+\u0099\3\2\2\2-\u009e\3\2\2\2/\u00a1\3\2\2\2\61"+
		"\u00a3\3\2\2\2\63\u00a5\3\2\2\2\65\u00ae\3\2\2\2\67\u00b6\3\2\2\29\u00bd"+
		"\3\2\2\2;\u00c6\3\2\2\2=\u00d2\3\2\2\2?\u00d4\3\2\2\2A\u00d7\3\2\2\2C"+
		"\u00db\3\2\2\2E\u00e7\3\2\2\2G\u00eb\3\2\2\2I\u00f3\3\2\2\2KL\7}\2\2L"+
		"\4\3\2\2\2MN\7\177\2\2N\6\3\2\2\2OP\7*\2\2P\b\3\2\2\2QR\7+\2\2R\n\3\2"+
		"\2\2ST\7<\2\2T\f\3\2\2\2UV\7?\2\2V\16\3\2\2\2WX\7d\2\2XY\7q\2\2YZ\7q\2"+
		"\2Z[\7n\2\2[\\\7g\2\2\\]\7c\2\2]^\7p\2\2^\20\3\2\2\2_`\7\u5e05\2\2`a\7"+
		"\u6799\2\2a\22\3\2\2\2bc\7]\2\2c\24\3\2\2\2de\7_\2\2e\26\3\2\2\2fg\7u"+
		"\2\2gh\7v\2\2hi\7t\2\2ij\7k\2\2jk\7p\2\2kl\7i\2\2l\30\3\2\2\2mn\7\u5b59"+
		"\2\2no\7\u4e34\2\2o\32\3\2\2\2pq\7e\2\2qr\7j\2\2rs\7c\2\2st\7t\2\2t\34"+
		"\3\2\2\2uv\7\u5b59\2\2vw\7\u5145\2\2w\36\3\2\2\2xy\7d\2\2yz\7{\2\2z{\7"+
		"v\2\2{|\7g\2\2| \3\2\2\2}~\7u\2\2~\177\7j\2\2\177\u0080\7q\2\2\u0080\u0081"+
		"\7t\2\2\u0081\u0082\7v\2\2\u0082\"\3\2\2\2\u0083\u0084\7k\2\2\u0084\u0085"+
		"\7p\2\2\u0085\u0086\7v\2\2\u0086$\3\2\2\2\u0087\u0088\7n\2\2\u0088\u0089"+
		"\7q\2\2\u0089\u008a\7p\2\2\u008a\u008b\7i\2\2\u008b&\3\2\2\2\u008c\u008d"+
		"\7h\2\2\u008d\u008e\7n\2\2\u008e\u008f\7q\2\2\u008f\u0090\7c\2\2\u0090"+
		"\u0091\7v\2\2\u0091(\3\2\2\2\u0092\u0093\7f\2\2\u0093\u0094\7q\2\2\u0094"+
		"\u0095\7w\2\2\u0095\u0096\7d\2\2\u0096\u0097\7n\2\2\u0097\u0098\7g\2\2"+
		"\u0098*\3\2\2\2\u0099\u009a\7x\2\2\u009a\u009b\7q\2\2\u009b\u009c\7k\2"+
		"\2\u009c\u009d\7f\2\2\u009d,\3\2\2\2\u009e\u009f\7.\2\2\u009f.\3\2\2\2"+
		"\u00a0\u00a2\t\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\60\3\2\2\2\u00a3\u00a4"+
		"\4\62;\2\u00a4\62\3\2\2\2\u00a5\u00a6\4\u0082\1\2\u00a6\64\3\2\2\2\u00a7"+
		"\u00a8\7e\2\2\u00a8\u00a9\7n\2\2\u00a9\u00aa\7c\2\2\u00aa\u00ab\7u\2\2"+
		"\u00ab\u00af\7u\2\2\u00ac\u00ad\7\u9860\2\2\u00ad\u00af\7\u5227\2\2\u00ae"+
		"\u00a7\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\66\3\2\2\2\u00b0\u00b1\7h\2\2"+
		"\u00b1\u00b2\7w\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b7\7e\2\2\u00b4\u00b5"+
		"\7\u51ff\2\2\u00b5\u00b7\7\u5f11\2\2\u00b6\u00b0\3\2\2\2\u00b6\u00b4\3"+
		"\2\2\2\u00b78\3\2\2\2\u00b8\u00b9\7x\2\2\u00b9\u00ba\7c\2\2\u00ba\u00be"+
		"\7t\2\2\u00bb\u00bc\7\u8b8c\2\2\u00bc\u00be\7\u657a\2\2\u00bd\u00b8\3"+
		"\2\2\2\u00bd\u00bb\3\2\2\2\u00be:\3\2\2\2\u00bf\u00c0\7r\2\2\u00c0\u00c1"+
		"\7t\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7p\2\2\u00c3\u00c7\7v\2\2\u00c4"+
		"\u00c5\7\u5372\2\2\u00c5\u00c7\7\u51fc\2\2\u00c6\u00bf\3\2\2\2\u00c6\u00c4"+
		"\3\2\2\2\u00c7<\3\2\2\2\u00c8\u00c9\7r\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb"+
		"\7k\2\2\u00cb\u00cc\7p\2\2\u00cc\u00cd\7v\2\2\u00cd\u00ce\7n\2\2\u00ce"+
		"\u00d3\7p\2\2\u00cf\u00d0\7\u5372\2\2\u00d0\u00d1\7\u51fc\2\2\u00d1\u00d3"+
		"\7\u884e\2\2\u00d2\u00c8\3\2\2\2\u00d2\u00cf\3\2\2\2\u00d3>\3\2\2\2\u00d4"+
		"\u00d5\t\3\2\2\u00d5@\3\2\2\2\u00d6\u00d8\t\4\2\2\u00d7\u00d6\3\2\2\2"+
		"\u00d8\u00d9\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00daB\3"+
		"\2\2\2\u00db\u00df\7$\2\2\u00dc\u00de\13\2\2\2\u00dd\u00dc\3\2\2\2\u00de"+
		"\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e2\3\2"+
		"\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e3\7$\2\2\u00e3D\3\2\2\2\u00e4\u00e8"+
		"\5/\30\2\u00e5\u00e8\5\61\31\2\u00e6\u00e8\5\63\32\2\u00e7\u00e4\3\2\2"+
		"\2\u00e7\u00e5\3\2\2\2\u00e7\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00e7"+
		"\3\2\2\2\u00e9\u00ea\3\2\2\2\u00eaF\3\2\2\2\u00eb\u00ee\5E#\2\u00ec\u00ed"+
		"\7\60\2\2\u00ed\u00ef\5E#\2\u00ee\u00ec\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0"+
		"\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1H\3\2\2\2\u00f2\u00f4\t\5\2\2"+
		"\u00f3\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6"+
		"\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\b%\2\2\u00f8J\3\2\2\2\17\2\u00a1"+
		"\u00ae\u00b6\u00bd\u00c6\u00d2\u00d9\u00df\u00e7\u00e9\u00f0\u00f5\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}