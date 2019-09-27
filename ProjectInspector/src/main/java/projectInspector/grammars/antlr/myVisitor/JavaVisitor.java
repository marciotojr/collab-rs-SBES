/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectInspector.grammars.antlr.myVisitor;

import projectInspector.grammars.antlr.code.JavaParser;
import projectInspector.grammars.antlr.code.JavaParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author 
 */
public class JavaVisitor extends JavaParserBaseVisitor<Object> {

    public static void log(ParserRuleContext ctx) {
        Token start = ctx.getStart();
        Token stop = ctx.getStop();
        System.out.println(Thread.currentThread().getStackTrace()[2].toString()
                + " \n\tLine begin= " + start.getLine()
                + " \n\tLine end =" + stop.getLine()
                + " \n\tColumn begin =" + (start.getCharPositionInLine()+1) + " - " + (start.getCharPositionInLine()+start.getText().length())
                + " \n\tColumn end =" + (stop.getCharPositionInLine() +1) + " - " + (stop.getCharPositionInLine()+stop.getText().length())
                + "\n\tText:"
                + "\n\t\t" + ctx.getText()
                + "\n\n"
        );
    }

    @Override
    public Object visitPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        log(ctx);
        return super.visitPackageDeclaration(ctx);
    }

    @Override
    public Object visitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        log(ctx);
        return super.visitClassDeclaration(ctx);
    }

    @Override
    public Object visitArguments(JavaParser.ArgumentsContext ctx) {
        log(ctx);

        return super.visitArguments(ctx);
    }

    @Override
    public Object visitExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx) {
        log(ctx);
        return super.visitExplicitGenericInvocationSuffix(ctx);
    }

    @Override
    public Object visitSuperSuffix(JavaParser.SuperSuffixContext ctx) {
        log(ctx);
        return super.visitSuperSuffix(ctx);
    }

    @Override
    public Object visitTypeArguments(JavaParser.TypeArgumentsContext ctx) {
        log(ctx);
        return super.visitTypeArguments(ctx);
    }

    @Override
    public Object visitPrimitiveType(JavaParser.PrimitiveTypeContext ctx) {
//        log(ctx);
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public Object visitTypeType(JavaParser.TypeTypeContext ctx) {
//        log(ctx);
        return super.visitTypeType(ctx);
    }

    @Override
    public Object visitTypeList(JavaParser.TypeListContext ctx) {
        log(ctx);
        return super.visitTypeList(ctx);
    }

    @Override
    public Object visitNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx) {
        log(ctx);
        return super.visitNonWildcardTypeArguments(ctx);
    }

    @Override
    public Object visitNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
        log(ctx);
        return super.visitNonWildcardTypeArgumentsOrDiamond(ctx);
    }

    @Override
    public Object visitTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx) {
        log(ctx);
        return super.visitTypeArgumentsOrDiamond(ctx);
    }

    @Override
    public Object visitExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx) {
        log(ctx);
        return super.visitExplicitGenericInvocation(ctx);
    }

    @Override
    public Object visitClassCreatorRest(JavaParser.ClassCreatorRestContext ctx) {
//        log(ctx);
        return super.visitClassCreatorRest(ctx);
    }

    @Override
    public Object visitArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx) {
        log(ctx);
        return super.visitArrayCreatorRest(ctx);
    }

    @Override
    public Object visitInnerCreator(JavaParser.InnerCreatorContext ctx) {
        log(ctx);
        return super.visitInnerCreator(ctx);
    }

    @Override
    public Object visitCreatedName(JavaParser.CreatedNameContext ctx) {
//        log(ctx);
        return super.visitCreatedName(ctx);
    }

    @Override
    public Object visitCreator(JavaParser.CreatorContext ctx) {
        log(ctx);
        return super.visitCreator(ctx);
    }

    @Override
    public Object visitClassType(JavaParser.ClassTypeContext ctx) {
        log(ctx);
        return super.visitClassType(ctx);
    }

    @Override
    public Object visitPrimary(JavaParser.PrimaryContext ctx) {
//        log(ctx);
        return super.visitPrimary(ctx);
    }

    @Override
    public Object visitLambdaBody(JavaParser.LambdaBodyContext ctx) {
        log(ctx);
        return super.visitLambdaBody(ctx);
    }

    @Override
    public Object visitLambdaParameters(JavaParser.LambdaParametersContext ctx) {
        log(ctx);
        return super.visitLambdaParameters(ctx);
    }

    @Override
    public Object visitLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
        log(ctx);
        return super.visitLambdaExpression(ctx);
    }

    @Override
    public Object visitExpression(JavaParser.ExpressionContext ctx) {
//        log(ctx);
        return super.visitExpression(ctx);
    }

    @Override
    public Object visitMethodCall(JavaParser.MethodCallContext ctx) {
        log(ctx);
        return super.visitMethodCall(ctx);
    }

    @Override
    public Object visitExpressionList(JavaParser.ExpressionListContext ctx) {
//        log(ctx);
        return super.visitExpressionList(ctx);
    }

    @Override
    public Object visitParExpression(JavaParser.ParExpressionContext ctx) {
        log(ctx);
        return super.visitParExpression(ctx);
    }

    @Override
    public Object visitEnhancedForControl(JavaParser.EnhancedForControlContext ctx) {
        log(ctx);
        return super.visitEnhancedForControl(ctx);
    }

    @Override
    public Object visitForInit(JavaParser.ForInitContext ctx) {
        log(ctx);
        return super.visitForInit(ctx);
    }

    @Override
    public Object visitForControl(JavaParser.ForControlContext ctx) {
        log(ctx);
        return super.visitForControl(ctx);
    }

    @Override
    public Object visitSwitchLabel(JavaParser.SwitchLabelContext ctx) {
        log(ctx);
        return super.visitSwitchLabel(ctx);
    }

    @Override
    public Object visitSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx) {
        log(ctx);
        return super.visitSwitchBlockStatementGroup(ctx);
    }

    @Override
    public Object visitResource(JavaParser.ResourceContext ctx) {
        log(ctx);
        return super.visitResource(ctx);
    }

    @Override
    public Object visitResources(JavaParser.ResourcesContext ctx) {
        log(ctx);
        return super.visitResources(ctx);
    }

    @Override
    public Object visitResourceSpecification(JavaParser.ResourceSpecificationContext ctx) {
        log(ctx);
        return super.visitResourceSpecification(ctx);
    }

    @Override
    public Object visitFinallyBlock(JavaParser.FinallyBlockContext ctx) {
        log(ctx);
        return super.visitFinallyBlock(ctx);
    }

    @Override
    public Object visitCatchType(JavaParser.CatchTypeContext ctx) {
        log(ctx);
        return super.visitCatchType(ctx);
    }

    @Override
    public Object visitCatchClause(JavaParser.CatchClauseContext ctx) {
        log(ctx);
        return super.visitCatchClause(ctx);
    }

    @Override
    public Object visitStatement(JavaParser.StatementContext ctx) {
//        log(ctx);
        return super.visitStatement(ctx);
    }

    @Override
    public Object visitLocalTypeDeclaration(JavaParser.LocalTypeDeclarationContext ctx) {
        log(ctx);
        return super.visitLocalTypeDeclaration(ctx);
    }

    @Override
    public Object visitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        log(ctx);
        return super.visitLocalVariableDeclaration(ctx);
    }

    @Override
    public Object visitBlockStatement(JavaParser.BlockStatementContext ctx) {
//        log(ctx);
        return super.visitBlockStatement(ctx);
    }

    @Override
    public Object visitBlock(JavaParser.BlockContext ctx) {
//        log(ctx);
        return super.visitBlock(ctx);
    }

    @Override
    public Object visitDefaultValue(JavaParser.DefaultValueContext ctx) {
        log(ctx);
        return super.visitDefaultValue(ctx);
    }

    @Override
    public Object visitAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx) {
        log(ctx);
        return super.visitAnnotationConstantRest(ctx);
    }

    @Override
    public Object visitAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx) {
        log(ctx);
        return super.visitAnnotationMethodRest(ctx);
    }

    @Override
    public Object visitAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx) {
        log(ctx);
        return super.visitAnnotationMethodOrConstantRest(ctx);
    }

    @Override
    public Object visitAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx) {
        log(ctx);
        return super.visitAnnotationTypeElementRest(ctx);
    }

    @Override
    public Object visitAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx) {
        log(ctx);
        return super.visitAnnotationTypeElementDeclaration(ctx);
    }

    @Override
    public Object visitAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx) {
        log(ctx);
        return super.visitAnnotationTypeBody(ctx);
    }

    @Override
    public Object visitAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
        log(ctx);
        return super.visitAnnotationTypeDeclaration(ctx);
    }

    @Override
    public Object visitElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx) {
        log(ctx);
        return super.visitElementValueArrayInitializer(ctx);
    }

    @Override
    public Object visitElementValue(JavaParser.ElementValueContext ctx) {
        log(ctx);
        return super.visitElementValue(ctx);
    }

    @Override
    public Object visitElementValuePair(JavaParser.ElementValuePairContext ctx) {
        log(ctx);
        return super.visitElementValuePair(ctx);
    }

    @Override
    public Object visitElementValuePairs(JavaParser.ElementValuePairsContext ctx) {
        log(ctx);
        return super.visitElementValuePairs(ctx);
    }

    @Override
    public Object visitAnnotation(JavaParser.AnnotationContext ctx) {
        log(ctx);
        return super.visitAnnotation(ctx);
    }

    @Override
    public Object visitFloatLiteral(JavaParser.FloatLiteralContext ctx) {
//        log(ctx);
        return super.visitFloatLiteral(ctx);
    }

    @Override
    public Object visitIntegerLiteral(JavaParser.IntegerLiteralContext ctx) {
//        log(ctx);
        return super.visitIntegerLiteral(ctx);
    }

    @Override
    public Object visitLiteral(JavaParser.LiteralContext ctx) {
//        log(ctx);
        return super.visitLiteral(ctx);
    }

    @Override
    public Object visitQualifiedName(JavaParser.QualifiedNameContext ctx) {
//        log(ctx);
        return super.visitQualifiedName(ctx);
    }

    @Override
    public Object visitLastFormalParameter(JavaParser.LastFormalParameterContext ctx) {
        log(ctx);
        return super.visitLastFormalParameter(ctx);
    }

    @Override
    public Object visitFormalParameter(JavaParser.FormalParameterContext ctx) {
        log(ctx);
        return super.visitFormalParameter(ctx);
    }

    @Override
    public Object visitFormalParameterList(JavaParser.FormalParameterListContext ctx) {
//        log(ctx);
        return super.visitFormalParameterList(ctx);
    }

    @Override
    public Object visitFormalParameters(JavaParser.FormalParametersContext ctx) {
//        log(ctx);
        return super.visitFormalParameters(ctx);
    }

    @Override
    public Object visitQualifiedNameList(JavaParser.QualifiedNameListContext ctx) {
        log(ctx);
        return super.visitQualifiedNameList(ctx);
    }

    @Override
    public Object visitTypeArgument(JavaParser.TypeArgumentContext ctx) {
        log(ctx);
        return super.visitTypeArgument(ctx);
    }

    @Override
    public Object visitClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {
//        log(ctx);
        return super.visitClassOrInterfaceType(ctx);
    }

    @Override
    public Object visitArrayInitializer(JavaParser.ArrayInitializerContext ctx) {
        log(ctx);
        return super.visitArrayInitializer(ctx);
    }

    @Override
    public Object visitVariableInitializer(JavaParser.VariableInitializerContext ctx) {
        log(ctx);
        return super.visitVariableInitializer(ctx);
    }

    @Override
    public Object visitVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx) {
//        log(ctx);
        return super.visitVariableDeclaratorId(ctx);
    }

    @Override
    public Object visitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
//        log(ctx);
        return super.visitVariableDeclarator(ctx);
    }

    @Override
    public Object visitVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx) {
//        log(ctx);
        return super.visitVariableDeclarators(ctx);
    }

    @Override
    public Object visitGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx) {
        log(ctx);
        return super.visitGenericInterfaceMethodDeclaration(ctx);
    }

    @Override
    public Object visitInterfaceMethodModifier(JavaParser.InterfaceMethodModifierContext ctx) {
        log(ctx);
        return super.visitInterfaceMethodModifier(ctx);
    }

    @Override
    public Object visitInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx) {
        log(ctx);
        return super.visitInterfaceMethodDeclaration(ctx);
    }

    @Override
    public Object visitConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx) {
        log(ctx);
        return super.visitConstantDeclarator(ctx);
    }

    @Override
    public Object visitConstDeclaration(JavaParser.ConstDeclarationContext ctx) {
        log(ctx);
        return super.visitConstDeclaration(ctx);
    }

    @Override
    public Object visitInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx) {
        log(ctx);
        return super.visitInterfaceMemberDeclaration(ctx);
    }

    @Override
    public Object visitInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx) {
        log(ctx);
        return super.visitInterfaceBodyDeclaration(ctx);
    }

    @Override
    public Object visitFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        log(ctx);
        return super.visitFieldDeclaration(ctx);
    }

    @Override
    public Object visitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        log(ctx);
        return super.visitConstructorDeclaration(ctx);
    }

    @Override
    public Object visitGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx) {
        log(ctx);
        return super.visitGenericConstructorDeclaration(ctx);
    }

    @Override
    public Object visitGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx) {
        log(ctx);
        return super.visitGenericMethodDeclaration(ctx);
    }

    @Override
    public Object visitTypeTypeOrVoid(JavaParser.TypeTypeOrVoidContext ctx) {
//        log(ctx);
        return super.visitTypeTypeOrVoid(ctx);
    }

    @Override
    public Object visitMethodBody(JavaParser.MethodBodyContext ctx) {
//        log(ctx);
        return super.visitMethodBody(ctx);
    }

    @Override
    public Object visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        log(ctx);
        return super.visitMethodDeclaration(ctx);
    }

    @Override
    public Object visitMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
//        log(ctx);
        return super.visitMemberDeclaration(ctx);
    }

    @Override
    public Object visitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
//        log(ctx);
        return super.visitClassBodyDeclaration(ctx);
    }

    @Override
    public Object visitInterfaceBody(JavaParser.InterfaceBodyContext ctx) {
        log(ctx);
        return super.visitInterfaceBody(ctx);
    }

    @Override
    public Object visitClassBody(JavaParser.ClassBodyContext ctx) {
//        log(ctx);
        return super.visitClassBody(ctx);
    }

    @Override
    public Object visitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        log(ctx);
        return super.visitInterfaceDeclaration(ctx);
    }

    @Override
    public Object visitEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx) {
        log(ctx);
        return super.visitEnumBodyDeclarations(ctx);
    }

    @Override
    public Object visitEnumConstant(JavaParser.EnumConstantContext ctx) {
        log(ctx);
        return super.visitEnumConstant(ctx);
    }

    @Override
    public Object visitEnumConstants(JavaParser.EnumConstantsContext ctx) {
        log(ctx);
        return super.visitEnumConstants(ctx);
    }

    @Override
    public Object visitEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        log(ctx);
        return super.visitEnumDeclaration(ctx);
    }

    @Override
    public Object visitTypeBound(JavaParser.TypeBoundContext ctx) {
        log(ctx);
        return super.visitTypeBound(ctx);
    }

    @Override
    public Object visitTypeParameter(JavaParser.TypeParameterContext ctx) {
        log(ctx);
        return super.visitTypeParameter(ctx);
    }

    @Override
    public Object visitTypeParameters(JavaParser.TypeParametersContext ctx) {
        log(ctx);
        return super.visitTypeParameters(ctx);
    }

    @Override
    public Object visitVariableModifier(JavaParser.VariableModifierContext ctx) {
        log(ctx);
        return super.visitVariableModifier(ctx);
    }

    @Override
    public Object visitClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx) {
//        log(ctx);
        return super.visitClassOrInterfaceModifier(ctx);
    }

    @Override
    public Object visitModifier(JavaParser.ModifierContext ctx) {
//        log(ctx);
        return super.visitModifier(ctx);
    }

    @Override
    public Object visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
//        log(ctx);
        return super.visitTypeDeclaration(ctx);
    }

    @Override
    public Object visitImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        log(ctx);
        return super.visitImportDeclaration(ctx);
    }

    @Override
    public Object visitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
//        log(ctx);
        return super.visitCompilationUnit(ctx);
    }

}
