package org.casc.lang.ast

// Statements
typealias VariableDeclaration = Statement.VariableDeclaration
typealias ExpressionStatement = Statement.ExpressionStatement
typealias ReturnStatement = Statement.ReturnStatement

// Expressions
typealias IntegerLiteral = Expression.IntegerLiteral
typealias FloatLiteral = Expression.FloatLiteral
typealias IdentifierCallExpression = Expression.IdentifierCallExpression
typealias FunctionCallExpression = Expression.FunctionCallExpression
typealias IndexExpression = Expression.IndexExpression
typealias AssignmentExpression = Expression.AssignmentExpression
typealias UnaryExpression = Expression.UnaryExpression
typealias BinaryExpression = Expression.BinaryExpression
typealias ArrayInitialization = Expression.ArrayInitialization
typealias ArrayDeclaration = Expression.ArrayDeclaration
