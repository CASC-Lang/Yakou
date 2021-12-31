import org.casc.lang.ast.TokenType
import org.casc.lang.lexer.Lexer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class TokenTest {
    companion object {
        private val constantTokens = TokenType.values().toMutableList().also {
            it.removeAll(
                arrayOf(
                    TokenType.Identifier,
                    TokenType.IntegerLiteral,
                    TokenType.CharLiteral,
                    TokenType.StringLiteral,
                    TokenType.FloatLiteral
                )
            )
        }

        private fun getTokenSet(vararg tokenOrdinals: Int): Array<TokenType> {
            val tokens = mutableSetOf<TokenType>()

            for (i in tokenOrdinals) {
                tokens += constantTokens[i]
            }

            return tokens.toTypedArray()
        }

        private fun generateDummyText(vararg tokenOrdinals: Int): String {
            val textBuilder = StringBuilder()

            for (i in tokenOrdinals) {
                // Add separator for token literal
                textBuilder.append("${constantTokens[i].literal} ")
            }

            return textBuilder.toString()
        }
    }

    @TestFactory
    fun testToken(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()
        val size = constantTokens.size

        for (i in 0 until size) {
            for (j in i + 1 until size) {
                for (k in j + 1 until size) {
                    val dummyText = generateDummyText(i, j, k)

                    tests += DynamicTest.dynamicTest("test $dummyText") {
                        val lexer = Lexer(listOf(dummyText))
                        val resultTokenTypes = lexer.lex().second.map { it.type }.toTypedArray()

                        Assertions.assertArrayEquals(getTokenSet(i, j, k), resultTokenTypes)
                    }
                }
            }
        }

        return tests
    }
}