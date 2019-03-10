package com.module_router.compiler;

import com.google.auto.service.AutoService;
import com.module_router.annotation.Route;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * create by zuyuan on 2019/3/10
 */
@AutoService(Processor.class)   //register
@SupportedSourceVersion(SourceVersion.RELEASE_7)    //java7
public class AnnotationProcessor extends AbstractProcessor {

    private ClassBuilder mBuilder;

    private Messager mMessager;     //log

    private Elements mElUtils;      //helper

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //select type
        Set<String> types = new LinkedHashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mBuilder.clear();

        if (roundEnvironment == null) return true;

        //find a module's node
        for (Element e : roundEnvironment.getElementsAnnotatedWith(Route.class)) {
            Node n = createNode(e);
            if (n != null) mBuilder.addNode(n);
        }
        //input table
        RouterFileUtil.saveTable(mBuilder.getNodes());
        //create class
        mBuilder.generateJavaFile();
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElUtils = processingEnvironment.getElementUtils();
        Filer filer = processingEnvironment.getFiler();
        mBuilder = new ClassBuilder(mElUtils, mMessager, filer);
    }

    private Node createNode(Element e) {
        if (e == null) return null;

        if (!e.getKind().isClass()) {
            mMessager.printMessage(Diagnostic.Kind.ERROR,
                    "The annotated must be class!");
            return null;
        }
        Route rAnno = e.getAnnotation(Route.class);
        if (rAnno == null) return null;

        Node node = new Node();
        node.setName(rAnno.name());
        node.setModule(rAnno.module());
        node.setDesc(rAnno.desc());
        String packageName = mElUtils.getPackageOf(e).toString();
        String uri = "router://" + rAnno.module() + "/" + rAnno.name();
        String className = e.getSimpleName().toString() + ".class";
        node.setPackageName(packageName);
        node.setUrl(uri);
        node.setClassName(className);
        node.setModulePackageName("com.module_router" + "." + node.getModule());

        return node;
    }
}
