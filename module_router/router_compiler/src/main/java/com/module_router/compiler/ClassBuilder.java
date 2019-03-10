package com.module_router.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class ClassBuilder {
    private Messager mMessager;
    private Elements mElements;
    private Filer mFiler;

    private List<Node> mNodes ;

    private static final String SUPER_CLASS_NAME = "com.module_router.go.BaseClassMap";

    ClassBuilder(Elements elements, Messager messager, Filer filer) {
        this.mElements = elements;
        this.mMessager = messager;
        this.mFiler = filer;

        mNodes = new ArrayList<>();
    }

    public void clear() {
        mNodes.clear();
    }

    public void addNode(Node node) {
        mNodes.add(node);
    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public void generateJavaFile() {
        if (mNodes.size() == 0) return;

        ClassName superName = ClassName.get(mElements.getTypeElement(SUPER_CLASS_NAME));
        String className = mNodes.get(0).getModule() + "$$Inject";
        String packageName = mNodes.get(0).getModulePackageName();

        MethodSpec.Builder injectMethod = MethodSpec.methodBuilder("add")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);
        for (Node n : mNodes) {
            injectMethod.addCode(
                    "mMap.put($S, "+n.getPackageName() + "." + n.getClassName()+");\n",
                    n.getUrl());
        }
        MethodSpec methodSpec = injectMethod.build();

        TypeSpec classSpec =TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superName)
                .addMethod(methodSpec)
                .build();

        try {
            //write java file
            JavaFile.builder(packageName, classSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR,
                    "Router ---> create file error!");
        }
    }
}
