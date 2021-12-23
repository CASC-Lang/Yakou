package org.casc.lang.ast

// Statements
typealias VariableDeclaration = Statement.VariableDeclaration
typealias IfStatement = Statement.IfStatement
typealias JForStatement = Statement.JForStatement
typealias BlockStatement = Statement.BlockStatement
typealias ExpressionStatement = Statement.ExpressionStatement
typealias ReturnStatement = Statement.ReturnStatement

// Expressions
typealias IntegerLiteral = Expression.IntegerLiteral
typealias FloatLiteral = Expression.FloatLiteral
typealias CharLiteral = Expression.CharLiteral
typealias StrLiteral = Expression.StrLiteral
typealias BoolLiteral = Expression.BoolLiteral
typealias NullLiteral = Expression.NullLiteral
typealias IdentifierCallExpression = Expression.IdentifierCallExpression
typealias FunctionCallExpression = Expression.FunctionCallExpression
typealias IndexExpression = Expression.IndexExpression
typealias AssignmentExpression = Expression.AssignmentExpression
typealias UnaryExpression = Expression.UnaryExpression
typealias BinaryExpression = Expression.BinaryExpression
typealias ArrayInitialization = Expression.ArrayInitialization
typealias ArrayDeclaration = Expression.ArrayDeclaration
